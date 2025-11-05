package audio;

import java.util.Arrays;



public class Test3 {
	static int[] mix0 = new int[] {3, 2, 66, 3, 2, 66, 3, 2, 66, 3, 2, 66, 4, 2, 50, 4, 2, 50, 4, 3, 75, 4, 4, 100, 4, 4, 100, 4, 4, 100, 4, 4, 100, 4, 4, 100, 4, 4, 100, 4, 3, 75, 4, 2, 50, 4, 1, 25, 4, 1, 25, 4, 5, 125, 6, 20, 333, 8, 35, 437, 11, 52, 472, 14, 66, 471, 17, 69, 405, 20, 69, 345, 24, 67, 279, 27, 64, 237, 30, 60, 200, 30, 57, 190, 28, 55, 196, 25, 50, 200, 23, 45, 195, 22, 41, 186, 21, 37, 176, 21, 35, 166, 20, 30, 150, 18, 28, 155, 18, 28, 155, 19, 39, 205, 23, 54, 234, 29, 70, 241, 42, 81, 192, 58, 82, 141, 77, 83, 107, 93, 83, 89, 96, 81, 84, 96, 81, 84, 84, 80, 95, 72, 80, 111, 63, 80, 126, 63, 80, 126, 65, 80, 123, 66, 80, 121, 66, 80, 121, 61, 79, 129, 56, 78, 139, 51, 78, 152, 48, 78, 162, 46, 78, 169, 44, 78, 177, 42, 77, 183, 38, 74, 194, 34, 72, 211, 30, 66, 220, 29, 62, 213, 28, 58, 207, 28, 53, 189, 27, 52, 192, 25, 49, 196, 25, 45, 180, 23, 43, 186, 21, 35, 166, 20, 28, 140, 18, 23, 127, 17, 18, 105, 15, 16, 106, 13, 16, 123, 10, 16, 160, 8, 13, 162, 6, 10, 166, 4, 6, 150, 4, 3, 75, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 2, 1, 50, 2, 1, 50};
	static int[] mix1 = new int[] {4, 1, 25, 4, 1, 25, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 4, 1, 25, 4, 1, 25, 4, 1, 25, 4, 1, 25, 3, 1, 33, 3, 1, 33, 4, 1, 25, 5, 1, 20, 6, 7, 116, 8, 18, 225, 10, 35, 350, 13, 51, 392, 17, 62, 364, 19, 67, 352, 21, 67, 319, 23, 67, 291, 25, 64, 256, 25, 61, 244, 25, 55, 220, 25, 51, 204, 23, 44, 191, 23, 40, 173, 23, 33, 143, 22, 30, 136, 20, 25, 125, 17, 25, 147, 17, 30, 176, 17, 44, 258, 25, 63, 252, 40, 83, 207, 61, 87, 142, 82, 87, 106, 95, 86, 90, 95, 85, 89, 88, 83, 94, 77, 82, 106, 70, 81, 115, 68, 80, 117, 68, 80, 117, 71, 81, 114, 72, 82, 113, 72, 82, 113, 71, 83, 116, 67, 83, 123, 63, 83, 131, 60, 83, 138, 59, 82, 138, 58, 81, 139, 58, 80, 137, 58, 79, 136, 56, 79, 141, 52, 80, 153, 48, 80, 166, 45, 80, 177, 43, 80, 186, 43, 78, 181, 42, 76, 180, 40, 72, 180, 37, 68, 183, 34, 62, 182, 30, 56, 186, 27, 48, 177, 25, 42, 168, 20, 37, 185, 16, 28, 175, 11, 23, 209, 8, 15, 187, 6, 8, 133, 5, 4, 80, 5, 2, 40, 5, 2, 40, 4, 1, 25, 4, 1, 25, 4, 1, 25, 4, 1, 25, 4, 1, 25, 4, 1, 25, 5, 2, 40, 5, 3, 60, 5, 4, 80, 4, 4, 100, 4, 4, 100, 4, 3, 75, 5, 3, 60, 5, 3, 60, 5, 3, 60, 4, 3, 75, 4, 3, 75, 4, 3, 75, 4, 2, 50, 5, 2, 40, 5, 2, 40, 5, 2, 40};
	static int[] mix2 = new int[] {2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 3, 1, 33, 4, 3, 75, 6, 16, 266, 8, 37, 462, 11, 55, 500, 16, 69, 431, 21, 69, 328, 25, 67, 268, 27, 64, 237, 29, 63, 217, 30, 61, 203, 32, 56, 175, 32, 53, 165, 31, 51, 164, 26, 47, 180, 23, 43, 186, 20, 40, 200, 19, 32, 168, 18, 29, 161, 16, 29, 181, 15, 25, 166, 14, 25, 178, 14, 29, 207, 14, 37, 264, 18, 50, 277, 26, 63, 242, 41, 70, 170, 60, 73, 121, 78, 74, 94, 91, 77, 84, 91, 77, 84, 86, 77, 89, 72, 77, 106, 56, 74, 132, 45, 71, 157, 38, 69, 181, 35, 67, 191, 33, 67, 203, 33, 67, 203, 31, 67, 216, 30, 67, 223, 28, 65, 232, 26, 65, 250, 25, 62, 248, 25, 62, 248, 25, 60, 240, 25, 60, 240, 25, 60, 240, 25, 60, 240, 26, 62, 238, 27, 63, 233, 27, 63, 233, 27, 62, 229, 26, 62, 238, 26, 59, 226, 25, 57, 228, 24, 55, 229, 22, 51, 231, 20, 47, 235, 18, 40, 222, 15, 31, 206, 13, 25, 192, 10, 22, 220, 7, 17, 242, 5, 14, 280, 3, 8, 266, 2, 3, 150, 2, 1, 50, 2, 1, 50, 2, 1, 50, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 2, 1, 50, 2, 1, 50};
	static int[] mix3 = new int[] {3, 1, 33, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 3, 7, 233, 4, 14, 350, 6, 19, 316, 7, 33, 471, 10, 45, 450, 12, 54, 450, 15, 73, 486, 17, 78, 458, 19, 78, 410, 19, 78, 410, 20, 71, 355, 20, 64, 320, 20, 58, 290, 20, 53, 265, 19, 48, 252, 18, 45, 250, 17, 42, 247, 16, 38, 237, 16, 37, 231, 16, 31, 193, 15, 31, 206, 15, 35, 233, 15, 48, 320, 21, 64, 304, 34, 77, 226, 55, 84, 152, 77, 84, 109, 92, 84, 91, 92, 84, 91, 85, 82, 96, 75, 81, 108, 67, 80, 119, 63, 80, 126, 59, 82, 138, 53, 82, 154, 46, 81, 176, 42, 79, 188, 40, 78, 195, 39, 78, 200, 39, 80, 205, 38, 80, 210, 37, 80, 216, 37, 80, 216, 35, 78, 222, 33, 78, 236, 30, 73, 243, 28, 70, 250, 27, 66, 244, 26, 62, 238, 26, 60, 230, 25, 57, 228, 24, 53, 220, 24, 49, 204, 23, 45, 195, 22, 36, 163, 20, 30, 150, 17, 25, 147, 14, 19, 135, 11, 18, 163, 9, 15, 166, 7, 11, 157, 5, 10, 200, 5, 9, 180, 5, 9, 180, 4, 9, 225, 4, 6, 150, 3, 4, 133, 3, 2, 66, 3, 2, 66, 3, 2, 66, 3, 3, 100, 3, 3, 100, 4, 4, 100, 4, 4, 100, 4, 4, 100, 4, 4, 100, 4, 4, 100, 4, 4, 100, 4, 4, 100, 4, 4, 100, 4, 4, 100, 4, 5, 125, 4, 5, 125, 5, 5, 100, 5, 5, 100, 6, 5, 83};
	static int[] mix4 = new int[] {3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 5, 8, 160, 7, 18, 257, 11, 30, 272, 15, 46, 306, 18, 54, 300, 24, 59, 245, 28, 62, 221, 32, 62, 193, 32, 61, 190, 31, 61, 196, 29, 56, 193, 27, 53, 196, 27, 49, 181, 27, 45, 166, 25, 43, 172, 23, 38, 165, 21, 31, 147, 21, 31, 147, 21, 32, 152, 24, 42, 175, 31, 57, 183, 42, 70, 166, 60, 78, 130, 79, 81, 102, 93, 82, 88, 93, 82, 88, 92, 81, 88, 81, 81, 100, 68, 77, 113, 57, 73, 128, 49, 69, 140, 43, 64, 148, 41, 64, 156, 39, 64, 164, 38, 63, 165, 38, 63, 165, 38, 63, 165, 37, 60, 162, 35, 60, 171, 33, 57, 172, 31, 52, 167, 28, 49, 175, 27, 47, 174, 27, 45, 166, 27, 44, 162, 28, 44, 157, 28, 43, 153, 28, 41, 146, 29, 38, 131, 30, 34, 113, 31, 28, 90, 31, 25, 80, 30, 23, 76, 28, 21, 75, 24, 21, 87, 21, 20, 95, 18, 20, 111, 15, 20, 133, 12, 19, 158, 9, 14, 155, 6, 11, 183, 4, 5, 125, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33};
	
	static int[] array0 = new int[] {3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 6, 8, 11, 14, 17, 20, 24, 27, 30, 30, 28, 25, 23, 22, 21, 21, 20, 18, 18, 19, 23, 29, 42, 58, 77, 93, 96, 96, 84, 72, 63, 63, 65, 66, 66, 61, 56, 51, 48, 46, 44, 42, 38, 34, 30, 29, 28, 28, 27, 25, 25, 23, 21, 20, 18, 17, 15, 13, 10, 8, 6, 4, 4, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2, 2};
	static int[] array1 = new int[] {4, 4, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 3, 3, 4, 5, 6, 8, 10, 13, 17, 19, 21, 23, 25, 25, 25, 25, 23, 23, 23, 22, 20, 17, 17, 17, 25, 40, 61, 82, 95, 95, 88, 77, 70, 68, 68, 71, 72, 72, 71, 67, 63, 60, 59, 58, 58, 58, 56, 52, 48, 45, 43, 43, 42, 40, 37, 34, 30, 27, 25, 20, 16, 11, 8, 6, 5, 5, 5, 4, 4, 4, 4, 4, 4, 5, 5, 5, 4, 4, 4, 5, 5, 5, 4, 4, 4, 4, 5, 5, 5};
	static int[] array2 = new int[] {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 4, 6, 8, 11, 16, 21, 25, 27, 29, 30, 32, 32, 31, 26, 23, 20, 19, 18, 16, 15, 14, 14, 14, 18, 26, 41, 60, 78, 91, 91, 86, 72, 56, 45, 38, 35, 33, 33, 31, 30, 28, 26, 25, 25, 25, 25, 25, 25, 26, 27, 27, 27, 26, 26, 25, 24, 22, 20, 18, 15, 13, 10, 7, 5, 3, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2, 2};
	static int[] array3 = new int[] {3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 4, 6, 7, 10, 12, 15, 17, 19, 19, 20, 20, 20, 20, 19, 18, 17, 16, 16, 16, 15, 15, 15, 21, 34, 55, 77, 92, 92, 85, 75, 67, 63, 59, 53, 46, 42, 40, 39, 39, 38, 37, 37, 35, 33, 30, 28, 27, 26, 26, 25, 24, 24, 23, 22, 20, 17, 14, 11, 9, 7, 5, 5, 5, 4, 4, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 5, 5, 6};
	static int[] array4 = new int[] {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2, 2, 2, 2, 2, 3, 3, 3, 3, 5, 7, 11, 15, 18, 24, 28, 32, 32, 31, 29, 27, 27, 27, 25, 23, 21, 21, 21, 24, 31, 42, 60, 79, 93, 93, 92, 81, 68, 57, 49, 43, 41, 39, 38, 38, 38, 37, 35, 33, 31, 28, 27, 27, 27, 28, 28, 28, 29, 30, 31, 31, 30, 28, 24, 21, 18, 15, 12, 9, 6, 4, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3};
	

	static int posCounter;
	static int negCounter;
	static int middleCounter;

	static int posStart;
	static int negStart;
	static int midStart;
	
	static int heightValue;
	static int heightPos;
	
	static int lines[] = new int[200];
	static int linesCounter = 0;

	static int[] retArray;
	static int retArrayCounter;
	static int lastModifyIndex = 0;
	static boolean endwave;
	
	static VoiceRecognitionSequence[] sequences;
	static int sequenceCounter;
	
	public static void main(String[] args) {

		int[] thisArray = array0;
		
		buildSections(thisArray);
		//selectSections(lines);
		buildRawSequences(lines,thisArray);

	}
	
	public static void buildSections(int[] input) {
				
		for(int i = 0; i < input.length-1; i++) {
			
			if(input[i+1] > input[i])  {	
				
				posCounter++;			
				
				if(posCounter == 1) {
					posStart = i;
					
					if(middleCounter > 0) {
						addTolines(0,midStart,middleCounter);
					}
					
					if(negCounter > 0) {
						addTolines(-1,negStart,negCounter);
					}
				}
			}
			
			if(input[i+1] < input[i])  {	
				
				negCounter++;		
				
				if(negCounter == 1) {
					
					negStart = i;
					
					if(posCounter > 0) {
						addTolines(1,posStart,posCounter);
					}
					
					if(middleCounter > 0) {
						addTolines(0,midStart,middleCounter);
					}
				}
			}
			
			if(input[i+1] == input[i])  {	
				
				middleCounter++;		
				
				if(middleCounter == 1) {
					midStart = i;
					
					if(posCounter > 0) {
						addTolines(1,posStart,posCounter);
					}
					
					if(negCounter > 0) {
						addTolines(-1,negStart,negCounter);
					}
				}
			}
		}
		//System.out.println("buildSequences "+Arrays.toString(lines));
	}
	
	public static void addTolines(int direction, int start, int length) {
		
		lines[linesCounter++] = direction;
		lines[linesCounter++] = start;
		lines[linesCounter++] = length;
		
		if(direction == -1)
			negCounter = 0;
		
		if(direction == 0)
			middleCounter = 0;
		
		if(direction == 1)
			posCounter = 0;
		
		System.out.println("addTolines direction: "+direction+", start: "+start+", length: "+length);
	}
		
	public static void buildRawSequences(int[] input , int[] baseArray ) {
		
		retArray = new int[input.length];
		retArrayCounter = 0;
		lastModifyIndex = 0;
		int[] check;
		
		for(int i = 0; i < input.length-5; i= i+3 ) {
			
			
			if(input[i] == 0 && input[i+1] == 0 && input[i+2] == 0)
				break;
			
			System.out.println("\n");

			check = checkNext(baseArray,input[i+1]);

			if(check == null)
				continue;
			
			System.out.println("Check 0.0 i "+i + ", direction:" +input[i]+ ", index: " +input[i+1]+ ", input[i+2] " +input[i+2]+", Array  " +Arrays.toString(check) );
			
			//1
			if(check[0] >= check[1] && check[0]>= check[2] && input[i] == 0 && check[3] >= check[4] && check[3]>= check[5]) { 
				System.out.println("1.1 i "+i + ", direction:" +input[i]+ ", index: " +input[i+1]+ ", input[i+2] " +input[i+2]);
				
				changeLastIndexValue(retArray, retArrayCounter-1, input, i+2);
				//addToRetArray(input[i],input[i+1],input[i+2],0);
				continue;
			}
			
			if(check[0] >= check[1] && check[0]>= check[2] && input[i] == 0 && check[5] >= check[3] && check[5]>= check[4]) { 
				System.out.println("1.2 i "+i + ", direction:" +input[i]+ ", index: " +input[i+1]+ ", input[i+2] " +input[i+2]);
				
				addToRetArray(input[i],input[i+1],input[i+2],0);
				continue;
			}
			
			if(check[0] >= check[1] && check[0]>= check[2] && input[i] == 1 && check[3] >= check[4] && check[3]>= check[5]) { 
				System.out.println("1.3 i "+i + ", direction:" +input[i]+ ", index: " +input[i+1]+ ", input[i+2] " +input[i+2]);
				
				addToRetArray(input[i],input[i+1],input[i+2],0);
				continue;
			}
			
			if(check[0] >= check[1] && check[0]>= check[2] && input[i] == 1 && check[4] >= check[3] && check[4]>= check[5]) { 
				System.out.println("1.4 i "+i + ", direction:" +input[i]+ ", index: " +input[i+1]+ ", input[i+2] " +input[i+2]);
				
				addToRetArray(input[i],input[i+1],input[i+2],0);
				continue;
			}
			
			//2
			if(check[1] >= check[0] && check[1]>= check[2] && input[i] == -1 && check[4] >= check[3] && check[4]>= check[5]) { 
				System.out.println("2.1 i "+i + ", direction:" +input[i]+ ", index: " +input[i+1]+ ", input[i+2] " +input[i+2]);
				
				changeLastIndexValue(retArray, retArrayCounter-1, input, i+2);
				//addToRetArray(input[i],input[i+1],input[i+2],0);
				continue;
			}
			
			if(check[1] >= check[0] && check[1]>= check[2] && input[i] == -1 && check[5] >= check[3] && check[5]>= check[4]) { 
				System.out.println("2.2 i "+i + ", direction:" +input[i]+ ", index: " +input[i+1]+ ", input[i+2] " +input[i+2]);
				
				changeLastIndexValue(retArray, retArrayCounter-1, input, i+2);
				//addToRetArray(input[i],input[i+1],input[i+2],0);
				continue;
			}
			
			if(check[1] >= check[0] && check[1]>= check[2] && input[i] == 1 && check[3] >= check[4] && check[3]>= check[5]) { 
				System.out.println("2.3 i "+i + ", direction:" +input[i]+ ", index: " +input[i+1]+ ", input[i+2] " +input[i+2]);
				
				addToRetArray(input[i],input[i+1],input[i+2],0);
				continue;
			}
			
			if(check[1] >= check[0] && check[1]>= check[2] && input[i] == 1 && check[4] >= check[3] && check[4]>= check[5]) { 
				System.out.println("2.4 i "+i + ", direction:" +input[i]+ ", index: " +input[i+1]+ ", input[i+2] " +input[i+2]);
				
				addToRetArray(input[i],input[i+1],input[i+2],0);
				continue;
			}
			
			//3
			if(check[2] >= check[0] && check[2]>= check[1] && input[i] == -1 && check[4] >= check[3] && check[4]>= check[5]) { 
				System.out.println("3.1 i "+i + ", direction:" +input[i]+ ", index: " +input[i+1]+ ", input[i+2] " +input[i+2]);
				
				changeLastIndexValue(retArray, retArrayCounter-1, input, i+2);
				//addToRetArray(input[i],input[i+1],input[i+2],0);
				continue;
			}
			
			if(check[2] >= check[0] && check[2]>= check[1] && input[i] == -1 && check[5] >= check[3] && check[5]>= check[4]) { 
				System.out.println("3.2 i "+i + ", direction:" +input[i]+ ", index: " +input[i+1]+ ", input[i+2] " +input[i+2]);
				
				changeLastIndexValue(retArray, retArrayCounter-1, input, i+2);
				//addToRetArray(input[i],input[i+1],input[i+2],0);
				continue;
			}
			
			if(check[2] >= check[0] && check[2]>= check[1] && input[i] == 0 && check[3] >= check[4] && check[3]>= check[5]) { 
				System.out.println("3.3 i "+i + ", direction:" +input[i]+ ", index: " +input[i+1]+ ", input[i+2] " +input[i+2]);
				
				changeLastIndexValue(retArray, retArrayCounter-1, input, i+2);
				//addToRetArray(input[i],input[i+1],input[i+2],0);
				continue;
			}
			
			if(check[2] >= check[0] && check[2]>= check[1] && input[i] == 0 && check[5] >= check[3] && check[5]>= check[4]) { 
				System.out.println("3.4 i "+i + ", direction:" +input[i]+ ", index: " +input[i+1]+ ", input[i+2] " +input[i+2]);
				
				changeLastIndexValue(retArray, retArrayCounter-1, input, i+2);
				//addToRetArray(input[i],input[i+1],input[i+2],0);
				continue;
			}
			

		}
		
		System.out.println("buildRawSequences: " +Arrays.toString(retArray));
	}
	
	public static void addToRetArray(int direction, int index, int length, int correction ) {
		
		retArray[retArrayCounter++] = direction;
		retArray[retArrayCounter++] = index;
		retArray[retArrayCounter++] = length + correction;
		
		lastModifyIndex = index +length + correction;
		endwave = false;
		System.out.println("addToretArray direction: "+ direction+", index: "+  index+", length: "+  length+", correction: "+  correction
				+", retArray direction: " + retArray[retArrayCounter-3] +", retArray index: "+ retArray[retArrayCounter-2]);
	}
	
	public static void changeLastIndexValue(int[] inputArray1, int index1, int[] inputArray2, int index2) {
		
		if(retArrayCounter == 0)
			return;
		
		if(endwave == false) {
			System.out.println("changeLastIndexValue startIdex: " +inputArray1[index1-1]+", i: "+inputArray2[index2-1]+", Item old value: "+inputArray1[index1]+", Item new: " +(inputArray2[index2] + inputArray1[index1]) );
			inputArray1[index1] = inputArray2[index2] + inputArray1[index1];
			lastModifyIndex = inputArray1[index1-1] + inputArray1[index1];System.out.println("lastModifyIndex "+lastModifyIndex);
		}
	}
	
	public static int[] checkNext(int[] input, int index) {
		
		int i;
		int loopLength = 10;
		int backPos = 0;
		int backNeg = 0;
		int backEnd = index-loopLength;
		int frontPos = 0;
		int frontNeg = 0;
		int frontEnd = index +loopLength;
				
		if(backEnd < 0 || frontEnd > input.length-2)
			return null;
		
		for(i = index ; i > backEnd; i--) {
			
			if(i < 0)
				break;
			
			if(input[i+1]- input[i] > 0)
				backPos++;
			
			if(input[i+1]- input[i] < 0)
				backNeg++;
		}
		
		for(i = index; i < frontEnd; i++) {
			
			if(i > input.length-2)
				break;
				
			if(input[i+1] - input[i] > 0)	
				frontPos++;

			if(input[i+1] - input[i] < 0)	
				frontNeg++;			
		
		}
		
		return new int[] {backNeg,(loopLength - backPos - backNeg), backPos, frontNeg,( loopLength - frontPos - frontNeg ),frontPos};
	}
}
