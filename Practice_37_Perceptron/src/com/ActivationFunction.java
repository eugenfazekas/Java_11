package com;

public final class ActivationFunction {
	
    public static int step(double x) {
        if (x >= 0) {
        	//System.out.println("ActivationFunction step 1");
            return 1;
        } else {
        	//System.out.println("ActivationFunction step -1");
            return -1;
        }
    }

}
