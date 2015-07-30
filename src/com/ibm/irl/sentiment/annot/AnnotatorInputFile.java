package com.ibm.irl.sentiment.annot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import com.ibm.irl.sentiment.util.SentimentUtil;

import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.util.CoreMap;

public class AnnotatorInputFile implements Serializable {
	private String docName;
	private String sentence;
	private String context;
	private List<String> aspects;
	private String fileName;

	public AnnotatorInputFile(String doc, String sentence, String context,
			List<String> aspects) {
		docName = doc;
		this.sentence = sentence;
		this.context = context;
		this.aspects = aspects;
	}

	public AnnotatorInputFile(String doc, List<CoreMap> sentences, int sIndex,
			List<String> terms) {
		docName = doc;
		aspects = terms;
		sentence = sentences.get(sIndex).get(TextAnnotation.class);
		fileName = docName + "-S-" + sIndex + ".input";
		StringBuilder contextBuilder = new StringBuilder();
		for (int index = sIndex - 1; index <= sIndex + 1 && index >= 0
				&& index < sentences.size(); index++)
			contextBuilder.append(sentences.get(index)
					.get(TextAnnotation.class));
		context = contextBuilder.toString();
	}

	public AnnotatorInputFile() {
	}

	public void read(String fileName) throws IOException {
		File f = new File(fileName);
		read(f);
	}

	public void read(File file) throws IOException {
		this.fileName = file.getName();
		BufferedReader reader = new BufferedReader(new FileReader(file));
		this.docName = reader.readLine();
		this.sentence = reader.readLine();
		this.context = reader.readLine();
		this.aspects = SentimentUtil.asList(reader.readLine());
		reader.close();
	}

	public void write(File annotatorInputFolder) throws IOException {
		File annotInFile = new File(annotatorInputFolder, fileName);
		// File.createTempFile(fileName, "input",
		// annotatorInputFolder);
		BufferedWriter writer = new BufferedWriter(new FileWriter(annotInFile));
		writer.append(docName);
		writer.newLine();
		writer.append(sentence);
		writer.newLine();
		writer.append(context);
		writer.newLine();
		writer.append(SentimentUtil.commaSeparated(aspects));
		writer.close();
	}

	public String getDocName() {
		return docName;
	}

	public String getSentence() {
		return sentence;
	}

	public String getContext() {
		return context;
	}

	public List<String> getAspects() {
		return aspects;
	}

	public String getFileName() {
		return fileName;
	}

	public String getOnlyName() {
		return fileName.substring(0, fileName.indexOf('.'));
	}

	public void setContext(String context) {
		this.context = context;
	}

	public static AnnotatorInputFile load(File annotIn, String fileName)
			throws IOException {
		File file = new File(annotIn, fileName);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String doc = reader.readLine();
		String sent = reader.readLine();
		String ctx = reader.readLine();
		List<String> aspects = SentimentUtil.asList(reader.readLine());
		reader.close();
		return new AnnotatorInputFile(doc, sent, ctx, aspects);
	}

}