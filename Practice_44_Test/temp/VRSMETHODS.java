package temp;

import java.util.Arrays;

public class VRSMETHODS {
	
	private static int lowVertIntPosition;
	private static int highVertIntPosition;
	private static int checkIntLengthResult;
	private static int checkExtLengthResult;
	private static int vertCheckResult;
	private static int hPos1;
	private static int hPos2;
	private static int horizCheckResult;
	private static int angle1;
	private static int angle2;
	private static int angleResult1;
	private static int angleResult2; 
	private static int percentResult;
	private static float tempResult;
	private static float totalSlopeResult;
	private static float result;

	private static float MAIN_HORIZONTAL_RESULT;
	private static float MAIN_VERTICAL_RESULT;
	private static float MAIN_ANGLE_RESULT;	
	private static float MAIN__SLOPE_RESULT;	
	
	static int[] igen1AmpSlope = new int[] {1, 36, 6, 19, -73, 9, 19, 15, 7, -63, 16, 7, 24, 80, 83, 24, 80, 29, 46, -81, 29, 46, 44, 29, -48, 45, 29, 59, 26, -12, 59, 26, 62, 18, -69};
	static int[] igen1FreqSlope = new int[] {1, 52, 15, 3, -74, 15, 3, 24, 76, 82, 24, 76, 28, 66, -68, 29, 66, 36, 55, -57, 37, 55, 48, 46, -39, 48, 46, 58, 43, -16, 58, 43, 62, 37, -56};
	static int[] igen2AmpSlope = new int[] {1, 33, 5, 40, 60, 5, 40, 10, 21, -75, 10, 21, 20, 10, -47, 20, 10, 27, 84, 84, 27, 84, 32, 55, -80, 33, 55, 38, 41, -70, 38, 41, 51, 37, -17, 51, 37, 61, 38, 5, 61, 38, 73, 11, -66, 77, 11, 82, 6, -45};
	static int[] igen2FreqSlope = new int[] {1, 45, 6, 53, 57, 6, 53, 12, 32, -74, 12, 32, 20, 3, -74, 20, 3, 27, 69, 83, 27, 69, 39, 47, -61, 39, 47, 52, 50, 12, 52, 50, 67, 34, -46, 69, 34, 74, 3, -80};
	static int[] nemAmpSlope = new int[] {3, 5, 27, 91, 74, 27, 91, 34, 78, -61, 35, 78, 44, 88, 48, 45, 88, 52, 57, -77, 53, 57, 58, 63, 50, 60, 63, 78, 10, -71};	
	static int[] nemfreqSlope = new int[] {9, 3, 14, 40, 82, 14, 40, 19, 46, 50, 19, 46, 26, 38, -48, 26, 38, 30, 46, 63, 32, 46, 39, 58, 59, 45, 58, 59, 42, -48, 60, 42, 69, 30, -53, 73, 30, 78, 3, -79};
	
	static int i;
	static int j;
	static int mainbuilderLength;
	static int jStarter = 0;
	static boolean setStarter;
	static float resultBuffer;
	static float resultBufferCounter;
	static int[] checkTempArray;
	static int[] dbTempArray;
	
	public static void main(String[] args) {
		

//		System.out.println(getLength(6,3,8,4));
//		System.out.println(getLength(3,6,4,8));
//		System.out.println(getLength(6,3,4,8));
//		System.out.println(getLength(3,6,8,4));
		
		mainBuilder(nemfreqSlope, igen2FreqSlope);
	}
	
	private static float getLength(int x1, int x2, int y1, int y2) {
		
		int hLength  = Math.abs(x1 - x2);
		int vLength = Math.abs(y1 - y2);
		
		double result = Math.sqrt( Math.pow(hLength, 2) + Math.pow(vLength, 2)) ;
		
		return (float)result;
	}
	
	public static float mainBuilder(int[] checkInput, int[] dbInput) {
			
		resultBuffer = 0;
		resultBufferCounter = 0;
		jStarter = 0;
		
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
					
					System.out.println("SetStart 1.0 j: "+ j+ ",  checkInput[i+5]: "+checkInput[i+5] + ", dbInput[j+5]: " + dbInput[j+5]);
					if(dbInput[j+5] <= checkInput[i+5] && dbInput[j+5] >= checkInput[i+5] -10) {
						jStarter = j+5;
						setStarter = true;
						System.out.println("SetStart 2.0 j: "+ j+ ",  checkInput[i]: "+checkInput[i] + ", dbInput[j]: " + dbInput[j]);
					}				
				}
								
				if(i >= checkInput.length || j >= dbInput.length || dbInput[j] > checkInput[i]+10 ) 
					break;
				
				dbTempArray = new int[] {dbInput[j], dbInput[j+1], dbInput[j+2], dbInput[j+3], dbInput[j+4]};
									
				totalSlopeResult = mainLogic(checkTempArray,dbTempArray);
								
				if(totalSlopeResult > tempResult)
					tempResult = totalSlopeResult;

			}
			System.out.println("mainBuilder i: " + i+ ", tempResult: "+tempResult + ", resultBuffer: "+resultBuffer + ", resultBufferCounter: " +resultBufferCounter);
			
			resultBuffer += tempResult;
			resultBufferCounter +=  getLength(checkInput[i], checkInput[i+1], checkInput[i+2], checkInput[i+3])/40 ;
			
			System.out.println("mainBuilder i: " + i+ ", resultBuffer: "+resultBuffer + ", resultBufferCounter: " +resultBufferCounter);
		}
		
		result = resultBuffer / resultBufferCounter;
		
		System.out.println("final result: "+ result + ",resultBuffer: "
				+resultBuffer + ", resultBufferCounter: " +resultBufferCounter);
		
		return result;
	}
	
	static float mainLogic(int[] check, int[] db) {
		
		System.out.println("\nmainBuilder check Array: " + Arrays.toString(check) + ", DB Array:" + Arrays.toString(db) );
		
		if((check[4] > 0 && db[4] < 0) || (check[4] < 0 && db[4] > 0)) {
			
			System.out.println("mainLogic INVERZ SLOPE! MAIN__SLOPE_RESULT = 0");
			return 0;
		}
		
		MAIN_HORIZONTAL_RESULT = horizontalCheck(check[0], check[2], db[0], db[2]);
		
		MAIN_VERTICAL_RESULT = verticalCheck(check[1], check[3], db[1], db[3]);
		
		MAIN_ANGLE_RESULT = gerAnglePercentDiff(check[4], db[4],20);	
		
		MAIN__SLOPE_RESULT = (((float)MAIN_HORIZONTAL_RESULT / 100) * ((float)MAIN_VERTICAL_RESULT / 100) * ((float)MAIN_ANGLE_RESULT / 100)) *100;
		
		System.out.println("mainBuilder M_H_R: "+MAIN_HORIZONTAL_RESULT + ", M_V_R: "+MAIN_VERTICAL_RESULT+ ", M_A_R: "+MAIN_ANGLE_RESULT+ ", MAIN__SLOPE_RESULT: "+MAIN__SLOPE_RESULT);
		
		return MAIN__SLOPE_RESULT;
	}
	
	static int verticalCheck(int vposSlope1First, int vposSlope1Second, int vposSlope2First, int vposSlope2Second) {
		
		if(vposSlope1First < vposSlope1Second) {
			
			lowVertIntPosition = vposSlope1First > vposSlope2First ? vposSlope1First : vposSlope2First;
			highVertIntPosition = vposSlope1Second < vposSlope2Second ? vposSlope1Second : vposSlope2Second;
			
			checkIntLengthResult = highVertIntPosition - lowVertIntPosition;
			checkExtLengthResult = vposSlope1Second - vposSlope1First;
		}
		
		if(vposSlope1First > vposSlope1Second) {
			
			highVertIntPosition = vposSlope1First < vposSlope2First ? vposSlope1First : vposSlope2First;
			lowVertIntPosition = vposSlope1Second > vposSlope2Second ? vposSlope1Second : vposSlope2Second;
			
			checkIntLengthResult = highVertIntPosition - lowVertIntPosition;
			checkExtLengthResult = vposSlope1First - vposSlope1Second ;
		}

		vertCheckResult = getLengthPercentDiff(checkIntLengthResult, checkExtLengthResult ,checkExtLengthResult);
		
		System.out.println("verticalCheck lowVertIntPosition: "+ lowVertIntPosition +", highVertIntPosition: " +highVertIntPosition);
		System.out.println( "verticalCheck checkIntLengthResult: " + checkIntLengthResult + ", checkExtLengthResult: " + checkExtLengthResult
			+ ", vertCheckResult: " + vertCheckResult);

		return vertCheckResult;
	}
	
	static int horizontalCheck(int hposSlope1First, int hposSlope1Second, int hposSlope2First, int hposSlope2Second) {
		
		hPos1 = (hposSlope1First + hposSlope1Second)/2;
		hPos2 = (hposSlope2First + hposSlope2Second)/2;
		horizCheckResult = getLengthPercentDiff(hPos1, hPos2 ,10);
		
		System.out.println("horizontalCheck hPos1: "+ hPos1+ ", hPos2: " +hPos2+ ", horizCheckResult: " + horizCheckResult);
		
			return horizCheckResult;
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
}