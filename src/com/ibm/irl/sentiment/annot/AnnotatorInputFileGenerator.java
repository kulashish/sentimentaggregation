package com.ibm.irl.sentiment.annot;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import com.ibm.irl.sentiment.analysis.SentenceProcessor;
import com.ibm.irl.sentiment.analysis.TextProcessorException;
import com.ibm.irl.sentiment.util.SentimentUtil;

import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class AnnotatorInputFileGenerator {

	private File datasetFolder;
	protected File annotatorInputFolder;
	private StanfordCoreNLP pipeline;

	public AnnotatorInputFileGenerator() {
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse");
		pipeline = new StanfordCoreNLP(props);
	}

	public AnnotatorInputFileGenerator(String dataPath, String annotPath) {
		this();
		datasetFolder = new File(dataPath);
		annotatorInputFolder = new File(annotPath);
	}

	public void generateAnnotatorInputFiles() throws IOException,
			TextProcessorException {
		String fileName = null;
		for (File dataFile : datasetFolder.listFiles()) {
			fileName = dataFile.getName();
			generateAnnotatorInputFiles(
					fileName.substring(0, fileName.indexOf('.')),
					SentimentUtil.readFile(dataFile));
		}
	}

	protected void generateAnnotatorInputFiles(String name, String data)
			throws IOException, TextProcessorException {
		Annotation document = new Annotation(data);
		pipeline.annotate(document);
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		SentenceProcessor sentenceProc = null;
		List<String> domainTerms;
		AnnotatorInputFile annotFile = null;
		int index = 0;
		for (CoreMap sentence : sentences) {
			sentenceProc = new SentenceProcessor(sentence);
			domainTerms = sentenceProc.getDomainTerms();
			annotFile = new AnnotatorInputFile(name, sentences, index++,
					domainTerms);
			annotFile.write(annotatorInputFolder);
		}
	}

	public static void main(String[] args) {
		String data = "C:\\Users\\IBM_ADMIN\\Documents\\sentiment_analysis\\datasets\\movie";
		String annot = "C:\\Users\\IBM_ADMIN\\Documents\\sentiment_analysis\\datasets\\annot_input";
		AnnotatorInputFileGenerator gen = new AnnotatorInputFileGenerator(data,
				annot);
		try {
			gen.generateAnnotatorInputFiles();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TextProcessorException e) {
			e.printStackTrace();
		}
	}

}
