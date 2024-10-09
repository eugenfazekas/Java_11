package com.audio5.audioGramSaver;

import com.audio0.main.AppSetup;
import com.audio8.util.Debug;

public class BuildAudioAnalysisFilesPaths {
	
	private static String[] paths;
	private static String[] tempPaths;
		
	public static String[] buildAudioAnalysisFilesPaths() {
		
		if(AppSetup.amplitudeGram) {
			paths = addStringToPath(paths,"amplitudeGram") ;
		}
		
		if(AppSetup.frequencyGram) {
			paths = addStringToPath(paths,"frequencyGram") ;
		}
		
		if(AppSetup.amplitudeGram && AppSetup.frequencyGram && AppSetup.rawAudioData) {
			paths = addStringToPath(paths,"rawAudioData") ;
		}
		
		if(AppSetup.amplitudeGram && AppSetup.frequencyGram && AppSetup.spektrogram) {
			paths = addStringToPath(paths,"spektroGram") ;
		}
		
		if(AppSetup.amplitudeGram && AppSetup.frequencyGram && AppSetup.mySpektrogram) {
			paths = addStringToPath(paths,"mySpektrogram") ;
		}
		
		if(AppSetup.amplitudeGram && AppSetup.frequencyGram && AppSetup.buildSequenceArray) {
			paths = addStringToPath(paths,"buildSequenceArray") ;
		}
		
		if(AppSetup.amplitudeGram && AppSetup.frequencyGram && AppSetup.voiceRecognitionData) {
			paths = addStringToPath(paths,"voiceRecognition") ;
		}
		
		if(AppSetup.wave) {
			paths = addStringToPath(paths,"wave") ;
		}
			return paths;
	}

	public static String[] addStringToPath(String[] paths, String path) {
		
		if(paths != null && paths.length > 0) {
			tempPaths = new String[paths.length+1];
			for(int i = 0; i < paths.length; i++)
				tempPaths[i] = paths[i];
			tempPaths[paths.length] = path;
		}	
		else tempPaths = new String[] {path};
		
		Debug.debug(5,"AudioAnalysisPhaseBuilder addtask return tempPaths.length: "+tempPaths.length);
			return tempPaths;
	}
}
