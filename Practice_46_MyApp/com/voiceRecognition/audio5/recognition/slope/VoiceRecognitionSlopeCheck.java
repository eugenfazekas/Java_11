package com.voiceRecognition.audio5.recognition.slope;

import java.util.Arrays;

import com.MainAppSetup;
import com.threads.MyThread;
import com.threads.ThreadAction;
import com.threads.ThreadManagement;
import com.threads.ThreadObjectDetails;
import com.voiceRecognition.audio1.logical.EntryPointMethods;
import com.voiceRecognition.audio5.recognition.VoiceRecognitionDB;
import com.voiceRecognition.audio5.recognition.VoiceRecognitionMain;
import com.voiceRecognition.audio8.util.Debug;

public class VoiceRecognitionSlopeCheck implements MyThread{

	private static int id;
	private Thread thread;
	public static boolean threadIsActive;
	public boolean threadIsSuspended;
	final private static String THREAD_NAME = "VoiceRecognitionSlopeCheck"; 
	ThreadObjectDetails threadObject; 
	
	private static int checkArray[][];
	private static float[][] tempResultVerifier;
	private static float result1;
	private static float result2;
	private static float match;	
	private static float localBestMatch;	
	private static int localBestPosition;
	private static int avg;
	private  static int globalBestName;
	private static int globalBestPosition;
	private static float globalBestValue;
	
	private static int debug_level_INFO = 5;
	private static int debud_level_DEBUG = 5;
	
	public VoiceRecognitionSlopeCheck(int Id ,int[][] inputArray) {
		
		id = Id;
		checkArray = inputArray;
		
		threadObject = new ThreadObjectDetails(THREAD_NAME, true,MainAppSetup.voiceRecognitionAppName);
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
						"addingThread VoiceRecognitionSlopeCheck"));
		
		Debug.debug(debug_level_INFO,"Starting "+Thread.currentThread().getName() +" Thread!");
		
		while(threadIsActive) {
			
			suspendThread();
			
		    try {		    
					mainVoiceFinder(checkArray);
		    
		    } catch (Exception ex) {
		    	
	            Debug.debug(1,"DebugException in VoiceRecognitionSlopeCheck! " +ex.getMessage());  

	    		ThreadManagement.threadActions.add(
	    				new ThreadAction("stopAllThreads",-1,EntryPointMethods.getSvitch(),this,
	    						"stopAllThreads VoiceRecognitionSlopeCheck"));
		    }
		}
		
		thread = null;			
	}
	
	private static void mainVoiceFinder(int[][] checkArray) {
		
		if(checkArray == null) return;
		
		Debug.startTime = System.currentTimeMillis();
			
		tempResultVerifier = new float[VoiceRecognitionDB.audioSlopeDB.length][];
		
		for(int i = 0; i <  VoiceRecognitionDB.audioSlopeDB.length; i++) {
			
			tempResultVerifier[i] = new float[VoiceRecognitionDB.audioSlopeDB[i].length+4];
			localBestMatch = 0;
			avg = 0;

			for(int j = 0; j <  VoiceRecognitionDB.audioSlopeDB[i].length; j++) {
							
				Debug.debug(debud_level_DEBUG,"VoiceRecognitionDB.audioSlopeDB[i][j][0]"
					+VoiceRecognitionDB.audioSlopeDB[i][j][0].length 
					+", VoiceRecognitionDB.audioSlopeDB[i][j][1]: "+VoiceRecognitionDB.audioSlopeDB[i][j][1].length
					+", checkArray[0].length: "+checkArray[0].length+", checkArray[1].length: "+checkArray[1].length 
					+ ", avg: "+avg);
				
				result1 = VRSLMainLogic.mainBuilder(checkArray[0],
					VoiceRecognitionDB.audioSlopeDB[i][j][0]);
				
				result2 = VRSLMainLogic.mainBuilder(checkArray[1],
						VoiceRecognitionDB.audioSlopeDB[i][j][1]);
					
				match = (  ( (float)  result1 / 100) * ( (float) result2 / 100 )  ) *100;;
				tempResultVerifier[i][j] = match;
				
				avg += match;
				
				Debug.debug(debud_level_DEBUG,"VoiceRecognitionDB result1: "+result1+ ", result2: "+ result2 
					+", match: "+match + ", avg: "+avg);
				
				if(match > localBestMatch) {
					localBestMatch = match;
					localBestPosition = j;
					
					if(localBestMatch > globalBestValue) {
							
						globalBestName = i;
						globalBestPosition = j;
						globalBestValue = localBestMatch;
					}			
				}
				
				if(j == VoiceRecognitionDB.audioSlopeDB[i].length-1) {
					
					tempResultVerifier[i][j+1] = localBestMatch;
					tempResultVerifier[i][j+2] = (float) avg / (j+1);
					tempResultVerifier[i][j+3] = i;
					tempResultVerifier[i][j+4] = localBestPosition;
					
					Debug.debug(debud_level_DEBUG, "VoiceRecognitionSlopeCheck mainVoiceFinder Match Word: "
						+VoiceRecognitionDB.DB_NAMES.get(i)+", avg: "+(j+1)+ ", Added "
						+Arrays.toString(tempResultVerifier[i])+ ", Break\n");
				}			
			}
		}
		
		Debug.debug(debud_level_DEBUG, "VoiceRecognitionSlopeCheck mainVoiceFinder Actual bestMatch: " 
			+ VoiceRecognitionDB.DB_NAMES.get(globalBestName)+ ", globalBestPosition: "
			+globalBestPosition+ ", globalBestValue: "+globalBestValue+", Voice Finder Elapsed Time: " 
			+(System.currentTimeMillis() - Debug.startTime));
		
		VoiceRecognitionMain.addToCheckResult(id, 1, tempResultVerifier);
		
		threadIsActive = false;
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
