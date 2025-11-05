package audio.alarm;

import java.util.Arrays;


public class AlarmObject {

	private int id;
	private String name;
	private String description;
	private String date;
	private String time;
	private boolean repeat;
	private String [] days;
	private String  repeatHours;
	private String  repeatMinutes;
    private int [][] repeatAlarmsTime; 
	private int hour;
	private int minute;
	private int year;
	private int month;
	private int day;
	private static String[] weekDays = new String[] {"MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY","SUNDAY"};	
	
	public AlarmObject() {}

	
	public AlarmObject(String id, String name, String description, String date, String time, String repeat, String days,
			String repeatHours, String repeatMinutes) {
		this.id = Integer.valueOf(id);
		this.name = name;
		this.description = description;
		this.date = date;
		setDate(date);
		this.time = time;
		setTime(time);
		setDays(days);
		this.repeat = repeat.equals("true") ? true : false;
		
		if(this.repeat == true) {
			this.repeatHours = repeatHours == null ? repeatHours : null;
			this.repeatMinutes = repeatMinutes == null ? repeatMinutes : null;
			System.out.println("name "+name+" repeatHours "+ repeatHours +" repeatMinutes: "+repeatMinutes);
			addRepeatHours(repeatHours);
			addRepeatMinutes(repeatMinutes);
		}
	}
	
	public void setRepeatedFeatures() {
		

	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {System.out.println("id: "+id);
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		String dateArray[] = date.split("\\.");
		setDay(Integer.valueOf(dateArray[0]));
		setMonth(Integer.valueOf(dateArray[1]));
		setYear(Integer.valueOf(dateArray[2]));
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		String timeArray[] = time.split("\\.");
		setHour(Integer.valueOf(timeArray[0]));
		setMinute(Integer.valueOf(timeArray[1]));
		this.time = time;
	}

	public String[] getDays() {
		return days;
	}

	public void setDays(String daysToCheck) {

		Character ch = null; 
		days = new String[7];
			for(int i = 0; i < 7; i++) {
				ch = daysToCheck.charAt(i);
				if (!ch.equals(Character.valueOf('0'))) {
					days[i] = weekDays[i];
					//System.out.println("alarmObjects[5].charAt(i) "+ch + " eqauals: "  + (ch !=0));
				}	
		}
	}

	public boolean isRepeat() {
		return repeat;
	}

	public void setRepeat(boolean repeat) {
		this.repeat = repeat;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}
	
	public int[][] getRepeatAlarmsTime() {
		return repeatAlarmsTime;
	}

	private void addToAlarms(int hour, int minute, String type) {
		
		int[][] newArray = null;
		
		if(repeatAlarmsTime == null) {
			repeatAlarmsTime = new int[1][];
			repeatAlarmsTime[0] = new int[] {hour,minute};
			System.out.println("AlarmObject.class addToAlarms 0: "+hour+" h: "+minute+ " min, Type: "+type);
		}
		
		else {
			newArray = new int[repeatAlarmsTime.length+1][];
			for(int i = 0; i < repeatAlarmsTime.length; i++) {
				newArray[i] = repeatAlarmsTime[i];
			}
			newArray[repeatAlarmsTime.length] = new int[] {hour,minute};
			repeatAlarmsTime = newArray;
			System.out.println("AlarmObject.class addToAlarms: "+hour+" h: "+minute+ " min, Type: "+type);
		}
	}
	
	private void addRepeatHours(String repeatHours) {
		
		if(repeatHours == null) {
			throw new NullPointerException("RepeatHours is NULL");  
		}	
		
		String[]  params =  repeatHours.split("\\.");
		int baseHour = hour;
		int increaseHour = Integer.valueOf(params[1]);

		for(int i = 0; i < Integer.valueOf(params[0]); i++) {

			if(baseHour>=24) {
				baseHour = baseHour - 24;	
				addToAlarms(baseHour,minute,"Hour");
				continue;
			}

			addToAlarms(baseHour,minute,"Hour");
			//System.out.println("addRepeatHours: "+baseHour+" h: "+minute+ " min");
			
			baseHour = + increaseHour+baseHour;
		}
	}
	
	private void addRepeatMinutes(String repeatMinutes) {
		
		if(repeatMinutes == null)
			throw new NullPointerException("RepeatMinutes is NULL");
		
		String[]  params =  repeatMinutes.split("\\.");
		int alarmHour =0;
		int alarmMinute = minute;
		int repeatAlarmsTimelength = repeatAlarmsTime.length;
		
		for(int i = 0; i < repeatAlarmsTimelength; i++) {
			
			alarmHour = repeatAlarmsTime[i][0];
			alarmMinute = minute;
			
			for(int j = 0; j < Integer.valueOf(params[0]); j++) {

				if(alarmMinute+Integer.valueOf(params[1]) >=60) {
						alarmMinute = alarmMinute + Integer.valueOf(params[1]) -60;
						alarmHour = alarmHour+1;
						addToAlarms(alarmHour,alarmMinute,"Minute");
						System.out.println("addRepeatMinutes 1: "+alarmHour+":"+alarmMinute);
						continue;
					} ;
				alarmMinute = alarmMinute +Integer.valueOf(params[1]); 
				addToAlarms(alarmHour,alarmMinute,"Minute");
				//System.out.println("addRepeatMinutes: "+alarmHour+" h: "+alarmMinute+ " min");
				}
			}
		}


	@Override
	public String toString() {
		return "AlarmObject [id=" + id + ", name=" + name + ", description=" + description + ", date=" + date
				+ ", time=" + time + ", repeat=" + repeat + ", days=" + Arrays.toString(days) + ", repeatHours="
				+ repeatHours + ", repeatMinutes=" + repeatMinutes + ", hour=" + hour + ", minute=" + minute + ", year=" + year
				+ ", month=" + month + ", day=" + day + "]";
	}			
}
