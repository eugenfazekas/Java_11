package temp;

import java.util.Arrays;
import java.util.Comparator;

public class PointsBuilder {
	
	static int[] test = new int[] {0,3,78,1,3,84,5,46,84,6,46,105,7,46,45,14,39,34,15,37,27,17,19,154,18,21,157,26,77,71,27,78,101,28,77,56,36,55,48,37,54,63,38,54,26,43,54,26,44,54,56,45,53,37,56,34,26,60,3,82,61,3,74};
	static int lengthLimit = 4;
	static Point[] newOrderPoints ;
	public static void main(String[] args) {

		Point[] buildedPoints = buildPoints(test);
		
		filterPoints(buildedPoints);
	}
	
	private static void filterPoints(Point[] inputPoints) {

		Point[] points = inputPoints;
		boolean build = true;	
		boolean added = false;	
        Arrays.sort(points, new SortPoints());
        
    	for(int i = 1; i < points.length; i++) 
    		System.out.println("Sorted i: "+i+", Point: "+points[i].toString());
        
		newOrderPoints = new Point[1];
		newOrderPoints[0] = points[0];

        while(build) {
        	
        	added = false;
        	
        	for(int i = 0; i < newOrderPoints.length; i++) {

            	for(int j = 0; j < points.length; j++) {
            		
            		System.out.println("\ni: "+i+", j: "+j +", newOrderPoints[i]: "+newOrderPoints[i].toString()
            				+", points[j]: "+points[j].toString());
            		
            		if(points[j].getIndex() < newOrderPoints[0].getIndex()-lengthLimit) {
            			
                		System.out.println("Phase 1.0 i: "+i+", j: "+j + ", nop length: " +newOrderPoints.length+", newOrderPoints[0]: "+newOrderPoints[0].toString()
	            				+", points[j]: "+points[j].toString());
	        			
	        			newOrderPoints = addPoints(newOrderPoints, points[j],0);
	        			added = true;
	        			break;          			
            		}
            			

					if(points[j].getIndex() >  newOrderPoints[newOrderPoints.length-1].getIndex()+lengthLimit) {
						
	            		System.out.println("Phase 2.0 i: "+i+", j: "+j+ ", nop length: " +newOrderPoints.length 
	            				+", newOrderPoints[length - 1]: "+newOrderPoints[newOrderPoints.length-1].toString()
	            				+", points[j]: "+points[j].toString());
	            		
	        			newOrderPoints = addPoints(newOrderPoints, points[j],newOrderPoints.length);
	        			added = true;
	        			break;						
					}
					
					if(i > 0 && newOrderPoints[i-1].getIndex() < points[j].getIndex()-lengthLimit &&
							newOrderPoints[i].getIndex() > points[j].getIndex()+lengthLimit) {
						
	            		System.out.println("Phase 3.0 i: "+i+", j: "+j+ ", nop length: " +newOrderPoints.length +", newOrderPoints[i-1]: "+newOrderPoints[i-1].toString()
	            				+", points[j]: "+points[j].toString()+", newOrderPoints[i+1]: "+newOrderPoints[i].toString());
	            		
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
        	System.out.println(newOrderPoints[i].toString());
	}
	
	static Point[] addPoints(Point[] inputPoints, Point point, int index) {
		
		Point[] newPoints = new Point[inputPoints.length+1];
		int arrayCounter = 0;
		int baseCounter = 0;
		for(int i = 0; i < newPoints.length; i++) {
			
			System.out.println("addPoints i: " +i + ", index: "+index + ", Point: "+point.toString());
			
			if(index == i) {
				
				System.out.println("VoiceRecognitionPointsBuilder addPoints addedPoint: " 
						+point.toString());
				newPoints[arrayCounter++] = point;

				continue;
			}
		
			newPoints[arrayCounter++] = inputPoints[baseCounter++];
			
			System.out.println("VoiceRecognitionPointsBuilder addPoints normal point: " 
					+newPoints[arrayCounter-1]);	
		}	
			return newPoints;
	}
	
	static Point[] buildPoints(int[] input) {
		
		Point[] points = new Point[input.length/3];
		
		for(int i = 0; i <  input.length; i = i+3) {
			
			points[i/3] = new Point(input[i],input[i+1],input[i+2]);
			System.out.println("Point i: "+ (i/3) +", Point: "+ points[i/3]);
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
