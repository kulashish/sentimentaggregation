package com.ibm.irl.sentiment.model;

public class SentimentLabels {

	public static final String[] labels = new String[] { "neg", "neu", "pos" };

	public static String getLabel(short id) {
		return id == -1 ? "neg" : id == 1 ? "pos" : "neu";
	}

	public static short getId(String label) {
		short index = 0;
		for (short i = 0; i < labels.length; i++)
			if (labels[i].equalsIgnoreCase(label)) {
				index = i;
				break;
			}
		return --index;
	}
}
