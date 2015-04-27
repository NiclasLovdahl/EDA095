import java.net.*;
import java.util.Scanner;
import java.io.*;

public class SendUDP2 {

	public SendUDP2() {

	}

	public void run() {
		try {
			String s = discoverServer();

			int i = 0;
			while (s.charAt(i) != '|') {
				i++;
			}

			String address = s.substring(0, i);
			int port = Integer.parseInt(s.substring(i + 1));

			System.out.print("Date/time: ");
			Scanner scan = new Scanner(System.in);

			DatagramSocket ds = new DatagramSocket();
			byte[] buf3 = scan.nextLine().getBytes();
			DatagramPacket dp3 = new DatagramPacket(buf3, buf3.length,
					InetAddress.getByName(address), port);
			ds.send(dp3);

			byte[] buf4 = new byte[65536];
			DatagramPacket dp4 = new DatagramPacket(buf4, buf4.length);
			ds.receive(dp4);

			String s2 = new String(dp4.getData(), 0, dp4.getLength());

			System.out.println(s2);

			ds.close();
			scan.close();

		} catch (IOException e) {
			System.out.println("Exception:" + e);
		}
	}

	private String discoverServer() {
		String s = "";
		try {
			MulticastSocket ms = new MulticastSocket();
			ms.setTimeToLive(1);
			InetAddress ia = InetAddress.getByName("experiment.mcast.net");

			byte[] buf = "DISCOVER".getBytes();
			DatagramPacket dp = new DatagramPacket(buf, buf.length, ia, 4099);
			ms.send(dp);

			byte[] buf2 = new byte[65536];
			DatagramPacket dp2 = new DatagramPacket(buf2, buf2.length);
			ms.receive(dp2);
			s = new String(dp2.getData(), 0, dp2.getLength());
			ms.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return s;
	}

	public static void main(String args[]) {
		SendUDP2 sudp = new SendUDP2();
		sudp.run();
	}
}