package com.executor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;

import com.MainAppSetup;
import com.comands.VoiceComandCenter;
import com.threads.MyThread;
import com.threads.ThreadAction;
import com.threads.ThreadManagement;
import com.threads.ThreadObjectDetails;
import com.voiceRecognition.audio3.recorder.refinary.StreamRefinaryAmplitudeMethods;
import com.voiceRecognition.audio8.util.Debug;

public class ClassMethodsExecuter implements MyThread{
	
	private boolean threadIsActive;
	private boolean threadIsSuspended;
	private Thread thread;
	final private String THREAD_NAME = "ClassMethodsExecuter"; 
	private ThreadObjectDetails threadObject; 
	
	private static String voiceResult;
    private static boolean instanceOf;
	private static int debug_level_INFO = 1;
	
	private final static ExecutorFunctionClass[] executorClasses = new ExecutorFunctionClass[] {
			
				new JavaExecutor(),
				new PowerShellExecutor()
			};

	public ClassMethodsExecuter() {
		
    	if(!instanceOf) {
    		
    		new VoiceComandCenter();
    		threadObject = new ThreadObjectDetails(THREAD_NAME, true, MainAppSetup.mainAppName);
    		threadIsActive = true;
    		threadIsSuspended = false;
    		thread = new Thread(this);		
    		thread.start();

	    	instanceOf = true;
    	}
	}

	private static void taskChecker() {
		
		checkReadedVoices();
	}
	
	private static void checkReadedVoices() {
		
		deleteOldVoices();
		
		voiceResult = VoiceComandCenter.checkReadedVoices();

		if(voiceResult != null) {
			Debug.debug(debug_level_INFO,"ClassMethodsExecuter New Voice Detected");
			checkExecuteClassForMethod(voiceResult);
		}
	}
	
	@Override
	public void run() {	
		
		Thread.currentThread().setName(THREAD_NAME);
		
		ThreadManagement.threadActions.add(
					new ThreadAction("addingThread",-1,"ClassMethodsExecuter",this,
							"addingThread ClassMethodsExecuter"));
		
		Debug.debug(debug_level_INFO,"Starting "+Thread.currentThread().getName() +" Thread!");
		
		while(threadIsActive) {

			suspendThread();
			
			taskChecker();
			
			sleepThread(StreamRefinaryAmplitudeMethods.sleepTime/2);	
		}		
	}
	
	
	public static void checkExecuteClassForMethod(String searchedMethodName) {
		
		for(int i = 0; i < executorClasses.length ; i++) {
			
			if(executeMethodByClass(executorClasses[i] ,searchedMethodName))
				break;
		}
	}

	public static boolean executeMethodByClass(ExecutorFunctionClass class1, String methodName) {	
		
		boolean result = false;
		
		Class<? extends ExecutorFunctionClass> inputClass = null;
	
		try {
			inputClass = class1.getClass();
		} catch (IllegalArgumentException | SecurityException e) {

			e.printStackTrace();
		}

		for (Method method : inputClass.getDeclaredMethods())	{
			Debug.debug(debug_level_INFO,"ClassName: " +class1.getClass().getName()+ ", method.getName() "+method.getName());
			if(method.getName().equals(methodName)) {
	
				try {
					method.invoke(inputClass);
					result = true;
					return result;
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			break;
			
			}
		}
		
		return result;
	}
	
	private static void deleteOldVoices() {
		
		if(System.currentTimeMillis() - VoiceComandCenter.lastAdd > 10000 &&
				VoiceComandCenter.readedVoiceArrray.size() > 0) {
			
			VoiceComandCenter.deleteOldVoices();		
			Debug.debug(debug_level_INFO,"Deleted voice at: "+ LocalDate.now());
		}
	}
	
	@Override
	public void stopThread() {	
		
		threadIsActive = false;
	}
	
	@Override
	public void suspendThread() {

		while(isThreadSuspended()) {
			
			sleepThread(50);
		}
	}
	
	@Override
	public void setSuspendThread() {
		
		threadIsSuspended = true;		
	}

	@Override
	public void stopSuspendThread() {
		
		threadIsSuspended = false;
	}

	@Override
	public Thread getThread() {

		return thread;
	}

	@Override
	public boolean isThreadSuspended() {
		
		return threadIsSuspended;
	}
	
	public void sleepThread(int mSec) {
		
		try {
			Thread.sleep(mSec);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	@Override
	public ThreadObjectDetails getThreadObjectDetails() {

		return threadObject;
	}
}
	
//	private static <T> List<T> pushBack(List<T> list, Class<T> typeKey) throws Exception {
//    list.add(typeKey.getConstructor().newInstance());
//    return list;
	
//    public static void processClass(Class<?> clazz, String methodName) throws Exception {
//        Method method = clazz.getMethod(methodName);
//        Object instance = clazz.getDeclaredConstructor().newInstance();
//        method.invoke(instance);
//    }
//
//    public static void main(String[] args) throws Exception {
//        processClass(ReflectionTarget.class, "sayHello");
//    }
//}
//
//class ReflectionTarget {
//    public void sayHello() {
//        System.out.println("Hello, Reflection!");
//    }
	
