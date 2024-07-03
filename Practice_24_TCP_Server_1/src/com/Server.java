package com;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
public class Server {
	public static void main(String argv[]) throws Exception {
		
		//create a server socket at port 6789
		ServerSocket welcomeSocket = new ServerSocket(6789);
		System.out.println("Server ready");
		
			while (true) {
				
				//wait for incoming connection
				Socket serverEnd = welcomeSocket.accept();
				System.out.println("Request accepted");
				
				//hand over this connection request to Handler
				new Handler(serverEnd);
			}
		}
	}

class Handler implements Runnable {
	
	Socket serverEnd;
	
	Handler(Socket s) {
		this.serverEnd = s;
		new Thread(this).start();
		System.out.println("A thread created");
	}
	
	public void run() {
		
		try {
			//get streams
			BufferedReader fromClient = new BufferedReader(new InputStreamReader(serverEnd.getInputStream()));
			PrintWriter toClient = new PrintWriter(serverEnd.getOutputStream(), true);
			
			while (true) {	
				//receive data from client
				int n = Integer.parseInt(fromClient.readLine());
				System.out.println("Received " + n);
				
				if (n == -1) {
					serverEnd.close();
					break;
				}
				
				int fact = 1;
				for (int i = 2; i <= n; i++)
				fact *= i;
				
				//send result to the client
				toClient.println(fact);
				System.out.println("Sent: " + fact);
			}
		} catch (IOException e) { }
	}
}