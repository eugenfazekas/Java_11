package voiceRecognition.audio7.threads;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import voiceRecognition.audio0.main.AppSetup;
import voiceRecognition.audio1.logical.EntryPointMethods;
import voiceRecognition.audio7.threads.util.ThreadObjectDetails;
import voiceRecognition.audio8.util.Debug;

public class ThreadManagement implements MyThread{
	
	private boolean threadIsActive;
	private boolean threadIsSuspended;
	private Thread thread;
	final private String THREAD_NAME = "ThreadManagement"; 
	private ThreadObjectDetails threadObject; 
	
	private static Set<MyThread> threads = new HashSet<MyThread>();
	public static int threadSleepTime;	
    public static ConcurrentLinkedQueue<ThreadAction> threadActions = new ConcurrentLinkedQueue<>();

	public static AtomicBoolean building = new AtomicBoolean(false);
	private String svitch;
	
	private static int debug_level_INFO = 1;
	private static int debud_level_DEBUG = 5;
		
	public ThreadManagement() {
		
		threadSleepTime = AppSetup.THREAD_MANAGEMENT_THREAD_SLEEP;
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

			suspendThread("ThreadManagement main");
			
			 try {		    
					checkThreadActions();
		    
		    } catch (Exception ex) {
		    	
	            Debug.debug(1,"DebugException in ThreadManagement! " +ex.getMessage());  

	    		ThreadManagement.threadActions.add(
	    				new ThreadAction("stopAllThreads",-1,EntryPointMethods.getSvitch(),this,
	    						"stopAllThreads ThreadManagement"));
		    }
						
			 sleepThread(threadObject.getThreadName()+" main",threadSleepTime);				
		}		
	}
	
	private void initSwitch(ThreadAction threadAction) {
		
		svitch = threadAction.getActionName();
		
		switch(svitch) {
				
			case "addingThread" :addingThread(threadAction.getThread());break;
			
			case "suspendThreadById" :suspendThreadById(threadAction.getThreadId()
					,threadAction.getThread().getThreadObjectDetails().getThreadName());break;
			
			case "stopAndRemoveThreadById":stopAndRemoveThreadById(threadAction.getThreadId()
					,threadAction.getThread().getThreadObjectDetails().getThreadName());break;
			
			case "enableThreadById" :enableThreadById(threadAction.getThreadId()
					,threadAction.getThread().getThreadObjectDetails().getThreadName());break;
						
			case "suspendThreadsByApplicationName" :suspendThreadsByApplicationName(
					threadAction.getApplicationName()
								,threadAction.getThread().getThreadObjectDetails().getThreadName());break;
			
			case "stopThreadsByApplicationName" :stopThreadsByApplicationName(threadAction.getApplicationName()
								,threadAction.getThread().getThreadObjectDetails().getThreadName());break;
			
			case "enableThreadsByApplicationName" :enableThreadsByApplicationName(threadAction.getApplicationName()
					,threadAction.getThread().getThreadObjectDetails().getThreadName());break;
			
			case "enableAllThreads" : enableAllThreads(
					threadAction.getThread().getThreadObjectDetails().getThreadName());break;
			
			case "stopAllThreads" : stopAllThreads(
					threadAction.getThread().getThreadObjectDetails().getThreadName());break;	
		}
	}
	
	private void addingThread(MyThread thread) {
		
		Debug.debug(debug_level_INFO, "ThreadManagement Adding thread: "
				+thread.getThreadObjectDetails().getThreadName());
		
		threads.add(thread);
	}
	
	private void suspendThreadById(int threadId, String initializer) {
		
		Debug.debug(debug_level_INFO, "ThreadManagement suspendThreadById "+threadId 
				+ ", initializer"+initializer);
				
		for (MyThread thread : threads) 
			if(thread.getThreadObjectDetails().getId() == threadId )
				thread.setSuspendThread(thread.getThreadObjectDetails().getThreadName());			
	}
	
	private void stopAndRemoveThreadById(int threadId, String initializer) {
		
		Debug.debug(debug_level_INFO, "ThreadManagement stopAndRemoveThreadById "+threadId 
				+ ", initializer"+initializer);
		
		for (MyThread thread : threads) 
			if(thread.getThreadObjectDetails().getId() == threadId) {
				thread.stopThread(thread.getThreadObjectDetails().getThreadName());
				boolean threadStopped = threads.remove(thread);
				
				Debug.debug(debug_level_INFO, "ThreadManagement removed thread "
					+thread.getThreadObjectDetails().getThreadName()+" "+threadStopped 
					+ ",Thread Size: "+threads.size() );
				break;
			}
	}
	
	private void enableThreadById(int threadId, String initializer) {
		
		Debug.debug(debug_level_INFO, "ThreadManagement enableThreadById "+threadId 
				+ ", initializer"+initializer);
		
		for (MyThread thread : threads) {
			
			if(thread.getThreadObjectDetails().getId() == threadId) {
				thread.stopSuspendThread(thread.getThreadObjectDetails().getThreadName());
				
				Debug.debug(debug_level_INFO, "ThreadManagement enableThreadById "
					+thread.getThreadObjectDetails().getThreadName()); 
			}
		}
	}
		
	private void suspendThreadsByApplicationName(String applicationName, String initializer) {
		
		Debug.debug(debug_level_INFO, "ThreadManagement suspendThreadsByApplicationName "+initializer);
		
		for (MyThread thread : threads) {
		
			if(thread.getThreadObjectDetails().getApplicationName().equals(applicationName) 
				&& thread.getThreadObjectDetails().isSuspendable()) {
				
				thread.setSuspendThread(thread.getThreadObjectDetails().getThreadName());

				Debug.debug(debug_level_INFO, "ThreadManagement suspendThreadsByApplicationName thread: " 
					+thread.getThreadObjectDetails().getThreadName()+" is suspended!");
			}
		}
	}
	
	private void stopThreadsByApplicationName(String applicationName, String initializer) {
		
		Debug.debug(debug_level_INFO, "ThreadManagement stopThreadsByApplicationName "+initializer);
		
		for (MyThread thread : threads) {
			
			if(thread.getThreadObjectDetails().getApplicationName().equals(applicationName) 
				&& thread.getThreadObjectDetails().isSuspendable()) {
				
				thread.stopThread(thread.getThreadObjectDetails().getThreadName());

				Debug.debug(debug_level_INFO, "ThreadManagement stopThreadsByApplicationName thread: " 
					+thread.getThreadObjectDetails().getThreadName()+" is stoped!");
			}
		}
	}
	
	private void enableThreadsByApplicationName(String applicationName, String initializer) {
		
		Debug.debug(debug_level_INFO, "ThreadManagement enableThreadsByApplicationName "+initializer);
		
		for (MyThread thread : threads) {
			
			if(thread.getThreadObjectDetails().getApplicationName().equals(applicationName) 
				&& thread.getThreadObjectDetails().isSuspendable()) {
				
				thread.stopSuspendThread(thread.getThreadObjectDetails().getThreadName());

				Debug.debug(1, "ThreadManagement enableThreadsByApplicationName thread: " 
					+thread.getThreadObjectDetails().getThreadName()+" is enabled!");
			}
		}
	}
	
	private void enableAllThreads(String initializer) {
		
		Debug.debug(debug_level_INFO, "ThreadManagement Enabling All Threads");
		
		for (MyThread thread : threads) 
				thread.stopSuspendThread(thread.getThreadObjectDetails().getThreadName());
	}
	
	private void stopAllThreads(String initializer) {

		Debug.debug(debug_level_INFO, "ThreadManagement Stoping All Threads "+initializer);
		
		for (MyThread thread : threads) {
				thread.stopThread(thread.getThreadObjectDetails().getThreadName());
				Debug.debug(debug_level_INFO, "ThreadManagement Stopping "
					+ thread.getThreadObjectDetails().getThreadName());	
		}
	}
	
	public static int getThreadsCount(String initializer) {
		
		return threads.size(); 
	}
	
	@Override
	public void stopThread(String initializer) {	
		
		Debug.debug(debug_level_INFO, THREAD_NAME+ " stoping Thread "+initializer);
		threadIsActive = false;
	}
	
	@Override
	public void suspendThread(String initializer) {
		
		Debug.debug(debud_level_DEBUG, THREAD_NAME+ " suspend Thread "+initializer);
		while(isThreadSuspended(initializer)) {
			
			sleepThread(initializer,50);
		}
	}
	
	@Override
	public void setSuspendThread(String initializer) {
		
		Debug.debug(debug_level_INFO, THREAD_NAME+ " set Suspend Thread "+initializer);
		threadIsSuspended = true;		
	}
	
	@Override
	public void stopSuspendThread(String initializer) {
		
		Debug.debug(debug_level_INFO, THREAD_NAME+ " stop Suspend Thread "+initializer);
		threadIsSuspended = false;
	}

	@Override
	public Thread getThread(String initializer) {

		Debug.debug(debug_level_INFO, THREAD_NAME+ " get Thread "+initializer);
		return thread;
	}

	@Override
	public boolean isThreadSuspended(String initializer) {
		
		Debug.debug(debud_level_DEBUG, THREAD_NAME+ " is Thread Suspended "+initializer);
		return threadIsSuspended;
	}
	
	public void sleepThread(String initializer,int mSec) {
		
		Debug.debug(debud_level_DEBUG, THREAD_NAME+ " sleep Thread "+initializer);
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

