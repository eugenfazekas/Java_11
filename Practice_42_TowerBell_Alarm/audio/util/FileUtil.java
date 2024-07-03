package audio.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class FileUtil {

	public final static String ALARMS_PATH = "alarm.txt";
	public final static String HOUR_BELL_PATH = "resources/audiomass1.wav";
	public final static String MINUTE_BELL_PATH = "resources/audiomass2.wav";


	public static List<String>  buildStringLines(FileInputStream stream) {

		List<String> lines = new ArrayList<>();
		StringBuilder line = new StringBuilder();
	    int ch; 
		        try {
					while ((ch = stream.read()) != -1) {
						if(ch != 10 && ch!=13) {
							line.append((char)ch);
						}
						if((line.length() == 0 && ch == 13))
							break;
						
						if(ch == 13) {
							lines.add(line.toString());
							line = new StringBuilder();
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		        try {
					stream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 

		System.out.println("Get 0: "+lines.get(0));
		return lines;
	}
	
	public static FileInputStream buildFileStreamFromFile(String filePath) {
		
		FileInputStream fin = null;
		
		try {
			fin = new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
			return fin;
	}
	
	public static void addStreamToFile( String addLine, String filePath) {
		
		  FileInputStream inputStream = buildFileStreamFromFile(filePath);
		  FileOutputStream out = null;
		  String newLine = "\r\n";
		  
			try {
				byte[] targetArray = new byte[inputStream.available()];			  
				inputStream.read(targetArray);
				out = new FileOutputStream(filePath);
				out.write(targetArray);
				out.write(addLine.getBytes());
				out.write(newLine.getBytes());
				out.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("FileUtil addStreamToFile addedLine: "+addLine + " to Path: "+filePath);
	}

}
