package com.sound.recorder;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

import com.sound.MySpektrumAnalysis;
import com.sound.util.PathUtil;

public class MainRecord {
	
	public static AudioInputStream buildRecordInputStream (int milisLength) {

	    SoundRecorder soundRecorder = new SoundRecorder();
	    
		try {
		     soundRecorder.build(getAudioFormat());	
		     System.out.println("Start recording ....");
		     soundRecorder.start();
		     Thread.sleep(milisLength);
		     soundRecorder.stop();
		     Thread.sleep(milisLength+1000);
			} catch (Exception e) {
				System.out.println("Main Record error: "+e);
			}
			
		return soundRecorder.getAudioInputStream();

	}
	
	public static void fixedRecord(String speechName, int milisLength, boolean buildWave, boolean buildSpektrum) {
		
		AudioInputStream recordedStream = buildRecordInputStream(milisLength);
		
		PathUtil.buildAndCheckDirectoryPath(speechName,"wave","amplitude", "frequency","spektroGram");
		
		MySpektrumAnalysis.buildMultiAudioGrammFromInputStream(recordedStream, speechName);
		
		PathUtil.addWaveFileToLibrary(speechName, AudioFileFormat.Type.WAVE, recordedStream);
	}
	
	public static AudioFormat buildAudioFormatInstance() {
		
		 ApplicationProperties aConstants = new ApplicationProperties();
		 AudioFormat.Encoding encoding = aConstants.ENCODING;
		 float rate = aConstants.RATE;
		 int channels = aConstants.CHANNELS;
		 int sampleSize = aConstants.SAMPLE_SIZE;
		 boolean bigEndian = aConstants.BIG_ENDIAN;
		
		 	return new AudioFormat(encoding, rate, sampleSize, channels, (sampleSize / 8) * channels, rate, bigEndian);
 }
 
	private static AudioFormat getAudioFormat() {
		 AudioFormat format = buildAudioFormatInstance();
		 	return format;
	 }
 
	 /*
	 private static void buildWaveFile(AudioInputStream inputStream) {	 
		 WaveDataUtil wd = new WaveDataUtil();
		 wd.saveToFile("/SoundClip", AudioFileFormat.Type.WAVE, inputStream);	
		}
	 */
	 
 /*
  	public static void mainRecord () {
		
	
	try {
	 AudioFormat format = buildAudioFormatInstance();

     SoundRecorder soundRecorder = new SoundRecorder();
     soundRecorder.build(format);

     System.out.println("Start recording ....");
     soundRecorder.start();
     Thread.sleep(2000);
     soundRecorder.stop();

     WaveDataUtil wd = new WaveDataUtil();
     Thread.sleep(3000);
     wd.saveToFile("/SoundClip", AudioFileFormat.Type.WAVE, soundRecorder.getAudioInputStream());
	} catch(Exception e) {
		}
	}

 public static AudioFormat buildAudioFormatInstance() {
     ApplicationProperties aConstants = new ApplicationProperties();
     AudioFormat.Encoding encoding = aConstants.ENCODING;
     float rate = aConstants.RATE;
     int channels = aConstants.CHANNELS;
     int sampleSize = aConstants.SAMPLE_SIZE;
     boolean bigEndian = aConstants.BIG_ENDIAN;

     return new AudioFormat(encoding, rate, sampleSize, channels, (sampleSize / 8) * channels, rate, bigEndian);
 }
  */
}
