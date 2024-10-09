package com.audio1.recorder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.TargetDataLine;

import com.audio2.recorder.refinary.StreamRefinaryAmplitudeMethods;
import com.audio2.recorder.refinary.StreamrefinarySelector;
import com.audio8.util.AudioUtil;
import com.audio8.util.Debug;
import com.audio8.util.ThreadUtil;

public class AudioListener implements Runnable {

    public static AudioFormat format = AudioUtil.getAudioFormat("AudioListener");
    private Thread thread;
    public static ConcurrentLinkedQueue<byte[]> audioBuffers = new ConcurrentLinkedQueue<>();;
    public int frameSizeInBytes = format.getFrameSize();
    public static int bufferLengthInBytes = (format.getFrameSize() * (int)format.getSampleRate());
    public static String speechName;

    
    public AudioListener(String inputSpeechName) {
    	speechName = inputSpeechName;
    	StreamrefinarySelector.mainRefinaryStarter();
    	StreamRefinaryAmplitudeMethods.bufferSizeNonZero = false;
		start();
		Debug.debug(1,"AudioListener frameSizeInBytes: "+frameSizeInBytes+ " bufferLengthInBytes: "+bufferLengthInBytes);
    }

    public void start() {
        thread = new Thread(this);
        thread.setName("AudioListener Thread");
        thread.start();
    }

    public void stop() {
        thread = null;
    }

    @Override
    public void run() {

    	Debug.debug(1,"Starting AudioListener Thread");

	        try (final ByteArrayOutputStream out = new ByteArrayOutputStream();
	        		final TargetDataLine line = AudioUtil.getTargetDataLineForRecord();) {
    		
	            buildByteOutputStream(out, line, frameSizeInBytes, bufferLengthInBytes);
	            
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            return;
	        }
	        
		ThreadUtil.printThreads("AudioListener Thread");
        stop();
    }

    public void buildByteOutputStream(final ByteArrayOutputStream out, final TargetDataLine line, int frameSizeInBytes, final int bufferLengthInBytes) throws IOException {
        byte[] data = new byte[bufferLengthInBytes];

        Debug.debug(5,"AudioListener buildByteOutputStream bufferLengthInBytes: "+bufferLengthInBytes + " frameSizeInBytes: " +frameSizeInBytes);
        line.start();
        
        while (thread.isAlive()) {
            if ((line.read(data, 0, bufferLengthInBytes)) == -1) {       	
                break;
            }
            audioBuffers.add(data);
            Debug.debug(5,"AudioListener added to Buffer, audioBuffers.size() "+ audioBuffers.size() +" Data length: "+data.length);
            StreamRefinaryAmplitudeMethods.bufferSizeNonZero = true;
            data = new byte[bufferLengthInBytes];
        }
        Debug.debug(5,"AudioListener ByteArrayOutputStream size: "+ out.size());
    }
}
