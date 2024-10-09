package audio.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import audio.alarm.AlarmService;
import audio.util.FileUtil;

public class AudioServer implements Runnable{
	
	   private static ServerSocket serverSocket;
	   private static DataInputStream in;
	   private static DataOutputStream out;
	   private static Socket clientSocket; 
	   
	   public AudioServer() {
		   new Thread(this).start();
	}

     @Override
	public void run() {

		   try {
				serverSocket = new ServerSocket(8000);
				//serverSocket.setSoTimeout(10000);
				clientSocket = serverSocket.accept();
				
				System.out.println("Just connected to " + clientSocket.getRemoteSocketAddress());
				
				in = new DataInputStream(clientSocket.getInputStream());
	            out = new DataOutputStream(clientSocket.getOutputStream());
	            
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   
		   String msg = "";
		   
	       while(!msg.equals("bye")) {
          	            
	            try {
	            	msg = in.readUTF();
	            	//System.out.println("New Message 1: "+ msg);
	            	if(!msg.equals("") && !msg.equals("bye")) {
	            		System.out.println("New Message 2: "+ msg);
	            		messageHhandler(msg);		  
	            	}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	      }
           try {
	   	        System.out.println("Connection closed to remoteHost:" +clientSocket.getLocalSocketAddress());
		        clientSocket.close();
           } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
           }

	   }
		
	public void messageHhandler(String msg) {

		AlarmService.addToAlarmObjectList(msg);
		FileUtil.addStreamToFile(msg, FileUtil.ALARMS_PATH);
	}
}
