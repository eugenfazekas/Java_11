package voiceRecognition.audio5.recognition;

import java.util.Arrays;

import voiceRecognition.audio8.util.Debug;

public class VoiceRecognitionDBUtil {

	private static int[] tempIntArray;

	private static int debug_level_INFO = 5;
	private static int debud_level_DEBUG = 5;

	static int[] convertStringArrayToIntArray(String[] input) {
		
		tempIntArray = new int[input.length];
		
		for(int i = 0; i < input.length; i++)
			tempIntArray[i] =  Integer.valueOf(input[i]);
		
		return tempIntArray;
	}
	
	static int[][][] buildThreeDAreaArray(String input) {

		String[] substrs1;
		String[] substrs2;
		String[] substrs3;
		String substr;
		int[] array;
		int[][][] threeDArray;
		
		String[] sequences = input.split("&");
		threeDArray = new int[sequences.length][][];
		Debug.debug(debug_level_INFO,"VoiceRecognitionUtil Seq lenght: "+sequences.length);
		
		for(int i = 0; i < sequences.length; i++) {
			
			substrs1 = sequences[i].split("_");
			substrs2 = substrs1[substrs1.length-1].split(":");			
			threeDArray[i] = new int[Integer.valueOf(substrs2[0])+1][];
			Debug.debug(debud_level_DEBUG,"VoiceRecognitionUtili length " +i + ", "+threeDArray[i].length );
			
			for(int j = 0; j < substrs1.length ; j++) { 
				
				substrs2 = substrs1[j].split(":");			
				substr = substrs2[1].substring(1, substrs2[1].length()-1);			
				substrs3 = substr.split(", ");
				
				array = new int[substrs3.length];
				
				for( int k = 0; k < substrs3.length; k++) {

					array[k] = Integer.valueOf(substrs3[k]);
				}
					threeDArray[i][Integer.valueOf(substrs2[0])] = array;
					
					Debug.debug(debud_level_DEBUG,"VoiceRecognitionUtil Array i: "+i+ ", j: "+ Integer.valueOf(substrs2[0])
						+ ", Array "+ Arrays.toString(array) + ", Check: "+substr);
			}
		}	
		return threeDArray;
	}
	
	public static int[][] buildThreeDFrequencyGram(String input) {
		
		String[] arrays = input.split(";");
		String[] subArray;
		
		int result [][] = new int[arrays.length][];
		
		for(int i = 0; i < result.length; i++) {
			
			subArray = arrays[i].split(",");
			result[i] = new int[subArray.length];
			
			for(int j = 0; j < result[i].length; j++)
				result[i][j] = Integer.parseInt(subArray[j]);
		}
		
		for(int k = 0; k < result.length; k++) 
			Debug.debug(debud_level_DEBUG, " "+ Arrays.toString(result[k]));
		
		return result; 
	}
}
