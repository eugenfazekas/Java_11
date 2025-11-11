package com.audio7.threads;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import com.audio0.main.AppSetup;
import com.audio1.logical.EntryPointMethods;
import com.audio7.threads.util.ThreadObjectDetails;
import com.audio8.util.Debug;

public class ThreadManagement implements MyThread{
	
	private boolean threadIsActive;
	private boolean threadIsSuspended;
	private Thread thread;
	final private String THREAD_NAME = "ThreadManagement"; 
	private ThreadObjectDetails threadObject; 
	
	private static Set<MyThread> threads = new HashSet<MyThread>();
	
    public static ConcurrentLinkedQueue<ThreadAction> threadActions = new ConcurrentLinkedQueue<>();

	public static AtomicBoolean building = new AtomicBoolean(false);
	private String svitch;
	
	private static int debug_level_INFO = 5;
	private static int debud_level_DEBUG = 5;
		
	public ThreadManagement() {
		
		threadObject = new ThreadObjectDetails(THREAD_NAME, false);		
		threadIsActive = true;
		threadIsSuspended = false;
		thread = new Thread(this);		
		thread.start();
	}
	
	private void checkThreadActions() {
		
		Debug.debug(debud_level_DEBUG,"ThreadManagement checkThreadActions threadActions.length: "
				+threadActions.size());
		
		while(!threadActions.isEmpty()) {
			initSwitch(threadActions.poll());	
		}
	}
	
	@Override
	public void run() {	
		
		Thread.currentThread().setName(THREAD_NAME);		
		addingThread(this);
		
		Debug.debug(1,"Starting "+Thread.currentThread().getName() +" Thread!");
		
		while(threadIsActive) {

			suspendThread();
			
			 try {		    
					checkThreadActions();
		    
		    } catch (Exception ex) {
		    	
	            Debug.debug(1,"DebugException in ThreadManagement! " +ex.getMessage());  

	    		ThreadManagement.threadActions.add(
	    				new ThreadAction("stopAllThreads",-1,EntryPointMethods.getSvitch(),this));
		    }
						
			sleepThread(AppSetup.RECORDER_MILISEC_BUFFER_LENGTH);					
		}		
	}
	
	private void initSwitch(ThreadAction threadAction) {
		
		svitch = threadAction.getActionName();
		
		switch(svitch) {
				
			case "addingThread" :addingThread(threadAction.getThread());break;
			
			case "suspendThreadById" :suspendThreadById(threadAction.getThreadId());break;
			
			case "stopAndRemoveThreadById":stopAndRemoveThreadById(threadAction.getThreadId());break;
			
			case "enableThreadById" :enableThreadById(threadAction.getThreadId());break;
						
			case "suspendThreadsByApplicationName" 
						:suspendThreadsByApplicationName(threadAction.getApplicationName());break;	
			
			case "stopThreadsByApplicationName" 
						:stopThreadsByApplicationName(threadAction.getApplicationName());break;
			
			case "enableThreadsByApplicationName" 
						:enableThreadsByApplicationName(threadAction.getApplicationName());break;
			
			case "enableAllThreads" : enableAllThreads();break;
			
			case "stopAllThreads" : stopAllThreads();break;	
		}
	}
	
	private void addingThread(MyThread thread) {
		
		Debug.debug(debug_level_INFO, "ThreadManagement Adding thread: "
				+thread.getThreadObjectDetails().getThreadName());
		
		threads.add(thread);
	}
	
	private void suspendThreadById(int threadId) {
				
		for (MyThread thread : threads) 
			if(thread.getThreadObjectDetails().getId() == threadId )
				thread.setSuspendThread();			
	}
	
	private void stopAndRemoveThreadById(int threadId) {
		
		for (MyThread thread : threads) 
			if(thread.getThreadObjectDetails().getId() == threadId) {
				thread.stopThread();
				boolean threadStopped = threads.remove(thread);
				
				Debug.debug(debug_level_INFO, "ThreadManagement removed thread "
					+thread.getThreadObjectDetails().getThreadName()+" "+threadStopped 
					+ ",Thread Size: "+threads.size() );
				break;
			}
	}
	
	private void enableThreadById(int threadId) {
			
		for (MyThread thread : threads) {
			
			if(thread.getThreadObjectDetails().getId() == threadId) {
				thread.stopSuspendThread();
				
				Debug.debug(debug_level_INFO, "ThreadManagement enableThreadById "
					+thread.getThreadObjectDetails().getThreadName()); 
			}
		}
	}
		
	private void suspendThreadsByApplicationName(String applicationName) {
		
		for (MyThread thread : threads) {
		
			if(thread.getThreadObjectDetails().getApplicationName().equals(applicationName) 
				&& thread.getThreadObjectDetails().isSuspendable()) {
				
				thread.setSuspendThread();

				Debug.debug(debug_level_INFO, "ThreadManagement suspendThreadsByApplicationName thread: " 
					+thread.getThreadObjectDetails().getThreadName()+" is suspended!");
			}
		}
	}
	
	private void stopThreadsByApplicationName(String applicationName) {
		
		for (MyThread thread : threads) {
			
			if(thread.getThreadObjectDetails().getApplicationName().equals(applicationName) 
				&& thread.getThreadObjectDetails().isSuspendable()) {
				
				thread.stopThread();

				Debug.debug(debug_level_INFO, "ThreadManagement stopThreadsByApplicationName thread: " 
					+thread.getThreadObjectDetails().getThreadName()+" is stoped!");
			}
		}
	}
	
	private void enableThreadsByApplicationName(String applicationName) {
		
		for (MyThread thread : threads) {
			
			if(thread.getThreadObjectDetails().getApplicationName().equals(applicationName) 
				&& thread.getThreadObjectDetails().isSuspendable()) {
				
				thread.stopSuspendThread();

				Debug.debug(1, "ThreadManagement enableThreadsByApplicationName thread: " 
					+thread.getThreadObjectDetails().getThreadName()+" is enabled!");
			}
		}
	}
	
	private void enableAllThreads() {
		
		Debug.debug(debug_level_INFO, "ThreadManagement Enabling All Threads");
		
		for (MyThread thread : threads) 
				thread.stopSuspendThread();
	}
	
	private void stopAllThreads() {

		Debug.debug(debug_level_INFO, "ThreadManagement Stoping All Threads");
		
		for (MyThread thread : threads) {
				thread.stopThread();
				Debug.debug(debug_level_INFO, "ThreadManagement Stopping "
					+ thread.getThreadObjectDetails().getThreadName());	
		}
	}
	
	public static int getThreadsCount() {
		
		return threads.size(); 
	}
	
	@Override
	public void stopThread() {	
		
		threadIsActive = false;
	}
	
	@Override
	public void suspendThread() {

		while(isThreadSuspended()) {
			
			sleepThread(50);
		}
	}
	
	@Override
	public void setSuspendThread() {
		
		threadIsSuspended = true;		
	}

	@Override
	public void stopSuspendThread() {
		
		threadIsSuspended = false;
	}

	@Override
	public Thread getThread() {

		return thread;
	}

	@Override
	public boolean isThreadSuspended() {
		
		return threadIsSuspended;
	}
	
	public void sleepThread(int mSec) {
		
		try {
			Thread.sleep(mSec);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	@Override
	public ThreadObjectDetails getThreadObjectDetails() {

		return threadObject;
	}
}	
	/*
	public int[] suspendAllThreadsWithExcluded(int[] keepThreadsAlive) {
		
		Debug.debug(1, "ThreadManagement starting suspendig threads!");
		
		boolean keepThread;
		int[] temp1 = new int[100];
		int counter = 0;
		for (MyThread thread : threads) {
			
			keepThread = false;
			Debug.debug(1, "ThreadManagement stopAllWithExcludedThreads thradName: "
					+thread.getThreadObjectDetails().getThreadName());
			
			for (int threadAlive : keepThreadsAlive) {					
				if(thread.getThreadObjectDetails().getId() == threadAlive)
					keepThread = true;
			}
			
			if(!keepThread && thread.getThreadObjectDetails().isSuspendable()) {
				thread.setSuspendThread();
				temp1[counter++] = thread.getThreadObjectDetails().getId();
				Debug.debug(1, "ThreadManagement thread: " 
						+thread.getThreadObjectDetails().getThreadName()+" is suspended!");
			}
		}
		
		int[] temp2 = new int[counter];
		
		for(int i = 0; i < temp2.length; i++)
			temp2[i] = temp1[i];
		
		return temp2;
	}*/

