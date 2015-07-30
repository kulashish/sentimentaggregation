package com.ibm.irl.sentiment.ontology;

public class DomainNode {

	protected String name;
	protected short sentiment;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public short getSentiment() {
		return sentiment;
	}

	public void setSentiment(short sentiment) {
		this.sentiment = sentiment;
	}

}
