package com;
import java.awt.Color;
public class Main2 {

	public static void main(String[] args) {

		int  red = (getIntFromColor(1,0,0));
		
		 Color c = new Color(red, true);
		System.out.println(red); 
		 
		    System.out.println(c.getRed());
		    System.out.println(c.getGreen());
		    System.out.println(c.getBlue());
		    System.out.println(c.getAlpha());
		System.out.println();
	}

	public static int getIntFromColor(float Red, float Green, float Blue){
	    int R = Math.round(255 * Red);
	    int G = Math.round(255 * Green);
	    int B = Math.round(255 * Blue);

	    R = (R << 16) & 0x00FF0000;
	    G = (G << 8) & 0x0000FF00;
	    B = B & 0x000000FF;

	    return 0xFF000000 | R | G | B;
	}
}
