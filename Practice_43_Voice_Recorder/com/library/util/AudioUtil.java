package com.library.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioUtil {
	
	private static AudioFormat audioFormat;
	
	private static AudioFormat buildAudioFormatInstance() {
		
		 ApplicationProperties aConstants = new ApplicationProperties();
		 
		 AudioFormat.Encoding encoding = aConstants.ENCODING;
		 float rate = aConstants.RATE;
		 int channels = aConstants.CHANNELS;
		 int sampleSize = aConstants.SAMPLE_SIZE;
		 boolean bigEndian = aConstants.BIG_ENDIAN;
		
		 	return new AudioFormat(encoding, rate, sampleSize, channels, (sampleSize / 8) * channels, rate, bigEndian);
}

	public static AudioFormat getAudioFormat(String caller) {
		 AudioFormat format = buildAudioFormatInstance();
		 audioFormat = format;
		 Debug.debug(2,"AudioUtil format SampleRate():" +format.getSampleRate()+ " FrameRate(): "+format.getFrameRate()
		 + " format.getChannels():"+format.getChannels() +" FrameSize(): "+format.getFrameSize()+ " SampleSizeInBits(): "+format.getSampleSizeInBits()+ ",caller: "+ caller);
		 	return format;
	 }
	
    public static AudioInputStream convertToAudioIStream(final ByteArrayOutputStream out, int frameSizeInBytes) {
        byte audioBytes[] = out.toByteArray();
        ByteArrayInputStream bais = new ByteArrayInputStream(audioBytes);
        AudioInputStream audioStream = new AudioInputStream(bais, audioFormat, audioBytes.length / frameSizeInBytes);
        	return audioStream;
    }

    public static TargetDataLine getTargetDataLineForRecord() {
        TargetDataLine line;
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);
        if (!AudioSystem.isLineSupported(info)) {
            return null;
        }
        try {
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(audioFormat, line.getBufferSize());
        } catch (final Exception ex) {
            return null;
        }
        return line;
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
}
