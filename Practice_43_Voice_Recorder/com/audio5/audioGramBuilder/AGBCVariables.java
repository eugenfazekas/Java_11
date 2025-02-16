package com.audio5.audioGramBuilder;

import com.audio0.main.AppSetup;
import com.audio4.audioGramInitializer.AudioAnalysisThread;
import com.audio8.util.AudioBuilderUtil;
import com.audio8.util.Debug;

public class AGBCVariables {

	public static int amplitude;
	public static int posi_ampl_avgs;
	public static int nega_ampl_avgs;
	public static int positiveA;
	public static int negativeA;
	public static int amplitudeTotalBuffer;
	public static int amplitudeBufferCounter;
	public static int[] amplitudeWaveMap;
 	public static int amplitudeWaveMapCounter;
 	public static int percentAmplitudeResult;
 	
	public static int frequencyBufferDiveder;
	public static int frequency ; 
	public static int frequencyTotalBuffer;
	public static int frequencyBufferCounter;
	public static int[] frequencyWaveMap;
 	public static int frequencyWaveMapCounter;
 	public static int[] mappedFrequencyArray;	
 	
	public static int avg;
	public static int AVG_MILISEC_LENGTH; 
	public static int avgMilisecCounter;
		
	public static int pozitiveACounter;
	public static int negativeACounter;
	public static int skipCounter;
	public static int totalSampleCounter;
	public static int limit = 15;
	public static boolean newFrequency;
	public static int freqLengthCorrection;
	
	
		
	public static void resetVariables(int id) {
		
	    posi_ampl_avgs = 0;
	    nega_ampl_avgs = 0;
		positiveA = 1;
		negativeA = 0;
		
		amplitudeTotalBuffer = 0;
		amplitudeBufferCounter = 0;
		amplitudeWaveMap = new int[1000];
		amplitudeWaveMapCounter = 0;
		
		frequencyTotalBuffer = 0;
		frequencyBufferDiveder = 0 ;
		frequencyWaveMap = new int[1000]; 
	    frequencyWaveMapCounter = 0;
		mappedFrequencyArray = null;
		
		avgMilisecCounter = 0; 
		freqLengthCorrection = 0;
		AVG_MILISEC_LENGTH = AudioBuilderUtil.getBuffersLengthByMilisec(
			(int)AudioAnalysisThread.startedVoiceCheck.get(id).getAudioFormat().getSampleRate()
				,AppSetup.AUDIO_BUFFER_MILISEC_LENGTH);
		
		Debug.debug(2,"AGBCVariables initAmplitudeDetaisl! AVG_MILISEC_LENGTH: " +AVG_MILISEC_LENGTH);
	}
}


