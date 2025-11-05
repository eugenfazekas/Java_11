package com.audio2.recorder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.TargetDataLine;

import com.audio0.main.AppSetup;
import com.audio7.threads.MyThread;
import com.audio7.threads.ThreadManagement;
import com.audio8.util.Debug;

public class AudioListener implements MyThread {

    public static AudioFormat format = AudioUtil.getAudioFormat("AudioListener");
    public static ConcurrentLinkedQueue<byte[]> audioBuffers = new ConcurrentLinkedQueue<>();
    public int frameSizeInBytes = format.getFrameSize();
    public static int bufferLengthInBytes=(int) ((format.getFrameSize()*format.getSampleRate()/1000) 
    	* AppSetup.RECORDER_MILISEC_BUFFER_LENGTH);
    public static int recorderBufferLength = bufferLengthInBytes/2;
	public static AtomicBoolean bufferSizeNonZero = new AtomicBoolean(false);
    private static boolean InstanceOf;
	private boolean threadIsActive;
	private boolean threadIsSuspended;
	final private String THREAD_NAME = "AudioListener"; 
	private Thread thread;
    
    public AudioListener() {
    	
    	if(!InstanceOf) {
    		
    		threadIsActive = true;
    		threadIsSuspended = false;
    		thread = new Thread(this);		
    		thread.start();

	    	bufferSizeNonZero.set(false);
	    	InstanceOf = true;
			
			Debug.debug(1,"AudioListener frameSizeInBytes: "+frameSizeInBytes
					+" bufferLengthInBytes: " +bufferLengthInBytes);
    	}
    }

    @Override
    public void run() {
		
		Thread.currentThread().setName(THREAD_NAME);
		Debug.debug(1,"Starting "+Thread.currentThread().getName() +" Thread!");		
		ThreadManagement.addingThread(this);
		
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
		Debug.debug(5,"Stopping AudioListener Thread!");
    }

    private void buildByteOutputStream(final ByteArrayOutputStream out, final TargetDataLine line, 
    		int frameSizeInBytes, final int bufferLengthInBytes) throws IOException {
        	
        Debug.debug(5,"AudioListener buildByteOutputStream LengthInBytes: "+bufferLengthInBytes
        		+ " frameSizeInBytes: " +frameSizeInBytes);
    	
    	byte[] data = new byte[bufferLengthInBytes];
    	
        line.start();
        
        while (threadIsActive) {
        	
        	Debug.startTime = System.currentTimeMillis();
            if (line.read(data, 0, bufferLengthInBytes) == -1)       	
                break;

	            audioBuffers.add(data);
	            
	            Debug.debug(5,"AudioListener added to Buffer, audioBuffers.size() "
	            	+ audioBuffers.size() +" Data length: "+data.length);
	            
		    	bufferSizeNonZero.set(true);
	            data = new byte[bufferLengthInBytes];
	            
	            Debug.debug(5,"AudioListeneRrecorder time mSec: "+ (System.currentTimeMillis()
	            		- Debug.startTime) );          
        }
        
        Debug.debug(5,"AudioListener ByteArrayOutputStream size: "+ out.size());
    }

	public static boolean isInstanceOf() {
		
		return InstanceOf;
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
}
