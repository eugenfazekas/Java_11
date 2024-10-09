package com.audio0.main;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;

import com.audio1.recorder.AudioListener;
import com.audio1.recorder.TimeFixedSoundRecorder;
import com.audio2.soundBuilder.Sequence;
import com.audio2.soundBuilder.SequenceBuilderFromFile;
import com.audio2.soundBuilder.SoundBuilder;
import com.audio3.audioGramInitializer.AudioAnalysisThread;
import com.audio3.recognition.VoiceRecognition;
import com.audio8.util.AudioInputStreamUtil;
import com.audio8.util.AudioUtil;

public class AppSetup {
	
	// RFINERY
	public static int idle_amplitude_volume;
	public final static int AMPLITUDE_HEIGHT_OPTIMIZATION_KONSTANT = 2000;
	public final static int VOICE_RECOGNITION_BUFFER_MILISEC_LENGTH = 2;
	public final static int FREQUENCY_CHECK_LOWER_LIMIT = 10;
	public final static int FREQUENCY_CHECK_UPPER_LIMIT = 1000;	
	public final static int POSSIBLE_VOICE_LIMIT = 4;
	
	// Only one refinary option must used.
	public static boolean   amplitudeRefinary;
	public static boolean   frequencyRefinary;
	public static boolean   voiceRecognitionAmplitudeRefinary;
	public static boolean   voiceRecognitionFrequencyRefinary;

	// AudioAnalysisInitializer
	public final static int VALID_COUNTER = 1000;
	public static int STARTER_VOLUME_LIMIT = 7000;
	public static int SHARPNESS_GROVER_MULTIPLIER = 40;
	
	// PRETRIM
	public static boolean   preTrim;
	public static boolean   continueWithNoTrim = false;  
	// PRETRIM amplitude based
	public static int START_WAVE_LOWER_LIMIT_A = STARTER_VOLUME_LIMIT/20;
	public static int START_WAVE_UPPER_LIMIT_A = 32736;
	public static int END_WAVE_LOWER_LIMIT_A = 0;
	public static int END_WAVE_UPPER_LIMIT_A = idle_amplitude_volume/2;
	public static final int START_AMPLITUDE_SEQUENCE_LENGTH_CHECK = 100;
	public static final int END_AMPLITUDE_SEQUENCE_LENGTH_CHECK = 300;
	public final static int BEFORE_CUT_LENGTH = 100;
	public final static int AFTER_CUT_LENGTH = 100;
	// PRETRIM Frequency based
	public final static int START_FREQUENCY_LOWER_LIMIT = 200 ;
	public final static int START_FREQUENCY_UPPER_LIMIT = 1000;
	public final static int END_FREQUENCY_LOWER_LIMIT = 1000 ;
	public final static int END_FREQUENCY_UPPER_LIMIT = 11025;
	public final static int START_WAVE_LOWER_LIMIT_F = STARTER_VOLUME_LIMIT/30;
	public final static int START_WAVE_UPPER_LIMIT_F = 32736;
	public final static int END_WAVE_LOWER_LIMIT_F = 0;
	public final static int END_WAVE_UPPER_LIMIT_F = STARTER_VOLUME_LIMIT /30;
	public final static int START_FREQUENCY_SEQUENCE_LENGTH_CHECK = 10;
	public final static int END_FREQUENCY_AMPLITUDE_SEQUENCE_LENGTH_CHECK = 30;
	
	//AmplitudeDetails
	public final static int AMPLITUDE_AVERAGE_CORRECTION_KONSTANT = 3;
	public static int     AMPLITUDE_BUFFER_MILISEC_LENGTH = 4;
	public static boolean filterAmplitudeGram = false;
	public static int     amplitudeGramFilter = 1;
	
	//FrequencyDetails
	public static int     FREQUENCY_BUFFER_MILISEC_LENGTH = 4;
	public static boolean filterFrequencyGram = false;
	public static int     frequencyGramLowerFilter = 1;
	
	//MySpektrogramDetails
	public static int     SPEKTROGRAM_BUFFER_MILISEC_LENGTH = 4;
	//
	public final static float BUILD_SEQUENCE_AMPLITUDE_CORRECTION_KONSTANT = 1.35f;
	
	//Data Analysis
	public static boolean amplitudeGram;
	public static boolean frequencyGram;
	public static boolean multiAnalysis;
	public static boolean mySpektrogram;
	public static boolean spektrogram;
	public static boolean buildSequenceArray;
	public static boolean rawAudioData;
	public static boolean voiceRecognitionData;
	public static boolean wave;
	
	//Common
	public static String BASE_AUDIO_PATH = "src/main/resources/static/audio/";
	public static String SPEKTRUM_PATH = "Spektrum/";
	public static String VOICE_TEST_PATH = "VoiceTestPath/";
	public static String CONTINUE_RECORDS_PATH = "D:/records/";
	private final static String VOICE_RECOGNITION_DEBUG_DIRECTORY = "VoiceRecognition Debug Dir";
	private final static String REBUILDED_SEQUENCE_DIRECTORY = "Rebuilded Sequences";
	private static final int FIXED_RECORD_LENGTH_IN_MILISEC = 1000;
		
	public static String svitch;
	
	public static void  mainOptionRun(String option, String fileName) {
		
		svitch = option;
		
		switch(svitch) {
		
			case "saveNamedRecords" :saveNamedRecords(fileName);break;
			
			case "voiceRecognition" :voiceRecognition();break;
			
			case "voiceRecognitionDebug" :voiceRecognitionDebug();break;
			
			case "saveContinueRecords" :saveContinueRecords();break;
			
			case "buildSequenceFromFile" :buildSequenceFromFrequncyListFromFile(fileName);break;
						
			case "buildTestSequence" :buildTestSequence();break;	
			
			case "buildSpektrogramFromAudioFile" :buildSpektrogaramFromAudioFile(fileName);break;
			
			case "timeFixedSoundRecorder" : timeFixedSoundRecorder();break;		
		}
	}
	
	// Only one refinary option must used.
	private static void saveNamedRecords(String recordName) {
         amplitudeRefinary = false;  frequencyRefinary = true;  preTrim = true;  continueWithNoTrim = false;  
		 amplitudeGram = true; frequencyGram = true; multiAnalysis = true; mySpektrogram = true;
		 spektrogram = true; buildSequenceArray = true; rawAudioData = true; voiceRecognitionData = true; wave = true;
		 new AudioListener(recordName);
	}
	
	private static void voiceRecognition() {
        voiceRecognitionAmplitudeRefinary = true; voiceRecognitionFrequencyRefinary = false; preTrim = true;  continueWithNoTrim = false;  
		amplitudeGram = true; frequencyGram = true; multiAnalysis = true; mySpektrogram = true;
	    spektrogram = false; buildSequenceArray = false; rawAudioData = false; voiceRecognitionData = true; wave = false;		
		VoiceRecognition.buildAudioDB();
		new AudioListener("Test");
	}
	
	private static void voiceRecognitionDebug() {
		amplitudeRefinary = true;  frequencyRefinary = false;  preTrim = true;  continueWithNoTrim = false; 
		amplitudeGram = true; frequencyGram = true; multiAnalysis = true; mySpektrogram = true;
	    spektrogram = true; buildSequenceArray = true; rawAudioData = true; voiceRecognitionData = true; wave = true;		
		VoiceRecognition.buildAudioDB();
		new AudioListener(VOICE_RECOGNITION_DEBUG_DIRECTORY);
	}
	
	private static void saveContinueRecords() {
		amplitudeRefinary = false;  frequencyRefinary = true; 
		amplitudeGram = true; frequencyGram = true; multiAnalysis = true; mySpektrogram = true;
		spektrogram = true; buildSequenceArray = true; rawAudioData = true; voiceRecognitionData = false; wave = true;	
		String date = ""+LocalDateTime.now().getDayOfMonth()+"-"+LocalDateTime.now().getMonthValue()+"-"+LocalDateTime.now().getYear();
		new AudioListener(date);
	}

	private static void buildSequenceFromFrequncyListFromFile(String fileName) {
		amplitudeGram = true; frequencyGram = true; multiAnalysis = true; mySpektrogram = true;
		spektrogram = true; buildSequenceArray = true; rawAudioData = true; voiceRecognitionData = false; wave = true;		 
		Sequence[] readedFrequencys = SequenceBuilderFromFile.mainBuilder(BASE_AUDIO_PATH +"SequencePathLocation/"+fileName) ;
		new AudioAnalysisThread(null,SoundBuilder.mainSequenceStreamBuilderByReadedFileInputSequences(readedFrequencys),null,REBUILDED_SEQUENCE_DIRECTORY, AudioUtil.getAudioFormat("MainRecord"),SoundBuilder.averageAmplitude);
	}
	
	private static void buildSpektrogaramFromAudioFile(String fileName) {
		amplitudeGram = true; frequencyGram = true; multiAnalysis = true; mySpektrogram = true;
		spektrogram = true; buildSequenceArray = true; rawAudioData = true; voiceRecognitionData = false; wave = true;
		AudioInputStream audioInputStream =  AudioInputStreamUtil.buildAudioInputStreamFromFile(BASE_AUDIO_PATH + "Default_Wave_Library/"+fileName);
		byte[] byteArray = AudioInputStreamUtil.buildAudiodDataFromAudioStream(audioInputStream);
		new AudioAnalysisThread(byteArray,null,null,"AudioInputStreamStream", audioInputStream.getFormat(),0);
	};
		
	private static void timeFixedSoundRecorder() {
		amplitudeRefinary = true;  frequencyRefinary = false; 
		amplitudeGram = true; frequencyGram = true; multiAnalysis = true; mySpektrogram = true;
		spektrogram = true; buildSequenceArray = true; rawAudioData = true; voiceRecognitionData = false; wave = true;
		new TimeFixedSoundRecorder("Fixed Record",FIXED_RECORD_LENGTH_IN_MILISEC);
	}
	
	private static void buildTestSequence() {	
		amplitudeGram = true; frequencyGram = true; multiAnalysis = true; mySpektrogram = true;
		spektrogram = true; buildSequenceArray = true; rawAudioData = true; voiceRecognitionData = false; wave = true;
		new AudioAnalysisThread(null,SoundBuilder.mainSequenceIntBuilderByTimeLength(getTestSequence()),null, "builded-" +LocalDateTime.now().getHour()+ "-"+LocalDateTime.now().getMinute()+"- "+LocalDateTime.now().getSecond(),AudioUtil.getAudioFormat("MainRecord"),SoundBuilder.averageAmplitude);	
	}
		
	private static List<Sequence> getTestSequence() {
		
		List<Sequence> sequence = new ArrayList<Sequence>();
	
//		sequence.add(new Sequence(10000,100,1000));
		sequence.add(new Sequence(10000,10,1000));
//		sequence.add(new Sequence(20000,700,300));
//		sequence.add(new Sequence(15000,11050,300));
//		sequence.add(new Sequence(10000,500,300));
//		sequence.add(new Sequence(10000,600,100));
//		sequence.add(new Sequence(10000,700,100));
//		sequence.add(new Sequence(10000,800,100));
//		sequence.add(new Sequence(10000,900,100));
//		sequence.add(new Sequence(11000,1000,100));
//		sequence.add(new Sequence(10000,2000,100));
//		sequence.add(new Sequence(10000,3000,100));
//		sequence.add(new Sequence(10000,4000,100));
//		sequence.add(new Sequence(10000,5000,100));
//		sequence.add(new Sequence(10000,6000,100));
//		sequence.add(new Sequence(10000,7000,100));
//		sequence.add(new Sequence(10000,8000,100));
//		sequence.add(new Sequence(10000,9000,100));
//		sequence.add(new Sequence(10000,10000,100));
//		sequence.add(new Sequence(10000,11000,100));
		
//		sequence.add(new Sequence(20000,50,100));
//		sequence.add(new Sequence(11000,200,100));
//		sequence.add(new Sequence(12000,300,100));
//		sequence.add(new Sequence(13000,400,100));
//		sequence.add(new Sequence(14000,500,100));
//		sequence.add(new Sequence(15000,600,100));
//		sequence.add(new Sequence(16000,700,100));
//		sequence.add(new Sequence(17000,800,100));
//		sequence.add(new Sequence(18000,900,100));
//		sequence.add(new Sequence(19000,1000,100));
//		sequence.add(new Sequence(20000,2000,100));
//		sequence.add(new Sequence(21000,3000,100));
//		sequence.add(new Sequence(22000,4000,100));
//		sequence.add(new Sequence(23000,5000,100));
//		sequence.add(new Sequence(24000,6000,100));
//		sequence.add(new Sequence(25000,7000,100));
//		sequence.add(new Sequence(26000,8000,100));
//		sequence.add(new Sequence(27000,9000,100));
//		sequence.add(new Sequence(28000,10000,100));
//		sequence.add(new Sequence(29000,11000,100));

		//StartAudioAnalysisFeatures save = new StartAudioAnalysisFeatures(SoundBuilder.mainSequenceByteBuilderByTimeLength(sequence),"alma", AudioUtil.getAudioFormat("MainRecord"));
		//StartSaveAudioFeatures save = new StartSaveAudioFeatures(SoundBuilder.mainSequenceByteBuilderByTimeLength(sequence),"alma", AudioUtil.getAudioFormat("MainRecord"));
		return sequence;
	}
	
//	VoiceRecognition.test(VoiceRecognition.testSamples);
//  AudioGramConstructor.buildMultiAudioGrammFromInputFile("audiomass2.wav");
//  TimeFixedSoundRecorder recorder = new TimeFixedSoundRecorder("record Name",1000);		
//	VoiceRecognition.test(VoiceRecognition.testSamples);			
//	saveContinueRecords();	
//	buildSequenceFromFile();	
//	buildTestSequence();		
//  SoundBuilder.buildAndWriteAmplitudeQuarteWaves();	
//	SoundBuilder.readAmplitudeQuarteWaves();
	
}
