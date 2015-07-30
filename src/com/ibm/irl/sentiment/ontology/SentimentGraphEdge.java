package com.ibm.irl.sentiment.ontology;

public class SentimentGraphEdge {

	private SentimentGraphNode source;
	private SentimentGraphNode target;

	public SentimentGraphEdge(SentimentGraphNode s, SentimentGraphNode t) {
		source = s;
		target = t;
	}

	public SentimentGraphNode getSource() {
		return source;
	}

	public void setSource(SentimentGraphNode source) {
		this.source = source;
	}

	public SentimentGraphNode getTarget() {
		return target;
	}

	public void setTarget(SentimentGraphNode target) {
		this.target = target;
	}

}
