package com.audio1.logical;

import java.util.Scanner;

import com.audio0.main.AppSetup;
import com.audio2.recorder.AudioListener;
import com.audio3.recorder.refinary.StreamRefinarySelector;
import com.audio4.audioGramInitializer.AudioAnalysisThread;
import com.audio5.recognition.VoiceRecognitionMain;
import com.audio6.audioGramSaver.DeleteLastAnalysisBuilder;
import com.audio6.audioGramSaver.DeleteLastDataModel;
import com.audio6.audioGramSaver.SaveMultiAudioFeatures;
import com.audio8.util.Debug;

public class DeleteOrNotActionLogic {
	
	private static Scanner scanner ;
	public static boolean checking;
	public static String input;
	private static DeleteLastDataModel lastDelete;
	private static String svitch;
	private static String speech;
	
	public static void startAnalysisSaveCheck() {
		
	    Runnable thread = () -> {   

	    	analysisSaveCheck();	    	
	     };
	     
	     new Thread(thread).start();
	};
		
	public static void analysisSaveCheck() {
					
		if(SaveMultiAudioFeatures.deleteList.peek()  == null) return;
		
		Debug.debug(3,"Starting analysisSaveCheck! ");	
			
		suspendBaseTask();
		
		if(AppSetup.voiceRecognition)		
			voiceRecognitionSaveCheck();
			
		if(!AppSetup.voiceRecognition) 		
			keyboardSavecheck();
		
		restoreBaseTask();
			
		Debug.debug(3, "DeleteOrNotActionLogic analysisSaveCheck result: "+ input);
	}
	
	private static void voiceRecognitionSaveCheck() {
		
		System.out.println("Start voiceRecognitionSaveCheck");
		
		svitch = EntryPointMethods.getSvitch();
		speech = EntryPointMethods.getSpeechName();
		StreamRefinarySelector.activeRefinary.getRefinaryThread().stopThread();
		
		EntryPointMethods.mainOptionRun("voiceRecognition", null);
	
		while(checking) {
			
			if(VoiceRecognitionMain.readedVoiceArrray.size() > 0) {
				
				//input = VoiceRecognitionMain.readedVoiceArrray.ge;			
				System.out.println("Start voiceRecognitionSaveCheck input: "+input);
				
				makeCheckAction(input);
			}
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		StreamRefinarySelector.activeRefinary.getRefinaryThread().stopThread();
		EntryPointMethods.mainOptionRun(svitch, speech);
	}
	
	private static void keyboardSavecheck() {
		
		StreamRefinarySelector.activeRefinary.getRefinaryThread().suspendThread();
		
		scanner = new Scanner(System.in);
		System.out.println("\nDelete Last Save? Yes(y) or No(n)");
		
		while(checking) {
			
			input = scanner.nextLine();
			
			if(makeCheckAction(input)) {	
				
				checking = false;
			}
		}	
		
		StreamRefinarySelector.activeRefinary.getRefinaryThread().stopSuspendThread();;
	}
	
	private static void suspendBaseTask() {
		
		AudioListener.addToRecorderBuffer = false;
		AudioAnalysisThread.analysisThreadEnabled = false;
		lastDelete = SaveMultiAudioFeatures.deleteList.poll();
		input = null;
		checking = true;
	}
	
	private static void restoreBaseTask() {
		
		AudioListener.addToRecorderBuffer = true;
		AudioAnalysisThread.analysisThreadEnabled = true;
	}
	
	private static boolean makeCheckAction(String readedInput) {
				
		if(readedInput.equals("yes") || readedInput.equals("y")|| readedInput.equals("Igen")) {
			
			DeleteLastAnalysisBuilder.deleteLastSave(lastDelete);
				
			return true;
		}
		
		else if(readedInput.equals("No") || readedInput.equals("n") || readedInput.equals("Nem")) 
			
			return true;
		
		else 		
			return false;
	}
	// Class Cast Example
	//Class<?> cls = Class.forName("com.getclassfromstr.MyNiceClass");
	// refinary = (StreamRefinary) cls.getDeclaredConstructor().newInstance();
}
