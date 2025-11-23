package com.voiceRecognition.audio6.audioGramSaver;


import java.util.Arrays;

import com.voiceRecognition.audio0.main.VoiceRecognitionAppSetup;
import com.voiceRecognition.audio8.util.Debug;

public class BuildAudioAnalysisFilesPaths {
	
	private static String[] paths;
	private static String[] tempPaths;

	private static int debud_level_DEBUG = 5;
		
	public static String[] buildAudioAnalysisFilesPaths() {
		
		paths = null;
		tempPaths = null;
		
		if(VoiceRecognitionAppSetup.amplitudeGram) 		
			paths = addStringToPath(paths,"amplitudeGram") ;		
		
		if(VoiceRecognitionAppSetup.frequencyGram) 			
			paths = addStringToPath(paths,"frequencyGram") ;
		
		if((VoiceRecognitionAppSetup.amplitudeGram && VoiceRecognitionAppSetup.frequencyGram && VoiceRecognitionAppSetup.rawAudioData)) 		
			paths = addStringToPath(paths,"rawAudioData") ;
				
		if(VoiceRecognitionAppSetup.amplitudeGram && VoiceRecognitionAppSetup.frequencyGram && VoiceRecognitionAppSetup.spektrogram)
		
			paths = addStringToPath(paths,"spektroGram") ;
				
		if(VoiceRecognitionAppSetup.amplitudeGram && VoiceRecognitionAppSetup.frequencyGram && VoiceRecognitionAppSetup.mySpektrogram)

			paths = addStringToPath(paths,"mySpektrogram") ;
		
		if(VoiceRecognitionAppSetup.amplitudeGram && VoiceRecognitionAppSetup.frequencyGram && VoiceRecognitionAppSetup.mySpektrogram)

			paths = addStringToPath(paths,"buildMixedWaveStreamPoints") ;
				
		if(VoiceRecognitionAppSetup.amplitudeGram && VoiceRecognitionAppSetup.frequencyGram && VoiceRecognitionAppSetup.buildSequenceArray) 		
			paths = addStringToPath(paths,"buildSequenceArray");
			
		if(VoiceRecognitionAppSetup.amplitudeGram && VoiceRecognitionAppSetup.frequencyGram && VoiceRecognitionAppSetup.voiceRecognitionPointsData)			
			paths = addStringToPath(paths,"voicePointsRecognition");
		
		if(VoiceRecognitionAppSetup.amplitudeGram && VoiceRecognitionAppSetup.frequencyGram && VoiceRecognitionAppSetup.voiceRecognitionSlopesData)	
			paths = addStringToPath(paths,"voiceSlopesRecognition");
		
		if(VoiceRecognitionAppSetup.amplitudeGram && VoiceRecognitionAppSetup.frequencyGram && VoiceRecognitionAppSetup.voiceRecognitionAreaData)	
			paths = addStringToPath(paths,"voiceAreaRecognition");
		
		if(VoiceRecognitionAppSetup.amplitudeGram && VoiceRecognitionAppSetup.frequencyGram && VoiceRecognitionAppSetup.voiceRecognitionScanData)	
			paths = addStringToPath(paths,"voiceScanRecognition");
			
		if(VoiceRecognitionAppSetup.wave) 
			paths = addStringToPath(paths,"wave");
				
		Debug.debug(debud_level_DEBUG,"BuildAudioAnalysisFilesPaths paths: "+ Arrays.toString(paths));
		
			return paths;
	}

	public static String[] addStringToPath(String[] paths, String path) {
		
		if(paths != null && paths.length > 0) {
			
			tempPaths = new String[paths.length+1];
			
			for(int i = 0; i < paths.length; i++)
				tempPaths[i] = paths[i];
			
				tempPaths[paths.length] = path;
		} else 
			
			tempPaths = new String[] {path};
		
		Debug.debug(debud_level_DEBUG,"BuildAudioAnalysisFilesPaths addtask return tempPaths.length: "
			+tempPaths.length);
		
			return tempPaths;
	}
}
