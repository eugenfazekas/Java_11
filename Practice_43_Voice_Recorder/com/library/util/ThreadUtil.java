package com.library.util;

import java.util.Set;

public class ThreadUtil {

	public static void printThreads(String location) {
		
		Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
		for (Thread t : threadSet) {
			Debug.debug(3,"ThreadUtil printThreads Location: "+location + " " +t.getName());
		}
	}
}
