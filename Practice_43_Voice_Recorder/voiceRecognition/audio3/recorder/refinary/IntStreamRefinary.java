package voiceRecognition.audio3.recorder.refinary;

import voiceRecognition.audio1.logical.EntryPointMethods;
import voiceRecognition.audio2.recorder.AudioListener;
import voiceRecognition.audio7.threads.MyThread;
import voiceRecognition.audio7.threads.ThreadAction;
import voiceRecognition.audio7.threads.ThreadManagement;
import voiceRecognition.audio7.threads.util.ThreadObjectDetails;
import voiceRecognition.audio8.util.Debug;

public class IntStreamRefinary implements MyThread,StreamRefinary{
	
	private boolean threadIsActive;
	private boolean threadIsSuspended;
	private Thread thread;
	final private String THREAD_NAME = "IntStreamRefinary"; 
	private ThreadObjectDetails threadObject; 
	
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
	    	
            Debug.debug(1,"DebugException in IntStreamRefinary! " +ex.getMessage());  

    		ThreadManagement.threadActions.add(new ThreadAction("stopAllThreads",-1,null,this,
    				"stopAllThreads IntStreamRefinary"));
	    }
	}
	
	@Override
	public void run() {	
		
		Thread.currentThread().setName(THREAD_NAME);
		
		ThreadManagement.threadActions.add(
					new ThreadAction("addingThread",-1,EntryPointMethods.getSvitch(),this,
							"addingThread IntStreamRefinary"));
		
		Debug.debug(debug_level_INFO,"Starting "+Thread.currentThread().getName() +" Thread!");
		
		while(threadIsActive) {

			suspendThread("IntStreamRefinary main");
			
			sleepThread("IntStreamRefinary main",StreamRefinaryAmplitudeMethods.sleepTime/2);

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
	public void stopThread(String initializer) {	
		
		Debug.debug(debug_level_INFO, THREAD_NAME+ " stoping Thread "+initializer);
		threadIsActive = false;
	}
	
	@Override
	public void suspendThread(String initializer) {
		
		Debug.debug(debug_level_INFO, THREAD_NAME+ " suspend Thread "+initializer);
		while(isThreadSuspended(initializer)) {
			
			sleepThread(initializer,50);
		}
	}
	
	@Override
	public void setSuspendThread(String initializer) {
		
		Debug.debug(debug_level_INFO, THREAD_NAME+ " set Suspend Thread "+initializer);
		threadIsSuspended = true;		
	}
	
	@Override
	public void stopSuspendThread(String initializer) {
		
		Debug.debug(debug_level_INFO, THREAD_NAME+ " stop Suspend Thread "+initializer);
		threadIsSuspended = false;
	}

	@Override
	public Thread getThread(String initializer) {

		Debug.debug(debug_level_INFO, THREAD_NAME+ " get Thread "+initializer);
		return thread;
	}

	@Override
	public boolean isThreadSuspended(String initializer) {
		
		Debug.debug(debug_level_INFO, THREAD_NAME+ " is Thread Suspended "+initializer);
		return threadIsSuspended;
	}
	
	public void sleepThread(String initializer,int mSec) {
		
		Debug.debug(debug_level_INFO, THREAD_NAME+ " sleep Thread "+initializer);
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
