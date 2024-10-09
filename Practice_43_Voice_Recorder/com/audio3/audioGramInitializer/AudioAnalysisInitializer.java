package com.audio3.audioGramInitializer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

import com.audio0.main.AppSetup;
import com.audio2.soundBuilder.SoundBuilder;
import com.audio5.audioGramSaver.SaveMultiAudioFeatures;
import com.audio8.util.Debug;

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
	 public static short sample;
	 public static int averageAmplitude;
	 public static float sharpnessGrower;
	 public static boolean build = false;
	 public static int msb; 
	 public static int lsb;
	 public static short endian = 0;
	 public static int validCounter;
	 
	public static void mainBuilder(byte[] byteStream, int[] intStream,int[] audiostreamDetails, AudioFormat audioFormat, int avgAmplitude) {
		

	    resetVariables();
		Debug.debug(3,"AnalysisInitializer mainBuilder avgAmplitude: "+avgAmplitude );
	
		if(intStream == null && byteStream != null) {
			buildAudioFormatInfo(audioFormat);
			buildSamples();
				return;
		}
		
		if(intStream != null) {
			audioSamples = intStream;
			buildAudioFormatInfo(audioFormat);
			averageAmplitude = avgAmplitude;
			setSharpnessGrower();
			build = true;
				return;
		}
		
		if(audiostreamDetails != null && intStream == null && byteStream == null) {
			buildAudioFormatInfo(audioFormat);
			audioStreamDetails = audiostreamDetails;
			averageAmplitude = avgAmplitude;
			setSharpnessGrower();
			build = true;
		}
	}

	public static void buildAudiodDataFromInt(int[] intStream)	{
			
		audioData = SoundBuilder.convertIntArrayToByteArray(intStream);
		Debug.debug(3,"AnalysisInitializer buildAudiodDataFromInt");
	}
	 	 
	public static void buildAudiodDataFromByte(byte[] byteAudioStream , AudioFormat audioFormat )	{
		
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
	   averageAmplitude =  averageAmplitude / ( audioSamples.length);
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
		build = validCounter > 50 ? true : false;
	    setSharpnessGrower();
		Debug.debug(4,"AnalysisInitializer buildInfoResult build: "+build +" averageAmplitude: "+(averageAmplitude)+ " sharpnessGrower: "+sharpnessGrower + " validCounter: "+validCounter); 
  }

	public static short getSampleBigEndian(int data0, int data1) {
		   
		 msb = data0 & 0xFF; // Magasabb rendű (MSB) byte
	     lsb = data1 & 0xFF; // Kisebb rendű (LSB) byte
		     	return sample =  (short)((msb << 8) | lsb & 0xFF); // Átalakítás a megfelelő sorrendbe (big endian)   
	}

	public static short getSampleLitteleEndian(int data0, int data1) {
		   
	     lsb = data0 & 0xFF; // Kisebb rendű (LSB) byte
	     msb = data1 & 0xFF; // Magasabb rendű (MSB) byte
		     	return sample =  (short)((msb << 8) | lsb & 0xFF); // Átalakítás a megfelelő sorrendbe (little endian)
	}

	public static short getEndian(boolean bigEndian, int data0, int data1) {
		   
		    endian = bigEndian ? getSampleBigEndian(data0,  data1) : getSampleLitteleEndian(data0,  data1);
		    	return endian;
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
    	
    	float average =  (float) (( SaveMultiAudioFeatures.waveHeightLimit * AppSetup.SHARPNESS_GROVER_MULTIPLIER) /averageAmplitude);
    	Debug.debug(2,"AnalysisInitializer waveAmplitudeMultiplier (AudioGramSaver.waveHeightLimit * 30) "+(SaveMultiAudioFeatures.waveHeightLimit * 30)
    			+ " averageAmplitude: "+ averageAmplitude + " average: "+average);
    		return average ;
    }
    
    public static void buildAudioFormatInfo(AudioFormat audioFormat) {
		format = audioFormat;
		channels = format.getChannels();
		sampleRate = (int)audioFormat.getSampleRate();
    }
    
    public static int getBuffersLengthByMilisec(int sampleRate, int milisec) {
   		
    		return (milisec * sampleRate) /1000;
    }
    
    private static void resetVariables() {
		audioSamples = null;
		audioStreamDetails = null;
		build = false;
		averageAmplitude =0;
    }
}

	  
