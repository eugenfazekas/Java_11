package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	 ServerSocket serverSocket;
     Socket clientSocket;
     PrintWriter out;
     BufferedReader in;

    public void start(int port) {
    	
    	try {
        serverSocket = new ServerSocket(port);
        System.out.println("Server Started:");
        clientSocket = serverSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String greeting = in.readLine();
        System.out.println("Server greeting: "+greeting);
            if ("hello server".equals(greeting)) {
                out.println("hello client");
            }
            else {
                out.println("unrecognised greeting");
            }
    	}catch(Exception e) {
    		
    	}
    }

    public void stop() {

        try {
			serverSocket.close();
	        in.close();
	        out.close();
	        clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public static void main(String[] args) {
    	Server server=new Server();
        server.start(6666);

    }
}
