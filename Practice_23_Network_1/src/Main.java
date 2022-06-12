import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Enumeration;

public class Main {

	public static void main(String[] args) throws Exception {
		Enumeration<NetworkInterface> intfs =
		NetworkInterface.getNetworkInterfaces();
		while(intfs.hasMoreElements()) {
		NetworkInterface intf = intfs.nextElement();
		System.out.println("\nInterface: "+intf.getName());
		System.out.println("Display name: "+intf.getDisplayName());
		Enumeration<NetworkInterface> subIfs = intf.getSubInterfaces();
		for (NetworkInterface subIf : Collections.list(subIfs)) {
		System.out.printf("\tSub Interface : "+subIf.getName());
		System.out.printf("\tSub Interface Displayname :"+subIf.getDisplayName());
			}
		}
		/*  Specify details about the interface entered by argument
		 * 
		NetworkInterface intf = NetworkInterface.getByName(args[0]);
		System.out.println("\nName : "+intf.getName());
		System.out.println("Display name : "+intf.getDisplayName());
		System.out.println("Up : "+ intf.isUp());
		System.out.println("Loopback : "+ intf.isLoopback());
		System.out.println("PointToPoint : "+intf.isPointToPoint());
		System.out.println("Supports multicast :"+intf.supportsMulticast());
		System.out.println("Virtual : "+intf.isVirtual());
		byte[] mac1 = intf.getHardwareAddress();
		if(mac1 != null) {
		System.out.print("Hardware Address : ");
		for (int k = 0; k < mac1.length; k++)
		System.out.format("%02X%s", mac1[k], (k < mac1.length - 1) ? "-" : "");
		System.out.println();
		}
		System.out.println("MTU :"+intf.getMTU());
		*/
	}
}
