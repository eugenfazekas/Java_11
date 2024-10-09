package com;

public class PrebuildSequence {

	public static void main(String[] args) {
		buildSequenceLength(testArray);
//		System.out.println(checkIfReachProcentReach(100,60,61));
//		System.out.println(checkIfReachProcentReach(100,61,61));
//		System.out.println(checkIfReachProcentReach(100,62,61));
	}
	
	
	public static boolean checkIfReachProcentReach(int totalValue, int countedValue, int procentRequired) {
		
		//System.out.println("totalValue: "+totalValue+", countedValue:"+countedValue+", procentRequired: "+procentRequired);
		
		int procent  = (int) (((float) countedValue / totalValue)*100) ;
		
		System.out.println(" procent: "+procent);
				
		return procent >= procentRequired;
	}
	
	
	 public static int VOLUME_LIMIT = 100;
	 public static int startIndex = -1;
	 public static int endIndex = -1;
	 public static boolean building;
	 public static int checkCounter;
	 public static int START_VALID_LIMIT = 8;
	 public static int END_VALID_LIMIT = 22;
	 public static int validCounter;
	 public static int lastIndex;
	 public static int firstCheckindex;
	 public static int sample;
	 public static int channles;
	 public static int limitCounter;
	 
	 static int [] testArray = new int[]{
			 35, 5, 37, 36, 49, 94, 62, 82, 75, 78, 
			 35, 5, 37, 36, 49, 94, 62, 82, 75, 78, 
			 35, 5, 37, 36, 49, 94, 62, 82, 75, 78, 
			 35, 5, 370, 36, 49, 94, 62, 82, 75, 78, 
			 35, 5, 37, 360, 490, 94, 62, 82, 75, 78, 
			 161, 142, 198, 212, 120, 170, 1 , 195, 164, 163,
			 1, 142, 198, 212, 178, 170, 190, 195, 164, 163, //validm
			 -173, -250, -222, -200, -237, -287, -319, -317, -311, -366,
			 -173, -250, -222, 200, -237, -287, -319, -317, -311, -366,
			 -2, -22, -14, 7, -44, -79, 52, -69, -18, -81,
			 35, 5, 37, 36, 49, 94, 62, 82, 75, 78,
			 120,50,50
			 };
	 
	 
	 public static void buildSequenceLength(int[] inputSamples) {

			 for(int i = 0; i < inputSamples.length; i++) {
				 
				 if(startIndex == -1 && Math.abs(inputSamples[i]) > VOLUME_LIMIT && lastIndex < VOLUME_LIMIT ) {
					 firstCheckindex = i;
					 checkCounter = 0;
					 System.out.println("Set Positive Start: "+firstCheckindex+ " inputSamples[i]: "+inputSamples[i]);
				 }
				 
				 if(startIndex == -1 && Math.abs(inputSamples[i]) > VOLUME_LIMIT )
					 checkCounter++;
				 
				 if(startIndex == -1 && START_VALID_LIMIT == checkCounter) {
					 System.out.println("Counter Reached firstCheckindex: "+firstCheckindex +" i: "+i + " checkCounter: "+checkCounter +" val: " + (i - checkCounter) );
					 if(checkIfReachProcentReach(START_VALID_LIMIT,checkCounter,100)) {
						  startIndex = firstCheckindex;
						  checkCounter=0;
						  System.out.println("Start index on i: " +firstCheckindex );
					  }
					  else {
						  checkCounter = 0;
					  }			 
				 }
				 
				 if(startIndex != -1 && endIndex == -1 && Math.abs(inputSamples[i]) < VOLUME_LIMIT  && checkCounter ==0) {
					 firstCheckindex = i;
					 System.out.println("Set Negative Start: "+firstCheckindex+ " inputSamples[i]: "+inputSamples[i]);
				 }
				 
				 if(startIndex != -1 && endIndex == -1  && Math.abs(inputSamples[i]) <  VOLUME_LIMIT ) {
					 checkCounter++;
				 }
					 				 
				 if(startIndex != -1 && endIndex == -1 && END_VALID_LIMIT == checkCounter) {
					 if(checkIfReachProcentReach(END_VALID_LIMIT,checkCounter,75)) {
						 endIndex = firstCheckindex;
						 System.out.println("End index founded on i: " +firstCheckindex );
						 }
				 }				 
				 lastIndex = inputSamples[i];
			 }
		 }
}
