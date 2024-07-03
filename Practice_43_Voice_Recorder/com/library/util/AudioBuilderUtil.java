  package com.library.util;

import java.util.Arrays;

import com.library.audio.audioGramInitializer.AudioGramInitializer;

public class AudioBuilderUtil {

	 public static int splineSequence = 1;
	 private static int counter;
	 private static int[] array;
	 private static int correctionLength = 3;
	   

	
	public static int[] buildSplineSequence(int[] arr, int length ) {
		
		Debug.debug(2,"AudioBuilderUtil buildSplineSequence arr.length: "+arr.length+ " length: "+ length);
		length = length > 2 ? length : 2; 
		double iterationLength= Math.floor(arr.length/length-1)*length;
		int[] res = new int [arr.length];
		int splineAverage  = 0;
		int dir = 0;
		for (int i = 1 ; i < iterationLength; i+=length) {
			splineAverage = 0;
			dir = 0;
			Debug.debug(5,"AudioBuilderUtil buildSplineSequence main i: "+i+" splineAverage " +splineAverage + " dir "+dir);
			for(int j = 1; j < length; j++) {
				//System.out.println("splinecalc: "+(arr[i+j]-arr[i-1+j]));
				splineAverage+= arr[i+j]-arr[i-1+j];
			}
			Debug.debug(5,"AudioBuilderUtil buildSplineSequence splineAverage: "+splineAverage+" direction: "+dir + " length -1 "+(length-1));
			dir = splineAverage / (length-1);
			Debug.debug(5,"AudioBuilderUtil buildSplineSequence splineAverage: "+splineAverage+" direction: "+dir + " length -1 "+(length-1) + " sum " + (splineAverage / length-1));
			for(int k = 0; k < length; k++) {
				if(i+k < 0 || i+ k +dir < 0) { 
					Debug.debug(5,"AudioBuilderUtil buildSplineSequence bound exception1 "+ (i+k )+" "+(i+ k +dir)+" dir: "+dir);
					res[i+k] = 0;
						continue;
				}
				if(i+k > arr.length || i+ k +dir > arr.length) { 
					Debug.debug(5,"AudioBuilderUtil buildSplineSequence bound exception2 "+ (i+k )+" "+(i+ k +dir)+" dir: "+dir);
					res[i+k] = arr.length ;
					continue;
				}
				else {
					Debug.debug(5,"AudioBuilderUtil buildSplineSequence else i+k: " +(i+ k)+ "  arr[(i-1)]+k+dir "+ (arr[(i-1)]+k*dir)+" dir: "+dir);
					res[i+k]= arr[(i)]+k*dir;
				}
			}
			Debug.debug(5,"AudioBuilderUtil buildSplineSequence splineAverage1 : "+splineAverage / (length-1));
			Debug.debug(5,"AudioBuilderUtil buildSplineSequence splineAverage2 : "+splineAverage);
	}
		//System.out.println(Arrays.toString(res));
		return AudioGramInitializer.waveMap = res;
	}
	
	public static void middleCorrection() {

		for(int i = 0; i < AudioGramInitializer.waveMap.length-correctionLength; i = i+correctionLength) {
			array = new int[correctionLength];
			for (int j = 0; j < correctionLength; j++) {
				array[j] = AudioGramInitializer.waveMap[i+j];
			}
			for (int k = 1; k < correctionLength; k++) {
				sort();
			}
			for (int j = 0; j < correctionLength; j++) {
				AudioGramInitializer.waveMap[i+j] = array[correctionLength/2];
			}
		}
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
	
	public static void filterItems(int filterLowerLimit) {
		
		int i ;
		counter=0;
		
		for(i = 0 ;i < AudioGramInitializer.waveMap.length; i++) 
			counter = AudioGramInitializer.waveMap[i] > filterLowerLimit ? ++counter : counter;
			
		int[] temp = new int[counter];
				
		counter = 0;
		
		for( i = 0; i < AudioGramInitializer.waveCounter; i++ ){
			if(AudioGramInitializer.waveMap[i] > filterLowerLimit) {
				temp[counter++] = AudioGramInitializer.waveMap[i]  ;
			}
		}
		Debug.debug(3,"AudioBuilderUtil filterItems: old length: "+ AudioGramInitializer.waveMap.length+ " new length: "+ temp.length+ " Items:[]: "+Arrays.toString(temp));
		AudioGramInitializer.waveMap = temp;
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
	  
	  public static void rebuildWaveMapByRate(int waveMapRate ) {
			
			if(!AudioGramInitializer.build) return;
			int newWaveMap[] = new int[AudioGramInitializer.waveCounter / waveMapRate];
			
			Debug.debug(2,"AudioBuilderUtil rebuildWaveMapByRate newWaveMap.length: "+ newWaveMap.length +" AnalysisInitializer.waveCounter: "+AudioGramInitializer.waveCounter);
			
			int rate = 0;
			int average = 0;
			int	counter = 0;
			
			for (int i = 0; i < AudioGramInitializer.waveCounter; i++) {
				
				average += AudioGramInitializer.waveMap[i];
				rate++;
				
				if(rate == waveMapRate ) {
					newWaveMap[counter++] = average / waveMapRate;
					average = 0;
					rate = 0;				
				}			
			}
			Debug.debug(4,"AudioBuilderUtil rebuildWaveMapByRate counter: "+counter);
			AudioGramInitializer.waveCounter = newWaveMap.length;
			AudioGramInitializer.waveMap = newWaveMap;
		}
}

 

