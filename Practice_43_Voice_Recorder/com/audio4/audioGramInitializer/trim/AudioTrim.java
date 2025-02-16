package com.audio4.audioGramInitializer.trim;

import com.audio4.audioGramInitializer.trim.sequence.SequenceFilter;

public interface AudioTrim {
	
    void buildSequence(int[] inputArray , SequenceFilter sequenceFilter);
}
