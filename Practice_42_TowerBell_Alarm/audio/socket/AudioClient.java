package audio.socket;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class AudioClient {
	
	
		// initialize socket and input output streams
		private Socket socket;
		private DataOutputStream out = null;
		BufferedReader terminal;
		// constructor to put ip address and port
		public AudioClient(String address, int port) throws Exception {
				// establish a connection
				try{
					socket = new Socket(address, port);
					System.out.println("Connected");
					
					// takes input from terminal
					terminal = new BufferedReader(new InputStreamReader(System.in));
					System.out.println("In ready");
					
					// sends output to the socket
			        OutputStream outToServer = socket.getOutputStream();
			        out = new DataOutputStream(outToServer);
				}
				catch(UnknownHostException u) {
					System.out.println(u);
				}
				catch(IOException i) {
					System.out.println(i);
				}// string to read message from input
				
				String line = "";
				
				// keep reading until "Over" is input
				while (!line.equals("bye")) {
						line = terminal.readLine();
						out.writeUTF(line);
				}	
				// close the connection
					try {
						System.out.println("Closing connection to: "+socket.getRemoteSocketAddress());
						terminal.close();
						out.close();
						socket.close();
					} catch(IOException i) {
						System.out.println(i);
					}
			}
		
			public static void main(String args[]) throws Exception {
				AudioClient client = new AudioClient("localhost", 6789);
			}
		}