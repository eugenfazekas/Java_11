package com.voiceRecognition.audio3.recorder.refinary;

import com.voiceRecognition.audio0.main.VoiceRecognitionAppSetup;
import com.voiceRecognition.audio8.util.Debug;

public class StreamRefinarySelector {
	
	public static StreamRefinary activeRefinary;
	
	public static void setStreamRefinary(StreamRefinary activerefinary) {
		
		activeRefinary = activerefinary;
	}
	
	public static void mainRefinaryStarter() {
		
		if(VoiceRecognitionAppSetup.amplitudeRefinary && !VoiceRecognitionAppSetup.frequencyRefinary ) {
			
				activeRefinary =  new IntStreamRefinary();
				
					return;
		}
		
		if(!VoiceRecognitionAppSetup.amplitudeRefinary && VoiceRecognitionAppSetup.frequencyRefinary) {

				activeRefinary = new IntFrequencyRefinary();
				
					return;
		}else {
			Debug.debug(1,"StreamRefinarySelector.class. Recorder Unable to choose refinary!");
		}
	}
}
