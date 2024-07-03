package com;

import java.awt.AWTException;
import java.awt.Frame;
import java.awt.Window;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import javax.swing.JDialog;
import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) throws Exception {
		/*
		 Robot robot = new Robot();
		 robot.keyPress(KeyEvent.VK_ALT);
		 robot.keyPress(KeyEvent.VK_TAB);
		 robot.keyRelease(KeyEvent.VK_ALT);
		 robot.keyRelease(KeyEvent.VK_TAB);
		 */
		
		//String command = "powershell.exe  your command";
		  //Getting the version
		  String command = "powershell.exe  Get-NetIPAddress";
		  // Executing the command
		  Process powerShellProcess = null;
		try {
			powerShellProcess = Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  // Getting the results
		  try {
			powerShellProcess.getOutputStream().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  String line;
		  System.out.println("Standard Output:");
		  BufferedReader stdout = new BufferedReader(new InputStreamReader(
		    powerShellProcess.getInputStream()));
		  try {
			while ((line = stdout.readLine()) != null) {
			   System.out.println(line);
			  }
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		  try {
			stdout.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  System.out.println("Standard Error:");
		  BufferedReader stderr = new BufferedReader(new InputStreamReader(
		    powerShellProcess.getErrorStream()));
		  try {
			while ((line = stderr.readLine()) != null) {
			   System.out.println(line);
			  }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  try {
			stderr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  System.out.println("Done");
		
		
		
	/*
		Runtime r  = Runtime.getRuntime();
		try {
			r.exec("calc");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}
 }

