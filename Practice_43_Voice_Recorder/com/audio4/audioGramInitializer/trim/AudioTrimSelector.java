package com.audio4.audioGramInitializer.trim;

import java.util.ArrayList;
import java.util.List;

import com.audio0.main.AppSetup;
import com.audio4.audioGramInitializer.AudioAnalysisThread;
import com.audio4.audioGramInitializer.trim.sequence.Sequence;
import com.audio4.audioGramInitializer.trim.sequence.SequenceFilter;
import com.audio8.util.Debug;
import com.audio8.util.filters.NoiseFilter2;

public class AudioTrimSelector {
	
	static AudioTrim trim;
	static int a_sum;
	static int f_sum;
    static int a_avg;
    static int f_avg;
    static int counter;
	static int freqBuffDive;
    static int startIndex;
    static int endIndex;
    static int detailsStartIndex;
    static int detailsEndIndex;
	static int nextIndexCheck;
   // static int[] inputSamples;
    static Sequence sequence;
	static int averageAmplitude;
	static int[] trimedSequence;
	static List<Sequence> sequences;
	static SequenceFilter amplitudeSequenceFilter;
	static SequenceFilter amplitudefrequencySequenceFilter;
	
	private static void initMainVariables(int id) {
		
		sequences = new ArrayList<Sequence>();
		trimedSequence = null;
	    startIndex = -1;
	    endIndex = -1;
		detailsStartIndex = -1;
		detailsEndIndex = -1;
		averageAmplitude = AudioAnalysisThread.startedVoiceCheck.get(id).getAverageAmplitude();
	}
	
	public static void buildSequenceChecks(int id) {

		amplitudeSequenceFilter = new SequenceFilter((int) (AppSetup.idle_amplitude_volume*1.5), 
			(int) (AppSetup.idle_amplitude_volume *1),0,0,0,0,100,100
			,AudioAnalysisThread.startedVoiceCheck.get(id).getAudioFormat());
	
		amplitudefrequencySequenceFilter = new SequenceFilter((int) (AppSetup.idle_amplitude_volume *1),
			(int) (AppSetup.idle_amplitude_volume *0.8), AppSetup.START_FREQUENCY_LOWER_LIMIT,
	        AppSetup.START_FREQUENCY_UPPER_LIMIT, AppSetup.END_FREQUENCY_LOWER_LIMIT,
	        AppSetup.END_FREQUENCY_UPPER_LIMIT,20,50
	        ,AudioAnalysisThread.startedVoiceCheck.get(id).getAudioFormat());
	}
	
	public static void mainAudioTrim(int id) {
		 
		Debug.startTime = System.currentTimeMillis();	
		
		if(!AudioAnalysisThread.startedVoiceCheck.get(id).getNextStage().equals("trim"))
			return;
		
		initMainVariables(id);
		
		buildSequenceChecks(id);
		
		amplitudeTrim(id);
					
		multiTrim(id);
	}
	
	private static void amplitudeTrim(int id) {
		
		if(AppSetup.preTrim && AppSetup.amplitudeRefinary) {

			trim = new AmplitudeTrimImpl (id, AudioAnalysisThread.startedVoiceCheck.get(id).getIntStream(),
					amplitudeSequenceFilter);
			
			AudioAnalysisThread.startedVoiceCheck.get(id).setByteStream(null);
			AudioAnalysisThread.startedVoiceCheck.get(id).setIntStream(trimedSequence);
			
			if(AppSetup.noiseFilter&&trimedSequence != null )
				AudioAnalysisThread.startedVoiceCheck.get(id).setIntStream(NoiseFilter2.noiseReduction(trimedSequence));
			
			AudioAnalysisThread.startedVoiceCheck.get(id).setNextStage("analysis");
			
			if(trimedSequence == null) {
				
				AudioAnalysisThread.startedVoiceCheck.get(id).setBuild(false);
				AudioAnalysisThread.startedVoiceCheck.get(id).setNextStage("save");
				Debug.debug(1,"AudioTrimSelector AmplitudeTrimImpl set false");
			}
			
			Debug.debug(1, "AudioTrimSelector AmplitudeTrimImplementation cycle length: "
					+(System.currentTimeMillis()-Debug.startTime));
			
			return;
		}
	}
	
	private static void multiTrim(int id) {
		
		if(AppSetup.preTrim && AppSetup.multiTrim
				&& (AppSetup.frequencyRefinary  
				|| AppSetup.voiceRecognitionAmplitudeRefinary 
				|| AppSetup.voiceRecognitionFrequencyRefinary)) {

				trim = new MultiTrimImpl(id, AudioAnalysisThread.startedVoiceCheck.get(id).getIntStream()
						,AudioAnalysisThread.startedVoiceCheck.get(id).getWaveStream()
						,amplitudefrequencySequenceFilter);
				
				AudioAnalysisThread.startedVoiceCheck.get(id).setByteStream(null);

				if(!AppSetup.voiceRecognition && trimedSequence != null)	{		
					
					AudioAnalysisThread.startedVoiceCheck.get(id).setIntStream(trimedSequence);
					AudioAnalysisThread.startedVoiceCheck.get(id).setNextStage("analysis");
				}
				
				if(AppSetup.voiceRecognition && trimedSequence != null)	
					AudioAnalysisThread.startedVoiceCheck.get(id).setNextStage("voiceCheck");
				
				
				if(trimedSequence == null) {
					
					AudioAnalysisThread.startedVoiceCheck.get(id).setBuild(false);
					AudioAnalysisThread.startedVoiceCheck.get(id).setNextStage("save");
					Debug.debug(1,"AudioTrimSelector VoiceReconition set false");
				}
				
				Debug.debug(1, "AudioTrimSelector DualTrimImplementation cycle length: "
						+(System.currentTimeMillis()-Debug.startTime +" mSec"));
				return;
			}
	}
	
	public static void setZeroMultiTrimVariables() {
		 
		 counter = 0;
		 a_sum = 0;
		 f_sum = 0;
		 a_avg = 0;
		 f_avg = 0;
		 freqBuffDive = 0;
	}
	 
	public static void setSequencePoints() {
		 
		 if(AudioTrimSelector.sequences.size() == 0) {
			 Debug.debug(1,"BuildTrimedSequence setSequencePoints Trim was Failed! sequences == null.");
			 return ;
		 }	
		 
		 startIndex = AudioTrimSelector.sequences.get(0).getStartIndex();
		 
		 endIndex = AudioTrimSelector.sequences.get(
				 AudioTrimSelector.sequences.size()-1).getEndIndex();
		 
		 detailsStartIndex = AudioTrimSelector.sequences.get(0).getDetailsStartIndex();
		 
		 detailsEndIndex = AudioTrimSelector.sequences.get(
				 AudioTrimSelector.sequences.size()-1).getDetailsEndIndex();
		 
		 Debug.debug(3,"BuildTrimedSequence setSequencePoints startIndex: "+startIndex +", endIndex: "
			+ endIndex + ", detailsStart: "+ detailsStartIndex + ", detailsEnd: "+ detailsEndIndex);
	 }
}
