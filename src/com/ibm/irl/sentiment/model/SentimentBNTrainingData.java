package com.ibm.irl.sentiment.model;

import java.io.File;
import java.io.IOException;

import edu.ucla.belief.learn.LearningData;

public class SentimentBNTrainingData {

	private LearningData data;
	private SentimentBayesianNetwork network;

	public SentimentBNTrainingData(File trainingFile,
			SentimentBayesianNetwork bn) throws IOException {
		network = bn;
		data = new LearningData();
		data.readData(trainingFile, bn.getModel());
//		System.out.println("Read data size : " + data.size());
//		Iterator<Variable> iter = data.keySet().iterator();
//		while (iter.hasNext()) {
//			Variable var = iter.next();
//			System.out.println(var.getID() + " :: " + data.get(var));
//		}
//		System.out.println("Weight:" + data.getCurrentWeight());
//		data.writeData(new File(
//				"C:\\Users\\IBM_ADMIN\\Documents\\sentiment_analysis\\code\\tempdata.dat"));
	}

	public LearningData getData() {
		return data;
	}

	public SentimentBayesianNetwork getNetwork() {
		return network;
	}

}
