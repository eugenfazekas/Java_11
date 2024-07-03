 package old;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.jtransforms.fft.DoubleFFT_1D;

import com.library.audio.audioGramSaver.SaveMultiAudioFeatures;
import com.library.util.Debug;
import com.library.util.FileUtil;
public class MySpektrumAnalysis {
	
	 private static AudioFormat format = null;
	 private static AudioInputStream audioInputStream;
	 private static byte[] audioData;
	 private static int[] audioSamples;
	 private static double[] resizedAudioSamples;
	 private static int sampleRate = 0;
	 private static int channels = 0;
	 private static int bitLength = 0;
	 private static boolean build = false;
	 private static double averageAmplitude = 0;
	 private static int positive = 0;
	 private static int negative = 0;
	 private static double  lastSV = 0;
	 private static int highestPositive = 0;
	 private static int highestNegative = 0;
	 private static int[] waveMap;
	 private static int waveCounter = 0;
	 private static int sharpnessGrower;
	 private static int waveHeightLimit = 100;
	 private static BufferedImage audioGram; 
	 private static double spectrogram[][];
	 public static int splineSequence = 1;
	 private static int msb = 0; 
	 private static int lsb = 0;
	 private static short endian = 0;
	 private static short sample = 0;
	 private static int validCounter = 0;
	 public static int VOLUME_LIMIT = 400;
	 public static int VALID_COUNTER = 3000;
     public static int index;
	 public static String[] dataLines = new String[3000];
	 
	 public static void buildMultiAudioGrammFromInputFile(String inputFileName) {
		buildAudioInputStreamFromFile(SaveMultiAudioFeatures.BASE_AUDIO_PATH+"Default_Wave_Library/" + inputFileName + FileUtil.inputAudioFileStreamType);
		FileUtil.buildAndCheckDirectoryPath(inputFileName,"wave","amplitude", "frequency","spektroGram");
		buildAmplitudoGram(inputFileName);
		buildFrequencyGram(inputFileName);
		buildSpektoGram(inputFileName);
		} 
	 
	 public static void buildMultiAudioGrammFromInputStream(AudioInputStream inputAudioInputStream, String outputFileName) {

		getAudiodData(inputAudioInputStream);
		buildSamples();
		buildAmplitudoGram(outputFileName);
		buildFrequencyGram(outputFileName);
		buildSpektoGram(outputFileName);
		FileUtil.buildRawAudioDataTextFile(dataLines,outputFileName);
	} 
	 
	 public static void buildMultiAudioGrammFromIntArray(int[] inputAudioArray, String outputFileName) {
		//audioData = inputAudioArray;
		buildAmplitudoGram(outputFileName);
		buildFrequencyGram(outputFileName);
		buildSpektoGram(outputFileName);
		FileUtil.buildRawAudioDataTextFile(dataLines,outputFileName);
	}
		 
	 public static void buildAmplitudoGram(String fileOuputName) {
		
		buildAmplitudeWaveMap();
		//rebuildWaveMapByRate(3);
		//buildSplineSequence(waveMap,splineSequence);
		trimWaveMap();
		buildImage(waveCounter,waveHeightLimit);
		FileUtil.addImageFileToLibrary(audioGram,fileOuputName,"amplitude");
	}
	
	public static void buildFrequencyGram( String speechName) {
		
		//getAudiodata(audioInputStream);
		//buildSamples();
		buildFrequencyWaveMap();
		rebuildWaveMapByRate(4);
		buildSplineSequence(waveMap,splineSequence);
		trimWaveMap();
		buildImage(waveCounter,waveHeightLimit);
		FileUtil.addImageFileToLibrary(audioGram,speechName,"frequency");
	}
	
	public static void buildSpektoGram(String speechName) {
		
		buildFFT();
		saveSpectrogramImage();
		FileUtil.addImageFileToLibrary(audioGram,speechName,"spektroGram");		
	}
		
	public static void buildAudioInputStreamFromFile(String filePath) {
		
		Debug.debug(3,"MySpektrumAnalysis buildAudioInputStreamFromFile filepath: "+filePath);
		try {
			 audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	private static void getAudiodData(AudioInputStream audioInputStream) {

			 format = audioInputStream.getFormat();
		     sampleRate = (int) format.getSampleRate();
		     channels = format.getChannels();
		     bitLength = getAudioBitLength(audioInputStream);	        
		     Debug.debug(1,"MySpektrumAnalysis getAudioData format: "+format+ " sampleRate: "+sampleRate+ " chanels: "+channels);     
			 audioData = new byte[(int) audioInputStream.getFrameLength() * format.getFrameSize()];
			 
	         try {
				audioInputStream.read(audioData);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	private static void buildSamples()  {
		boolean bigEndian = format.isBigEndian();
		audioSamples = new int[audioData.length / (2 * channels)];
		index = 0;
        for (int i = 0; i < audioData.length; i += 2* channels) {
    		
            sample =  getEndian(bigEndian,audioData[i],audioData[i+1]);	  
            audioSamples[index++] = sample;
            Debug.debug(5,"MySpektrumAnalysis buildSamples big-Endian sample: "+ sample +" i: "+i+" +audioData[i]+ "+audioData[i]+" audioData[i+1] "+audioData[i+1]);
        }
        	audioData = null;
    		waveMap = new int[audioSamples.length];
	}
	
	private static void gerInfo()  {
       
	    int max = 0; 
		for (int i = 0; i < audioSamples.length; i++) {
	
		     max = max > Math.abs(audioSamples[i]) ? max : Math.abs(sample);
		     averageAmplitude += Math.abs(audioSamples[i]);
		     validCounter = audioSamples[i] > VOLUME_LIMIT ? ++validCounter : validCounter;
		}   
        averageAmplitude =  averageAmplitude / ( audioSamples.length);
        sharpnessGrower = waveAmplitudeMultiplier();//(int) (averageAmplitude / 200);
        build = validCounter > 50 ? true : false;
        Debug.debug(1,"MySpektrumAnalysis gerInfo averageAmplitude "+averageAmplitude / (audioData.length/2)+" max amplitude: "+max);
        Debug.debug(1,"MySpektrumAnalysis gerInfo samples.length: "+audioSamples.length +" averageAmplitude: "+(averageAmplitude)+" "+waveAmplitudeMultiplier()+ " sharpnessGrower: "+sharpnessGrower + " validCounter: "+validCounter); 		

	}
	
	
	private static void buildResizedSamples() {
		
		resizedAudioSamples = new double[audioSamples.length];
		index = 0;
		
		for (int i = 0; i < audioSamples.length; i++) {
			
			resizedAudioSamples[index++] = (double)sample/32768;
		}
	}
	
	private static void buildAmplitudeWaveMap() {
		
		if(!build) return;

			float sampleAvg = 0;
			int sharpGrowerSum = 0;
		    float tempSamples = 0;
		    float pos = 0;
		    Debug.debug(5,"MySpektrumAnalysis buildAmplitudeWaveMap 1. WaveMapsamples.length: "+ audioSamples.length);
			for(int i = 0; i < audioSamples.length; i++) {
				Debug.debug(5,"MySpektrumAnalysis buildAmplitudeWaveMap 2. samples[i] "+audioSamples[i]);
				if(audioSamples[i] > 0) {
					//System.out.println("buildAmplitudeWaveMap samples[i] "+samples[i]);
					pos += audioSamples[i]*100 ;
					//positive += samples[i]*((100/averageAmplitude) * averageAmplitude*2) ;
					tempSamples++;
				}
				
				if(audioSamples[i] < 0) {
					
					if(pos != 0) {
						if(tempSamples == 0)
							tempSamples++;
						sampleAvg = (pos / tempSamples );
						sharpGrowerSum = sharpGrowerSum(sampleAvg,sharpnessGrower );
						Debug.debug(5,"MySpektrumAnalysis buildAmplitudeWaveMap 3.  "+" sampleAvg:" +sampleAvg +" sharpGrowerSum: "+sharpGrowerSum+" waveCounter: "+waveCounter +" positve: "+ pos + " tempSamples: "+tempSamples+ " audioSamples[i]: "+audioSamples[i] );
						waveMap[waveCounter++]  = sharpGrowerSum;							
					}
					tempSamples = 0;
					pos = 0;
					sampleAvg=0;
					sharpGrowerSum=0;
				}

			}
			pos = 0; 
			//waveHeightLimit = sharpnessGrower * 2;
			 Debug.debug(3,"MySpektrumAnalysis buildAmplitudeWaveMap 4.waveMap.length: "+waveMap.length + " waveCounter: "+waveCounter );
		}
	
	private static void buildFrequencyWaveMap() {
		
		if(!build) return;
		
	    StringBuilder sb = new StringBuilder();	
	    DecimalFormat df = new DecimalFormat("0.000");
	    
		waveCounter = 0;
		waveMap = new int[audioSamples.length / (2 * channels)];
		Debug.debug(3,"MySpektrumAnalysis buildFrequencyWaveMap samples.length: "+ audioSamples.length+ " waveMap.length "+waveMap.length);
		for(int i = 0; i < audioSamples.length; i++) {
			
			if(audioSamples[i] > 0 && lastSV >=0 ) {
				positive++;
				lastSV = audioSamples[i];
				sb.append(df.format(audioSamples[i]*100)+", ");
			}
			
			if(audioSamples[i] <= 0) {
				negative++;
				lastSV = audioSamples[i];
				sb.append(df.format(audioSamples[i]*100)+", ");
			}
			Debug.debug(5,"MySpektrumAnalysis buildFrequencyWaveMap lastSV "+lastSV);
			if(audioSamples[i] > 0 && lastSV <0 ) {
				//System.out.println("waveCounter"+waveCounter);
				//System.out.println("samples[i]:"+samples[i]);
				int frequency = getFrequency(positive+negative);
				Debug.debug(5,"MySpektrumAnalysis 3.5. frequency: "+frequency);
				int map = 0;
				map = getMappedFrequency(frequency);//*sharpnessGrower;
				if(waveCounter < 500) {
					Debug.debug(5,"MySpektrumAnalysis 4.counter: "+waveCounter+ " frequency: "+frequency+" map: "+ map+ " positive: "+positive+ " negative: "+negative);
				}
				waveMap[waveCounter++] = map;
				
				if(positive > highestPositive)
					highestPositive = positive;
				
				if(negative < highestNegative) 
					highestNegative = negative;
				
				positive = 1;
				negative = 0;
				lastSV = (int)audioSamples[i];
				dataLines[waveCounter-1]= sb.toString();
				sb = null;
				sb = new StringBuilder();
			}
			
		}
		Debug.debug(5,"MySpektrumAnalysis buildFrequencyWaveMap waveCounter:"+waveCounter);
		
		//System.out.println("low Frecvency: "+getLoWFrequencyMap(100));
		//System.out.println("middle Frecvency: "+getMiddleFrequencyMap(200));
		//System.out.println("high Frecvency: "+getHighFrequencyMap(2001));
	}
	
	private static int getMappedFrequency(int frequency) {
             int mappedFrequency = 0;
             
             if(frequency <= 200) {
            	 mappedFrequency = getLoWFrequencyMap(frequency); 
            	 Debug.debug(5,"getMappedFrequency getLoWFrequencyMap called ");
             }
             
             if(frequency > 200 && frequency< 2000) {
            	 mappedFrequency = getMiddleFrequencyMap(frequency); 
            	 Debug.debug(5,"getMappedFrequency getMiddleFrequencyMap called ");
             }
             
             if( frequency>= 2000) {
            	 mappedFrequency = getHighFrequencyMap(frequency); 
            	 Debug.debug(5,"getMappedFrequency getHighFrequencyMap called ");
             }
             
		return mappedFrequency;
	}
	
	private static int getFrequency(int cycleSamples) {
		
		int freq = sampleRate / cycleSamples;
		Debug.debug(5,"1. freq: "+freq);
		return freq;
	}
	
	private static void rebuildWaveMapByRate(int waveMapRate ) {
		
		if(!build) return;
		int newWaveMap[] = new int[waveCounter / waveMapRate];
		
		Debug.debug(5,"MySpektrumAnalysis rebuildWaveMapByRate newWaveMap.length: "+ newWaveMap.length +" waveCounter: "+waveCounter);
		
		int rate = 0;
		int average = 0;
		int	counter = 0;
		
		for (int i = 0; i < waveCounter; i++) {
			
			average += waveMap[i];
			rate++;
			
			if(rate == waveMapRate ) {
				newWaveMap[counter++] = average / waveMapRate;
				average = 0;
				rate = 0;				
			}			
		}
		Debug.debug(5,"MySpektrumAnalysis rebuildWaveMapByRate counter: "+counter);
		waveCounter = newWaveMap.length;
		waveMap = newWaveMap;
	}
		
	private static void buildImage(int width, int height) {
		
		if(!build) return;
		
		   Debug.debug(3,"MySpektrumAnalysis buildImage getRoundToOneHundred(waveMap.length) "+getRoundTo(20,waveMap.length)+ " waveMap.length "+waveMap.length+ " height: "+height);
		   audioGram = new BufferedImage(getRoundTo(20,width),height, BufferedImage.TYPE_INT_ARGB);	  
		    for (int x = 0; x < audioGram.getWidth(); x++) {
		    	
		    	for (int  y = audioGram.getHeight(); y > 0; y--) {
		    		
		    		if(x < waveMap.length && y <= waveMap[x]  ) {
		    			audioGram.setRGB(x, Math.abs(y-audioGram.getHeight()), new Color(0,0,0).getRGB());
		    			Debug.debug(5,"buildImage x: "+x + " waveMap[x] "+waveMap[x]+ " waveMap.length: "+waveMap.length);
		    		}
		    	}
		    }	
		    waveMap=null;
	}
	
	private static void trimWaveMap() {
		
		if(!build) return;	
		int counter = 0;
		
		for(int i = 1; i < waveMap.length; i++) {
			if(waveMap[i-1] > 1)
				counter++;
		}
		
		int newWaveMap[] = new int[counter+6];
		
		counter= 0;
		for(int i = 0; i < waveMap.length-3; i++) {
			if(waveMap[i] > 1 || i < 3) {
				newWaveMap[counter++] = waveMap[i];
			}
		}	
			waveMap = newWaveMap;	
			waveCounter = waveMap.length;
			Debug.debug(5,"end trim");
	}
	
	public static int[] buildSplineSequence(int[] arr, int length ) {
		
		length = length > 2 ? length : 2; 
		double iterationLength= Math.floor(arr.length/length-1)*length;
		int[] res = new int [arr.length];
		int splineAverage  = 0;
		int dir = 0;
		for (int i = 1 ; i < iterationLength; i+=length) {
			splineAverage = 0;
			dir = 0;
			Debug.debug(5,"MySpektrumAnalysis buildSplineSequence main i: "+i+" splineAverage " +splineAverage + " dir "+dir);
			for(int j = 1; j < length; j++) {
				//System.out.println("splinecalc: "+(arr[i+j]-arr[i-1+j]));
				splineAverage+= arr[i+j]-arr[i-1+j];
			}
			Debug.debug(5,"MySpektrumAnalysis buildSplineSequence splineAverage: "+splineAverage+" direction: "+dir + " length -1 "+(length-1));
			dir = splineAverage / (length-1);
			Debug.debug(5,"MySpektrumAnalysis buildSplineSequence splineAverage: "+splineAverage+" direction: "+dir + " length -1 "+(length-1) + " sum " + (splineAverage / length-1));
			for(int k = 0; k < length; k++) {
				if(i+k < 0 || i+ k +dir < 0) { 
					Debug.debug(5,"MySpektrumAnalysis buildSplineSequence bound exception1 "+ (i+k )+" "+(i+ k +dir)+" dir: "+dir);
					res[i+k] = 0;
						continue;
				}
				if(i+k > arr.length || i+ k +dir > arr.length) { 
					Debug.debug(5,"MySpektrumAnalysis buildSplineSequence bound exception2 "+ (i+k )+" "+(i+ k +dir)+" dir: "+dir);
					res[i+k] = arr.length ;
					continue;
				}
				else {
					Debug.debug(5,"MySpektrumAnalysis buildSplineSequence else i+k: " +(i+ k)+ "  arr[(i-1)]+k+dir "+ (arr[(i-1)]+k*dir)+" dir: "+dir);
					res[i+k]= arr[(i)]+k*dir;
				}
			}
			Debug.debug(5,"MySpektrumAnalysis buildSplineSequence splineAverage1 : "+splineAverage / (length-1));
			Debug.debug(5,"MySpektrumAnalysis buildSplineSequence splineAverage2 : "+splineAverage);
	}
		//System.out.println(Arrays.toString(res));
		return waveMap = res;
	}
		
	private static int getLoWFrequencyMap(int frequency) {
		
		return (int)(frequency * 33.3)/200;
	}
	
	private static int sharpGrowerSum(float initvalue, int growerTimes) {
		
		float sum = 0;
		
		for(int i = 0; i < growerTimes; i++)
			sum +=  initvalue;
		
		return (int)sum;
	}
	
	
	private static int getMiddleFrequencyMap(int frequency) {
		
		return (int)((((frequency-200) * 33.3)/1800)+34);
	}
	
	private static int getHighFrequencyMap(int frequency) {
		
		return (int)((((frequency-2000) * 33.3)/20500)+67);
	}
	
    private static int getRoundTo(int roundTO, int inputnumber) {
		
		while(inputnumber % roundTO != 0) {
			inputnumber++;
		}
		
		return inputnumber;
	}

    private static int waveAmplitudeMultiplier() {
    	
    	int average = (int) ((waveHeightLimit * 30) /averageAmplitude);
    		return average ;
    }

   private static void buildFFT() {
	   
       int fftSize = 1024; // FFT mérete
       int overlap = 512; // Az ablakok átfedése
       
       // Spektrogramm készítése
       int numFrames = audioSamples.length / overlap - (fftSize / overlap - 1);
       spectrogram = new double[fftSize / 2 + 1][numFrames];
       DoubleFFT_1D fft = new DoubleFFT_1D(fftSize);
       
       for (int frame = 0; frame < numFrames; frame++) {
           double[] frameSamples = new double[fftSize];
           int startIndex = frame * overlap;
           for (int i = 0; i < fftSize; i++) {
               frameSamples[i] = audioSamples[startIndex + i];
           }
           
           fft.realForward(frameSamples);
           
           for (int i = 0; i <= fftSize / 2-1; i++) {
               double magnitude = Math.sqrt(frameSamples[2 * i] * frameSamples[2 * i] + frameSamples[2 * i + 1] * frameSamples[2 * i + 1]);
               spectrogram[i][frame] = magnitude;
           }
       }
   }
   
	   private static void saveSpectrogramImage() {
		   
		   if(!build) return;
	       int width = spectrogram[0].length;
	       int height = spectrogram.length;
	       audioGram = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	       
	       // Az adatok átalakítása és a kép elkészítése
	       double maxMagnitude = getMaxMagnitude(spectrogram);
	       
	       for (int y = 0; y < height; y++) {
	           for (int x = 0; x < width; x++) {
	               float magnitude = (float) (spectrogram[y][x] / maxMagnitude);
	               int colorValue = (255 << 24) | ((int) (magnitude * 255) << 16) | ((int) (magnitude * 255) << 8) | (int) (magnitude * 255);
	               audioGram.setRGB(x, y, colorValue);
	           }
	       }	    
	   }
   
   private static double getMaxMagnitude(double[][] spectrogram) {
       double maxMagnitude = Double.MIN_VALUE;
       for (double[] row : spectrogram) {
           for (double value : row) {
               if (value > maxMagnitude) {
                   maxMagnitude = value;
               }
           }
       }
       return maxMagnitude;
   }
   
   private static short getSampleBigEndian(int data0, int data1) {
	   
		 msb = data0 & 0xFF; // Magasabb rendű (MSB) byte
	     lsb = data1 & 0xFF; // Kisebb rendű (LSB) byte
	     	return sample =  (short)((msb << 8) | lsb & 0xFF); // Átalakítás a megfelelő sorrendbe (big endian)   
   }
   
   private static short getSampleLitteleEndian(int data0, int data1) {
	   
	     lsb = data0 & 0xFF; // Kisebb rendű (LSB) byte
	     msb = data1 & 0xFF; // Magasabb rendű (MSB) byte
	     	return sample =  (short)((msb << 8) | lsb & 0xFF); // Átalakítás a megfelelő sorrendbe (little endian)
   }
   
   private static short getEndian(boolean bigEndian, int data0, int data1) {
	   
	    endian = bigEndian ? getSampleBigEndian(data0,  data1) : getSampleLitteleEndian(data0,  data1);
	    	return endian;
   }
   
	private static int getAudioBitLength(AudioInputStream audioInputStream) {
		
		int bitLength = 0;
		String[] formatDetails = audioInputStream.getFormat().toString().split(",");
		String [] format = formatDetails[1].split(" ");
		bitLength = Integer.parseInt(format[1]);
		Debug.debug(5,"MySpektrumAnalysis getAudioBitLength "+bitLength);	
			return bitLength;
	}

}

 
