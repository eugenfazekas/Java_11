package voiceRecognition.audio7.threads.util;

import java.time.LocalDateTime;

import voiceRecognition.audio1.logical.EntryPointMethods;

public class ThreadObjectDetails {

	private static int idCounter = 1;
	
	private int id;
	private String threadName;
	private String ApplicationName;
	private long longStartDate;
	private LocalDateTime date;
	private boolean suspendable;
		
	public ThreadObjectDetails(String threadName, boolean Suspendable) {
		
		this.id = idCounter++;
		this.threadName = threadName+"-"+this.id;
		this.ApplicationName = EntryPointMethods.getSvitch();
		this.longStartDate = System.currentTimeMillis();
		this.date = LocalDateTime.now();
		this.suspendable = Suspendable;
	}

	public int getId() {
		return id;
	}

	public String getThreadName() {
		return threadName;
	}

	public long getLongStartDate() {
		return longStartDate;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public boolean isSuspendable() {
		return suspendable;
	}
	
	public String getApplicationName() {
		return ApplicationName;
	}
	
	public void setApplicationName(String applicationName) {
		ApplicationName = applicationName;
	}
}
