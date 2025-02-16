package com.audio5.recognition.slope;

import java.util.Arrays;

import com.audio8.util.Debug;

public class SlopeBuilder {

	public static Slope slope ;
	public static Slope[] slopes;
	public static Slope[] temp;
	public static int minSlopeLength;
	public static int notAddedCounter;
	public static int slopeCounter;
	public static int thisAngle;
	public static int[] edgePoints;
	public static int lastAngle;
	
	public static Slope[]  mainSlopeBuilder(int[] inputArray) {
		
		Debug.debug(3,"SlopeBuilder mainSlopeBuilder NEW inputArray.length: "+ inputArray.length+ ", Array: "+ Arrays.toString(inputArray));
		
		slope = new Slope(0,inputArray[0]);
		slopes = new Slope[50];
		minSlopeLength = 5;
		notAddedCounter = 0;
		slopeCounter = 0;
		thisAngle = 0;
        edgePoints = null;
		lastAngle = inputArray[2] > inputArray[1] ?  getAngle(2,inputArray[2] - inputArray[0] ,2) : -getAngle(2,inputArray[0] - inputArray[2],2);
		slope.setAvgSlopedirection(lastAngle);

		for(int i = 2; i < inputArray.length; i++ ) {

			//System.out.println("slope.getAvgSlopedirection: "+slope.getAvgSlopedirection() + ", Actual slope: " + (inputArray[i] - inputArray[i-1]));

			thisAngle =  getAngle(inputArray[i-2],inputArray[i],2) ;	
			Debug.debug(5,"\n SlopeBuilder mainSlopeBuilder i: "+i +", thisAngle: "+thisAngle+ ", lastAngle: "+lastAngle+", inputArray[i] "+inputArray[i] + ", inputArray[i-2]: "+ inputArray[i-2] 
					 + ", notAddedCounter: "+notAddedCounter);
			
			if(i - slope.getStartHPosition() == 1) 	{	
				slope.setAvgSlopedirection(getAngle(slope.startVPosition, inputArray[i] , i - slope.startHPosition));
				notAddedCounter--;
				Debug.debug(5,"SlopeBuilder mainSlopeBuilder First Slope: "+ ", i: "+i+ ", inputArray[i-1]: "+inputArray[i-1] + ", inputArray[i]: "+inputArray[i]
						+ ", slope: "+ slope.getAvgSlopedirection());
			}
			
			if(i - slope.getStartHPosition() > 1 && angleCheckIsOk(slope.getAvgSlopedirection(),thisAngle,50) && (inputArray[i] - inputArray[i-1]) >= 0) 	{
				slope.setAvgSlopedirection(getAngle(slope.startVPosition, inputArray[i] , i - slope.startHPosition));
				notAddedCounter--;
				Debug.debug(5,"SlopeBuilder mainSlopeBuilder Pozitive slope i: "+i+ ", inputArray[i-1]: "+inputArray[i-1]+ ", inputArray[i]: "+inputArray[i]
						+ ", slope: "+ slope.getAvgSlopedirection());
			}
			
			if(i - slope.getStartHPosition() > 1 && angleCheckIsOk(slope.getAvgSlopedirection(),thisAngle,50)&& (inputArray[i] - inputArray[i-1]) < 0) 	{
				slope.setAvgSlopedirection(getAngle(slope.startVPosition, inputArray[i] , i - slope.startHPosition));
				notAddedCounter--;
				//System.out.println("bla; "+ ( i - slope.startHPosition +", slope.startHPosition: "+slope.startHPosition));
				Debug.debug(5,"SlopeBuilder mainSlopeBuilder Negative slope i: "+i+ ", inputArray[i-1]: "+inputArray[i-1]+ ", inputArray[i]: "+inputArray[i]
						+ ", slope: "+ slope.getAvgSlopedirection());
			}
			
			if((i - slope.getStartHPosition() > minSlopeLength && notAddedCounter >= 1) ) {
				
				edgePoints = getEdgepoints(inputArray,i-minSlopeLength,minSlopeLength);
				
				if(edgePoints[0] - slope.getStartHPosition() > 6 || Math.abs(inputArray[edgePoints[0]] - slope.startVPosition) >= 10) {
					
					Debug.debug(5,"SlopeBuilder mainSlopeBuilder Math.abs(inputArray[edgePoints[0]] - slope.startVPosition) "+Math.abs(inputArray[edgePoints[0]] - slope.startVPosition));
					slope.sethLength(edgePoints[0] - slope.getStartHPosition());
					slope.setvLength(inputArray[edgePoints[0]] > slope.startVPosition ?  inputArray[edgePoints[0]] - slope.startVPosition : slope.startVPosition - inputArray[edgePoints[0]]) ;
					slope.setEndHPosition(edgePoints[0]); 
					slope.setEndVPosition(inputArray[edgePoints[0]]);
					slope.sethPosition((slope.getStartHPosition() + slope.getEndHPosition()) / 2);
					slope.setvPosition((slope.getStartVPosition() + slope.getEndVPosition()) / 2);
					slope.setMainLength((int) Math.sqrt(Math.pow(slope.hLength,2)+ Math.pow(slope.vLength,2)));
					slopes[slopeCounter++] = slope;
					i = slope.endHPosition != 0  ?  edgePoints[1] : 2;
					Debug.debug(3,"SlopeBuilder mainSlopeBuilder Added Slope! i: "+i+ ", endHPosition: "+inputArray[edgePoints[0]]+ ", inputArray[i]: "+inputArray[i]+", Slope: "+slope.toString());
				}
				
				notAddedCounter = 0;
				slope = new Slope(edgePoints[1],inputArray[edgePoints[1]]);
				slope.setAvgSlopedirection(thisAngle);
				edgePoints =null;
				
				Debug.debug(3,"SlopeBuilder mainSlopeBuilder New slope Started! at i: "+i + ", thisAngle: "+thisAngle + ", slope: "+slope.getAvgSlopedirection()+" " +  slope.toString());
			}

			lastAngle = thisAngle;
			notAddedCounter++;
			//System.out.println("lastAngle : "+lastAngle);
		}
		
		if(inputArray.length -slope.startHPosition > 5 ) {
			
			slope.sethLength(inputArray.length-1- slope.getStartHPosition());
			slope.setvLength(inputArray[inputArray.length-1] > slope.startVPosition ?
				inputArray[inputArray.length-1] - slope.startVPosition :
					slope.startVPosition - inputArray[inputArray.length-1]) ;
			slope.setEndHPosition(inputArray.length-1); 
			slope.setEndVPosition(inputArray[inputArray.length-1]);
			slope.sethPosition((slope.getStartHPosition() + slope.getEndHPosition()) / 2);
			slope.setvPosition((slope.getStartVPosition() + slope.getEndVPosition()) / 2);
			slope.setMainLength((int) Math.sqrt(Math.pow(slope.hLength,2)+ Math.pow(slope.vLength,2)));
			slopes[slopeCounter++] = slope;
			Debug.debug(3,"SlopeBuilder mainSlopeBuilder Added Slope! i: "+inputArray.length
				+ ", inputArray[i-1]: "+inputArray[inputArray.length-2]+ ", inputArray[i]: "
					+inputArray[inputArray.length-1]+", Slope: "+slope.toString());
		}
			slopes  = cleanNullSlopes();
			
		Debug.debug(3,"SlopeBuilder mainSlopeBuilder END slopeCounter: "+ slopeCounter);
			return slopes;
	}
	
	private static Slope[] cleanNullSlopes() {
		
		temp = new Slope[slopeCounter];
		
		for(int i = 0; i < temp.length; i++) 
			temp[i] = slopes[i];
		
		return temp;
	}
	

	private static boolean angleCheckIsOk(int lastAngle, int thisAngle, int check) {
		
		//System.out.println("lastAngle: "+lastAngle+", thisAngle: "+thisAngle+", check: "+check);
		
		if((thisAngle < lastAngle && thisAngle < 0 && lastAngle <  0) || (thisAngle > lastAngle && thisAngle > 0 && lastAngle > 0) ) {
			Debug.debug(5,"SlopeBuilder angleCheckIsOk 1.0 lastAngle: "+lastAngle+", thisAngle: "+thisAngle+", check: "+check);
			return true;
		}
		if((thisAngle < 0 && lastAngle < 0 &&  Math.abs(lastAngle - thisAngle) < check ) || (thisAngle > 0 && lastAngle > 0 && lastAngle - thisAngle < check  )) {
			Debug.debug(5,"SlopeBuilder angleCheckIsOk 2.0 lastAngle: "+lastAngle+", thisAngle: "+thisAngle+", check: "+check);
			return true;
		}
		
		if((lastAngle < 0  && thisAngle > 0 &&  thisAngle - check < lastAngle) || (lastAngle > 0 && thisAngle < 0 && thisAngle + check > lastAngle ) ) {
			Debug.debug(5,"SlopeBuilder angleCheckIsOk 3.0 lastAngle: "+lastAngle+", thisAngle: "+thisAngle+", check: "+check);
			return true;
		}
		else 
			Debug.debug(5,"SlopeBuilder angleCheckIsOk 4.0 lastAngle: "+lastAngle+", thisAngle: "+thisAngle+", check: "+check);
		return false;
	}
	
	private static int getAngle(int y1, int y2, int length) {
		
		if(y1 > y2)
			return  -((int) Math.toDegrees(Math.atan2(length, y2 - y1))-89);
		
		else
			return  (int) Math.toDegrees(Math.atan2(length, y1 - y2))-89;
		//System.out.println("y: "+y+", y1: "+y1);
		
	}
	
	private static int[] getEdgepoints(int[] input, int startIndex, int length) {
		
		if(startIndex < 0)
			startIndex = 0;
		
		int[] retArr = new int[2];
		int negativeCounter = 0;
		int pozitveCounter = 0;
		int lowestEdge = input[startIndex];
		int highestEdge = input[startIndex];
		int lowestIndex = 0;
		int highestIndex = 0;
		int sameIndex = -1;
		
		for(int i = startIndex+1; i < startIndex + length; i++ ) {
			
			if(input[i-1] > input[i]) 
				negativeCounter++;
						
			if(input[i-1] < input[i]) 
				pozitveCounter++;
			
			 if(input[i] < lowestEdge) {
				 lowestEdge = input[i]; 
				 lowestIndex = i;
			 }
			 
			 if(input[i] > highestEdge) {
				 highestEdge = input[i];
				 highestIndex = i;
			 }
			 
			 if(lowestEdge == input[i] || highestEdge == input[i]) {
				 sameIndex = i;
			 }	
			 Debug.debug(3,"SlopeBuilder getEdgepoints i: "+i + ", input[i]: "+input[i] 
					+ ", retArr[0]: "+retArr[0] +", retArr[1]: "+ retArr[1] + ", pozitveCounter: " 
					+ pozitveCounter+", negativeCounter: " +negativeCounter+", lowestEdge: " 
					+ lowestEdge+ ", highestEdge: "+highestEdge+ ", highestIndex: "+highestIndex 
					+ ", lowestIndex: " +lowestIndex );
		}		
		
		retArr[0] = pozitveCounter > negativeCounter ? highestIndex : lowestIndex;
		retArr[1] = input[sameIndex] == input[retArr[0]] ? sameIndex : retArr[0];
		
		if(retArr[0] == 0 || retArr[1] == 0) {
			
			retArr[0] = startIndex + (length/2);
			retArr[1] = startIndex + (length/2);
		}
		
		Debug.debug(3,"SlopeBuilder getEdgepoints retArr[0]: "+retArr[0] +", retArr[1]: "
			+ retArr[1] + ", pozitveCounter: " +pozitveCounter+", negativeCounter: "  
			+negativeCounter+", lowestEdge: " +lowestEdge+ ", highestEdge: "+highestEdge
			+ ", highestIndex: "+highestIndex + ", lowestIndex: " +lowestIndex );
			
		return retArr;		
	}
}
