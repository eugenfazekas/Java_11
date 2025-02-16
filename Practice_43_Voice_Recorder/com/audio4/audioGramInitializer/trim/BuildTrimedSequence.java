package com.audio4.audioGramInitializer.trim;

import java.util.Arrays;

import com.audio0.main.AppSetup;
import com.audio8.util.Debug;
import com.audio8.util.VoiceRecognitionUtil;

public class BuildTrimedSequence {
	
	public static void mainTrimedSequenceBuilder(int id, int[] inputSamples,int[] streamDetails) {
		 
		 if(AudioTrimSelector.sequences.size() == 0) 
			 return;
		 
		 if(AppSetup.voiceRecognition)
			buildUsableVoiceRecognitionSequence(id,AudioTrimSelector.detailsStartIndex
											,AudioTrimSelector.detailsEndIndex,streamDetails);			 
		 else 
			 buildUsableSequence(AudioTrimSelector.startIndex
					 								,AudioTrimSelector.endIndex,inputSamples);		 
	}

	private static void  buildUsableSequence(int startIndex, int endIndex,int[] inputSamples) {
		 
		Debug.debug(1,"BuildTrimedSequence buildUsableSequence startIndex "+startIndex
				+ ", endIndex "+endIndex );
		
		if(endIndex - startIndex < 5000 ) {
			
			Debug.debug(1,"BuildTrimedSequence buildUsableSequence Trim was Failed! Length is to"
					+" small: "+(endIndex - startIndex));
			
			if(AppSetup.continueWithNoTrim) {
				
				AudioTrimSelector.trimedSequence = inputSamples;
				
				Debug.debug(1,"BuildTrimedSequence buildUsableSequence Trim was Failed!"
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
		
		Debug.debug(1,"BuildTrimedSequence buildUsableSequence New Array: "
	    +AudioTrimSelector.trimedSequence.length+" startIndex "+startIndex+", endIndex "+endIndex);
	}
	 
	private static void buildUsableVoiceRecognitionSequence(int id, int startIndex, int endIndex,
			int[] inputSamples) {
			
		Debug.debug(1,"BuildTrimedSequence buildUsableVoiceRecognitionSequence startIndex "
			+startIndex+ ", endIndex "+endIndex);

		if(endIndex - startIndex < 200 ) {
				
			Debug.debug(1,"BuildTrimedSequence buildUsableVoiceRecognitionSequence Trim was"
					+" Failed! Length is to small: "+(endIndex - startIndex));
				
			if(AppSetup.continueWithNoTrim) {
					
				for(int i = 0; i < inputSamples.length; i= i+3) {
					
				   AudioTrimSelector.trimedSequence[AudioTrimSelector.counter++]=inputSamples[i+1];
				   AudioTrimSelector.trimedSequence[AudioTrimSelector.counter++]=inputSamples[i+2];
				}
				
				Debug.debug(1,"BuildTrimedSequence buildUsableVoiceRecognitionSequence Trim was"
					+" Failed!..........Continuing with original array");
					
				   		return;
			}
				AudioTrimSelector.trimedSequence = null;
				
				return;
		}
			
		AudioTrimSelector.trimedSequence = new int[endIndex-startIndex];
		AudioTrimSelector.counter = 0;
			
		Debug.debug(3,"BuildTrimedSequence buildUsableVoiceRecognitionSequence "
			+ " inputSamples[startIndex]: "+inputSamples[startIndex]+", inputSamples[startIndex+1]"
			+ ": "+inputSamples[startIndex+1]+", inputSamples[startIndex+2]: "
			+ inputSamples[startIndex+2]);
		
		
		VoiceRecognitionUtil.rebuildStreamDetailsUnspikeAndAvg(id,inputSamples,startIndex,endIndex
				,AudioTrimSelector.averageAmplitude);
		
		
	/*	if(EntryPointMethods.getSvitch().equals("voiceRecognition")) {
			
			int[][] slopes = new int[2][];
			
			slopes[0] = VoiceRecognitionUtil.buildSlopeArrayAndConvertToIntArray(AudioAnalysisThread.startedVoiceCheck.get(id).getAmplitudeWaveMap());
			slopes[1] = VoiceRecognitionUtil.buildSlopeArrayAndConvertToIntArray(AudioAnalysisThread.startedVoiceCheck.get(id).getFrequencyWaveMap());
			
			AudioAnalysisThread.startedVoiceCheck.get(id).setVoiceReconitionCheckSlopesArray(slopes);
		}
	*/	
		AudioTrimSelector.trimedSequence = new int[3];			

		Debug.debug(1,"BuildTrimedSequence buildUsableVoiceRecognitionSequence New Array: "
		+ AudioTrimSelector.trimedSequence.length+" startIndex "+startIndex+", endIndex "+endIndex
			+", Array.ToString: "+Arrays.toString(AudioTrimSelector.trimedSequence));
	}
}
