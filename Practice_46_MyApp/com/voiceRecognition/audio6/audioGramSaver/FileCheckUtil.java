package com.voiceRecognition.audio6.audioGramSaver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import com.voiceRecognition.audio0.main.VoiceRecognitionAppSetup;
import com.voiceRecognition.audio8.util.Debug;

public class FileCheckUtil {

	public static String[] getFilesOrFoldersList(String path) {
		
	    File directoryPath = new File(path);
	     
	    	return  removeEmptyDirectoriesFromList(directoryPath.list());
	}
	
	public static boolean  buildAndCheckDirectoryPath(String path, String... audioTypes) {
			
		boolean check = pathExist(path);
		
		for(int i = 0 ; i <audioTypes.length; i++ ) {
			
			if(!pathExist(path+"/" +audioTypes[i]))
				check = false;
		}
		
		Debug.debug(2,"FileCheckUtil Directory check exist: "+ check);
		
		if (check == false) {
			
			Debug.debug(5,"FileCheckUtil Drirectory check failed! ");
			
			buildDirectory(path);
			
			for(int i = 0; i < audioTypes.length; i++) {
				
				buildDirectory(path+"/"+audioTypes[i]);
				
				check = true;
			}
		}	
			return check;
	}
	
	public static void buildDirectory(String path) {

		try {
			
			Files.createDirectories(Paths.get(path));
			DeleteLastAnalysisBuilder.addPathToDirectorySavedList(path);
			
		} catch (IOException e) {
			
			// TODO Auto-generated catch block
			Debug.debug(2,"FileCheckUtil Directory not have been created!: "+path);
			e.printStackTrace();
		}	
		
		Debug.debug(2,"FileCheckUtil buildDirectory Created! "+path);
	}
		
	public static boolean pathExist(String path) {
		
		boolean exist = false;
		File f = new File(path);
		
		if (f.exists() && f.isDirectory())
			exist = true;
		
		Debug.debug(5,"FileCheckUtil getBaseAudioPath pathExist: "+exist);
		
			return exist;
	}
	
	public static boolean fileExist(String filePath) {
		
		Path path = Paths.get(filePath);
		
			return Files.exists(path);
	}
	
	public static int countExistingFiles(String path) {
		Debug.debug(5,"FileCheckUtil countExistingFiles path " + path);
		int count =	new File(path).list().length;
		
			return count;
	}
	
	public static String[] removeEmptyDirectoriesFromList(String[] nonFilteredDirs) {
		
		int counter = 0;
		String[] nonEmptyDirs;
		
		for(int i = 0; i < nonFilteredDirs.length; i++) {
			if(countExistingFiles(VoiceRecognitionAppSetup.BASE_AUDIO_PATH+"spektrum/"+nonFilteredDirs[i])>0)
				counter++;

		}
		
		nonEmptyDirs = new String[counter];
		counter = 0;
		
		for(int i = 0; i < nonFilteredDirs.length; i++) {
			if(countExistingFiles(VoiceRecognitionAppSetup.BASE_AUDIO_PATH+"spektrum/"+nonFilteredDirs[i])>0)
				nonEmptyDirs[counter++] = nonFilteredDirs[i];
		}
		
		Debug.debug(3,"FileCheckUtil removeEmptyDirectories Array Length: "+nonEmptyDirs.length
				+" "+Arrays.toString(nonEmptyDirs));
		
			return nonEmptyDirs;
	}
}
