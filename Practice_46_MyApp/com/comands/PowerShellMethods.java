package com.comands;

import com.executor.ExecutorFunctionClass;
import com.util.StaticValues;

public class PowerShellMethods extends ExecutorFunctionClass {

	public static void stopProgram(String programeName) {
		
		CmdCommandLine.runCmdCommand("powerShell", StaticValues.powerShellPath+"StopProgram.ps1 " +programeName);
	}
	
	public static void startProgram(String programeName) {
		
		CmdCommandLine.runCmdCommand("powerShell", StaticValues.powerShellPath+"StartProgram.ps1  " +programeName);
	}
}
