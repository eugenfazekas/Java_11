package voiceRecognition.audio5.audioGramBuilder;

public class MySpketrogramDetails {

	static int spektrogramMapCounter;
	static int[] spektrogramMap;

	static void buildSpektroGramMap(int[] amplitudeMap, int[] frequencyMap) {
		
		spektrogramMapCounter = 0;
		spektrogramMap = new int[amplitudeMap.length <= frequencyMap.length ? 
			amplitudeMap.length* 2 : frequencyMap.length * 2];
		
		for(int i = 0; i < spektrogramMap.length/2; i++) {
			
			spektrogramMap[spektrogramMapCounter++] = amplitudeMap[i];
			spektrogramMap[spektrogramMapCounter++] = frequencyMap[i];
		}
	}
	
	static void addToSpektroGramMap(int amplitude,int frequency) {
		 
		 spektrogramMap[spektrogramMapCounter++] = amplitude;
		 spektrogramMap[spektrogramMapCounter++] = frequency;
	}
}
