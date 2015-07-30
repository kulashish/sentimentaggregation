package com.ibm.irl.sentiment.util;

import edu.cmu.lti.lexical_db.ILexicalDatabase;
import edu.cmu.lti.lexical_db.NictWordNet;
import edu.cmu.lti.ws4j.RelatednessCalculator;
import edu.cmu.lti.ws4j.impl.WuPalmer;
import edu.cmu.lti.ws4j.util.WS4JConfiguration;

public class DictionaryConceptSimMeasure {

	private static ILexicalDatabase db = new NictWordNet();
	private static RelatednessCalculator simCalc = new WuPalmer(db);

	public static double similarity(String w1, String w2) {
		WS4JConfiguration.getInstance().setMFS(true);
		if (w1 != null)
			w1 = w1.toLowerCase().replaceAll("-", "").replaceAll(" ", "");
		if (w2 != null)
			w2 = w2.toLowerCase().replaceAll("-", "").replaceAll(" ", "");
		// double res = 0.0d;
		// double res1 = simCalc.calcRelatednessOfWords(w1, w2);
		// res = res1;
		// double res2 = simCalc.calcRelatednessOfWords(w1.replace('-', ' '),
		// w2.replace('-', ' '));
		// if (res2 > res)
		// res = res2;
		// double res3 = simCalc.calcRelatednessOfWords(w1.replaceAll("-", ""),
		// w2.replaceAll("-", ""));
		// if (res3 > res)
		// res = res3;
		return simCalc.calcRelatednessOfWords(w1, w2);
	}

	public static void main(String args[]) {
		System.out.println(DictionaryConceptSimMeasure.similarity(
				"film making", "Filmmaking"));
	}

}
