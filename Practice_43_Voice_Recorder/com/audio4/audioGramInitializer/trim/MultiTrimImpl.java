package com.audio4.audioGramInitializer.trim;

import com.audio0.main.AppSetup;
import com.audio2.recorder.AudioListener;
import com.audio3.recorder.refinary.StreamRefinaryFrequencyMethods;
import com.audio4.audioGramInitializer.trim.sequence.Sequence;
import com.audio4.audioGramInitializer.trim.sequence.SequenceFilter;
import com.audio8.util.Debug;

public class MultiTrimImpl implements AudioTrim{
	
	public MultiTrimImpl (int id,int[] inputSamples,int[]audioStreamDetails,SequenceFilter sequenceFilter) {
		
		Debug.debug(3,"MultiTrimImpl 0.0 IDLE LIMIT: "+AppSetup.idle_amplitude_volume 
		+", audioStreamDetails.length: " +audioStreamDetails.length+","+sequenceFilter.toString());
		
		if(AppSetup.frequencyRefinary)
			 Debug.debug(3,"MultiTrimImpl Syncheck 1: "+inputSamples[1]+ ", Syncheck 2: " +
				audioStreamDetails[1]);
		
		buildSequence(audioStreamDetails, sequenceFilter);
		
		AudioTrimSelector.setSequencePoints();
		
		//getFocusedMultiSequencePoints(audioStreamDetails, AppSetup.idle_amplitude_volume 
		//   *2,AppSetup.idle_amplitude_volume *2, sequenceFilter.getStartFrequencyLowerLimit()
        //  ,sequenceFilter.getStartFrequencyUpperLimit(),
		//  sequenceFilter.getEndFrequencyLowerLimit(),sequenceFilter.getEndFrequencyUpperLimit(),
		//  sequenceFilter.getStartLengthCheck(),sequenceFilter.getEndLengthCheck());
		
		BuildTrimedSequence.mainTrimedSequenceBuilder(id,inputSamples,audioStreamDetails);
	}

	@Override
	public void buildSequence(int[] audioStreamDetails, SequenceFilter sequenceFilter) {
		
		AudioTrimSelector.setZeroMultiTrimVariables();
		AudioTrimSelector.sequence = new Sequence(-1,-1);
		AudioTrimSelector.nextIndexCheck = sequenceFilter.getStartLengthCheck();
		 
		for(int i = 0; i < audioStreamDetails.length-3; i=i+3) {
			 
			AudioTrimSelector.a_sum += audioStreamDetails[i+1];
			AudioTrimSelector.counter++;
			AudioTrimSelector.f_sum +=  AudioListener.format.getSampleRate();
			AudioTrimSelector.freqBuffDive 
				+= StreamRefinaryFrequencyMethods.frequencySampleLengths[audioStreamDetails[i+2]];
			 
			 Debug.debug(5,"AudioTrimSelector index: "+audioStreamDetails[i]+ ", Amplitude: " 
			  +audioStreamDetails[i+1]+",  a_sum: "+ AudioTrimSelector.a_sum + ", Frequency: "
		 	  +audioStreamDetails[i+2]+",  AudioTrimSelector.f_sum: "+ AudioTrimSelector.f_sum);	 
			
			if(AudioTrimSelector.sequence.getStartIndex() == -1 
				&& audioStreamDetails[i] >= AudioTrimSelector.nextIndexCheck) {
				
				AudioTrimSelector.a_avg = AudioTrimSelector.a_sum / AudioTrimSelector.counter;	
				AudioTrimSelector.f_avg = AudioTrimSelector.f_sum / AudioTrimSelector.freqBuffDive;	
				AudioTrimSelector.nextIndexCheck =
								 audioStreamDetails[i] + sequenceFilter.getStartLengthCheck();
				
				Debug.debug(3,"MultiTrimImpl 1.0 i: "+i+ ", index: "+audioStreamDetails[i]
					+ ", AudioTrimSelector.nextIndexCheck: "+AudioTrimSelector.nextIndexCheck
					+ ", AmplitudeStartLimit: "+sequenceFilter.getAmplitudeStartLimit() 
					+ ", a_avg "+AudioTrimSelector.a_avg+", f_avg:"+ AudioTrimSelector.f_avg
					+", f.l.l: "+ sequenceFilter.getStartFrequencyLowerLimit() + ", f.h.l: "
					+sequenceFilter.getStartFrequencyUpperLimit() );
					 
				if(AudioTrimSelector.a_avg > sequenceFilter.getAmplitudeStartLimit() 
					&& (AudioTrimSelector.f_avg > sequenceFilter.getStartFrequencyLowerLimit() 
					&& AudioTrimSelector.f_avg < sequenceFilter.getStartFrequencyUpperLimit())) {
					
					AudioTrimSelector.sequence
					.setStartIndex(audioStreamDetails[i] -sequenceFilter.getStartLengthCheck()*3);
					AudioTrimSelector.sequence
					.setDetailsStartIndex(i- AudioTrimSelector.counter*3*3);  
					AudioTrimSelector.nextIndexCheck =
									audioStreamDetails[i] + sequenceFilter.getEndLengthCheck();

					Debug.debug(3,"MultiTrimImpl 1.1 i: "+i+ ", index: "+ audioStreamDetails[i]
						+", startIndex: "+AudioTrimSelector.sequence.getStartIndex()
						+", detailsStartIndex: " +AudioTrimSelector.sequence.getDetailsStartIndex()
						+ ", a_avg: "+AudioTrimSelector.a_avg + ", f_avg: "+AudioTrimSelector.f_avg 
						+ ",AudioTrimSelector.nextIndexCheck: "+AudioTrimSelector.nextIndexCheck);
					  
					AudioTrimSelector.setZeroMultiTrimVariables();
					
				 } else {
					 
					 AudioTrimSelector.setZeroMultiTrimVariables();
				 }
			}
			//	 System.out.println("StartIndex: "+AudioTrimSelector.sequence.getStartIndex() 
			//+", i: "+i+", index: "+ inputArray[i] + ", amplitude: "+inputArray[i+1]+ ", a_sum: "
			//+ AudioTrimSelector.a_sum + ", frequency: "+inputArray[i+2]+ ", f_sum: "
			//+ AudioTrimSelector.f_sum + ",chk: "+sequenceFilter.getEndLengthCheck());		 
			
			if(AudioTrimSelector.sequence.getStartIndex() != -1 
				&& AudioTrimSelector.sequence.getEndIndex() == -1 
				&& audioStreamDetails[i] >= AudioTrimSelector.nextIndexCheck) {
				  
				AudioTrimSelector.a_avg = AudioTrimSelector.a_sum / AudioTrimSelector.counter;	
				AudioTrimSelector.f_avg = AudioTrimSelector.f_sum / AudioTrimSelector.freqBuffDive;	
				AudioTrimSelector.nextIndexCheck = audioStreamDetails[i] +  sequenceFilter.getEndLengthCheck();
				
				Debug.debug(3,"MultiTrimImpl 2.0 i: "+i+ ", index: "+audioStreamDetails[i]
					+ ", AudioTrimSelector.nextIndexCheck: "+AudioTrimSelector.nextIndexCheck 
					+ ", AmplitudeEndLimit: "+sequenceFilter.getAmplitudeEndLimit()
					+ ", a_avg "+AudioTrimSelector.a_avg+", f_avg:"+ AudioTrimSelector.f_avg+", f.l.l: "
					+ sequenceFilter.getEndFrequencyLowerLimit() + ", f.h.l: "
					+ sequenceFilter.getEndFrequencyUpperLimit());
				
				if(AudioTrimSelector.a_avg < sequenceFilter.getAmplitudeEndLimit() || (
					AudioTrimSelector.a_avg < sequenceFilter.getAmplitudeEndLimit() && 
				    AudioTrimSelector.f_avg > sequenceFilter.getEndFrequencyLowerLimit() &&
				    AudioTrimSelector.f_avg < sequenceFilter.getEndFrequencyUpperLimit())) {	
					
					AudioTrimSelector.sequence.setEndIndex(audioStreamDetails[i]);
					AudioTrimSelector.sequence.setDetailsEndIndex(i);  
					 
					Debug.debug(3,"MultiTrimImpl 2.1 i: "+i+ ", index: "+ audioStreamDetails[i]
						+", endIndex: "+AudioTrimSelector.sequence.getEndIndex()
						+", detailsEndIndex: "+AudioTrimSelector.sequence.getDetailsEndIndex()
					 	+ ", a_avg: "+AudioTrimSelector.a_avg + ", f_avg: "+ AudioTrimSelector.f_avg);		
					
						AudioTrimSelector.setZeroMultiTrimVariables();
						
				 } else {
					 
					 AudioTrimSelector.setZeroMultiTrimVariables();
				 }				 	
			  }
				
			if(AudioTrimSelector.sequence.getStartIndex() != -1 && AudioTrimSelector.sequence.getEndIndex() != -1 ) {
				
				if(AudioTrimSelector.sequence.getStartIndex() < 0 || AudioTrimSelector.sequence.getDetailsStartIndex() < 0) {
					
					AudioTrimSelector.sequence.setStartIndex(0);
					AudioTrimSelector.sequence.setDetailsStartIndex(0);
				}
				 
				if(AudioTrimSelector.sequence.getEndIndex() - AudioTrimSelector.sequence.getStartIndex() > 3000 )
					AudioTrimSelector.sequences.add(AudioTrimSelector.sequence);
				
					Debug.debug(3,"MultiTrimImpl 3.0 "+ AudioTrimSelector.sequence.toString());
					
					AudioTrimSelector.sequence = new Sequence(-1,-1);	
					AudioTrimSelector.nextIndexCheck = audioStreamDetails[i] + sequenceFilter.getStartLengthCheck();
			}
		}	
		System.out.println("MultiTrimImpl "+audioStreamDetails[audioStreamDetails.length-3]);
		
		if(AudioTrimSelector.sequence.getStartIndex() != -1 && AudioTrimSelector.sequence.getEndIndex() == -1 
				&& audioStreamDetails[audioStreamDetails.length-3] -  AudioTrimSelector.sequence.getStartIndex() > 10000) {
			
			if(AudioTrimSelector.sequence.getStartIndex() < 0 || AudioTrimSelector.sequence.getDetailsStartIndex() < 0) {
				
				AudioTrimSelector.sequence.setStartIndex(0);
				AudioTrimSelector.sequence.setDetailsStartIndex(0);
			}
			
			AudioTrimSelector.sequence.setEndIndex(audioStreamDetails[audioStreamDetails.length-3]);
			AudioTrimSelector.sequence.setDetailsEndIndex(audioStreamDetails.length-3);
			AudioTrimSelector.sequences.add(AudioTrimSelector.sequence);
			System.out.println(audioStreamDetails[audioStreamDetails.length-3]);
			
		}
	}
}
