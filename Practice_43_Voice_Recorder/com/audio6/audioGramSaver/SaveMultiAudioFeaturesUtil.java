package com.audio6.audioGramSaver;

import java.util.Arrays;

import com.audio0.main.AppSetup;
import com.audio1.logical.EntryPointMethods;
import com.audio8.util.Debug;

public class SaveMultiAudioFeaturesUtil {
	
	private static int debug_level_INFO = 5;

	static double getMaxMagnitude(double[][] spectrogram) {
		   
	    double maxMagnitude = Double.MIN_VALUE;
	       
	    for (double[] row : spectrogram) {
	    	   
	        for (double value : row) {
	        	   
	            if (value > maxMagnitude) 
	                   maxMagnitude = value;    
	        }
	    }
	       return maxMagnitude;
	}

	static int getRoundTo(int roundTO, int inputnumber) {
			
		while(inputnumber % roundTO != 0) 			
			inputnumber++;
				
		return inputnumber;
	}
	
    static int getBuffersLengthByMilisec(int sampleRate, int milisec) {
   		
    	return (milisec * sampleRate) /1000;
    }
    
	static byte[] buildAudiodDataFromInt(int[] intStream)	{
		
		Debug.debug(debug_level_INFO,"AnalysisInitializer buildAudiodDataFromInt intSream.Length: "
			+intStream.length);
		
		return convertIntArrayToByteArray(intStream);
	}
	
	static byte[] convertIntArrayToByteArray(int[] intStream) {
		
		byte[] byte_stram = new byte[intStream.length * 2];
		int counter = 0;
		
		for(int i = 0; i < intStream.length; i++) {  

			byte_stram[counter++] = (byte) (intStream[i] >> 8);
			byte_stram[counter++] = (byte) intStream[i] ;
		}
		
		Debug.debug(debug_level_INFO,"SoundBuilder convertIntArrayToByteArray byte_Sequence.length "
			+byte_stram.length);
		
			return byte_stram;
	}
	
	static String sequenceArrayToString (String[] inputArray) {
		
		Debug.debug(debug_level_INFO,"SoundBuilder sequenceArrayToString old length: " +inputArray.length 
				+",  InputArray: "+Arrays.toString(inputArray));
		
		StringBuilder string = new StringBuilder();
		string.append(inputArray[0]);
		
		for(int i = 1; i < inputArray.length-1; i++) {
		
			if(inputArray[i] == null)
				break;
			
			if(inputArray != null)
				string.append(","+inputArray[i]);
		}
		
		Debug.debug(debug_level_INFO,"SoundBuilder sequenceArrayToString New array: "+ string.toString());
		
			return string.toString();	
	}
	
	static String getSubPath() {
		
		String path = null;
		
		if(EntryPointMethods.getSvitch().equals("saveNamedRecords"))
			path = AppSetup.SPEKTRUM_PATH;
		
		if(EntryPointMethods.getSvitch().equals("voiceRecognitionDebug"))
			path = AppSetup.VOICE_RECOGNITION_DEBUG_DIRECTORY;
		
		if(EntryPointMethods.getSvitch().equals("buildSpektrogramFromAudioFile"))
			path = AppSetup.SPEKTRUM_PATH;
		
		if(EntryPointMethods.getSvitch().equals("buildSequenceFromFile"))
			path = AppSetup.REBUILDED_SEQUENCE_DIRECTORY;
		
		if(EntryPointMethods.getSvitch().equals("buildTestAudioSequence"))
			path = AppSetup.REBUILDED_SEQUENCE_DIRECTORY;
		
		if(EntryPointMethods.getSvitch().equals("timeFixedSoundRecorder"))
			path = AppSetup.TIME_FIXED_RECORD_PATH;
		
		if(EntryPointMethods.getSvitch().equals("saveContinueRecords"))
			path = AppSetup.CONTINUE_RECORDS_PATH;
		
			return path;
	}
}
