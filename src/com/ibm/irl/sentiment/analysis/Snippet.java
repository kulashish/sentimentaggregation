package com.ibm.irl.sentiment.analysis;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import edu.stanford.nlp.ling.CoreLabel;

public class Snippet {

	@JsonIgnore
	private static SnippetSentimentDetector sentimentDetector;
	private List<String> text;
	@JsonIgnore
	private List<CoreLabel> tokens;
	private short sentiment;

	static {
		sentimentDetector = new SnippetSentimentDetector();
	}

	// public Snippet(List<String> value) {
	// text = value;
	// sentimentDetector.sentiment(this);
	// }

	public Snippet() {

	}

	public Snippet(List<CoreLabel> value) {
		tokens = value;
		text = new ArrayList<String>();
		for (CoreLabel token : tokens)
			text.add(token.word());
		sentimentDetector.sentiment(this);
	}

	public List<CoreLabel> getTokens() {
		return tokens;
	}

	public void setTokens(List<CoreLabel> tokens) {
		this.tokens = tokens;
	}

	public short getSentiment() {
		return sentiment;
	}

	public void setSentiment(short sentiment) {
		this.sentiment = sentiment;
	}

	public List<String> getText() {
		return text;
	}

	public void setText(List<String> text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "Snippet [text=" + text + ", sentiment=" + sentiment + "]";
	}

}
