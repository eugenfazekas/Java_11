package com.audio6.audioGramSaver;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.sound.sampled.AudioInputStream;
import com.audio0.main.AppSetup;
import com.audio4.audioGramInitializer.mainInit.AudioAnalysisThread;
import com.audio8.util.Debug;

public class SaveMultiAudioFeatures {

	private static int id;
	public static String speechName;	
	public static  AudioInputStream audioInputStream; 
	public static byte[] audiodata;
	private static  BufferedImage image;
	public static int waveHeightLimit = 100;
	private static String[] PATHS;
	private static String MAIN_PATH;
	private static String SUB_PATH;
	public static LinkedList<DeleteLastDataModel> deleteList = new LinkedList<>();
	public static AtomicBoolean building = new AtomicBoolean(false);
	
	public static void mainSave(String Main_path, int Id) {

        id = Id;
        
		Debug.debug(1,"SaveMultiAudioFeatures id: " +id
			+", Build: "+AudioAnalysisThread.startedVoiceCheck.get(id).getBuild()+", equals save: "
			+ AudioAnalysisThread.startedVoiceCheck.get(id).getNextStage().equals("save") 
			+", MultiAnalysis:  " + AppSetup.multiAnalysis +" "
			+ AudioAnalysisThread.startedVoiceCheck.get(id).toString());
		
		if(!AudioAnalysisThread.startedVoiceCheck.get(id).getNextStage().equals("save") ||
		 !AppSetup.multiAnalysis ||!AudioAnalysisThread.startedVoiceCheck.get(id).getBuild()) {
			
			if(!AudioAnalysisThread.startedVoiceCheck.get(id).getBuild())
				AudioAnalysisThread.removeAudioObjct(AudioAnalysisThread.startedVoiceCheck.get(id).getId());
			
				building.set(false);
				return;
		}
		
		building.set(true);
		
        Debug.startTime = System.currentTimeMillis();
  
        PATHS = BuildAudioAnalysisFilesPaths.buildAudioAnalysisFilesPaths();          
        MAIN_PATH = Main_path;
        SUB_PATH =  SaveMultiAudioFeaturesUtil.getSubPath(); 
		speechName = AudioAnalysisThread.startedVoiceCheck.get(id).getSpeechName();
		
		Debug.debug(1,"SaveMultiAudioFeatures mainSave: "+MAIN_PATH+SUB_PATH+speechName);
		
		DeleteLastAnalysisBuilder.resetSavedData();
		
		FileCheckUtil.buildAndCheckDirectoryPath(MAIN_PATH+SUB_PATH+speechName,PATHS);
		
		saveAmplitudeGramm();
		saveFrequencyGramm();
		saveSpektroGramm();
		saveMySpektroGramm();
		saveMixedWaveStreamPointsArray();
		saveRawAudioData();
		buildSequenceArray();
		saveVoiceRecognitionArray();
		saveAudioStream();
		addToDeleteCheck();
		
		AudioAnalysisThread.startedVoiceCheck.get(id).setNextStage();

		Debug.debug(1,"SaveMultiAudioFeatures mainSave cycle time: "
				+(System.currentTimeMillis()-Debug.startTime));
		
		building.set(false);
	}
		
	private static void saveAmplitudeGramm() {
		
		if(AudioAnalysisThread.startedVoiceCheck.get(id).getAmplitudeWaveMap() 
				!= null && AppSetup.amplitudeGram) {
			
			image = BuildAudioImage.buildImage(AudioAnalysisThread.startedVoiceCheck.get(id)
				.getAmplitudeWaveMap(), AudioAnalysisThread.startedVoiceCheck.get(id)
				.getAmplitudeWaveMap().length, 100);
			
			FileUtil.addImageFileToLibrary(image,"AmplitudeGram-"+speechName
												, MAIN_PATH+SUB_PATH+speechName+"/amplitudeGram");
		}
	} 
	
	private static void saveFrequencyGramm() {
		
		if(AudioAnalysisThread.startedVoiceCheck.get(id).getFrequencyWaveMap() 
				!= null && (AppSetup.frequencyGram)) {
			
			image = BuildAudioImage.buildImage(AudioAnalysisThread.startedVoiceCheck.get(id)
					.getFrequencyWaveMap(), AudioAnalysisThread.startedVoiceCheck.get(id)
					.getFrequencyWaveMap().length, waveHeightLimit);

			FileUtil.addImageFileToLibrary(image,"Mapped-FrequencyGram-"+speechName
											 ,MAIN_PATH+SUB_PATH+speechName+"/frequencyGram");
		}
	} 
	
	private static void saveSpektroGramm() {
		
		if(AudioAnalysisThread.startedVoiceCheck.get(id).getSpektrogramMap() != null 
			&& AppSetup.spektrogram) {
			
			image = BuildAudioImage.buildSpectrogramImage(id,
				AudioAnalysisThread.startedVoiceCheck.get(id).getSpektrogramMap());
			FileUtil.addImageFileToLibrary(image,"SpektroGram-"+speechName, MAIN_PATH+SUB_PATH
																	+speechName+"/spektroGram");
		}
	}
	
	private static void saveMySpektroGramm() {
		
		Debug.debug(1,"SaveMultiAudioFeatures mainSave spek id "+ id+","
				+Arrays.toString(AudioAnalysisThread.startedVoiceCheck.get(id).getMySpektrogramMap()));
		
		if(AudioAnalysisThread.startedVoiceCheck.get(id).getMySpektrogramMap() != null && AppSetup.mySpektrogram) {

			image = BuildMySpektrogramImage.buildMySpectrogramImage(AudioAnalysisThread.startedVoiceCheck.get(id)
				.getMySpektrogramMap(), AudioAnalysisThread.startedVoiceCheck.get(id).getMySpektrogramMap().length/2
				, waveHeightLimit);
			
			FileUtil.addImageFileToLibrary(image,"MySpektroGram-"+speechName, MAIN_PATH+SUB_PATH
																	+speechName+"/mySpektrogram");
		}
	}
	
	private static void saveMixedWaveStreamPointsArray()   {
		
		if(AudioAnalysisThread.startedVoiceCheck.get(id).getMySpektrogramMap() != null && AppSetup.mySpektrogram) {
			
			FileUtil.buildRawAudioDataTextFile( new String[] { Arrays.toString(
				AudioAnalysisThread.startedVoiceCheck.get(id).getMySpektrogramMap())}, MAIN_PATH+SUB_PATH 
				+speechName+"/buildMixedWaveStreamPoints",speechName,"buildMixedWaveStreamPoints");
		}
	}
	
	private static void saveRawAudioData()   {
		
		if(AudioAnalysisThread.startedVoiceCheck.get(id).getRawAudioData() != null && AppSetup.rawAudioData) {

			FileUtil.buildRawAudioDataTextFile(AudioAnalysisThread.startedVoiceCheck.get(id).getRawAudioData()
				, MAIN_PATH+SUB_PATH +speechName+"/rawAudioData",speechName,"rawAudioData");
		}
	}
	
	private static void buildSequenceArray() {
		
		if(AudioAnalysisThread.startedVoiceCheck.get(id).getSequenceStringArray() != null 
				&& AppSetup.buildSequenceArray) {
			
			FileUtil.buildRawAudioDataTextFile(new String[] {
				SaveMultiAudioFeaturesUtil.sequenceArrayToString( 
					AudioAnalysisThread.startedVoiceCheck.get(id).getSequenceStringArray())
			}
				 ,MAIN_PATH+SUB_PATH+speechName+"/buildSequenceArray"
				,speechName,"buildSequenceArray");
		}
	}
		
	private static void saveVoiceRecognitionArray() {
		
		if(AppSetup.voiceRecognitionData) {
			
			String path1 = MAIN_PATH+SUB_PATH+speechName+"/voicePointsRecognition/"+speechName
					 													+"-voicePointsRecognition.txt";
			
			String path2 = MAIN_PATH+SUB_PATH+speechName+"/voiceSlopesRecognition/"+speechName
						                                                +"-voiceSlopesRecognition.txt";
			
			String path3 = MAIN_PATH+SUB_PATH+speechName+"/voiceAreaRecognition/"+speechName
																		+"-voiceAreaRecognition.txt";
			
			String path4 = MAIN_PATH+SUB_PATH+speechName+"/voiceScanRecognition/"+speechName
					+"-voiceScanRecognition.txt";
			
			boolean pathCHeck1 = FileCheckUtil.fileExist(path1);
			boolean pathCHeck2 = FileCheckUtil.fileExist(path2); // path1 ????
 			boolean pathCHeck3 = FileCheckUtil.fileExist(path3);
 			boolean pathCHeck4 = FileCheckUtil.fileExist(path4);
			
			String[] input1 = new String[1];
			String[] input2 = new String[1];
			String[] input3 = new String[1];
			String[] input4 = new String[1];
			
			if(AppSetup.voiceRecognitionPointsData 
				&& AudioAnalysisThread.startedVoiceCheck.get(id).getVoiceRecognitionPointsArray() != null) {
				
				input1[0] = AudioAnalysisThread.startedVoiceCheck.get(id).getVoiceRecognitionPointsArray();
				
				if(pathCHeck1) 					
					 FileUtil.addStreamToFile(input1[0], path1);
					 
				else 				
					 FileUtil.createTextFile(input1, path1);
			}
			
			if(AppSetup.voiceRecognitionSlopesData 
				&& AudioAnalysisThread.startedVoiceCheck.get(id).getVoiceRecognitionSlopesArray() != null) {	
				
				input2[0] = AudioAnalysisThread.startedVoiceCheck.get(id).getVoiceRecognitionSlopesArray();
				
				if(pathCHeck2) 
					 FileUtil.addStreamToFile(input2[0], path2);					 
				else 
					 FileUtil.createTextFile(input2, path2);					
			}
			
			if(AppSetup.voiceRecognitionAreaData 
					&& AudioAnalysisThread.startedVoiceCheck.get(id).getVoiceRecognitionAreaArray() != null) {	
					
				input3[0] = AudioAnalysisThread.startedVoiceCheck.get(id).getVoiceRecognitionAreaArray();
				
				if(pathCHeck3) 
					 FileUtil.addStreamToFile(input3[0], path3);					 
				else 
					 FileUtil.createTextFile(input3, path3);					
			}
			
			if(AppSetup.voiceRecognitionScanData 
					&& AudioAnalysisThread.startedVoiceCheck.get(id).getVoiceRecognitionScanArray() != null) {	
					
				input4[0] = AudioAnalysisThread.startedVoiceCheck.get(id).getVoiceRecognitionScanArray();
				
				if(pathCHeck4) 
					 FileUtil.addStreamToFile(input4[0], path4);					 
				else 
					 FileUtil.createTextFile(input4, path4);					
			}
		}
	} 	
	
	private static void saveAudioStream() {
		
		if(AudioAnalysisThread.startedVoiceCheck.get(id).getIntStream() == null &&
				AudioAnalysisThread.startedVoiceCheck.get(id).getByteStream() ==  null	) {
			
			Debug.debug(1,"SaveMultiAudioFeatures mainSave spek id "+ id
				+", saveAudioStream  NO STREAM TO WRITE! ");
			
			return;
		}

		if(AppSetup.wave == true 
				&& AudioAnalysisThread.startedVoiceCheck.get(id).getAudioFormat() != null) {	

			if(AudioAnalysisThread.startedVoiceCheck.get(id).getIntStream() != null)
				audiodata = SaveMultiAudioFeaturesUtil.buildAudiodDataFromInt(
					AudioAnalysisThread.startedVoiceCheck.get(id).getIntStream());	
			
			if(AudioAnalysisThread.startedVoiceCheck.get(id).getByteStream() !=  null)
				audiodata = AudioAnalysisThread.startedVoiceCheck.get(id).getByteStream();
			
			audioInputStream = AudioInputStreamUtil.buildAudioInputStream(
					audiodata, AudioAnalysisThread.startedVoiceCheck.get(id).getAudioFormat());

			FileUtil.addWaveFileToLibrary(speechName, MAIN_PATH+SUB_PATH+speechName+"/wave/"
																			,audioInputStream);
			
			if(AudioAnalysisThread.startedVoiceCheck.get(id).getIntStream() != null)
			FileUtil.buildRawAudioDataTextFile(new String[] 
					{ Arrays.toString(AudioAnalysisThread.startedVoiceCheck.get(id).getIntStream())}
					, MAIN_PATH+SUB_PATH +speechName+"/wave/",speechName,"Instream");
			
			if(AudioAnalysisThread.startedVoiceCheck.get(id).getByteStream() != null)
			FileUtil.buildRawAudioDataTextFile(new String[] 
					{ Arrays.toString(AudioAnalysisThread.startedVoiceCheck.get(id).getByteStream())}
					, MAIN_PATH+SUB_PATH +speechName+"/wave/",speechName,"Bytestream");
		} 
	}

	public static void addToDeleteCheck() {
		
		if(AppSetup.deleteCheck)
			deleteList.add(DeleteLastAnalysisBuilder.createFinalDeleteList());
	}
}
