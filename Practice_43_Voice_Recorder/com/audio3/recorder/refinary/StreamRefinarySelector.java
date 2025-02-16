package com.audio3.recorder.refinary;

import com.audio0.main.AppSetup;
import com.audio8.util.Debug;

public class StreamRefinarySelector {
	
	public static StreamRefinary activeRefinary;
	
	public static void setStreamRefinary(StreamRefinary activerefinary) {
		
		activeRefinary = activerefinary;
	}
	
	public static void mainRefinaryStarter() {
		
		if(AppSetup.amplitudeRefinary && !AppSetup.frequencyRefinary 
		    && !AppSetup.voiceRecognitionAmplitudeRefinary 
		    && !AppSetup.voiceRecognitionFrequencyRefinary) {
			
				activeRefinary =  new IntStreamRefinary();
				
					return;
		}
		
		if(!AppSetup.amplitudeRefinary && AppSetup.frequencyRefinary 
			&& !AppSetup.voiceRecognitionAmplitudeRefinary 
			&& !AppSetup.voiceRecognitionFrequencyRefinary) {

				activeRefinary = new IntFrequencyRefinary();
				
					return;
		}
		
		if(!AppSetup.amplitudeRefinary && !AppSetup.frequencyRefinary 
			&& AppSetup.voiceRecognitionAmplitudeRefinary 
			&& !AppSetup.voiceRecognitionFrequencyRefinary) {
			
				activeRefinary = new VoiceRecognitionAmplitudeRefinary();
				
					return;
		}
		
		if(!AppSetup.amplitudeRefinary && !AppSetup.frequencyRefinary 
		    && !AppSetup.voiceRecognitionAmplitudeRefinary 
		    && AppSetup.voiceRecognitionFrequencyRefinary) {
			
				activeRefinary = new VoiceRecognitionFrequencyRefinary() ;
				
					return;
		} else {
			Debug.debug(1,"StreamRefinarySelector.class. Recorder Unable to choose refinary!");
		}
	}
}
