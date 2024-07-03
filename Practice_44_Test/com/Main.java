package com;

public class Main {
	
	final static int THREADS_EXECUTION_FINAL_COUNT = 1000;
	final static int LOOP_LENGTH_VALUE = 100000;
    final static int MAXIMUM_THREADS = Runtime.getRuntime().availableProcessors()-1;
	static volatile int activeThreadsCount = 0;
	static volatile int executedThreadCount = 0;
	static int MAIN_VALUE = 0;

	public static void main(String[] args) {
		
		while(executedThreadCount < THREADS_EXECUTION_FINAL_COUNT ) {
			createNewThread(executedThreadCount);

		}
		System.out.println("THREADS Executed Totally: "+executedThreadCount);

		while(Thread.activeCount() != 1) {
	
		}
		System.out.println("MAIN_VALUE: "+Main.MAIN_VALUE+" activeThreadsCount:" +activeThreadsCount );
	}
	
	public synchronized static void updateMainValue(boolean increase,int threadName) {
		
		MAIN_VALUE = increase == true ? ++MAIN_VALUE : --MAIN_VALUE;
		//System.out.println("updateMainValue ThreadName: "+threadName+ " MAIN_VALUE: "+MAIN_VALUE + " increase: "+increase +" ActiveThreads:" + Thread.activeCount()  );
	}
	
	
	private static void createNewThread(int executedThreadCount) {

		if(activeThreadsCount < MAXIMUM_THREADS) {
		RunnableImpl t = new RunnableImpl(Main.executedThreadCount);
		}
		else {
			return;
			}
	}
}

class RunnableImpl implements Runnable {
   
   Thread thread;
   private int executedThreadCount;   
   
   public RunnableImpl(int executedthreadCount) {
	   executedThreadCount = executedthreadCount;
	   Main.executedThreadCount++;
	   Main.activeThreadsCount++; 
	   thread = new Thread(this);
	   thread.start();
	   //System.out.println("Constructed Thread: "+executedThreadCount);
   }

   
   private void stop() {
	   --Main.activeThreadsCount;
	   System.out.println("Stopping Thread Name : "+executedThreadCount+ " Active Threads: "+Main.activeThreadsCount);
	   thread = null;
   };
   
   public void run() {
	   

	   System.out.println("Started Thread: "+executedThreadCount + " Total threads running: "+ Thread.activeCount());
	   
	   if(executedThreadCount % 2 == 0) {
		   increment(executedThreadCount);
	   }
	   
	   else {
		   decrement(executedThreadCount);
	   }
   }
   
	private void increment(int threadName) {
		
		for(int i = 0; i < Main.LOOP_LENGTH_VALUE+1; i++ ) {
			Main.updateMainValue(true,threadName);
			//System.out.println("Thread Name: "+ threadName + " i: "+i + " MAIN_VALUE:"+Main.MAIN_VALUE);
		}
		stop();
	}
	
	private void decrement(int threadName) {
		
		for(int i = 0; i < Main.LOOP_LENGTH_VALUE; i++ ) {
			Main.updateMainValue(false,threadName);
			//System.out.println("Thread Name: "+ threadName + " i: "+i + " MAIN_VALUE:"+Main.MAIN_VALUE);
		}
		stop();
	}
}

