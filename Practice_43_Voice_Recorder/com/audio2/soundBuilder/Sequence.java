package com.audio2.soundBuilder;

public class Sequence {

	private int positveAmplitude;
	private int negativeAmplitude;
	private int posSampleCount;
	private int negSampleCount;
	private int frequency;
	private int milisecLength;
	
	public Sequence(int positveAmplitude, int frequency, int milisecLength) {
		this.positveAmplitude = positveAmplitude;
		this.frequency = frequency;
		this.milisecLength = milisecLength;
	}



	public Sequence(int frequency, int positveAmplitude, int posSampleCount, int negativeAmplitude, int negSampleCount) {
		this.frequency = frequency;
		this.positveAmplitude = positveAmplitude;
		this.negativeAmplitude = negativeAmplitude;
		this.posSampleCount = posSampleCount;
		this.negSampleCount = negSampleCount;
	}



	public int getPositveAmplitude() {
		return positveAmplitude;
	}

	public int getNegativeAmplitude() {
		return negativeAmplitude;
	}

	public int getPosSampleCount() {
		return posSampleCount;
	}

	public int getNegSampleCount() {
		return negSampleCount;
	}

	public int getFrequency() {
		return frequency;
	}

	public int getMilisecLength() {
		return milisecLength;
	}



	@Override
	public String toString() {
		return "Sequence [positveAmplitude=" + positveAmplitude + ", negativeAmplitude=" + negativeAmplitude
				+ ", posSampleCount=" + posSampleCount + ", negSampleCount=" + negSampleCount + ", frequency="
				+ frequency + ", milisecLength=" + milisecLength + "]";
	}	
}
