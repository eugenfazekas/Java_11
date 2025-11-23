package com.voiceRecognition.audio5.audioGramBuilder;

public class AudioGramObject {

	private int posAmplitude;
	private int negAmplitude;
	private int posCounter;
	private int negCounter;
	private int[] samples;
	private int samplesCounter;
	
	public AudioGramObject(int samplesLength) {
		this.posAmplitude = 0;
		this.negAmplitude = 0;
		this.posCounter = 0;
		this.negCounter = 0;
		this.samples = new int[samplesLength];
		this.samplesCounter = 0;
	}
	
	public void addSampleToObject(int sample) {
		
		addSamples(sample);
		
		if(sample > 0) {
			addPosAmplitude(sample);
			addPosCounter();
		}
		
		if(sample <= 0) {
			addNegAmplitude(sample);
			addNegCounter();
		}
	}

	public int getPosAmplitude() {
		return posAmplitude;
	}
	
	private void addPosAmplitude(int posAmplitude) {
		this.posAmplitude += posAmplitude;
	}
	
	public int getNegAmplitude() {
		return negAmplitude;
	}
	
	private void addNegAmplitude(int negAmplitude) {
		this.negAmplitude += Math.abs(negAmplitude);
	}
	
	public int getPosCounter() {
		return posCounter+1;
	}
	
	private void addPosCounter() {
		this.posCounter++;
	}
	
	public int getNegCounter() {
		return negCounter+1;
	}
	
	private void addNegCounter() {
		this.negCounter++;
	}
	
	public int[] getSamples() {
		return samples;
	}
	
	private void addSamples(int samples) {
		this.samples[samplesCounter++] = samples;
	}
	
	public int calculateAVGAmplitude(AudioGramObject first, AudioGramObject middle, AudioGramObject last ) {
		
		int amplitude = (first.getPosAmplitude() / first.getPosCounter()
				+first.getNegAmplitude() / first.getNegCounter()
				+middle.getPosAmplitude()/ middle.getPosCounter()
				+middle.getNegAmplitude()/ middle.getNegCounter()
				+last.getPosAmplitude()  / last.getPosCounter()
				+last.getNegAmplitude()  / last.getNegCounter())/3;
		
		return amplitude;
	}
}
