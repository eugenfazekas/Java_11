package com.audio8.util;

import java.util.Arrays;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

public class FFTFrecvencyCheck {

	private static double[] buildDoubleArray(int[] intStream) {
		
        double[] normalizedStream = new double[intStream.length];
        for (int i = 0; i < intStream.length; i++) {
            normalizedStream[i] = intStream[i] / 32768.0;
        }

        // Bemeneti hossz meghatározása
        int originalLength = normalizedStream.length;

        // Legközelebbi 2 hatványának kiszámítása
        int paddedLength = 1;
        while (paddedLength < originalLength) {
            paddedLength *= 2;
        }

       System.out.println("originalLength: "+originalLength+", paddedLength: "+paddedLength);
        double[] paddedStream = new double[paddedLength];
        System.arraycopy(normalizedStream, 0, paddedStream, 0, originalLength);

        return paddedStream;
	} 
	
	public static void getFrecvencys(int[] stream) {
		
        // Példa adatok: valódi jel (szimulált int jel 1 másodperces stream)
        int sampleRate = 22050; // Mintavételezési frekvencia (Hz)
        int fftSize = 1024;     // FFT ablakméret (power of 2)
        int overlap = 512;      // Átfedés (50%)
        int[] signal = stream; // 1 másodpercnyi szimulált jel (22050 minta)

        // Számítsuk ki az időablakok számát
        int numberOfWindows = (signal.length - fftSize) / (fftSize - overlap) + 1;
        double[] magnitudes = new double[fftSize / 2]; // Magnitúdók tárolása
        double[] frequencies = new double[fftSize / 2]; // Frekvenciák tárolása

        // Számoljuk ki a frekvenciákhoz tartozó indexeket
        for (int k = 0; k < frequencies.length; k++) {
            frequencies[k] = (k * sampleRate) / (double) fftSize;
        }

        // Iterálunk az időablakokon
        for (int w = 0; w < numberOfWindows; w++) {
            // Az aktuális ablak kiválasztása
            int start = w * (fftSize - overlap);
            int end = start + fftSize;
            if (end > signal.length) break;

            // Az ablak adatainak kinyerése
            int[] window = Arrays.copyOfRange(signal, start, end);

            // FFT input: konvertáljuk double-re (normalizálás)
            double[] fftInput = new double[fftSize];
            for (int i = 0; i < fftSize; i++) {
                fftInput[i] = window[i] / 32768.0; // Normalizált értékek
            }

            // FFT alkalmazása az aktuális ablakra (használj Java FFT library-t, pl. JTransforms)
            double[] fftOutput = performFFT(fftInput);

            // Magnitúdók kiszámítása (csak pozitív frekvenciák)
            for (int k = 0; k < fftSize / 2; k++) {
                double real = fftOutput[2 * k];
                double imag = fftOutput[2 * k + 1];
               // System.out.println("k:  "+k+",real: " +real+", imag: "+imag);
                magnitudes[k] = Math.sqrt(real * real + imag * imag);
            }

            // Az aktuális ablak frekvenciakomponensei
            System.out.printf("Ablak %d, kezdési idő: %.2f s%n", w, start / (double) sampleRate);
            for (int k = 0; k < magnitudes.length; k++) {
                if (magnitudes[k] > 10) { // Csak jelentős komponensek
                    System.out.printf("  Frekvencia: %.2f Hz, Magnitúdó: %.4f%n", frequencies[k], magnitudes[k]);
                }
            }
        }
    }
	
    public static double[] performFFT(double[] input) {
    	
        FastFourierTransformer fft = new FastFourierTransformer(DftNormalization.STANDARD);
        Complex[] fftResult = fft.transform(input, TransformType.FORWARD);

        // Calculate the magnitudes of the FFT result
        double[] magnitudes = new double[fftResult.length];
        for (int i = 0; i < fftResult.length; i++) {
            magnitudes[i] = fftResult[i].abs();
        }
        
        return magnitudes;
      //  throw new UnsupportedOperationException("FFT library implementation required.");
    }
	
	public static void getFrecvencys(int[] stream, int sampleRate) {
		
		double[] signal = buildDoubleArray(stream);

	     int fftSize = 1024;     // FFT ablakméret
	     int overlap = 512;      // Átfedés (50%)
	     int streamLength = 22050; // 1 másodpercnyi stream (22050 minta)
	        
        // Perform FFT
        FastFourierTransformer fft = new FastFourierTransformer(DftNormalization.STANDARD);
        Complex[] fftResult = fft.transform(signal, TransformType.FORWARD);

        // Calculate the magnitudes of the FFT result
        double[] magnitudes = new double[fftResult.length];
        for (int i = 0; i < fftResult.length; i++) {
            magnitudes[i] = fftResult[i].abs();
        }

        // Frequency range for human speech (fundamental frequencies and harmonics)
        double minFreq = 85;  // Lower bound of human male voice frequency
        double maxFreq = 255; // Upper bound of human female voice frequency

        // Calculate the frequency corresponding to the peak index
        System.out.println("Dominant frequencies within the human speech range:");
        for (int i = 0; i < magnitudes.length; i++) {
            double frequency = (double) i * sampleRate / signal.length;
            if (frequency >= minFreq && frequency <= maxFreq) {
                System.out.println("Frequency: " + frequency + " Hz, Magnitude: " + magnitudes[i]);
            }
        }
        
        double[] frequencies = new double[fftSize / 2];
        for (int k = 0; k < frequencies.length; k++) {
            frequencies[k] = (k * sampleRate) / (double) fftSize;
        }
        
        System.out.println("Frekvenciák: " + Arrays.toString(frequencies));

        // Számoljuk ki az időablakokat
        int numberOfWindows = (streamLength - fftSize) / (fftSize - overlap) + 1;
        
        double[] timeWindows = new double[numberOfWindows];
        for (int w = 0; w < timeWindows.length; w++) {
            timeWindows[w] = (w * (fftSize - overlap)) / (double) sampleRate;
        }
        System.out.println("Időablakok (másodpercben): " + Arrays.toString(timeWindows));

        // Példa: Az első ablakhoz tartozó frekvencia spektrumot megjelenítheted
        System.out.println("1. ablak frekvenciakomponensei:");
        for (int k = 0; k < frequencies.length; k++) {
            System.out.printf("Frequency: %.2f Hz%n", frequencies[k]);
        }
	}
	
}
