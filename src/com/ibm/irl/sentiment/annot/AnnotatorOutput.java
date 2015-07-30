package com.ibm.irl.sentiment.annot;

public class AnnotatorOutput {

	private String aspect;
	private String sentiment;

	public boolean parse(String annot) {
		boolean result = true;
		String[] parts = annot.split(":");
		if (parts == null || parts.length < 2 || parts[0].equals("")
				|| parts[1].equals(""))
			result = false;
		else {
			aspect = parts[0];
			sentiment = parts[1];
		}
		return result;
	}

	public String getAspect() {
		return aspect;
	}

	public void setAspect(String aspect) {
		this.aspect = aspect;
	}

	public String getSentiment() {
		return sentiment;
	}

	public void setSentiment(String sentiment) {
		this.sentiment = sentiment;
	}

}
