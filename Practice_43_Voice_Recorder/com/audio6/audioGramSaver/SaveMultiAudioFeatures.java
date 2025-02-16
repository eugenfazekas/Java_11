package com.audio6.audioGramSaver;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.LinkedList;

import javax.sound.sampled.AudioInputStream;

import com.audio0.main.AppSetup;
import com.audio4.audioGramInitializer.AudioAnalysisThread;
import com.audio5.audioGramBuilder.MiscellaneousData;
import com.audio5.audioGramBuilder.SpektroGramBuilder;
import com.audio8.util.AudioBuilderUtil;
import com.audio8.util.AudioInputStreamUtil;
import com.audio8.util.Debug;
import com.audio8.util.file.FileCheckUtil;
import com.audio8.util.file.FileUtil;

public class SaveMultiAudioFeatures {

	private static int id;
	public static String speechName;	
	public static  AudioInputStream audioInputStream; 
	private static  BufferedImage image;
	public static int waveHeightLimit = 100;
	private static String[] PATHS;
	private static String MAIN_PATH;
	private static String SUB_PATH;
	public static LinkedList<DeleteLastDataModel> deleteList = new LinkedList<>();
	
	public static void mainSave(String Main_path,String Sub_path, int Id) {
		
        id = Id;
        
		Debug.debug(1,"SaveMultiAudioFeatures mainSave id: "+id);
		
		Debug.debug(1," startedVoiceCheck.get(id).getNextStage() "
			+AudioAnalysisThread.startedVoiceCheck.get(id).getNextStage() + " AudioObject: "
				+AudioAnalysisThread.startedVoiceCheck.get(id).toString());
		
		if(!AudioAnalysisThread.startedVoiceCheck.get(id).getNextStage().equals("save") ||
		 !AppSetup.multiAnalysis ||!AudioAnalysisThread.startedVoiceCheck.get(id).getBuild()) {
			
			if(!AudioAnalysisThread.startedVoiceCheck.get(id).getBuild())

				removeAudioObjct();

				return;
		}
			
        Debug.startTime = System.currentTimeMillis();
  
        PATHS = BuildAudioAnalysisFilesPaths.buildAudioAnalysisFilesPaths();     
        MAIN_PATH =Main_path;
        SUB_PATH=Sub_path;
		speechName = AudioAnalysisThread.startedVoiceCheck.get(id).getSpeechName();
		
		Debug.debug(1,"SaveMultiAudioFeatures mainSave: "+MAIN_PATH+SUB_PATH+speechName);
		
		DeleteLastAnalysisBuilder.resetSavedData();
		
		FileCheckUtil.buildAndCheckDirectoryPath(MAIN_PATH+SUB_PATH+speechName,PATHS);
		
		saveAmplitudeGramm();
		saveFrequencyGramm();
		saveSpektroGramm();
		saveMySpektroGramm();
		saveRawAudioData();
		buildSequenceArray();
		saveVoiceRecognitionArray();
		saveAudioStream();
		buildVoiceCompareImage();
		addToDeleteCheck();
		removeAudioObjct();
		
		Debug.debug(1,"SaveMultiAudioFeatures mainSave cycle time: "
				+(System.currentTimeMillis()-Debug.startTime));
	}
		
	public static void saveAmplitudeGramm() {
		
		if(AudioAnalysisThread.startedVoiceCheck.get(id).getAmplitudeWaveMap() 
				!= null && (AppSetup.amplitudeGram || AppSetup.multiGram)) {
			
			image = BuildAudioImage.buildImage(AudioAnalysisThread.startedVoiceCheck.get(id)
				.getAmplitudeWaveMap(), AudioAnalysisThread.startedVoiceCheck.get(id)
				.getAmplitudeWaveMap().length, 100);
			
			FileUtil.addImageFileToLibrary(image,"AmplitudeGram-"+speechName
												, MAIN_PATH+SUB_PATH+speechName+"/amplitudeGram");
		}
	} 
	
	public static void saveFrequencyGramm() {
		
		if(AudioAnalysisThread.startedVoiceCheck.get(id).getFrequencyWaveMap() 
				!= null && (AppSetup.frequencyGram|| AppSetup.multiGram)) {
			
			image = BuildAudioImage.buildImage(AudioAnalysisThread.startedVoiceCheck.get(id)
					.getFrequencyWaveMap(), AudioAnalysisThread.startedVoiceCheck.get(id)
					.getFrequencyWaveMap().length, waveHeightLimit);

			FileUtil.addImageFileToLibrary(image,"Mapped-FrequencyGram-"+speechName
											 ,MAIN_PATH+SUB_PATH+speechName+"/frequencyGram");
		}
	} 
	
	public static void saveSpektroGramm() {
		
		if(SpektroGramBuilder.spectrogram != null && AppSetup.spektrogram) {
			
			image = BuildAudioImage.buildSpectrogramImage(id);
			FileUtil.addImageFileToLibrary(image,"SpektroGram-"+speechName, MAIN_PATH+SUB_PATH
																	+speechName+"/spektroGram");
		}
	}
	
	public static void saveMySpektroGramm() {
		
		Debug.debug(1,"SaveMultiAudioFeatures mainSave spek id "+ id+","+Arrays.toString(AudioAnalysisThread.startedVoiceCheck.get(id).getMySpektrogramMap()));
		
		if(AudioAnalysisThread.startedVoiceCheck.get(id).getMySpektrogramMap() != null && AppSetup.mySpektrogram) {

			image = BuildMySpektrogramImage.buildMySpectrogramImage(AudioAnalysisThread.startedVoiceCheck.get(id)
				.getMySpektrogramMap(), AudioAnalysisThread.startedVoiceCheck.get(id).getMySpektrogramMap().length/2
				, waveHeightLimit);
			
			FileUtil.addImageFileToLibrary(image,"MySpektroGram-"+speechName, MAIN_PATH+SUB_PATH
																	+speechName+"/mySpektrogram");
		}
	}
	
	public static void saveRawAudioData()   {
		
		if(AudioAnalysisThread.startedVoiceCheck.get(id).getRawAudioData() != null && AppSetup.rawAudioData) {
			
			FileUtil.buildRawAudioDataTextFile(AudioAnalysisThread.startedVoiceCheck.get(id).getRawAudioData()
				, MAIN_PATH+SUB_PATH +speechName+"/rawAudioData",speechName,"rawAudioData");
		}
	}
	
	public static void buildSequenceArray() {
		
		if(AudioAnalysisThread.startedVoiceCheck.get(id).getSequenceStringArray() != null 
				&& AppSetup.buildSequenceArray) {
			
			FileUtil.buildRawAudioDataTextFile(AudioAnalysisThread.startedVoiceCheck.get(id)
				.getSequenceStringArray() ,MAIN_PATH+SUB_PATH+speechName+"/buildSequenceArray"
				,speechName,"buildSequenceArray");
		}
	}
		
	public static void saveVoiceRecognitionArray() {
		
		if(AppSetup.voiceRecognitionData) {
			
			String path1 = MAIN_PATH+SUB_PATH+speechName+"/voicePointsRecognition/"+speechName
					 													+"-voicePointsRecognition.txt";
			
			String path2 = MAIN_PATH+SUB_PATH+speechName+"/voiceSlopesRecognition/"+speechName
						                                                +"-voiceSlopesRecognition.txt";
			
			boolean pathCHeck1 = FileCheckUtil.fileExist(path1);
			boolean pathCHeck2 = FileCheckUtil.fileExist(path1);
			
			String[] input1 = new String[1];
			String[] input2 = new String[1];
			
			if(AppSetup.voiceRecognitionPointsData 
				&& AudioAnalysisThread.startedVoiceCheck.get(id).getVoiceRecognitionPointsArray() != null) {
				
				input1[0] = Arrays.toString(AudioAnalysisThread
						.startedVoiceCheck.get(id).getVoiceRecognitionPointsArray());
				
				if(pathCHeck1) 					
					 FileUtil.addStreamToFile(input1[0], path1);
					 
				else 				
					 FileUtil.createTextFile(input1, path1);
			}
			
			if(AppSetup.voiceRecognitionSlopesData 
				&& AudioAnalysisThread.startedVoiceCheck.get(id).getVoiceRecognitionSlopesArray() != null) 	{	
				
					input2[0] = AudioAnalysisThread.startedVoiceCheck.get(id).getVoiceRecognitionSlopesArray();
					
					if(pathCHeck2) 
						 FileUtil.addStreamToFile(input2[0], path2);					 
					else 
						 FileUtil.createTextFile(input2, path2);					
			}
		}
	} 	
	
	public static void saveAudioStream() {

		if(AppSetup.wave == true 
				&& AudioAnalysisThread.startedVoiceCheck.get(id).getAudioFormat() != null) {	

			byte[] audiodata = AudioBuilderUtil.buildAudiodDataFromInt(
					AudioAnalysisThread.startedVoiceCheck.get(id).getIntStream());	
			
			audioInputStream = AudioInputStreamUtil.buildAudioInputStream(
					audiodata, AudioAnalysisThread.startedVoiceCheck.get(id).getAudioFormat());

			FileUtil.addWaveFileToLibrary(speechName, MAIN_PATH+SUB_PATH+speechName+"/wave/"
																			,audioInputStream);
			
			FileUtil.buildRawAudioDataTextFile(new String[] { Arrays.toString(AudioAnalysisThread.startedVoiceCheck.get(id).getIntStream())}
					, MAIN_PATH+SUB_PATH +speechName+"/rawAudioData",speechName,"instream");
		} 
	}
	
	public static void buildVoiceCompareImage() {
		
		if(AppSetup.voicePointsCompare && AudioAnalysisThread.startedVoiceCheck.get(id)
			.getVoiceRecognitionPointsArray() != null&&MiscellaneousData.bestDBMatchArray != null){
			
			image = BuildAudioImage.buildVoiceCompareImage(AudioAnalysisThread
				.startedVoiceCheck.get(id).getSpektrogramMap(), AudioAnalysisThread.startedVoiceCheck
				.get(id).getPointsBestDBMatchArray() , waveHeightLimit);
			
			FileUtil.addImageFileToLibrary(image,"VoiceCompare-"+speechName, MAIN_PATH+SUB_PATH
					+speechName+"/voiceCompare");
		}
	}
	
	public static void addToDeleteCheck() {
		
		if(AppSetup.deleteCheck)
			deleteList.add(DeleteLastAnalysisBuilder.createFinalDeleteList());
	}
	
	public static void removeAudioObjct() {
		
		Debug.debug(1, "SaveMultiAudioFeatures removing id: "+id);
		
		AudioAnalysisThread.startedVoiceCheck.remove(id);
		
		if(AudioAnalysisThread.startedVoiceCheck.size() == 0)
			AudioAnalysisThread.threadSleepTime = 1000;
	}
}
