package com.library.audio.audioGramSaver;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.library.audio.audioGramInitializer.AudioGramInitializer;
import com.library.util.AudioBuilderUtil;
import com.library.util.Debug;

public class BuidAudioImage {


	public static BufferedImage audioGram; 

	
	 
	public static BufferedImage buildImage(int[] map,int width, int height) {
		
		if(!AudioGramInitializer.build) return null;
		
		   Debug.debug(2,"AudioGramSaver buildImage getRoundToOneHundred(waveMap.length) "+AudioBuilderUtil.getRoundTo(20,width)+ " waveMap.length "+map.length+ " height: "+height);
		   audioGram = new BufferedImage(AudioBuilderUtil.getRoundTo(20,width),height, BufferedImage.TYPE_INT_ARGB);	  
		    for (int x = 0; x < audioGram.getWidth(); x++) {
		    	
		    	for (int  y = audioGram.getHeight(); y > 0; y--) {
		    		
		    		if(x < map.length && y <= map[x]  ) {
		    			audioGram.setRGB(x, Math.abs(y-audioGram.getHeight()), new Color(0,0,0).getRGB());
		    			Debug.debug(5,"buildImage x: "+x + " waveMap[x] "+map[x]+ " waveMap.length: "+map.length);
		    		}
		    	}
		    }	
		    return audioGram;
	}
	
	   public static BufferedImage saveSpectrogramImage() {
		   	   
		   Debug.debug(2,"AudioGramSaver saveSpectrogramImage. ");
		   if(!AudioGramInitializer.build) return null;
	       int width = SaveMultiAudioFeatures.spectrogram[0].length;
	       int height = SaveMultiAudioFeatures.spectrogram.length;
	       audioGram = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	       
	       // Az adatok átalakítása és a kép elkészítése
	       double maxMagnitude = AudioBuilderUtil.getMaxMagnitude(SaveMultiAudioFeatures.spectrogram);
	       
	       for (int y = 0; y < height; y++) {
	           for (int x = 0; x < width; x++) {
	               float magnitude = (float) (SaveMultiAudioFeatures.spectrogram[y][x] / maxMagnitude);
	               int colorValue = (255 << 24) | ((int) (magnitude * 255) << 16) | ((int) (magnitude * 255) << 8) | (int) (magnitude * 255);
	               audioGram.setRGB(x, y, colorValue);
	           }
	       }
	       return audioGram ;
	   }
}
