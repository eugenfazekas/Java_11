package audio.clock;

import javax.sound.sampled.AudioFormat;

public class RawAudio {

	private byte[] data;
	private AudioFormat audioFormat;
		
	public RawAudio(byte[] data, AudioFormat audioFormat) {
		this.data = data;
		this.audioFormat = audioFormat;
	}
	
	public byte[] getData() {
		return data;
	}
	
	public AudioFormat getAudioFormat() {
		return audioFormat;
	}
	
	
}
