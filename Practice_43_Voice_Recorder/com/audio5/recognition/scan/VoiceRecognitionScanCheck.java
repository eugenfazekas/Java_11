package com.audio5.recognition.scan;

import java.util.Arrays;

import com.audio5.recognition.VoiceRecognitionDB;
import com.audio5.recognition.VoiceRecognitionMain;
import com.audio7.threads.MyThread;
import com.audio7.threads.ThreadManagement;
import com.audio8.util.Debug;

public class VoiceRecognitionScanCheck implements MyThread{

	private static int id;
	private Thread thread;
	public static boolean threadIsActive;
	public boolean threadIsSuspended;
	final private static String THREAD_NAME = "VoiceRecognitionScanCheck"; 
	
	private static int checkArray[];
	private static float[][] tempResultVerifier;
	private static float result;
	private static float match;	
	private static float localBestMatch;	
	private static int localBestPosition;
	private static int avg;
	private  static int globalBestName;
	private static int globalBestPosition;
	private static float globalBestValue;
	
	public VoiceRecognitionScanCheck(int Id ,int[] inputArray) {
		
		id = Id;
		checkArray = inputArray;
		
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
			
			mainVoiceFinder(checkArray);
		}
		
		thread = null;			
	}
	
	private static void mainVoiceFinder(int[] checkArray) {
		
		if(checkArray == null) return;
		
		Debug.startTime = System.currentTimeMillis();
			
		tempResultVerifier = new float[VoiceRecognitionDB.audioScanDB.length][];
		
		for(int i = 0; i <  VoiceRecognitionDB.audioScanDB.length; i++) {
			
			tempResultVerifier[i] = new float[VoiceRecognitionDB.audioScanDB[i].length+4];
			localBestMatch = 0;
			avg = 0;

			for(int j = 0; j <  VoiceRecognitionDB.audioScanDB[i].length; j++) {
							
				Debug.debug(1,"VoiceRecognitionScanCheck VoiceRecognitionDB.audioScanDB[i][j]"
					+VoiceRecognitionDB.audioScanDB[i][j].length  +", checkArray[0].length: "
					+checkArray.length+", checkArray[1].length: "+checkArray.length + ", avg: "+avg);
				
				result = VRSCMainLogic.mainBuilder(checkArray, VoiceRecognitionDB.audioScanDB[i][j]);
								
				match =  result;
				tempResultVerifier[i][j] = match;
				
				avg += match;
				
				Debug.debug(1,"VoiceRecognitionScanCheck VoiceRecognitionDB result1: "+result +", match: "
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
					
					Debug.debug(3, "VoiceRecognitionScanCheck mainVoiceFinder Match Word: "
						+VoiceRecognitionDB.DB_NAMES.get(i)+", avg: "+(j+1)+ ", Added "
						+Arrays.toString(tempResultVerifier[i])+ ", Break\n");
				}			
			}
		}
		
		Debug.debug(3, "VoiceRecognitionScanCheck mainVoiceFinder Actual bestMatch: " 
			+ VoiceRecognitionDB.DB_NAMES.get(globalBestName)+ ", globalBestPosition: "
			+globalBestPosition + ", globalBestValue: " + globalBestValue + ", Voice Finder Elapsed Time: " 
			+(System.currentTimeMillis() - Debug.startTime));
		
		VoiceRecognitionMain.addToCheckResult(id, 3, tempResultVerifier);
		
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
	public String getThreadName() {
		
		return THREAD_NAME;
	}
}