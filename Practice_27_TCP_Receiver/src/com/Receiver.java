package com;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Receiver {
	public static void main(String argv[]) throws Exception {
	//create a server socket at port 6789
	ServerSocket serverSocket = new ServerSocket(6789);
	//wait for incoming connection
	System.out.println("Server is listening on port 6789");
	Socket socket = serverSocket.accept();
	System.out.println("Request accepted");
	//Create an ObjectInputStream Object
	ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
	//Restore the object
	Message msg = (Message) in.readObject();
	//Print the mesaage
	System.out.println("Received a message:");
	System.out.println("subject : " + msg.getSubject()+"\nbody :"+msg.getText());
	}
}