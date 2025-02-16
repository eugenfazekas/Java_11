package com.audio8.util.thread;

import java.time.LocalDateTime;

public class ThreadObject {

	private long id;
	private String name;
	private long longStartDate;
	private LocalDateTime date;
		
	public ThreadObject(Thread thread) {
		
		this.id = thread.getId();
		this.name = thread.getName();
		this.longStartDate = System.currentTimeMillis();
		this.date = LocalDateTime.now();
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public long getLongStartDate() {
		return longStartDate;
	}

	public LocalDateTime getDate() {
		return date;
	}	
}
