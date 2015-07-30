package com.ibm.irl.sentiment.lexicon;

import edu.stanford.nlp.ling.CoreLabel;

public interface Lexicon {

	public short sentiment(CoreLabel w);

}
