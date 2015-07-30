package com.ibm.irl.sentiment.ontology;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.irl.sentiment.util.DictionaryConceptSimMeasure;
import com.ibm.irl.sentiment.util.SentimentParameters;

import edu.stanford.nlp.ling.CoreLabel;

public class Ontology extends DomainStructure {

	private static final Logger LOGGER = Logger.getLogger(Ontology.class
			.getName());

	protected OntologyNode root;
	protected int height;
	protected List<OntologyNode> nodesList;
	static {
		LOGGER.setLevel(Level.FINE);
	}

	protected Ontology() {
		nodesList = new ArrayList<OntologyNode>();
	}

	public static Ontology getOntology() throws OntologyException {
		Ontology onto = null;
		if (SentimentParameters.DOMAIN == "film")
			onto = JedFilmOntology.getInstance();
		return onto;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public List<OntologyNode> getNodesList() {
		return nodesList;
	}

	public OntologyNode getRoot() {
		return root;
	}

	public void setRoot(OntologyNode root) {
		this.root = root;
	}

	public String printJson() throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(root);
	}

	public OntologyNode lookup(String concept) {
		// LOGGER.log(Level.INFO, "Looking up " + concept);
		OntologyNode matchingNode = null;
		double maxSim = 0.0d;
		double sim = 0.0d;
		for (OntologyNode node : nodesList) {
			sim = DictionaryConceptSimMeasure.similarity(concept,
					node.getName());
			// LOGGER.log(Level.INFO, concept + ":" + node.getName() + ":" +
			// sim);
			if (sim > maxSim) {
				maxSim = sim;
				matchingNode = node;
			}
		}
		matchingNode = maxSim > SentimentParameters.CONCEPT_MATCH_THRESHOLD ? matchingNode
				: null;
		// LOGGER.log(Level.INFO, "Returning node : "
		// + (matchingNode != null ? matchingNode.getName() : "NULL"));
		return matchingNode;
	}

	public OntologyNode lookup(CoreLabel concept) {
		return lookup(concept.lemma());
	}

	protected void printChildren(OntologyNode node) {
		if (node != null)
			for (OntologyNode child : node.getChildren())
				System.out.println(child.getName());
	}

	protected void printChildren(String name) {
		OntologyNode node = null;
		for (OntologyNode n : nodesList)
			if (n.getName().equalsIgnoreCase(name)) {
				node = n;
				break;
			}
		printChildren(node);
	}
}
