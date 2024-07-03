package com.test;

import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		
		 int[] arr = new int[]{5,4,3,2,1,0,1,2,3,4,5,5,4,3,2,1,0,1,2,3,4,5};


		 buildSpline(arr,5);
	}
	
	public static int[] buildSpline(int[] arr, int length ) {
		
		double iterationLength= Math.floor(arr.length/length)*length;
		int[] res = new int [arr.length];
		int splineAverage  = 0;
		int dir = 0;
		for (int i = 1 ; i < iterationLength; i+=length) {
			splineAverage = 0;
			dir = 0;
			System.out.println("main i: "+i+" splineAverage " +splineAverage + " dir "+dir);
			for(int j = 1; j < length; j++) {
				System.out.println("splinecalc: "+(arr[i+j]-arr[i-1+j]));
				splineAverage+= arr[i+j]-arr[i-1+j];
			}
			System.out.println("splineAverage: "+splineAverage+" direction: "+dir + " length -1 "+(length-1));
			dir = splineAverage / (length-1);
			System.out.println("splineAverage: "+splineAverage+" direction: "+dir + " length -1 "+(length-1) + " sum " + (splineAverage / length-1));
			for(int k = 0; k < length; k++) {
				if(i+k < 0 || i+ k +dir < 0) { 
					System.out.println("bound exception1 "+ (i+k )+" "+(i+ k +dir)+" dir: "+dir);
					res[i+k] = 0;
						continue;
				}
				if(i+k > arr.length || i+ k +dir > arr.length) { 
					System.out.println("bound exception2 "+ (i+k )+" "+(i+ k +dir)+" dir: "+dir);
					res[i+k] = arr.length ;
					continue;
				}
				else {
					System.out.println("else i+k: " +(i+ k)+ "  arr[(i-1)]+k+dir "+ (arr[(i-1)]+k*dir)+" dir: "+dir);
					res[i+k]= arr[(i)]+k*dir;
				}
			}
			System.out.println("splineAverage1 : "+splineAverage / (length-1));
			System.out.println("splineAverage2 : "+splineAverage);
	}
		System.out.println(Arrays.toString(res));
		return null;
	}

}
