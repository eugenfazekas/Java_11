package audio;

import java.util.Arrays;

public class Test8 {

	public static int lowLimitFrequency = 5;
	static int[] amp0 = new int[] {3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 6, 8, 11, 14, 17, 20, 24, 27, 30, 30, 28, 25, 23, 22, 21, 21, 20, 18, 18, 19, 23, 29, 42, 58, 77, 93, 96, 96, 84, 72, 63, 63, 65, 66, 66, 61, 56, 51, 48, 46, 44, 42, 38, 34, 30, 29, 28, 28, 27, 25, 25, 23, 21, 20, 18, 17, 15, 13, 10, 8, 6, 4, 4, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2, 2};
	static int[] amp1 = new int[] {4, 4, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 3, 3, 4, 5, 6, 8, 10, 13, 17, 19, 21, 23, 25, 25, 25, 25, 23, 23, 23, 22, 20, 17, 17, 17, 25, 40, 61, 82, 95, 95, 88, 77, 70, 68, 68, 71, 72, 72, 71, 67, 63, 60, 59, 58, 58, 58, 56, 52, 48, 45, 43, 43, 42, 40, 37, 34, 30, 27, 25, 20, 16, 11, 8, 6, 5, 5, 5, 4, 4, 4, 4, 4, 4, 5, 5, 5, 4, 4, 4, 5, 5, 5, 4, 4, 4, 4, 5, 5, 5};
	static int[] freq0 = new int[] {2, 2, 2, 2, 2, 2, 3, 4, 4, 4, 4, 4, 4, 3, 2, 1, 1, 5, 20, 35, 52, 66, 69, 69, 67, 64, 60, 57, 55, 50, 45, 41, 37, 35, 30, 28, 28, 39, 54, 70, 81, 82, 83, 83, 81, 81, 80, 80, 80, 80, 80, 80, 80, 79, 78, 78, 78, 78, 78, 77, 74, 72, 66, 62, 58, 53, 52, 49, 45, 43, 35, 28, 23, 18, 16, 16, 16, 13, 10, 6, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
	static int[] freq1 = new int[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 7, 18, 35, 51, 62, 67, 67, 67, 64, 61, 55, 51, 44, 40, 33, 30, 25, 25, 30, 44, 63, 83, 87, 87, 86, 85, 83, 82, 81, 80, 80, 81, 82, 82, 83, 83, 83, 83, 82, 81, 80, 79, 79, 80, 80, 80, 80, 78, 76, 72, 68, 62, 56, 48, 42, 37, 28, 23, 15, 8, 4, 2, 2, 1, 1, 1, 1, 1, 1, 2, 3, 4, 4, 4, 3, 3, 3, 3, 3, 3, 3, 2, 2, 2, 2};
	static int[][] twoD1;
	static int[][] twoD2;
	static int[] sequenceBorders1;
	static int[] sequenceBorders2;
	static int[] lineBorders1;
	static int[] lineBorders2;
	static float fakeBuffer;
	static float fakeCounter;	
	
	public static void main(String[] args) {

		
//		getLengthPercentDiff(10,8,10);
//		getLengthPercentDiff(100,60,60);
//	sequenceBorders1 =	buildSequenceBorders(freq0);	
//	twoD1 = buildTwoDFromOneD(freq0 ,sequenceBorders1[1], sequenceBorders1[4]);	
//	sequenceBorders2 =	buildSequenceBorders(freq1);	
//	twoD2 = buildTwoDFromOneD(freq1,sequenceBorders2[1], sequenceBorders2[4]);	
//	twoDCompare(twoD1,twoD2);
	
//	sequenceBorders1 =	buildSequenceBorders(amp0);	
//	twoD1 = buildTwoDFromOneD(amp0 ,sequenceBorders1[1], sequenceBorders1[4]);	
//	sequenceBorders2 =	buildSequenceBorders(amp1);	
//	twoD2 = buildTwoDFromOneD(amp1,sequenceBorders2[1], sequenceBorders2[4]);	
//	twoDCompare(twoD1,twoD2);
	
//	sequenceBorders1 =	buildSequenceBorders(amp0);	
//	twoD1 = buildTwoDFromOneD(amp0 ,sequenceBorders1[1], sequenceBorders1[4]);	
//	sequenceBorders2 =	buildSequenceBorders(freq0);	
//	twoD2 = buildTwoDFromOneD(freq0,sequenceBorders2[1], sequenceBorders2[4]);	
//	twoDCompare(twoD1,twoD2);
	
	sequenceBorders1 =	buildSequenceBorders(amp1);	
	twoD1 = buildTwoDFromOneD(amp1 ,sequenceBorders1[1], sequenceBorders1[4]);	
	sequenceBorders2 =	buildSequenceBorders(freq1);	
	twoD2 = buildTwoDFromOneD(freq1,sequenceBorders2[1], sequenceBorders2[4]);	
	twoDCompare(twoD1,twoD2);
		
		
	
	}

	public static int[]  buildSequenceBorders(int[] input) {
		
		int start = 0;
		int end = 0;
		
		for(int i = 0; i < input.length; i++) {
			
			if(input[i] > 4) {
				if(input[i+1] > input[i]+3 || (input[i+1] > input[i]+2 && input[i+2] > input[i+1]+2)) { 
					start = i-1;
					break;
				}
			}
		}
		
		for(int i = input.length-1; i > 0; i--) {
			
			if(input[i] > 4) {
				if(input[i-1] > input[i]+3 || (input[i-1] > input[i]+2 && input[i-2] > input[i-1]+2)) {
					end = i+2;
					break;
				}
			}		
		}
		return new int[] {1,start,input[start], -1,end,input[end]};
	}
	
	private static int[][] buildTwoDFromOneD(int[] inputOneD , int startIndex, int endIndex) {
		
		int edgeH = 0;
		int lengthV = endIndex - startIndex;
		int twoD[][];
		int i;
		
		
		for( i = 0; i < inputOneD.length; i++)
			if(inputOneD[i] > edgeH )
				edgeH = inputOneD[i];
		System.out.println("Edge: "+ edgeH);
		
		twoD = new int[edgeH+1][];
		
		for(i = 0; i < twoD.length; i++)
			twoD[i] = new int[lengthV];
		
		for(int index = startIndex; index < endIndex; index++) {
			
			for(i = 0; i < twoD.length; i++) {
			//	System.out.println("H: "+index +", V: "+i);
				if(i == inputOneD[index])
					break;
				
				if(i < inputOneD[index])
					twoD[i+1][index-startIndex] = 7;
			}
		}
		
		for(int test = twoD.length-1; test > 0 ; test --)
			System.out.println("H :"+ test + " "+Arrays.toString(twoD[test]));
		
		return twoD;
	}
	
	private static int[] buildLineBorders(int[] input) {
		
		
		int[] temp = new int[10];
		int[] result;
		int resultCounter = 0;
		
		System.out.println("buildLineBorders input "+ Arrays.toString(input));
		
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
		
		System.out.println("buildLineBorders Arr"+Arrays.toString(result));
		
		return result;
	}
	
	private static float oneDCompare(int[] check, int[] db) {
				
		System.out.println("checkArr: " +Arrays.toString(check)+", db: " +Arrays.toString(db));
		float buffer = 0;
		int bufferCounter = 0;
		int higherLength =  check.length > db.length ? check.length : db.length;
		int lowerLength = check.length < db.length ? check.length : db.length;
		int localResult;  
		float result;
		float endProcent;
		int i;
		for( i = 0; i < higherLength-1; i++) {

			localResult = 0;
			
			if(i+1 < lowerLength) {
				localResult = getLengthPercentDiff( check[i+1] - check[i],  db[i+1] - db[i],
						check[i+1] - check[i] > db[i+1] - db[i] ? check[i+1] - check[i] :  db[i+1] - db[i] );
				
				//buffer += localResult;
				buffer =  buffer > 0 ? ((float)((float)buffer / 100) * ((float)localResult / 100))*100 : localResult;
				bufferCounter++;
				System.out.println("i "+i+ ", check[i+1]: "+check[i+1] +", check[i]: "+check[i]+", db[i+1]: "+db[i+1] +", db[i]: "+db[i]);
			}
			
			 System.out.println("i "+i+", localResult: "+localResult+", bufferCounter: "+bufferCounter  +", buffer: "+buffer + ", avg: "+(buffer));
			//System.out.println("i "+i+", localResult: "+localResult+", bufferCounter: "+bufferCounter  +", buffer: "+buffer + ", avg: "+((float)buffer /bufferCounter));
		}

		endProcent = (Math.abs(check[check.length-1] - db[db.length-1]) *100) / 
				(check[check.length-1] > db[db.length-1] ? check[check.length-1] : db[db.length-1]);
		
		System.out.println("endProcent "+endProcent + " highest "+ (check[check.length-1] > db[db.length-1] ? check[check.length-1] : db[db.length-1]));
		result = (float) buffer - endProcent;
		result = result > 0 ? result : 0;
		System.out.println("oneDCompare i: "+ i +", result: " + result);
		
			return result;
	}
	
	private static void twoDCompare(int[][] check, int[][] db) {
		
		int highLength =  check.length > db.length ? check.length : db.length;
		int lowLengthH =  check.length < db.length ? check.length : db.length;
		float localResult ;
		int startH = 5;		
		int resultBuffer = 0;
		float finalResult;
		int bufferCounter = 0;
		fakeBuffer = 0;
		fakeCounter = 1;	
		
		for(int i = startH; i < highLength; i++) {
			System.out.println("\ntwoDCompare h: "+ i);
			
			if(i < lowLengthH) {
				lineBorders1 = buildLineBorders(check[i]);
				lineBorders2 = buildLineBorders(db[i]);
				
				localResult = oneDCompare(lineBorders1,lineBorders2);
				resultBuffer += localResult;
				bufferCounter++;
				System.out.println("twoDCompare h: "+ i + ", localResult: "+localResult + ", counter: "+ (i - startH+1) +", resultBuffer: "+resultBuffer + ", AVG: " + (float) resultBuffer / (i - startH+1));
				
			}
			
			if(i >= lowLengthH)
				addToFakeBuffer(bufferCounter,1);
		}
		
		finalResult = resultBuffer / (bufferCounter + fakeBuffer);
		System.out.println("finalResult "+finalResult + ", fakeBuffer "+fakeBuffer);
	}
	
	private static int[][]buildTwoDBorderList(int[][] inputMatrix) {
		
		int[][] returnArray = new int[inputMatrix.length][];
		
		for(int i = 0; i < inputMatrix.length; i++)
			inputMatrix[i] = buildLineBorders(inputMatrix[i]);
		
		return returnArray;
	}
	
	private static int getLengthPercentDiff(int input1, int input2, int baseHundredPercent) {
		
	int	lengthResult =  (((baseHundredPercent - Math.abs(input1 - input2)) *100 ) / baseHundredPercent);
		lengthResult = lengthResult > 0 ?  lengthResult : 0;

		System.out.println("getLengthPercentDiff input1: "+ input1+ ", input2: "+input2 + ", baseHundredPercent: " +baseHundredPercent
				+ ", lengthResult: "+ lengthResult );
		
		return lengthResult;
	}
	
	private static void addToFakeBuffer(int lengthCounter, int source) {
		
		fakeBuffer +=  fakeCounter *((float)lengthCounter / 90);
		fakeCounter *=1.03;		
		
		//System.out.println("\n ADDToFAKEBuffer fakeCounter: "+ fakeCounter + ", fakeBuffer: "+fakeBuffer  + ", Source: "+source+ "\n");
	}
}
