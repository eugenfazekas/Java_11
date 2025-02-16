package com.audio4.audioGramInitializer.trim;

import com.audio0.main.AppSetup;
import com.audio4.audioGramInitializer.trim.sequence.Sequence;
import com.audio4.audioGramInitializer.trim.sequence.SequenceFilter;
import com.audio8.util.Debug;

public class AmplitudeTrimImpl implements AudioTrim{
		
	public AmplitudeTrimImpl(int id, int[] inputArray ,SequenceFilter sequenceFilter) {
		
		buildSequence(inputArray,sequenceFilter);
		
		AudioTrimSelector.setSequencePoints();
		
//		getFocusedAmplitudeSequencePoints(inputArray,AppSetup.idle_amplitude_volume
//			,AppSetup.idle_amplitude_volume,sequenceFilter);
//		
		BuildTrimedSequence.mainTrimedSequenceBuilder(id, inputArray,null);
	}

	@Override
	public void buildSequence(int[] inputArray ,SequenceFilter sequenceFilter) {
		
		AudioTrimSelector.setZeroMultiTrimVariables();
		
		AudioTrimSelector.sequence = new Sequence(-1,-1);
		
		Debug.debug(3,"AmplitudeTrimImplementation 0.0 IDLE LIMIT: "+AppSetup.idle_amplitude_volume
			+ ", "+sequenceFilter.toString());
				
		for(int i = 1; i < inputArray.length; i++) {
		
			AudioTrimSelector.a_sum += Math.abs(inputArray[i]);
			AudioTrimSelector.counter++;
								 
			if(AudioTrimSelector.sequence.getStartIndex() == -1 
				&& AudioTrimSelector.counter == sequenceFilter.getStartLengthCheck()) {
				 
				 AudioTrimSelector.a_avg = 
						 AudioTrimSelector.a_sum / sequenceFilter.getStartLengthCheck();
				 
				 Debug.debug(3,"AmplitudeTrimImplementation 1.0 Index: " +i
					+ ", AmplitudeStartLimit: "+sequenceFilter.getAmplitudeStartLimit()  
				 	+ ", avg "+AudioTrimSelector.a_avg +", LengthCheck: "
					+ sequenceFilter.getStartLengthCheck());
				 
				 if(AudioTrimSelector.a_avg > sequenceFilter.getAmplitudeStartLimit()) {
					 
					 AudioTrimSelector.sequence
					 .setStartIndex((int) (i -  sequenceFilter.getStartLengthCheck()*1.2));
					 
					 Debug.debug(3,"AmplitudeTrimImplementation 1.1 Index: "+i+", avg " 
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
				 
				Debug.debug(3,"AmplitudeTrimImplementation 2.0 Index: "+i +", AmplitudeEndLimit: "
					+sequenceFilter.getAmplitudeEndLimit() +", avg: "+AudioTrimSelector.a_avg 
					+", LengthCheck: " + sequenceFilter.getEndLengthCheck());
				 
				if(AudioTrimSelector.a_avg < sequenceFilter.getAmplitudeEndLimit()) {
					
					AudioTrimSelector.sequence.setEndIndex((int) (i -  sequenceFilter.getStartLengthCheck()* 1));
					 
					Debug.debug(3,"AmplitudeTrimImplementation  2.1 Index: "+i+", avg "
						+ AudioTrimSelector.a_avg+ ", endIndex: "
						+ AudioTrimSelector.sequence.getEndIndex()
						+ " AmplitudeEndLimit: "+sequenceFilter.getAmplitudeEndLimit());
					 
					AudioTrimSelector.setZeroMultiTrimVariables();
					 
				} else {
					
					 AudioTrimSelector.setZeroMultiTrimVariables();
				}
			}
			
			if(AudioTrimSelector.sequence.getStartIndex() != -1 && AudioTrimSelector.sequence.getEndIndex() != -1 ) {
				
				if(AudioTrimSelector.sequence.getStartIndex() < 0) 			
					AudioTrimSelector.sequence.setStartIndex(0);
							 
				if(AudioTrimSelector.sequence.getEndIndex() - AudioTrimSelector.sequence.getStartIndex() > 3000 )
					AudioTrimSelector.sequences.add(AudioTrimSelector.sequence);
				
					Debug.debug(3,"AmplitudeTrimImplementation 3.0 "+ AudioTrimSelector.sequence.toString());
					
					AudioTrimSelector.sequence = new Sequence(-1,-1);	
			}
		}	
		
		Debug.debug(3,"AmplitudeTrimImplementation 4.0 "+inputArray.length);
		if(AudioTrimSelector.sequence.getStartIndex() != -1 && AudioTrimSelector.sequence.getEndIndex() == -1 
				&& inputArray.length -  AudioTrimSelector.sequence.getStartIndex() > 10000) {
			
			if(AudioTrimSelector.sequence.getStartIndex() < 0)
				AudioTrimSelector.sequence.setStartIndex(0);
			
			AudioTrimSelector.sequence.setEndIndex(inputArray.length);
			AudioTrimSelector.sequences.add(AudioTrimSelector.sequence);
		}
		
		Debug.debug(3,"AmplitudeTrimImplementation: " + (inputArray.length -  AudioTrimSelector.sequence.getStartIndex()));
				return ;
	}
	
	public static void getFocusedAmplitudeSequencePoints(int [] samples ,int startAMP,int endAMP,
			SequenceFilter sequenceFilter) {
		
		if(AudioTrimSelector.startIndex < 0 ||  AudioTrimSelector.endIndex == -1) return; 
			
		startAMP = (int) (startAMP *0.8);
		endAMP = (int) (endAMP *0.8);
		
		AudioTrimSelector.setZeroMultiTrimVariables();
		
		for(int i = AudioTrimSelector.startIndex+1; i < AudioTrimSelector.endIndex; i++) {
			
			AudioTrimSelector.a_sum+= Math.abs(samples[i]);
			AudioTrimSelector.counter++;
			 
			if(AudioTrimSelector.counter == 100) {
				 
				AudioTrimSelector.a_avg = AudioTrimSelector.a_sum /AudioTrimSelector.counter;
				 
				Debug.debug(1,"BuildTrimedSequence get focused i: " + i +" startAMP: "+startAMP
					+",  avg: "+AudioTrimSelector.a_avg);
				 
				if(AudioTrimSelector.a_avg > startAMP) {
					 
					AudioTrimSelector.startIndex = i - AudioTrimSelector.counter ;
					AudioTrimSelector.setZeroMultiTrimVariables();
					break;
				}
				 
				 AudioTrimSelector.setZeroMultiTrimVariables();
			 }
		}
		
		AudioTrimSelector.setZeroMultiTrimVariables();

		for(int i = AudioTrimSelector.endIndex; i > AudioTrimSelector.startIndex; i--) {
			
			AudioTrimSelector.a_sum+= Math.abs(samples[i]);
			AudioTrimSelector.counter++;
			 
			if(AudioTrimSelector.counter == 100) {
				
				AudioTrimSelector.a_avg = AudioTrimSelector.a_sum /AudioTrimSelector.counter;
				Debug.debug(1,"BuildTrimedSequence get focused i: " + i +" endAMP: "
				+endAMP+",  avg: "+AudioTrimSelector.a_avg);
				
				if(AudioTrimSelector.a_avg > endAMP) {
					 AudioTrimSelector.endIndex= i + AudioTrimSelector.counter;
					 AudioTrimSelector.setZeroMultiTrimVariables();
					 break;
				 }
				
				 AudioTrimSelector.setZeroMultiTrimVariables();
			 }
		}
		
		Debug.debug(1,"BuildTrimedSequence getFocusedAmplitudeSequencePoints result startIndex: "
			+ AudioTrimSelector.startIndex + ", endIndex: "+AudioTrimSelector.endIndex);
	}
}
