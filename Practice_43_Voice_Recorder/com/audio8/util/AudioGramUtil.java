package com.audio8.util;

public class AudioGramUtil {
	
	static int[] useArray;
	static int counter;
	
	public static int[] optimezeAmplitudeMapHeight(int[] inputArray) {
		
		float highest = 0;
		float sum = 0;
		float multiplier = 2f;
		int i = 0;

		useArray = new int[inputArray.length];
		
		for( i = 1; i < inputArray.length -1; i++) {
		
			sum = (inputArray[i-1] + inputArray[i] + inputArray[i+1]) / 3;
			
			if(sum > highest)
				highest = sum;
		}
		//double root = Math.pow(800, 1.0 / 4.0);
		double root = Math.pow(highest, 1.0/4.0);
		sum = 0;
		//System.out.println(""+ root);
		
		for(i = 0; i < 100; i++) {
			
			multiplier += 0.05;
			sum = (float) Math.pow(multiplier, root);
			
			//System.out.println("sum "+sum + ", highest: " +highest + ", multiplier: "+multiplier );	
			
			if(sum >70)  {
			//System.out.println("sum "+sum);	
				break;
			}
			
		}

		Debug.debug(3,"AudioGramUtil optimezeAmplitudeMapHeight multiplier "+multiplier);
		
		for(i = 0; i < inputArray.length; i++) {
			root =  Math.pow(inputArray[i], 1.0/4.0);
			useArray [i] = (int) Math.pow(root,multiplier)+1;
		}
		return useArray;
	}
	
	public static int[] buildAvgArray(int[] inputArray,int avgLength) {
		
		useArray = new int[inputArray.length - avgLength];
		counter = 0;
		
		for(int i = avgLength/2; i < inputArray.length - avgLength/2;i++ ) {

			useArray[counter++] = avg(inputArray, avgLength,i);
		}
			return useArray;
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
	
	public static int getAngle(int y1, int y2, int length) {
		
		if(y1 > y2)
			return  -((int) Math.toDegrees(Math.atan2(length, y2 - y1))-90);
		
		else
			return  (int) Math.toDegrees(Math.atan2(length, y1 - y2))-90;
		//System.out.println("y: "+y+", y1: "+y1);
		
	}
	
	public static int[] removeSpikes(int[] inputArray, int spykeLimit) {
		
		int limit = spykeLimit;
		
		int[] temp = new int[inputArray.length];
		
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
	
	public static boolean angleCheckIsOk(int lastAngle, int thisAngle, int check) {
		
		//System.out.println("lastAngle: "+lastAngle+", thisAngle: "+thisAngle+", check: "+check);
		
		if((thisAngle < lastAngle && thisAngle < 0 && lastAngle <  0) || (thisAngle > lastAngle && thisAngle > 0 && lastAngle > 0) ) {
			System.out.println("1.0 lastAngle: "+lastAngle+", thisAngle: "+thisAngle+", check: "+check);
			return true;
		}
		if((thisAngle < 0 && lastAngle < 0 &&  lastAngle + thisAngle < check ) || (thisAngle > 0 && lastAngle > 0 && lastAngle - thisAngle < check  )) {
			System.out.println("2.0 lastAngle: "+lastAngle+", thisAngle: "+thisAngle+", check: "+check);
			return true;
		}
		else 
			System.out.println("3.0 lastAngle: "+lastAngle+", thisAngle: "+thisAngle+", check: "+check);
		return false;
	}
	
	public static double getMaxMagnitude(double[][] spectrogram) {
		   
	    double maxMagnitude = Double.MIN_VALUE;
	       
	    for (double[] row : spectrogram) {
	    	   
	        for (double value : row) {
	        	   
	            if (value > maxMagnitude) 
	                   maxMagnitude = value;    
	        }
	    }
	       return maxMagnitude;
	}
}
