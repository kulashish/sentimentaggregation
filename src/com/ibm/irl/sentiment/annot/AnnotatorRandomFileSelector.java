package com.ibm.irl.sentiment.annot;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class AnnotatorRandomFileSelector extends AnnotatorFileSelector {
	private Random rand;

	public AnnotatorRandomFileSelector(String in, String out) {
		super(in, out);
		rand = new Random();
	}

	@Override
	public AnnotatorInputFile select() throws IOException {
		File[] inputFiles = inFolder.listFiles();
		AnnotatorInputFile inFile = new AnnotatorInputFile();
		inFile.read(inputFiles[rand.nextInt(inputFiles.length)]);
		return inFile;
	}

}
