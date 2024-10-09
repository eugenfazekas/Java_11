package audio.clock;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.time.LocalDateTime;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import audio.alarm.AlarmObject;
import audio.alarm.AlarmService;
import audio.alarm.VoiceSynthesizer;
import audio.util.FileUtil;

public class Clock {

	private static int month;
	private static int day;
	private static String dayOfWeek;
	private static int hour;
	private static int minute;
	private static int hour_24;
	private static int second;
	private static int hour_temp;
	private static int minute_temp;
	private static RawAudio [] rawAudioLib = new RawAudio[2];
	private static VoiceSynthesizer voiceSynthesizer;
	private static boolean synchronizing ;
	private static LocalDateTime timestamp;	
	//private static LocalDateTime timestamp = LocalDateTime.now();

	
	private static void init() {
		
		AudioInputStream stream = buildAudioInputStreamFromFile(FileUtil.HOUR_BELL_PATH);
		rawAudioLib[0] = new RawAudio(buildBuffer(stream),stream.getFormat()); 
		stream = buildAudioInputStreamFromFile(FileUtil.MINUTE_BELL_PATH);
		rawAudioLib[1] = new RawAudio(buildBuffer(stream),stream.getFormat()); 
		voiceSynthesizer = new VoiceSynthesizer();
		synchronizeTime(0);
	}
	
	public static void clock() {

		init();
		
		while(true) {
			
			increaseTime();
			
			checkTimeForBell();
			
			alarmCheck();
	
			sleep(60);			
		}
		
	}
	private static void alarmCheck() {
			
		for(AlarmObject o : AlarmService.alarms) {
			
			if(o.getDay() == day && o.getMonth() == month && o.getHour() == hour_24 && o.getMinute() == minute && !o.isRepeat()) {
				voiceSynthesizer.messages.add(o.getDescription());
				VoiceSynthesizer.bufferIsEmpty = false;
			}
			if(o.isRepeat()) {
				for(String s : o.getDays()) {
					if(s != null && s.equals(dayOfWeek)) {
						for(int i = 0; i < o.getRepeatAlarmsTime().length; i++) {							
							hour_temp = o.getRepeatAlarmsTime()[i][0];
							minute_temp =  o.getRepeatAlarmsTime()[i][1];
							if(hour_temp == hour_24 && minute_temp == minute) {
								voiceSynthesizer.messages.add(o.getDescription());
								VoiceSynthesizer.bufferIsEmpty = false;
								System.out.println("AlarmCheck repeat hour  "+hour_24+ " minute: "+minute+" isEmpty: "+voiceSynthesizer.messages.isEmpty());
							}
						}
						
					}
				}
			} 
		}
	}
	
	private static void checkTimeForBell() {
		
		timestamp = LocalDateTime.now();
		
		System.out.println("checkTimeForBell Hour: "+hour+" minute: "+minute+ " second: "+second );
		
		if(timestamp.getMinute() !=  minute)
			synchronizeTime(1);
		
		if(minute == 13 || minute == 28 || minute == 43|| minute == 58)
			synchronizeTime(1);
		
		if(timestamp.getHour() == hour_24 &&  minute == 0 && (second == 0 || second == 1 ))
			multiPlayer(hour, 0);
		
		if(timestamp.getMinute() == minute && (minute == 15 || minute == 30 || minute == 45)) {
			multiPlayer(hour, minute/15);		
		}
	}
	
	private static void synchronizeTime(int increase) {
		
		timestamp = LocalDateTime.now();
		
		if(timestamp.getHour() != hour_24 || timestamp.getMinute() != minute || ( Math.abs(timestamp.getSecond() - second)> 1)) {
			//System.out.println("synchronizeTime timestamp.toString(): "+(timestamp.getHour() != hour_24)+", "+(timestamp.getMinute() != minute+1)+", "+( Math.abs(timestamp.getSecond() - second)> 1));
			hour_24 = timestamp.getHour();
			hour = timestamp.getHour() > 12 ? timestamp.getHour() - 12 : timestamp.getHour();
			minute = timestamp.getMinute()+increase;
			second = timestamp.getSecond();
			month = timestamp.getMonthValue();
			day = timestamp.getDayOfMonth();
			dayOfWeek = timestamp.getDayOfWeek().toString();
			synchronizing = true;
			System.out.println("Synchronizing now! hour: "+hour+" minute: "+minute +" seconds: "+second);
		}
		if(synchronizing) {
			sleep(60-second);
			second = 0;
			synchronizing = false;
		}
		if(!synchronizing)
			System.out.println("NO need to synchronize Hour: " +hour + ", minute: "+minute +", second: " +second);
	}

	private static void increaseTime() {
		
		if(minute < 60)
			minute++;
		
		if(minute == 60) {
			minute = 0;
			hour++;
			hour_24++;
		}
		
		if(hour == 13)
			hour = 1;

		if(hour_24 == 24) {
			hour_24 = 0;
		}
		System.out.println("increaseTime hour: "+hour+" minute: "+minute +" seconds: "+second + " 24-hour "+ hour_24 );
	}
	
	private static void sleep(int sleepSeconds) {
		
		try {
			Thread.sleep(sleepSeconds * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void multiPlayer(int hour, int quarter ) {

		Player player= null;
		AudioInputStream stream = null;
		
		if(quarter == 0 && hour != 0 ) {
				stream = buildAudioInputStreamFromBuffer(rawAudioLib[0].getData(),rawAudioLib[0].getAudioFormat());
				player = new Player(stream,hour);	
				System.out.println("Fix hour bell! "+hour);
				
		}
		if(quarter != 0) {
			    stream = buildAudioInputStreamFromBuffer(rawAudioLib[1].getData(),rawAudioLib[1].getAudioFormat());
				player = new Player(stream,quarter);
				System.out.println("Quater bell!, Hour: " + hour+ ", minute: "+quarter*15);
		} 
	}
	
   private static AudioInputStream buildAudioInputStreamFromFile(String filePath) {
		
		AudioInputStream audioInputStream = null;

		try {
			 audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return audioInputStream;		
	}
    
    private static byte[] buildBuffer(AudioInputStream inputStream) {
    	
    	byte[] buffer = new byte[(int) inputStream.getFrameLength() * inputStream.getFormat().getFrameSize()];
    	try {
			inputStream.read(buffer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    	
    		return buffer;
    }
    
   private static AudioInputStream buildAudioInputStreamFromBuffer(byte[] buffer, AudioFormat audioFormat) {
    	
    	ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
    	AudioInputStream clonedStream = null ;
    	clonedStream = new AudioInputStream(bais, audioFormat, buffer.length / audioFormat.getFrameSize());
    	
    	audioFormat = null;
    	return clonedStream;
    }   
}
