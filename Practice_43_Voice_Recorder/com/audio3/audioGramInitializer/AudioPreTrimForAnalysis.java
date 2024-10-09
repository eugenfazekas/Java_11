 package com.audio3.audioGramInitializer;

public class AudioPreTrimForAnalysis {
/*
	 private static int startIndex = -1;
	 private static int endIndex = -1;
	 public static int averageAmplitude;
	 private static int sum;
	 private static int avg;
	 private static int counter;
	 public static int[] borderedIntSequence;
	 public static int[] borderedDetailsSequence;
	 private static int totalCounter = 0;

	 private static SequenceCheck[] basicSequenceChecks;
		 
	 public AudioPreTrimForAnalysis(int[] samples, int[] audioStreamDetails) {
		 borderedIntSequence = null;
		 startIndex = -1 ;
		 endIndex = -1 ;
		 totalCounter = 0;
		 
		 if(audioStreamDetails != null && AppSetup.preTrim)
			 buildSequenceLengthByFrequencyAndAmplitude(samples,audioStreamDetails,basicSequenceChecks);
		 else {
			 buildSequenceLengthByAmplitude(samples);
		 }
	 }

	 public static void buildSequenceLengthByFrequencyAndAmplitude(int[] inputSamples,int[] audioStreamDetails, SequenceCheck[] sequenceChecks) {
		 
		 if(AppSetup.preTrim == false) {
			 borderedIntSequence = inputSamples;
			 return;
		 }
		 int sequenceChecksCounter = 0;

		 basicSequenceChecks = AppSetup.basefreqencySequenceCheck;
		 
		 if(sequenceChecks == null)
			 sequenceChecks = basicSequenceChecks;
		 
		 for(int i = 0; i < audioStreamDetails.length-2; i= i+3) {
			 
			// System.out.println("frequencys[i]: " + frequencys[i] + ", frequencys[i+1]: "+frequencys[i+1] + ", frequencys[i+2]: " +frequencys[i+2] );
			 		 
			 if(Math.abs(audioStreamDetails[i+1])> sequenceChecks[sequenceChecksCounter].getAmplitudeLowerLimit() &&
			    Math.abs(audioStreamDetails[i+1])< sequenceChecks[sequenceChecksCounter].getAmplitudeUpperLimit() &&
				audioStreamDetails[i+2]> sequenceChecks[sequenceChecksCounter].getFrequencyLowerLimit() &&
			    audioStreamDetails[i+2]< sequenceChecks[sequenceChecksCounter].getFrequencyUpperLimit()) {
				        totalCounter++;
				        Debug.debug(5,"  \u2191" +" Increased = "+ totalCounter+ " i: "+i+",  frequency: "+audioStreamDetails[i+2]+" f[i]: "+audioStreamDetails[i]
				        		+" Lower Limit: "+ sequenceChecks[sequenceChecksCounter].getFrequencyLowerLimit()+" Upper Limit: "+sequenceChecks[sequenceChecksCounter].getFrequencyUpperLimit()
				        		+", AmplitudeLowerLimit: "+sequenceChecks[sequenceChecksCounter].getAmplitudeLowerLimit() + ", actualAmplitude: " +audioStreamDetails[i+1]);
			 }
			 
			 if(Math.abs(audioStreamDetails[i+1]) < sequenceChecks[sequenceChecksCounter].getAmplitudeLowerLimit()  ||
			    Math.abs(audioStreamDetails[i+1]) > sequenceChecks[sequenceChecksCounter].getAmplitudeUpperLimit()  ||
			    audioStreamDetails[i+2]< sequenceChecks[sequenceChecksCounter].getFrequencyLowerLimit() ||
			    audioStreamDetails[i+2]> sequenceChecks[sequenceChecksCounter].getFrequencyUpperLimit() ) {
				 	
				 if (totalCounter > 0) {
						totalCounter--;
				 }
				 	Debug.debug(5,"\u2193  " +" Decreased = "+ totalCounter+ " i: "+i+",  frequency: "+audioStreamDetails[i+2]+" f[i]: "+audioStreamDetails[i]
				 			+" Lower Limit: "+ sequenceChecks[sequenceChecksCounter].getFrequencyLowerLimit()+" Upper Limit: "+sequenceChecks[sequenceChecksCounter].getFrequencyUpperLimit()
			        		+", AmplitudeLowerLimit: "+sequenceChecks[sequenceChecksCounter].getAmplitudeLowerLimit() + ", actualAmplitude: " +audioStreamDetails[i+1]);
			 }
			 
			 if(totalCounter == sequenceChecks[sequenceChecksCounter].getMatchCount()) {
				 sequenceChecks[sequenceChecksCounter].setStreamEndIndex(audioStreamDetails[i]);//= sequenceCheck(sequenceChecks[sequenceChecksCounter]);
				 sequenceChecks[sequenceChecksCounter].setStreamStartIndex(audioStreamDetails[i - sequenceCheck(i,audioStreamDetails, sequenceChecks[sequenceChecksCounter])*3]);
				 sequenceChecks[sequenceChecksCounter].setStreamDetailsEndIndex(i);
				 sequenceChecks[sequenceChecksCounter].setStreamDetailsStartIndex(i - sequenceCheck(i,audioStreamDetails, sequenceChecks[sequenceChecksCounter])*3);
				 Debug.debug(3,"StartIndex Id: "+sequenceChecks[sequenceChecksCounter].getId() + " Start Index: "+sequenceChecks[sequenceChecksCounter].getStreamStartIndex()
						 + " EndIndex: " + sequenceChecks[sequenceChecksCounter].getStreamEndIndex() + ", Start Details: "
						 +sequenceChecks[sequenceChecksCounter].getStreamDetailsStartIndex() + ",  End Details: " + sequenceChecks[sequenceChecksCounter].getStreamDetailsEndIndex()) ;
				 sequenceChecksCounter++;
				 totalCounter = 0;
			 }
			 
			 if(sequenceChecksCounter == sequenceChecks.length) break;

		 }
		 if(!AppSetup.voiceRecognitionData) {
			 buildUsableSequence(sequenceChecks[0].getStreamStartIndex(), sequenceChecks[1].getStreamStartIndex(),inputSamples);
		 }else {
			 
		 }
		 buildUsableDetailsSequence(sequenceChecks[0].getStreamDetailsStartIndex(), sequenceChecks[1].getStreamDetailsStartIndex(),audioStreamDetails);
	 }
	 
	 private static int sequenceCheck(int backLoopStart, int[] dual, SequenceCheck sequenceCheck) {
		 
		 int negativeCounter = 0;
		 int positiveCounter = 0;
		 
		for(int i = backLoopStart; i >= 0; i=i-3) {

			 if(Math.abs(dual[i+1])> sequenceCheck.getAmplitudeLowerLimit() &&
					    Math.abs(dual[i+1])< sequenceCheck.getAmplitudeUpperLimit() &&
					    dual[i+2]> sequenceCheck.getFrequencyLowerLimit() &&
					    dual[i+2]< sequenceCheck.getFrequencyUpperLimit()) {
				 positiveCounter++;
					 }
					 
			 if(Math.abs(dual[i+1]) < sequenceCheck.getAmplitudeLowerLimit()  ||
					    Math.abs(dual[i+1]) > sequenceCheck.getAmplitudeUpperLimit()  ||
					    dual[i+2]< sequenceCheck.getFrequencyLowerLimit() ||
					    dual[i+2]> sequenceCheck.getFrequencyUpperLimit() ) {
				 negativeCounter++;
			}
			 
			 if(positiveCounter == sequenceCheck.getMatchCount()) {
				 Debug.debug(1,"AudioPreTrimForAnalysis sequenceCheck id: "+sequenceCheck.getId() +" sequenceCheck positive: "+positiveCounter + ", negative: "+  negativeCounter);
				 return positiveCounter + negativeCounter*2;
			 }
		}
			return -1;
	}
 
	 public static void buildSequenceLengthByAmplitude(int[] inputArray) {

		 startIndex = -1;
		 endIndex = -1;
		 counter =0;
		 setZero();
		 
		 for(int i = 0; i < inputArray.length; i++) {

			 if(startIndex == -1 || endIndex == -1) {
				 sum += Math.abs(inputArray[i]);
				 counter++;
			 }
			 
			 if(startIndex == -1 && counter == AppSetup.START_AMPLITUDE_SEQUENCE_LENGTH_CHECK) {
				 avg = sum / AppSetup.START_AMPLITUDE_SEQUENCE_LENGTH_CHECK;
				 if(avg > AppSetup.START_WAVE_LOWER_LIMIT_A) {
					 startIndex = i - 2*AppSetup.START_AMPLITUDE_SEQUENCE_LENGTH_CHECK - AppSetup.BEFORE_CUT_LENGTH;
					 Debug.debug(1,"AudioGramPrebuildForInit buildSequenceLength positive avg "+avg);
					 setZero();
				 } else {
					 setZero();
				 }
			 }
			 
			 if(startIndex != -1 && endIndex == -1 && counter == AppSetup.END_AMPLITUDE_SEQUENCE_LENGTH_CHECK) {
				 avg = sum / AppSetup.END_AMPLITUDE_SEQUENCE_LENGTH_CHECK;
				 if(avg < AppSetup.END_WAVE_UPPER_LIMIT_A) {
					 endIndex = i - 2*AppSetup.END_AMPLITUDE_SEQUENCE_LENGTH_CHECK + AppSetup.AFTER_CUT_LENGTH;
					 Debug.debug(1,"AudioGramPrebuildForInit buildSequenceLength negitive avg "+avg);
					 setZero();
				 } else {
					 setZero();
				 }
			 }
		 }
		 
		 if(startIndex != -1 && endIndex == -1)
			 endIndex = inputArray.length;
		 Debug.debug(1, "AudioGramPrebuildForInit buildSequenceLengthByAmplitude startIndex: "+startIndex+", endIndex:  "+endIndex);
		 buildUsableSequence(startIndex,endIndex,inputArray);
	 }
	 
		 private static void setZero() {
			 counter = 0;
			 sum = 0;
			 avg = 0;
		 }
		
		public static void buildUsableSequence(int startIndex, int endIndex,int[] inputSamples) {
			Debug.debug(1,"AudioGramPrebuildForInit buildUsableSequence startIndex "+startIndex+ ", endIndex "+endIndex );
			
			if(startIndex == 0 || endIndex == 0 || endIndex - startIndex < 10000 ) {
				AudioAnalysisInitializer.build = false;
				Debug.debug(1,"AudioGramPrebuildForInit buildUsableSequence Trim was Failed!");
				return;
			}
			
			counter = 0;
			averageAmplitude = 0;
			borderedIntSequence = new int[endIndex-startIndex];

			for(int i = startIndex; i < endIndex; i++ ) {
				borderedIntSequence[counter++] = inputSamples[i];
				averageAmplitude += Math.abs(inputSamples[i]);
			}
			averageAmplitude = averageAmplitude/borderedIntSequence.length;
			AudioAnalysisInitializer.build = true;
			Debug.debug(1,"AudioGramPrebuildForInit buildUsableSequence New Array: "+ borderedIntSequence.length +" startIndex "+startIndex+ ", endIndex "+endIndex );
		}
		
		public static void buildUsableDetailsSequence(int startIndex, int endIndex,int[] inputSamples) {
			
			Debug.debug(1,"AudioGramPrebuildForInit buildUsableDetailsSequence startIndex "+startIndex+ ", endIndex "+endIndex );
			
			if(startIndex == 0 || endIndex == 0 || endIndex - startIndex < 500 ) {
				AudioAnalysisInitializer.build = false;
				Debug.debug(1,"AudioGramPrebuildForInit buildUsableSequence Trim was Failed!");
				return;
			}
			
			borderedDetailsSequence = new int[((endIndex-startIndex)/3)*2];
			counter = 0;
			for(int i = startIndex; i < endIndex; i= i+3) {
				borderedDetailsSequence[counter++] = inputSamples[i+1];
				borderedDetailsSequence[counter++] = inputSamples[i+2];
			}
			Debug.debug(1,"AudioGramPrebuildForInit buildUsableDetailsSequence New Array: "+ borderedDetailsSequence.length +" startIndex "+startIndex+ ", endIndex "+endIndex
					+", Array.ToString: "+Arrays.toString(borderedDetailsSequence));
		}*/
}
