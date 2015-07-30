package com.ibm.irl.sentiment.ontology;

import java.util.ArrayList;
import java.util.List;

import com.ibm.irl.sentiment.analysis.ConceptSentimentDetector;
import com.ibm.irl.sentiment.analysis.ConceptSnippet;

public class SentimentGraphNode extends DomainNode {
	private static ConceptSentimentDetector sentimentDetector = new ConceptSentimentDetector();

	private List<SentimentGraphNode> influencedNodes;
	private List<ConceptSnippet> conceptSnippets;

	public SentimentGraphNode(String label) {
		name = label;
		influencedNodes = new ArrayList<SentimentGraphNode>();
		conceptSnippets = new ArrayList<ConceptSnippet>();
	}

	public boolean addInfluence(SentimentGraphNode node) {
		boolean blnContains = influencedNodes.contains(node);
		if (!blnContains)
			influencedNodes.add(node);
		return !blnContains;
	}

	public List<SentimentGraphNode> getInfluencedNodes() {
		return influencedNodes;
	}

	public void addConceptSnippet(ConceptSnippet cs) {
		conceptSnippets.add(cs);
		sentiment = sentimentDetector.sentiment(conceptSnippets);
	}

}
