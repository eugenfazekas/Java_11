package com.audio6.audioGramSaver;


import com.audio0.main.AppSetup;
import com.audio8.util.Debug;

public class BuildAudioAnalysisFilesPaths {
	
	private static String[] paths;
	private static String[] tempPaths;
		
	public static String[] buildAudioAnalysisFilesPaths() {
		
		paths = null;
		tempPaths = null;
		
		if(AppSetup.amplitudeGram || AppSetup.multiGram) 		
			paths = addStringToPath(paths,"amplitudeGram") ;
		
		
		if(AppSetup.frequencyGram  || AppSetup.multiGram) 			
			paths = addStringToPath(paths,"frequencyGram") ;
		
		
		if((AppSetup.amplitudeGram && AppSetup.frequencyGram && AppSetup.rawAudioData) 
			|| (AppSetup.multiGram && AppSetup.rawAudioData)) 		
			paths = addStringToPath(paths,"rawAudioData") ;
		
		
		if((AppSetup.amplitudeGram && AppSetup.frequencyGram && AppSetup.spektrogram)
			|| AppSetup.multiGram && AppSetup.spektrogram) 		
			paths = addStringToPath(paths,"spektroGram") ;
		
		
		if((AppSetup.amplitudeGram && AppSetup.frequencyGram && AppSetup.mySpektrogram)
			|| AppSetup.multiGram && AppSetup.mySpektrogram) 
			
			paths = addStringToPath(paths,"mySpektrogram") ;
		
		
		if((AppSetup.amplitudeGram && AppSetup.frequencyGram && AppSetup.buildSequenceArray) 
			||(AppSetup.multiGram && AppSetup.buildSequenceArray)) 		
			paths = addStringToPath(paths,"buildSequenceArray");
		
		
		if((AppSetup.amplitudeGram && AppSetup.frequencyGram && AppSetup.voiceRecognitionData)
			||(AppSetup.multiGram && AppSetup.voiceRecognitionData)) 			
			paths = addStringToPath(paths,"voicePointsRecognition");
		
		if((AppSetup.amplitudeGram && AppSetup.frequencyGram && AppSetup.voiceRecognitionData)
			||(AppSetup.multiGram && AppSetup.voiceRecognitionData)) 			
				paths = addStringToPath(paths,"voiceSlopesRecognition");
			
		if(AppSetup.wave) 
			paths = addStringToPath(paths,"wave");
		
		
		if(AppSetup.voicePointsCompare)
			paths = addStringToPath(paths,"voiceCompare");
		
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
		
		Debug.debug(5,"AudioAnalysisPhaseBuilder addtask return tempPaths.length: "
			+tempPaths.length);
		
			return tempPaths;
	}
}
