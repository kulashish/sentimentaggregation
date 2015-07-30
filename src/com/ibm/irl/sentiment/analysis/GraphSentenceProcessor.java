package com.ibm.irl.sentiment.analysis;

import com.ibm.irl.sentiment.ontology.DomainNode;
import com.ibm.irl.sentiment.ontology.SentimentGraph;
import com.ibm.irl.sentiment.ontology.SentimentGraphNode;

import edu.stanford.nlp.util.CoreMap;

public class GraphSentenceProcessor extends SentenceProcessor {

	public GraphSentenceProcessor(CoreMap sentence, SentimentGraph g)
			throws TextProcessorException {
		super(sentence);
		structure = g;
	}

	protected void addEntry(DomainNode node, ConceptSnippet cs) {
		((SentimentGraph) structure).addEntry((SentimentGraphNode) node, cs);
	}

}
