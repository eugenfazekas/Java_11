package com;

import java.util.Arrays;

public class MainTest3 {


	static int[] test = new int[]{30,30,30,35,50,40,40,60,70,10,50,40,40};
	
	public static void main(String[] args) {
			
		//addToarray(test,5);
		
		removeSpikes(test);
	}
	
	public static void removeSpikes(int [] inputArray) {
		
		int limit = 0;
		
		for(int i = 1; i < inputArray.length; i++) {
			
			if(inputArray[i-1] <= inputArray[i]-limit && inputArray[i+1] <= inputArray[i]-limit) 	
				inputArray[i] = inputArray[i-1] > inputArray[i+1] ? inputArray[i-1] : inputArray[i+1];
				
			if(inputArray[i-1] >= inputArray[i]+limit && inputArray[i+1] >= inputArray[i]+limit) 			
				inputArray[i] = inputArray[i-1] < inputArray[i+1] ? inputArray[i-1] : inputArray[i+1];

		}
		
		System.out.println(Arrays.toString(inputArray));
	}
	
	public static void addToarray(int[] arrToadd , int  value) {
		
		int[] newArray = new int[arrToadd.length+1];
		
		for(int i = 0; i < arrToadd.length; i++) {
			arrToadd [i] = arrToadd[i]+2;
		}
		newArray[arrToadd.length] = value;
		arrToadd = new int[2];
	}


}
