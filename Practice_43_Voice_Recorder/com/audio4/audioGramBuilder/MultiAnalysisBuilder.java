package com.audio4.audioGramBuilder;

import java.util.Arrays;

import com.audio0.main.AppSetup;
import com.audio3.audioGramInitializer.AudioAnalysisInitializer;
import com.audio3.audioGramInitializer.AudioAnalysisPhaseBuilder;
import com.audio3.audioGramInitializer.AudioAnalysisThread;
import com.audio8.util.AudioBuilderUtil;
import com.audio8.util.Debug;

public class MultiAnalysisBuilder {
	
	public static int i;
	private static int lastSample;

	private static void buildMultiWaveMap() {
		
		for(i = 0; i < AudioAnalysisInitializer.audioSamples.length; i++) {
			
				AudioAnalysisPhaseBuilder.runTasks(AudioAnalysisPhaseBuilder.multiAnalysisPhase0);					
						
				if(AudioAnalysisInitializer.audioSamples[i]  > 0 && lastSample > 0) { 
	                AudioAnalysisPhaseBuilder.runTasks(AudioAnalysisPhaseBuilder.multiAnalysisPhase1);                       
				}
				
				if(AudioAnalysisInitializer.audioSamples[i]  <= 0) {
					AudioAnalysisPhaseBuilder.runTasks(AudioAnalysisPhaseBuilder.multiAnalysisPhase2);
				}
				
				if(AudioAnalysisInitializer.audioSamples[i]  > 0 && lastSample <0 ) {
					AudioAnalysisPhaseBuilder.runTasks(AudioAnalysisPhaseBuilder.multiAnalysisPhase3);
				}
				increaseMilisecCounter();
		}					
	}
		 		
		 public static void mainAnalysisBuilder() {
	
			if(!AudioAnalysisInitializer.build || !AppSetup.multiAnalysis) return;
			resetMainDetails();
			
			new AudioAnalysisPhaseBuilder();
			Debug.startTime = System.currentTimeMillis();
			buildMultiWaveMap();
			
			AmplitudeDetails.amplitudeWaveMap = AudioBuilderUtil.filterIntItems(AmplitudeDetails.amplitudeWaveMap,1);
			Debug.debug(2,"MultiAnalysisBuilder mainbuilder filtered amplitudeWaveMap.length: " + AmplitudeDetails.amplitudeWaveMap.length + " Array: "+ Arrays.toString(AmplitudeDetails.amplitudeWaveMap));	
			
			FrequencyDetails.frequencyWaveMap = AudioBuilderUtil.filterIntItems(FrequencyDetails.frequencyWaveMap,0);
			Debug.debug(2,"MultiAnalysisBuilder mainbuilder filtered frequencyWaveMap.length: " + FrequencyDetails.frequencyWaveMap.length + " Array: "+ Arrays.toString(FrequencyDetails.frequencyWaveMap));
			FrequencyDetails.buildMappedArray();
			
			Debug.debug(2,"MultiAnalysisBuilder mainbuilder unfiltered spektrogramMap.length: " + MySpketrogramDetails.spektrogramMap.length + " Array: "+ Arrays.toString(MySpketrogramDetails.spektrogramMap));
			MySpketrogramDetails.spektrogramMap =AudioBuilderUtil.filterIntItems(MySpketrogramDetails.spektrogramMap,0);
			Debug.debug(2,"MultiAnalysisBuilder mainbuilder filtered spektrogramMap.length: " + MySpketrogramDetails.spektrogramMap.length + " Array: "+ Arrays.toString(MySpketrogramDetails.spektrogramMap));

			SpektroGramBuilder.mainBuilder();
			
			MiscellaneousData.buildSequenceArray = AudioBuilderUtil.filterEmptyStrings(MiscellaneousData.buildSequenceArray);
			Debug.debug(3,"MultiAnalysisBuilder mainBuilder Elapsed Time: "+(System.currentTimeMillis() - Debug.startTime));
		 }
		 
		 public static void smallAnalysisBuilder() {
			 
			if(!AudioAnalysisInitializer.build || !AppSetup.multiAnalysis) return;

			AmplitudeDetails.initAmplitudeWaveMapDetails();
			FrequencyDetails.initFrequencyWaveMapDetails();	
			MySpketrogramDetails.iniMySpektroGramDetails();
			AmplitudeDetails.amplitudeWaveMap = new int[AudioAnalysisThread.audioStreamDetails.length/2]; 
			FrequencyDetails.frequencyWaveMap = new int[AudioAnalysisThread.audioStreamDetails.length/2];
			MySpketrogramDetails.spektrogramMap = new int[AudioAnalysisThread.audioStreamDetails.length];

			for(int i = 0; i < AudioAnalysisThread.audioStreamDetails.length; i=i+2) {			 
				 AmplitudeDetails.addToAmplitudeWaveMap( AudioAnalysisThread.audioStreamDetails[i],1);
				 FrequencyDetails.addToFrequencyWaveMap( AudioAnalysisThread.audioStreamDetails[i+1],1);
				 MySpketrogramDetails.addToMySpektroGramMap(AudioAnalysisThread.audioStreamDetails[i], AudioAnalysisThread.audioStreamDetails[i+1],1);
			 }
			FrequencyDetails.buildMappedArray();
		 }
		 
		private static void increaseMilisecCounter() {
			
			AmplitudeDetails.aavgMilisecCounter++;
			FrequencyDetails.favgMilisecCounter++;
			MySpketrogramDetails.savgMilisecCounter++;
			lastSample = AudioAnalysisInitializer.audioSamples[i] ;
		}
		 
		 public static void phase_0_Amplitude() {  
				if(AmplitudeDetails.aavgMilisecCounter == AmplitudeDetails.A_AVG_MILISEC_LENGTH ) {
					   AmplitudeDetails.addToAmplitudeWaveMap(AmplitudeDetails.amplitudeTotalBuffer, AmplitudeDetails.amplitudeBufferCounter);
					   AmplitudeDetails.aavgMilisecCounter = 0;
				}
		 }
		 public static void phase_0_Frequency() { 
				if(FrequencyDetails.favgMilisecCounter == FrequencyDetails.F_AVG_MILISEC_LENGTH ) {
					   FrequencyDetails.addToFrequencyWaveMap(FrequencyDetails.frequencyTotalBuffer, FrequencyDetails.frequencyBufferCounter);
					   FrequencyDetails.favgMilisecCounter = 0;
				}				
		 }
		 
		 public static void phase_0_SpektroGram() { 
				if(MySpketrogramDetails.savgMilisecCounter == MySpketrogramDetails.S_AVG_MILISEC_LENGTH ) {				   
					   MySpketrogramDetails.addToMySpektroGramMap(MySpketrogramDetails.amplitudeTotalBuffer, MySpketrogramDetails.frequencyTotalBuffer,MySpketrogramDetails.bufferCounter);
					   MySpketrogramDetails.savgMilisecCounter = 0;
				}				
		 }
		 
		 public static void phase_1_Amplitude() { AmplitudeDetails.posi_ampl_avgs += AudioAnalysisInitializer.audioSamples[i] ;}
		 public static void phase_1_Frequency() { FrequencyDetails.positive++;}
		 public static void phase_1_RawAudioData() { MiscellaneousData.sb.append(AudioAnalysisInitializer.audioSamples[i] +", ");}
		 
		 public static void phase_2_Amplitude() { AmplitudeDetails.nega_ampl_avgs += Math.abs(AudioAnalysisInitializer.audioSamples[i] );}
		 public static void phase_2_Frequency() { FrequencyDetails.negative++;}
		 public static void phase_2_RawAudioData() { MiscellaneousData.sb.append(AudioAnalysisInitializer.audioSamples[i] +", ");}
		 
		 public static void phase_3_Amplitude() {
			 AmplitudeDetails.amplitude = (((AmplitudeDetails.posi_ampl_avgs+ AmplitudeDetails.nega_ampl_avgs) / (FrequencyDetails.positive + FrequencyDetails.negative))) ;
			 AmplitudeDetails.addToAmplitudeBuffer((int) AmplitudeDetails.amplitude);
		 }
		 public static void phase_3_Frequency() { 
			 FrequencyDetails.frequency = FrequencyDetails.getFrequency(AudioAnalysisInitializer.sampleRate, FrequencyDetails.positive+FrequencyDetails.negative);
			 FrequencyDetails.addToFrequnecyBuffer(FrequencyDetails.frequency);
		 }
		 
		 public static void phase_3_RawAudioData() {
			 MiscellaneousData.rawAudioData[MiscellaneousData.dataCounter]="\nFrequency: "+ (FrequencyDetails.frequency)+", Amplitude pozitiv: "+ (AmplitudeDetails.posi_ampl_avgs/ (FrequencyDetails.positive = FrequencyDetails.positive >0 
						? FrequencyDetails.positive : 1 ))+", Amplitude negativ: "+(AmplitudeDetails.nega_ampl_avgs/(FrequencyDetails.negative = FrequencyDetails.negative > 0 
						? FrequencyDetails.negative : 1 ))+", .-<<||||   "+ MiscellaneousData.sb.toString() + ";\n";
			 MiscellaneousData.sb = new StringBuilder();
			 MiscellaneousData.sb.append(AudioAnalysisInitializer.audioSamples[i] +", ");
		 }
		 
		 public static void phase_3_SpektroGram() { 
			 MySpketrogramDetails.addToSpektroGramBuffer(AmplitudeDetails.amplitude, FrequencyDetails.frequency); 
		 }
		 
		 public static void phase_3_BuildSequenceArray() {
			 MiscellaneousData.getWaveDetails(AmplitudeDetails.posi_ampl_avgs,FrequencyDetails.positive,AmplitudeDetails.nega_ampl_avgs,FrequencyDetails.negative);
			 MiscellaneousData.buildSequenceArray[MiscellaneousData.dataCounter++] =  (String.valueOf(FrequencyDetails.frequency)+":" +
             MiscellaneousData.waveDetails[0]+":" +MiscellaneousData.waveDetails[1]+":" +MiscellaneousData.waveDetails[2]+":"+MiscellaneousData.waveDetails[3]+",\n");
		 }
		 
		 public static void phase_3_ResetCounters() {
			 AmplitudeDetails.posi_ampl_avgs = AudioAnalysisInitializer.audioSamples[i] ;
			 AmplitudeDetails.nega_ampl_avgs = 0;
			 FrequencyDetails.positive = 1;
		     FrequencyDetails.negative = 0; 
		 }
		 
		 public static void resetMainDetails() {

			AmplitudeDetails.initAmplitudeWaveMapDetails();
			FrequencyDetails.initFrequencyWaveMapDetails();	
			MySpketrogramDetails.iniMySpektroGramDetails();
			MiscellaneousData.initMiscellaneousDataDetails();
		 }
}
