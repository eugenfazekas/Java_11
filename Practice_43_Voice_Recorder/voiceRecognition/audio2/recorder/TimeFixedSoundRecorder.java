package voiceRecognition.audio2.recorder;

import javax.sound.sampled.AudioFormat;

import voiceRecognition.audio0.main.AppSetup;
import voiceRecognition.audio1.logical.EntryPointMethods;
import voiceRecognition.audio2.audioObject.AudioObject;
import voiceRecognition.audio4.audioGramInitializer.mainInit.AudioAnalysisThread;
import voiceRecognition.audio7.threads.MyThread;
import voiceRecognition.audio7.threads.ThreadAction;
import voiceRecognition.audio7.threads.ThreadManagement;
import voiceRecognition.audio7.threads.util.ThreadObjectDetails;
import voiceRecognition.audio8.util.Debug;

public class TimeFixedSoundRecorder implements MyThread {

    private static AudioFormat format;
    private static int recordLength;
    private static String speechName;
	private static int sleepTime = AppSetup.RECORDER_MILISEC_BUFFER_LENGTH / 2;
    private static byte[] buffer;
    private static int[] intStream;
    private static int bufferCounter;
    private long endTime;
    	
	private boolean threadIsActive;
	private boolean threadIsSuspended;
	private Thread thread;
	final private String THREAD_NAME = "TimeFixedSoundRecorder"; 
	private ThreadObjectDetails threadObject; 
    private static int debug_level_INFO = 1;
    
    public TimeFixedSoundRecorder(String SpeechName, int RecordLength) {
    	
    	format = AudioUtil.getAudioFormat("MainRecord");
		recordLength = RecordLength;
		speechName = SpeechName;
		setAudioListener();
		
		threadIsActive = true;
		threadIsSuspended = false;
		threadObject = new ThreadObjectDetails(THREAD_NAME, true);
		thread = new Thread(this);		
		thread.start();
    }

    @Override
    public void run() {
    	
		Thread.currentThread().setName(THREAD_NAME);
		Debug.debug(1,"Starting "+Thread.currentThread().getName() +" Thread!");	
		ThreadManagement.threadActions.add(
				new ThreadAction("addingThread",-1,EntryPointMethods.getSvitch(),this,
						"addingThread TimeFixedSoundRecorder"));
		
		buffer = new byte[calculateBufferLength(recordLength)];
		bufferCounter = 0;
		Debug.debug(1, "Buffer length: "+buffer.length);
		endTime = System.currentTimeMillis() + recordLength;
		
		while(threadIsActive) {

			suspendThread("TimeFixedSoundRecorder main");
			
			if(AudioListener.audioBuffers.isEmpty())
				sleepThread("TimeFixedSoundRecorder main",sleepTime);
			
			if(!AudioListener.audioBuffers.isEmpty()) 	 				
				addTobuffer(AudioListener.audioBuffers.poll());
						
			if(System.currentTimeMillis() > endTime)
				ThreadManagement.threadActions.add(new ThreadAction(
				"stopAndRemoveThreadById",threadObject.getId(),EntryPointMethods.getSvitch(),this,
				"stopAndRemoveThreadById TimeFixedSoundRecorder"));
		}
		
		intStream = AudioUtil.buildIntStreamFromByteStream(buffer,format);

		AudioAnalysisThread.analysisStarter(new AudioObject(buffer,intStream,null,null,
				speechName,format,intStream[0],EntryPointMethods.getSvitch()));		
    }

	//Calculating with one one Frame.
	private int calculateBufferLength(int mSec) {
		
		int sampleSizeInBytesOneSec = (int) ((format.getSampleSizeInBits() / 8) 
				*  format.getSampleRate() * format.getChannels());
		float oneMilisecBufferLength = (float)sampleSizeInBytesOneSec / 1000;
		int finalBufferLengthInBytes = (int) (oneMilisecBufferLength * mSec) ;
		
		Debug.debug(1,"TimeFixedSoundRecorder calculateBufferLength sampleSizeInBytesOneSec: "
			+sampleSizeInBytesOneSec+ ", oneMilisecBufferLength: "
			+ oneMilisecBufferLength +", finalBufferLengthInBytes: "+finalBufferLengthInBytes);
		
		return finalBufferLengthInBytes;
	}
		
	private void addTobuffer(byte[] inputBuffer) {
		
		for(int i = 0; i < inputBuffer.length ; i++) {
			
			buffer[bufferCounter++] = inputBuffer[i];
			
		Debug.debug(5,"TimeFixedSoundRecorder i: "+ i + ", buffer length: "+inputBuffer.length 
			+ " inputBuffer[i] "+inputBuffer[i]+ "   " + buffer.length);
		}

	    Debug.debug(1, "TimeFixedSoundRecorder Timefixed added to buffer");
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
}
