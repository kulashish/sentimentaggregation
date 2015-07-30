package com.ibm.irl.sentiment.ontology;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;

public class JedFilmOntology extends Ontology {

	private static JedFilmOntology instance;

	private JedFilmOntology() {
		root = new OntologyNode("Cinema", 0);
		nodesList.add(root);
	}

	public static JedFilmOntology getInstance() throws OntologyException {
		if (instance == null) {
			instance = new JedFilmOntology();
			try {
				new JedFilmOntologyReader(instance).load();
			} catch (IOException e) {
				throw new OntologyException(e);
			}
		}
		return instance;
	}

	public static void main(String[] args) {
		try {
			// System.out.println("</ul>".contains("</ul>"));
			JedFilmOntology onto = JedFilmOntology.getInstance();
//			onto.printChildren(onto.getRoot());
			onto.printChildren("Film Industry");
			// System.out.println(onto.printJson());
		} catch (OntologyException e) {
			e.printStackTrace();
		}
		// catch (JsonProcessingException e) {
		// e.printStackTrace();
		// }
	}
}
