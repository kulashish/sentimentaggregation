package com.ibm.irl.sentiment.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.ibm.irl.sentiment.analysis.TextProcessor;
import com.ibm.irl.sentiment.ontology.OntologyException;

public class PersonExtractor {
	private static final String DATA = "C:\\Users\\IBM_ADMIN\\Documents\\sentiment_analysis\\datasets\\movie";
	private static final String OUT = "C:\\Users\\IBM_ADMIN\\Documents\\sentiment_analysis\\actors_extracted.list";

	public static void main(String[] args) {
		String folderPath = args[0];
		String outFile = args[1];
		File corpus = new File(folderPath);
		BufferedWriter writer = null;

		try {
			writer = new BufferedWriter(new FileWriter(outFile));

			TextProcessor processor = null;
			List<String> persons = null;
			for (File f : corpus.listFiles()) {
				try {
					processor = new TextProcessor(f);
					persons = processor.extractPersonNames();
					write(writer, persons);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (OntologyException e) {
					e.printStackTrace();
				}
			}
			writer.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private static void write(BufferedWriter writer, List<String> persons)
			throws IOException {
		for (String preson : persons) {
			writer.append(preson);
			writer.newLine();
		}
		writer.flush();
	}
}
