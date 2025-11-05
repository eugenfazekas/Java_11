package temp;

import java.util.Set;


public class Threads {

	public static void main(String[] args) {

		while(true) {
			Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
			
			for (Thread t : threadSet) 
				System.out.println("ThreadUtil printThreads " +t.getName());	
			
			System.out.println("\n");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
