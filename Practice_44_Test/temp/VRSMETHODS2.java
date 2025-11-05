package temp;

import java.util.Arrays;

public class VRSMETHODS2 {
	
	static int[] igen1A = new int[] {0,11,7,91,84,8,87,20,13,-80,22,12,30,72,82,31,71,38,87,66,39,85,49,52,-73,51,51,57,49,-18,58,48,64,47,-9,65,45,72,9,-78};
	static int[] igen1F = new int[] {0,3,5,50,83,7,50,21,3,-73,23,3,28,62,85,31,62,37,50,-63,38,49,41,46,-45,48,46,52,42,-45,54,42,58,44,26,59,43,66,36,-45,67,27,71,3,-80};
	static int[] igen2A = new int[] {0,7,6,41,79,7,42,17,9,-73,20,8,28,92,84,29,89,40,47,-75,41,46,45,44,-26,47,44,53,43,-9,54,41,59,35,-50,61,35,71,9,-68};
	static int[] igen2F = new int[] {0,3,4,48,84,5,47,17,3,-74,20,3,26,70,84,27,69,36,47,-67,37,46,41,42,-45,43,42,47,38,-45,57,38,61,34,-45};
	static int[] igen3A = new int[] {0,10,7,41,77,9,41,21,8,-70,22,8,31,79,82,32,75,35,66,-71,38,67,48,41,-68,50,41,57,51,55,59,50,67,35,-61,68,34,75,9,-74};
	static int[] igen3F = new int[] {0,3,5,46,83,10,46,21,3,-75,24,3,29,67,85,31,69,38,47,-72,40,46,44,42,-45,48,42,54,34,-53,55,35,58,38,45,62,38,66,42,45,67,41,75,3,-78};
	
	static int[] nem1A  = new int[] {0,12,8,88,83,10,89,14,86,-36,15,82,18,73,-71,19,74,29,91,59,31,91,43,55,-71,44,56,47,61,59,48,61,52,56,-51,53,56,57,58,26,58,56,65,13,-80};
	static int[] nem1F = new int[] {0,3,5,38,81,6,37,9,34,-45,10,35,13,38,45,15,38,19,42,45,28,42,32,38,-45,38,38,42,42,45,43,42,51,34,-45,52,34,56,38,45,57,38,61,34,-45,62,26,66,3,-80};
	static int[] nem2A  = new int[] {0,9,10,86,82,11,88,15,90,26,16,90,20,84,-56,22,84,30,74,-51,32,74,35,77,45,36,77,41,63,-70,42,63,46,69,56,48,68,60,13,-77};
	static int[] nem2F = new int[] {0,3,4,37,83,5,36,9,34,-26,13,34,17,36,26,18,35,35,38,10,36,37,39,34,-45,40,35,43,38,45,50,38,54,34,-45,55,34,60,3,-80};	
	static int[] nem3A  = new int[] {0,12,7,59,81,8,58,12,54,-45,14,55,30,90,65,32,90,40,79,-53,41,79,60,14,-73};
	static int[] nem3F = new int[] {0,3,5,38,81,15,38,22,46,48,23,45,26,40,-59,28,40,34,44,33,35,44,39,42,-26,48,42,59,3,-74};
	
	private static int horizLower;
	private static int horizHigher;
	private static int vertLower;
	private static int vertHigher;
	private static int slopeAngle;
	private static int slopeLength;

	private static int hLength;
	private static int vLength;
	private static int sLength;
	private static int angle1;
	private static int angle2;
	private static int angleResult1;
	private static int angleResult2; 
	private static int percentResult;

	private static float MAIN_LOCAL_SLOPE_RESULT;
	
	static int i;
	static int j;
	static int mainbuilderLength;
	static int jStarter = 0;
	static boolean setStarter;
	private static float tempResult;
	private static float totalSlopeResult;
	private static float MAIN_SLOPE_RESULT;
	static float resultBuffer;
	static float resultBufferCounter;
	static int[] checkTempArray;
	static int[] dbTempArray;
	
	private static float fakeBuffer;
	private static float fakeBufferMultiplier;
	private static int lastFakeIndex;
	
	
	private static int baseLengthHundredPercent = 15;
	private static float fakeBufferMultiplierValue = 1.7f;
	private static float fakeBufferMultiplierContinueValue = 3f;
	
	public static void main(String[] args) {

		
		float resultAmplitude  = mainBuilder(igen1A, nem2A);
		float resultFrequency  = mainBuilder(igen1F, nem2F);
		
	//System.out.println(resultFrequency);	
	
//		float resultAmplitude  = mainBuilder(igen1A, igen2A);
//		float resultFrequency  = mainBuilder(igen1F, igen2F);
		
		float mainResult = (((float)(float)resultAmplitude / 100) * ((float)resultFrequency / 100)) *100;
		System.out.println("ALL OVER RESULT: "+mainResult + ", resultAmplitude: "+ resultAmplitude + ", resultFrequency: "+resultFrequency);
	}

	public static float mainBuilder(int[] checkInput, int[] dbInput) {
			
		resultBuffer = 0;
		resultBufferCounter = 0;
		jStarter = 0;
		fakeBuffer = 1;
		fakeBufferMultiplier = 1;
		System.out.println("checkInput.length: "+checkInput.length + ", dbInput.length: "+dbInput.length);
		
		for(i = 0; i < checkInput.length-5; i = i+5) {
						
			setStarter = false;
			j = jStarter > 0 ? jStarter : 0 ;
			tempResult = 0;
			totalSlopeResult = 0;
			jStarter = 0;
			
			checkTempArray = new int[] {checkInput[i], checkInput[i+1], checkInput[i+2], checkInput[i+3], checkInput[i+4]};
		
			for(; j < dbInput.length-5; j = j+5) {
							
				System.out.println("\ni: " + i + ", j: "+ j + ", Check Arraay: " +Arrays.toString(checkTempArray)
				+ ", dbInput Arraay: " +Arrays.toString(dbTempArray));
				
				if(setStarter == false) {
					
					//System.out.println("SetStart 1.0 j: "+ j+ ",  checkInput[i+5]: "+checkInput[i+5] + ", dbInput[j+5]: " + dbInput[j+5]);
					if(dbInput[j+5] <= checkInput[i+5] && dbInput[j+5] >= checkInput[i+5] -10) {
						jStarter = j+5;
						setStarter = true;
						//System.out.println("SetStart 2.0 j: "+ j+ ",  checkInput[i]: "+checkInput[i] + ", dbInput[j]: " + dbInput[j]);
					}				
				}
								
				if(i >= checkInput.length || j >= dbInput.length || dbInput[j] > checkInput[i]+10 ) 
					break;
				
				dbTempArray = new int[] {dbInput[j], dbInput[j+1], dbInput[j+2], dbInput[j+3], dbInput[j+4]};
									
				totalSlopeResult = mainLogic(checkTempArray,dbTempArray);
								
				if(totalSlopeResult > tempResult)
					tempResult = totalSlopeResult;

			}
						
			if(tempResult == 0)
				addToFakeBuffer(i);
			
			resultBuffer += tempResult;
			resultBufferCounter += (float) getLength(checkInput[i], checkInput[i+1], checkInput[i+2], checkInput[i+3])/45 ;
			
			System.out.println("mainBuilder i: " + i+ ", tempResult: "+tempResult+ ", resultBuffer: "+resultBuffer + ", resultBufferCounter: " +resultBufferCounter 
				+", fakeBuffer: "+ fakeBuffer +", fakeBufferMultiplier: "+fakeBufferMultiplier);
		}
		
		MAIN_SLOPE_RESULT = (float) resultBuffer/(resultBufferCounter + fakeBuffer);
		
		System.out.println("final result: "+ MAIN_SLOPE_RESULT + ", resultBuffer: "
				+resultBuffer + ", resultBufferCounter: " +resultBufferCounter);
		
		return MAIN_SLOPE_RESULT;
	}
	
	static float mainLogic(int[] check, int[] db) {
		
	//	System.out.println("\nmainBuilder check Array: " + Arrays.toString(check) + ", DB Array:" + Arrays.toString(db) );
		
		if(( check[4] > 0 && db[4] < 0) || ( check[4] < 0 && db[4] > 0))
			return (float) 0;
		
		horizLower = getLengthPercentDiff(check[0],db[0],baseLengthHundredPercent);
		horizHigher = getLengthPercentDiff(check[1],db[1],baseLengthHundredPercent);
		vertLower = getLengthPercentDiff(check[2],db[2],baseLengthHundredPercent);
		vertHigher = getLengthPercentDiff(check[3],db[3],baseLengthHundredPercent);
		slopeAngle = gerAnglePercentDiff(check[4],db[4],45);
		slopeLength = getLengthPercentDiff(getLength( check[0], check[1], check[2], check[3]),
					                       getLength( db[0], db[1], db[2], db[3]),baseLengthHundredPercent);
				
		MAIN_LOCAL_SLOPE_RESULT = (((float)horizLower / 100) * ((float)horizHigher / 100) * ((float)vertLower / 100)
				* ((float)vertHigher / 100) * ((float)slopeAngle / 100) * ((float)slopeLength / 100)) *100;
		
//		System.out.println("mainBuilder horizLower: "+horizLower + ", horizHigher: "+horizHigher+ ", vertLower: "+vertLower
//			+", vertHigher: "+vertHigher + ", slopeAngle: "+slopeAngle+ ", slopeLength: "+slopeLength	
//			+ ", MAIN_LOCAL_SLOPE_RESULT: "+MAIN_LOCAL_SLOPE_RESULT);
		
		return MAIN_LOCAL_SLOPE_RESULT;
	}
		
	private static int getLengthPercentDiff(int input1, int input2, int baseHundredPercent) {
		
		percentResult =  (((baseHundredPercent - Math.abs(input1 - input2)) *100 ) / baseHundredPercent);
		percentResult = percentResult > 0 ?  percentResult : 0;

		//System.out.println("getLengthPercentDiff input1: "+ input1+ ", input2: "+input2 + ", baseHundredPercent: " 
		//	+baseHundredPercent + ", lengthPercentResult: " + percentResult);
		
		return percentResult;
	}
		
	static int gerAnglePercentDiff(int inputAngle1, int inputAngle2,int inputCheckComapre) {
		
		angle1 = inputAngle1 + 90;
		angle2 = inputAngle2 + 90;
		angleResult1 = (((inputCheckComapre - (Math.abs(angle1 - angle2))) * 100 ) / inputCheckComapre);
		angleResult2 = angleResult1 > 0 ?  angleResult1 : 0; 
		
		//System.out.println("gerAnglePercentDiff inputAngle1: "+ inputAngle1+ ", inputAngle2: "+inputAngle2 
		//	+", limit for 100%: "+inputCheckComapre + ", angleResult: "+ angleResult2 );
		
		return angleResult2;
	}
	
	private static int getLength(int x1, int x2, int y1, int y2) {
		
		hLength  = Math.abs(x1 - x2);
		vLength = Math.abs(y1 - y2);
		
		sLength =  (int) Math.sqrt( Math.pow(hLength, 2) + Math.pow(vLength, 2)) ;
		
		return sLength;
	}
	
	private static void addToFakeBuffer(int index) {
		
		fakeBufferMultiplier *= fakeBufferMultiplierValue;
		System.out.println( "VRSCMainLogic addToFakeBuffer index: "+index+", lastFakeIndex: "+lastFakeIndex);
		
		if(index - 5 == lastFakeIndex) {
			
			fakeBufferMultiplier *= fakeBufferMultiplierContinueValue;
			System.out.println( "VRSCMainLogic Repeated Fake! index: " + index + ", fakeBufferMultiplier: " + fakeBufferMultiplier);
		}
		
		fakeBuffer += fakeBufferMultiplier;
		
		lastFakeIndex = index;
	}
}