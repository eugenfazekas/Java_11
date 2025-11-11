package com.audio4.audioGramInitializer.trim;

import com.audio0.main.AppSetup;
import com.audio2.recorder.AudioListener;
import com.audio8.util.Debug;

public class BuildTrimedSequence {
	
	private static int debug_level_INFO = 5;
	private static int debud_level_DEBUG = 5;
	
	public static void mainTrimedSequenceBuilder(int id, int[] inputSamples) {
		 
		 if(AudioTrimSelector.sequences.size() == 0) 
			 return;
		 
			 buildUsableSequence(AudioTrimSelector.startIndex
				,AudioTrimSelector.endIndex,inputSamples);		 
	}

	static void  buildUsableSequence(int startIndex, int endIndex,int[] inputSamples) {
		 
		Debug.debug(debug_level_INFO,"BuildTrimedSequence buildUsableSequence startIndex "+startIndex
				+ ", endIndex "+endIndex );
		
		if(endIndex - startIndex < AudioListener.format.getSampleRate() / 2) {
			
			Debug.debug(debud_level_DEBUG,"BuildTrimedSequence buildUsableSequence Trim was Failed! Length is to"
					+" small: "+(endIndex - startIndex));
			
			if(AppSetup.continueWithNoTrim) {
				
				AudioTrimSelector.trimedSequence = inputSamples;
				
				Debug.debug(debud_level_DEBUG,"BuildTrimedSequence buildUsableSequence Trim was Failed!"
						+"......... Continuing with Base Stream");
				
					return;
			}
			
			AudioTrimSelector.trimedSequence = null;
			
				return;
		}
		
		AudioTrimSelector.counter = 0;
		AudioTrimSelector.trimedSequence = new int[endIndex-startIndex];

		for(int i = startIndex; i < endIndex; i++ ) 			
			AudioTrimSelector.trimedSequence[AudioTrimSelector.counter++] = inputSamples[i];
		
		Debug.debug(debug_level_INFO,"BuildTrimedSequence buildUsableSequence New Array: "
	    +AudioTrimSelector.trimedSequence.length+" startIndex "+startIndex+", endIndex "+endIndex);
	}
}
