package com.voiceRecognition.audio1.logical;


public class AllFeaturesEntryPoint {
	
	static String[] saveNamedRecords_Igen = new String[] {"saveNamedRecords", "Igen"};
	static String[] saveNamedRecords_Nem = new String[] {"saveNamedRecords", "Nem"};
	static String[] saveNamedRecords_Teszt = new String[] {"saveNamedRecords", "Teszt"};
	static String[] voiceRecognition = new String[] {"voiceRecognition", "Teszt"};
	static String[] voiceRecognitionDebug = new String[] {"voiceRecognitionDebug", "Teszt"};
	static String[] saveContinueRecords = new String[] {"saveContinueRecords", ""};
	static String[] buildSequenceFromFile = new String[] {"buildSequenceFromFile", "aaa.txt"};
	static String[] buildTestAudioSequence = new String[] {"buildTestAudioSequence", "Teszt"};
	static String[] buildSpektrogramFromAudioFile = new String[] {"buildSpektrogramFromAudioFile", "igen.wav"};
	static String[] timeFixedSoundRecorder = new String[] {"timeFixedSoundRecorder", "PathForFixedRecord"};
	
	
	private static String[] testArgs = voiceRecognition;
		
	public static void mainLogicalEntryPoint(String[] args) {
				
		if(args.length > 0) {

			testArgs[0] = args[0] != null ? args[0] :testArgs[0];	
			testArgs[1] = args[1] != null ? args[1] : "";			
		}
		
		EntryPointMethods.mainOptionRun(testArgs[0], testArgs[1]);	
				
	}
}
