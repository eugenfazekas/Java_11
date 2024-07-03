package com.sound.util;

import java.io.IOException;
import java.nio.file.Files;

import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import java.awt.image.BufferedImage;
import java.io.File;

public class PathUtil {
	
	

	public static String inputAudioFileStreamType = ".wav"; 
	public static String fileName = "";
	
	public static boolean  buildAndCheckDirectoryPath(String speechName, String... audioTypes) {
		
		boolean check = pathExist(getBaseAudioPath()+"spektrum/"+speechName);
		
		if (check == false) {
			
			buildDirectory(speechName);
			
			for(int i = 0; i < audioTypes.length; i++) {
				
				buildDirectory(speechName+"/"+audioTypes[i]);
				check = true;
			}
		}	
			return check;
	}
	
	private static void buildDirectory(String speechName) {
		
		try {
			Files.createDirectories(Paths.get(getBaseAudioPath()+"spektrum/"+speechName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Directory not have been created!: "+speechName);
			e.printStackTrace();
		}		
		System.out.println("Directory Created! "+speechName);
	}
	
	
	public static String getBaseAudioPath() {
		return "src/main/resources/static/audio/";
	}
	
	public static boolean pathExist(String path) {
		
		boolean exist = false;
		File f = new File(path);
		if (f.exists() && f.isDirectory())
			exist = true;
		
		System.out.println("pathExist: "+exist);
			return exist;
	}
	
	public static void addImageFileToLibrary(BufferedImage image, String speechName, String audioGrammType) {
		
		System.out.println("called "+getBaseAudioPath()+"spektrum/"+speechName+"/"+audioGrammType);

		int count =	new File(getBaseAudioPath()+"spektrum/"+speechName+"/"+audioGrammType).list().length;
		fileName = getBaseAudioPath()+"spektrum/"+speechName+"/"+audioGrammType+"/"+speechName+"-"+audioGrammType+"-"+count;
		System.out.println("addImageFileToLibrary filename "+fileName +" count: "+count+" path "+getBaseAudioPath()+speechName+"/"+audioGrammType+"/"+speechName+"-"+audioGrammType+"-"+count);
		
        File outputImage = new File(fileName+".png");
        try {
			ImageIO.write(image, "png", outputImage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static boolean addWaveFileToLibrary(String speechName, AudioFileFormat.Type fileType, AudioInputStream audioInputStream) {
		
		String path = System.getProperty("user.dir")+"/"+PathUtil.getBaseAudioPath()+"spektrum/"+speechName+"/wave/";
		System.out.println(path+speechName+"-"+fileType+"-"+5);
		int count =	new File(path).list().length;
		fileName = path +speechName+"-"+fileType+"-"+count;
		
        System.out.println("Saving...");
        if (null == speechName || null == fileType || audioInputStream == null) {
            return false;
        }
        File myFile = new File(fileName + "." + fileType.getExtension());
        try {
            audioInputStream.reset();
        } catch (Exception e) {
            return false;
        }
        int i = 0;
        while (myFile.exists()) {
            String temp = "" + i + myFile.getName();
            myFile = new File(temp);
        }
        try {
            AudioSystem.write(audioInputStream, fileType, myFile);
        } catch (Exception ex) {
            return false;
        }
        System.out.println("Saved " + myFile.getAbsolutePath());
        return true;
    }
}
