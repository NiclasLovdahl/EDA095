import java.net.*;
import java.io.*;

public class SendUDP {

	public static void main(String[] args) {
		if (args.length != 3) {
			System.out
					.println("Syntax: java Sender <destination> <port> <message>");
			System.exit(1);
		}

		DatagramSocket socket = null;
		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			System.out.println("Could not create socket!");
			System.exit(1);
		}

		byte[] data = args[2].getBytes();

		InetAddress dest = null;
		int port = Integer.parseInt(args[1]);
		try {
			dest = InetAddress.getByName(args[0]);
		} catch (UnknownHostException e) {
			System.out.println("Unknown host: " + args[0]);
			System.exit(1);
		}

		DatagramPacket dp = new DatagramPacket(data, data.length, dest, port);

		try {
			socket.send(dp);
		} catch (IOException e) {
			System.out.println("An IOException occured: " + e);
			System.exit(1);
		}

		byte[] data2 = new byte[65507]; // We are conservative...
		DatagramPacket dp2 = new DatagramPacket(data2, data2.length);

		try {
			socket.receive(dp2);
		} catch (IOException e) {
			System.out.println("An IOException occured: " + e);
			System.exit(1);
		}

		String message = new String(dp2.getData(), 0, dp2.getLength());

		System.out.println(message);

		socket.close();
	}

}