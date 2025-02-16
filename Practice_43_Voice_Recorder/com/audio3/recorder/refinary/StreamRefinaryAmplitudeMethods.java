package com.audio3.recorder.refinary;

import java.util.Arrays;

import javax.sound.sampled.AudioFormat;

import com.audio0.main.AppSetup;
import com.audio1.logical.EntryPointMethods;
import com.audio2.audioObject.AudioObject;
import com.audio2.recorder.AudioListener;
import com.audio4.audioGramInitializer.AudioAnalysisThread;
import com.audio8.util.AudioUtil;
import com.audio8.util.Debug;
import com.audio8.util.filters.NoiseFilter;

public class StreamRefinaryAmplitudeMethods {
	
    private static AudioFormat audioFormat; 
    public static String speechName;
	private static int lastValidCounter;
	private static int startValidCounter = 0;
	private static int endValidCounter = 0;
	private static int totalValidCounter;
	private static int startAmplitudeLimit;
    private static int endAmplitudeLimit;
	private static int minValidSampleSize;
	public static int loopStart;
	static short sample;
	static int lastSample = 1;
	static boolean build = false;
	private static int cycle;
	public static byte[] byteBuffer;
	public static int[] intBuffer;
	private static int intBufferCounter;
	
	//private static int lastBufferAmplitudeAvg = 0;
	private static int averageAmplitude = 0;
	
	private static boolean addedStream;
    private static byte[][] audioByteStream;
    private static byte[]   saveByteStream;
    private static byte[]   lastByteBuffer;
    
    private static int[][] audioIntStream;
    private static int[]   saveIntStream;
    private static int[]   lastIntBuffer;
 
    public static int sequences = 0;
    public static int sleepTime;
    public static int bufferLength;
    public static boolean bufferSizeNonZero;
    
	private static int actualHighestAvg;
	private static int highestAverage;
	private static int highestAmplitudeBuffer;
	private static int highestAmplitudeBufferCounter;
	
	private static boolean validVoiceDetection;
	private static int validAmplitudeBuffer;
	private static int validAmplitudeBufferCounter;
	private static int resetAmplitudeBufferCounter;
	
	public static int filter1;
	public static int filter11;
	public static int filter2;
	public static int filter22;
	public static int filter3;
	public static int filter33;
	public static int filter4;
	public static int filter44;
	public static int filter5;
	public static int filter55;
	public static int[] filtered;
	public static int filteredCounter;

    public StreamRefinaryAmplitudeMethods() {
    	
		audioFormat = AudioListener.format;
		bufferSizeNonZero = false;
		speechName = EntryPointMethods.speechName;
		
		sleepTime = AppSetup.MILISEC_BUFFER_LENGTH;
		
		startAmplitudeLimit = AppSetup.STARTER_VOLUME_LIMIT;
		minValidSampleSize = AppSetup.VALID_COUNTER;
		bufferLength = AudioListener.recorderBufferLength;
		StreamRefinaryFrequencyMethods.buildFrequencySampleLengths();
		
		Debug.debug(1,"StreamRefinary Start, bufferLength: "+bufferLength+", minValidSampleSize: "
			+minValidSampleSize + " waveHeightlimit: "+startAmplitudeLimit+" sleepTime: "+sleepTime);
	}

	public static void initBaseBufferVariables(byte[] inputBuffer) {
		
		loopStart = 0;
		lastValidCounter = startValidCounter;
		lastByteBuffer = byteBuffer;
		lastIntBuffer = intBuffer;
		startValidCounter = 0;
		endValidCounter = 0;
		intBuffer = new int[inputBuffer.length/2];
		intBufferCounter = 0;
		addedStream = false;
		byteBuffer = inputBuffer;
	 	validVoiceDetection = false;
		actualHighestAvg = 0;
		highestAmplitudeBuffer = 0;
		highestAmplitudeBufferCounter = 0;	
		
		if(resetAmplitudeBufferCounter >= 20 ) {
			resetAmplitudeBufferCounter = 0;
			highestAverage = 0;
		}
		
		if(AppSetup.noiseFilter) {
			
			Debug.debug(5, "noiseReduction filter1: "+ filter1 + ", filter11: "+filter11+ ", filter2: "+filter2
					+ ", filter22: "+filter22+ ", filter3: "+filter3+ ", filter33: "+filter33+ ", filter4: "
					+ filter4+ ", filter44: "+filter44+ ", filter5: "+filter5+ ", filter55: "+filter55);
			
			Debug.debug(5, "noiseReduction filteredCounter: "+filteredCounter+", Array: "+ Arrays.toString(filtered));
			 filter1 = 0;
			 filter11 = 0;
			 filter2 = 0;
			 filter22 = 0;
			 filter3 = 0;
			 filter33 = 0;
			 filter4 = 0;
			 filter44 = 0;
			 filter5 = 0;
			 filter55 = 0;
			 filtered = new int[1000];
			 filteredCounter = 0;
			initNoiceReduction();
		}
	}
	
	public static void initNoiceReduction() {
		
		int i;
		
		for(i = 0; i < 22; i = i+2) {
			
			buildSampleValue(i);
			addToIntBuffer();
		}
		loopStart = i;
	}
	
	public static void buildSampleValue(int i) {
		
		lastSample = sample;
        sample = (short)  AudioUtil.getEndian(audioFormat.isBigEndian()
        		,byteBuffer[i],byteBuffer[i+1]);
        
        Debug.debug(5,"StreamRefinaryAmplitudeMethods i: "+i+", byteBuffer[i]: "+byteBuffer[i]
        	+", iplus1"+byteBuffer[i] +", sample "+sample);
	}
	
	public static void addToIntBuffer() {
		
		intBuffer[intBufferCounter++] = sample;		
	}
	
	public static void noiseReduction(int i) {
		
		if(AppSetup.noiseFilter)			
			NoiseFilter.noiseReductionFilter(i/2); 
	}
	
	public static void amplitudeOptimizationMeasure() {
		
		highestAmplitudeBuffer += Math.abs(sample);
		highestAmplitudeBufferCounter++;
		
		if(highestAmplitudeBufferCounter == 200) {

			actualHighestAvg = highestAmplitudeBuffer /200;
			resetAmplitudeBufferCounter++;
			
			if(actualHighestAvg > highestAverage ) {
				
				highestAverage = actualHighestAvg;
			}
			
			highestAmplitudeBuffer = 0;
			highestAmplitudeBufferCounter = 0;
		}
	}
	
	public static void validAmplitudeCounter() {
 
		validVoiceCheck();
	}
	
	public static void optimizeAmplitudeLimit() {

		if(sequences == 0 ) { 
			
			endAmplitudeLimit = (int) (highestAverage * AppSetup.END_SOUND_THRESHOLD_MULTIPLIER);
			startAmplitudeLimit = (int) (highestAverage *  AppSetup.START_SOUND_THRESHOLD_MULTIPLIER);	
			AppSetup.idle_amplitude_volume = highestAverage;
		}
	}

	public static void addFirstAmplitudeStream() {
		
      	if(build == true && validVoiceDetection && audioByteStream == null ) {	
      		
      		addFirstStream(lastByteBuffer, lastIntBuffer,lastValidCounter);  
      	}
	}
	
	public static void addFirstMultiStream() {
		
      	if(build == true && validVoiceDetection && audioByteStream == null ) {
      		
			addFirstStream(lastByteBuffer, lastIntBuffer,lastValidCounter);
			
      		StreamRefinaryFrequencyMethods.addToStreamDetailsMap(
      				StreamRefinaryFrequencyMethods.tempFrequencyMap
      				,StreamRefinaryFrequencyMethods.tempFrequencyMapCounter);
      		
      		StreamRefinaryFrequencyMethods.frequencyIndexCorrection = intBuffer.length;
      	}  	
	}
	
	public static void addFirstVoiceRecognitionAmplitudeStream() {	
		
      	if(build == true && validVoiceDetection ) {	
      		
      		addToAmplitudeAvg();
      		
      		StreamRefinaryFrequencyMethods.addToStreamDetailsMap(
      			StreamRefinaryFrequencyMethods.tempFrequencyMap
      			,StreamRefinaryFrequencyMethods.lastFrequencyMapCounter);
      		
			Debug.debug(4,"StreamRefinary Started First Stream Record valid_Counter_temp: "
				+lastValidCounter + ", total_valid_Counter: "+totalValidCounter +", sequences: "
				+sequences + " avg Ampl: "+averageAmplitude);
      	}    	
	}
	
	public static void addFirstVoiceRecognitionFrequencyStream() {	
		
      	if(build == true && validVoiceDetection ) {
      		
      		addToAmplitudeAvg();
      		
      		StreamRefinaryFrequencyMethods.addToStreamDetailsMap(
      			StreamRefinaryFrequencyMethods.tempFrequencyMap
      			,StreamRefinaryFrequencyMethods.lastFrequencyMapCounter);
      		
			Debug.debug(4,"StreamRefinary Started First Stream Record valid_Counter_temp: "
				+ ""+lastValidCounter + ", total_valid_Counter: "+totalValidCounter
				+ ", sequences: "+sequences + ", possibleVoices: "
				+ StreamRefinaryFrequencyMethods.possibleVoices+", f.Map.l: "
				+ StreamRefinaryFrequencyMethods.tempFrequencyMapCounter 
				+ " avg Ampl: "+averageAmplitude);
      	}    	
	}
	
	private static void addToAmplitudeAvg() {
		
		if(highestAverage > averageAmplitude)
			averageAmplitude = highestAverage;
				
		Debug.debug(3,"StreamRefinary addToAmplitudeAvg; "+averageAmplitude);

	}
	
	private static void addFirstStream(byte[] byteStream,int[] intStream,int valid_counter) {

		audioByteStream = new byte[10][];
		audioIntStream = new int[10][];
		audioByteStream[sequences] = byteStream;
		audioIntStream[sequences++] = intStream;
		totalValidCounter = valid_counter;
		
		addToAmplitudeAvg();
		
		Debug.debug(3,"StreamRefinary AddFirstStream lastValidCounter: "+lastValidCounter
			+ ", validCounter: " + startValidCounter + ", total_valid_Counter "+totalValidCounter
			+ ", sequences: "+sequences	+ ", avg Ampl: "+averageAmplitude);
	}
	
	public static void addToStream (byte[] byteStream,int[] intStream,int amplitudeBufferCounter) {
		
		audioByteStream[sequences] = byteStream;
		audioIntStream[sequences++] = intStream;
		totalValidCounter = totalValidCounter + startValidCounter;
		
		addToAmplitudeAvg();
		
		Debug.debug(3,"StreamRefinary AddToStream Continue Adding Streams, valid_C: " +startValidCounter 
				+ ", total_v_c: "+totalValidCounter
				+ ", sequences: "+sequences+", wave_H_L: "+startAmplitudeLimit+" avg Ampl: "
				+ averageAmplitude);
	}
		
	public static void addValidAmplitudeStream() {
		
      	if(build == true && validVoiceDetection  &&  audioByteStream == null) {
      		
      		addFirstStream(byteBuffer, intBuffer,startValidCounter);
      		
      		addedStream = true;
		}

	   	if(build == true && validVoiceDetection && addedStream == false) { 
	   		
	   		addToStream(byteBuffer,intBuffer,startValidCounter);
	   		
	    	addedStream = true;
	    }
	}
	
	public static void addValidMultiStream() {
		
      	if(build == true && validVoiceDetection  &&  audioByteStream == null) {
      		
      		addFirstStream(byteBuffer, intBuffer,startValidCounter);
      		
      		StreamRefinaryFrequencyMethods.addToStreamDetailsMap(
      			StreamRefinaryFrequencyMethods.tempFrequencyMap
      			,StreamRefinaryFrequencyMethods.tempFrequencyMapCounter);
      		
      		addedStream = true;
		}

	   	if(build == true && validVoiceDetection && addedStream == false 
	   		&& StreamRefinaryFrequencyMethods.possibleVoices > AppSetup.POSSIBLE_VOICE_LIMIT) {
	   		
	   		addToStream(byteBuffer,intBuffer,startValidCounter);
	   		
	    	StreamRefinaryFrequencyMethods.addToStreamDetailsMap(
	    		StreamRefinaryFrequencyMethods.tempFrequencyMap
	    		,StreamRefinaryFrequencyMethods.tempFrequencyMapCounter);
	    	
	   		addedStream = true;
	   		
			Debug.debug(4,"StreamRefinary addValidMultiStream valid_Counter_temp: "
				+lastValidCounter + ", total_valid_Counter "+totalValidCounter +", sequences: "
				+sequences	+ " avg Ampl: "+averageAmplitude+", possibleVoices: "
				+StreamRefinaryFrequencyMethods.possibleVoices);
	    }
	}
	
	public static void addValidVoiceRecognitionAmplitudeStream() {
		
	   	if(build == true && validVoiceDetection ) { 
	   		
	    	addedStream = true;
	      	
	    	addToAmplitudeAvg();
	    	
			Debug.debug(4,"StreamRefinary addValidVoiceRecognitionFrequencyStream v_C_temp: "
				+lastValidCounter+", valid_Counter: "+startValidCounter + ", total_valid_Counter "
				+totalValidCounter);	
	    		StreamRefinaryFrequencyMethods.addToStreamDetailsMap(
	    				StreamRefinaryFrequencyMethods.tempFrequencyMap
	    				,StreamRefinaryFrequencyMethods.tempFrequencyMapCounter);
	    }
	}
	
	public static void addValidVoiceRecognitionFrequencyStream() {

	   	if(build == true && validVoiceDetection 
	   		&& StreamRefinaryFrequencyMethods.possibleVoices > AppSetup.POSSIBLE_VOICE_LIMIT) {
	   		
	    	addedStream = true;
	    	
	      	addToAmplitudeAvg();
	      		
	    	StreamRefinaryFrequencyMethods.addToStreamDetailsMap(
	    			StreamRefinaryFrequencyMethods.tempFrequencyMap
	    			,StreamRefinaryFrequencyMethods.tempFrequencyMapCounter);
			
	    	Debug.debug(4,"StreamRefinary addValidVoiceRecognitionFrequencyStream add Stream "
	    		+"Record valid_Counter_temp: "+lastValidCounter + ", total_valid_Counter "
	    		+totalValidCounter +", possibleVoices: "
	    		+StreamRefinaryFrequencyMethods.possibleVoices);	
	   	}
	}
	
	public static void saveAmplitudeStreamToExamine() {
		
        if (build == true && audioByteStream != null &&  addedStream == false) {
        	
        	if(true) {
        		
        		addToStream(byteBuffer,intBuffer,startValidCounter);
        		Debug.debug(3,"StreamRefinary saveAmplitudeStreamToExamine added endStream!"
        			+ " validCounter: "+startValidCounter);
        	}
        	
        	//averageAmplitude = averageAmplitude/sequences;
        	
        	Debug.debug(4,"StreamRefinary saveAmplitudeStreamToExamine Voice Stream Build Finished"
        		+" valid_c: "+ startValidCounter+", total_v_c: "+ totalValidCounter +", sequences: "
        		+sequences+ ", wave_H_L: "+startAmplitudeLimit+ " avg Ampl: "+averageAmplitude);
        	
        	convert2DArrrayTo1DArray();
        	
    		forwardDataToStartAudioAnalysis(saveByteStream,saveIntStream,null,
    			speechName,audioFormat,averageAmplitude);

    		resetSaveStream();
        }
	}
	
	public static void saveMultiStreamToExamine() {
		
        if (build == true && audioByteStream != null &&  addedStream == false) {
        	
        	if(true) {
        		
           		addToStream(byteBuffer,intBuffer,startValidCounter);
           		
        		StreamRefinaryFrequencyMethods.addToStreamDetailsMap(
        				StreamRefinaryFrequencyMethods.tempFrequencyMap
        				,StreamRefinaryFrequencyMethods.tempFrequencyMapCounter);
        		
        		Debug.debug(3,"StreamRefinary saveMultiStreamToExamine added endStream!"
        			+" validCounter: "+startValidCounter + ", possibleVoices: "
        			+StreamRefinaryFrequencyMethods.possibleVoices);
        	}
        	
        	//averageAmplitude = averageAmplitude/sequences;
        	
        	Debug.debug(4,"StreamRefinary save Finished valid_c: "+ startValidCounter+", total_v_c: "
        		+ totalValidCounter+", sequences: "+sequences+", possVoices: "
        		+ StreamRefinaryFrequencyMethods.possibleVoices+ ", wave_H_L: "+startAmplitudeLimit
        		+ ", f.Map.L: "+StreamRefinaryFrequencyMethods.frequencyMapCounter+ " avg Ampl: "
        		+averageAmplitude);
        	 
        	Debug.debug(4,"StreamRefinary save Finished frequencyMap[frequencyMapCounter-3] "
        		+StreamRefinaryFrequencyMethods.frequencyMap[
        		    StreamRefinaryFrequencyMethods.frequencyMapCounter-3] 
        		+ " frequencyMap[frequencyMapCounter-2] "
        		+StreamRefinaryFrequencyMethods.frequencyMap[
        		    StreamRefinaryFrequencyMethods.frequencyMapCounter-2]
        		+" frequencyMap[frequencyMapCounter-1] "
        		+StreamRefinaryFrequencyMethods.frequencyMap[
        		    StreamRefinaryFrequencyMethods.frequencyMapCounter-1]
    			+" frequencyMap.length: "+StreamRefinaryFrequencyMethods.frequencyMap.length 
    			+ " frequencyMapCounter: "+StreamRefinaryFrequencyMethods.frequencyMapCounter
    						);

        	convert2DArrrayTo1DArray();
        	
        	StreamRefinaryFrequencyMethods.freqeuncyArrayFilterEmptyElemnts();
        	
    		forwardDataToStartAudioAnalysis(saveByteStream,saveIntStream
    				,StreamRefinaryFrequencyMethods.frequencyMap
    				,speechName,audioFormat,averageAmplitude);
    		
    		resetSaveStream();
    		
    		StreamRefinaryFrequencyMethods.resetSaveStream();  		
        }
	}
	
	public static void saveVoiceRecognitionStreamAmplitudeToExamine() {
		
        if (build == true &&  addedStream == false && StreamRefinaryFrequencyMethods.frequencyMapCounter > 0) {
        	
        	if(endValidCounter > minValidSampleSize /40) {
        		
        		StreamRefinaryFrequencyMethods.addToStreamDetailsMap(
        				StreamRefinaryFrequencyMethods.tempFrequencyMap
        				,StreamRefinaryFrequencyMethods.tempFrequencyMapCounter);
        		
        		totalValidCounter = totalValidCounter +startValidCounter;
        		
        		Debug.debug(3,"StreamRefinary saveVoiceRecognitionStreamAmplitudeToExamine"
        			+" added endStream!" + " validCounter: "+startValidCounter);
        	}
       	
        	StreamRefinaryFrequencyMethods.freqeuncyArrayFilterEmptyElemnts();
        	
        	Debug.debug(4,"StreamRefinary saveVoiceRecognitionStreamAmplitudeToExamine "
        		+ " Voice Stream Build Finished valid_c: "+ startValidCounter+", total_v_c: "
        		+ totalValidCounter+", wave_H_L: "+startAmplitudeLimit+", FrequencyMap.length: "
        		+ StreamRefinaryFrequencyMethods.frequencyMap.length+", avg A: "+averageAmplitude);
        	
    		forwardDataToStartAudioAnalysis(null,null,StreamRefinaryFrequencyMethods.frequencyMap
    				,speechName,audioFormat,averageAmplitude);
    		
    		resetSaveStream();
    		
    		StreamRefinaryFrequencyMethods.resetSaveStream();
        }
	}
	
	public static void saveVoiceRecognitionStreamFrequencyToExamine() {
		
        if (build == true &&  addedStream == false && StreamRefinaryFrequencyMethods.frequencyMapCounter > 0) {
        	
        	if(endValidCounter > minValidSampleSize /40 
        		|| StreamRefinaryFrequencyMethods.possibleVoices > 0) {
        		
        		StreamRefinaryFrequencyMethods.addToStreamDetailsMap(
        				StreamRefinaryFrequencyMethods.tempFrequencyMap
        				,StreamRefinaryFrequencyMethods.tempFrequencyMapCounter);
        		
        		totalValidCounter = totalValidCounter +startValidCounter;
        		
        		Debug.debug(3,"StreamRefinary saveVoiceRecognitionStreamFrequencyToExamine "
        			+"added endStream! validCounter: "+startValidCounter + ", possibleVoices: "
        			+StreamRefinaryFrequencyMethods.possibleVoices);
        	}
       	
        	StreamRefinaryFrequencyMethods.freqeuncyArrayFilterEmptyElemnts();
        	Debug.debug(4,"StreamRefinary saveVoiceRecognitionStreamFrequencyToExamine Voice "
        		+"Stream Build Finished valid_c: "+startValidCounter+", total_v_c: "+totalValidCounter
        		+", sequences: "+sequences+", possVoices: "
        		+StreamRefinaryFrequencyMethods.possibleVoices + ", wave_H_L: "+startAmplitudeLimit
        		+", f.Map.L: "+ StreamRefinaryFrequencyMethods.frequencyMapCounter+ " avg Ampl: "
        		+averageAmplitude);
        	
        	Debug.debug(4,"StreamRefinary saveVoiceRecognitionStreamFrequencyToExamine Voice "
        		+ "Stream Build Finished frequencyMap[frequencyMapCounter-3] "
        		+ StreamRefinaryFrequencyMethods.frequencyMap[
        		      StreamRefinaryFrequencyMethods.frequencyMapCounter-3] 
        		+ " frequencyMap[frequencyMapCounter-2] "
        		+StreamRefinaryFrequencyMethods.frequencyMap[
        		      StreamRefinaryFrequencyMethods.frequencyMapCounter-2]
        		+ " frequencyMap[frequencyMapCounter-1] "
        		+StreamRefinaryFrequencyMethods.frequencyMap[
        		      StreamRefinaryFrequencyMethods.frequencyMapCounter-1]
    			+ " frequencyMap.length: "+StreamRefinaryFrequencyMethods.frequencyMap.length
    			+ " frequencyMapCounter: "+StreamRefinaryFrequencyMethods.frequencyMapCounter);

    		forwardDataToStartAudioAnalysis(null,null,StreamRefinaryFrequencyMethods.frequencyMap
    			,speechName,audioFormat,averageAmplitude);
    		
    		resetSaveStream();
    		
    		StreamRefinaryFrequencyMethods.resetSaveStream();
        }
	}
	
	public static void resetSaveStream () {
		
		audioByteStream = null;
		audioIntStream = null;
		saveByteStream = null;
		saveIntStream = null;
		totalValidCounter = 0;
		sequences = 0;
		AppSetup.STARTER_VOLUME_LIMIT = startAmplitudeLimit;
		averageAmplitude = 0;
	}
	
	public static void convert2DArrrayTo1DArray() {

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
		
		Debug.debug(3,"StreamRefinary convert2DArrrayTo1DArray saveByteStream.length " 
			+ saveByteStream.length+" saveIntStream.length: "+saveIntStream.length
			+ " sequences: "+sequences + " audioByteStream[0].length: "+audioByteStream[0].length 
			+ " audioIntStream[0].length: " +audioIntStream[0].length );
	}
	
	public static void forwardDataToStartAudioAnalysis(byte []saveByteStream, int []saveIntStream
			,int[] frequencyMap, String speechName,AudioFormat audioFormat,int averageAmplitude) {
		//FFTFrecvencyCheck.getFrecvencys(saveIntStream);
		AudioAnalysisThread.analysisStarter(
			new AudioObject(saveByteStream,saveIntStream,frequencyMap,
					speechName,audioFormat,averageAmplitude));		
	}
	
	public static void info(long cycleLength) {
		
		cycle++;
		
		if( AppSetup.amplitudeRefinary)
			Debug.debug(3,"StreamRefinary INFO cycle: "+cycle + ", c.legth: "+cycleLength
				+ " mSec, v_c_temp: "+ lastValidCounter+ ", valid_c: " +startValidCounter
      			+ ", total__C: "  +totalValidCounter
      			+ ", Wave_Idle: "+AppSetup.idle_amplitude_volume
      			+ ", OP_L: "+ endAmplitudeLimit + ", Wave_H_L: "+startAmplitudeLimit );
		//System.out.println("Invalid frequency: "+invalidFrequencyCounter + ", waveOptimizationLimit  "+waveOptimizationLimit + ", waveAmplitudeLimit "+waveAmplitudeLimit );
		
		if(build == true && (AppSetup.frequencyRefinary 
			|| AppSetup.voiceRecognitionAmplitudeRefinary
			|| AppSetup.voiceRecognitionFrequencyRefinary))
			Debug.debug(3,"StreamRefinary INFO cycle: "+cycle + ", c.legth: "+cycleLength
				+ " mSec, v_c_temp: "+ lastValidCounter+ ", valid_c: " +startValidCounter
      			+ ", total__C: "  +totalValidCounter
      			+ ", poss_Voices: "+StreamRefinaryFrequencyMethods.possibleVoices+" Wave_Idle: "
      			+ AppSetup.idle_amplitude_volume+ ", Wave_H_L: "+startAmplitudeLimit
      			+ ", OP_L: "+ endAmplitudeLimit 
      			+ ", t.Map.l: "+StreamRefinaryFrequencyMethods.tempFrequencyMapCounter ); 
		
		if(cycle == 9)
			build = true;
	}
	
	private static void validVoiceCheck() {
		
		
		validAmplitudeBuffer += Math.abs(sample);
		validAmplitudeBufferCounter++;
		
		if(validAmplitudeBufferCounter == 2000) {

			
			if(validAmplitudeBuffer / 2000 > startAmplitudeLimit) {
				
				validVoiceDetection  = true;
				Debug.debug(3,"StreamRefinary validAmplitudeBuffer / 2000 "+ (validAmplitudeBuffer / 2000) + " startAmplitudeLimit "+startAmplitudeLimit );
			}		
			validAmplitudeBuffer = 0;
			validAmplitudeBufferCounter = 0;
		}
	}

}
 


