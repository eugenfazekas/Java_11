package com;

import java.util.Arrays;

public class MatrixFilter {
	

	   public static void main(String args[]) {
		   
			int[][] testMatrix = {
					{0,0,0,0,0,1,0,0,0,0},
					{0,0,0,0,0,0,0,0,0,0},
					{0,0,0,0,0,0,0,0,0,0},
					{0,0,0,0,0,0,0,0,0,0},
					{1,0,0,0,0,0,0,0,0,0},
					{0,0,0,0,0,0,0,0,0,1},
					{0,0,0,0,0,0,0,0,0,0},
					{0,0,0,0,0,0,0,0,0,0},
					{0,0,0,0,0,0,0,0,0,0},
					{0,0,0,0,0,1,0,0,0,0},				
				};

		   
			int[] ret = mainFilterring(testMatrix,0,1,1,1);
			System.out.println("ret: "+ Arrays.toString(ret));
		   
	   }
	   	   
		public static int[] mainFilterring (int[][] inputMatrix, int baseValue,int testValue, int verticalFilterDensity, int orizontalFilterDensity) {
			
			int[] ret = new int[6];
			for(int y = 0; y < inputMatrix.length; y = y + verticalFilterDensity) {						
				for(int x = 0; x < inputMatrix[y].length; x = x + orizontalFilterDensity) {
									
					if(inputMatrix[y][x] != baseValue && (y > 0 && y < inputMatrix.length - 1 && x > 0  && x < inputMatrix[y].length - 1  ))
						ret[4] = 1;			
					if(y == 0 && inputMatrix[y][x] != baseValue )
					    ret[0] = 1;
					if(y == inputMatrix.length-1 && inputMatrix[y][x] != baseValue )
						ret[2] = 1; 
					if(x == 0 && inputMatrix[y][x] != baseValue)
						ret[1] = 1;
					if(x == inputMatrix[y].length-1 && inputMatrix[y][x] != baseValue)
						ret[3] = 1;
				}
			}
			
			ret[5] = (ret[0] + ret[1] + ret[2]+ ret[3]) == 0 && (ret[4] == 1) ? 1 : 0;
			return ret;
		}
}
