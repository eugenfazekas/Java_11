package com.audio3.audioGramInitializer.trim;

import com.audio0.main.AppSetup;
import com.audio3.audioGramInitializer.AudioAnalysisThread;
import com.audio8.util.Debug;

public class AudioPreTrimSelector {
	
	static int averageAmplitude;
	static int[] borderedIntSequence;
	private static AudioPreTrimForAnalysis trim;
	private static SequenceCheck start_A;
	private static SequenceCheck end_A ;
	private static  SequenceCheck start_F;
	private static  SequenceCheck end_F;
	private static  SequenceCheck start_V;
	private static  SequenceCheck end_V;
	public static  SequenceCheck [] baseAmplitudeSequenceCheck;
	private static   SequenceCheck [] basefreqencySequenceCheck;
	private static   SequenceCheck [] baseVoiceSequenceCheck;
	
	public static void buildSequenceChecks() {
	
		start_A = new SequenceCheck(0,0,0,AppSetup.START_AMPLITUDE_SEQUENCE_LENGTH_CHECK,AppSetup.idle_amplitude_volume,AppSetup.START_WAVE_UPPER_LIMIT_A);
		 
		end_A = new SequenceCheck(1,0,0,AppSetup.END_AMPLITUDE_SEQUENCE_LENGTH_CHECK, AppSetup.END_WAVE_LOWER_LIMIT_A, AppSetup.idle_amplitude_volume/4);
		 	
		start_F = new SequenceCheck(0,AppSetup.START_FREQUENCY_LOWER_LIMIT, AppSetup.START_FREQUENCY_UPPER_LIMIT,
				 AppSetup.START_FREQUENCY_SEQUENCE_LENGTH_CHECK,AppSetup.idle_amplitude_volume, AppSetup.START_WAVE_UPPER_LIMIT_F);
		 
		end_F = new SequenceCheck(1,AppSetup.END_FREQUENCY_LOWER_LIMIT, AppSetup.END_FREQUENCY_UPPER_LIMIT,
				 AppSetup.END_FREQUENCY_AMPLITUDE_SEQUENCE_LENGTH_CHECK,AppSetup.END_WAVE_LOWER_LIMIT_F, AppSetup.idle_amplitude_volume);
		 	
		start_V = new SequenceCheck(0,AppSetup.START_FREQUENCY_LOWER_LIMIT, AppSetup.START_FREQUENCY_UPPER_LIMIT,
				 AppSetup.START_FREQUENCY_SEQUENCE_LENGTH_CHECK,AppSetup.idle_amplitude_volume, AppSetup.START_WAVE_UPPER_LIMIT_F);
		 
		end_V = new SequenceCheck(1,AppSetup.END_FREQUENCY_LOWER_LIMIT, AppSetup.END_FREQUENCY_UPPER_LIMIT,
				 AppSetup.END_FREQUENCY_AMPLITUDE_SEQUENCE_LENGTH_CHECK/2,AppSetup.END_WAVE_LOWER_LIMIT_F, AppSetup.idle_amplitude_volume);
		
		baseAmplitudeSequenceCheck = new SequenceCheck[] {start_A,end_A};
		basefreqencySequenceCheck = new SequenceCheck[]  {start_F,end_F};
		baseVoiceSequenceCheck = new SequenceCheck[]     {start_V,end_V};
		averageAmplitude = 0;
		borderedIntSequence = null;
	}

	public static void mainAudioTrim(int[] inputSamples, int[] inputStreamDetails) {
		 
		Debug.startTime = System.currentTimeMillis();
		buildSequenceChecks();
		
		if(AppSetup.preTrim && AppSetup.amplitudeRefinary) {
			trim = new AmplitudeTrimImplementation(inputSamples,baseAmplitudeSequenceCheck);
			AudioAnalysisThread.byteStream = null;
			AudioAnalysisThread.intStream = trim.getTrimedSequence();
			AudioAnalysisThread.averageAmplitude = averageAmplitude;

			Debug.debug(1, "AudioPreTrimForAnalysis AmplitudeTrimImplementation cycle length: "+(System.currentTimeMillis()-Debug.startTime));
			return;
		}
		
		if(AppSetup.preTrim && AppSetup.frequencyRefinary) {
		    trim = new AmlitudeFrequencyTrimImplementation(inputSamples,inputStreamDetails,basefreqencySequenceCheck);
 			AudioAnalysisThread.byteStream = null;
			AudioAnalysisThread.intStream = trim.getTrimedSequence();
			AudioAnalysisThread.averageAmplitude = averageAmplitude;

			Debug.debug(1, "AudioPreTrimForAnalysis AmlitudeFrequencyTrimImplementation cycle length: "+(System.currentTimeMillis()-Debug.startTime));
			return;
		}
		
		if(AppSetup.preTrim && (AppSetup.voiceRecognitionFrequencyRefinary || AppSetup.voiceRecognitionAmplitudeRefinary)) {
			trim = new AudioStreamDetailsTrimImplementation(inputStreamDetails,baseVoiceSequenceCheck);
			AudioAnalysisThread.byteStream = null;
			AudioAnalysisThread.intStream = null;
			AudioAnalysisThread.audioStreamDetails = trim.getTrimedSequence();
			System.out.println("trim.getTrimedSequence() "+(trim.getTrimedSequence() == null) +", audioStreamDetails null? "+ (AudioAnalysisThread.audioStreamDetails == null));
			AudioAnalysisThread.averageAmplitude = averageAmplitude;
			Debug.debug(1, "AudioPreTrimForAnalysis AudioStreamDetailsTrimImplementation cycle length: "+(System.currentTimeMillis()-Debug.startTime));
			return;
		} else {
			Debug.debug(1, "AudioPreTrimForAnalysis No trim Selection possible!");
		}
	}
}
