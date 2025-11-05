package audio;

import java.util.Arrays;

class Sequence {
	
	public Sequence(int start, int avgHeight) {
		this.start = start;
		this.avgHeight = avgHeight;
	}

	int start;
	int startHeight;
	int end;
	int avgHeight;
	int slope;
	int length;
	
	@Override
	public String toString() {
		return "Sequence [start=" + start + ", end=" + end + ", avgHeight=" + avgHeight + ", slope=" + slope + "]";
	}	
}

public class Test2 {
	
	static int i;
	static Sequence sequence;
	static Sequence[] sequences = new Sequence[10];
	static int sequencesCounter;
	static double firstSlope;
	static double secondSlope;
	static double lastSlope;
	static int diffAngle = 40;
	static int posSlopeCounter;
	static int negSlopeCounter;
    static int minimumSequenceLength = 15;
    
	static int[] array1 = new int[] {17, 17, 18, 16, 14, 11, 9, 10, 11, 13, 12, 12, 13, 12, 11, 8, 10, 10, 13, 13, 19, 33, 47, 61, 68, 75, 72, 77, 80, 94, 105, 113, 116, 124, 130, 143, 137, 133, 116, 106, 102, 106, 107, 107, 100, 97, 94, 91, 91, 88, 91, 94, 100, 105, 103, 97, 79, 71, 63, 66, 60, 57, 62, 64, 70, 69, 75, 84, 94, 102, 95, 82, 61, 48, 37, 29, 23, 20, 17, 15, 15, 15, 15, 13, 11, 9, 8};
	static int[] array2 = new int [] {3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 5, 5, 4, 4, 6, 9, 13, 20, 26, 32, 36, 39, 40, 42, 44, 47, 50, 54, 56, 58, 58, 57, 57, 59, 63, 68, 73, 77, 79, 81, 85, 88, 92, 94, 94, 94, 91, 90, 89, 89, 87, 85, 84, 81, 81, 78, 77, 72, 68, 63, 56, 52, 48, 45, 43, 41, 40, 40, 37, 34, 30, 25, 19, 14, 11, 8, 7, 6, 5, 5, 4};
	public static void main(String[] args) {
		
//		firstSlope = Math.toDegrees(Math.atan2(array1[25+1]-array1[25], 1));
//		secondSlope = Math.toDegrees(Math.atan2(array1[25+2]-array1[25], 2));
//		lastSlope = Math.toDegrees(Math.atan2(array1[25+3]-array1[25], 3));
//		
//		System.out.println(" firstSlope "+firstSlope);
//		System.out.println(" secondSlope "+secondSlope);
//		System.out.println(" lastSlope "+lastSlope);
//		
//		System.out.println(Math.toDegrees(Math.atan2(-3, 1)));
//		System.out.println(Math.toDegrees(Math.atan2(2, 2)));
//		System.out.println(Math.toDegrees(Math.atan2(5, 3)));


		build(array2); 

	}
	
	private static void build(int[] input) {
		
		sequence = new Sequence(0,input[0]);
		
		for(i = 0; i < input.length-3; i=i+1) {
			
			firstSlope = Math.toDegrees(Math.atan2(input[i+1]-input[i], 1));
			secondSlope = Math.toDegrees(Math.atan2(input[i+2]-input[i], 2));
			lastSlope = Math.toDegrees(Math.atan2(input[i+3]-input[i], 3));
			
			sequence.avgHeight+=input[0];
			sequence.slope+=(firstSlope + secondSlope + lastSlope)/3;

			if(lastSlope > 0) 				
				posSlopeCounter++;
			
			if(lastSlope < 0) 
				negSlopeCounter++;
			//System.out.println("         1.0 i: "+i + ", input[i]: "+input[i]  +", firstSlope: "+firstSlope +", secondSlope: "+secondSlope +", lastSlope: "+ lastSlope);
			
			if(lastSlope > 0 &&  ((lastSlope > firstSlope + diffAngle) || (lastSlope > secondSlope +diffAngle))) {
							
//				System.out.println("    2.0 i: "+i + ", input[i]: "+input[i]  +", firstSlope: "+firstSlope 
//						+", secondSlope: "+secondSlope +", lastSlope: "+ lastSlope + ", posSlopeCounter: "
//						+ posSlopeCounter+", negSlopeCounter: "+negSlopeCounter);
				
				if((posSlopeCounter > 5 && negSlopeCounter == 0) || posSlopeCounter / negSlopeCounter > 5) {
						
					newSequence(input); 
				}
				
				resetSlopeCounter();
			}
			
			if( lastSlope < 0 &&   ((lastSlope + diffAngle < firstSlope ) || (lastSlope + diffAngle < secondSlope))) {
				
				
//				System.out.println("         3.0 i: "+i + ", input[i]: "+input[i]  +", firstSlope: "+firstSlope 
//						+", secondSlope: "+secondSlope +", lastSlope: "+ lastSlope + ", posSlopeCounter: "
//						+ posSlopeCounter+", negSlopeCounter: "+negSlopeCounter);
				
				if((negSlopeCounter > 5 && posSlopeCounter == 0) || negSlopeCounter / posSlopeCounter > 5) {

					newSequence(input); 
				}
				
				resetSlopeCounter();
			}
		}

		if(i - sequence.start > minimumSequenceLength )
			newSequence(input);
	}
	
	static void newSequence(int[] input) {
		
		if(i - sequence.start < minimumSequenceLength )
			return;
		
		sequence.end = i-1 ;
		sequences[sequencesCounter++] = sequence;
		System.out.println("4.0 i: "+i + ", input[i]: "+input[i]+ ", " +sequence.toString());
		sequence = new Sequence(i,input[i]);
	}
	
	static void resetSlopeCounter() {
		
		posSlopeCounter = 1;
		negSlopeCounter = 1;
	}
}
