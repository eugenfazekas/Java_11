package com.audio5.recognition;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.audio0.main.AppSetup;
import com.audio8.util.Debug;
import com.audio8.util.StringArrayUtil;
import com.audio8.util.file.FileCheckUtil;
import com.audio8.util.file.FileReaderUtil;

public class VoiceRecognitionDB {

	private static boolean instanceOf;
	public static int [][][] audioPointsDB;
	public static int [][][][] audioSlopeDB;
	public static Map<Integer, String> DB_NAMES = new HashMap<>();

	private static String tempString;
	private static String [] tempStringArray;
	//private static int[][] resultSum;
//	private static int RECOGNITION_PERCENT_LIMIT = 70;
	//private static int BORDER_LIMIT = 20;

	
	public static void buildAudioDB() {
		
		if(!AppSetup.voiceRecognition || instanceOf) return;
		
		instanceOf = true;
			
		String[] voicesNamesList = 
				FileCheckUtil.getFilesOrFoldersList(AppSetup.BASE_AUDIO_PATH+"spektrum/");
		
		List<String> readedPointsFile = null;
		List<String> readedSlopesFile = null;

	
		Debug.debug(5,"VoiceRecognition buildAudioDB length: "+ voicesNamesList.length);
		
		audioPointsDB = new int[voicesNamesList.length][][];
		
		audioSlopeDB = new int[voicesNamesList.length][][][];
		
		for(int i = 0; i < voicesNamesList.length; i++) {
				
			readedPointsFile = FileReaderUtil.buildStringLinesFromInputStream(
					FileReaderUtil.buildFileStreamFromFile(
							AppSetup.BASE_AUDIO_PATH+"spektrum/"+voicesNamesList[i]
									+"/voicePointsRecognition/"+voicesNamesList[i]
											+"-voicePointsRecognition.txt"));
			
			readedSlopesFile = FileReaderUtil.buildStringLinesFromInputStream(
					FileReaderUtil.buildFileStreamFromFile(
							AppSetup.BASE_AUDIO_PATH+"spektrum/"+voicesNamesList[i]
									+"/voiceSlopesRecognition/"+voicesNamesList[i]
											+"-voiceSlopesRecognition.txt"));
			
			DB_NAMES.put(i, voicesNamesList[i]);

			audioPointsDB[i] = new int[readedPointsFile.size()][];
			audioSlopeDB[i] = new int[readedPointsFile.size()][][];
			
			for(int j = 0 ; j < readedPointsFile.size(); j++ ) {
				
				audioSlopeDB[i][j] = new int[2][];
				
				tempStringArray = readedSlopesFile.get(j).split(";");
				
				audioPointsDB[i][j] 
						= StringArrayUtil.convertStringArrayToIntArray(
								buildVoicePointArray(readedPointsFile.get(j)));
				
				audioSlopeDB[i][j][0] = StringArrayUtil.convertStringArrayToIntArray(
						buildVoiceSlopeArray(tempStringArray[0]));
				
				audioSlopeDB[i][j][1] = StringArrayUtil.convertStringArrayToIntArray(
						buildVoiceSlopeArray(tempStringArray[1]));
				 
//				for(int k = 0; k < voiceNamesArrayList.length; k++ ) {
//					 
//					audioPointsDB[i][j][k] = Integer.valueOf(voiceNamesArrayList[k]);
					Debug.debug(3,"VoiceRecognition DB_NAMES: " +DB_NAMES.get(i) + ", Arr: "+Arrays.toString(audioPointsDB[i][j] ));
//				}
			}
		}
	}	
	
	private static String[] buildVoicePointArray(String input) {
	
		tempString = input.substring(1, (input.length()-1));
		 
			return tempString.split(", ");	
	}
	
	private static String[] buildVoiceSlopeArray(String input) {
		
		return input.split(",");	
	}
	
	public static void voiceFinder(int[] checkArray) {

	}
}
	
	

