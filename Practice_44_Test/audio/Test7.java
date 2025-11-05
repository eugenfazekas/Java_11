package audio;

import java.time.LocalDateTime;
import java.util.Arrays;

class Debug {

	private static int counter = 0;
	private static int baseDebugLevel = 4;
	public static long startTime;
	
	/* Debug Levels
	* 1.Contructor
	* 2.Method
	* 3.DEBUG
	* 4.INFO
	* 5.LOOP
	* 
	*/
	public static void debug( int debugLevel, String message) {
		
		if(debugLevel  <= baseDebugLevel)
			System.out.println(counter+++". "+message);
	}	
	
	public static String getTimeStamp() {
		
		 LocalDateTime timestamp = LocalDateTime.now();	
		 
		 return timestamp.getHour()+"_"+ timestamp.getMinute()+ "_" + timestamp.getSecond()  
				+" "+ timestamp.getDayOfMonth() + "." +timestamp.getMonthValue() + "."+timestamp.getYear();
	}
}

public class Test7 {
	
	private static String check = new String("0:[0, 2, 10, 50, 17, 18, 18, 2, 28, 50, 35, 18]_1:[0, 1, 1, 1, 2, 13, 3, 25, 4, 40, 5, 55, 6, 57, 7, 58, 8, 58, 9, 58, 10, 58, 11, 59, 12, 59, 13, 57, 14, 49, 15, 37, 16, 26]_2:[0, 1]_3:[2]_4:[3]_5:[3]_6:[3]_7:[4]_8:[4]_9:[4]_10:[4]_11:[4]_12:[5]_13:[5]_14:[5]_15:[5]_16:[5]_17:[5]_18:[5]_19:[6, 16]_20:[6, 15]_21:[6, 15]_22:[6, 15]_23:[6, 15]_24:[6, 14]_25:[6, 14]_26:[6, 14]_27:[6, 14]_28:[7, 14]_29:[7, 14]_30:[7, 14]_31:[7, 14]_32:[7, 13]_33:[7, 13]_34:[7, 13]_35:[7, 13]_36:[8, 13]_37:[8, 13]_38:[8, 13]_39:[8, 13]_40:[8, 12]_41:[8, 12]_42:[8, 12]_43:[9, 12]_44:[9, 12]_45:[9, 12]_46:[9, 12]_47:[9, 12]_48:[10, 11]_49:[10, 11]_50:[10, 10, 11, 11]&0:[0, 18, 8, 95, 46, 4, 38, 18, 46, 95, 84, 4]_1:[0, 15, 1, 18, 2, 28, 3, 40, 4, 56, 5, 67, 6, 73, 7, 76, 8, 78, 9, 78, 10, 78, 11, 78, 12, 77, 13, 74, 14, 70, 15, 67, 16, 64, 17, 63, 18, 63, 19, 63, 20, 63, 21, 63, 22, 64, 23, 64, 24, 63, 25, 62, 26, 59, 27, 57, 28, 56, 29, 55, 30, 55, 31, 53, 32, 51, 33, 46, 34, 36, 35, 28, 36, 20, 37, 14, 38, 13, 39, 13, 40, 13, 41, 12, 42, 9, 43, 6, 44, 2, 45, 1]_5:[-1, 46]_6:[-1, 45]_7:[-1, 44]_8:[-1, 44]_9:[-1, 43]_10:[-1, 43]_11:[-1, 42]_12:[-1, 42]_13:[-1, 42]_14:[-1, 41]_15:[-1, 41]_16:[-1, 41]_17:[-1, 40]_18:[0, 1, 39]_19:[2, 39]_20:[2, 38]_21:[3, 37, 38, 38]_22:[3, 32, 33, 33, 34, 34, 35, 35, 36, 36]_23:[3, 31]_24:[3, 30]_25:[3, 29]_26:[4, 28]_27:[4, 27]_28:[4, 27]_29:[4, 27]_30:[4, 26]_31:[4, 26]_32:[4, 25]_33:[4, 23, 24, 24, 25, 25]_34:[5, 18, 19, 19, 20, 20, 21, 21, 22, 22]_35:[5, 17]_36:[5, 16]_37:[5, 16]_38:[5, 16]_39:[5, 15]_40:[5, 15]_41:[5, 15]_42:[5, 15]_43:[5, 15]_44:[5, 15]_45:[5, 14]_46:[5, 14]_47:[6, 14]_48:[6, 14]_49:[6, 14]_50:[6, 14]_51:[6, 13]_52:[6, 13]_53:[6, 13]_54:[6, 13]_55:[6, 13]_56:[6, 13]_57:[6, 13]_58:[6, 13]_59:[6, 12]_60:[6, 12]_61:[6, 12]_62:[6, 12]_63:[6, 12]_64:[7, 12]_65:[7, 12]_66:[7, 12]_67:[7, 12]_68:[7, 12]_69:[7, 11]_70:[7, 11]_71:[7, 11]_72:[7, 11]_73:[7, 11]_74:[7, 11]_75:[7, 11]_76:[7, 11]_77:[7, 11]_78:[7, 11]_79:[7, 11]_80:[7, 11]_81:[7, 10]_82:[7, 10]_83:[8, 10]_84:[8, 10]_85:[8, 10]_86:[8, 10]_87:[8, 10]_88:[8, 10]_89:[8, 10]_90:[8, 10]_91:[8, 10]_92:[8, 10]_93:[8, 9]_94:[8, 9]_95:[8, 8, 9, 9];0:[0, 1, 11, 59, 18, 15, 19, 1, 30, 59, 37, 15]_1:[0, 3, 1, 6, 2, 11, 3, 18, 4, 27, 5, 35, 6, 42, 7, 47, 8, 50, 9, 50, 10, 47, 11, 39, 12, 31, 13, 23, 14, 19, 15, 18, 16, 18, 17, 18]_2:[2]_3:[2]_4:[2]_5:[2]_6:[2]_7:[2]_8:[2]_9:[2]_10:[2]_11:[2]_12:[2]_13:[2]_14:[3]_15:[3]_16:[3, 17]_17:[3, 17]_18:[3, 17]_19:[3, 16]_20:[3, 16]_21:[3, 16]_22:[3, 16]_23:[3, 16]_24:[3, 16]_25:[3, 16]_26:[4, 16]_27:[4, 15]_28:[4, 15]_29:[4, 15]_30:[4, 15]_31:[4, 15]_32:[4, 15]_33:[4, 15]_34:[4, 15]_35:[4, 15]_36:[4, 15]_37:[4, 15]_38:[4, 14]_39:[4, 14]_40:[4, 14]_41:[5, 14]_42:[5, 14]_43:[5, 14]_44:[5, 14]_45:[5, 14]_46:[5, 14]_47:[5, 14]_48:[5, 14]_49:[5, 14]_50:[5, 13]_51:[5, 13]_52:[5, 13]_53:[5, 13]_54:[5, 13]_55:[5, 13]_56:[6, 13]_57:[6, 13]_58:[7, 7, 8, 8, 9, 9, 10, 12]_59:[11, 11, 12, 12]&0:[0, 15, 10, 78, 46, 1, 38, 15, 48, 78, 84, 1]_1:[0, 18, 1, 20, 2, 25, 3, 33, 4, 46, 5, 63, 6, 82, 7, 95, 8, 95, 9, 92, 10, 80, 11, 68, 12, 58, 13, 50, 14, 44, 15, 38, 16, 35, 17, 34, 18, 34, 19, 34, 20, 34, 21, 34, 22, 33, 23, 33, 24, 33, 25, 31, 26, 29, 27, 26, 28, 25, 29, 24, 30, 23, 31, 22, 32, 22, 33, 22, 34, 22, 35, 22, 36, 21, 37, 21, 38, 19, 39, 16, 40, 13, 41, 10, 42, 8, 43, 6, 44, 5, 45, 4]_2:[-1, 46]_3:[-1, 45]_4:[-1, 45]_5:[-1, 45]_6:[-1, 45]_7:[-1, 44]_8:[-1, 44]_9:[-1, 44]_10:[-1, 43]_11:[-1, 43]_12:[-1, 43]_13:[-1, 39, 40, 40, 41, 41]_14:[-1, 39]_15:[0, 1, 37]_16:[2, 37]_17:[2, 37]_18:[2, 37]_19:[3, 37]_20:[3, 37]_21:[3, 36]_22:[3, 36]_23:[3, 36]_24:[3, 36]_25:[3, 36]_26:[3, 36]_27:[3, 36]_28:[3, 36]_29:[4, 35]_30:[4, 35]_31:[4, 35]_32:[4, 35]_33:[4, 35]_34:[4, 35]_35:[4, 35]_36:[4, 35]_37:[4, 34]_38:[4, 34]_39:[4, 34]_40:[4, 34]_41:[5, 34]_42:[5, 34]_43:[5, 34]_44:[5, 34]_45:[5, 34]_46:[5, 34]_47:[5, 33]_48:[5, 33]_49:[5, 33]_50:[5, 33]_51:[5, 33]_52:[5, 32]_53:[5, 32]_54:[5, 31]_55:[5, 30, 31, 31]_56:[5, 29]_57:[6, 28]_58:[6, 27]_59:[6, 27]_60:[6, 26]_61:[6, 26]_62:[6, 26]_63:[6, 18, 19, 19, 20, 20, 21, 21, 22, 25]_64:[6, 17, 23, 23, 23, 24, 24]_65:[6, 16]_66:[6, 16]_67:[6, 16]_68:[7, 15]_69:[7, 15]_70:[7, 15]_71:[7, 14]_72:[7, 14]_73:[7, 14]_74:[8, 14]_75:[8, 13]_76:[8, 13]_77:[9, 13]_78:[9, 9, 10, 10, 11, 11, 12, 12]");
	private static String db = new String("0:[0, 4, 8, 37, 19, 20, 17, 4, 25, 37, 36, 20]_1:[0, 1, 1, 6, 2, 19, 3, 35, 4, 49, 5, 59, 6, 61, 7, 61, 8, 59, 9, 55, 10, 48, 11, 36, 12, 27, 13, 20, 14, 15, 15, 15, 16, 15, 17, 15, 18, 15]_4:[0, 1]_5:[2]_6:[2]_7:[2]_8:[3]_9:[3]_10:[3]_11:[3]_12:[3]_13:[4]_14:[4]_15:[4]_16:[4]_17:[4]_18:[4]_19:[4]_20:[5]_21:[5, 18]_22:[5, 18]_23:[5, 17]_24:[5, 17]_25:[5, 14, 15, 15, 16, 16]_26:[5, 13]_27:[6, 12]_28:[6, 12]_29:[6, 12]_30:[6, 11]_31:[6, 11]_32:[6, 11]_33:[6, 10]_34:[7, 10]_35:[7, 10]_36:[7, 9]_37:[7, 7, 8, 8, 9, 9]&0:[0, 20, 7, 91, 39, 11, 37, 20, 44, 91, 76, 11]_1:[0, 22, 1, 34, 2, 48, 3, 65, 4, 75, 5, 80, 6, 80, 7, 80, 8, 78, 9, 75, 10, 73, 11, 69, 12, 63, 13, 58, 14, 52, 15, 52, 16, 53, 17, 54, 18, 56, 19, 56, 20, 56, 21, 53, 22, 48, 23, 47, 24, 44, 25, 44, 26, 45, 27, 45, 28, 46, 29, 46, 30, 45, 31, 44, 32, 40, 33, 34, 34, 28, 35, 23, 36, 19, 37, 17, 38, 15]_12:[-1, 39]_13:[-1, 38]_14:[-1, 38]_15:[-1, 37]_16:[-1, 37]_17:[-1, 36]_18:[-1, 36]_19:[-1, 36]_20:[0, 1, 34]_21:[2, 34]_22:[2, 33]_23:[2, 33]_24:[2, 32]_25:[3, 29, 30, 30, 31, 31]_26:[3, 28]_27:[3, 27]_28:[3, 26]_29:[3, 26]_30:[3, 25]_31:[3, 25]_32:[3, 24]_33:[3, 24]_34:[4, 24]_35:[4, 23]_36:[4, 23]_37:[4, 22]_38:[4, 22]_39:[4, 22]_40:[4, 21]_41:[4, 20]_42:[4, 20]_43:[4, 19]_44:[4, 19]_45:[4, 18]_46:[4, 18]_47:[4, 18]_48:[4, 18]_49:[5, 17]_50:[5, 17]_51:[5, 17]_52:[5, 16]_53:[5, 16]_54:[5, 16]_55:[5, 16]_56:[5, 15]_57:[5, 15]_58:[5, 15]_59:[5, 15]_60:[5, 15]_61:[5, 14]_62:[5, 14]_63:[5, 14]_64:[5, 14]_65:[5, 14]_66:[5, 13]_67:[5, 13]_68:[6, 13]_69:[6, 13]_70:[6, 13]_71:[6, 13]_72:[6, 13]_73:[6, 12]_74:[6, 12]_75:[6, 12]_76:[6, 12]_77:[6, 11]_78:[6, 11]_79:[6, 11]_80:[6, 10]_81:[6, 10]_82:[6, 10]_83:[6, 9]_84:[7, 9]_85:[7, 9]_86:[7, 9]_87:[7, 9]_88:[7, 8]_89:[7, 8]_90:[7, 8]_91:[7, 7, 8, 8];0:[0, 1, 7, 61, 15, 15, 17, 1, 24, 61, 32, 15]_1:[0, 4, 1, 7, 2, 12, 3, 19, 4, 26, 5, 33, 6, 37, 7, 37, 8, 37, 9, 35, 10, 32, 11, 29, 12, 26, 13, 25, 14, 25]_2:[2]_3:[2]_4:[2]_5:[2]_6:[2]_7:[3]_8:[3]_9:[3]_10:[3]_11:[3]_12:[3]_13:[3]_14:[3]_15:[3]_16:[3, 14]_17:[3, 14]_18:[3, 14]_19:[3, 14]_20:[4, 14]_21:[4, 13]_22:[4, 13]_23:[4, 13]_24:[4, 13]_25:[4, 13]_26:[4, 13]_27:[4, 13]_28:[4, 12]_29:[4, 12]_30:[4, 12]_31:[4, 12]_32:[4, 12]_33:[4, 12]_34:[4, 12]_35:[4, 12]_36:[5, 12]_37:[5, 11]_38:[5, 11]_39:[5, 11]_40:[5, 11]_41:[5, 11]_42:[5, 11]_43:[5, 11]_44:[5, 11]_45:[5, 11]_46:[5, 11]_47:[5, 11]_48:[5, 11]_49:[5, 10]_50:[6, 10]_51:[6, 10]_52:[6, 10]_53:[6, 10]_54:[6, 10]_55:[6, 10]_56:[6, 9]_57:[6, 9]_58:[6, 9]_59:[6, 9]_60:[7, 8]_61:[7, 7, 8, 8]&0:[0, 15, 8, 80, 45, 1, 36, 15, 44, 80, 81, 1]_1:[0, 20, 1, 20, 2, 24, 3, 33, 4, 48, 5, 67, 6, 83, 7, 91, 8, 91, 9, 87, 10, 82, 11, 79, 12, 76, 13, 72, 14, 65, 15, 60, 16, 55, 17, 51, 18, 48, 19, 44, 20, 42, 21, 40, 22, 39, 23, 36, 24, 34, 25, 31, 26, 29, 27, 27, 28, 26, 29, 25, 30, 25, 31, 25, 32, 24, 33, 23, 34, 21, 35, 19, 36, 16, 37, 14, 38, 12, 39, 11, 40, 11, 41, 9, 42, 8, 43, 6, 44, 5]_2:[-1, 45]_3:[-1, 45]_4:[-1, 45]_5:[-1, 44]_6:[-1, 44]_7:[-1, 44]_8:[-1, 43]_9:[-1, 43]_10:[-1, 43]_11:[-1, 42]_12:[-1, 42]_13:[-1, 42]_14:[-1, 42]_15:[0, 1, 40]_16:[2, 39]_17:[2, 39]_18:[2, 38]_19:[2, 38]_20:[2, 37]_21:[2, 37]_22:[2, 37]_23:[3, 37]_24:[3, 36]_25:[3, 36]_26:[3, 36]_27:[3, 36]_28:[3, 36]_29:[3, 35]_30:[3, 35]_31:[3, 35]_32:[3, 35]_33:[3, 35]_34:[3, 35]_35:[4, 34]_36:[4, 34]_37:[4, 34]_38:[4, 34]_39:[4, 34]_40:[4, 34]_41:[4, 33]_42:[4, 33]_43:[4, 33]_44:[4, 26, 27, 33]_45:[4, 25, 28, 28, 28, 29, 32]_46:[4, 25, 30, 30, 30, 31, 31]_47:[4, 25]_48:[4, 24]_49:[5, 23]_50:[5, 23]_51:[5, 23]_52:[5, 16, 17, 23]_53:[5, 15, 18, 18, 23]_54:[5, 15, 19, 19, 22]_55:[5, 15, 20, 20, 22]_56:[5, 15, 20, 20, 20, 21, 21, 22, 22]_57:[5, 15]_58:[5, 15]_59:[5, 14]_60:[5, 14]_61:[5, 14]_62:[5, 14]_63:[5, 14]_64:[5, 13]_65:[5, 13]_66:[6, 13]_67:[6, 13]_68:[6, 13]_69:[6, 13]_70:[6, 12]_71:[6, 12]_72:[6, 12]_73:[6, 12]_74:[6, 11]_75:[6, 11]_76:[7, 10]_77:[7, 10]_78:[7, 10]_79:[7, 9]_80:[7, 7, 8, 8, 9, 9]");
	private static int i;
	private static int j;
	private static float fakeBuffer;
	private static float fakeCounter;
	private static int lowerSampleStart;
	private static int upperSampleStart;
	private static int lowerDbStart;
	private static int upperDbStart;
	private static int[] borders = new int[2];		
	private static int lengthtBuffer = 0;
	private static int multiBuffer = 0;
	private static int multiBufferCounter = 0;
	private static int lengthPercent1;
	private static int lengthPercent2;
	private static float lengthPercentTotal;
	private static float multiPercentTotal;
	private static int dbSecondRightIndex = 0;
	private static int basePercent = 20;
	private static int lengthResult;
	private static int array2Test1;
	private static int array2Test2;
	private static float array2TestPercentTotal;
	private static float array2TestBuffer;
	private static int checkCounter;
	private static int dbCounter;
	private static int[] lastCheckEgde;
	private static int[] lastDbEgde;
	private static float checkPercentLimit1;
	private static float checkPercentLimit2;
	private static float checkSpaceLimit1;
	private static float checkSpaceLimit2;
	private static int checkPercentLimit1Counter;
	private static int checkPercentLimit2Counter;
	private static int checkSpaceLimit1Counter;
	private static int checkSpaceLimit2Counter;
	private static int angle1;
	private static int angle2;
	private static int angleResult1;
	private static int angleResult2; 
	private static int checkAngle;
	private static int dbAngle;	
	private static float lengthPercent;
	private static float anglePercent;
	private static int result;
	private static float finalResult;
	private static float multiCorrectionKonstant = 1.5f;
	private static int mainLogicDebugLEVEL = 5;
	private static int mainBuilderDebugLEVEL = 3;
	

	public static void main(String[] args) {
		
		String[] checkMix = check.split(";");
		int[][][][] checkTestArray = new int [2][][][];
		int[][][][] dbTestArray = new int [2][][][];
		
		checkTestArray[0] = buildThreeDAreaArray(checkMix[0]);
		checkTestArray[1] = buildThreeDAreaArray(checkMix[1]);
		
		checkMix = db.split(";");
		dbTestArray[0] = buildThreeDAreaArray(checkMix[0]);
		dbTestArray[1] = buildThreeDAreaArray(checkMix[1]);
		
		//checkRead(checkTestArray[1]);
		mainBuilder(checkTestArray,dbTestArray);
		
	}
	
	static float mainBuilder(int[][][][] checkArray, int[][][][] db) {
		
		resetMainBuilderVariables();
		
		for( j = 0; j < checkArray[0].length ; j++ ) {
			
			if(checkArray[0][checkCounter][0][4] < 10) {
				Debug.debug(mainBuilderDebugLEVEL, "checkCounter small k: "+ j + ", checkCounter: "+checkCounter 
					+ ",chekcLength: "+checkArray[0][checkCounter][0][4]+ " "+ Arrays.toString(checkArray[0][checkCounter][0]));
				checkCounter++;
				j++;

				continue;
			}
			
			if(db[0][dbCounter][0][4] < 10) {
				
				dbCounter++;
				j--;

				Debug.debug(mainBuilderDebugLEVEL, "checkCounter small k: "+ j + ", dbCounter: "+dbCounter 
						+ ",dbLength: "+db[0][dbCounter][0][5]+ " "+ Arrays.toString(db[0][dbCounter][0]));
				continue;
			}
			
			if(getLengthPercentDiff(checkArray[0][checkCounter][0][4], db[0][dbCounter][0][4],100 ) >  30 ) {

				if(lastCheckEgde != null && lastDbEgde != null) {

					result  = freeSpaceCheck(checkArray[0][checkCounter][0][8] - lastCheckEgde[0],
							   checkArray[0][checkCounter][0][9] - lastCheckEgde[1],
							   db[0][dbCounter][0][8] - lastDbEgde[0],
							   db[0][dbCounter][0][9] - lastDbEgde[1]);		
						
					checkSpaceLimit1 += result;
					checkSpaceLimit1Counter++;
					
					Debug.debug(mainBuilderDebugLEVEL, "S 1.0 j: "+j+ ",checkSpaceLimit1 " + checkSpaceLimit1 + ", result: "+result 
							 + ", checkSpaceLimit1Counter: "+checkSpaceLimit1Counter);
				}
				
				result = mainLogic(checkArray[0][checkCounter],db[0][dbCounter]);			

				checkPercentLimit1 += result;
				checkPercentLimit1Counter++;
				
				Debug.debug(mainBuilderDebugLEVEL, "P 1.0 j: "+j+ ", checkPercentLimit1 " + checkPercentLimit1 +", result " + result +", calc; " 
						+ ", checkPercentLimit1Counter: "+checkPercentLimit1Counter);
				
				lastCheckEgde = new int[] { checkArray[0][checkCounter][0][8], checkArray[0][checkCounter][0][9] };
				lastDbEgde = new int[] { db[0][dbCounter][0][8],  db[0][dbCounter][0][9] };
				
				checkCounter++;
				dbCounter++;
			}
			
			Debug.debug(mainBuilderDebugLEVEL, "j: " +j+ ", checkPercentLimit1: " +checkPercentLimit1+ ", checkPercentLimit1Counter: "
					+checkPercentLimit1Counter +", checkPercentLimit1 AVG: " + (checkPercentLimit1 / checkPercentLimit1Counter));
			
			Debug.debug(mainBuilderDebugLEVEL, "j: " +j+ ", checkSpaceLimit1: " +checkSpaceLimit1+ ", checkSpaceLimit1Counter: "
					+checkSpaceLimit1Counter+", checkSpaceLimit1 AVG: " + (checkSpaceLimit1  / checkSpaceLimit1Counter));
		}
		
		checkCounter = 0;
		dbCounter = 0;
		lastCheckEgde = null;
		lastDbEgde = null;
		
		for( j = 0; j < checkArray[1].length ; j++ ) {
			
			if(checkArray[1][checkCounter][0][4] < 10) {
				Debug.debug(mainBuilderDebugLEVEL, "checkCounter small k: "+ j + ", checkCounter: "+checkCounter 
					+ ",chekcLength: "+checkArray[1][checkCounter][0][4]+ " "+ Arrays.toString(checkArray[1][checkCounter][0]));
				checkCounter++;
				j++;

				continue;
			}
			
			if(db[1][dbCounter][0][4] < 10) {
				
				dbCounter++;
				j--;

				Debug.debug(mainBuilderDebugLEVEL, "checkCounter small k: "+ j + ", dbCounter: "+dbCounter 
						+ ",dbLength: "+db[1][dbCounter][0][5]+ " "+ Arrays.toString(db[1][dbCounter][0]));
				continue;
			}
			
			if(getLengthPercentDiff(checkArray[1][checkCounter][0][4], db[1][dbCounter][0][4],100 ) >  30 ) {

				if(lastCheckEgde != null && lastDbEgde != null) {

					result  = freeSpaceCheck(checkArray[1][checkCounter][0][8] - lastCheckEgde[0],
							   checkArray[1][checkCounter][0][9] - lastCheckEgde[1],
							   db[1][dbCounter][0][8] - lastDbEgde[0],
							   db[1][dbCounter][0][9] - lastDbEgde[1]);		

					checkSpaceLimit2 += result;
					checkSpaceLimit2Counter++;
					
					Debug.debug(mainBuilderDebugLEVEL, "S 2.0 j: "+j+ ",checkSpaceLimit2 " + checkSpaceLimit2 + ", result: "+result 
						+ ", checkSpaceLimit2Counter: "+checkSpaceLimit2Counter);
				}
				
				result = mainLogic(checkArray[1][checkCounter],db[1][dbCounter]);
				
				checkPercentLimit2 += result;
				checkPercentLimit2Counter++;
				
				Debug.debug(mainBuilderDebugLEVEL, "P 2.0 j: "+j+ ", checkPercentLimit2 " + checkPercentLimit2 +", result " + result +", calc; " 
						+ ", checkPercentLimit2Counter: "+checkPercentLimit2Counter);
				
				lastCheckEgde = new int[] { checkArray[1][checkCounter][0][8], checkArray[1][checkCounter][0][9] };
				lastDbEgde = new int[] { db[1][dbCounter][0][8],  db[1][dbCounter][0][9] };
				
				checkCounter++;
				dbCounter++;
			}
			
			Debug.debug(mainBuilderDebugLEVEL, "j: " +j+ ", checkPercentLimit2: " +checkPercentLimit2+ ", checkPercentLimit2Counter: "
					+checkPercentLimit2Counter +", checkPercentLimit2 AVG: " + (checkPercentLimit2 / checkPercentLimit2Counter));
			
			Debug.debug(mainBuilderDebugLEVEL, "j: " +j+ ", checkSpaceLimit2: " +checkSpaceLimit2+ ", checkSpaceLimit2Counter: "
					+checkSpaceLimit2Counter+", checkSpaceLimit2 AVG: " + (checkSpaceLimit2  / checkSpaceLimit2Counter));
		}
		
		Debug.debug(mainBuilderDebugLEVEL, "P 1: "+(checkPercentLimit1 / checkPercentLimit1Counter)+", S 1: "+(checkSpaceLimit1  / checkSpaceLimit1Counter)
				+", P 2: "+(checkPercentLimit2 / checkPercentLimit2Counter)+", S 2: "+(checkSpaceLimit2  / checkSpaceLimit2Counter));
		
		finalResult = (((float) ((float) (checkPercentLimit1 / checkPercentLimit1Counter) / 100)
				* ((float) (checkSpaceLimit1  / checkSpaceLimit1Counter) / 100) 
				* ((float) (checkPercentLimit2 / checkPercentLimit2Counter) / 100) 
				* ((float) (checkSpaceLimit2  / checkSpaceLimit2Counter) / 100))*100);
		Debug.debug(mainBuilderDebugLEVEL,"MainBuilder result: "+finalResult);
		return 0;
	}

	private static int freeSpaceCheck(int checkLength, int checkHeight, int dbLength, int dbHeight) {
		
		Debug.debug(mainBuilderDebugLEVEL,"freeSpaceCheck checkLength "+checkLength + ", checkHeight: "
				+ checkHeight+ ", dbLength: "+dbLength+ ", dbHeight "+dbHeight );
		
		checkAngle = getDegree(checkHeight, checkLength);
		dbAngle = 	getDegree(dbHeight, dbLength);	
		lengthPercent = getLengthPercentDiff(checkLength, dbLength,30);
		anglePercent = gerAnglePercentDiff(checkAngle,dbAngle,45);
		
		result = (int) (((float) ((float) lengthPercent / 100) * ((float) anglePercent / 100))*100);
		
		Debug.debug(mainBuilderDebugLEVEL,"freeSpaceCheck checkAngle "+checkAngle + ", dbAngle: "
				+ dbAngle+ ", lengthPercent: "+lengthPercent+ ", anglePercent "+anglePercent +", result: "+result);
		return result;
	} 
	
	static int  mainLogic(int[][] input,  int[][] db) {
		
		resetMainLogicVariables();
		
		borders[0] = lowerSampleStart <  lowerDbStart ? lowerSampleStart : lowerDbStart;
		borders[1] = upperSampleStart >  upperDbStart ? upperSampleStart : upperDbStart;

		Debug.debug(mainLogicDebugLEVEL,"input[0]"+Arrays.toString(input[0])+", db[0]: "+Arrays.toString(db[0]));
		Debug.debug(mainLogicDebugLEVEL, "\nw.border 0: "+borders[0]+", w.border 1: "+borders[1]);

		for(i = borders[0]+2; i < 100; i++ ) {
			
			if(i+1 < input.length && i+1 < db.length) {
				Debug.debug(mainLogicDebugLEVEL,"\n0.0 i: " + i + ", Sample Array i-1: " +Arrays.toString(input[i-1])+ ", Sample Array i: " +Arrays.toString(input[i])+ ", Sample Array i+1: " +Arrays.toString(input[i+1])
				+ ", db Array i-1: " +Arrays.toString(db[i-1])+ ", db Array i: " +Arrays.toString(db[i])+ ", db Array i+1: " +Arrays.toString(db[i+1]));
			}
			
			lengthPercent1 = 0;
			lengthPercent2 = 0;
			lengthPercentTotal = 0;
			array2Test1 = 0;
			array2Test2 = 0;
			multiPercentTotal = 0;
			dbSecondRightIndex = 0;

			if(i+1 > input.length-1 || i+1 > db.length-1 ||input[i] == null || db[i] == null) {
								
				if((i > input.length-1 && i > db.length-1 ) || (i > input.length-1 && i > db.length-1 && input[i] == null && db[i] == null && multiBufferCounter > 3 )) {
					Debug.debug(mainLogicDebugLEVEL,"BREAK !!!!");
					break;
				}
				
				if((i+1 > input.length-1 || i+1 > db.length-1 || input[i] != null && db[i] == null) || (input[i] == null && db[i] != null)) {
				
					addToFakeBuffer(multiBufferCounter,1);
					Debug.debug(mainLogicDebugLEVEL,"0.1 i " + i+ ", lengthBufferCounter: " + (multiBufferCounter + fakeBuffer) +", lengthtBuffer:  "+ lengthtBuffer 
							+ ",           L_AVG: " + (lengthtBuffer / (multiBufferCounter + fakeBuffer)));
					
					Debug.debug(mainLogicDebugLEVEL,"0.2 i " + i+ ", lengthBufferCounter: " + (multiBufferCounter + fakeBuffer) +", array2TestBuffer:  "+ array2TestBuffer 
							+ ", S_AVG: " + (array2TestBuffer / (multiBufferCounter + fakeBuffer)));
								
					Debug.debug(mainLogicDebugLEVEL,"0.3 i " + i+ ", angleLengthBufferCounter: " + (multiBufferCounter+ fakeBuffer) +", multiBuffer:  "+ multiBuffer 
							+ ",         LS_AVG: " + (multiBuffer / (multiBufferCounter + fakeBuffer)));	
				}	
				continue;
			}
			
			if(input[i].length > 1 && input[i][0] != input[i][1]+1 ) {
				
				if(input[i][0] != -1 ) {
									
					if(db[i][0] != -1 && db[i].length > 1) {
						
						lengthPercent1 = getLengthPercentDiff(input[0][4] - db[i][0], db[0][4] - input[i][0],basePercent);
						lengthPercent2 = getLengthPercentDiff(input[i][input[i].length-1], db[i][db[i].length-1],basePercent);
						lengthPercentTotal = ((float) ((float) lengthPercent1 / 100) * ((float) lengthPercent2 / 100));
						lengthtBuffer += lengthPercentTotal * 100;
						

						if(input[i][0] > 0  && db[i][0] > 0) {
							
							array2Test1 = getLengthPercentDiff(input[1][input[i][0]*2-1], db[1][db[i][0]*2-1],basePercent);
							
							dbSecondRightIndex = db[i][db[i].length - 1] < db[1].length/2 ? db[1][ db[i][db[i].length - 1] *2 - 1] : db[1][ db[1].length - 1];
							array2Test2 = getLengthPercentDiff(input[1][ input[i][input[i].length - 1] *2 - 1], dbSecondRightIndex, 45);
							
							
												
//							System.out.println("Arr "+Arrays.toString(db[1]));
//							System.out.println("Arr "+Arrays.toString(db[i]));
//				
//							System.out.println("1.0 .i: "+i+", "+ (db[1][db[i][0]*2-1]));
//							System.out.println("2.1 "+ (db[1][ db[i][db[i].length - 1] *2 - 1]));
//							System.out.println("2.2 "+ (db[1][ db[1].length - 1]));
							
							System.out.println("3.0 1: " +(db[i][db[i].length - 1]) + ", 2: " + db[1].length/2);
			

							
							array2TestPercentTotal = ((float) ((float) array2Test1 / 100) * ((float) array2Test2 / 100));
							array2TestBuffer += array2TestPercentTotal * 100;
							
							multiPercentTotal = ((float) ((float) lengthPercentTotal) * ((float) array2TestPercentTotal));	
							multiBuffer +=  multiPercentTotal * 100 * multiCorrectionKonstant;
						}
						
						multiBufferCounter++;
						
						Debug.debug(mainLogicDebugLEVEL,"1.1 lengthPercent1: "+lengthPercent1 +", lengthPercent2: " +lengthPercent2 + ", lengthPercentTotal: "+lengthPercentTotal
								+ ", lengthBufferCounter counters: " + multiBufferCounter +", lengthtBuffer:  "+ lengthtBuffer 
								+ ", L_AVG: " + (lengthtBuffer / (multiBufferCounter + fakeBuffer)));
						
						Debug.debug(mainLogicDebugLEVEL,"1.2 array2Test1: "+array2Test1 +", array2Test2: " +array2Test2 + ", array2TestPercentTotal: "+array2TestPercentTotal
								+ ", lengthBufferCounter counters: " + multiBufferCounter +", array2TestBuffer:  "+ array2TestBuffer 
								+ ", S_AVG: " + (array2TestBuffer / (multiBufferCounter + fakeBuffer)));
						
						Debug.debug(mainLogicDebugLEVEL,"1.3 multiLengthPercentTotal: "+multiPercentTotal +", multiBuffer: " +multiBuffer
								+ ", LS_AVG: " + (multiBuffer / (multiBufferCounter + fakeBuffer)));
											
						continue;
					}
					
					if(db[i][0] == -1) 
						addToFakeBuffer(multiBufferCounter,1);
				}				
			}	
							
			if(input[i][0] == -1) {
				
				if(db[i].length > 1) {
					
					lengthPercent2 = getLengthPercentDiff(input[i][input[i].length-1], db[i][db[i].length-1],basePercent);				
					lengthtBuffer += lengthPercent2;
				
					array2Test2 = getLengthPercentDiff(input[1][ input[i][input[i].length - 1] *2 - 1], db[1][ db[i][db[i].length - 1] *2 - 1], 45);
					array2TestBuffer += array2Test2;
					
					multiPercentTotal = ((float) ((float) lengthPercent2 / 100) * ((float) array2Test2 / 100));
					multiBuffer += multiPercentTotal * 100 * multiCorrectionKonstant;
					multiBufferCounter++;
			
					Debug.debug(mainLogicDebugLEVEL,"3.1 i " + i+ ", lengthPercent1: "+lengthPercent1
							+ ", lengthBufferCounter counters: " + (multiBufferCounter + fakeBuffer) +", lengthtBuffer:  "+ lengthtBuffer 
							+ ", L_AVG: " + (lengthtBuffer / (multiBufferCounter + fakeBuffer)));
					
					Debug.debug(mainLogicDebugLEVEL,"3.2 i " + i+ ", array2Test2: "+array2Test2
							+ ", lengthBufferCounter counters: " + (multiBufferCounter) +", array2TestBuffer:  "+ array2TestBuffer 
							+ ", S_AVG: " + (array2TestBuffer / (multiBufferCounter + fakeBuffer)));
					
					Debug.debug(mainLogicDebugLEVEL,"3.3 i " + i+ ", multiLengthPercentTotal: "+multiPercentTotal +", multiBuffer: " +multiBuffer
							+ ", LS_AVG: " + (multiBuffer / (multiBufferCounter + fakeBuffer)));
				}
				
				if(db[i].length == 1) {
					addToFakeBuffer(multiBufferCounter, 3);
				}
				continue;
			}

			
			if((input[i].length == 1 || input[i][0] == input[i][1]+1 ) && db[i][0]> 0 ) {
				
				if(db[i].length == 1  || (db[i].length >= 1 && db[i][0] != -1 )) {
					
					lengthPercent1 = getLengthPercentDiff(input[0][4] - db[i][0], db[0][4] - input[i][0],basePercent);
					lengthtBuffer += lengthPercent1;
						
					System.out.println("Arr "+Arrays.toString(db[1]));
					System.out.println("Arr "+Arrays.toString(db[i]));
		
					System.out.println("1.0 .i: "+i+", "+ (db[1][ db[i][0] *2 - 1]));
					System.out.println("2.1 "+ (db[1].length/2));
					System.out.println("2.2 "+ (db[1][ db[1].length - 1]));
					
					dbSecondRightIndex = db[1][db[i][0]] < db[1].length/2 ? db[1][ db[i][0] *2 - 1] : db[1][ db[1].length - 1];
					


					array2Test1 = getLengthPercentDiff(input[1][input[i][0]*2-1], dbSecondRightIndex, basePercent);
					array2TestBuffer += array2Test1;
							
					multiPercentTotal = ((float) ((float) lengthPercent1 / 100) * ((float) array2Test1 / 100));	
					multiBuffer +=  multiPercentTotal * 100 * multiCorrectionKonstant;
					
					multiBufferCounter++;

					Debug.debug(mainLogicDebugLEVEL,"4.1 lengthPercent1: "+lengthPercent1
							+ ", lengthBufferCounter counters: " + (multiBufferCounter + fakeBuffer) +", lengthtBuffer:  "+ lengthtBuffer 
							+ ", L_AVG: " + (lengthtBuffer / (multiBufferCounter + fakeBuffer)));
					
					Debug.debug(mainLogicDebugLEVEL,"4.2 array2Test1: "+array2Test1
							+ ", lengthBufferCounter counters: " + (multiBufferCounter) +", array2TestBuffer:  "+ array2TestBuffer 
							+ ", S_AVG: " + (array2TestBuffer / (multiBufferCounter + fakeBuffer)));
					
					Debug.debug(mainLogicDebugLEVEL,"4.3 multiLengthPercentTotal: "+multiPercentTotal +", multiBuffer: " +multiBuffer
							+ ", LS_AVG: " + (multiBuffer / (multiBufferCounter + fakeBuffer)));
				}
					if(( db[i].length > 1 && db[i][0] == -1)) 
						addToFakeBuffer(multiBufferCounter, 4);
					continue;
			}						
		}
			return (int) (multiBuffer / (multiBufferCounter + fakeBuffer));
	}
	
	private static void addToFakeBuffer(int lengthCounter, int source) {
			
		fakeBuffer +=  fakeCounter *((float)lengthCounter / 50);
		fakeCounter *=1.05;		
		
		Debug.debug(mainLogicDebugLEVEL,"\n ADDToFAKEBuffer fakeCounter: "+ fakeCounter + ", fakeBuffer: "+fakeBuffer  + ", Source: "+source+ "\n");
	}
	
	
	static int getLengthPercentDiff(int input1, int input2, int baseHundredPercent) {
					

		lengthResult =  (((baseHundredPercent - Math.abs(input1 - input2)) *100 ) / baseHundredPercent);

		Debug.debug(mainLogicDebugLEVEL,"getLengthPercentDiff input1: "+ input1+ ", input2: "+input2 + ", onePercent: "
				+ ", lengthResult: "+ lengthResult );
		
		return lengthResult;
	}
	
	static int gerAnglePercentDiff(int inputAngle1, int inputAngle2,int inputCheckComapre) {
		
		angle1 = inputAngle1 + 90;
		angle2 = inputAngle2 + 90;
		angleResult1 = (((inputCheckComapre - (Math.abs(angle1 - angle2))) * 100 ) / inputCheckComapre);
		angleResult2 = angleResult1 > 0 ?  angleResult1 : 0; 
		
		Debug.debug(mainLogicDebugLEVEL,"gerAnglePercentDiff inputAngle1: "+ inputAngle1+ ", inputAngle2: "+inputAngle2 
			+", limit for 100%: "+inputCheckComapre + ", angleResult: "+ angleResult2 );
		
		return angleResult2;
	}

	public static int getDegree(int y, int x) {
		
		result =  (int) Math.toDegrees(Math.atan2(y,x));
		Debug.debug(mainLogicDebugLEVEL,"getDegree height: " +y +", length: "+x +", degree: "+result);
		
		return result;
	}

	public static void resetMainLogicVariables() {
				
		lengthtBuffer = 0;
		array2TestBuffer = 0;
		multiBuffer = 0;
		
		multiBufferCounter = 0;
		
		fakeBuffer = 1;
		fakeCounter = 1;
	
	}
	
	public static void resetMainBuilderVariables() {

		checkCounter = 0;
		dbCounter = 0;
		lastCheckEgde = null;
		lastDbEgde = null;
		checkPercentLimit1 = 0;
		checkPercentLimit2 = 0;
		checkSpaceLimit1 = 0;
		checkSpaceLimit2 = 0;
		checkPercentLimit1Counter = 0;
		checkPercentLimit2Counter = 0;
		checkSpaceLimit1Counter = 0;
		checkSpaceLimit2Counter = 0;
	}
	
	public static int[][][] buildThreeDAreaArray(String input) {

		String[] substrs1;
		String[] substrs2;
		String[] substrs3;
		String substr;
		int[] array;
		int[][][] threeDArray;
		
		System.out.println("buildThreeDAreaArray input "+input);
		String[] sequences = input.split("&");
		threeDArray = new int[sequences.length][][];
		System.out.println("buildThreeDAreaArray Seq lenght: "+sequences.length);
		
		for(int i = 0; i < sequences.length; i++) {
			
			substrs1 = sequences[i].split("_");
			substrs2 = substrs1[substrs1.length-1].split(":");			
			threeDArray[i] = new int[Integer.valueOf(substrs2[0])+1][];
			//System.out.println("\ni length " +i + ", "+threeDArray[i].length );
			
			for(int j = 0; j < substrs1.length ; j++) { 
				
				substrs2 = substrs1[j].split(":");		
				substr = substrs2[1].substring(1, substrs2[1].length()-1);	
				substrs3 = substr.split(", ");				
				array = new int[substrs3.length];
				
				for( int k = 0; k < substrs3.length; k++) {

					array[k] = Integer.valueOf(substrs3[k]);
				}
					threeDArray[i][Integer.valueOf(substrs2[0])] = array;
					//System.out.println("Array i: "+i+ ", j: "+ Integer.valueOf(substrs2[0])+ ", Array "+ Arrays.toString(array) + ", Check: "+substr);
			}
		}	
		return threeDArray;
	}
	
	public static void checkRead(int[][][] input) {
		
		for(int i = 0; i < input.length; i++) 
			for(int j = 0; j < input[i].length; j++) 
				System.out.println("checkRead i "+i+", k: "+j+", Array "+ Arrays.toString(input[i][j]));
	}
}
