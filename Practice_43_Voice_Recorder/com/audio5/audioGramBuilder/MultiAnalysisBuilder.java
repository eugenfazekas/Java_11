package com.audio5.audioGramBuilder;

import java.util.Arrays;

import com.audio0.main.AppSetup;
import com.audio1.logical.EntryPointMethods;
import com.audio4.audioGramInitializer.AudioAnalysisPhaseBuilder;
import com.audio4.audioGramInitializer.AudioAnalysisThread;
import com.audio8.util.AudioGramUtil;
import com.audio8.util.Debug;
import com.audio8.util.IntArrayUtil;
import com.audio8.util.StringArrayUtil;

public class MultiAnalysisBuilder {
	
	public static int i;
	private static int lastSample;
	private static int id;

	private static void buildMultiWaveMap(int id) {
		
		for(i = 0; i < AudioAnalysisThread.startedVoiceCheck.get(id).getIntStream().length; i++) {
			
			AudioAnalysisPhaseBuilder.runTasks(AudioAnalysisPhaseBuilder.multiAnalysisPhase0);					
					
			if(AudioAnalysisThread.startedVoiceCheck.get(id).getIntStream()[i]  > 0 && lastSample > 0) { 
				
                AudioAnalysisPhaseBuilder.runTasks(AudioAnalysisPhaseBuilder.multiAnalysisPhase1);                       
			}
			
			if(AudioAnalysisThread.startedVoiceCheck.get(id).getIntStream()[i]  <= 0) {

				AudioAnalysisPhaseBuilder.runTasks(AudioAnalysisPhaseBuilder.multiAnalysisPhase2);
			}
			
			if((AudioAnalysisThread.startedVoiceCheck.get(id).getIntStream()[i]  > 0 && lastSample <0 && AppSetup.amplitudeGram) 
				|| (AppSetup.multiGram && AGBCVariables.newFrequency == true )) {
				
				AudioAnalysisPhaseBuilder.runTasks(AudioAnalysisPhaseBuilder.multiAnalysisPhase3);
			}
			
			increaseMilisecCounter();
		}	
		
		AudioAnalysisPhaseBuilder.runTasks(AudioAnalysisPhaseBuilder.multiAnalysisPhase4);
	}
		 		
	public static void mainAnalysisBuilder(int Id) {
		
		id = Id;

		if(!AudioAnalysisThread.startedVoiceCheck.get(id).getBuild() || 
								
			!AudioAnalysisThread.startedVoiceCheck.get(id).getNextStage().equals("analysis") 
			  || !AppSetup.multiAnalysis) 
				return;
		
		resetMainDetails();
				
		new AudioAnalysisPhaseBuilder();
			
		Debug.startTime = System.currentTimeMillis();
		
		buildMultiWaveMap(id);

		if(EntryPointMethods.getSvitch().equals("saveNamedRecords"))
			AudioAnalysisThread.startedVoiceCheck.get(id).setNextStage("save");
		
		if(EntryPointMethods.getSvitch().equals("voiceRecognitionDebug"))
			AudioAnalysisThread.startedVoiceCheck.get(id).setNextStage("voiceCheck");
		
		Debug.debug(2,"MultiAnalysisBuilder mainbuilder unfiltered spektrogramMap.length: " 
			+ MySpketrogramDetails.spektrogramMap.length + " Array: "
			+ Arrays.toString(MySpketrogramDetails.spektrogramMap));

		Debug.debug(3,"MultiAnalysisBuilder mainBuilder Elapsed Time: "
			+(System.currentTimeMillis() - Debug.startTime));		
	}
		 
	public static void smallAnalysisBuilder(int id) {

		if(!AudioAnalysisThread.startedVoiceCheck.get(id).getBuild() || !AppSetup.multiAnalysis
			|| !AudioAnalysisThread.startedVoiceCheck.get(id).getNextStage().equals("analysis")
			|| !EntryPointMethods.getSvitch().equals("voiceRecognition")) 
					return;
	
		if(AudioAnalysisThread.startedVoiceCheck.get(id).getMySpektrogramMap() == null) {
			
			AGBCVariables.resetVariables(id);
			MySpketrogramDetails.spektrogramMapCounter = 0;
			
			AGBCVariables.amplitudeWaveMap=new int[AudioAnalysisThread.startedVoiceCheck
			                      					.get(id).getWaveStream().length/3]; 
			AGBCVariables.frequencyWaveMap=new int[AudioAnalysisThread.startedVoiceCheck
				                      					.get(id).getWaveStream().length/3];
			MySpketrogramDetails.spektrogramMap=new int[AudioAnalysisThread.startedVoiceCheck
				                      					.get(id).getWaveStream().length/3*2];
			
			for(int i = 0; i < AudioAnalysisThread.startedVoiceCheck.get(id).getWaveStream().length; i=i+3) {	
				
				Debug.debug(5, "MultiAnalysisBuilder smallAnalysisBuilder id "+ id+", i: "+ i +", AudioAnalysisThread.audioStreamDetails[i]: "
						+ AudioAnalysisThread.startedVoiceCheck.get(id).getWaveStream()[i]
						+ ",  AudioAnalysisThread.audioStreamDetails[i+1]: "+AudioAnalysisThread.startedVoiceCheck
	  					.get(id).getWaveStream()[i+1] +", AudioAnalysisThread.audioStreamDetails[i+2]: "
						+ AudioAnalysisThread.startedVoiceCheck.get(id).getWaveStream()[i+2]);
				
				AmplitudeDetails.addToAmplitudeWaveMapSimple(AudioAnalysisThread.startedVoiceCheck
	  					.get(id).getWaveStream()[i]);
				
				FrequencyDetails.addToFrequencyWaveMap(AudioAnalysisThread.startedVoiceCheck
	  					.get(id).getWaveStream()[i+1],1);
				
				MySpketrogramDetails.addToSpektroGramMap(AudioAnalysisThread.startedVoiceCheck
	  					.get(id).getWaveStream()[i]
				,AudioAnalysisThread.startedVoiceCheck.get(id).getWaveStream()[i+1]);
			}
			
			AudioAnalysisThread.startedVoiceCheck.get(id).setAmplitudeWaveMap(AGBCVariables.amplitudeWaveMap);
			
			AudioAnalysisThread.startedVoiceCheck.get(id).setFrequencyWaveMap(AGBCVariables.frequencyWaveMap);
			
			AudioAnalysisThread.startedVoiceCheck.get(id).setMySpektrogramMap(MySpketrogramDetails.spektrogramMap);
		}
		
		if(AppSetup.voiceRecognitionPointsData)
			phase_4_BuildVoiceRecognitionPointsArray();
		
		if(AppSetup.voiceRecognitionSlopesData && AudioAnalysisThread.startedVoiceCheck.get(id)
				.getVoiceReconitionCheckPointsArray() == null)			
			phase_4_BuildVoiceRecognitionSlopesArray();
		
		AudioAnalysisThread.startedVoiceCheck.get(id).setNextStage("save");

		Debug.debug(2,"MultiAnalysisBuilder smallAnalysisBuilder id "+ id+ ", Points array length "
		+ AudioAnalysisThread.startedVoiceCheck.get(id).getVoiceRecognitionPointsArray().length);
	}
	
	private static void increaseMilisecCounter() {
		
		AGBCVariables.avgMilisecCounter++;
		lastSample = AudioAnalysisThread.startedVoiceCheck.get(id).getIntStream()[i] ;
	}
		 
	public static void phase_0_Amplitude() {  
		 
		if(AGBCVariables.avgMilisecCounter >= AGBCVariables.AVG_MILISEC_LENGTH
			&& AudioAnalysisThread.startedVoiceCheck.get(id).getIntStream()[i] > 0 && lastSample < 0 ) {
			
			AmplitudeDetails.addToAmplitudeWaveMap(AGBCVariables.amplitudeTotalBuffer
			,AGBCVariables.amplitudeBufferCounter,id);
		}
	}
	 
	public static void phase_0_Frequency() { 
		 
		if(AGBCVariables.avgMilisecCounter >= AGBCVariables.AVG_MILISEC_LENGTH
			&& AudioAnalysisThread.startedVoiceCheck.get(id).getIntStream()[i] > 0 && lastSample < 0 ) {

			FrequencyDetails.addToFrequencyWaveMap(AGBCVariables.frequencyTotalBuffer
				,AGBCVariables.frequencyBufferDiveder);
			
		    AGBCVariables.avgMilisecCounter = 0;
		}				
	}
	 
	public static void phase_1_Amplitude() {
		
		AGBCVariables.posi_ampl_avgs  += AudioAnalysisThread.startedVoiceCheck.get(id).getIntStream()[i]; 
	}
	
	public static void phase_1_Frequency() { 
		
		AGBCVariables.positiveA++;
	}
	
	public static void phase_1_MultiPositive() { 
		
		MultiDetails.buildFrequencysPositive(AudioAnalysisThread.startedVoiceCheck.get(id).getIntStream()[i],i,
				(int)AudioAnalysisThread.startedVoiceCheck.get(id).getAudioFormat().getSampleRate());
	}
	
	public static void phase_1_RawAudioData() { 
		
		MiscellaneousData.sb.append(AudioAnalysisThread.startedVoiceCheck.get(id).getIntStream()[i] +", ");
	}
	 
	public static void phase_2_Amplitude() {
		
		AGBCVariables.nega_ampl_avgs += Math.abs(AudioAnalysisThread.startedVoiceCheck.get(id).getIntStream()[i]);
	}
	
	public static void phase_2_Frequency() { 
		
		AGBCVariables.negativeA++;
	}
	
	public static void phase_2_MultiNegative() { 
		
		MultiDetails.buildFrequencysNegative(AudioAnalysisThread.startedVoiceCheck.get(id).getIntStream()[i]);
	}
	
	public static void phase_2_RawAudioData() {
		
		MiscellaneousData.sb.append(AudioAnalysisThread.startedVoiceCheck.get(id).getIntStream()[i] +", ");
	}
	 
	public static void phase_3_Amplitude() {
		
		AGBCVariables.amplitude = (((AGBCVariables.posi_ampl_avgs
		+AGBCVariables.nega_ampl_avgs)/(AGBCVariables.positiveA+AGBCVariables.negativeA)));
		 
		AmplitudeDetails.addToAmplitudeBuffer((int) AGBCVariables.amplitude 
				 							,AGBCVariables.positiveA+AGBCVariables.negativeA );
	}
	
	public static void phase_3_Frequency() { 
		
		AGBCVariables.frequency = FrequencyDetails.getFrequency((int) AudioAnalysisThread.startedVoiceCheck.get(id)
			.getAudioFormat().getSampleRate(),AGBCVariables.positiveA+AGBCVariables.negativeA );
		//	-AGBCVariables.freqLengthCorrection);
		
		FrequencyDetails.addToFrequnecyBuffer(AGBCVariables.frequency);
	}
	 
	public static void phase_3_RawAudioData() {
		
		MiscellaneousData.rawAudioData[MiscellaneousData.rawDataCounter++]="\nFrequency: "
			+ AGBCVariables.frequency+", Amplitude pozitiv: "+(AGBCVariables.posi_ampl_avgs
				/ (AGBCVariables.positiveA = AGBCVariables.positiveA > 0 
					? AGBCVariables.positiveA : 1 ))+", Amplitude negativ: "
						+(AGBCVariables.nega_ampl_avgs/(AGBCVariables.negativeA = 
							AGBCVariables.negativeA > 0 
								? AGBCVariables.negativeA : 1 ))
									+", .-<<||||   "+ MiscellaneousData.sb.toString() + ";\n";
		 
		MiscellaneousData.sb = new StringBuilder();
		MiscellaneousData.sb.append(AudioAnalysisThread.startedVoiceCheck.get(id).getIntStream()[i] +", ");
	}

	public static void phase_3_BuildSequenceArray() {
		
		MiscellaneousData.getWaveDetails(AGBCVariables.posi_ampl_avgs,AGBCVariables.positiveA
				 						,AGBCVariables.nega_ampl_avgs,AGBCVariables.negativeA);
		
		MiscellaneousData.buildSequenceStringArray[MiscellaneousData.sequenceDataCounter++] = 
		(String.valueOf(AGBCVariables.frequency)+":" + MiscellaneousData.waveDetails[0]+":" 
		+ MiscellaneousData.waveDetails[1]+":" +MiscellaneousData.waveDetails[2]+":"
		+MiscellaneousData.waveDetails[3]+",\n");
	}
	 
	public static void phase_3_ResetCounters() {
		
		AGBCVariables.posi_ampl_avgs = AudioAnalysisThread.startedVoiceCheck.get(id).getIntStream()[i] ;
		AGBCVariables.nega_ampl_avgs = 0;
		AGBCVariables.positiveA = 1;
	    AGBCVariables.negativeA = 0; 
	    
	    if(AppSetup.multiGram)
	    	MultiDetails.resetMultiDetails();
	}
	 
	public static void phase_4_AmplitudeFiltering() { 

		AGBCVariables.amplitudeWaveMap = IntArrayUtil.rebuildIntArray(
				AGBCVariables.amplitudeWaveMap, AGBCVariables.amplitudeWaveMapCounter
						, "amplitudeWaveMap");
		
		AudioAnalysisThread.startedVoiceCheck.get(id).setAmplitudeWaveMap(AGBCVariables.amplitudeWaveMap);
		Debug.debug(2,"MultiAnalysisBuilder mainbuilder filtered amplitudeWaveMap Array: "
			+ Arrays.toString(AudioAnalysisThread.startedVoiceCheck.get(id).getAmplitudeWaveMap()));	
	}
	 
	public static void phase_4_FrequencyFiltering() {
		
		AGBCVariables.frequencyWaveMap = IntArrayUtil.rebuildIntArray(
			AGBCVariables.frequencyWaveMap
			,AGBCVariables.frequencyWaveMapCounter,"frequencyWaveMap");
		
		Debug.debug(2,"MultiAnalysisBuilder mainbuilder filtered frequencyWaveMap Array: "

			+ Arrays.toString(AGBCVariables.frequencyWaveMap));
		
		FrequencyDetails.buildMappedArray();
		AudioAnalysisThread.startedVoiceCheck.get(id).setFrequencyWaveMap(AGBCVariables.mappedFrequencyArray);
	}
		
	public static void phase_4_SpykeRemove() {
		
		AudioAnalysisThread.startedVoiceCheck.get(id).setAmplitudeWaveMap(
			AudioGramUtil.removeSpikes(AudioAnalysisThread.startedVoiceCheck.get(id).getAmplitudeWaveMap(),1));
		
		AudioAnalysisThread.startedVoiceCheck.get(id).setFrequencyWaveMap(
				AudioGramUtil.removeSpikes(AudioAnalysisThread.startedVoiceCheck.get(id).getFrequencyWaveMap(),1));	
		
		Debug.debug(2,"MultiAnalysisBuilder mainbuilder phase_4_Amplitude spykeRemove Array: "

			+ Arrays.toString(AGBCVariables.amplitudeWaveMap));
		
		Debug.debug(2,"MultiAnalysisBuilder mainbuilder phase_4_Frequency spykeRemove Array: "

			+ Arrays.toString(AGBCVariables.mappedFrequencyArray));				
	}
	
	public static void phase_4_AmplitudeOptimizing() {
		
		AudioAnalysisThread.startedVoiceCheck.get(id).setAmplitudeWaveMap(
				AudioGramUtil.optimezeAmplitudeMapHeight(AudioAnalysisThread.startedVoiceCheck.get(id).getAmplitudeWaveMap()));
		
		Debug.debug(2,"MultiAnalysisBuilder mainbuilder AmplitudeOptimizing amplitudeWaveMap Array: "
				+ Arrays.toString(AGBCVariables.amplitudeWaveMap));
	}
	
	public static void phase_4_buildAvgMaps() {
		
		AudioAnalysisThread.startedVoiceCheck.get(id).setAmplitudeWaveMap(
				AudioGramUtil.buildAvgArray(AudioAnalysisThread.startedVoiceCheck.get(id).getAmplitudeWaveMap(),AppSetup.avglength));
				
		AudioAnalysisThread.startedVoiceCheck.get(id).setFrequencyWaveMap(
				AudioGramUtil.buildAvgArray(AudioAnalysisThread.startedVoiceCheck.get(id).getFrequencyWaveMap(),AppSetup.avglength));
		
		Debug.debug(2,"MultiAnalysisBuilder mainbuilder phase_4_Amplitude buildAvgMaps Array: "

			+ Arrays.toString(AGBCVariables.amplitudeWaveMap));
		
		Debug.debug(2,"MultiAnalysisBuilder mainbuilder phase_4_Frequency buildAvgMaps Array: "

			+ Arrays.toString(AGBCVariables.mappedFrequencyArray));
	}
			
	public static void phase_4_BuildMySpektrogram() { 
		
		MySpketrogramDetails.buildSpektroGramMap(AudioAnalysisThread.startedVoiceCheck.get(id).getAmplitudeWaveMap()
				,AudioAnalysisThread.startedVoiceCheck.get(id).getFrequencyWaveMap());

		AudioAnalysisThread.startedVoiceCheck.get(id)
			.setMySpektrogramMap(MySpketrogramDetails.spektrogramMap);
		
		Debug.debug(2,"MultiAnalysisBuilder mainbuilder filtered spektrogramMap Array Length: " 
			+ MySpketrogramDetails.spektrogramMap.length 
			+ " Array: "+ Arrays.toString(MySpketrogramDetails.spektrogramMap));
	}
	 
	public static void phase_4_BuildSpektrogram() {
		
		SpektroGramBuilder.mainBuilder(id);
	}
	      	 
	public static void phase_4_BuildSequenceFilterring() { 	
		
		AudioAnalysisThread.startedVoiceCheck.get(id).setSequenceStringArray(
			StringArrayUtil.filterEmptyStringsWithUnknownLength(
				MiscellaneousData.buildSequenceStringArray, "buildSequenceArray"));
	}
	 
	public static void phase_4_BuildVoiceRecognitionPointsArray() { 
		
		MiscellaneousData.buildVoiceRecognitionPointsArray(
			AudioAnalysisThread.startedVoiceCheck.get(id).getMySpektrogramMap());
			
		AudioAnalysisThread.startedVoiceCheck.get(id).setVoiceRecognitionPointsArray(
			MiscellaneousData.voiceRecognitionPointsStringArray);
	}
	
	public static void phase_4_BuildVoiceRecognitionSlopesArray() {
		
		MiscellaneousData.buildVoiceRecognitionSlopesArray(id);		
	}
	
	public static void phase_4_CopyRawData() {
		
		AudioAnalysisThread.startedVoiceCheck.get(id).setRawAudioData(
				MiscellaneousData.rawAudioData);
		
		AudioAnalysisThread.startedVoiceCheck.get(id)
			.setSequenceStringArray(MiscellaneousData.buildSequenceStringArray);
	} 
	
	public static void resetMainDetails() {

		AGBCVariables.resetVariables(id);
		MiscellaneousData.initMiscellaneousDataDetails(id);
	}
}
