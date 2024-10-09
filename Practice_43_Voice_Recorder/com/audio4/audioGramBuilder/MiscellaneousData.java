package com.audio4.audioGramBuilder;

import com.audio0.main.AppSetup;
import com.audio3.audioGramInitializer.AudioAnalysisInitializer;
import com.audio8.util.Debug;

public class MiscellaneousData {

	public static String voiceRecognitionArray[];
	public static String buildSequenceArray[];
	public static int dataCounter;
	public static int [] waveDetails;
	public static StringBuilder sb = new StringBuilder();	
	public static String[] rawAudioData;
	
	public static void initMiscellaneousDataDetails() {
		
		rawAudioData = new String[AudioAnalysisInitializer.audioSamples.length];
		voiceRecognitionArray= new String [2000];
		buildSequenceArray = new String [2000];
		dataCounter = 0;
		waveDetails = new int[4];
		sb = new StringBuilder();
		Debug.debug(2,"MiscellaneousData initFrequencyWaveDetails!");
	} 
	
	public static void getWaveDetails(int pos_avg , int pos_counter,int neg_avg , int neg_counter) {
		 
		 pos_counter = pos_counter > 0 ?   pos_counter : 1 ;
		 neg_counter = neg_counter > 0 ?   neg_counter : 1 ;
		 
		 waveDetails = new int[] {(pos_avg/pos_counter),pos_counter,(neg_avg/neg_counter),neg_counter};
		 
		 waveDetails[0] = (int) (waveDetails[0] > 10 ?  waveDetails[0] * AppSetup.BUILD_SEQUENCE_AMPLITUDE_CORRECTION_KONSTANT : 10);
		 waveDetails[2] = (int) (waveDetails[2] > 10 ?  waveDetails[2] * AppSetup.BUILD_SEQUENCE_AMPLITUDE_CORRECTION_KONSTANT : 10);

	 }
}
