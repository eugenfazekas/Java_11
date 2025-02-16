package com.audio8.util.thread;

import java.util.Set;

import com.audio8.util.Debug;

public class ThreadUtil{

	public static void printThreads(String location) {
		
		Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
		
		for (Thread t : threadSet) 
			Debug.debug(3,"ThreadUtil printThreads Location: "+location + " " +t.getName());		
	}
	
	public static void sleepThreadInMilisec(int milisec) {
		
		try {
			Thread.sleep(milisec);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public static void suspendThreadCheck(MyThread myThread) {
		
		if(myThread.isThreadSuspended() == false) return;
		
		else {		

			while(myThread.isThreadSuspended() == true) {
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
			}
		}
	}
}
