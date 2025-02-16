package com.audio3.recorder.refinary;

import com.audio8.util.thread.MyThread;

public interface StreamRefinary {

	void bufferCheck(byte[] inputBuffer);
	
	MyThread getRefinaryThread();
}
