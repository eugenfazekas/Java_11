package com.audio5.audioGramBuilder;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import com.audio0.main.AppSetup;
import com.audio2.recorder.AudioListener;
import com.audio4.audioGramInitializer.mainInit.AudioAnalysisThread;
import com.audio8.util.Debug;

public class MultiAnalysisBuilder {
	
	private static int i;
	private static int id;
	public static AtomicBoolean building = new AtomicBoolean(false);
	
	private static int debug_level_INFO = 5;
	private static int debud_level_DEBUG = 5;
    
	private static void buildMultiWaveMap(int id) {
				
		for(;i < AudioAnalysisThread.startedVoiceCheck.get(id).getIntStream().length; i++) {
			
			AudioAnalysisPhaseBuilder.runTasks(AudioAnalysisPhaseBuilder.multiAnalysisPhase1);		
			 
			if(AGBCVariables.avgMilisecCounter == AGBCVariables.AVG_MILISEC_LENGTH-1) 		{
				
				AudioAnalysisPhaseBuilder.runTasks(AudioAnalysisPhaseBuilder.multiAnalysisPhase2);
				phase_2_ResetCounters();
			}
			
			increaseMilisecCounter();
		}	
		
		AudioAnalysisPhaseBuilder.runTasks(AudioAnalysisPhaseBuilder.multiAnalysisPhase3);
	}
		 		
	public static void mainAnalysisBuilder(int Id) {
		
		id = Id;

		Debug.debug(debug_level_INFO,"MultiAnalysisBuilder mainAnalysisBuilder started: " 
				+AudioAnalysisThread.startedVoiceCheck.get(id).getBuild()+" "
				+AudioAnalysisThread.startedVoiceCheck.get(id).getNextStage().equals("analysis")+" "
				+AppSetup.multiAnalysis);
		
		if(!AudioAnalysisThread.startedVoiceCheck.get(id).getBuild() || 								
			!AudioAnalysisThread.startedVoiceCheck.get(id).getNextStage().equals("analysis") 
			  || !AppSetup.multiAnalysis) 
				return;
		
		building.set(true);
		
		Debug.debug(debug_level_INFO,"MultiAnalysisBuilder mainAnalysisBuilder started: ");
		Debug.startTime = System.currentTimeMillis();
		
		new AudioAnalysisPhaseBuilder();
		
		resetMainDetails();
			
		init(); 
					
		buildMultiWaveMap(id);
		
		AudioAnalysisThread.startedVoiceCheck.get(id).setNextStage();

		Debug.debug(debug_level_INFO,"MultiAnalysisBuilder mainBuilder Elapsed Time: "
			+(System.currentTimeMillis() - Debug.startTime));	
		
		building.set(false);
	}
	
	private static void init() {
		
		int length = AGBCVariables.AVG_MILISEC_LENGTH * 2;
	
		for(i = 0; i < length; i++) {
		
			if(i < AGBCVariables.AVG_MILISEC_LENGTH) 
				
				AGBCVariables.first.addSampleToObject(
					AudioAnalysisThread.startedVoiceCheck.get(id).getIntStream()[i]);						
			 else 				
				AGBCVariables.middle.addSampleToObject(
					AudioAnalysisThread.startedVoiceCheck.get(id).getIntStream()[i]);						
		}
		
		Debug.debug(debug_level_INFO,"MultiAnalysisBuilder first getPosCounter: "+AGBCVariables.first.getPosCounter()
			+",AGBCVariables.first.addNegCounter:  "+AGBCVariables.first.getNegCounter()
			+", AGBCVariables.midddle.addPosCounter"
			+ AGBCVariables.middle.getPosCounter() +", AGBCVariables.midddle.addNegCounter:"
			+ AGBCVariables.middle.getNegCounter());
	}
	
	private static void increaseMilisecCounter() {
		
		AGBCVariables.avgMilisecCounter++;
	}
 	 
	static void phase_1_Amplitude_Frequency() {
		
		AGBCVariables.last.addSampleToObject(
			AudioAnalysisThread.startedVoiceCheck.get(id).getIntStream()[i]);				
	}
	
	static void phase_1_Sequence() {
		
		MiscellaneousData.sequenceSetHighestAmplitude(
			AudioAnalysisThread.startedVoiceCheck.get(id).getIntStream()[i]);
	}
			 
	static void phase_2_AmplitudeCheck() {  
		 			
		AmplitudeDetails.addToAmplitudeWaveMap(
				AmplitudeDetails.calculateAVGAmplitude(
						AGBCVariables.first, AGBCVariables.middle, AGBCVariables.last ),id);	
		
		Debug.debug(debud_level_DEBUG, "MultiAnalysisBuilder phase_2_AmplitudeCheck AGBCVariables.first "
			+ AGBCVariables.first.getPosAmplitude()+" AGBCVariables.middle "
			+ AGBCVariables.middle.getPosAmplitude()+" AGBCVariables.last "
			+ AGBCVariables.last.getPosAmplitude());
	}
	
	static void phase_2_FrequencyCheck() {  
			
		AGBCVariables.frequency = FFTFrecvencyCheckUtil.getFrecvencys(
			AGBCVariables.first.getSamples(), AGBCVariables.middle.getSamples()
			,AGBCVariables.last.getSamples(), ((int) AudioListener.format.getSampleRate()))[1];
		
		FrequencyDetails.addToFrequencyWaveMap(AGBCVariables.frequency);		
	}

	static void phase_2_AddRawAudioData() {
		
		MiscellaneousData.rawAudioData[MiscellaneousData.rawDataCounter++]="\nFrequency: "
			+ AGBCVariables.frequency+", Amplitude: "+AGBCVariables.amplitude
				+", .-<<||||   "+ Arrays.toString(AGBCVariables.middle.getSamples()) + ";\n";
	}
	
	static void phase_2_AddToBuildSeuqnce() {

		MiscellaneousData.buildSequenceStringArray[MiscellaneousData.sequenceDataCounter++]
			= MiscellaneousData.sequenceGetAmplitude()+ ":" + (AGBCVariables.frequency) ;
	}
	
	public static void phase_2_ResetCounters() {
		
		AGBCVariables.first = AGBCVariables.middle;
		AGBCVariables.middle = AGBCVariables.last;
		AGBCVariables.last = new AudioGramObject(AGBCVariables.AVG_MILISEC_LENGTH);
		AGBCVariables.avgMilisecCounter = 0;
		AGBCVariables.amplitudeHighest1 = 0;
		AGBCVariables.amplitudeHighest2 = 0; 
	}
	 
	static void phase_3_AmplitudeFiltering() { 

		AGBCVariables.amplitudeWaveMap = IntArrayUtil.rebuildIntArray(
				AGBCVariables.amplitudeWaveMap, AGBCVariables.amplitudeWaveMapCounter
						, "amplitudeWaveMap");
		
		AudioAnalysisThread.startedVoiceCheck.get(id).setAmplitudeWaveMap(AGBCVariables.amplitudeWaveMap);
		Debug.debug(debug_level_INFO,"MultiAnalysisBuilder mainbuilder filtered amplitudeWaveMap Array: "
			+ Arrays.toString(AudioAnalysisThread.startedVoiceCheck.get(id).getAmplitudeWaveMap()));	
	}
	 
	static void phase_3_FrequencyFiltering() {
		
		AGBCVariables.frequencyWaveMap = IntArrayUtil.rebuildIntArray(
			AGBCVariables.frequencyWaveMap
			,AGBCVariables.frequencyWaveMapCounter,"frequencyWaveMap");
		
		Debug.debug(debug_level_INFO,"MultiAnalysisBuilder mainbuilder filtered frequencyWaveMap Array: "

			+ Arrays.toString(AGBCVariables.frequencyWaveMap));
		
		FrequencyDetails.buildMappedArray();
		AudioAnalysisThread.startedVoiceCheck.get(id).setFrequencyWaveMap(AGBCVariables.mappedFrequencyArray);
	}
		
	static void phase_3_SpykeRemove() {
		
		AudioAnalysisThread.startedVoiceCheck.get(id).setAmplitudeWaveMap(
			AudioGramUtil.removeSpikes(
				AudioAnalysisThread.startedVoiceCheck.get(id).getAmplitudeWaveMap(),1));
		
		AudioAnalysisThread.startedVoiceCheck.get(id).setFrequencyWaveMap(
			AudioGramUtil.removeSpikes(
				AudioAnalysisThread.startedVoiceCheck.get(id).getFrequencyWaveMap(),1));	
		
		Debug.debug(debug_level_INFO,"MultiAnalysisBuilder mainbuilder phase_4_Amplitude spykeRemove Array: "

			+ Arrays.toString(AGBCVariables.amplitudeWaveMap));
		
		Debug.debug(debug_level_INFO,"MultiAnalysisBuilder mainbuilder phase_4_Frequency spykeRemove Array: "

			+ Arrays.toString(AGBCVariables.mappedFrequencyArray));				
	}
	
	static void phase_3_AmplitudeOptimizing() {
		
		AudioAnalysisThread.startedVoiceCheck.get(id).setAmplitudeWaveMap(
			AudioGramUtil.optimezeAmplitudeMapHeight(
				AudioAnalysisThread.startedVoiceCheck.get(id).getAmplitudeWaveMap()));
		
		Debug.debug(debug_level_INFO,"MultiAnalysisBuilder mainbuilder AmplitudeOptimizing amplitudeWaveMap Array: "
			+ Arrays.toString(AudioAnalysisThread.startedVoiceCheck.get(id).getAmplitudeWaveMap()));
	}

	static void phase_3_buildAvgMaps() {
		
		AudioAnalysisThread.startedVoiceCheck.get(id).setAmplitudeWaveMap(
				AudioGramUtil.buildAvgArray(
					AudioAnalysisThread.startedVoiceCheck.get(id).getAmplitudeWaveMap(),AppSetup.avglength));
				
		AudioAnalysisThread.startedVoiceCheck.get(id).setFrequencyWaveMap(
				AudioGramUtil.buildAvgArray(
					AudioAnalysisThread.startedVoiceCheck.get(id).getFrequencyWaveMap(),AppSetup.avglength));
		
		Debug.debug(debug_level_INFO,"MultiAnalysisBuilder mainbuilder phase_4_Amplitude buildAvgMaps Array: "
			+ Arrays.toString(AudioAnalysisThread.startedVoiceCheck.get(id).getAmplitudeWaveMap()));
		
		Debug.debug(debug_level_INFO,"MultiAnalysisBuilder mainbuilder phase_4_Frequency buildAvgMaps Array: "
			+ Arrays.toString(AudioAnalysisThread.startedVoiceCheck.get(id).getFrequencyWaveMap()));
	}
			
	static void phase_3_BuildMySpektrogram() { 
		
		MySpketrogramDetails.buildSpektroGramMap(
				AudioAnalysisThread.startedVoiceCheck.get(id).getAmplitudeWaveMap()
				,AudioAnalysisThread.startedVoiceCheck.get(id).getFrequencyWaveMap());

		AudioAnalysisThread.startedVoiceCheck.get(id)
			.setMySpektrogramMap(MySpketrogramDetails.spektrogramMap);
		
		Debug.debug(debug_level_INFO,"MultiAnalysisBuilder mainbuilder filtered spektrogramMap Array Length: " 
			+ MySpketrogramDetails.spektrogramMap.length 
			+ " Array: "+ Arrays.toString(MySpketrogramDetails.spektrogramMap));
	}
	 
	static void phase_3_BuildSpektrogram() {
		
		AudioAnalysisThread.startedVoiceCheck.get(id).setSpektrogramMap(
			SpektroGramBuilder.mainBuilder(id));
	}
	      	 
	static void phase_3_BuildRawDataFilterring() { 	
		
		AudioAnalysisThread.startedVoiceCheck.get(id).setRawAudioData(
			StringArrayUtil.filterEmptyStringsWithUnknownLength(
				MiscellaneousData.rawAudioData, "buildRawAudioDataArray"));
	}
	
	static void phase_3_BuildSequenceFilterring() { 	
		
		AudioAnalysisThread.startedVoiceCheck.get(id).setSequenceStringArray(
			StringArrayUtil.filterEmptyStringsWithUnknownLength(
				MiscellaneousData.buildSequenceStringArray, "buildSequenceArray"));
		
		AudioAnalysisThread.startedVoiceCheck.get(id).getSequenceStringArray()[0] 
			= String.valueOf(AGBCVariables.AVG_MILISEC_LENGTH);
	}
	
	static void phase_3_BuildVoiceRecognitionBorders() { 
		
		MiscellaneousData.buildVoiceRecognitionBorders(id);			
	}
	 
	static void phase_3_BuildVoiceRecognitionPointsArray() { 
		
		MiscellaneousData.buildVoiceRecognitionPointsArray(id);			
	}
	
	static void phase_3_BuildVoiceRecognitionSlopesArray() {
		
		MiscellaneousData.buildVoiceRecognitionSlopesArray(id);		
	}
	
	static void phase_3_BuildVoiceRecognitionAreasArray() {
		
		MiscellaneousData.buildVoiceRecognitionAreaArray(id);		
	}
	
	static void phase_3_BuildVoiceRecognitionScanArray() {
		
		MiscellaneousData.buildVoiceRecognitionScanString(id);		
	}
		
	static void resetMainDetails() {

		AGBCVariables.resetVariables(id);
		MiscellaneousData.initMiscellaneousDataDetails(id);
	}
}
