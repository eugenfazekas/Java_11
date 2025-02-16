package com.audio2.recorder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.TargetDataLine;

import com.audio0.main.AppSetup;
import com.audio3.recorder.refinary.StreamRefinaryAmplitudeMethods;
import com.audio8.util.AudioUtil;
import com.audio8.util.Debug;

public class AudioListener implements Runnable {

    public static AudioFormat format = AudioUtil.getAudioFormat("AudioListener");
    private Thread thread;
    public static ConcurrentLinkedQueue<byte[]> audioBuffers = new ConcurrentLinkedQueue<>();
    public int frameSizeInBytes = format.getFrameSize();
    public static int bufferLengthInBytes = (int) ((format.getFrameSize() * format.getSampleRate() / 1000) * AppSetup.MILISEC_BUFFER_LENGTH);
    public static int recorderBufferLength = bufferLengthInBytes/2;
    public static boolean addToRecorderBuffer;
    private static boolean InstanceOf;
    
    public AudioListener() {
    	
    	if(!InstanceOf) {

	    	StreamRefinaryAmplitudeMethods.bufferSizeNonZero = false;
	    	addToRecorderBuffer = true;
	    	InstanceOf = true;
			start();
			
			Debug.debug(1,"AudioListener frameSizeInBytes: "+frameSizeInBytes
					+" bufferLengthInBytes: " +bufferLengthInBytes);
    	}
    }

    public void start() {
    	
        thread = new Thread(this);
        thread.start();
    }

    public void stop() {
    	
        thread = null;
    }

    @Override
    public void run() {

    	Debug.debug(1,"Starting AudioListener Thread!");

        try (final ByteArrayOutputStream out = new ByteArrayOutputStream();
        		final TargetDataLine line = AudioUtil.getTargetDataLineForRecord();) {
		
            buildByteOutputStream(out, line, frameSizeInBytes, bufferLengthInBytes);
            
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }
        
        stop();
    }

    public void buildByteOutputStream(final ByteArrayOutputStream out, final TargetDataLine line, 
    		int frameSizeInBytes, final int bufferLengthInBytes) throws IOException {
        	
        Debug.debug(5,"AudioListener buildByteOutputStream LengthInBytes: "+bufferLengthInBytes
        		+ " frameSizeInBytes: " +frameSizeInBytes);
    	
    	byte[] data = new byte[bufferLengthInBytes];
    	
        line.start();
        
        while (thread.isAlive()) {
        	
        	Debug.startTime = System.currentTimeMillis();
            if (line.read(data, 0, bufferLengthInBytes) == -1)       	
                break;
            
            if(addToRecorderBuffer) {
            	
	            audioBuffers.add(data);
	            
	            Debug.debug(5,"AudioListener added to Buffer, audioBuffers.size() "
	            	+ audioBuffers.size() +" Data length: "+data.length);
	            
	            StreamRefinaryAmplitudeMethods.bufferSizeNonZero = true;
	            data = new byte[bufferLengthInBytes];
	            
	            Debug.debug(5,"AudioListenerrecorder time mSec: "+ (System.currentTimeMillis()- Debug.startTime) );
            }
        }
        
        Debug.debug(5,"AudioListener ByteArrayOutputStream size: "+ out.size());
    }

	public static boolean isInstanceOf() {
		
		return InstanceOf;
	}
}
