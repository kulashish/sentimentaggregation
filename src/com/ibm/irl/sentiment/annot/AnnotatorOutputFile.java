package com.ibm.irl.sentiment.annot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ibm.irl.sentiment.util.SentimentParameters;

public class AnnotatorOutputFile implements Serializable {

	private List<AnnotatorOutput> annotations;
	private String fileName;
	private String user;

	public AnnotatorOutputFile(String name) {
		fileName = name + ".output";
	}

	public List<AnnotatorOutput> getAnnotations() {
		if (annotations == null)
			annotations = new ArrayList<AnnotatorOutput>();
		return annotations;
	}

	public String addAnnotation() {
		System.out.println("out - adding annotaion");
		getAnnotations().add(new AnnotatorOutput());
		System.out.println("Num annotations: " + getAnnotations().size());
		return "success";
	}

	// public void addAnnotation(AnnotatorOutput annot) {
	// getAnnotations().add(annot);
	// }

	public String getFileName() {
		return fileName;
	}

	public String getUser() {
		return user;
	}

	public void write() throws IOException {
		File out = new File(SentimentParameters.DATASET_ANNOT_OUT, fileName);
		BufferedWriter writer = new BufferedWriter(new FileWriter(out, true));
		for (AnnotatorOutput annotation : annotations) {
			writer.append(annotation.getAspect()).append(':')
					.append(annotation.getSentiment());
			writer.newLine();
		}
		writer.close();
	}

	public static AnnotatorOutputFile load(File annotOutFile)
			throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(annotOutFile));
		String annot = null;
		AnnotatorOutputFile file = new AnnotatorOutputFile(
				annotOutFile.getName());
		AnnotatorOutput annotation = null;
		boolean res = false;
		while ((annot = reader.readLine()) != null) {
			annotation = new AnnotatorOutput();
			res = annotation.parse(annot);
			if (!res) {
				file = null;
				break;
			} else
				file.addAnnotation(annotation);
		}
		reader.close();
		return file;
	}

	private void addAnnotation(AnnotatorOutput annotation) {
		getAnnotations().add(annotation);
	}

	public void removeAnnotation(AnnotatorOutput annotation) {
		annotations.remove(annotation);
	}

}
