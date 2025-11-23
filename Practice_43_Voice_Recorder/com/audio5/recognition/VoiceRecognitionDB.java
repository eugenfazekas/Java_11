package com.audio5.recognition;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.audio0.main.AppSetup;
import com.audio6.audioGramSaver.FileCheckUtil;
import com.audio6.audioGramSaver.FileReaderUtil;
import com.audio8.util.Debug;

public class VoiceRecognitionDB {

	private static boolean instanceOf;
	public static int [][][][]audioPointsDB;
	public static int [][][][] audioSlopeDB;
	public static int [][][][][] audioAreaDB;
	public static int [][][] audioScanDB;
	public static Map<Integer, String> DB_NAMES = new HashMap<>();

	private static String[] tempStringArray1;

	private static int debug_level_INFO = 5;
	
	public static void buildAudioDB() {
		
		if(!AppSetup.voiceRecognition || instanceOf ||
			!FileCheckUtil.pathExist(AppSetup.BASE_AUDIO_PATH+"spektrum/")) 
				return;
		
		instanceOf = true;
			
		String[] voicesNamesList = 
				FileCheckUtil.getFilesOrFoldersList(AppSetup.BASE_AUDIO_PATH+"spektrum/");
		
		List<String> readedPointsFile = null;
		List<String> readedSlopesFile = null;
		List<String> readedAreaFile = null;
		List<String> readedScanFile = null;

		Debug.debug(5,"VoiceRecognition buildAudioDB length: "+ voicesNamesList.length);
		
		audioPointsDB = new int[voicesNamesList.length][][][];
		
		audioSlopeDB = new int[voicesNamesList.length][][][];
		
		audioAreaDB = new int[voicesNamesList.length][][][][];
		
		audioScanDB = new int[voicesNamesList.length][][];
		
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
			
			readedAreaFile = FileReaderUtil.buildStringLinesFromInputStream(
					FileReaderUtil.buildFileStreamFromFile(
							AppSetup.BASE_AUDIO_PATH+"spektrum/"+voicesNamesList[i]
									+"/voiceAreaRecognition/"+voicesNamesList[i]
											+"-voiceAreaRecognition.txt"));
			
			readedScanFile = FileReaderUtil.buildStringLinesFromInputStream(
					FileReaderUtil.buildFileStreamFromFile(
							AppSetup.BASE_AUDIO_PATH+"spektrum/"+voicesNamesList[i]
									+"/voiceScanRecognition/"+voicesNamesList[i]
											+"-voiceScanRecognition.txt"));
			
			DB_NAMES.put(i, voicesNamesList[i]);

			audioPointsDB[i] = new int[readedPointsFile.size()][][];
			audioSlopeDB[i] = new int[readedSlopesFile.size()][][];
			audioAreaDB[i] = new int[readedAreaFile.size()][][][];
			audioScanDB[i] = new int[readedScanFile.size()][];
			
			for(int j = 0 ; j < readedPointsFile.size(); j++ ) {
				
				audioPointsDB[i][j] = new int[2][];
				
				audioSlopeDB[i][j] = new int[2][];
				
				audioAreaDB[i][j] = new int[1][][];
				
				tempStringArray1 = readedPointsFile.get(j).split(";");
				
				audioPointsDB[i][j][0] = VoiceRecognitionDBUtil.convertStringArrayToIntArray(
						buildVoiceSlopeArray(tempStringArray1[0]));
				
				audioPointsDB[i][j][1] = VoiceRecognitionDBUtil.convertStringArrayToIntArray(
						buildVoiceSlopeArray(tempStringArray1[1]));
				
				tempStringArray1 = readedSlopesFile.get(j).split(";");
				
				audioSlopeDB[i][j][0] = VoiceRecognitionDBUtil.convertStringArrayToIntArray(
						buildVoiceSlopeArray(tempStringArray1[0]));
				
				audioSlopeDB[i][j][1] = VoiceRecognitionDBUtil.convertStringArrayToIntArray(
						buildVoiceSlopeArray(tempStringArray1[1]));
				
				audioAreaDB [i][j] = VoiceRecognitionDBUtil.buildThreeDAreaArray(readedAreaFile.get(j));
				
				audioScanDB[i][j] = VoiceRecognitionDBUtil.convertStringArrayToIntArray(
						buildVoiceSlopeArray(readedScanFile.get(j)));
				 
				Debug.debug(debug_level_INFO,"VoiceRecognition DB_NAMES: " +DB_NAMES.get(i) + ", Arr: "
					+Arrays.toString(audioPointsDB[i][j]));
			}
		}
	}	

	private static String[] buildVoiceSlopeArray(String input) {
		
		return input.split(",");	
	}	
}
	
	

