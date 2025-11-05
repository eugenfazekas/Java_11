package audio;

import java.util.Arrays;
import java.util.Random;

public class Test {
	
    private static int favgMilisecCounter = 0;
    private static int F_AVG_MILISEC_LENGTH = 20;
    private static int mSecAmplitudeBuffer;
	private static int [] mSecFrequencyBuffer;
    private static int mSecFrequencyBufferCounter;
    private static int[] firstBuffer;
    private static int[] middleBuffer;
    private static int[] lastBuffer;
    private static int firstAmplitudeBuffer;
    private static int middleAmplitudeBuffer;

	public static void main(String[] args) {

		double root = Math.pow(100, 1/4.0);
		
		System.out.println("root: "+root);
		System.out.println("root: "+Math.pow(3.16, 4));
//		for(int i = 0; i < 100; i++) {
//			prebuildMsecCheck(i, getRandom());
//		}
	}

	static int getRandom() {
		
		Random random = new Random();
		return random.nextInt(1000 - 100) + 100;
	}
	public static void prebuildMsecCheck(int i, int sample) {
		
		if(lastBuffer != null)
			return;
				
		if(firstBuffer == null && favgMilisecCounter == 0) 	{
			
			firstBuffer = new int[F_AVG_MILISEC_LENGTH];
			firstAmplitudeBuffer = 0;
			System.out.println("1.");
		}		
		
		if(favgMilisecCounter == F_AVG_MILISEC_LENGTH) {
			
			System.out.println("2.");
			favgMilisecCounter = 0;
			
			if(middleBuffer == null && firstBuffer != null) {
				
				middleBuffer = new int[F_AVG_MILISEC_LENGTH];
				middleAmplitudeBuffer = 0;	
				System.out.println("3.");
			}		
		}
		
		if(middleBuffer == null && lastBuffer == null && favgMilisecCounter < F_AVG_MILISEC_LENGTH) {
			
			firstBuffer[favgMilisecCounter] = sample;
			firstAmplitudeBuffer += Math.abs(sample);
			System.out.println("4.");
		}
		
		if(middleBuffer != null && lastBuffer == null && favgMilisecCounter < F_AVG_MILISEC_LENGTH) {
			
			middleBuffer[favgMilisecCounter] = sample;
			middleAmplitudeBuffer += Math.abs(sample);
			System.out.println("5.");
		}
		
		favgMilisecCounter++;
		
		if(middleBuffer != null && favgMilisecCounter == F_AVG_MILISEC_LENGTH) {
			lastBuffer = new int[F_AVG_MILISEC_LENGTH];
			System.out.println("firstAmplitudeBuffer "+firstAmplitudeBuffer + ", firstBuffer Array "+Arrays.toString(firstBuffer));
			System.out.println("middleAmplitudeBuffer "+middleAmplitudeBuffer + ", middleBuffer Array "+Arrays.toString(middleBuffer));
		}	
	}
}
