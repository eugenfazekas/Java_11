package com;

import java.io.ObjectOutputStream;
import java.net.Socket;

public class Sender {
	public static void main(String argv[]) throws Exception {
	String fact;
	//create a socket to the server
	Socket socket = new Socket("172.16.5.81", 6789);
	System.out.println("Connected to localhost at port 6789");
	//Create a Message object to be sent
	Message msg = new Message("Remainder","Return my book on Monday");
	//Create an ObjectOutputStream object
	ObjectOutputStream oos = new
	ObjectOutputStream(socket.getOutputStream());
	//Serialize and send over TCP
	oos.writeObject(msg);
	System.out.println("Sent an object");
	socket.close();
}
}
