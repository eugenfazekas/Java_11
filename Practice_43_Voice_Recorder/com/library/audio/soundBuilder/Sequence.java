package com.library.audio.soundBuilder;

public class Sequence {

	private int amplitude;
	private int frequency;
	private int milisecLength;
	
	public Sequence(int amplitude, int frequency, int milisecLength) {
		this.amplitude = amplitude;
		this.frequency = frequency;
		this.milisecLength = milisecLength;
	}

	public int getAmplitude() {
		return amplitude;
	}

	public int getFrequency() {
		return frequency;
	}

	public int getMilisecLength() {
		return milisecLength;
	}
}
