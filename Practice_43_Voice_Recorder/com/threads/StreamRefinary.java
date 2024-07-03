package com.threads;

import javax.sound.sampled.AudioFormat;

import com.library.audio.audioGramInitializer.AudioGramInitializer;
import com.library.util.Debug;

public class StreamRefinary implements Runnable{
	
	private int waveAmplitudeLimit;
	private int minValidSampleSize;
    private Thread thread;
    private static byte[][] audioByteStream;
    private static byte[]   saveByteStream;
    private static byte[]   tempStream;
    private AudioFormat audioFormat;  
    private static StartSaveAudioFeatures save;
    private int sequences = 0;
    private int sleepTime;
    public static boolean bufferSizeNonZero = false;
	private int validCounter;
	private int valid_Counter_temp;
	private int total_valid_Counter;
	private boolean counterEnabled = false;
	private int waveMetricCounter;
	private int metricCounterEnd;
	private int waveAvg;
	private int waveMax;
	private boolean addedStream ;
	private static int msb ; 
	private static int  lsb ;
	private static short sample;
	private final int AMPLITUDE_HEIGHT_OPTIMIZATION_KONSTANT = 2000;
    
    public StreamRefinary(AudioFormat audioFormat) {
		this.audioFormat = audioFormat;
		bufferSizeNonZero = false;
		sleepTime = (( (int) audioFormat.getFrameSize() * (int) audioFormat.getSampleRate()) / AudioListener.bufferLengthInBytes )*1000 /2;
		waveAmplitudeLimit = AudioGramInitializer.VOLUME_LIMIT;
		minValidSampleSize = AudioGramInitializer.VALID_COUNTER;
		Debug.debug(1,"StreamRefinary Start minValidSampleSize: "+minValidSampleSize + " waveHeightlimit: "+waveAmplitudeLimit+" sleepTime: "+sleepTime);
	}

	public void start() {
        thread = new Thread(this);
        thread.setName("Stream Refinary Thread");
        thread.start();
    }

    public void stop() {
        thread = null;
    }
    
	@Override
	public void run() {
	 
		Debug.debug(1,"Starting StreamRefinary Thread" );

		while(true) {

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			if(bufferSizeNonZero) 	
				readAudioBuffer();
		}
	}
	
	public void readAudioBuffer() {
		    Debug.debug(5,"readAudioBuffer Theadname"+ Thread.currentThread().getName());
			bufferCheck(AudioListener.audioBuffers.poll());	
			bufferSizeNonZero = false;		
	}
	
	private byte[] bufferCheck(byte[] inputBuffer ) {
		
		Debug.debug(5,"StreamRefinary bufferCheck 1. Started sequence: "+sequences + " inputBuffer.length "+inputBuffer.length +" inputBuffer[0] "+ inputBuffer[0]+" inputBuffer[1] "+inputBuffer[1]+" inputBuffer.length-2 "+ inputBuffer[inputBuffer.length-2]+" inputBuffer.length-1 "+inputBuffer[inputBuffer.length-1]);
		valid_Counter_temp = validCounter;
		validCounter = 0;
		addedStream = false;
		waveMetricCounter = 1;
		metricCounterEnd = 0;
		waveAvg = 0;
		waveMax = 0;
	
      	if(valid_Counter_temp > 50 &&  audioByteStream == null) {
      			audioByteStream = new byte[10][];
    			audioByteStream[sequences++] = tempStream;
    			total_valid_Counter = valid_Counter_temp ;
    			Debug.debug(4,"StreamRefinary bufferCheck 2. Voice Stream build started  valid_Counter_temp: "+valid_Counter_temp + ", total_valid_Counter "+total_valid_Counter +", sequences: "+sequences);
    	}

        for (int i = 0; i < inputBuffer.length; i=i+2) {
        	
	   		msb = inputBuffer[i] & 0xFF; 
		    lsb = inputBuffer[i + 1] & 0xFF;
	        sample =  (short)((msb << 8) | lsb & 0xFF); 
	        
	        if(i % 1000 == 0) {
	        		waveMetricCounter = i;
	        		counterEnabled = true;
	        		metricCounterEnd = i+50 ;        	
	        }
	        
        	if(counterEnabled && waveMax <  Math.abs(sample))
        		waveMax = Math.abs(sample); 
        	
        	if(metricCounterEnd == i) {
        		waveAvg += waveMax;
        		counterEnabled = false;
        		Debug.debug(5,"StreamRefinary bufferCheck 3. added i: " + i+" waveMax:  "+waveMax);
        	}

	        if(sample > waveAmplitudeLimit)
	        	validCounter++;
        }
                
        if(audioByteStream == null && validCounter < minValidSampleSize /10)    {  
    		waveAmplitudeLimit = (int) (waveAvg / (inputBuffer.length  /1000)+ AMPLITUDE_HEIGHT_OPTIMIZATION_KONSTANT);
        	Debug.debug(3,"StreamRefinary bufferCheck 5. Possible speech NOT found! valid_Counter_temp: "+ valid_Counter_temp+ ", validCounter: " +validCounter+", total_valid_Counter: "  +(valid_Counter_temp+validCounter)+ ", waveHeightlimit: "+waveAmplitudeLimit);      
        }
        
    	if(audioByteStream != null && validCounter > minValidSampleSize /20 ) {
    		addToStream(inputBuffer);
    		addedStream = true;
    		total_valid_Counter = total_valid_Counter + validCounter;
    		Debug.debug(3,"StreamRefinary bufferCheck 6. Continue adding, validCounters: " +validCounter +", total_valid_Counter: "+total_valid_Counter +", sequences: "+sequences+", waveHeightlimit: "+waveAmplitudeLimit);
    		return null;
    	}

        if (audioByteStream != null &&  addedStream == false) {
        	
        	if(validCounter > 5 )
        		addToStream(inputBuffer);
        	
        	sendStreamToExamine();
        	Debug.debug(4,"StreamRefinary bufferCheck 7. Voice Stream build finished validCounters: "+ validCounter+", total_valid_Counter: "+ total_valid_Counter+", sequences: "+sequences+ ", waveHeightlimit: "+waveAmplitudeLimit );
			audioByteStream = null;
			saveByteStream = null;
			total_valid_Counter = 0;
			sequences = 0;
			AudioGramInitializer.VOLUME_LIMIT = waveAmplitudeLimit;
        }
        	tempStream = inputBuffer;
        		
		return null;
	}
	
	private void addToStream (byte[] inputStream) {
		
			audioByteStream[sequences++] = inputStream;
			Debug.debug(5,"StreamRefinary addToStream added sequence: "+(sequences-1));
	}
	
	private void sendStreamToExamine() {
		convert2DArrrayTo1DArray();
		save = new StartSaveAudioFeatures(saveByteStream,AudioListener.speechName,audioFormat);
		//AudioInputStream stream = BuildAudioInputStream.buildAudioInputStream(saveByteStream, audioFormat);
		//save = new StartSaveAudioFeatures("alma",stream);
	}
	
	private void convert2DArrrayTo1DArray() {
		
		saveByteStream = new byte[(sequences)* audioByteStream[0].length];
		Debug.debug(3,"StreamRefinary convert2DArrrayTo1DArray saveByteStream.length " +saveByteStream.length+" sequences: "+sequences + " audioByteStream[0].length: "+audioByteStream[0].length );
		AudioGramInitializer.waveCounter = 0;
		for(int i = 0; i < sequences; i++) {

			for(int j = 0; j < audioByteStream[i].length; j++) {
				saveByteStream[AudioGramInitializer.waveCounter++] = audioByteStream[i][j];
			}
		}
	}
}
