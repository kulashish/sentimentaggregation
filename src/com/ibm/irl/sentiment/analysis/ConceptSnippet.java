package com.ibm.irl.sentiment.analysis;

import java.util.List;

import edu.stanford.nlp.ling.CoreLabel;

public class ConceptSnippet {

	public ConceptSnippet(Concept c, Snippet s) {
		concept = c;
		snippet = s;
	}

	public ConceptSnippet(CoreLabel key, List<CoreLabel> value) {
		concept = new Concept(key);
		snippet = new Snippet(value);
	}

	public Concept getConcept() {
		return concept;
	}

	public Snippet getSnippet() {
		return snippet;
	}

	private Concept concept;
	private Snippet snippet;
}
