package com.audio8.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioInputStreamUtil {

	
	public static AudioInputStream buildAudioInputStream(byte[] byteStream, AudioFormat audioFormat) {
		
        ByteArrayInputStream bais = new ByteArrayInputStream(byteStream);      
        AudioInputStream audioOutStream = new AudioInputStream(bais, audioFormat, byteStream.length / audioFormat.getFrameSize());
        
        Debug.debug(2,"SaveAudioFeatures buildAudioInputStream byteStream.length: "+audioOutStream.getFrameLength());       
        	return audioOutStream;
	}
	
	   public static AudioInputStream buildAudioInputStreamFromFile(String filePath) {
			
			AudioInputStream audioInputStream = null;

			try {
				 audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
			} catch (UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				return audioInputStream;		
		}
	   
		public static byte[] buildAudiodDataFromAudioStream(AudioInputStream audioInputStream) {
			   
			byte[] audioData = new byte[(int) audioInputStream.getFrameLength() * audioInputStream.getFormat().getFrameSize()];
			 
	       try {
				audioInputStream.read(audioData);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   Debug.debug(3,"AudioUtil buildAudiodDataFromAudioStream");
		   return audioData;
		}
		
	    public static AudioInputStream convertToAudioInputStream(final ByteArrayOutputStream out, int frameSizeInBytes, AudioFormat audioFormat) {
	        byte audioBytes[] = out.toByteArray();
	        ByteArrayInputStream bais = new ByteArrayInputStream(audioBytes);
	        AudioInputStream audioStream = new AudioInputStream(bais, audioFormat, audioBytes.length / frameSizeInBytes);
	        	return audioStream;
	    }
	    
	    public static AudioInputStream modifiedStream(AudioInputStream originalStream) {
	        
	        AudioFormat originalFormat = originalStream.getFormat();

	        // Új mintavételezési frekvencia kiszámítása (10%-kal növelve)
	        float newSampleRate = originalFormat.getSampleRate() * 2f;
	        
	        // Új formátum létrehozása
	        AudioFormat newFormat = new AudioFormat(
	            originalFormat.getEncoding(),
	            newSampleRate,
	            originalFormat.getSampleSizeInBits(),
	            originalFormat.getChannels(),
	            originalFormat.getFrameSize(),
	            newSampleRate,
	            originalFormat.isBigEndian()
	        );

	        // Az eredeti audio átalakítása az új formátumba
	        AudioInputStream newStream = AudioSystem.getAudioInputStream(newFormat, originalStream);

	        	return newStream;
	    }
}
