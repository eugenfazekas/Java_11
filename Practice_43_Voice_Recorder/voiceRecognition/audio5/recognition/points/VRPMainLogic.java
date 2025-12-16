package voiceRecognition.audio5.recognition.points;

import voiceRecognition.audio8.util.Debug;

public class VRPMainLogic {
		
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
	private static float fakeBuffer;
	private static float fakeBufferMultiplier;
	private static int lastFakeIndex;
	
	private static int debugLevel = 5;
	
	static float mainBuilder(int[] checkInput, int[] dbInput) {
			

		resultBuffer = 0;
		resultBufferCounter = 0;
		jStarter = 0;
		fakeBuffer = 1;
		fakeBufferMultiplier = 1;
		
		Debug.debug(debugLevel,"VRPMainLogic checkInput.length: "+checkInput.length + ", dbInput.length: "
			+dbInput.length);
		
		for(i = 0; i < checkInput.length; i = i+2) {
			
			Debug.debug(debugLevel,"\nVRPMainLogic i: " + i);
			
			setStarter = false;
			j = jStarter > 0 ? jStarter : 0 ;
			jStarter = 0;
			tempResult = 0;
			
			for(; j < dbInput.length; j = j+2) {
						
				if(setStarter == false) {
					
					if(j< dbInput.length-2 && i < checkInput.length-2 && dbInput[j+2] + 10 >=  checkInput[i+2] ) {
						jStarter = j+2;
						setStarter = true;
						Debug.debug(debugLevel,"VRPMainLogic SetStart 2.0 j: "+ (j+2)+ ",  checkInput[i+2]: "
							+checkInput[i+2] + ", dbInput[j+2]: " + dbInput[j+2]);
					}				
				}
								
				if(i == checkInput.length-1 || j == dbInput.length-1 || dbInput[j] >  checkInput[i] +10) 
					break;
				
				horizCheckResult = getLengthPercentDiff(checkInput[i], dbInput[j] , 10);
				vertCheckResult = getLengthPercentDiff(checkInput[i+1], dbInput[j+1] ,10);
				actualResult = ( (  (float)horizCheckResult / 100) * (  (float)vertCheckResult / 100)  ) *100;
				
				Debug.debug(debugLevel,"VRPMainLogic mainBuilder i: " + i+ ", j: " + j+ ", checkInput[i]: " 
						+checkInput[i]+ ", checkInput[i+1]: " +checkInput[i+1]
						+ ", dbInput[i]: " +dbInput[j]+ ", dbInput[i+1]: " +dbInput[j+1]);
				
				Debug.debug(debugLevel,"VRPMainLogic mainBuilder horizCheckResult: " +horizCheckResult
					+ ", vertCheckResult: " +vertCheckResult+ ", actualResult: " +actualResult
					+ ", resultBuffer: "+resultBuffer + ", resultBufferCounter: " +resultBufferCounter);

				
				if(actualResult > tempResult)
					tempResult = actualResult;

			}
			
			resultBuffer += tempResult;
			resultBufferCounter++;
			
			if(tempResult == 0)
				addToFakeBuffer(i);
			
			Debug.debug(debugLevel,"VRPMainLogic mainBuilder i: " + i+ ", resultBuffer: "+resultBuffer 
				+ ", resultBufferCounter: " +resultBufferCounter
				+ ", fakeBuffer: "+fakeBuffer +", fakeBufferMultiplier: "+fakeBufferMultiplier);
		}
		
		result = (float) resultBuffer/(resultBufferCounter + fakeBuffer);
		
		Debug.debug(debugLevel,"VRPMainLogic final result: "+ result + ", resultBuffer: " +resultBuffer 
			+ ", resultBufferCounter: " +resultBufferCounter+ ", fakeBuffer: "+fakeBuffer 
			+", fakeBufferMultiplier: "+fakeBufferMultiplier);
		
		return result;
	}
	
	private static int getLengthPercentDiff(int input1, int input2, int baseHundredPercent) {
		
		percentResult =  (((baseHundredPercent - Math.abs(input1 - input2)) *100 ) / baseHundredPercent);
		percentResult = percentResult > 0 ?  percentResult : 0;

//		Debug.debug(debugLevel, "getLengthPercentDiff input1: "+ input1+ ", input2: "+input2 
		//+ ", baseHundredPercent: " +baseHundredPercent
//				+ ", lengthResult: "+ lengthResult );
		
		return percentResult;
	}
	
	private static void addToFakeBuffer(int index) {
		
		fakeBufferMultiplier *= 1.07;
		Debug.debug(debugLevel, "VRPMainLogic addToFakeBuffer index"+index+", lastFakeIndex"+lastFakeIndex);
		
		if(index - 2 == lastFakeIndex) {
			
			fakeBufferMultiplier *= 1.07;
			Debug.debug(debugLevel, "VRPMainLogic Repeated Fake! index: " + index + ", fakeBufferMultiplier: " 
					+ fakeBufferMultiplier);
		}
		
		fakeBuffer += fakeBufferMultiplier;
		
		lastFakeIndex = index;
	}
}
