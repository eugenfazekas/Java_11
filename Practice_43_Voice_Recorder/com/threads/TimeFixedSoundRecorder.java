package com.threads;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.TargetDataLine;

import com.library.util.AudioUtil;
import com.library.util.Debug;

public class TimeFixedSoundRecorder implements Runnable {

    private AudioFormat format;
    private Thread thread;
    private long recordLength;
    private String speechName;
    private AudioInputStream audioStream;
    private StartSaveAudioFeatures save;

    public TimeFixedSoundRecorder(String speechName, long recordLength) {
    	this.format = AudioUtil.getAudioFormat("MainRecord");
		this.recordLength = recordLength;
		this.speechName = speechName;
		start();
    }

    public void start() {
        thread = new Thread(this);
        thread.setName("Fixed Sound Record Thread");
        thread.start();
    }

    public void stop() {
        thread = null;
    }

    @Override
    public void run() {

		Debug.debug(1,"Starting Fixed Sound Record Thread! ");

	        try (final ByteArrayOutputStream out = new ByteArrayOutputStream();
	        		final TargetDataLine line =  AudioUtil.getTargetDataLineForRecord();) {
	           
	            buildByteOutputStream(out, line, format.getFrameSize(), (line.getBufferSize() * format.getFrameSize()/5));
	            
	            audioStream =  AudioUtil.convertToAudioIStream(out, format.getFrameSize());
	            
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            return;
	        }
	        	        
	        save = new StartSaveAudioFeatures(audioStream, speechName);

        stop();
    }

    public void buildByteOutputStream(final ByteArrayOutputStream out, final TargetDataLine line, int frameSizeInBytes, final int bufferLengthInBytes) throws IOException {
        final byte[] data = new byte[bufferLengthInBytes];
        int numBytesRead;
        
		long startTime = System.currentTimeMillis();
		long endTime = startTime + recordLength;
        line.start();
        int counter = 0;

        while (endTime <= startTime + recordLength ) {
            if ((numBytesRead = line.read(data, 0, bufferLengthInBytes)) == -1) { 
            	Debug.debug(3,"data.length 1 :" +data[0] + " "+data[1] + " counter "+counter+ " numBytesRead" +numBytesRead);
                break;
            }
            out.write(data, 0, numBytesRead);
            Debug.debug(3,"data.length 2.:" +data.length + " counter "+counter+ " numBytesRead " +numBytesRead+ " bufferLengthInBytes "+bufferLengthInBytes);
            endTime = System.currentTimeMillis();

            counter++;   
        }
    }
}
