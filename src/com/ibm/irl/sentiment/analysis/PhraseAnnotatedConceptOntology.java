package com.ibm.irl.sentiment.analysis;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.irl.sentiment.ontology.Ontology;
import com.ibm.irl.sentiment.ontology.OntologyNode;

public class PhraseAnnotatedConceptOntology {

	private static ObjectMapper jsonObjectMapper = new ObjectMapper();
	@JsonIgnore
	private Ontology ontology;
	private Map<OntologyNode, PhraseAnnotatedConceptOntologyNode> psotMap;
	private int sentiment;
	@JsonIgnore
	boolean blnAggregated;
	@JsonIgnore
	private StringBuilder logBuilder;

	public PhraseAnnotatedConceptOntology() {
		psotMap = new HashMap<OntologyNode, PhraseAnnotatedConceptOntologyNode>();
		logBuilder = new StringBuilder();
	}

	public PhraseAnnotatedConceptOntology(Ontology ontology) {
		this();
		this.ontology = ontology;
		for (OntologyNode oNode : ontology.getNodesList())
			psotMap.put(oNode, new PhraseAnnotatedConceptOntologyNode());
	}

	public void addEntry(OntologyNode node, ConceptSnippet cs) {
		PhraseAnnotatedConceptOntologyNode psotNode = psotMap.get(node);
		if (psotNode == null) {
			psotNode = new PhraseAnnotatedConceptOntologyNode();
			psotMap.put(node, psotNode);
		}
		psotNode.addConceptSnippet(cs);
	}

	public String getLog() {
		return logBuilder.toString();
	}

	public String printJson() throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(psotMap);
	}

	public Ontology getOntology() {
		return ontology;
	}

	public Map<OntologyNode, PhraseAnnotatedConceptOntologyNode> getPsotMap() {
		return psotMap;
	}

	public int getSentiment() throws JsonProcessingException {
		if (!blnAggregated) {
			sentiment = computeSentiment(ontology.getRoot());
			blnAggregated = true;
		}
		return sentiment;
	}

	private int computeSentiment(OntologyNode oNode)
			throws JsonProcessingException {
		int aSentiment = 0;
		if (oNode != null) {
			PhraseAnnotatedConceptOntologyNode pNode = psotMap.get(oNode);
			aSentiment = pNode.getSentiment()
					* (ontology.getHeight() - oNode.getDepth());
			if (oNode.getChildren() != null)
				for (OntologyNode child : oNode.getChildren())
					aSentiment += computeSentiment(child);
			pNode.setAggregateSentiment(aSentiment);
			if (!pNode.getConceptSnippetList().isEmpty() || aSentiment != 0)
				logBuilder.append(jsonObjectMapper.writeValueAsString(oNode))
						.append(jsonObjectMapper.writeValueAsString(pNode));
		}
		return aSentiment;
	}
}
