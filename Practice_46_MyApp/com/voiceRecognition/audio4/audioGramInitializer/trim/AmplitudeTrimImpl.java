package com.voiceRecognition.audio4.audioGramInitializer.trim;

import com.voiceRecognition.audio0.main.VoiceRecognitionAppSetup;
import com.voiceRecognition.audio4.audioGramInitializer.trim.sequence.Sequence;
import com.voiceRecognition.audio4.audioGramInitializer.trim.sequence.SequenceFilter;
import com.voiceRecognition.audio8.util.Debug;

public class AmplitudeTrimImpl implements AudioTrim{
	
	private static int debug_level_INFO = 5;
	private static int debud_level_DEBUG = 5;
		
	public AmplitudeTrimImpl(int id, int[] inputArray) {
		
		buildTrimSequenceBorders(id, inputArray);
		
		AudioTrimSelector.setSequencePoints();

		BuildTrimedSequence.mainTrimedSequenceBuilder(id, inputArray);
	}

	@Override
	public void buildTrimSequenceBorders(int id,int[] inputArray) {
		
		AudioTrimSelector.setZeroMultiTrimVariables();
		SequenceFilter sequenceFilter = AudioTrimSelector.amplitudeSequenceFilter;
		AudioTrimSelector.sequence = new Sequence(-1,-1);
		
		Debug.debug(debug_level_INFO,"AmplitudeTrimImpl buildTrimSequenceBorders 0.0 IDLE LIMIT: "
			+VoiceRecognitionAppSetup.IDLE_AMPLITUDE_VOLUME+ ", "+sequenceFilter.toString());
				
		for(int i = 1; i < inputArray.length; i++) {
		
			AudioTrimSelector.a_sum += Math.abs(inputArray[i]);
			AudioTrimSelector.counter++;
								 
			if(AudioTrimSelector.sequence.getStartIndex() == -1 
				&& AudioTrimSelector.counter == sequenceFilter.getStartLengthCheck()) {
				 
				 AudioTrimSelector.a_avg = 
						 AudioTrimSelector.a_sum / sequenceFilter.getStartLengthCheck();
				 
				 Debug.debug(debud_level_DEBUG,"AmplitudeTrimImpl buildTrimSequenceBorders 1.0 Index: " +i
					+ ", AmplitudeStartLimit: "+sequenceFilter.getAmplitudeStartLimit()  
				 	+ ", avg "+AudioTrimSelector.a_avg +", LengthCheck: "
					+ sequenceFilter.getStartLengthCheck());
				 
				 if(AudioTrimSelector.a_avg > sequenceFilter.getAmplitudeStartLimit()) {
					 
					 AudioTrimSelector.sequence
					 .setStartIndex((int) (i -  AudioTrimSelector.getLengthCorrection(false,
							 VoiceRecognitionAppSetup.START_BEFORE_MSEC_TRIM_LENGTH,false)));
					 
					 Debug.debug(debud_level_DEBUG,"AmplitudeTrimImpl buildTrimSequenceBorders 1.1 Index: "+i+", avg " 
						+AudioTrimSelector.a_avg+ ", startIndex: "
						+AudioTrimSelector.sequence.getStartIndex() + " AmplitudeStartLimit: "
						+sequenceFilter.getAmplitudeStartLimit());
					 
					 AudioTrimSelector.setZeroMultiTrimVariables();
					 
				 } else {
					 
					 AudioTrimSelector.setZeroMultiTrimVariables();
				 }
			 }
			 
			if(AudioTrimSelector.sequence.getStartIndex() != -1 
				&& AudioTrimSelector.sequence.getEndIndex() == -1 
				&& AudioTrimSelector.counter == sequenceFilter.getEndLengthCheck()) {
				 
				AudioTrimSelector.a_avg=AudioTrimSelector.a_sum/sequenceFilter.getEndLengthCheck();
				 
				Debug.debug(debud_level_DEBUG,"AmplitudeTrimImpl buildTrimSequenceBorders 2.0 Index: "+i+", AmplitudeEndLimit: "
					+sequenceFilter.getAmplitudeEndLimit() +", avg: "+AudioTrimSelector.a_avg 
					+", LengthCheck: " + sequenceFilter.getEndLengthCheck());
				 
				if(AudioTrimSelector.a_avg < sequenceFilter.getAmplitudeEndLimit()) {
					
					AudioTrimSelector.sequence
					.setEndIndex((int) (i -  AudioTrimSelector.getLengthCorrection(false,
						VoiceRecognitionAppSetup.AFTER_END_MSEC_TRIM_LENGTH,false)));
					 
					Debug.debug(debud_level_DEBUG,"AmplitudeTrimImpl buildTrimSequenceBorders 2.1 Index: "+i+", avg "
						+ AudioTrimSelector.a_avg+ ", endIndex: "
						+ AudioTrimSelector.sequence.getEndIndex()
						+ " AmplitudeEndLimit: "+sequenceFilter.getAmplitudeEndLimit());
					 
					AudioTrimSelector.setZeroMultiTrimVariables();
					 
				} else {
					
					 AudioTrimSelector.setZeroMultiTrimVariables();
				}
			}
			
			if(AudioTrimSelector.sequence.getStartIndex()!=-1&&AudioTrimSelector.sequence.getEndIndex()!=-1) {
				
				if(AudioTrimSelector.sequence.getStartIndex() < 0) 			
					AudioTrimSelector.sequence.setStartIndex(0);
							 
				if(AudioTrimSelector.sequence.getEndIndex()-AudioTrimSelector.sequence.getStartIndex()>3000)
					AudioTrimSelector.sequences.add(AudioTrimSelector.sequence);
				
					Debug.debug(debud_level_DEBUG,"AmplitudeTrimImpl buildTrimSequenceBorders 3.0 "
							+ AudioTrimSelector.sequence.toString());
					
					AudioTrimSelector.sequence = new Sequence(-1,-1);	
			}
		}	
		
		Debug.debug(debud_level_DEBUG,"AmplitudeTrimImpl buildTrimSequenceBorders 4.0 "+inputArray.length);
		if(AudioTrimSelector.sequence.getStartIndex() != -1 && AudioTrimSelector.sequence.getEndIndex() == -1 
				&& inputArray.length -  AudioTrimSelector.sequence.getStartIndex() > 10000) {
			
			if(AudioTrimSelector.sequence.getStartIndex() < 0)
				AudioTrimSelector.sequence.setStartIndex(0);
			
			AudioTrimSelector.sequence.setEndIndex(inputArray.length);
			AudioTrimSelector.sequences.add(AudioTrimSelector.sequence);
		}
		
		Debug.debug(debud_level_DEBUG,"AmplitudeTrimImpl buildTrimSequenceBorders " 
			+ (inputArray.length -  AudioTrimSelector.sequence.getStartIndex()));
				return ;
	}
}
