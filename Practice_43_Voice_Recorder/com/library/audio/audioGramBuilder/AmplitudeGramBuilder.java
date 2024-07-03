package com.library.audio.audioGramBuilder;

import java.util.Arrays;

import com.library.audio.audioGramInitializer.AudioGramInitializer;
import com.library.audio.audioGramSaver.SaveMultiAudioFeatures;
import com.library.util.AudioBuilderUtil;
import com.library.util.Debug;

public class AmplitudeGramBuilder {
	 
	 
	 private static int amplitude = 0;
	 private static int lastSample; 


	 public static void buildAmplitudeWaveMap() {
			
			if(!AudioGramInitializer.build)
				return;
			
			AudioGramInitializer.waveCounter = 0;
			AudioGramInitializer.waveMap = new int[(AudioGramInitializer.timeSequenceBuffers.length*2)]; 
			AudioGramInitializer.AVG_MILISEC_LENGTH = AudioGramInitializer.getBuffersLengthByMilisec(4);
			AudioGramInitializer.tempBuffer = new int[AudioGramInitializer.AVG_MILISEC_LENGTH];
			AudioGramInitializer.avgMilisecCounter =0; 
			 
			for(int i = 0; i < AudioGramInitializer.timeSequenceBuffers.length; i++) {
				
				for(int j = 0; j < AudioGramInitializer.timeSequenceBuffers[i].length; j++) {
					
					//System.out.println("i: "+i+" j: "+j+ " 1. lastSample:" +lastSample+ " [i][j]: "+AnalysisInitializer.timeSequenceBuffers[i][j] );
					
					if(Math.abs(AudioGramInitializer.timeSequenceBuffers[i][j]) > amplitude) {
						amplitude = AudioGramInitializer.timeSequenceBuffers[i][j];
					}
					
					if(AudioGramInitializer.timeSequenceBuffers[i][j] > 0 && lastSample < 0   ) {
						//System.out.println("i: "+i+" j: "+j+ " 2. lastSample:" +lastSample +" [i][j]: "+AnalysisInitializer.timeSequenceBuffers[i][j]+" , buufercounter: " +AnalysisInitializer.bufferCounter+ " avgMilisecCounter: "+AnalysisInitializer.avgMilisecCounter);
						AudioGramInitializer.addToBuffer((int) (amplitude *100 /32736*AudioGramInitializer.sharpnessGrower));
						lastSample = 0;
						amplitude = 0;
					}
					lastSample = AudioGramInitializer.timeSequenceBuffers[i][j];
					AudioGramInitializer.avgMilisecCounter++;
					
					if(AudioGramInitializer.avgMilisecCounter == AudioGramInitializer.AVG_MILISEC_LENGTH ) {
						AudioGramInitializer.addToWaveMap();
						AudioGramInitializer.avgMilisecCounter = 0;
					}
				}			
			}
					
				//AudioBuilderUtil.filterItems(1);
				Debug.debug(5,"AmplitudeGramBuilder buildAmplitudeWaveMap 4.waveMap.length: "+AudioGramInitializer.waveMap.length + " waveCounter: "+AudioGramInitializer.waveCounter );
	 }
	 
	 public static void mainBuilder() {
		buildAmplitudeWaveMap();
		AudioBuilderUtil.filterItems(2);
		SaveMultiAudioFeatures.amplitudeMap = AudioGramInitializer.waveMap;
		Debug.debug(3,"AmplitudeGramBuilder Array.length:"+AudioGramInitializer.waveMap.length+ " Wavemap array: " + Arrays.toString(AudioGramInitializer.waveMap));
	 }
}
