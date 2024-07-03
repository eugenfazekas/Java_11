package com;

import java.text.DecimalFormat;
import java.util.Arrays;



public class MainTest2 {

	static int[][] waveSequenceArray;
	static int full_Sequence[];
	static int newSequence[];
	private static int[] array;
	private static int correctionLength = 3;
	static int waveMap[] = new int [] {3,-5,1,-2,10,8,-7,-12,11};
	
	static int[] test000 = new int[] {};
	static int[] test00 = new int[] {10};
	static int[] test0 = new int[] {10,25};
	static int[] test1 = new int[] {10,25, 100, 300};
	static int[] test2 = new int[] {10,25, 100, 300,500,800};
	static int[] test3 = new int[] {10,25, 100, 300,500,800,1500,2000};
	static int[] test4 = new int[] {10,25, 100, 300,500,800,1500,2000,3000,5000};

	
	public static void main(String[] args) {

		//buildHalfWave(test00,4,3000);
		//int test = (44100 / (2 * 1) /2205) % == 0 ? (44100 / (2 * 1) /2205) : (44100 / (2 * 1) /2205+1) ;
		

		middleCorrection();
		
		System.out.println(Arrays.toString(waveMap));
	}
	
	public static void middleCorrection() {

		for(int i = 0; i < waveMap.length-correctionLength; i = i+correctionLength) {
			array = new int[correctionLength];
			for (int j = 0; j < correctionLength; j++) {
				array[j] = waveMap[i+j];
			}
			for (int k = 1; k < correctionLength; k++) {
				sort();
			}
			for (int j = 0; j < correctionLength; j++) {
				waveMap[i+j] = array[correctionLength/2];
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
	
	private static int [] buildWaveSequence(int[] wave, int cycles) {
		
		Debug.debug("SoundBuilder buildWaveSequence wave"+ Arrays.toString(wave) ,3);
	    int[] waveSequence = new int[wave.length * cycles];
	     int cyclesCounter = 0;
	     int counter = 0;
	     
		for (int i = 0; i < wave.length; i++ ) {
			
			waveSequence[counter] = wave[i];
			System.out.println("waveSequence counter:  "+counter + " i: "+i+" waveSequence[counter]: " +waveSequence[counter]);
			counter++;

			//System.out.println("counter: "+counter+" i: "+i+" cycles: "+cycles );
			if(i == wave.length-1 && cyclesCounter < cycles) {
				
				//if(counter < wave.length*cycles )
				//waveSequence[counter++] = wave[i];
				i = -1; 
				System.out.println("counter: "+counter+ " i: "+ i);
				cyclesCounter++;
			}
			
			if(cyclesCounter == cycles)
				break;
		}	
		Debug.debug("SoundBuilder buildWaveSequence buildWaveSequence waveSequence: "+Arrays.toString(waveSequence),2);
			return waveSequence;
	}
	
	static void addTofullSequence(int[] sequence) {
		
		if(full_Sequence != null) {
			
			newSequence = new int[ full_Sequence.length + sequence.length];
			
			for(int i = 0; i < full_Sequence.length; i++) 		
				newSequence[i] = full_Sequence[i];
			
			for(int i = 0; i < sequence.length; i++) 		
				newSequence[i+full_Sequence.length] = sequence[i];
					
			full_Sequence = newSequence;
			Debug.debug("SoundBuilder addTofullSequence full_Sequence.length: "+full_Sequence.length +" "+Arrays.toString(full_Sequence) ,2);			
		} else {
				full_Sequence = sequence;
				Debug.debug("SoundBuilder created full_Sequence.length: "+full_Sequence.length+" "+Arrays.toString(full_Sequence),2);	
		}
	}
	
	
	//buildAmplitudeQuarteWaves();

//	
//	buildHalfWave(test000,2,3000);
	
//	buildHalfWave(test0,6,3000);
//	buildHalfWave(test1,8,3000);
//	buildHalfWave(test2,10,3000);
//	buildHalfWave(test3,12,3000);
//	buildHalfWave(test4,12,3000);
//	buildHalfWave(test3,14,3000);
//	buildHalfWave(test3,16,3000);
//	buildHalfWave(test3,18,3000);
//	buildHalfWave(test3,20,3000);
//	buildHalfWave(test3,22,3000);
//	buildHalfWave(test3,24,3000);
//	buildHalfWave(test3,26,3000);
//	buildHalfWave(test3,28,3000);
//	buildHalfWave(test3,30,3000);
	
	
	private static int[] buildHalfWave(int[] inputArr ,int sampleLength, int amplitude) {
		
		System.out.println("buildHalfWave 1. sampleLength "+sampleLength+" amplitude: "+ amplitude+" inputArr " + Arrays.toString(inputArr));
		
		int middle = amplitude;
		int leftCounter=0;
		int rightCounter=0;
		int  halfWaveCounter = 0;
		int[] leftWave = new int[sampleLength/4];	
		int[] rightWave = new int[sampleLength/4];
		int[] halfWave;
		

		
		if(sampleLength == 2) {
			System.out.println("buildHalfWave 2.: "+ Arrays.toString(new int [] {amplitude})+"\n");
			return new int [] {amplitude};
		}
		
		for(int i = 0; i < sampleLength /2-1; i++) {
			 		
			if(i%2 == 0) {
				//System.out.println(" if 1 "+ inputArr[leftCounter]);
				leftWave[leftCounter] = inputArr[leftCounter++];
			}
			
			if(i%2 != 0) {
				//System.out.println(" if 2");
				rightWave[rightCounter] = inputArr[rightCounter++];
			}
		}
		
		halfWave = new int[sampleLength/2];
		
		if( leftCounter > 0) {
			
			for (int i = 0; i < leftCounter; i++) 
				halfWave[halfWaveCounter++] =  amplitude -leftWave[leftCounter-i-1];
			}
		
		halfWave[halfWaveCounter++] = middle;
		
		if( rightCounter > 0) {
		
			for (int i = 0; i < rightCounter; i++) 
				halfWave[halfWaveCounter++] = amplitude - rightWave[i];
			}
		
		System.out.println("halfWave: "+ Arrays.toString(halfWave)+ "\n");
		
		return halfWave;
	}
	
private static void buildAmplitudeQuarteWaves() {
		
		
				long startTime = System.currentTimeMillis();
				long endTime = 0;

				double power = 15d;
				double value = 0;
				waveSequenceArray  = new int[15000][];
				int j = 1;
								
				for(int i = 1; i < 14000 ; i++ ) {
					
					waveSequenceArray[i] = new int[j];
					
					for( j = 2 ; j < 500; j++) {
						
						
						value =  Math.pow(j, power);
						
						if(value < 32736) {
							waveSequenceArray[i][j-2] = (int) value;						
						}
						
						if(value > 32736) {

							
	
							//waveSequenceArray[i][0] =  (int) power * 1000;
							break;
						}
							
										
					}
				
					power = power - 0.001;
					// 
					System.out.println("i: "+i + " power: "+power+ "array i: "+ Arrays.toString(waveSequenceArray[i]) );
				}
				
				 endTime = System.currentTimeMillis();
				 
				 
				 System.out.println("Elapsed Time: "+(endTime - startTime));
	}
}
