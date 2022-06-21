package com;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;
public class Sender {
	public static void main(String[] args) {
		long score = 0, run;
		Random r = new Random();
		try {
		int port = 8379;
		InetAddress group = InetAddress.getByName(args[0]);
		//Create a DatagramSocket
		DatagramSocket socket = new DatagramSocket();
		while (true) {
		//Fill the buffer with score generated artificially
		do {
		Thread.sleep(1000+r.nextInt(1000));
		}while((run = r.nextInt(7)) == 0);
		score += run;
		String msg = "score: " + score;
		byte[] out = msg.getBytes();
		//Create a DatagramPacket
		DatagramPacket pkt = new DatagramPacket(out, out.length, group, port);
		//Send the pkt
		socket.send(pkt);
		System.out.println("Sent-->" + msg);
		}
		} catch (Exception e) {
		e.printStackTrace();
		}
	}
}
