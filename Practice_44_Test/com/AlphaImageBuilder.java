package com;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class AlphaImageBuilder {
	private static Color color;
	private static int r;
	private static int g;
	private static int b;
	private static float a;
	private static BufferedImage image;
	private static int flagtimes 	;
	private static BufferedImage baseImage;
	static List<BufferedImage> flagImages = new ArrayList<>();
	
	public static void main(String[] args) {
		//mainBuilder("20", "goku.png","roman zaszlo.png","uk.png","magyar zaszlo.png","usa.png");
		mainBuilder("1", "goku.png","magyar zaszlo.png");

	}
	
	public static void mainBuilder(String... args) {
		
		 flagtimes = Integer.parseInt(args[0])	;
		 baseImage = readImage(args[1]);	
		
		buildFlagImageList(args);
				
		buildImage(baseImage,flagImages,flagtimes);
		
		writeImage(image,"D:/flagged.png");
		//writeImage(flagImages.get(0),"D:/flagged.png");
	}
	
	public static void buildImage(BufferedImage baseImage,List<BufferedImage> flagImage, int flagtimes) {
		
		int horizontalCounter = 0;
		int verticalCounter = 0;
		int actualFlagImage = 0;
		int startingFlag = 0;
		
		for(int i = 0; i < baseImage.getHeight(); i++) {
						
			verticalCounter++;
			//System.out.println("i: "+i + " baseImage.getHeight()" +baseImage.getHeight() );
			for( int j = 0 ; j < baseImage.getWidth(); j++) {
				
				if(j == 0) {
					actualFlagImage = startingFlag;
					horizontalCounter = 0;
				}
				if(i % 2 ==0 && j % 2 ==0) {
					int pixel = flagImages.get(actualFlagImage).getRGB(horizontalCounter++,verticalCounter);
					//System.out.println("i: "+i +" j: "+j);
					baseImage.setRGB(j, i, getColorAlpha(pixel));
				}
				
				if(horizontalCounter == flagImages.get(actualFlagImage).getWidth()-1) {
					horizontalCounter = 0;
					j = j + flagImages.get(actualFlagImage++).getWidth();
					if(actualFlagImage > flagImages.size()-1)
						actualFlagImage = 0;
					//System.out.println("Increase actualFlagImage 1.: "+actualFlagImage+" i: "+i +" j: "+j + " flagImages.size(): "+flagImages.size() );
				}
			}
			
			if(verticalCounter == flagImages.get(actualFlagImage).getHeight()-1) {
				verticalCounter = 0;
				i = i + flagImages.get(actualFlagImage).getHeight();
				startingFlag++;
				if(startingFlag > flagImages.size()-1)
					startingFlag = 0;
				//System.out.println("Increase actualFlagImage 2.: "+actualFlagImage+" i: "+i);
				
			}

		}
		
		image = baseImage;
	}
	
	public static int getColorAlpha(int pixel) {
		
		color = new Color(pixel);
		r=color.getRed();
		g=color.getGreen();
		b=color.getBlue();
		a=1;
		
		return color.getRGB(); 
	}
	
	public static BufferedImage  readImage(String path) {
		System.out.println("path: "+path);
		image=null;
		try {
			 image = ImageIO.read(new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Readed Image: "+ path + " Width: "+image.getWidth()+" Height: "+ image.getHeight());
		return image;
	}
	
	public static void writeImage(BufferedImage image, String path) {
				
		try {
			ImageIO.write(image , "png", new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
			
		    BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
		    Graphics2D graphics2D = resizedImage.createGraphics();
		    graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
		    graphics2D.dispose();
		    System.out.println("ResizeImage Image Width: "+resizedImage.getWidth()+" Height: "+ resizedImage.getHeight());
	    return resizedImage;
	}
	
	public static void buildFlagImageList(String[] flags) {

		for(int i = 2; i < flags.length; i++ ) {
			
			image = readImage(flags[i]);
			try {
				image = resizeImage(image, baseImage.getWidth()/flagtimes, baseImage.getHeight()/flagtimes);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			flagImages.add(image);
			System.out.println("added :");
		}
		
	}
}
