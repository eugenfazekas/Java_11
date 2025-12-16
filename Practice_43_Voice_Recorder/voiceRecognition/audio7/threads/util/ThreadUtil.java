package voiceRecognition.audio7.threads.util;

import java.util.Set;

import voiceRecognition.audio8.util.Debug;

public class ThreadUtil{
	
	private static int debud_level_DEBUG = 5;

	public static void printThreads(String location) {
		
		Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
		
		for (Thread t : threadSet) 
			Debug.debug(debud_level_DEBUG,"ThreadUtil printThreads Location: "+location + " " +t.getName());		
	}
}
