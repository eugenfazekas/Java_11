package temp;

import java.util.Arrays;

public class Temp {

	static int[] igenAmp0 = new int[] {3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 6, 8, 11, 14, 17, 20, 24, 27, 30, 30, 28, 25, 23, 22, 21, 21, 20, 18, 18, 19, 23, 29, 42, 58, 77, 93, 96, 96, 84, 72, 63, 63, 65, 66, 66, 61, 56, 51, 48, 46, 44, 42, 38, 34, 30, 29, 28, 28, 27, 25, 25, 23, 21, 20, 18, 17, 15, 13, 10, 8, 6, 4, 4, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2, 2};
	static int[] igenAamp1 = new int[] {4, 4, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 3, 3, 4, 5, 6, 8, 10, 13, 17, 19, 21, 23, 25, 25, 25, 25, 23, 23, 23, 22, 20, 17, 17, 17, 25, 40, 61, 82, 95, 95, 88, 77, 70, 68, 68, 71, 72, 72, 71, 67, 63, 60, 59, 58, 58, 58, 56, 52, 48, 45, 43, 43, 42, 40, 37, 34, 30, 27, 25, 20, 16, 11, 8, 6, 5, 5, 5, 4, 4, 4, 4, 4, 4, 5, 5, 5, 4, 4, 4, 5, 5, 5, 4, 4, 4, 4, 5, 5, 5};
	static int[] igenAfreq0 = new int[] {2, 2, 2, 2, 2, 2, 3, 4, 4, 4, 4, 4, 4, 3, 2, 1, 1, 5, 20, 35, 52, 66, 69, 69, 67, 64, 60, 57, 55, 50, 45, 41, 37, 35, 30, 28, 28, 39, 54, 70, 81, 82, 83, 83, 81, 81, 80, 80, 80, 80, 80, 80, 80, 79, 78, 78, 78, 78, 78, 77, 74, 72, 66, 62, 58, 53, 52, 49, 45, 43, 35, 28, 23, 18, 16, 16, 16, 13, 10, 6, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
	static int[] igenAfreq1 = new int[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 7, 18, 35, 51, 62, 67, 67, 67, 64, 61, 55, 51, 44, 40, 33, 30, 25, 25, 30, 44, 63, 83, 87, 87, 86, 85, 83, 82, 81, 80, 80, 81, 82, 82, 83, 83, 83, 83, 82, 81, 80, 79, 79, 80, 80, 80, 80, 78, 76, 72, 68, 62, 56, 48, 42, 37, 28, 23, 15, 8, 4, 2, 2, 1, 1, 1, 1, 1, 1, 2, 3, 4, 4, 4, 3, 3, 3, 3, 3, 3, 3, 2, 2, 2, 2};
	
	
	static int[][][] test1;
	static int[][][] test2;
	static int[] lineBorders1;
	static int[] lineBorders2;
	static float fakeBuffer;
	static float fakeCounter;	
	
	public static void main(String[] args) {
		
		test1  = 	mainBuilder(igenAmp0,igenAfreq0);
		test2  = 	mainBuilder(igenAamp1,igenAfreq1);
	
	
	twoDCompare(test1,test2);
	
	
//	buildVoiceRecognitionAreaString(result);
		
//		for(int i = 0; i < result[0].length ; i++)
//			System.out.println("0 i: "+i+ " "+ Arrays.toString(result[0][i]));
//		
//		for(int i = 0; i < result[1].length ; i++)
//			System.out.println("1 i: "+i+ " "+Arrays.toString(result[1][i]));
	}

	private static float oneDCompare(int[] check, int[] db, int[] checkAMP, int[]dbAMP) {
		
		System.out.println("\noneDCompare checkArr: " +Arrays.toString(check)+", db: " +Arrays.toString(db));
		float buffer = 0;
		int bufferCounter = 0;
		int higherLength =  check.length > db.length ? check.length : db.length;
		int lowerLength = check.length < db.length ? check.length : db.length;
		float localResult;  
		float freq1Result;
		float freq2Result;
		float tempResult;
		float result;
		float endProcent;
		int i;
		
		localResult  = getLengthPercentDiff( check[1],  db[1],
				check[1] > db[1] ? check[1]:  db[1]);
		
		if(checkAMP != null) {
			
			freq1Result = getLengthPercentDiff(checkAMP[check[0]], dbAMP[db[0]], 100);
			freq2Result = getLengthPercentDiff(checkAMP[check[1]], dbAMP[db[1]], 100);
			localResult = ( (float) ( (float)localResult / 100) * ((float)freq1Result / 100) * ((float)freq2Result / 100)) *100;
		}
		
		buffer = localResult;
		bufferCounter++;
		
		for(i = 1; i < higherLength-1; i++) {

			System.out.println("oneDCompare i: "+ i+ ", buffer: "+buffer+", Check Array: "+ Arrays.toString(check) + ", Db Array: " +Arrays.toString(db));
			localResult = 0;
			
			if(i+1 < lowerLength) {
				localResult = getLengthPercentDiff( check[i+1] - check[i],  db[i+1] - db[i],
						check[i+1] - check[i] > db[i+1] - db[i] ? check[i+1] - check[i] :  db[i+1] - db[i] );
				
				if(checkAMP != null) {
					tempResult = localResult;
					freq1Result = getLengthPercentDiff(checkAMP[check[i]], dbAMP[db[i]], 100);
					freq2Result = getLengthPercentDiff(checkAMP[check[i+1]], dbAMP[db[i+1]], 100);
					localResult = ( (float) ( (float)localResult / 100) * ((float)freq1Result / 100) * ((float)freq2Result / 100)) *100;
					System.out.println("oneDCompare localResult: "+tempResult+", freq1Result: "+freq1Result +", freq2Result: "+freq2Result + ", newLocalResult: "+localResult);
				}

				buffer += localResult;
				bufferCounter++;
				System.out.println("oneDCompare i: "+i+ ", check[i+1]: "+check[i+1] +", check[i]: "+check[i]+", db[i+1]: "+db[i+1] +", db[i]: "+db[i]);
			}
			
			 System.out.println("oneDCompare i "+i+", localResult: "+localResult+", bufferCounter: "+bufferCounter  +", buffer: "+buffer + ",one avg: "+(buffer/bufferCounter));
			//System.out.println("i "+i+", localResult: "+localResult+", bufferCounter: "+bufferCounter  +", buffer: "+buffer + ", avg: "+((float)buffer /bufferCounter));
		}

		endProcent = (Math.abs(check[check.length-1] - db[db.length-1]) *100) / 
				(check[check.length-1] > db[db.length-1] ? check[check.length-1] : db[db.length-1]);
		
		System.out.println("endProcent "+endProcent + " highest "+ (check[check.length-1] > db[db.length-1] ? check[check.length-1] : db[db.length-1]));
		result = (float) (buffer/bufferCounter)- endProcent;
		result = result > 0 ? result : 0;
		System.out.println("oneDCompare i: "+ i +", result: " + result);
		
			return result;
	}
	
	private static void twoDCompare(int[][][] check, int[][][] db) {
		
		float ampResult;
		float freqResult;
		float multiResult;
		float resultBuffer = 0;
		int resultBufferCounter = 0;
		float finalResult;
		
		fakeBuffer = 0;
		fakeCounter = 1;	
		
		int ampHLength  = check[0].length > db[0].length ? check[0].length : db[0].length; 
		int freqHLength = check[1].length > db[1].length ? check[1].length : db[1].length; 
		int overallH = ampHLength > freqHLength ? ampHLength : freqHLength;
		
		for(int i = 1; i < overallH ; i++) {
			
			ampResult = 0;
			freqResult = 0;
			multiResult = 0;
			
			if(i < check[0].length && i < db[0].length) {
				
				ampResult = oneDCompare(check[0][i], db[0][i], null ,null);
				multiResult = ampResult;
			}
			
			if(i < check[1].length && i < db[1].length) {
				freqResult = oneDCompare(check[1][i], db[1][i], check[0][0], db[0][0]);
				multiResult = ampResult > 0 ? ((float)((float)ampResult / 100) * ((float)freqResult  / 100))*100 : freqResult;
			}
			
			System.out.println("twoDCompare i: " +i+", ampResult: "+ampResult +", freqResult: "+freqResult +", multiResult: "
					+multiResult + ", resultBuffer "+resultBuffer + ", AVG: "+ (resultBuffer/ (i+fakeBuffer)) );
			resultBuffer += multiResult;
			resultBufferCounter++;
						
			if((i >= check[0].length &&  i < db[0].length) || (i < check[0].length &&  i >= db[0].length))
				addToFakeBuffer(resultBufferCounter,1);
			
			if((i >= check[1].length &&  i < db[1].length) || (i < check[1].length &&  i >= db[1].length))
				addToFakeBuffer(resultBufferCounter,1);
		}

		finalResult = resultBuffer / (resultBufferCounter + fakeBuffer);
		System.out.println("finalResult "+finalResult + ", resultBufferCounter "+resultBufferCounter + ", fakeBuffer "+fakeBuffer +", fakeCounter "+fakeCounter );
	}
	
	public static int[][][] mainBuilder(int[] amplitde, int[] frequnecys ) {
		
		int[][][] result = new int[2][][];
		int[][] twoDTemp1;
		int[][] twoDTemp2;
		int[] sequenceBorders1;
		int highest1;
				
		sequenceBorders1 = buildSequenceBorders(frequnecys);
		twoDTemp1 = buildTwoDFromOneD(amplitde ,sequenceBorders1[1], sequenceBorders1[4]);	
		twoDTemp2 = buildTwoDFromOneD(frequnecys ,sequenceBorders1[1], sequenceBorders1[4]);
		
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
	
	public static int[]  buildSequenceBorders(int[] input) {
		
		int start = 0;
		int end = 0;
		int[] result;
		for(int i = 0; i < input.length; i++) {
			
				if(input[i]>8) { 
					start = i-1;
					break;
			}
		}
		
		for(int i = input.length-1; i > 0; i--) {
			
			if(input[i]>8) {
					end = i+2;
					break;
			}		
		}
		
		result = new int[] {1,start,input[start], -1,end,input[end]};
		System.out.println("buildSequenceBorders "+ Arrays.toString(result));
		
		return result;
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
	
	public static String buildVoiceRecognitionAreaString (int[][][] inputHeights) {
			
			StringBuilder stringBuilder = new StringBuilder();
			for(int i = 0; i < inputHeights.length ; i++) {
			
				for(int j = 0; j < inputHeights[i].length ; j++) {
	
						if(j > 0 && j < inputHeights[i].length)
							stringBuilder.append("_");
						
						stringBuilder.append(j+":"+Arrays.toString(inputHeights[i][j]));
	
				}
				if(i < inputHeights.length-1)
					stringBuilder.append("&");
				}
			
			System.out.println("VoiceRecognitionUtil buildVoiceRecognitionAreaString: "+stringBuilder.toString() + ", inputHeights.length: "+inputHeights.length + ", [0] "+ Arrays.toString(inputHeights[0][0]));
				return stringBuilder.toString();
		}
	private static int getLengthPercentDiff(int input1, int input2, int baseHundredPercent) {
		
	int	lengthResult =  (((baseHundredPercent - Math.abs(input1 - input2)) *100 ) / baseHundredPercent);
		lengthResult = lengthResult > 0 ?  lengthResult : 0;

//		System.out.println("getLengthPercentDiff input1: "+ input1+ ", input2: "+input2 + ", baseHundredPercent: " +baseHundredPercent
//				+ ", lengthResult: "+ lengthResult );
		
		return lengthResult;
	}
	
	private static void addToFakeBuffer(int lengthCounter, int source) {
		
		fakeBuffer +=  fakeCounter *((float)lengthCounter / 90);
		fakeCounter *=1.03;		
		
		//System.out.println("\n ADDToFAKEBuffer fakeCounter: "+ fakeCounter + ", fakeBuffer: "+fakeBuffer  + ", Source: "+source+ "\n");
	}
}
