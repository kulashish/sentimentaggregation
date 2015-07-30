package com.ibm.irl.sentiment.ontology;

import com.ibm.irl.sentiment.util.SentimentParameters;

import edu.stanford.nlp.ling.CoreLabel;

public abstract class DomainStructure {

	public abstract DomainNode lookup(CoreLabel token);

	public static DomainStructure getStructure() {
		DomainStructure structure = null;
		if (SentimentParameters.DOMAIN.equalsIgnoreCase("film"))
			structure = new OWLOntologyReader(
					SentimentParameters.JED_FILM_ONTOLOGY_OWL).load();
		else if (SentimentParameters.DOMAIN.equalsIgnoreCase("restaurant"))
			structure = new OWLOntologyReader(
					SentimentParameters.RESTAURANT_ONTOLOGY_OWL).load();
		return structure;
	}

}
