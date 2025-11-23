package com.voiceRecognition.audio5.audioGramBuilder;

import java.util.Arrays;
import java.util.Comparator;

import com.voiceRecognition.audio8.util.Debug;

public class VoiceRecognitionPointsBuilder {
	
	static int i;
	static int[][] points;
	static int pointsCounter;
	static int lastAddedIndex;
	static int backAngle;
	static int forwardAngle;
	static int lengthLimit = 4;
	static Point[] newOrderPoints ;
	static Point[] temp;
	
	private static int debug_level_INFO = 5;
	private static int debud_level_DEBUG = 5;
	
	public static int[][] mainPointsBuilder(int[] frequency, int[] amplitude, int[] borders) {
		
		newOrderPoints = new Point[100];
		pointsCounter = 0;
		lastAddedIndex =0;
		i = borders[0] > 2 ? borders[0] : 2;
	
		for(; i < frequency.length-2; i++) {
			
			if((frequency[i-1] > frequency[i] && frequency[i+1] >= frequency[i]) || 
				(frequency[i-1] < frequency[i] && frequency[i+1] <= frequency[i]) || 
					angleCheck(frequency[i-2],frequency[i],frequency[i+2],2) > 25) {
				
					newOrderPoints[pointsCounter++] = new Point(i-borders[0],frequency[i]
						,angleCheck(frequency[i-2],frequency[i],frequency[i+2],2));

					lastAddedIndex = i;
			}
		}
		
		temp = new Point[pointsCounter];
		
		for(i = 0; i < pointsCounter; i++) 
			temp[i] = newOrderPoints[i];
		
		newOrderPoints = temp;
		
		temp = filterPoints(newOrderPoints);
		points = new int[2][];
		points[0] = new int[temp.length * 2];
		points[1] = new int[temp.length * 2];
		
		pointsCounter = 0;
		
		for( i = 0; i < temp.length; i++) {
			
			points[0][pointsCounter++] = temp[i].getIndex();
			points[0][pointsCounter++] = temp[i].getHeight();
		}
		
		pointsCounter = 0;
		
		for(i = 0; i < temp.length; i++) {
			
			Debug.debug(debud_level_DEBUG,"VoiceRecognitionPointsBuilder 2.0 i:"+ i+ ", amp[i]" +amplitude[i] 
					+ ", border[0]: "+ borders[0] +",temp[i].getIndex(): "+temp[i].getIndex());
			points[1][pointsCounter++] = temp[i].getIndex();
			points[1][pointsCounter++] = amplitude[temp[i].getIndex()+borders[0]];
		}
		
		Debug.debug(debug_level_INFO,"VoiceRecognitionPointsBuilder Freq_Array: "
				+Arrays.toString(points[0])+ ", Ampl_Array: "+Arrays.toString(points[1]));
		
			return points;
	}
	
	private static int angleCheck(int y1, int y2, int y3 ,int x) {

		backAngle = getAngle(y2-y1,x);
		forwardAngle = getAngle(y3-y2,x);
		
			return Math.abs(forwardAngle - backAngle);
	}
	
	private static int getAngle(int y, int x) {
		
		return (int) Math.toDegrees(Math.atan2(y, x));
	}

	private static Point[] filterPoints(Point[] inputPoints) {

		Point[] points = inputPoints;
		boolean build = true;	
		boolean added = false;	
        Arrays.sort(points, new SortPoints());
        
    	for(int i = 1; i < points.length; i++) 
    		Debug.debug(debud_level_DEBUG,"VoiceRecognitionPointsBuilder Sorted i: "+i+", Point: "
    			+points[i].toString());
        
		newOrderPoints = new Point[1];
		newOrderPoints[0] = points[0];

        while(build) {
        	
        	added = false;
        	
        	for(int i = 0; i < newOrderPoints.length; i++) {

            	for(int j = 0; j < points.length; j++) {
            		
            		Debug.debug(debud_level_DEBUG,"\nVoiceRecognitionPointsBuilder i: "+i+", j: "+j 
            			+", newOrderPoints[i]: "+newOrderPoints[i].toString()+", points[j]: "
            			+points[j].toString());
            		
            		if(points[j].getIndex() < newOrderPoints[0].getIndex()-lengthLimit) {
            			
            			Debug.debug(debud_level_DEBUG,"VoiceRecognitionPointsBuilder Phase 1.0 i: "+i+", j: "+j 
            					+ ", nop length: " +newOrderPoints.length+", newOrderPoints[0]: "
            					+newOrderPoints[0].toString()+", points[j]: "+points[j].toString());
	        			
	        			newOrderPoints = addPoints(newOrderPoints, points[j],0);
	        			added = true;
	        			break;          			
            		}
            			

					if(points[j].getIndex() >  newOrderPoints[newOrderPoints.length-1].getIndex()+lengthLimit) {
						
						Debug.debug(debud_level_DEBUG,"VoiceRecognitionPointsBuilder Phase 2.0 i: "+i+", j: "+j
								+ ", nop length: " +newOrderPoints.length +", newOrderPoints[length - 1]: "
								+newOrderPoints[newOrderPoints.length-1].toString()+", points[j]: "
								+points[j].toString());
	            		
	        			newOrderPoints = addPoints(newOrderPoints, points[j],newOrderPoints.length);
	        			added = true;
	        			break;						
					}
					
					if(i > 0 && newOrderPoints[i-1].getIndex() < points[j].getIndex()-lengthLimit &&
							newOrderPoints[i].getIndex() > points[j].getIndex()+lengthLimit) {
						
						Debug.debug(debud_level_DEBUG,"VoiceRecognitionPointsBuilder Phase 3.0 i: "+i+", j: "
								+j+ ", nop length: " +newOrderPoints.length +", newOrderPoints[i-1]: "
								+newOrderPoints[i-1].toString()+", points[j]: "+points[j].toString()
								+", newOrderPoints[i+1]: "+newOrderPoints[i].toString());
	            		
	        			newOrderPoints = addPoints(newOrderPoints, points[j],i);
	        			added = true;    	
	        			break;						
					}
            	}
            	
            	if(added)
            		break;
        	}
        		if(!added)
        			build = false;
        }
        
        for(int i = 0 ; i < newOrderPoints.length; i++)
        	Debug.debug(debud_level_DEBUG,"VoiceRecognitionPointsBuilder newOrderPoints: "
        		+newOrderPoints[i].toString());
        
        return newOrderPoints;
	}
	
	static Point[] addPoints(Point[] inputPoints, Point point, int index) {
		
		Point[] newPoints = new Point[inputPoints.length+1];
		int arrayCounter = 0;
		int baseCounter = 0;
		for(int i = 0; i < newPoints.length; i++) {
			
			Debug.debug(debud_level_DEBUG,"VoiceRecognitionPointsBuilder addPoints i: " +i + ", index: "+index
				+ ", Point: "+point.toString());
			
			if(index == i) {
				
				Debug.debug(debud_level_DEBUG,"VoiceRecognitionPointsBuilder addPoints addedPoint: " 
						+point.toString());
				newPoints[arrayCounter++] = point;

				continue;
			}
		
			newPoints[arrayCounter++] = inputPoints[baseCounter++];
			
			Debug.debug(debud_level_DEBUG,"VoiceRecognitionPointsBuilder addPoints normal point: " 
					+newPoints[arrayCounter-1]);	
		}	
			return newPoints;
	}
	
	static Point[] buildPoints(int[] input) {
		
		Point[] points = new Point[input.length/3];
		
		for(int i = 0; i <  input.length; i = i+3) {
			
			points[i/3] = new Point(input[i],input[i+1],input[i+2]);
			
			Debug.debug(debud_level_DEBUG,"VoiceRecognitionPointsBuilder Point i: "+ (i/3) +", Point: "+ points[i/3]);
		}
		
		return points;
	}
}

	class SortPoints implements Comparator<Point> {
    
	@Override
    public int compare(Point p1, Point p2){
        
        return p2.getAngle() - p1.getAngle();
    }
}

	class Point {
	
		private int index;
		private int height;
		private int angle;
			  
		public Point(int index, int height, int angle) {
			this.index = index;
			this.height = height;
			this.angle = angle;
		}
	
		public int getIndex() {
			return index;
		}
	
		public int getHeight() {
			return height;
		}
	
		public int getAngle() {
			return angle;
		}
	
		@Override
		public String toString() {
			return "Point [index=" + index + ", height=" + height + ", angle=" + angle + "]";
		}		
}
