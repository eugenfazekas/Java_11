package com.audio5.audioGramBuilder;

import org.jtransforms.fft.DoubleFFT_1D;

import com.audio0.main.AppSetup;
import com.audio4.audioGramInitializer.mainInit.AudioAnalysisThread;
import com.audio8.util.Debug;

public class SpektroGramBuilder {
	
	private static int debug_level_INFO = 5;
		   
	static double[][] mainBuilder(int id) {
		   
	    if(!AppSetup.spektrogram) return null;
	    
		double spectrogram[][];
	   
        int fftSize = 1024; // FFT mérete
        int overlap = 512;
   	    // Az ablakok átfedése
       
        // Spektrogramm készítése
        int numFrames = AudioAnalysisThread.startedVoiceCheck.get(id).getIntStream()
        		.length/overlap-(fftSize/overlap-1);
        spectrogram = new double[fftSize / 2 + 1][numFrames];
        DoubleFFT_1D fft = new DoubleFFT_1D(fftSize);
       
        for (int frame = 0; frame < numFrames; frame++) {
        	
            double[] frameSamples = new double[fftSize];
            int startIndex = frame * overlap;
            
            for (int i = 0; i < fftSize; i++) {
            	
               frameSamples[i] = AudioAnalysisThread.startedVoiceCheck.get(id)
            		   .getIntStream()[startIndex + i] / 32736f;
            }
           
            fft.realForward(frameSamples);
           
            for (int i = 0; i <= fftSize / 2-1; i++) {
            	
                double magnitude = Math.sqrt(frameSamples[2 * i] * frameSamples[2 * i] 
                + frameSamples[2 * i + 1] * frameSamples[2 * i + 1]);
                
                spectrogram[i][frame] = magnitude;
            }
        }
        
        Debug.debug(debug_level_INFO,"SpektroGramBuilder buildFFT spectrogram length: "+spectrogram.length 
        	+ " spectrogram[0].length: "+spectrogram[0].length );
        
        return spectrogram; 
	}
}
