package voiceRecognition.audio7.threads;

import voiceRecognition.audio7.threads.util.ThreadObjectDetails;

public interface MyThread extends Runnable {

	public Thread getThread(String initializer);

	public void stopThread(String initializer);
		
	public void suspendThread(String initilizer);
	
	public void setSuspendThread(String initializer);

	public void stopSuspendThread(String initializer);
	
	public boolean isThreadSuspended(String initializer);
	
	public void sleepThread(String initilizer, int mSec);

	public ThreadObjectDetails getThreadObjectDetails();
}
