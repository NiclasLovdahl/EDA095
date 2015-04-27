import java.net.*;
import java.io.*;

public class MCServerOffer extends Thread {

	int port;
	int msPort;

	public MCServerOffer(int port, int msPort) {
		this.port = port;
		this.msPort = msPort;
	}

	public void run() {
		try {
			MulticastSocket ms = new MulticastSocket(msPort);
			InetAddress ia = InetAddress.getByName("experiment.mcast.net");
			ms.joinGroup(ia);

			InetAddress ia2 = InetAddress.getLocalHost();

			while (true) {
				byte[] buf = new byte[65536];
				DatagramPacket dp = new DatagramPacket(buf, buf.length);
				ms.receive(dp);
				String s = new String(dp.getData(), 0, dp.getLength());
				System.out.println("Received: " + s);

				DatagramSocket ds = new DatagramSocket();
				String address = ia2.getHostAddress() + "|" + port;
				byte[] data2 = new byte[65507];
				data2 = address.getBytes();
				DatagramPacket dp2 = new DatagramPacket(data2, data2.length,
						dp.getAddress(), dp.getPort());

				ds.send(dp2);
			}

		} catch (IOException e) {
			System.out.println("Exception:" + e);
		}
	}

	public static void main(String args[]) {
		MCServerOffer so = new MCServerOffer(30000, 4099);
		MCServerOffer so2 = new MCServerOffer(30000, 4055);
		TimeServerUDP tsu = new TimeServerUDP(30000);
		tsu.start();
		so.start();
		so2.start();
	}
}