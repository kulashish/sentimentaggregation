package com.ibm.irl.sentiment.analysis;

import com.fasterxml.jackson.annotation.JsonIgnore;

import edu.stanford.nlp.ling.CoreLabel;

public class Concept {

	private String name;
	@JsonIgnore
	private CoreLabel token;

	public Concept(CoreLabel token) {
		this(token.word());
		this.token = token;
	}

	public Concept(String key) {
		name = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Concept [name=" + name + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Concept other = (Concept) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
