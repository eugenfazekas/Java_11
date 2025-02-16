package com;

public class Test3 {
	
	
	private static int[] DB_A = new int[]      {4,43,-78,35,  15,54,75,57,  42,47,-62,79,  66,14,23,10,  78,11,-54,13};
	private static int[] DB_A_copy = new int[] {4,43,-78,35,  15,54,75,57,  42,47,-62,79,  66,14,23,10,  78,11,-54,13};
	
	private static int[] checkA = new int[] {4,21,67,23,  16,21,-52,27,   34,42,74,65,   61,42,-63,70,    86,8,-21,17};
	
	private static int[] DB_F =   {4,43,-78,35,  15,54,75,57,  42,47,-62,79,  66,14,23,10,  78,11,-54,13};
	private static int[] DB_F_copy =   {4,43,-78,35,  15,54,75,57,  42,47,-62,79,  66,14,23,10,  78,11,-54,13};

	private static int[] checkF = new int[] {7,64,-64,10,  14,69,66,22,   24,57,-74,46,  33,43,73,16,     39,44,-59,14,   50,55,68,36,  64,50,-72,46,   77,33,31,14,   87,29,-63,18};
	
	private static int i;
	private static int j;
	private static int k;
	private static int aHPos;
	private static int aVPos;
	private static int aAngle;
	private static int aLength;
	private static int amplitudeMatch;
	
	private static int fHPos;
	private static int fVPos;
	private static int fAngle;
	private static int fLength;
	private static int frequencyMatch;

	private static int startingSlide;
	private static int highestA;
	private static int highestF;
	private static boolean optimizedLoopStart;
	
	public static void main(String[] args) {

		//mainLogic(DB_A,DB_F,DB_A_copy,DB_F_copy,4);
		mainLogic(DB_A,DB_F,checkA,checkF,4);
	}

	public static int[] mainLogic(int[] dbAInput, int[] dbFInput, int[] ASlopes, int[] FSlopes, int slideLength) {
		
		startingSlide = - (slideLength/2)*4;
		highestA = 0;
		highestF = 0;

		for(i = 0; i < slideLength; i++) {
			
			amplitudeMatch = 0;
			frequencyMatch = 0;
			j = 0;
			optimizedLoopStart = true;
			System.out.println("\nStarting A!");
			
    		for(j = Math.abs(startingSlide); j < ASlopes.length - Math.abs(startingSlide)+4; j = j+4)  {
    			
    			if(optimizedLoopStart == true && startingSlide == 4 && j == 4) {
    				j=0;
    				optimizedLoopStart = false;
    				System.out.println("0.0 A Opimized");
    			}
  				System.out.println("\n1.0 A startingSlide: "+startingSlide+ ", j: "+j+ ", length: "+(ASlopes.length - Math.abs(startingSlide)));
  				
    				if(j+3 < dbAInput.length && j+startingSlide < ASlopes.length) {
    					
    	    			System.out.println("2.0 B j: "+j+ ", dbAInput.length: "+dbAInput.length+", j+startingSlide: "+(j+startingSlide) + ", FSlopes length: "+ASlopes.length);
	    					
	    				if(Math.abs(dbAInput[j] - ASlopes[j+startingSlide]) < 50 || Math.abs(ASlopes[j+startingSlide] - dbAInput[j]) < 50 ) {
    						
		   					aHPos = 100 - Math.abs(dbAInput[j] - ASlopes[j+startingSlide]);
		   					aVPos = 100 - Math.abs(dbAInput[j+1] - ASlopes[j+1+startingSlide]);
		   					aAngle = 100 - Math.abs(dbAInput[j+2] - ASlopes[j+2+startingSlide]);
		   					aLength = 100 - Math.abs(dbAInput[j+3] - ASlopes[j+3+startingSlide]);
		   					
							System.out.println("\nDBA     j: " +(j)+ ", dbAInput[j]: "+dbAInput[j] +", [j+1]: "+dbAInput[j+1]+", [j+2]: "+dbAInput[j+2]+", [j+3]: "+dbAInput[j+3]);
							System.out.println("ASlopes j: " +(j+startingSlide) +",  ASlopes[j]: "+ASlopes[j+startingSlide]  +", [j+1]: "
								+ASlopes[j+startingSlide+1] +", [j+2]: "+ASlopes[j+startingSlide+2] +", [j+3]: "+ASlopes[j+startingSlide+3]);  					
		   					
		   					amplitudeMatch += (aHPos * aVPos * aAngle * aLength) / 1000;
		   					
		   					System.out.println("Slide: "+startingSlide +", Sloope res aHPos: "+aHPos +", aVPos: "+aVPos+", Angl: "
			   						+aAngle+", Lengt: "+aLength+", amplitudeMatch: "+(aHPos * aVPos * aAngle * aLength) / 1000 + ", amplitudeMatchTotal: "+amplitudeMatch);
	    				}
	    			}	
			}
    		
			if(dbAInput.length > ASlopes.length) {
						
				for(k = ASlopes.length;  k < dbAInput.length+4; k = k+4) {
									
  					amplitudeMatch-= (amplitudeMatch / 100) * dbAInput[k+3];
  					System.out.println("OverLength DB > AS k: "+k+ ", minus: "+((amplitudeMatch / 100) * dbAInput[k+3])+", amplitudeMatchTotal : "+amplitudeMatch + ", dbAInput[k+3] "+ dbAInput[k+3]);
				}

			}

			if(ASlopes.length > dbAInput.length) {		

				for(k =  j; k < dbAInput.length+4; k = k+4) {
					
					amplitudeMatch-= (amplitudeMatch / 100) * ASlopes[k+3];
					System.out.println("OverLength AS > DB j: "+k+ ", minus: "+((amplitudeMatch / 100) * ASlopes[k+3])+", amplitudeMatch : "+amplitudeMatch + ", dbAInput[k+3] "+ ASlopes[k+3]+ ", minus: "+(amplitudeMatch / 100) * ASlopes[k+3]);
				}
			}
    		
    		if(amplitudeMatch > highestA) {
    			
    			System.out.println("NEW HIGHEST A! "+amplitudeMatch);
    			highestA = amplitudeMatch;
    		}
    		
			j = 0;
			optimizedLoopStart = true;
			System.out.println("\nStarting B!");
			
    		for(j = Math.abs(startingSlide); j < FSlopes.length - Math.abs(startingSlide)+4; j = j+4)  {
    			
      			if(optimizedLoopStart == true && startingSlide == 4 && j == 4) {
    				j=0;
    				optimizedLoopStart = false;
    				System.out.println("0.0 A Opimized");
    			}
    			
  				System.out.println("\n1.0 B startingSlide: "+startingSlide+ ", j: "+j+ ", FSlopes.length - startingSlide: "+(FSlopes.length - Math.abs(startingSlide)));
  				
    			if(j+3 < dbFInput.length && j+startingSlide < FSlopes.length) {
    					
	    			System.out.println("2.0 B j: "+j+ ", dbFInput.length: "+dbFInput.length+", j+startingSlide: "+(j+startingSlide) + ", FSlopes length: "+FSlopes.length);
	    					
	    			if(Math.abs(dbFInput[j] - FSlopes[j+startingSlide]) < 50 || Math.abs(FSlopes[j+startingSlide] - dbFInput[j]) < 50 ) {
							
						fHPos = 100 - Math.abs(dbFInput[j] - FSlopes[j+startingSlide]);
						fVPos = 100 - Math.abs(dbFInput[j+1] - FSlopes[j+1+startingSlide]);
						fAngle = 100 - Math.abs(dbFInput[j+2] - FSlopes[j+2+startingSlide]);
						fLength = 100 - Math.abs(dbFInput[j+3] - FSlopes[j+3+startingSlide]);
						 
						System.out.println("DBF     j: " +(j)+ ", dbFInput[j]: "+dbFInput[j] +", [j+1]: "+dbFInput[j+1]+", [l+2]: "+dbFInput[j+2]+", [j+3]: "+dbFInput[j+3]);
						System.out.println("FSlopes j: " +(j+startingSlide)+ ",  FSlopes[j]: "+FSlopes[j+startingSlide]  +", [j+1]: "+FSlopes[j+startingSlide+1] +", [j+2]: "+FSlopes[j+startingSlide+2] +", [j+3]: "+FSlopes[j+startingSlide+3]);
						 		   				
						frequencyMatch += (fHPos * fVPos * fAngle * fLength) / 1000;		
						
		   				System.out.println("Slide: "+startingSlide +", Sloope res fHPos: "+fHPos +", fVPos: "+fVPos+", Angl: "
			   					+fAngle+", Lengt: "+fLength+", frequencyMatch: "+(fHPos * fVPos * fAngle * fLength) / 1000+ ", frequencyMatch: "+frequencyMatch);
					}				
				}
			}
			
			if(dbFInput.length > FSlopes.length) {
				
				for(k = FSlopes.length+4 ; k < dbFInput.length; k = k+4) {
					
					frequencyMatch-= (frequencyMatch / 100) * ASlopes[k+3];
					System.out.println("OverLength DB > FS k: "+k+", frequencyMatch : "+frequencyMatch + ", dbFInput[k+3] "+ dbFInput[k+3]+ ", minus: "+((frequencyMatch / 100) * dbFInput[k+3]));
				}

			}
			System.out.println("longer length!" + (FSlopes.length > dbFInput.length) );	
			if(FSlopes.length > dbFInput.length) {		
		
				for(k = dbFInput.length+4; k < FSlopes.length; k = k+4) {
					
					frequencyMatch-= (frequencyMatch / 100) * FSlopes[k+3];
					System.out.println("OverLength FS > DB k: "+k+", frequencyMatch : "+frequencyMatch + ", dbFInput[k+3] "+ FSlopes[k+3]+ ", minus: "+(frequencyMatch / 100) * FSlopes[k+3]);
				}
			}
			
    		if(frequencyMatch > highestF) {
    			
    			System.out.println("NEW HIGHEST B!"+frequencyMatch);
    			highestF = frequencyMatch;
    		}
			System.out.println("\nNew Slide! ");
			startingSlide += 4;
		}

		System.out.println("HighestA: "+highestA+ ", HighestF: "+highestF);
	
		return new int[] {highestA,highestF};
	}
}
