package com.ibm.irl.sentiment.lexicon;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.ibm.irl.sentiment.util.SentimentParameters;

import edu.stanford.nlp.ling.CoreLabel;

public class BingLiuLexicon implements Lexicon {
	private static final String POSITIVE_PATH = SentimentParameters.BING_POSITIVE_PATH;
	private static final String NEGATIVE_PATH = SentimentParameters.BING_NEGATIVE_PATH;

	private static BingLiuLexicon lexicon;
	private Map<Character, List<String>> posDictionary;
	private Map<Character, List<String>> negDictionary;

	private static final Logger LOGGER = Logger.getLogger(BingLiuLexicon.class
			.getName());

	private BingLiuLexicon() {
		posDictionary = new HashMap<Character, List<String>>();
		negDictionary = new HashMap<Character, List<String>>();
		load();
	}

	public static BingLiuLexicon getInstance() {
		if (lexicon == null)
			lexicon = new BingLiuLexicon();
		return lexicon;
	}

	public short sentiment(CoreLabel token) {
		String word = token.word();
		String lemma = token.lemma();
		// System.out.println("word: " + word + ":: lemma: " + lemma);
		List<String> pList = posDictionary.get(word.charAt(0));
		List<String> nList = negDictionary.get(word.charAt(0));
		if (pList != null && (pList.contains(word) || pList.contains(lemma)))
			return +1;
		if (nList != null && (nList.contains(word) || nList.contains(lemma)))
			return -1;
		return 0;
	}

	private void load(String file, Map<Character, List<String>> dict)
			throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String word = null;
		List<String> wordList = null;
		while (null != (word = reader.readLine())) {
			wordList = dict.containsKey(word.charAt(0)) ? dict.get(word
					.charAt(0)) : new ArrayList<String>();
			wordList.add(word);
			dict.put(word.charAt(0), wordList);
		}
		reader.close();
	}

	private void load() {
		try {
			load(POSITIVE_PATH, posDictionary);
			load(NEGATIVE_PATH, negDictionary);
		} catch (IOException e) {
			LOGGER.severe(e.getMessage());
		}
	}
}
