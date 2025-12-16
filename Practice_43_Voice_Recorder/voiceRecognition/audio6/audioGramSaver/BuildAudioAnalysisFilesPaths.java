package voiceRecognition.audio6.audioGramSaver;


import java.util.Arrays;

import voiceRecognition.audio0.main.AppSetup;
import voiceRecognition.audio8.util.Debug;

public class BuildAudioAnalysisFilesPaths {
	
	private static String[] paths;
	private static String[] tempPaths;

	private static int debud_level_DEBUG = 5;
		
	public static String[] buildAudioAnalysisFilesPaths() {
		
		paths = null;
		tempPaths = null;
		
		if(AppSetup.amplitudeGram) 		
			paths = addStringToPath(paths,"amplitudeGram") ;		
		
		if(AppSetup.frequencyGram) 			
			paths = addStringToPath(paths,"frequencyGram");
		
		if(AppSetup.frequencyGram) 			
			paths = addStringToPath(paths,"frequencyMultiGram") ;
		
		if((AppSetup.amplitudeGram && AppSetup.frequencyGram && AppSetup.rawAudioData)) 		
			paths = addStringToPath(paths,"rawAudioData") ;
				
		if(AppSetup.amplitudeGram && AppSetup.frequencyGram && AppSetup.spektrogram)
		
			paths = addStringToPath(paths,"spektroGram") ;
				
		if(AppSetup.amplitudeGram && AppSetup.frequencyGram && AppSetup.mySpektrogram)

			paths = addStringToPath(paths,"mySpektrogram") ;
		
		if(AppSetup.amplitudeGram && AppSetup.frequencyGram && AppSetup.mySpektrogram)

			paths = addStringToPath(paths,"buildMixedWaveStreamPoints") ;
				
		if(AppSetup.amplitudeGram && AppSetup.frequencyGram && AppSetup.buildSequenceArray) 		
			paths = addStringToPath(paths,"buildSequenceArray");
			
		if(AppSetup.amplitudeGram && AppSetup.frequencyGram && AppSetup.voiceRecognitionPointsData)			
			paths = addStringToPath(paths,"voicePointsRecognition");
		
		if(AppSetup.amplitudeGram && AppSetup.frequencyGram && AppSetup.voiceRecognitionSlopesData)	
			paths = addStringToPath(paths,"voiceSlopesRecognition");
		
		if(AppSetup.amplitudeGram && AppSetup.frequencyGram && AppSetup.voiceRecognitionAreaData)	
			paths = addStringToPath(paths,"voiceAreaRecognition");
		
		if(AppSetup.amplitudeGram && AppSetup.frequencyGram && AppSetup.voiceRecognitionScanData)	
			paths = addStringToPath(paths,"voiceScanRecognition");
		
		if(AppSetup.amplitudeGram && AppSetup.frequencyGram && AppSetup.voiceRecognitionFrequencyGramData)	
			paths = addStringToPath(paths,"voiceFrequencyGramRecognition");
				
		if(AppSetup.wave) 
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
