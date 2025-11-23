package com.voiceRecognition.audio1.logical;

import java.time.LocalDateTime;

import javax.sound.sampled.AudioInputStream;

import com.voiceRecognition.audio0.main.VoiceRecognitionAppSetup;
import com.voiceRecognition.audio2.audioObject.AudioObject;
import com.voiceRecognition.audio2.audioSequenceBuilder.AudioSequenceBuilder;
import com.voiceRecognition.audio2.audioSequenceBuilder.TestSequence;
import com.voiceRecognition.audio2.audioSequenceBuilder.WavesArrayBuilderFromFile;
import com.voiceRecognition.audio2.recorder.AudioUtil;
import com.voiceRecognition.audio2.recorder.TimeFixedSoundRecorder;
import com.voiceRecognition.audio3.recorder.refinary.StreamRefinarySelector;
import com.voiceRecognition.audio4.audioGramInitializer.mainInit.AudioAnalysisThread;
import com.voiceRecognition.audio5.recognition.VoiceRecognitionDB;
import com.voiceRecognition.audio6.audioGramSaver.AudioInputStreamUtil;
import com.voiceRecognition.audio6.audioGramSaver.FileCheckUtil;
import com.voiceRecognition.audio8.util.Debug;

/*
 * Parameters in this class are starter function parameters.
 */
public class EntryPointMethods {

	public static String  speechName;
	private static String  svitch;
	private static boolean instanceOf;

	private static AudioAnalysisThread audioAnalysisThread;
		
	public static void  mainOptionRun(String option, String inputName) {
	
		if(instanceOf && !option.equals(svitch)){
			Debug.debug(0, "EntryPointMethods mainOptionRun  Multiple options Has been selelcted! "
					+ "option: "+option + ", Has been not started!\n");
			return;
		}
		
		if(svitch == null || option.equals(svitch)) {
			
			svitch = option;
			instanceOf = true;
		}

		
		if(audioAnalysisThread == null)
			audioAnalysisThread = new AudioAnalysisThread();

		setMainProfile(option,inputName);
		
		checkMainPath();
	}
	
	private static void setMainProfile(String option, String inputName) {
		
		svitch = option;
		
		switch(svitch) {
				
			case "saveNamedRecords" :saveNamedRecords(inputName);break;
			
			case "voiceRecognition" :voiceRecognition(inputName);break;
			
			case "voiceRecognitionDebug" :voiceRecognitionDebug(inputName);break;
			
			case "saveContinueRecords" :saveContinueRecords();break;
			
			case "buildSequenceFromFile" :buildSequenceFromFrequncyListFromFile(inputName);break;
						
			case "buildTestAudioSequence" :buildTestAudioSequence();break;	
			
			case "buildSpektrogramFromAudioFile" :buildSpektrogaramFromAudioFile(inputName);break;
			
			case "timeFixedSoundRecorder" : timeFixedSoundRecorder(inputName);break;			
		}
	}
	
	public static void loadProfileSetup(String Profile) {
		
		String profile = Profile;
		
		switch(profile) {
				
			case "saveNamedRecords" :VoiceRecognitionAppSetup.activateSaveNamedRecordsProfile();break;
			
			case "voiceRecognition" :VoiceRecognitionAppSetup.activateVoiceRecognitionProfile();break;
			
			case "voiceRecognitionDebug" :VoiceRecognitionAppSetup.activateVoiceRecognitionDebugProfile();break;
			
			case "saveContinueRecords" :VoiceRecognitionAppSetup.activateSaveContinueRecordsProfile();break;
			
			case "buildSequenceFromFile" :VoiceRecognitionAppSetup.activateBuildSequenceFromFileProfile();break;
						
			case "buildTestAudioSequence" :VoiceRecognitionAppSetup.activateBuildTestAudioSequenceProfile();break;	
			
			case "buildSpektrogramFromAudioFile" :VoiceRecognitionAppSetup.activateBuildSpektrogaramFromAudioFileProfile();break;
			
			case "timeFixedSoundRecorder" : VoiceRecognitionAppSetup.activateTimeFixedSoundRecorderProfile();break;			
		}
	}
	
	// Only one refinary option must used.
	private static void saveNamedRecords(String inputName) {
		
		loadProfileSetup(svitch);
		
		setSpeechName(inputName);
		
		VoiceRecognitionDB.buildAudioDB();
		
		StreamRefinarySelector.mainRefinaryStarter();
	}
	
	private static void voiceRecognition(String inputName) {
		
		loadProfileSetup(svitch);
		
		setSpeechName(inputName);
        
		VoiceRecognitionDB.buildAudioDB();
		
		StreamRefinarySelector.mainRefinaryStarter();
	}
	
	private static void voiceRecognitionDebug(String inputName) {
		
		loadProfileSetup(svitch);
		
		setSpeechName(inputName);
        
		VoiceRecognitionDB.buildAudioDB();
		
		StreamRefinarySelector.mainRefinaryStarter();
	}
	
	private static void saveContinueRecords() {
		
		loadProfileSetup(svitch);

		setSpeechName(Debug.getTimeStamp());
		
		StreamRefinarySelector.mainRefinaryStarter();
	}
	
	private static void buildSequenceFromFrequncyListFromFile(String fileName) {
		
		loadProfileSetup(svitch);
		
		int[] readedSequenceInfo = WavesArrayBuilderFromFile.mainBuilder(VoiceRecognitionAppSetup.BASE_AUDIO_PATH 
				                     +"SequencePathLocation/"+fileName);
	
		AudioAnalysisThread.analysisStarter(new AudioObject(null
				,AudioSequenceBuilder.mainBuilder(readedSequenceInfo,
						(int)AudioUtil.getAudioFormat("AudioListener").getSampleRate())
				,null,null,fileName, AudioUtil.getAudioFormat("MainRecord")
					,AudioSequenceBuilder.averageAmplitude,getSvitch()));
	}
	
	private static void buildTestAudioSequence() {	
		
		loadProfileSetup(svitch);
		
		AudioAnalysisThread.analysisStarter(new AudioObject(null,
				AudioSequenceBuilder.mainBuilder(TestSequence.getTestSequence(),
						(int)AudioUtil.getAudioFormat("AudioListener").getSampleRate()),null,null
				, "builded-" + LocalDateTime.now().getHour()+ "-"+LocalDateTime.now().getMinute()
			+ "- " + LocalDateTime.now().getSecond(),AudioUtil.getAudioFormat("MainRecord")
				,AudioSequenceBuilder.averageAmplitude,getSvitch()));
	}
	
	private static void buildSpektrogaramFromAudioFile(String fileName) {
		
		loadProfileSetup(svitch);
		
		AudioInputStream audioInputStream = 
				AudioInputStreamUtil.buildAudioInputStreamFromFile(VoiceRecognitionAppSetup.BASE_AUDIO_PATH 
				+ "Default_Wave_Library/"+fileName);
	
		byte[] byteArray =
				AudioInputStreamUtil.buildAudiodDataFromAudioStream(audioInputStream);
		int[] intStream = AudioUtil.buildIntStreamFromByteStream(
									byteArray, AudioUtil.getAudioFormat("Spektrogram"));
		
		AudioAnalysisThread.analysisStarter(new AudioObject(byteArray,intStream,null,null,
			"Spektrogram Ruilded",audioInputStream.getFormat(),intStream[0],getSvitch()));
	}
	
	private static void timeFixedSoundRecorder(String fileName) {
		
		loadProfileSetup(svitch);
		
		new TimeFixedSoundRecorder(fileName,VoiceRecognitionAppSetup.FIXED_RECORD_LENGTH_IN_MILISEC);
	}
	
	private static void checkMainPath() {
		
		if(!FileCheckUtil.pathExist(VoiceRecognitionAppSetup.BASE_AUDIO_PATH))
			FileCheckUtil.buildDirectory(VoiceRecognitionAppSetup.BASE_AUDIO_PATH);
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
	
	public static void setInstanceFalse() {
		
		instanceOf = false;
	}
}
