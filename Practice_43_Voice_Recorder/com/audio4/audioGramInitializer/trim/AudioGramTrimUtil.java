package com.audio4.audioGramInitializer.trim;

import java.util.Arrays;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

import com.audio0.main.AppSetup;
import com.audio8.util.Debug;

public class AudioGramTrimUtil {
	
	private static int paddedLength;
	private static double[] normalizedStream;
	//private static double[] magnitudes;
	private static double frequency;
	private static double magnitude;
	private static int[] result;
	private static int i;
	private static int streamLength;
	private static int counter;
	private static float mag_x_freqbuffer;
	private static float mag_buffer;
//	private static Frequency[] frequencys;
//	private static Frequency[] tempFrequencys;
	private static int debugLevel = 5;
	
	private static void buildDoubleArray(int[] intStream) {
		
        paddedLength = 1;
        
        while (paddedLength * 2 < intStream.length) {
            paddedLength *= 2;
        }
		
        normalizedStream = new double[paddedLength];
        Debug.debug(debugLevel,"AudioGramTrimUtil buildDoubleArray normalizedStream.length: "
        		+normalizedStream.length + ", intStream.length: " + intStream.length);
        for (i = 0; i < paddedLength; i++) {
            normalizedStream[i] = intStream[i] / 32768.0;
        }
	}
	
	private static void buildDoubleArray(int[] intStream1, int[] intStream2, int[] intStream3) {
		
		streamLength = intStream1.length * 10;
        paddedLength = 1;
        
        while (paddedLength * 2 < streamLength) {
            paddedLength *= 2;
        }
		
        normalizedStream = new double[paddedLength];    
        i=0;
       // i = (paddedLength - (3*intStream1.length))/2 ;
        
        Debug.debug(debugLevel,"AudioGramTrimUtil buildDoubleArray i: "+i+ ", intStream1.length: "
        +intStream1.length+", i+ data: "+(i+(3 *intStream1.length))+", paddedLength: "+paddedLength);
        
        counter = 0;
        
        while(counter < intStream1.length && i < paddedLength) {  
        	
        	normalizedStream[i++] = intStream1[counter++] / 32768.0;
        	
        }
        
        counter = 0;
        
        while(counter < intStream2.length && i < paddedLength )  {   
        	
        	normalizedStream[i++] = intStream2[counter++] / 32768.0;
        }
    
        counter = 0;
        
        
        while(counter < intStream3.length && i < paddedLength )     {
        	normalizedStream[i++] = intStream3[counter++] / 32768.0;
        }
        
        Debug.debug(debugLevel,"AudioGramTrimUtil buildDoubleArray i: "+i + ", paddedLength: "
        		+paddedLength);
	}
	
	static int[] getFrecvencys(int[] stream1,int[] stream2, int[] stream3, int sampleRate) {
		
		buildDoubleArraySelector(stream1, stream2, stream3);

        FastFourierTransformer fft = new FastFourierTransformer(DftNormalization.STANDARD);
        Complex[] fftResult = fft.transform(normalizedStream, TransformType.FORWARD);

		mag_x_freqbuffer = 0;
		mag_buffer = 0;
		//frequencys = new Frequency[400];
		frequency=0;
		i=0;
		counter = 0;
		
        while(frequency < AppSetup.FFT_UPPER_LIMIT_CHECK) {
        	
        	i++;
        	magnitude = fftResult[i].abs();
            frequency = (double) i * sampleRate / normalizedStream.length;
        	mag_x_freqbuffer += frequency * magnitude;
        	mag_buffer += magnitude; 
        	counter++;
         
            Debug.debug(debugLevel,"AudioGramTrimUtil getFrecvencys Frequency: " + frequency 
            		+ " Hz, Magnitude: " + magnitude);
        }
       
    	result = mag_buffer / counter > AppSetup.FFT_MAGNITUDE_LIMIT ?  new int[] 
    			{0, (int)(mag_x_freqbuffer / mag_buffer) } : new int[]{0,20};

    	Debug.debug(debugLevel,"AudioGramTrimUtil getFrecvencys result: "+ result[1]);
        return result;
	}
	
	private static void buildDoubleArraySelector (int[] stream1,int[] stream2, int[] stream3) {
		
		if(stream2 == null)
			buildDoubleArray(stream1);
		
		else
			buildDoubleArray(stream1, stream2, stream3);
	}
	
	static int splineSequence = 1;
	   
	static int getRoundTo(int roundTO, int inputnumber) {
			
		while(inputnumber % roundTO != 0) 			
			inputnumber++;
				
		return inputnumber;
	}
	
    static int getBuffersLengthByMilisec(int sampleRate, int milisec) {
   		
    	return (milisec * sampleRate) /1000;
    }
    
    static byte[] buildAudiodDataFromInt(int[] intStream)	{
		
		Debug.debug(3,"AudioGramTrimUtil buildAudiodDataFromInt intSream.Length: "
			+intStream.length);
		
		return convertIntArrayToByteArray(intStream);
	}
    
	static int[]  buildSequenceBorders(int[] input, int length, int  startLimit, int endLimit) {
		
		Debug.debug(1,"AudioGramUtil buildSequenceBorders input Array:  "  + Arrays.toString(input));
		int start = 0;
		int end = 0;
		int[] result;
		for(int i = 0; i < length; i++) {
			
			if(input[i]>startLimit) { 
				start = i > 1 ? i-2 : 0;
				break;
			}
		}
		
		for(int i = length-1; i > 0; i--) {
			
			if(input[i]>endLimit) {
				end = i < input.length-2 ? i+2 : i;
				break;
			}		
		}
		
		result = new int[] {1,start,input[start], -1,end,input[end]};
		Debug.debug(1,"AudioGramUtil buildSequenceBorders "+ Arrays.toString(result));
		
		return result;
	}
	
	static byte[] convertIntArrayToByteArray(int[] intStream) {
		
		byte[] byte_stram = new byte[intStream.length * 2];
		int counter = 0;
		
		for(int i = 0; i < intStream.length; i++) {  

			byte_stram[counter++] = (byte) (intStream[i] >> 8);
			byte_stram[counter++] = (byte) intStream[i] ;
		}
		
		Debug.debug(2,"AudioGramTrimUtil convertIntArrayToByteArray byte_Sequence.length "
			+byte_stram.length);
		
			return byte_stram;
	}
}
