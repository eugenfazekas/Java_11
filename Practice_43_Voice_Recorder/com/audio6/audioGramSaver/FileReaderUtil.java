package com.audio6.audioGramSaver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.audio8.util.Debug;

public class FileReaderUtil {

	public static List<String>  buildStringLinesFromInputStream(FileInputStream stream) {

		List<String> lines = new ArrayList<>();
		StringBuilder line = new StringBuilder();
	    int ch;
	    
	        try {
				while ((ch = stream.read()) != -1) {
					if(ch != 10 && ch!=13) {
						line.append((char)ch);
					}		
					if(ch == 13 || ch ==10) {
						if(line.length() >0)
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
	        //Debug.debug(3,"FileReaderUtil buildStringLines Get 0: "+lines.get(0));
	        
	        	return lines;
	}
	
	public static FileInputStream buildFileStreamFromFile(String filePath) {
		
		FileInputStream fin = null;	
		
		try {
			
			fin = new FileInputStream(filePath);
			
			Debug.debug(3,"FileReaderUtil buildFileStreamFromFile: "+filePath 
				+", available"+fin.available());
	
		} catch (Exception e) {
			Debug.debug(3,"FileReaderUtil buildFileStreamFromFile READ ERRROORR");
			e.printStackTrace();
		}		
			return fin;
	}
}
