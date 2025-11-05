package com.audio6.audioGramSaver;

import java.awt.Color;
import java.awt.image.BufferedImage;
import com.audio4.audioGramInitializer.mainInit.AudioAnalysisThread;
import com.audio8.util.Debug;

public class BuildAudioImage {

	private static BufferedImage audioGram; 

	static BufferedImage buildImage(int[] map,int width, int height) {

	   Debug.debug(2,"AudioGramSaver buildImage getRoundToOneHundred(waveMap.length) "
			+SaveMultiAudioFeaturesUtil.getRoundTo(20,width)+ " waveMap.length "+map.length
			+ " height: "+height);
	   
	   audioGram = new BufferedImage(
			  SaveMultiAudioFeaturesUtil.getRoundTo(20,width),height, BufferedImage.TYPE_INT_ARGB);	  
	    
	   for (int x = 0; x < audioGram.getWidth(); x++) {
	    	
	    	for (int  y = audioGram.getHeight(); y > 0; y--) {
	    		
	    		if(x < map.length && y <= map[x]  ) {
	    			audioGram.setRGB(x, Math.abs(y-audioGram.getHeight()), new Color(0,161,215).getRGB());
	    			Debug.debug(5,"buildImage x: "+x + " waveMap[x] "+map[x]+ " waveMap.length: "
	    					+map.length);
	    		}
	    	}
	    }	
	    return audioGram;
	}
	
	static BufferedImage buildSpectrogramImage(int id, double[][] spectrogram ) {
	   	   
		Debug.debug(2,"AudioGramSaver saveSpectrogramImage. ");
		
		if(!AudioAnalysisThread.startedVoiceCheck.get(id).getBuild()) 
			return null;
		
	    int width = spectrogram[0].length;
	    int height = spectrogram.length;
	    double maxMagnitude = SaveMultiAudioFeaturesUtil.getMaxMagnitude(spectrogram);
	    audioGram = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	       
        for (int y = 0; y < height; y++) {
        	
            for (int x = 0; x < width; x++) {
        	   
               float magnitude = (float) (spectrogram[y][x] / maxMagnitude);
               int colorValue = (255 << 24) | ((int) (magnitude * 255) << 16) 
            		   | ((int) (magnitude * 255) << 8) | (int) (magnitude * 255);
               audioGram.setRGB(x, y, colorValue);
            }
        }
	       return audioGram ;
	}
	
	static BufferedImage buildVoiceCompareImage(int[] map1, int[] map2, int height) {
		
		
		Color amp1 = new Color(255,0,0);
		Color freq1 = new Color(0,255,0);
		
		Color amp2 = new Color(0,0,255);
		Color freq2 = new Color(204,102,0);
		


	  
	    int length =  (map1.length / 2 < map2.length /3 ? map1.length /2 : map2.length /3);
	    
	    Debug.debug(5,"AudioGramSaver buildVoiceCompareImage getRoundToOneHundred length: "+length*2 
	    	+ ", map1.length: "+map1.length + ", map2.length: "+map2.length);
	    
	    audioGram = new BufferedImage(length*2,height, BufferedImage.TYPE_INT_ARGB);	 
	    
	    for (int x = 2; x < length*2; x = x+2) {
	    			
	    	Debug.debug(5,"buildImage x1: "+x + " waveMap.length: "+map1.length);
	    	
	    	for (int  y = audioGram.getHeight(); y > 0; y--) {
	    		
	    		if(x < map1.length && ( y > map1[x] && y <= map1[x]+3) ) {
	    			
	    			audioGram.setRGB(x, Math.abs(y-audioGram.getHeight()), amp1.getRGB());

	    		}
	    		
	    		if(x < map1.length && (y > map1[x+1] && y <= map1[x+1]+3) ) {
	    			
	    			audioGram.setRGB(x, Math.abs(y-audioGram.getHeight()), freq1.getRGB());

	    		}
	    	}
	    }
	    
	    for (int x = 3; x < length*3; x=x+3) {
	    	
			Debug.debug(5,"buildImage x2: "+x + " waveMap[x]: "+map2[x]+ ", map2[x+1]: "+map2[x+1]
				+ " waveMap.length: "+map2.length);
			
	    	for (int  y = audioGram.getHeight(); y > 0; y--) {
	    		
	    		if(x < map2.length && ( y > map2[x] && y <= map2[x]+3) ) {
	    			
	    			audioGram.setRGB(x/3*2, Math.abs(y-audioGram.getHeight()), amp2.getRGB());
	    		}
	    		
	    		if(x < map2.length && (y > map2[x+1] && y <= map2[x+1]+3) ) {
	    			
	    			audioGram.setRGB(x/3*2, Math.abs(y-audioGram.getHeight()), freq2.getRGB());
	    		}
	    	}
	    }

	    return audioGram;
	}
}
