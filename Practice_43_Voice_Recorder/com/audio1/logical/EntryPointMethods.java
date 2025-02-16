package com.audio1.logical;

import java.time.LocalDateTime;

import javax.sound.sampled.AudioInputStream;

import com.audio0.main.AppSetup;
import com.audio2.audioObject.AudioObject;
import com.audio2.audioSequenceBuilder.AudioSequenceBuilder;
import com.audio2.audioSequenceBuilder.AudioWave;
import com.audio2.audioSequenceBuilder.TestSequence;
import com.audio2.audioSequenceBuilder.WavesArrayBuilderFromFile;
import com.audio2.recorder.TimeFixedSoundRecorder;
import com.audio3.recorder.refinary.StreamRefinarySelector;
import com.audio4.audioGramInitializer.AudioAnalysisThread;
import com.audio5.recognition.VoiceRecognitionDB;
import com.audio8.util.AudioInputStreamUtil;
import com.audio8.util.AudioUtil;
import com.audio8.util.Debug;

/*
 * Parameters in this class are starter function parameters.
 */
public class EntryPointMethods {

	public static String  speechName;
	private static String  svitch;
		
	public static void  mainOptionRun(String option, String inputName) {
	
		new AudioAnalysisThread();
		
		setMainProfile(option,inputName);		
	}
	
	private static void setMainProfile(String option, String inputName) {
		
		svitch = option;
		
		switch(svitch) {
				
		case "saveNamedRecords" :saveNamedRecords(inputName);break;
		
		case "voiceRecognition" :voiceRecognition();break;
		
		case "voiceRecognitionDebug" :voiceRecognitionDebug();break;
		
		case "saveContinueRecords" :saveContinueRecords();break;
		
		case "buildSequenceFromFile" :buildSequenceFromFrequncyListFromFile(inputName);break;
					
		case "buildTestAudioSequence" :buildTestAudioSequence();break;	
		
		case "buildSpektrogramFromAudioFile" :buildSpektrogaramFromAudioFile(inputName);break;
		
		case "timeFixedSoundRecorder" : timeFixedSoundRecorder();break;	
		
		}
	}
	
	// Only one refinary option must used.
	private static void saveNamedRecords(String inputName) {
		
		AppSetup.activateSaveNamedRecordsProfile();
		
		setSpeechName(inputName);
		
		//VoiceRecognitionDB.buildAudioDB();
		
		StreamRefinarySelector.mainRefinaryStarter();
	}
	
	private static void voiceRecognition() {
		
		AppSetup.activateVoiceRecognitionProfile();
		
		setSpeechName(AppSetup.VOICE_RECOGNITION_DEBUG_DIRECTORY);
        
		VoiceRecognitionDB.buildAudioDB();
		
		StreamRefinarySelector.mainRefinaryStarter();
	}
	
	private static void voiceRecognitionDebug() {
		
		AppSetup.activateVoiceRecognitionDebugProfile();
		
		setSpeechName(AppSetup.VOICE_RECOGNITION_DEBUG_DIRECTORY);
        
		VoiceRecognitionDB.buildAudioDB();
		
		StreamRefinarySelector.mainRefinaryStarter();
	}
	
	private static void saveContinueRecords() {
		
		AppSetup.activateSaveContinueRecordsProfile();

		setSpeechName(Debug.getTimeStamp());
		
		new StreamRefinarySelector();

	}
	
	private static void buildSequenceFromFrequncyListFromFile(String fileName) {
		
		AppSetup.activateBuildSequenceFromFrequncyListFromFileProfile();
		
		AudioWave[] readedFrequencys=WavesArrayBuilderFromFile.mainBuilder(AppSetup.BASE_AUDIO_PATH 
				                     +"SequencePathLocation/"+fileName);
	
		AudioAnalysisThread.analysisStarter(new AudioObject(null
		,AudioSequenceBuilder.mainSequenceStreamBuilderByReadedFileInputSequences(readedFrequencys)
		,null,AppSetup.REBUILDED_SEQUENCE_DIRECTORY, AudioUtil.getAudioFormat("MainRecord")
			,AudioSequenceBuilder.averageAmplitude));
	}
	
	private static void buildSpektrogaramFromAudioFile(String fileName) {
		
		AppSetup.activateBuildSpektrogaramFromAudioFileProfile();
		
		AudioInputStream audioInputStream = 
				AudioInputStreamUtil.buildAudioInputStreamFromFile(AppSetup.BASE_AUDIO_PATH 
				+ "Default_Wave_Library/"+fileName);
	
		byte[] byteArray =
				AudioInputStreamUtil.buildAudiodDataFromAudioStream(audioInputStream);
	
		AudioAnalysisThread.analysisStarter(new AudioObject(byteArray,null,null,"AudioInputStreamStream"
										 						,audioInputStream.getFormat(),0));
	}
		
	private static void timeFixedSoundRecorder() {
		
		AppSetup.activateTimeFixedSoundRecorderProfile();
		
		new TimeFixedSoundRecorder("Fixed Record",AppSetup.FIXED_RECORD_LENGTH_IN_MILISEC);
	}
	
	private static void buildTestAudioSequence() {	
				
		AudioAnalysisThread.analysisStarter(new AudioObject(null
			,AudioSequenceBuilder.mainSequenceIntBuilderByTimeLength(TestSequence.getTestSequence())
			,null, "builded-" + LocalDateTime.now().getHour()+ "-"+LocalDateTime.now().getMinute()
		+ "- " + LocalDateTime.now().getSecond(),AudioUtil.getAudioFormat("MainRecord")
			,AudioSequenceBuilder.averageAmplitude));	
	}
		
	public static String getSpeechName() {
		
		return speechName;
	}

	public static void setSpeechName(String speechName) {
		
		EntryPointMethods.speechName = speechName;
	}

	public static String getSvitch() {
		
		return svitch;
	}

	public static void setSvitch(String svitch) {
		
		EntryPointMethods.svitch = svitch;
	} 
}
