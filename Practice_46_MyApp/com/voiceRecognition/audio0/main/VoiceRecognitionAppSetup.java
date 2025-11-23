package com.voiceRecognition.audio0.main;

/*
 * These parameters are final or Runtime parameters.
 * 
 * STARTER PARAMETERS DEFINED IN EntrypointMethod CLASS
 */
public class VoiceRecognitionAppSetup {
	
	//AudioListener
	public static int RECORDER_MILISEC_BUFFER_LENGTH = 300;
	
	public static float FFT_MAGNITUDE_LIMIT = 0.8f;
	
	//Recomended lower then 500 // Build Sequence must have 2000!
	public static int FFT_UPPER_LIMIT_CHECK = 500;
	
	public static float AMPLITUDE_AVERAGE_CORRECTION_KONSTANT = 1.5f;
	
	public static float VOICE_RECOGNITION_POINTS_CORRECTION_KONSTANT = 1.0f;
	public static float VOICE_RECOGNITION_SLOPE_CORRECTION_KONSTANT = 1.0f;
	public static float VOICE_RECOGNITION_AREA_CORRECTION_KONSTANT = 1.0f;
	public static float VOICE_RECOGNITION_SCAN_CORRECTION_KONSTANT = 1.0f;

	// RFINERY
	// AMPLITUDE REFINARY USELESS WHEN ANY KIND OF WIND PRESENT
	public static int IDLE_AMPLITUDE_VOLUME;
	public static float START_SOUND_THRESHOLD_MULTIPLIER = 2f;
	public static float END_SOUND_THRESHOLD_MULTIPLIER = 1f;
	public final static int AUDIO_BUFFER_MILISEC_LENGTH = 10;
	public final static int AUDIO_BUFFER_MILISEC_BEFORE_LENGTH = 300;
	public final static int AUDIO_BUFFER_MILISEC_AFTER_LENGTH = 600;
	public final static int START_OPTIMIZATION_CYCLES = 3;
	public final static int RECORD_BUFFER_LENGTH = 100;	
	// PRETRIM common	
	public final static int START_LENGTH_CHECK = 50;
	public final static int END_LENGTH_CHECK = 200;
	public final static int START_LENGTH_CORRECTION = 50;
	public final static int END_LENGTH_CORRECTION = 50;
	public final static int START_BEFORE_MSEC_TRIM_LENGTH = 100;
	public final static int AFTER_END_MSEC_TRIM_LENGTH = 100;
	
	// PRETRIM Frequency based
	public final static int START_FREQUENCY_LOWER_LIMIT = 100;
	public final static int START_FREQUENCY_UPPER_LIMIT = 1000;
	public final static int END_FREQUENCY_LOWER_LIMIT = 1000;
	public final static int END_FREQUENCY_UPPER_LIMIT = 11025;
	
	// Only one refinary option must used.
	public static boolean amplitudeRefinary;
	public static boolean frequencyRefinary;
	
	// PRETRIM
	public static boolean preTrim;
	public static boolean amplitudeTrim;
	public static boolean frequencyTrim;
	public static boolean continueWithNoTrim; 
	
	//Data Analysis
	public static boolean amplitudeGram;
	public static boolean frequencyGram;
	public static boolean multiAnalysis;
	public static boolean mySpektrogram;
	public static boolean spektrogram;
	public static boolean buildSequenceArray;
	public static boolean rawAudioData;
	public static boolean voiceRecognitionData;
	public static boolean voiceRecognitionPointsData;
	public static boolean voiceRecognitionSlopesData;
	public static boolean voiceRecognitionAreaData;
	public static boolean voiceRecognitionScanData;
	public static boolean wave;
	public static boolean deleteCheck;
	public static boolean voiceRecognition;
	public static boolean voicePointsRecognition;
	public static boolean voiceSlopesRecognition;
	public static boolean voiceAreaRecognition;
	public static boolean voiceScanRecognition;
	public static boolean save;
	public static boolean spykeRemove = true;
	public static boolean avgBuild = true;
	public static int  avglength = 4;
	public static boolean amplitudeHeightRebuild = true;

	//Common
	public final static String BASE_AUDIO_PATH = "src/resources/static/voiceRecognition/";
	public final static String SPEKTRUM_PATH = "Spektrum/";
	public final static String VOICE_TEST_PATH = "VoiceTestPath/";
	public final static String TIME_FIXED_RECORD_PATH = "TimeFixedRecorder/";
	public final static String CONTINUE_RECORDS_PATH = "Records/";
	public final static String VOICE_RECOGNITION_DEBUG_DIRECTORY = "VoiceRecognition Debug Dir/";
	public final static String REBUILDED_SEQUENCE_DIRECTORY = "Rebuilded Sequences/";
	
	public final static int FIXED_RECORD_LENGTH_IN_MILISEC = 2000;
	
	public final static String DEAFULT_PREBUILT_WAVES_ARRAY =
														"Default_Wave_Library/PrebuitWaves.txt";
	public static void activateSaveNamedRecordsProfile() {
		
	    amplitudeRefinary = true;  frequencyRefinary = false;  preTrim = true; amplitudeTrim = true;
	    frequencyTrim = false; continueWithNoTrim = false;amplitudeGram = true;frequencyGram = true;
	    multiAnalysis = true; mySpektrogram = true; spektrogram = true; voiceRecognition = false;
	    buildSequenceArray = true; rawAudioData = true; voiceRecognitionData = true; wave = true;
	    deleteCheck = true; voiceRecognitionPointsData = true; voiceRecognitionSlopesData = true;
	    voiceRecognitionAreaData = true; voiceRecognitionScanData = true; save = true;
	} 
	
	public static void activateVoiceRecognitionProfile(){
		
		amplitudeRefinary = true;  frequencyRefinary = false; voiceRecognition = true;
		preTrim = true; amplitudeTrim = true; frequencyTrim = false; continueWithNoTrim = false;
	    amplitudeGram = true; frequencyGram = true; multiAnalysis = true; mySpektrogram = true;  
	    spektrogram = false; buildSequenceArray = false; rawAudioData = false; voiceRecognitionData = true;
	    wave = false; voicePointsRecognition = true; voiceSlopesRecognition = true; voiceAreaRecognition = true;
	    voiceScanRecognition = true; voiceRecognitionPointsData = true; voiceRecognitionSlopesData = true; 
	    voiceRecognitionAreaData = true; voiceRecognitionScanData = true; save = false;
	}
	
	public static void activateVoiceRecognitionDebugProfile(){
		
	    amplitudeRefinary = true;  frequencyRefinary = false;  preTrim = true;  amplitudeTrim = true; 
	    frequencyTrim = false; continueWithNoTrim = false; amplitudeGram = true; frequencyGram = true;
	    multiAnalysis = true; mySpektrogram = true; spektrogram = true; buildSequenceArray = true; 
	    rawAudioData = true; voiceRecognitionData = true; wave = true; deleteCheck = true; voiceRecognition = true;
	    voicePointsRecognition = true; voiceSlopesRecognition = true;  voiceAreaRecognition = true;
	    voiceScanRecognition = true; voiceRecognitionPointsData = true; voiceRecognitionSlopesData = true;
	    voiceRecognitionAreaData = true; voiceRecognitionScanData = true; save = true;
	}
	
	public static void activateSaveContinueRecordsProfile(){
	
		amplitudeRefinary = false;frequencyRefinary = true;amplitudeGram =true;frequencyGram =true;
		multiAnalysis = true; mySpektrogram = true; spektrogram = true; buildSequenceArray = true; 
		rawAudioData = true; voiceRecognitionData = false; wave = true;	 deleteCheck = false;
		save = true;
	}
	
	public static void activateBuildSequenceFromFileProfile(){
		
		amplitudeGram = true; frequencyGram = true; multiAnalysis = true; mySpektrogram = true;
		spektrogram = true; buildSequenceArray = true; rawAudioData = true;
		voiceRecognitionData = false; wave = true;  deleteCheck = false;
		save = true;
	}
				
	public static void activateBuildTestAudioSequenceProfile(){
		
		amplitudeGram = true; frequencyGram = true; multiAnalysis = true;  mySpektrogram = true;
		spektrogram = true; buildSequenceArray = true; rawAudioData = true; deleteCheck = false;
		voiceRecognitionData = false; wave = true; save = true;
	}
	
	public static void activateBuildSpektrogaramFromAudioFileProfile(){
		
		amplitudeGram = true; frequencyGram = true; multiAnalysis = true; mySpektrogram = true;
		spektrogram = true; buildSequenceArray = true; rawAudioData = true;  deleteCheck = true;
		voiceRecognitionData = false; wave = true; save = true;
	}
	
	public static void activateTimeFixedSoundRecorderProfile(){
		
		amplitudeRefinary = true;frequencyRefinary = false;amplitudeGram = true;deleteCheck = true;
		frequencyGram = true; multiAnalysis = true; mySpektrogram = true; spektrogram = true;
		buildSequenceArray = true; rawAudioData = true; voiceRecognitionData = false; wave = true;
		save = true;
	}
}
