package com.audio3.recorder.refinary;

import com.audio0.main.AppSetup;
import com.audio2.recorder.AudioListener;
import com.audio5.audioGramBuilder.AudioGramObject;
import com.audio8.util.Debug;

public class StreamRefinaryFrequencyMethods {
	 
	static int amplitude;
	static int sampleRate;	
	static boolean validVoiceDetection;
	
	static int[] tempAmpMap = new int[500];
	static int   tempAmpMapCounter = 0;		
	static int[] tempFreqMap = new int[500];
	static int   tempFreqMapCounter = 0;	
		
	
	static int[] ampMap; 
	static int   ampMapCounter;
	static int[] freqMap; 
	static int   freqMapCounter;
	
	static int[][]lastAmpMaps;
	static int   lastAmpMapsCounter;
	static int[][] lastFreqMaps;
	static int   lastFreqMapsCounter;	
	
	static int[] mapsLengthCounter;
	static int  mapsArrayLengthCounter;

	private static int F_AVG_MILISEC_LENGTH;
	private static int favgMilisecCounter;

    private static AudioGramObject firstBuffer;
    private static AudioGramObject middleBuffer;
    private static AudioGramObject lastBuffer;

    
	//public static float[] frequencySampleLengths;
    	
	public StreamRefinaryFrequencyMethods () {
			
		F_AVG_MILISEC_LENGTH = StreamRefinaryUtil.getBuffersLengthByMilisec(
			(int)AudioListener.format.getSampleRate()
			,AppSetup.AUDIO_BUFFER_MILISEC_LENGTH);
		
		ampMap = new int[20000]; 
		ampMapCounter = 0;	
		freqMap = new int[20000]; 
		freqMapCounter = 0;	
		
		lastAmpMaps = new int[200][];
		lastAmpMapsCounter = 0;
		lastFreqMaps = new int[200][];
		lastFreqMapsCounter = 0;
		
		mapsLengthCounter = new int[200];
		mapsArrayLengthCounter = 0;
		
		sampleRate = (int) AudioListener.format.getSampleRate();		
		firstBuffer = null;
		middleBuffer = null;
		lastBuffer = null;	
	}
   
	 static void initBaseBufferVariables( ) {

		lastAmpMaps[lastAmpMapsCounter++] = tempAmpMap;
  		lastFreqMaps[lastFreqMapsCounter++] = tempFreqMap;
  		mapsLengthCounter[mapsArrayLengthCounter++] = tempAmpMapCounter;
  		
  		tempAmpMap = new int[1000];
  		tempAmpMapCounter = 0;
  		
  		tempFreqMap = new int[1000];
  		tempFreqMapCounter = 0; 
  		 		
		validVoiceDetection = false;
	}
	
	 static void prebuildMsecCheck(int sample) {
		
		if(lastBuffer != null)
			return;

		if(firstBuffer == null && favgMilisecCounter == 0) 			
			firstBuffer = new AudioGramObject(F_AVG_MILISEC_LENGTH);
	
		if(firstBuffer != null && favgMilisecCounter == 0) 		
			middleBuffer = new AudioGramObject(F_AVG_MILISEC_LENGTH);
				
		if(middleBuffer == null)
			firstBuffer.addSampleToObject(sample);
			
		if(middleBuffer != null)
			middleBuffer.addSampleToObject(sample);
		
		favgMilisecCounter++;
		
		if(favgMilisecCounter == F_AVG_MILISEC_LENGTH-1) {
			
			favgMilisecCounter = 0;
			
			if(middleBuffer != null)
				lastBuffer = new AudioGramObject(F_AVG_MILISEC_LENGTH);
			
			Debug.debug(5,"StreamRefinaryFrequencyMethods prebuildMsecCheck fBuff: "+
					(firstBuffer == null) + ", mBuff: " +(middleBuffer == null)+ ", lBuff: "
					+ (firstBuffer == null));
		}
	}

	 static void builTempAmpFreqMap(int i, int sample) {
		
		if(lastBuffer == null) {
			prebuildMsecCheck(sample);
		
			return;
		}

		lastBuffer.addSampleToObject(sample);
		
		if(favgMilisecCounter == F_AVG_MILISEC_LENGTH-1) {
			
			amplitude = calculateAVGAmplitude(firstBuffer,middleBuffer,lastBuffer);
			
			tempAmpMap[tempAmpMapCounter++] = amplitude;
			tempFreqMap[tempFreqMapCounter++] =  StreamRefinaryUtil.getFrecvencys(firstBuffer.getSamples(),
					middleBuffer.getSamples(), lastBuffer.getSamples(), sampleRate)[1];

			favgMilisecCounter = -1;
			firstBuffer = middleBuffer;
			middleBuffer = lastBuffer;
			lastBuffer = new AudioGramObject(F_AVG_MILISEC_LENGTH);	
		}
		
		favgMilisecCounter++;
	}
	
	 static void addToAmpFreqMap(int[] inputAmpMap, int[] inputFrecMap, int inputLength) {
		
		if(StreamRefinaryAmplitudeMethods.build == false) return;
		
		for(int i = 0; i < inputLength;i++) {
			
			ampMap[ampMapCounter++] = inputAmpMap[i];
			freqMap[freqMapCounter++] = inputFrecMap[i];
		}

		Debug.debug(5,"StreamRefinaryFrequencyMethods addToFrequencyMap oldLength: "
			+(freqMapCounter-inputLength)+", New frequencyMap Length: "+freqMapCounter);
	}
	
	 static void freqeuncyArrayFilterEmptyElemnts() {
			
		int[] newArray1 = new int[ampMapCounter];
		int[] newArray2 = new int[freqMapCounter];
		
		for(int i = 0; i < newArray1.length;i++) {
			newArray1[i]= ampMap[i];
			newArray2[i]= freqMap[i];
			
		}
		ampMap = newArray1;	
		freqMap = newArray2;	
	}
	
	 static int calculateAVGAmplitude(AudioGramObject first, AudioGramObject middle, AudioGramObject last ) {
		
		amplitude = (first.getPosAmplitude() / first.getPosCounter()
				+first.getNegAmplitude() / first.getNegCounter()
				+middle.getPosAmplitude()/ middle.getPosCounter()
				+middle.getNegAmplitude()/ middle.getNegCounter()
				+last.getPosAmplitude()  / last.getPosCounter()
				+last.getNegAmplitude()  / last.getNegCounter())/3;
		
		return amplitude;
	}
	
	 static void validVoiceCheck() {
		
		int voiceCounter = 0;
		
		for(int i = 0; i < tempFreqMapCounter; i = i+2) {
			
			Debug.debug(5, "StreamRefinaryFrequencyMethods validVoiceCheck i "+ i 
					+ ", tempAmpFreqMap[i]: "+tempFreqMap[i]);
			Debug.debug(5, "StreamRefinaryFrequencyMethods validVoiceCheck i "+ i 
					+ ", tempAmpFreqMap[i+1]: "+tempFreqMap[i+1]);
			
			if(tempFreqMap[i+1] >30) 
				voiceCounter++;
			
			if(tempFreqMap[i+1] < 30) 
				voiceCounter = 0;
			
			if(voiceCounter == 5) {
				validVoiceDetection = true;
				break;
			}
		}
	} 

	 static void resetSaveStream() {

		ampMap = new int[20000]; 
		ampMapCounter = 0;
		
		freqMap = new int[20000]; 
		freqMapCounter = 0;	
	}
}
