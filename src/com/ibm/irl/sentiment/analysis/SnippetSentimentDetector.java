package com.ibm.irl.sentiment.analysis;

import java.util.List;

import com.ibm.irl.sentiment.lexicon.BingLiuLexicon;
import com.ibm.irl.sentiment.lexicon.Lexicon;

import edu.stanford.nlp.ling.CoreLabel;

public class SnippetSentimentDetector {
	private static final int NUM_LEXICONS = 1;
	private static Lexicon[] lexicons = new Lexicon[] { BingLiuLexicon
			.getInstance() };

	public void sentiment(Snippet s) {
		int numPos = 0;
		int numNeg = 0;
		List<CoreLabel> tokenList = s.getTokens();
		int senti = lexiconBasedSentiment(tokenList);
		if (senti == 1)
			numPos++;
		else if (senti == -1)
			numNeg++;
		s.setSentiment(numPos > numNeg ? (short) 1
				: numNeg > numPos ? (short) -1 : 0);
	}

	/*
	 * Sentiment of a text based on the number of positive and negative words as
	 * per the lexicons
	 */
	private int lexiconBasedSentiment(List<CoreLabel> tokenList) {
		int numPos = 0, numNeg = 0;
		short senti = 0;
		for (CoreLabel w : tokenList) {
			senti = lexiconBasedSentiment(w);
			if (senti == 1)
				numPos++;
			else if (senti == -1)
				numNeg++;
		}
		return numPos > numNeg ? (short) 1 : numNeg > numPos ? (short) -1 : 0;
	}

	/*
	 * Sentiment of a word based on majority voting between lexicons
	 */
	private short lexiconBasedSentiment(CoreLabel w) {
		int numPos = 0, numNeg = 0;
		short s = 0;
		for (Lexicon lexicon : lexicons) {
			s = lexicon.sentiment(w);
			if (s == 1)
				numPos++;
			else if (s == -1)
				numNeg++;
		}
		return numPos > numNeg ? (short) 1 : numNeg > numPos ? (short) -1 : 0;
	}
}
