package com.mi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.filetransit.WriteToFile;

public class Main {
	
	public static void main(String[] args) throws IOException {
		/*
		List<String> functions = new ArrayList<String>();

		FunctionModel newfunction = new FunctionModel();
		newfunction.setId(UUID.randomUUID().toString());
		newfunction.setName("firstfunction");
		newfunction.setArgument("");
		newfunction.setReturnValueType("void");
		newfunction.setSyntax("System.out.println();");
		
		String  funcString = CreateFunctionObject.createFunctionObject(newfunction);
		
		functions.add(funcString);
		WriteToFile.writeToFile("src\\com\\library\\" , "test", functions);
		*/
		
		
		for(int i = 0 ; i < Chars.CharList.length; i++) {
			CreateImageByFont.createBufferedImagefont(Chars.CharList[i],"Arial", "src\\main\\resources\\static\\fonts\\");
		}
		
		//CreateImageByPixel.createImage();
	}

}
