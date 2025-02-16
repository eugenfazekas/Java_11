package com.audio5.recognition.slope;

import java.util.Arrays;

import com.audio5.recognition.VoiceRecognitionDB;
import com.audio5.recognition.VoiceRecognitionMain;
import com.audio8.util.Debug;
import com.audio8.util.thread.MyThread;
import com.audio8.util.thread.ThreadManagement;
import com.audio8.util.thread.ThreadUtil;

public class VoiceRecognitionSlopeCheck implements MyThread{

	private static int id;
	private Thread thread;
	public static boolean threadIsActive;
	public boolean threadIsSuspended;
	
	private static int checkArray[][];
	private static int[][] tempResultVerifier;
	private static int[] baseResult;
	private static int result1;
	private static int result2;
	private static int match;	
	private static int localBestMatch;	
	private static int localBestPosition;
	private static int avg;
	private  static int globalBestName;
	private static int globalBestPosition;
	private static int globalBestValue;
	
	public VoiceRecognitionSlopeCheck(int Id ,int[][] inputArray) {
		
		id = Id;
		checkArray = inputArray;
		threadIsActive = true;
		threadIsSuspended = false;
		thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void run() {
		
		Thread.currentThread().setName("VoiceRecognitionSlopeCheck");
		
		ThreadManagement.threads.add(this);
		ThreadUtil.suspendThreadCheck(this);
		
		Debug.debug(1, "Starting VoiceRecognitionPointsCheck Thread!");
		
		while(threadIsActive)
			
			mainVoiceFinder(checkArray);
		
		thread = null;	
		
	}
	
	private static void mainVoiceFinder(int[][] checkArray) {
		
		if(checkArray == null) return;
		
		Debug.startTime = System.currentTimeMillis();
			
		tempResultVerifier = new int[VoiceRecognitionDB.audioSlopeDB.length][];
		
		for(int i = 0; i <  VoiceRecognitionDB.audioSlopeDB.length; i++) {
			
			tempResultVerifier[i] = new int[VoiceRecognitionDB.audioSlopeDB[i].length+4];
			localBestMatch = 0;
			avg = 0;

			for(int j = 0; j <  VoiceRecognitionDB.audioSlopeDB[i].length; j++) {
							
				Debug.debug(1,"VoiceRecognitionDB.audioSlopeDB[i][j][0]"+VoiceRecognitionDB.audioSlopeDB[i][j][0].length 
						+", VoiceRecognitionDB.audioSlopeDB[i][j][1]: "+VoiceRecognitionDB.audioSlopeDB[i][j][1].length
						+", checkArray[0].length: "+checkArray[0].length+", checkArray[1].length: "+checkArray[1].length + ", avg: "+avg);
				
				baseResult = VRSmainLogic.mainLogic(VoiceRecognitionDB.audioSlopeDB[i][j][0],
					VoiceRecognitionDB.audioSlopeDB[i][j][1], checkArray[0], checkArray[1], 8);
				

				result1 = baseResult[0] < baseResult[1] ? baseResult[0] : baseResult[1];	
				result2 = baseResult[0] > baseResult[1] ? baseResult[0] : baseResult[1];	
				match = ((result1 * 10)*(result2 /1000))/100000;
				tempResultVerifier[i][j] = match;
				avg += match;
				Debug.debug(1,"VoiceRecognitionDB result1: "+result1+ ", result2: "+ result2 +", match: "+match + ", avg: "+avg+", r1 *10 "+
						(result1 * 10) + ", r2 /10 " +(result2 /10) +", sum "+ ((result1 * 10)*(result2 /1000))/100000);
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
					tempResultVerifier[i][j+2] = avg / (j+1);
					tempResultVerifier[i][j+3] = i;
					tempResultVerifier[i][j+4] = localBestPosition;
					
					Debug.debug(3, "VoiceRecognitionSlopeCheck mainVoiceFinder Match Word: "+VoiceRecognitionDB.DB_NAMES.get(i)
					 +", avg: "+(j+1)+ ", Added "+Arrays.toString(tempResultVerifier[i])+ ", Break\n");
				}			
			}
		}
		
		Debug.debug(3, "VoiceRecognitionSlopeCheck mainVoiceFinder Actual bestMatch: " + VoiceRecognitionDB.DB_NAMES.get(globalBestName)
			+ ", globalBestPosition: "+globalBestPosition + ", globalBestValue: " 
			+ globalBestValue + ", Voice Finder Elapsed Time: " +(System.currentTimeMillis() - Debug.startTime));
		
		VoiceRecognitionMain.startedVoiceCheck.get(id).setSlopesResultVerifier(tempResultVerifier);
		VoiceRecognitionMain.checkTestEnd(id);
		
		threadIsActive = false;
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
