package com.audio8.util;

import java.io.File;

public class VoiceRecognitionFileUtil {

	public static String[] getFilesOrFoldersList(String path) {
	     File directoryPath = new File(path);
	     return directoryPath.list();
	}
}
