package com.filetransit;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadFromFile {

	public List<String> read(String path) {
		
		FileInputStream inputStream = null;
		Scanner sc = null;
		List<String> list = new ArrayList<String>();
		
		try {
            inputStream = new FileInputStream(path);
		    sc = new Scanner(inputStream, "UTF-8");
		    while (sc.hasNextLine()) {
		    	String line = sc.nextLine();
		    	list.add(line);
		    	System.out.println("READIMPL  readed new line "+ line );
		    }
		} 
	    catch (Exception e) {   
	    	System.out.println("Input stream read error! "+e);
	    }	
		finally {
		    if (inputStream != null) {
		        try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		    }
		    if (sc != null) {
		        sc.close();
		    }
		}
		return list;
	}
}
