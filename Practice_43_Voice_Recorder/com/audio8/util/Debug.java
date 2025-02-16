package com.audio8.util;

import java.time.LocalDateTime;

public class Debug {

	private static int counter = 0;
	private static int baseDebugLevel = 4;
	public static long startTime;
	
	/* Debug Levels
	* 1.Contructor
	* 2.Method
	* 3.DEBUG
	* 4.INFO
	* 5.LOOP
	* 
	*/
	public static void debug( int debugLevel, String message) {
		
		if(debugLevel  <= baseDebugLevel)
			System.out.println(counter+++"."+message);
	}	
	
	public static String getTimeStamp() {
		
		 LocalDateTime timestamp = LocalDateTime.now();	
		 
		 return timestamp.getHour()+"_"+ timestamp.getMinute()+ "_" + timestamp.getSecond()  
				+" "+ timestamp.getDayOfMonth() + "." +timestamp.getMonthValue() + "."+timestamp.getYear();
	}
}
