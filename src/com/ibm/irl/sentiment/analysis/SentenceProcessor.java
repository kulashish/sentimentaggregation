package com.ibm.irl.sentiment.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.ibm.irl.sentiment.ontology.DomainNode;
import com.ibm.irl.sentiment.ontology.DomainStructure;
import com.ibm.irl.sentiment.ontology.OntologyNode;
import com.ibm.irl.sentiment.util.SentimentUtil;

import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.BasicDependenciesAnnotation;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.util.CoreMap;

public class SentenceProcessor {

	protected CoreMap sentence;
	protected SemanticGraph graph;
	protected List<CoreLabel> tokens;
	protected Map<CoreLabel, NounsMapEntry> nounsMap;
	protected DomainStructure structure;
	private PhraseAnnotatedConceptOntology pOntology;

	public SentenceProcessor(CoreMap sentence) throws TextProcessorException {
		// try {
		structure = DomainStructure.getStructure();
		// } catch (OntologyException e) {
		// throw new TextProcessorException(e);
		// }
		this.sentence = sentence;
		graph = sentence.get(BasicDependenciesAnnotation.class);
		pruneGraph();
		tokens = sentence.get(TokensAnnotation.class);
	}

	public SentenceProcessor(CoreMap sentence,
			PhraseAnnotatedConceptOntology onto) throws TextProcessorException {
		this(sentence);
		pOntology = onto;
	}

	public Map<CoreLabel, NounsMapEntry> getNounsMap() {
		return nounsMap;
	}

	private void pruneGraph() {
		SemanticGraph graphCopy = new SemanticGraph(graph);
		Iterator<SemanticGraphEdge> iter = graphCopy.edgeIterable().iterator();
		SemanticGraphEdge edge = null;
		while (iter.hasNext()) {
			edge = iter.next();
			if (!SignificantDepRelations.getRelationsList().contains(
					edge.getRelation().getShortName()))
				graph.removeEdge(edge);
		}
	}

	public void process() {
		extractConcepts();
		String word = null;
		CoreLabel noun = null;
		// Map words to nearest concepts
		for (CoreLabel token : tokens) {
			word = token.get(TextAnnotation.class);
			if (!nounsMap.keySet().contains(token)) {
				noun = findNearestNoun(word);
				if (noun != null)
					nounsMap.get(noun).addWord(token);
			}
		}

		for (Entry<CoreLabel, NounsMapEntry> e : nounsMap.entrySet()) {
			addEntry(e.getValue().oNode,
					new ConceptSnippet(e.getKey(), e.getValue().words));
		}
	}

	protected void addEntry(DomainNode node, ConceptSnippet cs) {
		pOntology.addEntry((OntologyNode) node, cs);
	}

	private CoreLabel findNearestNoun(String word) {
		int gDist = 9999;
		int tDist = 9999;
		CoreLabel nearestNoun = null;
		List<SemanticGraphEdge> edgePath = null;
		IndexedWord indexedNoun = null;
		IndexedWord indexedWord = graph.getNodeByWordPattern(SentimentUtil
				.preprocess(word));
		int currTdist = 0;
		if (indexedWord != null)
			for (CoreLabel noun : nounsMap.keySet()) {
				indexedNoun = graph.getNodeByWordPattern(noun.word());
				edgePath = graph.getShortestUndirectedPathEdges(indexedNoun,
						indexedWord);
				if (indexedNoun != null)
					currTdist = Math.abs(indexedNoun.index()
							- indexedWord.index());
				if (edgePath != null
						&& (edgePath.size() < gDist || (edgePath.size() == gDist && currTdist < tDist))) {
					gDist = edgePath.size();
					tDist = currTdist;
					nearestNoun = noun;
				}
			}
		return nearestNoun;
	}

	protected void extractConcepts() {
		nounsMap = new HashMap<CoreLabel, NounsMapEntry>();
		String pos = null;
		String ne = null;
		for (CoreLabel token : tokens) {
			pos = token.get(PartOfSpeechAnnotation.class);
			// ne = token.get(NamedEntityTagAnnotation.class);
			if (pos.equalsIgnoreCase("NN") || pos.equalsIgnoreCase("NNS")
					|| pos.equalsIgnoreCase("NNP")) {
				DomainNode node = structure.lookup(token);
				if (node != null)
					nounsMap.put(token, new NounsMapEntry(node));
			}
		}
	}

	public List<String> getDomainTerms() {
		List<String> concepts = new ArrayList<String>();
		String pos = null;
		for (CoreLabel token : tokens) {
			pos = token.get(PartOfSpeechAnnotation.class);
			// ne = token.get(NamedEntityTagAnnotation.class);
			if (pos.equalsIgnoreCase("NN") || pos.equalsIgnoreCase("NNS")
					|| pos.equalsIgnoreCase("NNP")) {
				DomainNode node = structure.lookup(token);
				if (node != null)
					concepts.add(node.getName());
			}
		}
		return concepts;
	}

	class NounsMapEntry {
		public NounsMapEntry(DomainNode node) {
			oNode = node;
			words = new ArrayList<CoreLabel>();
		}

		public void addWord(CoreLabel token) {
			words.add(token);
		}

		private DomainNode oNode;
		private List<CoreLabel> words;
	}

}
