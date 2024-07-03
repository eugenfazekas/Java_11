package com;

import java.util.Arrays;
import java.util.List;


public class SoundBuilder {
	
	//private static int SampleRate = ApplicationProperties.RATE;
	static float sampleRate = 22050f;
	static int[] array= new int[500];
	static int[] waveSequence;
	static int[] full_Sequence;
	static int[] newSequence;
	static byte[] byte_Sequence; 
	
	static void addTofullSequence(int[] sequence) {
		
		
		if(full_Sequence != null) {
			
			newSequence = new int[ full_Sequence.length + sequence.length];
			
			for(int i = 0; i < full_Sequence.length; i++) 		
				newSequence[i] = full_Sequence[i];
			
			for(int i = 0; i < sequence.length; i++) 		
				newSequence[i+full_Sequence.length] = full_Sequence[i];
					
			full_Sequence = newSequence;
			System.out.println("addTofullSequence full_Sequence.length: "+full_Sequence.length);			
		} else {
				full_Sequence = sequence;
				System.out.println("addTofullSequence full_Sequence.length: "+full_Sequence.length);	
		}
	}
	
	public static byte[] mainBuilder(List<Sequence> sequence) {


		
		multiSequenceBuilder(sequence);
		convertIntArrayToByteArray();
		
		return byte_Sequence;
	}
	
	
	private  static void multiSequenceBuilder(List<Sequence> sequence) {
		
		for(Sequence s : sequence) {
			addTofullSequence(buildeSequence(s));
		}
		
	}
	
	private static int[] buildeSequence(Sequence sequence ) {
		
		 int samples =getSampleCountByFrequency(sequence.getFrequency());
		 float cycleTime  =  getCycleTimeInMilisec(samples);
		 int totalCyclesBySequenceTime = getTotalCyclesBySequenceTime(cycleTime,sequence.getMilisecLength());
		 int[] wave  = buildWave( samples, sequence.getAmplitude());
		 int[] waveSequence =  buildWaveSequence(wave,totalCyclesBySequenceTime);
		 
		 return waveSequence;
	}

	private static int getSampleCountByFrequency(float frequency) {
		
		float sampleCount = sampleRate / frequency;

		System.out.println("getSampleCountByFrequency sampleCount: " +sampleCount+ " Positive: "+sampleCount/2+" Negative: "+sampleCount/2);
		
			return (int)sampleCount ;
	}
	
	private static float getCycleTimeInMilisec(int inputSamples) {
		
		float rateUnit = 1000 /sampleRate;
		float cycleTimeInMilisec = (rateUnit * inputSamples);
		System.out.println("getCycleTime rateUnit: "+rateUnit+ "  getCycleTimeInMilisec "+ cycleTimeInMilisec);	
		return cycleTimeInMilisec ;
	}
	
	private static int getTotalCyclesBySequenceTime(float cycleTime, int sequenceTimeMsec) {
			
		int totalCyclesBySequenceTime = (int) (sequenceTimeMsec / cycleTime);
		System.out.println("getTotalCyclesBySequenceTime: " + totalCyclesBySequenceTime);
			return (int)totalCyclesBySequenceTime;
	}
	
	private static int [] buildWaveSequence(int[] wave, int cycles) {
		
	     waveSequence = new int[wave.length * cycles];
	     int cyclesCounter = 0;
	     int counter = 0;
	     
		for (int i = 0; i < wave.length * cycles; i++ ) {
			
			waveSequence[counter++] = wave[i];
			//System.out.println("counter: "+counter+" i: "+i+" cycles: "+cycles );
			if(i == wave.length-1 && cyclesCounter < cycles) {
				i = 0; 
				cyclesCounter++;
			}
			
			if(cyclesCounter == cycles)
				break;
		}	
		//System.out.println("buildWaveSequence waveSequence: "+Arrays.toString(waveSequence));
			return waveSequence;
	}
	 
	private static int[] buildWave(int sampleCount, int amplitude) {
		
		int[] quaterSequence = getQuarterWaveSequence(sampleCount,amplitude); 
		int[] mainSequence = new int[quaterSequence.length*4+2]; 
		int counter = 0;
		int i;
		for(i = 0; i < quaterSequence.length; i++) {
			mainSequence[counter++] = amplitude - quaterSequence[quaterSequence.length-1 -i]+1;
			//System.out.println("counter: " +counter +" 1. mainSequence[i] "+mainSequence[counter-1] +" " +quaterSequence[i] );
		}
			mainSequence[counter++] =  amplitude;
			//System.out.println("counter: " +counter +" 2. mainSequence[i] "+mainSequence[counter-1]);
			
		for(i = 0; i < quaterSequence.length; i++) {
				mainSequence[counter++] = amplitude - quaterSequence[i]+1;
				//System.out.println("counter: " +counter +" 2. mainSequence[i] "+mainSequence[counter-1] +" " +quaterSequence[i] );
			}
		for(i = 0; i < quaterSequence.length; i++) {
			mainSequence[counter++] = -(amplitude - quaterSequence[quaterSequence.length-1 -i]+1);
			//System.out.println("counter: " +counter +" 2. mainSequence[i] "+mainSequence[counter-1] +" " +quaterSequence[i] );
		}
		
		mainSequence[counter++] =  -amplitude;
		//System.out.println("counter: " +counter +" 2. mainSequence[i] "+mainSequence[counter-1]);
		
		for(i = 0; i < quaterSequence.length; i++) {
			mainSequence[counter++] = -(amplitude - quaterSequence[i]+1);
			System.out.println("counter: " +counter +" 2. mainSequence[i] "+mainSequence[counter-1] +" " +quaterSequence[i] );
		}

		System.out.println("buildWave mainSequence.length: "+mainSequence.length);
		return mainSequence;
	}
	
	private static int[] getQuarterWaveSequence(int sampleCount, int amplitude) {
		
		
		System.out.println("getSqaureForSmapleLength Start 1. sampleCount: "+sampleCount+ " amplitude: "+amplitude);
		boolean testing = true;
		double value = 0;
		double maxPower = 3.22d;
		double minPower = 1.5d;
		int arrCounter = 0;
		int i = 2;
		int[] returnArray = null;
		while (testing && maxPower > minPower) {
			
			array = new int [500];
			arrCounter = 0;
			
			//System.out.println("getSqaureForSmapleLength Start 2 maxPower check: "+ maxPower);
			for(i = 2; i< 500; i++) {
				
				//System.out.println(" getSqaureForSmapleLength: "+value+" maxPower" +maxPower+ " i: "+i);
				//sleep(500);
			
				value = Math.pow(i, maxPower);
				
				if(value < amplitude) {
					array[arrCounter++]= (int)value;
				}
				if(value > amplitude)
					break;	

			}
			
			if(i == sampleCount) {
				testing = false;
				System.out.println("maxPower find: "+maxPower);
				returnArray = new int[arrCounter];
				returnArray[0]=1;
				for(i = 0; i < arrCounter; i++)
					returnArray[i] = array[i];
				
			}
			
			if(minPower == maxPower)
				throw new RuntimeException("Infinite Loop");
				
				
			maxPower = maxPower-0.01;	
			i=2;
		}
		System.out.println("returnArray.length: "+returnArray.length + " returnArray[last]: "+returnArray[returnArray.length-1]+ "maxPower "+(maxPower - 0.01) + " List "+Arrays.toString(returnArray)); 		
		return returnArray;
	}
	
	private static void convertIntArrayToByteArray() {
		
		byte_Sequence = new byte[full_Sequence.length * 2];
		int counter = 0;
		
		for(int i = 0; i < full_Sequence.length; i++) {         
			byte_Sequence[counter++] = (byte) (full_Sequence[i] >> 8);
			byte_Sequence[counter++] = (byte) full_Sequence[i] ;
		}
	} 
	
	
	
	private static void sleep(int milisec) {
		
		try {
			Thread.sleep(milisec);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

