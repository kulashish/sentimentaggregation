package com.ibm.irl.sentiment.util;

public class SentimentParameters {

	public static final String ROOT_PATH = "C:\\Users\\IBM_ADMIN\\workspace\\SentimentAggregation\\";
	// public static final String ROOT_PATH = "/home/askulk61/";
	public static final String JED_FILM_ONTOLOGY = ROOT_PATH
			+ "ontology/jedfilm.txt";
	public static final String JED_FILM_ONTOLOGY_RDFS = ROOT_PATH
			+ "ontology/cinema.rdfs";
	public static final String JED_FILM_ONTOLOGY_OWL = ROOT_PATH
			+ "ontology/cinema_trim.owl";
	public static final String RESTAURANT_ONTOLOGY_OWL = ROOT_PATH
			+ "ontology/rest.owl";
	// public static final String JED_FILM_ONTOLOGY =
	// "/home/askulk61/ontology/jedfilm.txt";
	public static final String MOVIE_ONTOLOGY = ROOT_PATH
			+ "ontology/movieontology.owl";

	// public static final String DATASET_MOVIE_ANNOT_IN =
	// "C:\\Users\\IBM_ADMIN\\Documents\\sentiment_analysis\\datasets\\annot_input";
	// public static final String DATASET_MOVIE_ANNOT_OUT =
	// "C:\\Users\\IBM_ADMIN\\Documents\\sentiment_analysis\\datasets\\annot_output";
	public static final String DATASET_RESTAURANT = "C:\\Users\\IBM_ADMIN\\Documents\\sentiment_analysis\\datasets\\restaurant\\reviews_restaurants.txt";
	// public static final String DATASET_RESTAURANT_ANNOT_IN =
	// "C:\\Users\\IBM_ADMIN\\Documents\\sentiment_analysis\\datasets\\rest_annot_input\\stage1";
	// public static final String DATASET_RESTAURANT_ANNOT_OUT =
	// "C:\\Users\\IBM_ADMIN\\Documents\\sentiment_analysis\\datasets\\rest_annot_output";

	public static final String DATASET_MOVIE_ANNOT_IN = ROOT_PATH
			+ "sentiment/annot_input";
	public static final String DATASET_MOVIE_ANNOT_OUT = ROOT_PATH
			+ "sentiment/annot_output";
	public static final String DATASET_RESTAURANT_ANNOT_IN = ROOT_PATH
			+ "sentiment/rest_annot_input";
	public static final String DATASET_RESTAURANT_ANNOT_OUT = ROOT_PATH
			+ "sentiment/rest_annot_output";
	public static String DATASET_ANNOT_IN = null;
	public static String DATASET_ANNOT_OUT = null;

	public static final String BING_POSITIVE_PATH = ROOT_PATH
			+ "lexicons/bingliu/positive-words-ak.txt";
	public static final String BING_NEGATIVE_PATH = ROOT_PATH
			+ "lexicons/bingliu/negative-words-ak.txt";
	// public static final String BING_POSITIVE_PATH =
	// "/home/askulk61/lexicons/bingliu/positive-words-ak.txt";
	// public static final String BING_NEGATIVE_PATH =
	// "/home/askulk61/lexicons/bingliu/negative-words-ak.txt";

	public static final double CONCEPT_MATCH_THRESHOLD = 0.749d;

	// public static final String DOMAIN = "film";
	public static final String DOMAIN = "restaurant";

	static {
		if (DOMAIN.equalsIgnoreCase("film")) {
			DATASET_ANNOT_IN = DATASET_MOVIE_ANNOT_IN;
			DATASET_ANNOT_OUT = DATASET_MOVIE_ANNOT_OUT;
		} else if (DOMAIN.equalsIgnoreCase("restaurant")) {
			DATASET_ANNOT_IN = DATASET_RESTAURANT_ANNOT_IN;
			DATASET_ANNOT_OUT = DATASET_RESTAURANT_ANNOT_OUT;
		}
	}

}
