package com.audio3.audioGramInitializer.trim;

import com.audio0.main.AppSetup;
import com.audio8.util.Debug;

public class AmlitudeFrequencyTrimImplementation implements AudioPreTrimForAnalysis{
	
	private static int counter;
	private static int totalCounter ;
	//index Corretion;
	private static int a_index;
	private static int f_index;
	private static int l_index;
		
	public AmlitudeFrequencyTrimImplementation (int[] inputSamples,int[] audioStreamDetails, SequenceCheck [] sequenceChecks) {
		setIndexCorrection();
		buildSequence(audioStreamDetails, sequenceChecks);
		buildUsableSequence(sequenceChecks[0].getStreamStartIndex(),sequenceChecks[1].getStreamStartIndex(),inputSamples);
	}
	
	static void buildSequence(int [] audioStreamDetails,SequenceCheck [] sequenceChecks) {

		 resetClassVariables();
		 int sequenceChecksCounter = 0;	 
		 
		 for(int i = 0; i < audioStreamDetails.length-2; i=i+l_index) {
			 
			// System.out.println("frequencys[i]: " + frequencys[i] + ", frequencys[i+1]: "+frequencys[i+1] + ", frequencys[i+2]: " +frequencys[i+2] );
			 		 
			 if(Math.abs(audioStreamDetails[i+a_index])> sequenceChecks[sequenceChecksCounter].getAmplitudeLowerLimit() &&
			    Math.abs(audioStreamDetails[i+a_index])< sequenceChecks[sequenceChecksCounter].getAmplitudeUpperLimit() &&
				audioStreamDetails[i+f_index]> sequenceChecks[sequenceChecksCounter].getFrequencyLowerLimit() &&
			    audioStreamDetails[i+f_index]< sequenceChecks[sequenceChecksCounter].getFrequencyUpperLimit()) {
				        totalCounter++;
				        Debug.debug(3,"   \u2191" +" Increased = "+ totalCounter+ " i: "+i+",  frequency: "+audioStreamDetails[i+f_index]+" f[i]: "+audioStreamDetails[i]
				        		+" Lower Limit: "+ sequenceChecks[sequenceChecksCounter].getFrequencyLowerLimit()+" Upper Limit: "+sequenceChecks[sequenceChecksCounter].getFrequencyUpperLimit()
				        		+", AmplitudeLowerLimit: "+sequenceChecks[sequenceChecksCounter].getAmplitudeLowerLimit() + ", actualAmplitude: " +audioStreamDetails[i+a_index]);
			 }
			 
			 if(Math.abs(audioStreamDetails[i+a_index]) < sequenceChecks[sequenceChecksCounter].getAmplitudeLowerLimit()  ||
			    Math.abs(audioStreamDetails[i+a_index]) > sequenceChecks[sequenceChecksCounter].getAmplitudeUpperLimit()  ||
			    audioStreamDetails[i+f_index]< sequenceChecks[sequenceChecksCounter].getFrequencyLowerLimit() ||
			    audioStreamDetails[i+f_index]> sequenceChecks[sequenceChecksCounter].getFrequencyUpperLimit() ) {
				 	
				 if (totalCounter > 0) {
						totalCounter--;
				 }
				 	Debug.debug(3," \u2193  " +" Decreased = "+ totalCounter+ " i: "+i+",  frequency: "+audioStreamDetails[i+f_index]+" f[i]: "+audioStreamDetails[i]
				 			+" Lower Limit: "+ sequenceChecks[sequenceChecksCounter].getFrequencyLowerLimit()+" Upper Limit: "+sequenceChecks[sequenceChecksCounter].getFrequencyUpperLimit()
			        		+", AmplitudeLowerLimit: "+sequenceChecks[sequenceChecksCounter].getAmplitudeLowerLimit() + ", actualAmplitude: " +audioStreamDetails[i+a_index]);
			 }
			 
			 if(totalCounter == sequenceChecks[sequenceChecksCounter].getMatchCount()) {
				 sequenceChecks[sequenceChecksCounter].setStreamDetailsEndIndex(i+a_index);
				 sequenceChecks[sequenceChecksCounter].setStreamDetailsStartIndex(i +a_index- sequenceCheck(i,audioStreamDetails, sequenceChecks[sequenceChecksCounter])*2);
				 if(AppSetup.voiceRecognitionAmplitudeRefinary == false && AppSetup.voiceRecognitionFrequencyRefinary == false) {
					 sequenceChecks[sequenceChecksCounter].setStreamEndIndex(audioStreamDetails[i]);//= sequenceCheck(sequenceChecks[sequenceChecksCounter]);
					 sequenceChecks[sequenceChecksCounter].setStreamStartIndex(audioStreamDetails[i - sequenceCheck(i,audioStreamDetails, sequenceChecks[sequenceChecksCounter])*3]);
				 }
				 Debug.debug(3,"StartIndex Id: "+sequenceChecks[sequenceChecksCounter].getId() + " Start Index: "+sequenceChecks[sequenceChecksCounter].getStreamStartIndex()
						 + " EndIndex: " + sequenceChecks[sequenceChecksCounter].getStreamEndIndex() + ", Start Details: "
						 +sequenceChecks[sequenceChecksCounter].getStreamDetailsStartIndex() + ",  End Details: " + sequenceChecks[sequenceChecksCounter].getStreamDetailsEndIndex()) ;
				 sequenceChecksCounter++;
				 totalCounter = 0;
			 }
			 if(sequenceChecksCounter > 0)
				 AudioPreTrimSelector.averageAmplitude += Math.abs(audioStreamDetails[i+a_index]);
			 if(sequenceChecksCounter == sequenceChecks.length) {
					Debug.debug(1,"AmlitudeFrequencyTrimImplementation totalaverageAmplitude "+AudioPreTrimSelector.averageAmplitude +
							", endIndex "+sequenceChecks[sequenceChecksCounter-1].getStreamDetailsEndIndex()
							+ ",startIndex "+sequenceChecks[sequenceChecksCounter-2].getStreamDetailsStartIndex());
					AudioPreTrimSelector.averageAmplitude = 
							AudioPreTrimSelector.averageAmplitude/((sequenceChecks[sequenceChecksCounter-1].getStreamDetailsEndIndex()-
							                                       sequenceChecks[sequenceChecksCounter-2].getStreamDetailsStartIndex()
							                                     )/l_index);
					Debug.debug(1,"AmlitudeFrequencyTrimImplementation averageAmplitude: "+AudioPreTrimSelector.averageAmplitude);
					break;}
		 }
	}


	 static int[] buildUsableSequence(int startIndex, int endIndex,int[] inputSamples) {
		Debug.debug(1,"AmlitudeFrequencyTrimImplementation buildUsableSequence startIndex "+startIndex+ ", endIndex "+endIndex );
		
		if(startIndex < 0 || endIndex == 0 || endIndex - startIndex < 10000 ) {
			Debug.debug(1,"AudioGramPrebuildForInit buildUsableSequence Trim was Failed!");
			if(AppSetup.continueWithNoTrim) {
				AudioPreTrimSelector.borderedIntSequence = inputSamples;
				Debug.debug(1,"AudioGramPrebuildForInit buildUsableSequence Trim was Failed!......... Continuing with Base Stream");
				return AudioPreTrimSelector.borderedIntSequence;
			}
			AudioPreTrimSelector.borderedIntSequence = null;
			return AudioPreTrimSelector.borderedIntSequence;
		}
		
		counter = 0;
		AudioPreTrimSelector.borderedIntSequence = new int[endIndex-startIndex];

		for(int i = startIndex; i < endIndex; i++ ) {
			AudioPreTrimSelector.borderedIntSequence[counter++] = inputSamples[i];
		}

		Debug.debug(1,"AudioGramPrebuildForInit buildUsableSequence New Array: "+ AudioPreTrimSelector.borderedIntSequence.length +" startIndex "+startIndex+ ", endIndex "+endIndex );
		return AudioPreTrimSelector.borderedIntSequence;
	 }
	
	 static int sequenceCheck(int backLoopStart, int[] dual, SequenceCheck sequenceCheck) {
		 
		 int negativeCounter = 0;
		 int positiveCounter = 0;
		 
		for(int i = backLoopStart; i >= 0; i=i-l_index) {

			 if(Math.abs(dual[i+a_index])> sequenceCheck.getAmplitudeLowerLimit() &&
					    Math.abs(dual[i+a_index])< sequenceCheck.getAmplitudeUpperLimit() &&
					    dual[i+f_index]> sequenceCheck.getFrequencyLowerLimit() &&
					    dual[i+f_index]< sequenceCheck.getFrequencyUpperLimit()) {
				 positiveCounter++;
					 }
					 
			 if(Math.abs(dual[i+a_index]) < sequenceCheck.getAmplitudeLowerLimit()  ||
					    Math.abs(dual[i+a_index]) > sequenceCheck.getAmplitudeUpperLimit()  ||
					    dual[i+f_index]< sequenceCheck.getFrequencyLowerLimit() ||
					    dual[i+f_index]> sequenceCheck.getFrequencyUpperLimit() ) {
				 negativeCounter++;
				 positiveCounter--;
			}
			 
			 if(positiveCounter == sequenceCheck.getMatchCount()) {
				 Debug.debug(1,"AudioPreTrimForAnalysis sequenceCheck id: "+sequenceCheck.getId() +" sequenceCheck positive: "+positiveCounter + ", negative: "+  negativeCounter);
				 return positiveCounter + negativeCounter;
			 }
		}
			return -1;
	}

	@Override
	public int[] getTrimedSequence() {
		return AudioPreTrimSelector.borderedIntSequence;
	}

	public static void resetClassVariables() {
		 counter = 0;
		 AudioPreTrimSelector.averageAmplitude = 0;
		 totalCounter = 0;
		 AudioPreTrimSelector.borderedIntSequence = null;
	}
	
	public static void setIndexCorrection() {
		if(AppSetup.voiceRecognitionAmplitudeRefinary == true || AppSetup.voiceRecognitionFrequencyRefinary == true) {
			 a_index = 0;
			 f_index = 1;
			 l_index = 2; 
		} else {
			 a_index = 1;
			 f_index = 2;
			 l_index = 3;
		}
	}
}
