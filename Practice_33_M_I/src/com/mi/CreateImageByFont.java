package com.mi;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CreateImageByFont {
	
	public CreateImageByFont () {

	}
	
	public static void createBufferedImagefont(char inputChar,String font,String path) {
		
		BufferedImage bufferedImage = new BufferedImage(28, 28, BufferedImage.TYPE_BYTE_BINARY);
		String ch = Character.toString(inputChar);
		Graphics g = bufferedImage.createGraphics();
		g.setFont(new Font(font, Font.PLAIN, 25));
		g.drawString(ch,3,21);
		g.dispose();
		
		File file = new File(path+"\\"+font+"\\"+(String.format("\\U+%04X", (int) inputChar)+".png"));
        try {
			ImageIO.write(bufferedImage, "png", file);
			System.out.println("Image created!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
