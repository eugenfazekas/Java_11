package voiceRecognition.audio3.recorder.refinary;

import voiceRecognition.audio7.threads.MyThread;

public interface StreamRefinary {

	void bufferCheck(byte[] inputBuffer);
	
	MyThread getRefinaryThread();
}
