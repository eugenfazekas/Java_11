package com.audio2.recorder.refinary;

import com.audio0.main.AppSetup;
import com.audio8.util.Debug;

public class StreamrefinarySelector {
	
	public static void mainRefinaryStarter() {
		
		if(AppSetup.amplitudeRefinary && !AppSetup.frequencyRefinary 
		  && !AppSetup.voiceRecognitionAmplitudeRefinary && !AppSetup.voiceRecognitionFrequencyRefinary) {
				new IntStreamRefinary();
					return;
		}
		
		if(!AppSetup.amplitudeRefinary && AppSetup.frequencyRefinary 
				  && !AppSetup.voiceRecognitionAmplitudeRefinary && !AppSetup.voiceRecognitionFrequencyRefinary) {
				new IntFrequencyRefinary();
					return;
		}
		if(!AppSetup.amplitudeRefinary && !AppSetup.frequencyRefinary 
				  && AppSetup.voiceRecognitionAmplitudeRefinary && !AppSetup.voiceRecognitionFrequencyRefinary) {
				new VoiceRecognitionAmplitudeRefinary();
					return;
		}
		if(!AppSetup.amplitudeRefinary && !AppSetup.frequencyRefinary 
				  && !AppSetup.voiceRecognitionAmplitudeRefinary && AppSetup.voiceRecognitionFrequencyRefinary) {
				new VoiceRecognitionFrequencyRefinary() ;
					return;
		} else {
			Debug.debug(1,"StreamRefinarySelector.class. Recorder Unable to choose refinary!");
		}
	}
}
