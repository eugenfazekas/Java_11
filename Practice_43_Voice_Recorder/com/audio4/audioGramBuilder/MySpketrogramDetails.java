package com.audio4.audioGramBuilder;

import com.audio0.main.AppSetup;
import com.audio3.audioGramInitializer.AudioAnalysisInitializer;
import com.audio8.util.Debug;

public class MySpketrogramDetails {

	 public static int amplitudeTotalBuffer;
	 public static int frequencyTotalBuffer;
	 private static int avg_a;
	 private static int avg_f;
	 public static int bufferCounter;
	 private static int spektrogramMapCounter;
	 public static int[] spektrogramMap;
	 public  static int S_AVG_MILISEC_LENGTH; 
	 public  static int savgMilisecCounter;
	 
	 
	 public static void iniMySpektroGramDetails() {
		 
		 amplitudeTotalBuffer = 0;
		 frequencyTotalBuffer = 0;
		 bufferCounter = 0;
		 spektrogramMapCounter = 0;
		 spektrogramMap = new int [2000];
		 savgMilisecCounter = 0;		
		 S_AVG_MILISEC_LENGTH = AudioAnalysisInitializer.getBuffersLengthByMilisec(AudioAnalysisInitializer.sampleRate,AppSetup.SPEKTROGRAM_BUFFER_MILISEC_LENGTH);
		 Debug.debug(2,"MySpketrogramDetails initFrequencyWaveDetails!");
	 }
	 
	 static void addToSpektroGramBuffer(int amplitude, int frequency) {
		 
		  amplitudeTotalBuffer += amplitude;
		  frequencyTotalBuffer += frequency;
		  bufferCounter++;
		  Debug.debug(5,"DualGramBuilder addToSPKMBuffer  amplitudeTotal: "+amplitudeTotalBuffer+ ", amplitude: "+ amplitude 
				  + ", frequencyTotal:  "+frequencyTotalBuffer + ", frequency:  "+frequency+ ", bufferCounter: "+bufferCounter + ", i: "+MultiAnalysisBuilder.i);
	 }
	 
	 static void addToMySpektroGramMap(int amplitudeTotal ,int  frequencyTotal, int  buffercounter) {

		 if(buffercounter == 0 || amplitudeTotal == 0)
			 return;
		 
		 avg_a = amplitudeTotal / buffercounter;
		 avg_f = frequencyTotal / buffercounter;
		 
		 spektrogramMap[spektrogramMapCounter++] =  AmplitudeDetails.getPercentAmplitude(AudioAnalysisInitializer.averageAmplitude, avg_a)+1;
		 spektrogramMap[spektrogramMapCounter++] =  avg_f;
		 
		 Debug.debug(5,"DualGramBuilder addToSpektroGramMap amplitudeTotal: "+amplitudeTotal + ", frequencyTotal: "+frequencyTotal
				 + ", avg_a: "+spektrogramMap[spektrogramMapCounter-2] + ", avg_f "+spektrogramMap[spektrogramMapCounter-1] + ", bufferCounter: "+buffercounter + ", i: "+MultiAnalysisBuilder.i);
		 
		 amplitudeTotalBuffer = 0;
		 frequencyTotalBuffer = 0;
		 bufferCounter = 0;
	 }
}
