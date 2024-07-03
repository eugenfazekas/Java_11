package com.library.audio.audioGramInitializer;

import java.io.IOException;
import java.util.Arrays;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

import com.library.audio.audioGramSaver.SaveMultiAudioFeatures;
import com.library.audio.soundBuilder.SoundBuilder;
import com.library.util.Debug;

public class AudioGramInitializer   {	

	 public static AudioFormat format = null;
	 public static AudioInputStream audioInputStream;
	 public static byte[] audioData;
	 public static float[] audioSamples;
	 public static int sampleRate ;
	 public static int channels;
	 public static int bitLength;
     public static int index;
	 public static short sample;
	 public static double averageAmplitude;
	 public static float sharpnessGrower;
	 public static boolean build = false;
	 public static int msb; 
	 public static int lsb;
	 public static short endian = 0;
	 public static int validCounter;
	 public static int VOLUME_LIMIT = 5000;
	 public final static int VALID_COUNTER = 1000;
	 public static double[] resizedAudioSamples;
	 public static int[] waveMap;
	 public static int[] tempBuffer;
	 public static int bufferCounter = 0;
 	 public static int waveCounter;
	 public static int[][] timeSequenceBuffers;
	 public final static int BUFFERS_LENGTH_TIMED_INTERVAL_MILISEC = 2; 
	 public  static int AVG_MILISEC_LENGTH; 
	 public  static int avgMilisecCounter;
	 private static int total;
	 private static int avg = 0;
	 
	public static void buildAudiodData(int[] intStream , AudioFormat audioFormat )	{
			
			audioData = SoundBuilder.convertIntArrayToByteArray(intStream);
			buildAudioFormatInfo(audioFormat);
		}
	 	 
	public static void buildAudiodData(byte[] byteAudioStream , AudioFormat audioFormat )	{
		
		audioData = byteAudioStream;
		buildAudioFormatInfo(audioFormat);
	}
	
	public static void buildAudiodData(AudioInputStream audioInputStream) {
		
		 format = audioInputStream.getFormat();
	     sampleRate = (int) format.getSampleRate();	     
	     bitLength = getAudioBitLength(audioInputStream);	    
	     Debug.debug(2,"AnalysisInitializer getAudioData format: "+format+ " sampleRate: "+sampleRate+ " chanels: "+channels);     
		 audioData = new byte[(int) audioInputStream.getFrameLength() * format.getFrameSize()];
	     buildAudioFormatInfo(format);
		 
        try {
			audioInputStream.read(audioData);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}

  public static void buildSamples()  {	   
	   int counter = 0;
	   int bufferCounter = 0;
	   int samplesBufferLength = getBuffersLengthByMilisec(BUFFERS_LENGTH_TIMED_INTERVAL_MILISEC);
	   float timeSequenceBuffersReminder = (audioData.length / (2 * channels) % samplesBufferLength);
	   int samplesLength = (audioData.length / (2 * channels));
	   int timeSequenceBuffersLength =  timeSequenceBuffersReminder == 0 ?
			   								samplesLength / samplesBufferLength: 
			   							    samplesLength / samplesBufferLength + 1;
	   timeSequenceBuffers = new int[timeSequenceBuffersLength][]; 
	   timeSequenceBuffers[0] = new int[samplesBufferLength];
	   timeSequenceBuffers[timeSequenceBuffersLength -1] = timeSequenceBuffersReminder == 0 ? new int[samplesBufferLength] : new int [ (int) timeSequenceBuffersReminder];
	   Debug.debug(2,"AnalysisInitializer buildSamples samples length: "+ samplesLength +" timeSequenceBuffers.length: "+timeSequenceBuffers.length
			   + " samplesBufferLength: "+samplesBufferLength+ " reminder: "+timeSequenceBuffersReminder);
	   boolean bigEndian = format.isBigEndian();
	   audioSamples = new float[samplesLength];
	   index = 0;
	   for (int i = 0; i < audioData.length; i += 2* channels) {
			
	       sample = getEndian(bigEndian,audioData[i],audioData[i+1]);	
	       
	       audioSamples[index++] = sample / 32736f;
	       //System.out.println(" sample "+ sample  + " sample / 32736     " + (sample / 32736d) + " audioSamples[index++] "+audioSamples[index-1] );
	      // System.out.println("bufferCounter: "+bufferCounter + " counter: "+counter+ " sample: "+sample);
	       timeSequenceBuffers[bufferCounter][counter++] = sample;
	       
	       buildInfo(sample);
	       
	       if(counter == samplesBufferLength) {
	    	   bufferCounter++;
	    	   counter = 0;
	    	   Debug.debug(5,"AnalysisInitializer bufferCounter "+bufferCounter+" Array: "+Arrays.toString(timeSequenceBuffers[bufferCounter-1]));
	    	   if(bufferCounter < timeSequenceBuffersLength - 1) {
	    		   timeSequenceBuffers[bufferCounter] = new int [samplesBufferLength];	    
	    	   }	    	   
	       }     
	   }
	   // System.out.println(" audioSamples last" + audioSamples[+ audioSamples.length-1]+ " buffer last: "
	   //+timeSequenceBuffers[timeSequenceBuffersLength-1][timeSequenceBuffers[timeSequenceBuffersLength-1].length-2] );
	   Debug.debug(5,"AnalysisInitializer audioSamples "+audioSamples[audioSamples.length-2]+" Array: " +Arrays.toString(timeSequenceBuffers[timeSequenceBuffersLength-1]));
	   buildInfoResult();		
	}
  
  public static void buildSamplesFromIntStream(int[] inputSamples)  {	
	  
	   int counter = 0;
	   int bufferCounter = 0;
	   int samplesBufferLength = getBuffersLengthByMilisec(BUFFERS_LENGTH_TIMED_INTERVAL_MILISEC);
	   float timeSequenceBuffersReminder = inputSamples.length % samplesBufferLength;
	   int samplesLength = inputSamples.length;
	   int timeSequenceBuffersLength =  timeSequenceBuffersReminder == 0 ?
			   								samplesLength / samplesBufferLength: 
			   							    samplesLength / samplesBufferLength + 1;
	   timeSequenceBuffers = new int[timeSequenceBuffersLength][]; 
	   timeSequenceBuffers[0] = new int[samplesBufferLength];
	   timeSequenceBuffers[timeSequenceBuffersLength -1] = timeSequenceBuffersReminder == 0 ? new int[samplesBufferLength] : new int [ (int) timeSequenceBuffersReminder];
	   Debug.debug(2,"AnalysisInitializer buildSamples samples length: "+ samplesLength +" timeSequenceBuffers.length: "+timeSequenceBuffers.length
			   + " samplesBufferLength: "+samplesBufferLength+ " reminder: "+timeSequenceBuffersReminder);
	   audioSamples = new float[inputSamples.length];
	   index = 0;
	   for (int i = 0; i < inputSamples.length; i ++) {
	
	       audioSamples[index++] = inputSamples[i] / 32736f;
	       //System.out.println(" sample "+ sample  + " sample / 32736     " + (sample / 32736d) + " audioSamples[index++] "+audioSamples[index-1] );
	      // System.out.println("bufferCounter: "+bufferCounter + " counter: "+counter+ " sample: "+sample);
	       timeSequenceBuffers[bufferCounter][counter++] = inputSamples[i];
	       
	       buildInfo(inputSamples[i]);
	       
	       if(counter == samplesBufferLength) {
	    	   bufferCounter++;
	    	   counter = 0;
	    	   Debug.debug(5,"AnalysisInitializer bufferCounter "+bufferCounter+" Array: "+Arrays.toString(timeSequenceBuffers[bufferCounter-1]));
	    	   if(bufferCounter < timeSequenceBuffersLength - 1) {
	    		   timeSequenceBuffers[bufferCounter] = new int [samplesBufferLength];	    
	    	   }	    	   
	       }     
	   }
	   // System.out.println(" audioSamples last" + audioSamples[+ audioSamples.length-1]+ " buffer last: "
	   //+timeSequenceBuffers[timeSequenceBuffersLength-1][timeSequenceBuffers[timeSequenceBuffersLength-1].length-2] );
	   Debug.debug(5,"AnalysisInitializer audioSamples "+audioSamples[audioSamples.length-2]+" Array: " +Arrays.toString(timeSequenceBuffers[timeSequenceBuffersLength-1]));
	   buildInfoResult();		
	}

  public static void buildInfo(int sample)  {
  
		averageAmplitude += Math.abs(sample);
		validCounter = sample > VOLUME_LIMIT ? ++validCounter : validCounter;
	
		Debug.debug(5,"AnalysisInitializer buildInfo samples.length: "+validCounter); 		
}
  
  public static void buildInfoResult() {
		averageAmplitude =  averageAmplitude / ( audioSamples.length);
		sharpnessGrower = waveAmplitudeMultiplier();//(int) (averageAmplitude / 200);
		build = validCounter > 50 ? true : false;
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
		Debug.debug(2,"MySpektrumAnalysis getAudioBitLength "+bitLength);	
			return bitLength;
	}
	
    public static float waveAmplitudeMultiplier()  {
    	
    	float average =  (float) (( SaveMultiAudioFeatures.waveHeightLimit * 35) /averageAmplitude);
    	Debug.debug(2,"AnalysisInitializer waveAmplitudeMultiplier (AudioGramSaver.waveHeightLimit * 30) "+(SaveMultiAudioFeatures.waveHeightLimit * 40)
    			+ " averageAmplitude: "+ averageAmplitude + " average: "+average);
    		return average ;
    }
    
    public static void buildAudioFormatInfo(AudioFormat audioFormat) {
		format = audioFormat;
		channels = format.getChannels();
		sampleRate = (int)audioFormat.getSampleRate();
    }
    
    public static int getBuffersLengthByMilisec(int milisec) {
   	
    	int sampleRate = (int)format.getSampleRate();
    	
    		return (milisec * sampleRate) /1000;
    }
    
	 public static void addToBuffer(int bufferValue) {
		 
		 tempBuffer[AudioGramInitializer.bufferCounter++] = bufferValue;
		 Debug.debug(5,"AnalysisInitializer addToBuffer bufferValue: "+bufferValue + ",  bufferCounter: "+(bufferCounter-1)+ " , tempBuffer.length: "+AudioGramInitializer.tempBuffer.length);
	 }
	 
	 public static void addToWaveMap() {
		 
		 if(bufferCounter == 0 )
			 return;

		 for(int i = 0 ; i < tempBuffer.length; i++ ) 
			 total += tempBuffer[i];
		 
		 	 avg = total / bufferCounter ;
			 
		 waveMap[waveCounter++] =  avg; 
		 tempBuffer = new int[AudioGramInitializer.AVG_MILISEC_LENGTH];
		 tempBuffer[0] = -1;
		 
		 Debug.debug(5,"addToWaveMap total: "+total+ " / bufferCounter "+ bufferCounter + " avg: "+avg+ " ,AnalysisInitializer.waveCounter: "+AudioGramInitializer.waveCounter+" ,wavemap [waveCounter]: "+AudioGramInitializer.waveMap[AudioGramInitializer.waveCounter-1]);	
		 bufferCounter = 0;
		 total = 0;

	 }
}

	  
