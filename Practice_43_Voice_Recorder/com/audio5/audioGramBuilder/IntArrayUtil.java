package com.audio5.audioGramBuilder;

import com.audio8.util.Debug;

public class IntArrayUtil {
	
	private static int counter;
	private static int[] tempArray; 
	
	static int[] rebuildIntArray(int[] inputgArray, int arraySize, String arrayName) {
		
		tempArray = new int[arraySize];
		
		for(counter = 0 ; counter < arraySize; counter++) 
			tempArray[counter] =  inputgArray[counter];	
		
		Debug.debug(3,"IntArrayUtil rebuildInttArray Array Name: "
				+arrayName+", old length: "+inputgArray.length+", new length: "
				+tempArray.length);
		
			return tempArray;
		
	}
	
	static int [] filterIntArray(int[] inputgArray, int filterLowerLimit
			,String arrayName) {
		
		int i;
		counter=0;
			
		for(i = 0 ;i < inputgArray.length; i++) 
			counter = inputgArray[i] > filterLowerLimit ? counter++ : counter;
			
		counter = 0;
		
		for( i = 0; i < inputgArray.length; i++ ){
			
			if(inputgArray[i] > filterLowerLimit) {
				
				tempArray[counter++] = inputgArray[i];
			}
		}
			
		Debug.debug(3,"IntArrayUtil filterEmptyStringsWithUnknownLength Array Name: "
			+arrayName+", old length: "+inputgArray.length+", new length: "
			+tempArray.length);
		
			return tempArray;		
	}
	
	static byte[] convertIntArrayToByteArray(int[] intStream) {
		
		byte[] byte_stram = new byte[intStream.length * 2];
		int counter = 0;
		
		for(int i = 0; i < intStream.length; i++) {  

			byte_stram[counter++] = (byte) (intStream[i] >> 8);
			byte_stram[counter++] = (byte) intStream[i] ;
		}
		
		Debug.debug(2,"IntArrayUtil convertIntArrayToByteArray byte_Sequence.length "
			+byte_stram.length);
		
			return byte_stram;
	}
}
