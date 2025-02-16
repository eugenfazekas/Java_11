package com.audio5.audioGramBuilder;

import java.util.Arrays;

import com.audio2.recorder.AudioListener;
import com.audio3.recorder.refinary.StreamRefinaryFrequencyMethods;
import com.audio8.util.Debug;

public class FrequencyDetails {

//	public static int frequencyBufferDiveder;
//	public static int positive = 1;
//	public static int negative = 0;
//	//public static int dataCounter = 0;
//	public static int avg = 0;
//	public static int frequency = 0;
//	public static int F_AVG_MILISEC_LENGTH; 
//	public static int favgMilisecCounter;
//	public static int frequencyTotalBuffer;
//	public static int frequencyBufferCounter = 0;
//	public static int[] frequencyWaveMap;
// 	public static int frequencyWaveMapCounter;
// 	public static int[] mappedFrequencyArray;	
//	 	
//	public static void initFrequencyWaveMapDetails(int id) {
//			
//		positive = 1;
//		negative = 0;
//		frequencyTotalBuffer = 0;
//		frequencyBufferDiveder = 0 ;
//		frequencyWaveMap = new int[1000]; 
//	    frequencyWaveMapCounter = 0;
//		mappedFrequencyArray = null;
//		favgMilisecCounter = 0;		
//		
//		F_AVG_MILISEC_LENGTH = AudioBuilderUtil.getBuffersLengthByMilisec(
//			(int)AudioAnalysisThread.startedVoiceCheck.get(id).getAudioFormat().getSampleRate() 
//			,AppSetup.FREQUENCY_BUFFER_MILISEC_LENGTH);
//			
//		Debug.debug(3,"FrequencyDetails initFrequencyWaveDetails!");
//	}
	

	public static void addToFrequnecyBuffer(int bufferValue) {

		if(bufferValue > 11025) {
			
			Debug.debug(3,"FrequencyDetails addToBuffer bufferValue: "+bufferValue);
			bufferValue =11025;
		}
			
			
		AGBCVariables.frequencyTotalBuffer +=  AudioListener.format.getSampleRate();
		AGBCVariables.frequencyBufferDiveder += StreamRefinaryFrequencyMethods.frequencySampleLengths[bufferValue];

		Debug.debug(5,"FrequencyDetails addToBuffer bufferValue: "+bufferValue 
			+ ",  frequencyBuffer: "+AGBCVariables.frequencyTotalBuffer + ", frequencyBufferCounter: "
			+ AGBCVariables.frequencyBufferDiveder +", i: "+MultiAnalysisBuilder.i);
	 }
	  

	 public static void addToFrequencyWaveMap(int buffer, int divider) {
		 
		if(divider > 0) {
	 
			AGBCVariables.avg = buffer / divider ;	
	
			AGBCVariables.frequencyWaveMap[AGBCVariables.frequencyWaveMapCounter++] = AGBCVariables.avg; 
	
			 Debug.debug(5,"FrequencyDetails addToFrequencyWaveMap i:  "+ MultiAnalysisBuilder.i + ", frequencyTotalbuffer: "
				+ buffer + " / bufferCounter "+ divider + " avg: "+AGBCVariables.avg
				+ " ,AnalysisInitializer.waveCounter: "+AGBCVariables.frequencyWaveMapCounter+", frequency: "
				+ AGBCVariables.frequencyWaveMap[AGBCVariables.frequencyWaveMapCounter-1]);	
		}
		 
		AGBCVariables.frequencyTotalBuffer = 0;
		AGBCVariables.frequencyBufferDiveder = 0;
	}
 			
	public static int getFrequency(int sapmleRate , int cycleSamples) {
		
		int freq = sapmleRate / cycleSamples;
		
		Debug.debug(5,"1. freq: "+freq);
		
		return freq;
	}
	
	 public static void buildMappedArray() {
		 
		 AGBCVariables.mappedFrequencyArray = new int[AGBCVariables.frequencyWaveMap.length];
		
		for(int i = 0 ; i < AGBCVariables.frequencyWaveMap.length; i++)
			AGBCVariables.mappedFrequencyArray[i] = getMappedFrequency100( AGBCVariables.frequencyWaveMap[i]);
		  
		Debug.debug(5,"FrequencyDetailsV1 buildMappedArray: "
			+ Arrays.toString(AGBCVariables.mappedFrequencyArray));
	 }
	 
	 
	 public static int getMappedFrequency300(int frequency) {
		 
		 if(frequency <= 300) {
			 
			 return  (int) ((Math.log(frequency)/ Math.log(1.13)) - 26) ;
		 }
		 
		 else if(frequency <= 900) {
			 
			 return (int) (frequency/4.3 -  39) ;
			 
		 } else {
			 
			 return (int) ((Math.log(frequency)/ Math.log(1.041)) +1); 
		 }

	 }
	 
	 public static int getMappedFrequency200(int frequency) {
		 
		 if(frequency <= 300) {
			 
			 return  (int) ((Math.log(frequency)/ Math.log(1.28)) - 13) ;
		 }
		 
		 else if(frequency <= 900) {
			 
			 return (int) (frequency/8.6 - 24.5) ;
			 
		 } else {
			 
			 return (int) ((Math.log(frequency)/ Math.log(1.062)) - 33); 
		 }

	 }
	 
	 public static int getMappedFrequency100(int frequency) {
		 
		 if(frequency <= 100) 
			 
			 return  1;	 
		 
		 else if(frequency <= 700) 
			 
			 return (int) ((float)frequency/54*7.75-10) ;
			 
		  else 
			 
			 return (int) (Math.log(frequency)/ Math.log(1.155) +45); 		 
	 }
	 
 public static int getMappedFrequency50(int frequency) {
		 
		 if(frequency <= 100) 
			 
			 return  1;	 
		 
		 else if(frequency <= 900) 
			 
			 return (int) ((float)frequency/54*6-10) ;
			 
		  else 
			 
			 return (int) (Math.log(frequency)/ Math.log(1.14) +39); 		 
	 }

	 
/*
	 public static int getMappedFrequency(int frequency) {
		  
        int mappedFrequency = 0;
         
        if(frequency <= 100) {
        	 
        	 mappedFrequency = getLoWFrequencyMap(frequency); 
        	 
        	 Debug.debug(5,"getMappedFrequency getLoWFrequencyMap called ");
        }
         
        if(frequency > 100 && frequency< 1000) {
        	 
        	 mappedFrequency = getMiddleFrequencyMap(frequency); 
        	 
        	 Debug.debug(5,"getMappedFrequency getMiddleFrequencyMap called ");
        }
         
        if( frequency>= 1000) {
        	 
        	 mappedFrequency = getHighFrequencyMap(frequency); 
        	 
        	 Debug.debug(5,"getMappedFrequency getHighFrequencyMap called ");
        }
	         
			return mappedFrequency;
	}

	private static int getLoWFrequencyMap(int frequency) {
		
		return (int)(frequency * 10)/99;
	}	
	
	private static int getMiddleFrequencyMap(int frequency) {
		
		return (int)((((frequency-100) * 80)/1025)+10);
	}
	
	private static int getHighFrequencyMap(int frequency) {
		
		return (int)((((frequency-1000) * 20)/10000)+80);
	}
	*/
	
}
