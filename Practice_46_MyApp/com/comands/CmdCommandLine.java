package com.comands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.util.Debug;

public class CmdCommandLine {
	
	public static int debugLevelInfo = 1;
	public static int debugLeveDebug = 1;

	public static List<String> runCmdCommand(String programName, String script) {

		//String command = "powershell.exe  your command";
		//Getting the version
		List<String> response = new ArrayList<String>();
		  String command = programName+ " " + script;
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
		  Debug.debug(debugLevelInfo,"Standard Output:");
		  BufferedReader stdout = new BufferedReader(new InputStreamReader(
		    powerShellProcess.getInputStream()));
		  try {
			while ((line = stdout.readLine()) != null)  {
	            if(!line.equals("")) {
					response.add(line);
					Debug.debug(debugLeveDebug,line);
			   }
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
		  BufferedReader stderr = new BufferedReader(new InputStreamReader(
		    powerShellProcess.getErrorStream()));
		  try {
			while ((line = stderr.readLine()) != null) {
				Debug.debug(debugLeveDebug,"Error Message line: "+line);
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
		  Debug.debug(debugLevelInfo,"Done");
			
		return response;
	}
}
