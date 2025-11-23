package com.voiceRecognition.audio1.logical;

import java.util.Map;
import java.util.Scanner;

import com.MainAppSetup;
import com.threads.MyThread;
import com.threads.ThreadAction;
import com.threads.ThreadManagement;
import com.threads.ThreadObjectDetails;
import com.voiceRecognition.audio0.main.VoiceRecognitionAppSetup;
import com.voiceRecognition.audio5.recognition.VoiceRecognitionDB;
import com.voiceRecognition.audio5.recognition.VoiceRecognitionMain;
import com.voiceRecognition.audio6.audioGramSaver.DeleteLastAnalysisBuilder;
import com.voiceRecognition.audio6.audioGramSaver.DeleteLastDataModel;
import com.voiceRecognition.audio6.audioGramSaver.SaveMultiAudioFeatures;
import com.voiceRecognition.audio8.util.Debug;

public class DeleteOrNotActionLogic implements MyThread {
	
	private boolean threadIsActive;
	private boolean threadIsSuspended;
	private Thread thread;	
	final private static String THREAD_NAME = "DeleteOrNotActionLogic"; 
	ThreadObjectDetails threadObject = new ThreadObjectDetails(THREAD_NAME, true,MainAppSetup.voiceRecognitionAppName); 

	private static Scanner scanner ;
	public static boolean checking;
	public static String input;
	private static DeleteLastDataModel lastDelete;
	private static String svitch;
	private static String speechName;
	
	private static int debug_level_INFO = 1;
	private static int debud_level_DEBUG = 1;
		
	public DeleteOrNotActionLogic() {
		
		threadIsActive = true;
		threadIsSuspended = false;
		thread = new Thread(this);		
		thread.start();
	}

	@Override
	public void run() {
		
		Thread.currentThread().setName(THREAD_NAME);	
		threadObject.setApplicationName("DeleteOrNotActionLogic");
		
		ThreadManagement.threadActions.add(
				new ThreadAction("addingThread",-1,EntryPointMethods.getSvitch(),this,"addingThread DeleteOrNotActionLogic"));
		
		Debug.debug(debug_level_INFO,"Starting "+Thread.currentThread().getName() +" Thread!");

		while(threadIsActive) {
			
			suspendThread();
			
			analysisSaveCheck();
			
			stopThread();
		}
	}
		
	private void analysisSaveCheck() {
					
		if(SaveMultiAudioFeatures.deleteList.peek()  == null) return;
		
		Debug.debug(debud_level_DEBUG,"Starting DeleteOrNotActionLogic analysisSaveCheck! ");	
			
		suspendBaseTask();
		
		if(VoiceRecognitionAppSetup.voiceRecognition &&
				(VoiceRecognitionDB.audioPointsDB != null &&VoiceRecognitionDB.audioPointsDB.length >= 2))		
			voiceRecognitionSaveCheck();
			
		if(!VoiceRecognitionAppSetup.voiceRecognition ||
				(VoiceRecognitionDB.audioPointsDB != null &&VoiceRecognitionDB.audioPointsDB.length < 2))		
			keyboardSavecheck();
		
		restoreBaseTask();
			
		Debug.debug(debud_level_DEBUG, "DeleteOrNotActionLogic analysisSaveCheck result: "+ input);
	}
	
	private void voiceRecognitionSaveCheck() {
		
		Debug.debug(debud_level_DEBUG, "DeleteOrNotActionLogic Start voiceRecognitionSaveCheck");
		
		EntryPointMethods.setInstanceFalse();
		EntryPointMethods.mainOptionRun("voiceRecognition", null);
	
		while(checking) {
			
			if(VoiceRecognitionMain.readedVoiceArrray.size() > 0) {
				
				for(Map.Entry<Integer, String> entry: VoiceRecognitionMain.readedVoiceArrray.entrySet()) {
					
					input  = entry.getValue();
					Debug.debug(debud_level_DEBUG, "DeleteOrNotActionLogic Start voiceRecognitionSaveCheck input: "+input);
					
					if(makeCheckAction(input)) 	{				
						checking = false;
						VoiceRecognitionMain.readedVoiceArrray.remove(entry.getKey());
						input = null;
						break;
					}
				}
			}			
				sleepThread(50);
		}

		ThreadManagement.threadActions.add(
				new ThreadAction("stopThreadsByApplicationName",-1,"voiceRecognition",this,
						"stopThreadsByApplicationName voiceRecognition DeleteOrNotActionLogic"));
		ThreadManagement.threadActions.add(
				new ThreadAction("stopThreadsByApplicationName",-1,"DeleteOrNotActionLogic",this,
						"stopThreadsByApplicationName DeleteOrNotActionLogic DeleteOrNotActionLogic"));

		EntryPointMethods.setInstanceFalse();
	}
	
	private void keyboardSavecheck() {
		
		scanner = new Scanner(System.in);
		Debug.debug(debud_level_DEBUG, "\nDeleteOrNotActionLogicDelete Last Save? Yes(y) or No(n)");
		
		while(checking) {
			
			input = scanner.nextLine();
			
			if(makeCheckAction(input)) {	
				
				checking = false;
			}
		}	
	}
	
	private void suspendBaseTask() {
		
		svitch = EntryPointMethods.getSvitch();
		speechName = EntryPointMethods.getSpeechName();
		ThreadManagement.threadActions.add(
				new ThreadAction("suspendThreadsByApplicationName",-1,svitch,this,
						"suspendThreadsByApplicationName DeleteOrNotActionLogic"));
 
		lastDelete = SaveMultiAudioFeatures.deleteList.poll();
		input = null;
		checking = true;
	}
	
	private void restoreBaseTask() {
		
		EntryPointMethods.setSvitch(svitch);
		EntryPointMethods.loadProfileSetup(svitch);
		EntryPointMethods.setSpeechName(speechName);
		
		ThreadManagement.threadActions.add(
				new ThreadAction("enableAllThreads",-1,null,this, 
						"enableAllThreads DeleteOrNotActionLogic"));  
	}
	
	private boolean makeCheckAction(String readedInput) {
				
		if(readedInput.equals("yes") || readedInput.equals("y")|| readedInput.equals("Igen")) {
			
			DeleteLastAnalysisBuilder.deleteLastSave(lastDelete);
				
			return true;
		}
		
		else if(readedInput.equals("No") || readedInput.equals("n") || readedInput.equals("Nem")) 
			
			return true;
		
		else 		
			return false;
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
	// Class Cast Example
	//Class<?> cls = Class.forName("com.voiceRecognition.getclassfromstr.MyNiceClass");
	// refinary = (StreamRefinary) cls.getDeclaredConstructor().newInstance();
}
