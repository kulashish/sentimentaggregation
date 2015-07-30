package com.ibm.irl.sentiment.model;

import java.io.File;

import com.ibm.irl.sentiment.ontology.OntologyException;

import edu.ucla.belief.BeliefNetwork;
import edu.ucla.belief.BeliefNetworkImpl;
import edu.ucla.belief.FiniteVariable;
import edu.ucla.belief.FiniteVariableImpl;
import edu.ucla.belief.io.xmlbif.RunWriteBIF;

public abstract class SentimentBayesianNetwork {

	protected BeliefNetwork model;

	public SentimentBayesianNetwork(BeliefNetwork bn) {
		model = bn;
	}

	protected SentimentBayesianNetwork() {
		model = new BeliefNetworkImpl();
	}

	public abstract void create() throws OntologyException;

	public BeliefNetwork getModel() {
		return model;
	}

	public void setModel(BeliefNetwork model) {
		this.model = model;
	}

	public FiniteVariable createVariable(String id, String[] labels) {
		FiniteVariable var = (FiniteVariable) model.forID(id);
		if (var == null) {
			var = new FiniteVariableImpl(id, labels);
			model.addVariable(var, true);
		}
		return var;
	}

	public static void main(String[] args) {
		SentimentBayesianNetwork network = new JedFilmBayesianNetwork();
		try {
			network.create();
		} catch (OntologyException e) {
			e.printStackTrace();
		}
		File xBif = new File(
				"C:\\Users\\IBM_ADMIN\\Documents\\sentiment_analysis\\samiam\\network_samples\\jed_film.xbif");
		RunWriteBIF writer = new RunWriteBIF(xBif, network.getModel());
		writer.run();
	}

}
