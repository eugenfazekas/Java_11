package com;

public class GenericMethodTest {

	  // generic method printArray
	   public static < E > void printArray( E[] inputArray ) {
	      // Display array elements
	      for(E element : inputArray) {
	         System.out.printf("%s ", element);
	      }
	      System.out.println();
	   }
	   
	private static boolean checkCountedLastPixelsIfSame(int[] array, int index,int sizeToChek) {
		
		int num = array[index];
		int remain = array.length - index;
		int counter = 0;
		//if(array.length-remain < sizeToChek)
			//return false;
	
		for(int i = array.length-remain; i-- > array.length-remain-sizeToChek; ) {
			System.out.println("i: "+i+" array[i]: "+array[i]+" "+num);counter++;
			if(num != array[i]) {
				System.out.println(num+": "+array[i]);System.out.println("counter"+counter);
				return true;
			}
		}			
		return false;
	}

	   public static void main(String args[]) {
		   
		      int[] intArray1 = {20,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
		      int[] intArray2 = {0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		      System.out.println(intArray2.length);
	

		     System.out.println(checkCountedLastPixelsIfSame(intArray2,19,19));  
		     
		      //for(int i = 10;  i-- > 0; ) {}
		    	//  System.out.println(i);
		      
                // System.out.println(checkCountedLastPixelsIfSame(intArray,10));
		   
		   /*
	      // Create arrays of Integer, Double and Character

	      Double[] doubleArray = { 1.1, 2.2, 3.3, 4.4 };
	      Character[] charArray = { 'H', 'E', 'L', 'L', 'O' };

	      System.out.println("Array integerArray contains:");
	      printArray(intArray);   // pass an Integer array

	      System.out.println("\nArray doubleArray contains:");
	      printArray(doubleArray);   // pass a Double array

	      System.out.println("\nArray characterArray contains:");
	      printArray(charArray);   // pass a Character array
	      */
	   }

}
