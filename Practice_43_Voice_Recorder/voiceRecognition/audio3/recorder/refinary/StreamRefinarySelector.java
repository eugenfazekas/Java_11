package voiceRecognition.audio3.recorder.refinary;

import voiceRecognition.audio0.main.AppSetup;
import voiceRecognition.audio8.util.Debug;

public class StreamRefinarySelector {
	
	public static StreamRefinary activeRefinary;
	
	public static void setStreamRefinary(StreamRefinary activerefinary) {
		
		activeRefinary = activerefinary;
	}
	
	public static void mainRefinaryStarter() {
		
		if(AppSetup.amplitudeRefinary && !AppSetup.frequencyRefinary ) {
			
				activeRefinary =  new IntStreamRefinary();
				
					return;
		}
		
		if(!AppSetup.amplitudeRefinary && AppSetup.frequencyRefinary) {

				activeRefinary = new IntFrequencyRefinary();
				
					return;
		}else {
			Debug.debug(1,"StreamRefinarySelector.class. Recorder Unable to choose refinary!");
		}
	}
}
