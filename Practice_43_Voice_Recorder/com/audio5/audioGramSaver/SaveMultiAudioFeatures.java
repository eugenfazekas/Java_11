package com.audio5.audioGramSaver;

import java.awt.image.BufferedImage;
import java.util.Arrays;

import javax.sound.sampled.AudioInputStream;

import com.audio0.main.AppSetup;
import com.audio3.audioGramInitializer.AudioAnalysisInitializer;
import com.audio4.audioGramBuilder.AmplitudeDetails;
import com.audio4.audioGramBuilder.FrequencyDetails;
import com.audio4.audioGramBuilder.MiscellaneousData;
import com.audio4.audioGramBuilder.MySpketrogramDetails;
import com.audio4.audioGramBuilder.SpektroGramBuilder;
import com.audio8.util.AudioInputStreamUtil;
import com.audio8.util.Debug;
import com.audio8.util.FileUtil;

public class SaveMultiAudioFeatures {

	public static String speechName;	
	public static AudioInputStream audioInputStream; 
	private static BufferedImage image;
	public static int waveHeightLimit = 100;
	private static String[] PATHS;
	private static String MAIN_PATH;
	private static String SUB_PATH;
			
	public static void mainSave(String Main_path,String Sub_path,String inputSpeechName) {

		if(!AudioAnalysisInitializer.build || !AppSetup.multiAnalysis) return;

        Debug.startTime = System.currentTimeMillis();
        
		PATHS = BuildAudioAnalysisFilesPaths.buildAudioAnalysisFilesPaths(); 
		MAIN_PATH = Main_path;
		SUB_PATH = Sub_path;
		speechName = inputSpeechName;
		Debug.debug(1,"SaveMultiAudioFeatures mainSave: "+MAIN_PATH+SUB_PATH+speechName );
		FileUtil.buildAndCheckDirectoryPath(MAIN_PATH+SUB_PATH+speechName,PATHS);
		saveAmplitudeGramm();
		saveFrequencyGramm();
		saveSpektroGramm();
		saveMySpektroGramm();
		saveRawAudioData();
		buildSequenceArray();
		saveVoiceRecognitionArray();
		saveAudioStream();
		
		Debug.debug(1,"SaveMultiAudioFeatures mainSave cycle time: "+(System.currentTimeMillis()-Debug.startTime));
	}
		
	public static void saveAmplitudeGramm() {
		
		if(AmplitudeDetails.amplitudeWaveMap != null && AppSetup.amplitudeGram) {
			image = BuildAudioImage.buildImage(AmplitudeDetails.amplitudeWaveMap , AmplitudeDetails.amplitudeWaveMap.length, waveHeightLimit);
			FileUtil.addImageFileToLibrary(image,"AmplitudeGram-"+speechName, MAIN_PATH+SUB_PATH+speechName+"/amplitudeGram");
		}
	} 
	
	public static void saveFrequencyGramm() {
		
		if(FrequencyDetails.frequencyWaveMap != null && AppSetup.frequencyGram) {
			image = BuildAudioImage.buildImage(FrequencyDetails.mappedFrequencyArray , FrequencyDetails.frequencyWaveMap.length, waveHeightLimit);
			FileUtil.addImageFileToLibrary(image,"FrequencyGram-"+speechName, MAIN_PATH+SUB_PATH+speechName+"/frequencyGram");
		}
	} 
	
	public static void saveSpektroGramm() {
		
		if(SpektroGramBuilder.spectrogram != null && AppSetup.spektrogram) {
			image = BuildAudioImage.buildSpectrogramImage();
			FileUtil.addImageFileToLibrary(image,"SpektroGram-"+speechName, MAIN_PATH+SUB_PATH+speechName+"/spektroGram");
		}
	}
	
	public static void saveMySpektroGramm() {
		
		if(MySpketrogramDetails.spektrogramMap != null && AppSetup.mySpektrogram) {
			image = BuildAudioImage.buildMySpectrogramImage(MySpketrogramDetails.spektrogramMap ,MySpketrogramDetails.spektrogramMap.length/2, waveHeightLimit);
			FileUtil.addImageFileToLibrary(image,"MySpektroGram-"+speechName, MAIN_PATH+SUB_PATH+speechName+"/mySpektrogram");
		}
	}
	
	public static void saveRawAudioData()   {
		if(MiscellaneousData.rawAudioData != null && AppSetup.rawAudioData) {
			FileUtil.buildRawAudioDataTextFile(MiscellaneousData.rawAudioData, MAIN_PATH+SUB_PATH+speechName+"/rawAudioData",speechName,"rawAudioData");
		}
	}
	
	public static void buildSequenceArray() {
		if(MiscellaneousData.buildSequenceArray != null && AppSetup.buildSequenceArray) {
			FileUtil.buildRawAudioDataTextFile(MiscellaneousData.buildSequenceArray, MAIN_PATH+SUB_PATH+speechName+"/buildSequenceArray",speechName,"buildSequenceArray");
		}
	}
		
	public static void saveVoiceRecognitionArray() {
		
		if(MySpketrogramDetails.spektrogramMap != null && AppSetup.voiceRecognitionData) {
			 String path = MAIN_PATH+SUB_PATH+speechName+"/voiceRecognition/"+speechName+"-voiceRecognition.txt";
			 String[]  input = new String[1];
			 input[0] = Arrays.toString(MySpketrogramDetails.spektrogramMap);
			 boolean pathCHeck = FileUtil.fileExist(path);
			 if(pathCHeck) {
				 FileUtil.addStreamToFile(input[0], path);
			 } else {
				 FileUtil.createTextFile(input, path);
			 }
		}
	} 
	
	public static void saveAudioStream() {

		if(AppSetup.wave == true && AudioAnalysisInitializer.format != null) {	

			AudioAnalysisInitializer.buildAudiodDataFromInt(AudioAnalysisInitializer.audioSamples);			
			audioInputStream = AudioInputStreamUtil.buildAudioInputStream(AudioAnalysisInitializer.audioData, AudioAnalysisInitializer.format);
			FileUtil.addWaveFileToLibrary(speechName, MAIN_PATH+SUB_PATH+speechName+"/wave/", audioInputStream);
		} 
	} 	
}
