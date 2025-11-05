package com.audio5.recognition.area;

import java.util.Arrays;

import com.audio8.util.Debug;

public class VRAMainLogic {
	
	static float ampResult;
	static float ampBuffer;
	static int ampBufferCounter;
	static float[] freqResult;
	static float freqBuffer;
	static int freqBufferCounter;
	static float freqComboResult;
	static float freqComboBuffer;
	static int freqComboBufferCounter;
	static float multiResult;
	static float multiBuffer;
	static int multiBufferCounter;
	static float finalResult;	
	static float oneDBuffer1;
	static int oneDBufferCounter1;
	static float oneDBuffer2;
	static int oneDBufferCounter2;
	static int higherLength;
	static int higherIndex;
	static int actualIndex;
	static int lowerLength;
	static int ampHLength; 
	static int freqHLength; 
	static int overallH;
	static float oneDLocalResult1;  
	static float oneDLocalResult2; 
	static float freq1Result;
	static float freq2Result;
	static float tempResult;
	static float[] result;
	static float endProcent;
	static int i;
	static int j;
	static int lengthResult;
	static int tempLengthResult;
	static float fakeBuffer;
	static float fakeCounter;
	static int debugLevel = 5;
	
	private static float[] oneDCompare(int[] check, int[] db, int[] checkAMP, int[]dbAMP) {
		
		Debug.debug(debugLevel, "\noneDCompare checkArr: " +Arrays.toString(check)+
			", db: " +Arrays.toString(db));
		result = new float[2];
		oneDBuffer1 = 0;
		oneDBufferCounter1 = 0;
		oneDBuffer2 = 0;
		oneDBufferCounter2 = 0;
		
		higherLength =  check.length > db.length ? check.length : db.length;
		lowerLength = check.length < db.length ? check.length : db.length;
		higherIndex = check[check.length-1]>db[db.length-1] ? check[check.length-1]:db[db.length-1];

		oneDLocalResult1  = getLengthPercentDiff( check[1],  db[1],
				check[1] > db[1] ? check[1]:  db[1]);
		oneDBuffer1 = oneDLocalResult1;
		oneDBufferCounter1++;
		
		if(checkAMP != null) {
			
			freq1Result = getLengthPercentDiff(checkAMP[check[0]], dbAMP[db[0]], 100);
			freq2Result = getLengthPercentDiff(checkAMP[check[1]], dbAMP[db[1]], 100);
			oneDLocalResult2 = (((float)freq1Result / 100) * ((float)freq2Result / 100)) *100;
			oneDBuffer2 = oneDLocalResult2;
			oneDBufferCounter2++;
		}
				
		for(j = 1; j < higherLength-1; j++) {

			oneDLocalResult1 = 0;
			oneDLocalResult2 = 0;
			Debug.debug(debugLevel, "oneDCompare j: "+ j+ ", buffer: "+oneDBuffer1+", Check Array: "
					+ Arrays.toString(check) + ", Db Array: " +Arrays.toString(db));

			if(j+1 < lowerLength) {
				
				oneDLocalResult1 = getLengthPercentDiff( check[j+1] - check[j],  db[j+1] - db[j],
					check[j+1] - check[j] > db[j+1] - db[j]?check[j+1] - check[j]:db[j+1] - db[j]);				
				oneDBuffer1 += oneDLocalResult1;
				oneDBufferCounter1++;
				
				actualIndex = check[j+1] < db[j+1] ? check[j+1] : db[j+1]; 
				
				if(checkAMP != null) {
					tempResult = oneDLocalResult1;
					freq1Result = getLengthPercentDiff(checkAMP[check[j]], dbAMP[db[j]], 100);
					freq2Result = getLengthPercentDiff(checkAMP[check[j+1]], dbAMP[db[j+1]], 100);
					
					oneDLocalResult2 = (((float)oneDLocalResult1 / 100) * ((float)freq1Result / 100) 
							* ((float)freq2Result / 100)) *100;
					oneDBuffer2 += oneDLocalResult2;
					oneDBufferCounter2++;				
				}
				Debug.debug(debugLevel, "oneDCompare oneDLocalResult1: "+oneDLocalResult1 
					+ ", oneDLocalResult2: "+oneDLocalResult2 + ", freq1Result: "+freq1Result 
					+", freq2Result: "+freq2Result + ", newLocalResult: "+oneDLocalResult2);
				Debug.debug(debugLevel, "oneDCompare j: "+j+ ", check[i+1]: "+check[j+1] 
					+", check[i]: "+check[j]+", db[i+1]: "+db[j+1] +", db[i]: "+db[j]);
			}
			
			 Debug.debug(debugLevel, "oneDCompare j: "+j+", localResult: "+oneDLocalResult1
					+", bufferCounter: "+oneDBufferCounter1  +", buffer: "+oneDBuffer1 
					+ ",one avg: "+(oneDBuffer1/oneDBufferCounter1));
			//Debug.debug(debugLevel, "i "+i+", localResult: "+localResult+", bufferCounter: "+bufferCounter  
			 //+", buffer: "+buffer + ", avg: "+((float)buffer /bufferCounter));
		}

		endProcent = 100 - getLengthPercentDiff(actualIndex, higherIndex, higherIndex);
		
		Debug.debug(debugLevel, "endProcent "+endProcent + " highest "+ higherIndex + ", actualIndex: "
				+actualIndex);
		
		tempResult = (float) (oneDBuffer1/oneDBufferCounter1)- endProcent ;
		result[0] =  tempResult > 0 ? tempResult: 0;
		
		tempResult = (float) (oneDBuffer2/oneDBufferCounter2)- endProcent ;
		result[1] =  tempResult > 0 ? tempResult: 0;

		Debug.debug(debugLevel,"oneDCompare i: "+j +", result1: "+result[0]+", result2: "+result[1]);
		
			return result;
	}
	
	public static float mainBuilder(int[][][] check, int[][][] db) {
		
		ampBuffer = 0;
		ampBufferCounter = 0;		
		freqBuffer = 0;
		freqBufferCounter = 0;	
		freqComboBuffer = 0;
		freqComboBufferCounter = 0;
		multiBuffer = 0;
		multiBufferCounter = 0;		
		fakeBuffer = 0;
		fakeCounter = 1;	
		
		ampHLength  = check[0].length > db[0].length ? check[0].length : db[0].length; 
		freqHLength = check[1].length > db[1].length ? check[1].length : db[1].length; 
		overallH = ampHLength > freqHLength ? ampHLength : freqHLength;
		
		for(int i = 25; i < overallH ; i++) {
			
			ampResult = 0;
			freqResult = new float[2];
			freqComboResult = 0;
			multiResult = 0;
		//Debug.debug(debugLevel, "twoDCompare i: " +i + ", check 0: "+ Arrays.toString(check[0][i])
			//+ ", db 0: "+ Arrays.toString(check[0][i]));
		//	Debug.debug(debugLevel, "twoDCompare i: " +i + ", check 1: "+  Arrays.toString(check[1][i])
			//+ ", db 1: "+ Arrays.toString(check[1][i]));

			
//			if(i  < check[1].length  && i < db[1].length && check[1][i].length  == 4 && db[1][i].length == 4) 
//				continue;
			
			if(i < check[0].length && i < db[0].length) {
				ampResult = oneDCompare(check[0][i], db[0][i], null, null)[0];
				ampBuffer += ampResult;
				ampBufferCounter++;
			}
			
			if(i < check[1].length && i < db[1].length) {
				
				freqResult = oneDCompare(check[1][i], db[1][i], check[0][0], db[0][0]);
				freqBuffer += freqResult[0];
				freqBufferCounter++;
				
				freqComboBuffer += freqResult[1];
				freqComboBufferCounter++;
				
				if(ampResult > 0 && freqResult[0] > 0) {
					
					multiResult =  ((float) ((float)ampResult / 100) * ((float)freqResult[0] / 100)) *100;
					multiBuffer += multiResult;
					multiBufferCounter++;
				}				
			}
			
			
//			if(freqResult == 0 )			
//				resultBuffer -= 50;
			
			Debug.debug(debugLevel, "twoDCompare i: " +i+", A_R: "+ampResult +", F_R: "+freqResult[0]
				+", C_R: "+freqResult[1] +", M_R: " +multiResult + ", A_Buff: "+ampBuffer
				+ ", F_Buff: "+freqBuffer + ", F_C_Buff: "+freqComboBuffer + ", M_Buff: "+multiBuffer 
				+ ", fakeBuffer: "+fakeBuffer + ", AVG: "+ (ampBuffer/ (ampBufferCounter+fakeBuffer)) 
				+ ", FVG: "+ (freqBuffer/ (freqBufferCounter+fakeBuffer)) + ", CVG: "
				+ (freqComboBuffer/ (freqComboBufferCounter+fakeBuffer))+ ", MVG: "
				+ (multiBuffer/ (multiBufferCounter+fakeBuffer)));
					
			if((i >= check[0].length &&  i < db[0].length) || (i < check[0].length &&  i >= db[0].length))
				addToFakeBuffer(ampBufferCounter,1);
			
			if((i >= check[1].length &&  i < db[1].length) || (i < check[1].length &&  i >= db[1].length))
				addToFakeBuffer(freqBufferCounter,1);
		}

		finalResult = ((float) ((float)(freqBuffer/ (freqBufferCounter+fakeBuffer)) / 100) 
				* ((float)(freqComboBuffer/ (freqComboBufferCounter+fakeBuffer) / 100)) *100);
		Debug.debug(debugLevel, "finalResult "+finalResult);
		return finalResult;
	}
	
	private static int getLengthPercentDiff(int input1, int input2, int baseHundredPercent) {

		Debug.debug(debugLevel, "getLengthPercentDiff input1: "+ input1+ ", input2: "+input2 
			+ ", baseHundredPercent: " +baseHundredPercent);

		baseHundredPercent  = baseHundredPercent > 0 ? baseHundredPercent : 1;
		tempLengthResult = (baseHundredPercent - Math.abs(input1 - input2));
		tempLengthResult = tempLengthResult != 0 ? tempLengthResult : 1;
		lengthResult =  (tempLengthResult *100 ) / baseHundredPercent;
		lengthResult = lengthResult > 0 ?  lengthResult : 0;

//		Debug.debug(debugLevel, "getLengthPercentDiff input1: "+ input1+ ", input2: "+input2 
		//+ ", baseHundredPercent: " +baseHundredPercent
//				+ ", lengthResult: "+ lengthResult );
		
		return lengthResult;
	}
	
	private static void addToFakeBuffer(int lengthCounter, int source) {
		
		fakeBuffer +=  fakeCounter *((float)lengthCounter / 90);
		fakeCounter *=1.20;		
		
		//Debug.debug(debugLevel, "\n ADDToFAKEBuffer fakeCounter: "+ fakeCounter + ", fakeBuffer: "
		//+fakeBuffer  + ", Source: "+source+ "\n");
	}
}
