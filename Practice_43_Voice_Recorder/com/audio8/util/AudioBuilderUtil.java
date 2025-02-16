  package com.audio8.util;

public class AudioBuilderUtil {

	public static int splineSequence = 1;
					   
	public static int getRoundTo(int roundTO, int inputnumber) {
			
		while(inputnumber % roundTO != 0) 			
			inputnumber++;
				
		return inputnumber;
	}
	
    public static int getBuffersLengthByMilisec(int sampleRate, int milisec) {
   		
    	return (milisec * sampleRate) /1000;
    }
    
	public static byte[] buildAudiodDataFromInt(int[] intStream)	{
		
		Debug.debug(3,"AnalysisInitializer buildAudiodDataFromInt intSream.Length: "
			+intStream.length);
		
		return IntArrayUtil.convertIntArrayToByteArray(intStream);
	}
}

 

