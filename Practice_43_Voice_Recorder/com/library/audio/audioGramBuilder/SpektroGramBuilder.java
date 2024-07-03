package com.library.audio.audioGramBuilder;

import org.jtransforms.fft.DoubleFFT_1D;

import com.library.audio.audioGramInitializer.AudioGramInitializer;
import com.library.audio.audioGramSaver.SaveMultiAudioFeatures;
import com.library.util.Debug;

public class SpektroGramBuilder {
	   
	   public static void mainBuilder() {
		   
	       int fftSize = 1024; // FFT mérete
	       int overlap = 512; // Az ablakok átfedése
	       
	       // Spektrogramm készítése
	       int numFrames = AudioGramInitializer.audioSamples.length / overlap - (fftSize / overlap - 1);
	       SaveMultiAudioFeatures.spectrogram = new double[fftSize / 2 + 1][numFrames];
	       DoubleFFT_1D fft = new DoubleFFT_1D(fftSize);
	       
	       for (int frame = 0; frame < numFrames; frame++) {
	           double[] frameSamples = new double[fftSize];
	           int startIndex = frame * overlap;
	           for (int i = 0; i < fftSize; i++) {
	               frameSamples[i] = AudioGramInitializer.audioSamples[startIndex + i];
	           }
	           
	           fft.realForward(frameSamples);
	           
	           for (int i = 0; i <= fftSize / 2-1; i++) {
	               double magnitude = Math.sqrt(frameSamples[2 * i] * frameSamples[2 * i] + frameSamples[2 * i + 1] * frameSamples[2 * i + 1]);
	               SaveMultiAudioFeatures.spectrogram[i][frame] = magnitude;
	           }
	       }      
	       Debug.debug(2,"SpektroGramBuilder buildFFT spectrogram length: "+SaveMultiAudioFeatures.spectrogram.length + " spectrogram[0].length: "+SaveMultiAudioFeatures.spectrogram[0].length );
	   }
}
