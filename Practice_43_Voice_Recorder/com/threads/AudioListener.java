package com.threads;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.TargetDataLine;

import com.library.util.AudioUtil;
import com.library.util.Debug;
import com.library.util.ThreadUtil;

public class AudioListener implements Runnable {

    private static AudioFormat format = AudioUtil.getAudioFormat("AudioListener");
    private Thread thread;
    public static ConcurrentLinkedQueue<byte[]> audioBuffers = new ConcurrentLinkedQueue<>();;
    private StreamRefinary streamRefinary;
    public int frameSizeInBytes = format.getFrameSize();
    public static int bufferLengthInBytes = (format.getFrameSize() * (int)format.getSampleRate());
    public static String speechName;
    private static int numBytesRead;
    
    
    public AudioListener(String inputSpeechName) {
    	speechName = inputSpeechName;
		streamRefinary = new StreamRefinary(format);
		streamRefinary.start();
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
        numBytesRead = 0;
        Debug.debug(5,"AudioListener buildByteOutputStream bufferLengthInBytes: "+bufferLengthInBytes + " frameSizeInBytes: " +frameSizeInBytes);
        line.start();
        
        while (thread.isAlive()) {
            if ((numBytesRead = line.read(data, 0, bufferLengthInBytes)) == -1) {       	
                break;
            }
            audioBuffers.add(data);
            Debug.debug(5,"AudioListener buildByteOutputStream Data length: "+data.length+ " audioBuffers.size() "+ audioBuffers.size());
            StreamRefinary.bufferSizeNonZero = true;
            //System.out.println("Audio listener buildByteOutputStream sequence: "+sequence++ +" data[0] " +data[0] +" data[1] "+data[1] +" data.length: "+data.length +" data.length-2 "+ data[data.length-2] + " data.length-1 " +data[data.length-1] );
            //System.out.println("Data added data.length "+data.length + " audioBuffers.length:" +audioBuffers.size()+ " numBytesRead "+numBytesRead);
            data = new byte[bufferLengthInBytes];
        }
        Debug.debug(5,"AudioListener ByteArrayOutputStream size: "+ out.size());
    }
}
