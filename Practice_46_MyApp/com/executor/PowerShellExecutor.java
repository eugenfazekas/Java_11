package com.executor;

import com.comands.PowerShellMethods;

public class PowerShellExecutor extends ExecutorFunctionClass{

	public static void stopVLC() {
		
		PowerShellMethods.stopProgram("vlc");
	}
	
	public static void startVLC() {
		
		PowerShellMethods.startProgram("'C:\\Program Files\\VideoLAN\\VLC\\vlc.exe'");
	}
}
