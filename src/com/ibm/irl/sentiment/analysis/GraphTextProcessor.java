package com.ibm.irl.sentiment.analysis;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.ibm.irl.sentiment.ontology.SentimentGraph;

import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.util.CoreMap;

public class GraphTextProcessor extends TextProcessor {

	private SentimentGraph sentiGraph;

	public GraphTextProcessor(File file, SentimentGraph graph)
			throws IOException, TextProcessorException {
		super(file);
		sentiGraph = graph;
	}

	public void process() throws IOException, TextProcessorException {
		Annotation document = new Annotation(text);
		pipeline.annotate(document);
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		SentenceProcessor sentProcessor = null;
		for (CoreMap sentence : sentences) {
			sentProcessor = new GraphSentenceProcessor(sentence, sentiGraph);
			sentProcessor.process();
		}
	}

}
