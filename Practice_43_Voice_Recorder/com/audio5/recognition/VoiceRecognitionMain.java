package com.audio5.recognition;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import com.audio0.main.AppSetup;
import com.audio4.audioGramInitializer.mainInit.AudioAnalysisThread;
import com.audio5.recognition.area.VoiceRecognitionAreaCheck;
import com.audio5.recognition.points.VoiceRecognitionPointsCheck;
import com.audio5.recognition.scan.VoiceRecognitionScanCheck;
import com.audio5.recognition.slope.VoiceRecognitionSlopeCheck;
import com.audio8.util.Debug;

public class VoiceRecognitionMain {
	
	public static Map<Integer, VoiceCheckModel> startedVoiceCheck =
					new ConcurrentHashMap<Integer, VoiceCheckModel>();
	public static Map<Integer, String> readedVoiceArrray = 
					new ConcurrentHashMap<Integer, String>();
	public static float[][] sortResult;
	public static float[] mainResult;
	private static float[] tempResult;
	private static int testsLength;
	private static int sortParam = 4; // sort param 4 BestMatch ; sort param 3 Avg; 
	private static int readedVoiceArrrayCounter = 0;
	public static AtomicBoolean building = new AtomicBoolean(false);
	
	private static int debug_level_INFO = 5;
	private static int debud_level_DEBUG = 5;
	
	public static void main(int id) {
		
		if(AudioAnalysisThread.startedVoiceCheck.get(id).getBuild() == false 
			|| !AudioAnalysisThread.startedVoiceCheck.get(id).getNextStage().equals("voiceCheck")) 
				return;
		building.set(true);

		VoiceCheckModel vcModel = new VoiceCheckModel(id,getVoiceRecognitionTestsLength());

		startedVoiceCheck.put(id,vcModel);
		
		initVoiceRecognitionThreads(id);
		
		building.set(false);
	}
	
	private static void initVoiceRecognitionThreads(int id) {
		
		if(AudioAnalysisThread.startedVoiceCheck.get(id).getVoiceReconitionCheckPointsArray() != null) 
			new VoiceRecognitionPointsCheck(id, AudioAnalysisThread
					.startedVoiceCheck.get(id).getVoiceReconitionCheckPointsArray());
				
		if(AudioAnalysisThread.startedVoiceCheck.get(id).getVoiceReconitionCheckSlopesArray() != null)
			new VoiceRecognitionSlopeCheck(id,AudioAnalysisThread
					.startedVoiceCheck.get(id).getVoiceReconitionCheckSlopesArray());
		
		if(AudioAnalysisThread.startedVoiceCheck.get(id).getVoiceReconitionCheckAreaArray() != null)
			new VoiceRecognitionAreaCheck(id,AudioAnalysisThread
					.startedVoiceCheck.get(id).getVoiceReconitionCheckAreaArray());
		
		if(AudioAnalysisThread.startedVoiceCheck.get(id).getVoiceReconitionCheckScanArray() != null)
			new VoiceRecognitionScanCheck(id,AudioAnalysisThread
					.startedVoiceCheck.get(id).getVoiceReconitionCheckScanArray());
	}
		
	private static int getVoiceRecognitionTestsLength() {
		
		testsLength = 0;
		
		if(AppSetup.voicePointsRecognition)
			testsLength++;
			
		if(AppSetup.voiceSlopesRecognition)
			testsLength++;
				
		if(AppSetup.voiceAreaRecognition)
			testsLength++;
		
		if(AppSetup.voiceScanRecognition)
			testsLength++;
		
		Debug.debug(debug_level_INFO, "VoiceRecognitionMain setVoiceRecognitionTestsLength: " +testsLength+ "!");
		
			return testsLength;
	}
	
	public static void addToCheckResult(int id, int index, float[][] result) {
		
		startedVoiceCheck.get(id).setCheckResult(result, index);
		
		if(startedVoiceCheck.get(id).getFinishedTestsCounter() 
			== startedVoiceCheck.get(id).getBaseTestsCounter()) {
			
			buildMainResultBuilder(id);
		}		
	}
	
	private static void buildMainResultBuilder(int id) {
		
		sortResults(id);
		
		mainResult = new float[startedVoiceCheck.get(id).getBaseTestsCounter()*2];
		
		if(startedVoiceCheck.get(id).sortedResults[0] != null) {
			mainResult[0] =
					//startedVoiceCheck.get(id).sortedResults[0][0][startedVoiceCheck.get(id).sortedResults[0][0].length-1-sortParam] *
					AppSetup.VOICE_RECOGNITION_POINTS_CORRECTION_KONSTANT
					* (startedVoiceCheck.get(id).sortedResults[0][0][startedVoiceCheck.get(id).sortedResults[0][0].length-sortParam] / 
					   startedVoiceCheck.get(id).sortedResults[0][1][startedVoiceCheck.get(id).sortedResults[0][1].length-sortParam] );
			Debug.debug(debug_level_INFO, "VoiceRecognitionMain 1 " 
					   +startedVoiceCheck.get(id).sortedResults[0][0][startedVoiceCheck.get(id).sortedResults[0][0].length-sortParam] 
				+ ", 2: "+ startedVoiceCheck.get(id).sortedResults[0][1][startedVoiceCheck.get(id).sortedResults[0][1].length-sortParam]);
			mainResult[1] = startedVoiceCheck.get(id).sortedResults[0][0][startedVoiceCheck.get(id).sortedResults[0][0].length-2];
		}
		
		if(startedVoiceCheck.get(id).sortedResults[1] != null) {
			mainResult[2] = 
					//startedVoiceCheck.get(id).sortedResults[1][0][startedVoiceCheck.get(id).sortedResults[1][0].length-1-sortParam] *
					 AppSetup.VOICE_RECOGNITION_SLOPE_CORRECTION_KONSTANT
					* (startedVoiceCheck.get(id).sortedResults[1][0][startedVoiceCheck.get(id).sortedResults[1][0].length-sortParam] / 
					   startedVoiceCheck.get(id).sortedResults[1][1][startedVoiceCheck.get(id).sortedResults[1][1].length-sortParam] );
			
			mainResult[3] = startedVoiceCheck.get(id).sortedResults[1][0][startedVoiceCheck.get(id).sortedResults[1][0].length-2];
		}
		
		if(startedVoiceCheck.get(id).sortedResults[2] != null) {
			mainResult[4] = 
					//startedVoiceCheck.get(id).sortedResults[2][0][startedVoiceCheck.get(id).sortedResults[2][0].length-1-sortParam] *
					 AppSetup.VOICE_RECOGNITION_AREA_CORRECTION_KONSTANT
					* (startedVoiceCheck.get(id).sortedResults[2][0][startedVoiceCheck.get(id).sortedResults[2][0].length-sortParam] / 
					   startedVoiceCheck.get(id).sortedResults[2][1][startedVoiceCheck.get(id).sortedResults[2][1].length-sortParam] );
						
			mainResult[5] = startedVoiceCheck.get(id).sortedResults[2][0][startedVoiceCheck.get(id).sortedResults[2][0].length-2];
		}
		
		if(startedVoiceCheck.get(id).sortedResults[3] != null) {
			mainResult[6] = 
					//startedVoiceCheck.get(id).sortedResults[3][0][startedVoiceCheck.get(id).sortedResults[3][0].length-1-sortParam] *
					 AppSetup.VOICE_RECOGNITION_SCAN_CORRECTION_KONSTANT
					* (startedVoiceCheck.get(id).sortedResults[3][0][startedVoiceCheck.get(id).sortedResults[3][0].length-sortParam] / 
					   startedVoiceCheck.get(id).sortedResults[3][1][startedVoiceCheck.get(id).sortedResults[3][1].length-sortParam] );
						
			mainResult[7] = startedVoiceCheck.get(id).sortedResults[3][0][startedVoiceCheck.get(id).sortedResults[3][0].length-2];
		}
		
		tempResult = new float[2];
		
		for(int i = 0; i < startedVoiceCheck.get(id).getBaseTestsCounter() * 2; i = i + 2) {
						
			Debug.debug(1, "VoiceRecognitionMain buildMainResultBuilder  mainResult[i] " + mainResult[i]
					+ ", mainResult[i+1] "+mainResult[i+1]);
			if(mainResult[i] > tempResult[0] && mainResult[i] < 100000) {
				tempResult[0] = mainResult[i];
				tempResult[1] = mainResult[i+1];
			}
		}
		
		AudioAnalysisThread.startedVoiceCheck.get(id).setNextStage();
		
		if(VoiceRecognitionDB.DB_NAMES.get((int)tempResult[1])!= null)
		readedVoiceArrray.put(readedVoiceArrrayCounter++, VoiceRecognitionDB.DB_NAMES.get((int)tempResult[1]));
		
		Debug.debug(1, "VoiceRecognitionMain buildMainResultBuilder MatchPercent: "+ tempResult[0] 
			+ ", Match Name-: "+ VoiceRecognitionDB.DB_NAMES.get((int)tempResult[1]));
		
		startedVoiceCheck.remove(id);
	}

	private static void sortResults(int id) {
		
		if(startedVoiceCheck.get(id).getDbCheckResults()[0]!= null)
			startedVoiceCheck.get(id).setSortResult(sort(startedVoiceCheck.get(id).getDbCheckResults()[0], sortParam),0);
		
		if(startedVoiceCheck.get(id).getDbCheckResults()[1]!= null)		
			startedVoiceCheck.get(id).setSortResult(sort(startedVoiceCheck.get(id).getDbCheckResults()[1],sortParam),1);
		
		if(startedVoiceCheck.get(id).getDbCheckResults()[2]!= null)	
			startedVoiceCheck.get(id).setSortResult(sort(startedVoiceCheck.get(id).getDbCheckResults()[2],sortParam),2);
		
		if(startedVoiceCheck.get(id).getDbCheckResults()[3]!= null)	
			startedVoiceCheck.get(id).setSortResult(sort(startedVoiceCheck.get(id).getDbCheckResults()[3],sortParam),3);
	}
	
	private static float[][] sort(float[][] input, int sortParam) {

		sortResult = input;
    	float highest[] = input[0] ;
    	int highestpos = 0;
    	int counter = 0;
		for(int i = 0; i < sortResult.length; i++) {
			
			Debug.debug(debud_level_DEBUG, "VoiceRecognitionMain sort i: "+i +", result[i][result[i].length-sortParam-1] "
					+sortResult[i][sortResult[i].length-sortParam]+ ", Array: "+Arrays.toString(sortResult[i]));
			if(sortResult[i][sortResult[i].length-sortParam] > highest[highest.length-sortParam]) {	
						highest = sortResult[i];
						highestpos = i;
			}
					
			if(i == sortResult.length-1) {
				Debug.debug(debud_level_DEBUG, "VoiceRecognitionMain sort i: "+i +", highest: "+Arrays.toString(highest) +", counter: "+counter 
					+  ", array[counter]: "+ Arrays.toString(sortResult[counter]));
				sortResult[highestpos] = sortResult[counter];
				sortResult[counter++] = highest;
				i = counter;
				highest = sortResult[counter];
				highestpos = counter;
			}
		}		
			return sortResult;
	}	
}
