package com.audio3.recorder.refinary;

import java.util.Arrays;

import com.audio0.main.AppSetup;
import com.audio2.recorder.AudioListener;
import com.audio8.util.AudioBuilderUtil;
import com.audio8.util.Debug;

public class StreamRefinaryFrequencyMethods {
	 
	public static int possibleVoices;
	private static int checkPossibleVoiceCounter;
	private static int frequencyMeasure;
	private static int frequency;
	public static int frequencyIndexCorrection = 0;
	private static int checkArrayLength = 5 ;
	private static int[] frequencys = new int[checkArrayLength];
	private static int lastfrequency;
	private static int sampleRate;
	
	public static int[] tempFrequencyMap = new int[8000];
	public static int   tempFrequencyMapCounter = 0;
	public static int[] lastFrequencyMap;
	public static int   lastFrequencyMapCounter ;
	public static int[] frequencyMap; 
	public static int frequencyMapCounter;
	private static int tempAvgAmplitude = 0;
	
	public static int F_AVG_MILISEC_LENGTH;
	public static int favgMilisecCounter;
	public static int frequencyBuffer;

	public static int avg_a;
	public static int avg_f;
	
	//private static int amplitudeBufferCounter;
	public static int frequencyBufferDiveder = 0;
	public static int amplitudeTotal;
	public static int frequencyTotal;
	public static float[] frequencySampleLengths;
    	
	public StreamRefinaryFrequencyMethods () {
		
		F_AVG_MILISEC_LENGTH = AudioBuilderUtil.getBuffersLengthByMilisec(
			(int)AudioListener.format.getSampleRate()
			,AppSetup.AUDIO_BUFFER_MILISEC_LENGTH);
		
		frequencyMap = new int[20000]; 
		frequencyMapCounter = 0;	
		sampleRate = (int) AudioListener.format.getSampleRate();
	}
   
	public static void initBaseBufferVariables( ) {
		
		frequencyMeasure = 1;
		possibleVoices = 0;
		frequencys = new int[checkArrayLength];//frequencys = new int[sampleRate]
		
		frequencyIndexCorrection = (int) (StreamRefinaryAmplitudeMethods.sequences 
			                         * StreamRefinaryAmplitudeMethods.bufferLength);
		
		lastFrequencyMap = tempFrequencyMap;
  		lastFrequencyMapCounter = tempFrequencyMapCounter;
  		tempFrequencyMap = new int[8000];
  		tempFrequencyMapCounter = 0;  		
	}

	public static void IFR_buildFrequencyCheck(int lasSample, int inputSample,int i) {
		
		if(StreamRefinaryAmplitudeMethods.build == false) return;
		
		    lastfrequency =0;
        	frequencyMeasure++;
        	tempAvgAmplitude += Math.abs(inputSample);
        	
        	Debug.debug(5,"StreamRefinaryFrequencyMethods buildFrequencyCheck inputSample: "
        		+ inputSample + " lasSample: "+lasSample + " frequencyMeasure: "  +frequencyMeasure
        		+ " i: "+(i/2));

        if(inputSample > 0 && lasSample < 0) {
        	
        	lastfrequency = frequency ;
        	frequency =   sampleRate / frequencyMeasure;    
        	
        	tempFrequencyMap[tempFrequencyMapCounter++] = 
        			frequencyIndexCorrection+ (i/2)-frequencyMeasure;
        	
        	tempFrequencyMap[tempFrequencyMapCounter++] = tempAvgAmplitude / frequencyMeasure;
        	tempFrequencyMap[tempFrequencyMapCounter++] = frequency;
        	//System.out.println("frequencyMeasure: "+frequencyMeasure + " frequency [0,1,2] "
        	//+tempFrequencyMap[tempFrequencyMapCounter-3]+ " "
        	//+tempFrequencyMap[tempFrequencyMapCounter-2]
        	//+ " " +tempFrequencyMap[tempFrequencyMapCounter-1] +", i: "+i );  
        	frequencyMeasure = 0;	
        	tempAvgAmplitude = 0;
	        // System.out.println("i: "+i+"frequency: "+frequency+" lastfrequency: " +lastfrequency 
        	//+ " frequencyMeasure: "+frequencyMeasure + " sample: "+inputSample);
        	
	    	if((frequency > AppSetup.FREQUENCY_CHECK_LOWER_LIMIT 
	    		&& frequency < AppSetup.FREQUENCY_CHECK_UPPER_LIMIT ) 
	    		&& (lastfrequency> AppSetup.FREQUENCY_CHECK_LOWER_LIMIT 
	    		&& lastfrequency < AppSetup.FREQUENCY_CHECK_UPPER_LIMIT)) {
	    		
	    		frequencys[checkPossibleVoiceCounter++] = frequency; 
	    		
	    		Debug.debug(5,"StreamRefinaryFrequencyMethods buildFrequencyCheck Found one"
	    			+" possible checkPossibleVoiceCounter: "+checkPossibleVoiceCounter+" Array: " 
	    			+Arrays.toString(frequencys));
	    	}
	    	
	    	if((frequency < AppSetup.FREQUENCY_CHECK_LOWER_LIMIT 
	    			|| frequency  > AppSetup.FREQUENCY_CHECK_UPPER_LIMIT) && frequency!= 0) {
	    		
	    		checkPossibleVoiceCounter = 0;
	    		frequencys = new int [checkArrayLength];
	    		
	    		Debug.debug(5,"StreamRefinaryFrequencyMethods buildFrequencyCheck New Array"
	    			+" frequency:" +frequency);
	    	}
	    	
	    	if(checkPossibleVoiceCounter == checkArrayLength ) {
	    		
	    		possibleVoices++;
	    		checkPossibleVoiceCounter=0;
	    		
	    		//System.out.println("i: "+i+" possibleVoices: "+possibleVoices+", Array "
	    		//+ Arrays.toString(testArray));
	    		
	    		frequencys = new int [checkArrayLength];	
	        }
		}
    }
	
	public static void VRAR_buildMilisecDualMap(int lasSample, int inputSample,int i) {
		
		if(StreamRefinaryAmplitudeMethods.build == false) return;
		
		frequencyMeasure++;
		tempAvgAmplitude += Math.abs(inputSample);
		
		if(inputSample > 0 && lasSample < 0) {		
			
        	tempFrequencyMap[tempFrequencyMapCounter++] = 
        			frequencyIndexCorrection+ (i/2)-frequencyMeasure;
        	
        	tempFrequencyMap[tempFrequencyMapCounter++] = tempAvgAmplitude / frequencyMeasure;
        	
        	tempFrequencyMap[tempFrequencyMapCounter++] 
        			= sampleRate / frequencyMeasure;

	    	frequencyMeasure = 0;	
	    	tempAvgAmplitude = 0;
		}
	}
	
	public static void VRFR_buildMilisecFrequencyCheck(int lasSample, int inputSample,int i) {
		
		if(StreamRefinaryAmplitudeMethods.build == false) return;
		
		    lastfrequency = 0;
	    	frequencyMeasure++;
	    	tempAvgAmplitude += Math.abs(inputSample);
	    	//System.out.println("inputSample: "+inputSample + " lasSample: "+lasSample
	    	//+ " frequencyMeasure: "  +frequencyMeasure+ " i: "+(i/2));
	
	    if(inputSample > 0 && lasSample < 0) {
	    	
	    	lastfrequency = frequency ;
	    	frequency = sampleRate / frequencyMeasure;  
	    	
        	tempFrequencyMap[tempFrequencyMapCounter++] = 
        			frequencyIndexCorrection+ (i/2)-frequencyMeasure;
        	
        	tempFrequencyMap[tempFrequencyMapCounter++] = tempAvgAmplitude / frequencyMeasure;
        	
        	tempFrequencyMap[tempFrequencyMapCounter++] = frequency; 
	    	
	    	frequencyMeasure = 0;	
	    	tempAvgAmplitude = 0;
	        // System.out.println("i: "+i+"frequency: "+frequency+" lastfrequency: "+lastfrequency 
	    	//+ " frequencyMeasure: "+frequencyMeasure + " sample: "+inputSample);
	    	
	    	if((frequency > AppSetup.FREQUENCY_CHECK_LOWER_LIMIT 
	    			&& frequency < AppSetup.FREQUENCY_CHECK_UPPER_LIMIT) 
	    			&& (lastfrequency > AppSetup.FREQUENCY_CHECK_LOWER_LIMIT 
	    			&& lastfrequency < AppSetup.FREQUENCY_CHECK_UPPER_LIMIT)) {
	    		
	    		frequencys[checkPossibleVoiceCounter++] = frequency; 
	    		//System.out.println("Found one possible checkPossibleVoiceCounter: "
	    		//+checkPossibleVoiceCounter+" Array: " +Arrays.toString(testArray));
	    	}
	    	
	    	if((frequency < AppSetup.FREQUENCY_CHECK_LOWER_LIMIT 
	    		|| frequency  > AppSetup.FREQUENCY_CHECK_UPPER_LIMIT) && frequency!= 0) {
	    		
	    		checkPossibleVoiceCounter = 0;
	    		frequencys = new int [checkArrayLength];
	    		//System.out.println("New Array frequency:"+frequency);
	    	}
	    	
	    	if(checkPossibleVoiceCounter == checkArrayLength ) {
	    		
	    		possibleVoices++;
	    		checkPossibleVoiceCounter=0;
	    		//System.out.println("i: "+i+" possibleVoices: "+possibleVoices+", Array "
	    		//+ Arrays.toString(testArray));
	    		frequencys = new int [checkArrayLength] ;	
	        }
		}
	}
	
	public static void addToStreamDetailsMap(int[] frequencyIndex, int inputLength) {
		
		if(StreamRefinaryAmplitudeMethods.build == false) return;
		
		for(int i = 0; i < inputLength;i++) {
			
			frequencyMap[frequencyMapCounter++] = frequencyIndex[i];
		}
		
		if(AppSetup.voiceRecognitionAmplitudeRefinary 
			|| AppSetup.voiceRecognitionFrequencyRefinary)
			StreamRefinaryAmplitudeMethods.sequences++;    
		
		Debug.debug(5,"StreamRefinary FrequencyMethods addToFrequencyMap oldLength: "
			+(frequencyMapCounter-inputLength)+", New frequencyMap Length: "+frequencyMapCounter);
	}
	 	 
	public static void freqeuncyArrayFilterEmptyElemnts() {
			
		int[] newArray = new int[frequencyMapCounter];
		
		for(int i = 0; i < newArray.length;i++)
			newArray[i]= frequencyMap[i];
		
		frequencyMap = newArray;	
	}
		
	public static void resetSaveStream () {
		 
		frequencyMap = new int[20000]; 
		frequencyMapCounter = 0;
	}
	
	public static void buildFrequencySampleLengths() {
	
  		frequencySampleLengths = new float [(int) (AudioListener.format.getSampleRate()/2 +5)];
  		
		for(int i = 1; i < 11029 ; i++)
			frequencySampleLengths[i] = (float)AudioListener.format.getSampleRate() / i ;
	}
}
