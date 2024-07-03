package com.filetransit;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.library.StatLib;

public class WriteToFile {

	public static void writeToFile(String path,String fileName, List<String> input) 
			  throws IOException {
		
			    String str = "";
			    	  str = str.concat("package com.library; \n\n"
			    + StatLib.IMPORTS 
			     +"\npublic class "+fileName + " { \n \n");
			    
			    for(String s : input) {
			    	//System.out.println("s:"+s);
			    	str = str.concat(s);
			    }
			    
			    str = str.concat("\n }");
			    System.out.println("str end: "+str);
			    FileOutputStream outputStream = new FileOutputStream(path+fileName+".java");
			    byte[] strToBytes = str.getBytes();
			    outputStream.write(strToBytes);

			    outputStream.close();
			}
}
