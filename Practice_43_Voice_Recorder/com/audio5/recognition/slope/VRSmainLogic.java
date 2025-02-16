package com.audio5.recognition.slope;

import com.audio8.util.Debug;

public class VRSmainLogic {

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

	public static int[] mainLogic(int[] dbAInput, int[] dbFInput, int[] ASlopes, int[] FSlopes, int slideLength) {
		
		startingSlide = - (slideLength/2)*4;
		highestA = 0;
		highestF = 0;

		for(i = 0; i < slideLength; i++) {
			
			amplitudeMatch = 0;
			frequencyMatch = 0;
			j = 0;
			optimizedLoopStart = true;
			Debug.debug(5,"\nStarting A!");
			
    		for(j = Math.abs(startingSlide); j < ASlopes.length - Math.abs(startingSlide)+4; j = j+4)  {
    			
    			if(optimizedLoopStart == true && startingSlide == 4 && j == 4) {
    				j=0;
    				optimizedLoopStart = false;
    				Debug.debug(5,"0.0 A Opimized");
    			}
    			Debug.debug(5,"\n1.0 A startingSlide: "+startingSlide+ ", j: "+j+ ", length: "+(ASlopes.length - Math.abs(startingSlide)));
  				
    				if(j+3 < dbAInput.length && j+startingSlide < ASlopes.length) {
    					
    					Debug.debug(5,"2.0 B j: "+j+ ", dbAInput.length: "+dbAInput.length+", j+startingSlide: "+(j+startingSlide) + ", FSlopes length: "+ASlopes.length);
	    					
	    				if(Math.abs(dbAInput[j] - ASlopes[j+startingSlide]) < 50 || Math.abs(ASlopes[j+startingSlide] - dbAInput[j]) < 50 ) {
    						
		   					aHPos = 100 - Math.abs(dbAInput[j] - ASlopes[j+startingSlide]);
		   					aVPos = 100 - Math.abs(dbAInput[j+1] - ASlopes[j+1+startingSlide]);
		   					aAngle = 100 - Math.abs(dbAInput[j+2] - ASlopes[j+2+startingSlide]);
		   					aLength = 100 - Math.abs(dbAInput[j+3] - ASlopes[j+3+startingSlide]);
		   					
		   					Debug.debug(5,"\nDBA     j: " +(j)+ ", dbAInput[j]: "+dbAInput[j] +", [j+1]: "+dbAInput[j+1]+", [j+2]: "+dbAInput[j+2]+", [j+3]: "+dbAInput[j+3]);
		   					Debug.debug(5,"ASlopes j: " +(j+startingSlide) +",  ASlopes[j]: "+ASlopes[j+startingSlide]  +", [j+1]: "
								+ASlopes[j+startingSlide+1] +", [j+2]: "+ASlopes[j+startingSlide+2] +", [j+3]: "+ASlopes[j+startingSlide+3]);  					
		   					
		   					amplitudeMatch += aHPos * (Math.pow(aVPos/10,2) ) * (Math.pow(aAngle/10,3)) *(Math.pow(aLength/10,4)) / 100000;	
		   					
		   					Debug.debug(5,"Slide: "+startingSlide +", Sloope res aHPos: "+aHPos +", aVPos: "+aVPos+", Angl: "
			   						+aAngle+", Lengt: "+aLength+", amplitudeMatch: "+(aHPos * (Math.pow(aVPos/10,2) ) * (Math.pow(aAngle/10,3)) *(Math.pow(aLength/10,4)) / 100000)+ ", amplitudeMatchTotal: "+amplitudeMatch);
	    				}
	    			}	
			}
    		
			if(dbAInput.length > ASlopes.length) {
						
				for(k = ASlopes.length+4;  k < dbAInput.length; k = k+4) {
									
  					amplitudeMatch-= (amplitudeMatch / 100) * dbAInput[k+3];
  					Debug.debug(5,"OverLength DB > AS k: "+k+ ", minus: "+((amplitudeMatch / 100) * dbAInput[k+3])+", amplitudeMatchTotal : "+amplitudeMatch + ", dbAInput[k+3] "+ dbAInput[k+3]);
				}

			}

			if(ASlopes.length > dbAInput.length) {		

				for(k =  dbAInput.length+4; k < ASlopes.length; k = k+4) {
					
					amplitudeMatch-= (amplitudeMatch / 100) * ASlopes[k+3];
					Debug.debug(5,"OverLength AS > DB j: "+k+ ", minus: "+((amplitudeMatch / 100) * ASlopes[k+3])+", amplitudeMatch : "+amplitudeMatch + ", dbAInput[k+3] "+ ASlopes[k+3]+ ", minus: "+(amplitudeMatch / 100) * ASlopes[k+3]);
				}
			}
    		
    		if(amplitudeMatch > highestA) {
    			
    			Debug.debug(5,"NEW HIGHEST A! "+amplitudeMatch);
    			highestA = amplitudeMatch;
    		}
    		
			j = 0;
			optimizedLoopStart = true;
			Debug.debug(5,"\nStarting B!");
			
    		for(j = Math.abs(startingSlide); j < FSlopes.length - Math.abs(startingSlide)+4; j = j+4)  {
    			
      			if(optimizedLoopStart == true && startingSlide == 4 && j == 4) {
    				j=0;
    				optimizedLoopStart = false;
    				Debug.debug(5,"0.0 A Opimized");
    			}
    			
      			Debug.debug(5,"\n1.0 B startingSlide: "+startingSlide+ ", j: "+j+ ", FSlopes.length - startingSlide: "+(FSlopes.length - Math.abs(startingSlide)));
  				
    			if(j+3 < dbFInput.length && j+startingSlide < FSlopes.length) {
    					
    				Debug.debug(5,"2.0 B j: "+j+ ", dbFInput.length: "+dbFInput.length+", j+startingSlide: "+(j+startingSlide) + ", FSlopes length: "+FSlopes.length);
	    					
	    			if(Math.abs(dbFInput[j] - FSlopes[j+startingSlide]) < 50 || Math.abs(FSlopes[j+startingSlide] - dbFInput[j]) < 50 ) {
							
						fHPos = 100 - Math.abs(dbFInput[j] - FSlopes[j+startingSlide]);
						fVPos = 100 - Math.abs(dbFInput[j+1] - FSlopes[j+1+startingSlide]);
						fAngle = 100 - Math.abs(dbFInput[j+2] - FSlopes[j+2+startingSlide]);
						fLength = 100 - Math.abs(dbFInput[j+3] - FSlopes[j+3+startingSlide]);
						 
						Debug.debug(5,"DBF     j: " +(j)+ ", dbFInput[j]: "+dbFInput[j] +", [j+1]: "+dbFInput[j+1]+", [l+2]: "+dbFInput[j+2]+", [j+3]: "+dbFInput[j+3]);
						Debug.debug(5,"FSlopes j: " +(j+startingSlide)+ ",  FSlopes[j]: "+FSlopes[j+startingSlide]  +", [j+1]: "+FSlopes[j+startingSlide+1] +", [j+2]: "+FSlopes[j+startingSlide+2] +", [j+3]: "+FSlopes[j+startingSlide+3]);
						
						frequencyMatch += (fAngle * (Math.pow(fVPos/10,2)) * (Math.pow(fHPos/10,3)    *(Math.pow(fLength/10,4))) / 100000);
						//frequencyMatch += (fHPos * (Math.pow(fVPos/10,2) ) * (Math.pow(fAngle/10,3)) *(Math.pow(fLength/10,4))) / 100000;	
						
	
						
						Debug.debug(5,"Slide: "+startingSlide +", Sloope res fHPos: "+fHPos +", fVPos: "+fVPos+", Angl: "
			   					+fAngle+", Lengt: "+fLength+", frequencyMatch: "+(fHPos * (Math.pow(fVPos/10,2) ) * (Math.pow(fAngle/10,3)) *(Math.pow(fLength/10,4)) / 100000)+ ", frequencyMatchTotal: "+frequencyMatch);
					}				
				}
			}
			
			if(dbFInput.length > FSlopes.length) {
				
				for(k = FSlopes.length+4 ; k < dbFInput.length; k = k+4) {
					
					frequencyMatch-= (frequencyMatch / 100) * dbFInput[k+3];
					Debug.debug(5,"OverLength DB > FS k: "+k+", frequencyMatch : "+frequencyMatch + ", dbFInput[k+3] "+ dbFInput[k+3]+ ", minus: "+((frequencyMatch / 100) * dbFInput[k+3]));
				}

			}
			Debug.debug(5,"longer length!" + (FSlopes.length > dbFInput.length) );	
			if(FSlopes.length > dbFInput.length) {		
		
				for(k = dbFInput.length+4; k < FSlopes.length; k = k+4) {
					
					frequencyMatch-= (frequencyMatch / 100) * FSlopes[k+3];
					Debug.debug(5,"OverLength FS > DB k: "+k+", frequencyMatch : "+frequencyMatch + ", dbFInput[k+3] "+ FSlopes[k+3]+ ", minus: "+(frequencyMatch / 100) * FSlopes[k+3]);
				}
			}
			
    		if(frequencyMatch > highestF) {
    			
    			Debug.debug(5,"NEW HIGHEST B!"+frequencyMatch);
    			highestF = frequencyMatch;
    		}
    		Debug.debug(5,"\nNew Slide! ");
			startingSlide += 4;
		}

		Debug.debug(3,"HighestA: "+(highestA/10)+ ", HighestF: "+(highestF/10));
	
		return new int[] {highestA/10,highestF/10};
	}
}
