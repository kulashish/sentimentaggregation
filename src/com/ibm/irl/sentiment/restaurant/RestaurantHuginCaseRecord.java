package com.ibm.irl.sentiment.restaurant;

import java.util.ArrayList;
import java.util.List;

import com.ibm.irl.sentiment.analysis.Concept;
import com.ibm.irl.sentiment.analysis.ConceptSnippet;
import com.ibm.irl.sentiment.analysis.Snippet;
import com.ibm.irl.sentiment.annot.AnnotatorOutput;
import com.ibm.irl.sentiment.model.SentimentLabels;
import com.ibm.irl.sentiment.ontology.DomainStructure;
import com.ibm.irl.sentiment.ontology.SentimentGraph;
import com.ibm.irl.sentiment.ontology.SentimentGraphNode;

public class RestaurantHuginCaseRecord {

	private String restaurantID;
	private SentimentGraph sentiGraph;

	public RestaurantHuginCaseRecord(String id) {
		restaurantID = id;
		sentiGraph = (SentimentGraph) DomainStructure.getStructure();
	}

	public String getRestaurantID() {
		return restaurantID;
	}

	public SentimentGraph getSentiGraph() {
		return sentiGraph;
	}

	public void recordAnnotations(List<AnnotatorOutput> annotations) {
		SentimentGraphNode node = null;
		// To avoid duplicate entries for the same aspect
		List<AnnotatorOutput> browsedAnnotations = new ArrayList<AnnotatorOutput>();
		for (AnnotatorOutput annotation : annotations) {
			if (browsedAnnotations.contains(annotation))
				continue;
			browsedAnnotations.add(annotation);
			node = sentiGraph.lookup(annotation.getAspect());
			if (node != null) {
				Concept concept = new Concept(annotation.getAspect());
				Snippet snippet = new Snippet();
				snippet.setSentiment(SentimentLabels.getId(annotation
						.getSentiment()));
				node.addConceptSnippet(new ConceptSnippet(concept, snippet));
			}
		}
	}
}
