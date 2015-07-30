package com.ibm.irl.sentiment.annot;

import java.io.File;
import java.io.IOException;

public abstract class AnnotatorFileSelector {

	protected File inFolder;
	protected File outFolder;
	
	public AnnotatorFileSelector(String in, String out){
		inFolder = new File(in);
		outFolder = new File(out);
	}

	public abstract AnnotatorInputFile select() throws IOException;
}
