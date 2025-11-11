package com.audio4.audioGramInitializer.mainInit;
 
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.audio0.main.AppSetup;
import com.audio0.main.Main;
import com.audio1.logical.DeleteOrNotActionLogic;
import com.audio1.logical.EntryPointMethods;
import com.audio2.audioObject.AudioObject;
import com.audio4.audioGramInitializer.trim.AudioTrimSelector;
import com.audio5.audioGramBuilder.MultiAnalysisBuilder;
import com.audio5.recognition.VoiceRecognitionMain;
import com.audio6.audioGramSaver.SaveMultiAudioFeatures;
import com.audio7.threads.MyThread;
import com.audio7.threads.ThreadAction;
import com.audio7.threads.ThreadManagement;
import com.audio7.threads.util.ThreadObjectDetails;
import com.audio8.util.Debug;
 
public class AudioAnalysisThread implements MyThread{
 
	private static boolean instanceOf;
	private Thread thread;
	private boolean threadIsActive;
	private boolean threadIsSuspended;
	final private String THREAD_NAME = "AudioAnalysisThread"; 
	ThreadObjectDetails threadObject; 
	
	public static int threadSleepTime;	
	private static AudioObject audioObject;
	public static Map<Integer,AudioObject> startedVoiceCheck 
									= new ConcurrentHashMap<Integer, AudioObject>();
	
	private static int debug_level_INFO = 5;
	private static int debud_level_DEBUG = 5;
	
	public AudioAnalysisThread() {
		
		if(instanceOf) {
			return;
			//throw new RuntimeException("RuntimeException AudioAnalysisThread already running!");
		}
		
		instanceOf = true;
		threadSleepTime = 1000;	
		
		threadObject = new ThreadObjectDetails(THREAD_NAME, false); 
		threadIsActive = true;
		threadIsSuspended = false;
		thread = new Thread(this);		
		thread.start();
	}
	
	public static void analysisStarter(AudioObject audioObject) {
		
		Debug.debug(debug_level_INFO, "AudioAnalysisThread New AudioObject id: "+audioObject.getId());
		startedVoiceCheck.put(audioObject.getId(), audioObject);
		threadSleepTime = 100; 
	}
		
	private void mainAnalysis() {

		Debug.debug(debud_level_DEBUG,"AudioAnalysisThread mainAnalysis startedVoiceCheck.length: "
				+startedVoiceCheck.size());
		
		for(Map.Entry<Integer, AudioObject> objects : startedVoiceCheck.entrySet()) 
			initSwitch(objects.getKey());	
	}
 
	@Override
	public void run() {
			
		Thread.currentThread().setName(THREAD_NAME);
		
		Debug.debug(debug_level_INFO,"Starting "+Thread.currentThread().getName() +" Thread!");
		
		ThreadManagement.threadActions.add(
				new ThreadAction("addingThread",-1,EntryPointMethods.getSvitch(),this));
 
		while(threadIsActive) {
			
			suspendThread();
						
		    try {		    
					mainAnalysis();	
		    
		    } catch (Exception ex) {
		    	
	            Debug.debug(debud_level_DEBUG,"DebugException in AudioAnalysisThread! " +ex.getMessage());  

	    		ThreadManagement.threadActions.add(
	    				new ThreadAction("stopAllThreads",-1,EntryPointMethods.getSvitch(),this));
		    }
			
			sleepThread(threadSleepTime);
		}
		
		Debug.debug(debug_level_INFO,"Stopping AudioAnalysisThread Thread!");
	}
 
	private void saveNamedRecords(int id) {
		
    	Debug.debug(debud_level_DEBUG,"AudioAnalysisThread saveNamedRecords inputFileName: "+id);
    	
    	mainAudioTrim(id);
 
    	mainAnalysisBuilder(id);
	    
    	saveMultiAudioFeatures(id);
		
		new DeleteOrNotActionLogic();
	}
	
	private void voiceRecognition(int id) {
		
    	Debug.debug(debud_level_DEBUG,"AudioAnalysisThread voiceRecognition inputFileName: "+id);
    	
    	mainAudioTrim(id);
    	
    	mainAnalysisBuilder(id);
 
    	voiceRecognitionMain(id);		   
	}
	
	private void voiceRecognitionDebug(int id) {
		
    	Debug.debug(debud_level_DEBUG,"AudioAnalysisThread voiceRecognitionDebug inputFileName: "+id);
    	
    	mainAudioTrim(id);
    	
    	mainAnalysisBuilder(id);
	    
    	voiceRecognitionMain(id);
			    
    	saveMultiAudioFeatures(id);
	}
	
	private void saveContinueRecords(int id) {	
		
    	Debug.debug(debud_level_DEBUG,"AudioAnalysisThread saveContinueRecords inputFileName: "+id);
    	
    	mainAudioTrim(id);
 
    	mainAnalysisBuilder(id);
	    
    	saveMultiAudioFeatures(id);
	}
	
	private void  buildSpektrogramFromAudioFile(int id) {
		
    	Debug.debug(debud_level_DEBUG,"AudioAnalysisThread buildSpektrogramFromAudioFile inputFileName: "+id);
 
	    mainAnalysisBuilder(id);
	    
	    saveMultiAudioFeatures(id);
	    
	    Main.setStopAllThreads();
	}
 
	private void buildSequenceFromFrequncyListFromFile(int id) {
		
    	Debug.debug(debud_level_DEBUG,"AudioAnalysisThread buildSequenceFromFrequncyListFromFile inputFileName: "+id);
    	
	    mainAnalysisBuilder(id);
	    
	    saveMultiAudioFeatures(id);	
	    
	    Main.setStopAllThreads();
	}
	
	private void buildTestAudioSequence(int id) {
		
    	Debug.debug(debud_level_DEBUG,"AudioAnalysisThread buildTestSequence inputFileName: "+id 
    		+ ", AppSetup.multiAnalysis: " + AppSetup.multiAnalysis);
 
	    mainAnalysisBuilder(id);
	    
	    saveMultiAudioFeatures(id);
	    
	    Main.setStopAllThreads();
	}
	
	private void timeFixedSoundRecorder(int id) {		
		
    	Debug.debug(debud_level_DEBUG,"AudioAnalysisThread timeFixedSoundRecorder inputFileName: "+audioObject 
    			+ ", byteStream.length: " +audioObject);
 
	    mainAnalysisBuilder(id);
	    
	    saveMultiAudioFeatures(id);

	    Main.setStopAllThreads();
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
	
	private void mainAudioTrim(int id) {
		
		if(AudioTrimSelector.building.get() == false) 
	    	AudioTrimSelector.mainAudioTrim(id);
		
		else {
			
			while(AudioTrimSelector.building.get() == true 
				&& startedVoiceCheck.get(id).getNextStage().equals("trim")) {	
		    	Debug.debug(debud_level_DEBUG,"AudioAnalysisThread mainAudioTrim OCCUPAID!");
				sleepThread(50); 
			}
			
	    	AudioTrimSelector.mainAudioTrim(id);
		}
	}
	
	private void voiceRecognitionMain(int id) {
		
		if(VoiceRecognitionMain.building.get() == false) 
			VoiceRecognitionMain.main(id);
		
		else {
			
			while(VoiceRecognitionMain.building.get() == true
				&& startedVoiceCheck.get(id).getNextStage().equals("voiceCheck")) 	 {		
		    	Debug.debug(debud_level_DEBUG,"AudioAnalysisThread voiceRecognitionMain OCCUPAID!");
				sleepThread(50); 
			}
			
			VoiceRecognitionMain.main(id);		
		}
	}
	
	private void mainAnalysisBuilder(int id) {
		
		if(MultiAnalysisBuilder.building.get() == false) 
		    MultiAnalysisBuilder.mainAnalysisBuilder(id);
		
		else {
			
			while(MultiAnalysisBuilder.building.get() == true 
				&& startedVoiceCheck.get(id).getNextStage().equals("analysis"))	{	
				
		    	Debug.debug(debud_level_DEBUG,"AudioAnalysisThread mainAnalysisBuilder OCCUPAID!");
				sleepThread(50); 
			}
			
		    MultiAnalysisBuilder.mainAnalysisBuilder(id);
		}
	}
		
	private void saveMultiAudioFeatures(int id) {
		
		if(SaveMultiAudioFeatures.building.get() == false) 
			SaveMultiAudioFeatures.mainSave(AppSetup.BASE_AUDIO_PATH,id);
		
		else {
			
			while(SaveMultiAudioFeatures.building.get() == true 
				&& startedVoiceCheck.get(id).getNextStage().equals("save")) {	
				
		    	Debug.debug(debud_level_DEBUG,"AudioAnalysisThread saveMultiAudioFeatures OCCUPAID!");	
				sleepThread(50); 
			}
			
			SaveMultiAudioFeatures.mainSave(AppSetup.BASE_AUDIO_PATH,id);
		}
	}
	
	public static void removeAudioObjct(int id) {
		
		Debug.debug(debug_level_INFO, "AudioAnalysisThread REMOVING id: "+id);
		
		AudioAnalysisThread.startedVoiceCheck.remove(id);
		
		if(AudioAnalysisThread.startedVoiceCheck.size() == 0)
			AudioAnalysisThread.threadSleepTime = 1000;
	}
	
	@Override
	public void stopThread() {	
		
		threadIsActive = false;

	}
	
	@Override
	public void suspendThread() {
		
		while(isThreadSuspended()) {
			
			sleepThread(50);
		}
	}
	
	@Override
	public void setSuspendThread() {
		
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
	
	public void sleepThread(int mSec) {
		
		try {
			Thread.sleep(mSec);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	@Override
	public ThreadObjectDetails getThreadObjectDetails() {

		return threadObject;
	}
}