package voiceRecognition.audio2.audioSequenceBuilder;

import java.util.Arrays;
import java.util.List;

import voiceRecognition.audio6.audioGramSaver.FileReaderUtil;
import voiceRecognition.audio8.util.Debug;

public class WavesArrayBuilderFromFile {
	
	private static int[] tempSequences;
	
	public static int[] mainBuilder(String path) {
		
		return extractSequenceInfo(
			FileReaderUtil.buildStringLinesFromInputStream(
				FileReaderUtil.buildFileStreamFromFile(path)),",");		
	}
	
	private static int[] extractSequenceInfo(List<String> stringLinesArray, String separator) {
		
		String[] lineElements = null;
		String[] sequenceElements = null;
		int counter = 0;

		lineElements = stringLinesArray.get(0).split(separator);	
		Debug.debug(3,"WavesArrayBuilderFromFile extractSequenceInfo stringLinesArray.length: "
				+stringLinesArray.size() + ", lineElements.length: "+lineElements.length);	
		
		tempSequences = new int[lineElements.length*3 - 3];
				
		for(int i = 1; i < lineElements.length; i++) {
				
			sequenceElements = lineElements[i].split(":");
				tempSequences[counter++] = Integer.parseInt(sequenceElements[0]);
				tempSequences[counter++] = Integer.parseInt(sequenceElements[1]);
				tempSequences[counter++] = Integer.parseInt(lineElements[0]);
		}

		Debug.debug(3,"WavesArrayBuilderFromFile extractSequenceInfo sequence.length: "
				+ (tempSequences.length  / 3) + " Array: "+Arrays.toString(tempSequences));
		
			return tempSequences;
	}
}
