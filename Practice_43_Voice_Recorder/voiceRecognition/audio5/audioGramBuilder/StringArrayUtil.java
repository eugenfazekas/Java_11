package voiceRecognition.audio5.audioGramBuilder;

import java.util.Arrays;

import voiceRecognition.audio8.util.Debug;

public class StringArrayUtil {
	
	private static int counter;
	private static String[] tempStringArray; 
	private static StringBuilder stringBuilder;
	
	private static int debug_level_INFO = 5;
	private static int debud_level_DEBUG = 5;
	
	static String[] convertIntArrayToStringArray(int[] intArray) {
		
		String[] stringArray = new String[intArray.length];
		
		for(int i = 0; i < intArray.length; i++) 
			stringArray[i] = String.valueOf(intArray[i]); 
		
			return stringArray;		
	}
	
	static String[] convertInt2DToStringArray(int[][] int_2D_Array) {
		
		Debug.debug(debug_level_INFO,"StringArrayUtil convertIntArrayToStringArray 2d length: " 
				+int_2D_Array.length);
		
		StringBuilder builder =  new StringBuilder();
		String[] returnArray = new String[544];
		int counter = 1;
		int lastLength = 0;
		returnArray[0] = "0\n";
		
		for(int i = 1; i < int_2D_Array.length; i++) {
			
			if(int_2D_Array[i].length > lastLength) {
				
				builder =  new StringBuilder();
				//builder.append(counter+",");
				
				for(int j = 0; j < lastLength+1; j++) 
					builder.append(int_2D_Array[i][j]+",");
				
				builder.deleteCharAt(builder.length()-1);
				builder.append("\n");
				returnArray[counter++] = builder.toString();
				lastLength = lastLength+1;
			}
			
			Debug.debug(debud_level_DEBUG,"FileUtil convertInt2DToStringArray "
				+Arrays.toString(int_2D_Array[i]));
		}
		
		Debug.debug(debug_level_INFO,"FileUtil convertInt2DToStringArray returnArray: "
			+returnArray.length +" arr[0] "+ returnArray[0]);
		
			return returnArray;
	}
	
	static String[] mergeStringsAndFilterNull(String[] a, String[] b) {
		
		int counter1 = 0;
		int counter2 = 0;
		String[] mergedString = new String[a.length + b.length];
		
		for(int i = 0; i < a.length ; i++ ) {
			
			if(a[i] != null)
				counter1++;
		}
		
		for(int i = 0; i < b.length ; i++ ) {
			
			if(b[i] != null)
				counter2++;			
		}
		
		mergedString = new String[counter1+ counter2];
		
		counter=0;
		
		for(int i = 0; i < counter1 ; i++ )
			mergedString[counter++] = a[i];
		
		for(int i = 0; i < counter2 ; i++ ) 
			mergedString[counter++] = b[i];			
				
		Debug.debug(debug_level_INFO,"AudioBuilderUtil mergeStringsAndFilterNull a.length: "
			+a.length + ", b.length: "+ b.length+ " new length: "+mergedString.length);
		
			return mergedString;
	}
	
	static String[]filterEmptyStringsWithFixedLength(String[]inputStringArray,int arraySize
			,String arrayNam) {
		
		tempStringArray = new String[arraySize];
		
		for (int i = 0; i < arraySize ; i++)
			tempStringArray[i] = inputStringArray[i];
		
		Debug.debug(debug_level_INFO,"AudioBuilderUtil filterEmptyStringsWithUnknownLength Array Name: "
			+arrayNam+", old length: "+inputStringArray.length+", new length: "+tempStringArray.length);
		
			return tempStringArray;		
	}
	
	static String[] filterEmptyStringsWithUnknownLength(String[] inputStringArray
			,String arrayNam) {
		
		int i;
		counter = 0;
			
		for(i = 0; i < inputStringArray.length; i++) {
			
			if(inputStringArray[i] != null)
				counter++;
			
			if(inputStringArray[i] == null)
				break;
		}
		
		tempStringArray = new String[counter];

		for(i = 0; i < counter; i++ )
			tempStringArray[i] = inputStringArray[i]; 
		
		Debug.debug(debug_level_INFO,"AudioBuilderUtil filterEmptyStringsWithUnknownLength Array Name: "
			+arrayNam+", old length: "+inputStringArray.length+", new length: " +tempStringArray.length);
		
			return tempStringArray;
	}
	
	static String buildIntArrayToString(int[] input) {
		
		stringBuilder = new StringBuilder();
		
		stringBuilder.append(input[0]);
		
		for(int i = 1; i < input.length; i++) 
			
			stringBuilder.append(","+input[i]);
		
		return stringBuilder.toString();
	}
}
