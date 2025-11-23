package com.audio3.recorder.refinary;

import java.util.Arrays;

import javax.sound.sampled.AudioFormat;

import com.audio0.main.AppSetup;
import com.audio0.main.Main;
import com.audio1.logical.EntryPointMethods;
import com.audio2.audioObject.AudioObject;
import com.audio2.recorder.AudioListener;
import com.audio4.audioGramInitializer.mainInit.AudioAnalysisThread;
import com.audio8.util.Debug;

public class StreamRefinaryAmplitudeMethods {
	
	static AudioFormat audioFormat; 

//	private static int lastValidCounter;

//	private static int totalValidCounter;
	static int startAmplitudeLimit;
    static int endAmplitudeLimit;
	static int minValidSampleSize;
	static int loopStart;
	static short sample;
	static int lastSample = 1;
	static boolean build;
	static int cycle;
	static byte[] byteBuffer;
	static int[] intBuffer;
	static int intBufferCounter;

	static int averageAmplitude = 0;
	
	private static boolean addedStream;
	private static int RECORDED_MILISEC;
	
	private static int BEFORE_LOOP_LENGTH;
	private static int BUFFER_MILISEC_BEFORE_LENGTH;
	
	private static int BUFFER_MILISEC_AFTER_LENGTH_END;

	
    private static byte[][]  lastByteBuffers;
    private static int lastByteBuffersCounter;
    private static int[][]   lastIntBuffers;
    private static int lastIntBuffersCounter;
    
    private static boolean addedBeforeIntStream;
    private static boolean addedBeforeFrequencyStream;
    private static boolean recordEndSet;
    
    private static byte[][] audioByteStream;
    private static int[][] audioIntStream;
    
    private static byte[] saveByteStream;
    private static int[]  saveIntStream;

    public static int sequences = 0;
    public static int sleepTime;
    public static int bufferLength;
    
	private static int actualHighestAvg;
	private static int highestAverage;
	private static int highestAmplitudeBuffer;
	private static int highestAmplitudeBufferCounter;
	private static int amplitude1;
	private static int amplitude2;
	private static int amplitude3;
		
	private static boolean validVoiceDetection;
	private static int validCounter;
	private static int validCounterLimit;
	private static int validLimit;
	private static int counter;
	private static int null_Counter;
	
	private static int debug_level_INFO = 5;
	private static int debud_level_DEBUG = 5;

    public StreamRefinaryAmplitudeMethods() {
    	
		audioFormat = AudioListener.format;
		cycle = 0;
		build = false;
		
		sleepTime = AppSetup.RECORDER_MILISEC_BUFFER_LENGTH;
		bufferLength = AudioListener.recorderBufferLength;
		
		BUFFER_MILISEC_BEFORE_LENGTH = StreamRefinaryUtil.getBuffersLengthByMilisec(
				(int)AudioListener.format.getSampleRate()
				,AppSetup.AUDIO_BUFFER_MILISEC_BEFORE_LENGTH);
				
		BEFORE_LOOP_LENGTH = BUFFER_MILISEC_BEFORE_LENGTH / AudioListener.recorderBufferLength + 1;
		BUFFER_MILISEC_AFTER_LENGTH_END = 0;
		validCounterLimit = (int) ((StreamRefinaryUtil.getBuffersLengthByMilisec(
				(int)AudioListener.format.getSampleRate()
				,AppSetup.RECORDER_MILISEC_BUFFER_LENGTH)) * 1);
		
		validLimit = validCounterLimit/10;
		validVoiceDetection = false;
		
		lastByteBuffers = new byte [AppSetup.RECORD_BUFFER_LENGTH * 2][];
		lastByteBuffersCounter = 0;
		lastIntBuffers = new int[AppSetup.RECORD_BUFFER_LENGTH][];
		lastIntBuffersCounter = 0;
		
		audioByteStream = new byte[10][];
		audioIntStream = new int[10][];
		sequences = 0;
		null_Counter = 0;
		
		Debug.debug(1,"StreamRefinaryAmplitudeMethods Start, bufferLength: "+bufferLength
			+", minValidSampleSize: "+minValidSampleSize + " waveHeightlimit: "+startAmplitudeLimit
			+" sleepTime: "+sleepTime);
	}

	static void initBaseBufferVariables(byte[] inputBuffer) {
		
		if(null_Counter > inputBuffer.length/8) {
			
			Debug.debug(1,"StreamRefinaryAmplitudeMethods initBaseBufferVariables"+
					"APPLICATION STOPPING! NO MICROPHONE DETECTED!");
			
		    Main.setStopAllThreads("StreamRefinaryAmplitudeMethods");
		}
		
		if(lastIntBuffersCounter == AppSetup.RECORD_BUFFER_LENGTH) {
			
			lastByteBuffers = new byte [AppSetup.RECORD_BUFFER_LENGTH * 2][];
			lastByteBuffersCounter = 0;
			lastIntBuffers = new int[AppSetup.RECORD_BUFFER_LENGTH][];
			lastIntBuffersCounter = 0;
		}
		
		RECORDED_MILISEC += AppSetup.RECORDER_MILISEC_BUFFER_LENGTH;
		loopStart = 0;
		lastByteBuffers[lastByteBuffersCounter++] = byteBuffer;
		lastIntBuffers [lastIntBuffersCounter++] = intBuffer;
		intBuffer = new int[inputBuffer.length/2];
		intBufferCounter = 0;
		addedStream = false;
		byteBuffer = inputBuffer;
	 	validVoiceDetection = false;
	 	highestAverage = 0;
		actualHighestAvg = 0;
		highestAmplitudeBuffer = 0;
		highestAmplitudeBufferCounter = 0;	
		null_Counter = 0;
	}
	
	static void buildSampleValue(int i) {
		
		lastSample = sample;
        sample = (short)  StreamRefinaryUtil.getEndian(audioFormat.isBigEndian()
        		,byteBuffer[i],byteBuffer[i+1]);
        
        Debug.debug(debud_level_DEBUG,"StreamRefinaryAmplitudeMethods i: "+i+", byteBuffer[i]: "+byteBuffer[i]
        	+", [i + 1]: "+byteBuffer[i+1] +", sample "+sample);
	}
	
	static void addToIntBuffer() {
		
		intBuffer[intBufferCounter++] = sample;
	}	
	
	static void amplitudeOptimizationMeasure(int i) {
		
		highestAmplitudeBuffer += Math.abs(sample);
		highestAmplitudeBufferCounter++;
		
		if(highestAmplitudeBufferCounter == 200) {

			actualHighestAvg = highestAmplitudeBuffer /200;

			if(actualHighestAvg > highestAverage ) {
				
				highestAverage = actualHighestAvg;
			}
			actualHighestAvg = 0;
			highestAmplitudeBuffer = 0;
			highestAmplitudeBufferCounter = 0;
		}
		
		if(byteBuffer[i+1]> -5 && byteBuffer[i+1] < 5)
			null_Counter++;
	}
	
	static void validAmplitudeCounter() {
 
		validVoiceCheck();
	}
	
	private static void addToAmplitudeAvg() {
		
		if(highestAverage > averageAmplitude)
			averageAmplitude = highestAverage;
				
		Debug.debug(debud_level_DEBUG,"StreamRefinaryAmplitudeMethods addToAmplitudeAvg; "+averageAmplitude);
	}
	
	static void optimizeAmplitudeLimit() {

		if(sequences == 0 ) { 
			
			amplitude1 = amplitude2;
			amplitude2 = amplitude3;
			amplitude3 = highestAverage;
			Debug.debug(debud_level_DEBUG,"StreamRefinaryAmplitudeMethods Optimizing amplitude1: "+ amplitude1
				+", amplitude2: "+ amplitude2+ ", amplitude3: "+amplitude3);
			
			endAmplitudeLimit = (int) (((amplitude1+amplitude2+amplitude3)/3) 
					* AppSetup.END_SOUND_THRESHOLD_MULTIPLIER);
			
			startAmplitudeLimit = (int) (((amplitude1+amplitude2+amplitude3)/3)
					*  AppSetup.START_SOUND_THRESHOLD_MULTIPLIER);	
			
			AppSetup.IDLE_AMPLITUDE_VOLUME = ((amplitude1+amplitude2+amplitude3)/3);
		}
	}
	
	static void addBeforeIntStream() {

		for(int i = lastByteBuffersCounter - BEFORE_LOOP_LENGTH; i  < lastByteBuffersCounter; i++) 
			addToStream(lastByteBuffers[i], lastIntBuffers[i]);	
		
		Debug.debug(debug_level_INFO,"StreamRefinaryAmplitudeMethods addBeforeIntStream BUFFER_MILISEC_BEFORE_LENGTH: "
				+BUFFER_MILISEC_BEFORE_LENGTH+", BEFORE_LOOP_LENGTH: "+BEFORE_LOOP_LENGTH);
	}
	
	static void addBeforeFrequnecyStream() {
		
		for(int i = StreamRefinaryFrequencyMethods.lastAmpMapsCounter - BEFORE_LOOP_LENGTH;
				i  < StreamRefinaryFrequencyMethods.lastAmpMapsCounter; i++) {
			
			StreamRefinaryFrequencyMethods. addToAmpFreqMap(
				StreamRefinaryFrequencyMethods.lastAmpMaps[i],
				StreamRefinaryFrequencyMethods.lastFreqMaps[i],
				StreamRefinaryFrequencyMethods.mapsLengthCounter[i]);
		}
		
		Debug.debug(debug_level_INFO,"StreamRefinaryAmplitudeMethods addBeforeFrequnecyStream" 
			+" BUFFER_MILISEC_BEFORE_LENGTH: "+BUFFER_MILISEC_BEFORE_LENGTH
			+", BEFORE_LOOP_LENGTH: "+BEFORE_LOOP_LENGTH);
	}

	static void addToStream (byte[] byteStream,int[] intStream) {
		
		audioByteStream[sequences] = byteStream;
		audioIntStream[sequences++] = intStream;		
		addToAmplitudeAvg();
		Debug.debug(debug_level_INFO,"StreamRefinaryAmplitudeMethods AddToStream Continue Adding Streams, valid_C: " 
			+ ", sequences: "+sequences+", wave_H_L: "+startAmplitudeLimit
			+ ", valVoice: "+validVoiceDetection);
	}
		
	static void addValidAmplitudeStream() {
		
	   	if(build == true && validVoiceDetection && addedStream == false) { 
	   		   		
	   		if(!addedBeforeIntStream) {
	   			
	   			addBeforeIntStream();
	   			addedBeforeIntStream = true;
	   		}
	   		
	   		addToStream(byteBuffer,intBuffer);   		
	    	addedStream = true;
	    }
	}
	
	 static void addValidMultiStream() {
		 
	   	if(build == true && StreamRefinaryFrequencyMethods.validVoiceDetection && addedStream == false ) {
	   			   		
	   		if(!addedBeforeFrequencyStream) {
	   			
	   			addBeforeIntStream();
	   			addBeforeFrequnecyStream();
	   			addedBeforeFrequencyStream = true;	   			
	   		}
	   		
			Debug.debug(debug_level_INFO,"StreamRefinaryAmplitudeMethods StreamRefinaryFrequencyMethods.validVoiceDetection: "
					+StreamRefinaryFrequencyMethods.validVoiceDetection );
	   		
	   		addToStream(byteBuffer,intBuffer); 
	   		StreamRefinaryFrequencyMethods.addToAmpFreqMap(
	   				StreamRefinaryFrequencyMethods.tempAmpMap,
	   				StreamRefinaryFrequencyMethods.tempFreqMap,
	   				StreamRefinaryFrequencyMethods.tempAmpMapCounter);
      		addedStream = true;
			validVoiceDetection = true;
	    }
	}
	
	 static void saveAmplitudeStreamToExamine() {
		 		
        if (build == true && sequences > 0  &&  addedStream == false) {

        	if(!recordEndSet) {
        		
        		BUFFER_MILISEC_AFTER_LENGTH_END = AppSetup.AUDIO_BUFFER_MILISEC_AFTER_LENGTH 
        			+ RECORDED_MILISEC;
        		
        		recordEndSet = true;
        		
        		Debug.debug(debug_level_INFO,"StreamRefinaryAmplitudeMethods RECORDED_MILISEC: "+RECORDED_MILISEC
        				+", BUFFER_MILISEC_AFTER_LENGTH_END: "+BUFFER_MILISEC_AFTER_LENGTH_END);
        	}
        	
        	if(recordEndSet) {
        		
        		if(RECORDED_MILISEC < BUFFER_MILISEC_AFTER_LENGTH_END) 
        			addToStream(byteBuffer,intBuffer);
        		
        		if(RECORDED_MILISEC > BUFFER_MILISEC_AFTER_LENGTH_END) {
        			
            		Debug.debug(debug_level_INFO,"StreamRefinaryAmplitudeMethods saveAmplitudeStreamToExamine"
            			+" added endStream!");

                	Debug.debug(debug_level_INFO,"StreamRefinaryAmplitudeMethods saveAmplitudeStreamToExamine"
                		+" Voice Stream Build Finished" +", sequences: " +sequences+ ", wave_H_L: "
                		+startAmplitudeLimit);
                	
                	convert2DArrrayTo1DArray();
                	
            		forwardDataToStartAudioAnalysis(saveByteStream,saveIntStream,null,null,
            				EntryPointMethods.getSpeechName(),audioFormat,averageAmplitude);

            		resetSaveStream();
        		}
        	}       
        }
	}
	
	 static void saveMultiStreamToExamine() {
		
        if (build == true && sequences > 0 &&  addedStream == false) {
        	       		
        	if(!recordEndSet) {
        		
        		BUFFER_MILISEC_AFTER_LENGTH_END = AppSetup.AUDIO_BUFFER_MILISEC_AFTER_LENGTH 
        			+ RECORDED_MILISEC;
        		
        		recordEndSet = true;
        		
        		Debug.debug(debug_level_INFO,"StreamRefinaryAmplitudeMethods RECORDED_MILISEC: "+RECORDED_MILISEC
            			+", BUFFER_MILISEC_AFTER_LENGTH_END: "+BUFFER_MILISEC_AFTER_LENGTH_END);
        	}
        	
        	if(recordEndSet) {
        		
        		if(RECORDED_MILISEC < BUFFER_MILISEC_AFTER_LENGTH_END) {
        			
        			addToStream(byteBuffer,intBuffer);
        	   		StreamRefinaryFrequencyMethods.addToAmpFreqMap(
        	   				StreamRefinaryFrequencyMethods.tempAmpMap,
        	   				StreamRefinaryFrequencyMethods.tempFreqMap,
        	   				StreamRefinaryFrequencyMethods.tempAmpMapCounter);
        	   		
            		Debug.debug(debug_level_INFO,"StreamRefinaryAmplitudeMethods Adding Ending RECORDED_MILISEC: "
            		+RECORDED_MILISEC+", BUFFER_MILISEC_AFTER_LENGTH_END: "+BUFFER_MILISEC_AFTER_LENGTH_END);
        		}
        		
        		if(RECORDED_MILISEC > BUFFER_MILISEC_AFTER_LENGTH_END) {

		        	convert2DArrrayTo1DArray();
		        	
		        	StreamRefinaryFrequencyMethods.freqeuncyArrayFilterEmptyElemnts();
		        	
		        	forwardDataToStartAudioAnalysis(saveByteStream,saveIntStream    				
						,StreamRefinaryFrequencyMethods.ampMap,StreamRefinaryFrequencyMethods.freqMap
		   				,EntryPointMethods.getSpeechName(),audioFormat,averageAmplitude);
		    		
		    		resetSaveStream();
		    		
		    		StreamRefinaryFrequencyMethods.resetSaveStream();  		
        		}
        	}
        }
	}

	 static void resetSaveStream () {
		
		audioByteStream = null;
		audioIntStream = null;
		saveByteStream = null;
		saveIntStream = null;
		audioByteStream = new byte[10][];
		audioIntStream = new int[10][];
		sequences = 0;
		averageAmplitude = 0;
	    addedBeforeIntStream = false;
	    addedBeforeFrequencyStream = false;
	    recordEndSet = false;
	}
	
	 static void convert2DArrrayTo1DArray() {

		saveByteStream = new byte[sequences * audioByteStream[0].length];
		saveIntStream = new int[sequences * audioIntStream[0].length];
		
		int counter1 = 0;
		int counter2 = 0;
		
		for(int i = 0; i < sequences; i++) {

			for(int j = 0; j < audioByteStream[i].length; j++) {
				saveByteStream[counter1++] = audioByteStream[i][j];
			}
			
			for(int k = 0; k < audioIntStream[i].length; k++) {
				saveIntStream[counter2++] = audioIntStream[i][k];
			}
		}	
		
		Debug.debug(debug_level_INFO,"StreamRefinaryAmplitudeMethods convert2DArrrayTo1DArray saveByteStream.length " 
			+ saveByteStream.length+" saveIntStream.length: "+saveIntStream.length
			+ " sequences: "+sequences + " audioByteStream[0].length: "+audioByteStream[0].length 
			+ " audioIntStream[0].length: " +audioIntStream[0].length );
	}
	
	 static void forwardDataToStartAudioAnalysis(byte []saveByteStream, int []saveIntStream
		,int[] ampMap,int[] freqMap, String speechName,AudioFormat audioFormat,int averageAmplitude) {
		Debug.debug(debug_level_INFO,"StreamRefinaryAmplitudeMethods forwardDataToStartAudioAnalysis: " 
			+ Arrays.toString(freqMap));	
		
		AudioAnalysisThread.analysisStarter(new AudioObject(saveByteStream,saveIntStream,ampMap,freqMap,
				speechName,audioFormat,averageAmplitude, EntryPointMethods.getSvitch()));
	}
	
	 static void info(long cycleLength) {
		
		cycle++;
		
		if( AppSetup.amplitudeRefinary)
			Debug.debug(debud_level_DEBUG,"StreamRefinaryAmplitudeMethods INFO cycle: "+cycle + ", c.legth: "+cycleLength
				+ " mSec"+ ", Wave_Idle: "+AppSetup.IDLE_AMPLITUDE_VOLUME
      			+ ", OP_L: "+ endAmplitudeLimit + ", Wave_H_L: "+startAmplitudeLimit );		
		
		if(cycle == 9)
			build = true;
	}
	
	private static void validVoiceCheck() {
			
		counter++;
		
		if(sample > 0 && sequences == 0 && Math.abs(sample) > startAmplitudeLimit) 		
			validCounter++;
		
		if(sample > 0 && sequences > 0 && Math.abs(sample) > startAmplitudeLimit)
			validCounter++;
		
		if(counter == validCounterLimit) {
			Debug.debug(debud_level_DEBUG,"StreamRefinaryAmplitudeMethods sample "+Math.abs(sample) 
				+ ", endAmplitudeLimit "+endAmplitudeLimit);
			
			if(validCounter >= validLimit) {
				validVoiceDetection = true;
			
			Debug.debug(debud_level_DEBUG,"StreamRefinaryAmplitudeMethods valid voice DETECTED, validLimit: " +validLimit
					+", check: "+ validCounter + ", startAmplitudeLimit "+startAmplitudeLimit);
			}
			
			Debug.debug(debud_level_DEBUG,"StreamRefinaryAmplitudeMethods valid voice NOT DETECTED, validLimit: " +validLimit
					+", check: "+ validCounter + ", startAmplitudeLimit "+startAmplitudeLimit);
			
			validCounter = 0;
			counter = 0;			
		}			
	}
}
 


