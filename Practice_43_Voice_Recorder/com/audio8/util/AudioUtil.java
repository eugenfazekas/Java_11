package com.audio8.util;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

import com.audio2.recorder.ApplicationProperties;

public class AudioUtil {
	
	private static AudioFormat audioFormat;
	public static int msb; 
	public static int lsb;
	public static short endian = 0;
	public static short sample;
	
	private static AudioFormat buildAudioFormatInstance() {
		
		 ApplicationProperties aConstants = new ApplicationProperties();	 
		 AudioFormat.Encoding encoding = aConstants.ENCODING;
		 float rate = aConstants.RATE;
		 int channels = aConstants.CHANNELS;
		 int sampleSize = aConstants.SAMPLE_SIZE;
		 boolean bigEndian = aConstants.BIG_ENDIAN;
		
		 	return new AudioFormat(encoding, rate, sampleSize, channels, (sampleSize / 8)
		 													* channels, rate, bigEndian);
	}

	public static AudioFormat getAudioFormat(String caller) {
		
		 AudioFormat format = buildAudioFormatInstance();
		 audioFormat = format;
		 Debug.debug(2,"AudioUtil format SampleRate():" +format.getSampleRate()+ " FrameRate(): "
			+ format.getFrameRate()+ " format.getChannels():"+format.getChannels()+" FrameSize(): "
			+ format.getFrameSize()+ " SampleSizeInBits(): "+format.getSampleSizeInBits()
			+ ",caller: "+ caller);
		 
		 	return format;
	 }
	
    public static TargetDataLine getTargetDataLineForRecord() {
    	
        TargetDataLine line;
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);
        
        if (!AudioSystem.isLineSupported(info)) 
            return null;
        
        try {
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(audioFormat, line.getBufferSize());
        } catch (final Exception ex) {
            return null;
        }      
        	return line;
    }
    
	public static short getSampleBigEndian(int data0, int data1) {
		   
		msb = data0 & 0xFF; // Magasabb rendű (MSB) byte
	    lsb = data1 & 0xFF; // Kisebb rendű (LSB) byte
	     
		    return sample =  (short)((msb << 8) | lsb & 0xFF);   
	}

	public static short getSampleLitteleEndian(int data0, int data1) {
		   
	    lsb = data0 & 0xFF; // Kisebb rendű (LSB) byte
	    msb = data1 & 0xFF; // Magasabb rendű (MSB) byte
	    
		    return sample =  (short)((msb << 8) | lsb & 0xFF); 
	}

	public static short getEndian(boolean bigEndian, int data0, int data1) {
		   
		endian = bigEndian ? getSampleBigEndian(data0,data1) : getSampleLitteleEndian(data0,data1);
		
		    return endian;
	}
}
