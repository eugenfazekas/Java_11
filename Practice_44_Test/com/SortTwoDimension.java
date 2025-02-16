package com;

import java.util.Arrays;

public class SortTwoDimension {
	
	public static int[] oneDArray = new int[] {6,2,7,8,1,9,5,0,4,3};
	public static int[][] twoDarray = new int[][] {{3,3,3,3,3,3},{5,5,5,5,5,5},{7,7,7,7,7,7,7},{1,1,1,1,1,1,1,1},{6,6,6,6,6,6,6},{8,8,8,8,8,8,8,8,8},{2,2,2,2,2,2,2,2},
		{3,3,3,3,3,3},{5,5,5,5,5,5},{7,7,7,7,7,7,7},{1,1,1,1,1,1,1,1},{6,6,6,6,6,6,6},{8,8,8,8,8,8,8,8,8},{2,2,2,2,2,2,2,2}};
	public static int[][] result;

	public static void main(String[] args) {

		sort(twoDarray,0);
		
		for(int[] arr :result )
			System.out.println(Arrays.toString(arr));
		
		
		//sort(oneDArray);
		//System.out.println(Arrays.toString(sort(oneDArray)));
	}
	
	private static int[][] sort(int[][] input, int sortParam) {
		
		
    	int highest[] = input[0] ;
    	int highestpos = 0;
    	int counter = 0;
		result = input;
		int temp[] ;
		
		for(int i = 0; i < result.length; i++) {
			
        System.out.println("i: "+i);
				if(result[i][result[i].length-sortParam-1] > highest[highest.length-sortParam-1]) {	
						highest = result[i];
						highestpos = i;
					}
					
					if(i == result.length-1) {
						System.out.println(", i: "+i +", highest: "+highest +", counter: "+counter +  ", array[counter]: "+ result[counter]);
						result[highestpos] = result[counter];
						result[counter++] = highest;
						i = counter;
						highest = result[counter];
						highestpos = counter;
					}
		}
		
		return result;
	}
	
    public static int[] sort(int[] inputArray) {
    	

    	int highest = 0;
    	int highestpos = 0;
    	int counter = 0;
    	int[] array = inputArray;

		
		for (int i = 0; i < array.length; i++) {

			if(array[i] > highest) {
				highest = array[i];
				highestpos = i;
			}
			
			if(i == array.length-1) {
				System.out.println(", i: "+i +", highest: "+highest +", counter: "+counter +  ", array[counter]: "+ array[counter]);
				array[highestpos] = array[counter];
				array[counter++] = highest;
				i = counter;
				highest = array[counter];
				highestpos = counter;
			}
		}
			return array;
    }

}
