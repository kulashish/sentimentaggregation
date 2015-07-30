package com.ibm.irl.sentiment.analysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import edu.stanford.nlp.dcoref.CoNLL2011DocumentReader.NamedEntityAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.BasicDependenciesAnnotation;
import edu.stanford.nlp.trees.GrammaticalRelation;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.util.CoreMap;

public class ParseDepStatistics {
	private Map<GrammaticalRelation, Integer> relMap;
	private List<String> nouns;
	private int numSentences;

	public ParseDepStatistics() {
		relMap = new HashMap<GrammaticalRelation, Integer>();
		numSentences = 0;
	}

	public void collect(Annotation document) {
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		numSentences += sentences.size();
		SemanticGraph semGraph = null;
		GrammaticalRelation rel = null;
		for (CoreMap sentence : sentences) {
			semGraph = sentence.get(BasicDependenciesAnnotation.class);
			for (TypedDependency dep : semGraph.typedDependencies()) {
				rel = dep.reln();
				int count = relMap.containsKey(rel) ? relMap.get(rel) : 0;
				relMap.put(rel, count + 1);
			}
			nouns = extractNouns(sentence);
		}
	}

	public List<String> extractNouns(CoreMap sentence) {
		List<String> nounsList = new ArrayList<String>();
		String word = null;
		String pos = null;
		String ne = null;
		for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
			word = token.get(TextAnnotation.class);
			pos = token.get(PartOfSpeechAnnotation.class);
			ne = token.get(NamedEntityTagAnnotation.class);
			System.out.println(word + "::" + pos + "::" + ne);
		}

		return nounsList;
	}

	public void print() {
		System.out.println("Number of sentences: " + numSentences);
		List<Entry<GrammaticalRelation, Integer>> list = new LinkedList<Entry<GrammaticalRelation, Integer>>(
				relMap.entrySet());
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o1)).getValue())
						.compareTo(((Map.Entry) (o2)).getValue());
			}
		});
		Iterator<Entry<GrammaticalRelation, Integer>> iter = list.iterator();
		Entry<GrammaticalRelation, Integer> entry = null;
		while (iter.hasNext()) {
			entry = iter.next();
			System.out.println(entry.getKey() + ":" + entry.getValue() * 1.0
					/ numSentences);
		}
	}
}
