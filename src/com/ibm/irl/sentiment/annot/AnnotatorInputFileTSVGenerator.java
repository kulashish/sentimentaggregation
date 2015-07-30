package com.ibm.irl.sentiment.annot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.ibm.irl.sentiment.analysis.TextProcessorException;
import com.ibm.irl.sentiment.util.SentimentParameters;

public class AnnotatorInputFileTSVGenerator extends AnnotatorInputFileGenerator {

	private File tsvFile;

	public AnnotatorInputFileTSVGenerator() {
		super();
	}

	public AnnotatorInputFileTSVGenerator(String tsvPath, String outPath) {
		tsvFile = new File(tsvPath);
		annotatorInputFolder = new File(outPath);
	}

	public void generateAnnotatorInputFiles() throws IOException,
			TextProcessorException {
		BufferedReader reader = new BufferedReader(new FileReader(tsvFile));
		String line = null;
		String[] columns = null;
		while ((line = reader.readLine()) != null) {
			if (line.trim().length() == 0)
				continue;

			columns = line.split("\t");
			generateAnnotatorInputFiles(columns[2], columns[columns.length - 1]);
		}
		reader.close();
	}

	public static void main(String[] args) {
		AnnotatorInputFileGenerator fileGen = new AnnotatorInputFileTSVGenerator(
				SentimentParameters.DATASET_RESTAURANT,
				SentimentParameters.DATASET_RESTAURANT_ANNOT_IN);
		try {
			fileGen.generateAnnotatorInputFiles();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TextProcessorException e) {
			e.printStackTrace();
		}
	}

}
