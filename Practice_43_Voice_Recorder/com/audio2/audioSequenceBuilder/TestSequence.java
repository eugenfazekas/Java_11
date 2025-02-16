package com.audio2.audioSequenceBuilder;

import java.util.ArrayList;
import java.util.List;

public class TestSequence {

	public static List<AudioWave> getTestSequence() {
		
		List<AudioWave> sequence = new ArrayList<AudioWave>();
	
		//		sequence.add(new Sequence(10000,100,1000));
		sequence.add(new AudioWave(10000,10,1000));
	//		sequence.add(new Sequence(20000,700,300));
	//		sequence.add(new Sequence(15000,11050,300));
	//		sequence.add(new Sequence(10000,500,300));
	//		sequence.add(new Sequence(10000,600,100));
	//		sequence.add(new Sequence(10000,700,100));
	//		sequence.add(new Sequence(10000,800,100));
	//		sequence.add(new Sequence(10000,900,100));
	//		sequence.add(new Sequence(11000,1000,100));
	//		sequence.add(new Sequence(10000,2000,100));
	//		sequence.add(new Sequence(10000,3000,100));
	//		sequence.add(new Sequence(10000,4000,100));
	//		sequence.add(new Sequence(10000,5000,100));
	//		sequence.add(new Sequence(10000,6000,100));
	//		sequence.add(new Sequence(10000,7000,100));
	//		sequence.add(new Sequence(10000,8000,100));
	//		sequence.add(new Sequence(10000,9000,100));
	//		sequence.add(new Sequence(10000,10000,100));
	//		sequence.add(new Sequence(10000,11000,100));
		
	//		sequence.add(new Sequence(20000,50,100));
	//		sequence.add(new Sequence(11000,200,100));
	//		sequence.add(new Sequence(12000,300,100));
	//		sequence.add(new Sequence(13000,400,100));
	//		sequence.add(new Sequence(14000,500,100));
	//		sequence.add(new Sequence(15000,600,100));
	//		sequence.add(new Sequence(16000,700,100));
	//		sequence.add(new Sequence(17000,800,100));
	//		sequence.add(new Sequence(18000,900,100));
	//		sequence.add(new Sequence(19000,1000,100));
	//		sequence.add(new Sequence(20000,2000,100));
	//		sequence.add(new Sequence(21000,3000,100));
	//		sequence.add(new Sequence(22000,4000,100));
	//		sequence.add(new Sequence(23000,5000,100));
	//		sequence.add(new Sequence(24000,6000,100));
	//		sequence.add(new Sequence(25000,7000,100));
	//		sequence.add(new Sequence(26000,8000,100));
	//		sequence.add(new Sequence(27000,9000,100));
	//		sequence.add(new Sequence(28000,10000,100));
	//		sequence.add(new Sequence(29000,11000,100));
	
	//StartAudioAnalysisFeatures save = 
	//new StartAudioAnalysisFeatures(SoundBuilder.mainSequenceByteBuilderByTimeLength(sequence)
	//,"alma", AudioUtil.getAudioFormat("MainRecord"));
	//StartSaveAudioFeatures save = new StartSaveAudioFeatures(SoundBuilder
	//.mainSequenceByteBuilderByTimeLength(sequence),"alma"
	//, AudioUtil.getAudioFormat("MainRecord"));
			return sequence;
	}
}
