package com.audio3.audioGramInitializer.trim;

import com.audio0.main.AppSetup;
import com.audio8.util.Debug;

public class AmplitudeTrimImplementation implements AudioPreTrimForAnalysis {

	private int startIndex;
	private int endIndex;

    private int sum;
    private int avg;
	private int counter;
	 
	public AmplitudeTrimImplementation(int[] inputArray, SequenceCheck [] sequenceChecks) {
		buildSequence(inputArray, sequenceChecks);
		AudioPreTrimSelector.borderedIntSequence = AmlitudeFrequencyTrimImplementation.buildUsableSequence(startIndex,endIndex,inputArray);
	}
	
	public void buildSequence(int[] inputArray, SequenceCheck [] sequenceChecks) {
		 startIndex = -1;
		 endIndex = -1;
		 counter =0;
		 setZero();
		 for(int i = 1; i < inputArray.length; i++) {

			 if(startIndex == -1 || endIndex == -1) {
				 sum += Math.abs(inputArray[i]);
				 counter++;
			 }

			 if(startIndex == -1 && counter == sequenceChecks[0].getMatchCount()) {
				 avg = sum / sequenceChecks[0].getMatchCount();
				 if(avg > sequenceChecks[0].getAmplitudeLowerLimit()) {
					 startIndex = i - sequenceChecks[0].getMatchCount();
					 if(startIndex - AppSetup.BEFORE_CUT_LENGTH > 0)
						 startIndex = startIndex - AppSetup.BEFORE_CUT_LENGTH;
					 Debug.debug(1,"AmplitudeTrimImplementation buildSequenceLength positive avg "+avg+ ", startIndex: "+startIndex 
							 + ", i: "+i + " lowerLimit: "+sequenceChecks[0].getAmplitudeLowerLimit());
					 setZero();
				 } else {
					 setZero();
				 }
			 }
			 
			 if(startIndex != -1 && endIndex == -1 && counter == sequenceChecks[1].getMatchCount()) {
				 AudioPreTrimSelector.averageAmplitude += sum;
				 avg = sum / sequenceChecks[1].getMatchCount();
				// System.out.println("avg: "+avg +", sequenceChecks[1].getAmplitudeUpperLimit() "+sequenceChecks[1].getAmplitudeUpperLimit()+ ", IDLE_AMPLITUDE_VOLUME: "+AppSetup.IDLE_AMPLITUDE_VOLUME);
				 if(avg < sequenceChecks[1].getAmplitudeUpperLimit()) {
					 endIndex = i - sequenceChecks[1].getMatchCount();
					 if(endIndex + AppSetup.AFTER_CUT_LENGTH <inputArray.length )
						 endIndex = endIndex + AppSetup.AFTER_CUT_LENGTH;
					 Debug.debug(1,"AmplitudeTrimImplementation buildSequenceLength negitive avg "+avg+ ", endIndex: "+endIndex
							 + ", i: "+i + " upperLimit: "+sequenceChecks[1].getAmplitudeUpperLimit());
					 setZero();
				 } else {
					 setZero();
				 }
			 }
			 if(startIndex != -1 && endIndex != -1 ) {
				 Debug.debug(1,"AmplitudeTrimImplementation averageAmplitude total: "+AudioPreTrimSelector.averageAmplitude
						 +" , endIndex: "+endIndex +", startIndex: "+startIndex+ ", average Amplitude: "+
						 (AudioPreTrimSelector.averageAmplitude / (endIndex - startIndex )) );
				 AudioPreTrimSelector.averageAmplitude = AudioPreTrimSelector.averageAmplitude / (endIndex - startIndex );
				 break;
			 }
		 }		
		 Debug.debug(1, "AmplitudeTrimImplementation buildSequence startIndex: "+startIndex+", endIndex:  "+endIndex);
	}
	 private  void setZero() {
		 counter = 0;
		 sum = 0;
		 avg = 0;
	 }

	@Override
	public int[] getTrimedSequence() {
		return AudioPreTrimSelector.borderedIntSequence;
	}
}
