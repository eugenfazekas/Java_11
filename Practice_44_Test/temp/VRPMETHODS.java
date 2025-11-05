package temp;

public class VRPMETHODS {

	static int percentResult;
	static int i;
	static int j;
	static int jStarter = 0;
	static boolean setStarter;
	static float resultBuffer;
	static int resultBufferCounter;
	private static float tempResult;
	private static float actualResult;
	private static float result;
	private static int vertCheckResult;
	private static int horizCheckResult;
	
	private static int[] igenAmp1 = new int[] {15, 7, 24, 80, 32, 43, 39, 34, 46, 30, 54, 27};
	private static int[] igenFreq1 = new int[] {15, 3, 24, 76, 36, 55, 47, 46, 54, 46};
	private static int[] igenAmp2 = new int[] {20, 10, 27, 84, 38, 41, 49, 37, 57, 37, 73, 11};
	private static int[] igenFreq2 = new int[] {19, 3, 27, 69, 40, 46, 46, 50, 56, 46, 67, 34, 73, 3};
	private static int[] nemAmp0 = new int[] {28, 92, 34, 78, 44, 88, 52, 57, 58, 63, 80, 7};
	private static int[] nemFreq0 = new int[] {18, 46, 26, 38, 39, 58, 59, 42, 69, 30, 77, 3};
	
	
	
	public static void main(String[] args) {
		
		mainBuilder(nemAmp0,nemAmp0);

	}
	
	public static float mainBuilder(int[] checkInput, int[] dbInput) {
		
		resultBuffer = 0;
		resultBufferCounter = 0;
		jStarter = 0;
		
		System.out.println("checkInput.length: "+checkInput.length + ", dbInput.length: "+dbInput.length);
		
		for(i = 0; i < checkInput.length; i = i+2) {
			
			System.out.println("\ni: " + i);
			
			setStarter = false;
			j = jStarter > 0 ? jStarter : 0 ;
			jStarter = 0;
			tempResult = 0;
			
			for(; j < dbInput.length; j = j+2) {
						
				if(setStarter == false) {
					
					if(j< dbInput.length-2 && i < checkInput.length-2 && dbInput[j+2] + 10 >=  checkInput[i+2] ) {
						jStarter = j+2;
						setStarter = true;
						System.out.println("SetStart 2.0 j: "+ (j+2)+ ",  checkInput[i+2]: "+checkInput[i+2] + ", dbInput[j+2]: " + dbInput[j+2]);
					}				
				}
								
				if(i == checkInput.length || j == dbInput.length || dbInput[j] >  checkInput[i] +10) 
					break;
				
				horizCheckResult = getLengthPercentDiff(checkInput[i], dbInput[j] , 10);
				vertCheckResult = getLengthPercentDiff(checkInput[i+1], dbInput[j+1] ,10);
				actualResult = ( (  (float)horizCheckResult / 100) * (  (float)vertCheckResult / 100)  ) *100;
				
				System.out.println("mainBuilder i: " + i+ ", j: " + j+ ", checkInput[i]: " +checkInput[i]+ ", checkInput[i+1]: " +checkInput[i+1]
						+ ", dbInput[i]: " +dbInput[j]+ ", dbInput[i+1]: " +dbInput[j+1]);
				System.out.println("mainBuilder horizCheckResult: " +horizCheckResult+ ", vertCheckResult: " +vertCheckResult
						+ ", actualResult: " +actualResult+ ", resultBuffer: "+resultBuffer + ", resultBufferCounter: " +resultBufferCounter);

				
				if(actualResult > tempResult)
					tempResult = actualResult;

			}
			
			resultBuffer += tempResult;
			resultBufferCounter++;
			System.out.println("mainBuilder i: " + i+ ", resultBuffer: "+resultBuffer + ", resultBufferCounter: " +resultBufferCounter);
		}
		
		result = resultBuffer / resultBufferCounter;
		
		System.out.println("final result: "+ result + ", resultBuffer: "
				+resultBuffer + ", resultBufferCounter: " +resultBufferCounter);
		
		return result;
	}
	
	private static int getLengthPercentDiff(int input1, int input2, int baseHundredPercent) {
		
		percentResult =  (((baseHundredPercent - Math.abs(input1 - input2)) *100 ) / baseHundredPercent);
		percentResult = percentResult > 0 ?  percentResult : 0;

//		Debug.debug(debugLevel, "getLengthPercentDiff input1: "+ input1+ ", input2: "+input2 + ", baseHundredPercent: " +baseHundredPercent
//				+ ", lengthResult: "+ lengthResult );
		
		return percentResult;
	}

}
