package com.ibm.irl.sentiment.ontology;

import com.hp.hpl.jena.ontology.OntModel;

public abstract class OntologyReader {
	protected String ontPath;
	protected OntModel model;

	public OntologyReader(String path) {
		ontPath = path;
	}

	public abstract SentimentGraph load();

}
