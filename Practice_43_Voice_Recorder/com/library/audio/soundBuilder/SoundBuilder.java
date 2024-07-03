package com.library.audio.soundBuilder;

import java.util.Arrays;
import java.util.List;

import com.library.util.Debug;

public class SoundBuilder {
	
	//private static int SampleRate = ApplicationProperties.RATE;
	static float sampleRate = 22050f;
	static int[] array= new int[500];
	static int[] waveSequence;
	static int[] full_Sequence;
	static int[] newSequence;
	static byte[] byte_Sequence; 
	static int[][] waveSequenceArray;
	
	static void addTofullSequence(int[] sequence) {
		//Debug.debug("SoundBuilder addTofullSequence sequence: "+Arrays.toString(sequence),2);
		
		if(full_Sequence != null) {
			
			newSequence = new int[ full_Sequence.length + sequence.length];
			
			for(int i = 0; i < full_Sequence.length; i++) 		
				newSequence[i] = full_Sequence[i];
			
			for(int i = 0; i < sequence.length; i++) 		
				newSequence[i+full_Sequence.length] = sequence[i];
					
			full_Sequence = newSequence;
			Debug.debug(2,"SoundBuilder addTofullSequence full_Sequence.length: "+full_Sequence.length +" next line...\n");			
		} else {
				full_Sequence = sequence;
			Debug.debug(2,"SoundBuilder created full_Sequence.length: "+full_Sequence.length+" next line...\n");	
		}
	}
	
	public static byte[] mainByteBuilder(List<Sequence> sequence) {

		
		multiSequenceBuilder(sequence);

		
		Debug.debug(2,"SoundBuilder mainBuilder test full_Sequence 1,2..n,ne [0]: "+full_Sequence[0]+" [1]: "+full_Sequence[1]+" -2: "+full_Sequence[full_Sequence.length-2]+" -1: "+full_Sequence[full_Sequence.length-1]);
		
		return convertIntArrayToByteArray(full_Sequence);
	}
	
	public static int[] mainIntBuilder(List<Sequence> sequence) {
		
		multiSequenceBuilder(sequence);		
		Debug.debug(2,"SoundBuilder mainBuilder test full_Sequence 1,2..n,ne [0]: "+full_Sequence[0]+" [1]: "+full_Sequence[1]+" -2: "+full_Sequence[full_Sequence.length-2]+" -1: "+full_Sequence[full_Sequence.length-1]);
		
		return full_Sequence;
	}
	
	
	private  static void multiSequenceBuilder(List<Sequence> sequence) {
		
		for(int i = 0 ; i < sequence.size(); i++ ) {
			Debug.debug(2,"SoundBuilder multiSequenceBuilder sequence details i: " + i +" amplitude:" + sequence.get(i).getAmplitude() + " Frequency:"+sequence.get(i).getFrequency() + " Milisec length:"+sequence.get(i).getMilisecLength());	
			addTofullSequence(buildeSequence(sequence.get(i)));
		}
		
	}
	
	private static int[] buildeSequence(Sequence sequence ) {
		
		 buildAmplitudeQuarteWaves();
		 float samples = getSampleCountByFrequency(sequence.getFrequency());
		 float cycleTime  = getCycleTimeInMilisec((int)samples);
		 int totalCyclesBySequenceTime = getTotalCyclesBySequenceTime(cycleTime,sequence.getMilisecLength());
		 int[] wave  = buildWave( samples, sequence.getAmplitude());
		 int[] waveSequence =  buildWaveSequence(wave,totalCyclesBySequenceTime);
		 
		 return waveSequence;
	}

	private static float getSampleCountByFrequency(float frequency) {
		
		float sampleCount = (sampleRate / frequency);

		Debug.debug(2,"SoundBuilder getSampleCountByFrequency SampleRate: "+ sampleRate +" / frequency: "+ frequency +" = TotalSampleCount "+ sampleCount);
		
			return sampleCount ;
	}
	
	private static float getCycleTimeInMilisec(int inputSamples) {
		
		float rateUnit = 1000 /sampleRate;
		float cycleTimeInMilisec = rateUnit * inputSamples;
		Debug.debug(2,"SoundBuilder getCycleTime rateUnit: "+rateUnit+ " * TotalSampleCount: "+ inputSamples+" =  CycleTimeInMilisec: "+ cycleTimeInMilisec);	
		return cycleTimeInMilisec ;
	}
	
	private static int getTotalCyclesBySequenceTime(float cycleTime, int sequenceTimeMsec) {
			
		int totalCyclesBySequenceTime = (int) (sequenceTimeMsec / cycleTime );
		Debug.debug(2,"SoundBuilder getTotalCyclesBySequenceTime sequenceTimeMsec: "+sequenceTimeMsec+ " / cycleTime: "+cycleTime+" = TotalCyclesBySequenceTime: " + totalCyclesBySequenceTime);
			return (int)totalCyclesBySequenceTime;
	}
	
	private static int [] buildWaveSequence(int[] wave, int cycles) {
		
		Debug.debug(3,"SoundBuilder buildWaveSequence wave"+ Arrays.toString(wave) );
	     waveSequence = new int[wave.length * cycles];
	     int cyclesCounter = 0;
	     int counter = 0;
	     
		for (int i = 0; i < wave.length; i++ ) {
			
			waveSequence[counter++] = wave[i];
			//System.out.println("counter: "+counter+" i: "+i+" cycles: "+cycles );
			if(i == wave.length-1 && cyclesCounter < cycles) {
				i = -1; 
				cyclesCounter++;
			}
			
			if(cyclesCounter == cycles)
				break;
		}	
		Debug.debug(2,"SoundBuilder buildWaveSequence buildWaveSequence.length: "+waveSequence.length);
			return waveSequence;
	}
	 
	private static int[] buildWave(float totalSamples, int amplitude) {
		
		int totalSampleCount = (int) Math.round(totalSamples);
		int[] halfWave = buildHalfWave(totalSampleCount,amplitude); 
		int[] integralSequence = new int[halfWave.length*2]; 
		int counter = 0;
		
		for(int i =0; i < totalSampleCount / 2; i++)
			integralSequence[counter++] = halfWave[i];
		
		for(int i =0; i < totalSampleCount / 2; i++)
			integralSequence[counter++] = -halfWave[i];
		
		Debug.debug(2,"SoundBuilder buildWave integralSequence.length: "+integralSequence.length + " IntegralSequence: "+ Arrays.toString(integralSequence));
		return integralSequence;
	}
	
private static int[] buildHalfWave(int sampleLength, int amplitude) {
			
		int middle = amplitude;
		int leftCounter=0;
		int rightCounter=0;
		int  halfWaveCounter = 0;	
		int[] leftWave = new int[sampleLength/4];	
		int[] rightWave = new int[sampleLength/4];
		int[] halfWave;		
		
		if(sampleLength == 2) {
			Debug.debug(5,"SoundBuilder buildHalfWave 2.: "+ Arrays.toString(new int [] {amplitude}));
			return new int [] {amplitude};
		}
		
		int[] quaterSequence = getQuarterWaveSequence(sampleLength,amplitude);
		
		Debug.debug(5,"SoundBuilder buildHalfWave 1. sampleLength "+sampleLength+" amplitude: "+ amplitude);
				
		for(int i = 0; i < sampleLength /2-1; i++) {
			 		
			if(i%2 == 0) {
				//System.out.println(" if 1 "+ inputArr[leftCounter]);
				leftWave[leftCounter] = quaterSequence[leftCounter++];
			}
			
			if(i%2 != 0) {
				//System.out.println(" if 2");
				rightWave[rightCounter] = quaterSequence[rightCounter++];
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
		
		Debug.debug(4,"SoundBuilder halfWave Array.length: "+halfWave.length+ ", Array"+ Arrays.toString(halfWave));
		
		return halfWave;
	}

private static int[] getQuarterWaveSequence(int totalSampleCount, int amplitude) {
	
	
	int[] returnArr = new int [totalSampleCount / 4];
	Debug.debug(5,"SoundBuilder getQuarterWaveSequence 1. sampleCount: "+totalSampleCount + " Amplitude: "+amplitude + " reeturnArr.length: " +returnArr.length);
	
	if(totalSampleCount >= 4) {
		for(int i = 1; i < waveSequenceArray.length; i++) {
			//System.out.println(" Arrays.toString i: "+i+" " +Arrays.toString(waveSequenceArray[i]) );
			for(int j = 0; j < waveSequenceArray[i].length; j++) {
				
				//System.out.println( "waveSequenceArray[i] waveSequenceArray[i].length: "+waveSequenceArray[i].length+ " returnArr.length: "+returnArr.length );
				if(waveSequenceArray[i].length > returnArr.length  &&  waveSequenceArray[i][returnArr.length-1] < amplitude && waveSequenceArray[i][returnArr.length] > amplitude) {
					Debug.debug(5,"SoundBuilder getQuarterWaveSequence 2. return array: "+Arrays.toString(waveSequenceArray[i]));
					
					if(returnArr.length > 0) {
						for( int k = 0; k < returnArr.length;k++) {
							returnArr[k] = waveSequenceArray[i][k];
						}
					}
						Debug.debug(5,"SoundBuilder getQuarterWaveSequence 3. return array: "+Arrays.toString(returnArr));
				 return waveSequenceArray[i];
				}
			}			
		}
	}
	Debug.debug(2,"SoundBuilder getQuarterWaveSequence 3. totalSampleCount: " +totalSampleCount+" amplitude: "+amplitude+ "+ Return array: "+Arrays.toString(returnArr));
	return returnArr;
}
	
	public static byte[] convertIntArrayToByteArray(int[] intStream) {
		
		byte[] byte_stram = new byte[intStream.length * 2];
		int counter = 0;
		
		for(int i = 0; i < full_Sequence.length; i++) {  
			//Debug.debug("SoundBuilder convertIntArrayToByteArray full_Sequence[i] "+full_Sequence[i],2);
			byte_stram[counter++] = (byte) (intStream[i] >> 8);
			byte_stram[counter++] = (byte) intStream[i] ;
		}
		Debug.debug(2,"SoundBuilder convertIntArrayToByteArray byte_Sequence.length "+byte_stram.length);
		return byte_stram;
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
				
				if(value < 32736) 
					waveSequenceArray[i][j-2] = (int) value;						
								
				if(value > 32736) {
					break;
				}								
			}		
			power = power - 0.001;
			// 
			//System.out.println("i: "+i + " power: "+power+ "array i: "+ Arrays.toString(waveSequenceArray[i]) );
		}
		
		 endTime = System.currentTimeMillis();
		 		 
		 Debug.debug(5,"SoundBuilder buildAmplitudeQuarteWaves Elapsed Time: "+(endTime - startTime));
	}

}
