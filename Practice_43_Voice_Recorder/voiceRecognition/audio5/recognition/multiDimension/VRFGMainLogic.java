package voiceRecognition.audio5.recognition.multiDimension;

import voiceRecognition.audio8.util.Debug;

public class VRFGMainLogic {
	
	private static float resultBuffer;
	private static int resultBufferCounter;

	private static float localResult;
	private static float tempResult;
	private static int[][] testArray;
	
	private static float lengthResult;
	
	private static int i;
	private static int j;
	
	private static int v;
	private static int h;
	
	private static int iLength;
	private static int jLength;
	
	private static int vStartPosition;
	private static int hStartPosition;
	
	private static int vWindowsSize = 3;
	private static int hWindowsSize = 6;
	
	private static float val_1,val_2,val_3,val_4,val_5,val_6;
	
	private static float fakeBuffer;
	private static float fakeBufferMultiplier;
	private static float fakeBufferMultiplierSingle = 1.005f;
	private static float fakeBufferMultiplierContinue = 1.2f;
	private static float fakeBuffercheck;
	private static int lastFakeIndex;
	
	private static int magLimit = 3;
	private static int magnitudePercent100 = 20;
	
	private static int debug_level_INFO = 5;
	private static int debud_level_DEBUG = 5;

	public static float localCompare(int[][] db, int[][] testData, int vStart, int hStart
			, int hWindowSize, int vWindowSize) {
		
		fakeBuffercheck = 0;
		tempResult = 0;
		localResult = 0;
		
		for(v = vStart; v < vStart + vWindowSize; v++ ) {
						
			for(h = hStart; h < hStart + hWindowSize; h++ ) {
				
				if((db[v][h] > magLimit && testData[0][0] == 0 && db[v+2][h] > magLimit && testData[2][0] == 0) || 
				   (db[v][h] == 0 && testData[0][0] > magLimit && db[v+2][h] == 0 && testData[2][0] > magLimit)) {
					fakeBuffercheck += Math.abs(db[v+1][h] - testData[1][0]) ;
					Debug.debug(debud_level_DEBUG,"VRFGMainLogic v: "+v +", h:" +h 
						+ ", fakeBuffercheck: "+fakeBuffercheck);
				}
				
				val_1 = getLengthPercentDiff(db[v][h], testData[0][0],magnitudePercent100);
				val_2 = getLengthPercentDiff(db[v][h+1], testData[0][1],magnitudePercent100); 
				val_3 = getLengthPercentDiff(db[v+1][h], testData[1][0],magnitudePercent100); 
				val_4 = getLengthPercentDiff(db[v+1][h+1], testData[1][1],magnitudePercent100); 
				val_5 = getLengthPercentDiff(db[v+2][h], testData[2][0],magnitudePercent100); 
				val_6 = getLengthPercentDiff(db[v+2][h+1], testData[2][1],magnitudePercent100); 
				
				tempResult=((val_1/100)*(val_2/100)*(val_3/100)*(val_4/100)*(val_5/100)*(val_6/100))*100; 

				if(tempResult > 1000) {
					Debug.debug(debud_level_DEBUG,"VRFGMainLogic v: "+v+", h: "+h +", val_1: "+val_1
						+", val_2: "+val_2+", val_3: "+val_3 +", val_4: "+val_4+", val_5: "+val_5+", val_6: "
						+val_6 + ", tempResult: "+tempResult);
				}
				
				if(tempResult > localResult)
					localResult = tempResult;
			}
		} 
			if(fakeBuffercheck > 1 && localResult == 0) {
				addToFakeBuffer(h);
				
			}Debug.debug(debud_level_DEBUG,"VRFGMainLogic fakeBuffercheck: "+fakeBuffercheck 
				+ ", localResult: "+localResult);
			
			return localResult;
	}
	
	static float mainBuilder(int[][] db, int[][] check) {
		
		resultBuffer = 0;
		resultBufferCounter = 0;
		fakeBuffer = 0; 
		fakeBufferMultiplier = 1;
		lastFakeIndex = 0;
		iLength = db.length-vWindowsSize/2-2;
		jLength = db[i].length-hWindowsSize/2;
		
		for(i = 0; i < iLength; i++) {
			
			//Debug.debug(debud_level_DEBUG,"VRFGMainLogic i: "+i + ", db[i].length " + db[i].length);
			
			if(i == check.length-3)
				break;

			for(j = 1; j < jLength; j++) {
			
				Debug.debug(debud_level_DEBUG,"VRFGMainLogic mainBuilder i: "+i+", j: "+j + " "
					+(db[i].length-hWindowsSize/2) + ", db.length "+db.length +", db[i].length " +db[i].length);
				
				if(j >check[i].length-2)
					break;
							
				vStartPosition = i - vWindowsSize / 2 > -1 ? i - vWindowsSize / 2 : 0; 
				hStartPosition = j - hWindowsSize / 2 > 0 ? j - hWindowsSize / 2 : 1; 
				
				testArray = new int[][] {{check[i][j],check[i][j+1]}
				                        ,{check[i+1][j],check[i+1][j+1]}
				                        ,{check[i+2][j],check[i+2][j+1]} };
//				 Debug.debug(debud_level_DEBUG,"VRFGMainLogic i: "+i + ", j: "+j+" check[i].length: "
//				       +check[i].length+" db[i].length: " +db[i].length+" "+ Arrays.toString(testArray[0])+ " " 
//				                        + Arrays.toString(testArray[1]) + " " +Arrays.toString(testArray[2]));                       
				resultBuffer += 
					localCompare(db,testArray, vStartPosition, hStartPosition, hWindowsSize, vWindowsSize);
				resultBufferCounter++;			
			}
			//Debug.debug(debud_level_DEBUG,"VRFGMainLogic ResultBuffer: "+resultBuffer +", ResultBufferCounter: "
			//+resultBufferCounter+", Result " + (resultBuffer/resultBufferCounter));
		}
		
		Debug.debug(debug_level_INFO,"VRFGMainLogic mainBuilder ResultBuffer: "+resultBuffer 
			+", ResultBufferCounter: "+resultBufferCounter + ",fakeBufferMultiplier: "+ fakeBufferMultiplier
			+", fakeBuffer: "+fakeBuffer +", Result " + (resultBuffer/(resultBufferCounter + fakeBuffer)));
		
		return resultBuffer/(resultBufferCounter + fakeBuffer);
	}
	
	static float getLengthPercentDiff(int input1, int input2, int baseHundredPercent) {

		if((input1 == 0  && input2 != 0) || input1 != 0 && input2 == 0) {
			
			return 0;
		}
	
		if(input1 == 0  && input2 == 0)
			return 0;
		
		lengthResult =  (((baseHundredPercent - Math.abs(input1 - input2)) *100 ) / baseHundredPercent);

//		Debug.debug(debud_level_DEBUG,"VRFGMainLogic getLengthPercentDiff input1: "+ input1+ ", input2: "
//		+input2 + ", onePercent: "+ ", lengthResult: "+ lengthResult );
		lengthResult = lengthResult < 0 ? 0: lengthResult;
		return lengthResult;
	}

	private static void addToFakeBuffer(int index) {
		
		fakeBufferMultiplier *= fakeBufferMultiplierSingle;
		Debug.debug(debud_level_DEBUG,"VRFGMainLogic addToFakeBuffer index"+index
			+", lastFakeIndex"+lastFakeIndex);
		
		if(index-1 == lastFakeIndex) {
			
			fakeBufferMultiplier *= fakeBufferMultiplierContinue;
			Debug.debug(debud_level_DEBUG,"VRFGMainLogic addToFakeBuffer Repeated Fake! index: " 
					+ index + ", fakeBufferMultiplier: " 
					+ fakeBufferMultiplier);
		}
		
		fakeBuffer += fakeBufferMultiplier;
		
		lastFakeIndex = index;
	}
}
