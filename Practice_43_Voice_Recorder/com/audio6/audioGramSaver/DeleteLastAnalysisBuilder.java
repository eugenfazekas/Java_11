package com.audio6.audioGramSaver;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.audio8.util.StringArrayUtil;
import com.audio8.util.Debug;
import com.audio8.util.file.FileWriterUtil;

public class DeleteLastAnalysisBuilder {
	
	private static int fileCounter;
	private static int directoryCounter;
	public static String[] fileSavePaths;
	public static String[] directorySavePaths;	
	public static Map<String, byte[]> addedSavedFiles;
	
	public static void deleteLastSave(DeleteLastDataModel deleteData) {
		
		deleteList(deleteData.getFileSavePaths());
		deleteAddedStream(deleteData.getAddedSavedFiles());
		deleteList(deleteData.getDirectorySavePaths());		
	}
	
	public static void delete(String path) {
		
		File myObj = new File(path); 
		
	    if(myObj.delete()) 
	    	Debug.debug(3,"DeleteLastAnalysis Object " + myObj.getName()+ " deleted");      
	   
	    else 	    	
	    	Debug.debug(3,"DeleteLastAnalysis Failed to delete object: "+myObj.getName());	    
	}
	
	public static void deleteAddedStream(Map<String, byte[]> lastSavedFiles) {
		
		if(lastSavedFiles.size() == 0) return;
		
		for(String key : lastSavedFiles.keySet()) {
			
			//delete(key);
			FileWriterUtil.fileWriter(new byte[][] {lastSavedFiles.get(key)},null,key);
		}
	}
	
	public static void  addPathToFileSavedList(String path) {
		
		fileSavePaths[fileCounter++] = path;
	}
	
	public static void  addPathToDirectorySavedList(String path) {
		
		directorySavePaths[directoryCounter++] = path;
	}	
	
	public static void addToLastSavedFileArray(String filePath, byte[] file) {
		
		addedSavedFiles.put(filePath, file);
	}
	
	public static void resetSavedData () {
		
		Debug.debug(3,"DeleteLastAnalysis resetSavedData!");
		
		fileSavePaths = new String[12];
		directorySavePaths = new String[12];
		addedSavedFiles = new HashMap<>();
		fileCounter = 0;
		directoryCounter = 0;
	}
	
	public static void deleteList(String[] deleteList) {
		
		if(deleteList.length == 0 ) return;
		
		Debug.debug(3,"Delete List length: "+deleteList.length+ ", Array: "
			+ Arrays.toString(deleteList));
	
		for(int i = 0 ; i < deleteList.length; i++) 
			delete(deleteList[i]);		
	}
	
	public static DeleteLastDataModel createFinalDeleteList() {

		fileSavePaths = StringArrayUtil.filterEmptyStringsWithFixedLength(fileSavePaths,
				fileCounter, "fileSavePaths");
		
		directorySavePaths = StringArrayUtil.filterEmptyStringsWithFixedLength(directorySavePaths
				,directoryCounter, "directorySavePaths");
		
		return new DeleteLastDataModel(fileSavePaths, directorySavePaths, addedSavedFiles);
	}
}
