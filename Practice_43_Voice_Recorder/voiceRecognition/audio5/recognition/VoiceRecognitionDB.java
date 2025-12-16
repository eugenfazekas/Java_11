package voiceRecognition.audio5.recognition;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import voiceRecognition.audio0.main.AppSetup;
import voiceRecognition.audio6.audioGramSaver.FileCheckUtil;
import voiceRecognition.audio6.audioGramSaver.FileReaderUtil;
import voiceRecognition.audio8.util.Debug;

public class VoiceRecognitionDB {

	private static boolean instanceOf;
	public static int [][][][]audioPointsDB;
	public static int [][][][] audioSlopeDB;
	public static int [][][][][] audioAreaDB;
	public static int [][][] audioScanDB;
	public static int [][][][] audioFrequencyGramDB;
	public static Map<Integer, String> DB_NAMES = new HashMap<>();

	private static String[] tempStringArray1;

	private static int debug_level_INFO = 1;
	
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
		List<String> readedFrequencyGramFile = null;

		Debug.debug(5,"VoiceRecognition buildAudioDB length: "+ voicesNamesList.length);
		
		audioPointsDB = new int[voicesNamesList.length][][][];
		
		audioSlopeDB = new int[voicesNamesList.length][][][];
		
		audioAreaDB = new int[voicesNamesList.length][][][][];
		
		audioScanDB = new int[voicesNamesList.length][][];
		
		audioFrequencyGramDB = new int[voicesNamesList.length][][][];
		
		
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
			
			readedFrequencyGramFile = FileReaderUtil.buildStringLinesFromInputStream(
					FileReaderUtil.buildFileStreamFromFile(
							AppSetup.BASE_AUDIO_PATH+"spektrum/"+voicesNamesList[i]
									+"/voiceFrequencyGramRecognition/"+voicesNamesList[i]
											+"-voiceFrequencyGramRecognition.txt"));
			
			DB_NAMES.put(i, voicesNamesList[i]);

			audioPointsDB[i] = new int[readedPointsFile.size()][][];
			audioSlopeDB[i] = new int[readedSlopesFile.size()][][];
			audioAreaDB[i] = new int[readedAreaFile.size()][][][];
			audioScanDB[i] = new int[readedScanFile.size()][];
			audioFrequencyGramDB[i] = new int[readedFrequencyGramFile.size()][][];
			
			for(int j = 0 ; j < readedPointsFile.size(); j++ ) {
				
				audioPointsDB[i][j] = new int[2][];
				
				audioSlopeDB[i][j] = new int[2][];
								
				audioFrequencyGramDB[i][j] = new int[1][];
				
				tempStringArray1 = readedPointsFile.get(j).split(";");
				
				audioPointsDB[i][j][0] = VoiceRecognitionDBUtil.convertStringArrayToIntArray(
						commaSplitArray(tempStringArray1[0]));
				
				audioPointsDB[i][j][1] = VoiceRecognitionDBUtil.convertStringArrayToIntArray(
						commaSplitArray(tempStringArray1[1]));
				
				tempStringArray1 = readedSlopesFile.get(j).split(";");
				
				audioSlopeDB[i][j][0] = VoiceRecognitionDBUtil.convertStringArrayToIntArray(
						commaSplitArray(tempStringArray1[0]));
				
				audioSlopeDB[i][j][1] = VoiceRecognitionDBUtil.convertStringArrayToIntArray(
						commaSplitArray(tempStringArray1[1]));
				
				audioAreaDB [i][j] = VoiceRecognitionDBUtil.buildThreeDAreaArray(readedAreaFile.get(j));
				
				audioScanDB[i][j] = VoiceRecognitionDBUtil.convertStringArrayToIntArray(
						commaSplitArray(readedScanFile.get(j)));

				audioFrequencyGramDB[i][j] =  VoiceRecognitionDBUtil.buildThreeDFrequencyGram(
						   (readedFrequencyGramFile.get(j)));
				 
				Debug.debug(debug_level_INFO,"VoiceRecognition DB_NAMES: " +DB_NAMES.get(i) + ", Arr: "
					+Arrays.toString(audioPointsDB[i][j]));
			}
		}
	}	

	private static String[] commaSplitArray(String input) {
		
		return input.split(",");	
	}	
}
	
	

