package com;

import java.util.Arrays;
import java.util.Comparator;

public class SortExample {
	
	
	public static void sort() {
		
		Point[] points = new Point[10];
		
	    Arrays.sort(points, new SortPoints());
	}
}

class SortPoints implements Comparator<Point>{

	@Override
	public int compare(Point p1, Point p2){
	    
	    return p1.getAngle() - p2.getAngle();
	}
}

class Point{
	
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
}

