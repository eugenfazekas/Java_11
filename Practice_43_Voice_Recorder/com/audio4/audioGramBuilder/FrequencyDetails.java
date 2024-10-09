package com.audio4.audioGramBuilder;

import java.util.Arrays;

import com.audio0.main.AppSetup;
import com.audio3.audioGramInitializer.AudioAnalysisInitializer;
import com.audio8.util.Debug;

public class FrequencyDetails {
	
	 static int positive = 1;
	 static int negative = 0;
	 public static int dataCounter = 0;
	 public static int avg = 0;
	 public static int frequency = 0;
	 public static int F_AVG_MILISEC_LENGTH; 
	 public static int favgMilisecCounter;
	 public static int frequencyTotalBuffer;
	 public static int frequencyBufferCounter = 0;
	 public static int[] frequencyWaveMap;
 	 public static int frequencyWaveMapCounter;
 	// static int[] milisecFrequencyArray;
 	 public static int[] mappedFrequencyArray;
	 
	public static void initFrequencyWaveMapDetails() {
			
		positive = 1;
		negative = 0;
		frequencyTotalBuffer = 0;
		frequencyBufferCounter = 0;
		frequencyWaveMap = new int[1000]; 
	    frequencyWaveMapCounter = 0;
		mappedFrequencyArray = null;
		favgMilisecCounter = 0;		
		F_AVG_MILISEC_LENGTH = AudioAnalysisInitializer.getBuffersLengthByMilisec(AudioAnalysisInitializer.sampleRate,AppSetup.FREQUENCY_BUFFER_MILISEC_LENGTH);
			
		Debug.debug(3,"FrequencyGramBuilder initFrequencyWaveDetails!");
	}
	 
	 public static void addToFrequnecyBuffer(int bufferValue) {
		 
		 frequencyTotalBuffer += bufferValue;
		 frequencyBufferCounter++;
		 Debug.debug(5,"FrequencyGramBuilder addToBuffer bufferValue: "+bufferValue + ",  frequencyBuffer: "+frequencyTotalBuffer + ", frequencyBufferCounter: "+frequencyBufferCounter +", i: "+MultiAnalysisBuilder.i);
	 }
	  
	 public static void addToFrequencyWaveMap(int frequencybuffer, int frequencybufferCounter) {
		 
		 if(frequencybufferCounter == 0 )
			 return;
	 
		 avg = frequencybuffer / frequencybufferCounter ;			 
		 frequencyWaveMap[frequencyWaveMapCounter++] =  avg; 

		 Debug.debug(5,"FrequencyGramBuilder addToFrequencyWaveMap frequencyBuffer: "+frequencybuffer+ " / bufferCounter "+ frequencybufferCounter + " avg: "+avg+ " ,AnalysisInitializer.waveCounter: "+frequencyWaveMapCounter+", frequency: "+frequencyWaveMap[frequencyWaveMapCounter-1]);	
		 frequencyTotalBuffer = 0;
		 frequencyBufferCounter = 0;
	 }
	 
	 public static void buildMappedArray() {
		 
		  mappedFrequencyArray = new int[frequencyWaveMap.length];
		  for(int i = 0 ; i < frequencyWaveMap.length; i++)
			  mappedFrequencyArray[i] = getMappedFrequency( frequencyWaveMap[i]);
		  
		  Debug.debug(3,"FrequencyGramBuilder buildMappedArray: "+ Arrays.toString(mappedFrequencyArray));
	 }
	 	
	public static int getFrequency(int sapmleRate , int cycleSamples) {
		
		int freq = sapmleRate / cycleSamples;
		Debug.debug(5,"1. freq: "+freq);
		return freq;
	}
	
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
	
	
	/*
	 * 
	 * 	  static int getMappedFrequency(int frequency) {
         int mappedFrequency = 0;
         
         if(frequency <= 200) {
        	 mappedFrequency = getLoWFrequencyMap(frequency); 
        	 Debug.debug(5,"getMappedFrequency getLoWFrequencyMap called ");
         }
         
         if(frequency > 200 && frequency< 2000) {
        	 mappedFrequency = getMiddleFrequencyMap(frequency); 
        	 Debug.debug(5,"getMappedFrequency getMiddleFrequencyMap called ");
         }
         
         if( frequency>= 2000) {
        	 mappedFrequency = getHighFrequencyMap(frequency); 
        	 Debug.debug(5,"getMappedFrequency getHighFrequencyMap called ");
         }
	         
		return mappedFrequency;
	}

	private static int getLoWFrequencyMap(int frequency) {
		
		return (int)(frequency * 33.3)/200;
	}	
	
	private static int getMiddleFrequencyMap(int frequency) {
		
		return (int)((((frequency-200) * 33.3)/1800)+34);
	}
	
	private static int getHighFrequencyMap(int frequency) {
		
		return (int)((((frequency-2000) * 33.3)/10000)+67);
	}
	 * 
	static int getMappedFrequency(int frequency) {
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
		
		return (int)(frequency * 33.3)/100;
	}	
	
	private static int getMiddleFrequencyMap(int frequency) {
		
		return (int)((((frequency-100) * 34)/1000)+34);
	}
	
	private static int getHighFrequencyMap(int frequency) {
		
		return (int)((((frequency-1000) * 33.3)/10000)+67);
	}


	 */
}
