package com.audio8.util.filters;

import com.audio3.recorder.refinary.StreamRefinaryAmplitudeMethods;

public class NoiseFilter {

	public static void noiseReductionFilter (int i) {
//		System.out.println("noiseReductionFilter i: "+i+", i-11: "+StreamRefinaryAmplitudeMethods.intBuffer[i-11]+", i-10: "+StreamRefinaryAmplitudeMethods.intBuffer[i-10]
//				+", i-9: "+StreamRefinaryAmplitudeMethods.intBuffer[i-9] +", i-8: "+StreamRefinaryAmplitudeMethods.intBuffer[i-8]+", i-7: "+StreamRefinaryAmplitudeMethods.intBuffer[i-7]
//						+", i-7: "+StreamRefinaryAmplitudeMethods.intBuffer[i-7]
//				+", i-6: "+StreamRefinaryAmplitudeMethods.intBuffer[i-6]+", i-5: "+StreamRefinaryAmplitudeMethods.intBuffer[i-5]+", i-4: "+StreamRefinaryAmplitudeMethods.intBuffer[i-4]
//						+", i-3: "+StreamRefinaryAmplitudeMethods.intBuffer[i-3]+", i-2: "+StreamRefinaryAmplitudeMethods.intBuffer[i-2]
//								+", i-1: "+StreamRefinaryAmplitudeMethods.intBuffer[i-1]);
						
		if(StreamRefinaryAmplitudeMethods.intBuffer[i-11] > 0 && StreamRefinaryAmplitudeMethods.intBuffer[i-10] > 0 && StreamRefinaryAmplitudeMethods.intBuffer[i-9] > 0  
			&& StreamRefinaryAmplitudeMethods.intBuffer[i-3] > 0 && StreamRefinaryAmplitudeMethods.intBuffer[i-2] > 0 && StreamRefinaryAmplitudeMethods.intBuffer[i-1] > 0 
			&& (StreamRefinaryAmplitudeMethods.intBuffer[i-8] < 0 && StreamRefinaryAmplitudeMethods.intBuffer[i-7] < 0 && StreamRefinaryAmplitudeMethods.intBuffer[i-6] < 0 
			&& StreamRefinaryAmplitudeMethods.intBuffer[i-5] < 0 && StreamRefinaryAmplitudeMethods.intBuffer[i-4] < 0 )) {
				
			if(StreamRefinaryAmplitudeMethods.intBuffer[i-8] < 0) {
				
				StreamRefinaryAmplitudeMethods.filtered[StreamRefinaryAmplitudeMethods.filteredCounter++] = StreamRefinaryAmplitudeMethods.intBuffer[i-8];
				StreamRefinaryAmplitudeMethods.intBuffer[i-8] = 1;
			}
			
			if(StreamRefinaryAmplitudeMethods.intBuffer[i-7] < 0) {
				
				StreamRefinaryAmplitudeMethods.filtered[StreamRefinaryAmplitudeMethods.filteredCounter++] = StreamRefinaryAmplitudeMethods.intBuffer[i-7];
				StreamRefinaryAmplitudeMethods.intBuffer[i-7] = 1;
			}
			
			if(StreamRefinaryAmplitudeMethods.intBuffer[i-6] < 0) {
				
				StreamRefinaryAmplitudeMethods.filtered[StreamRefinaryAmplitudeMethods.filteredCounter++] = StreamRefinaryAmplitudeMethods.intBuffer[i-6];
				StreamRefinaryAmplitudeMethods.intBuffer[i-6] = 1;
			}
			
			if(StreamRefinaryAmplitudeMethods.intBuffer[i-5] < 0) {
				
				StreamRefinaryAmplitudeMethods.filtered[StreamRefinaryAmplitudeMethods.filteredCounter++] = StreamRefinaryAmplitudeMethods.intBuffer[i-5];
				StreamRefinaryAmplitudeMethods.intBuffer[i-5] = 1;
			}
			
			if(StreamRefinaryAmplitudeMethods.intBuffer[i-4] < 0) {
				
				StreamRefinaryAmplitudeMethods.filtered[StreamRefinaryAmplitudeMethods.filteredCounter++] = StreamRefinaryAmplitudeMethods.intBuffer[i-4];
				StreamRefinaryAmplitudeMethods.intBuffer[i-4] = 1;
			}
			
			StreamRefinaryAmplitudeMethods.filter1++;			
		}
			
		if(StreamRefinaryAmplitudeMethods.intBuffer[i-11] < 0 && StreamRefinaryAmplitudeMethods.intBuffer[i-10] < 0 && StreamRefinaryAmplitudeMethods.intBuffer[i-9] < 0  
			&& StreamRefinaryAmplitudeMethods.intBuffer[i-3] < 0  && StreamRefinaryAmplitudeMethods.intBuffer[i-2] < 0  && StreamRefinaryAmplitudeMethods.intBuffer[i-1] < 0 
			&& (StreamRefinaryAmplitudeMethods.intBuffer[i-8] > 0 && StreamRefinaryAmplitudeMethods.intBuffer[i-7] > 0 && StreamRefinaryAmplitudeMethods.intBuffer[i-6] > 0 
			&& StreamRefinaryAmplitudeMethods.intBuffer[i-5] > 0 && StreamRefinaryAmplitudeMethods.intBuffer[i-4] > 0 )) {
				
			if(StreamRefinaryAmplitudeMethods.intBuffer[i-8] > 0) {
				
				StreamRefinaryAmplitudeMethods.filtered[StreamRefinaryAmplitudeMethods.filteredCounter++] = StreamRefinaryAmplitudeMethods.intBuffer[i-8];
				StreamRefinaryAmplitudeMethods.intBuffer[i-8] = -1;
			}
			
			if(StreamRefinaryAmplitudeMethods.intBuffer[i-7] > 0) {
				
				StreamRefinaryAmplitudeMethods.filtered[StreamRefinaryAmplitudeMethods.filteredCounter++] = StreamRefinaryAmplitudeMethods.intBuffer[i-7];
				StreamRefinaryAmplitudeMethods.intBuffer[i-7] = -1;
			}
			
			if(StreamRefinaryAmplitudeMethods.intBuffer[i-6] > 0) {
				
				StreamRefinaryAmplitudeMethods.filtered[StreamRefinaryAmplitudeMethods.filteredCounter++] = StreamRefinaryAmplitudeMethods.intBuffer[i-6];
				StreamRefinaryAmplitudeMethods.intBuffer[i-6] = -1;
			}
			
			if(StreamRefinaryAmplitudeMethods.intBuffer[i-5] > 0) {
				
				StreamRefinaryAmplitudeMethods.filtered[StreamRefinaryAmplitudeMethods.filteredCounter++] = StreamRefinaryAmplitudeMethods.intBuffer[i-5] ;
				StreamRefinaryAmplitudeMethods.intBuffer[i-5] =  -1;
			}
			
			if(StreamRefinaryAmplitudeMethods.intBuffer[i-4] > 0) {
				
				StreamRefinaryAmplitudeMethods.filtered[StreamRefinaryAmplitudeMethods.filteredCounter++] = StreamRefinaryAmplitudeMethods.intBuffer[i-4];
				StreamRefinaryAmplitudeMethods.intBuffer[i-4] = -1;
			}
			
			StreamRefinaryAmplitudeMethods.filter11++;
		}
			
			
		if(StreamRefinaryAmplitudeMethods.intBuffer[i-10] > 0 && StreamRefinaryAmplitudeMethods.intBuffer[i-9] > 0 && StreamRefinaryAmplitudeMethods.intBuffer[i-8] > 0  
			&& StreamRefinaryAmplitudeMethods.intBuffer[i-3] > 0 && StreamRefinaryAmplitudeMethods.intBuffer[i-2] > 0 && StreamRefinaryAmplitudeMethods.intBuffer[i-1] > 0 
			&& (StreamRefinaryAmplitudeMethods.intBuffer[i-7] < 0 && StreamRefinaryAmplitudeMethods.intBuffer[i-6] < 0 && StreamRefinaryAmplitudeMethods.intBuffer[i-5] < 0 
			&& StreamRefinaryAmplitudeMethods.intBuffer[i-4] < 0)) {
							
			if(StreamRefinaryAmplitudeMethods.intBuffer[i-7] < 0) {
				
				StreamRefinaryAmplitudeMethods.filtered[StreamRefinaryAmplitudeMethods.filteredCounter++] = StreamRefinaryAmplitudeMethods.intBuffer[i-7];
				StreamRefinaryAmplitudeMethods.intBuffer[i-7] = 1;
			}
									
			if(StreamRefinaryAmplitudeMethods.intBuffer[i-6] < 0) {
				
				StreamRefinaryAmplitudeMethods.filtered[StreamRefinaryAmplitudeMethods.filteredCounter++] = StreamRefinaryAmplitudeMethods.intBuffer[i-6];
				StreamRefinaryAmplitudeMethods.intBuffer[i-6] = 1;
			}
			
			if(StreamRefinaryAmplitudeMethods.intBuffer[i-5] < 0) {
				
				StreamRefinaryAmplitudeMethods.filtered[StreamRefinaryAmplitudeMethods.filteredCounter++] = StreamRefinaryAmplitudeMethods.intBuffer[i-5];
				StreamRefinaryAmplitudeMethods.intBuffer[i-5] = 1;
			}
			
			if(StreamRefinaryAmplitudeMethods.intBuffer[i-4] < 0) {
				
				StreamRefinaryAmplitudeMethods.filtered[StreamRefinaryAmplitudeMethods.filteredCounter++] = StreamRefinaryAmplitudeMethods.intBuffer[i-4];
				StreamRefinaryAmplitudeMethods.intBuffer[i-4] = 1;
			}
			
			StreamRefinaryAmplitudeMethods.filter2++;
		}
			
		if(StreamRefinaryAmplitudeMethods.intBuffer[i-10] < 0 && StreamRefinaryAmplitudeMethods.intBuffer[i-9] < 0 && StreamRefinaryAmplitudeMethods.intBuffer[i-8] < 0  
			&& StreamRefinaryAmplitudeMethods.intBuffer[i-3] < 0  && StreamRefinaryAmplitudeMethods.intBuffer[i-2] < 0  && StreamRefinaryAmplitudeMethods.intBuffer[i-1] < 0 
			&& (StreamRefinaryAmplitudeMethods.intBuffer[i-7] > 0 && StreamRefinaryAmplitudeMethods.intBuffer[i-6] > 0 && StreamRefinaryAmplitudeMethods.intBuffer[i-5] > 0 && StreamRefinaryAmplitudeMethods.intBuffer[i-4] > 0)) {
				
			if(StreamRefinaryAmplitudeMethods.intBuffer[i-7] > 0) {
				
				StreamRefinaryAmplitudeMethods.filtered[StreamRefinaryAmplitudeMethods.filteredCounter++] = StreamRefinaryAmplitudeMethods.intBuffer[i-7];
				StreamRefinaryAmplitudeMethods.intBuffer[i-7] = -1;
			}
			
			if(StreamRefinaryAmplitudeMethods.intBuffer[i-6] > 0) {
				
				StreamRefinaryAmplitudeMethods.filtered[StreamRefinaryAmplitudeMethods.filteredCounter++] = StreamRefinaryAmplitudeMethods.intBuffer[i-6];
				StreamRefinaryAmplitudeMethods.intBuffer[i-6] = -1;
			}
			
			if(StreamRefinaryAmplitudeMethods.intBuffer[i-5] > 0) {
				
				StreamRefinaryAmplitudeMethods.filtered[StreamRefinaryAmplitudeMethods.filteredCounter++] = StreamRefinaryAmplitudeMethods.intBuffer[i-5] ;
				StreamRefinaryAmplitudeMethods.intBuffer[i-5] =  -1;
			}
			
			if(StreamRefinaryAmplitudeMethods.intBuffer[i-4] > 0) {
				
				StreamRefinaryAmplitudeMethods.filtered[StreamRefinaryAmplitudeMethods.filteredCounter++] = StreamRefinaryAmplitudeMethods.intBuffer[i-4];
				StreamRefinaryAmplitudeMethods.intBuffer[i-4] = -1;
			}
			
			StreamRefinaryAmplitudeMethods.filter22++;
		}
		
		if(StreamRefinaryAmplitudeMethods.intBuffer[i-9] > 0 && StreamRefinaryAmplitudeMethods.intBuffer[i-8] > 0 && StreamRefinaryAmplitudeMethods.intBuffer[i-7] > 0  
			&& StreamRefinaryAmplitudeMethods.intBuffer[i-3] > 0 && StreamRefinaryAmplitudeMethods.intBuffer[i-2] > 0 && StreamRefinaryAmplitudeMethods.intBuffer[i-1] > 0 
			&& (StreamRefinaryAmplitudeMethods.intBuffer[i-6] < 0 && StreamRefinaryAmplitudeMethods.intBuffer[i-5] < 0 && StreamRefinaryAmplitudeMethods.intBuffer[i-4] < 0)) {
			
			if(StreamRefinaryAmplitudeMethods.intBuffer[i-6] < 0) {
				
				StreamRefinaryAmplitudeMethods.filtered[StreamRefinaryAmplitudeMethods.filteredCounter++] = StreamRefinaryAmplitudeMethods.intBuffer[i-6];
				StreamRefinaryAmplitudeMethods.intBuffer[i-6] = 1;
			}
			
			if(StreamRefinaryAmplitudeMethods.intBuffer[i-5] < 0) {
				
				StreamRefinaryAmplitudeMethods.filtered[StreamRefinaryAmplitudeMethods.filteredCounter++] = StreamRefinaryAmplitudeMethods.intBuffer[i-5];
				StreamRefinaryAmplitudeMethods.intBuffer[i-5] = 1;
			}
			
			if(StreamRefinaryAmplitudeMethods.intBuffer[i-4] < 0) {
				
				StreamRefinaryAmplitudeMethods.filtered[StreamRefinaryAmplitudeMethods.filteredCounter++] = StreamRefinaryAmplitudeMethods.intBuffer[i-4];
				StreamRefinaryAmplitudeMethods.intBuffer[i-4] = 1;
			}
			
			StreamRefinaryAmplitudeMethods.filter3++;
		}
						
		if(StreamRefinaryAmplitudeMethods.intBuffer[i-9] < 0 && StreamRefinaryAmplitudeMethods.intBuffer[i-8] < 0 && StreamRefinaryAmplitudeMethods.intBuffer[i-7] < 0  
			&& StreamRefinaryAmplitudeMethods.intBuffer[i-3] < 0  && StreamRefinaryAmplitudeMethods.intBuffer[i-2] < 0  && StreamRefinaryAmplitudeMethods.intBuffer[i-1] < 0 
			&& (StreamRefinaryAmplitudeMethods.intBuffer[i-6] > 0 && StreamRefinaryAmplitudeMethods.intBuffer[i-5] > 0 && StreamRefinaryAmplitudeMethods.intBuffer[i-4] > 0)) {
			
			if(StreamRefinaryAmplitudeMethods.intBuffer[i-6] > 0) {
				
				StreamRefinaryAmplitudeMethods.filtered[StreamRefinaryAmplitudeMethods.filteredCounter++] = StreamRefinaryAmplitudeMethods.intBuffer[i-6];
				StreamRefinaryAmplitudeMethods.intBuffer[i-6] = -1;
			}
			
			if(StreamRefinaryAmplitudeMethods.intBuffer[i-5] > 0) {
				
				StreamRefinaryAmplitudeMethods.filtered[StreamRefinaryAmplitudeMethods.filteredCounter++] = StreamRefinaryAmplitudeMethods.intBuffer[i-5] ;
				StreamRefinaryAmplitudeMethods.intBuffer[i-5] =  -1;
			}
			
			if(StreamRefinaryAmplitudeMethods.intBuffer[i-4] > 0) {
				
				StreamRefinaryAmplitudeMethods.filtered[StreamRefinaryAmplitudeMethods.filteredCounter++] = StreamRefinaryAmplitudeMethods.intBuffer[i-4];
				StreamRefinaryAmplitudeMethods.intBuffer[i-4] = -1;
			}
			
			StreamRefinaryAmplitudeMethods.filter33++;
		}
						
		if(StreamRefinaryAmplitudeMethods.intBuffer[i-9] > 0 && StreamRefinaryAmplitudeMethods.intBuffer[i-8] > 0 && StreamRefinaryAmplitudeMethods.intBuffer[i-7] > 0  
			&& StreamRefinaryAmplitudeMethods.intBuffer[i-4] > 0 && StreamRefinaryAmplitudeMethods.intBuffer[i-3] > 0 && StreamRefinaryAmplitudeMethods.intBuffer[i-2] > 0  
			&& (StreamRefinaryAmplitudeMethods.intBuffer[i-6] < 0 && StreamRefinaryAmplitudeMethods.intBuffer[i-5] < 0 )) {
			
			if(StreamRefinaryAmplitudeMethods.intBuffer[i-6] < 0) {
				
				StreamRefinaryAmplitudeMethods.filtered[StreamRefinaryAmplitudeMethods.filteredCounter++] = StreamRefinaryAmplitudeMethods.intBuffer[i-6];
				StreamRefinaryAmplitudeMethods.intBuffer[i-6] = 1;
			}
			
			if(StreamRefinaryAmplitudeMethods.intBuffer[i-5] < 0) {
				
				StreamRefinaryAmplitudeMethods.filtered[StreamRefinaryAmplitudeMethods.filteredCounter++] = StreamRefinaryAmplitudeMethods.intBuffer[i-5];
				StreamRefinaryAmplitudeMethods.intBuffer[i-5] = 1;
			}
			
			StreamRefinaryAmplitudeMethods.filter4++;
		}
							
		if(StreamRefinaryAmplitudeMethods.intBuffer[i-9] < 0 && StreamRefinaryAmplitudeMethods.intBuffer[i-8] < 0 && StreamRefinaryAmplitudeMethods.intBuffer[i-7] < 0  
			&& StreamRefinaryAmplitudeMethods.intBuffer[i-4] < 0 && StreamRefinaryAmplitudeMethods.intBuffer[i-3] < 0 && StreamRefinaryAmplitudeMethods.intBuffer[i-2] < 0 
			&& (StreamRefinaryAmplitudeMethods.intBuffer[i-6] > 0 && StreamRefinaryAmplitudeMethods.intBuffer[i-5] > 0)) {
					
			if(StreamRefinaryAmplitudeMethods.intBuffer[i-6] > 0) {
				
				StreamRefinaryAmplitudeMethods.filtered[StreamRefinaryAmplitudeMethods.filteredCounter++] = StreamRefinaryAmplitudeMethods.intBuffer[i-6];
				StreamRefinaryAmplitudeMethods.intBuffer[i-6] = -1;
	
			}							
			if(StreamRefinaryAmplitudeMethods.intBuffer[i-5] > 0) {
				
				StreamRefinaryAmplitudeMethods.filtered[StreamRefinaryAmplitudeMethods.filteredCounter++] = StreamRefinaryAmplitudeMethods.intBuffer[i-5];
				StreamRefinaryAmplitudeMethods.intBuffer[i-5] = -1;
			}		
			
			StreamRefinaryAmplitudeMethods.filter44++;
		}
						
		if(StreamRefinaryAmplitudeMethods.intBuffer[i-9] > 0 && StreamRefinaryAmplitudeMethods.intBuffer[i-8] > 0 && StreamRefinaryAmplitudeMethods.intBuffer[i-7] > 0  
			&& StreamRefinaryAmplitudeMethods.intBuffer[i-5] > 0 && StreamRefinaryAmplitudeMethods.intBuffer[i-4] > 0  && StreamRefinaryAmplitudeMethods.intBuffer[i-3] > 0  
			&& StreamRefinaryAmplitudeMethods.intBuffer[i-6] < 0) {
				
			if(StreamRefinaryAmplitudeMethods.intBuffer[i-6] < 0) {
					
				StreamRefinaryAmplitudeMethods.filtered[StreamRefinaryAmplitudeMethods.filteredCounter++] = StreamRefinaryAmplitudeMethods.intBuffer[i-6];
				StreamRefinaryAmplitudeMethods.intBuffer[i-6] = 1;
			}
				
			StreamRefinaryAmplitudeMethods.filter5++;
		}
							
		if(StreamRefinaryAmplitudeMethods.intBuffer[i-9] < 0 && StreamRefinaryAmplitudeMethods.intBuffer[i-8] < 0 && StreamRefinaryAmplitudeMethods.intBuffer[i-7] < 0  
			&& StreamRefinaryAmplitudeMethods.intBuffer[i-5] < 0 && StreamRefinaryAmplitudeMethods.intBuffer[i-4] < 0  && StreamRefinaryAmplitudeMethods.intBuffer[i-3] < 0  
			&& StreamRefinaryAmplitudeMethods.intBuffer[i-6] > 0) {
				
			if(StreamRefinaryAmplitudeMethods.intBuffer[i-6] > 0) {
				
				StreamRefinaryAmplitudeMethods.filtered[StreamRefinaryAmplitudeMethods.filteredCounter++] = StreamRefinaryAmplitudeMethods.intBuffer[i-6];
				StreamRefinaryAmplitudeMethods.intBuffer[i-6] =  -1;
			}
					
			StreamRefinaryAmplitudeMethods.filter55++;					
		}
	}
}
