package com.audio4.audioGramInitializer.trim;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import com.audio0.main.AppSetup;
import com.audio2.recorder.AudioListener;
import com.audio4.audioGramInitializer.mainInit.AudioAnalysisThread;
import com.audio4.audioGramInitializer.trim.sequence.Sequence;
import com.audio4.audioGramInitializer.trim.sequence.SequenceFilter;
import com.audio8.util.Debug;

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
    static Sequence sequence;
	static int averageAmplitude;
	static int[] trimedSequence;
	static List<Sequence> sequences;
	static SequenceFilter amplitudeSequenceFilter;
	static SequenceFilter amplitudefrequencySequenceFilter;
	public static AtomicBoolean building = new AtomicBoolean(false);
	
	private static int debug_level_INFO = 5;
	
	private static void initMainVariables(int id) {
		
		sequences = new ArrayList<Sequence>();
		trimedSequence = null;
	    startIndex = -1;
	    endIndex = -1;
		detailsStartIndex = -1;
		detailsEndIndex = -1;
		averageAmplitude = AudioAnalysisThread.startedVoiceCheck.get(id).getAverageAmplitude();
		Debug.debug(debug_level_INFO, "AudioTrimSelector initMainVariables initiated!");
	}
	
	private static void buildSequenceChecks(int id) {

		amplitudeSequenceFilter = new SequenceFilter((int) (AppSetup.IDLE_AMPLITUDE_VOLUME * 1.2), 
			(int) (AppSetup.IDLE_AMPLITUDE_VOLUME*0.8),0,0,0,0,AppSetup.START_LENGTH_CHECK,
			AppSetup.END_LENGTH_CHECK,AudioAnalysisThread.startedVoiceCheck.get(id).getAudioFormat());	
	}
	
	public static void mainAudioTrim(int id) {
		 

		if(!AudioAnalysisThread.startedVoiceCheck.get(id).getNextStage().equals("trim")) {
			
			if(AppSetup.amplitudeTrim && AppSetup.frequencyTrim)
				Debug.debug(debug_level_INFO, "AudioTrimSelector mainAudioTrim, "
						+ " Unable to select Both Amplitude And Frequency Trim Enabled");
			return;
		}
		
		building.set(true);
		Debug.startTime = System.currentTimeMillis();	
		
		Debug.debug(debug_level_INFO, "AudioTrimSelector mainAudioTrim started id: "
				+AudioAnalysisThread.startedVoiceCheck.get(id).getId()
				+ ", nextStage: "+AudioAnalysisThread.startedVoiceCheck.get(id).getNextStage());
			
		initMainVariables(id);
		
		buildSequenceChecks(id);
		
		amplitudeTrim(id);
					
		frequencyTrim(id);
		
		building.set(false);
		
		Debug.debug(debug_level_INFO, "AudioTrimSelector mainAudioTrim Building: "+building.get()+ ", Build Time: "
		+(System.currentTimeMillis()-Debug.startTime) + ", nextStage: "
		+AudioAnalysisThread.startedVoiceCheck.get(id).getNextStage());
	}
	
	private static void amplitudeTrim(int id) {
		
		if(AppSetup.preTrim && AppSetup.amplitudeTrim && !AppSetup.frequencyTrim ) {

			trim = new AmplitudeTrimImpl (id, AudioAnalysisThread.startedVoiceCheck.get(id).getIntStream());
			
			if(trimedSequence != null) {
				
				AudioAnalysisThread.startedVoiceCheck.get(id).setByteStream(null);
				AudioAnalysisThread.startedVoiceCheck.get(id).setIntStream(trimedSequence);			
				AudioAnalysisThread.startedVoiceCheck.get(id).setNextStage();
				Debug.debug(debug_level_INFO,"AudioTrimSelector amplitudeTrim trim completed!");
			}
			
			if(trimedSequence == null && AppSetup.continueWithNoTrim) {
				
				AudioAnalysisThread.startedVoiceCheck.get(id).setNextStage();
				Debug.debug(debug_level_INFO,"AudioTrimSelector amplitudeTrim failed Continue without trim!");
			}
			
			if(trimedSequence == null && !AppSetup.continueWithNoTrim) {
				
				AudioAnalysisThread.startedVoiceCheck.get(id).setManualNextStage("end");
				AudioAnalysisThread.startedVoiceCheck.get(id).setBuild(false);
				Debug.debug(debug_level_INFO,"AudioTrimSelector amplitudeTrim failed Continue without trim!");			
			}
		}
	}
	
	private static void frequencyTrim(int id) {
		
		if(AppSetup.preTrim && AppSetup.frequencyTrim && !AppSetup.amplitudeTrim) {

			trim = new FrequencyTrimImpl(id, AudioAnalysisThread.startedVoiceCheck.get(id).getIntStream());
			
			AudioAnalysisThread.startedVoiceCheck.get(id).setByteStream(null);

			if(!AppSetup.voiceRecognition && trimedSequence != null)	{		
				
				AudioAnalysisThread.startedVoiceCheck.get(id).setByteStream(null);
				AudioAnalysisThread.startedVoiceCheck.get(id).setIntStream(trimedSequence);			
				AudioAnalysisThread.startedVoiceCheck.get(id).setNextStage();
				Debug.debug(debug_level_INFO,"AudioTrimSelector frequencyTrim trim completed!");
			}
											
			if(trimedSequence == null && AppSetup.continueWithNoTrim) {
				
				AudioAnalysisThread.startedVoiceCheck.get(id).setNextStage();
				Debug.debug(debug_level_INFO,"AudioTrimSelector frequencyTrim set false");
			}
			
			if(trimedSequence == null && !AppSetup.continueWithNoTrim) {
				
				AudioAnalysisThread.startedVoiceCheck.get(id).setManualNextStage("end");
				AudioAnalysisThread.startedVoiceCheck.get(id).setBuild(false);
				Debug.debug(debug_level_INFO,"AudioTrimSelector frequencyTrim failed Continue without trim!");			
			}
		}
	}
	
	static void setZeroMultiTrimVariables() {
		 
		 counter = 0;
		 a_sum = 0;
		 f_sum = 0;
		 a_avg = 0;
		 f_avg = 0;
		 freqBuffDive = 0;
	}
	 
	static void setSequencePoints() {
		 
		 if(AudioTrimSelector.sequences.size() == 0) {
			 Debug.debug(debug_level_INFO,"AudioTrimSelector setSequencePoints Trim was Failed! sequences == null.");
			 return ;
		 }	
		 
		 startIndex = AudioTrimSelector.sequences.get(0).getStartIndex();
		 
		 endIndex = AudioTrimSelector.sequences.get(
				 AudioTrimSelector.sequences.size()-1).getEndIndex();
		 
		 Debug.debug(debug_level_INFO,"AudioTrimSelector setSequencePoints startIndex: "+startIndex +", endIndex: "
			+ endIndex + ", detailsStart: "+ detailsStartIndex + ", detailsEnd: "+ detailsEndIndex);
	}
	
	static int getLengthCorrection(boolean stream, int mSec, boolean negative) {
		
		int mSecLength;
		int streamBufferLength;
		
		mSecLength = AudioGramTrimUtil.getBuffersLengthByMilisec(
				(int)AudioListener.format.getSampleRate()
				,mSec);
		
		streamBufferLength = AudioGramTrimUtil.getBuffersLengthByMilisec(
				(int)AudioListener.format.getSampleRate()
				,AppSetup.AUDIO_BUFFER_MILISEC_LENGTH);
		
		if(stream)
			mSecLength = (mSecLength / streamBufferLength)*3;
		
		if(negative)
			mSecLength *= -1;
		
		Debug.debug(debug_level_INFO,"AudioTrimSelector getLengthCorrection mSec: "+mSec + ", stream: "+stream 
			+ ", mSecLength: "+mSecLength);
		
		return mSecLength;
	}
	
}
