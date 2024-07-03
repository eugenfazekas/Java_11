package com.library.audio.audioGramInitializer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

import com.library.audio.audioGramBuilder.AmplitudeGramBuilder;
import com.library.audio.audioGramBuilder.FrequencyGramBuilder;
import com.library.audio.audioGramBuilder.SpektroGramBuilder;
import com.library.audio.audioGramSaver.SaveMultiAudioFeatures;
import com.library.util.AudioUtil;
import com.library.util.Debug;


public class AudioGramConstructor {

	    public static void buildMultiAudioGrammFromInputFile(String inputFileName) {
	    	Debug.debug(2,"MySpektrumAnalysis buildMultiAudioGrammFromInputFile inputFileName: "+inputFileName);
	    	AudioGramInitializer.buildAudiodData(AudioUtil.buildAudioInputStreamFromFile(inputFileName));
			AudioGramInitializer.buildSamples();
			AmplitudeGramBuilder.mainBuilder();
			FrequencyGramBuilder.mainBuilder();
			SpektroGramBuilder.mainBuilder();
			SaveMultiAudioFeatures.mainSave(inputFileName);
		} 
		 
		 public static void buildMultiAudioGrammFromInputStream(AudioInputStream inputAudioInputStream, String outputFileName) {
			Debug.debug(2,"MySpektrumAnalysis buildMultiAudioGrammFromInputStream outputFileName: "+outputFileName);
			AudioGramInitializer.buildAudiodData(inputAudioInputStream);
			AudioGramInitializer.buildSamples();
			AmplitudeGramBuilder.mainBuilder();
			FrequencyGramBuilder.mainBuilder();
			SpektroGramBuilder.mainBuilder();
			SaveMultiAudioFeatures.mainSave(outputFileName);	
		} 
		 	
		 public static void buildMultiAudioGrammFromInputStream(byte[] byteAudioStream, String outputFileName, AudioFormat audioFormat) {
 
			Debug.debug(2,"AnalysisConstructor buildMultiAudioGrammFromByteArray 8 outputFileName: "+outputFileName);
			AudioGramInitializer.buildAudiodData(byteAudioStream,audioFormat);
			AudioGramInitializer.buildSamples();
			AmplitudeGramBuilder.mainBuilder();
			FrequencyGramBuilder.mainBuilder();
			SpektroGramBuilder.mainBuilder();
			SaveMultiAudioFeatures.mainSave(outputFileName);
		 };
		 
		 public static void buildMultiAudioGrammFromIntStream(int[] inputAudioArray, String outputFileName, AudioFormat audioFormat) {
			Debug.debug(2,"AnalysisConstructor buildMultiAudioGrammFromIntArray outputFileName: "+outputFileName);
			AudioGramInitializer.buildAudiodData(inputAudioArray,audioFormat);
			AudioGramInitializer.buildSamplesFromIntStream(inputAudioArray);
			AmplitudeGramBuilder.mainBuilder();
			FrequencyGramBuilder.mainBuilder();
			SpektroGramBuilder.mainBuilder();
			SaveMultiAudioFeatures.mainSave(outputFileName);
		}
}
