package com.ibm.irl.sentiment.model;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.ibm.irl.sentiment.annot.AnnotatorInputFile;
import com.ibm.irl.sentiment.annot.AnnotatorOutput;
import com.ibm.irl.sentiment.annot.AnnotatorOutputFile;
import com.ibm.irl.sentiment.ontology.SentimentGraphNode;
import com.ibm.irl.sentiment.restaurant.RestaurantDataAccess;
import com.ibm.irl.sentiment.restaurant.RestaurantHuginCaseRecord;
import com.ibm.irl.sentiment.restaurant.RestaurantHuginCaseRecords;
import com.ibm.irl.sentiment.util.DataAccessException;

public class AnnotationHuginCaseFileGenerator extends HuginCaseFileGenerator {

	private File annotIn;
	private File annotOut;
	private RestaurantHuginCaseRecords caseRecords;

	public AnnotationHuginCaseFileGenerator(String inPath, String outPath,
			String casePath) throws IOException {
		super(casePath);
		annotIn = new File(inPath);
		annotOut = new File(outPath);
		caseRecords = new RestaurantHuginCaseRecords();
	}

	public void generateHugin() throws IOException, DataAccessException {
		File[] annotFiles = annotOut.listFiles();
		Arrays.sort(annotFiles);
		AnnotatorOutputFile outFile = null;
		AnnotatorInputFile inFile = null;
		System.out.println("Processing annotation files...");
		for (File annotFile : annotFiles) {
			outFile = AnnotatorOutputFile.load(annotFile);
			if (outFile != null) {
				inFile = AnnotatorInputFile.load(annotIn, outFile.getFileName()
						.substring(0, outFile.getFileName().indexOf('.'))
						+ ".input");
				addRecord(inFile.getDocName(), outFile.getAnnotations());
			}
		}
		System.out.println("Completed processing annotation files");
		boolean first = true;
		System.out.println("Writing the case file ...");
		for (RestaurantHuginCaseRecord r : caseRecords.getRecords()) {
			if (first) {
				writeHeader(r);
				first = false;
			}
			writeCaseRecord(r);
		}
		writer.close();
		System.out.println("Done!!");
	}

	private void writeHeader(RestaurantHuginCaseRecord r) throws IOException {
		StringBuilder caseHeaderBuilder = new StringBuilder();
		for (SentimentGraphNode node : r.getSentiGraph().getNodesList())
			caseHeaderBuilder.append(',').append(node.getName());
		writer.append(caseHeaderBuilder.toString().substring(1));
		writer.newLine();
		writer.flush();
	}

	private void writeCaseRecord(RestaurantHuginCaseRecord record)
			throws IOException {
		StringBuilder caseLineBuilder = new StringBuilder();
		for (SentimentGraphNode node : record.getSentiGraph().getNodesList())
			caseLineBuilder.append(',').append(
					SentimentLabels.getLabel(node.getSentiment()));
		writer.append(caseLineBuilder.toString().substring(1));
		writer.newLine();
		writer.flush();
	}

	private void addRecord(String reviewId, List<AnnotatorOutput> annotations)
			throws DataAccessException {
		String restId = new RestaurantDataAccess().getRestaurantId(reviewId);
		RestaurantHuginCaseRecord record = caseRecords.getRecord(restId);
		if (record == null) {
			record = new RestaurantHuginCaseRecord(restId);
			caseRecords.addRecord(record);
		}
		record.recordAnnotations(annotations);
	}

	public static void main(String args[]) {
		final String IN = "C:\\Users\\IBM_ADMIN\\Documents\\sentiment_analysis\\datasets\\hugin_test\\rest_in";
		final String OUT = "C:\\Users\\IBM_ADMIN\\Documents\\sentiment_analysis\\datasets\\hugin_test\\rest_out";
		final String CASE = "C:\\Users\\IBM_ADMIN\\Documents\\sentiment_analysis\\datasets\\hugin_test\\hugin_rest.dat";
		AnnotationHuginCaseFileGenerator generator = null;
		try {
			generator = new AnnotationHuginCaseFileGenerator(IN, OUT, CASE);
			generator.generateHugin();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

}
