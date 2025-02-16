package com.audio5.audioGramBuilder;

public class MultiDetails {

	
	public static void resetMultiDetails() {
		
	    AGBCVariables.totalSampleCounter = 0;
	    AGBCVariables.pozitiveACounter = 0; 
	    AGBCVariables.negativeACounter = 0; 
	    AGBCVariables.skipCounter = 0; 
	    AGBCVariables.newFrequency = false;
	    AGBCVariables.freqLengthCorrection = 0;
	    MultiAnalysisBuilder.i = MultiAnalysisBuilder.i - AGBCVariables.limit;
	    
	}
	
	public static void buildFrequencysPositive (int sample,int i, int sampleRate) {

		AGBCVariables.totalSampleCounter++;

	//System.out.println("a i: "+i + " AGBCVariables.totalSampleCounter "+AGBCVariables.totalSampleCounter  );
		if(AGBCVariables.pozitiveACounter < AGBCVariables.limit) 
			AGBCVariables.pozitiveACounter++;
		
		if(AGBCVariables.pozitiveACounter == AGBCVariables.limit && AGBCVariables.negativeACounter > 0 && AGBCVariables.negativeACounter < AGBCVariables.limit )
			AGBCVariables.negativeACounter--;
						
		if(AGBCVariables.pozitiveACounter >= AGBCVariables.limit && AGBCVariables.negativeACounter >= AGBCVariables.limit) {
					
			AGBCVariables.freqLengthCorrection =  AGBCVariables.limit + AGBCVariables.skipCounter*2+1;
			AGBCVariables.newFrequency = true;
	System.out.println(" true i: " +i);		
//			AGBCVariables.amplitudeWaveMap[AGBCVariables.amplitudeWaveMapCounter++] = AGBCVariables.amplitudeTotalBuffer / AGBCVariables.totalSampleCounter;				
			//AGBCVariables.frequencyWaveMap[AGBCVariables.frequencyWaveMapCounter++] = sampleRate / (AGBCVariables.totalSampleCounter - AGBCVariables.limit - AGBCVariables.skipCounter*2+1) ;

			
//			AGBCVariables.totalSampleCounter= AGBCVariables.limit + AGBCVariables.skipCounter;
//			AGBCVariables.amplitudeTotalBuffer = 0;
//			AGBCVariables.pozitiveACounter = 0;
//			AGBCVariables.skipCounter = 0;
//			AGBCVariables.negativeACounter = 0;
		}
		
//		System.out.println("buildfrequencys pos Added! pozitiveACounter: " + AGBCVariables.pozitiveACounter 
//				+ ", AGBCVariables.negativeACounter " +AGBCVariables.negativeACounter+ " , i: "
//					+(i - AGBCVariables.limit - AGBCVariables.skipCounter*2+1) + ", skipCounter: "+AGBCVariables.skipCounter + ", sample " + sample);
	//	System.out.println("buildfrequencys i: "+i +", samples[i]: "+sample +", pozitiveACounter: "+AGBCVariables.pozitiveACounter+", negativeACounter: "+AGBCVariables.negativeACounter 
//			+ ", skipCounter: "+AGBCVariables.skipCounter + ", totalSampleCounter: "+AGBCVariables.totalSampleCounter);
	
		
	//	System.out.println(""+Arrays.toString(AGBCVariables.amplitudeWaveMap));
	//	System.out.println(""+Arrays.toString(AGBCVariables.frequencyWaveMap));
	};
	
	public static void buildFrequencysNegative (int sample) {
		
		AGBCVariables.totalSampleCounter++;
//		System.out.println("buildfrequencys neg ! pozitiveACounter: " + AGBCVariables.pozitiveACounter 
//				+ ", AGBCVariables.negativeACounter " +AGBCVariables.negativeACounter + ", skipCounter: "+AGBCVariables.skipCounter + ", sample: "+sample);
		if(AGBCVariables.pozitiveACounter > 0 && AGBCVariables.pozitiveACounter < AGBCVariables.limit) {

			AGBCVariables.pozitiveACounter--;
			AGBCVariables.skipCounter++;
		}
		
		if(AGBCVariables.negativeACounter < AGBCVariables.limit) 	
			AGBCVariables.negativeACounter++;		
		
		if(AGBCVariables.negativeACounter == AGBCVariables.limit) {
			AGBCVariables.pozitiveACounter = 0;
			AGBCVariables.skipCounter = 0;
		}
	}
}
