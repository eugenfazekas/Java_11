package com.audio5.recognition.scan;

import java.util.Arrays;

import com.audio8.util.Debug;

public class VRSCMainLogic {

private static int horizResult;
	
	private static int ampVResult;
	private static int ampMainAngleResult;
	
	private static int freqVResult;
	private static int freqMainAngleResult;
	
	private static float mainLocalResult;
	
	private static int angle1;
	private static int angle2;
	private static int angleResult1;
	private static int angleResult2; 
	private static int percentResult;
	
	private static float actualResult;
	private static float bestResult; 
	
	private static int dbStartIndex;
	private static int dbEndIndex; 
	
	private static int[] checkTestArray;
	private static int[] checkDBArray;
	
	private static int checkLength = 5; //before and after loops length
	private static int checkPercentLength = 15;
	private static int checkPercentAangle = 45;
		
	private static int resultBuffer;
	private static int resultBufferCounter;
	
	private static float fakeBuffer;
	private static float fakeBufferMultiplier;
	private static int lastFakeIndex;
	//private static int fakeCounter;
	
	private static int debugLevel = 5;
	
//	private static int[] testLocal1 = new int[] {50, 20, -50,  30, 20};
//	private static int[] testLocal2 = new int[] {45, 28 ,-20,  45, 25};	
//	
//	private static int[] igen1 = new int[] {11,3,77,79,18,14,83,85,29,26,85,85,46,38,86,85,65,50,86,80,80,50,85,0,90,50,79,0,91,50,-56,-26,87,49,-79,-45,80,48,-82,-56,71,46,-82,-63,65,44,-79,-63,60,42,-75,-63,57,40,-68,-56,55,39,-74,-45,50,38,-81,-45,42,37,-83,-78,32,28,-83,-83,23,19,-82,-83,16,10,-78,-82,13,3,-63,-74,12,3,-26,0,12,3,26,0,13,3,45,80,14,15,75,85,21,30,84,86,34,45,85,86,49,60,86,83,63,62,84,45,71,62,77,0,72,62,0,0,71,62,-45,-26,70,61,0,-56,71,59,71,-68,76,56,77,-71,80,53,75,-68,84,51,74,-56,87,50,56,-45,87,49,-45,-45,85,48,-68,-45,82,47,-74,-45,78,46,-75,-26,74,46,-75,0,70,46,-75,0,66,46,-74,0,63,46,-74,0,59,46,-74,0,56,46,-71,0,53,46,-63,-26,52,45,-45,-45,51,44,-26,-45,51,43,26,-45,52,42,26,-26,52,42,0,0,52,42,-26,26,51,43,-26,45,51,44,-45,26,49,44,-56,0,48,44,-45,-26,47,43,-26,-45,47,42,0,-45,47,41,0,-45,47,40,26,-45,48,39,0,-45,47,38,-56,-45,45,37,-71,-45,41,36,-78,-78,35,27,-81,-83,28,18,-81,-83,22,10,-80,-82};
//	private static int[] igen2 = new int[] {7,3,74,79,12,14,80,85,19,26,82,85,27,37,82,84,34,48,80,78,39,47,74,-56,41,45,56,-56,42,44,0,-56,41,42,-26,-63,41,40,-45,-56,39,39,-71,-56,35,37,-77,-56,30,36,-79,-78,24,27,-79,-83,19,18,-78,-83,14,10,-75,-82,11,3,-68,-74,9,3,-56,0,8,3,-26,0,8,3,0,0,8,3,56,79,11,14,78,85,18,29,84,86,31,46,86,86,50,63,86,84,69,68,86,74,84,70,84,26,91,69,75,-56,92,67,-45,-68,89,64,-68,-74,87,60,-45,-74,87,57,-26,-71,86,54,-63,-68,83,52,-75,-63,78,50,-80,-63,71,48,-82,-56,63,47,-82,-45,56,46,-80,-45,51,45,-75,-45,48,44,-63,-45,47,43,-45,-45,46,42,-45,-26,45,42,-26,0,45,42,-26,-26,44,41,-26,-45,44,40,0,-45,44,39,0,-45,44,38,26,-26,45,38,26,0,45,38,26,0,46,38,0,0,45,38,-45,0,44,38,-45,0,43,38,-56,0,41,38,-56,0,40,38,-45,0,39,38,-56,0,37,38,-56,-26,36,37,-45,-45,35,36,-26,-45,35,35,0,-45,35,34,-26,-26,34,34,-45,0,33,34,-45,0,32,34,-56,0,30,34,-68,0,27,34,-71,-75,24,26,-74,-82,20,18,-75,-82,16,10,-75,-82};
//	private static int[] igen3 = new int[] {10,3,68,78,13,13,75,84,18,24,79,84,24,35,80,84,30,46,79,79,35,46,77,0,39,46,71,0,41,46,45,0,41,46,0,0,41,46,-26,0,40,46,-56,-26,38,45,-63,-45,36,44,-63,-63,34,41,-56,-71,33,38,-63,-68,30,36,-71,-63,27,34,-74,-78,23,26,-77,-82,18,18,-78,-82,13,10,-75,-82,10,3,-68,-74,8,3,-45,0,8,3,26,0,9,3,45,0,10,3,68,81,14,16,80,85,22,31,84,86,36,47,86,86,54,64,86,84,69,67,85,68,78,69,78,45,79,69,-56,-56,75,66,-77,-74,70,62,-75,-75,67,58,-63,-75,66,54,0,-74,67,51,26,-68,67,49,0,-63,67,47,-26,-56,66,46,-45,-26,65,46,-56,-26,63,45,-63,-45,61,44,-71,-45,57,43,-75,-45,53,42,-77,-26,48,42,-75,0,45,42,-71,0,42,42,-63,0,41,42,-26,-26,41,41,0,-45,41,40,26,-56,42,38,45,-63,43,36,56,-56,45,35,63,-45,47,34,63,0,49,35,56,45,50,36,45,45,51,37,0,45,50,38,-26,26,50,38,-26,0,49,38,-45,0,48,38,-56,0,46,38,-63,26,44,39,-68,45,41,40,-68,45,39,41,-68,45,36,42,-63,0,35,41,-45,-56,34,39,-45,-63,33,37,-56,-63,31,35,-68,-79,28,26,-77,-83,22,18,-80,-82,16,10,-78,-82};
//	private static int[] nem1 = new int[] {12,3,71,75,16,11,80,83,24,20,84,83,36,29,85,83,51,38,86,77,66,38,85,-26,78,37,83,-45,85,36,78,-45,88,35,63,-45,89,34,26,0,89,35,26,45,90,36,0,45,89,37,-45,45,88,38,-56,26,86,38,-71,0,82,38,-75,26,78,39,-74,45,75,40,-68,45,73,41,-26,45,74,42,56,26,76,42,56,0,77,42,56,0,79,42,56,0,80,42,45,0,81,42,56,0,83,42,63,0,85,42,68,0,88,42,68,0,90,42,56,-26,91,41,26,-45,91,40,0,-45,91,39,-45,-45,89,38,-56,-26,88,38,-56,0,86,38,-63,0,84,38,-63,0,82,38,-68,0,79,38,-75,0,74,38,-78,26,69,39,-78,45,64,40,-77,45,60,41,-74,45,57,42,-68,26,55,42,-26,-26,56,41,56,-45,58,40,63,-45,60,39,56,-45,61,38,26,-45,61,37,-26,-45,60,36,-56,-45,58,35,-56,-45,57,34,-45,-26,56,34,-26,26,56,35,26,45,57,36,45,45,58,37,26,45,58,38,0,26,58,38,-45,-26,56,37,-63,-45,54,36,-74,-45,49,35,-80,-45,42,34,-82,-77,33,26,-83,-82,23,18,-83,-82,16,10,-78,-82};	
//	private static int[] nem2 = new int[] {9,3,45,75,12,11,77,83,18,20,83,83,29,29,85,83,43,37,85,74,57,36,85,-45,69,35,84,-45,77,34,80,-26,81,34,74,0,84,34,68,0,86,34,63,0,88,34,56,0,89,34,45,0,90,34,26,26,90,35,0,45,90,36,0,26,90,36,-45,0,88,36,-56,-26,87,35,-56,-45,85,34,-56,-26,84,34,-26,26,84,35,0,45,84,36,-26,45,83,37,-26,45,83,38,-26,26,82,38,-45,0,81,38,-56,0,79,38,-63,0,77,38,-63,0,75,38,-56,0,74,38,-26,0,74,38,0,0,74,38,26,0,75,38,45,0,76,38,45,0,77,38,26,-26,77,37,-45,-45,75,36,-68,-45,72,35,-74,-45,68,34,-74,0,65,35,-68,45,63,36,-45,45,63,37,26,45,64,38,56,26,66,38,63,0,68,38,56,0,69,38,0,0,68,38,-26,0,68,38,-45,0,66,38,-56,0,65,38,-56,-26,63,37,-68,-45,60,36,-71,-45,57,35,-74,-45,53,34,-79,-26,46,34,-83,-75,36,26,-84,-82,26,18,-83,-82,18,10,-80,-82};
//	private static int[] nem3 = new int[] {12,3,75,75,17,11,81,83,25,20,83,83,35,29,84,83,45,38,83,77,52,38,80,0,57,38,74,0,59,38,26,0,58,38,-45,0,57,38,-45,0,56,38,-56,0,54,38,-45,0,54,38,26,0,55,38,26,0,55,38,26,0,56,38,26,26,56,39,45,45,58,40,63,45,60,41,63,56,62,43,68,56,65,44,71,45,68,45,71,45,71,46,71,0,74,45,68,-45,76,44,68,-56,79,42,68,-63,81,40,68,-45,84,40,68,0,86,40,68,26,89,41,63,45,90,42,26,26,90,42,0,26,90,43,-26,45,89,44,-26,26,89,44,-26,0,88,44,-45,-26,87,43,-56,-45,85,42,-63,-26,83,42,-68,0,80,42,-63,0,79,42,-26,0,79,42,26,0,80,42,0,0,79,42,-45,0,78,42,-63,0,75,42,-75,0,70,42,-78,0,65,42,-78,0,60,42,-78,-26,55,41,-77,-45,51,40,-75,-45,47,39,-75,-56,43,37,-75,-56,39,36,-75,-45,35,35,-74,-78,32,26,-74,-83,28,18,-75,-82,24,10,-75,-82};

//	public static void main(String[] args) {
//	
//		//System.out.println(mainBuilder(igen1,igen2));
//		//System.out.println(mainBuilder(igen1,igen3));
//		System.out.println(mainBuilder(igen1,nem1));
//		//System.out.println(mainBuilder(igen2,nem2));
//	
//	}
	
	static float mainBuilder(int[] check, int[] db) {
	
		fakeBuffer = 1;
		fakeBufferMultiplier = 1;
		
		for(int i = 0; i < check.length; i = i+4) {
			
			checkTestArray = new int[] {check[i],check[i+1],check[i+2],check[i+3],i/4};
			dbStartIndex = i - checkLength * 4 > 0 ? i - checkLength * 4  : 0;
            dbEndIndex =   i + checkLength * 4 + 4 < db.length ? i + checkLength * 4 + 4: db.length;		
			bestResult = 0;
			
			for(int j = dbStartIndex; j < dbEndIndex; j = j+4) {
				
				checkDBArray = new int[] {db[j],db[j+1],db[j+2],db[j+3],j/4};
				
				actualResult = localCheck(checkTestArray,checkDBArray);
				
				if(actualResult > bestResult)
					bestResult = actualResult;
			}
 
			resultBuffer += bestResult;
			resultBufferCounter++;
			
			if(bestResult == 0)
				addToFakeBuffer(i);
			
			Debug.debug(debugLevel, "VRSCMainLogic mainBuilder i: "+ i+ ", bestResult: "+bestResult 
				+", resultBufferCounter: "+resultBufferCounter+ ", resultBuffer: "+resultBuffer
				+ ", fakeBuffer: "+fakeBuffer +", fakeBufferMultiplier: "+fakeBufferMultiplier +"\n");
		}
	
		return (float) resultBuffer/(resultBufferCounter + fakeBuffer);
	}
	
	static float localCheck(int[] check, int[] db) {
		
		ampVResult = getLengthPercentDiff(check[0], db[0],checkPercentLength);
		freqVResult = getLengthPercentDiff(check[1], db[1],checkPercentLength);
		ampMainAngleResult = getAnglePercentDiff(check[2], db[2],checkPercentAangle);
		freqMainAngleResult = getAnglePercentDiff(check[3], db[3],checkPercentAangle);				
		horizResult = getLengthPercentDiff(check[4], db[4],checkPercentLength);
	
		mainLocalResult = ( ((float)ampVResult / 100) * ((float)ampMainAngleResult / 100) * 
		((float)freqVResult / 100) * ((float)freqMainAngleResult / 100) * ((float)horizResult / 100) ) *100;
		
	//	Debug.debug(1, "VRSCMainLogic localCheck check: "+Arrays.toString(check)+ ", db: "+Arrays.toString(db) 
	//	+", Result: "+ mainLocalResult);	
			
		Debug.debug(debugLevel, "VRSCMainLogic localCheck a_VR: "+ampVResult + ", f_VR: "+freqVResult  
			+ ", a_AR: "+ampMainAngleResult + ", f_AR: "+freqMainAngleResult + ", h_R: "+horizResult
			+ ", main_R: "+mainLocalResult +", check: " +Arrays.toString(check)+ ", db: "+Arrays.toString(db));
		  
			return mainLocalResult;
	}
 
	private static int getLengthPercentDiff(int input1, int input2, int baseHundredPercent) {
		
		percentResult =  (((baseHundredPercent - Math.abs(input1 - input2)) *100 ) / baseHundredPercent);
		percentResult = percentResult > 0 ?  percentResult : 0;
 
		//Debug.debug(1, "VRSCMainLogic getLengthPercentDiff input1: "+ input1+ ", input2: "+input2 
		//+ ", baseHundredPercent: " 	+baseHundredPercent + ", lengthPercentResult: " + percentResult);
		
		return percentResult;
	}
		
	private static int getAnglePercentDiff(int inputAngle1, int inputAngle2,int inputCheckComapre) {
		
		angle1 = inputAngle1 + 90;
		angle2 = inputAngle2 + 90;
		angleResult1 = (((inputCheckComapre - (Math.abs(angle1 - angle2))) * 100 ) / inputCheckComapre);
		angleResult2 = angleResult1 > 0 ?  angleResult1 : 0; 
		
		//Debug.debug(debugLevel, "VRSCMainLogic gerAnglePercentDiff inputAngle1: "+ inputAngle1
		//+ ", inputAngle2: "+inputAngle2 +", limit for 100%: "+inputCheckComapre + ", angleResult: "
		// angleResult2 );
		
		return angleResult2;
	}
	
	private static void addToFakeBuffer(int index) {
		
		fakeBufferMultiplier *= 1.5;
		Debug.debug(debugLevel,"VRSCMainLogic addToFakeBuffer index"+index+", lastFakeIndex"+lastFakeIndex);
		
		if(index - 4 == lastFakeIndex) {
			
			fakeBufferMultiplier *= 1.3;
			Debug.debug(debugLevel, "VRSCMainLogic Repeated Fake! index: " + index 
				+ ", fakeBufferMultiplier: " + fakeBufferMultiplier);
		}
		
		fakeBuffer += fakeBufferMultiplier;
		
		lastFakeIndex = index;
	}

}