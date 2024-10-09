package com;

import java.util.Arrays;

public class MainTest3 {

	static int dB_1_Percent;
	static int difference;
	static int result;
	static int[] test = new int[]{0,1};
	
	public static void main(String[] args) {
		
		
		addToarray(test,5);
		
		System.out.println(Arrays.toString(test));

//		System.out.println(getProcent(400,400));
//		System.out.println(getProcent(400,500));
//		System.out.println(getProcent(400,300));
//		
//		System.out.println(getProcent(400,600));
//		System.out.println(getProcent(400,200));
//		
//		System.out.println(getProcent(400,700));
//		System.out.println(getProcent(400,100));
//		
//		System.out.println(getProcent(400,800));
//		System.out.println(getProcent(400,0));
//		
//		System.out.println(getProcent(400,900));
//		System.out.println(getProcent(100,150));
	}
	
	public static void addToarray(int[] arrToadd , int  value) {
		
		int[] newArray = new int[arrToadd.length+1];
		
		for(int i = 0; i < arrToadd.length; i++) {
			arrToadd [i] = arrToadd[i]+2;
		}
		newArray[arrToadd.length] = value;
		arrToadd = new int[2];
	}

	public static int getProcent(int dB_Value, int check_Value) {
		
		dB_1_Percent = dB_Value/100;
		difference = Math.abs(dB_Value) - Math.abs(check_Value);
		result = 100 - Math.abs(difference /dB_1_Percent);
		if((dB_Value > 0 && check_Value < 0)||(dB_Value < 0 && check_Value > 0) ||result< 0 )
			return 1;
		return result;
	}
}
