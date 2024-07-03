package com.sound;
import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.io.*;
import org.jtransforms.fft.DoubleFFT_1D;
import javax.imageio.ImageIO;

public class WaveToSpectrogram {
    public static void gpt() {
        String filePath = "test3.wav";
        int fftSize = 1024; // FFT mérete
        int overlap = 512; // Az ablakok átfedése
        
        try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath))) {
            AudioFormat format = audioInputStream.getFormat();
            int sampleRate = (int) format.getSampleRate();
            int channels = format.getChannels();
            
            byte[] audioData = new byte[(int) audioInputStream.getFrameLength() * format.getFrameSize()];
            audioInputStream.read(audioData);
            

            
            // Hangadatok konvertálása double típusra
            double[] audioSamples = new double[audioData.length / (2 * channels)];
            int index = 0;
            for (int i = 0; i < audioData.length; i += 2 * channels) {
                short sample = (short) ((audioData[i + 1] << 8) | (audioData[i] & 0xFF));         
                audioSamples[index] = sample / 32768.0; // Normalizálás -1 és 1 közé
                index++;
                System.out.println("i: "+i+" sample:"+sample+" audioData[i]: "+audioData[i]);
                System.out.println("i: "+(i+1)+" sample:"+sample+" audioData[i+1]: "+audioData[i+1]);
            }
            System.out.println("format: "+format+ " sampleRate: "+sampleRate+ " chanels: "+channels);
            // Spektrogramm készítése
            int numFrames = audioSamples.length / overlap - (fftSize / overlap - 1);
            double[][] spectrogram = new double[fftSize / 2 + 1][numFrames];
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
            
            // Spektrogramm adatok használata
            saveSpectrogramImage(spectrogram);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void saveSpectrogramImage(double[][] spectrogram) {
        int width = spectrogram[0].length;
        int height = spectrogram.length;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        
        // Az adatok átalakítása és a kép elkészítése
        double maxMagnitude = getMaxMagnitude(spectrogram);
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                float magnitude = (float) (spectrogram[y][x] / maxMagnitude);
                int colorValue = (255 << 24) | ((int) (magnitude * 255) << 16) | ((int) (magnitude * 255) << 8) | (int) (magnitude * 255);
                image.setRGB(x, y, colorValue);
            }
        }
        
        // Kép mentése PNG fájlba
        try {
            File outputImage = new File("image.png");
            ImageIO.write(image, "png", outputImage);
        } catch (IOException e) {
            e.printStackTrace();
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