package com.ibm.irl.sentiment.analysis;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import com.ibm.irl.sentiment.ontology.Ontology;
import com.ibm.irl.sentiment.ontology.OntologyException;
import com.ibm.irl.sentiment.ontology.OntologyNode;
import com.ibm.irl.sentiment.util.SentimentUtil;

import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.BasicDependenciesAnnotation;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.util.CoreMap;

/**
 * 
 * @author Ashish
 * 
 *         Reads a document and associates domain concepts with phrases in which
 *         they occur
 * 
 */
public class TextProcessor {
	protected File file;
	protected String text;
	protected StanfordCoreNLP pipeline;
	// private ConceptSnippetMap csMap;
	private PhraseAnnotatedConceptOntology psot;

	public TextProcessor(String t) throws TextProcessorException {
		text = t;
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse");
		pipeline = new StanfordCoreNLP(props);
		// csMap = new ConceptSnippetMap();
		try {
			psot = new PhraseAnnotatedConceptOntology(Ontology.getOntology());
		} catch (OntologyException e) {
			throw new TextProcessorException(e);
		}
	}

	public TextProcessor(File file) throws TextProcessorException, IOException {
		this(SentimentUtil.readFile(file));
		this.file = file;
	}

	public void process() throws IOException, TextProcessorException {
		Annotation document = new Annotation(text);
		pipeline.annotate(document);
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		SentenceProcessor sentProcessor = null;
		for (CoreMap sentence : sentences) {
			sentProcessor = new SentenceProcessor(sentence, psot);
			sentProcessor.process();
			// map(sentProcessor.mapWordsToNouns());
		}
	}

	public String getText() {
		return text;
	}

	public PhraseAnnotatedConceptOntology getPsot() {
		return psot;
	}

	private void map(Map<CoreLabel, List<CoreLabel>> nounsMap)
			throws OntologyException {
		Iterator<Entry<CoreLabel, List<CoreLabel>>> iter = nounsMap.entrySet()
				.iterator();
		Entry<CoreLabel, List<CoreLabel>> entry = null;
		OntologyNode node = null;
		while (iter.hasNext()) {
			entry = iter.next();
			// Add an entry to the Concept-Snippet map if there is a snippet
			// associated with the concept
			node = Ontology.getOntology().lookup(entry.getKey());
			ConceptSnippet cs = null;
			if (node != null && entry.getValue() != null
					&& !entry.getValue().isEmpty()) {
				cs = new ConceptSnippet(entry.getKey(), entry.getValue());
				psot.addEntry(node, cs);
			}
		}
	}

	public void collectStats() throws IOException {
		Annotation document = null;
		ParseDepStatistics depStats = new ParseDepStatistics();
		if (file != null && file.isDirectory()) {
			for (File f : file.listFiles()) {
				document = new Annotation(SentimentUtil.readFile(f));
				pipeline.annotate(document);
				depStats.collect(document);
			}
			depStats.print();
		}
		if (file == null && text != null) {
			document = new Annotation(text);
			pipeline.annotate(document);
			depStats.collect(document);
		}
	}

	private void print(Annotation document) {
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		SemanticGraph semGraph = null;
		// for (CoreMap sentence : sentences) {
		System.out.println(sentences.get(0).get(TextAnnotation.class));
		semGraph = sentences.get(0).get(BasicDependenciesAnnotation.class);
		// System.out.println(semGraph.toFormattedString());
		for (TypedDependency dep : semGraph.typedDependencies())
			System.out.println(dep.reln());
		// }
	}

	public List<String> extractPersonNames() {
		Annotation document = new Annotation(text);
		pipeline.annotate(document);
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		List<CoreLabel> tokens = null;
		String ne = null;
		List<String> personsList = new ArrayList<String>();
		for (CoreMap sentence : sentences) {
			tokens = sentence.get(TokensAnnotation.class);
			for (CoreLabel token : tokens) {
				ne = token.ner();
				if ("PERSON".equalsIgnoreCase(ne))
					personsList.add(token.originalText());
			}
		}
		return personsList;
	}

	// public ConceptSnippetMap getCsMap() {
	// return csMap;
	// }

	public static void main(String args[]) {
		String sample = "by sheer force of talent , the three actors wring marginal enjoyment from the proceedings whenever they're on screen , but the mod squad is just a second-rate action picture with a first-rate cast .";
		String file = null;
		if (args.length == 1)
			file = args[0];
		try {
			TextProcessor processor = null;
			if (null != file)
				processor = new TextProcessor(new File(file));
			else
				processor = new TextProcessor(sample);
			// processor.collectStats();
			processor.process();
			System.out.println(processor.getPsot().printJson());
			System.out.println(processor.getPsot().getSentiment());
			System.out.println(processor.getPsot().getLog());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TextProcessorException e) {
			e.printStackTrace();
		}
	}
}
