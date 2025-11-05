package audio.clock;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.*;

public class Player implements LineListener ,Runnable{

	boolean isPlaybackCompleted = false;
	private Thread thread;
	private int times;
	private AudioInputStream audioStream;
	private int volumePercent;
	
	

	public Player( AudioInputStream stream,int times, int volumePercent) {
		this.times = times;
		this.volumePercent = volumePercent;
		this.audioStream = stream;
		this.thread = new Thread(this);
		thread.start();
		System.out.println("1. Player stream hascode: "+stream.hashCode() + " audioStream hashcode "+ audioStream.hashCode()  );
	}
	
	@Override
	public void run() {
		play(audioStream,times,volumePercent);
		thread = null;
	}

	@Override
    public void update(LineEvent event) {
        if (LineEvent.Type.START == event.getType()) {
            System.out.println("Playback started.");
            isPlaybackCompleted = false;
        } else if (LineEvent.Type.STOP == event.getType()) {
            isPlaybackCompleted = true;
            System.out.println("Playback completed.");
        }
    }
	
	public void play (AudioInputStream stream, int times, int volumePercent ) {
		
		AudioInputStream audioStream = stream;
				
		try {
            // Elindítjuk a lejátszást
			Clip clip = AudioSystem.getClip();
            clip.open(stream);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue((gainControl.getMaximum()*volumePercent)/100);
       
            //System.out.println("clip.getFrameLength() "+clip.getFrameLength()+" clip.getFramePosition() "+clip.getFramePosition()+" clip.getMicrosecondLength() "+clip.getMicrosecondLength()+" clip.getBufferSize() "+clip.getBufferSize());
            //System.out.println("audioStream.available() "+audioStream.reset());
            clip.start();
            clip.loop(times-1);

            Thread.sleep((clip.getMicrosecondLength() / 1000)*(times+1)); 
            
            clip.close();
		    audioStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}


