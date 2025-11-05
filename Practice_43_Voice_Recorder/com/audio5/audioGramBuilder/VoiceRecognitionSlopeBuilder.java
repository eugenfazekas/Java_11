package com.audio5.audioGramBuilder;

import com.audio8.util.Debug;

public class VoiceRecognitionSlopeBuilder {

	static int startH;
	static int startV;
	static int endH;
	static int endV;
	static int length;
	static int avgAngle;
	static int counter;
	static int thisAngle;
	static int testAngle;
	
	static boolean positive;
	static boolean negative;
	static boolean forwardTest;

	static int[] slopes;
	static int[] tempSlopes;
	static int slopeCounter;
	
	static int i;
	static int j;
	private static int debugLevel = 5;

	public static int[] mainSlopeBuilder(int[] input, int[] borders) {
	
		slopes = new int[100];
		slopeCounter = 0;
		reset();
		
		for(i = 1; i < input.length-1; i++) {
			
			thisAngle = getLocalAngle(input);
			Debug.debug(debugLevel,"VoiceRecognitionSlopeBuilder mainSlopeBuilder i: " + i
				+ ", input[i]: "+input[i] +", input[i+1]: "+input[i+1] + ", thisAngle: "+thisAngle);
			
			if(positive) {

				if(angleEndCheck(input)) {
					addToArray(input,borders);
					continue;
				}
			}
			
			if(negative) {

				if(angleEndCheck(input)) {
					addToArray(input,borders);
					continue;
				}
			}
			
			if(!negative && !positive) {
				
				if(input[i+1] > input[i]) {
				
					positive = true;
					startH = i;
					startV = input[i];
				}	
				
				if(input[i+1] < input[i]) {
					
					negative = true;
					startH = i;
					startV = input[i];
				}
			}
		}
		
		Debug.debug(debugLevel,"VoiceRecognitionSlopeBuilder mainSlopeBuilder End V: " + input[i-1]
			+ ", H: " + (i - startH));
		
		if(startH != 0 && (input[i-1] + (i-1 - startH)) >  10)
			addToArray(input,borders);
		
		slopes = filterEmptyElements(slopes, slopeCounter);
		
			return slopes;
	}
	
	static void reset() {
		
		startH = 0;
		startV = 0;
		endH = 0;
		endV = 0;
		positive = false;
		negative = false;		
	}

	static void addToArray(int[] input, int[] borders) {
		
		slopes[slopeCounter++] = startH-borders[0]-1;
		slopes[slopeCounter++] = startV;
		slopes[slopeCounter++] = i-borders[0]-1;
		slopes[slopeCounter++] = input[i];
		slopes[slopeCounter++] = getAngle(input[i] - startV, i - startH);
		
		if(input[i] != input[i])
			i--;
		
		reset();
	}
		
	static boolean angleEndCheck(int[] input) {
		
		testAngle = getAngle(input[i] - startV, i - startH);
		
		Debug.debug(debugLevel,"VoiceRecognitionSlopeBuilder angleEndCheck testAngle: " + testAngle 
			+", thisAngle: "+thisAngle + ", V: "+Math.abs(input[i] - startV)+", H: " + (i - startH));
		
		if( Math.abs ( (testAngle + 180) - (thisAngle + 180)  ) > 25  &&
			(((Math.abs(input[i] - startV) + (i - startH))  >  5 ) ||  Math.abs(input[i] - startV) > 20 ))
			return true;
		
		else
			return false;
	}
	
	private static int getLocalAngle(int[] input) {
		
		if( (input[i-1] < input[i] && input[i+1] < input[i]) ||
			(input[i-1] > input[i] && input[i+1] > input[i]) )
			
			return getAngle(input[i+1] - input[i], 2);
			
		else
			return getAngle(input[i+1] - input[i-1], 3);
	}
	
	private static int getAngle(int y, int x) {
		
		return (int) Math.toDegrees(Math.atan2(y, x));
	}

	private static int[] filterEmptyElements(int[] input, int lentgh) {
		
		tempSlopes = new int[lentgh];
		
		for(i = 0; i < tempSlopes.length; i++)
			tempSlopes[i] = input[i];
		
		Debug.debug(debugLevel,"VoiceRecognitionSlopeBuilder filterEmptyElements old length"
				+ input.length + ", new length: "+lentgh);
		
		return tempSlopes;
	}

	
}
