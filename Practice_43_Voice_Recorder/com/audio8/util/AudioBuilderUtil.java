  package com.audio8.util;

import java.util.Arrays;

import javax.sound.sampled.AudioFormat;

import com.audio4.audioGramBuilder.FrequencyDetails;

public class AudioBuilderUtil {

	 public static int splineSequence = 1;
	 private static int counter;
	 private static int[] array;
	 private static int correctionLength = 3;
	
	public static int[] middleCorrection(int[] waveMap) {

		int[] returnArray = new int[waveMap.length];
		for(int i = 0; i < waveMap.length-correctionLength; i = i+correctionLength) {
			array = new int[correctionLength];
			for (int j = 0; j < correctionLength; j++) {
				array[j] = waveMap[i+j];
			}
			for (int k = 1; k < correctionLength; k++) {
				sort();
			}
			for (int j = 0; j < correctionLength; j++) {
				returnArray[i+j] = array[correctionLength/2];
			}
		}
			return returnArray;
	}
	
    private static void sort() {
	 	
		int temp = 0;
    	
		for (int k = 1; k < correctionLength; k++) {
			if(array[k-1] > array[k]) {
				temp = array[k];
				array[k] = array[k-1];
				array[k-1]=temp;
			}
		}
    }
	
	public static int[] filterIntItems(int[] waveMap, int  filterLowerLimit) {
		
		int i;
		counter=0;
			
		for(i = 0 ;i < waveMap.length; i++) 
			counter = waveMap[i] > filterLowerLimit ? counter+1 : counter;
			
		int[] temp = new int[counter];
				
		counter = 0;
		
		for( i = 0; i < waveMap.length; i++ ){
			if(waveMap[i] > filterLowerLimit) {
				temp[counter++] = waveMap[i]  ;
			}
		}
		Debug.debug(3,"AudioBuilderUtil filterItems: old length: "+ waveMap.length+ " new length: "+ temp.length+ " Items:[]: "+Arrays.toString(temp));
		return temp;
	}
	
	public static String[] filterEmptyStrings(String[] inputStringArray) {
		
		int i; counter = 0;
			
		for(i = 0; i < inputStringArray.length; i++) {
			if(inputStringArray[i] != null)
				counter++;
			if(inputStringArray[i] == null)
				break;
		}
		
		String[] temp = new String[counter];

		for(i = 0; i < counter; i++ ){
			temp[i] = inputStringArray[i]; 
		}
		Debug.debug(3,"AudioBuilderUtil filterEmptyStrings: old length: "+ inputStringArray.length+ " new length: "+ temp.length);
			return temp;
	}
	
	public static String[] mergeStringsAndFilterNull(String[] a, String[] b ) {
		
		int counter1 = 0;
		int counter2 = 0;
		String[] mergedString = new String[a.length + b.length];
		
		for(int i = 0; i < a.length ; i++ )
			if(a[i] != null)
				counter1++;
		
		for(int i = 0; i < b.length ; i++ ) {
			if(b[i] != null)
				counter2++;			
		}
		
		mergedString = new String[counter1+ counter2];
		counter=0;
		
		for(int i = 0; i < counter1 ; i++ )
			mergedString[counter++] = a[i];
		
		for(int i = 0; i < counter2 ; i++ ) {
			mergedString[counter++] = b[i];			
		}
		
		Debug.debug(3,"a.length: "+a.length + ", b.length: "+ b.length+ " new length: "+mergedString.length);
		return mergedString;
	}
	
	   public static double getMaxMagnitude(double[][] spectrogram) {
	       double maxMagnitude = Double.MIN_VALUE;
	       for (double[] row : spectrogram) {
	           for (double value : row) {
	               if (value > maxMagnitude) {
	                   maxMagnitude = value;
	               }
	           }
	       }
	       return maxMagnitude;
	   }
	   
	   public static int getRoundTo(int roundTO, int inputnumber) {
			
			while(inputnumber % roundTO != 0) {
				inputnumber++;
			}		
				return inputnumber;
		}
			
	  public static int sharpGrowerSum(float initvalue, float growerTimes) {
	  	
	  	float sum = 0;
	  	
	  	for(int i = 0; i < growerTimes; i++)
	  		sum +=  initvalue;
	  	
	  	return (int)sum;
	  }
	  
	  public static int[] buildFrequencyArray(int[] samples, AudioFormat audioFormat) {
		  
	 int positive = -1;
	 int negative = 0;
	 int lastSample = 0;
	 int []tempArrray = new int [1000];
	 int frequency = 0;
	 
		  for(int i = 0; i < samples.length; i++) {
			  
				if(samples[i]  > 0 ) { 
					positive++;
				}
				
				if(samples[i]  <= 0) {
					negative++;				
				}
				
				if(samples[i]  > 0 && lastSample <0 ) {
					frequency = FrequencyDetails.getFrequency((int) audioFormat.getSampleRate(), positive+negative);
					tempArrray[counter++] = frequency;
					positive = 0;
					negative = 0;						
				}	
		  }
		  array = new int[counter-1];
		  
		  for(int i = 0; i < array.length; i++) {
			  array[i] = tempArrray[i];
		  }
		  
		  return array;
	  }
	  
	  
	  public static int[] rebuildWaveMapByRate(int[] waveMap, int waveMapLength, int waveMapRate ) {

			int newWaveMap[] = new int[waveMapLength / waveMapRate];
			
			Debug.debug(2,"AudioBuilderUtil rebuildWaveMapByRate newWaveMap.length: "+ newWaveMap.length +" waveMapLength: "+waveMapLength);
			
			int rate = 0;
			int average = 0;
			int	counter = 0;
			
			for (int i = 0; i < waveMapLength ; i++) {
				
				average += waveMap[i];
				rate++;
				
				if(rate == waveMapRate ) {
					newWaveMap[counter++] = average / waveMapRate;
					average = 0;
					rate = 0;				
				}			
			}
			Debug.debug(4,"AudioBuilderUtil rebuildWaveMapByRate counter: "+counter);

			return newWaveMap;
		}
}

 

