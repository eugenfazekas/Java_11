package com.audio7.threads;

import com.audio7.threads.util.ThreadObjectDetails;

public interface MyThread extends Runnable {

	public Thread getThread();

	public void stopThread();
		
	public void suspendThread();
	
	public void setSuspendThread();

	public void stopSuspendThread();
	
	public boolean isThreadSuspended();
	
	public void sleepThread(int mSec);

	public ThreadObjectDetails getThreadObjectDetails();
}
