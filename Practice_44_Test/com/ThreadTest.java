package com;

import java.util.ArrayList;
import java.util.List;

public class ThreadTest {
	
	
	public static long id;
	public static List<Thread> threads = new ArrayList<>();
	
	public static void main(String[] args) {

		id = Thread.currentThread().getId();
		System.out.println("adding thread");
		threads.add(Thread.currentThread());
		System.out.println("stoping thread");
		deleteLastThread();
		
		while(true) {
			
			
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("while 1000");
		}
	}

	public static void stopThread(long threadId, String threadName) {
		
		
		for (Thread thread : threads) {
			
		    if(thread.getId() == threadId || thread.getName().equals(threadName) ){
		    	System.out.println("Thread Id: "+thread.getId() +", Thread Name: "+thread.getName());
    			thread.interrupt();
		    	while(!thread.isInterrupted()){
		    		try {
	

		    			Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    	}
		    	
		    }
		}
	}
	
	public static void deleteLastThread() {
		 
		 stopThread(threads.get(threads.size()-1).getId()
			,threads.get(threads.size()-1).getName());
	 }
}
