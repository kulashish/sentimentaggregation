package com.ibm.irl.sentiment.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class SentimentUtil {
	private static final char[] CHAR_FILTER = new char[] { '?', '*', '+' };

	public static String readFile(File file) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(file.getPath()));
		return new String(encoded);
	}

	public static String preprocess(String w) {
		StringBuilder strbuilder = new StringBuilder(w);
		int index = -1;
		for (char c : CHAR_FILTER) {
			index = w.indexOf(c);
			if (index != -1)
				strbuilder.deleteCharAt(index);
		}
		return strbuilder.toString();
	}

	public static String commaSeparated(List<String> list) {
		StringBuilder csBuilder = new StringBuilder();
		if (list != null && !list.isEmpty()) {
			for (String str : list)
				csBuilder.append(str).append(',');
			csBuilder.deleteCharAt(csBuilder.length() - 1);
		}
		return csBuilder.toString();
	}

	public static List<String> asList(String csList) {
		List<String> tokensList = new ArrayList<String>();
		if (csList != null) {
			StringTokenizer tokenizer = new StringTokenizer(csList, ",");
			while (tokenizer.hasMoreTokens())
				tokensList.add(tokenizer.nextToken());
		}
		return tokensList;
	}
}
