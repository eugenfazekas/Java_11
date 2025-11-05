package com.audio7.threads.util;

import java.util.Set;

import com.audio8.util.Debug;

public class ThreadUtil{

	public static void printThreads(String location) {
		
		Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
		
		for (Thread t : threadSet) 
			Debug.debug(3,"ThreadUtil printThreads Location: "+location + " " +t.getName());		
	}
}
