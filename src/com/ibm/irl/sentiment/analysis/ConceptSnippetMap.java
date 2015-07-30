package com.ibm.irl.sentiment.analysis;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConceptSnippetMap {

	private Map<Concept, List<Snippet>> map;

	public ConceptSnippetMap() {
		map = new HashMap<Concept, List<Snippet>>();
	}

	private void addConcept(Concept c) {
		if (!map.containsKey(c))
			map.put(c, new ArrayList<Snippet>());
	}

	public void addEntry(Concept c, Snippet s) {
		addConcept(c);
		map.get(c).add(s);
	}

	public void print() {
		print(System.out);
	}

	public void print(PrintStream pStream) {
		Iterator<Entry<Concept, List<Snippet>>> iter = map.entrySet()
				.iterator();
		Entry<Concept, List<Snippet>> entry = null;
		while (iter.hasNext()) {
			entry = iter.next();
			pStream.println(entry.getKey());
			for (Snippet s : entry.getValue())
				pStream.println(s);
		}
	}

	public String printJson() throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(map);
	}
}
