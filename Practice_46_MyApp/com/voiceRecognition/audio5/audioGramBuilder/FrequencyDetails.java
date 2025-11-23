package com.voiceRecognition.audio5.audioGramBuilder;

import java.util.Arrays;

import com.voiceRecognition.audio8.util.Debug;

public class FrequencyDetails {
	
	private static int debug_level_INFO = 5;
	private static int debud_level_DEBUG = 5;

	static void addToFrequencyWaveMap(int frequency) {
		 
		AGBCVariables.frequencyWaveMap[AGBCVariables.frequencyWaveMapCounter++] = frequency; 

		 Debug.debug(debud_level_DEBUG,"FrequencyDetails addToFrequencyWaveMap frequencyTotalbuffer: "
			+ " frequency: "+ frequency
			+" ,AnalysisInitializer.waveCounter: "+AGBCVariables.frequencyWaveMapCounter+", frequency: "
			+ AGBCVariables.frequencyWaveMap[AGBCVariables.frequencyWaveMapCounter-1]);		
	}
 			
	static int getFrequency(int sapmleRate , int cycleSamples) {
		
		int freq = sapmleRate / cycleSamples;
		
		Debug.debug(debud_level_DEBUG,"1. freq: "+freq);
		
		return freq;
	}
	
	static void buildMappedArray() {
		 
		 AGBCVariables.mappedFrequencyArray = new int[AGBCVariables.frequencyWaveMap.length];
		
		for(int i = 0 ; i < AGBCVariables.frequencyWaveMap.length; i++)
			AGBCVariables.mappedFrequencyArray[i]=getMappedFrequency25( AGBCVariables.frequencyWaveMap[i]);
		  
		Debug.debug(debug_level_INFO,"FrequencyDetailsV1 buildMappedArray: "
			+ Arrays.toString(AGBCVariables.mappedFrequencyArray));
	 }
	 	 
	 static int getMappedFrequency300(int frequency) {
		 
		 if(frequency <= 300) {
			 
			 return  (int) ((Math.log(frequency)/ Math.log(1.13)) - 26) ;
		 }
		 
		 else if(frequency <= 900) {
			 
			 return (int) (frequency/4.3 -  39) ;
			 
		 } else {
			 
			 return (int) ((Math.log(frequency)/ Math.log(1.041)) +1); 
		 }

	 }
	 
	 static int getMappedFrequency200(int frequency) {
		 
		 if(frequency <= 300) {
			 
			 return  (int) ((Math.log(frequency)/ Math.log(1.28)) - 13) ;
		 }
		 
		 else if(frequency <= 900) {
			 
			 return (int) (frequency/8.6 - 24.5) ;
			 
		 } else {
			 
			 return (int) ((Math.log(frequency)/ Math.log(1.062)) - 33); 
		 }

	 }
	 
	 static int getMappedFrequency100(int frequency) {
		 
		 if(frequency <= 100) 
			 
			 return  1;	 
		 
		 else if(frequency <= 700) 
			 
			 return (int) ((float)frequency/54*7.75-10) ;
			 
		  else 
			 
			 return (int) (Math.log(frequency)/ Math.log(1.155) +45); 		 
	 }
	 
	 static int getMappedFrequency50(int frequency) {
			 
			 if(frequency <= 100) 
				 
				 return  1;	 
			 
			 else if(frequency <= 900) 
				 
				 return (int) ((float)frequency/54*6-10) ;
				 
			  else 
				 
				 return (int) (Math.log(frequency)/ Math.log(1.14) +39); 		 
	}
	 
	 static int getMappedFrequency25(int frequency) {
			 
		 if(frequency < 100) 
			 return (frequency/10)+1;
		 
		 return (int) ((float)(frequency / 20) * 4.05 + 2) ;
	 
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
