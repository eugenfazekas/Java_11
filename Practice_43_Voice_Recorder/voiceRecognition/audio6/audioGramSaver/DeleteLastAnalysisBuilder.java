package voiceRecognition.audio6.audioGramSaver;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import voiceRecognition.audio8.util.Debug;

public class DeleteLastAnalysisBuilder {
	
	private static int fileCounter;
	private static int directoryCounter;
	public static String[] fileSavePaths;
	public static String[] directorySavePaths;	
	public static Map<String, byte[]> addedSavedFiles;
	private static String[] tempStringArray; 
	
	private static int debug_level_INFO = 5;
	
	public static void deleteLastSave(DeleteLastDataModel deleteData) {
		
		deleteList(deleteData.getFileSavePaths());
		deleteAddedStream(deleteData.getAddedSavedFiles());
		deleteList(deleteData.getDirectorySavePaths());		
	}
	
	public static void delete(String path) {
		
		File myObj = new File(path); 
		
	    if(myObj.delete()) 
	    	Debug.debug(debug_level_INFO,"DeleteLastAnalysis Object " + myObj.getName()+ " deleted");      
	   
	    else 	    	
	    	Debug.debug(debug_level_INFO,"DeleteLastAnalysis Failed to delete object: "+myObj.getName());	    
	}
	
	public static void deleteAddedStream(Map<String, byte[]> lastSavedFiles) {
		
		if(lastSavedFiles.size() == 0) return;
		
		for(String key : lastSavedFiles.keySet()) 			
			FileWriterUtil.fileWriter(new byte[][] {lastSavedFiles.get(key)},null,key);		
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
		
		Debug.debug(debug_level_INFO,"DeleteLastAnalysis resetSavedData!");
		
		fileSavePaths = new String[15];
		directorySavePaths = new String[15];
		addedSavedFiles = new HashMap<>();
		fileCounter = 0;
		directoryCounter = 0;
	}
	
	public static void deleteList(String[] deleteList) {
		
		if(deleteList.length == 0 ) return;
		
		Debug.debug(debug_level_INFO,"DeleteLastAnalysis Delete List length: "+deleteList.length
			+ ", Array: "+ Arrays.toString(deleteList));
	
		for(int i = 0 ; i < deleteList.length; i++) 
			delete(deleteList[i]);		
	}
	
	public static DeleteLastDataModel createFinalDeleteList() {

		fileSavePaths = filterEmptyStringsWithFixedLength(fileSavePaths,
				fileCounter, "fileSavePaths");
		
		directorySavePaths = filterEmptyStringsWithFixedLength(directorySavePaths
				,directoryCounter, "directorySavePaths");
		
		return new DeleteLastDataModel(fileSavePaths, directorySavePaths, addedSavedFiles);
	}
	
	static String[]filterEmptyStringsWithFixedLength(String[]inputStringArray,int arraySize
			,String arrayNam) {
		
		tempStringArray = new String[arraySize];
		
		for (int i = 0; i < arraySize ; i++)
			tempStringArray[i] = inputStringArray[i];
		
		Debug.debug(debug_level_INFO,"AudioBuilderUtil filterEmptyStringsWithUnknownLength Array Name: "
			+arrayNam+", old length: "+inputStringArray.length+", new length: "
			+tempStringArray.length);
		
			return tempStringArray;		
	}
}
