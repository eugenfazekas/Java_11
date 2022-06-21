package com;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TCPClient {

	public static void main(String[] args) throws Exception {
		//create a socket to the server
		Socket socket = new Socket("localhost", 6789);
		System.out.println("Connected to localhost at port 6789");
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		String classFile = "com//Job.class";
		out.writeObject(classFile);
		FileInputStream fis = new FileInputStream(classFile);
		byte[] b = new byte[fis.available()];
		fis.read(b);
		out.writeObject(b);
		Job aJob = new Job();
		out.writeObject(aJob);
		System.out.println("Submitted a job for execution");
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		classFile = (String)in.readObject();
		b = (byte[])in.readObject();
		FileOutputStream fos = new FileOutputStream(classFile);
		fos.write(b);
		Result r = (Result)in.readObject();
		System.out.println("result = "+r.output()+", time taken ="+r.completionTime()+" ns");
		socket.close();
	}

}
