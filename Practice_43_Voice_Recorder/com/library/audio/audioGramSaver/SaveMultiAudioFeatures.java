package com.library.audio.audioGramSaver;

import java.awt.image.BufferedImage;
import java.util.Arrays;

import javax.sound.sampled.AudioInputStream;

import com.library.audio.audioGramInitializer.AudioGramInitializer;
import com.library.util.FileUtil;

public class SaveMultiAudioFeatures {

	public static final String[] PATHS = new String[] {"wave","amplitudeGram","frequencyGram","spektroGram","rawAudioData","frequencyData"};
	public final static String BASE_AUDIO_PATH = "src/main/resources/static/audio/spektrum/";
	public static String speechName;
	public static int[] amplitudeMap;
	public static int[] frequencyMap;
	public static double spectrogram[][];
	public static String[] rawDataLines ;
	public static int[] frequencyArray;
	public static AudioInputStream audioInputStream;
	private static BufferedImage image;
	public static int waveHeightLimit = 100;
		
	public static void mainSave(String inputSpeechName) {
		
		speechName = inputSpeechName;
		FileUtil.buildAndCheckDirectoryPath(BASE_AUDIO_PATH+speechName,PATHS);
		saveAmplitudeGramm();
		saveFrequencyGramm();
		saveSpektroGrammGramm();
		saveRawAudioData();
		saveFrequencyArray();
		saveAudioStream();
	}
		
	public static void saveAmplitudeGramm() {
		
		if(amplitudeMap != null) {
			image = BuidAudioImage.buildImage(amplitudeMap, amplitudeMap.length, waveHeightLimit);
			FileUtil.addImageFileToLibrary(image,"AmplitudeGram-"+speechName, BASE_AUDIO_PATH+speechName+"/amplitudeGram");
		}
	} 
	public static void saveFrequencyGramm() {
		
		if(frequencyMap != null) {
			image = BuidAudioImage.buildImage(frequencyMap, frequencyMap.length, waveHeightLimit);
			FileUtil.addImageFileToLibrary(image,"FrequencyGram-"+speechName, BASE_AUDIO_PATH+speechName+"/frequencyGram");
		}
	} 
	
	public static void saveSpektroGrammGramm() {
		
		if(spectrogram != null) {
			image = BuidAudioImage.saveSpectrogramImage();
			FileUtil.addImageFileToLibrary(image,"SpektroGram-"+speechName, BASE_AUDIO_PATH+speechName+"/spektroGram");
		}
	} 
	public static void saveRawAudioData()   {
		
		if(rawDataLines != null) {
			 FileUtil.buildRawAudioDataTextFile(rawDataLines, speechName);
		}
	}
	public static void saveFrequencyArray() {
		
		if(frequencyArray != null) {
			 String path = BASE_AUDIO_PATH+speechName+"/frequencyData/"+speechName+"-frequencyArray.txt";
			 String[]  input = new String[1];
			 input[0] = Arrays.toString(frequencyArray);
			 boolean pathCHeck = FileUtil.fileExist(path);
			 if(pathCHeck) {
				 FileUtil.addStreamToFile(input[0], path);
			 } else {
				 FileUtil.createTextFile(input, path);
			 }
		}
	} 
	public static void saveAudioStream() {

		if(AudioGramInitializer.audioData != null && AudioGramInitializer.format != null) {		
			audioInputStream = BuildAudioInputStream.buildAudioInputStream(AudioGramInitializer.audioData, AudioGramInitializer.format);
			FileUtil.addWaveFileToLibrary(speechName, BASE_AUDIO_PATH+speechName+"/wave/", audioInputStream);
		} 
	} 
	
}
