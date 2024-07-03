package com.threads;

import java.util.ArrayList;
import java.util.List;

import com.library.audio.audioGramInitializer.AudioGramConstructor;
import com.library.audio.soundBuilder.Sequence;
import com.library.audio.soundBuilder.SoundBuilder;
import com.library.util.AudioUtil;

public class Main {
	
	/**
	 * Files are saved at classpath (path to class files)
	 *                    or this folder......
	 * \src\main\resources\static\audio\spektrum\record Name
	 * 
	 * Run one option at one time.
	 */
	public static void main(String[] args) {
		
		//AudioGramConstructor.buildMultiAudioGrammFromInputFile("audiomass2.wav");
		
		//TimeFixedSoundRecorder recorder = new TimeFixedSoundRecorder("record Name",1000);	
		
		AudioListener listener = new AudioListener("record Name");
		
		//buildSequence();
	}

	
	/**
	 * Sequence param 1 = Amplitude,
	 * Sequence param 2 = Frequency, 
	 * Sequence param 3 = MilisecLength,
	 * 
	 *Multi Sound Sequence will be created in adding order.    
	 */
	private static void buildSequence() {
		
		List<Sequence> sequence = new ArrayList<Sequence>();
	
//		sequence.add(new Sequence(10000,100,1000));
		sequence.add(new Sequence(10000,300,300));
		sequence.add(new Sequence(20000,700,300));
		sequence.add(new Sequence(15000,11050,300));
		sequence.add(new Sequence(10000,500,300));
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

		StartSaveAudioFeatures save = new StartSaveAudioFeatures(SoundBuilder.mainByteBuilder(sequence),"alma", AudioUtil.getAudioFormat("MainRecord"));
	//	StartSaveAudioFeatures save = new StartSaveAudioFeatures(SoundBuilder.mainIntBuilder(sequence),"alma", AudioUtil.getAudioFormat("MainRecord"));
		//StartSaveAudioFeatures save = new StartSaveAudioFeatures(SoundBuilder.mainBuilder(sequence), AudioUtil.getAudioFormat(),"builded-" +LocalDateTime.now().getHour()+ "-"+LocalDateTime.now().getMinute()+"- "+LocalDateTime.now().getSecond());	
	}
}
