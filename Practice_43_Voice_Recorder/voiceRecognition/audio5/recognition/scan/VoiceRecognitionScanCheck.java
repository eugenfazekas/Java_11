package voiceRecognition.audio5.recognition.scan;

import java.util.Arrays;

import voiceRecognition.audio1.logical.EntryPointMethods;
import voiceRecognition.audio5.recognition.VoiceRecognitionDB;
import voiceRecognition.audio5.recognition.VoiceRecognitionMain;
import voiceRecognition.audio7.threads.MyThread;
import voiceRecognition.audio7.threads.ThreadAction;
import voiceRecognition.audio7.threads.ThreadManagement;
import voiceRecognition.audio7.threads.util.ThreadObjectDetails;
import voiceRecognition.audio8.util.Debug;

public class VoiceRecognitionScanCheck implements MyThread{

	private int id;
	private Thread thread;
	public boolean threadIsActive;
	public boolean threadIsSuspended;
	final private static String THREAD_NAME = "VoiceRecognitionScanCheck"; 
	private ThreadObjectDetails threadObject; 
	
	private int checkArray[];
	private float[][] tempResultVerifier;
	private float result;
	private float match;	
	private float localBestMatch;	
	private int localBestPosition;
	private int avg;
	private int globalBestName;
	private int globalBestPosition;
	private float globalBestValue;
	
	private static int debug_level_INFO = 1;
	private static int debud_level_DEBUG = 5;
	
	public VoiceRecognitionScanCheck(int Id ,int[] inputArray) {
		
		id = Id;
		checkArray = inputArray;
		
		threadObject = new ThreadObjectDetails(THREAD_NAME, true);
		threadIsActive = true;
		threadIsSuspended = false;
		thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void run() {
		
		Thread.currentThread().setName(THREAD_NAME);	
		
		ThreadManagement.threadActions.add(
				new ThreadAction("addingThread",-1,EntryPointMethods.getSvitch(),this,
						"addingThread VoiceRecognitionScanCheck"));
		
		Debug.debug(debug_level_INFO,"Starting "+Thread.currentThread().getName() +" Thread!");
		
		while(threadIsActive) {
			
			suspendThread("VoiceRecognitionScanCheck main");
			
			 try {		    
					mainVoiceFinder(checkArray);
		    
		    } catch (Exception ex) {
		    	
	            Debug.debug(1,"DebugException in VoiceRecognitionScanCheck! " +ex.getMessage());  

	    		ThreadManagement.threadActions.add(
	    				new ThreadAction("stopAllThreads",-1,EntryPointMethods.getSvitch(),this,
	    						"stopAllThreads VoiceRecognitionScanCheck"));
		    }
		}				
	}
	
	private void mainVoiceFinder(int[] checkArray) {
		
		if(checkArray == null) return;
		
		Debug.startTime = System.currentTimeMillis();
			
		tempResultVerifier = new float[VoiceRecognitionDB.audioScanDB.length][];
		
		for(int i = 0; i <  VoiceRecognitionDB.audioScanDB.length; i++) {
			
			tempResultVerifier[i] = new float[VoiceRecognitionDB.audioScanDB[i].length+4];
			localBestMatch = 0;
			avg = 0;

			for(int j = 0; j <  VoiceRecognitionDB.audioScanDB[i].length; j++) {
							
				Debug.debug(debud_level_DEBUG,"VoiceRecognitionScanCheck VoiceRecognitionDB.audioScanDB[i][j]"
					+VoiceRecognitionDB.audioScanDB[i][j].length  +", checkArray[0].length: "
					+checkArray.length+", checkArray[1].length: "+checkArray.length + ", avg: "+avg);
				
				result = VRSCMainLogic.mainBuilder(checkArray, VoiceRecognitionDB.audioScanDB[i][j]);
								
				match =  result;
				tempResultVerifier[i][j] = match;
				
				avg += match;
				
				Debug.debug(debud_level_DEBUG,"VoiceRecognitionScanCheck VoiceRecognitionDB result1: "+result +", match: "
						+match + ", avg: "+avg);
				
				if(match > localBestMatch) {
					localBestMatch = match;
					localBestPosition = j;
					
					if(localBestMatch > globalBestValue) {
							
						globalBestName = i;
						globalBestPosition = j;
						globalBestValue = localBestMatch;
					}			
				}
				
				if(j == VoiceRecognitionDB.audioScanDB[i].length-1) {
					
					tempResultVerifier[i][j+1] = localBestMatch;
					tempResultVerifier[i][j+2] = (float) avg / (j+1);
					tempResultVerifier[i][j+3] = i;
					tempResultVerifier[i][j+4] = localBestPosition;
					
					Debug.debug(debud_level_DEBUG, "VoiceRecognitionScanCheck mainVoiceFinder Match Word: "
						+VoiceRecognitionDB.DB_NAMES.get(i)+", avg: "+(j+1)+ ", Added "
						+Arrays.toString(tempResultVerifier[i])+ ", Break\n");
				}			
			}
		}
		
		Debug.debug(debud_level_DEBUG, "VoiceRecognitionScanCheck mainVoiceFinder Actual bestMatch: " 
			+ VoiceRecognitionDB.DB_NAMES.get(globalBestName)+ ", globalBestPosition: "
			+globalBestPosition + ", globalBestValue: " + globalBestValue + ", Voice Finder Elapsed Time: " 
			+(System.currentTimeMillis() - Debug.startTime));
		
		VoiceRecognitionMain.addToCheckResult(id, 3, tempResultVerifier);
		
		stopThread(threadObject.getThreadName());
	}

	@Override
	public void stopThread(String initializer) {	
		
		Debug.debug(debug_level_INFO, THREAD_NAME+ " stoping Thread "+initializer);
		threadIsActive = false;
	}
	
	@Override
	public void suspendThread(String initializer) {
		
		Debug.debug(debug_level_INFO, THREAD_NAME+ " suspend Thread "+initializer);
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
		
		Debug.debug(debug_level_INFO, THREAD_NAME+ " is Thread Suspended "+initializer);
		return threadIsSuspended;
	}
	
	public void sleepThread(String initializer,int mSec) {
		
		Debug.debug(debug_level_INFO, THREAD_NAME+ " sleep Thread "+initializer);
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