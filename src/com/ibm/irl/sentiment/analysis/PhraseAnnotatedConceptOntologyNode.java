package com.ibm.irl.sentiment.analysis;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PhraseAnnotatedConceptOntologyNode {
	@JsonIgnore
	private static ConceptSentimentDetector sentimentDetector = new ConceptSentimentDetector();
	private List<ConceptSnippet> conceptSnippetList;
	private short sentiment;
	private int aggregateSentiment;

	public PhraseAnnotatedConceptOntologyNode() {
		conceptSnippetList = new ArrayList<ConceptSnippet>();
	}

	public void addConceptSnippet(ConceptSnippet cs) {
		conceptSnippetList.add(cs);
		sentiment = sentimentDetector.sentiment(conceptSnippetList);
	}

	public int getAggregateSentiment() {
		return aggregateSentiment;
	}

	public void setAggregateSentiment(int aggregateSentiment) {
		this.aggregateSentiment = aggregateSentiment;
	}

	public short getSentiment() {
		return sentiment;
	}

	public void setSentiment(short sentiment) {
		this.sentiment = sentiment;
	}

	public List<ConceptSnippet> getConceptSnippetList() {
		return conceptSnippetList;
	}

}
