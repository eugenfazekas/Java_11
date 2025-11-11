package com.audio7.threads;

public class ThreadAction {

	private String actionName;
	private int threadId;
	private String applicationName;
	private MyThread thread;
			
	public ThreadAction(String actionName, int threadId, String applicationName, MyThread thread) {
		
		this.actionName = actionName;
		this.threadId = threadId;
		this.applicationName = applicationName;
		this.thread = thread;
	}

	public String getActionName() {
		return actionName;
	}
	
	public int getThreadId() {
		return threadId;
	}
	
	public String getApplicationName() {
		return applicationName;
	}

	public MyThread getThread() {
		return thread;
	}
}
