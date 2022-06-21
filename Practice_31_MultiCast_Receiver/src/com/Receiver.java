package com;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Receiver {
	public static void main(String[] args) {
		byte[] inBuffer = new byte[256];
		try {
		InetAddress address = InetAddress.getByName("224.0.0.1");
		//Create a MulticastSocket
		MulticastSocket socket = new MulticastSocket(8379);
		//Join to the multicast group
		socket.joinGroup(address);
		while (true) {
		DatagramPacket packet = new DatagramPacket(inBuffer, inBuffer.length);
		socket.receive(packet);
		String msg = new String(inBuffer, 0, packet.getLength());
		System.out.println("Received<--" + msg);
		}
		} catch (IOException ioe) {
		System.out.println(ioe);
		}
	}
}
