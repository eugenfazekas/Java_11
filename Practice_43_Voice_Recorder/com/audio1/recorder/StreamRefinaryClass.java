package com.audio1.recorder;

import javax.sound.sampled.AudioFormat;

import com.audio0.main.AppSetup;
import com.audio3.audioGramInitializer.AudioAnalysisInitializer;
import com.audio3.audioGramInitializer.AudioAnalysisThread;
import com.audio8.util.Debug;

public class StreamRefinaryClass implements Runnable{
	
	private static int waveAmplitudeLimit;
	private static int minValidSampleSize;
    private Thread thread;
    private static byte[][] audioByteStream;
    private static byte[]   saveByteStream;
    private static byte[]   tempByteStream;
    private static int[][] audioIntStream;
    private static int[]   saveIntStream;
    private static int[]   tempIntStream;
    private static AudioFormat audioFormat;  
    private static int sequences = 0;
    private int sleepTime;
    public static boolean bufferSizeNonZero = false;
	private static int validCounter;
	private static int valid_Counter_temp;
	private static int total_valid_Counter;
	private static boolean counterEnabled = false;
	private static int bufferAmplitudeAvg = 0;
	private static int lastAmplitudeAvg = 0;
	private static int averageAmplitude = 0;
	private static int metricCounterEnd;
	private static int waveAvg;
	private static int waveMax;
	private static boolean addedStream ;
	private static short sample;
	private static int lastSample = 1;
	private static int possibleVoices;
	private static int checkPossibleVoiceCounter;
	private static int frequencyMeasure;
	private static int frequency;
	private static int frequencyIndexCorrection = 0;
	private static int checkArrayLength = 5 ;
	private static int[] frequencys = new int[checkArrayLength];
	private static int lastfrequency;
	private static int sampleRate;
	private static int[] intBuffer;
	private static int intBufferCounter;
	private static int[] tempFrequencyMap = new int[4000];
	private static int tempFrequencyMapCounter = 0;
	private static int[] frequencyMap = new int[20000]; 
	private static int frequencyMapCounter = 0;
    private static int tempAvgAmplitude = 0;
    
    public StreamRefinaryClass(AudioFormat audioformat) {
		audioFormat = audioformat;
		bufferSizeNonZero = false;
		sleepTime = (( (int) audioFormat.getFrameSize() * (int) audioFormat.getSampleRate()) / AudioListener.bufferLengthInBytes )*1000 /2;
		waveAmplitudeLimit = AppSetup.STARTER_VOLUME_LIMIT;
		minValidSampleSize = AppSetup.VALID_COUNTER;
		Debug.debug(1,"StreamRefinary Start minValidSampleSize: "+minValidSampleSize + " waveHeightlimit: "+waveAmplitudeLimit+" sleepTime: "+sleepTime);
		sampleRate = (int) audioFormat.getSampleRate();
		frequencys = new int[sampleRate];
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
	
	private void bufferCheck(byte[] inputBuffer) {
				
		initBufferCheckVariables(inputBuffer);
			
        for (int i = 0; i < inputBuffer.length; i=i+2) {

	        buildSampleValue(inputBuffer[i],inputBuffer[i+1]); 
	        
	        addToIntBuffer();
	        
	        buildAmplitudeOptimizationResult(i,sample);
	        
	        buildFrequencyCheck(lastSample,sample,i); 
	        
	        validAmplitudeCounter();	        	      	
        }
        
        addFirstPartStream();
        
        addValidStream(inputBuffer);
        
        saveStreamToExamine(inputBuffer);
        
        optimizeAmplitudeLimit(inputBuffer);
	}
		
	private static void initBufferCheckVariables(byte[] inputBuffer) {
		valid_Counter_temp = validCounter;
		validCounter = 0;
		intBuffer = new int[inputBuffer.length/2];
		intBufferCounter = 0;
		bufferAmplitudeAvg = 0;
		addedStream = false;
		metricCounterEnd = 0;
		waveAvg = 0;
		waveMax = 0;
		
		//if(AppSetup.FREQUENCY_CHECK_ENABLED == false) return;
			frequencyMeasure = 1;
			possibleVoices = 0;
			frequencys = new int[inputBuffer.length];//frequencys = new int[sampleRate]
			frequencyIndexCorrection = sequences * sampleRate;
	}
	
	private static void buildSampleValue(int i, int iplus1) {
		lastSample = sample;
        sample = AudioAnalysisInitializer.getEndian(audioFormat.isBigEndian(),i,iplus1);
	}
	
	private static void addToIntBuffer() {
		intBuffer[intBufferCounter++] = sample;	 
		bufferAmplitudeAvg += Math.abs(sample);	
	}
	
	private static void addFirstPartStream() {
		
      	if(valid_Counter_temp > minValidSampleSize /50 && possibleVoices > 2 && validCounter > minValidSampleSize  &&  audioByteStream == null ) {			
			total_valid_Counter = valid_Counter_temp ;
			averageAmplitude = lastAmplitudeAvg;
      		addToStream(tempByteStream, tempIntStream);
      		addToFrequencyMap(tempFrequencyMap,tempFrequencyMapCounter);
      		frequencyIndexCorrection = sampleRate;
			Debug.debug(4,"StreamRefinary Started First Stream Record valid_Counter_temp: "+valid_Counter_temp + ", total_valid_Counter "+total_valid_Counter +", sequences: "+sequences
					+", possibleVoices: "+possibleVoices+", f.Map.l: "+tempFrequencyMapCounter + " avg Ampl: "+averageAmplitude);
      	}
      	
      	if(validCounter < minValidSampleSize  &&  audioByteStream == null) {

      		Debug.debug(3,"StreamRefinary bufferCheck Possible speech NOT found! v_c_temp: "+ valid_Counter_temp+ ", valid_c: " +validCounter
      				+", total__C: "  +(valid_Counter_temp+validCounter)+", poss_Voices: "+possibleVoices+ ", wave_H_L: "+waveAmplitudeLimit
      				+", f.Map.l: "+tempFrequencyMapCounter + " avg Ampl: "+averageAmplitude);    
      	}
	}
		
	private static void addToStream (byte[] inputStream,int[] intStream) {
		
		if(audioByteStream == null) {
			audioByteStream = new byte[10][];
			audioIntStream = new int[10][];
			audioByteStream[sequences] = inputStream;
			audioIntStream[sequences++] = intStream;
    		averageAmplitude += bufferAmplitudeAvg;
    		total_valid_Counter = total_valid_Counter + validCounter;
			Debug.debug(4,"StreamRefinary addToStream First Stream Record valid_Counter_temp: "+valid_Counter_temp + ", total_valid_Counter "+total_valid_Counter +", sequences: "+sequences
					+", possibleVoices: "+possibleVoices+", f.Map.l: "+tempFrequencyMapCounter + " avg Ampl: "+averageAmplitude);
		} else {
			audioByteStream[sequences] = inputStream;
			audioIntStream[sequences++] = intStream;
    		averageAmplitude += bufferAmplitudeAvg;
    		total_valid_Counter = total_valid_Counter + validCounter;
    		Debug.debug(3,"StreamRefinary addToStream Continue Adding Streams, valid_C: " +validCounter +", total_v_c: "+total_valid_Counter
    				+", sequences: "+sequences+", possVoices: "+possibleVoices+", wave_H_L: "+waveAmplitudeLimit+", f.M.length: "+frequencyMapCounter+ " avg Ampl: "+averageAmplitude );
		}
	}
	
	private static void addValidStream(byte[] buffer ) {

   	if(validCounter > minValidSampleSize && possibleVoices > AppSetup.POSSIBLE_VOICE_LIMIT) { 
    		addedStream = true;
      		addToFrequencyMap(tempFrequencyMap,tempFrequencyMapCounter);
    		addToStream(buffer,intBuffer);
    	}
	}

	private static void validAmplitudeCounter() {

        if(Math.abs(sample) > waveAmplitudeLimit)
        	validCounter++;
	}
	
	private static void buildAmplitudeOptimizationResult(int i, int inputSample) {
		
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
	
	private static void optimizeAmplitudeLimit(byte[]byteBuffer ) {
		
        if(audioByteStream == null && validCounter < minValidSampleSize /10)    {  
    		waveAmplitudeLimit = (int) (waveAvg / (byteBuffer.length  /1000)+ AppSetup.AMPLITUDE_HEIGHT_OPTIMIZATION_KONSTANT);
        	tempByteStream = byteBuffer;
        	tempIntStream = intBuffer;
        }
  		tempFrequencyMap = new int[4000];
  		tempFrequencyMapCounter = 0;
  		lastAmplitudeAvg = 	bufferAmplitudeAvg;
	}
	
	private static void buildFrequencyCheck(int lasSample, int inputSample,int i) {

		//if(AppSetup.FREQUENCY_CHECK_ENABLED == false) return;
		    lastfrequency =0;
        	frequencyMeasure++;
        	tempAvgAmplitude += inputSample;
        	//System.out.println("inputSample: "+inputSample + " lasSample: "+lasSample + " frequencyMeasure: "  +frequencyMeasure+ " i: "+(i/2));

        if(inputSample > 0 && lasSample < 0) {
        	lastfrequency = frequency ;
        	frequency =   sampleRate / frequencyMeasure;         	
        	tempFrequencyMap[tempFrequencyMapCounter++] = frequencyIndexCorrection+ (i/2)-frequencyMeasure;
        	tempFrequencyMap[tempFrequencyMapCounter++] = tempAvgAmplitude / frequencyMeasure;
        	tempFrequencyMap[tempFrequencyMapCounter++] = frequency;
        	//System.out.println("frequencyMeasure: "+frequencyMeasure + " frequency [0,1,2] "+tempFrequencyMap[tempFrequencyMapCounter-3]+ " "+tempFrequencyMap[tempFrequencyMapCounter-2]
        	//+ " " +tempFrequencyMap[tempFrequencyMapCounter-1] +", i: "+i );  
        	frequencyMeasure = 0;	
        	tempAvgAmplitude = 0;
	        // System.out.println("i: "+i+ "frequency: "+frequency +" lastfrequency: "  +lastfrequency + " frequencyMeasure: "+frequencyMeasure + " sample: "+inputSample);
	    	if((frequency > AppSetup.FREQUENCY_CHECK_LOWER_LIMIT && frequency< AppSetup.FREQUENCY_CHECK_UPPER_LIMIT ) && (lastfrequency> AppSetup.FREQUENCY_CHECK_LOWER_LIMIT && lastfrequency < AppSetup.FREQUENCY_CHECK_UPPER_LIMIT)) {
	    		frequencys[checkPossibleVoiceCounter++] = frequency; 
	    		//System.out.println("Found one possible checkPossibleVoiceCounter: "+checkPossibleVoiceCounter+" Array: " +Arrays.toString(testArray));
	    	}
	    	
	    	if((frequency < AppSetup.FREQUENCY_CHECK_LOWER_LIMIT || frequency  > AppSetup.FREQUENCY_CHECK_UPPER_LIMIT) && frequency!= 0) {
	    		checkPossibleVoiceCounter = 0;
	    		frequencys = new int [checkArrayLength];
	    		//System.out.println("New Array frequency:"+frequency);
	    	}
	    	
	    	if(checkPossibleVoiceCounter == checkArrayLength ) {
	    		possibleVoices++;
	    		checkPossibleVoiceCounter=0;
	    		//System.out.println("i: "+i+" possibleVoices: "+possibleVoices+", Array "+ Arrays.toString(testArray));
	    		frequencys = new int [checkArrayLength] ;	
	        }
		}
    }
	
	private static void addToFrequencyMap(int[] frequencyIndex, int inputLength) {
		
	//	if(AppSetup.FREQUENCY_CHECK_ENABLED == false) return;
		
		for(int i = 0; i < inputLength;i++) {
			frequencyMap[frequencyMapCounter++] = frequencyIndex[i];
		}
	}
		
	private void saveStreamToExamine(byte[] byteBuffer) {
		
        if (audioByteStream != null &&  addedStream == false) {
        	
        	if(validCounter > minValidSampleSize /100 || possibleVoices > 0 ) {
        		addToFrequencyMap(tempFrequencyMap,tempFrequencyMapCounter);
        		addToStream(byteBuffer,intBuffer);
        		total_valid_Counter = total_valid_Counter +validCounter;
        		Debug.debug(3,"StreamRefinary saveStreamToExamine added endStream! validCounter: "+validCounter + ", possibleVoices: "+possibleVoices);
        	}
        	averageAmplitude = (averageAmplitude/intBuffer.length) / sequences;
        	
        	Debug.debug(4,"StreamRefinary saveStreamToExamin Voice Stream Build Finished valid_c: "+ validCounter+", total_v_c: "+ total_valid_Counter
        	+", sequences: "+sequences+", possVoices: "+possibleVoices+ ", wave_H_L: "+waveAmplitudeLimit+", f.Map.L: "+frequencyMapCounter+ " avg Ampl: "+averageAmplitude);
        	
        //	if(AppSetup.FREQUENCY_CHECK_ENABLED) 
        	Debug.debug(4,"StreamRefinary saveStreamToExamin Voice Stream Build Finished frequencyMap[frequencyMapCounter-3] "+frequencyMap[frequencyMapCounter-3] + 
    				" frequencyMap[frequencyMapCounter-2] "+frequencyMap[frequencyMapCounter-2]+
    				" frequencyMap[frequencyMapCounter-1] "+frequencyMap[frequencyMapCounter-1]+
    				" frequencyMap.length: "+frequencyMap.length + " frequencyMapCounter: "+frequencyMapCounter
    						);

        	convert2DArrrayTo1DArray();
        	freqeuncyArrayFilterEmptyElemnts();
    		forwardDataToStartAudioAnalysis(saveByteStream,saveIntStream,frequencyMap,AudioListener.speechName,audioFormat,averageAmplitude);

			audioByteStream = null;
			audioIntStream = null;
			saveByteStream = null;
			saveIntStream = null;
			total_valid_Counter = 0;
			sequences = 0;
			frequencyMap = new int[20000]; 
			frequencyMapCounter = 0;
			AppSetup.STARTER_VOLUME_LIMIT = waveAmplitudeLimit;
    		averageAmplitude = 0;
        }
	}
	
	private static void convert2DArrrayTo1DArray() {
		
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
	
	private static void freqeuncyArrayFilterEmptyElemnts() {
		
//		if(AppSetup.FREQUENCY_CHECK_ENABLED == false) {
//			frequencyMap = null;
//				return;
//		}
		
		int[] newArray = new int[frequencyMapCounter];
		for(int i = 0; i < newArray.length;i++)
			newArray[i]= frequencyMap[i];
		frequencyMap = newArray;	
	}
	
	private static void forwardDataToStartAudioAnalysis(byte []saveByteStream, int []saveIntStream,int[] frequencyMap, String speechName,AudioFormat audioFormat,int averageAmplitude) {
		
		Debug.debug(3,"StreamRefinary forwardDataToStartAudioAnalysis");
		new AudioAnalysisThread(saveByteStream,saveIntStream,frequencyMap,speechName,audioFormat,averageAmplitude);
	}
}


 


