package com.audio2.recorder.refinary;

import javax.sound.sampled.AudioFormat;

import com.audio0.main.AppSetup;
import com.audio1.recorder.AudioListener;
import com.audio3.audioGramInitializer.AudioAnalysisInitializer;
import com.audio3.audioGramInitializer.AudioAnalysisThread;
import com.audio8.util.Debug;

public class StreamRefinaryAmplitudeMethods {
	
    static AudioFormat audioFormat; 
    public static int sampleRate;
	private static int valid_Counter_temp;
	private static int validCounter;
	private static int total_valid_Counter;
	private static int waveAmplitudeLimit;
	private static int minValidSampleSize;
	static short sample;
	static int lastSample = 1;
	private static int[] intBuffer;
	private static int intBufferCounter;
	
	private static boolean addedStream ;
    private static byte[][] audioByteStream;
    private static byte[]   saveByteStream;
    private static byte[]   tempByteStream;
    
    private static int[][] audioIntStream;
    private static int[]   saveIntStream;
    private static int[]   tempIntStream;
 
    static int sequences = 0;
    public static int sleepTime;
    public static boolean bufferSizeNonZero;
   
	private static int bufferAmplitudeAvg = 0;
	private static int lastAmplitudeAvg = 0;
	private static int averageAmplitude = 0;
	private static boolean counterEnabled = false;
	private static int metricCounterEnd;
	private static int waveAvg;
	private static int waveMax;
	
    public StreamRefinaryAmplitudeMethods() {
		audioFormat = AudioListener.format;
		bufferSizeNonZero = false;
		sleepTime = (( (int) audioFormat.getFrameSize() * (int) audioFormat.getSampleRate()) / AudioListener.bufferLengthInBytes )*1000 /2;
		waveAmplitudeLimit = AppSetup.STARTER_VOLUME_LIMIT;
		minValidSampleSize = AppSetup.VALID_COUNTER;
		Debug.debug(1,"StreamRefinary Start minValidSampleSize: "+minValidSampleSize + " waveHeightlimit: "+waveAmplitudeLimit+" sleepTime: "+sleepTime);
		sampleRate = (int) audioFormat.getSampleRate();
		
	}

	public static void initBaseBufferVariables() {
		valid_Counter_temp = validCounter;
		validCounter = 0;
		intBuffer = new int[sampleRate];
		intBufferCounter = 0;
		addedStream = false;
		metricCounterEnd = 0;
		waveAvg = 0;
		waveMax = 0;
  		lastAmplitudeAvg = 	bufferAmplitudeAvg;
		bufferAmplitudeAvg = 0;
	}
	
	public static void buildSampleValue(int i, int iplus1) {
		lastSample = sample;
        sample = AudioAnalysisInitializer.getEndian(audioFormat.isBigEndian(),i,iplus1);
        Debug.debug(5,"StreamRefinaryAmplitudeMethods i: "+i +", iplus1"+iplus1 +", sample "+sample);
	}
	
	public static void addToIntBuffer() {
		intBuffer[intBufferCounter++] = sample;	 
		bufferAmplitudeAvg += Math.abs(sample);	
	}
	
	public static void addFirstAmplitudeStream() {
		
      	if(valid_Counter_temp > minValidSampleSize /50 && validCounter > minValidSampleSize  &&  audioByteStream == null ) {			
			total_valid_Counter = valid_Counter_temp;
			averageAmplitude = lastAmplitudeAvg;
      		addToStream(tempByteStream, tempIntStream);
			Debug.debug(4,"StreamRefinary Started First Stream Record valid_Counter_temp: "+valid_Counter_temp + ", total_valid_Counter "
      		              +total_valid_Counter +", sequences: "+sequences + " avg Ampl: "+averageAmplitude);
      	}
      	
      	if(validCounter < minValidSampleSize  &&  audioByteStream == null) {
      		Debug.debug(3,"StreamRefinary bufferCheck Possible speech NOT found! v_c_temp: "+ valid_Counter_temp+ ", valid_c: " +validCounter
      				+", total__C: "  +(valid_Counter_temp+validCounter)+ ", wave_H_L: "+waveAmplitudeLimit
      				+ " avg Ampl: "+averageAmplitude);    
      	}
	}
	
	public static void addFirstMultiStream() {
		
      	if(valid_Counter_temp > minValidSampleSize /50 && StreamRefinaryFrequencyMethods.possibleVoices > 2 && validCounter > minValidSampleSize  &&  audioByteStream == null ) {			
			total_valid_Counter = valid_Counter_temp ;
			averageAmplitude = lastAmplitudeAvg;
      		addToStream(tempByteStream, tempIntStream);
      		StreamRefinaryFrequencyMethods.addToFrequencyMap(StreamRefinaryFrequencyMethods.tempFrequencyMap,StreamRefinaryFrequencyMethods.tempFrequencyMapCounter);
      		StreamRefinaryFrequencyMethods.frequencyIndexCorrection = sampleRate;
			Debug.debug(4,"StreamRefinary Started First Stream Record valid_Counter_temp: "+valid_Counter_temp + ", total_valid_Counter "+total_valid_Counter +", sequences: "+sequences
					+", possibleVoices: "+StreamRefinaryFrequencyMethods.possibleVoices+", f.Map.l: "+StreamRefinaryFrequencyMethods.tempFrequencyMapCounter + " avg Ampl: "+averageAmplitude);
      	}
      	
      	if(validCounter < minValidSampleSize  &&  audioByteStream == null) {

      		Debug.debug(3,"StreamRefinary bufferCheck Possible speech NOT found! v_c_temp: "+ valid_Counter_temp+ ", valid_c: " +validCounter
      				+", total__C: "  +(valid_Counter_temp+validCounter)+", poss_Voices: "+StreamRefinaryFrequencyMethods.possibleVoices+ ", wave_H_L: "+waveAmplitudeLimit
      				+", f.Map.l: "+StreamRefinaryFrequencyMethods.tempFrequencyMapCounter + " avg Ampl: "+averageAmplitude);    
      	}
	}
	
	public static void addFirstVoiceRecognitionAmplitudeStream() {
		
      	if(valid_Counter_temp > minValidSampleSize /50 && validCounter > minValidSampleSize ) {			
      		StreamRefinaryFrequencyMethods.addToFrequencyMap(StreamRefinaryFrequencyMethods.lastFrequencyMap,StreamRefinaryFrequencyMethods.lastFrequencyMapCounter);
			Debug.debug(4,"StreamRefinary Started First Stream Record valid_Counter_temp: "+valid_Counter_temp + ", total_valid_Counter "+total_valid_Counter +", sequences: "+sequences
					+ " avg Ampl: "+averageAmplitude);
      	}    	
      	if(validCounter < minValidSampleSize  &&  audioByteStream == null) {

      		Debug.debug(3,"StreamRefinary bufferCheck Possible speech NOT found! v_c_temp: "+ valid_Counter_temp+ ", valid_c: " +validCounter
      				+", total__C: "  +(valid_Counter_temp+validCounter)+ ", wave_H_L: "+waveAmplitudeLimit + " avg Ampl: "+averageAmplitude);    
      	}
	}
	
	public static void addFirstVoiceRecognitionFrequencyStream() {
		
      	if(valid_Counter_temp > minValidSampleSize /50 && StreamRefinaryFrequencyMethods.possibleVoices > 2 && validCounter > minValidSampleSize) {			
      		StreamRefinaryFrequencyMethods.addToFrequencyMap(StreamRefinaryFrequencyMethods.lastFrequencyMap,StreamRefinaryFrequencyMethods.lastFrequencyMapCounter);
			Debug.debug(4,"StreamRefinary Started First Stream Record valid_Counter_temp: "+valid_Counter_temp + ", total_valid_Counter "+total_valid_Counter +", sequences: "+sequences
					+", possibleVoices: "+StreamRefinaryFrequencyMethods.possibleVoices+", f.Map.l: "+StreamRefinaryFrequencyMethods.tempFrequencyMapCounter + " avg Ampl: "+averageAmplitude);
      	}    	
      	if(validCounter < minValidSampleSize  &&  audioByteStream == null) {

      		Debug.debug(3,"StreamRefinary bufferCheck Possible speech NOT found! v_c_temp: "+ valid_Counter_temp+ ", valid_c: " +validCounter
      				+", total__C: "  +(valid_Counter_temp+validCounter)+", poss_Voices: "+StreamRefinaryFrequencyMethods.possibleVoices+ ", wave_H_L: "+waveAmplitudeLimit
      				+", f.Map.l: "+StreamRefinaryFrequencyMethods.tempFrequencyMapCounter + " avg Ampl: "+averageAmplitude);    
      	}
	}
		
	public static void addToStream (byte[] inputStream,int[] intStream) {
		
		if(audioByteStream == null) {
			audioByteStream = new byte[10][];
			audioIntStream = new int[10][];
			audioByteStream[sequences] = inputStream;
			audioIntStream[sequences++] = intStream;
    		averageAmplitude += bufferAmplitudeAvg;
    		total_valid_Counter = total_valid_Counter + validCounter;
			Debug.debug(4,"StreamRefinary addToStream First Stream Record valid_Counter_temp: "+valid_Counter_temp + ", total_valid_Counter "+total_valid_Counter
					+", sequences: "+sequences	+ " avg Ampl: "+averageAmplitude);
		} else {
			audioByteStream[sequences] = inputStream;
			audioIntStream[sequences++] = intStream;
    		averageAmplitude += bufferAmplitudeAvg;
    		total_valid_Counter = total_valid_Counter + validCounter;
    		Debug.debug(3,"StreamRefinary addToStream Continue Adding Streams, valid_C: " +validCounter +", total_v_c: "+total_valid_Counter
    				+", sequences: "+sequences+", wave_H_L: "+waveAmplitudeLimit+" avg Ampl: "+averageAmplitude );
		}
	}
	
	public static void addValidAmplitudeStream(byte[] buffer ) {

   	if(validCounter > minValidSampleSize) { 
    		addedStream = true;
    		addToStream(buffer,intBuffer);
    	}
	}
	
	public static void addValidMultiStream(byte[] buffer ) {

	   	if(validCounter > minValidSampleSize && StreamRefinaryFrequencyMethods.possibleVoices > AppSetup.POSSIBLE_VOICE_LIMIT) { 
	    		addedStream = true;
	    		StreamRefinaryFrequencyMethods.addToFrequencyMap(StreamRefinaryFrequencyMethods.tempFrequencyMap,StreamRefinaryFrequencyMethods.tempFrequencyMapCounter);
	    		addToStream(buffer,intBuffer);
				Debug.debug(4,"StreamRefinary addValidMultiStream add Stream Record valid_Counter_temp: "+valid_Counter_temp + ", total_valid_Counter "+total_valid_Counter
						+", sequences: "+sequences	+ " avg Ampl: "+averageAmplitude+", possibleVoices: "+StreamRefinaryFrequencyMethods.possibleVoices);
	    	}
	}
	
	public static void addValidVoiceRecognitionAmplitudeStream() {
	   	if(validCounter > minValidSampleSize) { 
	    		addedStream = true;
	    		StreamRefinaryFrequencyMethods.addToFrequencyMap(StreamRefinaryFrequencyMethods.tempFrequencyMap,StreamRefinaryFrequencyMethods.tempFrequencyMapCounter);
	    	}
	}
	
	public static void addValidVoiceRecognitionFrequencyStream() {

	   	if(validCounter > minValidSampleSize && StreamRefinaryFrequencyMethods.possibleVoices > AppSetup.POSSIBLE_VOICE_LIMIT) { 
	    		addedStream = true;
	    		StreamRefinaryFrequencyMethods.addToFrequencyMap(StreamRefinaryFrequencyMethods.tempFrequencyMap,StreamRefinaryFrequencyMethods.tempFrequencyMapCounter);
	    	}
	}

	public static void validAmplitudeCounter() {

        if(Math.abs(sample) > waveAmplitudeLimit)
        	validCounter++;
	}
	
	public static void buildAmplitudeOptimizationResult(int i, int inputSample) {
		
        if(counterEnabled == false && i % 1000 == 0) {
    		counterEnabled = true;
	    		metricCounterEnd = i+50 ;        	
	    }
	    
	  	if(counterEnabled && waveMax <  Math.abs(inputSample))
			waveMax = Math.abs(inputSample); 
		
		if(counterEnabled && metricCounterEnd == i) {
			waveAvg += waveMax;
			counterEnabled = false;
			Debug.debug(5,"StreamRefinary buildAmplitudeOptimization 3. added i: " + i+" waveMax:  "+waveMax);
		}
	}
	
	public static void optimizeAmplitudeLimit(byte[]byteBuffer ) {
		
        if(audioByteStream == null && validCounter < minValidSampleSize /10)    {  
    		waveAmplitudeLimit = (int) (waveAvg / (byteBuffer.length  /1000)+ AppSetup.AMPLITUDE_HEIGHT_OPTIMIZATION_KONSTANT);
    		AppSetup.idle_amplitude_volume = waveAvg / (byteBuffer.length  /1000);
        	tempByteStream = byteBuffer;
        	tempIntStream = intBuffer;
        }
	}
			
	public static void saveAmplitudeStreamToExamine(byte[] byteBuffer) {
		
        if (audioByteStream != null &&  addedStream == false) {
        	
        	if(validCounter > minValidSampleSize /100) {
        		addToStream(byteBuffer,intBuffer);
        		total_valid_Counter = total_valid_Counter +validCounter;
        		Debug.debug(3,"StreamRefinary saveAmplitudeStreamToExamine added endStream! validCounter: "+validCounter);
        	}
        	averageAmplitude = (averageAmplitude/intBuffer.length) / sequences;
        	
        	Debug.debug(4,"StreamRefinary saveAmplitudeStreamToExamine Voice Stream Build Finished valid_c: "+ validCounter+", total_v_c: "+ total_valid_Counter
        	+", sequences: "+sequences+ ", wave_H_L: "+waveAmplitudeLimit+ " avg Ampl: "+averageAmplitude);
        	
        	convert2DArrrayTo1DArray();
    		forwardDataToStartAudioAnalysis(saveByteStream,saveIntStream,null,AudioListener.speechName,audioFormat,averageAmplitude);

    		resetSaveStream();
        }
	}
	
	public static void saveMultiStreamToExamine(byte[] byteBuffer) {
		
        if (audioByteStream != null &&  addedStream == false) {
        	
        	if(validCounter > minValidSampleSize /100 || StreamRefinaryFrequencyMethods.possibleVoices > 0) {
        		StreamRefinaryFrequencyMethods.addToFrequencyMap(StreamRefinaryFrequencyMethods.tempFrequencyMap,StreamRefinaryFrequencyMethods.tempFrequencyMapCounter);
        		addToStream(byteBuffer,intBuffer);
        		total_valid_Counter = total_valid_Counter +validCounter;
        		Debug.debug(3,"StreamRefinary saveMultiStreamToExamine added endStream! validCounter: "+validCounter + ", possibleVoices: "+StreamRefinaryFrequencyMethods.possibleVoices);
        	}
        	averageAmplitude = (averageAmplitude/intBuffer.length) / sequences;
        	
        	Debug.debug(4,"StreamRefinary saveMultiStreamToExamine Voice Stream Build Finished valid_c: "+ validCounter+", total_v_c: "+ total_valid_Counter
        	+", sequences: "+sequences+", possVoices: "+StreamRefinaryFrequencyMethods.possibleVoices+ ", wave_H_L: "+waveAmplitudeLimit+", f.Map.L: "+StreamRefinaryFrequencyMethods.frequencyMapCounter+ " avg Ampl: "+averageAmplitude);
        	 
        	Debug.debug(4,"StreamRefinary saveMultiStreamToExamine Voice Stream Build Finished frequencyMap[frequencyMapCounter-3] "+StreamRefinaryFrequencyMethods.frequencyMap[StreamRefinaryFrequencyMethods.frequencyMapCounter-3] + 
    				" frequencyMap[frequencyMapCounter-2] "+StreamRefinaryFrequencyMethods.frequencyMap[StreamRefinaryFrequencyMethods.frequencyMapCounter-2]+
    				" frequencyMap[frequencyMapCounter-1] "+StreamRefinaryFrequencyMethods.frequencyMap[StreamRefinaryFrequencyMethods.frequencyMapCounter-1]+
    				" frequencyMap.length: "+StreamRefinaryFrequencyMethods.frequencyMap.length + " frequencyMapCounter: "+StreamRefinaryFrequencyMethods.frequencyMapCounter
    						);

        	convert2DArrrayTo1DArray();
        	StreamRefinaryFrequencyMethods.freqeuncyArrayFilterEmptyElemnts();
    		forwardDataToStartAudioAnalysis(saveByteStream,saveIntStream,StreamRefinaryFrequencyMethods.frequencyMap,AudioListener.speechName,audioFormat,averageAmplitude);
    		resetSaveStream();
    		StreamRefinaryFrequencyMethods.resetSaveStream();
    		
        }
	}
	
	public static void saveVoiceRecognitionStreamAmplitudeToExamine(byte[] byteBuffer) {
		
        if (StreamRefinaryFrequencyMethods.frequencyMapCounter > 0 &&  addedStream == false) {
        	
        	if(validCounter > minValidSampleSize /100) {
        		StreamRefinaryFrequencyMethods.addToFrequencyMap(StreamRefinaryFrequencyMethods.tempFrequencyMap,StreamRefinaryFrequencyMethods.tempFrequencyMapCounter);
        		total_valid_Counter = total_valid_Counter +validCounter;
        		Debug.debug(3,"StreamRefinary saveStreamToExamine added endStream! validCounter: "+validCounter + ", possibleVoices: "+StreamRefinaryFrequencyMethods.possibleVoices);
        	}
       	
        	Debug.debug(4,"StreamRefinary saveStreamToExamin Voice Stream Build Finished valid_c: "+ validCounter+", total_v_c: "+ total_valid_Counter
        	+", sequences: "+sequences+ ", wave_H_L: "+waveAmplitudeLimit+ " avg Ampl: "+averageAmplitude);
        	
        	
        	StreamRefinaryFrequencyMethods.freqeuncyArrayFilterEmptyElemnts();
    		forwardDataToStartAudioAnalysis(null,null,StreamRefinaryFrequencyMethods.frequencyMap,AudioListener.speechName,audioFormat,averageAmplitude);
    		resetSaveStream();
    		StreamRefinaryFrequencyMethods.resetSaveStream();
        }
	}
	
	public static void saveVoiceRecognitionStreamFrequencyToExamine(byte[] byteBuffer) {
		
        if (StreamRefinaryFrequencyMethods.frequencyMapCounter > 0 &&  addedStream == false) {
        	
        	if(validCounter > minValidSampleSize /100 || StreamRefinaryFrequencyMethods.possibleVoices > 0) {
        		StreamRefinaryFrequencyMethods.addToFrequencyMap(StreamRefinaryFrequencyMethods.tempFrequencyMap,StreamRefinaryFrequencyMethods.tempFrequencyMapCounter);
        		total_valid_Counter = total_valid_Counter +validCounter;
        		Debug.debug(3,"StreamRefinary saveStreamToExamine added endStream! validCounter: "+validCounter + ", possibleVoices: "+StreamRefinaryFrequencyMethods.possibleVoices);
        	}
       	
        	Debug.debug(4,"StreamRefinary saveStreamToExamin Voice Stream Build Finished valid_c: "+ validCounter+", total_v_c: "+ total_valid_Counter
        	+", sequences: "+sequences+", possVoices: "+StreamRefinaryFrequencyMethods.possibleVoices+ ", wave_H_L: "+waveAmplitudeLimit+", f.Map.L: "+StreamRefinaryFrequencyMethods.frequencyMapCounter+ " avg Ampl: "+averageAmplitude);
        	
        	Debug.debug(4,"StreamRefinary saveStreamToExamin Voice Stream Build Finished frequencyMap[frequencyMapCounter-3] "+StreamRefinaryFrequencyMethods.frequencyMap[StreamRefinaryFrequencyMethods.frequencyMapCounter-3] + 
    				" frequencyMap[frequencyMapCounter-2] "+StreamRefinaryFrequencyMethods.frequencyMap[StreamRefinaryFrequencyMethods.frequencyMapCounter-2]+
    				" frequencyMap[frequencyMapCounter-1] "+StreamRefinaryFrequencyMethods.frequencyMap[StreamRefinaryFrequencyMethods.frequencyMapCounter-1]+
    				" frequencyMap.length: "+StreamRefinaryFrequencyMethods.frequencyMap.length + " frequencyMapCounter: "+StreamRefinaryFrequencyMethods.frequencyMapCounter
    						);
        	StreamRefinaryFrequencyMethods.freqeuncyArrayFilterEmptyElemnts();
    		forwardDataToStartAudioAnalysis(null,null,StreamRefinaryFrequencyMethods.frequencyMap,AudioListener.speechName,audioFormat,averageAmplitude);
    		resetSaveStream();
    		StreamRefinaryFrequencyMethods.resetSaveStream();
        }
	}
	
	public static void resetSaveStream () {
		audioByteStream = null;
		audioIntStream = null;
		saveByteStream = null;
		saveIntStream = null;
		total_valid_Counter = 0;
		sequences = 0;
		AppSetup.STARTER_VOLUME_LIMIT = waveAmplitudeLimit;
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
		Debug.debug(3,"StreamRefinary convert2DArrrayTo1DArray saveByteStream.length " +saveByteStream.length+" saveIntStream.length: "+saveIntStream.length+" sequences: "+sequences + " audioByteStream[0].length: "+audioByteStream[0].length +" audioIntStream[0].length: " +audioIntStream[0].length );
	}
	
	public static void forwardDataToStartAudioAnalysis(byte []saveByteStream, int []saveIntStream,int[] frequencyMap, String speechName,AudioFormat audioFormat,int averageAmplitude) {
		
		Debug.debug(3,"StreamRefinary forwardDataToStartAudioAnalysis");
		new AudioAnalysisThread(saveByteStream,saveIntStream,frequencyMap,speechName,audioFormat,averageAmplitude);
	}
}


 


