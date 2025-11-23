package com.voiceRecognition.audio2.audioObject;

import com.voiceRecognition.audio0.main.VoiceRecognitionAppSetup;

public class ObjectSetup {

	private boolean amplitudeRefinary;
	private boolean frequencyRefinary;
	
	// PRETRIM
	private boolean preTrim;
	private boolean amplitudeTrim;
	private boolean frequencyTrim;
	private boolean continueWithNoTrim; 
	
	//Data Analysis
	private boolean amplitudeGram;
	private boolean frequencyGram;
	private boolean multiAnalysis;
	private boolean mySpektrogram;
	private boolean spektrogram;
	private boolean buildSequenceArray;
	private boolean rawAudioData;
	private boolean voiceRecognitionData;
	private boolean voiceRecognitionPointsData;
	private boolean voiceRecognitionSlopesData;
	private boolean voiceRecognitionAreaData;
	private boolean wave;
	private boolean deleteCheck;
	private boolean voiceRecognition;
	private boolean voicePointsRecognition;
	private boolean voiceSlopesRecognition;
	private boolean voiceAreaRecognition;
	private boolean save;
		
	public ObjectSetup() {
		buildSetupObject();
	}

	private void buildSetupObject() {
		
		if(VoiceRecognitionAppSetup.amplitudeRefinary)
			setAmplitudeRefinary(true);
		
		if(VoiceRecognitionAppSetup.frequencyRefinary)
			setFrequencyRefinary(true);

		if(VoiceRecognitionAppSetup.preTrim)
			setPreTrim(true);
		
		if(VoiceRecognitionAppSetup.amplitudeTrim)
			setAmplitudeGram(true);
		
		if(VoiceRecognitionAppSetup.frequencyTrim)
			setFrequencyTrim(true);
		
		if(VoiceRecognitionAppSetup.continueWithNoTrim)
			setContinueWithNoTrim(true);
		//Data Analysis
		if(VoiceRecognitionAppSetup.amplitudeGram)
			setAmplitudeGram(true);
		
		if(VoiceRecognitionAppSetup.frequencyGram)
			setFrequencyGram(true);
		
		if(VoiceRecognitionAppSetup.multiAnalysis)
			setMultiAnalysis(true);
		
		if(VoiceRecognitionAppSetup.mySpektrogram)
			setMySpektrogram(true);
		
		if(VoiceRecognitionAppSetup.spektrogram)
			setSpektrogram(true);
		
		if(VoiceRecognitionAppSetup.buildSequenceArray)
			setBuildSequenceArray(true);
			
		if(VoiceRecognitionAppSetup.rawAudioData)
			setRawAudioData(true);
			
		if(VoiceRecognitionAppSetup.voiceRecognitionData)
			setVoiceRecognitionData(true);
			
		if(VoiceRecognitionAppSetup.voiceRecognitionPointsData)
			setVoiceRecognitionPointsData(true);
		
		if(VoiceRecognitionAppSetup.voiceRecognitionSlopesData)
			setVoiceRecognitionSlopesData(true);
		
		if(VoiceRecognitionAppSetup.voiceRecognitionAreaData)
			setVoiceRecognitionAreaData(true);
		
		if(VoiceRecognitionAppSetup.wave)		
			setWave(true);
		
		if(VoiceRecognitionAppSetup.save)		
			setSave(true);
		
		if(VoiceRecognitionAppSetup.deleteCheck)
			setDeleteCheck(true);
			
		if(VoiceRecognitionAppSetup.voiceRecognition)
			setVoiceRecognition(true);
			
		if(VoiceRecognitionAppSetup.voicePointsRecognition)
			setVoicePointsRecognition(true);
		
		if(VoiceRecognitionAppSetup.voiceSlopesRecognition)
			setVoiceSlopesRecognition(true);
			
		if(VoiceRecognitionAppSetup.voiceAreaRecognition)
			setVoiceAreaRecognition(true);
	}
	
	public boolean isAmplitudeRefinary() {
		return amplitudeRefinary;
	}
	public ObjectSetup setAmplitudeRefinary(boolean amplitudeRefinary) {
		this.amplitudeRefinary = amplitudeRefinary;
		return this;
	}
	public boolean isFrequencyRefinary() {
		return frequencyRefinary;
	}
	public ObjectSetup setFrequencyRefinary(boolean frequencyRefinary) {
		this.frequencyRefinary = frequencyRefinary;
		return this;
	}
	public boolean isPreTrim() {
		return preTrim;
	}
	public ObjectSetup setPreTrim(boolean preTrim) {
		this.preTrim = preTrim;
		return this;
	}
	public boolean isAmplitudeTrim() {
		return amplitudeTrim;
	}
	public ObjectSetup setAmplitudeTrim(boolean amplitudeTrim) {
		this.amplitudeTrim = amplitudeTrim;
		return this;
	}
	public boolean isFrequencyTrim() {
		return frequencyTrim;
	}
	public ObjectSetup setFrequencyTrim(boolean frequencyTrim) {
		this.frequencyTrim = frequencyTrim;
		return this;
	}
	public boolean isContinueWithNoTrim() {
		return continueWithNoTrim;
	}
	public ObjectSetup setContinueWithNoTrim(boolean continueWithNoTrim) {
		this.continueWithNoTrim = continueWithNoTrim;
		return this;
	}
	public boolean isAmplitudeGram() {
		return amplitudeGram;
	}
	public ObjectSetup setAmplitudeGram(boolean amplitudeGram) {
		this.amplitudeGram = amplitudeGram;
		return this;
	}
	public boolean isFrequencyGram() {
		return frequencyGram;
	}
	public ObjectSetup setFrequencyGram(boolean frequencyGram) {
		this.frequencyGram = frequencyGram;
		return this;
	}
	public boolean isMultiAnalysis() {
		return multiAnalysis;
	}
	public ObjectSetup setMultiAnalysis(boolean multiAnalysis) {
		this.multiAnalysis = multiAnalysis;
		return this;
	}
	public boolean isMySpektrogram() {
		return mySpektrogram;
	}
	public ObjectSetup setMySpektrogram(boolean mySpektrogram) {
		this.mySpektrogram = mySpektrogram;
		return this;
	}
	public boolean isSpektrogram() {
		return spektrogram;
	}
	public ObjectSetup setSpektrogram(boolean spektrogram) {
		this.spektrogram = spektrogram;
		return this;
	}
	public boolean isBuildSequenceArray() {
		return buildSequenceArray;
	}
	public ObjectSetup setBuildSequenceArray(boolean buildSequenceArray) {
		this.buildSequenceArray = buildSequenceArray;
		return this;
	}
	public boolean isRawAudioData() {
		return rawAudioData;
	}
	public ObjectSetup setRawAudioData(boolean rawAudioData) {
		this.rawAudioData = rawAudioData;
		return this;
	}
	public boolean isVoiceRecognitionData() {
		return voiceRecognitionData;
	}
	public ObjectSetup setVoiceRecognitionData(boolean voiceRecognitionData) {
		this.voiceRecognitionData = voiceRecognitionData;
		return this;
	}
	public boolean isVoiceRecognitionPointsData() {
		return voiceRecognitionPointsData;
	}
	public ObjectSetup setVoiceRecognitionPointsData(boolean voiceRecognitionPointsData) {
		this.voiceRecognitionPointsData = voiceRecognitionPointsData;
		return this;
	}
	public boolean isVoiceRecognitionSlopesData() {
		return voiceRecognitionSlopesData;
	}
	public ObjectSetup setVoiceRecognitionSlopesData(boolean voiceRecognitionSlopesData) {
		this.voiceRecognitionSlopesData = voiceRecognitionSlopesData;
		return this;
	}
	public boolean isVoiceRecognitionAreaData() {
		return voiceRecognitionAreaData;
	}
	public ObjectSetup setVoiceRecognitionAreaData(boolean voiceRecognitionAreaData) {
		this.voiceRecognitionAreaData = voiceRecognitionAreaData;
		return this;
	}
	public boolean isWave() {
		return wave;
	}
	public ObjectSetup setWave(boolean wave) {
		this.wave = wave;
		return this;
	}
	public boolean isDeleteCheck() {
		return deleteCheck;
	}
	public ObjectSetup setDeleteCheck(boolean deleteCheck) {
		this.deleteCheck = deleteCheck;
		return this;
	}
	public boolean isVoiceRecognition() {
		return voiceRecognition;
	}
	public ObjectSetup setVoiceRecognition(boolean voiceRecognition) {
		this.voiceRecognition = voiceRecognition;
		return this;
	}
	public boolean isVoicePointsRecognition() {
		return voicePointsRecognition;
	}
	public ObjectSetup setVoicePointsRecognition(boolean voicePointsRecognition) {
		this.voicePointsRecognition = voicePointsRecognition;
		return this;
	}
	public boolean isVoiceSlopesRecognition() {
		return voiceSlopesRecognition;
	}
	public ObjectSetup setVoiceSlopesRecognition(boolean voiceSlopesRecognition) {
		this.voiceSlopesRecognition = voiceSlopesRecognition;
		return this;
	}
	public boolean isVoiceAreaRecognition() {
		return voiceAreaRecognition;
	}
	public ObjectSetup setVoiceAreaRecognition(boolean voiceAreaRecognition) {
		this.voiceAreaRecognition = voiceAreaRecognition;
		return this;
	}
	public boolean isSave() {
		return save;
	}
	public ObjectSetup setSave(boolean save) {
		this.save = save;
		return this;
	}
}
