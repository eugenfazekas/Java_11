package com.audio8.util.file;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import com.audio6.audioGramSaver.DeleteLastAnalysisBuilder;
import com.audio8.util.Debug;

public class FileUtil {
	
	public static String fileName = "";
	private static byte[][] fileOutputByteArray;	

	public static void addImageFileToLibrary(BufferedImage image,String speechName, String path) {

		int count = FileCheckUtil.countExistingFiles(path);
		fileName = path+"/"+speechName+"-"+count;
		FileWriterUtil.writeImageFile(image, fileName);
	}

	public static void addWaveFileToLibrary(String speechName, String path
			, AudioInputStream audioInputStream) {
		
		int count =	FileCheckUtil.countExistingFiles(path);
		fileName = path +speechName+"-"+ AudioFileFormat.Type.WAVE+"-"+count;
		FileWriterUtil.writeWavFile(fileName, audioInputStream);
    }
	
	public static void createTextFile(String[] data, String pathWithFileName) {
		
		Debug.debug(4,"CreateTextFile pathWithFileName:"+pathWithFileName 
			+ " data.length:"+ data.length);
		DeleteLastAnalysisBuilder.addPathToFileSavedList(pathWithFileName);
		FileWriterUtil.fileWriter(null,data,pathWithFileName);
	}
	
	public static void buildRawAudioDataTextFile (String[] data,String path,String speechName
			,String rawType) {
		
		int count =	FileCheckUtil.countExistingFiles(path);		  
		fileName = rawType+"-"+speechName+"-"+count+".txt";
		createTextFile(data, path+"/"+fileName);
		Debug.debug(2,"FileUtil buildRawAudioDataTextFile "+ path+"/"+rawType+fileName);
	}

	public static void addStreamToFile( String addLine, String filePath) {
		
	    FileInputStream inputStream = FileReaderUtil.buildFileStreamFromFile(filePath);
	    fileOutputByteArray = new byte[2][];
	    byte[] targetArray = null;
	    
		try {
		    targetArray = new byte[inputStream.available()];			  
			inputStream.read(targetArray); 
			Debug.debug(2,"targetArray.length: "+targetArray.length +", inputStream.available(): "
				+inputStream.available());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DeleteLastAnalysisBuilder.addToLastSavedFileArray(filePath, targetArray);
		
		fileOutputByteArray[0] = targetArray;
		fileOutputByteArray[1] = addLine.getBytes();
		
		FileWriterUtil.fileWriter(fileOutputByteArray,null,filePath);
		
		Debug.debug(3,"FileUtil addStreamToFile addedLine: "+addLine + " to Path: "+filePath 
			+", targetArray.length: "+targetArray.length + ", addLine.getBytes().length: "
			+addLine.getBytes().length);					
	}
}
