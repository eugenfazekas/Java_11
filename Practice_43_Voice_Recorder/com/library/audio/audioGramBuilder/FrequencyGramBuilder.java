package com.library.audio.audioGramBuilder;

import java.util.Arrays;

import com.library.audio.audioGramInitializer.AudioGramInitializer;
import com.library.audio.audioGramSaver.SaveMultiAudioFeatures;
import com.library.util.AudioBuilderUtil;
import com.library.util.Debug;

public class FrequencyGramBuilder {
	
	 private static int positive = 0;
	 private static int negative = 0;
	 private static double  lastSV = 0;
	 private static int dataCounter = 0;
	 private static StringBuilder sb = new StringBuilder();	
	
	public static void buildFrequencyWaveMap() {
		
		if(!AudioGramInitializer.build) return;
		
	    AudioGramInitializer.waveCounter = 0;
		AudioGramInitializer.waveMap = new int[AudioGramInitializer.timeSequenceBuffers.length]; 
		AudioGramInitializer.AVG_MILISEC_LENGTH = AudioGramInitializer.getBuffersLengthByMilisec(4);
		AudioGramInitializer.tempBuffer = new int[AudioGramInitializer.AVG_MILISEC_LENGTH*2];
		AudioGramInitializer.avgMilisecCounter = 0;
		SaveMultiAudioFeatures.rawDataLines = new String[50000];
		dataCounter = 0;
		
		Debug.debug(3,"FrequencyGramBuilder AVG_MILISEC_LENGTH: "+AudioGramInitializer.AVG_MILISEC_LENGTH);
		
		for(int i = 0; i < AudioGramInitializer.timeSequenceBuffers.length; i++) {
			
			for(int j = 0; j < AudioGramInitializer.timeSequenceBuffers[i].length; j++) {
			
				if(AudioGramInitializer.timeSequenceBuffers[i][j]  > 0 && lastSV >=0 ) {
					positive++;
					lastSV = AudioGramInitializer.timeSequenceBuffers[i][j];
					sb.append(AudioGramInitializer.timeSequenceBuffers[i][j]+", ");
				}
				
				if(AudioGramInitializer.timeSequenceBuffers[i][j] <= 0) {
					negative++;
					lastSV = AudioGramInitializer.timeSequenceBuffers[i][j];
					sb.append(AudioGramInitializer.timeSequenceBuffers[i][j]+", ");
					
				}
				
				if(AudioGramInitializer.timeSequenceBuffers[i][j] > 0 && lastSV <0 ) {
					Debug.debug(5,"FrequencyGramBuilder buildFrequencyWaveMap i: "+i+ " j: "+j+" lastSV "+lastSV);
					AudioGramInitializer.addToBuffer(getFrequency(positive+negative));
					SaveMultiAudioFeatures.rawDataLines[dataCounter++]="Frequency: "+ (getFrequency(positive+negative))+" .-<<||||   "+ sb.toString() + ";\n";
					
					positive = 1;
					negative = 0;
					lastSV = (int)AudioGramInitializer.timeSequenceBuffers[i][j];		
					sb = new StringBuilder();
					sb.append(AudioGramInitializer.timeSequenceBuffers[i][j]+", ");
				}
				
				if(AudioGramInitializer.avgMilisecCounter == AudioGramInitializer.AVG_MILISEC_LENGTH ) {
					AudioGramInitializer.addToWaveMap();
					AudioGramInitializer.avgMilisecCounter = 0;
				}
				AudioGramInitializer.avgMilisecCounter++;
			}			
		}
		Debug.debug(2,"FrequencyGramBuilder waveMap.length: " + AudioGramInitializer.waveCounter + " Array: "+ Arrays.toString(AudioGramInitializer.waveMap));
	}
	 
	 public static void mainBuilder() {
		buildFrequencyWaveMap();
		AudioBuilderUtil.filterItems(2);
		SaveMultiAudioFeatures.frequencyArray = AudioGramInitializer.waveMap;
		buildMappedArray();		 
	 }
	 
	 private static void buildMappedArray() {
		 
		  SaveMultiAudioFeatures.frequencyMap = new int[AudioGramInitializer.waveMap.length];
		  for(int i = 0 ; i < AudioGramInitializer.waveMap.length; i++)
			  SaveMultiAudioFeatures.frequencyMap[i] = getMappedFrequency( AudioGramInitializer.waveMap[i]);
		  
		  Debug.debug(2,"FrequencyGramBuilder buildMappedArray: "+ Arrays.toString(SaveMultiAudioFeatures.frequencyMap));
	 }
	 	
	private static int getFrequency(int cycleSamples) {
		
		int freq = AudioGramInitializer.sampleRate / cycleSamples;
		Debug.debug(5,"1. freq: "+freq);
		return freq;
	}
	
	private static int getMappedFrequency(int frequency) {
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
}
