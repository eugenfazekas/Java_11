package voiceRecognition.audio5.audioGramBuilder;

import java.util.Arrays;

import voiceRecognition.audio8.util.Debug;

public class VoiceRecognitionBuilderUtil {
	
	private static int debug_level_INFO = 5;

	static int[]  buildSequenceBorders(int[] input, int  startLimit, int endLimit) {
		
		Debug.debug(debug_level_INFO,"VoiceRecognitionUtil buildSequenceBorders input Array:  "  
			+ Arrays.toString(input));
		
		int start = 0;
		int end = input.length;
		int[] result;
		for(int i = 0; i < input.length; i++) {
			
			if(input[i]>startLimit) { 
				start = i > 1 ? i-2 : 0;
				break;
			}
		}
		
		for(int i = input.length-1; i > 0; i--) {
			
			if(input[i]>endLimit) {
				end = i < input.length-2 ? i+2 : i;
				break;
			}		
		}
		
		result = new int[] {start,end};
		Debug.debug(debug_level_INFO,"VoiceRecognitionUtil frequency borders: "+ Arrays.toString(result));
		
		return result;
	}
	
	static String buildVoiceRecognitionAreaString (int[][][] inputHeights) {
		
		StringBuilder stringBuilder = new StringBuilder();
		for(int i = 0; i < inputHeights.length ; i++) {
		
			for(int j = 0; j < inputHeights[i].length ; j++) {

					if(j > 0 && j < inputHeights[i].length)
						stringBuilder.append("_");
					
					stringBuilder.append(j+":"+Arrays.toString(inputHeights[i][j]));
			}
			
			if(i < inputHeights.length-1)
				stringBuilder.append("&");
		}
		
		Debug.debug(debug_level_INFO,"VoiceRecognitionUtil buildVoiceRecognitionAreaString: "+stringBuilder.toString() 
			+ ", inputHeights.length: "+inputHeights.length + ", [0] "+ Arrays.toString(inputHeights[0][0]));
			return stringBuilder.toString();
	}
}
