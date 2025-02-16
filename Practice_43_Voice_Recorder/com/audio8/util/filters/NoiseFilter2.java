package com.audio8.util.filters;

import java.util.Arrays;

import com.audio8.util.Debug;

public class NoiseFilter2 {
		
	public static int i;
	public static int filter1;
	public static int filter11;
	public static int filter2;
	public static int filter22;
	public static int filter3;
	public static int filter33;
	public static int[] filtered;
	public static int filteredCounter;
	
	public static void noiseReduction (int i, int[] inputArray) {
		
		 filter1 = 0;
		 filter11 = 0;
		 filter2 = 0;
		 filter22 = 0;
		 filter3 = 0;
		 filter33 = 0;
		 filtered = new int[1000];
		 filteredCounter = 0;
											
						if(inputArray[i-9] > 0 && inputArray[i-8] > 0 && inputArray[i-7] > 0  
							&& inputArray[i-3] > 0 && inputArray[i-2] > 0 && inputArray[i-1] > 0 
							&& (inputArray[i-6] < 0 && inputArray[i-5] < 0 && inputArray[i-4] < 0)) {
							
							if(inputArray[i-6] < 0) {
								
								filtered[filteredCounter++] = inputArray[i-6];
								inputArray[i-6] = 1;
							}
							
							if(inputArray[i-5] < 0) {
								
								filtered[filteredCounter++] = inputArray[i-5];
								inputArray[i-5] = 1;
							}
							
							if(inputArray[i-4] < 0) {
								
								filtered[filteredCounter++] = inputArray[i-4];
								inputArray[i-4] = 1;
							}
							
							filter1++;
						}
						
						if(inputArray[i-9] < 0 && inputArray[i-8] < 0 && inputArray[i-7] < 0  
							&& inputArray[i-3] < 0  && inputArray[i-2] < 0  && inputArray[i-1] < 0 
								&& (inputArray[i-6] > 0 && inputArray[i-5] > 0 && inputArray[i-4] > 0)) {
								
								if(inputArray[i-6] > 0) {
									
									filtered[filteredCounter++] = inputArray[i-6];
									inputArray[i-6] = -1;
								}
								
								if(inputArray[i-5] > 0) {
									
									filtered[filteredCounter++] = inputArray[i-5] ;
									inputArray[i-5] =  -1;
								}
								
								if(inputArray[i-4] > 0) {
									
									filtered[filteredCounter++] = inputArray[i-4];
									inputArray[i-4] = -1;
								}
								
								filter11++;
							}
						
						if(inputArray[i-9] > 0 && inputArray[i-8] > 0 && inputArray[i-7] > 0  
								&& inputArray[i-4] > 0 && inputArray[i-3] > 0 && inputArray[i-2] > 0  
								&& (inputArray[i-6] < 0 && inputArray[i-5] < 0 )) {
								
								if(inputArray[i-6] < 0) {
									
									filtered[filteredCounter++] = inputArray[i-6];
									inputArray[i-6] = 1;
								}
								
								if(inputArray[i-5] < 0) {
									
									filtered[filteredCounter++] = inputArray[i-5];
									inputArray[i-5] = 1;
								}
								
								filter2++;
						}
							
						if(inputArray[i-9] < 0 && inputArray[i-8] < 0 && inputArray[i-7] < 0  
								&& inputArray[i-4] < 0 && inputArray[i-3] < 0 && inputArray[i-2] < 0 
									&& (inputArray[i-6] > 0 && inputArray[i-5] > 0)) {
									
									if(inputArray[i-6] > 0) {
										
										filtered[filteredCounter++] = inputArray[i-6];
										inputArray[i-6] = -1;

									}							
									if(inputArray[i-5] > 0) {
										
										filtered[filteredCounter++] = inputArray[i-5];
										inputArray[i-5] = -1;
									}		
									
									filter22++;
						}
						
						if(inputArray[i-9] > 0 && inputArray[i-8] > 0 && inputArray[i-7] > 0  
								&& inputArray[i-5] > 0 && inputArray[i-4] > 0  && inputArray[i-3] > 0  
									&& inputArray[i-6] < 0) {
								
								if(inputArray[i-6] < 0) {
									
									filtered[filteredCounter++] = inputArray[i-6];
									inputArray[i-6] = 1;
								}
								
								filter3++;
						}
							
						if(inputArray[i-9] < 0 && inputArray[i-8] < 0 && inputArray[i-7] < 0  
								&& inputArray[i-5] < 0 && inputArray[i-4] < 0  && inputArray[i-3] < 0  
									&& inputArray[i-6] > 0) {
									
									if(inputArray[i-6] > 0) {
										
										filtered[filteredCounter++] = inputArray[i-6];
										inputArray[i-6] =  -1;
									}
									
									filter33++;
					}
				
					
					Debug.debug(3, "noiseReduction filter1: "+ filter1 + ", filter11: "+filter11+ ", filter2: "+filter2
							+ ", filter22: "+filter22+ ", filter3: "+filter3+ ", filter33: "+filter33);
					
					Debug.debug(5, "noiseReduction Array: "+ Arrays.toString(filtered));
					
	}
	
	public static int[] noiseReduction (int[] inputArray) {
		
		 filter1 = 0;
		 filter11 = 0;
		 filter2 = 0;
		 filter22 = 0;
		 filter3 = 0;
		 filter33 = 0;
		 filtered = new int[1000];
		 filteredCounter = 0;
					
					for (int i = 3; i < inputArray.length-5 ; i++) {
						
						if(inputArray[i-3] > 0 && inputArray[i-2] > 0 && inputArray[i-1] > 0  
							&& inputArray[i+3] > 0 && inputArray[i+4] > 0 && inputArray[i+5] > 0 
							&& (inputArray[i] < 0 && inputArray[i+1] < 0 && inputArray[i+2] < 0)) {
							
							if(inputArray[i] < 0) {
								
								filtered[filteredCounter++] = inputArray[i];
								inputArray[i] = 1;
							}
							
							if(inputArray[i+1] < 0) {
								
								filtered[filteredCounter++] = inputArray[i+1];
								inputArray[i+1] = 1;
							}
							
							if(inputArray[i+2] < 0) {
								
								filtered[filteredCounter++] = inputArray[i+2];
								inputArray[i+2] = 1;
							}
							
							filter1++;
						}
						
						if(inputArray[i-3] < 0 && inputArray[i-2] < 0 && inputArray[i-1] < 0  
							&& inputArray[i+3] < 0  && inputArray[i+4] < 0  && inputArray[i+5] < 0 
								&& (inputArray[i] > 0 && inputArray[i+1] > 0 && inputArray[i+2] > 0)) {
								
								if(inputArray[i] > 0) {
									
									filtered[filteredCounter++] = inputArray[i];
									inputArray[i] = -1;
								}
								
								if(inputArray[i+1] > 0) {
									
									filtered[filteredCounter++] = inputArray[i+1] ;
									inputArray[i+1] =  -1;
								}
								
								if(inputArray[i+2] > 0) {
									
									filtered[filteredCounter++] = inputArray[i+2];
									inputArray[i+2] = -1;
								}
								
								filter11++;
							}
						
						if(inputArray[i-3] > 0 && inputArray[i-2] > 0 && inputArray[i-1] > 0  
								&& inputArray[i+2] > 0 && inputArray[i+3] > 0 && inputArray[i+4] > 0  
								&& (inputArray[i] < 0 && inputArray[i+1] < 0 )) {
								
								if(inputArray[i] < 0) {
									
									filtered[filteredCounter++] = inputArray[i];
									inputArray[i] = 1;
								}
								
								if(inputArray[i+1] < 0) {
									
									filtered[filteredCounter++] = inputArray[i+1];
									inputArray[i+1] = 1;
								}
								
								filter2++;
						}
							
						if(inputArray[i-3] < 0 && inputArray[i-2] < 0 && inputArray[i-1] < 0  
								&& inputArray[i+2] < 0 && inputArray[i+3] < 0 && inputArray[i+4] < 0 
									&& (inputArray[i] > 0 && inputArray[i+1] > 0)) {
									
									if(inputArray[i] > 0) {
										
										filtered[filteredCounter++] = inputArray[i];
										inputArray[i] = -1;

									}							
									if(inputArray[i+1] > 0) {
										
										filtered[filteredCounter++] = inputArray[i+1];
										inputArray[i+1] = -1;
									}		
									
									filter22++;
						}
						
						if(inputArray[i-3] > 0 && inputArray[i-2] > 0 && inputArray[i-1] > 0  
								&& inputArray[i+1] > 0 && inputArray[i+2] > 0  && inputArray[i+3] > 0  
									&& inputArray[i] < 0) {
								
								if(inputArray[i] < 0) {
									
									filtered[filteredCounter++] = inputArray[i];
									inputArray[i] = 1;
								}
								
								filter3++;
						}
							
						if(inputArray[i-3] < 0 && inputArray[i-2] < 0 && inputArray[i-1] < 0  
								&& inputArray[i+1] < 0 && inputArray[i+2] < 0  && inputArray[i+3] < 0  
									&& inputArray[i] > 0) {
									
									if(inputArray[i] > 0) {
										
										filtered[filteredCounter++] = inputArray[i];
										inputArray[i] =  -1;
									}
									
									filter33++;
						}
					}
					
					Debug.debug(1, "noiseReduction filter1: "+ filter1 + ", filter11: "+filter11+ ", filter2: "+filter2
							+ ", filter22: "+filter22+ ", filter3: "+filter3+ ", filter33: "+filter33);
					
					Debug.debug(1, "noiseReduction Array: "+ Arrays.toString(filtered));
					
				return inputArray;
	}
	
	public static int[] noiseReduction2 (int[] inputArray) {
		
		 filter1 = 0;
		 filter11 = 0;
		 filter2 = 0;
		 filter22 = 0;
		 filter3 = 0;
		 filter33 = 0;
		 filtered = new int[1000];
		 filteredCounter = 0;
					
					for (int i = 3; i < inputArray.length-3 ; i++) {
						
						if(inputArray[i-3] > 0 && inputArray[i-2] > 0 && inputArray[i-1] > 0  
							&& inputArray[i+3] > 0  
							&& (inputArray[i] < 0 && inputArray[i+1] < 0 && inputArray[i+2] < 0)) {
							
							if(inputArray[i] < 0) {
								
								filtered[filteredCounter++] = inputArray[i];
								inputArray[i] = slopeCalculator(inputArray[i+3], inputArray[i-3]);
							}
							
							if(inputArray[i+1] < 0) {
								
								filtered[filteredCounter++] = inputArray[i+1];
								inputArray[i+1] = slopeCalculator(inputArray[i+3], inputArray[i-3]);
							}
							
							if(inputArray[i+2] < 0) {
								
								filtered[filteredCounter++] = inputArray[i+2];
								inputArray[i+2] = slopeCalculator(inputArray[i+3], inputArray[i-3]);
							}
							
							filter1++;
						}
						
						if(inputArray[i-3] < 0 && inputArray[i-2] < 0 && inputArray[i-1] < 0  
							&& inputArray[i+3] < 0  
								&& (inputArray[i] > 0 && inputArray[i+1] > 0 && inputArray[i+2] > 0)) {
								
								if(inputArray[i] > 0) {
									
									filtered[filteredCounter++] = inputArray[i];
									inputArray[i] = slopeCalculator(inputArray[i+3], inputArray[i-3]);
								}
								
								if(inputArray[i+1] > 0) {
									
									filtered[filteredCounter++] = inputArray[i+1] ;
									inputArray[i+1] =  slopeCalculator(inputArray[i+3], inputArray[i-3]);
								}
								
								if(inputArray[i+2] > 0) {
									
									filtered[filteredCounter++] = inputArray[i+2];
									inputArray[i+2] = slopeCalculator(inputArray[i+3], inputArray[i-3]);
								}
								
								filter11++;
							}
						
						if(inputArray[i-3] > 0 && inputArray[i-2] > 0 && inputArray[i-1] > 0  
								&& inputArray[i+2] > 0  
								&& (inputArray[i] < 0 && inputArray[i+1] < 0 )) {
								
								if(inputArray[i] < 0) {
									
									filtered[filteredCounter++] = inputArray[i];
									inputArray[i] = slopeCalculator(inputArray[i+2], inputArray[i-3]);
								}
								
								if(inputArray[i+1] < 0) {
									
									filtered[filteredCounter++] = inputArray[i+1];
									inputArray[i+1] = slopeCalculator(inputArray[i+2], inputArray[i-3]);
								}
								
								filter2++;
						}
							
						if(inputArray[i-3] < 0 && inputArray[i-2] < 0 && inputArray[i-1] < 0  
								&& inputArray[i+2] < 0  
									&& (inputArray[i] > 0 && inputArray[i+1] > 0)) {
									
									if(inputArray[i] > 0) {
										
										filtered[filteredCounter++] = inputArray[i];
										inputArray[i] = slopeCalculator(inputArray[i+2], inputArray[i-3]);

									}							
									if(inputArray[i+1] > 0) {
										
										filtered[filteredCounter++] = inputArray[i+1];
										inputArray[i+1] = slopeCalculator(inputArray[i+2], inputArray[i-3]);
									}		
									
									filter22++;
						}
						
						if(inputArray[i-3] > 0 && inputArray[i-2] > 0 && inputArray[i-1] > 0  
								&& inputArray[i+1] > 0  
									&& inputArray[i] < 0) {
								
								if(inputArray[i] < 0) {
									
									filtered[filteredCounter++] = inputArray[i];
									inputArray[i] = slopeCalculator(inputArray[i+1], inputArray[i-3]);
								}
								
								filter3++;
						}
							
						if(inputArray[i-3] < 0 && inputArray[i-2] < 0 && inputArray[i-1] < 0  
								&& inputArray[i+1] < 0 
									&& inputArray[i] > 0) {
									
									if(inputArray[i] > 0) {
										
										filtered[filteredCounter++] = inputArray[i];
										inputArray[i] =  slopeCalculator(inputArray[i+1], inputArray[i-3]);
									}
									
									filter33++;
						}
					}
					
					Debug.debug(1, "noiseReduction filter1: "+ filter1 + ", filter11: "+filter11+ ", filter2: "+filter2
							+ ", filter22: "+filter22+ ", filter3: "+filter3+ ", filter33: "+filter33);
					
					Debug.debug(1, "noiseReduction Array: "+ Arrays.toString(filtered));
					
				return inputArray;
	}
	
	public static int slopeCalculator(int x2 , int x1) {
		
		System.out.println("x2: "+x2 +", x1: "+x1 + ", slope ; "+ ((x2+x1)/2)   );
		return  ((x2+x1)/2);
	}
	
	public static int[] noiseReduction3 (int[] inputArray) {
		
	 filter1 = 0;
	 filter11 = 0;
	 filter2 = 0;
	 filter22 = 0;
	 filter3 = 0;
	 filter33 = 0;
	 filtered = new int[1000];
	 filteredCounter = 0;
				
				for (int i = 5; i < inputArray.length-10 ; i++) {
					
					if(inputArray[i-3] > 0 && inputArray[i-2] > 0 && inputArray[i-1] > 0  
						&& inputArray[i+3] > 0  
						&& (inputArray[i] < 0 && inputArray[i+1] < 0 && inputArray[i+2] < 0)) {
						
						if(inputArray[i] < 0) {
							
							inputArray[i] = inputArray[i] * -1;
							filtered[filteredCounter++] = inputArray[i] ;
						}
						if(inputArray[i+1] < 0) {
							
							inputArray[i+1] = inputArray[i+1] * -1;
							filtered[filteredCounter++] = inputArray[i+1] ;
						}
						if(inputArray[i+2] < 0) {
							
							inputArray[i+2] = inputArray[i+2] * -1;
							filtered[filteredCounter++] = inputArray[i+2];
						}
						
						filter1++;
					}
					
					if(inputArray[i-3] < 0 && inputArray[i-2] < 0 && inputArray[i-1] < 0  
						&& inputArray[i+3] < 0  
							&& (inputArray[i] > 0 && inputArray[i+1] > 0 && inputArray[i+2] > 0)) {
							
							if(inputArray[i] > 0) {
								
								inputArray[i] = inputArray[i] * -1;
								filtered[filteredCounter++] = inputArray[i] ;
							}
							if(inputArray[i+1] > 0) {
								
								inputArray[i+1] = inputArray[i+1] * -1;
								filtered[filteredCounter++] = inputArray[i+1] ;
							}
							if(inputArray[i+2] > 0) {
								
								inputArray[i+2] = inputArray[i+2] * -1;
								filtered[filteredCounter++] = inputArray[i+2] ;
							}
							
							filter11++;
						}
					
					if(inputArray[i-3] > 0 && inputArray[i-2] > 0 && inputArray[i-1] > 0  
							&& inputArray[i+2] > 0  
							&& (inputArray[i] < 0 && inputArray[i+1] < 0 )) {
							
							if(inputArray[i] < 0) {
								
								inputArray[i] = inputArray[i] * -1;
								filtered[filteredCounter++] = inputArray[i] ;
							}
							if(inputArray[i+1] < 0) {
								
								inputArray[i+1] = inputArray[i+1] * -1;
								filtered[filteredCounter++] = inputArray[i+1] ;
							}
							
							filter2++;
					}
						
					if(inputArray[i-3] < 0 && inputArray[i-2] < 0 && inputArray[i-1] < 0  
							&& inputArray[i+2] < 0  
								&& (inputArray[i] > 0 && inputArray[i+1] > 0)) {
								
								if(inputArray[i] > 0) {
									
									inputArray[i] = inputArray[i] * -1;
									filtered[filteredCounter++] = inputArray[i] ;
								}							
								if(inputArray[i+1] > 0) {
									
									inputArray[i+1] = inputArray[i+1] * -1;
									filtered[filteredCounter++] = inputArray[i+1] ;
								}		
								
								filter22++;
					}
					
					if(inputArray[i-3] > 0 && inputArray[i-2] > 0 && inputArray[i-1] > 0  
							&& inputArray[i+1] > 0  
								&& inputArray[i] < 0) {
							
							if(inputArray[i] < 0) {
								
								inputArray[i] = inputArray[i] * -1;
								filtered[filteredCounter++] = inputArray[i] ;
							}
							filter3++;
					}
						
					if(inputArray[i-3] < 0 && inputArray[i-2] < 0 && inputArray[i-1] < 0  
							&& inputArray[i+1] < 0 
								&& inputArray[i] > 0) {
								
								if(inputArray[i] > 0) {
									
									inputArray[i] = inputArray[i] * -1;
									filtered[filteredCounter++] = inputArray[i] ;
								}
								filter33++;
					}
				}
				
				Debug.debug(1, "noiseReduction filter1: "+ filter1 + ", filter11: "+filter11+ ", filter2: "+filter2
						+ ", filter22: "+filter22+ ", filter3: "+filter3+ ", filter33: "+filter33);
				
				Debug.debug(1, "noiseReduction Array: "+ Arrays.toString(filtered));
				
			return inputArray;
	}
	
	public static int[] noiseReduction4 (int[] inputArray) {
			
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
