package com.mi;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Color;
import javax.imageio.ImageIO;

public class CreateImageByPixel {

	// Create buffered image object


	
	public static void createImage() {
		
		Color myWhite = new Color(255, 0, 0); // Color white
		int rgb = myWhite.getRGB();
		
		System.out.println("Color.WHITE.getRGB(): "+Color.GREEN.getRGB());
		System.out.println("Color.black.getRGB(): "+Color.black.getRGB());
		
		BufferedImage bufferedImage = new BufferedImage(28, 28, BufferedImage.TYPE_BYTE_BINARY);
		
		bufferedImage.setRGB(0, 0, Color.white.getRGB());
		bufferedImage.setRGB(1, 1,Color.white.getRGB());
		bufferedImage.setRGB(2, 2, Color.white.getRGB());
		bufferedImage.setRGB(3, 3, Color.white.getRGB());
		
		File file = new File("test.png");
        try {
			ImageIO.write(bufferedImage, "png", file);
			System.out.println("Image created!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
