package com.ibm.irl.sentiment.analysis;

import java.util.List;

public class ConceptSentimentDetector {

	public short sentiment(List<ConceptSnippet> csList) {
		short overallSentiment = 0;
		for (ConceptSnippet cs : csList)
			overallSentiment += cs.getSnippet().getSentiment();
		return overallSentiment;
	}
}
