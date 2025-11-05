package com.audio7.threads;

import java.util.HashSet;
import java.util.Set;

import com.audio8.util.Debug;

public class ThreadManagement {

	private static boolean keepThread;
	
	private static Set<MyThread> threads = new HashSet<MyThread>();
	
	public static void addingThread(MyThread thread) {
		
		Debug.debug(1, "ThreadManagement Adding thread: "+thread.getThreadName());
		
		threads.add(thread);
	}
	
	public static void suspendThreadByName(String threadName) {
				
		for (MyThread thread : threads) 
			if(thread.getThreadName().equals(threadName))
				thread.suspendThread();			
	}
	
	public static void stopAndRemoveThreadByName(String threadName) {
		
		for (MyThread thread : threads) 
			if(thread.getThreadName().equals(threadName)) {
				thread.stopThread();
				boolean threadStopped = threads.remove(thread);
				Debug.debug(1, "ThreadManagement removed thread "+threadName+" "+threadStopped 
					+ ",Thread Size: "+threads.size() );
				break;
			}
	}
	
	public static void enableThreadByName(String threadName) {
			
		for (MyThread thread : threads) 
			if(thread.getThreadName().equals(threadName))
				thread.stopSuspendThread();
	}
	
	public static void suspendAllThreadsWithExcluded(String[] keepThreadsAlive) {
		
		Debug.debug(1, "ThreadManagement starting suspendig threads!");
		
		for (MyThread thread : threads) {
			
			keepThread = false;
			Debug.debug(1, "ThreadManagement stopAllWithExcludedThreads thradName: "
					+thread.getThreadName());
			
			for (String threadAlive : keepThreadsAlive) {					
				if(thread.getThreadName().equals(threadAlive))
					keepThread = true;
			}
			
			if(!keepThread) {
				thread.setSuspendThread();
				Debug.debug(1, "ThreadManagement thread: " +thread.getThreadName()+" is suspended!");
			}
		}
	}
	
	public static void enableAllThreads() {
		
		Debug.debug(1, "ThreadManagement Enabling All Threads");
		
		for (MyThread thread : threads) 
				thread.stopSuspendThread();
	}
	
	public static void stopAllThreads() {

		Debug.debug(1, "ThreadManagement Stoping All Threads");
		for (MyThread thread : threads) {
				thread.stopThread();
				Debug.debug(1, "ThreadManagement Stopping "+ thread.getThreadName());	
		}
	}
	
	public static int getThreadsCount() {
		
		return threads.size() ; 
	}
}
