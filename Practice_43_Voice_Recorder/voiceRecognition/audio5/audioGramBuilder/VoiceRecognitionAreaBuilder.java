package voiceRecognition.audio5.audioGramBuilder;

import java.util.Arrays;

import voiceRecognition.audio8.util.Debug;

public class VoiceRecognitionAreaBuilder {
	
	static int[][][] result = new int[2][][];
	static int[][] twoDTemp1;
	static int[][] twoDTemp2;
	static int highest1;
	static int edgeH;
	static int lengthV;
	static int twoD[][];
	private static int debug_level_INFO = 5;
	private static int debud_level_DEBUG = 5;

	public static int[][][] mainAreaBuilder(int[] amplitde, int[] frequnecys,int[] borders) {
		
		result = new int[2][][];				
		twoDTemp1 = buildTwoDFromOneD(amplitde ,borders[0], borders[1]);	
		twoDTemp2 = buildTwoDFromOneD(frequnecys ,borders[0],borders[1]);
		
		highest1 = twoDTemp1.length > twoDTemp2.length ? twoDTemp1.length : twoDTemp2.length;
		
		highest1 = twoDTemp1.length > twoDTemp2.length ? twoDTemp1.length : twoDTemp2.length;
		
		result[0] = new int[twoDTemp1.length][];
		result[1] = new int[twoDTemp2.length][];
		
		for(int i = 0; i < highest1; i++) {
			
			if(i < twoDTemp1.length) {
				result[0][i] = buildLineBorders(twoDTemp1[i]);
			}
			
			if(i < twoDTemp2.length) {
				result[1][i] = buildLineBorders(twoDTemp2[i]);
			}
		}
		
		result[0][0]= frequnecys;
		result[1][0]= amplitde;
		
		return result;
	}
		
	private static int[][] buildTwoDFromOneD(int[] inputOneD , int startIndex, int endIndex) {
		
		edgeH = 0;
		lengthV = endIndex - startIndex;
		int i;
				
		for( i = 0; i < inputOneD.length; i++)
			if(inputOneD[i] > edgeH )
				edgeH = inputOneD[i];
		
		Debug.debug(debug_level_INFO,"VoiceRecognitionAreaBuilder buildTwoDFromOneD Edge: "+ edgeH);
		
		twoD = new int[edgeH+1][];
		
		for(i = 0; i < twoD.length; i++)
			twoD[i] = new int[lengthV];
		
		for(int index = startIndex; index < endIndex; index++) {
			
			for(i = 0; i < twoD.length; i++) {
				
			//	Debug.debug(debugLevel,"VoiceRecognitionAreaBuilder buildTwoDFromOneD H: "
			//+index +", V: "+i);
				
				if(i == inputOneD[index])
					break;
				
				if(i < inputOneD[index])
					twoD[i+1][index-startIndex] = 7;
			}
		}
		
		for(int test = twoD.length-1; test > 0 ; test --)
			Debug.debug(debud_level_DEBUG,"H :"+ test + " "+Arrays.toString(twoD[test]));
		
		return twoD;
	}
	
	private static int[] buildLineBorders(int[] input) {
		
		
		int[] temp = new int[20];
		int[] result;
		int resultCounter = 0;
		
		Debug.debug(debug_level_INFO,"VoiceRecognitionAreaBuilder buildLineBorders input "
			+ Arrays.toString(input));
		
		temp[resultCounter++] = 0;
		for(int i = 0; i < input.length-1; i++) {
			
			if(input[i]== 0 && input[i+1]== 7)
				temp[resultCounter++] = i+1;
			
			if(input[i]== 7 && input[i+1]== 0)
				temp[resultCounter++] = i;
		}
		temp[resultCounter++] = input.length-1;
		
		result = new int[resultCounter];
		resultCounter = 0;
		
		for(int i = 0; i < result.length; i++)
			result[resultCounter++] = temp[i];
		
		Debug.debug(debug_level_INFO,"VoiceRecognitionAreaBuilder buildLineBorders Arr"
				+Arrays.toString(result));
		
		return result;
	}
}
