package com.audio4.audioGramInitializer.trim.sequence;

import javax.sound.sampled.AudioFormat;

public class SequenceFilter {

	private int amplitudeStartLimit;
	private int amplitudeEndLimit;
	private int startFrequencyLowerLimit;
	private int startFrequencyUpperLimit;
	private int endFrequencyLowerLimit;
	private int endFrequencyUpperLimit;
	private int startLengthCheck;
	private int endLengthCheck;
	private  AudioFormat audioFormat;
	
	public SequenceFilter(int amplitudeStartLimit,int amplitudeEndLimit
			,int startFrequencyLowerLimit ,int startFrequencyUpperLimit
			, int endFrequencyLowerLimit, int endFrequencyUpperLimit
			,int startLengthCheck, int endLengthCheck, AudioFormat audioFormat) {
		
		this.audioFormat = audioFormat;
		this.amplitudeStartLimit = amplitudeStartLimit;
		this.amplitudeEndLimit = amplitudeEndLimit;
		this.startFrequencyLowerLimit = startFrequencyLowerLimit;
		this.startFrequencyUpperLimit = startFrequencyUpperLimit;
		this.endFrequencyLowerLimit = endFrequencyLowerLimit;
		this.endFrequencyUpperLimit = endFrequencyUpperLimit;
		this.startLengthCheck = getAmplitudeMatchCounter(startLengthCheck);	
		this.endLengthCheck =  getAmplitudeMatchCounter(endLengthCheck);
	}

	public int getAmplitudeStartLimit() {
		
		return amplitudeStartLimit;
	}

	public int getAmplitudeEndLimit() {
		
		return amplitudeEndLimit;
	}

	public int getStartFrequencyLowerLimit() {
		
		return startFrequencyLowerLimit;
	}

	public int getStartFrequencyUpperLimit() {
		
		return startFrequencyUpperLimit;
	}

	public int getEndFrequencyLowerLimit() {
		
		return endFrequencyLowerLimit;
	}

	public int getEndFrequencyUpperLimit() {
		
		return endFrequencyUpperLimit;
	}

	public int getStartLengthCheck() {
		
		return startLengthCheck;
	}

	public int getEndLengthCheck() {
		
		return endLengthCheck;
	}	
	
	public AudioFormat getAudioFormat() {
		return audioFormat;
	}

	public void setAudioFormat(AudioFormat audioFormat) {
		this.audioFormat = audioFormat;
	}
	
	public  int getAmplitudeMatchCounter(int miliSec ) {

		return (int) ((getAudioFormat().getSampleRate() / 1000) * miliSec);
	}

	@Override
	public String toString() {
		return "SequenceFilter [amplitudeStartLimit=" + amplitudeStartLimit + ", amplitudeEndLimit=" + amplitudeEndLimit
				+ ", startFrequencyLowerLimit=" + startFrequencyLowerLimit + ", startFrequencyUpperLimit="
				+ startFrequencyUpperLimit + ", endFrequencyLowerLimit=" + endFrequencyLowerLimit
				+ ", endFrequencyUpperLimit=" + endFrequencyUpperLimit + ", startLengthCheck=" + startLengthCheck
				+ ", endLengthCheck=" + endLengthCheck + "]";
	}	
}
