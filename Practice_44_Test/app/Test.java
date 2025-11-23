package app;

import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;


public class Test {
	
	private static final String[] tvBekapcs = new String[] {"tv","bekapcs"};
	private static final String[] tvKikapcs = new String[] {"tv","kikapcs"};
	
	private static final String test1 = "bekapcs";
	private static final String test2 = "tv";
	private static final String test3 = "bla";
	
	public static void main(String[] args) {

		buildVoiceComnadList();
		voiceComandCheck();
		System.out.println("run ended");
	}
	
	public static Map<Integer, String> readedVoiceArrray = 
			new ConcurrentHashMap<Integer, String>();
	
	private static final Map<String, String[]> definedVoiceComands 
		= new ConcurrentHashMap<String, String[]>();
		
	private static boolean endBreak;
	
	private static int checkCounter;	
	private static int[] removeArray;

	public static void voiceComandCheck() {
		

		endBreak = false;
		
		for(Entry<String, String[]> voiceComandsEntry: definedVoiceComands.entrySet()) {
			
			if(endBreak)
				break;
			
			checkCounter = 0;
			removeArray = new int[5];
			
			System.out.println("\nArr " +Arrays.toString(voiceComandsEntry.getValue()));
			
			for(int i = 0; i < voiceComandsEntry.getValue().length; i++) {
				
				for(Map.Entry<Integer, String> voiceEntry: readedVoiceArrray.entrySet()) {

					if(voiceComandsEntry.getValue()[i].equals(voiceEntry.getValue()))				
						removeArray[checkCounter++] = voiceEntry.getKey();					
					
					if(checkCounter == voiceComandsEntry.getValue().length) {
						System.out.println("Finded DB: " + Arrays.toString(voiceComandsEntry.getValue()) +", redeadVoices: "+voiceEntry.getValue());
						endBreak = true;
						break;
					}
						//ClassMethodsExecuter.checkExecuteClassForMethod(voiceComandsEntry.getKey());
				}			
			}
		}
		removeFindedVoices();
	}
	
	private static void removeFindedVoices() {
		
		for(int i = 0; i < checkCounter; i++ )
			readedVoiceArrray.remove(removeArray[i]);
	}
	
	public static void buildVoiceComnadList() {
		
		definedVoiceComands.put("tvBekapcs", tvBekapcs);
		definedVoiceComands.put("tvKikapcs", tvKikapcs);
		
		readedVoiceArrray.put(0, test1);
		readedVoiceArrray.put(1, test2);
		readedVoiceArrray.put(2, test3);
	}
}
