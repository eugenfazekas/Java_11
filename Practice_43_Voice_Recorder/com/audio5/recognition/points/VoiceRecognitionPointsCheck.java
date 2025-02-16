package com.audio5.recognition.points;

import java.util.Arrays;

import com.audio5.recognition.VoiceRecognitionDB;
import com.audio5.recognition.VoiceRecognitionMain;
import com.audio8.util.Debug;
import com.audio8.util.thread.MyThread;
import com.audio8.util.thread.ThreadManagement;
import com.audio8.util.thread.ThreadUtil;

public class VoiceRecognitionPointsCheck implements MyThread{

	private static int id;
	private static int[] checkArray;
	private static int[][] tempResultVerifier;
	private static float matchCounter;
	private static int localBestMatch;	
	private static int localBestPosition;	
	public  static int globalBestName;
	public static int globalBestPosition;
	private static int globalBestValue;
	private static int avgCounter;
	private Thread thread;
	public static boolean threadIsActive;
	public boolean threadIsSuspended;
	
	public VoiceRecognitionPointsCheck(int Id, int[] inputArray) {
		
		resetVariables();
		
		id = Id;
		checkArray = inputArray;
		threadIsActive = true;
		threadIsSuspended = false;
		thread = new Thread(this);
		thread.start();
	}
		
	@Override
	public void run() {
		
		Thread.currentThread().setName("VoiceRecognitionPointsCheck");
		
		ThreadManagement.threads.add(this);
		ThreadUtil.suspendThreadCheck(this);
		
		Debug.debug(1, "Starting VoiceRecognitionPointsCheck Thread!");
		
		while(threadIsActive)
			
			mainVoiceFinder(checkArray);
		
		thread = null;		
	}
	
	private static void mainVoiceFinder(int[] checkArray) {
		
		if(checkArray == null) return;
		
		Debug.startTime = System.currentTimeMillis();
		
		Debug.debug(5,"VoiceRecognitionPointsCheck mainVoiceFinder checkArray.length: " +checkArray.length);
	
		tempResultVerifier = new int[VoiceRecognitionDB.audioPointsDB.length][];
		globalBestValue = 0;
		
		for(int i = 0; i < VoiceRecognitionDB.audioPointsDB.length; i++) {
			
			tempResultVerifier[i] = new int[VoiceRecognitionDB.audioPointsDB[i].length+4];
			avgCounter = 0;
			localBestMatch = 0;
			
			for(int j = 0; j < VoiceRecognitionDB.audioPointsDB[i].length; j++) {	
				
				//System.out.println("audioDB.length: "+audioDB.length + " audioDB[i].length "
				//+audioDB[i].length + " i: "+ i + " j: "+j);
				
				matchCounter = VRPmainLogic.mainLogic(VoiceRecognitionDB.audioPointsDB[i][j], checkArray,12,i,j);			
				
				Debug.debug(3, "VoiceRecognitionPointsCheck mainVoiceFinder DB_NAMES.get(i) "+VoiceRecognitionDB.DB_NAMES.get(i)+" matchCounter: "+matchCounter);
				
				tempResultVerifier[i][j] = (int) matchCounter;
				avgCounter += matchCounter;
				
				if(matchCounter > localBestMatch) {
					
					localBestMatch = (int) matchCounter;
					localBestPosition = j;
					if(localBestMatch > globalBestValue) {
						
						globalBestName = i;
						globalBestPosition = j;
						globalBestValue = localBestMatch;
					}
					//MiscellaneousData.bestDBMatchArray = VoiceRecognitionDB.audioPointsDB[i][j];
					
					Debug.debug(3, "VoiceRecognitionPointsCheck mainVoiceFinder New Best match: " 
						+ localBestMatch + " position "+ j+ ", globalBestName: " + globalBestName
						+ ", globalBestPosition: "+globalBestPosition + ", globalBestValue: " 
						+ globalBestValue);
				}						
	
				if(j == VoiceRecognitionDB.audioPointsDB[i].length-1) {
					
					tempResultVerifier[i][j+1] = localBestMatch;
					tempResultVerifier[i][j+2] = avgCounter / j;
					tempResultVerifier[i][j+3] = i;
					tempResultVerifier[i][j+4] = localBestPosition;
					
					Debug.debug(3, "VoiceRecognitionPointsCheck mainVoiceFinder Match Word: "+VoiceRecognitionDB.DB_NAMES.get(i)
					 + ", Added "+Arrays.toString(tempResultVerifier[i])+ ", Break\n");
				}				
			}
			
			Debug.debug(5,"VoiceRecognitionPointsCheck mainVoiceFinder End Match Word: "+VoiceRecognitionDB.DB_NAMES.get(i)
				+", matchCounter "+matchCounter);			
		}
		 VoiceRecognitionMain.startedVoiceCheck.get(id).setPointsResultVerifier(tempResultVerifier);
		 VoiceRecognitionMain.checkTestEnd(id);

		
		//VoiceRecognitionMain.readedVoiceArrray.add(globalBestMatch);
		
		Debug.debug(3, "VoiceRecognitionPointsCheck mainVoiceFinder Actual bestMatch: " + VoiceRecognitionDB.DB_NAMES.get(globalBestName)
			+ ", globalBestPosition: "+globalBestPosition + ", globalBestValue: " 
			+ globalBestValue + ", Voice Finder Elapsed Time: " +(System.currentTimeMillis() - Debug.startTime));
		
		threadIsActive = false;
	}
	
	public static void resetVariables() {
		
		tempResultVerifier = null;
		matchCounter = 0;
		localBestMatch = 0;	
		localBestPosition = 0;
		globalBestName = 0;
	    globalBestPosition = 0;
		globalBestValue = 0;
		avgCounter = 0;
	}

	@Override
	public void stopThread() {	
		
		threadIsActive = false;
	}
	
	@Override
	public void suspendThread() {
		
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
}
