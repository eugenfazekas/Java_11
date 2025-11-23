package com.executor;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class JavaExecutor extends ExecutorFunctionClass{

	final static int[] coordinates1 = new int[] {37,954};	
	//final static int[] coordinates1 = new int[] {645,1023};
	final static int[] coordinates2 = new int[] {-1902,34};
	final static int[] coordinates3 = new int[] {-1835,165};
	final static int[] coordinates4 = new int[] {-868,540};
	final static String clipBoardText1 = new String("1920x1080");
	final static int[] coordinates5 = new int[] {-777,632};
	final static int[] coordinates6 = new int[] {-856,377};
	//back x 3
	final static String clipBoardText2 = new String("16:9");
	final static int[] coordinates7 = new int[] {-1140,452};
	final static int[] coordinates8 = new int[] {-1142,475};
	final static int[] coordinates9 = new int[] {-886,684};
	final static int[] coordinates10 = new int[] {-839,704};
		
	final static int[] coordinates11 = new int[] {241,322};
	
	final static int[] coordinates12 = new int[] {244,99};	
	final static int[] coordinates13 = new int[] {262,98};
	
	final static int[] coordinates14 = new int[] {262,119};	
	final static int[] coordinates15 = new int[] {234,118};
	
	final static int[] coordinates16 = new int[] {281,162};	
	final static int[] coordinates17 = new int[] {234,162};
	
	final static int[] coordinates18 = new int[] {406,355};	
	final static int[] coordinates19 = new int[] {246,356};
	
	final static int[] coordinates20 = new int[] {130,198};
	final static int[] coordinates21 = new int[] {130,218};
	
	final static int[] coordinates22 = new int[] {149,132};
	
	final static int[] coordinates23 = new int[] {354,284};
	final static int[] coordinates24 = new int[] {193,286};
		
	final static int[] coordinates25 = new int[] {227,276};


	public static void tvBekapcs() {
		
		Robot bot = null;
		int mask;
		mask = InputEvent.BUTTON1_DOWN_MASK;
		try {
			bot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//start vlc		
//		bot.mouseMove(coordinates1[0], coordinates1[1]);           
//		bot.mousePress(mask);     
//		bot.mouseRelease(mask);
//		sleep(100);
//		bot.mouseMove(coordinates1[0], coordinates1[1]);           
//		bot.mousePress(mask);     
//		bot.mouseRelease(mask);
	
		PowerShellExecutor.startVLC();
		
		sleep(800);//menu click	
		bot.mouseMove(coordinates2[0], coordinates2[1]);           
		bot.mousePress(mask);     
		bot.mouseRelease(mask);
		
		sleep(200);// click Felvevo eszkoz
		bot.mouseMove(coordinates3[0], coordinates3[1]);           
		bot.mousePress(mask);     
		bot.mouseRelease(mask);
		
		sleep(500); // copy too videosize
		bot.mouseMove(coordinates4[0], coordinates4[1]);
		bot.mousePress(mask);     
		bot.mouseRelease(mask);
		pasteFromClipboard(bot,clipBoardText1);
		
		// click specialis 
		bot.mouseMove(coordinates5[0], coordinates5[1]);
		bot.mousePress(mask);     
		bot.mouseRelease(mask);
		
		sleep(500); // copy too videoratio
		bot.mouseMove(coordinates6[0], coordinates6[1]);
		bot.mousePress(mask);     
		bot.mouseRelease(mask);
		bot.keyPress(KeyEvent.VK_BACK_SPACE);
		bot.keyRelease(KeyEvent.VK_BACK_SPACE);
		bot.keyPress(KeyEvent.VK_BACK_SPACE);
		bot.keyRelease(KeyEvent.VK_BACK_SPACE);
		bot.keyPress(KeyEvent.VK_BACK_SPACE);
		bot.keyRelease(KeyEvent.VK_BACK_SPACE);
		pasteFromClipboard(bot,clipBoardText2);
		
		// click enable eszkoz tulajdonsag
		bot.mouseMove(coordinates7[0], coordinates7[1]);
		bot.mousePress(mask);     
		bot.mouseRelease(mask);
		
		// click enable tuner tulajdonsag
		bot.mouseMove(coordinates8[0], coordinates8[1]);
		bot.mousePress(mask);     
		bot.mouseRelease(mask);
		
		// click ok
		bot.mouseMove(coordinates9[0], coordinates9[1]);
		bot.mousePress(mask);     
		bot.mouseRelease(mask);
		
		// click play
		sleep(500);
		bot.mouseMove(coordinates10[0], coordinates10[1]);
		bot.mousePress(mask);     
		bot.mouseRelease(mask);
				
		// click set default properties
		sleep(1000);
		bot.mouseMove(coordinates11[0], coordinates11[1]);
		bot.mousePress(mask);     
		bot.mouseRelease(mask);
		
		
		// click set brightness
		bot.mouseMove(coordinates12[0], coordinates12[1]);
		bot.mousePress(mask);  
		bot.mouseMove(coordinates13[0], coordinates13[1]);
		bot.mouseRelease(mask);
		
		// click set contrast
		bot.mouseMove(coordinates14[0], coordinates14[1]);
		bot.mousePress(mask);  
		bot.mouseMove(coordinates15[0], coordinates15[1]);
		bot.mouseRelease(mask);
		
		// click set saturation
		bot.mouseMove(coordinates16[0], coordinates16[1]);
		bot.mousePress(mask);  
		bot.mouseMove(coordinates17[0], coordinates17[1]);
		bot.mouseRelease(mask);
		
		// click apply
		bot.mouseMove(coordinates18[0], coordinates18[1]);
		bot.mousePress(mask);     
		bot.mouseRelease(mask);
		
		// click ok
		bot.mouseMove(coordinates19[0], coordinates19[1]);
		bot.mousePress(mask);     
		bot.mouseRelease(mask);
		
		sleep(500);//change frames type
		bot.mouseMove(coordinates20[0], coordinates20[1]);
		bot.mousePress(mask);  
		bot.mouseRelease(mask);
		sleep(200);
		bot.mouseMove(coordinates21[0], coordinates21[1]);
		bot.mousePress(mask); 
		bot.mouseRelease(mask);
		
		//change frames count
		bot.mouseMove(coordinates22[0], coordinates22[1]);
		bot.mousePress(mask); 
		bot.mouseRelease(mask);
		bot.keyPress(KeyEvent.VK_BACK_SPACE);
		bot.keyRelease(KeyEvent.VK_BACK_SPACE);
		bot.keyPress(KeyEvent.VK_5);
		bot.keyRelease(KeyEvent.VK_5);
		
		// click apply
		bot.mouseMove(coordinates23[0], coordinates23[1]);
		bot.mousePress(mask);     
		bot.mouseRelease(mask);
		
		// click ok
		bot.mouseMove(coordinates24[0], coordinates24[1]);
		bot.mousePress(mask);     
		bot.mouseRelease(mask);
		
		// click ok start play
		bot.mouseMove(coordinates25[0], coordinates25[1]);
		bot.mousePress(mask);     
		bot.mouseRelease(mask);

	}
	
	private static void pasteFromClipboard(Robot robot, String clipboardtext) {
		
		  Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();//Set the String to Enter

		  StringSelection stringSelection = new StringSelection(clipboardtext);//Copy the String to Clipboard

		  clipboard.setContents(stringSelection, null);//Use Robot class instance to simulate CTRL+C and CTRL+V key events :

		  robot.keyPress(KeyEvent.VK_CONTROL);

		  robot.keyPress(KeyEvent.VK_V);

		  robot.keyRelease(KeyEvent.VK_V);

		  robot.keyRelease(KeyEvent.VK_CONTROL);//Simulate Enter key event

		// robot.keyPress(KeyEvent.VK_ENTER);

		 //robot.keyRelease(KeyEvent.VK_ENTER);
	}
	
	private static void sleep(int sleepMsec) {
		
		try {
			Thread.sleep(sleepMsec);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
