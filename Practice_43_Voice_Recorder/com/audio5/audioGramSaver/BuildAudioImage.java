package com.audio5.audioGramSaver;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.audio3.audioGramInitializer.AudioAnalysisInitializer;
import com.audio4.audioGramBuilder.FrequencyDetails;
import com.audio4.audioGramBuilder.SpektroGramBuilder;
import com.audio8.util.AudioBuilderUtil;
import com.audio8.util.Debug;

public class BuildAudioImage {


	public static BufferedImage audioGram; 
	static int color1;
	static int color2;
	static int color3;
	static int color4;
	static int color5;
	static int color6;
	static int color7;
	static int color8;
 
	public static BufferedImage buildImage(int[] map,int width, int height) {

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
	
	public static BufferedImage buildMySpectrogramImage(int[] map,int width, int height) {
	/*
		 color1 = (int) (AudioAnalysisInitializer.averageAmplitude * 2 / 15);
		 color2 = (int) (AudioAnalysisInitializer.averageAmplitude * 2 / 8);
		 color3 = (int) (AudioAnalysisInitializer.averageAmplitude * 2 / 4);
		 color4 = (int) (AudioAnalysisInitializer.averageAmplitude * 2 / 2);
		 color5 = (int) (AudioAnalysisInitializer.averageAmplitude * 2 * 0.75);
		 color6 = (int) (AudioAnalysisInitializer.averageAmplitude * 2 );
		 color7 = (int) (AudioAnalysisInitializer.averageAmplitude * 2  * 1.15);
		 color8 = (int) (AudioAnalysisInitializer.averageAmplitude * 2  * 1.3);
	*/	
		 color1 = 10;
		 color2 = 20;
		 color3 = 30;
		 color4 = 43;
		 color5 = 55;
		 color6 = 65;
		 color7 = 75;
		 color8 = 85;
		Debug.debug(2,"BuildAudioImage buildMySpectrogramImage averageAmplitude: "+AudioAnalysisInitializer.averageAmplitude+", color1: " +color1+", color2: " +color2+", color3: " 
		+color3+", color4: " +color4+", color5: " +color5+", color6: " +color6+", color7: " +color7+", color8: " +color8);
		int spektroGramColor = 0;
		int mapedFrequency = 0;
		   Debug.debug(2,"AudioGramSaver buildImage getRoundToOneHundred(waveMap.length) "+AudioBuilderUtil.getRoundTo(20,width)+ " waveMap.length "+map.length+ " height: "+height);
		   audioGram = new BufferedImage(AudioBuilderUtil.getRoundTo(20,width),height, BufferedImage.TYPE_INT_ARGB);	  
		    for (int x = 0; x < map.length-2; x = x+2) {
		    	
		    	spektroGramColor = getMySpektrogramColor(Math.abs(map[x]));
		    	mapedFrequency = FrequencyDetails.getMappedFrequency(map[x+1]);
		    	  Debug.debug(5,"AudioGramSaver buildMySpectrogramImage Amplitude: "+ map[x] + ", frequency: "+map[x+1]);
		    	for (int  y = audioGram.getHeight(); y > 0; y--) {
		    		
		    		if(x < map.length && y <= mapedFrequency) {
		    			audioGram.setRGB(x/2, Math.abs(y-audioGram.getHeight()), spektroGramColor);
		    			Debug.debug(5,"buildImage x: "+x + " waveMap[x] "+map[x]+ " waveMap.length: "+map.length);
		    		}
		    		if(x < map.length && y > mapedFrequency &&  mapedFrequency <  mapedFrequency +10) {
		    			audioGram.setRGB(x/2, Math.abs(y-audioGram.getHeight()), new Color(51,102,255).getRGB());
		    		} 
		    		if(x < map.length && y > mapedFrequency +10) 
		    			audioGram.setRGB(x/2, Math.abs(y-audioGram.getHeight()), new Color(0,0,255).getRGB());
		    	}
		    }	
		    return audioGram;
	}
	
	private static int getMySpektrogramColor(int inputValue ) {

		if(inputValue >= 0 && inputValue < color1)
			return	new Color(0,153,0).getRGB();
		
		if(inputValue >= color1 && inputValue < color2)
			return	new Color(102,204,51).getRGB();
		
		if(inputValue >= color2 && inputValue < color3)
			return	new Color(173,255,47).getRGB();
		
		if(inputValue >= color3 && inputValue < color4)
			return	new Color(255,255,153).getRGB();
		
		if(inputValue >= color4 && inputValue < color5)
			return	new Color(255,216,53).getRGB();
		
		if(inputValue >= color5 && inputValue < color6)
			return	new Color(255,184,77).getRGB();
		
		if(inputValue >= color6 && inputValue < color7)
			return	new Color(255,153,255).getRGB();
		
		if(inputValue >= color7 && inputValue < color8)
			return	new Color(240,94,92).getRGB();
		
		if(inputValue >= color8)
			return	new Color(198,40,40).getRGB();
		
			return new Color(0,0,0).getRGB();
	}
	
	   public static BufferedImage buildSpectrogramImage() {
		   	   
		   Debug.debug(2,"AudioGramSaver saveSpectrogramImage. ");
		   if(!AudioAnalysisInitializer.build) return null;
	       int width = SpektroGramBuilder.spectrogram[0].length;
	       int height = SpektroGramBuilder.spectrogram.length;
	       audioGram = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	       
	       // Az adatok átalakítása és a kép elkészítése
	       double maxMagnitude = AudioBuilderUtil.getMaxMagnitude(SpektroGramBuilder.spectrogram);
	       
	       for (int y = 0; y < height; y++) {
	           for (int x = 0; x < width; x++) {
	               float magnitude = (float) (SpektroGramBuilder.spectrogram[y][x] / maxMagnitude);
	               int colorValue = (255 << 24) | ((int) (magnitude * 255) << 16) | ((int) (magnitude * 255) << 8) | (int) (magnitude * 255);
	               audioGram.setRGB(x, y, colorValue);
	           }
	       }
	       return audioGram ;
	   }
}
