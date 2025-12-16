package voiceRecognition.audio2.audioSequenceBuilder;


import voiceRecognition.audio8.util.Debug;

public class AudioSequenceBuilder {
	
	private static int[] stream;
	private static int[] sequence;
	public static int averageAmplitude;
	private static int debugLevel;
	
	public static int[] mainBuilder(int[] sequenceInfo, int sampleRate) {
		
		averageAmplitude = 0;
		//stream = new int[(sequenceInfo.length / 2) * sequenceLength - ((sequenceInfo.length / 2) )];
		stream = new int[(sequenceInfo.length / 3)  * sequenceInfo[2]];
		int counter = 0;
		Debug.debug(debugLevel,"AudioSequenceBuilder buildWaveStream stream.length " + stream.length);
		for(int i = 0; i < sequenceInfo.length ; i = i+3) {
			
			Debug.debug(debugLevel,"AudioSequenceBuilder buildWaveStream check i: " +i);
			sequence=buildSinWavesSequence(sequenceInfo[i],sequenceInfo[i+1],sampleRate,sequenceInfo[i+2]);
			
			if(sequenceInfo [i] > averageAmplitude)
				averageAmplitude = sequenceInfo [i];
			
			for(int j = 1; j < sequence.length ; j++) {
				stream[counter++]= sequence[j];
				Debug.debug(debugLevel,"AudioSequenceBuilder buildWaveStream Result i: "+ (i/3) + ", AMP: "
					+ sequenceInfo [i] +", FREQ: "+sequenceInfo [i+1]+ ", stream[i]: " +stream[counter-1]);
			}
		}	
			return stream;
		
	}
	
	static int[] buildSinWavesSequence(int amplitude, int frequency, int sampleRate, int samplesLength) {

        int[] samples = new int[samplesLength];

        for (int n = 1; n < samplesLength; n++) 
            samples[n] = (int) (amplitude * Math.sin(2 * Math.PI * frequency * n / sampleRate));
        
		return samples;
	}
}
