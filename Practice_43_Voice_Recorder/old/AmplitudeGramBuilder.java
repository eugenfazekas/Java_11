package old;

import com.library.audio.audioGramInitializer.AudioGramInitializer;
import com.library.util.AudioBuilderUtil;
import com.library.util.Debug;

public class AmplitudeGramBuilder {


	 
	 private static float sampleAvg;
	 private static	int sharpGrowerSum ;
	 private static float pos ;
	 static int counter;
	 
	 
	 
public static void buildAmplitudeWaveMap() {
		
		if(!AudioGramInitializer.build)
			return;

		AudioGramInitializer.waveMap = new int[AudioGramInitializer.resizedAudioSamples.length];
		counter = 0;
		sampleAvg=0;
		
		    Debug.debug(2,"AmplitudeGramBuilder buildAmplitudeWaveMap 1. WaveMapsamples.length: "+ AudioGramInitializer.resizedAudioSamples.length);
			for(int i = 0; i < AudioGramInitializer.resizedAudioSamples.length; i++) {
				Debug.debug(5,"AmplitudeGramBuilder buildAmplitudeWaveMap 2. samples[i] "+AudioGramInitializer.resizedAudioSamples[i]);
				if(AudioGramInitializer.resizedAudioSamples[i] > 0) {
					//System.out.println("buildAmplitudeWaveMap samples[i] "+samples[i]);
					pos += AudioGramInitializer.resizedAudioSamples[i]*100 ;
					//positive += samples[i]*((100/averageAmplitude) * averageAmplitude*2) ;
					counter++;
				}
				
				if(AudioGramInitializer.resizedAudioSamples[i] < 0) {
					
					if(pos != 0) {
						if(counter == 0)
							counter++;
						sampleAvg = (pos / counter );
						sharpGrowerSum = AudioBuilderUtil.sharpGrowerSum(sampleAvg,AudioGramInitializer.sharpnessGrower );
						Debug.debug(5,"MySpektrumAnalysis buildAmplitudeWaveMap 3.  "+" sampleAvg:" +sampleAvg +" sharpGrowerSum: "+sharpGrowerSum+" waveCounter: "+AudioGramInitializer.waveCounter +" positve: "+ pos + " counter: "+counter+ " resizedAudioSamples[i]: "+AudioGramInitializer.resizedAudioSamples[i] );
						AudioGramInitializer.waveMap[AudioGramInitializer.waveCounter++]  = sharpGrowerSum;							
					}
					counter = 0;
					pos = 0;
					sampleAvg=0;
					sharpGrowerSum=0;
				}

			}
			pos = 0; 
			//waveHeightLimit = sharpnessGrower * 2;
			 Debug.debug(4,"MySpektrumAnalysis buildAmplitudeWaveMap 4.waveMap.length: "+AudioGramInitializer.waveMap.length + " waveCounter: "+AudioGramInitializer.waveCounter );
		}	  
}
