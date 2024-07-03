 package com.sound;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.jtransforms.fft.DoubleFFT_1D;

import com.sound.util.PathUtil;

public class MySpektrumAnalysis {
	
	 private static AudioFormat format = null;
	 private static AudioInputStream audioInputStream;
	 private static byte[] audioData;
	 private static double[] audioSamples;
	 private static int sampleRate = 0;
	 private static int channels = 0;
	 private static int bitLength = 0;
	 private static double averageAmplitude = 0;
	 private static int positive = 0;
	 private static int negative = 0;
	 private static double  lastSV = 0;
	 private static int highestPositive = 0;
	 private static int highestNegative = 0;
	 private static int[] waveMap;
	 private static int waveCounter = 0;
	 private static int sharpnessGrower = 2;
	 private static int waveHeightLimit = 100;
	 private static BufferedImage audioGram; 
	 private static double spectrogram[][];
	 public static int splineSequence = 1;
	 
	 public static void buildMultiAudioGrammFromInputFile(String inputFileName) {
		buildAudioInputStreamFromFile(PathUtil.getBaseAudioPath()+"Default_Wave_Library/" + inputFileName + PathUtil.inputAudioFileStreamType);
		PathUtil.buildAndCheckDirectoryPath(inputFileName,"wave","amplitude", "frequency","spektroGram");
		buildAmplitudoGram(inputFileName);
		buildFrequencyGram(inputFileName);
		buildSpektoGram(inputFileName);
		} 
	 
	 public static void buildMultiAudioGrammFromInputStream(AudioInputStream inputAudioInputStream, String outputFileName) {
		
		audioInputStream = inputAudioInputStream;
		PathUtil.buildAndCheckDirectoryPath(outputFileName,"wave","amplitude", "frequency","spektroGram");
		buildAmplitudoGram(outputFileName);
		buildFrequencyGram(outputFileName);
		buildSpektoGram(outputFileName);
	} 
	

	 
	 public static void buildAmplitudoGram(String fileOuputName) {
		
		getAudiodata(audioInputStream);
		buildSamples();
		buildAmplitudeWaveMap();
		//rebuildWaveMapByRate(3);
		//buildSplineSequence(waveMap,splineSequence);
		//trimWaveMap();
		buildImage(waveCounter,waveHeightLimit * sharpnessGrower );
		PathUtil.addImageFileToLibrary(audioGram,fileOuputName,"amplitude");
	}
	
	public static void buildFrequencyGram( String speechName) {
		
		//getAudiodata(audioInputStream);
		//buildSamples();
		buildFrequencyWaveMap();
		rebuildWaveMapByRate(4);
		buildSplineSequence(waveMap,splineSequence);
		//trimWaveMap();
		buildImage(waveCounter,(waveHeightLimit * sharpnessGrower )*2);
		PathUtil.addImageFileToLibrary(audioGram,speechName,"frequency");
	}
	
	public static void buildSpektoGram(String speechName) {
		
		buildFFT();
		saveSpectrogramImage();
		PathUtil.addImageFileToLibrary(audioGram,speechName,"spektroGram");		
	}
		
	public static void buildAudioInputStreamFromFile(String filePath) {
		
		System.out.println("buildAudioInputStreamFromFile filepath: "+filePath);
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
	
	private static void getAudiodata(AudioInputStream audioInputStream) {

			 format = audioInputStream.getFormat();
		     sampleRate = (int) format.getSampleRate();
		     channels = format.getChannels();
		     bitLength = getAudioBitLength(audioInputStream);
		        
		     System.out.println("format: "+format+ " sampleRate: "+sampleRate+ " chanels: "+channels);
		     
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
         double[] samples = new double[audioData.length / (2 * channels)];
         
         waveMap = new int[audioData.length / (2 * channels)];
	         int index = 0;
	         short sample = 0;
	 		 int msb = 0; // Magasabb rendű (MSB) byte
		     int lsb = 0;
		     int max = 0;
         for (int i = 0; i < audioData.length; i += 2* channels) {
        	 
        	 
        	 if(bigEndian) {
        		 
        		 msb = audioData[i] & 0xFF; // Magasabb rendű (MSB) byte
        	     lsb = audioData[i + 1] & 0xFF; // Kisebb rendű (LSB) byte
                 sample =  (short)((msb << 8) | lsb & 0xFF); // Átalakítás a megfelelő sorrendbe (big endian)
                 samples[index++] = (double)sample/32768;
    		     averageAmplitude += Math.abs(sample);
    		     max = max > Math.abs(sample) ? max : Math.abs(sample);
    	        // System.out.println("buildSamples big-Endian sample: "+ sample + " sample/32768: "+(double)sample/32768  +" audioSamples[index] " +samples[index-1]+" i: "+i+" audioData[i] "+audioData[i]+" audioData[i+1] "+audioData[i+1]);
        	 }
        	 
        	 if(!bigEndian) {
        		 
    		     lsb = audioData[i] & 0xFF; // Kisebb rendű (LSB) byte
    		     msb = audioData[i + 1] & 0xFF; // Magasabb rendű (MSB) byte
    		     sample =  (short)((msb << 8) | lsb & 0xFF); // Átalakítás a megfelelő sorrendbe (little endian)
    		     samples[index++] = (double)sample/32768;
    		     averageAmplitude += Math.abs(sample);
    		     max = max > Math.abs(sample) ? max : Math.abs(sample);
    		     //System.out.println("buildSamples little-Endian sample: "+ sample + " sample/32768: "+(double)sample/32768 +" audioSamples[index] " +samples[index-1]+" i: "+i+" audioData[i] "+audioData[i]+" audioData[i+1] "+audioData[i+1]);
        	 }
         }
         audioSamples = samples;
         System.out.println("averageAmplitude "+averageAmplitude / (audioData.length/2)+" max amplitude: "+max);
         averageAmplitude =  averageAmplitude / audioSamples.length/2;
         System.out.println("buildSamples samples.length: "+audioSamples.length +" averageAmplitude: "+(averageAmplitude)+" "+waveAmplitudeMultiplier() );
	}
	
	private static void buildAmplitudeWaveMap() {
		
		    int tempSamples = 0;
			
			//System.out.println("samples.length: "+ samples.length);
			for(int i = 0; i < audioSamples.length; i++) {
				//System.out.println("buildAmplitudeWaveMap samples[i] "+samples[i]);
				if(audioSamples[i] > 0) {
					//System.out.println("buildAmplitudeWaveMap samples[i] "+samples[i]);
					positive += audioSamples[i]*100 ;
					//positive += samples[i]*((100/averageAmplitude) * averageAmplitude*2) ;
					tempSamples++;
				}
				
				if(audioSamples[i] < 0) {
					
					if(positive != 0) {
						waveMap[waveCounter++]  = positive / tempSamples;		
						//System.out.println("buildAmplitudeWaveMap:  "+(positive / tempSamples) +" waveCounter: "+waveCounter );
					}
					tempSamples = 0;
					positive = 0;
				}

			}
			positive = 0; 
			System.out.println("buildAmplitudeWaveMap waveMap.length: "+waveMap.length + " waveCounter: "+waveCounter );
		}
	
	private static void buildFrequencyWaveMap() {
		waveCounter = 0;
		waveMap = new int[audioData.length / (2 * channels)];
		System.out.println("buildFrequencyWaveMap samples.length: "+ audioSamples.length+ " waveMap.length "+waveMap.length);
		for(int i = 0; i < audioSamples.length; i++) {
			
			if(audioSamples[i] > 0 && lastSV >=0 ) {
				positive++;
				lastSV = audioSamples[i];
			}
			
			if(audioSamples[i] <= 0) {
				negative++;
				lastSV = audioSamples[i];
			}
			//System.out.println(" buildFrequencyWaveMap lastSV "+lastSV);
			if(audioSamples[i] > 0 && lastSV <0 ) {
				//System.out.println("waveCounter"+waveCounter);
				//System.out.println("samples[i]:"+samples[i]);
				int frequency = getFrequency(positive+negative);
				int map = 0;
				map = getMappedFrequency(frequency)*sharpnessGrower;
				if(waveCounter < 500) {
				//System.out.println("4.counter: "+waveCounter+ " frequency: "+frequency+" map: "+ map+ " positive: "+positive+ " negative: "+negative);
				}
				waveMap[waveCounter++] = map;
				
				if(positive > highestPositive)
					highestPositive = positive;
				
				if(negative < highestNegative) 
					highestNegative = negative;
				
				positive = 1;
				negative = 0;
				lastSV = (int)audioSamples[i];
				
			}
			
		}
		System.out.println("buildFrequencyWaveMap waveCounter:"+waveCounter);
		
		System.out.println("low Frecvency: "+getLoWFrequencyMap(100));
		System.out.println("middle Frecvency: "+getMiddleFrequencyMap(200));
		System.out.println("high Frecvency: "+getHighFrequencyMap(2001));
	}
	
	private static int getMappedFrequency(int frequency) {
             int mappedFrequency = 0;
             
             if(frequency <= 200) {
            	 mappedFrequency = getLoWFrequencyMap(frequency); 
            	// System.out.println("getMappedFrequency getLoWFrequencyMap called ");
             }
             
             if(frequency > 200 && frequency< 2000) {
            	 mappedFrequency = getMiddleFrequencyMap(frequency); 
            	// System.out.println("getMappedFrequency getMiddleFrequencyMap called ");
             }
             
             if( frequency>= 2000) {
            	 mappedFrequency = getHighFrequencyMap(frequency); 
            	// System.out.println("getMappedFrequency getHighFrequencyMap called ");
             }
             
		return mappedFrequency;
	}
	
	private static int getFrequency(int cycleSamples) {
		
		int freq = sampleRate / cycleSamples;
		//System.out.println("1. freq: "+freq);
		return freq;
	}
	
	private static void rebuildWaveMapByRate(int waveMapRate ) {
		
		int newWaveMap[] = new int[waveCounter / waveMapRate];
		
		System.out.println("rebuildWaveMapByRate newWaveMap.length: "+ newWaveMap.length +" waveCounter: "+waveCounter  );
		
		int rate = 0;
		int average = 0;
		int	counter = 0;
		

		for (int i = 0; i < waveCounter; i++) {
			
			average += waveMap[i];
			rate++;
			
			if(rate == waveMapRate ) {
				newWaveMap[counter++] = (average / waveMapRate)*sharpnessGrower;
				average = 0;
				rate = 0;				
			}			
		}
		System.out.println("rebuildWaveMapByRate counter: "+counter);
		waveCounter = newWaveMap.length;
		waveMap = newWaveMap;
	}
	
	private static int getAudioBitLength(AudioInputStream audioInputStream) {
	
		int bitLength = 0;
		String[] formatDetails = audioInputStream.getFormat().toString().split(",");
		String [] format = formatDetails[1].split(" ");
		bitLength = Integer.parseInt(format[1]);
		System.out.println(bitLength);
		
		return bitLength;
	}
	
	private static void buildImage(int width, int height) {
		
			
		   audioGram = new BufferedImage(getRoundToOneHundred(width),height, BufferedImage.TYPE_INT_ARGB);
		   System.out.println("getRoundToOneHundred(waveMap.length) "+getRoundToOneHundred(waveMap.length)+ " waveMap.length "+waveMap.length);
		    for (int x = 0; x < audioGram.getWidth(); x++) {
		    	//System.out.println("buildImage x: "+x + " waveMap[x] "+waveMap[x]);
		    	for (int  y = audioGram.getHeight(); y > 0; y--) {
		    		
		    		if(x < waveMap.length && y <= waveMap[x]  ) {
		    			audioGram.setRGB(x, Math.abs(y-audioGram.getHeight()), new Color(0,0,0).getRGB());
		    		}
		    	}
		    }	
		    waveMap=null;
	}
/*	
	private static void saveSpektroGram() {
	        try {
	            File outputImage = new File(imagePath+".png");
	            ImageIO.write(image, "png", outputImage);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }  
		}
	*/

	
	private static void trimWaveMap() {
		
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
			System.out.println("end trim");
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
			//System.out.println("main i: "+i+" splineAverage " +splineAverage + " dir "+dir);
			for(int j = 1; j < length; j++) {
				//System.out.println("splinecalc: "+(arr[i+j]-arr[i-1+j]));
				splineAverage+= arr[i+j]-arr[i-1+j];
			}
			//System.out.println("splineAverage: "+splineAverage+" direction: "+dir + " length -1 "+(length-1));
			dir = splineAverage / (length-1);
			//System.out.println("splineAverage: "+splineAverage+" direction: "+dir + " length -1 "+(length-1) + " sum " + (splineAverage / length-1));
			for(int k = 0; k < length; k++) {
				if(i+k < 0 || i+ k +dir < 0) { 
					//System.out.println("bound exception1 "+ (i+k )+" "+(i+ k +dir)+" dir: "+dir);
					res[i+k] = 0;
						continue;
				}
				if(i+k > arr.length || i+ k +dir > arr.length) { 
					//System.out.println("bound exception2 "+ (i+k )+" "+(i+ k +dir)+" dir: "+dir);
					res[i+k] = arr.length ;
					continue;
				}
				else {
					//System.out.println("else i+k: " +(i+ k)+ "  arr[(i-1)]+k+dir "+ (arr[(i-1)]+k*dir)+" dir: "+dir);
					res[i+k]= arr[(i)]+k*dir;
				}
			}
			//System.out.println("splineAverage1 : "+splineAverage / (length-1));
			//System.out.println("splineAverage2 : "+splineAverage);
	}
		//System.out.println(Arrays.toString(res));
		return waveMap = res;
	}
		
	private static int getLoWFrequencyMap(int frequency) {
		
		return (int)(frequency * 33.3)/200;
	}
	
	private static int getMiddleFrequencyMap(int frequency) {
		
		return (int)((((frequency-200) * 33.3)/1800)+34);
	}
	
	private static int getHighFrequencyMap(int frequency) {
		
		return (int)((((frequency-2000) * 33.3)/20500)+67);
	}
	
    private static int getRoundToOneHundred(int numberToRound) {
		
		while(numberToRound % 100 != 0) {
			numberToRound++;
		}
		
		return numberToRound;
	}
    
    private static int waveAmplitudeMultiplier() {
    	
    	int average = (int) (100/averageAmplitude * averageAmplitude);
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
}
/*
 * 	private static void buildImage() {
	

   spectoGram = new BufferedImage((int)Math.ceil((double)waveMap.length /100) *100,waveHeightLimit, BufferedImage.TYPE_INT_ARGB);
   System.out.println("Math.ceil(waveMap.length /100)"+Math.ceil((double)waveMap.length)+ " waveMap.length "+waveMap.length);
    for (int x = 0; x < spectoGram.getWidth(); x++) {
    	for (int y = 0; y < spectoGram.getHeight(); y++) {
    		//System.out.println("buildImage x: "+x + " y: "+y);
    		//if(y > spectoGram.getHeight()/2+ waveMap[x]/2 || y < spectoGram.getHeight()/2 - waveMap[x]/2)    
    		//System.out.println("buildImage Y: "+y+ "x "+x + " waveMap[x]: "+ waveMap[x] );
    		if(( y > spectoGram.getHeight()/2&& x < waveMap.length && y < spectoGram.getHeight()/2+ waveMap[x]  ) ||(y <= spectoGram.getHeight()/2 && x < waveMap.length && y >= spectoGram.getHeight()/2- waveMap[x]) ) {
    			spectoGram.setRGB(x, y, new Color(0,0,0).getRGB());

    		}
    		
    		//if( x > waveMap.length-4 && ( y == spectoGram.getHeight()/2 || y == spectoGram.getHeight()/2-1))
    		if( x > waveMap.length-4 && ( y == spectoGram.getHeight()/2 ))
    			spectoGram.setRGB(x, y, new Color(0,0,0).getRGB());      		
           System.out.println("magnitude*100: "+((int) magnitude*1000));
            image.setRGB(x, y, new Color((int) magnitude*1000,0,0).getRGB());
    	}
    }
}
*/
 
