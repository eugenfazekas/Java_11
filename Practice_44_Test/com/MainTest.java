package com;

import java.util.ArrayList;
import java.util.List;


class Sequence {
	
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


public class MainTest {
	
	public static void main(String[] args) {

		List<Sequence> sequence = new ArrayList<>();
		sequence.add(new Sequence(10000,300, 500));
		
		SoundBuilder.mainBuilder(sequence);
	}
}

