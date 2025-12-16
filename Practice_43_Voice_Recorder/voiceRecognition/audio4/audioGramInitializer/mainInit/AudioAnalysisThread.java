package voiceRecognition.audio4.audioGramInitializer.mainInit;
 

import java.util.Map;
import java.util.concurrent.*;

import voiceRecognition.audio0.main.AppSetup;
import voiceRecognition.audio0.main.Main;
import voiceRecognition.audio1.logical.DeleteOrNotActionLogic;
import voiceRecognition.audio1.logical.EntryPointMethods;
import voiceRecognition.audio2.audioObject.AudioObject;
import voiceRecognition.audio4.audioGramInitializer.trim.AudioTrimSelector;
import voiceRecognition.audio5.audioGramBuilder.MultiAnalysisBuilder;
import voiceRecognition.audio5.recognition.VoiceRecognitionMain;
import voiceRecognition.audio6.audioGramSaver.SaveMultiAudioFeatures;
import voiceRecognition.audio7.threads.MyThread;
import voiceRecognition.audio7.threads.ThreadAction;
import voiceRecognition.audio7.threads.ThreadManagement;
import voiceRecognition.audio7.threads.util.ThreadObjectDetails;
import voiceRecognition.audio8.util.Debug;
 
public class AudioAnalysisThread implements MyThread{
 
	private static boolean instanceOf;
	private Thread thread;
	private boolean threadIsActive;
	private boolean threadIsSuspended;
	final private String THREAD_NAME = "AudioAnalysisThread"; 
	private ThreadObjectDetails threadObject; 
	
	public static int threadSleepTime;	
	private static AudioObject audioObject;
	public static Map<Integer,AudioObject> startedVoiceCheck 
									= new ConcurrentHashMap<Integer, AudioObject>();
	
	private static int debug_level_INFO = 1;
	private static int debud_level_DEBUG = 5;
	
	public AudioAnalysisThread() {
		
		if(instanceOf) {
			return;
			//throw new RuntimeException("RuntimeException AudioAnalysisThread already running!");
		}
		
		instanceOf = true;
		threadSleepTime = AppSetup.AUDIO_ANALYSIS_THREAD_SLEEP;	
		
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

		Debug.debug(debud_level_DEBUG,threadObject.getThreadName()+" mainAnalysis startedVoiceCheck.length: "
				+startedVoiceCheck.size());
		
		if(startedVoiceCheck.size() > 0)
			for(Map.Entry<Integer, AudioObject> objects : startedVoiceCheck.entrySet()) 
				initSwitch(objects.getKey());	
	}
 
	@Override
	public void run() {
			
		Thread.currentThread().setName(THREAD_NAME);
		
		Debug.debug(debug_level_INFO,"Starting "+threadObject.getThreadName() +" Thread!");
		
		ThreadManagement.threadActions.add(
				new ThreadAction("addingThread",-1,EntryPointMethods.getSvitch(),this,
						"addingThread AudioAnalysisThread"));
 
		while(threadIsActive) {
			
			suspendThread(threadObject.getThreadName()+" main");
		
		    try {		    

				mainAnalysis();	
				
		    } catch (Exception ex) {
		    	
	            Debug.debug(1,"DebugException in AudioAnalysisThread! " +ex.getMessage());  

	    		ThreadManagement.threadActions.add(
	    				new ThreadAction("stopAllThreads",-1,EntryPointMethods.getSvitch(),this,
	    						"stopAllThreads AudioAnalysisThread"));
		    }
			
			sleepThread(threadObject.getThreadName()+" main",threadSleepTime);
		}
		
		Debug.debug(debug_level_INFO,"Stopping "+threadObject.getThreadName()+" Thread!");
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
	    
	    Main.setStopAllThreads("AudioAnalysisThread buildSpektrogramFromAudioFile");
	}
 
	private void buildSequenceFromFrequncyListFromFile(int id) {
		
    	Debug.debug(debud_level_DEBUG,"AudioAnalysisThread buildSequenceFromFrequncyListFromFile inputFileName: "+id);
    	
	    mainAnalysisBuilder(id);
	    
	    saveMultiAudioFeatures(id);	
	    
	    Main.setStopAllThreads("AudioAnalysisThread buildSequenceFromFrequncyListFromFile");
	}
	
	private void buildTestAudioSequence(int id) {
		
    	Debug.debug(debud_level_DEBUG,"AudioAnalysisThread buildTestSequence inputFileName: "+id 
    		+ ", AppSetup.multiAnalysis: " + AppSetup.multiAnalysis);
 
	    mainAnalysisBuilder(id);
	    
	    saveMultiAudioFeatures(id);
	    
	    Main.setStopAllThreads("AudioAnalysisThread buildTestAudioSequence");
	}
	
	private void timeFixedSoundRecorder(int id) {		
		
    	Debug.debug(debud_level_DEBUG,"AudioAnalysisThread timeFixedSoundRecorder inputFileName: "+audioObject 
    			+ ", byteStream.length: " +audioObject);
 
	    mainAnalysisBuilder(id);
	    
	    saveMultiAudioFeatures(id);

	    Main.setStopAllThreads("AudioAnalysisThread timeFixedSoundRecorder");
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
				sleepThread("AudioAnalysisThread main",50); 
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
				sleepThread("AudioAnalysisThread main",50); 
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
				sleepThread("AudioAnalysisThread main",50); 
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
				sleepThread("AudioAnalysisThread main",50); 
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
	public void stopThread(String initializer) {	
		
		Debug.debug(debug_level_INFO, THREAD_NAME+ " stoping Thread "+initializer);
		threadIsActive = false;
	}
	
	@Override
	public void suspendThread(String initializer) {
		
		Debug.debug(debud_level_DEBUG, THREAD_NAME+ " suspend Thread "+initializer);
		while(isThreadSuspended(initializer)) {
			
			sleepThread(initializer,50);
		}
	}
	
	@Override
	public void setSuspendThread(String initializer) {
		
		Debug.debug(debug_level_INFO, THREAD_NAME+ " set Suspend Thread "+initializer);
		threadIsSuspended = true;		
	}
	
	@Override
	public void stopSuspendThread(String initializer) {
		
		Debug.debug(debug_level_INFO, THREAD_NAME+ " stop Suspend Thread "+initializer);
		threadIsSuspended = false;
	}

	@Override
	public Thread getThread(String initializer) {

		Debug.debug(debug_level_INFO, THREAD_NAME+ " get Thread "+initializer);
		return thread;
	}

	@Override
	public boolean isThreadSuspended(String initializer) {
		
		Debug.debug(debud_level_DEBUG, THREAD_NAME+ " is Thread Suspended "+initializer);
		return threadIsSuspended;
	}
	
	public void sleepThread(String initializer,int mSec) {
		
		Debug.debug(debud_level_DEBUG, THREAD_NAME+ " sleep Thread "+initializer);
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