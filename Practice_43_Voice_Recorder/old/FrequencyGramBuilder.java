package old;

import com.library.audio.audioGramInitializer.AudioGramInitializer;
import com.library.util.Debug;

public class FrequencyGramBuilder {
	
	 private static int positive = 0;
	 private static int negative = 0;
	 private static double  lastSV = 0;
	 private static int highestPositive = 0;
	 private static int highestNegative = 0;
	 public static String[] dataLines = new String[100000];
	 

	public static void buildFrequencyWaveMap() {
		
		if(!AudioGramInitializer.build) return;
		
		AudioGramInitializer.waveMap = new int[AudioGramInitializer.resizedAudioSamples.length];
	    StringBuilder sb = new StringBuilder();	
	    
	    AudioGramInitializer.waveCounter = 0;
		AudioGramInitializer.waveMap = new int[AudioGramInitializer.resizedAudioSamples.length / (2 * AudioGramInitializer.channels)];
		Debug.debug(2,"FrequencyGramBuilder buildFrequencyWaveMap samples.length: "+ AudioGramInitializer.resizedAudioSamples.length+ " waveMap.length "+AudioGramInitializer.waveMap.length);
		for(int i = 0; i < AudioGramInitializer.resizedAudioSamples.length; i++) {
			
			if(AudioGramInitializer.resizedAudioSamples[i] > 0 && lastSV >=0 ) {
				positive++;
				lastSV = AudioGramInitializer.resizedAudioSamples[i];
				sb.append(AudioGramInitializer.audioSamples[i]+", ");
			}
			
			if(AudioGramInitializer.resizedAudioSamples[i] <= 0) {
				negative++;
				lastSV = AudioGramInitializer.resizedAudioSamples[i];
				sb.append(AudioGramInitializer.audioSamples[i]+", ");
			}
			Debug.debug(5,"FrequencyGramBuilder buildFrequencyWaveMap lastSV "+lastSV);
			if(AudioGramInitializer.resizedAudioSamples[i] > 0 && lastSV <0 ) {
				//System.out.println("AnalysisInitializer.waveCounter"+AnalysisInitializer.waveCounter);
				//System.out.println("samples[i]:"+samples[i]);
				int frequency = getFrequency(positive+negative);
				Debug.debug(5,"FrequencyGramBuilder 3.5. frequency: "+frequency + " positive: "+positive +" negative: "+negative +" Sum p + n: "+ (positive+negative));
				int map = 0;
				map = getMappedFrequency(frequency);//*sharpnessGrower;
				if(AudioGramInitializer.waveCounter < 500) {
					Debug.debug(5,"FrequencyGramBuilder 4.counter: "+AudioGramInitializer.waveCounter+ " frequency: "+frequency+" map: "+ map+ " positive: "+positive+ " negative: "+negative);
				}
				AudioGramInitializer.waveMap[AudioGramInitializer.waveCounter++] = map;
				
				if(positive > highestPositive)
					highestPositive = positive;
				
				if(negative < highestNegative) 
					highestNegative = negative;
				
				positive = 1;
				negative = 0;
				lastSV = (int)AudioGramInitializer.resizedAudioSamples[i];
				dataLines[AudioGramInitializer.waveCounter-1]="Frequency: "+ frequency+" .-<<||||   "+ sb.toString() + ";\n";
				
				sb = null;
				sb = new StringBuilder();
				sb.append(AudioGramInitializer.audioSamples[i]+", ");
			}
			
		}
		Debug.debug(4,"FrequencyGramBuilder buildFrequencyWaveMap AnalysisInitializer.waveCounter:"+AudioGramInitializer.waveCounter);
		
		//System.out.println("low Frecvency: "+getLoWFrequencyMap(100));
		//System.out.println("middle Frecvency: "+getMiddleFrequencyMap(200));
		//System.out.println("high Frecvency: "+getHighFrequencyMap(2001));
	}
	/*
	 public static void buildFrequencyWaveMap2() {
			
			if(!AnalysisInitializer.build) return;
			
		    AnalysisInitializer.waveCounter = 0;
			AnalysisInitializer.waveMap = new int[AnalysisInitializer.timeSequenceBuffers.length]; 
			AnalysisInitializer.AVG_MILISEC_LENGTH = AnalysisInitializer.getBuffersLengthByMilisec(5); 
			AnalysisInitializer.tempBuffer = new int[AnalysisInitializer.AVG_MILISEC_LENGTH*2];
			AnalysisInitializer.avgMilisecCounter = 0; 
			
			for(int i = 0; i < AnalysisInitializer.timeSequenceBuffers.length; i++) {
	
				for(int j = 0; j < AnalysisInitializer.timeSequenceBuffers[i].length; j++) {
					
					if(positive >= 1 && AnalysisInitializer.timeSequenceBuffers[i][j] < 0) {
								
							AnalysisInitializer.addToBuffer(getFrequency(positive*2));
							reset(positive);
					}
						
					if(negative >= 1 && AnalysisInitializer.timeSequenceBuffers[i][j] > 0) {
							addToDataLine(i,j);
							AnalysisInitializer.addToBuffer(getFrequency(negative*2));
							reset(negative);
					}
					
					if(AnalysisInitializer.timeSequenceBuffers[i][j] > 0) {
						positive++;
						sb.append(AnalysisInitializer.timeSequenceBuffers[i][j]+", ");
						//System.out.println("timeSequenceBuffers[i][j]: "+AnalysisInitializer.timeSequenceBuffers[i][j]+" positive: "+positive);
					}
					
					if(AnalysisInitializer.timeSequenceBuffers[i][j] < 0) {
						negative++;
						sb.append(AnalysisInitializer.timeSequenceBuffers[i][j]+", ");
						//System.out.println("timeSequenceBuffers[i][j]: "+AnalysisInitializer.timeSequenceBuffers[i][j]+" negative: "+negative+" lastSample: "+lastSample );
					}
					
					if(AnalysisInitializer.avgMilisecCounter == AnalysisInitializer.AVG_MILISEC_LENGTH ) {
						AnalysisInitializer.addToWaveMap();
						AnalysisInitializer.avgMilisecCounter = 0;
					}
					AnalysisInitializer.avgMilisecCounter++;
				}				
			}
			Debug.debug("FrequencyGramBuilder waveMap.length: " + AnalysisInitializer.waveCounter + " Array: "+ Arrays.toString(AnalysisInitializer.waveMap),2);		
		}
	*/
	private static int getFrequency(int cycleSamples) {
		
		int freq = AudioGramInitializer.sampleRate / cycleSamples;
		Debug.debug(5,"1. freq: "+freq);
		return freq;
	}
	
	private static int getMappedFrequency(int frequency) {
             int mappedFrequency = 0;
             
             if(frequency <= 200) {
            	 mappedFrequency = getLoWFrequencyMap(frequency); 
            	 Debug.debug(5,"getMappedFrequency getLoWFrequencyMap called ");
             }
             
             if(frequency > 200 && frequency< 2000) {
            	 mappedFrequency = getMiddleFrequencyMap(frequency); 
            	 Debug.debug(5,"getMappedFrequency getMiddleFrequencyMap called ");
             }
             
             if( frequency>= 2000) {
            	 mappedFrequency = getHighFrequencyMap(frequency); 
            	 Debug.debug(5,"getMappedFrequency getHighFrequencyMap called ");
             }
             
		return mappedFrequency;
	}
	
	private static int getLoWFrequencyMap(int frequency) {
		
		return (int)(frequency * 33.3)/200;
	}	
	
	private static int getMiddleFrequencyMap(int frequency) {
		
		return (int)((((frequency-200) * 33.3)/1800)+34);
	}
	
	private static int getHighFrequencyMap(int frequency) {
		
		return (int)((((frequency-2000) * 33.3)/20500)+67);
	}
}
