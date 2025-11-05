package temp;

import java.util.Arrays;

public class Temp3 {

	private static int i;
	private static int j;

	private static int percentResult;
	
	private static int horizontal_Result;
	private static int vertical_Result;
	private static int angle_Result;
	private static int slope_length_Result;
	private static float totalSlopeResult;
	private static int resultBuffer;
	private static int resultBufferCounter;
	
	private static float tempResult;

	private static float[] result;
	
	
	private static int[] amp1 = new int[] {11,13,0,12,16,16,-27,16,21,52,85,56,28,61,-78,37,38,36,-48,18,53,23,-33,20};
	private static int[] freq1 = new int[] {5,44,0,20,11,18,-78,31,19,39,83,73,30,65,-59,24,56,42,-46,15};
	
	private static int[] amp2 = new int[] {15,18,0,13,20,19,-46,14,25,55,85,58,29,69,-81,29,34,48,1,13,41,42,1,8,56,38,5,11,68,25,-69,29,78,8,-33,9};
	private static int[] freq2 = new int[] {8,43,0,19,15,18,-74,32,23,36,84,66,33,57,-59,26,47,47,15,12,63,26,-66,50,78,3,1,8};
	
	private static int[] amp3 = new int[] {47,75,0,25,56,60,34,9,71,34,-69,61};
	private static int[] freq3 = new int[] {34,51,52,17,58,30,-56,66};
	
	public static void main(String[] args) {
		
		mainLogic(amp1,amp2);
	}
	
	public static float[] mainLogic(int[] checkInput, int[] dbInput) {
		
		
		resultBuffer = 0;
		resultBufferCounter = 0;
		int jStarter = 0;
		boolean setStarter;
		
		System.out.println("checkInput.length: "+checkInput.length + ", dbInput.length: "+dbInput.length);
		
		for(i = 0; i < checkInput.length; i = i+4) {
			
			System.out.println("\ni: " + i);
			
			setStarter = false;
			j = jStarter > 0 ? jStarter : 0 ;
			
			jStarter = 0;
			
			for(; j < dbInput.length; j = j+4) {
			
				System.out.println("  j: "+j + ", checkInput[i]: "+checkInput[i] + ", dbInput[j]: "+dbInput[j]);
				
				if(setStarter == false && j > 10) {
					
					if(dbInput[j] + 10 >=  checkInput[i] ) {
						jStarter = j;
						setStarter = true;
						System.out.println("SetStart j: "+ j+ ",  checkInput[i]: "+checkInput[i] + ", dbInput[j]: " + dbInput[j]);
					}				
				}
				

				
				if(i == checkInput.length || j == dbInput.length || dbInput[j] >  checkInput[i] +10) 
					break;
					
				horizontal_Result = getLengthPercentDiff(checkInput[i],dbInput[i],10);
				vertical_Result = getLengthPercentDiff(checkInput[i+1],dbInput[i+1],10);
				angle_Result = getLengthPercentDiff(checkInput[i+2],dbInput[i+2],10);
				slope_length_Result = getLengthPercentDiff(checkInput[i+3],dbInput[i+3],10);				
				totalSlopeResult = (((float)horizontal_Result / 100) * ((float)vertical_Result / 100) 
					* ((float)angle_Result / 100) * ((float)slope_length_Result / 100)) *100;
				
				System.out.println("    j: " + j + ", h_Res: " + horizontal_Result+ ", v_Res: " + vertical_Result
					+ ", a_Result: " + angle_Result+ ", s_l_Result: " + slope_length_Result+ ", t_S_Result: " + totalSlopeResult);
				
				if(totalSlopeResult > tempResult)
					tempResult = totalSlopeResult;

			}
			

			resultBuffer += tempResult;
			resultBufferCounter++;
		}
		
		result = new float[] {resultBuffer / resultBufferCounter};
		
		System.out.println("final result: "+ Arrays.toString(result));
		
		return result;
	}
	
	private static int getLengthPercentDiff(int input1, int input2, int baseHundredPercent) {
		
		percentResult =  (((baseHundredPercent - Math.abs(input1 - input2)) *100 ) / baseHundredPercent);
		percentResult = percentResult > 0 ?  percentResult : 0;

//		Debug.debug(debugLevel, "getLengthPercentDiff input1: "+ input1+ ", input2: "+input2 + ", baseHundredPercent: " +baseHundredPercent
//				+ ", lengthResult: "+ lengthResult );
		
		return percentResult;
	}
	
//	private static void addToFakeBuffer(int lengthCounter, int source) {
//		
//		fakeBuffer +=  fakeCounter *((float)lengthCounter / 90);
//		fakeCounter *=1.20;		
//		
//		//Debug.debug(debugLevel, "\n ADDToFAKEBuffer fakeCounter: "+ fakeCounter + ", fakeBuffer: "+fakeBuffer  + ", Source: "+source+ "\n");
//	}
}
