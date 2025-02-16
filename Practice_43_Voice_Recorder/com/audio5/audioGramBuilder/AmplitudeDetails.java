package com.audio5.audioGramBuilder;

import com.audio0.main.AppSetup;
import com.audio4.audioGramInitializer.AudioAnalysisThread;
import com.audio8.util.Debug;

public class AmplitudeDetails {
	 
//	public static int amplitude;
//	public static int posi_ampl_avgs = 0;
//	public static int nega_ampl_avgs = 0;
//	public static int avg = 0;
//	public static int lastSample; 
//	public static int amplitudeTotalBuffer;
//	public static int amplitudeBufferCounter = 0;
//	public static int[] amplitudeWaveMap;
// 	public static int amplitudeWaveMapCounter;
//	public static int A_AVG_MILISEC_LENGTH; 
//	public static int aavgMilisecCounter;
//	public static int percentAmplitudeResult;
//
//	public static void initAmplitudeWaveMapDetails(int id) {
//			
//	    posi_ampl_avgs = 0;
//	    nega_ampl_avgs = 0;
//		amplitudeTotalBuffer = 0;
//		amplitudeBufferCounter = 0;
//		amplitudeWaveMap = new int[1000];
//		amplitudeWaveMapCounter = 0;
//		aavgMilisecCounter = 0; 
//		
//		A_AVG_MILISEC_LENGTH = AudioBuilderUtil.getBuffersLengthByMilisec(
//			(int)AudioAnalysisThread.startedVoiceCheck.get(id).getAudioFormat().getSampleRate()
//				,AppSetup.AMPLITUDE_BUFFER_MILISEC_LENGTH);
//		
//		Debug.debug(2,"AmplitudeDetails initAmplitudeDetaisl!");
//	}
	 
	public static void addToAmplitudeBuffer(int bufferValue,int sampleCount) {
					 
		AGBCVariables.amplitudeTotalBuffer += bufferValue;
		AGBCVariables.amplitudeBufferCounter++;
			
			Debug.debug(3,"AmplitudeDetails addToBuffer bufferValue: "+bufferValue
				+ ", amplitudeBuffer: "+(AGBCVariables.amplitudeTotalBuffer) + ",amplitudeBufferCounter " 
				+AGBCVariables.amplitudeBufferCounter);

	}
	 
	public static void addToAmplitudeWaveMap(int amplitudebuffer, int amplitudebufferCounter, int id ) {
		 
		if(amplitudebufferCounter > 0) {
		
			AGBCVariables.avg = amplitudebuffer / amplitudebufferCounter ;		
			 
			AGBCVariables.amplitudeWaveMap[AGBCVariables.amplitudeWaveMapCounter++] = 
				getPercentAmplitude(AudioAnalysisThread
						.startedVoiceCheck.get(id).getAverageAmplitude(),AGBCVariables.avg);
	
			 Debug.debug(5,"AmplitudeDetails addToAmplitudeWaveMap amplitudeBuffer total: "
				+ amplitudebuffer+ " / bufferCounter "+ amplitudebufferCounter + " avg: "+AGBCVariables.avg
				+ " ,AnalysisInitializer.waveCounter: "+AGBCVariables.amplitudeWaveMapCounter+", amplitude: "
				+ AGBCVariables.amplitudeWaveMap[AGBCVariables.amplitudeWaveMapCounter-1]);	
		}
		
		AGBCVariables.amplitudeTotalBuffer = 0;
		AGBCVariables.amplitudeBufferCounter = 0;
	}
	 
	public static int getPercentAmplitude(int average,int  input) {
			
		AGBCVariables.percentAmplitudeResult 
				=(int) (input/((average/100)* AppSetup.AMPLITUDE_AVERAGE_CORRECTION_KONSTANT))+1; 
		
		Debug.debug(5,"AmplitudeDetails getPercentAmplitude avg: "+average + ", input: "+input 
				+ ", percentAmplitudeResult: "+AGBCVariables.percentAmplitudeResult);
		
			return AGBCVariables.percentAmplitudeResult;
	}
		
	public static void addToAmplitudeWaveMapSimple(int inputValue) {
		
		AGBCVariables.amplitudeWaveMap[AGBCVariables.amplitudeWaveMapCounter++] = inputValue;
	}
}
