package com.ibm.irl.sentiment.ontology;

import java.util.ArrayList;
import java.util.List;

import com.ibm.irl.sentiment.analysis.ConceptSnippet;
import com.ibm.irl.sentiment.util.DictionaryConceptSimMeasure;
import com.ibm.irl.sentiment.util.SentimentParameters;

import edu.stanford.nlp.ling.CoreLabel;

public class SentimentGraph extends DomainStructure {

	protected List<SentimentGraphNode> nodesList;
	protected List<SentimentGraphEdge> edgesList;

	protected SentimentGraph() {
		nodesList = new ArrayList<SentimentGraphNode>();
		edgesList = new ArrayList<SentimentGraphEdge>();
	}

	public List<SentimentGraphNode> getNodesList() {
		return nodesList;
	}

	public List<SentimentGraphEdge> getEdgesList() {
		return edgesList;
	}

	public SentimentGraphNode findNode(String name) {
		SentimentGraphNode resNode = null;
		for (SentimentGraphNode n : nodesList)
			if (name.equalsIgnoreCase(n.getName())) {
				resNode = n;
				break;
			}
		return resNode;
	}

	public SentimentGraphNode lookup(String concept) {
		// LOGGER.log(Level.INFO, "Looking up " + concept);
		SentimentGraphNode matchingNode = null;
		double maxSim = 0.0d;
		double sim = 0.0d;
		for (SentimentGraphNode node : nodesList) {
			sim = DictionaryConceptSimMeasure.similarity(concept,
					node.getName());
			if (sim > maxSim) {
				maxSim = sim;
				matchingNode = node;
			}
		}
		matchingNode = maxSim > SentimentParameters.CONCEPT_MATCH_THRESHOLD ? matchingNode
				: null;
		return matchingNode;
	}

	public SentimentGraphNode lookup(CoreLabel concept) {
		return lookup(concept.lemma());
	}

	public void addEntry(SentimentGraphNode node, ConceptSnippet cs) {
		node.addConceptSnippet(cs);
	}

}
