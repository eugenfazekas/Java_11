package com.voiceRecognition.audio5.audioGramBuilder;

import com.voiceRecognition.audio0.main.VoiceRecognitionAppSetup;
import com.voiceRecognition.audio8.util.Debug;

public class AudioAnalysisPhaseBuilder {
	
	private static boolean instanceOf = false;
	private static Task task;
	static Task[] multiAnalysisPhase1 = new Task[0];
	static Task[] multiAnalysisPhase2 = new Task[0];
	static Task[] multiAnalysisPhase3 = new Task[0];
	static Task[] tempsTasks;
	
	private static int debug_level_INFO = 5;;
	
	public AudioAnalysisPhaseBuilder() {
		
		if(!instanceOf) {
			
			instanceOf = true;
			
			mainAnalysisPhaseBuilder();
		}
	}
	
	static void mainAnalysisPhaseBuilder() {
		
		Debug.debug(debug_level_INFO,"AudioAnalysisPhaseBuilder mainAnalysisPhaseBuilder iniated! ");
		
		buildMultiAnalysisPhase1();
		
		buildMultiAnalysisPhase2();
		
		buildMultiAnalysisPhase3();
	}
	
	private static void buildMultiAnalysisPhase1() {
			
		if(VoiceRecognitionAppSetup.amplitudeGram) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_1_Amplitude_Frequency();};};
			 
			multiAnalysisPhase1 = addTask(multiAnalysisPhase1,task) ;
		}
		
		if(VoiceRecognitionAppSetup.buildSequenceArray) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_1_Sequence();};};
			 
			multiAnalysisPhase1 = addTask(multiAnalysisPhase1,task) ;
		}	
			
		Debug.debug(debug_level_INFO,"AudioAnalysisPhaseBuilder multiAnalysisPhase1: "+multiAnalysisPhase1.length);
	}
		
	private static void buildMultiAnalysisPhase2() {
		
		if(VoiceRecognitionAppSetup.amplitudeGram) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_2_AmplitudeCheck();};};
			 
			multiAnalysisPhase2 = addTask(multiAnalysisPhase2,task) ;
		}
		
		if(VoiceRecognitionAppSetup.frequencyGram) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_2_FrequencyCheck();};};
			 
			multiAnalysisPhase2 = addTask(multiAnalysisPhase2,task);
		}
		
		if(VoiceRecognitionAppSetup.amplitudeGram && VoiceRecognitionAppSetup.frequencyGram && VoiceRecognitionAppSetup.rawAudioData) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_2_AddRawAudioData();;};};
			 
			multiAnalysisPhase2 = addTask(multiAnalysisPhase2,task);
		}
		
		if(VoiceRecognitionAppSetup.amplitudeGram && VoiceRecognitionAppSetup.frequencyGram && VoiceRecognitionAppSetup.buildSequenceArray) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_2_AddToBuildSeuqnce();};};
			 
			multiAnalysisPhase2 = addTask(multiAnalysisPhase2,task);
		}
	
		Debug.debug(debug_level_INFO,"AudioAnalysisPhaseBuilder multiAnalysisPhase2: "+multiAnalysisPhase2.length);
	} 
	
	private static void buildMultiAnalysisPhase3() {
		
		if(VoiceRecognitionAppSetup.amplitudeGram) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_3_AmplitudeFiltering();};};
			 
			multiAnalysisPhase3 = addTask(multiAnalysisPhase3,task) ;
		}
			
		if(VoiceRecognitionAppSetup.amplitudeGram && VoiceRecognitionAppSetup.frequencyGram) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_3_FrequencyFiltering();};};
			 
			multiAnalysisPhase3 = addTask(multiAnalysisPhase3,task) ;
		}
		
		if(VoiceRecognitionAppSetup.amplitudeGram && VoiceRecognitionAppSetup.frequencyGram && VoiceRecognitionAppSetup.amplitudeHeightRebuild) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_3_AmplitudeOptimizing();};};
			 
			multiAnalysisPhase3 = addTask(multiAnalysisPhase3,task) ;
		}
				
		if(VoiceRecognitionAppSetup.amplitudeGram && VoiceRecognitionAppSetup.frequencyGram && VoiceRecognitionAppSetup.spykeRemove) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_3_SpykeRemove();};};
			 
			multiAnalysisPhase3 = addTask(multiAnalysisPhase3,task) ;
		}
			
		if(VoiceRecognitionAppSetup.amplitudeGram && VoiceRecognitionAppSetup.frequencyGram && VoiceRecognitionAppSetup.avgBuild) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_3_buildAvgMaps();};};
			 
			multiAnalysisPhase3 = addTask(multiAnalysisPhase3,task) ;
		}
				
		if((( VoiceRecognitionAppSetup.amplitudeGram && VoiceRecognitionAppSetup.frequencyGram))&& VoiceRecognitionAppSetup.mySpektrogram) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_3_BuildMySpektrogram();};}; 
			 
			multiAnalysisPhase3 = addTask(multiAnalysisPhase3,task) ;
		}
		
		if(VoiceRecognitionAppSetup.spektrogram) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_3_BuildSpektrogram();};}; 
			 
			multiAnalysisPhase3 = addTask(multiAnalysisPhase3,task) ;
		}
		
		if(VoiceRecognitionAppSetup.rawAudioData) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_3_BuildRawDataFilterring();};};
			 
			multiAnalysisPhase3 = addTask(multiAnalysisPhase3,task) ;
		}
		
		if(VoiceRecognitionAppSetup.buildSequenceArray) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_3_BuildSequenceFilterring();};};
			 
			multiAnalysisPhase3 = addTask(multiAnalysisPhase3,task) ;
		}
			
		if(VoiceRecognitionAppSetup.amplitudeGram && VoiceRecognitionAppSetup.frequencyGram && VoiceRecognitionAppSetup.mySpektrogram
				&& VoiceRecognitionAppSetup.voiceRecognitionData) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_3_BuildVoiceRecognitionBorders();};};
			 
			multiAnalysisPhase3 = addTask(multiAnalysisPhase3,task) ;
		}
		
		if((VoiceRecognitionAppSetup.amplitudeGram && VoiceRecognitionAppSetup.frequencyGram)&& VoiceRecognitionAppSetup.mySpektrogram
				&& VoiceRecognitionAppSetup.voiceRecognitionData && VoiceRecognitionAppSetup.voiceRecognitionPointsData) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_3_BuildVoiceRecognitionPointsArray();};};
			 
			multiAnalysisPhase3 = addTask(multiAnalysisPhase3,task) ;
		}
		
		if((VoiceRecognitionAppSetup.amplitudeGram && VoiceRecognitionAppSetup.frequencyGram) && VoiceRecognitionAppSetup.mySpektrogram
				&& VoiceRecognitionAppSetup.voiceRecognitionData && VoiceRecognitionAppSetup.voiceRecognitionSlopesData) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_3_BuildVoiceRecognitionSlopesArray();};};
			 
			multiAnalysisPhase3 = addTask(multiAnalysisPhase3,task) ;
		}
		
		if((VoiceRecognitionAppSetup.amplitudeGram && VoiceRecognitionAppSetup.frequencyGram) && VoiceRecognitionAppSetup.mySpektrogram
				&& VoiceRecognitionAppSetup.voiceRecognitionData && VoiceRecognitionAppSetup.voiceRecognitionAreaData) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_3_BuildVoiceRecognitionAreasArray();};};
			 
			multiAnalysisPhase3 = addTask(multiAnalysisPhase3,task) ;
		}
		
		if((VoiceRecognitionAppSetup.amplitudeGram && VoiceRecognitionAppSetup.frequencyGram) && VoiceRecognitionAppSetup.mySpektrogram
				&& VoiceRecognitionAppSetup.voiceRecognitionData && VoiceRecognitionAppSetup.voiceRecognitionScanData) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_3_BuildVoiceRecognitionScanArray();};};
			 
			multiAnalysisPhase3 = addTask(multiAnalysisPhase3,task) ;
		}
				
		Debug.debug(debug_level_INFO,"AudioAnalysisPhaseBuilder multiAnalysisPhase3: "+multiAnalysisPhase3.length);
	}
	
	static Task[] addTask(Task[] tasks, Task task) {
				
		if(tasks.length > 0) {
			
			tempsTasks = new Task[tasks.length+1];
			
			for(int i = 0; i < tasks.length; i++)
				tempsTasks[i] = tasks[i];
			
			tempsTasks[tasks.length] = task;
			
		} else 
			tempsTasks = new Task[] {task};
		
		Debug.debug(debug_level_INFO,"AudioAnalysisPhaseBuilder addtask tasks.length: "
			+tasks.length+ ", Return Tasks.length: "+tempsTasks.length);
		
			return tempsTasks;
	}
	
	static void runTasks (Task[] tasks) {
		
		for (Task task : tasks)
			task.makeTask();	
	}
}
