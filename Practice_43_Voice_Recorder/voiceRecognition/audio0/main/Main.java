package voiceRecognition.audio0.main;


import voiceRecognition.audio1.logical.AllFeaturesEntryPoint;
import voiceRecognition.audio7.threads.ThreadAction;
import voiceRecognition.audio7.threads.ThreadManagement;
import voiceRecognition.audio8.util.Debug;

public class Main {
    		
	private static boolean threadIsActive;
	private static int debud_level_DEBUG = 5;
	/**
	* Files are saved at classpath (path to class files)
	*                    or this folder......
	* \src\main\resources\static\audio\spektrum\record Name
	* 
	* Run one option at one time from ALLFeaturesEntryPoint.
	*/
	public static void main(String[] args){
		
		AllFeaturesEntryPoint.mainLogicalEntryPoint(args);
		
		threadIsActive = true;
		
		while(threadIsActive) {
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				
				Debug.debug(1, "Main InterruptedException " + e.getMessage());
				e.printStackTrace();
			}	
			
			Debug.debug(6, "Main Threads Count: " + ThreadManagement.getThreadsCount("MAIN main"));
		}	
		
		ThreadManagement.threadActions.add(new ThreadAction("stopAllThreads",-1,null,null,
				"stopAllThreads main"));
	}
	
	public static void setStopAllThreads(String initializer) {
		
		
		Debug.debug(debud_level_DEBUG, "Main setStopAllThreads initializer: " + initializer);
		threadIsActive = false;
	}
}
