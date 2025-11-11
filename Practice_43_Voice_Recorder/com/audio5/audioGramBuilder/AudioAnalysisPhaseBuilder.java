package com.audio5.audioGramBuilder;

import com.audio0.main.AppSetup;
import com.audio8.util.Debug;

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
			
		if(AppSetup.amplitudeGram) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_1_Amplitude_Frequency();};};
			 
			multiAnalysisPhase1 = addTask(multiAnalysisPhase1,task) ;
		}
		
		if(AppSetup.buildSequenceArray) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_1_Sequence();};};
			 
			multiAnalysisPhase1 = addTask(multiAnalysisPhase1,task) ;
		}	
			
		Debug.debug(debug_level_INFO,"AudioAnalysisPhaseBuilder multiAnalysisPhase1: "+multiAnalysisPhase1.length);
	}
		
	private static void buildMultiAnalysisPhase2() {
		
		if(AppSetup.amplitudeGram) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_2_AmplitudeCheck();};};
			 
			multiAnalysisPhase2 = addTask(multiAnalysisPhase2,task) ;
		}
		
		if(AppSetup.frequencyGram) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_2_FrequencyCheck();};};
			 
			multiAnalysisPhase2 = addTask(multiAnalysisPhase2,task);
		}
		
		if(AppSetup.amplitudeGram && AppSetup.frequencyGram && AppSetup.rawAudioData) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_2_AddRawAudioData();;};};
			 
			multiAnalysisPhase2 = addTask(multiAnalysisPhase2,task);
		}
		
		if(AppSetup.amplitudeGram && AppSetup.frequencyGram && AppSetup.buildSequenceArray) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_2_AddToBuildSeuqnce();};};
			 
			multiAnalysisPhase2 = addTask(multiAnalysisPhase2,task);
		}
	
		Debug.debug(debug_level_INFO,"AudioAnalysisPhaseBuilder multiAnalysisPhase2: "+multiAnalysisPhase2.length);
	} 
	
	private static void buildMultiAnalysisPhase3() {
		
		if(AppSetup.amplitudeGram) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_3_AmplitudeFiltering();};};
			 
			multiAnalysisPhase3 = addTask(multiAnalysisPhase3,task) ;
		}
			
		if(AppSetup.amplitudeGram && AppSetup.frequencyGram) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_3_FrequencyFiltering();};};
			 
			multiAnalysisPhase3 = addTask(multiAnalysisPhase3,task) ;
		}
		
		if(AppSetup.amplitudeGram && AppSetup.frequencyGram && AppSetup.amplitudeHeightRebuild) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_3_AmplitudeOptimizing();};};
			 
			multiAnalysisPhase3 = addTask(multiAnalysisPhase3,task) ;
		}
				
		if(AppSetup.amplitudeGram && AppSetup.frequencyGram && AppSetup.spykeRemove) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_3_SpykeRemove();};};
			 
			multiAnalysisPhase3 = addTask(multiAnalysisPhase3,task) ;
		}
			
		if(AppSetup.amplitudeGram && AppSetup.frequencyGram && AppSetup.avgBuild) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_3_buildAvgMaps();};};
			 
			multiAnalysisPhase3 = addTask(multiAnalysisPhase3,task) ;
		}
				
		if((( AppSetup.amplitudeGram && AppSetup.frequencyGram))&& AppSetup.mySpektrogram) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_3_BuildMySpektrogram();};}; 
			 
			multiAnalysisPhase3 = addTask(multiAnalysisPhase3,task) ;
		}
		
		if(AppSetup.spektrogram) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_3_BuildSpektrogram();};}; 
			 
			multiAnalysisPhase3 = addTask(multiAnalysisPhase3,task) ;
		}
		
		if(AppSetup.rawAudioData) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_3_BuildRawDataFilterring();};};
			 
			multiAnalysisPhase3 = addTask(multiAnalysisPhase3,task) ;
		}
		
		if(AppSetup.buildSequenceArray) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_3_BuildSequenceFilterring();};};
			 
			multiAnalysisPhase3 = addTask(multiAnalysisPhase3,task) ;
		}
			
		if(AppSetup.amplitudeGram && AppSetup.frequencyGram && AppSetup.mySpektrogram
				&& AppSetup.voiceRecognitionData) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_3_BuildVoiceRecognitionBorders();};};
			 
			multiAnalysisPhase3 = addTask(multiAnalysisPhase3,task) ;
		}
		
		if((AppSetup.amplitudeGram && AppSetup.frequencyGram)&& AppSetup.mySpektrogram
				&& AppSetup.voiceRecognitionData && AppSetup.voiceRecognitionPointsData) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_3_BuildVoiceRecognitionPointsArray();};};
			 
			multiAnalysisPhase3 = addTask(multiAnalysisPhase3,task) ;
		}
		
		if((AppSetup.amplitudeGram && AppSetup.frequencyGram) && AppSetup.mySpektrogram
				&& AppSetup.voiceRecognitionData && AppSetup.voiceRecognitionSlopesData) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_3_BuildVoiceRecognitionSlopesArray();};};
			 
			multiAnalysisPhase3 = addTask(multiAnalysisPhase3,task) ;
		}
		
		if((AppSetup.amplitudeGram && AppSetup.frequencyGram) && AppSetup.mySpektrogram
				&& AppSetup.voiceRecognitionData && AppSetup.voiceRecognitionAreaData) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_3_BuildVoiceRecognitionAreasArray();};};
			 
			multiAnalysisPhase3 = addTask(multiAnalysisPhase3,task) ;
		}
		
		if((AppSetup.amplitudeGram && AppSetup.frequencyGram) && AppSetup.mySpektrogram
				&& AppSetup.voiceRecognitionData && AppSetup.voiceRecognitionScanData) {
			
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
