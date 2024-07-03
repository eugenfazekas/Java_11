package com;
import java.io.*;
import java.net.*;
public class Client {
public static void main(String argv[]) throws Exception {

	String fact;
	
	//create a socket to the server
	Socket clientEnd = new Socket("localhost", 6789);
	
	System.out.println("connected to localhost at port 6789");
	
	//get streams
	PrintWriter toServer = new PrintWriter(clientEnd.getOutputStream(), true);
	
	BufferedReader fromServer = new BufferedReader(new InputStreamReader(clientEnd.getInputStream()));
	
	BufferedReader fromUser = new BufferedReader(new InputStreamReader(System.in));
	
	while (true) {
		
		//get an integer from user
		System.out.print("Enter an integer: ");
		String n = fromUser.readLine();
		
		//send it to server
		toServer.println(n);
		System.out.println("Sent to server: " + n);
		
		if (n.equals("-1"))
			break;
		
		//retrieve result
		fact = fromServer.readLine();
		System.out.println("Received from server: " + fact);
	}
	
	//close the socket
	clientEnd.close();
		}
	}

