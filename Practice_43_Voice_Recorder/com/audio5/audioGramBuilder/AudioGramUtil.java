package com.audio5.audioGramBuilder;

import java.util.Arrays;

import com.audio8.util.Debug;

public class AudioGramUtil {
	
	static int[] useArray;
	static int counter;
	
	static int[] optimezeAmplitudeMapHeight(int[] inputArray) {
		
		float highest = 0;
		float sum = 0;
		int pos = 0 ;
		float multiplier = 2f;
		int i = 0;

		useArray = new int[inputArray.length];
		
		for( i = 1; i < inputArray.length -1; i++) {
		
			sum = (inputArray[i-1] + inputArray[i] + inputArray[i+1]) / 3;
			
			if(sum > highest) {
				highest = sum;
				pos = i;
			}
		}
		//double root = Math.pow(800, 1.0 / 4.0);
		double root = Math.pow(inputArray[pos], 1.0/4.0);
		sum = 0;
		//System.out.println(""+ root);
		
		for(i = 0; i < 150; i++) {
			
			multiplier += 0.05;
			sum = (float) Math.pow(root ,multiplier);

			if(sum >90)  {
				Debug.debug(3,"AudioGramUtil optimezeAmplitudeMapHeight sum "+sum);	
				break;
			}
			
		}
		Debug.debug(3,"AudioGramUtil optimezeAmplitudeMapHeight Array: "
				+ Arrays.toString(inputArray));
		Debug.debug(3,"AudioGramUtil optimezeAmplitudeMapHeight highest: " + highest+ ", root: " 
				+root+", sum: "+sum +", multiplier "+multiplier);
		
		for(i = 0; i < inputArray.length; i++) {
			root =  Math.pow(inputArray[i], 1.0/4.0);
			useArray [i] = (int) Math.pow(root,multiplier)+1;
		}
		return useArray;
	}
	
	static int[] buildAvgArray(int[] inputArray,int avgLength) {
		
		if(inputArray.length - avgLength < 0)
			return inputArray;
		
		useArray = new int[inputArray.length - avgLength];
		counter = 0;
		
		for(int i = avgLength/2; i < inputArray.length - avgLength/2;i++ ) {

			useArray[counter++] = avg(inputArray, avgLength,i);
		}
			return useArray;
	}

	static int avg(int[] inputArray ,int avgLength ,int index) {
		
		int startAvg = index -  avgLength / 2;
		int endAvg = startAvg + avgLength;
		int avg = 0;
		
		for(int i = startAvg; i < endAvg; i++ ) {
			avg +=  inputArray[i];
		}
		
		return avg / avgLength ;
	}
	
	static int getAngle(int y1, int y2, int length) {
		
		if(y1 > y2)
			return  -((int) Math.toDegrees(Math.atan2(length, y2 - y1))-90);
		
		else
			return  (int) Math.toDegrees(Math.atan2(length, y1 - y2))-90;
		//System.out.println("y: "+y+", y1: "+y1);
		
	}
	
	static int[] removeSpikes(int[] inputArray, int spykeLimit) {
		
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
	
	static boolean angleCheckIsOk(int lastAngle, int thisAngle, int check) {
		
		//System.out.println("lastAngle: "+lastAngle+", thisAngle: "+thisAngle+", check: "+check);
		
		if((thisAngle < lastAngle && thisAngle < 0 && lastAngle <  0) 
				|| (thisAngle > lastAngle && thisAngle > 0 && lastAngle > 0) ) {
			
			Debug.debug(1,"AudioGramUtil 1.0 lastAngle: "+lastAngle+", thisAngle: "+thisAngle
					+", check: "+check);
			
			return true;
		}
		
		if((thisAngle < 0 && lastAngle < 0 &&  lastAngle + thisAngle < check ) 
				|| (thisAngle > 0 && lastAngle > 0 && lastAngle - thisAngle < check  )) {
			
			Debug.debug(1,"AudioGramUtil 2.0 lastAngle: "+lastAngle+", thisAngle: "+thisAngle
					+", check: "+check);
			
			return true;
		}
		
		else 
			Debug.debug(1,"AudioGramUtil 3.0 lastAngle: "+lastAngle+", thisAngle: "+thisAngle
					+", check: "+check);
		
		return false;
	}
	
    static int getBuffersLengthByMilisec(int sampleRate, int milisec) {
   		
    	return (milisec * sampleRate) /1000;
    }
	
	static int[]  buildSequenceBorders(int[] input, int length, int  startLimit, int endLimit) {
		
		Debug.debug(1,"AudioGramUtil buildSequenceBorders input Array:  "  + Arrays.toString(input));
		int start = 0;
		int end = 0;
		int[] result;
		for(int i = 0; i < length; i++) {
			
			if(input[i]>startLimit) { 
				start = i > 1 ? i-2 : 0;
				break;
			}
		}
		
		for(int i = length-1; i > 0; i--) {
			
			if(input[i]>endLimit) {
				end = i < input.length-2 ? i+2 : i;
				break;
			}		
		}
		
		result = new int[] {1,start,input[start], -1,end,input[end]};
		Debug.debug(1,"AudioGramUtil buildSequenceBorders "+ Arrays.toString(result));
		
		return result;
	}
}
