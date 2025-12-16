package voiceRecognition.audio5.audioGramBuilder;

import voiceRecognition.audio0.main.AppSetup;
import voiceRecognition.audio4.audioGramInitializer.mainInit.AudioAnalysisThread;
import voiceRecognition.audio8.util.Debug;

public class AmplitudeDetails {	
	
	private static int debud_level_DEBUG = 5;
	 
	static void addToAmplitudeWaveMap(int amplitude, int id ) {	 	
			 
		AGBCVariables.amplitudeWaveMap[AGBCVariables.amplitudeWaveMapCounter++] = 
			getPercentAmplitude(AudioAnalysisThread
					.startedVoiceCheck.get(id).getAverageAmplitude(),amplitude);

		 Debug.debug(debud_level_DEBUG,"AmplitudeDetails addToAmplitudeWaveMap amplitudeBuffer total: "
			+ " avg: "+amplitude
			+" ,AnalysisInitializer.waveCounter: "+AGBCVariables.amplitudeWaveMapCounter+", amplitude: "
			+ AGBCVariables.amplitudeWaveMap[AGBCVariables.amplitudeWaveMapCounter-1]);		
	}
	 
	static int getPercentAmplitude(int average,int  input) {
			
		AGBCVariables.percentAmplitudeResult 
				=(int) (input/((average/100)* AppSetup.AMPLITUDE_AVERAGE_CORRECTION_KONSTANT))+1; 
		
		Debug.debug(debud_level_DEBUG,"AmplitudeDetails getPercentAmplitude avg: "+average + ", input: "+input 
				+ ", percentAmplitudeResult: "+AGBCVariables.percentAmplitudeResult);
		
			return AGBCVariables.percentAmplitudeResult;
	}
		
	static void addToAmplitudeWaveMapSimple(int inputValue) {
		
		AGBCVariables.amplitudeWaveMap[AGBCVariables.amplitudeWaveMapCounter++] = inputValue;
	}
	
	static int calculateAVGAmplitude(AudioGramObject first, AudioGramObject middle, AudioGramObject last ) {
		
		AGBCVariables.amplitude = (first.getPosAmplitude() / first.getPosCounter()
				+first.getNegAmplitude() / first.getNegCounter()
				+middle.getPosAmplitude()/ middle.getPosCounter()
				+middle.getNegAmplitude()/ middle.getNegCounter()
				+last.getPosAmplitude()  / last.getPosCounter()
				+last.getNegAmplitude()  / last.getNegCounter())/3;
		
		return AGBCVariables.amplitude;
	}
}
