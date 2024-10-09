package com.audio4.audioGramBuilder;

import com.audio0.main.AppSetup;
import com.audio3.audioGramInitializer.AudioAnalysisInitializer;
import com.audio8.util.Debug;

public class AmplitudeDetails {
	 
	 public static int amplitude;
	 public static int posi_ampl_avgs = 0;
	 public static int nega_ampl_avgs = 0;
	 public static int avg = 0;
	 public static int lastSample; 
	 public static int amplitudeTotalBuffer;
	 public static int amplitudeBufferCounter = 0;
	 public static int[] amplitudeWaveMap;
 	 public static int amplitudeWaveMapCounter;
	 public static int A_AVG_MILISEC_LENGTH; 
	 public static int aavgMilisecCounter;

	 public static void initAmplitudeWaveMapDetails() {
			
		    posi_ampl_avgs = 0;
		    nega_ampl_avgs = 0;
			amplitudeTotalBuffer = 0;
			amplitudeBufferCounter = 0;
			amplitudeWaveMap = new int[1000];
			amplitudeWaveMapCounter = 0;
			aavgMilisecCounter = 0; 
			A_AVG_MILISEC_LENGTH = AudioAnalysisInitializer.getBuffersLengthByMilisec(AudioAnalysisInitializer.sampleRate, AppSetup.AMPLITUDE_BUFFER_MILISEC_LENGTH);
			
			Debug.debug(2,"AmplitudeGramBuilder initAmplitudeDetaisl!");
	 }
	 
	 public static void addToAmplitudeBuffer(int bufferValue) {
		 
		 amplitudeTotalBuffer += bufferValue;
		 amplitudeBufferCounter++;
		 Debug.debug(5,"AmplitudeGramBuilder addToBuffer bufferValue: "+bufferValue + ",  amplitudeBuffer: "+(amplitudeTotalBuffer) + ",amplitudeBufferCounter " +amplitudeBufferCounter);
	 }
	 public static void addToAmplitudeWaveMap(int amplitudebuffer, int amplitudebufferCounter ) {
		 
		 if(amplitudebufferCounter == 0 )
			 return;
	 
		 avg = amplitudebuffer / amplitudebufferCounter ;		
		// amplitudeWaveMap[amplitudeWaveMapCounter++] =  (int) (avg* 100 /32736* AudioAnalysisInitializer.sharpnessGrower); 
		 
		 amplitudeWaveMap[amplitudeWaveMapCounter++] = getPercentAmplitude(AudioAnalysisInitializer.averageAmplitude,avg);
		
		 Debug.debug(5,"AmplitudeGramBuilder addToAmplitudeWaveMap amplitudeBuffer total: "+amplitudebuffer+ " / bufferCounter "+ amplitudebufferCounter + " avg: "+avg+ " ,AnalysisInitializer.waveCounter: "+amplitudeWaveMapCounter+", amplitude: "+amplitudeWaveMap[amplitudeWaveMapCounter-1] +" SharpG: "+AudioAnalysisInitializer.sharpnessGrower);	
		 amplitudeTotalBuffer = 0;
		 amplitudeBufferCounter = 0;
	 }
	 
		public static int getPercentAmplitude(int average,int  input) {
			
			return input/((average/100)* AppSetup.AMPLITUDE_AVERAGE_CORRECTION_KONSTANT); 
		}
}
