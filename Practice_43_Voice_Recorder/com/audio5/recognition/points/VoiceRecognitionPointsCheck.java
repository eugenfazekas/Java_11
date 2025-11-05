package com.audio5.recognition.points;

import java.util.Arrays;

import com.audio5.recognition.VoiceRecognitionDB;
import com.audio5.recognition.VoiceRecognitionMain;
import com.audio7.threads.MyThread;
import com.audio7.threads.ThreadManagement;
import com.audio8.util.Debug;

public class VoiceRecognitionPointsCheck implements MyThread{

	private static int id;
	private Thread thread;
	public static boolean threadIsActive;
	public boolean threadIsSuspended;
	final private static String THREAD_NAME = "VoiceRecognitionPointsCheck"; 
	
	private static int[][] checkArray;
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

	public VoiceRecognitionPointsCheck(int Id, int[][] inputArray) {
		
		Debug.debug(1, "Starting VoiceRecognitionPointsCheck 0! Input Array: " 
				+Arrays.toString(checkArray) );
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
	
	private static void mainVoiceFinder(int[][] checkArray) {
		
		if(checkArray == null) return;
		
		Debug.startTime = System.currentTimeMillis();
		
		Debug.debug(5,"VoiceRecognitionPointsCheck mainVoiceFinder checkArray.length: " 
				+checkArray.length);
	
		tempResultVerifier = new float[VoiceRecognitionDB.audioPointsDB.length][];
		globalBestValue = 0;
		
		for(int i = 0; i < VoiceRecognitionDB.audioPointsDB.length; i++) {
			
			tempResultVerifier[i] = new float[VoiceRecognitionDB.audioPointsDB[i].length+4];
			localBestMatch = 0;
			avg = 0;
			
			for(int j = 0; j < VoiceRecognitionDB.audioPointsDB[i].length; j++) {	
				
				//System.out.println("audioDB.length: "+audioDB.length + " audioDB[i].length "
				//+audioDB[i].length + " i: "+ i + " j: "+j);
				
				result1 = VRPMainLogic.mainBuilder(checkArray[0],
						VoiceRecognitionDB.audioPointsDB[i][j][0]);
					
				result2 = VRPMainLogic.mainBuilder(checkArray[1],
						VoiceRecognitionDB.audioPointsDB[i][j][1]);
						
					match = (  ( (float)  result1 / 100) * ( (float) result2 / 100 )  ) *100;;
					tempResultVerifier[i][j] = match;			
					
					avg += match;
					
					Debug.debug(1,"VoiceRecognitionDB result1: "+result1+ ", result2: "+ result2 
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
	
				if(j == VoiceRecognitionDB.audioPointsDB[i].length-1) {
					
					tempResultVerifier[i][j+1] = localBestMatch;
					tempResultVerifier[i][j+2] = (float) avg / (j+1);
					tempResultVerifier[i][j+3] = i;
					tempResultVerifier[i][j+4] = localBestPosition;
					
					Debug.debug(3, "VoiceRecognitionPointsCheck mainVoiceFinder Match Word: "
						+VoiceRecognitionDB.DB_NAMES.get(i) + ", Added "
						+Arrays.toString(tempResultVerifier[i])+ ", Break\n");
				}				
			}
			
			Debug.debug(5,"VoiceRecognitionPointsCheck mainVoiceFinder End Match Word: "
				+VoiceRecognitionDB.DB_NAMES.get(i)+", matchCounter "+match);			
		}
		
		//VoiceRecognitionMain.readedVoiceArrray.add(globalBestMatch);
		
		Debug.debug(3, "VoiceRecognitionPointsCheck mainVoiceFinder Actual bestMatch: " 
			+ VoiceRecognitionDB.DB_NAMES.get(globalBestName)
			+ ", globalBestPosition: "+globalBestPosition + ", globalBestValue: " 
			+ globalBestValue + ", Voice Finder Elapsed Time: " 
			+(System.currentTimeMillis() - Debug.startTime));
		
		VoiceRecognitionMain.addToCheckResult(id, 0, tempResultVerifier);
		
		threadIsActive = false;
	}
	
	public static void resetVariables() {
		
		tempResultVerifier = null;
		match = 0;
		localBestMatch = 0;	
		localBestPosition = 0;
		globalBestName = 0;
	    globalBestPosition = 0;
		globalBestValue = 0;
		avg= 0;
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
