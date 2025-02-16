package com.audio5.recognition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class VoiceCheckModel {

	private int id;
	public int [][] pointsResultVerifier;
	public int [][] slopesResultVerifier;
	private int testsCounter;
	public Map<Integer, int[]> evoluatorResult = new ConcurrentHashMap<Integer, int[]>();
	
	public VoiceCheckModel(int Id) {
		
		this.id = Id;
		testsCounter = 0;
	}
	
	public int getId() {
		return id;
	}
	
	public int[][] getPointsResultVerifier() {
		return pointsResultVerifier;
	}

	public void setPointsResultVerifier(int[][] pointsResultVerifier) {
		this.pointsResultVerifier = pointsResultVerifier;
		testsCounter++;
	}

	public int[][] getSlopesResultVerifier() {
		return slopesResultVerifier;
	}

	public void setSlopesResultVerifier(int[][] slopesResultVerifier) {
		this.slopesResultVerifier = slopesResultVerifier;
		testsCounter++;
	}

	public int getTestsCounter() {
		return testsCounter;
	}	
}
