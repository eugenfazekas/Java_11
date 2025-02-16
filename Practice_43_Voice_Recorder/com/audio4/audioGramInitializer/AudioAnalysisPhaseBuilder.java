package com.audio4.audioGramInitializer;

import com.audio0.main.AppSetup;
import com.audio5.audioGramBuilder.MultiAnalysisBuilder;
import com.audio8.util.Debug;

public class AudioAnalysisPhaseBuilder {
	
	private static boolean instanceOf = false;
	private static Task task;
	public static Task[] multiAnalysisPhase0 = new Task[0];
	public static Task[] multiAnalysisPhase1 = new Task[0];
	public static Task[] multiAnalysisPhase2 = new Task[0];
	public static Task[] multiAnalysisPhase3 = new Task[0];
	public static Task[] multiAnalysisPhase4 = new Task[0];
	private static Task[] tempsTasks;
	
	public AudioAnalysisPhaseBuilder() {
		
		if(!instanceOf) {
			
			instanceOf = true;
			
			mainAnalysisPhaseBuilder();
		}
	}
	
	public static void mainAnalysisPhaseBuilder() {
		
		Debug.debug(4,"AudioAnalysisPhaseBuilder mainAnalysisPhaseBuilder iniated! ");
		
		buildMultiAnalysisPhase0();
		
		buildMultiAnalysisPhase1();
		
		buildMultiAnalysisPhase2();
		
		buildMultiAnalysisPhase3();
		
		buildMultiAnalysisPhase4();
	}
	
	private static void buildMultiAnalysisPhase0() {
			
		if(AppSetup.amplitudeGram || AppSetup.multiGram) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_0_Amplitude();};};
			 
			multiAnalysisPhase0 = addTask(multiAnalysisPhase0,task) ;
		}
		
		if(AppSetup.frequencyGram || AppSetup.multiGram) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_0_Frequency();};};
			 
			multiAnalysisPhase0 = addTask(multiAnalysisPhase0,task) ;
		}		
		
		Debug.debug(5,"AudioAnalysisPhaseBuilder multiAnalysisPhase4: "+multiAnalysisPhase0.length);
	}
		
	private static void buildMultiAnalysisPhase1() {
		
		if(AppSetup.amplitudeGram || AppSetup.multiGram) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_1_Amplitude();};};
			 
			multiAnalysisPhase1 = addTask(multiAnalysisPhase1,task) ;
		}
		
		if(AppSetup.frequencyGram || AppSetup.multiGram) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_1_Frequency();};};
			 
			multiAnalysisPhase1 = addTask(multiAnalysisPhase1,task) ;
		}
		
		if(AppSetup.multiGram) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_1_MultiPositive();};};
			 
			multiAnalysisPhase1 = addTask(multiAnalysisPhase1,task) ;
		}
		
		if((AppSetup.amplitudeGram && AppSetup.frequencyGram && AppSetup.rawAudioData) ||
				(AppSetup.multiGram && AppSetup.rawAudioData)) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_1_RawAudioData();};};
			 
			multiAnalysisPhase1 = addTask(multiAnalysisPhase1,task) ;
		}
		
		Debug.debug(5,"AudioAnalysisPhaseBuilder multiAnalysisPhase1: "+multiAnalysisPhase1.length);
	} 
	
	private static void buildMultiAnalysisPhase2() {
		
		if(AppSetup.amplitudeGram || AppSetup.multiGram) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_2_Amplitude();};};
			 
			multiAnalysisPhase2 = addTask(multiAnalysisPhase2,task) ;
		}
		
		if(AppSetup.frequencyGram || AppSetup.multiGram) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_2_Frequency();;};};
			 
			multiAnalysisPhase2 = addTask(multiAnalysisPhase2,task) ;
		}
		
		if(AppSetup.multiGram) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_2_MultiNegative();};};
			 
			multiAnalysisPhase2 = addTask(multiAnalysisPhase2,task) ;
		}
		
		if((AppSetup.amplitudeGram && AppSetup.frequencyGram && AppSetup.rawAudioData) ||
				(AppSetup.multiGram && AppSetup.rawAudioData)) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_2_RawAudioData();};};
			 
			multiAnalysisPhase2 = addTask(multiAnalysisPhase2,task) ;
		}
			
		Debug.debug(5,"AudioAnalysisPhaseBuilder multiAnalysisPhase2: "+multiAnalysisPhase2.length);
	}
	
	private static void buildMultiAnalysisPhase3() {
		
		if(AppSetup.amplitudeGram || AppSetup.multiGram) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_3_Amplitude();};};
			 
			multiAnalysisPhase3 = addTask(multiAnalysisPhase3,task) ;
		}
		
		if(AppSetup.frequencyGram || AppSetup.multiGram) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_3_Frequency();};};
			 
			multiAnalysisPhase3 = addTask(multiAnalysisPhase3,task) ;
		}
		
		if((AppSetup.amplitudeGram && AppSetup.frequencyGram && AppSetup.rawAudioData) 
			|| (AppSetup.multiGram && AppSetup.rawAudioData)) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_3_RawAudioData();};};
			 
			multiAnalysisPhase3 = addTask(multiAnalysisPhase3,task) ;
		}
		
		if((AppSetup.amplitudeGram && AppSetup.frequencyGram && AppSetup.buildSequenceArray) 
			|| (AppSetup.multiGram && AppSetup.buildSequenceArray )) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_3_BuildSequenceArray();};};
			 
			multiAnalysisPhase3 = addTask(multiAnalysisPhase3,task) ;
		}
		
		if(AppSetup.amplitudeGram || AppSetup.frequencyGram || AppSetup.multiGram) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_3_ResetCounters();};};
			 
			multiAnalysisPhase3 = addTask(multiAnalysisPhase3,task) ;
		}	
		
		Debug.debug(5,"AudioAnalysisPhaseBuilder multiAnalysisPhase3: "+multiAnalysisPhase3.length);
	}
	
	private static void buildMultiAnalysisPhase4() {
		
		if(AppSetup.amplitudeGram|| AppSetup.multiGram) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_4_AmplitudeFiltering();};};
			 
			multiAnalysisPhase4 = addTask(multiAnalysisPhase4,task) ;
		}
		
		if(AppSetup.frequencyGram|| AppSetup.multiGram) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_4_FrequencyFiltering();};};
			 
			multiAnalysisPhase4 = addTask(multiAnalysisPhase4,task) ;
		}
		
		if(AppSetup.spektrogram) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_4_BuildSpektrogram();};};
			 
			multiAnalysisPhase4 = addTask(multiAnalysisPhase4,task) ;
		}
		
		if((AppSetup.amplitudeGram && AppSetup.frequencyGram && AppSetup.rawAudioData) 
			|| (AppSetup.multiGram && AppSetup.rawAudioData)) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_4_CopyRawData();};};
			 
			multiAnalysisPhase4 = addTask(multiAnalysisPhase4,task) ;
		}
		
		if((AppSetup.amplitudeGram && AppSetup.frequencyGram && AppSetup.buildSequenceArray) 
			|| (AppSetup.multiGram && AppSetup.buildSequenceArray)) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_4_BuildSequenceFilterring();};};
			 
			multiAnalysisPhase4 = addTask(multiAnalysisPhase4,task) ;
		}
				
		if(AppSetup.spykeRemove) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_4_SpykeRemove();};};
			 
			multiAnalysisPhase4 = addTask(multiAnalysisPhase4,task) ;
		}
			
		if(AppSetup.avgBuild) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_4_buildAvgMaps();};};
			 
			multiAnalysisPhase4 = addTask(multiAnalysisPhase4,task) ;
		}
		
		if(AppSetup.amplitudeHeightRebuild) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_4_AmplitudeOptimizing();};};
			 
			multiAnalysisPhase4 = addTask(multiAnalysisPhase4,task) ;
		}
		
		if((( AppSetup.amplitudeGram && AppSetup.frequencyGram) || AppSetup.multiGram)&& AppSetup.mySpektrogram) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_4_BuildMySpektrogram();};}; 
			 
			multiAnalysisPhase4 = addTask(multiAnalysisPhase4,task) ;
		}
				
		if((AppSetup.amplitudeGram && AppSetup.frequencyGram || AppSetup.multiGram)&& AppSetup.mySpektrogram
				&& AppSetup.voiceRecognitionData && AppSetup.voiceRecognitionPointsData) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_4_BuildVoiceRecognitionPointsArray();};};
			 
			multiAnalysisPhase4 = addTask(multiAnalysisPhase4,task) ;
		}
		
		if((AppSetup.amplitudeGram && AppSetup.frequencyGram  || AppSetup.multiGram) && AppSetup.mySpektrogram
				&& AppSetup.voiceRecognitionData && AppSetup.voiceRecognitionSlopesData) {
			
			task = new Task() { public void makeTask() {MultiAnalysisBuilder.phase_4_BuildVoiceRecognitionSlopesArray();};};
			 
			multiAnalysisPhase4 = addTask(multiAnalysisPhase4,task) ;
		}
		
		Debug.debug(5,"AudioAnalysisPhaseBuilder multiAnalysisPhase2: "+multiAnalysisPhase4.length);
	}
		
	public static Task[] addTask(Task[] tasks, Task task) {
				
		if(tasks.length > 0) {
			
			tempsTasks = new Task[tasks.length+1];
			
			for(int i = 0; i < tasks.length; i++)
				tempsTasks[i] = tasks[i];
			
			tempsTasks[tasks.length] = task;
			
		} else 
			tempsTasks = new Task[] {task};
		
		Debug.debug(5,"AudioAnalysisPhaseBuilder addtask tasks.length: "
			+tasks.length+ ", Return Tasks.length: "+tempsTasks.length);
		
			return tempsTasks;
	}
	
	public static void runTasks (Task[] tasks) {
		
		for (Task task : tasks)
			task.makeTask();	
	}
}
