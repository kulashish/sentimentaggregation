package com.ibm.irl.sentiment.model;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import com.ibm.irl.sentiment.ontology.OntologyException;

import edu.ucla.belief.Dynamator;
import edu.ucla.belief.FiniteVariable;
import edu.ucla.belief.FiniteVariableImpl;
import edu.ucla.belief.StateNotFoundException;
import edu.ucla.belief.inference.HuginEngineGenerator;
import edu.ucla.util.EnumProperty;
import edu.ucla.util.EnumValue;

public class AsiaBayesianNetwork extends SentimentBayesianNetwork {
	private static final Logger LOGGER = Logger
			.getLogger(AsiaBayesianNetwork.class.getName());

	@Override
	public void create() throws OntologyException {
		FiniteVariable asia = new FiniteVariableImpl("asia", new String[] {
				"no", "yes" });
		FiniteVariable smoke = new FiniteVariableImpl("smoke", new String[] {
				"no", "yes" });
		FiniteVariable tub = new FiniteVariableImpl("tub", new String[] { "no",
				"yes" });
		FiniteVariable lung = new FiniteVariableImpl("lung", new String[] {
				"no", "yes" });
		FiniteVariable either = new FiniteVariableImpl("either", new String[] {
				"no", "yes" });
		FiniteVariable bronc = new FiniteVariableImpl("bronc", new String[] {
				"no", "yes" });
		FiniteVariable xray = new FiniteVariableImpl("xray", new String[] {
				"no", "yes" });
		FiniteVariable dysp = new FiniteVariableImpl("dysp", new String[] {
				"no", "yes" });
		FiniteVariable[] nodes = new FiniteVariable[] { asia, smoke, tub, lung,
				either, bronc, xray, dysp };
		for (FiniteVariable var : nodes)
			model.addVariable(var, true);
		model.addEdge(asia, tub, true);
		model.addEdge(smoke, lung, true);
		model.addEdge(smoke, bronc, true);
		model.addEdge(tub, either, true);
		model.addEdge(lung, either, true);
		model.addEdge(either, xray, true);
		model.addEdge(either, dysp, true);
		model.addEdge(bronc, dysp, true);
		Map<EnumProperty, EnumValue> props = asia.getEnumProperties();
		for (Entry<EnumProperty, EnumValue> entry : props.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
	}

	public static void main(String[] args) {
		SentimentBayesianNetwork network = new AsiaBayesianNetwork();
		File asiaData = new File(
				"C:\\Users\\IBM_ADMIN\\workspace\\SentimentAggregation\\data\\asia.dat");
		SentimentBNTrainingData asiaTrainData = null;
		SentimentBNLearner asiaLearner = null;
		Dynamator dyn = new HuginEngineGenerator();
		SentimentBNPredictor predictor = null;
		try {
			System.out.println("Creating BN...");
			network.create();
			System.out.println("BN created ...");
			asiaTrainData = new SentimentBNTrainingData(asiaData, network);
			asiaLearner = new SentimentBNLearner(asiaTrainData, dyn);
			System.out.println("Training...");
			asiaLearner.learn();
			System.out.println("Done!");
			predictor = new SentimentBNPredictor(network, asiaLearner);
			predictor.conditionalProbability("bronc");
			predictor.observe("asia", "yes");
			predictor.observe("smoke", "no");
			predictor.conditionalProbability("bronc");
		} catch (OntologyException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (StateNotFoundException e) {
			e.printStackTrace();
		}

	}

}
