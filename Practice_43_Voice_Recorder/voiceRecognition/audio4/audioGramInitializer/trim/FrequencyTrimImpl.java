package voiceRecognition.audio4.audioGramInitializer.trim;

import java.util.Arrays;

import voiceRecognition.audio0.main.AppSetup;
import voiceRecognition.audio2.recorder.AudioListener;
import voiceRecognition.audio4.audioGramInitializer.mainInit.AudioAnalysisThread;
import voiceRecognition.audio4.audioGramInitializer.trim.sequence.Sequence;
import voiceRecognition.audio5.audioGramBuilder.AudioGramObject;
import voiceRecognition.audio8.util.Debug;

public class FrequencyTrimImpl implements AudioTrim{
	
	static int AVG_MILISEC_LENGTH;
	static int mSecCounter;
	static AudioGramObject first;
	static AudioGramObject middle;
	static AudioGramObject last;
	static AudioGramObject temp;
	static int[] amplitudes;
	static int[] frequencys;
	static int[] amplitudesTemp;
	static int[] frequencysTemp;
	static int counter;
	static int i;
	static int amplitude;
	
	private static int debug_level_INFO = 5;
	
	public FrequencyTrimImpl (int id,int[] inputIntArray) {

		buildTrimSequenceBorders(id,inputIntArray);
		
		AudioTrimSelector.setSequencePoints();
		
		BuildTrimedSequence.mainTrimedSequenceBuilder(id, inputIntArray);
	}

	@Override
	public void buildTrimSequenceBorders(int id, int[] inputIntArray) {
				
		int[] borders;
		amplitudes = new int[200];
		frequencys = new int[200];
		counter = 0;
		AVG_MILISEC_LENGTH = AudioGramTrimUtil.getBuffersLengthByMilisec(
				(int)AudioAnalysisThread.startedVoiceCheck.get(id).getAudioFormat().getSampleRate()
					,AppSetup.AUDIO_BUFFER_MILISEC_LENGTH);

		Debug.debug(debug_level_INFO,"FrequencyTrimImpl buildTrimSequenceBorders id: "+id + ", AVG_MILISEC_LENGTH: " 
				+ AVG_MILISEC_LENGTH);
		
		if(AudioAnalysisThread.startedVoiceCheck.get(id).getFrequencyWaveMap() != null) {
			
			frequencys = AudioAnalysisThread.startedVoiceCheck.get(id).getFrequencyWaveMap();
			counter = AudioAnalysisThread.startedVoiceCheck.get(id).getFrequencyWaveMap().length;
		}
		
		if(AudioAnalysisThread.startedVoiceCheck.get(id).getFrequencyWaveMap() == null) 
			buildFrequencyMap(inputIntArray);
	
		borders = AudioGramTrimUtil.buildSequenceBorders(frequencys,counter,20,20);
		
		borders[1] = borders[1]-5 > 2 ? borders[1] - 5 : borders[1];
		borders[4] = (borders[4]+6) * AVG_MILISEC_LENGTH <  inputIntArray.length ? borders[4]+6 : borders[4];
		
		AudioTrimSelector.sequences.add(new Sequence(borders[1]*AVG_MILISEC_LENGTH,borders[4]*AVG_MILISEC_LENGTH));
		
		 AudioTrimSelector.setZeroMultiTrimVariables();
		
		Debug.debug(debug_level_INFO,"FrequencyTrimImpl buildTrimSequenceBorders id: "+id+", Array old Length: " 
				+inputIntArray.length);		
		Debug.debug(debug_level_INFO,"FrequencyTrimImpl buildTrimSequenceBorders id: "+id+", Array new Length: " 
				+ ((borders[4]*AVG_MILISEC_LENGTH) - (borders[1]*AVG_MILISEC_LENGTH)) );
		
		if(AppSetup.voiceAreaRecognition && AppSetup.frequencyTrim ) {
			
			amplitudesTemp = new int[counter];
			frequencysTemp = new int[counter];
			
			counter = 0;
			
			for(i = borders[1]; i < borders[4] ; i++) {
				
				amplitudesTemp[counter] = amplitudes[i];
				frequencysTemp[counter++] = frequencys[i];		
			}
			
			Debug.debug(debug_level_INFO,"FrequencyTrimImpl buildTrimSequenceBorders voiceAreaRecognition id: "+id+
				", Amplitude Array old Length: " +amplitudes.length + ", Amplitude Array: " 
				+ Arrays.toString(amplitudes));
			
			Debug.debug(debug_level_INFO,"FrequencyTrimImpl buildTrimSequenceBorders voiceAreaRecognition id: "+id+
				", Amplitude Array new Length: " +amplitudesTemp.length+ ", Amplitude Array: " 
				+ Arrays.toString(amplitudesTemp));
			
			Debug.debug(debug_level_INFO,"FrequencyTrimImpl buildTrimSequenceBorders voiceAreaRecognition id: "+id
				+", Frequency Array old Length: " +frequencys.length+ ", Amplitude Array: " 
				+ Arrays.toString(frequencys));
			
			Debug.debug(debug_level_INFO,"FrequencyTrimImpl buildTrimSequenceBorders voiceAreaRecognition id: "+id
				+", Frequency Array new Length: " +frequencysTemp.length
				+ ", Amplitude Array: " + Arrays.toString(frequencysTemp));
			
			AudioAnalysisThread.startedVoiceCheck.get(id).setAmplitudeWaveMap(amplitudesTemp);
			AudioAnalysisThread.startedVoiceCheck.get(id).setFrequencyWaveMap(frequencysTemp);
		}
	}
	
	private static void buildFrequencyMap(int[] inputIntArray) {
		
		first = buildAVGSequence(inputIntArray, 0, AVG_MILISEC_LENGTH);
		middle = buildAVGSequence(inputIntArray, AVG_MILISEC_LENGTH, AVG_MILISEC_LENGTH*2);
		
		last = new AudioGramObject(AVG_MILISEC_LENGTH);
		i = AVG_MILISEC_LENGTH * 2;

		for(; i <  inputIntArray.length; i = i + AVG_MILISEC_LENGTH) {
			
			last = buildAVGSequence(inputIntArray, i, i + AVG_MILISEC_LENGTH);

			amplitude = calculateAVGAmplitude(first,middle,last);
			amplitudes[counter] = amplitude;
			
			frequencys[counter++] = AudioGramTrimUtil.getFrecvencys(first.getSamples(),
					middle.getSamples(),last.getSamples(),((int) AudioListener.format.getSampleRate()))[1];
			first = middle;
			middle = last;
			last = new AudioGramObject(AVG_MILISEC_LENGTH);
		}
	}
	
	private static AudioGramObject  buildAVGSequence(int[] inputIntArray, int startIndex, int endIndex) {
				
		temp = new AudioGramObject(AVG_MILISEC_LENGTH);
		
		for(int i = startIndex; i <  endIndex; i++) 	
			temp.addSampleToObject(inputIntArray[i]);
						
		 	return temp;
	}
	
	private static int calculateAVGAmplitude(AudioGramObject first, AudioGramObject middle, AudioGramObject last) {
		
		amplitude = (first.getPosAmplitude() / first.getPosCounter()
				+first.getNegAmplitude() / first.getNegCounter()
				+middle.getPosAmplitude()/ middle.getPosCounter()
				+middle.getNegAmplitude()/ middle.getNegCounter()
				+last.getPosAmplitude()  / last.getPosCounter()
				+last.getNegAmplitude()  / last.getNegCounter())/3;
		
		return amplitude;
	}
}
