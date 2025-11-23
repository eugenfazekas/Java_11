package com.voiceRecognition.audio3.recorder.refinary;

import com.threads.MyThread;

public interface StreamRefinary {

	void bufferCheck(byte[] inputBuffer);
	
	MyThread getRefinaryThread();
}
