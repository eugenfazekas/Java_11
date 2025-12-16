package voiceRecognition.audio5.audioGramBuilder;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

import voiceRecognition.audio0.main.AppSetup;
import voiceRecognition.audio8.util.Debug;

public class FFTFrecvencyCheckUtil {
	
	private static int paddedLength;
	private static double[] normalizedStream;
	private static double frequency;
	private static double magnitude;
	
	private static int[] result;
	private static int resultCounter;
	
	private static int i;
	private static int streamLength;
	private static int counter;
	private static float mag_x_freqbuffer;
	private static float mag_buffer;

	private static int debug_level_INFO = 5;
	private static int debud_level_DEBUG = 5;
	
	private static void buildDoubleArray(int[] intStream) {
		
        paddedLength = 1;
        
        while(paddedLength * 2 < intStream.length) 
            paddedLength *= 2;
		
        normalizedStream = new double[paddedLength];
        
        Debug.debug(debug_level_INFO,"FFTFrecvencyCheck buildDoubleArray normalizedStream.length: "
        		+normalizedStream.length + ", intStream.length: " + intStream.length);
        
        for (i = 0; i < paddedLength; i++) 
            normalizedStream[i] = intStream[i] / 32768.0;      
	}
	
	private static void buildDoubleArray(int[] intStream1, int[] intStream2, int[] intStream3) {
		
		streamLength = intStream1.length * 10;
        paddedLength = 1;
        
        while(paddedLength * 2 < streamLength) 
            paddedLength *= 2;
        		
        normalizedStream = new double[paddedLength];    
        i=0;
       // i = (paddedLength - (3*intStream1.length))/2 ;
        
        Debug.debug(debug_level_INFO,"FFTFrecvencyCheck buildDoubleArray i: "+i+ ", intStream1.length: "
        		+intStream1.length + ", i+ data: " + (i+(3 *intStream1.length)) + ", paddedLength: "
        		+paddedLength);
        
        counter = 0;
        
        while(counter < intStream1.length && i < paddedLength) 	
        	normalizedStream[i++] = intStream1[counter++] / 32768.0;      
        
        counter = 0;
        
        while(counter < intStream2.length && i < paddedLength )        	
        	normalizedStream[i++] = intStream2[counter++] / 32768.0;
         
        counter = 0;
                
        while(counter < intStream3.length && i < paddedLength )     
        	normalizedStream[i++] = intStream3[counter++] / 32768.0;       
        
        Debug.debug(debug_level_INFO,"FFTFrecvencyCheck buildDoubleArray i: "+i + ", paddedLength: "
        		+paddedLength);
	}
	
	static int[] getFrecvencys(int[] stream1,int[] stream2, int[] stream3, int sampleRate) {
		
		buildDoubleArraySelector(stream1, stream2, stream3);

        FastFourierTransformer fft = new FastFourierTransformer(DftNormalization.STANDARD);
        Complex[] fftResult = fft.transform(normalizedStream, TransformType.FORWARD);

		mag_x_freqbuffer = 0;
		mag_buffer = 0;
		frequency=0;
		i=0;
		counter = 0;
		
		result = new int[fftResult.length/32];
		resultCounter = 0;
		
		for(int i = 0; i < fftResult.length/64; i++) {
						
	    	magnitude = fftResult[i].abs();
	        frequency = (double) i * sampleRate / normalizedStream.length;
			//frequencys[frequencysCounter++] = new Frequency(magnitude, frequency);
			
	        result[resultCounter++] = (int) magnitude;
	        result[resultCounter++] = (int) frequency;
	        
		    if(frequency < AppSetup.FFT_UPPER_LIMIT_CHECK) {
		    	
		    	mag_x_freqbuffer += frequency * magnitude;
		    	mag_buffer += magnitude; 
		    	counter++;
		     
		        Debug.debug(debud_level_DEBUG,"FFTFrecvencyCheck getFrecvencys Frequency: " + frequency 
		        	+ " Hz, Magnitude: " + magnitude);
		    }
		    if(i == fftResult.length/2 -2) {		    System.out.println("magnitude "+magnitude +" frequency "+frequency);}
		    	
		}
		
    	result[0] = 0;
    	result[1] = mag_buffer / counter > AppSetup.FFT_MAGNITUDE_LIMIT ? 
    			(int)(mag_x_freqbuffer / mag_buffer)  : 20;

    	Debug.debug(debug_level_INFO,"FFTFrecvencyCheck getFrecvencys result: "+ result[1]);
    	
    		return result;
	}
	
	private static void buildDoubleArraySelector (int[] stream1,int[] stream2, int[] stream3) {
		
		if(stream2 == null)
			buildDoubleArray(stream1);
		
		else
			buildDoubleArray(stream1, stream2, stream3);
	}
}
