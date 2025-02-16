package com.audio5.audioGramBuilder;

import com.audio0.main.AppSetup;
import com.audio4.audioGramInitializer.AudioAnalysisThread;
import com.audio5.recognition.slope.Slope;
import com.audio5.recognition.slope.SlopeBuilder;
import com.audio8.util.Debug;

public class MiscellaneousData {

	public static int voiceRecognitionPointsintArray[];
	public static String voiceRecognitionPointsStringArray[];
	
	public static int voiceRecognitionSlopesIntArray;
	public static String voiceRecognitionSlopesStringArray;
	
	public static int buildSequenceIntArray[];
	public static String buildSequenceStringArray[];
	
	public static Slope[] tempSlopeArray;
	public static int[] tempArray;
	public static int rawDataCounter;
	public static int sequenceDataCounter;
	public static int [] waveDetails;
	public static StringBuilder sb = new StringBuilder();	
	public static String[] rawAudioData;
	public static int[] bestDBMatchArray;
	private static StringBuilder stringBuilder;
	
	public static void initMiscellaneousDataDetails(int id) {
		
		rawAudioData = new String[(int)
		    AudioAnalysisThread.startedVoiceCheck.get(id).getAudioFormat().getSampleRate()];
		buildSequenceStringArray = new String [2000];
		rawDataCounter = 0;
		sequenceDataCounter = 0;
		waveDetails = new int[4];
		sb = new StringBuilder();
		
		Debug.debug(2,"MiscellaneousData initFrequencyWaveDetails!");
	}
	
	public static void buildVoiceRecognitionPointsArray(int[] spektrogramMap) {
		
		if(spektrogramMap == null) return;
		
		rawDataCounter = 0;
		sequenceDataCounter = 0;
		
		voiceRecognitionPointsintArray = new int[(spektrogramMap.length/2)*3];
		voiceRecognitionPointsStringArray = new String[(spektrogramMap.length/2)*3];
		
		Debug.debug(5,"MiscellaneousData buildVoiceRecognitionPointsArray "
			+ "spektrogramMap.length: "+spektrogramMap.length );
		
		for(int i = 0; i < spektrogramMap.length ; i = i+2) {

			voiceRecognitionPointsintArray[rawDataCounter++] = spektrogramMap[i];
			voiceRecognitionPointsintArray[rawDataCounter++] = spektrogramMap[i+1];
			voiceRecognitionPointsintArray[rawDataCounter++] =
					(spektrogramMap[i+1] *100) / spektrogramMap[i];
			
			voiceRecognitionPointsStringArray[sequenceDataCounter++] = String.valueOf(spektrogramMap[i]);
			voiceRecognitionPointsStringArray[sequenceDataCounter++] = String.valueOf(spektrogramMap[i+1]);
			voiceRecognitionPointsStringArray[sequenceDataCounter++] =
								String.valueOf((spektrogramMap[i+1] *100) / spektrogramMap[i]);
			
			Debug.debug(5,"MiscellaneousData buildVoiceRecognitionPointsArray dataCounter: "
				+rawDataCounter);
		}
	}
	
	public static void buildVoiceRecognitionSlopesArray(int id) {
		
		rawDataCounter = 0;
		stringBuilder = new StringBuilder();
		int[][] slopes = new int[2][];
		
		tempSlopeArray = SlopeBuilder.mainSlopeBuilder(AudioAnalysisThread.startedVoiceCheck.get(id)
				.getAmplitudeWaveMap());
		
		tempArray = new int[tempSlopeArray.length*4];
		
		for(int i = 0; i < tempSlopeArray.length; i++) {
			
			tempArray[rawDataCounter++] = tempSlopeArray[i].gethPosition();
			tempArray[rawDataCounter++] = tempSlopeArray[i].getvPosition();
			tempArray[rawDataCounter++] = tempSlopeArray[i].getAvgSlopedirection();
			tempArray[rawDataCounter++] = tempSlopeArray[i].getMainLength();
			
			stringBuilder.append(String.valueOf(tempSlopeArray[i].gethPosition()) + ",");
			stringBuilder.append(String.valueOf(tempSlopeArray[i].getvPosition())+ ",");
			stringBuilder.append(String.valueOf(tempSlopeArray[i].getAvgSlopedirection())+ ",");
			stringBuilder.append(String.valueOf(tempSlopeArray[i].getMainLength())+ ",");	
		}
			stringBuilder.deleteCharAt(stringBuilder.length()-1);
			slopes[0] = tempArray;
			
			stringBuilder.append(";");
			
			rawDataCounter = 0;
			tempSlopeArray = SlopeBuilder.mainSlopeBuilder(AudioAnalysisThread.startedVoiceCheck.get(id)
					.getFrequencyWaveMap());
			tempArray = new int[tempSlopeArray.length*4];
			
			for(int i = 0; i < tempSlopeArray.length; i++) {
				
				tempArray[rawDataCounter++] = tempSlopeArray[i].gethPosition();
				tempArray[rawDataCounter++] = tempSlopeArray[i].getvPosition();
				tempArray[rawDataCounter++] = tempSlopeArray[i].getAvgSlopedirection();
				tempArray[rawDataCounter++] = tempSlopeArray[i].getMainLength();
				
				stringBuilder.append(String.valueOf(tempSlopeArray[i].gethPosition()) + ",");
				stringBuilder.append(String.valueOf(tempSlopeArray[i].getvPosition())+ ",");
				stringBuilder.append(String.valueOf(tempSlopeArray[i].getAvgSlopedirection())+ ",");
				stringBuilder.append(String.valueOf(tempSlopeArray[i].getMainLength())+ ",");	
			}
			
			stringBuilder.deleteCharAt(stringBuilder.length()-1);
			slopes[1] = tempArray;
			
			AudioAnalysisThread.startedVoiceCheck.get(id).setVoiceReconitionCheckSlopesArray(slopes);
			AudioAnalysisThread.startedVoiceCheck.get(id).setVoiceRecognitionSlopesArray(stringBuilder.toString());
			
		Debug.debug(3,"MiscellaneousData buildVoiceRecognitionSlopeArray 1.0: "+stringBuilder.toString());
	}
	
	public static void getWaveDetails(int pos_avg , int pos_counter,int neg_avg , int neg_counter) {
		 
		 pos_counter = pos_counter > 0 ?   pos_counter : 1 ;
		 neg_counter = neg_counter > 0 ?   neg_counter : 1 ;
		 
		 waveDetails = new int[] {(pos_avg/pos_counter),pos_counter,(neg_avg/neg_counter),neg_counter};
		 
		 waveDetails[0] = (int) (waveDetails[0] > 10 ?  waveDetails[0]
				 * AppSetup.BUILD_SEQUENCE_AMPLITUDE_CORRECTION_KONSTANT : 10);
		 
		 waveDetails[2] = (int) (waveDetails[2] > 10 ?  waveDetails[2]
				 * AppSetup.BUILD_SEQUENCE_AMPLITUDE_CORRECTION_KONSTANT : 10);
	 }
}
