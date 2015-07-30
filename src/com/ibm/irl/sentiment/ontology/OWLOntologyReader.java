package com.ibm.irl.sentiment.ontology;

import java.io.File;
import java.io.InputStream;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;

public class OWLOntologyReader extends OntologyReader {

	public OWLOntologyReader(String path) {
		super(path);
	}

	@Override
	public OWLOntology load() {
		model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
		InputStream stream = FileManager.get().open(ontPath);
		model.read(stream, null);
		return new OWLOntology(model);
	}

	public OWLOntology loadOwlAPI() throws OWLOntologyCreationException {
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		return new OWLOntology(
				manager.loadOntologyFromOntologyDocument(new File(ontPath)));
	}

	public static void main(String[] args) {
		OWLOntology owlOnto = null;
		owlOnto = new OWLOntologyReader(
				"C:\\Users\\IBM_ADMIN\\workspace\\SentimentAggregation\\ontology\\cinema_trim.owl")
				.load();
		for (SentimentGraphNode node : owlOnto.getNodesList())
			System.out.println(node.getName());
		for (SentimentGraphEdge edge : owlOnto.getEdgesList())
			System.out.println(edge.getSource().getName() + "-->"
					+ edge.getTarget().getName());
		// OWLGraphConverter graphConverter = new OWLGraphConverter(model,
		// true);
		// Graph graph = graphConverter.getGraph();
		// Table nodesTable = graph.getNodeTable();
		// for (int i = 0; i < nodesTable.getColumnCount(); i++)
		// System.out.println(nodesTable.getColumnName(i));
		// Table edgesTable = graph.getEdgeTable();
		// for (int i = 0; i < edgesTable.getColumnCount(); i++)
		// System.out.println(edgesTable.getColumnName(i));
		// for (int i = 0; i < edgesTable.getRowCount(); i++) {
		// System.out.println(nodesTable.getString(edgesTable.getInt(i, 0), 1)
		// + "-->" + nodesTable.getString(edgesTable.getInt(i, 1), 1)
		// + "-->" + edgesTable.getString(i, 2));
		// }
		// while(nodes.hasNext())
		// System.out.println(nodes.next().);

	}
}
