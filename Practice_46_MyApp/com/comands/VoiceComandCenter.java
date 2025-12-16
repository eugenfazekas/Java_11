package com.comands;

import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class VoiceComandCenter {
	
	public VoiceComandCenter() {
		
		buildVoiceComnadList();
	}
	
	private static int readedVoiceCounter = 0;
	private static final String[] tvBekapcs = new String[] {"Tv","Bekapcs"};
	private static final String[] stopVLC = new String[] {"Tv","Leall"};
	
	public static Map<Integer, String> readedVoiceArrray = 
			new ConcurrentHashMap<Integer, String>();
	
	private static final Map<String, String[]> definedVoiceComands 
		= new ConcurrentHashMap<String, String[]>();
		
	public static AtomicBoolean added = new AtomicBoolean(false); 
				
	private static boolean endBreak;	
	private static int checkCounter;	
	private static int[] removeArray;
	private static String result;
	public static long lastAdd;
	
	public static void addToReadedVoiceArray(String readedVoice) {
		
		System.out.println("Added to addToReadedVoiceArray " + readedVoice);
		readedVoiceArrray.put(readedVoiceCounter++, readedVoice);
		added.set(true);
		lastAdd = System.currentTimeMillis();
	}
	
	public static void deleteOldVoices() {
		
		readedVoiceArrray = new ConcurrentHashMap<Integer, String>();
	}
	
	public static String checkReadedVoices() {
		
		if(!added.get() || readedVoiceArrray.size() == 0)
			return null;
		
		added.set(false);
		
		return voiceComandCheck();
	}

	public static String voiceComandCheck() {
		
		endBreak = false;
		result = null;
		
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
						//ClassMethodsExecuter.checkExecuteClassForMethod(voiceComandsEntry.getKey());
						removeFindedVoices();
						result = voiceComandsEntry.getKey();
						break;
					}
				}			
			}
		}
			return result;
	}
	
	private static void removeFindedVoices() {
		
		for(int i = 0; i < checkCounter; i++ )
			readedVoiceArrray.remove(removeArray[i]);
	}
	
	public static void buildVoiceComnadList() {
		
		System.out.println("Building voiceCommnads ");
		definedVoiceComands.put("tvBekapcs", tvBekapcs);
		definedVoiceComands.put("stopVLC", stopVLC);

	}
}
