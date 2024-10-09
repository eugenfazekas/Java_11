package com.audio2.soundBuilder;

import java.util.Arrays;
import java.util.List;

import com.audio8.util.AudioBuilderUtil;
import com.audio8.util.Debug;
import com.audio8.util.FileUtil;

public class SoundBuilder {
	
	//private static int SampleRate = ApplicationProperties.RATE;
	static float sampleRate = 22050f;
	static int[] array= new int[500];
	static int[] waveSequence;
	static int[] full_Sequence;
	static int[] newSequence;
	static byte[] byte_Sequence; 
	static int[][] waveSequenceArray;
	public static int averageAmplitude;
	
	static void addTofullSequence(int[] sequence) {
		//Debug.debug("SoundBuilder addTofullSequence sequence: "+Arrays.toString(sequence),2);
		
		if(full_Sequence != null) {
			
			newSequence = new int[ full_Sequence.length + sequence.length];
			
			for(int i = 0; i < full_Sequence.length; i++) 		
				newSequence[i] = full_Sequence[i];
			
			for(int i = 0; i < sequence.length; i++) 		
				newSequence[i+full_Sequence.length] = sequence[i];
					
			full_Sequence = newSequence;
			Debug.debug(5,"SoundBuilder addTofullSequence full_Sequence.length: "+full_Sequence.length +" next line...\n");			
		} else {
				full_Sequence = sequence;
			Debug.debug(5,"SoundBuilder created full_Sequence.length: "+full_Sequence.length+" next line...\n");	
		}
	}
	
	public static byte[] mainSequenceByteBuilderByTimeLength(List<Sequence> sequence) {

		
		multiSequenceBuilder(sequence);

		
		Debug.debug(2,"SoundBuilder mainBuilder test full_Sequence 1,2..n,ne [0]: "+full_Sequence[0]+" [1]: "+full_Sequence[1]+" -2: "+full_Sequence[full_Sequence.length-2]+" -1: "+full_Sequence[full_Sequence.length-1]);
		
		return convertIntArrayToByteArray(full_Sequence);
	}
	
	public static int[] mainSequenceIntBuilderByTimeLength(List<Sequence> sequence) {
		
		multiSequenceBuilder(sequence);		
		Debug.debug(2,"SoundBuilder mainBuilder test full_Sequence 1,2..n,ne [0]: "+full_Sequence[0]+" [1]: "+full_Sequence[1]+" -2: "+full_Sequence[full_Sequence.length-2]+" -1: "+full_Sequence[full_Sequence.length-1]);
		averageAmplitude = averageAmplitude / full_Sequence.length;
		return full_Sequence;
	}
	
	public static  int[] mainSequenceStreamBuilderByReadedFileInputSequences(Sequence[] sequences) {
		
		readAmplitudeQuarteWaves();
		for(int i = 0; i <sequences.length; i++ ) {
			Debug.debug(2,"SoundBuilder mainSequenceStreamBuilderByReadedFileInputSequences sequence.toString(): "+sequences[i].toString());	
			addTofullSequence(buildWave(sequences[i].getPositveAmplitude(),sequences[i].getPosSampleCount(),sequences[i].getNegativeAmplitude(),sequences[i].getNegSampleCount()));
			averageAmplitude += (sequences[i].getPositveAmplitude() + sequences[i].getNegativeAmplitude())*15;
		}
		averageAmplitude = averageAmplitude / full_Sequence.length;
		Debug.debug(2,"SoundBuilder mainSequenceStreamBuilderByReadedFileInputSequences  averageAmplitude:" +averageAmplitude);	
			return full_Sequence;
	}
	
	
	private  static void multiSequenceBuilder(List<Sequence> sequence) {
		
		for(int i = 0 ; i < sequence.size(); i++ ) {
			Debug.debug(2,"SoundBuilder multiSequenceBuilder sequence details i: " + i +" amplitude:" + sequence.get(i).getPositveAmplitude()
					+ " Frequency:"+sequence.get(i).getFrequency() + " Milisec length:"+sequence.get(i).getMilisecLength() +  ", averageAmplitude:" +averageAmplitude);	
			addTofullSequence(buildeSequence(sequence.get(i)));
		}	
	}
	
	private static int[] buildeSequence(Sequence sequence ) {
		
		 readAmplitudeQuarteWaves();
		 float samples = getSampleCountByFrequency(sequence.getFrequency());
		 float cycleTime  = getCycleTimeInMilisec((int)samples);
		 int totalCyclesBySequenceTime = getTotalCyclesBySequenceTime(cycleTime,sequence.getMilisecLength());
		 int[] wave  = buildWave( sequence.getPositveAmplitude(),samples/2, sequence.getPositveAmplitude(),samples/2);
		 int[] waveSequence =  buildWaveSequence(wave,totalCyclesBySequenceTime);
		 
		 return waveSequence;
	}

	public static void readAmplitudeQuarteWaves() {
		String[] splitter = null; 
		List<String> lines = FileUtil.buildStringLinesFromInputStream(FileUtil.buildFileStreamFromFile("src\\main\\resources\\static\\audio\\Default_Wave_Library\\WaveSequenceArray.txt"));
		waveSequenceArray = new int[lines.size()-1][];
		System.out.println("lines.size "+lines.size());
		waveSequenceArray = new int[lines.size()][];
		
		for(int i = 0; i < lines.size(); i++ ) {

			splitter = lines.get(i).split(",");
			waveSequenceArray[i] = new int[splitter.length];
			
				for(int j = 0; j < splitter.length; j++)
					waveSequenceArray[i][j] = 	Integer.parseInt(splitter[j]);
			//System.out.println("i: "+i+" " +waveSequenceArray[i].length);
		}
		System.out.println("SoundBuilder readAmplitudeQuarteWaves waveSequenceArray.length: "+waveSequenceArray.length + "splitter.length: "+splitter.length);
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
		
		Debug.debug(5,"SoundBuilder buildWaveSequence wave"+ Arrays.toString(wave) );
	     waveSequence = new int[wave.length * cycles];
	     int cyclesCounter = 0;
	     int counter = 0;
	     
		for (int i = 0; i < wave.length; i++ ) {
			
			waveSequence[counter++] = wave[i];
			averageAmplitude+= Math.abs(wave[i]);
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
	 
	private static int[] buildWave(int posAmplitude, float positiveSamples,int negAmplitude, float negativeSamples) {
				
		Debug.debug(5,"SoundBuilder buildWave positiveSamples: "+positiveSamples+ " posAmplitude: "+posAmplitude + " negatveSamples: "+negativeSamples + " negativeSamples: "+ negAmplitude );
		int posSampleCount = (int) Math.round(positiveSamples);
		int negSampleCount = (int) Math.round(negativeSamples);
		int[] posHalfWave = buildHalfWave(posSampleCount*2,posAmplitude); 
		int[] negHalfWave = buildHalfWave(negSampleCount*2,negAmplitude); 
		int[] integralSequence = new int[posSampleCount+negSampleCount]; 
		int counter = 0;
		
		Debug.debug(5, " Array: "+ Arrays.toString(posHalfWave));
		
		for(int i =0; i < posSampleCount; i++) {
			integralSequence[counter++] = posHalfWave[i];
		}
		
		for(int i =0; i < negSampleCount; i++)
			integralSequence[counter++] = -negHalfWave[i];

		Debug.debug(4,"SoundBuilder buildWave integralSequence.length: "+integralSequence.length + " IntegralSequence: "+ Arrays.toString(integralSequence));
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
		
		int[] quaterSequence = getQuarterWaveSequence(sampleLength/4,amplitude);
		
		Debug.debug(5,"SoundBuilder buildHalfWave 1. sampleLength "+sampleLength+" amplitude: "+ amplitude+ " quaterSequence: "+Arrays.toString(quaterSequence));
				
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
		Debug.debug(5,"SoundBuilder halfWave quaterSequence: "+Arrays.toString(quaterSequence));
		Debug.debug(4,"SoundBuilder halfWave samples: "+sampleLength+", Amplitude: "+amplitude+", halfWave.length: "+halfWave.length+ ", Array"+ Arrays.toString(halfWave));
		
		return halfWave;
	}

	private static int[] getQuarterWaveSequence(int totalSampleCount, int amplitude) {
		Debug.debug(4,"SoundBuilder getQuarterWaveSequence totalSampleCount:" +totalSampleCount + ",amplitude: "+amplitude);
		int[] returnArr = null;
		
		if(totalSampleCount > waveSequenceArray[waveSequenceArray.length-1].length)
			returnArr = getHighWaveSequence(totalSampleCount,amplitude);
		
		if(totalSampleCount < waveSequenceArray[waveSequenceArray.length-1].length)
			returnArr = waveSequenceArray[totalSampleCount];
		
		float amplitudeCorrection = (float) (amplitude-1)/ returnArr[returnArr.length-1] ;
		
		Debug.debug(4,"SoundBuilder getQuarterWaveSequence amplitudeCorrection "+amplitudeCorrection + ",amplitude-1 "+ (amplitude-1) + ", returnArr[returnArr.length-1] "+returnArr[returnArr.length-1]+ ", returnArr.length: "+returnArr.length);
	
		for(int i = 0 ; i <returnArr.length; i++ ) {
			returnArr[i] = (int) (returnArr[i] * amplitudeCorrection);
			
			if(returnArr[i] > 32736 || returnArr[i] < -32736)
				throw new RuntimeException("SoundBuilder getQuarterWaveSequence to big value "+returnArr[i] +", amplitude: "+amplitude +", returnArr[returnArr.length-1]: "+returnArr[returnArr.length-1] +" arr"+ Arrays.toString(returnArr) );
		}	
		
		Debug.debug(4,"SoundBuilder getQuarterWaveSequence totalSampleCount: "+totalSampleCount+", amplitude:"+amplitude + " Array: "+ Arrays.toString(returnArr));
			return returnArr;
	}
	
	private static int[] getHighWaveSequence(int sampleCount, int amplitude) {
		
		
		int baseSequenceLength = waveSequenceArray.length-2;
		int multiplier = sampleCount / baseSequenceLength ;
		int differenceAdd = sampleCount - baseSequenceLength;
		int[] returnArray = new int[sampleCount];
		int diff_Counter = 0;
		int totalCounter = 0;
		System.out.println("waveSequenceArray.length-2.length"+waveSequenceArray[waveSequenceArray.length-2].length +",sampleCount: "+sampleCount);
		Debug.debug(4,"SoundBuilder getHighWaveSequence 1. sampleCount: "+sampleCount +", amplitude:"+amplitude+" baseSequenceLength.length " +baseSequenceLength + " differenceAdd "+differenceAdd );
		for(int i = 0; i < baseSequenceLength; i++) {
			//System.out.println("SoundBuilder getHighWaveSequence arr "+Arrays.toString(waveSequenceArray[baseSequenceLength-1]));
			//System.out.println("SoundBuilder getHighWaveSequence i"+i+"[baseSequenceLength][i]:  "+waveSequenceArray[baseSequenceLength][i]);
			returnArray[totalCounter++] = waveSequenceArray[baseSequenceLength][i];
			
			if(diff_Counter < differenceAdd - multiplier) {
				for(int j = 0; j < multiplier; j++) {	
					returnArray[totalCounter++] = waveSequenceArray[baseSequenceLength][i];
					diff_Counter++;
				}
			}
			//System.out.println("i: "+i+ " returnArray[counter] "+returnArray[totalCounter-1] + ", totalCounter: "+totalCounter  );
		}
		for(int i = totalCounter; i <sampleCount; i++ )
			returnArray[totalCounter++] = waveSequenceArray[baseSequenceLength][waveSequenceArray.length-3];

		//System.out.println("waveSequenceArray.length-1 "+waveSequenceArray[waveSequenceArray.length-1].length+" waveSequenceArray.length "+waveSequenceArray.length+" " +Arrays.toString(waveSequenceArray[waveSequenceArray.length-1]));
		Debug.debug(4,"SoundBuilder getHighWaveSequence 2. sampleCount: "+sampleCount + " arr.length: "+ returnArray.length+" returnArray "+Arrays.toString(returnArray));
		return returnArray;		
	}

	
	public static byte[] convertIntArrayToByteArray(int[] intStream) {
		
		byte[] byte_stram = new byte[intStream.length * 2];
		int counter = 0;
		
		for(int i = 0; i < intStream.length; i++) {  
			//Debug.debug("SoundBuilder convertIntArrayToByteArray full_Sequence[i] "+full_Sequence[i],2);
			byte_stram[counter++] = (byte) (intStream[i] >> 8);
			byte_stram[counter++] = (byte) intStream[i] ;
		}
		Debug.debug(2,"SoundBuilder convertIntArrayToByteArray byte_Sequence.length "+byte_stram.length);
		return byte_stram;
	} 
	
	public static void buildAndWriteAmplitudeQuarteWaves() {
		
		buildAmplitudeQuarteWaves();
		String[] waves = FileUtil.convertInt2DToStringArray(waveSequenceArray);
		FileUtil.createTextFile(waves,"src\\main\\resources\\static\\audio\\Default_Wave_Library\\WaveSequenceArray.txt");
	}
	
	private static void buildAmplitudeQuarteWaves() {
				
		long startTime = System.currentTimeMillis();
		long endTime = 0;

		double power = 15d;
		double value = 0;
		waveSequenceArray  = new int[13500][];
		int j = 1;
						
		for(int i = 1; i < 13500 ; i++ ) {
			
			waveSequenceArray[i] = new int[j];
			
			for( j = 2 ; j < 545; j++) {
							
					value =  Math.pow(j, power);
					
					if(value < 32736) 
						waveSequenceArray[i][j-2] = (int) value;						
									
					if(value > 32736) {
						break;
					}								
			}
			waveSequenceArray[i] = AudioBuilderUtil.filterIntItems(waveSequenceArray[i], 0);
			power = power - 0.001;
			// 
			//System.out.println("i: "+i + " power: "+power+ "array i: "+ Arrays.toString(waveSequenceArray[i]) );
		}
		
		 endTime = System.currentTimeMillis();
		 		 
		 Debug.debug(3,"SoundBuilder buildAmplitudeQuarteWaves Elapsed Time: "+(endTime - startTime));
	}

}
