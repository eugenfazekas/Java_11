package com.audio3.audioGramInitializer;

import javax.sound.sampled.AudioFormat;

import com.audio0.main.AppSetup;
import com.audio3.audioGramInitializer.trim.AudioPreTrimSelector;
import com.audio3.recognition.VoiceRecognition;
import com.audio4.audioGramBuilder.MultiAnalysisBuilder;
import com.audio4.audioGramBuilder.MySpketrogramDetails;
import com.audio5.audioGramSaver.SaveMultiAudioFeatures;
import com.audio8.util.Debug;

public class AudioAnalysisThread implements Runnable{

	private static  Thread thread;
	private static  String speechName;
	public static  byte[] byteStream;
	public static  int[] intStream;
	public static  int[] audioStreamDetails;
	public static  int averageAmplitude;
	private static  AudioFormat audioFormat;

	public AudioAnalysisThread(byte[] bytStream, int[] intstream, int[] audiostreamDetails,
			String speechname, AudioFormat audioformat, int avegAmplitude) {
		speechName = speechname;
		byteStream = bytStream;
		intStream = intstream;
		audioStreamDetails = audiostreamDetails;
		audioFormat = audioformat;
		averageAmplitude = avegAmplitude;
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {

		switch(AppSetup.svitch) {
			
			case "saveNamedRecords" :saveNamedRecords();break;
			
			case "saveContinueRecords" :saveContinueRecords();break;
			
			case "voiceRecognition" :voiceRecognition();break;
			
			case "voiceRecognitionDebug" :voiceRecognitionDebug();break;
			
			case "buildSequenceFromFile" :buildSequenceFromFrequncyListFromFile();break;
			
			case "buildTestSequence" :buildTestSequence();break;	
			
			case "buildSpektrogramFromAudioFile" :buildSpektrogramFromAudioFile();break;
			
			case "timeFixedSoundRecorder" : timeFixedSoundRecorder();break;
		}		
		thread = null;
	}

	private static void saveNamedRecords() {
		
    	Debug.debug(2,"AudioAnalysisThread saveNamedRecords inputFileName: "+speechName);
    	AudioPreTrimSelector.mainAudioTrim(intStream,audioStreamDetails);
    	AudioAnalysisInitializer.mainBuilder(byteStream,intStream,null,audioFormat,averageAmplitude);
	    MultiAnalysisBuilder.mainAnalysisBuilder();
		SaveMultiAudioFeatures.mainSave(AppSetup.BASE_AUDIO_PATH,AppSetup.SPEKTRUM_PATH,speechName);
	}
	
	private static void voiceRecognition() {
		
    	Debug.debug(2,"AudioAnalysisThread voiceRecognition inputFileName: "+speechName);
    	AudioPreTrimSelector.mainAudioTrim(null,audioStreamDetails);
    	AudioAnalysisInitializer.mainBuilder(null,null,audioStreamDetails,audioFormat,averageAmplitude);
		VoiceRecognition.voiceFinder(AudioAnalysisInitializer.audioStreamDetails);
	    MultiAnalysisBuilder.smallAnalysisBuilder();
		SaveMultiAudioFeatures.mainSave(AppSetup.BASE_AUDIO_PATH, AppSetup.VOICE_TEST_PATH,speechName);
	}
	
	private static void voiceRecognitionDebug() {
		
    	Debug.debug(2,"AudioAnalysisThread voiceRecognitionDebug inputFileName: "+speechName);
    	AudioPreTrimSelector.mainAudioTrim(intStream,audioStreamDetails);
    	AudioAnalysisInitializer.mainBuilder(byteStream,intStream,null,audioFormat,averageAmplitude);
	    MultiAnalysisBuilder.mainAnalysisBuilder();
		VoiceRecognition.voiceFinder(MySpketrogramDetails.spektrogramMap);
		SaveMultiAudioFeatures.mainSave(AppSetup.BASE_AUDIO_PATH, AppSetup.VOICE_TEST_PATH,speechName);
	}
	
	private static void saveContinueRecords() {	
		
    	Debug.debug(2,"AudioAnalysisThread saveContinueRecords inputFileName: "+speechName);
    	AudioPreTrimSelector.mainAudioTrim(intStream,audioStreamDetails);
    	AudioAnalysisInitializer.mainBuilder(byteStream,intStream,null,audioFormat, averageAmplitude);
	    MultiAnalysisBuilder.mainAnalysisBuilder();
		SaveMultiAudioFeatures.mainSave(AppSetup.CONTINUE_RECORDS_PATH,"",speechName);
	}
	
	private static void  buildSpektrogramFromAudioFile() {
		
    	Debug.debug(2,"AudioAnalysisThread buildSpektrogramFromAudioFile inputFileName: "+speechName);
		AudioAnalysisInitializer.mainBuilder(byteStream, null,null, audioFormat,0);
	    MultiAnalysisBuilder.mainAnalysisBuilder();
		SaveMultiAudioFeatures.mainSave(AppSetup.BASE_AUDIO_PATH,AppSetup.SPEKTRUM_PATH,speechName);	
	}

	private static void buildSequenceFromFrequncyListFromFile() {
    	Debug.debug(2,"AudioAnalysisThread buildSequenceFromFrequncyListFromFile inputFileName: "+speechName);
    	AudioAnalysisInitializer.mainBuilder(byteStream,intStream,null, audioFormat, averageAmplitude);
	    MultiAnalysisBuilder.mainAnalysisBuilder();
		SaveMultiAudioFeatures.mainSave(AppSetup.BASE_AUDIO_PATH,AppSetup.SPEKTRUM_PATH,speechName);	
	}
	
	private static void buildTestSequence() {
    	Debug.debug(2,"AudioAnalysisThread buildTestSequence inputFileName: "+speechName);
    	AudioAnalysisInitializer.mainBuilder(byteStream,intStream,null,audioFormat, averageAmplitude);
		MultiAnalysisBuilder.mainAnalysisBuilder();
		SaveMultiAudioFeatures.mainSave(AppSetup.BASE_AUDIO_PATH,AppSetup.SPEKTRUM_PATH,speechName);
	}
	
	private void timeFixedSoundRecorder() {		
    	Debug.debug(2,"AudioAnalysisThread timeFixedSoundRecorder inputFileName: "+speechName);
    	AudioAnalysisInitializer.mainBuilder(byteStream,intStream,null, audioFormat, averageAmplitude);
	    MultiAnalysisBuilder.mainAnalysisBuilder();
		SaveMultiAudioFeatures.mainSave(AppSetup.BASE_AUDIO_PATH,AppSetup.SPEKTRUM_PATH,speechName);
	}
}
