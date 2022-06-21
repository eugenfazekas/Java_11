package com;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

	public static void main(String[] args) throws IOException, Exception {
		// create a server socket at port 6789
		ServerSocket serverSocket = new ServerSocket(6789);
		// wait for incoming connection
		System.out.println("Server is listening on port 6789");
		Socket socket = serverSocket.accept();
		System.out.println("Request accepted");
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		String classFile = (String) in.readObject();
		byte[] b = (byte[]) in.readObject();
		FileOutputStream fos = new FileOutputStream(classFile);
		fos.write(b);
		Executable ex = (Executable) in.readObject();
		System.out.print("Starting execution...");
		double startTime = System.nanoTime();
		String output = ex.execute();
		double endTime = System.nanoTime();
		double completionTime = endTime - startTime;
		System.out.println("[ DONE ]");
		ResultImpl r = new ResultImpl(output, completionTime);
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		classFile = "com//ResultImpl.class";
		out.writeObject(classFile);
		FileInputStream fis = new FileInputStream(classFile);
		byte[] bo = new byte[fis.available()];
		fis.read(bo);
		out.writeObject(bo);
		out.writeObject(r);
		System.out.print("Result sent");
		socket.close();
	}

}
