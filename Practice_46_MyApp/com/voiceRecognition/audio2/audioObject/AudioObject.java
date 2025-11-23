package com.voiceRecognition.audio2.audioObject;

import javax.sound.sampled.AudioFormat;

import com.voiceRecognition.audio4.audioGramInitializer.mainInit.AudioAnalysisThread;
import com.voiceRecognition.audio8.util.Debug;

public class AudioObject {

	private static int counter = 0;	
	private int id;
	private ObjectSetup objectSetup;
	private String mainProfile;
	private String[] stages;
	private String nextStage;
	private String speechName;
	private byte[] byteStream;
	private int[] intStream;
	private int averageAmplitude;
	private AudioFormat audioFormat;
	private boolean build = false;
	private int[] amplitudeWaveMap;
	private int[] frequencyWaveMap;
	private int[] mySpektrogramMap;
	private String mixedWaveStreamPointsStringArray;
	private double[][] spektrogramMap;
	private String[] rawAudioData;
	private String[] sequenceStringArray;
	private int[] voiceRecognitionBorders;
	private String voiceRecognitionPointsArray;
	private String voiceRecognitionSlopesArray;
	private String voiceRecognitionAreaArray;
	private String voiceRecognitionScanArray;
	private int[][] voiceReconitionCheckPointsArray;
	private int[][] voiceReconitionCheckSlopesArray;
	private int[][][] voiceReconitionCheckAreaArray;
	private int[] voiceReconitionCheckScanArray;
	
	private static int debug_level_INFO = 1;
	private static int debud_level_DEBUG = 5;
			
	public AudioObject(byte[] byteStream, int[] intStream, int[] ampMap, int[] freqMap,
			String speechName, AudioFormat audioFormat, int averageAmplitude, String mainProfile) {
		
		this.id = counter++;
		this.speechName = speechName;
		this.byteStream = byteStream;
		this.intStream = intStream;
		this.amplitudeWaveMap = ampMap;
		this.frequencyWaveMap = freqMap;
		this.audioFormat = audioFormat;
		this.averageAmplitude = averageAmplitude;
		this.build = true;
		this.mainProfile = mainProfile;
		this.objectSetup = new ObjectSetup();
		this.stages = StageManagement.buildStages(objectSetup , this.mainProfile);
		this.nextStage = this.stages[0];

		Debug.debug(debug_level_INFO,toString());
	}

	public int getId() {
		return id;
	}
	
	public ObjectSetup getObjectSetup() {
		return objectSetup;
	}

	public AudioObject setObjectSetup(ObjectSetup objectSetup) {
		this.objectSetup = objectSetup;
		return this;
	}

	public String getMainProfile() {
		return mainProfile;
	}
	
	public AudioObject setMainProfile(String mainProfile) {
		this.mainProfile = mainProfile;
		return this;
	}
	
	public String[] getStages() {
		return stages;
	}

	public AudioObject setStages(String[] stages) {
		this.stages = stages;
		return this;
	}

	public String getNextStage() {
		return nextStage;
	}

	public AudioObject setManualNextStage(String nextStage) {
		this.nextStage = nextStage;
		return this;
	}
	
	public AudioObject setNextStage() {
		
		for(int i = 0; i < getStages().length; i++)
			
			if(getNextStage().equals(getStages()[i])) {
				setManualNextStage(getStages()[i+1]);
				if(getNextStage().equals("end"))
					AudioAnalysisThread.removeAudioObjct(getId());
				break;			
			}	
		
		Debug.debug(debud_level_DEBUG, "AudioObject id: "+getId() + ", next Stage: "+ getNextStage());
		return this;
	}

	public String getSpeechName() {
		return speechName;
	}

	public AudioObject setSpeechName(String speechName) {
		this.speechName = speechName;
		return this;
	}

	public byte[] getByteStream() {
		return byteStream;
	}
	
	public AudioObject setByteStream(byte[] byteStream) {
		this.byteStream = byteStream;
		return this;
	}
	
	public int[] getIntStream() {
		return intStream;
	}
	
	public AudioObject setIntStream(int[] intStream) {
		this.intStream = intStream;
		return this;
	}

	public int getAverageAmplitude() {
		return averageAmplitude;
	}
	
	public AudioObject setAverageAmplitude(int averageAmplitude) {
		this.averageAmplitude = averageAmplitude;
		return this;
	}
	
	public AudioFormat getAudioFormat() {
		return audioFormat;
	}
	
	public AudioObject setAudioFormat(AudioFormat audioFormat) {
		this.audioFormat = audioFormat;
		return this;
	}
	
	public boolean getBuild() {
		return build;
	}
	
	public AudioObject setBuild(boolean build) {
		this.build = build;
		return this;
	}
	
	public int[] getAmplitudeWaveMap() {
		return amplitudeWaveMap;
	}
	
	public AudioObject setAmplitudeWaveMap(int[] amplitudeWaveMap) {
		this.amplitudeWaveMap = amplitudeWaveMap;
		return this;
	}
	
	public int[] getFrequencyWaveMap() {
		return frequencyWaveMap;
	}
	
	public AudioObject setFrequencyWaveMap(int[] frequencyWaveMap) {
		this.frequencyWaveMap = frequencyWaveMap;
		return this;
	}
	
	public int[] getMySpektrogramMap() {
		return mySpektrogramMap;
	}
	
	public AudioObject setMySpektrogramMap(int[] mySpektrogramMap) {
		this.mySpektrogramMap = mySpektrogramMap;
		return this;
	}
		
	public String getMixedWaveStreamPointsStringArray() {
		return mixedWaveStreamPointsStringArray;
	}

	public AudioObject setMixedWaveStreamPointsStringArray(String mixedWaveStreamPointsStringArray) {
		this.mixedWaveStreamPointsStringArray = mixedWaveStreamPointsStringArray;
		return this;
	}

	public double[][] getSpektrogramMap() {
		return spektrogramMap;
	}

	public AudioObject setSpektrogramMap(double[][] spektrogramMap) {
		this.spektrogramMap = spektrogramMap;
		return this;
	}

	public String[] getRawAudioData() {
		return rawAudioData;
	}
	
	public AudioObject setRawAudioData(String[] rawAudioData) {
		this.rawAudioData = rawAudioData;
		return this;
	}
	
	public String[] getSequenceStringArray() {
		return sequenceStringArray;
	}
	
	public AudioObject setSequenceStringArray(String[] buildSequenceStringArray) {
		this.sequenceStringArray = buildSequenceStringArray;
		return this;
	}
		
	public int[] getVoiceRecognitionBorders() {
		return voiceRecognitionBorders;
	}

	public void setVoiceRecognitionBorders(int[] voiceRecognitionBorders) {
		this.voiceRecognitionBorders = voiceRecognitionBorders;
	}

	public String getVoiceRecognitionPointsArray() {
		return voiceRecognitionPointsArray;
	}
	
	public AudioObject setVoiceRecognitionPointsArray(String voiceRecognitionPointsArray) {
		this.voiceRecognitionPointsArray = voiceRecognitionPointsArray;
		return this;
	}
	
	public String getVoiceRecognitionSlopesArray() {
		return voiceRecognitionSlopesArray;
	}
	
	public AudioObject setVoiceRecognitionSlopesArray(String voiceRecognitionSlopesArray) {
		this.voiceRecognitionSlopesArray = voiceRecognitionSlopesArray;
		return this;
	}
		
	public int[][] getVoiceReconitionCheckPointsArray() {
		return voiceReconitionCheckPointsArray;
	}

	public AudioObject setVoiceReconitionCheckPointsArray(
			int[][] voiceReconitionCheckPointsArray) {
		this.voiceReconitionCheckPointsArray = voiceReconitionCheckPointsArray;
		return this;
	}

	public int[][] getVoiceReconitionCheckSlopesArray() {
		return voiceReconitionCheckSlopesArray;
	}

	public AudioObject setVoiceReconitionCheckSlopesArray(int[][] voiceReconitionCheckSlopesArray) {
		this.voiceReconitionCheckSlopesArray = voiceReconitionCheckSlopesArray;
		return this;
	}

	public String getVoiceRecognitionAreaArray() {
		return voiceRecognitionAreaArray;
	}

	public void setVoiceRecognitionAreaArray(String voiceRecognitionAreaArray) {
		this.voiceRecognitionAreaArray = voiceRecognitionAreaArray;
	}
	
	public String getVoiceRecognitionScanArray() {
		return voiceRecognitionScanArray;
	}

	public AudioObject setVoiceRecognitionScanArray(String voiceRecognitionScanArray) {
		this.voiceRecognitionScanArray = voiceRecognitionScanArray;
		return this;
	}

	public int[][][] getVoiceReconitionCheckAreaArray() {
		return voiceReconitionCheckAreaArray;
	}

	public void setVoiceReconitionCheckAreaArray(int[][][] voiceReconitionCheckAreaArray) {
		this.voiceReconitionCheckAreaArray = voiceReconitionCheckAreaArray;
	}

	public int[] getVoiceReconitionCheckScanArray() {
		return voiceReconitionCheckScanArray;
	}

	public AudioObject setVoiceReconitionCheckScanArray(int[] voiceReconitionCheckScanArray) {
		this.voiceReconitionCheckScanArray = voiceReconitionCheckScanArray;
		return this;
	}

	@Override
	public String toString() {
		return "AudioObject [id=" + id + ", nextStage=" + nextStage + ", speechName=" + speechName 
				+ ", build=" + build+ ", averageAmplitude " +averageAmplitude+ "]";
	}	
}
