package com.audio8.util;

import java.util.Arrays;

import com.audio0.main.AppSetup;
import com.audio2.recorder.AudioListener;
import com.audio3.recorder.refinary.StreamRefinaryFrequencyMethods;
import com.audio4.audioGramInitializer.AudioAnalysisThread;
import com.audio5.audioGramBuilder.AmplitudeDetails;
import com.audio5.audioGramBuilder.FrequencyDetails;
import com.audio5.audioGramBuilder.MiscellaneousData;
import com.audio5.recognition.slope.Slope;

public class VoiceRecognitionUtil {
	
	private static float sumToDivide;
	private static int diveder;
	private static float result;	
	private static int[] amplitudeArray;
	private static int[] frequencyArray;
	private static int[] tempArray1;
	private static int[] tempArray2;
	private static int tempArrayCounter1;
	private static int tempArrayCounter2;
	private static int amplitudeBufferDiveder;
	public static int frequencyBufferDiveder;
	public static int amplitudeTotal;
	public static int frequencyTotal;
	public static int milisecCounter;
	public static Slope[] slopeArray;
	public static int AVG_MILISEC_LENGTH = AudioBuilderUtil.getBuffersLengthByMilisec(
	   (int)AudioListener.format.getSampleRate(),AppSetup.AUDIO_BUFFER_MILISEC_LENGTH);

	public static int calculateMatchProcent1(int dB_Value, int check_Value) {

		sumToDivide = dB_Value < check_Value ? dB_Value : check_Value;
		diveder = dB_Value < check_Value ? check_Value : dB_Value  ;
		result = ((float)sumToDivide / diveder) * 100;
		
		Debug.debug(5, "VoiceRecognition calculateMatchProcent dB_Value: "+dB_Value
				          +", check_Value: "+check_Value + ", result: "+result);
		
			return (int)result;
	}
	
	public static int calculateMatchProcent2(int dB_Value, int check_Value) {
		
		if((dB_Value - check_Value <= 20 && dB_Value - check_Value >= 0) 
			|| (check_Value - dB_Value <= 20 && check_Value - dB_Value >= 0))	{	
		
			result = dB_Value - check_Value <= 20 && dB_Value - check_Value > 0 ? 
				dB_Value - check_Value : check_Value - dB_Value;
			
			return (int) (100 - result);
		
		} else
			
			return 0;
	}
	
	public static void resetVariables() {
		
		amplitudeBufferDiveder = 0;
		frequencyBufferDiveder = 0;
		amplitudeTotal = 0;
		frequencyTotal = 0;
		result = 0;
		milisecCounter = 0;
	}
	
	public static void rebuildStreamDetailsUnspikeAndAvg(int id, int[] inputSamples, int startIndex, int endIndex, int averageAmplitude) {

		tempArrayCounter1 = 0;
		resetVariables();
		
		amplitudeArray = new int[(endIndex - startIndex) /3];
		frequencyArray = new int[(endIndex - startIndex) /3];

		for(int i = startIndex; i < endIndex; i= i+3) {

			amplitudeTotal += inputSamples[i+1];
			amplitudeBufferDiveder++;

			frequencyTotal +=  AudioListener.format.getSampleRate();
			frequencyBufferDiveder += StreamRefinaryFrequencyMethods.frequencySampleLengths[inputSamples[i+2]];
			milisecCounter+= inputSamples[i+3]- inputSamples[i];

			if(milisecCounter >= AVG_MILISEC_LENGTH || i == endIndex - 3) {
				Debug.debug(5,"VoiceRecognitionUtil rebuildStreamDetailsUnspikeAndAvg i+3: " + inputSamples[i+3] 
						+ ", inputSamples[i]: "+inputSamples[i]);
				
				result = amplitudeTotal / amplitudeBufferDiveder;
				amplitudeArray[tempArrayCounter1] = AmplitudeDetails.getPercentAmplitude(averageAmplitude ,(int)result);
				
				result = frequencyTotal / frequencyBufferDiveder;
				frequencyArray[tempArrayCounter1++] = FrequencyDetails.getMappedFrequency100((int)result);		
			
				Debug.debug(5,"VoiceRecognitionUtil rebuildStreamDetailsUnspikeAndAvg i: "+i
					+", tempArrayCounter: "+(tempArrayCounter1-1)+", amplitudeArray "
					+amplitudeArray[tempArrayCounter1-1]+", frequencyArray[frequencyArray-1] "
					+frequencyArray[tempArrayCounter1-1]);
				resetVariables();
				milisecCounter = 0;
			}
		}	
		
		tempArray1 = new int[tempArrayCounter1-1];
		tempArray2 = new int[tempArrayCounter1-1];
		
		for(int i = 0; i < tempArray1.length ; i++) {
			
			tempArray1[i] = amplitudeArray[i];
			tempArray2[i] = frequencyArray[i];
		}
		
		amplitudeArray = tempArray1;
		frequencyArray = tempArray2;
				
		if(AppSetup.spykeRemove) {
			
			amplitudeArray =  AudioGramUtil.removeSpikes(amplitudeArray,1);
			frequencyArray =  AudioGramUtil.removeSpikes(frequencyArray,1);
		}
		
		if(AppSetup.avgBuild) {
			
			amplitudeArray = AudioGramUtil.buildAvgArray(amplitudeArray,AppSetup.avglength);
			frequencyArray = AudioGramUtil.buildAvgArray(frequencyArray,AppSetup.avglength);
		}	
		
		amplitudeArray = AudioGramUtil.optimezeAmplitudeMapHeight(amplitudeArray);
		
		tempArrayCounter1 = 0;
		tempArrayCounter2 = 0;
		
		tempArray1 = new int[amplitudeArray.length*3];
		tempArray2 = new int[amplitudeArray.length*2];

		for(int i = 0; i < amplitudeArray.length; i++) {
			
			tempArray1[tempArrayCounter1++] = amplitudeArray[i];
			tempArray1[tempArrayCounter1++] = frequencyArray[i];	
			tempArray1[tempArrayCounter1++] =(frequencyArray[i] *100) / amplitudeArray[i];
			
			tempArray2[tempArrayCounter2++] = amplitudeArray[i];
			tempArray2[tempArrayCounter2++] = frequencyArray[i];	
		}
		
		AudioAnalysisThread.startedVoiceCheck.get(id).setAmplitudeWaveMap(amplitudeArray);
		AudioAnalysisThread.startedVoiceCheck.get(id).setFrequencyWaveMap(frequencyArray);
		AudioAnalysisThread.startedVoiceCheck.get(id).setVoiceReconitionCheckPointsArray(tempArray1);
		AudioAnalysisThread.startedVoiceCheck.get(id).setMySpektrogramMap(tempArray2);
		
		MiscellaneousData.buildVoiceRecognitionSlopesArray(id);
		
		Debug.debug(3, "VoiceRecognitionUtil rebuild Amplitude length : " + amplitudeArray.length+", Array: "
				+ Arrays.toString(AudioAnalysisThread.startedVoiceCheck.get(id).getAmplitudeWaveMap()));
		
		Debug.debug(3, "VoiceRecognitionUtil rebuild Frequency length : " + frequencyArray.length+", Array: "
				+ Arrays.toString(AudioAnalysisThread.startedVoiceCheck.get(id).getFrequencyWaveMap()));
		
		Debug.debug(3, "VoiceRecognitionUtil rebuild Spektrogram length : " + tempArray2.length+  ", Array: "
				+ Arrays.toString(AudioAnalysisThread.startedVoiceCheck.get(id).getMySpektrogramMap()));
	}
	/*
	public static int[] buildSlopeArrayAndConvertToIntArray(int[] buildInput) {
				
		slopeArray = SlopeBuilder.mainSlopeBuilder(buildInput);
		tempArrayCounter1 = 0;
		tempArray1 = new int[slopeArray.length*4];
		
		for(int i = 0; i < slopeArray.length; i++) {
			
			if(slopeArray[i] == null) break;
			
				tempArray1[tempArrayCounter1++] = slopeArray[i].gethPosition();
				tempArray1[tempArrayCounter1++] = slopeArray[i].getvPosition();
				tempArray1[tempArrayCounter1++] = slopeArray[i].getAvgSlopedirection();
				tempArray1[tempArrayCounter1++] = slopeArray[i].getMainLength();
		}

		return tempArray1;
	}
	*/
}
