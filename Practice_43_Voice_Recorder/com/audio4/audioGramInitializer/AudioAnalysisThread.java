package com.audio4.audioGramInitializer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.audio0.main.AppSetup;
import com.audio1.logical.DeleteOrNotActionLogic;
import com.audio1.logical.EntryPointMethods;
import com.audio2.audioObject.AudioObject;
import com.audio4.audioGramInitializer.trim.AudioTrimSelector;
import com.audio5.audioGramBuilder.MultiAnalysisBuilder;
import com.audio5.recognition.VoiceRecognitionMain;
import com.audio6.audioGramSaver.SaveMultiAudioFeatures;
import com.audio8.util.Debug;
import com.audio8.util.thread.MyThread;
import com.audio8.util.thread.ThreadManagement;
import com.audio8.util.thread.ThreadUtil;

public class AudioAnalysisThread implements MyThread{

	public static boolean analysisThreadEnabled;
	public static boolean threadIsActive;
	public static boolean threadIsSuspended;
	public static int threadSleepTime;
	public Thread thread;
	
	private static AudioObject audioObject;
	public static Map<Integer,AudioObject> startedVoiceCheck = new ConcurrentHashMap<Integer, AudioObject>();
	
	public AudioAnalysisThread() {
		
		new VoiceRecognitionMain();		
		
		threadIsActive = true;
		threadIsSuspended = false;
		analysisThreadEnabled = true;
		threadSleepTime = 1000;
		
		thread = new Thread(this);
		
		thread.start();
	}
	
	public static void analysisStarter(AudioObject audioObject) {
		
		Debug.debug(1, "AudioAnalysisThread New AudioObject id: "+audioObject.getId());
		startedVoiceCheck.put(audioObject.getId(), audioObject);
		threadSleepTime = 100; 
	}
		
	private void mainAnalysis() {

		if(!analysisThreadEnabled) return;
		
		int id;
		
		for(Map.Entry<Integer, AudioObject> objects : startedVoiceCheck.entrySet()) {
			id = objects.getKey();
			initSwitch(id);
		}	
	}

	@Override
	public void run() {
		
		Thread.currentThread().setName("AudioAnalysisThread");
		
		ThreadManagement.threads.add(this);
		ThreadUtil.suspendThreadCheck(this);
		
		Debug.debug(1,"Starting "+Thread.currentThread().getName() +" Thread!");
		
		while(threadIsActive) {
			
			mainAnalysis();
			
			ThreadUtil.sleepThreadInMilisec(1000);
		}
	}

	private void saveNamedRecords(int id) {
		
    	Debug.debug(2,"AudioAnalysisThread saveNamedRecords inputFileName: "+id);
    	
    	AudioTrimSelector.mainAudioTrim(id);

	    MultiAnalysisBuilder.mainAnalysisBuilder(id);
	    
		SaveMultiAudioFeatures.mainSave(AppSetup.BASE_AUDIO_PATH,AppSetup.SPEKTRUM_PATH,id);
		
		DeleteOrNotActionLogic.startAnalysisSaveCheck();
	}
	
	private void voiceRecognition(int id) {
		
    	Debug.debug(2,"AudioAnalysisThread voiceRecognition inputFileName: "+id);
    	
    	AudioTrimSelector.mainAudioTrim(id);

    	VoiceRecognitionMain.main(id);
		
	    MultiAnalysisBuilder.smallAnalysisBuilder(id);
	    
		SaveMultiAudioFeatures.mainSave(AppSetup.BASE_AUDIO_PATH, AppSetup.VOICE_TEST_PATH,id);
	}
	
	private void voiceRecognitionDebug(int id) {
		
    	Debug.debug(2,"AudioAnalysisThread voiceRecognitionDebug inputFileName: "+id);
    	
    	AudioTrimSelector.mainAudioTrim(id);
    	
	    MultiAnalysisBuilder.mainAnalysisBuilder(id);
	    
	   	VoiceRecognitionMain.main(id);
		
		SaveMultiAudioFeatures.mainSave(AppSetup.BASE_AUDIO_PATH, AppSetup.VOICE_TEST_PATH,id);
	}
	
	private void saveContinueRecords(int id) {	
		
    	Debug.debug(2,"AudioAnalysisThread saveContinueRecords inputFileName: "+id);
    	
    	AudioTrimSelector.mainAudioTrim(id);

	    MultiAnalysisBuilder.mainAnalysisBuilder(id);
	    
		SaveMultiAudioFeatures.mainSave(AppSetup.CONTINUE_RECORDS_PATH,"",id);
	}
	
	private void  buildSpektrogramFromAudioFile(int id) {
		
    	Debug.debug(2,"AudioAnalysisThread buildSpektrogramFromAudioFile inputFileName: "+id);

	    MultiAnalysisBuilder.mainAnalysisBuilder(id);
	    
		SaveMultiAudioFeatures.mainSave(AppSetup.BASE_AUDIO_PATH,AppSetup.SPEKTRUM_PATH,id);	
	}

	private void buildSequenceFromFrequncyListFromFile(int id) {
		
    	Debug.debug(2,"AudioAnalysisThread buildSequenceFromFrequncyListFromFile inputFileName: "+id);
    	
	    MultiAnalysisBuilder.mainAnalysisBuilder(id);
	    
		SaveMultiAudioFeatures.mainSave(AppSetup.BASE_AUDIO_PATH,AppSetup.SPEKTRUM_PATH,id);	
	}
	
	private void buildTestAudioSequence(int id) {
		
    	Debug.debug(2,"AudioAnalysisThread buildTestSequence inputFileName: "+id);

		MultiAnalysisBuilder.mainAnalysisBuilder(id);
		
		SaveMultiAudioFeatures.mainSave(AppSetup.BASE_AUDIO_PATH,AppSetup.SPEKTRUM_PATH,id);
	}
	
	private void timeFixedSoundRecorder(int id) {		
		
    	Debug.debug(2,"AudioAnalysisThread timeFixedSoundRecorder inputFileName: "+audioObject + ", byteStream.length: " +audioObject);

	    MultiAnalysisBuilder.mainAnalysisBuilder(id);
	    
		SaveMultiAudioFeatures.mainSave(AppSetup.BASE_AUDIO_PATH,AppSetup.TIME_FIXED_RECORD_PATH,id);
	}
	
	private void initSwitch(int id) {
		
		switch(EntryPointMethods.getSvitch()) {
		
		case "saveNamedRecords" :saveNamedRecords(id);break;
		
		case "saveContinueRecords" :saveContinueRecords(id);break;
		
		case "voiceRecognition" :voiceRecognition(id);break;
		
		case "voiceRecognitionDebug" :voiceRecognitionDebug(id);break;
		
		case "buildSequenceFromFile" :buildSequenceFromFrequncyListFromFile(id);break;
		
		case "buildTestAudioSequence" :buildTestAudioSequence(id);break;	
		
		case "buildSpektrogramFromAudioFile" :buildSpektrogramFromAudioFile(id);break;
		
		case "timeFixedSoundRecorder" : timeFixedSoundRecorder(id);break;
		
		}
	}
	
	@Override
	public void stopThread() {	
		
		threadIsActive = false;
	}
	
	@Override
	public void suspendThread() {
		
		threadIsSuspended = true;
	}
	
	@Override
	public void stopSuspendThread() {
		
		threadIsSuspended = false;
	}

	@Override
	public Thread getThread() {

		return thread;
	}

	@Override
	public boolean isThreadSuspended() {
		
		return threadIsSuspended;
	}
}
