package com.audio1.logical;

import java.util.Map;
import java.util.Scanner;

import com.audio0.main.AppSetup;
import com.audio5.recognition.VoiceRecognitionMain;
import com.audio6.audioGramSaver.DeleteLastAnalysisBuilder;
import com.audio6.audioGramSaver.DeleteLastDataModel;
import com.audio6.audioGramSaver.SaveMultiAudioFeatures;
import com.audio7.threads.MyThread;
import com.audio7.threads.ThreadManagement;
import com.audio8.util.Debug;

public class DeleteOrNotActionLogic implements MyThread {
	
	private boolean threadIsActive;
	private boolean threadIsSuspended;
	private Thread thread;	
	final private static String THREAD_NAME = "DeleteOrNotActionLogic"; 
	private static Scanner scanner ;
	public static boolean checking;
	public static String input;
	private static DeleteLastDataModel lastDelete;
	private static String svitch;
	private final static String[] threadsAlive = new String[] {"DeleteOrNotActionLogic"};
		
	public DeleteOrNotActionLogic() {
		
		threadIsActive = true;
		threadIsSuspended = false;
		thread = new Thread(this);		
		thread.start();
	}

	@Override
	public void run() {
		
		Thread.currentThread().setName(THREAD_NAME);		
		ThreadManagement.addingThread(this);
		
		Debug.debug(1,"Starting "+Thread.currentThread().getName() +" Thread!");

		while(threadIsActive) {
			
			suspendThread();
			
			analysisSaveCheck();
			
			stopThread();
		}
	}
		
	private static void analysisSaveCheck() {
					
		if(SaveMultiAudioFeatures.deleteList.peek()  == null) return;
		
		Debug.debug(3,"Starting DeleteOrNotActionLogic analysisSaveCheck! ");	
			
		suspendBaseTask();
		
		if(AppSetup.voiceRecognition)		
			voiceRecognitionSaveCheck();
			
		if(!AppSetup.voiceRecognition) 		
			keyboardSavecheck();
		
		restoreBaseTask();
			
		Debug.debug(3, "DeleteOrNotActionLogic analysisSaveCheck result: "+ input);
	}
	
	private static void voiceRecognitionSaveCheck() {
		
		Debug.debug(3, "DeleteOrNotActionLogic Start voiceRecognitionSaveCheck");
		
		svitch = EntryPointMethods.getSvitch();
		EntryPointMethods.mainOptionRun("voiceRecognition", null);
	
		while(checking) {
			
			if(VoiceRecognitionMain.readedVoiceArrray.size() > 0) {
				
				for(Map.Entry<Integer, String> entry: VoiceRecognitionMain.readedVoiceArrray.entrySet()) {
					
					input  = entry.getValue();
					Debug.debug(3, "DeleteOrNotActionLogic Start voiceRecognitionSaveCheck input: "+input);
					
					if(makeCheckAction(input)) 					
						checking = false;
				}
			}
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		EntryPointMethods.setSvitch(svitch);
	}
	
	private static void keyboardSavecheck() {
		
		scanner = new Scanner(System.in);
		Debug.debug(3, "\nDeleteOrNotActionLogicDelete Last Save? Yes(y) or No(n)");
		
		while(checking) {
			
			input = scanner.nextLine();
			
			if(makeCheckAction(input)) {	
				
				checking = false;
			}
		}	
	}
	
	private static void suspendBaseTask() {
		
		ThreadManagement.suspendAllThreadsWithExcluded(threadsAlive);   
		lastDelete = SaveMultiAudioFeatures.deleteList.poll();
		input = null;
		checking = true;
	}
	
	private static void restoreBaseTask() {
		
		ThreadManagement.enableAllThreads();  
	}
	
	private static boolean makeCheckAction(String readedInput) {
				
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
	public String getThreadName() {
		
		return THREAD_NAME;
	}
	// Class Cast Example
	//Class<?> cls = Class.forName("com.getclassfromstr.MyNiceClass");
	// refinary = (StreamRefinary) cls.getDeclaredConstructor().newInstance();
}
