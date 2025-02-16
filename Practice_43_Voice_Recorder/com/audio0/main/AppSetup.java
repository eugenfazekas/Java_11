package com.audio0.main;

/*
 * These parameters are final or Runtime parameters.
 * 
 * STARTER PARAMETERS DEFINED IN EntrypointMethod CLASS
 */
public class AppSetup {
	
	//AudioListener
	public static int MILISEC_BUFFER_LENGTH = 300;
	
	// RFINERY
	public static int idle_amplitude_volume;
	public static float START_SOUND_THRESHOLD_MULTIPLIER = 2f;
	public static float END_SOUND_THRESHOLD_MULTIPLIER = 1f;
	public final static int AUDIO_BUFFER_MILISEC_LENGTH = 4;
	public final static int FREQUENCY_CHECK_LOWER_LIMIT = 10;
	public final static int FREQUENCY_CHECK_UPPER_LIMIT = 1000;	
	public final static int POSSIBLE_VOICE_LIMIT = 4;
	public final static int START_OPTIMIZATION_CYCLES = 3;
		
	// AudioAnalysisInitializer
	public final static int VALID_COUNTER = 1000;
	public static int STARTER_VOLUME_LIMIT = 1000;
	public static int SHARPNESS_GROVER_MULTIPLIER = 40;
	public static int LOW_FREQUENCY_FILTER = 100;
	public static int HIGH_FREQUENCY_FILTER = 2500;
	
	// PRETRIM amplitude based
	public final static int START_WAVE_LOWER_LIMIT_A = STARTER_VOLUME_LIMIT/20;
	public final static int START_WAVE_UPPER_LIMIT_A = 32736;
	public final static int END_WAVE_LOWER_LIMIT_A = 0;
	public final static int END_WAVE_UPPER_LIMIT_A = idle_amplitude_volume/2;
	//public final static int START_AMPLITUDE_SEQUENCE_LENGTH_CHECK = 100;
	//public final static int END_AMPLITUDE_SEQUENCE_LENGTH_CHECK = 300;
	public final static int BEFORE_CUT_LENGTH = 100;
	public final static int AFTER_CUT_LENGTH = 100;
	
	// PRETRIM Frequency based
	public final static int START_FREQUENCY_LOWER_LIMIT = 100;
	public final static int START_FREQUENCY_UPPER_LIMIT = 1000;
	public final static int END_FREQUENCY_LOWER_LIMIT = 1000;
	public final static int END_FREQUENCY_UPPER_LIMIT = 11025;
	public final static int START_WAVE_LOWER_LIMIT_F = STARTER_VOLUME_LIMIT/30;
	public final static int START_WAVE_UPPER_LIMIT_F = 32736;
	public final static int END_WAVE_LOWER_LIMIT_F = 0;
	public final static int END_WAVE_UPPER_LIMIT_F = STARTER_VOLUME_LIMIT /30;
	public final static int START_FREQUENCY_SEQUENCE_LENGTH_CHECK = 10;
	public final static int END_FREQUENCY_AMPLITUDE_SEQUENCE_LENGTH_CHECK = 200;
	
	//AmplitudeDetails
	public final static float AMPLITUDE_AVERAGE_CORRECTION_KONSTANT = 1f;
	public final static boolean filterAmplitudeGram = false;
	public final static int     amplitudeGramFilter = 1;
	
	//FrequencyDetails
	public final static boolean filterFrequencyGram = false;
	public final static int     frequencyGramLowerFilter = 1;
	
	//MySpektrogramDetails
	public final static int   SPEKTROGRAM_BUFFER_MILISEC_LENGTH = AUDIO_BUFFER_MILISEC_LENGTH;
	
	//MiscellaneousData
	public final static float BUILD_SEQUENCE_AMPLITUDE_CORRECTION_KONSTANT = 1.4f;
	
	public static boolean noiseFilter = false;
	public static boolean spykeRemove = true;
	public static boolean avgBuild = true;
	public static int  avglength = 8;
	public static boolean amplitudeHeightRebuild = true;
	
	// Only one refinary option must used.
	public static boolean amplitudeRefinary;
	public static boolean frequencyRefinary;
	public static boolean voiceRecognitionAmplitudeRefinary;
	public static boolean voiceRecognitionFrequencyRefinary;
	
	// PRETRIM
	public static boolean preTrim;
	public static boolean multiTrim;
	public static boolean continueWithNoTrim;  

	//Data Analysis
	public static boolean amplitudeGram;
	public static boolean frequencyGram;
	public static boolean multiGram;
	public static boolean multiAnalysis;
	public static boolean mySpektrogram;
	public static boolean spektrogram;
	public static boolean buildSequenceArray;
	public static boolean rawAudioData;
	public static boolean voiceRecognitionData;
	public static boolean voiceRecognitionPointsData;
	public static boolean voiceRecognitionSlopesData;
	public static boolean wave;
	public static boolean deleteCheck;
	public static boolean voiceRecognition;
	public static boolean voicePointsRecognition;
	public static boolean voiceSlopesRecognition;
	public static boolean voicePointsCompare;

	//Common
	public final static String BASE_AUDIO_PATH = "src/main/resources/static/audio/";
	public final static String SPEKTRUM_PATH = "Spektrum/";
	public final static String VOICE_TEST_PATH = "VoiceTestPath/";
	public final static String TIME_FIXED_RECORD_PATH = "TimeFixedRecorder/";
	public final static String CONTINUE_RECORDS_PATH = "D:/records/";
	public final static String VOICE_RECOGNITION_DEBUG_DIRECTORY = "VoiceRecognition Debug Dir";
	public final static String REBUILDED_SEQUENCE_DIRECTORY = "Rebuilded Sequences";
	public final static int FIXED_RECORD_LENGTH_IN_MILISEC = 1000;
	
	public final static String DEAFULT_PREBUILT_WAVES_ARRAY =
														"Default_Wave_Library/PrebuitWaves.txt";
	public static void activateSaveNamedRecordsProfile() {
		
	    amplitudeRefinary = true;  frequencyRefinary = false;  preTrim = true; multiTrim = true;
	    continueWithNoTrim=false;amplitudeGram = false;frequencyGram = false; multiGram = true;
	    voiceRecognition = false; multiAnalysis = true; mySpektrogram = true; spektrogram = true;
	    buildSequenceArray = true; rawAudioData = true; voiceRecognitionData = true; wave = true;
	    deleteCheck = true; voiceRecognitionPointsData = true; voiceRecognitionSlopesData = true;
	} 
	
	public static void activateVoiceRecognitionProfile(){
		
		amplitudeRefinary = false; frequencyRefinary = false; deleteCheck = false; preTrim = true;
	    voiceRecognitionAmplitudeRefinary = true; voiceRecognitionFrequencyRefinary = false;
	    voiceRecognition = true; multiTrim = true; continueWithNoTrim = false;amplitudeGram = true;
	    frequencyGram = true; multiAnalysis = true; mySpektrogram = true;  spektrogram = false; 
	    buildSequenceArray = false; rawAudioData = false; voiceRecognitionData = true ;wave = false;
	    voicePointsRecognition = true; voiceSlopesRecognition = true; voicePointsCompare = true;
	    voiceRecognitionPointsData = true; voiceRecognitionSlopesData = true;
	}
	
	public static void activateVoiceRecognitionDebugProfile(){
		
	    amplitudeRefinary = false;  frequencyRefinary = true;  preTrim = true; multiTrim = true;
	    continueWithNoTrim = false; amplitudeGram = true;frequencyGram = true;multiAnalysis = true;
	    mySpektrogram = true; spektrogram = true; buildSequenceArray = true; rawAudioData = true;
	    voiceRecognitionData = true; wave = true; deleteCheck = true; voiceRecognition = true;
	    voicePointsRecognition = true; voiceSlopesRecognition = true;  voicePointsCompare = true; 
	    voiceRecognitionPointsData = true; voiceRecognitionSlopesData = true;
	}
	
	public static void activateSaveContinueRecordsProfile(){
	
		amplitudeRefinary = false;frequencyRefinary = true;amplitudeGram =true;frequencyGram =true;
		multiAnalysis = true; mySpektrogram = true; spektrogram = true; buildSequenceArray = true; 
		rawAudioData = true; voiceRecognitionData = false; wave = true;	 deleteCheck = false;
	}
	
	public static void activateBuildSequenceFromFrequncyListFromFileProfile(){
		
		amplitudeGram = true; frequencyGram = true; multiAnalysis = true; mySpektrogram = true;
		spektrogram = true; buildSequenceArray = true; rawAudioData = true;
		voiceRecognitionData = false; wave = true;  deleteCheck = false;	
	}
				
	public static void activateBuildTestAudioSequenceProfile(){
		
		amplitudeGram = true; frequencyGram = true; multiAnalysis = true;  mySpektrogram = true;
		spektrogram = true; buildSequenceArray = true; rawAudioData = true; deleteCheck = false;
		voiceRecognitionData = false; wave = true; 
	}
	
	public static void activateBuildSpektrogaramFromAudioFileProfile(){
		
		amplitudeGram = true; frequencyGram = true; multiAnalysis = true; mySpektrogram = true;
		spektrogram = true; buildSequenceArray = true; rawAudioData = true;  deleteCheck = true;
		voiceRecognitionData = false; wave = true;
	}
	
	public static void activateTimeFixedSoundRecorderProfile(){
		
		amplitudeRefinary = true;frequencyRefinary = false;amplitudeGram = true;deleteCheck = true;
		frequencyGram = true; multiAnalysis = true; mySpektrogram = true; spektrogram = true;
		buildSequenceArray = true; rawAudioData = true; voiceRecognitionData = false; wave = true;
	}
}
