package com.audio3.audioGramInitializer.trim;

import java.util.Arrays;

import com.audio0.main.AppSetup;
import com.audio8.util.Debug;

public class AudioStreamDetailsTrimImplementation implements AudioPreTrimForAnalysis{

	private int counter;
	
	public AudioStreamDetailsTrimImplementation (int[] inputArray, SequenceCheck [] sequenceChecks) {
		AmlitudeFrequencyTrimImplementation.setIndexCorrection();
		AmlitudeFrequencyTrimImplementation.buildSequence(inputArray, sequenceChecks);
		buildUsableSequence(sequenceChecks[0].getStreamDetailsStartIndex(),sequenceChecks[1].getStreamDetailsEndIndex(),inputArray);
	}
	
	public int[] buildUsableSequence(int startIndex, int endIndex, int[] inputSamples) {
		
		Debug.debug(1,"AudioGramPrebuildForInit buildUsableDetailsSequence startIndex "+startIndex+ ", endIndex "+endIndex );

		if(startIndex < 0 || endIndex == 0 || endIndex - startIndex < 200 ) {
			Debug.debug(1,"AudioGramPrebuildForInit buildUsableSequence Trim was Failed!");
			if(AppSetup.continueWithNoTrim) {
				for(int i = 0; i < inputSamples.length-2; i= i+2) {
					AudioPreTrimSelector.borderedIntSequence[counter++] = inputSamples[i];
					AudioPreTrimSelector.borderedIntSequence[counter++] = inputSamples[i+1];
				}	
				Debug.debug(1,"AudioGramPrebuildForInit buildUsableSequence Trim was Failed!..........Continuing with original array");
			}
			AudioPreTrimSelector.borderedIntSequence = null;
			return AudioPreTrimSelector.borderedIntSequence;
		}
		
		AudioPreTrimSelector.borderedIntSequence = new int[endIndex-startIndex];
		counter = 0;
		
		for(int i = startIndex; i < endIndex-2; i= i+2) {
			AudioPreTrimSelector.borderedIntSequence[counter++] = inputSamples[i];
			AudioPreTrimSelector.borderedIntSequence[counter++] = inputSamples[i+1];
		}
		
		Debug.debug(1,"AudioGramPrebuildForInit buildUsableDetailsSequence New Array: "+ AudioPreTrimSelector.borderedIntSequence.length +" startIndex "+startIndex+ ", endIndex "+endIndex
				+", Array.ToString: "+Arrays.toString(AudioPreTrimSelector.borderedIntSequence));
		return AudioPreTrimSelector.borderedIntSequence;
	}

	@Override
	public int[] getTrimedSequence() {
		return AudioPreTrimSelector.borderedIntSequence;
	}
}
