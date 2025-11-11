package com.audio5.audioGramBuilder;

import com.audio4.audioGramInitializer.mainInit.AudioAnalysisThread;
import com.audio8.util.Debug;

public class MiscellaneousData {

	static StringBuilder stringBuilder;
	static String[] rawAudioData;
	static int[] bestDBMatchArray;
	static int sequenceStartIndex;
	static int sequenceEndIndex;

	static int voiceCheckStartIndex;
	static int[] tempSlopeArray;
	static int[] tempArray;
	
	static int rawDataCounter;
	static int sequenceDataCounter;
	
	static int[] mixedWaveStreamPointsIntArray;
	static String[] MixedWaveStreamPointsStringArray;
	static String buildSequenceStringArray[];
		
	static int voiceRecognitionPointsIntArray[];
	static String voiceRecognitionPointsStringArray;
	
	static int voiceRecognitionSlopesIntArray;
	static String voiceRecognitionSlopesStringArray;
		
	static int[][][] voiceRecognitionAreaArrays;
	static String voiceRecognitionStringAreaArray;
	
	static String buildVoiceRecognitionScanString[];
	
	static int[] temp;
	static int tempCounter;
	
	private static int debug_level_INFO = 5;
	
	static void initMiscellaneousDataDetails(int id) {
		
		rawAudioData = new String[(int)
		    AudioAnalysisThread.startedVoiceCheck.get(id).getAudioFormat().getSampleRate()];
		rawDataCounter = 0;
		
		buildSequenceStringArray = new String [300];
		sequenceDataCounter = 0;
		voiceCheckStartIndex = 0;

		Debug.debug(debug_level_INFO,"MiscellaneousData initFrequencyWaveDetails!");
	}
		
	static void buildVoiceRecognitionPointsArray(int id) {
		
		stringBuilder = new StringBuilder();
		int[][] points = new int[2][];
				
		points = VoiceRecognitionPointsBuilder.mainPointsBuilder(AudioAnalysisThread.startedVoiceCheck.get(id)
			.getFrequencyWaveMap(), AudioAnalysisThread.startedVoiceCheck.get(id)
			.getAmplitudeWaveMap(), AudioAnalysisThread.startedVoiceCheck.get(id).getVoiceRecognitionBorders());
		
		stringBuilder.append(StringArrayUtil.buildIntArrayToString(points[0]));
		
		stringBuilder.append(";");
			
		stringBuilder.append(StringArrayUtil.buildIntArrayToString(points[1]));
		
		AudioAnalysisThread.startedVoiceCheck.get(id).setVoiceReconitionCheckPointsArray(points);
		AudioAnalysisThread.startedVoiceCheck.get(id).setVoiceRecognitionPointsArray(stringBuilder.toString());	
	}
	
	static void buildVoiceRecognitionSlopesArray(int id) {
		
		stringBuilder = new StringBuilder();
		int[][] slopes = new int[2][];
		
		slopes[0] = VoiceRecognitionSlopeBuilder.mainSlopeBuilder(AudioAnalysisThread.startedVoiceCheck.get(id)
			.getAmplitudeWaveMap(),AudioAnalysisThread.startedVoiceCheck.get(id).getVoiceRecognitionBorders());
				
		slopes[1] = VoiceRecognitionSlopeBuilder.mainSlopeBuilder(AudioAnalysisThread.startedVoiceCheck.get(id)
			.getFrequencyWaveMap(),AudioAnalysisThread.startedVoiceCheck.get(id).getVoiceRecognitionBorders());		

		stringBuilder.append(StringArrayUtil.buildIntArrayToString(slopes[0]));
		
		stringBuilder.append(";");
			
		stringBuilder.append(StringArrayUtil.buildIntArrayToString(slopes[1]));

		AudioAnalysisThread.startedVoiceCheck.get(id).setVoiceReconitionCheckSlopesArray(slopes);
		AudioAnalysisThread.startedVoiceCheck.get(id).setVoiceRecognitionSlopesArray(stringBuilder.toString());
			
		Debug.debug(debug_level_INFO,"MiscellaneousData buildVoiceRecognitionSlopeArray 1.0: "+stringBuilder.toString());
	}

	static void buildVoiceRecognitionAreaArray(int id) {
		
		stringBuilder = new StringBuilder();
		voiceRecognitionAreaArrays = new int[1][][];
				
		voiceRecognitionAreaArrays = VoiceRecognitionAreaBuilder.mainAreaBuilder(
			AudioAnalysisThread.startedVoiceCheck.get(id).getAmplitudeWaveMap(),
			AudioAnalysisThread.startedVoiceCheck.get(id).getFrequencyWaveMap(),
			AudioAnalysisThread.startedVoiceCheck.get(id).getVoiceRecognitionBorders()
		);
		
		Debug.debug(debug_level_INFO,"MiscellaneousData voiceRecognitionAreaArrays int READY! ");
		stringBuilder.append(VoiceRecognitionBuilderUtil.buildVoiceRecognitionAreaString(voiceRecognitionAreaArrays));

		
		AudioAnalysisThread.startedVoiceCheck.get(id).setVoiceReconitionCheckAreaArray(voiceRecognitionAreaArrays);
		
		AudioAnalysisThread.startedVoiceCheck.get(id).setVoiceRecognitionAreaArray(stringBuilder.toString());	
		Debug.debug(debug_level_INFO,"MiscellaneousData voiceRecognitionAreaArrays String READY! ");
	}
	
	static void sequenceSetHighestAmplitude(int amplitude) {
		
		if(AGBCVariables.amplitudeHighest1 < amplitude) {
			
			AGBCVariables.amplitudeHighest2 = AGBCVariables.amplitudeHighest1; 
			AGBCVariables.amplitudeHighest1 = amplitude;  
		}
	};
	
	static int sequenceGetAmplitude() {
		
		return (AGBCVariables.amplitudeHighest2 + AGBCVariables.amplitudeHighest1) / 2;
	}
	
	static void buildVoiceRecognitionBorders(int id) {
		
		AudioAnalysisThread.startedVoiceCheck.get(id).setVoiceRecognitionBorders(
			VoiceRecognitionBuilderUtil.buildSequenceBorders(
			AudioAnalysisThread.startedVoiceCheck.get(id).getFrequencyWaveMap(),10 , 10));
	}
	
	static void buildVoiceRecognitionScanString(int id) {
		
		temp = new int[(AudioAnalysisThread.startedVoiceCheck.get(id).getVoiceRecognitionBorders()[1]
				- AudioAnalysisThread.startedVoiceCheck.get(id).getVoiceRecognitionBorders()[0])*4];
		tempCounter = 0; 
		
		int start = AudioAnalysisThread.startedVoiceCheck.get(id).getVoiceRecognitionBorders()[0] * 2 > 2 ? 
				AudioAnalysisThread.startedVoiceCheck.get(id).getVoiceRecognitionBorders()[0] * 2 : 2;
		
		int end = AudioAnalysisThread.startedVoiceCheck.get(id).getVoiceRecognitionBorders()[1] * 2 < 
				AudioAnalysisThread.startedVoiceCheck.get(id).getMySpektrogramMap().length - 3 ? 
				AudioAnalysisThread.startedVoiceCheck.get(id).getVoiceRecognitionBorders()[1] * 2 : 
				AudioAnalysisThread.startedVoiceCheck.get(id).getVoiceRecognitionBorders()[1] - 3;
		Debug.debug(debug_level_INFO, "MiscellaneousData buildVoiceRecognitionScanString start: " +start+ " end: " +end 
				+", temp.length "+temp.length+", MySpektrogram length:"
				+ AudioAnalysisThread.startedVoiceCheck.get(id).getMySpektrogramMap().length); 
		
		for(int i = start; i < end; i = i + 2) {
			
			temp[tempCounter++] = AudioAnalysisThread.startedVoiceCheck.get(id).getMySpektrogramMap()[i];
			temp[tempCounter++] = AudioAnalysisThread.startedVoiceCheck.get(id).getMySpektrogramMap()[i+1];
			
			temp[tempCounter++] = (int) Math.toDegrees(Math.atan2(
					AudioAnalysisThread.startedVoiceCheck.get(id).getMySpektrogramMap()[i+2] 
					- AudioAnalysisThread.startedVoiceCheck.get(id).getMySpektrogramMap()[i-2], 2));
			temp[tempCounter++] = (int) Math.toDegrees(Math.atan2(
					AudioAnalysisThread.startedVoiceCheck.get(id).getMySpektrogramMap()[i+3] 
					- AudioAnalysisThread.startedVoiceCheck.get(id).getMySpektrogramMap()[i-1], 2));		
		}	
		
		AudioAnalysisThread.startedVoiceCheck.get(id).setVoiceReconitionCheckScanArray(temp);
		
		AudioAnalysisThread.startedVoiceCheck.get(id).setVoiceRecognitionScanArray(
			StringArrayUtil.buildIntArrayToString(temp));
	}
}
