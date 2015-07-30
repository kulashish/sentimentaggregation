package com.ibm.irl.sentiment.model;

import com.ibm.irl.sentiment.ontology.OntologyException;
import com.ibm.irl.sentiment.ontology.SentimentGraph;
import com.ibm.irl.sentiment.ontology.SentimentGraphEdge;
import com.ibm.irl.sentiment.ontology.SentimentGraphNode;

import edu.ucla.belief.FiniteVariable;

public class DefaultBayesianNetwork extends SentimentBayesianNetwork {
	private SentimentGraph sGraph;

	@Override
	public void create() throws OntologyException {
		for (SentimentGraphEdge edge : sGraph.getEdgesList())
			addEdge(edge);
	}

	private void addEdge(SentimentGraphEdge edge) {
		SentimentGraphNode source = edge.getSource();
		SentimentGraphNode target = edge.getTarget();
		FiniteVariable mSource = createVariable(source.getName(),
				SentimentLabels.labels);
		FiniteVariable mTarget = createVariable(target.getName(),
				SentimentLabels.labels);
		model.addEdge(mSource, mTarget, true);
	}

}
