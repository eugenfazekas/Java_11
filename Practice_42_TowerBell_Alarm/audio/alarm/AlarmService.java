package audio.alarm;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import audio.util.FileUtil;

public class AlarmService {
	
	public static List<AlarmObject> alarms = new ArrayList<>();
	
	private static AlarmObject buildAlarmObject(String alarmString) {

		String alarmObjects[] = alarmString.split(",");
		AlarmObject alarm = new AlarmObject(
				alarmObjects[0],alarmObjects[1],alarmObjects[2],alarmObjects[3],alarmObjects[4],
				alarmObjects[5],alarmObjects[6],alarmObjects[7],alarmObjects[8]
											);

			System.out.println("New Alarm Object added: "+alarm.toString());
		return alarm;
	}
	
	private static List<AlarmObject> buildAlarmListFromStrings(List<String> list) {
		
		List<AlarmObject> alarms = new ArrayList<>();
		
		for(int i = 1; i <  list.size(); i++ ) {
			System.out.println("Alarm: "+list.get(i).toString());
				alarms.add(buildAlarmObject(list.get(i)));
		}
		
		return alarms;
	}

	public static void buildAlarmObjectList(String path) {
		
		FileInputStream stream = FileUtil.buildFileStreamFromFile(path);
		List<String> alarmList = FileUtil.buildStringLines(stream);
		List<AlarmObject> alarmsFromStrings = buildAlarmListFromStrings(alarmList);
		
			 alarms = alarmsFromStrings;
	}
	
	public static void addToAlarmObjectList(String alarmFromClient) {
		alarms.add(buildAlarmObject(alarmFromClient));
	}
}
