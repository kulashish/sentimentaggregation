package com.ibm.irl.sentiment.model;

import edu.ucla.belief.BeliefNetwork;
import edu.ucla.belief.Dynamator;
import edu.ucla.belief.learn.Learning;

public class SentimentBNLearner {
	private SentimentBNTrainingData trainData;
	private Dynamator dynamator;

	public SentimentBNLearner(SentimentBNTrainingData data, Dynamator dyn) {
		trainData = data;
		dynamator = dyn;
	}

	public void learn() {
		BeliefNetwork net = trainData.getNetwork().getModel();
		BeliefNetwork learnedModel = Learning.learnParamsEM(net,
				trainData.getData(), 0.05, 50, dynamator, true);
		// Collection<TableShell> shells = learnedModel.tables();
		// for (TableShell shell : shells) {
		// System.out.println("Printing CPT for "
		// + shell.getVariable().getID());
		// System.out.println(shell.getCPT());
		// }
		// FiniteVariable var = (FiniteVariable) learnedModel.forID("asia");
		// InferenceEngine predictor = new HuginEngine(learnedModel, dynamator);
		// System.out.println(predictor.joint(var));
		trainData.getNetwork().setModel(learnedModel);
	}

	public Dynamator getDynamator() {
		return dynamator;
	}

}
