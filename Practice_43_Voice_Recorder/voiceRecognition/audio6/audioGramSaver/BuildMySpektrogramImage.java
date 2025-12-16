package voiceRecognition.audio6.audioGramSaver;

import java.awt.Color;
import java.awt.image.BufferedImage;

import voiceRecognition.audio8.util.Debug;

public class BuildMySpektrogramImage {
	
	public static BufferedImage audioGram; 
	
	private static int debug_level_INFO = 5;
	private static int debud_level_DEBUG = 5;
	
	public static BufferedImage buildMySpectrogramImage(int[] map,int width, int height) {

		int spektroGramColor = 0;
		
		Debug.debug(debug_level_INFO,
			" BuildMySpektrogramImage buildMySpectrogramImage getRoundToOneHundred(waveMap.length) "
			+ SaveMultiAudioFeaturesUtil.getRoundTo(20,width)+ " waveMap.length "+map.length
			+ " height: "+height);
		
		audioGram = new BufferedImage(SaveMultiAudioFeaturesUtil.getRoundTo(20,width),height
																	,BufferedImage.TYPE_INT_ARGB);	  
		for (int x = 0; x < map.length-2; x = x+2) {
		    	
		    spektroGramColor = getMySpektrogramColor(Math.abs(map[x]));

		    Debug.debug(debud_level_DEBUG,"AudioGramSaver buildMySpectrogramImage Amplitude: "+ map[x]
		    		+ ", frequency: " + map[x+1]);
		    
		    for (int  y = audioGram.getHeight(); y > 0; y--) {
		    		
		    	if(x < map.length && y <= map[x+1]) {
		    		
		    		audioGram.setRGB(x/2, Math.abs(y-audioGram.getHeight()), spektroGramColor);
		    		Debug.debug(debud_level_DEBUG,"buildImage x: "+x + " waveMap[x] "+map[x]+ " waveMap.length: "
		    			+map.length);
		    	}
		    	
		    	if(x < map.length && y > map[x+1] &&  map[x+1] <  map[x+1] +10) 
		    		audioGram.setRGB(x/2, Math.abs(y-audioGram.getHeight()), new Color(51,102,255).getRGB());
		    	    	
		    	if(x < map.length && y > map[x+1] +10) 
		    		audioGram.setRGB(x/2, Math.abs(y-audioGram.getHeight()), new Color(0,0,255).getRGB());
		    	}
		    }	
				return audioGram;
	}
	

	
	private static int getMySpektrogramColor(int inputValue ) {
	
        if(inputValue < 100 )
        	return  colors[99 - inputValue].getRGB();

        else 
        	return colors [0].getRGB();
	}
	
	static Color[] colors = new Color[] {
			
			new Color(215, 0, 0),
			new Color(215, 0, 0),			
			new Color(225, 0, 0),
			new Color(225, 0, 0),				
			new Color(235, 0, 0),
			new Color(235, 0, 0),
			new Color(245, 0, 0),
			new Color(245, 0, 0),
			new Color(255, 0, 0),
			new Color(255, 0, 0),
			new Color(255, 26, 26),
			new Color(255, 26, 26),
			new Color(255, 41, 41),
			new Color(255, 41, 41),
			new Color(255, 35, 72),
			new Color(255, 35, 72),
			new Color(255, 26, 107),
			new Color(255, 26, 107),
			new Color(255, 35, 118),
			new Color(255, 35, 118),
			new Color(255, 26, 140),
			new Color(255, 26, 140),
			new Color(255, 51, 153),
			new Color(255, 51, 153),
			new Color(255, 75, 175),
			new Color(255, 75, 175),
			new Color(255, 95, 190),
			new Color(255, 95, 190),
			new Color(255, 105, 198),
			new Color(255, 105, 198),
			new Color(255, 123, 200),
			new Color(255, 123, 200),
			new Color(255, 150, 190),
			new Color(255, 150, 190),
			new Color(255, 170, 160),	
			new Color(255, 170, 160),	
			new Color(255, 190, 120),
			new Color(255, 190, 120),
			new Color(255, 205, 100),
			new Color(255, 205, 100),
			new Color(255, 215, 80),
			new Color(255, 215, 80),
			new Color(255, 225, 60),
			new Color(255, 225, 60),
			new Color(255, 235, 40),
			new Color(255, 235, 40),
			new Color(255, 245, 30),
			new Color(255, 245, 30),
			new Color(255, 255, 20),
			new Color(255, 255, 20),
			new Color(255, 255, 0),
			new Color(255, 255, 0),
			new Color(255, 255, 15),
			new Color(255, 255, 15),
			new Color(255, 255, 26),
			new Color(255, 255, 26),
			new Color(255, 255, 51),
			new Color(255, 255, 51),
			new Color(245, 255, 77),
			new Color(245, 255, 77),
			new Color(235, 255, 82),
			new Color(235, 255, 82),
			new Color(225, 255, 82),
			new Color(225, 255, 82),
			new Color(210, 255, 77),
			new Color(210, 255, 77),
			new Color(204, 255, 51), 
			new Color(204, 255, 51), 
			new Color(184, 255, 36),
			new Color(184, 255, 36),
			new Color(160, 255, 30),
			new Color(160, 255, 30),
			new Color(140, 255, 82),
			new Color(140, 255, 82),
			new Color(121, 255, 77),
			new Color(121, 255, 77),
			new Color(102, 255, 51),
			new Color(102, 255, 51),
			new Color(75, 250, 26),
			new Color(75, 250, 26),
			new Color(60, 245, 0),
			new Color(60, 245, 0),
			new Color(57, 235, 0),	
			new Color(57, 235, 0),	
			new Color(51, 215, 0),
			new Color(51, 215, 0),
			new Color(45, 195, 0),	
			new Color(45, 195, 0),	
			new Color(38, 175, 0),	
			new Color(38, 175, 0),	
			new Color(32, 155, 0),	
			new Color(32, 155, 0),	
			new Color(26, 135, 0),
			new Color(26, 135, 0),
			new Color(26, 115, 0),
			new Color(26, 115, 0),
			new Color(26, 105, 0),
			new Color(26, 105, 0),
			new Color(26, 95, 0),
			new Color(26, 95, 0)
	};
}
