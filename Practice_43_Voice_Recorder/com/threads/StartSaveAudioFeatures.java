package com.threads;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

import com.library.audio.audioGramInitializer.AudioGramConstructor;
import com.library.util.Debug;

public class StartSaveAudioFeatures implements Runnable{

	private Thread thread;
	private String speechName;
	private AudioInputStream recordedStream;
	private byte[] byteStream;
	private int[] intStream;
	private AudioFormat audioFormat;
    
	   public StartSaveAudioFeatures( AudioInputStream recordedStream, String speechName) {
		   this.recordedStream = recordedStream;
		   this.speechName = speechName;
		   start();
	   }
	   
	   public StartSaveAudioFeatures(byte[] byteStream,String speechName,AudioFormat audioFormat) {
		   this.speechName = speechName;
		   this.byteStream = byteStream;
		   this.audioFormat = audioFormat;
		   start();
	   }
	   
	   public StartSaveAudioFeatures(int[] intStream,String speechName,AudioFormat audioFormat) {
		   this.speechName = speechName;
		   this.intStream = intStream;
		   this.audioFormat = audioFormat;
		   start();
	   }   

	public void start() {
	        thread = new Thread(this);
	        thread.setName("Save Audio Features Thread");
	        thread.start();
	    }

	    public void stop() {
	        thread = null;
	    }
	    
	@Override
	public void run() {
		
		Debug.debug(1,"Staring SaveAudioFeatures Thread");
		
		if(byteStream != null)
			saveByteStream();
		
		if(intStream != null)
			saveIntStream();
		
		if(recordedStream != null)
			saveAudioStream();
		
		stop();
	}
	
	private void saveAudioStream() {
		
		Debug.debug(2,"SaveAudioFeatures saveAudioStream recordedStream.length: "+recordedStream.getFrameLength());		
		AudioGramConstructor.buildMultiAudioGrammFromInputStream(recordedStream, speechName);
	} 
	
	private void saveByteStream() {
		
		Debug.debug(2,"SaveAudioFeatures saveByteStream byteStream.length: "+byteStream.length);				
		AudioGramConstructor.buildMultiAudioGrammFromInputStream(byteStream, speechName, audioFormat);
	} 
	
	private void saveIntStream() {
		
		Debug.debug(2,"SaveAudioFeatures saveIntStream intStream.length: "+intStream.length);				
		AudioGramConstructor.buildMultiAudioGrammFromIntStream(intStream, speechName, audioFormat);
	}
}
