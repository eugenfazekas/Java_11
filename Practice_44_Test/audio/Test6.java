package audio;

import java.util.Arrays;

public class Test6 {
	
	private static float fakeBuffer;
	private static float fakeCounter;
	private static int lowerSampleStart;
	private static int upperSampleStart;
	private static int lowerDbStart;
	private static int upperDbStart;
	private static int[] borders = new int[2];		
	private static int lengthtBuffer;


	private static int multiBuffer;
	private static int multiBufferCounter = 0;
	
	private static int lengthPercent1;
	private static int lengthPercent2;
	private static float lengthPercentTotal;
	
	private static float multiLengthPercentTotal;
	
	private static int dbSecondRightIndex = 0;

	private static int lastRightDbIndex  = 0;
	private static int basePercent = 20;
	
	private static int lengthResult;
	private static int angle1;
	private static int angle2 ;
	private static int angleResult1;
	private static int angleResult2; 
	private static int array2Test1;
	private static int array2Test2;
	private static float array2TestPercentTotal;
	private static float array2TestBuffer;
	
	private static int[][] heights;
	private static int[][] testHeights1 = new int[100][];
	private static int[][] testHeights2 = new int[100][];
	
	
	private static int[][][] heightsArray = new int[5][][];
	private static int heightsArrayCounter;
	private static int[] startInfo;

	private static int[][] rebuilded;
	
	private static int start2;
	private static int end2;
	private static boolean positive;
	
	private static int[] angles ;
	private static int counter;
	
	private static int[] returnArray;
	private static int result;
	
//	private static int i;
//	private static int j;
//	private static int positiveCounter;
//	private static int equalCounter;
//	private static int negativeCounter;
//	private static boolean start;
//	
//	private static int checkLength;
//	private static int sequenceLength;
//	private static boolean nextNegativeCheck;
//	
//	private static int lastnegativeEdgeIndex;
//	private static int lastnegativeEdgeIndexValue;
//	private static int sequenceCounter;
//	
//	private static int[] points;
//	private static int pointsCounter;
	
	private static int[] secondary;
	private static int   secondaryCounter;
	
	static int[] mix0 = new int[] {3, 2, 66, 3, 2, 66, 3, 2, 66, 3, 2, 66, 4, 2, 50, 4, 2, 50, 4, 3, 75, 4, 4, 100, 4, 4, 100, 4, 4, 100, 4, 4, 100, 4, 4, 100, 4, 4, 100, 4, 3, 75, 4, 2, 50, 4, 1, 25, 4, 1, 25, 4, 5, 125, 6, 20, 333, 8, 35, 437, 11, 52, 472, 14, 66, 471, 17, 69, 405, 20, 69, 345, 24, 67, 279, 27, 64, 237, 30, 60, 200, 30, 57, 190, 28, 55, 196, 25, 50, 200, 23, 45, 195, 22, 41, 186, 21, 37, 176, 21, 35, 166, 20, 30, 150, 18, 28, 155, 18, 28, 155, 19, 39, 205, 23, 54, 234, 29, 70, 241, 42, 81, 192, 58, 82, 141, 77, 83, 107, 93, 83, 89, 96, 81, 84, 96, 81, 84, 84, 80, 95, 72, 80, 111, 63, 80, 126, 63, 80, 126, 65, 80, 123, 66, 80, 121, 66, 80, 121, 61, 79, 129, 56, 78, 139, 51, 78, 152, 48, 78, 162, 46, 78, 169, 44, 78, 177, 42, 77, 183, 38, 74, 194, 34, 72, 211, 30, 66, 220, 29, 62, 213, 28, 58, 207, 28, 53, 189, 27, 52, 192, 25, 49, 196, 25, 45, 180, 23, 43, 186, 21, 35, 166, 20, 28, 140, 18, 23, 127, 17, 18, 105, 15, 16, 106, 13, 16, 123, 10, 16, 160, 8, 13, 162, 6, 10, 166, 4, 6, 150, 4, 3, 75, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 2, 1, 50, 2, 1, 50};
	static int[] mix1 = new int[] {4, 1, 25, 4, 1, 25, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 4, 1, 25, 4, 1, 25, 4, 1, 25, 4, 1, 25, 3, 1, 33, 3, 1, 33, 4, 1, 25, 5, 1, 20, 6, 7, 116, 8, 18, 225, 10, 35, 350, 13, 51, 392, 17, 62, 364, 19, 67, 352, 21, 67, 319, 23, 67, 291, 25, 64, 256, 25, 61, 244, 25, 55, 220, 25, 51, 204, 23, 44, 191, 23, 40, 173, 23, 33, 143, 22, 30, 136, 20, 25, 125, 17, 25, 147, 17, 30, 176, 17, 44, 258, 25, 63, 252, 40, 83, 207, 61, 87, 142, 82, 87, 106, 95, 86, 90, 95, 85, 89, 88, 83, 94, 77, 82, 106, 70, 81, 115, 68, 80, 117, 68, 80, 117, 71, 81, 114, 72, 82, 113, 72, 82, 113, 71, 83, 116, 67, 83, 123, 63, 83, 131, 60, 83, 138, 59, 82, 138, 58, 81, 139, 58, 80, 137, 58, 79, 136, 56, 79, 141, 52, 80, 153, 48, 80, 166, 45, 80, 177, 43, 80, 186, 43, 78, 181, 42, 76, 180, 40, 72, 180, 37, 68, 183, 34, 62, 182, 30, 56, 186, 27, 48, 177, 25, 42, 168, 20, 37, 185, 16, 28, 175, 11, 23, 209, 8, 15, 187, 6, 8, 133, 5, 4, 80, 5, 2, 40, 5, 2, 40, 4, 1, 25, 4, 1, 25, 4, 1, 25, 4, 1, 25, 4, 1, 25, 4, 1, 25, 5, 2, 40, 5, 3, 60, 5, 4, 80, 4, 4, 100, 4, 4, 100, 4, 3, 75, 5, 3, 60, 5, 3, 60, 5, 3, 60, 4, 3, 75, 4, 3, 75, 4, 3, 75, 4, 2, 50, 5, 2, 40, 5, 2, 40, 5, 2, 40};
	static int[] mix2 = new int[] {2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 3, 1, 33, 4, 3, 75, 6, 16, 266, 8, 37, 462, 11, 55, 500, 16, 69, 431, 21, 69, 328, 25, 67, 268, 27, 64, 237, 29, 63, 217, 30, 61, 203, 32, 56, 175, 32, 53, 165, 31, 51, 164, 26, 47, 180, 23, 43, 186, 20, 40, 200, 19, 32, 168, 18, 29, 161, 16, 29, 181, 15, 25, 166, 14, 25, 178, 14, 29, 207, 14, 37, 264, 18, 50, 277, 26, 63, 242, 41, 70, 170, 60, 73, 121, 78, 74, 94, 91, 77, 84, 91, 77, 84, 86, 77, 89, 72, 77, 106, 56, 74, 132, 45, 71, 157, 38, 69, 181, 35, 67, 191, 33, 67, 203, 33, 67, 203, 31, 67, 216, 30, 67, 223, 28, 65, 232, 26, 65, 250, 25, 62, 248, 25, 62, 248, 25, 60, 240, 25, 60, 240, 25, 60, 240, 25, 60, 240, 26, 62, 238, 27, 63, 233, 27, 63, 233, 27, 62, 229, 26, 62, 238, 26, 59, 226, 25, 57, 228, 24, 55, 229, 22, 51, 231, 20, 47, 235, 18, 40, 222, 15, 31, 206, 13, 25, 192, 10, 22, 220, 7, 17, 242, 5, 14, 280, 3, 8, 266, 2, 3, 150, 2, 1, 50, 2, 1, 50, 2, 1, 50, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 2, 1, 50, 2, 1, 50};
	static int[] mix3 = new int[] {3, 1, 33, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 3, 7, 233, 4, 14, 350, 6, 19, 316, 7, 33, 471, 10, 45, 450, 12, 54, 450, 15, 73, 486, 17, 78, 458, 19, 78, 410, 19, 78, 410, 20, 71, 355, 20, 64, 320, 20, 58, 290, 20, 53, 265, 19, 48, 252, 18, 45, 250, 17, 42, 247, 16, 38, 237, 16, 37, 231, 16, 31, 193, 15, 31, 206, 15, 35, 233, 15, 48, 320, 21, 64, 304, 34, 77, 226, 55, 84, 152, 77, 84, 109, 92, 84, 91, 92, 84, 91, 85, 82, 96, 75, 81, 108, 67, 80, 119, 63, 80, 126, 59, 82, 138, 53, 82, 154, 46, 81, 176, 42, 79, 188, 40, 78, 195, 39, 78, 200, 39, 80, 205, 38, 80, 210, 37, 80, 216, 37, 80, 216, 35, 78, 222, 33, 78, 236, 30, 73, 243, 28, 70, 250, 27, 66, 244, 26, 62, 238, 26, 60, 230, 25, 57, 228, 24, 53, 220, 24, 49, 204, 23, 45, 195, 22, 36, 163, 20, 30, 150, 17, 25, 147, 14, 19, 135, 11, 18, 163, 9, 15, 166, 7, 11, 157, 5, 10, 200, 5, 9, 180, 5, 9, 180, 4, 9, 225, 4, 6, 150, 3, 4, 133, 3, 2, 66, 3, 2, 66, 3, 2, 66, 3, 3, 100, 3, 3, 100, 4, 4, 100, 4, 4, 100, 4, 4, 100, 4, 4, 100, 4, 4, 100, 4, 4, 100, 4, 4, 100, 4, 4, 100, 4, 4, 100, 4, 5, 125, 4, 5, 125, 5, 5, 100, 5, 5, 100, 6, 5, 83};
	static int[] mix4 = new int[] {3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 5, 8, 160, 7, 18, 257, 11, 30, 272, 15, 46, 306, 18, 54, 300, 24, 59, 245, 28, 62, 221, 32, 62, 193, 32, 61, 190, 31, 61, 196, 29, 56, 193, 27, 53, 196, 27, 49, 181, 27, 45, 166, 25, 43, 172, 23, 38, 165, 21, 31, 147, 21, 31, 147, 21, 32, 152, 24, 42, 175, 31, 57, 183, 42, 70, 166, 60, 78, 130, 79, 81, 102, 93, 82, 88, 93, 82, 88, 92, 81, 88, 81, 81, 100, 68, 77, 113, 57, 73, 128, 49, 69, 140, 43, 64, 148, 41, 64, 156, 39, 64, 164, 38, 63, 165, 38, 63, 165, 38, 63, 165, 37, 60, 162, 35, 60, 171, 33, 57, 172, 31, 52, 167, 28, 49, 175, 27, 47, 174, 27, 45, 166, 27, 44, 162, 28, 44, 157, 28, 43, 153, 28, 41, 146, 29, 38, 131, 30, 34, 113, 31, 28, 90, 31, 25, 80, 30, 23, 76, 28, 21, 75, 24, 21, 87, 21, 20, 95, 18, 20, 111, 15, 20, 133, 12, 19, 158, 9, 14, 155, 6, 11, 183, 4, 5, 125, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33};
	static int[] mix5 = new int[] {6, 1, 16, 6, 1, 16, 6, 1, 16, 6, 1, 16, 6, 1, 16, 5, 1, 20, 5, 1, 20, 5, 1, 20, 4, 1, 25, 4, 1, 25, 4, 1, 25, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 4, 1, 25, 7, 1, 14, 11, 5, 45, 18, 20, 111, 27, 34, 125, 37, 46, 124, 46, 51, 110, 51, 51, 100, 54, 48, 88, 54, 44, 81, 54, 40, 74, 52, 37, 71, 50, 31, 62, 48, 26, 54, 47, 21, 44, 46, 18, 39, 44, 18, 40, 40, 18, 45, 35, 18, 51, 27, 18, 66, 22, 19, 86, 22, 29, 131, 30, 39, 130, 46, 55, 119, 66, 62, 93, 81, 64, 79, 90, 66, 73, 91, 66, 72, 91, 66, 72, 90, 65, 72, 87, 62, 71, 83, 58, 69, 77, 52, 67, 72, 47, 65, 70, 42, 60, 70, 37, 52, 71, 35, 49, 73, 32, 43, 74, 31, 41, 75, 28, 37, 76, 27, 35, 77, 27, 35, 77, 24, 31, 76, 22, 28, 75, 20, 26, 74, 18, 24, 74, 18, 24, 74, 18, 24, 74, 18, 24, 74, 18, 24, 73, 18, 24, 72, 18, 25, 69, 19, 27, 66, 19, 28, 63, 18, 28, 59, 17, 28, 55, 16, 29, 49, 15, 30, 44, 15, 34, 39, 15, 38, 35, 15, 42, 31, 15, 48, 27, 15, 55, 24, 15, 62, 21, 15, 71, 18, 14, 77, 15, 10, 66, 13, 7, 53, 11, 3, 27, 9, 1, 11, 7, 1, 14, 6, 1, 16, 4, 1, 25, 4, 1, 25, 4, 1, 25, 4, 1, 25, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33};
	static int[] mix6 = new int[] {2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 3, 1, 33, 3, 1, 33, 3, 1, 33, 4, 1, 25, 4, 1, 25, 4, 1, 25, 6, 1, 16, 9, 5, 55, 15, 17, 113, 22, 27, 122, 31, 36, 116, 39, 39, 100, 46, 39, 84, 52, 38, 73, 56, 38, 67, 56, 38, 67, 53, 36, 67, 48, 36, 75, 42, 35, 83, 36, 35, 97, 32, 35, 109, 27, 29, 107, 23, 24, 104, 20, 19, 95, 16, 14, 87, 15, 14, 93, 14, 14, 100, 13, 15, 115, 13, 15, 115, 15, 22, 146, 22, 32, 145, 33, 44, 133, 47, 59, 125, 60, 67, 111, 70, 72, 102, 74, 74, 100, 77, 74, 96, 80, 73, 91, 85, 70, 82, 91, 70, 76, 91, 69, 75, 91, 68, 74, 83, 68, 81, 73, 64, 87, 63, 62, 98, 56, 58, 103, 51, 55, 107, 48, 54, 112, 48, 52, 108, 46, 52, 113, 44, 52, 118, 41, 51, 124, 38, 50, 131, 37, 48, 129, 37, 47, 127, 37, 45, 121, 35, 44, 125, 33, 43, 130, 31, 43, 138, 29, 43, 148, 28, 40, 142, 28, 38, 135, 28, 30, 107, 29, 24, 82, 31, 21, 67, 32, 19, 59, 32, 19, 59, 31, 18, 58, 29, 17, 58, 27, 15, 55, 24, 15, 62, 23, 15, 65, 20, 15, 75, 17, 15, 88, 15, 15, 100, 13, 14, 107, 11, 10, 90, 10, 7, 70, 8, 3, 37, 7, 1, 14, 6, 1, 16, 5, 1, 20, 4, 1, 25, 4, 1, 25, 4, 1, 25, 4, 1, 25, 5, 1, 20, 5, 1, 20, 5, 1, 20, 5, 1, 20, 5, 1, 20, 6, 1, 16, 6, 1, 16, 6, 1, 16, 6, 1, 16, 6, 1, 16, 6, 1, 16, 6, 1, 16, 7, 1, 14, 7, 1, 14, 7, 1, 14, 6, 1, 16, 6, 1, 16, 5, 1, 20, 4, 1, 25, 3, 1, 33, 3, 1, 33, 2, 1, 50, 2, 1, 50};
	static int[] mix7 = new int[] {9, 1, 11, 9, 1, 11, 9, 1, 11, 8, 1, 12, 8, 1, 12, 8, 1, 12, 8, 1, 12, 8, 1, 12, 7, 1, 14, 7, 1, 14, 6, 1, 16, 6, 1, 16, 5, 1, 20, 4, 1, 25, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 6, 1, 16, 9, 1, 11, 12, 14, 116, 16, 29, 181, 18, 45, 250, 21, 61, 290, 22, 63, 286, 22, 63, 286, 22, 63, 286, 19, 47, 247, 17, 31, 182, 15, 24, 160, 15, 24, 160, 16, 25, 156, 17, 33, 194, 17, 33, 194, 18, 33, 183, 18, 33, 183, 18, 27, 150, 17, 23, 135, 16, 17, 106, 15, 12, 80, 15, 12, 80, 16, 28, 175, 21, 50, 238, 31, 68, 219, 47, 89, 189, 68, 89, 130, 86, 86, 100, 94, 85, 90, 94, 82, 87, 86, 78, 90, 79, 75, 94, 79, 73, 92, 83, 73, 87, 85, 75, 88, 85, 77, 90, 80, 80, 100, 74, 83, 112, 70, 85, 121, 67, 87, 129, 65, 88, 135, 63, 88, 139, 61, 88, 144, 58, 88, 151, 52, 88, 169, 44, 87, 197, 35, 87, 248, 26, 87, 334, 21, 87, 414, 19, 87, 457, 18, 87, 483, 18, 87, 483, 17, 76, 447, 16, 64, 400, 14, 42, 300, 12, 19, 158, 11, 9, 81, 11, 1, 9, 10, 1, 10, 9, 1, 11, 9, 1, 11, 9, 1, 11, 9, 1, 11, 9, 1, 11, 10, 1, 10, 10, 1, 10, 11, 1, 9, 12, 1, 8, 12, 1, 8, 13, 1, 7, 13, 1, 7, 14, 1, 7, 14, 1, 7, 15, 1, 6, 15, 1, 6, 14, 1, 7, 14, 1, 7, 13, 1, 7, 12, 1, 8, 11, 1, 9, 9, 1, 11, 7, 1, 14, 6, 1, 16, 4, 1, 25, 4, 1, 25, 4, 1, 25, 6, 1, 16, 8, 1, 12, 9, 1, 11, 10, 1, 10, 11, 1, 9, 12, 1, 8, 13, 1, 7, 13, 1, 7, 14, 1, 7};
	static int[] mix8 = new int[] {2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 3, 1, 33, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 2, 1, 50, 3, 3, 100, 5, 10, 200, 10, 19, 190, 17, 28, 164, 25, 36, 144, 32, 39, 121, 38, 41, 107, 42, 42, 100, 46, 43, 93, 50, 44, 88, 53, 45, 84, 55, 45, 81, 55, 44, 80, 55, 44, 80, 53, 42, 79, 48, 40, 83, 42, 39, 92, 35, 37, 105, 28, 36, 128, 22, 34, 154, 16, 29, 181, 12, 24, 200, 9, 18, 200, 8, 13, 162, 8, 11, 137, 8, 10, 125, 10, 10, 100, 12, 10, 83, 16, 19, 118, 25, 27, 108, 38, 37, 97, 55, 47, 85, 71, 51, 71, 84, 56, 66, 91, 60, 65, 94, 62, 65, 94, 62, 65, 89, 60, 67, 83, 57, 68, 78, 54, 69, 73, 53, 72, 70, 52, 74, 64, 51, 79, 58, 50, 86, 51, 48, 94, 46, 47, 102, 43, 47, 109, 42, 48, 114, 41, 49, 119, 41, 49, 119, 41, 49, 119, 41, 48, 117, 40, 48, 120, 38, 48, 126, 35, 46, 131, 31, 42, 135, 28, 37, 132, 25, 33, 132, 24, 30, 125, 22, 27, 122, 20, 26, 130, 19, 24, 126, 19, 24, 126, 19, 24, 126, 20, 25, 125, 22, 26, 118, 22, 26, 118, 22, 22, 100, 19, 19, 100, 15, 14, 93, 12, 10, 83, 9, 9, 100, 8, 6, 75, 8, 5, 62, 10, 3, 30, 11, 3, 27, 12, 3, 25, 12, 3, 25, 11, 3, 27, 10, 3, 30, 8, 3, 37, 7, 2, 28, 5, 2, 40, 3, 2, 66};
	
	static int[] amp0 = new int[] {3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 6, 8, 11, 14, 17, 20, 24, 27, 30, 30, 28, 25, 23, 22, 21, 21, 20, 18, 18, 19, 23, 29, 42, 58, 77, 93, 96, 96, 84, 72, 63, 63, 65, 66, 66, 61, 56, 51, 48, 46, 44, 42, 38, 34, 30, 29, 28, 28, 27, 25, 25, 23, 21, 20, 18, 17, 15, 13, 10, 8, 6, 4, 4, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2, 2};
	static int[] amp1 = new int[] {4, 4, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 3, 3, 4, 5, 6, 8, 10, 13, 17, 19, 21, 23, 25, 25, 25, 25, 23, 23, 23, 22, 20, 17, 17, 17, 25, 40, 61, 82, 95, 95, 88, 77, 70, 68, 68, 71, 72, 72, 71, 67, 63, 60, 59, 58, 58, 58, 56, 52, 48, 45, 43, 43, 42, 40, 37, 34, 30, 27, 25, 20, 16, 11, 8, 6, 5, 5, 5, 4, 4, 4, 4, 4, 4, 5, 5, 5, 4, 4, 4, 5, 5, 5, 4, 4, 4, 4, 5, 5, 5};
	
	static int[] freq0 = new int[] {2, 2, 2, 2, 2, 2, 3, 4, 4, 4, 4, 4, 4, 3, 2, 1, 1, 5, 20, 35, 52, 66, 69, 69, 67, 64, 60, 57, 55, 50, 45, 41, 37, 35, 30, 28, 28, 39, 54, 70, 81, 82, 83, 83, 81, 81, 80, 80, 80, 80, 80, 80, 80, 79, 78, 78, 78, 78, 78, 77, 74, 72, 66, 62, 58, 53, 52, 49, 45, 43, 35, 28, 23, 18, 16, 16, 16, 13, 10, 6, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
	static int[] freq1 = new int[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 7, 18, 35, 51, 62, 67, 67, 67, 64, 61, 55, 51, 44, 40, 33, 30, 25, 25, 30, 44, 63, 83, 87, 87, 86, 85, 83, 82, 81, 80, 80, 81, 82, 82, 83, 83, 83, 83, 82, 81, 80, 79, 79, 80, 80, 80, 80, 78, 76, 72, 68, 62, 56, 48, 42, 37, 28, 23, 15, 8, 4, 2, 2, 1, 1, 1, 1, 1, 1, 2, 3, 4, 4, 4, 3, 3, 3, 3, 3, 3, 3, 2, 2, 2, 2};
	

	static String test = new String("0:[0, 2, 7, 40, 14, 17, 19, 2, 26, 40, 33, 17]_1:[0, 26, 1, 63, 2, 77, 3, 82, 4, 84, 5, 83, 6, 80, 7, 63, -20, 0, -20, 0, -20, 0, -20, 0, -20, 0, -20, 0]_2:[0, 1]_3:[2]_4:[3]_5:[3]_6:[3]_7:[4]_8:[4]_9:[4]_10:[4]_11:[4]_12:[4]_13:[5]_14:[5]_15:[5]_16:[5]_17:[5]_18:[5, 13]_19:[5, 13]_20:[5, 12]_21:[5, 12]_22:[6, 12]_23:[6, 12]_24:[6, 12]_25:[6, 11]_26:[6, 11]_27:[6, 11]_28:[6, 11]_29:[6, 11]_30:[6, 11]_31:[6, 11]_32:[6, 10]_33:[7, 10]_34:[7, 10]_35:[7, 10]_36:[7, 10]_37:[7, 10]_38:[7, 10]_39:[7, 10]_40:[7, 9]_41:[8, 9]_42:[8, 9]_43:[8, 9]_44:[8, 8, 9, 9]&0:[0, 17, 21, 25, 41, 2, 35, 17, 56, 25, 76, 2]_1:[0, 26, 1, 56, 2, 75, 3, 82, 4, 85, 5, 86, 6, 86, 7, 85, 8, 78, 9, -78, 10, -85, 11, -86, 12, -85, 13, -81, 14, -71, 15, -68, 16, -68, 17, -68, 18, -68, 19, -63, 20, -63, 21, -63, 22, -68, 23, -68, 24, -63, 25, -56, 26, -45, 27, -26, 28, -26, 29, -26, 30, -26, 31, -45, 32, -26, 33, -26, 34, -45, 35, -45, 36, -45, 37, -45, 38, -45, 39, -45, 40, -26]_3:[-1, 41]_4:[-1, 40]_5:[-1, 39]_6:[-1, 38]_7:[-1, 37]_8:[-1, 36]_9:[-1, 33, 34, 34]_10:[-1, 33]_11:[-1, 30, 31, 31]_12:[-1, 28, 29, 29]_13:[-1, 28]_14:[-1, 27]_15:[-1, 26]_16:[-1, 26]_17:[0, 1, 24]_18:[2, 24]_19:[3, 23]_20:[3, 23]_21:[4, 23]_22:[4, 22]_23:[4, 22]_24:[4, 21]_25:[4, 21]_26:[4, 20]_27:[5, 20]_28:[5, 19]_29:[5, 19]_30:[5, 18]_31:[5, 18]_32:[5, 18]_33:[5, 17]_34:[5, 17]_35:[5, 16]_36:[5, 16]_37:[6, 16]_38:[6, 15]_39:[6, 15]_40:[6, 14]_41:[6, 14]_42:[6, 14]_43:[6, 14]_44:[6, 13]_45:[6, 13]_46:[6, 13]_47:[6, 13]_48:[6, 13]_49:[6, 13]_50:[6, 13]_51:[7, 13]_52:[7, 13]_53:[7, 12]_54:[7, 12]_55:[7, 12]_56:[7, 12]_57:[7, 12]_58:[7, 12]_59:[7, 12]_60:[7, 12]_61:[7, 12]_62:[7, 12]_63:[7, 12]_64:[7, 12]_65:[7, 12]_66:[7, 12]_67:[7, 11]_68:[8, 11]_69:[8, 11]_70:[8, 11]_71:[8, 11]_72:[8, 11]_73:[8, 11]_74:[8, 11]_75:[8, 11]_76:[8, 11]_77:[8, 11]_78:[8, 11]_79:[8, 11]_80:[8, 11]_81:[8, 11]_82:[8, 11]_83:[8, 11]_84:[9, 10]_85:[9, 10]_86:[9, 10]_87:[9, 10]_88:[9, 10]_89:[9, 10]_90:[9, 10]_91:[9, 10]_92:[9, 10]_93:[9, 9, 10, 10]_;0:[0, 1, 8, 63, 15, 15, 20, 1, 28, 63, 35, 15]_1:[0, 81, 1, 85, 2, 86, 3, 86, 4, 83, 5, 63, 6, 56, 7, 26, 8, -63, -21, 0, -21, 0, -21, 0, -21, 0, -21, 0, -21, 0]_2:[2]_3:[2]_4:[2]_5:[2]_6:[2]_7:[2]_8:[2]_9:[2]_10:[2]_11:[2]_12:[2]_13:[2]_14:[2]_15:[3]_16:[3, 14]_17:[3, 14]_18:[3, 14]_19:[3, 14]_20:[3, 13]_21:[3, 13]_22:[3, 13]_23:[3, 13]_24:[3, 13]_25:[3, 13]_26:[3, 13]_27:[3, 13]_28:[3, 12]_29:[4, 12]_30:[4, 12]_31:[4, 12]_32:[4, 12]_33:[4, 12]_34:[4, 12]_35:[4, 12]_36:[4, 12]_37:[4, 12]_38:[4, 12]_39:[4, 11]_40:[4, 11]_41:[4, 11]_42:[4, 11]_43:[4, 11]_44:[5, 11]_45:[5, 11]_46:[5, 11]_47:[5, 11]_48:[5, 11]_49:[5, 11]_50:[5, 11]_51:[5, 11]_52:[5, 10]_53:[5, 10]_54:[5, 10]_55:[5, 10]_56:[5, 10]_57:[5, 10]_58:[5, 10]_59:[6, 10]_60:[6, 9]_61:[7, 9]_62:[7, 9]_63:[8, 8, 9, 9]&0:[0, 15, 20, 77, 40, 1, 35, 15, 55, 77, 75, 1]_1:[0, 45, 1, 79, 2, 84, 3, 85, 4, 86, 5, 84, 6, 78, 7, 68, 8, 26, 9, 26, 10, 26, 11, -45, 12, -45, 13, -45, 14, -56, 15, -26, 16, 0, 17, 0, 18, 45, 19, 63, 20, 45, 21, -45, 22, -77, 23, -81, 24, -82, 25, -81, 26, -71, 27, -26, 28, 0, 29, -26, 30, -78, 31, -83, 32, -82, 33, -81, 34, -78, 35, -71, 36, -75, 37, -74, 38, -68, 39, -45]_2:[-1, 40]_3:[-1, 40]_4:[-1, 39]_5:[-1, 39]_6:[-1, 39]_7:[-1, 38]_8:[-1, 38]_9:[-1, 38]_10:[-1, 38]_11:[-1, 37]_12:[-1, 37]_13:[-1, 37]_14:[-1, 37]_15:[0, 1, 35]_16:[2, 35]_17:[2, 34]_18:[3, 34]_19:[3, 34]_20:[3, 34]_21:[3, 34]_22:[3, 34]_23:[3, 34]_24:[3, 34]_25:[3, 33]_26:[3, 33]_27:[4, 33]_28:[4, 33]_29:[4, 33]_30:[4, 33]_31:[4, 32]_32:[4, 32]_33:[4, 32]_34:[4, 32]_35:[4, 32]_36:[4, 32]_37:[4, 32]_38:[4, 32]_39:[5, 32]_40:[5, 31]_41:[5, 31]_42:[5, 31]_43:[5, 31]_44:[5, 31]_45:[5, 31]_46:[5, 31]_47:[5, 31]_48:[5, 31]_49:[5, 28, 29, 29, 30, 30]_50:[5, 27]_51:[5, 26]_52:[5, 26]_53:[5, 26]_54:[6, 26]_55:[6, 26]_56:[6, 25]_57:[6, 25]_58:[6, 25]_59:[6, 25]_60:[6, 25]_61:[6, 25]_62:[6, 25]_63:[6, 25]_64:[6, 24]_65:[6, 24]_66:[6, 24]_67:[6, 24]_68:[6, 24]_69:[7, 24]_70:[7, 24]_71:[7, 23]_72:[7, 23]_73:[7, 23]_74:[7, 23]_75:[8, 16, 17, 17, 18, 18, 19, 23]_76:[8, 15, 20, 20, 23]_77:[8, 14, 20, 20, 23]_78:[8, 13, 14, 14, 21, 21, 22]_79:[9, 9, 10, 12, 21, 21, 21, 22, 22]_80:[11, 11, 12, 12]_");
	
	public static void main(String[] args) {
		
		
		testHeights1 = buildHeights(amp0,freq0, 36 , 79); 		
     	testHeights2 = buildHeights(amp1,freq1, 37 , 78);
//   	    testHeights1 = buildHeights(amp0, freq0, 17 , 35); 		
//        testHeights2 = buildHeights(amp1, freq1,15 , 35);
		testHeights1 = heightsRebuild(testHeights1);
		testHeights2 = heightsRebuild(testHeights2);		
		compare(testHeights1,testHeights2);
		
//		String[] a_f = test.split(";");
//		check(buildTwoDAreaArray(a_f[0]));
//		getLengthPercentDiff(39,63,20);
	}
	
	public static int[][][] buildTwoDAreaArray(String input) {

		String[] substrs1;
		String[] substrs2;
		String[] substrs3;
		String substr;
		int[] array;
		int[][][] threeDArray = new int[2][][];

		
		String[] sequences = input.split("&");
		threeDArray = new int[sequences.length][][];
		System.out.println("Seq lenght: "+sequences.length);
		
		for(int i = 0; i < sequences.length; i++) {
			
			substrs1 = sequences[i].split("_");
			substrs2 = substrs1[substrs1.length-1].split(":");			
			threeDArray[i] = new int[Integer.valueOf(substrs2[0])+1][];
			System.out.println("\ni length " +i + ", "+threeDArray[i].length );
			
			for(int j = 0; j < substrs1.length ; j++) { 
				
				substrs2 = substrs1[j].split(":");			
				substr = substrs2[1].substring(1, substrs2[1].length()-1);			
				substrs3 = substr.split(", ");
				
				array = new int[substrs3.length];
				
				for( int k = 0; k < substrs3.length; k++) {

					array[k] = Integer.valueOf(substrs3[k]);
				}
					threeDArray[i][Integer.valueOf(substrs2[0])] = array;
					System.out.println("Array i: "+i+ ", j: "+ Integer.valueOf(substrs2[0])+ ", Array "+ Arrays.toString(array) + ", Check: "+substr);
			}

		}
	
		return threeDArray;
	}

	
	static void check (int[][][] check) {
		
		for(int i = 0; i < check.length; i++) 
			for(int j = 0; j < check[i].length; j++) 
					System.out.println("i: "+i + ", j: " +j+ ", Array " + Arrays.toString(check[i][j]) );	
	}
	
	private static void compare(int[][] input,  int[][] db) {
			
		multiBufferCounter = 0;
		lengthtBuffer = 0;
		multiBuffer = 0;
		fakeBuffer = 1;
		fakeCounter = 1;

		borders[0] = lowerSampleStart <  lowerDbStart ? lowerSampleStart : lowerDbStart;
		borders[1] = upperSampleStart >  upperDbStart ? upperSampleStart : upperDbStart;

		System.out.println("input[0]"+Arrays.toString(input[0])+", db[0]: "+Arrays.toString(db[0]));
		System.out.println("\nw.border 0: "+borders[0]+", w.border 1: "+borders[1]);

		for(int i = borders[0]+2; i < 100; i++ ) {
			
			System.out.println("\n0.0 i: " + i + ", Sample Array i-1: " +Arrays.toString(input[i-1])+ ", Sample Array i: " +Arrays.toString(input[i])+ ", Sample Array i+1: " +Arrays.toString(input[i+1])
			+ ", db Array i-1: " +Arrays.toString(db[i-1])+ ", db Array i: " +Arrays.toString(db[i])+ ", db Array i+1: " +Arrays.toString(db[i+1]));
			
			lengthPercent1 = 0;
			lengthPercent2 = 0;
			lengthPercentTotal = 0;
			array2Test1 = 0;
			array2Test2 = 0;
			multiLengthPercentTotal = 0;
			dbSecondRightIndex = 0;

			if(input[i] == null || db[i] == null) {
								
				if(input[i] == null && db[i] == null && multiBufferCounter > 3) {
					System.out.println("BREAK !!!!");
					break;
				}
				
				if((input[i] != null && db[i] == null) || (input[i] == null && db[i] != null)) {
				
					addToFakeBuffer(multiBufferCounter,1);
					System.out.println("0.1 lengthBufferCounter: " + (multiBufferCounter + fakeBuffer) +", lengthtBuffer:  "+ lengthtBuffer 
							+ ",           L_AVG: " + (lengthtBuffer / (multiBufferCounter + fakeBuffer)));
					
					System.out.println("0.2 lengthBufferCounter: " + (multiBufferCounter + fakeBuffer) +", array2TestBuffer:  "+ array2TestBuffer 
							+ ", S_AVG: " + (array2TestBuffer / (multiBufferCounter + fakeBuffer)));
								
					System.out.println("0.3 angleLengthBufferCounter: " + (multiBufferCounter+ fakeBuffer) +", multiBuffer:  "+ multiBuffer 
							+ ",         LS_AVG: " + (multiBuffer / (multiBufferCounter + fakeBuffer)));	
				}	
				continue;
			}
			
			if(input[i].length > 1 && input[i][0] != input[i][1]+1 ) {
				
				if(input[i][0] != -1 ) {
									
					if(db[i][0] != -1 && db[i].length > 1) {
						
						lengthPercent1 = getLengthPercentDiff(input[0][4] - db[i][0], db[0][4] - input[i][0],basePercent);
						lengthPercent2 = getLengthPercentDiff(input[i][input[i].length-1], db[i][db[i].length-1],basePercent);
						lengthPercentTotal = ((float) ((float) lengthPercent1 / 100) * ((float) lengthPercent2 / 100));
						lengthtBuffer += lengthPercentTotal * 100;
						

						if(input[i][0] > 0  && db[i][0] > 0) {
							
							array2Test1 = getLengthPercentDiff(input[1][input[i][0]*2-1], db[1][db[i][0]*2-1],basePercent);
							
							dbSecondRightIndex = db[i][db[i].length - 1] *2 - 1 < db[1].length * 2 ? db[1][ db[i][db[i].length - 1] *2 - 1] : db[1][ db[1][db[i].length - 1] *2 - 1];
							array2Test2 = getLengthPercentDiff(input[1][ input[i][input[i].length - 1] *2 - 1], dbSecondRightIndex, 45);
							
							
							array2TestPercentTotal = ((float) ((float) array2Test1 / 100) * ((float) array2Test2 / 100));
							array2TestBuffer += array2TestPercentTotal * 100;
							
							multiLengthPercentTotal = ((float) ((float) lengthPercentTotal) * ((float) array2TestPercentTotal));	
							multiBuffer +=  multiLengthPercentTotal *100;
						}
						
						multiBufferCounter++;
						
						System.out.println("1.1 lengthPercent1: "+lengthPercent1 +", lengthPercent2: " +lengthPercent2 + ", lengthPercentTotal: "+lengthPercentTotal
								+ ", lengthBufferCounter counters: " + multiBufferCounter +", lengthtBuffer:  "+ lengthtBuffer 
								+ ", L_AVG: " + (lengthtBuffer / (multiBufferCounter + fakeBuffer)));
						
						System.out.println("1.2 array2Test1: "+array2Test1 +", array2Test2: " +array2Test2 + ", array2TestPercentTotal: "+array2TestPercentTotal
								+ ", lengthBufferCounter counters: " + multiBufferCounter +", array2TestBuffer:  "+ array2TestBuffer 
								+ ", S_AVG: " + (array2TestBuffer / (multiBufferCounter + fakeBuffer)));
						
						System.out.println("1.3 multiLengthPercentTotal: "+multiLengthPercentTotal +", multiBuffer: " +multiBuffer
								+ ", LS_AVG: " + (multiBuffer / (multiBufferCounter + fakeBuffer)));
											
						continue;
					}
					
					if(db[i][0] == -1) 
						addToFakeBuffer(multiBufferCounter,1);
				}				
			}	
							
			if(input[i][0] == -1) {
				
				if(db[i].length > 1) {
					
					lengthPercent2 = getLengthPercentDiff(input[i][input[i].length-1], db[i][db[i].length-1],basePercent);				
					lengthtBuffer += lengthPercent2;
					
					array2Test2 = getLengthPercentDiff(input[1][ input[i][input[i].length - 1] *2 - 1], db[1][ db[i][db[i].length - 1] *2 - 1], 45);
					array2TestBuffer += array2Test2;
					
					multiLengthPercentTotal = ((float) ((float) lengthPercent2 / 100) * ((float) array2Test2 / 100));
					multiBuffer += multiLengthPercentTotal * 100;
					multiBufferCounter++;
			
					System.out.println("3.1 lengthPercent1: "+lengthPercent1
							+ ", lengthBufferCounter counters: " + (multiBufferCounter + fakeBuffer) +", lengthtBuffer:  "+ lengthtBuffer 
							+ ", L_AVG: " + (lengthtBuffer / (multiBufferCounter + fakeBuffer)));
					
					System.out.println("3.2 array2Test2: "+array2Test2
							+ ", lengthBufferCounter counters: " + (multiBufferCounter) +", array2TestBuffer:  "+ array2TestBuffer 
							+ ", S_AVG: " + (array2TestBuffer / (multiBufferCounter + fakeBuffer)));
					
					System.out.println("3.3 multiLengthPercentTotal: "+multiLengthPercentTotal +", multiBuffer: " +multiBuffer
							+ ", LS_AVG: " + (multiBuffer / (multiBufferCounter + fakeBuffer)));
				}
				
				if(db[i].length == 1) {
					addToFakeBuffer(multiBufferCounter, 3);
				}
				continue;
			}

			
			if(input[i].length == 1 || input[i][0] == input[i][1]+1 ) {
				
				if(db[i].length == 1  || (db[i].length >= 1 && db[i][0] != -1 )) {
					
					lengthPercent1 = getLengthPercentDiff(input[0][4] - db[i][0], db[0][4] - input[i][0],basePercent);
					lengthtBuffer += lengthPercent1;
						
					dbSecondRightIndex = db[i][db[i].length - 1] *2 - 1 < db[1].length * 2 ? db[1][ db[i][db[i].length - 1] *2 - 1] : db[1][ db[1][db[i].length - 1] *2 - 1];
					array2Test1 = getLengthPercentDiff(input[1][input[i][0]*2-1], dbSecondRightIndex, basePercent);
					array2TestBuffer += array2Test1;
							
					multiLengthPercentTotal = ((float) ((float) lengthPercent1 / 100) * ((float) array2Test1 / 100));	
					multiBuffer +=  multiLengthPercentTotal * 100;
					
					multiBufferCounter++;

					System.out.println("4.1 lengthPercent1: "+lengthPercent1
							+ ", lengthBufferCounter counters: " + (multiBufferCounter + fakeBuffer) +", lengthtBuffer:  "+ lengthtBuffer 
							+ ", L_AVG: " + (lengthtBuffer / (multiBufferCounter + fakeBuffer)));
					
					System.out.println("4.2 array2Test1: "+array2Test1
							+ ", lengthBufferCounter counters: " + (multiBufferCounter) +", array2TestBuffer:  "+ array2TestBuffer 
							+ ", S_AVG: " + (array2TestBuffer / (multiBufferCounter + fakeBuffer)));
					
					System.out.println("4.3 multiLengthPercentTotal: "+multiLengthPercentTotal +", multiBuffer: " +multiBuffer
							+ ", LS_AVG: " + (multiBuffer / (multiBufferCounter + fakeBuffer)));
				}
					if(( db[i].length > 1 && db[i][0] == -1)) 
						addToFakeBuffer(multiBufferCounter, 4);
					continue;
			}						
		}
	}
	
	private static void addToFakeBuffer(int lengthCounter, int source) {
		
		fakeBuffer +=  fakeCounter *((float)(lengthCounter + 1)/ 50);
		fakeCounter *=1.05;		
		
		System.out.println("\n ADDToFAKEBuffer fakeCounter: "+ fakeCounter + ", fakeBuffer: "+fakeBuffer  + ", Source: "+source+ "\n");
	}
	
	private static int getLengthPercentDiff(int input1, int input2, int baseHundredPercent) {
					

		lengthResult =  (((baseHundredPercent - Math.abs(input1 - input2)) *100 ) / baseHundredPercent);
		lengthResult = lengthResult > 0 ?  lengthResult : 0;

		System.out.println("getLengthPercentDiff input1: "+ input1+ ", input2: "+input2 + ", onePercent: " +baseHundredPercent
				+ ", lengthResult: "+ lengthResult );
		
		return lengthResult;
	}
	
	private static int[][] heightsRebuild(int[][] input) {
		
		rebuilded = new int[input.length][];
		
		rebuilded[0] = Arrays.copyOf(input[0], input[0].length);
		rebuilded[0][0] = 0;
		rebuilded[0][2] = input[0][2] - input[0][0];
		rebuilded[0][4] = input[0][4] - input[0][0];
		
		System.out.println(Arrays.toString(input[0]));
		System.out.println(Arrays.toString(rebuilded[0]));
		for(int i = 1; i < 100; i++) {
			
			if(input[i] != null) {

				if( i > 2 ) {
					
					if(input[i].length == 1) {
						rebuilded[0][2] =  input[i][0] - input[0][0];
						rebuilded[0][8] =  input[i][0];
						rebuilded[0][3] = i; rebuilded[0][9] = i;
					}
					
					if(input[i][0] == -1) {
						rebuilded[0][2] =  input[i][input[i].length-1] - input[0][0];
						rebuilded[0][8] =  input[i][input[i].length-1];
						rebuilded[0][3] = i; rebuilded[0][9] = i;
					}
					
					if(input[i].length > 1 &&  input[i][0] != -1) {
						rebuilded[0][2] =  ((input[i][input[i].length-1] + input[i][0])/2 ) - input[0][0];
						rebuilded[0][8] =  ((input[i][input[i].length-1] + input[i][0])/2 );
						rebuilded[0][3] = i; rebuilded[0][9] = i;
					}
					
				}
				
				rebuilded[i] = new int[input[i].length];
			
				for(int j = 0; j< input[i].length; j++) {
					

					
					if(i == 1 || i == 2) {
						if( j % 2 == 0 && j < rebuilded[i].length) {
							rebuilded[i][j] = input[i][j] - input[0][0]-1;
							rebuilded[i][j+1] = input[i][j+1];
						}
						continue;
					}
					
					if(input[i][j] == -1)
						rebuilded[i][j] = -1;
					
					if(input[i][j] != -1)
						rebuilded[i][j] = input[i][j] - input[0][0];
				} 		
			}
		}
		
		for(int i  = 0; i < rebuilded.length; i++)
			
			if(rebuilded[i] != null)
				System.out.println("Rebuilded i: "+i +", Array: "+ Arrays.toString(rebuilded[i]));
		
		return rebuilded;
	}

	private static int[][] buildHeights(int[] input1,int[] input2,  int start1, int end1) {
		
		System.out.println("buildHeights start: " +start1 +", end: "+end1);
		angles = new int[(end1-start1)*2+2];
		counter = 0;
		
		startInfo = new int[] {start1, input1[start1]};
		heights = new int[100][];
		heights[0] = new int[] {start1-1,input1[start1],((start1+end1)/2),input1[(start1+end1)/2],end1, input1[end1],
				start1-1,input1[start1],((start1+end1)/2),input1[(start1+end1)/2],end1, input1[end1]};
		
		secondary = new int[(end1-start1)*2+2];
		secondaryCounter = 0;
		for(int i = start1; i < end1+1; i++) {
			
			secondary[secondaryCounter++] = i;
			secondary[secondaryCounter++] = input2[i];
			
			start2 = 0;
			end2 = 0 ;
			
			positive = false;
			
//			if(i < angles.length) {
//				angles[counter++] = i;
//				angles[counter++] = getDegree(input1[i+1] - input1[i-1], 2);
//			}
			
			if(input1[i-1] == input1[i]) {
				
				System.out.println("i: " +i +", add equal!" + ", height: "+ input1[i]);
				heights[input1[i-1]] = addToArray(heights[input1[i-1]] ,i-1, input1[i-1]);
				heights[input1[i]] = addToArray(heights[input1[i]] ,i, input1[i]);
				System.out.println("array i: " + i + ", arr: " + Arrays.toString(heights[input1[i]]) );		
			}
			
			if(input1[i+1] - input1[i] > 0 || input1[i+1] - input1[i] < 0 ) {
				
				start2 = input1[i] < input1[i+1] ? input1[i]+1 : input1[i+1]+1; 
				end2 = input1[i] < input1[i+1] ? input1[i+1]+1 : input1[i]+1; 
				positive =  input1[i] < input1[i+1] ? true : false; 
				System.out.println("i: " +i +", i+1: " +(i+1) + ", positive: " + positive+", start2: "+start2 +", end2: "+(end2) );
				
				for(int j = start2; j < end2 ; j++) {
					
					if(heights[j] != null && positive )
						heights[j] = addToArray(heights[j] ,i+1, j);
					
					if(heights[j] != null && !positive )
						heights[j] = addToArray(heights[j] ,i, j);
					
					else					
						heights[j] = addToArray(heights[j] ,i+1, j);
				}
			}
		}
		
		//heights[1] = angles;
		heights[1] = secondary;
		
		for(int k= 0; k <heights.length ; k++)
			if(heights[k] != null)
				System.out.println("k: "+ k+", Array "+Arrays.toString(heights[k]));
		
		return heights;
	}
	
	private static int[] addToArray(int[] array, int index, int height) {
		
		if(array == null) {
			
			if(startInfo[1]> height)
				return new int[] {-1,index};
				
			System.out.println("addToHeight index: "+ index + ", First index: "+ index + ", height: "+height);
				return new int[] {index};
		}
		
		returnArray = new int[array.length +1];
		
		for(int i = 0; i < array.length; i++)
			returnArray[i] = array[i];
		
		returnArray[array.length] = index;
		System.out.println("addToHeight index: "+ index + ", Arr: "+Arrays.toString(returnArray));
		return returnArray;
	}
}
