package com.audio2.soundBuilder;

import java.util.Arrays;
import java.util.List;

import com.audio8.util.Debug;
import com.audio8.util.FileUtil;

public class SequenceBuilderFromFile {
	
	//private static List<String> stringLinesArray;
	private static Sequence[] tempSequences = new Sequence[100000];
	
	public static Sequence[] mainBuilder(String path) {
		return extractfrequencys(FileUtil.buildStringLinesFromInputStream(FileUtil.buildFileStreamFromFile(path)),",");	
		//return extractfrequencys(stringLinesArray = FileUtil.buildStringLinesFromInputStream(FileUtil.buildFileStreamFromFile(path)),",");	
	}
	
	private static Sequence[] extractfrequencys(List<String> stringLinesArray, String separator) {
		
		String[] lineElements = null;
		String[] wave = null;
		int counter = 0;
		Sequence[] sequences  = new Sequence[100000];
		
		for (int i = 0 ; i < stringLinesArray.size(); i++) {
			
			lineElements = stringLinesArray.get(i).split(separator);
			
			for(int j = 0; j < lineElements.length;j++) {
				
				wave = lineElements[j].split(":");
				
				if(wave != null && wave.length == 5) {
					tempSequences[counter++] = new Sequence(Integer.parseInt(wave[0]),Integer.parseInt(wave[1]),Integer.parseInt(wave[2]),Integer.parseInt(wave[3]),Integer.parseInt(wave[4]));
				//	System.out.println("Sequence to string"+ tempSequences[counter-1].toString());
				}
			}
		}
				
		sequences = new Sequence[counter]; 
		
		for(int i = 0; i < counter; i++ )
			sequences[i] = tempSequences[i];
		
		Debug.debug(3,"FrequencyBuilder extractfrequencys frequencys.length: "+sequences.length + " Array: "+Arrays.toString(sequences));
			return sequences;
	}
}
