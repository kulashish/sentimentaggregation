package com.ibm.irl.sentiment.model;

import edu.ucla.belief.BeliefNetwork;
import edu.ucla.belief.EvidenceController;
import edu.ucla.belief.FiniteVariable;
import edu.ucla.belief.InferenceEngine;
import edu.ucla.belief.StateNotFoundException;
import edu.ucla.belief.Table;
import edu.ucla.belief.inference.HuginEngine;

public class SentimentBNPredictor {
	private BeliefNetwork network;
	private EvidenceController controller;
	// private SentimentBNLearner learner;
	private InferenceEngine predictor;

	public SentimentBNPredictor(SentimentBayesianNetwork bn,
			SentimentBNLearner learner) {
		network = bn.getModel();
		controller = network.getEvidenceController();
		// controller = new EvidenceController(network);
		predictor = new HuginEngine(network, learner.getDynamator());
	}

	public void observe(String var, String value) throws StateNotFoundException {
		FiniteVariable bnVar = (FiniteVariable) network.forID(var);
		controller.observe(bnVar, bnVar.instance(value));
	}

	public void conditionalProbability(String var) {
		FiniteVariable bnVar = (FiniteVariable) network.forID(var);
		Table tab = predictor.conditional(bnVar);
		System.out.println(tab.toString());
	}
}
