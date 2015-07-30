package com.ibm.irl.sentiment.ontology;

import java.util.Iterator;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;

public abstract class OntologyGraph extends SentimentGraph {
	protected OntModel model;

	public OntologyGraph(OntModel model) {
		this.model = model;
		createNodes();
		createEdges();
	}

	public OntologyGraph() {
	}

	abstract protected void createEdges();

	protected void createNodes() {
		Iterator<OntClass> classes = model.listNamedClasses();
		OntClass claz = null;
		while (classes.hasNext() && !(claz = classes.next()).isAnon())
			nodesList.add(new SentimentGraphNode(claz.getLocalName()));
	}

	protected void createEdge(OntClass source, OntClass target) {
		SentimentGraphNode sNode = findNode(source.getLocalName());
		SentimentGraphNode tNode = findNode(target.getLocalName());
		if (sNode.addInfluence(tNode))
			edgesList.add(new SentimentGraphEdge(sNode, tNode));
	}

	public OntModel getModel() {
		return model;
	}

}
