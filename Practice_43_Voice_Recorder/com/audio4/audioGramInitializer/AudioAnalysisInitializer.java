package com.audio4.audioGramInitializer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

public class AudioAnalysisInitializer   {	

	public static AudioFormat format = null;
	public static AudioInputStream audioInputStream;
	public static byte[] audioData;
	public static int [] audioSamples;
	public static int [] audioStreamDetails;
	public static int sampleRate ;
	public static int channels;
	public static int bitLength;
    public static int index;

	public static int averageAmplitude;
	public static float sharpnessGrower;

	public static int validCounter;
	//public static int minFrequencySampleCount;
	//public static int maxFrequencySampleCount;
	/* 
	public static void mainBuilder(int id) {
		
	    resetVariables();
	
		if(AudioAnalysisThread.startedVoiceCheck.get(id).getByteStream() != null 
				&& AudioAnalysisThread.startedVoiceCheck.get(id).getIntStream() == null) {
			
			Debug.debug(3,"AnalysisInitializer mainBuilder byteStream.length: "
			+AudioAnalysisThread.startedVoiceCheck.get(id).getByteStream().length+
			" avgAmplitude: "+AudioAnalysisThread.startedVoiceCheck.get(id).getAverageAmplitude());
			
			buildAudiodDataFromByte(AudioAnalysisThread.startedVoiceCheck.get(id).getByteStream()
					,AudioAnalysisThread.startedVoiceCheck.get(id).getAudioFormat());
			
			buildSamples();

				return;
		}
		
		if(AudioAnalysisThread.startedVoiceCheck.get(id).getIntStream() != null) {
						
			audioSamples = AudioAnalysisThread.startedVoiceCheck.get(id).getIntStream();
			averageAmplitude = AudioAnalysisThread.startedVoiceCheck.get(id).getAverageAmplitude();
			
			buildAudioFormatInfo(AudioAnalysisThread.startedVoiceCheck.get(id).getAudioFormat());

			setSharpnessGrower();
			
			Debug.debug(3,"AnalysisInitializer builder audioSamples.length: "+audioSamples.length 
					+" avgAmplitude: "+averageAmplitude);
			
				return;
		}
		
		if(AudioAnalysisThread.startedVoiceCheck.get(id).getWaveStream() != null 
				&& AudioAnalysisThread.startedVoiceCheck.get(id).getIntStream() == null 
				&& AudioAnalysisThread.startedVoiceCheck.get(id).getByteStream() == null) {
			
			Debug.debug(3,"AnalysisInitializer mainBuilder audiostreamDetails.length: "
				+AudioAnalysisThread.startedVoiceCheck.get(id).getWaveStream().length 
				+" avgAmplitude: "
				+AudioAnalysisThread.startedVoiceCheck.get(id).getAverageAmplitude());
			
			audioStreamDetails = AudioAnalysisThread.startedVoiceCheck.get(id).getWaveStream();
			averageAmplitude = AudioAnalysisThread.startedVoiceCheck.get(id).getAverageAmplitude();

			buildAudioFormatInfo(AudioAnalysisThread.startedVoiceCheck.get(id).getAudioFormat());
				
			setSharpnessGrower();
		}
	}


	 	 
	public static void buildAudiodDataFromByte(byte[] byteAudioStream , AudioFormat audioFormat ){
		
		audioData = byteAudioStream;
		
		buildAudioFormatInfo(audioFormat);
		
		Debug.debug(3,"AnalysisInitializer buildAudiodDataFromByte");
	}
	
    public static void buildSamples()  {	  

	   audioSamples = new int[(audioData.length / (2 * channels))];
	   index = 0;
	   
	   for (int i = 0; i < audioData.length; i += 2* channels) {
			
	       sample = getEndian(format.isBigEndian(),audioData[i],audioData[i+1]);	       
	       audioSamples[index++] = sample ;	   
	       
	       buildInfo(sample); 
	   }
	   
	   averageAmplitude =  averageAmplitude / audioSamples.length;
	   
	   Debug.debug(3,"AnalysisInitializer buildSamples "+audioSamples[audioSamples.length-2]);
	   
	   buildInfoResult();		
	}

    public static void buildInfo(int sample)  {
  
		averageAmplitude += Math.abs(sample);
		validCounter = sample > AppSetup.STARTER_VOLUME_LIMIT ? ++validCounter : validCounter;
	
		Debug.debug(5,"AnalysisInitializer buildInfo samples.length: "+validCounter); 		
    }
  
    public static void setSharpnessGrower() {
    	
	  sharpnessGrower = waveAmplitudeMultiplier();
	  
	  Debug.debug(4,"AnalysisInitializer setSharpnessGrower sharpnessGrower: "+sharpnessGrower); 
    }
  
    public static void buildInfoResult() {
	  
	    setSharpnessGrower();
	    
		Debug.debug(4,"AnalysisInitializer buildInfoResult build: "+AudioAnalysisThread.build 
			+" averageAmplitude: "+(averageAmplitude)+ " sharpnessGrower: "+sharpnessGrower 
			+ " validCounter: "+validCounter); 
    }



	public static int getAudioBitLength(AudioInputStream audioInputStream) {
		
		int bitLength = 0;
		String[] formatDetails = audioInputStream.getFormat().toString().split(",");
		String [] format = formatDetails[1].split(" ");
		bitLength = Integer.parseInt(format[1]);
		
		Debug.debug(2,"AnalysisInitializer getAudioBitLength "+bitLength);	
		
			return bitLength;
	}
	
    public static float waveAmplitudeMultiplier()  {
    	
    	float average =  (float) (( SaveMultiAudioFeatures.waveHeightLimit 
    			* AppSetup.SHARPNESS_GROVER_MULTIPLIER) /averageAmplitude);
    	
    	Debug.debug(2,"AnalysisInitializer waveAmplitudeMultiplier (AudioGramSaver.waveHeightLimit "
    		+ " * 30) "+(SaveMultiAudioFeatures.waveHeightLimit * 30)
    		+ " averageAmplitude: "+ averageAmplitude + " average: "+average);
    	
    		return average ;
    }
    
    public static void buildAudioFormatInfo(AudioFormat audioFormat) {
    	
		format = audioFormat;
		channels = format.getChannels();
		sampleRate = (int)audioFormat.getSampleRate();		
		minFrequencySampleCount = sampleRate / AppSetup.LOW_FREQUENCY_FILTER;
		maxFrequencySampleCount = sampleRate / AppSetup.HIGH_FREQUENCY_FILTER ; 

    }
    

    
    private static void resetVariables() {
    	
		audioSamples = null;
		audioStreamDetails = null;
		AudioAnalysisThread.build = false;
		averageAmplitude = 0;
    }
   */
}

	  
