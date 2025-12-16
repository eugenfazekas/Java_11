package voiceRecognition.audio6.audioGramSaver;

import java.util.Map;

public class DeleteLastDataModel {

	private  String[] fileSavePaths;
	private  String[] directorySavePaths;	
	private  Map<String, byte[]> addedSavedFiles;

	public DeleteLastDataModel(String[] fileSavePaths, String[] directorySavePaths,
			Map<String, byte[]> addedSavedFiles) {
		
		this.fileSavePaths = fileSavePaths;
		this.directorySavePaths = directorySavePaths;
		this.addedSavedFiles = addedSavedFiles;
	}

	public String[] getFileSavePaths() {
		
		return fileSavePaths;
	}

	public String[] getDirectorySavePaths() {
		
		return directorySavePaths;
	}

	public Map<String, byte[]> getAddedSavedFiles() {
		
		return addedSavedFiles;
	}
}
