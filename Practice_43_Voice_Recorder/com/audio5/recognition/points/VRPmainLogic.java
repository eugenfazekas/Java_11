package com.audio5.recognition.points;

import java.util.Arrays;

import com.audio8.util.Debug;

public class VRPmainLogic {
		
	private static int sumToDivide;
	private static int diveder;
    private static int startingSlide = 0;
	private static int highestSum;
	private static int continuityCounter;
	private static float integrityCounter;
	private static int matchProcentA;
	private static int matchProcentF;
	private static int matchProcentL;
	private static int matchProcentR;
	private static int resultA;
	private static int resultF;
	private static int resultL;
	private static int resultR;
	private static int totalCounter;
	private static int[] DBTestA;
	private static int[] DBTestF;
	private static int[] DBTestR;
	private static int[] checkTestA;
	private static int[] checkTestF;
	private static int[] checkTestR;
	private static int[] resultTestA;
	private static int[] resultTestF;
	private static int[] resultTestL;
	private static int[] resultTestR;
	private static int[][] saveResult = new int[10][];
	private static int uTest;
	
 static int mainLogic(int[]  dbInput, int[] checkArray, int slideLength, int ii, int jj) {
	    	
    	startingSlide = - (slideLength/2)*3;
    	highestSum = 0;
    	sumToDivide = dbInput.length < checkArray.length ? dbInput.length : checkArray.length;
    	diveder = dbInput.length  > checkArray.length ? dbInput.length : checkArray.length;
    	integrityCounter = (float) sumToDivide / diveder;
    	
    	Debug.debug(5,"VoiceRecognitionPointsCheck mainVoiceFinder dbInput[i]: "+dbInput[0] + ", dbInput[i+1]: "+dbInput[1]+ ", dbInput[i+2]: "+dbInput[2]);
    	Debug.debug(5,"VoiceRecognitionPointsCheck mainVoiceFinder checkArray[i]: "+checkArray[0] + ", checkArray[i+1]: "+checkArray[1]+ ", checkArray[i+2]: "+checkArray[2]);
    	Debug.debug(5,"VoiceRecognitionPointsCheck mainVoiceFinder checkArray[i]: "+checkArray[-startingSlide] + ", checkArray[i+1]: "+checkArray[-startingSlide+1]+ ", checkArray[i+2]: "+checkArray[-startingSlide+2]);
    	
    	for(int i = 0; i < slideLength; i++) {

    		int j;
			continuityCounter = 0;
			matchProcentA = 0;
			matchProcentF = 0;
			matchProcentL = 0;
			matchProcentR = 0;
			resultA = 0;
			resultF = 0;
			resultL = 0;
			resultR = 0;
			DBTestA = new int[sumToDivide];
			DBTestF = new int[sumToDivide];
			DBTestR = new int[sumToDivide];
			checkTestA = new int[sumToDivide];
			checkTestF = new int[sumToDivide];
			checkTestR = new int[sumToDivide];
			resultTestA = new int[sumToDivide];
			resultTestF = new int[sumToDivide];
			resultTestL = new int[sumToDivide];
			resultTestR = new int[sumToDivide];
			
    		for(j = Math.abs(startingSlide); j < dbInput.length - Math.abs(startingSlide); j= j+3) {
    			
    			//System.out.println("j: "+j + ", startingSlide: "+startingSlide + ", j+startingSlide: "+(j+startingSlide));
    			if(j > dbInput.length || j+1+startingSlide > checkArray.length) {
    				
    				matchProcentR = (int) (matchProcentR* 0.95);
    				continue;
    			}
    			resultA = 100 - Math.abs(dbInput[j] - checkArray[j+startingSlide]);
    			resultF = 100 - Math.abs(dbInput[j+1] - checkArray[j+1+startingSlide]);
    			resultL = 100 - Math.abs(dbInput[j] - checkArray[j+startingSlide]) < 100 - Math.abs(dbInput[j+1] - checkArray[j+1+startingSlide]) ?
      					  Math.abs(dbInput[j] - checkArray[j+startingSlide]) : 100 - Math.abs(dbInput[j+1] - checkArray[j+1+startingSlide]);
    			
    			if(resultA > 80 && resultF > 70 )
    			resultR =  (resultA * resultF );  		
    			
    			if(resultA < 80 || resultF < 70 )
    				resultR =  - (resultA * resultF );  
    			
    			DBTestA[j/3] = dbInput[j];
    			DBTestF[j/3] = dbInput[j+1];
    			DBTestR[j/3] = dbInput[j+2];
    			checkTestA[j/3] = checkArray[j+startingSlide];
    			checkTestF[j/3] = checkArray[j+1+startingSlide];
    			checkTestR[j/3] = checkArray[j+2+startingSlide];
    			resultTestA[j/3] = resultA;
    			resultTestF[j/3] = resultF;
    			resultTestL[j/3] = resultL; 
    			resultTestR[j/3] = resultR;  
    			
    			matchProcentA += resultA;
    			matchProcentF += resultF;
    			matchProcentL += resultL;
    			matchProcentR += resultR;
    						
    		} 
    		   	totalCounter =  (int) (matchProcentR*integrityCounter/100);
    		
//    		totalCounter = (int) ((Math.pow(2,(double)matchProcentA/1000)* Math.pow(2,(double)matchProcentF/1000) 
//    			* Math.pow(2,(double)matchProcentL/1000) * Math.pow(2,(double)matchProcentR/1000)*integrityCounter));
    		
    		Debug.debug(3,"VoiceRecognitionPointsCheck mainVoiceFinder matchProcentA: "+ Math.pow(2,matchProcentA/1000)
    		+", matchProcentF: "+Math.pow(2,matchProcentF/1000) + ", matchProcentR:  "+Math.pow(2,matchProcentR/1000) 
    				+", continuityCounter: "  + continuityCounter + ", integrityCounter: "+integrityCounter + ", totalCounter: "+totalCounter);
    		
    		if(totalCounter > highestSum) {
    			
    			saveResult[0] = DBTestA;     saveResult[0][0] = uTest/1000000;
    			saveResult[1] = checkTestA;
    			saveResult[2] = resultTestA; saveResult[2][0] = matchProcentA;
    			saveResult[3] = DBTestF;
    			saveResult[4] = checkTestF;
    			saveResult[5] = resultTestF; saveResult[5][0] = matchProcentF;
    			saveResult[6] = DBTestR; 			
    			saveResult[7] = checkTestR;
       			saveResult[8] = resultTestL; saveResult[8][0] = matchProcentL;
       			saveResult[9] = resultTestR; saveResult[9][0] = matchProcentR;
    			//saveResult[9] = resultTestR; saveResult[9][0] = matchProcentR;

    			highestSum = totalCounter;
    		}
    		if(totalCounter < 0) {
				Debug.debug(3,"    DBTestA " + Arrays.toString(DBTestA));
				Debug.debug(3," checkTestA " + Arrays.toString(checkTestA));
				Debug.debug(3,"resultTestA " + Arrays.toString(resultTestA));
				Debug.debug(3,"resultTestA: "+matchProcentA);
				Debug.debug(3,"    DBTestF " + Arrays.toString(DBTestF));
				Debug.debug(3," checkTestF " + Arrays.toString(checkTestF));
				Debug.debug(3,"resultTestF " + Arrays.toString(resultTestF));
				Debug.debug(3,"resultTestF: "+matchProcentF);
				Debug.debug(3,"    DBTestR " + Arrays.toString(DBTestR));
				Debug.debug(3," checkTestR " + Arrays.toString(checkTestR));
				Debug.debug(3,"resultTestR " + Arrays.toString(resultTestL));
				Debug.debug(3,"resultTestR " + Arrays.toString(resultTestR));
				Debug.debug(3,"resultTestR: "+matchProcentR + ", totalCounter: "+ totalCounter
					+ ", highestSum: "+highestSum+", startingSlide: "+startingSlide+", ii: "+ii+", jj: "+jj+"\n");
    		}
    		startingSlide = startingSlide + 3;
    	}
    	
    	for(int i = 0; i < saveResult.length; i++)
    		Debug.debug(3,"VoiceRecognitionPointsCheck mainVoiceFinder Array: "+Arrays.toString(saveResult[i]));
    	
    	Debug.debug(3,"VoiceRecognitionPointsCheck mainVoiceFinder highestSum: "+highestSum);
    	
    		return highestSum;
    }
}
