package temp;

import java.util.Arrays;

public class Temp5 {
	
	static int[] igen1amp = new int[] {37, 36, 32, 28, 24, 20, 19, 19, 19, 19, 18, 15, 13, 10, 8, 7, 7, 10, 16, 24, 34, 48, 63, 76, 80, 75, 64, 54, 48, 46, 45, 44, 43, 43, 41, 40, 38, 37, 36, 34, 34, 32, 31, 30, 29, 29, 30, 30, 30, 29, 29, 28, 28, 28, 27, 27, 27, 27, 27, 26, 24, 21, 18,};
	static int[] igen1freq = new int[] {50, 52, 53, 54, 52, 48, 43, 38, 34, 32, 31, 29, 22, 15, 8, 3, 12, 25, 38, 52, 58, 63, 69, 74, 76, 74, 71, 68, 66, 66, 65, 64, 62, 60, 58, 56, 55, 55, 56, 57, 58, 56, 54, 52, 49, 48, 47, 46, 46, 47, 48, 48, 48, 47, 46, 46, 45, 44, 43, 41, 40, 39, 37,};	
	static int[] igen2amp = new int[] {29, 33, 36, 38, 40, 40, 37, 31, 26, 21, 21, 22, 24, 25, 24, 21, 18, 15, 12, 11, 10, 13, 16, 26, 41, 60, 77, 84, 80, 71, 61, 56, 55, 55, 52, 48, 45, 42, 41, 42, 42, 43, 43, 43, 43, 42, 40, 39, 38, 37, 37, 37, 38, 38, 38, 38, 38, 37, 37, 36, 37, 38, 39, 39, 38, 35, 32, 28, 25, 22, 18, 15, 12, 11, 11, 11, 11, 11, 10, 10, 9, 8, 6,};
	static int[] igen2freq = new int[] {44, 45, 47, 48, 50, 52, 53, 52, 48, 43, 38, 34, 32, 31, 30, 30, 23, 16, 9, 3, 3, 14, 26, 42, 58, 63, 68, 69, 68, 66, 64, 62, 61, 60, 58, 55, 53, 50, 48, 47, 46, 46, 46, 47, 48, 49, 50, 50, 50, 50, 50, 50, 50, 49, 48, 47, 46, 46, 45, 44, 43, 41, 40, 39, 37, 36, 35, 34, 34, 34, 26, 18, 10, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,};
	static int[] nemAmp = new int[] {6, 5, 5, 5, 6, 7, 8, 9, 9, 9, 10, 12, 16, 21, 28, 36, 44, 51, 57, 62, 66, 71, 75, 79, 83, 86, 89, 91, 92, 92, 90, 87, 83, 79, 78, 78, 79, 81, 81, 82, 83, 84, 86, 87, 88, 88, 86, 82, 76, 70, 63, 59, 57, 57, 58, 59, 61, 62, 63, 63, 63, 64, 63, 62, 59, 55, 51, 48, 44, 40, 35, 30, 25, 21, 18, 16, 14, 12, 10, 9, 7, 7, 6,};	
	static int[] nemFreq = new int[] {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 11, 20, 29, 39, 40, 42, 44, 45, 46, 46, 45, 44, 43, 41, 40, 39, 38, 40, 42, 44, 46, 46, 46, 47, 48, 50, 53, 55, 57, 58, 58, 58, 58, 58, 58, 58, 57, 56, 55, 54, 53, 52, 51, 49, 48, 47, 45, 44, 43, 42, 42, 41, 40, 38, 36, 35, 33, 32, 31, 30, 30, 30, 30, 30, 23, 16, 9, 3, 3, 3, 3, 3, 3,};
	
	
	static int i;
	static int[] points;
	static int[] temp;
	static int pointsCounter;
	static int lastAddedIndex;

	public static void main(String[] args) {

		System.out.println(Math.toDegrees(Math.atan2(2,2)));
//		temp = nemFreq;
//		getFirstPoint(temp);
//		buildPoints(temp);

	}

	public static void buildPoints(int[] input) {
		
		points = new int[50];
		pointsCounter = 0;
	
		for(; i < input.length-1; i++) {
					
			if(( input[i-1] > input[i] && input[i+1] >= input[i]) || 
				(input[i-1] < input[i] && input[i+1] <= input[i]) ) {
				
				if(i - lastAddedIndex > 5) {
					
					points[pointsCounter++] = i ;
					points[pointsCounter++] = input[i];
					lastAddedIndex = i;
				}
			}
		}
		
		temp = new int[pointsCounter];
		
		for(i = 0; i < pointsCounter; i++) 
			temp[i] = points[i];
		
		points = temp;
		System.out.println("Array: "+Arrays.toString(points));
	}
	
	public static void getFirstPoint(int[] input) {
		
		for(i = 1; i < input.length; i++) 			
			if((input[i+1] > input[i-1]+10) || (input[i+4] > input[i-1]+15))				
					break;
		
		System.out.println("i: "+i);
	}
}
