package voiceRecognition.audio5.audioGramBuilder;

import java.util.Arrays;

import voiceRecognition.audio8.util.Debug;

public class VoiceRecognitionFrequencyGramBuilder {

	private static int[][] result;
	
	private static int buffer;
	private static int columnCounter;
	private static int rowCounter;
	private static int i;
	private static int j;
	private static int k;
	private static int l;
	
	private static int debugLevelDebug = 5;
	private static int debugLevelInfo = 5;
	
	public static int[][] buildPoints(int[][] input) {
		
		int[] startPoints = getStatrtEndPoints(input);
		int matrixSize = 2;
		int startPos = startPoints[0] > 0 ? startPoints[0] : 1; 
		int endPos = startPoints[1]; 
		
		Debug.debug(debugLevelInfo,"VoiceRecognitionFrequencyGramBuilder Start: " +startPos 
				+", EndPoint: "+endPos + ", old Length "+input.length + " Frequencys array length: "+ input[0].length);
		
		result = new int[input[0].length / 2][];
		columnCounter = 0;
		rowCounter = 0;
		
		for(i = 0; i < input[0].length; i = i + 2) {

			//Debug.debug("i "+ i +" "+result.length );
			
			result[rowCounter] = new int[(endPos-startPos)/matrixSize+ matrixSize];
			columnCounter = 0;
			result[rowCounter][columnCounter++] = input[0][i+1];

			for(j = startPos; j < endPos - matrixSize; j = j + matrixSize)	{
				
				buffer = 0;
					
				if(i + matrixSize*2 < input[0].length) {
					
					for(k = 0; k < matrixSize; k++) {
						
						for(l = 0; l < matrixSize * 2 ; l = l + 2) {
							
							Debug.debug(debugLevelDebug,"VoiceRecognitionFrequencyGramBuilder, i: "
								+i + ", j: "+j	+", k "+ k+", l "+l+ ", i+k " +(i+k)+", j+l "+(j+l)
								+" , input[j+k][i+l] "+input[j+k][i+l]);
							
							buffer += input[j+k][i+l];
						}
					}
				}
				Debug.debug(debugLevelDebug,"VoiceRecognitionFrequencyGramBuilder, i " +i+", j "+ j
					+ " rowCounter "+rowCounter + ", buffer: " +buffer);
				result[rowCounter][columnCounter++] = buffer / (matrixSize) ;

			}
			rowCounter++;
		}		
			printResult(result);
			
			return result;
	} 
	
	private static int[] getStatrtEndPoints(int[][] input) {
		
		printResult(input);		
		int start = 0;
		int end = 0;
		int check = 0;
		for(int i = 0; i < input.length; i++) {
			
			for(int j = 0; j < input[i].length; j = j + 2) {
				
				if(input[i][j] > 5) {
					start = i;
					check = 1;
					Debug.debug(debugLevelDebug,"VoiceRecognitionFrequencyGramBuilder getStatrtEndPoints i: " 
							+i +", j:"+j+", frequencys[i][j+1] "+input[i][j]);
					break;
				}
			}
			if(check > 0)
				break;
		}
		check = 0;
		
		for(int i = input.length-1; i > 0; i--) {
			
			for(int j = 0; j < input[i].length; j = j + 2) {
				
				if(input[i][j] > 5) {
					end = i;
					check = 1; 
					Debug.debug(debugLevelDebug,"VoiceRecognitionFrequencyGramBuilder getStatrtEndPoints i: " 
							+i +", j:"+j+", frequencys[i][j+1] "+input[i][j]);
					break;
				}
			}
			if(check > 0)
				break;
		}
			return new int[] {start,end};
	}
	
	private static void printResult(int[][] input) {
		
		for(int i = 0; i < input.length; i++) 
				
			Debug.debug(debugLevelInfo,"VoiceRecognitionFrequencyGramBuilder printResult i: "+i+" "+Arrays.toString(input[i]));		
	}
}
