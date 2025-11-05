package com.audio2.recorder;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

import com.audio3.recorder.refinary.StreamRefinaryUtil;
import com.audio8.util.Debug;

public class AudioUtil {
	
	private static AudioFormat audioFormat;
    private static int[] stream ;
    private static int counter ;   
    private static int amplitude;
	
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
	
    static TargetDataLine getTargetDataLineForRecord() {
    	
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
    
    public static int[] buildIntStreamFromByteStream(byte[] byteStream, AudioFormat audioFormat) {
    	  		
		amplitude = 0;
		counter = 0;
		stream = new int[byteStream.length / 2  ];
		counter = 0;

		for(int i = 0; i < stream.length; i = i +2 ) {
			
			stream[counter++] =  StreamRefinaryUtil.getEndian(audioFormat.isBigEndian()
	        		,byteStream[i],byteStream[i+1]);

			if(stream[counter-1] >  amplitude) 
				amplitude = stream[counter-1];  				
    	}	
		
		stream[0] = amplitude;
		
		Debug.debug(2,"AudioUtil buildIntStreamFromByteStream amplitude: "+ amplitude 
				+ " intStream.Length: "+ stream.length);
		
			return stream;
    }

}
