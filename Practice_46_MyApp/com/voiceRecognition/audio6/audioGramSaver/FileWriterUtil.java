package com.voiceRecognition.audio6.audioGramSaver;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import com.voiceRecognition.audio8.util.Debug;

public class FileWriterUtil {
	
	private static int counter;
	
	private static int debug_level_INFO = 5;
	
	public static void writeImageFile(BufferedImage image,String fileName) {
		
        File outputImage = new File(fileName+".png");
        
        try {
			ImageIO.write(image, "png", outputImage);
			DeleteLastAnalysisBuilder.addPathToFileSavedList(fileName+".png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeWavFile(String fileName, AudioInputStream audioInputStream ) {
		
        File myFile = new File(fileName+".wav");
        
        try {
			AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, myFile);
			DeleteLastAnalysisBuilder.addPathToFileSavedList(fileName+".wav");
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        Debug.debug(debug_level_INFO,"FileUtil Saved " + myFile.getAbsolutePath());
	}
	
	public static void fileWriter(byte[][] byteData,String[] stringData, String filePath ) {

	    counter = 0;
	    String newLine = "\r\n";
	    FileOutputStream out = null;
	    
	    try {
	    	
		    out = new FileOutputStream(filePath);
			
		    if(stringData != null) { 				
				
		    	while(counter < stringData.length && stringData[counter] != null) {
		    		
					out.write(stringData[counter].getBytes());
					out.write(newLine.getBytes());
					counter++;			
		    	}
		    }
		    
		    else if(byteData != null && byteData.length == 1) {
		    	
		    	out.write(byteData[0]);
		    	out.close();
		    		    	
			} else {
				
				while(counter < byteData.length) {
					
					out.write(byteData[counter]);
					out.write(newLine.getBytes());
					counter++;
				}
			}			
			
		    out.close();
		    
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		  
	}
}
