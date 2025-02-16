package com;

public class NoiseFilter {

	private static int[] testarray = new int[] {1,2,3,4,5,6,7,8,9,6,-7,-8,-9,3,4,5,6,3,4,5,6,-7,-8,-9,-7,-8,-9,1,2,3,4,5,1,2,3,4,5,-7,-8,-9,
			1,2,3,4,5,6,7,8,-7,-8,-9,-7,-8,-9,3,2,1,-7,-8,-9,-7,-8,-9,1,2,-2,1,-1};
	
	public static void main(String[] args) {
		//System.out.println(Arrays.toString(noiseReduction(testarray)));

		for(int i = 0; i < 3000; i = i + 50) 
			System.out.println("i: "+ i +", "+getMappedFrequency100(i));
		
//		System.out.println(slopeCalculator(2000,3000));
//		
//		System.out.println(slopeCalculator(3000,2000));
//		
//		System.out.println(slopeCalculator(-3000,-2000));
//		
//		System.out.println(slopeCalculator(-2000,-3000));
		
//		System.out.println(Math.pow(10, 5));
		
	}
	
	
	public static int slopeCalculator(int x2 , int x1) {
		
		return x2+(x2 -x1);
	}

	 public static int getMappedFrequency100(int frequency) {
		 
		 if(frequency <= 100) 
			 
			 return  1;	 
		 
		 else if(frequency <= 900) 
			 
			 return (int) ((float)frequency/54*6-10) ;
			 
		  else 
			 
			 return (int) (Math.log(frequency)/ Math.log(1.14) +39); 		 
	 }
	
	public static int[] noiseReduction (int[] inputArray) {
		
		for (int i = 5; i < inputArray.length-10 ; i++) {
			
			if(inputArray[i-3] > 0 && inputArray[i-2] > 0 && inputArray[i-1] > 0  && inputArray[i+5] > 0  
				&& inputArray[i+6] > 0  && inputArray[i+7] > 0  && inputArray[i+8] > 0  && inputArray[i+9] > 0
				&& (inputArray[i] < 0 || inputArray[i+1] < 0 || inputArray[i+2] < 0 || inputArray[i+3] < 0 || inputArray[i+4] < 0)) {
				
				if(inputArray[i] < 0)
					inputArray[i] = inputArray[i] * -1;
				
				if(inputArray[i+1] < 0)
					inputArray[i+1] = inputArray[i+1] * -1;
				
				if(inputArray[i+2] < 0)
					inputArray[i+2] = inputArray[i+2] * -1;
				
				if(inputArray[i+3] < 0)
					inputArray[i+3] = inputArray[i+3] * -1;
				
				if(inputArray[i+4] < 0)
					inputArray[i+4] = inputArray[i+4] * -1;
			}
			
			if(inputArray[i-3] < 0 && inputArray[i-2] < 0 && inputArray[i-1] < 0  && inputArray[i+5] < 0  
					&& inputArray[i+6] < 0  && inputArray[i+7] < 0  && inputArray[i+8] < 0  && inputArray[i+9] < 0
					&& (inputArray[i] > 0 || inputArray[i+1] > 0 || inputArray[i+2] > 0 || inputArray[i+3] > 0 || inputArray[i+4] > 0)) {
					
					if(inputArray[i] > 0)
						inputArray[i] = inputArray[i] * -1;
					
					if(inputArray[i+1] > 0)
						inputArray[i+1] = inputArray[i+1] * -1;
					
					if(inputArray[i+2] > 0)
						inputArray[i+2] = inputArray[i+2] * -1;
					
					if(inputArray[i+3] > 0)
						inputArray[i+3] = inputArray[i+3] * -1;
					
					if(inputArray[i+4] > 0)
						inputArray[i+4] = inputArray[i+4] * -1;
				}
		}
		
		return inputArray;
	}
}
