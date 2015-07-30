package com.ibm.irl.sentiment.ontology;

import java.util.Iterator;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;

public class RDFSOntology extends OntologyGraph {

	public RDFSOntology(OntModel model) {
		super(model);
	}

	protected void createEdges() {
		Iterator<OntClass> classes = model.listClasses();
		OntClass claz = null;
		while (classes.hasNext()) {
			claz = classes.next();
			createEdge(claz, claz.getSuperClass());
		}
	}
}
