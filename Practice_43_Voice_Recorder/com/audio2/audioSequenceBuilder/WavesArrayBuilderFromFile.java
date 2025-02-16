package com.audio2.audioSequenceBuilder;

import java.util.Arrays;
import java.util.List;

import com.audio8.util.Debug;
import com.audio8.util.file.FileReaderUtil;

public class WavesArrayBuilderFromFile {
	
	private static AudioWave[] tempSequences = new AudioWave[100000];
	
	public static AudioWave[] mainBuilder(String path) {
		
		return extractfrequencys(
			FileReaderUtil.buildStringLinesFromInputStream(
				FileReaderUtil.buildFileStreamFromFile(path)),",");		
	}
	
	private static AudioWave[] extractfrequencys(List<String> stringLinesArray, String separator) {
		
		String[] lineElements = null;
		String[] wave = null;
		int counter = 0;
		AudioWave[] wavesArray  = new AudioWave[100000];
		
		for (int i = 0 ; i < stringLinesArray.size(); i++) {
			
			lineElements = stringLinesArray.get(i).split(separator);
			
			for(int j = 0; j < lineElements.length;j++) {
				
				wave = lineElements[j].split(":");
				
				if(wave != null && wave.length == 5) {
					
					tempSequences[counter++] = new AudioWave(Integer.parseInt(wave[0])
						,Integer.parseInt(wave[1]),Integer.parseInt(wave[2])
						,Integer.parseInt(wave[3]),Integer.parseInt(wave[4]));
				//	System.out.println("Sequence to string"+ tempSequences[counter-1].toString());
				}
			}
		}				
		wavesArray = new AudioWave[counter]; 
		
		for(int i = 0; i < counter; i++ )
			wavesArray[i] = tempSequences[i];
		
		Debug.debug(3,"FrequencyBuilder extractfrequencys frequencys.length: "+wavesArray.length 
			+ " Array: "+Arrays.toString(wavesArray));
		
			return wavesArray;
	}
}
