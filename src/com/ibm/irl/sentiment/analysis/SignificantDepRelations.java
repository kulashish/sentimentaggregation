package com.ibm.irl.sentiment.analysis;

import java.util.Arrays;
import java.util.List;

public class SignificantDepRelations {

	private static final String[] RELATIONS = { "neg", "appos", "auxpass",
			"vmod", "advcl", "ccomp", "rcmod", "mark", "xcomp", "poss", "cop",
			"conj", "cc", "aux", "dep", "nn", "dobj", "advmod", "amod",
			"nsubj", "pobj" };

	public static List<String> getRelationsList() {
		return Arrays.asList(RELATIONS);
	}
}
