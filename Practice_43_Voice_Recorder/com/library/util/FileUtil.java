package com.library.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import com.library.audio.audioGramSaver.SaveMultiAudioFeatures;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class FileUtil {
	
	public static String inputAudioFileStreamType = ".wav"; 
	public static String fileName = "";
	
	public static boolean  buildAndCheckDirectoryPath(String path, String... audioTypes) {
		
		boolean check = pathExist(path);
		Debug.debug(2,"FileUtil Directory check exist: "+ check);
		if (check == false) {
			Debug.debug(5,"FileUtil Drirectory check failed! ");
			buildDirectory(path);
			
			for(int i = 0; i < audioTypes.length; i++) {
				
				buildDirectory(path+"/"+audioTypes[i]);
				check = true;
			}
		}	
			return check;
	}
	
	private static void buildDirectory(String path) {
		
		try {
			Files.createDirectories(Paths.get(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Debug.debug(2,"Directory not have been created!: "+path);
			e.printStackTrace();
		}		
		Debug.debug(2,"FileUtil buildDirectory Created! "+path);
	}
	
	
	public static boolean pathExist(String path) {
		
		boolean exist = false;
		File f = new File(path);
		if (f.exists() && f.isDirectory())
			exist = true;
		
		Debug.debug(4,"FileUtil getBaseAudioPath pathExist: "+exist);
			return exist;
	}
	
	public static boolean fileExist(String filePath) {
		
		Path path = Paths.get(filePath);
		return Files.exists(path);
	}
	
	public static int countExistingFiles(String path) {

		int count =	new File(path).list().length;
		return count;
	}
	
	public static void addImageFileToLibrary(BufferedImage image,String speechName, String path) {
		
		//System.out.println("addImageFileToLibrary Path: " +path );
		int count = countExistingFiles(path);
		fileName = path+"/"+speechName+"-"+count;

	//	Debug.debug("FileUtil addImageFileToLibrary filename "+fileName +" count: "+filesCount+" path "+BASE_AUDIO_PATH+path+"/"+speechName+"-"+audioGrammType+"-"+filesCount,2);
		
        File outputImage = new File(fileName+".png");
        try {
			ImageIO.write(image, "png", outputImage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void addWaveFileToLibrary(String speechName, String path, AudioInputStream audioInputStream) {
		
		int count =	countExistingFiles(path);
		fileName = path +speechName+"-"+ AudioFileFormat.Type.WAVE+"-"+count;

        File myFile = new File(fileName + "." + "wav");
        try {
			AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, myFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Debug.debug(4,"FileUtil Saved " + myFile.getAbsolutePath());
    }
	
	public static void createTextFile(String[] data, String pathWithFileName) {
		  System.out.println("CreateTextFile pathWithFileName:"+pathWithFileName + " data.length:"+ data.length);
		  FileOutputStream out;
		  String line = null;
		  String newLine = "\r\n";
		  int counter=0;
		  
		  try {			  
				out = new FileOutputStream(pathWithFileName);		
				while(counter < data.length && data[counter] != null) {
					//System.out.println("couter: "+ counter+ " data[counter] "+data[counter]  );
					line = counter +". "+data[counter];
					out.write(line.getBytes());
					out.write(newLine.getBytes());
					counter++;
				}			
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	  
	}
	
	public static void buildRawAudioDataTextFile (String[] data,String speechName) {
		
		
		  String path = System.getProperty("user.dir")+"/"+SaveMultiAudioFeatures.BASE_AUDIO_PATH+speechName+"/rawAudioData/";
		  int count =	new File(path).list().length;		  
		  fileName = speechName+"-"+"rawAudioData"+"-"+count+".txt";
		  
		  createTextFile(data, path+fileName);

		  Debug.debug(2,"FileUtil buildRawAudioDataTextFile " + SaveMultiAudioFeatures.BASE_AUDIO_PATH+speechName+"/rawAudioData/"+fileName);

	}
	public static List<String>  buildStringLines(FileInputStream stream) {

		List<String> lines = new ArrayList<>();
		StringBuilder line = new StringBuilder();
	    int ch; 
		        try {
					while ((ch = stream.read()) != -1) {
						if(ch != 10 && ch!=13) {
							line.append((char)ch);
						}
						
						if(ch == 13) {
							lines.add(line.toString());
							line = new StringBuilder();
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		        try {
					stream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 

		        Debug.debug(3,"FileUutil buildStringLines Get 0: "+lines.get(0));
		return lines;
	}
	
	public static FileInputStream buildFileStreamFromFile(String filePath) {
		
		FileInputStream fin = null;
		
		try {
			fin = new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
			return fin;
	}
	
	public static void addStreamToFile( String addLine, String filePath) {
		
		  FileInputStream inputStream = buildFileStreamFromFile(filePath);
		  FileOutputStream out = null;
		  String newLine = "\r\n";
		  
			try {
				byte[] targetArray = new byte[inputStream.available()];			  
				inputStream.read(targetArray); 
				out = new FileOutputStream(filePath);
				out.write(targetArray);
				out.write(newLine.getBytes());
				out.write(addLine.getBytes());
				out.write(newLine.getBytes());
				out.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Debug.debug(3,"FileUtil addStreamToFile addedLine: "+addLine + " to Path: "+filePath);
	}
}
