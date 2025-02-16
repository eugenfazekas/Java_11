package com.audio3.recorder.refinary;

import com.audio2.recorder.AudioListener;
import com.audio8.util.Debug;
import com.audio8.util.thread.MyThread;
import com.audio8.util.thread.ThreadManagement;
import com.audio8.util.thread.ThreadUtil;

public class VoiceRecognitionAmplitudeRefinary implements MyThread, StreamRefinary{

	public boolean threadIsActive;
	public boolean threadIsSuspended;
	public Thread thread;
	
	public VoiceRecognitionAmplitudeRefinary () {
		
		threadIsActive = true;
		threadIsSuspended = false;
		
		setAudioListener();
		
		new StreamRefinaryAmplitudeMethods();
		
		new StreamRefinaryFrequencyMethods();
		
		thread = new Thread(this);

		thread.start();
	}
	
	public void readAudioBuffer() {
		
	    Debug.debug(5,"StreamRefinaryAmplitudeMethods.bufferSizeNonZero: "
	    + StreamRefinaryAmplitudeMethods.bufferSizeNonZero);
	    
		bufferCheck(AudioListener.audioBuffers.poll());	
		
		StreamRefinaryAmplitudeMethods.bufferSizeNonZero = false;	
	}
	
	@Override
	public void run() {
		
		Thread.currentThread().setName("VoiceRecognitionAmplitudeRefinary");
		
		ThreadManagement.threads.add(this);
		ThreadUtil.suspendThreadCheck(this);
		
		Debug.debug(1,"Starting "+Thread.currentThread().getName() +" Thread!");
	
	//	StreamRefinaryAmplitudeMethods.initSetWavemAplitude(); 
		
		while(threadIsActive) {
			
			ThreadUtil.sleepThreadInMilisec(StreamRefinaryAmplitudeMethods.sleepTime/2);	
			
			if(StreamRefinaryAmplitudeMethods.bufferSizeNonZero) 	
				readAudioBuffer();
		}		
	}
	
	@Override
	public void bufferCheck(byte[] inputBuffer) {

		Debug.startTime = System.currentTimeMillis();
		
		StreamRefinaryAmplitudeMethods.initBaseBufferVariables(inputBuffer);
		
		StreamRefinaryFrequencyMethods.initBaseBufferVariables();
		
        for (int i = StreamRefinaryAmplitudeMethods.loopStart; i < inputBuffer.length; i=i+2) {

        	StreamRefinaryAmplitudeMethods.buildSampleValue(i); 
        	
        	StreamRefinaryAmplitudeMethods.addToIntBuffer();
        	
        	StreamRefinaryAmplitudeMethods.noiseReduction(i);
        	
        	StreamRefinaryAmplitudeMethods.amplitudeOptimizationMeasure();
	        
        	StreamRefinaryFrequencyMethods.VRAR_buildMilisecDualMap(
        		StreamRefinaryAmplitudeMethods.lastSample,StreamRefinaryAmplitudeMethods.sample,i); 
	        
        	StreamRefinaryAmplitudeMethods.validAmplitudeCounter();	        	      	
        }
        
        StreamRefinaryAmplitudeMethods.addFirstVoiceRecognitionAmplitudeStream();
        
        StreamRefinaryAmplitudeMethods.addValidVoiceRecognitionAmplitudeStream();
        
        StreamRefinaryAmplitudeMethods.optimizeAmplitudeLimit();
        
        StreamRefinaryAmplitudeMethods.saveVoiceRecognitionStreamAmplitudeToExamine();
 
        StreamRefinaryAmplitudeMethods.info(System.currentTimeMillis()-Debug.startTime);
	}
	
	public  void setAudioListener() {
		
		if(!AudioListener.isInstanceOf())
			new AudioListener();
	}
	
	@Override
	public void stopThread() {	
		
		threadIsActive = false;
	}
	
	@Override
	public void suspendThread() {
		
		threadIsSuspended = true;
	}
	
	@Override
	public void stopSuspendThread() {
		
		threadIsSuspended = false;
	}

	@Override
	public Thread getThread() {

		return thread;
	}

	@Override
	public boolean isThreadSuspended() {
		
		return threadIsSuspended;
	}
	
	@Override
	public MyThread getRefinaryThread() {

		return this;
	}	
}
