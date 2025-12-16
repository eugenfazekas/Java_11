package voiceRecognition.audio5.audioGramBuilder;

import voiceRecognition.audio0.main.AppSetup;
import voiceRecognition.audio4.audioGramInitializer.mainInit.AudioAnalysisThread;
import voiceRecognition.audio8.util.Debug;

public class AGBCVariables {
	
	static AudioGramObject first;
	static AudioGramObject middle;
	static AudioGramObject last;
	
	static int amplitude; 	
	static int[] amplitudeWaveMap;
 	static int amplitudeWaveMapCounter;
 	static int percentAmplitudeResult;
 	
	static int[] frequencyTemp; 
	static int[] frequencys; 
	static int[] frequencyWaveMap;
 	static int frequencyWaveMapCounter;
 	static int[] mappedFrequencyArray;	
 	
	static int[][] frequencyMultiWaveMap;
 	static int frequencyMultiWaveMapCounter;
 	
	static int AVG_MILISEC_LENGTH; 
	static int avgMilisecCounter;
	static int amplitudeHighest1;
	static int amplitudeHighest2;
	
	private static int debug_level_INFO = 5;
			
	static void resetVariables(int id) {

		amplitudeWaveMap = new int[1000];
		amplitudeWaveMapCounter = 0;
		
		frequencyWaveMap = new int[1000]; 
	    frequencyWaveMapCounter = 0;
	    
		frequencyMultiWaveMap = new int[1000][];
	 	frequencyMultiWaveMapCounter = 0;
	 	
		mappedFrequencyArray = null;
		
		amplitudeHighest1 = 0;
		amplitudeHighest2 = 0;
		
		avgMilisecCounter = 0; 
		AVG_MILISEC_LENGTH = AudioGramUtil.getBuffersLengthByMilisec(
			(int)AudioAnalysisThread.startedVoiceCheck.get(id).getAudioFormat().getSampleRate()
				,AppSetup.AUDIO_BUFFER_MILISEC_LENGTH);
			
		AGBCVariables.first = new AudioGramObject(AGBCVariables.AVG_MILISEC_LENGTH);
		AGBCVariables.middle = new AudioGramObject(AGBCVariables.AVG_MILISEC_LENGTH);
		AGBCVariables.last = new AudioGramObject(AGBCVariables.AVG_MILISEC_LENGTH); 
		
		Debug.debug(debug_level_INFO,"AGBCVariables initAmplitudeDetaisl! AVG_MILISEC_LENGTH: " +AVG_MILISEC_LENGTH);
	}
}


