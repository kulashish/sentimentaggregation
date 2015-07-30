package com.ibm.irl.sentiment.model;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.ibm.irl.sentiment.ontology.JedFilmOntology;
import com.ibm.irl.sentiment.ontology.Ontology;
import com.ibm.irl.sentiment.ontology.OntologyException;
import com.ibm.irl.sentiment.ontology.OntologyNode;

import edu.ucla.belief.FiniteVariableImpl;
import edu.ucla.belief.Variable;

public class JedFilmBayesianNetwork extends SentimentBayesianNetwork {
	private static final Logger LOGGER = Logger
			.getLogger(JedFilmBayesianNetwork.class.getName());

	public JedFilmBayesianNetwork() {
		super();
	}

	public void create() throws OntologyException {
		Ontology jedOnto = JedFilmOntology.getInstance();
		create(jedOnto.getRoot());
	}

	public void create(OntologyNode ontoNode) {
		LOGGER.log(Level.INFO, "Processing node " + ontoNode.getName());
		Variable childVar = model.forID(ontoNode.getName());
		if (childVar == null) {
			childVar = new FiniteVariableImpl(ontoNode.getName(),
					SentimentLabels.labels);
			model.addVariable(childVar, true);
		}
		Variable parentVar = null;
		if (ontoNode != null && ontoNode.getChildren() != null
				&& !ontoNode.getChildren().isEmpty())
			for (OntologyNode childNode : ontoNode.getChildren()) {
				parentVar = new FiniteVariableImpl(childNode.getName(),
						SentimentLabels.labels);
				model.addVariable(parentVar, true);
				model.addEdge(parentVar, childVar, true);
				create(childNode);
			}
	}
}
