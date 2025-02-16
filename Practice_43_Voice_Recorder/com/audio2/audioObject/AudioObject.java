package com.audio2.audioObject;

import javax.sound.sampled.AudioFormat;

import com.audio0.main.AppSetup;
import com.audio8.util.Debug;

public class AudioObject {

	private static int counter = 0;	
	private int id;
	private String nextStage;
	private String speechName;
	private byte[] byteStream;
	private int[] intStream;
	private int[] waveStream;
	private int averageAmplitude;
	private AudioFormat audioFormat;
	private boolean build = false;
	private int[] amplitudeWaveMap;
	private int[] frequencyWaveMap;
	private int[] mySpektrogramMap;
	private int[] spektrogramMap;
	private String[] rawAudioData;
	private String[] sequenceStringArray;
	private String[] voiceRecognitionPointsArray;
	private String voiceRecognitionSlopesArray;
	private int[] voiceReconitionCheckPointsArray;
	private int[][] voiceReconitionCheckSlopesArray;
	private int[] bestPointsDBMatchArray;
			
	public AudioObject(byte[] byteStream, int[] intStream, int[] waveStream,
			String speechName, AudioFormat audioFormat, int averageAmplitude) {
		
		this.id = counter++;
		this.speechName = speechName;
		this.byteStream = byteStream;
		this.intStream = intStream;
		this.waveStream = waveStream;
		this.audioFormat = audioFormat;
		this.averageAmplitude = averageAmplitude;
		this.build = true;
		this.nextStage = "";

		if(AppSetup.preTrim)
			this.nextStage = "trim";
		
		Debug.debug(1,toString());
	}

	public int getId() {
		return id;
	}
		
	public String getNextStage() {
		return nextStage;
	}

	public AudioObject setNextStage(String nextStage) {
		this.nextStage = nextStage;
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
	
	public int[] getWaveStream() {
		return waveStream;
	}
	
	public AudioObject setWaveStream(int[] waveStream) {
		this.waveStream = waveStream;
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
		
	public int[] getSpektrogramMap() {
		return spektrogramMap;
	}

	public AudioObject setSpektrogramMap(int[] spektrogramMap) {
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
	
	public String[] getVoiceRecognitionPointsArray() {
		return voiceRecognitionPointsArray;
	}
	
	public AudioObject setVoiceRecognitionPointsArray(String[] voiceRecognitionPointsArray) {
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
		
	public int[] getVoiceReconitionCheckPointsArray() {
		return voiceReconitionCheckPointsArray;
	}

	public AudioObject setVoiceReconitionCheckPointsArray(
			int[] voiceReconitionCheckPointsArray) {
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

	public int[] getPointsBestDBMatchArray() {
		return bestPointsDBMatchArray;
	}
	
	public AudioObject setPointsBestDBMatchArray(int[] bestDBMatchArray) {
		this.bestPointsDBMatchArray = bestDBMatchArray;
		return this;
	}

	@Override
	public String toString() {
		return "AudioObject [id=" + id + ", nextStage=" + nextStage + ", speechName=" + speechName + ", build=" + build
				+ "]";
	}	
}
