package voiceRecognition.audio6.audioGramSaver;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import voiceRecognition.audio4.audioGramInitializer.mainInit.AudioAnalysisThread;
import voiceRecognition.audio8.util.Debug;

public class BuildAudioImage {

	private static BufferedImage audioGram; 
	
	private static int debug_level_INFO = 5;
	private static int debud_level_DEBUG = 5;
	private static int temp;
	
	private static String testString;

	static BufferedImage buildImage(int[] map,int width, int height) {

	   Debug.debug(debug_level_INFO,"AudioGramSaver buildImage getRoundToOneHundred(waveMap.length) "
			+SaveMultiAudioFeaturesUtil.getRoundTo(20,width)+ " waveMap.length "+map.length
			+ " height: "+height);
	   
	   audioGram = new BufferedImage(
			  SaveMultiAudioFeaturesUtil.getRoundTo(20,width),height, BufferedImage.TYPE_INT_ARGB);	  
	    
	   for (int x = 0; x < audioGram.getWidth(); x++) {
	    	
	    	for (int  y = audioGram.getHeight(); y > 0; y--) {
	    		
	    		if(x < map.length && y <= map[x]  ) {
	    			audioGram.setRGB(x, Math.abs(y-audioGram.getHeight()), new Color(0,161,215).getRGB());
	    			Debug.debug(debud_level_DEBUG,"buildImage x: "+x + " waveMap[x] "+map[x]+ " waveMap.length: "
	    					+map.length);
	    		}
	    	}
	    }	
	    return audioGram;
	}
	
	static BufferedImage buildSpectrogramImage(int id, double[][] spectrogram ) {
	   	   
		Debug.debug(debug_level_INFO,"AudioGramSaver saveSpectrogramImage. ");
		
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
	
	static BufferedImage buildFrquencyMultiImage(int[][] input) {
		
	    audioGram = new BufferedImage(input.length, input[0].length/2, BufferedImage.TYPE_INT_ARGB);
	    
        for (int x = 0; x < input.length; x++) {
        	
            for (int y = 0; y < input[0].length; y = y + 2) {
            	
            	temp = input[x][y] * 20 < 256 ? input[x][y] * 20 : 255;
           	
            	 audioGram.setRGB(x, y/2, new Color(temp,temp,temp).getRGB());
            }
        }
        	writeTestString(input);
        
	    return audioGram;
	}
	
	private static void writeTestString(int[][] input) {
		
        if(debug_level_INFO > 4)
        	return;
		
	    StringBuilder sb = new StringBuilder();
	    sb.append("private static int[][] frequencys = new int[][] {");

        for (int x = 0; x < input.length; x++) {
        	
            for (int y = 0; y < input[0].length; y = y + 2) {
            	
            	temp = input[x][y] * 20 < 256 ? input[x][y] * 20 : 255;           	
            }
            testString = Arrays.toString(input[x]);
    		testString = testString.substring(1, testString.length()-1) ;
    		Debug.debug(debug_level_INFO,"private static int[] testfrequency"+x + " = new int[]{"+testString+"};");
            sb.append(", testfrequency"+x);
        }

        sb.append("};");
        Debug.debug(debug_level_INFO,sb.toString());
	}
}
