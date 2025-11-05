package com.audio3.recorder.refinary;

import com.audio7.threads.MyThread;

public interface StreamRefinary {

	void bufferCheck(byte[] inputBuffer);
	
	MyThread getRefinaryThread();
}
