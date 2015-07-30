package com.ibm.irl.sentiment.ontology;

import java.io.InputStream;

import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;

public class RDFSOntologyReader extends OntologyReader {

	public RDFSOntologyReader(String path) {
		super(path);
	}

	public RDFSOntology load() {
		model = ModelFactory
				.createOntologyModel("http://www.w3.org/2000/01/rdf-schema#");
		InputStream stream = FileManager.get().open(ontPath);
		model.read(stream, null);
		return new RDFSOntology(model);
	}

	// public static void main(String[] args) {
	// RDFSOntologyReader reader = new RDFSOntologyReader();
	// reader.load();
	// }

}
