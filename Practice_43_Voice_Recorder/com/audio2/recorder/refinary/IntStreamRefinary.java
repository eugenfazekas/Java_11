package com.audio2.recorder.refinary;

import com.audio1.recorder.AudioListener;
import com.audio8.util.Debug;
import com.audio8.util.ThreadUtil;

public class IntStreamRefinary implements StreamRefinary, Runnable  {
	
	public IntStreamRefinary () {
		new StreamRefinaryAmplitudeMethods();
		new Thread(this).start();
	}
	
	public void readAudioBuffer() {
	    Debug.debug(5,"IntStreamRefinary readAudioBuffer Theadname"+ Thread.currentThread().getName()+" audioBuffers.size() "+AudioListener.audioBuffers.size());
	    bufferCheck(AudioListener.audioBuffers.poll());	
		StreamRefinaryAmplitudeMethods.bufferSizeNonZero = false;		
	}
	
	@Override
	public void run() {	
		Debug.debug(1,"IntStreamRefinary Thread initialized! bufferSizeNonZero" +StreamRefinaryAmplitudeMethods.bufferSizeNonZero);
		while(true) {
			ThreadUtil.sleepThreadInMilisec(StreamRefinaryAmplitudeMethods.sleepTime);
			if(AudioListener.audioBuffers.size() > 0) 	
				readAudioBuffer();		
		}		
	}
	
	@Override
	public void bufferCheck(byte[] inputBuffer) {
		Debug.startTime = System.currentTimeMillis();
		
		StreamRefinaryAmplitudeMethods.initBaseBufferVariables();
		
        for (int i = 0; i < inputBuffer.length; i=i+2) {

        	StreamRefinaryAmplitudeMethods.buildSampleValue(inputBuffer[i],inputBuffer[i+1]); 
	        
        	StreamRefinaryAmplitudeMethods.addToIntBuffer();
	        
        	StreamRefinaryAmplitudeMethods.buildAmplitudeOptimizationResult(i,StreamRefinaryAmplitudeMethods.sample);
	        
        	StreamRefinaryAmplitudeMethods.validAmplitudeCounter();	        	      	
        }
        
        StreamRefinaryAmplitudeMethods.addFirstAmplitudeStream();
        
        StreamRefinaryAmplitudeMethods.addValidAmplitudeStream(inputBuffer);
        
        StreamRefinaryAmplitudeMethods.saveAmplitudeStreamToExamine(inputBuffer);
        
        StreamRefinaryAmplitudeMethods.optimizeAmplitudeLimit(inputBuffer);
       
        Debug.debug(1,"IntStreamRefinary bufferCheck cycleTime: "+(System.currentTimeMillis()-Debug.startTime));
	}
}
