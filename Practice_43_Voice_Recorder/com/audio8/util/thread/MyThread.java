package com.audio8.util.thread;

public interface MyThread extends Runnable {

	public Thread getThread();

	public void stopThread();
		
	public void suspendThread();
	
	public void stopSuspendThread();
	
	public boolean isThreadSuspended();	
}
