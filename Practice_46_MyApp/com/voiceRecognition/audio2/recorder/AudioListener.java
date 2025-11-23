package com.voiceRecognition.audio2.recorder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.TargetDataLine;

import com.MainAppSetup;
import com.threads.MyThread;
import com.threads.ThreadAction;
import com.threads.ThreadManagement;
import com.threads.ThreadObjectDetails;
import com.voiceRecognition.audio0.main.VoiceRecognitionAppSetup;
import com.voiceRecognition.audio1.logical.EntryPointMethods;
import com.voiceRecognition.audio8.util.Debug;

public class AudioListener implements MyThread {

    public static AudioFormat format = AudioUtil.getAudioFormat("AudioListener");
    public static ConcurrentLinkedQueue<byte[]> audioBuffers = new ConcurrentLinkedQueue<>();
    public int frameSizeInBytes = format.getFrameSize();
    public static int bufferLengthInBytes=(int) ((format.getFrameSize()*format.getSampleRate()/1000) 
    	* VoiceRecognitionAppSetup.RECORDER_MILISEC_BUFFER_LENGTH);
    public static int recorderBufferLength = bufferLengthInBytes/2;
    private static boolean instanceOf;
    
	private boolean threadIsActive;
	private boolean threadIsSuspended;
	private Thread thread;
	final private String THREAD_NAME = "AudioListener";
	ThreadObjectDetails threadObject;

	private static int debug_level_INFO = 5;
	private static int debud_level_DEBUG = 5;
    
    public AudioListener() {
    	
    	if(!instanceOf) {
    		
    		threadObject = new ThreadObjectDetails(THREAD_NAME, true,MainAppSetup.voiceRecognitionAppName);
    		threadIsActive = true;
    		threadIsSuspended = false;
    		thread = new Thread(this);		
    		thread.start();

	    	instanceOf = true;
			
			Debug.debug(debug_level_INFO,"AudioListener frameSizeInBytes: "+frameSizeInBytes
					+" bufferLengthInBytes: " +bufferLengthInBytes);
    	}
    }

    @Override
    public void run() {
		
		Thread.currentThread().setName(THREAD_NAME);
		
		Debug.debug(debug_level_INFO,"Starting "+Thread.currentThread().getName() +" Thread!");	
		
		ThreadManagement.threadActions.add(
				new ThreadAction("addingThread",-1,EntryPointMethods.getSvitch(),this,
						"addingThread AudioListener"));
		
		while(threadIsActive) {
			
			suspendThread();
			
	        try (final ByteArrayOutputStream out = new ByteArrayOutputStream();
	        		final TargetDataLine line = AudioUtil.getTargetDataLineForRecord();) {
			
	            buildByteOutputStream(out, line, frameSizeInBytes, bufferLengthInBytes);
	            
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            return;
	        }
		}	
		Debug.debug(debug_level_INFO,"Stopping AudioListener Thread!");
    }

    private void buildByteOutputStream(final ByteArrayOutputStream out, final TargetDataLine line, 
    		int frameSizeInBytes, final int bufferLengthInBytes) throws IOException {
        	
        Debug.debug(debud_level_DEBUG,"AudioListener buildByteOutputStream LengthInBytes: "+bufferLengthInBytes
        		+ " frameSizeInBytes: " +frameSizeInBytes);
    	
    	byte[] data = new byte[bufferLengthInBytes];
    	
        line.start();
        
        while (threadIsActive) {
        	
        	Debug.startTime = System.currentTimeMillis();
            if (line.read(data, 0, bufferLengthInBytes) == -1)       	
                break;

	            audioBuffers.add(data);
	            
	            Debug.debug(debud_level_DEBUG,"AudioListener added to Buffer, audioBuffers.size() "
	            	+ audioBuffers.size() +" Data length: "+data.length);
	            
	            data = new byte[bufferLengthInBytes];
	            
	            Debug.debug(debud_level_DEBUG,"AudioListeneRrecorder time mSec: "+ (System.currentTimeMillis()
	            		- Debug.startTime) );          
        }
        
        Debug.debug(debud_level_DEBUG,"AudioListener ByteArrayOutputStream size: "+ out.size());
    }

	public static boolean isInstanceOf() {
		
		return instanceOf;
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
}
