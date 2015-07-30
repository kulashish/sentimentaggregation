package com.ibm.irl.sentiment.ontology;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class OntologyNode extends DomainNode {

	private int depth;
	@JsonIgnore
	private List<OntologyNode> children;
	@JsonIgnore
	private OntologyNode parent;

	public OntologyNode(String name) {
		this.name = name;
	}

	public OntologyNode(String name, int d) {
		this(name);
		depth = d;
	}

	public int getDepth() {
		return depth;
	}

	public void addChild(OntologyNode node) {
		if (children == null)
			children = new ArrayList<OntologyNode>();
		children.add(node);
		node.setParent(this);
	}

	public List<OntologyNode> getChildren() {
		return children;
	}

	public void setChildren(List<OntologyNode> children) {
		this.children = children;
	}

	public OntologyNode getParent() {
		return parent;
	}

	public void setParent(OntologyNode parent) {
		this.parent = parent;
	}

	@Override
	public String toString() {
		return "OntologyNode [name=" + name + "]";
	}

}
