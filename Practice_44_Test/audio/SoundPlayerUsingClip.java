package audio;

public class SoundPlayerUsingClip {
	
	
	public static void main(String args[]) throws InterruptedException {

		String a = "test";
		
		System.out.println("ThreadName "+Thread.currentThread().getName()+" hashcode a: "+ a.hashCode());
		writer(a);
		
		MyThread t = new MyThread(a);
		
		Thread.sleep(3000);
		t.threadWriter(a);
		
		} 
	
	public static void writer(String code) {
		
		System.out.println("ThreadName "+Thread.currentThread().getName()+" hashcode code: "+ code.hashCode());
	} 
}

class MyThread implements Runnable{

	Thread thread;
	private String test;
	
	public MyThread (String test) {
		this.test = test;
		thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void run() {
		System.out.println("1. ThreadName "+Thread.currentThread().getName());
		threadWriter(test);
	} 
	
	public void threadWriter(String code) {
		System.out.println("2. ThreadName "+Thread.currentThread().getName()+" hashcode code: "+ code.hashCode());
	}
}

