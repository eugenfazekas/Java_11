package com.audio5.recognition;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.audio0.main.AppSetup;
import com.audio1.logical.EntryPointMethods;
import com.audio4.audioGramInitializer.AudioAnalysisThread;
import com.audio5.recognition.points.VoiceRecognitionPointsCheck;
import com.audio5.recognition.slope.VoiceRecognitionSlopeCheck;
import com.audio8.util.Debug;

public class VoiceRecognitionMain {
	
    public static int id = 0;
	public static Map<Integer, VoiceCheckModel> startedVoiceCheck = new ConcurrentHashMap<Integer, VoiceCheckModel>();
	public static Map<Integer, String> readedVoiceArrray = new ConcurrentHashMap<Integer, String>();
	public static int[][] result;
	private static int testsLength = 0;
	private static int sortParam = 2;
	
	public static void main(int id) {
		
		if(AudioAnalysisThread.startedVoiceCheck.get(id).getBuild() == false 
			|| !AudioAnalysisThread.startedVoiceCheck.get(id).getNextStage().equals("voiceCheck")) 
				return;

		setVoiceRecognitionTestsLength();
		
		AudioAnalysisThread.startedVoiceCheck.get(id).setNextStage("pending");
			
		Debug.debug(3, "VoiceRecognitionMain main WaveStream().length: "
			+AudioAnalysisThread.startedVoiceCheck.get(id).getWaveStream().length);
		
		VoiceCheckModel vcModel = new VoiceCheckModel(id);
		
		if(AudioAnalysisThread.startedVoiceCheck.get(id).getVoiceReconitionCheckPointsArray() != null)
			new VoiceRecognitionPointsCheck(vcModel.getId(), AudioAnalysisThread
					.startedVoiceCheck.get(id).getVoiceReconitionCheckPointsArray());
		
		if(AudioAnalysisThread.startedVoiceCheck.get(id).getVoiceReconitionCheckSlopesArray() != null)
			new VoiceRecognitionSlopeCheck(vcModel.getId(),AudioAnalysisThread
					.startedVoiceCheck.get(id).getVoiceReconitionCheckSlopesArray());
		
		startedVoiceCheck.put(id,vcModel);
	}
	
	private static void setVoiceRecognitionTestsLength() {
		
		if(AppSetup.voicePointsRecognition)
			testsLength++;
			
		if(AppSetup.voiceSlopesRecognition)
			testsLength++;
		
		Debug.debug(1, "VoiceRecognitionMain setVoiceRecognitionTestsLength: " +testsLength+ "!");
	}
	
	
	private static void addToResultArray(int id, int[][] resultFromDbCheck) {
		
		int existCounter;
		int chekResultLength = 	resultFromDbCheck.length < 3 ? resultFromDbCheck.length : 3;
		Debug.debug(1, "VoiceRecognitionMain evoluatorResult.size: "+startedVoiceCheck.get(id).evoluatorResult.size() + ", id: "+id);
			for(int i = 0; i < chekResultLength; i++) {
				
				existCounter = 0;
				
				for(Map.Entry<Integer, int[]> result : startedVoiceCheck.get(id).evoluatorResult.entrySet()){
					
					if(resultFromDbCheck[i][resultFromDbCheck[i].length-2] == result.getKey()) {
						
						Debug.debug(1, "VoiceRecognitionMain addToResultArray i: "+resultFromDbCheck[i][resultFromDbCheck[i].length-2] 
							+ ", result.getKey(): "+result.getKey());
						existCounter++;
					}
				}
				
				if(existCounter == 0) {
					startedVoiceCheck.get(id).evoluatorResult.put(resultFromDbCheck[i][resultFromDbCheck[i].length-2]
							, new int[] {0,resultFromDbCheck[i][resultFromDbCheck[i].length-2],resultFromDbCheck[i][resultFromDbCheck[i].length-1]});
					
					Debug.debug(1, "VoiceRecognitionMain addToResultArray! "+resultFromDbCheck[i][resultFromDbCheck[i].length-2]
						+ " "+ resultFromDbCheck[i][resultFromDbCheck[i].length-1]);
				}
			}
	}
	
	private static void buildResultPodium(int id) {
		
		if(AppSetup.voicePointsRecognition) 		
			addToResultArray(id, startedVoiceCheck.get(id).pointsResultVerifier);
		
		if(AppSetup.voiceSlopesRecognition) 		
			addToResultArray(id, startedVoiceCheck.get(id).slopesResultVerifier);
		
		
		Debug.debug(1, "VoiceRecognitionMain buildResultPodium!  id: "+id);
	}
	
	private static void compareResult(int[][] inputResult) {
		
		Debug.debug(1, "VoiceRecognitionMain compareResult! id: "+id);
		
		for(Map.Entry<Integer, int[]> result : startedVoiceCheck.get(id).evoluatorResult.entrySet()) {
			
			for(int i = 0; i < inputResult.length; i++) {
				
				if(result.getKey() == inputResult[i][inputResult[i].length-2]) 
					result.setValue(new int[] {result.getValue()[0] +  10 - i * 2, result.getValue()[1], result.getValue()[2]});				
			}
		}
		
		Debug.debug(1, "VoiceRecognitionMain compareResult!");
	}
	
	private static void evaulateResult(int id) {
		
		int key = 0;
		int value = 0;
		
		buildResultPodium(id);
		
		if(AppSetup.voicePointsRecognition && startedVoiceCheck.get(id).pointsResultVerifier != null) 	
			compareResult(startedVoiceCheck.get(id).pointsResultVerifier);
		
		if(AppSetup.voiceSlopesRecognition &&  startedVoiceCheck.get(id).slopesResultVerifier != null)
			compareResult(startedVoiceCheck.get(id).slopesResultVerifier);
		
		for(Map.Entry<Integer, int[]> result : startedVoiceCheck.get(id).evoluatorResult.entrySet()) {
			
			if(result.getValue()[0] > value) {
				
				key = result.getKey();
				value = result.getValue()[0];
			}
		}
		
		resultValidityCheck(id);
		
		Debug.debug(1, "VoiceRecognitionMain evoluatorResult key[0]: "+startedVoiceCheck.get(id).evoluatorResult.get(key)[0] 
				+ ", evoluatorResult key[1]: "+startedVoiceCheck.get(id).evoluatorResult.get(key)[1] + ", Rank 1 Match: "+
				VoiceRecognitionDB.DB_NAMES.get(startedVoiceCheck.get(id).evoluatorResult.get(key)[1]));
		
		Debug.debug(1, "VoiceRecognitionMain evoluatorResult DB length: "+VoiceRecognitionDB.audioPointsDB[startedVoiceCheck.get(id).evoluatorResult.get(key)[1]]
				[startedVoiceCheck.get(id).evoluatorResult.get(key)[2]].length + ", Array: "
				+Arrays.toString(VoiceRecognitionDB.audioPointsDB[startedVoiceCheck.get(id).evoluatorResult.get(key)[1]]
				[startedVoiceCheck.get(id).evoluatorResult.get(key)[2]]));
		
		Debug.debug(1, "VoiceRecognitionMain evoluatorResult AudioObject: "+ AudioAnalysisThread.startedVoiceCheck.get(id).toString());
		
		AudioAnalysisThread.startedVoiceCheck.get(id).setPointsBestDBMatchArray(
			VoiceRecognitionDB.audioPointsDB[startedVoiceCheck.get(id).evoluatorResult.get(key)[1]]
					[startedVoiceCheck.get(id).evoluatorResult.get(key)[2]]);
		
		Debug.debug(1,"VoiceRecognitionMain evaulateResult Best result: "+ VoiceRecognitionDB.DB_NAMES.get(key) 
			+ ", position: "+startedVoiceCheck.get(id).evoluatorResult.get(key)[1]);
	}
	
	public static void checkTestEnd(int id) {
				
		Debug.debug(1,"VoiceRecognitionMain checkTestEnd id: "+id 
			+", startedVoiceCheck.get(id).getTestsCounter(): "
				+startedVoiceCheck.get(id).getTestsCounter() + ", testsLength: "+testsLength);
		
		if(startedVoiceCheck.get(id).getTestsCounter() == testsLength) {
			
			if(AppSetup.voicePointsRecognition)
				startedVoiceCheck.get(id).setPointsResultVerifier(sort(startedVoiceCheck.get(id).getPointsResultVerifier(),sortParam));
			
			if(AppSetup.voiceSlopesRecognition)
				startedVoiceCheck.get(id).setSlopesResultVerifier(sort(startedVoiceCheck.get(id).getSlopesResultVerifier(),sortParam));
			
			if((testsLength == 1 && startedVoiceCheck.get(id).pointsResultVerifier != null ) || (testsLength == 2 
					&& startedVoiceCheck.get(id).pointsResultVerifier != null  && startedVoiceCheck.get(id).slopesResultVerifier != null))
			evaulateResult(id);
			
			if(EntryPointMethods.getSvitch().equals("voiceRecognition"))
				AudioAnalysisThread.startedVoiceCheck.get(id).setNextStage("analysis");
			
			if(EntryPointMethods.getSvitch().equals("voiceRecognitionDebug"))
				AudioAnalysisThread.startedVoiceCheck.get(id).setNextStage("save");
		}
	}
	
	private static void resultValidityCheck(int id) {
		
		Debug.debug(1,"VoiceRecognitionMain resultValidityCheck validity: "+((float)startedVoiceCheck
			.get(id).getPointsResultVerifier()[0][startedVoiceCheck.get(id).getPointsResultVerifier()[0].length - sortParam-1] 
				/ startedVoiceCheck.get(id).getPointsResultVerifier()[1][startedVoiceCheck.get(id).getPointsResultVerifier()[1].length - sortParam-1]));
	}

	public static int[][] sort(int[][] input, int sortParam) {
		
		result = input;
    	int highest[] = input[0] ;
    	int highestpos = 0;
    	int counter = 0;
		for(int i = 0; i < result.length; i++) {
			
			Debug.debug(3, "VoiceRecognitionMain sort i: "+i +", result[i][result[i].length-sortParam-1] "
					+result[i][result[i].length-sortParam-1]+ ", Array: "+Arrays.toString(result[i]));
			if(result[i][result[i].length-sortParam-1] > highest[highest.length-sortParam-1]) {	
						highest = result[i];
						highestpos = i;
			}
					
			if(i == result.length-1) {
				Debug.debug(3, "VoiceRecognitionMain sort i: "+i +", highest: "+Arrays.toString(highest) +", counter: "+counter +  ", array[counter]: "+ Arrays.toString(result[counter]));
				result[highestpos] = result[counter];
				result[counter++] = highest;
				i = counter;
				highest = result[counter];
				highestpos = counter;
			}
		}
		
		return result;
	}	
}
