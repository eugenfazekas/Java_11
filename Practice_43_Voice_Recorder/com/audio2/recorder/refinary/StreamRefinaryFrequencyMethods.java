package com.audio2.recorder.refinary;

import com.audio0.main.AppSetup;
import com.audio1.recorder.AudioListener;
import com.audio3.audioGramInitializer.AudioAnalysisInitializer;
import com.audio4.audioGramBuilder.MultiAnalysisBuilder;
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
	
	public static int[] tempFrequencyMap = new int[4000];
	public static int tempFrequencyMapCounter = 0;
	public static int[] lastFrequencyMap;
	public static int lastFrequencyMapCounter ;
	public static int[] frequencyMap; 
	public static int frequencyMapCounter;
    private static int tempAvgAmplitude = 0;
    
	 public static int F_AVG_MILISEC_LENGTH;
	 public static int favgMilisecCounter;
	 public static int frequencyBuffer;
	 public static int frequencyBufferCounter = 0;
	 public static int avg_a;
	 public static int avg_f;
	 private static int bufferCounter;
	 public static int amplitudeTotal;
	 public static int frequencyTotal;
    	
	public StreamRefinaryFrequencyMethods () {
		F_AVG_MILISEC_LENGTH = AudioAnalysisInitializer.getBuffersLengthByMilisec((int)AudioListener.format.getSampleRate(), AppSetup.VOICE_RECOGNITION_BUFFER_MILISEC_LENGTH);
		frequencyMap = new int[20000]; 
		frequencyMapCounter = 0;		
	}
   
	public static void initBufferFrequencyVariables( ) {
		frequencyMeasure = 1;
		possibleVoices = 0;
		frequencys = new int[checkArrayLength];//frequencys = new int[sampleRate]
		frequencyIndexCorrection = (int) (StreamRefinaryAmplitudeMethods.sequences * StreamRefinaryAmplitudeMethods.sampleRate);
		lastFrequencyMap = tempFrequencyMap;
  		lastFrequencyMapCounter = tempFrequencyMapCounter;
  		tempFrequencyMap = new int[4000];
  		tempFrequencyMapCounter = 0;
	}

	public static void buildFrequencyCheck(int lasSample, int inputSample,int i) {

		    lastfrequency =0;
        	frequencyMeasure++;
        	tempAvgAmplitude += Math.abs(inputSample);
        	//System.out.println("inputSample: "+inputSample + " lasSample: "+lasSample + " frequencyMeasure: "  +frequencyMeasure+ " i: "+(i/2));

        if(inputSample > 0 && lasSample < 0) {
        	lastfrequency = frequency ;
        	frequency =   StreamRefinaryAmplitudeMethods.sampleRate / frequencyMeasure;         	
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
	
	public static void buildMilisecDualMap(int lasSample, int inputSample,int i) {
		frequencyMeasure++;
		tempAvgAmplitude += Math.abs(inputSample);
		
		if(inputSample > 0 && lasSample < 0) {
			addToFrequencyBuffer(tempAvgAmplitude / frequencyMeasure,StreamRefinaryAmplitudeMethods.sampleRate / frequencyMeasure); 
	    	frequencyMeasure = 0;	
	    	tempAvgAmplitude = 0;
		}
    	if(favgMilisecCounter == F_AVG_MILISEC_LENGTH) {
    		addToMilisecTempMap();
    		favgMilisecCounter = 0;
    	}
    	//System.out.println("favgMilisecCounter: "+favgMilisecCounter +", F_AVG_MILISEC_LENGTH: "+F_AVG_MILISEC_LENGTH);
    	favgMilisecCounter++;
	}
	
	public static void buildMilisecFrequencyCheck(int lasSample, int inputSample,int i) {
	
		    lastfrequency =0;
	    	frequencyMeasure++;
	    	tempAvgAmplitude += Math.abs(inputSample);
	    	//System.out.println("inputSample: "+inputSample + " lasSample: "+lasSample + " frequencyMeasure: "  +frequencyMeasure+ " i: "+(i/2));
	
	    if(inputSample > 0 && lasSample < 0) {
	    	lastfrequency = frequency ;
	    	frequency = StreamRefinaryAmplitudeMethods.sampleRate / frequencyMeasure;  
	    	addToFrequencyBuffer(tempAvgAmplitude / frequencyMeasure,frequency); 
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
    	if(favgMilisecCounter == F_AVG_MILISEC_LENGTH) {
    		addToMilisecTempMap();
    		favgMilisecCounter = 0;
    	}
    	favgMilisecCounter++;
	}
	
	public static void addToFrequencyMap(int[] frequencyIndex, int inputLength) {

		for(int i = 0; i < inputLength;i++) {
			frequencyMap[frequencyMapCounter++] = frequencyIndex[i];
		}
		Debug.debug(3,"StreamRefinaryFrequencyMethods addToFrequencyMap oldLength: "+(frequencyMapCounter - inputLength)+ ", New frequencyMap Length: "+frequencyMapCounter);
	}
	 static void addToFrequencyBuffer(int amplitude, int frequency) {
		 
		  amplitudeTotal += amplitude;
		  frequencyTotal += frequency;
		  bufferCounter++;
		  Debug.debug(5,"StreamRefinaryFrequencyMethods addToFrequencyBuffer  amplitude: "+amplitude+ ", amplitudeTotal: "+ amplitudeTotal 
			+", frequency: "+frequency + ", frequencyTotal:  "+frequencyTotal+ ", bufferCounter: "+bufferCounter + ", i: "+MultiAnalysisBuilder.i);
	 }
	 
	 static void addToMilisecTempMap() {
		 if(bufferCounter == 0 )
			 return;
		 
		 avg_a = amplitudeTotal / bufferCounter;
		 avg_f = frequencyTotal / bufferCounter;
		 tempFrequencyMap[tempFrequencyMapCounter++] =  avg_a+1;
		 tempFrequencyMap[tempFrequencyMapCounter++] =  avg_f;
		 
		 Debug.debug(5,"StreamRefinaryFrequencyMethods addToMilisecTempMap amplitudeTotal: "+amplitudeTotal + ", frequencyTotal: "+frequencyTotal
				 + ", avg_a: "+tempFrequencyMap[tempFrequencyMapCounter-2] + ", avg_f "+tempFrequencyMap[tempFrequencyMapCounter-1] + ", bufferCounter: "+bufferCounter + ", i: "+MultiAnalysisBuilder.i);
		 
		 amplitudeTotal = 0;
		 frequencyTotal = 0;
		 bufferCounter = 0;
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
}
