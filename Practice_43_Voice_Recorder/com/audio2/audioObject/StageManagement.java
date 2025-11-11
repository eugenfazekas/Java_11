package com.audio2.audioObject;

import java.util.Arrays;

import com.audio8.util.Debug;

public class StageManagement {
		
	private static String[] temp1;
	private static String[] temp2;
	
	private static int debug_level_INFO = 5;
	private static int debud_level_DEBUG = 5;
	
	static String[] buildStages(ObjectSetup setups, String mainProfile) {
		
		temp1 = new String[0]; 
		
		if(setups.isPreTrim())
			temp1 = addToArray(temp1, "trim");
		
		if(setups.isMultiAnalysis())
			temp1 = addToArray(temp1, "analysis");
		
		if(setups.isVoiceRecognition() && mainProfile.equals("voiceRecognition") 
			|| setups.isVoiceRecognition() && mainProfile.equals("voiceRecognitionDebug"))
			temp1 = addToArray(temp1, "voiceCheck");
		
		if(setups.isSave())
			temp1 = addToArray(temp1, "save");
	
		temp1 = addToArray(temp1, "end");
		
		Debug.debug(debug_level_INFO, "StageManagement buildStages: "+ Arrays.toString(temp1));
				
		return temp1;
	}

	private static String[] addToArray(String[] input, String addString) {
		
		temp2 = new String[input.length+1];
		
		for(int i = 0; i < input.length; i++) 
			temp2[i] = input[i];
		
		temp2[input.length] = addString;
		
		Debug.debug(debud_level_DEBUG, "StageManagement addToArray old length: " + input.length +", old Array: "
				+ Arrays.toString(input) + ", new array: "+ Arrays.toString(temp2));
		
		return temp2;
	}
}
