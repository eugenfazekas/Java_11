package audio;

import audio.alarm.AlarmService;
import audio.clock.Clock;
import audio.socket.AudioServer;
import audio.util.FileUtil;

public class Main {
		
	public static void main(String[] args) {

		AlarmService.buildAlarmObjectList(FileUtil.ALARMS_PATH);
		//AlarmService.setClockBellVolume(FileUtil.CLOCK_VOLUME_PATH);
		AudioServer server = new AudioServer();
		Clock.clock();

	}     
}


