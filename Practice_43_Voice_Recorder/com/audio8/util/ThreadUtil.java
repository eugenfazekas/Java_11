package com.audio8.util;

import java.util.Set;

public class ThreadUtil {

	public static void printThreads(String location) {
		
		Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
		for (Thread t : threadSet) {
			Debug.debug(3,"ThreadUtil printThreads Location: "+location + " " +t.getName());
		}
	}
	
	public static void sleepThreadInMilisec(int milisec) {
		try {
			Thread.sleep(milisec);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
