package com.audio1.logical;

import java.time.LocalDateTime;

import javax.sound.sampled.AudioInputStream;

import com.audio0.main.AppSetup;
import com.audio2.audioObject.AudioObject;
import com.audio2.audioSequenceBuilder.AudioSequenceBuilder;
import com.audio2.audioSequenceBuilder.TestSequence;
import com.audio2.audioSequenceBuilder.WavesArrayBuilderFromFile;
import com.audio2.recorder.AudioUtil;
import com.audio2.recorder.TimeFixedSoundRecorder;
import com.audio3.recorder.refinary.StreamRefinarySelector;
import com.audio4.audioGramInitializer.mainInit.AudioAnalysisThread;
import com.audio5.recognition.VoiceRecognitionDB;
import com.audio6.audioGramSaver.AudioInputStreamUtil;
import com.audio7.threads.ThreadManagement;
import com.audio8.util.Debug;

/*
 * Parameters in this class are starter function parameters.
 */
public class EntryPointMethods {

	public static String  speechName;
	private static String  svitch;
	private static boolean instanceOf;
	public static ThreadManagement threadManagement;
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
		
		if(threadManagement == null)
			threadManagement = new ThreadManagement();
		
		if(audioAnalysisThread == null)
			audioAnalysisThread = new AudioAnalysisThread();

		setMainProfile(option,inputName);		
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
				
			case "saveNamedRecords" :AppSetup.activateSaveNamedRecordsProfile();break;
			
			case "voiceRecognition" :AppSetup.activateVoiceRecognitionProfile();break;
			
			case "voiceRecognitionDebug" :AppSetup.activateVoiceRecognitionDebugProfile();break;
			
			case "saveContinueRecords" :AppSetup.activateSaveContinueRecordsProfile();break;
			
			case "buildSequenceFromFile" :AppSetup.activateBuildSequenceFromFileProfile();break;
						
			case "buildTestAudioSequence" :AppSetup.activateBuildTestAudioSequenceProfile();break;	
			
			case "buildSpektrogramFromAudioFile" :AppSetup.activateBuildSpektrogaramFromAudioFileProfile();break;
			
			case "timeFixedSoundRecorder" : AppSetup.activateTimeFixedSoundRecorderProfile();break;			
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
		
		int[] readedSequenceInfo = WavesArrayBuilderFromFile.mainBuilder(AppSetup.BASE_AUDIO_PATH 
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
				AudioInputStreamUtil.buildAudioInputStreamFromFile(AppSetup.BASE_AUDIO_PATH 
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
		
		new TimeFixedSoundRecorder(fileName,AppSetup.FIXED_RECORD_LENGTH_IN_MILISEC);
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
