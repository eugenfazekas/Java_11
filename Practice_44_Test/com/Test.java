package com;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioFormat;

class SequenceCheck {
	
	int id;
	int frequencyLowerLimit;
	int frequencyUpperLimit;
	int startIndex;
	int endIndex;
	int matchCount;
		
	public SequenceCheck(int id, int frequencyLowerLimit, int frequencyUpperLimit,
			int startIndex, int endIndex, int matchCount) {

		this.id = id;
		this.frequencyLowerLimit = frequencyLowerLimit;
		this.frequencyUpperLimit = frequencyUpperLimit;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.matchCount = matchCount;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFrequencyLowerLimit() {
		return frequencyLowerLimit;
	}
	public void setFrequencyLowerLimit(int frequencyLowerLimit) {
		this.frequencyLowerLimit = frequencyLowerLimit;
	}
	public int getFrequencyUpperLimit() {
		return frequencyUpperLimit;
	}
	public void setFrequencyUpperLimit(int frequencyUpperLimit) {
		this.frequencyUpperLimit = frequencyUpperLimit;
	}
	public int getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	public int getEndIndex() {
		return endIndex;
	}
	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}
	public int getMatchCount() {
		return matchCount;
	}
	public void setMatchCount(int matchCount) {
		this.matchCount = matchCount;
	}
	
}

public class Test {

	 public static int startIndex = -1;
	 public static int endIndex = -1;
	 public static int startSequenceLengthCheck = 20;
	 public static int endSequenceLengthCheck = 20;
	 public static int START_FREQUENCY_LIMIT = 300 ;
	 public static int END_FREQUENCY_LIMIT = 1000;
	 private static int maxDiffernce;
	 private static int positive = -1;
	 private static int negative = 0;
	 private static int lastSample = 0;
	 private static int []tempArrray = new int [1000];
	 private static int [] frequencyStart = new int [1000];
	 private static int tempArrrayCounter = 0;
	 private static int frequency = 0;
	 private static int totalCounter = 0;
	 private static int firstStart = 0;
	 private static int RECOGNITION_PERCENT_LIMIT = 88;
	 private static int inverseLimit = 100 - RECOGNITION_PERCENT_LIMIT;
	 private static int lowerPercentLimit = 100 - inverseLimit;
	 private static int upperPercentLimit = 100 + inverseLimit;
	 public static int[] testFrequencys = new int [] 
			 {
                2,4,5,-2,-1, 2,4,5,-2,-1, 2,4,5,-2,-1, 2,4,5,-2,-1, 2,4,5,-2,-1,
                2,4,5,-2,-1, 2,4,5,-2,-1, 2,4,5,-2,-1, 2,4,5,-2,-1, 2,4,5,-2,-1,
			 };
	 

	public static void main(String... args) {
		//buildSequenceLengthByfrequency(amplitudes);
		
	//	buildSequenceLengthByfrequency2();
		
//		byte [] byteArray = convertIntArrayToByteArray(testSamples);
//		int  msb = byteArray[2] & 0xFF; // Magasabb rendű (MSB) byte
//	    int  lsb = byteArray[3] & 0xFF;
//	    int zero  = (int)((msb << 8) | lsb & 0xFF);
//		System.out.println(zero);
		
		System.out.println(calculateMatchProcent(90,100));
	}
	
	public static int getFrequency(int cycleSamples) {
		
		int freq = 22050 / cycleSamples;
		return freq;
	}
	
	public static boolean calculateMatchProcent(int dB_Value ,int inputValue) {

		float matchPercent = ((float) dB_Value / inputValue)*100;
		System.out.println("matchPercent: "+matchPercent + ", lowerPercentLimit: "+lowerPercentLimit +", upperPercentLimit "+upperPercentLimit);
		if(matchPercent  > lowerPercentLimit && matchPercent < upperPercentLimit)
			return true;
			return false;
	}
	
	 public static void buildSequenceLengthByfrequency2(int[] inputSamples, SequenceCheck[] sequenceChecks, AudioFormat audioFormat) {
		 
		 int sequenceChecksCounter = 0;
		 
		 for(int i = 0; i < inputSamples.length; i++) {
			 
				if(inputSamples[i]  > 0 ) { 
					positive++;
				}
				
				if(inputSamples[i]  <= 0) {
					negative++;				
				}
				
				if(inputSamples[i]  > 0 && lastSample <0 ) {
					frequency = getFrequency(positive+negative);
					frequencyStart[tempArrrayCounter] = i - (int) (audioFormat.getSampleRate()/frequency);
					System.out.println("New Frequency: "+frequency);
					positive = 0;
					negative = 0;						
				}	
			 
			 if(frequency != 0 && frequency > sequenceChecks[sequenceChecksCounter].getFrequencyLowerLimit() &&
			    frequency < sequenceChecks[sequenceChecksCounter].getFrequencyUpperLimit()) {
				        totalCounter++;
				        tempArrray[tempArrrayCounter++] = frequency;
			 }
			 
			 if(frequency != 0 && frequency < sequenceChecks[sequenceChecksCounter].getFrequencyLowerLimit() ||
			    frequency > sequenceChecks[sequenceChecksCounter].getFrequencyUpperLimit()) {
				 	if (totalCounter > 1)
						 totalCounter--;
					     tempArrray[tempArrrayCounter++] = inputSamples[i];
					 }
			 if(frequency != 0 && totalCounter == sequenceChecks[sequenceChecksCounter].getMatchCount()) {
				 sequenceChecks[sequenceChecksCounter].setEndIndex(i);//= sequenceCheck(sequenceChecks[sequenceChecksCounter]);
				 sequenceChecks[sequenceChecksCounter++].setStartIndex(frequencyStart[tempArrrayCounter - sequenceCheck(sequenceChecks[sequenceChecksCounter])]);
				 System.out.println("StartIndex Id: "+sequenceChecks[sequenceChecksCounter].getId() + " Start Index: "+sequenceChecks[sequenceChecksCounter].getStartIndex()
						 + " EndIndex: " + sequenceChecks[sequenceChecksCounter].getEndIndex());
				 frequencyStart = new int [1000];
				 tempArrray = new int [1000];
				 tempArrrayCounter=0;

			 }
			 
			 frequency = 0;
			 lastSample = inputSamples[i];
		 }
		 
	 }
	 private static int sequenceCheck(SequenceCheck sequenceCheck) {
		 
		 int negativeCounter = 0;
		 int positiveCounter = 0;

		for(int i = tempArrrayCounter; i >= 0; i-- ) {
			
			 if(tempArrray[i] > sequenceCheck.getFrequencyLowerLimit() &&
					 tempArrray[i] < sequenceCheck.getFrequencyUpperLimit()) {
				 positiveCounter++;
					 }
					 
			 if(tempArrray[i] < sequenceCheck.getFrequencyLowerLimit() ||
					 tempArrray[i] > sequenceCheck.getFrequencyUpperLimit()) {
				 negativeCounter++;
			}
			 
			 if(positiveCounter == sequenceCheck.getMatchCount()) {
				 System.out.println("sequenceCheck length: "+(positiveCounter + negativeCounter));
				 return positiveCounter + negativeCounter;
			 }
		}
			return -1;
	}

		
		public static byte[] convertIntArrayToByteArray(int[] intStream) {
			
			byte[] byte_stram = new byte[intStream.length * 2];
			int counter = 0;
			
			for(int i = 0; i < intStream.length; i++) {  
				//Debug.debug("SoundBuilder convertIntArrayToByteArray full_Sequence[i] "+full_Sequence[i],2);
				byte_stram[counter++] = (byte) (intStream[i] >> 8);
				byte_stram[counter++] = (byte) intStream[i] ;
			}
			 System.out.println("SoundBuilder convertIntArrayToByteArray byte_Sequence.length "+byte_stram.length);
			return byte_stram;
		}
		
		static int[] amplitudes = new int[] {4499, 2468, 1152, 1095, 1674, 1953, 1167, 532, 314, 577, 496, 659, 792, 525, -398, -1242, -2067, -2314, -1638, -860, -657, -1112, -1862, -2573, -2481, -2090, -1734, -1872, -2458, -2862, -2425, -1559, -690, -670, -1197, -1630, -2079, -1704, -597,
				337, 756, 590, 29, 16, 633, 1156, 1143, 837, 670, 736, 1160, 1877, 1844, 1615, 1288, 703, 271, 337, 687, 932, 1085, 480, -191, -492, -715, -927, -907, -1025, -1622, -1911, -2013, -2030, -2184, -2613, -3307, -3144, -2412, -3011, -4308, -4117, -2457, -1955, -2953, -3476, -3358, -3084, -2572, -2840,
				 -2769, -2215, -1847, -1926, -1836, -1520, -1223, -950, -703, -916, -1409, -646,
				1026, 1251, -88, -340,543, 1622, 1746, 772, -398,2149, 4168, 6896, 7864, 5363, 1363, -1454, -510,
				420, 2152, 2398, 1138, 549, 1270, 1859, 1448, 488, 864, 1488, 1198, 704, 1108, 1921, 1891, 871, 740, 1460, 2185, 2116, 2175, 2928, 3054, 2269, 2257, 4173, 5360, 4355, 2198, 1181, 1309, 
				2795, 4611, 4867, 4107, 2732, 1302, 933, 1161, 965, 508, -390, -912, -509,
				587, 1107, 333, -1536, -3413, -4658, -4342, -2878, -1914, -1680, -2153, -3029, -3904, -3851, -3594, -3378, -3532, -4221, -4534, -3816, -2329, -1538, -1849, -2436, -2804, -2807, -2270, -1223,
				753, 4119, 5432, 3703, 1775, 608, 283, 892, 405, -1475, -3262, -2780, -1751, -235,
				710, 280, -1998, -4461, -6396, -6582, -5086, -3004, -2138, -2611, -3419, -4862, -5112, -4587, -4191, -4873, -5437, -5361, -4629, -3335, -2295, -1724, -1786, -2799, -3688, -3487, -1810,
				129, 1058, 1024, 693, 1233, 2003, 2542, 2595, 2262, 1507, 1051, 1342, 4090, 6638, 7460, 5293, 1233, -752, -232,
				2196, 3829, 4716, 3392, 1065, -30, -743, -1320, -1350, -1844, -2668, -3385, -3167, -3226, -2724, -2733, -4787, -7170, -8226, -7023, -5348, -3844, -4054, -5396, -6706, -6943, -6411, -5165, -4460, -4236, -4559, -4265, -4056, -3367, -2405, -1453, -1043, -1679, -1831, -1138,
				5213, 11702, 13966, 8075, -502, -5926, -6682, -1888,
				5213, 11702, 13966, 8075, -502, -5926, -6682, -1888,
				5213, 11702, 13966, 8075, -502, -5926, -6682, -1888,
				433, 7126, 5153, 1951, -699, -1991, -1313, -735, -2226, -5027, -6197, -4847, -1141,
				433, 7126, 5153, 1951, -699, -1991, -1313, -735, -2226, -5027, -6197, -4847, -1141,
				433, 7126, 5153, 1951, -699, -1991, -1313, -735, -2226, -5027, -6197, -4847, -1141
				};

}
