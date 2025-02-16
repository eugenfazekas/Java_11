package com;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

class Slope {
	
	int h_position;
	int v_position;
	int startHPosition;
	int startVPosition;
	int endHPosition;
	int endVPosition;
	int hLength;
	int vLength;
	int avgSlopedirection;
	int avgSlopePass;
	int hPosition;
	int vPosition;
	
	public Slope(int startHPosition, int startVPosition) {
		
		this.startHPosition = startHPosition;
		this.startVPosition = startVPosition;
	}

	public int getAvgSlopedirection() {
		return avgSlopedirection;
	}

	public void setAvgSlopedirection(int avgSlopedirection) {
		this.avgSlopedirection = avgSlopedirection;
	}

	public int getStartHPosition() {
		return startHPosition;
	}

	public Slope(int startHPosition, int startVPosition, int endHPosition, int endVPosition, int hLength) {
		this.startHPosition = startHPosition;
		this.startVPosition = startVPosition;
		this.endHPosition = endHPosition;
		this.endVPosition = endVPosition;
		this.hLength = hLength;
	}

	@Override
	public String toString() {
		return "Slope [startHPosition=" + startHPosition + ", startVPosition=" + startVPosition + ", endHPosition="
				+ endHPosition + ", endVPosition=" + endVPosition + ", hLength=" + hLength + ", vLength=" + vLength
				+ ", avgSlopedirection=" + avgSlopedirection + "]";
	}	
}

public class Test4 {
	
	private static int RECOGNITION_PERCENT_LIMIT = 93;
	static float sumToDivide;
	static int diveder;
	static float result;
	static int matchCounter = 0;
    static int cycle = 0;
	private static int chekckIndex = 0;
	private static int[] dB_CheckGroup;
	private static int check;
	private static int[] resultArr;
	private static int[][] resultSum;
	
    static int[] DB = new int [] {
    		2, 76, 38, 1, 99, 99, 1, 82, 82, 7, 19, 2, 8, 7, 0, 22, 17, 0, 27, 30, 1, 29, 42, 1, 27, 30, 1, 30, 59, 1, 29, 30, 1, 44, 37, 0, 37, 32, 0, 37, 32, 0, 27, 65, 2, 36, 13, 0, 36, 13, 0, 35, 42, 1, 24, 12, 0, 21, 14, 0, 16, 32, 2, 13, 13, 1, 13, 11, 0, 14, 11, 0, 10, 11, 1, 9, 13, 1, 11, 12, 1, 10, 12, 1, 4, 47, 11, 4, 45, 11, 4, 83, 20, 4, 85, 21, 4, 96, 24, 3, 83, 27, 3, 88, 29, 20, 9, 0, 55, 41, 0, 34, 71, 2, 31, 67, 2, 44, 50, 1, 60, 31, 0, 62, 30, 0, 46, 35, 0, 52, 51, 0, 56, 54, 0, 30, 81, 2, 35, 31, 0, 33, 31, 0, 33, 31, 0, 36, 49, 1, 29, 43, 1, 28, 53, 1, 27, 27, 1, 28, 49, 1, 24, 49, 2, 28, 35, 1, 21, 53, 2, 26, 28, 1, 35, 28, 0, 22, 34, 1, 21, 30, 1, 19, 28, 1, 19, 29, 1, 18, 23, 1, 9, 57, 6, 23, 50, 2, 15, 9, 0, 6, 53, 8, 11, 41, 3, 10, 18, 1, 13, 7, 0, 12, 11, 0, 12, 12, 1, 9, 30, 3, 9, 30, 3, 12, 12, 1, 11, 11, 1, 10, 11, 1
    };
    
    static int[] checkGroup1 = new int[] {
    		2, 76, 38, 1, 99, 99, 1, 82, 82, 7, 19, 2, 8, 7, 0, 22, 17, 0, 27, 30, 1, 29, 42, 1, 27, 30, 1, 30, 59, 1, 29, 30, 1, 44, 37, 0, 37, 32, 0, 37, 32, 0, 27, 65, 2, 36, 13, 0, 36, 13, 0, 35, 42, 1, 24, 12, 0, 21, 14, 0, 16, 32, 2, 13, 13, 1, 13, 11, 0, 14, 11, 0, 10, 11, 1, 9, 13, 1, 11, 12, 1, 10, 12, 1, 4, 47, 11, 4, 45, 11, 4, 83, 20, 4, 85, 21, 4, 96, 24, 3, 83, 27, 3, 88, 29, 20, 9, 0, 55, 41, 0, 34, 71, 2, 31, 67, 2, 44, 50, 1, 60, 31, 0, 62, 30, 0, 46, 35, 0, 52, 51, 0, 56, 54, 0, 30, 81, 2, 35, 31, 0, 33, 31, 0, 33, 31, 0, 36, 49, 1, 29, 43, 1, 28, 53, 1, 27, 27, 1, 28, 49, 1, 24, 49, 2, 28, 35, 1, 21, 53, 2, 26, 28, 1, 35, 28, 0, 22, 34, 1, 21, 30, 1, 19, 28, 1, 19, 29, 1, 18, 23, 1, 9, 57, 6, 23, 50, 2, 15, 9, 0, 6, 53, 8, 11, 41, 3, 10, 18, 1, 13, 7, 0, 12, 11, 0, 12, 12, 1, 9, 30, 3, 9, 30, 3, 12, 12, 1, 11, 11, 1, 10, 11, 1
    };
	
    static int[] checkGroup2 = new int[] {
    		4, 56, 14, 3, 86, 28, 3, 80, 26, 3, 22, 7, 8, 15, 1, 25, 13, 0, 32, 72, 2, 32, 54, 1, 46, 33, 0, 33, 80, 2, 42, 30, 0, 45, 13, 0, 56, 42, 0, 41, 12, 0, 39, 13, 0, 40, 33, 0, 31, 12, 0, 16, 31, 1, 12, 32, 2, 9, 32, 3, 8, 34, 4, 9, 12, 1, 11, 39, 3, 8, 65, 8, 7, 45, 6, 12, 7, 0, 8, 26, 3, 14, 6, 0, 9, 7, 0, 9, 84, 9, 8, 93, 11, 6, 53, 8, 5, 98, 19, 5, 90, 18, 9, 28, 3, 41, 22, 0, 53, 26, 0, 48, 81, 1, 57, 51, 0, 43, 84, 1, 51, 42, 0, 51, 32, 0, 34, 86, 2, 43, 69, 1, 53, 51, 0, 66, 32, 0, 70, 33, 0, 70, 32, 0, 49, 52, 1, 31, 66, 2, 25, 68, 2, 31, 72, 2, 34, 50, 1, 37, 50, 1, 35, 31, 0, 40, 25, 0, 33, 31, 0, 33, 31, 0, 20, 81, 4, 26, 65, 2, 34, 43, 1, 22, 33, 1, 24, 26, 1, 31, 30, 0, 22, 35, 1, 18, 30, 1, 13, 22, 1, 12, 28, 2, 15, 65, 4, 14, 42, 3, 14, 11, 0, 8, 49, 6, 11, 28, 2, 12, 11, 0
    };
    
    static int test[] = new int[]{50,80,100, 50,80,100, 50,80,100, 50,80,100, 50,80,100, 50,80,100, 50,80,100, 50,80,100, 50,80,100, 50,80,100};
    
    public static void compare(int[]  dbInput, int[] checkArray, int slideLength) {
    	
    	int startingSlide = - (slideLength/2)*3;
    	int macthCounter = 0;
    	
    	for(int i = 0; i < slideLength; i++) {
    		
    		macthCounter=0;
    		for(int j = Math.abs(startingSlide); j < dbInput.length-  Math.abs(startingSlide); j++) {
    			
    			macthCounter += calculateMatchProcent2(dbInput[j], checkArray[j+startingSlide]);
    			
    		} 
    		
    		startingSlide = startingSlide + 3;
    		System.out.println("slide: "+i + ",macthCounter: "+macthCounter);
    	}
    	
    	//calculateMatchProcent();
    }
    
	public static int calculateMatchProcent2(int dB_Value, int check_Value) {
		

		return 100 - Math.abs(dB_Value - check_Value );
	}
	
	public static void main(String[] args) {

		int counter = 0;
		int[] useArray  = rebuildArray(checkGroup2,1);
		System.out.println("useArray.length "+ useArray.length);
		int[] spikeArray  = removeSpikes(useArray,1);
		
		int avgLength = 6; 
		int[] avg = new int [useArray.length];
		int[] avg_spike = new int [useArray.length- avgLength];

		
		
		
		for(int i = avgLength/2; i < useArray.length - avgLength/2;i++ ) {
			
			//System.out.println("i"+i);
			//avg[counter++] = (useArray[i-2] + useArray[i-1] + useArray[i] +useArray[i+1]+useArray[i+2]) /5;
			avg[counter++] = avg(useArray, avgLength,i);
		}
		
		counter = 0;
		
		for(int i = avgLength/2; i < useArray.length - avgLength/2; i++ ) {
			//System.out.println("i"+i);
			//avg_spike[counter++] = (avg[i-2] + avg[i-1] + avg[i] +avg[i+1]+avg[i+2]) /5;
			avg_spike[counter++] = avg(avg, avgLength,i);
		}
		
//			writeImageFile(buildImage(spikeArray ,100,100),"voice4");
//			writeImageFile(buildImage(avg ,100,100),"voice5");
//			writeImageFile(buildImage(avg_spike ,100,100),"voice6");
		
		
		mainBuild(avg_spike);
		System.out.println(""+(Math.sqrt(Math.pow(3,2)+ Math.pow(4,2))));
		//compare(avg_spike,avg_spike,10);
		
		
		//compare(test,test,10);		
//		System.out.println("lastAngle: "+ -40 +", thisAngle: "+ -30+", angleIsOk: " +angleCheckIsOk(-40,-30,40)+"\n");		
//		System.out.println("lastAngle: "+ -60 +", thisAngle: "+ -30+", angleIsOk: " +angleCheckIsOk(-60,-30,40)+"\n");
//		System.out.println("lastAngle: "+ -40 +", thisAngle: "+ -90+", angleIsOk: " +angleCheckIsOk(-40,-90,40)+"\n");
//		System.out.println("lastAngle: "+ -60 +", thisAngle: "+ -10+", angleIsOk: " +angleCheckIsOk(-60,-10,40)+"\n");
//		System.out.println("lastAngle: "+ -20 +", thisAngle: "+ 15+", angleIsOk: " +angleCheckIsOk(-20,15,40)+"\n");
//		System.out.println("lastAngle: "+ -20 +", thisAngle: "+ 25+", angleIsOk: " +angleCheckIsOk(-20,25,40)+"\n");
		
//		System.out.println("lastAngle: "+ 20 +", thisAngle: "+ 85+", angleIsOk: " +angleCheckIsOk(20,85,40)+"\n");
//		System.out.println("lastAngle: "+ 80 +", thisAngle: "+ 50+", angleIsOk: " +angleCheckIsOk(80,50,40)+"\n");
//		System.out.println("lastAngle: "+ 80 +", thisAngle: "+ 30+", angleIsOk: " +angleCheckIsOk(80,30,40)+"\n");
//		System.out.println("lastAngle: "+ 20 +", thisAngle: "+ -10+", angleIsOk: " +angleCheckIsOk(20,-10,40)+"\n");
//		System.out.println("lastAngle: "+ 20 +", thisAngle: "+ -25+", angleIsOk: " +angleCheckIsOk(20,-25,40)+"\n");
		
//		System.out.println(" "+calculateMatchProcent2(40,40));
//		System.out.println(" "+calculateMatchProcent2(60,40));
//		System.out.println(" "+calculateMatchProcent2(40,60));
//		System.out.println(" "+calculateMatchProcent2(20,41));
//		System.out.println(" "+calculateMatchProcent2(40,61));
		

//		System.out.println("cos degree: " +getAngle(20,10,1));
//		System.out.println("cos degree: " +getAngle(20,19,1));
//		System.out.println("cos degree: " +getAngle(40,40,1));
//		System.out.println("cos degree: " +getAngle(40,41,1));
//		System.out.println("cos degree: " +getAngle(40,50,1));

		


	}
	
	public static int getAngle(int y1, int y2, int length) {
		
		if(y1 > y2)
			return  -((int) Math.toDegrees(Math.atan2(length, y2 - y1))-89);
		
		else
			return  (int) Math.toDegrees(Math.atan2(length, y1 - y2))-89;
		//System.out.println("y: "+y+", y1: "+y1);
		
	}
	
	public static int avg(int[] inputArray ,int avgLength ,int index) {
		
		int startAvg = index -  avgLength / 2;
		int endAvg = startAvg + avgLength;
		int avg = 0;
		
		for(int i = startAvg; i < endAvg; i++ ) {
			avg +=  inputArray[i];
		}
		
		return avg / avgLength ;
	}
	
	public static boolean angleCheckIsOk(int lastAngle, int thisAngle, int check) {
		
		//System.out.println("lastAngle: "+lastAngle+", thisAngle: "+thisAngle+", check: "+check);
		
		if((thisAngle < lastAngle && thisAngle < 0 && lastAngle <  0) || (thisAngle > lastAngle && thisAngle > 0 && lastAngle > 0) ) {
			System.out.println("1.0 lastAngle: "+lastAngle+", thisAngle: "+thisAngle+", check: "+check);
			return true;
		}
		if((thisAngle < 0 && lastAngle < 0 &&  Math.abs(lastAngle - thisAngle) < check ) || (thisAngle > 0 && lastAngle > 0 && lastAngle - thisAngle < check  )) {
			System.out.println("2.0 lastAngle: "+lastAngle+", thisAngle: "+thisAngle+", check: "+check);
			return true;
		}
		
		if((lastAngle < 0  && thisAngle > 0 &&  thisAngle - check < lastAngle) || (lastAngle > 0 && thisAngle < 0 && thisAngle + check > lastAngle ) ) {
			System.out.println("3.0 lastAngle: "+lastAngle+", thisAngle: "+thisAngle+", check: "+check);
			return true;
		}
		else 
			System.out.println("4.0 lastAngle: "+lastAngle+", thisAngle: "+thisAngle+", check: "+check);
		return false;
	}
	
	public static int[] getEdgepoints(int[] input, int startIndex) {
		
		int[] retArr = new int[2];
		int negativeCounter = 0;
		int pozitveCounter = 0;
		int lowestEdge = input[startIndex];
		int highestEdge = input[startIndex];
		int lowestIndex = 0;
		int highestIndex = 0;
		int sameIndex = -1;
		
		for(int i = startIndex+1; i < startIndex + 5; i++ ) {
			
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
				System.out.println("i: "+i + ", input[i]: "+input[i] + ", retArr[0]: "+retArr[0] +", retArr[1]: "+ retArr[1] + ", pozitveCounter: " +pozitveCounter+", negativeCounter: " 
						+negativeCounter+", lowestEdge: " +lowestEdge+ ", highestEdge: "+highestEdge+ ", highestIndex: "+highestIndex + ", lowestIndex: " +lowestIndex );
		}		
		
		retArr[0] = pozitveCounter > negativeCounter ? highestIndex : lowestIndex;
		retArr[1] = input[sameIndex] == input[retArr[0]] ? sameIndex : retArr[0];

		
		System.out.println("retArr[0]: "+retArr[0] +", retArr[1]: "+ retArr[1] + ", pozitveCounter: " +pozitveCounter+", negativeCounter: " 
		+negativeCounter+", lowestEdge: " +lowestEdge+ ", highestEdge: "+highestEdge+ ", highestIndex: "+highestIndex + ", lowestIndex: " +lowestIndex );
			return retArr;		
	}
	
	public static void mainBuild(int[] inputArray) {
		
		Slope slope = new Slope(0,inputArray[0]);
		Slope [] slopes = new Slope[20];
		int notAddedCounter = 1;
		int slopeCounter = 0;
		int thisAngle = 0;
        int [] edgePoints;
		int lastAngle = inputArray[2] > inputArray[1] ?  getAngle(2,inputArray[2] - inputArray[0] ,2) : -getAngle(2,inputArray[0] - inputArray[2],2);
		slope.setAvgSlopedirection(lastAngle);
		//slope.setAvgSlopedirection(inputArray[1] - inputArray[0]);

		for(int i = 5; i < inputArray.length; i++ ) {

			//System.out.println("slope.getAvgSlopedirection: "+slope.getAvgSlopedirection() + ", Actual slope: " + (inputArray[i] - inputArray[i-1]));

			thisAngle =  getAngle(inputArray[i-2],inputArray[i],2) ;
			
			System.out.println("\ni: "+i +", thisAngle: "+thisAngle+ ", lastAngle: "+lastAngle+", inputArray[i] "+inputArray[i] + ", inputArray[i-2]: "+ inputArray[i-2] 
					 + ", notAddedCounter: "+notAddedCounter);
			
			if(i - slope.getStartHPosition() == 1) 	{	
				slope.setAvgSlopedirection(getAngle(slope.startVPosition, inputArray[i] , i - slope.startHPosition));
				notAddedCounter--;
				System.out.println("First Slope: "+ ", i: "+i+ ", inputArray[i-1]: "+inputArray[i-1] + ", inputArray[i]: "+inputArray[i]
						+ ", slope: "+ slope.getAvgSlopedirection());
			}
			
			if(i - slope.getStartHPosition() > 1 && angleCheckIsOk(slope.getAvgSlopedirection(),thisAngle,50) && (inputArray[i] - inputArray[i-1]) >= 0) 	{
				slope.setAvgSlopedirection(getAngle(slope.startVPosition, inputArray[i] , i - slope.startHPosition));
				notAddedCounter--;
				System.out.println("Pozitive slope i: "+i+ ", inputArray[i-1]: "+inputArray[i-1]+ ", inputArray[i]: "+inputArray[i]
						+ ", slope: "+ slope.getAvgSlopedirection());
			}
			
			if(i - slope.getStartHPosition() > 1 && angleCheckIsOk(slope.getAvgSlopedirection(),thisAngle,50)&& (inputArray[i] - inputArray[i-1]) < 0) 	{
				slope.setAvgSlopedirection(getAngle(slope.startVPosition, inputArray[i] , i - slope.startHPosition));
				notAddedCounter--;
				//System.out.println("bla; "+ ( i - slope.startHPosition +", slope.startHPosition: "+slope.startHPosition));
				System.out.println("Negative slope i: "+i+ ", inputArray[i-1]: "+inputArray[i-1]+ ", inputArray[i]: "+inputArray[i]
						+ ", slope: "+ slope.getAvgSlopedirection());
			}
			
			if((i - slope.getStartHPosition() > 1 && notAddedCounter >= 1) ) {
				
				edgePoints = getEdgepoints(inputArray,i-5);
				
				if(edgePoints[0] - slope.getStartHPosition() > 5 || Math.abs(inputArray[edgePoints[0]] - slope.startVPosition) >= 10) {
					
System.out.println("Math.abs(inputArray[edgePoints[0]] - slope.startVPosition) "+Math.abs(inputArray[edgePoints[0]] - slope.startVPosition));
					slope.hLength = edgePoints[0] - slope.startHPosition;
					slope.vLength = inputArray[i] > slope.startVPosition ?  inputArray[edgePoints[0]] - slope.startVPosition : slope.startVPosition -inputArray[edgePoints[0]] ;
					slope.endHPosition = edgePoints[0]; 
					slope.endVPosition = inputArray[edgePoints[0]];
					slopes[slopeCounter++] = slope;
					i = slope.endHPosition != 0  ?  edgePoints[1] : 2;
					System.out.println("Added Slope! i: "+i+ ", endHPosition: "+inputArray[edgePoints[0]]+ ", inputArray[i]: "+inputArray[i]+", Slope: "+slope.toString());
				}
				

				notAddedCounter = 0;
				slope = new Slope(edgePoints[1],inputArray[i]);
				slope.setAvgSlopedirection(thisAngle);

				System.out.println("New slope not added! " + ", thisAngle: "+thisAngle + ", slope: "+slope.getAvgSlopedirection() +  slope.toString());
			}

			lastAngle = thisAngle;
			notAddedCounter++;
			//System.out.println("lastAngle : "+lastAngle);
		}
		
		if(inputArray.length -slope.startHPosition > 5 ) {
			
			slope.hLength = inputArray.length - slope.startHPosition;
			slope.vLength = inputArray[inputArray.length-1] > slope.startVPosition ?  inputArray[inputArray.length-1] - slope.startVPosition:slope.startVPosition -inputArray[inputArray.length-1] ;
			slope.endHPosition = inputArray.length; 
			slope.endVPosition = inputArray[inputArray.length-1];
			slope.h_position = (slope.startHPosition + slope.endHPosition)/2;
			slope.v_position = (slope.startVPosition + slope.endVPosition)/2;
			
			slopes[slopeCounter++] = slope;
			System.out.println("Added Slope! i: "+inputArray.length+ ", inputArray[i-1]: "+inputArray[inputArray.length-2]+ ", inputArray[i]: "+inputArray[inputArray.length-1]+", Slope: "+slope.toString());
		}
		
		
	}
		
	public static int[] rebuildArray(int[] initArray, int theNeededItem) {
		
		int[] returnArray = new int[initArray.length/3];
		int counter = 0;
		
		for(int i = 0; i < initArray.length; i= i+3) {
			returnArray[counter++] = initArray[i+theNeededItem];
		}

			return returnArray;
	}
	
	public static int[] removeSpikes(int[] inputArray, int spykeLimit) {
		
		int limit = spykeLimit;
		
		int[] temp = new int[inputArray.length+1];
		
		for(int i = 0; i < inputArray.length; i++)
			temp[i] = inputArray[i];
		
		for(int i = 1; i < temp.length-1; i++) {
			
			if(temp[i-1] <= temp[i]-limit && temp[i+1] <= temp[i]-limit) 	{
				temp[i] = temp[i-1] > temp[i+1] ? temp[i-1] : temp[i+1];
				i--;
			}	
			
			else if(temp[i-1] >= temp[i]+limit && temp[i+1] >= temp[i]+limit) {			
				temp[i] = temp[i-1] < temp[i+1] ? temp[i-1] : temp[i+1];
				i--;
			}

		}		
			return temp;
	}
	
	
	public static int calculateMatchProcent(int dB_Value, int check_Value) {

		sumToDivide = dB_Value < check_Value ? dB_Value : check_Value;
		diveder = dB_Value < check_Value ? check_Value : dB_Value  ;
		result = ((float)sumToDivide / diveder) * 100;
		
		//if(result >RECOGNITION_PERCENT_LIMIT )
		//System.out.println("VoiceRecognition calculateMatchProcent dB_Value: "+dB_Value+", check_Value: "+check_Value + ", result: "+result);
		
			return (int)result;
	}
	
	public static BufferedImage buildImage(int[] map,int width, int height) {

		 
		BufferedImage  audioGram = new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB);	  
		    for (int x = 0; x < audioGram.getWidth(); x++) {
		    	
		    	for (int  y = audioGram.getHeight(); y > 0; y--) {
		    		
		    		if(x < map.length && y <= map[x]  ) {
		    			audioGram.setRGB(x, Math.abs(y-audioGram.getHeight()), new Color(0,161,215).getRGB());
		    			System.out.println("buildImage x: "+x + " waveMap[x] "+map[x]+ " waveMap.length: "+map.length);
		    		}
		    	}
		    }	
		    return audioGram;
	}
	
	public static void writeImageFile(BufferedImage image,String fileName) {
		
        File outputImage = new File(fileName+".png");
        
        try {
			ImageIO.write(image, "png", outputImage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
