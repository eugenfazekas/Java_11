package com.library.audio.audioGramSaver;

import java.io.ByteArrayInputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

import com.library.util.Debug;

public class BuildAudioInputStream {

	
	public static AudioInputStream buildAudioInputStream(byte[] byteStream, AudioFormat audioFormat) {
		
        ByteArrayInputStream bais = new ByteArrayInputStream(byteStream);
        
        AudioInputStream audioOutStream = new AudioInputStream(bais, audioFormat, byteStream.length / audioFormat.getFrameSize());
        
        Debug.debug(2,"SaveAudioFeatures buildAudioInputStream byteStream.length: "+audioOutStream.getFrameLength());
        
        	return audioOutStream;
	}
}
