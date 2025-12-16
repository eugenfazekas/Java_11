package voiceRecognition.audio5.recognition.slope;

import java.util.Arrays;

import voiceRecognition.audio8.util.Debug;

public class VRSLMainLogic {
	
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
		
	private static int debugLevel = 5;
	
	public static float mainBuilder(int[] checkInput, int[] dbInput) {
			
		resultBuffer = 0;
		resultBufferCounter = 0;
		jStarter = 0;
		fakeBuffer = 1;
		fakeBufferMultiplier = 1;
		Debug.debug(debugLevel,"VRSLMainLogic mainBuilder checkInput.length: "+checkInput.length 
			+ ", dbInput.length: "+dbInput.length);
		
		for(i = 0; i < checkInput.length-5; i = i+5) {
						
			setStarter = false;
			j = jStarter > 0 ? jStarter : 0 ;
			tempResult = 0;
			totalSlopeResult = 0;
			jStarter = 0;
			
			checkTempArray = new int[] {checkInput[i], checkInput[i+1], checkInput[i+2]
				, checkInput[i+3], checkInput[i+4]};
		
			for(; j < dbInput.length-5; j = j+5) {
							
				Debug.debug(debugLevel,"\n VRSLMainLogic mainBuilder i: " + i + ", j: "+ j + ", Check Arraay: " 
						+Arrays.toString(checkTempArray)+ ", dbInput Arraay: " +Arrays.toString(dbTempArray));
				
				if(setStarter == false) {
					
					if(dbInput[j+5] <= checkInput[i+5] && dbInput[j+5] >= checkInput[i+5] -10) {
						jStarter = j+5;
						setStarter = true;
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
			
			Debug.debug(debugLevel,"VRSLMainLogic mainBuilder i: " + i+ ", tempResult: "+tempResult
				+ ", resultBuffer: "+resultBuffer + ", resultBufferCounter: " +resultBufferCounter 
				+", fakeBuffer: "+ fakeBuffer +", fakeBufferMultiplier: "+fakeBufferMultiplier);
		}
		
		MAIN_SLOPE_RESULT = (float) resultBuffer/(resultBufferCounter + fakeBuffer);
		
		Debug.debug(debugLevel,"VRSLMainLogic mainBuilder final result: "+ MAIN_SLOPE_RESULT + ", resultBuffer: "
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
		Debug.debug(debugLevel, "VRSLMainLogic addToFakeBuffer addToFakeBuffer index: "+index
			+", lastFakeIndex: "+lastFakeIndex);
		
		if(index - 5 == lastFakeIndex) {
			
			fakeBufferMultiplier *= fakeBufferMultiplierContinueValue;
			Debug.debug(debugLevel,"VRSLMainLogic addToFakeBuffer Repeated Fake! index: " + index 
				+ ", fakeBufferMultiplier: " + fakeBufferMultiplier);
		}
		
		fakeBuffer += fakeBufferMultiplier;
		
		lastFakeIndex = index;
	}
}
