package com.ibm.irl.sentiment.ontology;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ibm.irl.sentiment.util.SentimentParameters;

public class JedFilmOntologyReader {
	private JedFilmOntology instance;
	private OntologyNode currentParent;
	private OntologyNode currentNode;
	private int depthIndex = 0;

	private static final String UL_PATTERN_OPEN = "<ul>";
	private static final String LI_PATTERN_OPEN = "<li>";
	private static final String UL_PATTERN_CLOSE = "</ul>";
	private static final String LI_PATTERN_CLOSE = "(</li>)*";
	private static final String TEXT_PATTERN_STR = LI_PATTERN_OPEN
			+ "([a-zA-Z_]+)" + LI_PATTERN_CLOSE;
	private static final Pattern TEXT_PATTERN = Pattern
			.compile(TEXT_PATTERN_STR);

	public JedFilmOntologyReader(JedFilmOntology instance) {
		this.instance = instance;
		currentParent = instance.getRoot();
		// System.out.println("Current parent: " + currentParent);
		currentNode = instance.getRoot();
		depthIndex = 0;
		instance.setHeight(depthIndex + 1);
	}

	public void load() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(
				SentimentParameters.JED_FILM_ONTOLOGY));
		String line = null;
		while ((line = reader.readLine()) != null)
			process(line);
		reader.close();
	}

	private void process(String line) {
		if (line.contains(UL_PATTERN_OPEN))
			addChildNodes();
		if (line.contains(LI_PATTERN_OPEN))
			addChild(line);
		if (line.contains(UL_PATTERN_CLOSE)) {
			currentParent = currentParent.getParent();
			depthIndex--;
		}

	}

	private void addChild(String line) {
		Matcher matcher = TEXT_PATTERN.matcher(line);
		if (matcher.find()) {
			currentNode = new OntologyNode(matcher.group(1).replace('_', ' '),
					depthIndex);
			// System.out.println("Adding child :" + currentNode);
			currentParent.addChild(currentNode);
			instance.getNodesList().add(currentNode);
		}
	}

	private void addChildNodes() {
		currentParent = currentNode;
		// System.out.println("Parent = " + currentParent);
		currentParent.setChildren(new ArrayList<OntologyNode>());
		depthIndex++;
		if (depthIndex > instance.getHeight())
			instance.setHeight(depthIndex + 1);
	}
}
