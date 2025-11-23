package com.voiceRecognition.audio5.recognition;

import com.voiceRecognition.audio8.util.Debug;

public class VoiceCheckModel {

	private int id;
	public float [][][] dbCheckResults;
	public float [][][] sortedResults;
	private int baseTestsCounter;
	private int finishedTestsCounter;

	private static int debud_level_DEBUG = 5;
	
	public VoiceCheckModel(int Id , int testsCounter) {
		
		this.id = Id;
		this.baseTestsCounter = testsCounter;
		initArrays();
	}
	
	public int getId() {
		return id;
	}
	
	public float[][][] getDbCheckResults() {
		return dbCheckResults;
	}
	
	public void setCheckResult(float[][] checkResult, int setIndex) {		
		this.dbCheckResults[setIndex] = checkResult;
		addToFinishTestLength();
	}
	
	public float[][][] getSortedResults() {
		return sortedResults;
	}
	
	public void setSortResult(float[][] sortResult, int setIndex) {	
		
		Debug.debug(debud_level_DEBUG, "VoiceCheckModel setSortResult: "+ setIndex);
		this.sortedResults[setIndex] = sortResult;
	}
	
	public int getBaseTestsCounter() {
		return baseTestsCounter;
	}

	public int getFinishedTestsCounter() {
		return finishedTestsCounter;
	}

	public void addToFinishTestLength() {		
		finishedTestsCounter++;
	}
	
	public void initArrays() {
		
		this.dbCheckResults = new float[baseTestsCounter][][];
		this.sortedResults = new float[baseTestsCounter][][];
	}
}
