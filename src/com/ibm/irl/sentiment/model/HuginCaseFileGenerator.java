package com.ibm.irl.sentiment.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import com.ibm.irl.sentiment.analysis.PhraseAnnotatedConceptOntology;
import com.ibm.irl.sentiment.analysis.PhraseAnnotatedConceptOntologyNode;
import com.ibm.irl.sentiment.analysis.TextProcessor;
import com.ibm.irl.sentiment.analysis.TextProcessorException;
import com.ibm.irl.sentiment.ontology.Ontology;
import com.ibm.irl.sentiment.ontology.OntologyException;
import com.ibm.irl.sentiment.ontology.OntologyNode;

public class HuginCaseFileGenerator {

	private File trainingDataPath;
	protected File huginCaseFile;
	protected BufferedWriter writer;
	private Ontology onto;

	public HuginCaseFileGenerator(String huginFile) throws IOException {
		huginCaseFile = new File(huginFile);
		writer = new BufferedWriter(new FileWriter(huginCaseFile));
	}

	public HuginCaseFileGenerator(String path, String huginFile)
			throws OntologyException, IOException {
		this(huginFile);
		trainingDataPath = new File(path);
		onto = Ontology.getOntology();
	}

	public void init() throws IOException, TextProcessorException {
		// writer = new BufferedWriter(new FileWriter(huginCaseFile));
		File[] files = trainingDataPath.listFiles(); // sub-folders with
														// name=label
		String rootLabel = null;
		for (File file : files) {
			rootLabel = file.getName();
			generateHugin(file, rootLabel);
		}
		writer.flush();
		writer.close();
	}

	private void generateHugin(File file, String rootLabel) throws IOException,
			TextProcessorException {
		TextProcessor processor = null;
		for (OntologyNode n : onto.getNodesList())
			System.out.print(n.getName() + ", ");
		System.out.println();
		for (File f : file.listFiles()) {
			processor = new TextProcessor(f);
			processor.process();
			writeHuginCase(processor.getPsot(), rootLabel);
		}

	}

	private void writeHuginCase(PhraseAnnotatedConceptOntology psot,
			String rootLabel) throws IOException {
		Ontology onto = psot.getOntology();
		for (OntologyNode n : onto.getNodesList())
			System.out.print(n.getName() + ", ");
		System.out.println();
		Map<OntologyNode, PhraseAnnotatedConceptOntologyNode> map = psot
				.getPsotMap();
		map.get(onto.getRoot()).setSentiment(SentimentLabels.getId(rootLabel));
		StringBuilder huginLineBuilder = new StringBuilder();
		for (OntologyNode node : onto.getNodesList())
			huginLineBuilder.append(',').append(
					SentimentLabels.getLabel(map.get(node).getSentiment()));
		writer.write(huginLineBuilder.substring(1));
		writer.newLine();
	}

	public static void main(String[] args) {
		try {
			HuginCaseFileGenerator hugin = new HuginCaseFileGenerator(args[0],
					args[1]);
			hugin.init();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TextProcessorException e) {
			e.printStackTrace();
		} catch (OntologyException e) {
			e.printStackTrace();
		}
	}

}
