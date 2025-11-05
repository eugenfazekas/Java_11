package com.audio5.recognition.area;

import java.util.Arrays;

import com.audio5.recognition.VoiceRecognitionDB;
import com.audio5.recognition.VoiceRecognitionMain;
import com.audio7.threads.MyThread;
import com.audio7.threads.ThreadManagement;
import com.audio8.util.Debug;


public class VoiceRecognitionAreaCheck implements MyThread {
	
	private static int id;
	private Thread thread;
	public static boolean threadIsActive;
	public boolean threadIsSuspended;
	final private static String THREAD_NAME = "VoiceRecognitionAreaCheck"; 
	
	private static int checkArray[][][];
	private static float[][] tempResultVerifier;
	private static float  match;	
	private static float localBestMatch;	
	private static int localBestPosition;
	private static float avg;
	private  static int globalBestName;
	private static int globalBestPosition;
	private static float globalBestValue;
	
	public VoiceRecognitionAreaCheck (int Id, int[][][] Check) {
		
		id = Id;
		checkArray = Check;
		
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
	
	private static void mainVoiceFinder(int[][][] checkArray) {
		
		if(checkArray == null) return;
		
		Debug.startTime = System.currentTimeMillis();
			
		tempResultVerifier = new float[VoiceRecognitionDB.audioAreaDB.length][];
		
		for(int i = 0; i <  VoiceRecognitionDB.audioAreaDB.length; i++) {
			
			tempResultVerifier[i] = new float[VoiceRecognitionDB.audioAreaDB[i].length+4];
			localBestMatch = 0;
			avg = 0;

			for(int j = 0; j <  VoiceRecognitionDB.audioAreaDB[i].length; j++) {
				
				match = -1;
				match=(float)VRAMainLogic.mainBuilder(checkArray,VoiceRecognitionDB.audioAreaDB[i][j]);
				tempResultVerifier[i][j] = match;
				avg += match;

				if(match > localBestMatch) {
					localBestMatch = match;
					localBestPosition = j;
					
					if(localBestMatch > globalBestValue) {
							
						globalBestName = i;
						globalBestPosition = j;
						globalBestValue = localBestMatch;
					}			
				}
				
				if(j == VoiceRecognitionDB.audioAreaDB[i].length-1) {
					
					tempResultVerifier[i][j+1] = localBestMatch;
					tempResultVerifier[i][j+2] = (float) avg / (j+1);
					tempResultVerifier[i][j+3] = i;
					tempResultVerifier[i][j+4] = localBestPosition;
					
					Debug.debug(3, "VoiceRecognitionAreaCheck mainVoiceFinder Match Word: "
						+VoiceRecognitionDB.DB_NAMES.get(i)+", avg: "+(j+1)+ ", Added "
						+Arrays.toString(tempResultVerifier[i])+ ", Break\n");
				}						
			}
		}
		
		Debug.debug(3, "VoiceRecognitionAreaCheck mainVoiceFinder Actual bestMatch: " 
			+ VoiceRecognitionDB.DB_NAMES.get(globalBestName)+ ", globalBestPosition: "
			+globalBestPosition+", globalBestValue: "+globalBestValue+", Voice Finder Elapsed Time: " 
			+(System.currentTimeMillis() - Debug.startTime));
		
		Debug.debug(3, "VoiceRecognitionAreaCheck tempResultVerifier.length: " + tempResultVerifier.length);
		
		VoiceRecognitionMain.addToCheckResult(id, 2, tempResultVerifier);
		
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

