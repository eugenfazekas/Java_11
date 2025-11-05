package com.audio3.recorder.refinary;

import com.audio2.recorder.AudioListener;
import com.audio7.threads.MyThread;
import com.audio7.threads.ThreadManagement;
import com.audio8.util.Debug;

public class IntStreamRefinary implements MyThread,StreamRefinary{
	
	private boolean threadIsActive;
	private boolean threadIsSuspended;
	private Thread thread;
	final private static String THREAD_NAME = "IntStreamRefinary"; 
	
	public IntStreamRefinary () {

		setAudioListener();
		new StreamRefinaryAmplitudeMethods();
		
		threadIsActive = true;
		threadIsSuspended = false;
		thread = new Thread(this);		
		thread.start();
	}
	
	private void readAudioBuffer() {
		
	    Debug.debug(5,"IntStreamRefinary StreamRefinaryAmplitudeMethods.bufferSizeNonZero: "
	    + AudioListener.bufferSizeNonZero);
	    	    	
		    bufferCheck(AudioListener.audioBuffers.poll());	
		    
		    AudioListener.bufferSizeNonZero.set(false);	
	}
	
	@Override
	public void run() {	
		
		Thread.currentThread().setName(THREAD_NAME);		
		ThreadManagement.addingThread(this);
		
		Debug.debug(1,"Starting "+Thread.currentThread().getName() +" Thread!");
		
		while(threadIsActive) {

			suspendThread();
			
			sleepThread(StreamRefinaryAmplitudeMethods.sleepTime/2);
			
			if(AudioListener.bufferSizeNonZero.get()) 	
				readAudioBuffer();		
		}		
	}
	
	@Override
	public void bufferCheck(byte[] inputBuffer) {
		
		Debug.startTime = System.currentTimeMillis();

		StreamRefinaryAmplitudeMethods.initBaseBufferVariables(inputBuffer);

        for (int i = StreamRefinaryAmplitudeMethods.loopStart; i < inputBuffer.length; i=i+2) {

        	StreamRefinaryAmplitudeMethods.buildSampleValue(i); 
	        
        	StreamRefinaryAmplitudeMethods.addToIntBuffer();
        	
        	StreamRefinaryAmplitudeMethods.amplitudeOptimizationMeasure();
	        
        	StreamRefinaryAmplitudeMethods.validAmplitudeCounter();	        	      	
        }

        StreamRefinaryAmplitudeMethods.addValidAmplitudeStream();
        
        StreamRefinaryAmplitudeMethods.optimizeAmplitudeLimit();
        
        StreamRefinaryAmplitudeMethods.saveAmplitudeStreamToExamine();
       
        StreamRefinaryAmplitudeMethods.info(System.currentTimeMillis()-Debug.startTime);
	}
	
	private void setAudioListener() {
		
		if(!AudioListener.isInstanceOf())
			new AudioListener();
	}
	
	@Override
	public void stopThread() {	
		
		threadIsActive = false;
	}
	
	@Override
	public void suspendThread() {

		while(isThreadSuspended()) {
			
			sleepThread(50);
		}
	}
	
	@Override
	public void setSuspendThread() {
		
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
	
	public void sleepThread(int mSec) {
		
		try {
			Thread.sleep(mSec);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	@Override
	public String getThreadName() {
		
		return THREAD_NAME;
	}

	@Override
	public MyThread getRefinaryThread() {

		return this;
	}
}
