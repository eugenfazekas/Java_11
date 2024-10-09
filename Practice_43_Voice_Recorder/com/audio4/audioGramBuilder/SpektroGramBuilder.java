package com.audio4.audioGramBuilder;

import org.jtransforms.fft.DoubleFFT_1D;

import com.audio0.main.AppSetup;
import com.audio3.audioGramInitializer.AudioAnalysisInitializer;
import com.audio8.util.Debug;

public class SpektroGramBuilder {
	
	public static double spectrogram[][];
	   
	   public static void mainBuilder() {
		   
		   if(!AppSetup.spektrogram)
			   return;
		   
	       int fftSize = 1024; // FFT mérete
	       int overlap = 512;
	   	   // Az ablakok átfedése
	       
	       // Spektrogramm készítése
	       int numFrames = AudioAnalysisInitializer.audioSamples.length / overlap - (fftSize / overlap - 1);
	       spectrogram = new double[fftSize / 2 + 1][numFrames];
	       DoubleFFT_1D fft = new DoubleFFT_1D(fftSize);
	       
	       for (int frame = 0; frame < numFrames; frame++) {
	           double[] frameSamples = new double[fftSize];
	           int startIndex = frame * overlap;
	           for (int i = 0; i < fftSize; i++) {
	               frameSamples[i] = AudioAnalysisInitializer.audioSamples[startIndex + i] / 32736f;
	           }
	           
	           fft.realForward(frameSamples);
	           
	           for (int i = 0; i <= fftSize / 2-1; i++) {
	               double magnitude = Math.sqrt(frameSamples[2 * i] * frameSamples[2 * i] + frameSamples[2 * i + 1] * frameSamples[2 * i + 1]);
	               spectrogram[i][frame] = magnitude;
	           }
	       }      
	       Debug.debug(2,"SpektroGramBuilder buildFFT spectrogram length: "+spectrogram.length + " spectrogram[0].length: "+spectrogram[0].length );
	   }
}
