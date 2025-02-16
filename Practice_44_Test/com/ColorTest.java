package com;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class ColorTest {
	
	static BufferedImage image;
	public static void main(String[]  args) {
		buildImage();
	
	}
	
	public static void buildImage() {
		
		image = new BufferedImage(50,50,BufferedImage.TYPE_INT_ARGB);
		
	    for (int x = 0; x < image.getWidth(); x++) {
	    	
	    	for (int  y = image.getHeight()-1; y > 0; y--) {
	    		image.setRGB(x, y, colors[x].getRGB());
	    	}
	    	
	    }
	  
	    String fileName = "Test";
        File outputImage = new File(fileName+".png");
        try {
			ImageIO.write(image, "png", outputImage);
		} catch (IOException e) {
		}
	}
	
	static Color[] colors = new Color[] {
			new Color(215, 0, 0),
			new Color(225, 0, 0),
			new Color(235, 0, 0),
			new Color(245, 0, 0),
			new Color(255, 0, 0),
			new Color(255, 26, 26),
			new Color(255, 41, 41),
			new Color(255, 35, 72),
			new Color(255, 26, 107),
			new Color(255, 35, 118),
			new Color(255, 26, 140),
			new Color(255, 51, 153),
			new Color(255, 75, 175),
			new Color(255, 95, 190),
			new Color(255, 105, 198),
			new Color(255, 123, 200),
			new Color(255, 150, 190),
			new Color(255, 170, 160),	
			new Color(255, 190, 120),
			new Color(255, 205, 100),
			new Color(255, 215, 80),
			new Color(255, 225, 60),
			new Color(255, 235, 40),
			new Color(255, 245, 30),
			new Color(255, 255, 20),
			new Color(255, 255, 0),
			new Color(255, 255, 15),
			new Color(255, 255, 26),
			new Color(255, 255, 51),
			new Color(245, 255, 77),
			new Color(235, 255, 82),
			new Color(225, 255, 82),
			new Color(210, 255, 77),
			new Color(204, 255, 51), 
			new Color(184, 255, 36),
			new Color(160, 255, 30),
			new Color(140, 255, 82),
			new Color(121, 255, 77),
			new Color(102, 255, 51),
			new Color(75, 250, 26),
			new Color(60, 245, 0),
			new Color(57, 235, 0),	
			new Color(51, 215, 0),
			new Color(45, 195, 0),	
			new Color(38, 175, 0),	
			new Color(32, 155, 0),	
			new Color(26, 135, 0),
			new Color(26, 115, 0),
			new Color(26, 105, 0),
			new Color(26, 95, 0)
	};
}


	    
