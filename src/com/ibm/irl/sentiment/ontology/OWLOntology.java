package com.ibm.irl.sentiment.ontology;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAxiom;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

public class OWLOntology extends OntologyGraph {

	public OWLOntology(OntModel model) {
		super(model);
	}

	public OWLOntology(org.semanticweb.owlapi.model.OWLOntology owlOnto) {
		super();
		Set<OWLClass> classes = owlOnto.getClassesInSignature();
		for (OWLClass cl : classes) {
			System.out.println(className(cl.toString()));
			for (OWLClassAxiom expr : owlOnto.getAxioms(cl))
				System.out.print(className(expr.toString()) + ", ");
			System.out.println();
		}
	}

	private String className(String stringID) {
		int last = stringID.indexOf('>');
		if (last == -1)
			last = stringID.length();
		return stringID.substring(stringID.indexOf('#') + 1, last);
	}

	@Override
	protected void createEdges() {
		Iterator<OntClass> classIter = model.listNamedClasses();
		while (classIter.hasNext())
			createEdges(classIter.next());
	}

	private void createEdges(OntClass claz) {
		Iterator<OntClass> superClasses = claz.listSuperClasses();
		List<OntClass> classList = null;
		while (superClasses.hasNext()) {
			classList = new ArrayList<OntClass>();
			classNames(superClasses.next(), classList);
			for (OntClass superClaz : classList)
				createEdge(claz, superClaz);
		}
	}

	private void classNames(Resource resource, List<OntClass> classList) {
		OntClass claz = null;
		// System.out.println("Checking resource " + resource.getLocalName()
		// + "--" + resource);
		if (resource instanceof OntClass)
			claz = (OntClass) resource;
		if (claz != null && !claz.isAnon())
			classList.add(claz);

		if (claz != null && claz.isRestriction()
				&& claz.asRestriction().isAllValuesFromRestriction())
			classNames((Resource) claz.asRestriction()
					.asAllValuesFromRestriction().getAllValuesFrom(), classList);
		if (claz != null && claz.isUnionClass()) {
			ExtendedIterator<? extends OntClass> nodes = claz.asUnionClass()
					.listOperands();
			while (nodes.hasNext())
				classNames((Resource) nodes.next(), classList);
		}
	}
}
