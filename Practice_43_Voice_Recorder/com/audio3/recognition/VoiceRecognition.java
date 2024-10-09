package com.audio3.recognition;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.audio0.main.AppSetup;
import com.audio8.util.Debug;
import com.audio8.util.FileUtil;
import com.audio8.util.VoiceRecognitionFileUtil;

public class VoiceRecognition {

	
	private static int [][][] audioDB ;
//	private static int RECOGNITION_PERCENT_LIMIT = 90;
	private static Map<Integer, String> DB_NAMES = new HashMap<>();
	private static int [][] resultVerifier;
	private static float matchCounter;
	private static int match;
	private static String bestMatch;
//	private static float matchPercent;
//	private static int bonusCounter;
	private static int avgCounter;
	static float dB_1_Percent;
	static int difference;
	static int result;
	static float testResult1;
	static float testResult2;
	static float testResult3;
	public static int[] testSamples = new int [] {3539, 918, 5256, 416, 1767, 574, 420, 522, 400, 512, 482, 433, 497, 466, 450, 454, 442, 479, 433, 479, 437, 433, 441, 445, 416, 1263, 1938, 1304, 2105, 2874, 730, 409, 442, 1917, 392, 3675, 274, 279, 1708, 2188, 2323, 2335, 2818, 3200, 3240, 3125, 3911, 3540, 3844, 3355, 3549, 3549, 3578, 420, 522, 400, 512, 482, 2872, 3429, 946, 245, 247, 690, 2984, 3328, 1750, 1087, 2836, 3234, 4152, 3751, 933, 1951, 1081, 927, 1909, 1840, 191, 842, 279, 204, 1162, 1169, 855, 2332, 600, 1489, 976, 172, 770, 200, 176, 1700, 294, 173, 3595, 2047, 3856, 3710, 814, 2298, 1913, 229, 116, 3764, 2405, 5913, 3623, 3462, 454, 229, 1613, 4468, 1256, 355, 4097, 4934, 5220, 1378, 5030, 816, 478, 1831, 5096, 2139, 297, 92, 5614, 3100, 4777, 4152, 631, 3964, 2830, 2450, 5381, 5480, 1283, 4782, 2815, 2756, 4259, 2919, 1761, 125, 245, 4759, 52, 3702, 3510, 1238, 5125, 3139, 2765, 3972, 2806, 2713, 3138, 6586, 3546, 2756, 131, 2247, 2603, 1619, 3723, 2784, 2161, 150, 3590, 4996, 2824, 5606, 5176, 3717};
	
	public static void test(int[] inputSamples) {
		
		buildAudioDB();
		voiceFinder(inputSamples);
	}
		
	public static void buildAudioDB() {
			
		String[] voicesNamesList = VoiceRecognitionFileUtil.getFilesOrFoldersList(AppSetup.BASE_AUDIO_PATH+"spektrum/");
		List<String> readedFile = null;
		String str = null;
		String[] voiceNamesArrayList = null; 
		
	
		Debug.debug(5,"VoiceRecognition buildAudioDB VoicesNamesList.length: "+ voicesNamesList.length);
		
		audioDB = new int[voicesNamesList.length][][];
		
		for(int i = 0; i < voicesNamesList.length; i++) {
				
			readedFile = FileUtil.buildStringLinesFromInputStream(FileUtil.buildFileStreamFromFile(AppSetup.BASE_AUDIO_PATH+"spektrum/"+voicesNamesList[i]+"/voiceRecognition/"+voicesNamesList[i]+"-voiceRecognition.txt"));
			DB_NAMES.put(i, voicesNamesList[i]);

			audioDB[i] = new int[readedFile.size()][];
			
			for(int j = 0 ; j < readedFile.size(); j++ ) {
								 
				 str = readedFile.get(j).substring(1, (readedFile.get(j).length()-1));
				 
				 voiceNamesArrayList = str.split(", ");	
				 
				 audioDB[i][j] = new int[voiceNamesArrayList.length];
				 
				 for(int k = 0; k < voiceNamesArrayList.length; k++ ) {
					 
					 audioDB[i][j][k] = Integer.valueOf(voiceNamesArrayList[k]);
					 Debug.debug(5,"VoiceRecognition I: "+i+ " J: "+ j + "  K: "+k+ " audioDB[i][j][k]: "+audioDB[i][j][k]);
				 }
			}
		}
	}	
	public static void voiceFinder(int[] checkArray) {
		
		if(checkArray == null) return;
		
		Debug.debug(3,"VoiceRecognition voiceFinder checkArray.length: " +checkArray.length);
		
		resultVerifier = new int[audioDB.length][];
		match = 0;
		for(int i = 0; i < audioDB.length; i++) {
			resultVerifier[i] = new int[audioDB[i].length+1];
			avgCounter = 0;
			for(int j = 0; j < audioDB[i].length; j++) {	
				
				//System.out.println("audioDB.length: "+audioDB.length + " audioDB[i].length "+audioDB[i].length + " i: "+ i + " j: "+j);
				matchCounter = 0;
//				bonusCounter = 0;

				for(int k = 0; k < audioDB[i][j].length; k = k+2) {
				//	System.out.println("i: "+i+" j:" +j +" k: "+ k +",audioDB[i][j][k] "+audioDB[i][j][k]  +" checkArray[k] : "+ checkArray[k]
						//	+",audioDB[i][j][k+1]+ "+audioDB[i][j][k+1] + " checkArray[k+1] : "+ checkArray[k+1]);
					
					if(checkArray[k+1] < 1000 ) {
						testResult1 = calculateMatchProcent(audioDB[i][j][k],checkArray[k]);
						testResult2 = calculateMatchProcent(audioDB[i][j][k+1],checkArray[k+1]);
						testResult3 = testResult1 * testResult2;
						Debug.debug(5,"VoiceRecognition voiceFinder testResult1: "+testResult1 + ", testResult2: "+testResult2 + ", testResult3: "+testResult3);
						matchCounter += testResult3;
						;
					}
					
					if( k >= checkArray.length-2 || k >= audioDB[i][j].length-2) {
						resultVerifier[i][j] = (int) matchCounter;
						avgCounter = (int) (avgCounter+matchCounter);
						Debug.debug(3, "VoiceRecognition voiceFinder avgCounter: "+avgCounter + " audioDB[i].length: "+audioDB[i].length );
						if(matchCounter > match) {
							bestMatch = DB_NAMES.get(i) + " position "+ j ;
							match = (int) matchCounter;
							Debug.debug(3, "VoiceRecognition voiceFinder New Best match: " + bestMatch + ", match:" + match);
						}						
						//System.out.println("Actual  arrayFinalDiffSum: "+arrayFinalDiffSum);					
						if(j == audioDB[i].length-1)
						{
							resultVerifier[i][j+1] = avgCounter / audioDB[i].length;
						}
						Debug.debug(3, "VoiceRecognition voiceFinder Added "+Arrays.toString(resultVerifier[i])+ ", Break");
						break;
					}
				}
				
				Debug.debug(3,"VoiceRecognition voiceFinder End i: " + i + " j:"+ j +", matchCounter "+matchCounter);
			}
		}
		Debug.debug(3, "VoiceRecognition voiceFinder Actual bestMatch: " + bestMatch + ", match: "+match );
		//printVerify();
	}
	public static void printVerify() {
		
		for(int i = 0; i < resultVerifier.length; i++) {
			for(int j = 0; i < resultVerifier[i].length; j++) {	
				System.out.println("resultVerifier: "+resultVerifier[i][j]);
			}
		}
	}
	
	public static int calculateMatchProcent(int dB_Value, int check_Value) {
		
		//System.out.println("voiceFinder calculateMatchProcent dB_Value: "+dB_Value + ",check_Value : "+check_Value);
		dB_1_Percent = dB_Value/100;
		difference = Math.abs(dB_Value) - Math.abs(check_Value);
		result = (int) (100 - Math.abs(difference /dB_1_Percent));
		if((dB_Value > 0 && check_Value < 0)||(dB_Value < 0 && check_Value > 0) ||result< 0 )
			result =  1;
		Debug.debug(5, "VoiceRecognition calculateMatchProcent calculateMatchProcent result: "+result);
		return result;
	}
	/*
	public static boolean calculateMatchProcent(int dB_Value ,int inputValue) {

		matchPercent = ((float) dB_Value / inputValue)*100;
		Debug.debug(5,"matchPercent: "+matchPercent + ", lowerPercentLimit: "+lowerPercentLimit +", upperPercentLimit "+upperPercentLimit);
		if(matchPercent  > lowerPercentLimit && matchPercent < upperPercentLimit) {
			bonusCounter++;
			return true;
		}
		if(bonusCounter > 1) {
			bonusCounter--;
		}
			return false;
	}
	*/
	/*
	public static int voiceFinder(int[] checkArray) {
		
		int result = 0;
		match = 500000;
		resultVerifier = new int[audioDB.length][];
		for(int i = 0; i < audioDB.length; i++) {
			resultVerifier[i] = new int[audioDB[i].length];
			for(int j = 0; j < audioDB[i].length; j++) {	
				
				
				//System.out.println("audioDB.length: "+audioDB.length + " audioDB[i].length "+audioDB[i].length + " i: "+ i + " j: "+j);
				arrayFinalDiffSum = 0;
				for(int k = 0; k < audioDB[i][j].length-3; k = k+3) {
					//System.out.println("i: "+i+" j:" +j +" k: "+ k + " value: "+audioDB[i][j][k]  +" checkArray.length: "+ checkArray.length + " audioDB[i][j].length: "+audioDB[i][j].length );
					if(checkArray.length > k+3) {
						dbSum = audioDB[i][j][k];
						dbSum += audioDB[i][j][k+1];
						dbSum += audioDB[i][j][k+2];
						
						inputSum = checkArray[k];
						inputSum += checkArray[k+1];
						inputSum += checkArray[k+2];
						
						arrayFinalDiffSum+= Math.abs(dbSum-inputSum);
						Debug.debug(3, "VoiceRecognition voiceFinder i: "+ i +" j: "+j+" k: " +k+ " audioDB[i][j][k]: "+audioDB[i][j][k]+" checkArray[k]: "+checkArray[k] +" dbSum "+ dbSum+ " inputSum: "+inputSum+" arrayFinalDiffSum: "+arrayFinalDiffSum );						
					}
					//System.out.println("checkArray.length "+checkArray.length + ", k+6: " +(k+6));
					if( k >= checkArray.length-6 || k >= audioDB[i][j].length-6) {
						arrayFinalDiffSum = arrayFinalDiffSum/(k+3);
						resultVerifier[i][j] = arrayFinalDiffSum;
						if(arrayFinalDiffSum <match) {
							bestMatch = DB_NAMES.get(i) + " position "+ j + ", arrayFinalDiffSum: "+arrayFinalDiffSum;
							result = arrayFinalDiffSum;
							match = arrayFinalDiffSum;
							System.out.println("Best match: " + bestMatch + " result: " +result);
						}						
						//System.out.println("Actual  arrayFinalDiffSum: "+arrayFinalDiffSum);
						arrayFinalDiffSum =0;						
						Debug.debug(5, "Added "+Arrays.toString(resultVerifier[i]));	
						break;
					}
				}
			}
		}
		System.out.println("Actual match: " + bestMatch + " result: " +result + ", match: "+match +", arrayFinalDiffSum: "+arrayFinalDiffSum +", alculateMatchProcent  "+calculateMatchProcent(result));
		//printVerify();
		return 0;
	}

   */
}
