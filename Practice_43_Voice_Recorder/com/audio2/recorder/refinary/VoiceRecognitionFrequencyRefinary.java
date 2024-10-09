package com.audio2.recorder.refinary;

import com.audio1.recorder.AudioListener;
import com.audio8.util.Debug;
import com.audio8.util.ThreadUtil;

public class VoiceRecognitionFrequencyRefinary implements StreamRefinary, Runnable {

	public VoiceRecognitionFrequencyRefinary () {
		new StreamRefinaryAmplitudeMethods();
		new StreamRefinaryFrequencyMethods();
		new Thread(this).start();
	}
	
	public void readAudioBuffer() {
	    Debug.debug(5,"readAudioBuffer Theadname"+ Thread.currentThread().getName());
		bufferCheck(AudioListener.audioBuffers.poll());	
		StreamRefinaryAmplitudeMethods.bufferSizeNonZero = false;		
	}
	
	@Override
	public void run() {
		Debug.debug(1,"VoiceRecognitionFrequencyRefinary Thead initialized!");
		while(true) {
			ThreadUtil.sleepThreadInMilisec(StreamRefinaryAmplitudeMethods.sleepTime);		
			if(StreamRefinaryAmplitudeMethods.bufferSizeNonZero) 	
				readAudioBuffer();
		}		
	}
	
	@Override
	public void bufferCheck(byte[] inputBuffer) {
		
		Debug.startTime = System.currentTimeMillis();
		
		StreamRefinaryAmplitudeMethods.initBaseBufferVariables();
		StreamRefinaryFrequencyMethods.initBufferFrequencyVariables();
		
        for (int i = 0; i < inputBuffer.length; i=i+2) {

        	StreamRefinaryAmplitudeMethods.buildSampleValue(inputBuffer[i],inputBuffer[i+1]); 
	        
        	StreamRefinaryAmplitudeMethods.addToIntBuffer();
	        
        	StreamRefinaryAmplitudeMethods.buildAmplitudeOptimizationResult(i,StreamRefinaryAmplitudeMethods.sample);
	        
        	StreamRefinaryFrequencyMethods.buildMilisecFrequencyCheck(StreamRefinaryAmplitudeMethods.lastSample,StreamRefinaryAmplitudeMethods.sample,i); 
	        
        	StreamRefinaryAmplitudeMethods.validAmplitudeCounter();	        	      	
        }
        
        StreamRefinaryAmplitudeMethods.addFirstVoiceRecognitionFrequencyStream();
        
        StreamRefinaryAmplitudeMethods.addValidVoiceRecognitionFrequencyStream();
        
        StreamRefinaryAmplitudeMethods.saveVoiceRecognitionStreamFrequencyToExamine(inputBuffer);
        
        StreamRefinaryAmplitudeMethods.optimizeAmplitudeLimit(inputBuffer);	
        
        Debug.debug(1,"VoiceRecognitionFrequencyRefinary bufferCheck cycleTime: "+(System.currentTimeMillis()-Debug.startTime));
	}
}
