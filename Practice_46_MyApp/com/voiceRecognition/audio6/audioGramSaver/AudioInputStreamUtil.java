package com.voiceRecognition.audio6.audioGramSaver;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.voiceRecognition.audio8.util.Debug;

public class AudioInputStreamUtil {
	
	private static int debug_level_INFO = 5;
	
	public static AudioInputStream buildAudioInputStream(byte[]byteStream,AudioFormat audioFormat){
		
        ByteArrayInputStream bais = new ByteArrayInputStream(byteStream);      
        AudioInputStream audioOutStream = new AudioInputStream(bais, audioFormat, byteStream.length
        															/audioFormat.getFrameSize());
        
        Debug.debug(debug_level_INFO,"SaveAudioFeatures buildAudioInputStream byteStream.length: "
        	+audioOutStream.getFrameLength());  
        
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
			   
		byte[] audioData = new byte[(int) audioInputStream.getFrameLength() 
		                            			* audioInputStream.getFormat().getFrameSize()];		 
        try {
			audioInputStream.read(audioData);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	    Debug.debug(debug_level_INFO,"AudioUtil buildAudiodDataFromAudioStream length: "
	    		+audioData.length);
	    
	    	return audioData;
	}
		
    public static AudioInputStream convertToAudioInputStream(final ByteArrayOutputStream out
    		,int frameSizeInBytes, AudioFormat audioFormat) {
    	
        byte audioBytes[] = out.toByteArray();
        ByteArrayInputStream bais = new ByteArrayInputStream(audioBytes);
        
        AudioInputStream audioStream = new AudioInputStream(bais, audioFormat, audioBytes.length
        																	/ frameSizeInBytes);
        	return audioStream;
    }
}
