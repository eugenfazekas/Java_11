package com;

import com.executor.ClassMethodsExecuter;
import com.threads.ThreadManagement;
import com.voiceRecognition.audio0.main.VoiceRecognitionMainStart;

public class Main {

	public static ThreadManagement threadManagement;
	
	public static void main(String[] args) {

		//VoiceComandCenter.buildVoiceComnadList();
		//VoiceComandCenter.voiceComandCheck();
		
		mainAppStarter();

	}
	
	public static void mainAppStarter() {
			
		threadManagement = new ThreadManagement();
		
		if(MainAppSetup.audioApp)
			new VoiceRecognitionMainStart();
		
		if(MainAppSetup.methodExecutor)
			new ClassMethodsExecuter();
	}

}
