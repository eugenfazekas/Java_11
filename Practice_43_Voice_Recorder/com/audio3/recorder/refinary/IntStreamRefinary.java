package com.audio3.recorder.refinary;

import com.audio1.logical.EntryPointMethods;
import com.audio2.recorder.AudioListener;
import com.audio7.threads.MyThread;
import com.audio7.threads.ThreadAction;
import com.audio7.threads.ThreadManagement;
import com.audio7.threads.util.ThreadObjectDetails;
import com.audio8.util.Debug;

public class IntStreamRefinary implements MyThread,StreamRefinary{
	
	private boolean threadIsActive;
	private boolean threadIsSuspended;
	private Thread thread;
	final private String THREAD_NAME = "IntStreamRefinary"; 
	ThreadObjectDetails threadObject; 
	
	private static int debug_level_INFO = 5;
	
	public IntStreamRefinary () {

		setAudioListener();
		new StreamRefinaryAmplitudeMethods();
		
		threadObject = new ThreadObjectDetails(THREAD_NAME, true);		
		threadIsActive = true;
		threadIsSuspended = false;
		thread = new Thread(this);		
		thread.start();
	}
	
	private void readAudioBuffer() {
	    
	    try {    
	    		bufferCheck(AudioListener.audioBuffers.poll());	
	    
	    } catch (Exception ex) {
	    	
            Debug.debug(debug_level_INFO,"DebugException in IntStreamRefinary! " +ex.getMessage());  

    		ThreadManagement.threadActions.add(new ThreadAction("stopAllThreads",-1,null,this));
	    }
	}
	
	@Override
	public void run() {	
		
		Thread.currentThread().setName(THREAD_NAME);
		
		ThreadManagement.threadActions.add(
					new ThreadAction("addingThread",-1,EntryPointMethods.getSvitch(),this));
		
		Debug.debug(debug_level_INFO,"Starting "+Thread.currentThread().getName() +" Thread!");
		
		while(threadIsActive) {

			suspendThread();
			
			sleepThread(StreamRefinaryAmplitudeMethods.sleepTime/2);

			if(!AudioListener.audioBuffers.isEmpty()) 
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
        	
        	StreamRefinaryAmplitudeMethods.amplitudeOptimizationMeasure(i);
	        
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
	public ThreadObjectDetails getThreadObjectDetails() {

		return threadObject;
	}
	
	@Override
	public MyThread getRefinaryThread() {

		return this;
	}
}
