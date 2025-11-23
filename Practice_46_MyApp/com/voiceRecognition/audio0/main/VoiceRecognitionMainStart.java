package com.voiceRecognition.audio0.main;


import com.MainAppSetup;
import com.threads.MyThread;
import com.threads.ThreadAction;
import com.threads.ThreadManagement;
import com.threads.ThreadObjectDetails;
import com.voiceRecognition.audio1.logical.AllFeaturesEntryPoint;
import com.voiceRecognition.audio1.logical.EntryPointMethods;
import com.voiceRecognition.audio3.recorder.refinary.StreamRefinaryAmplitudeMethods;
import com.voiceRecognition.audio8.util.Debug;

public class VoiceRecognitionMainStart implements MyThread{
    	
	private static boolean instanceOf;
	private boolean threadIsActive;
	private boolean threadIsSuspended;
	private Thread thread;
	final private String THREAD_NAME = "VoiceRecognitionMainStart"; 
	ThreadObjectDetails threadObject; 
	
	private static int debug_level_INFO = 1;
	
	public VoiceRecognitionMainStart() {
				
		if(instanceOf) 
			return;
		
		instanceOf = true;		
		threadObject = new ThreadObjectDetails(THREAD_NAME, false, MainAppSetup.voiceRecognitionAppName); 
		threadIsActive = true;
		threadIsSuspended = false;
		thread = new Thread(this);		
		thread.start();
	}


	@Override
	public void run() {
		
		Thread.currentThread().setName(THREAD_NAME);
		
		Debug.debug(debug_level_INFO,"Starting "+Thread.currentThread().getName() +" Thread!");
			
		AllFeaturesEntryPoint.mainLogicalEntryPoint(
				new String[] {MainAppSetup.audioAppProfile, MainAppSetup.audioAppProfilePathName});
		
		ThreadManagement.threadActions.add(
				new ThreadAction("addingThread",-1,EntryPointMethods.getSvitch(),this,
						"addingThread VoiceRecognitionMainStart"));
		
		while(threadIsActive) {
			
			suspendThread();
			
			sleepThread(StreamRefinaryAmplitudeMethods.sleepTime/2);
		}
		
		Debug.debug(debug_level_INFO,"Stopping VoiceRecognitionMainStart Thread!");
		
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


	public static void setStopAllThreads(String string) {
	
		ThreadManagement.threadActions.add(
			new ThreadAction("stopThreadsByApplicationName",-1,EntryPointMethods.getSvitch(),null,
			"stopThreadsByApplicationName "+ EntryPointMethods.getSvitch()
			+" VoiceRecognitionMainStart"));
		
	}
}
