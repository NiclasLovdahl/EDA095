import java.net.*;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.io.*;

public class TimeServerUDP {

	int port;

	public TimeServerUDP(int port) {
		this.port = port;
	}

	public void run() {
		DatagramSocket socket = null;
		try {
			socket = new DatagramSocket(port);
		} catch (SocketException e) {
			System.out.println("Could not create socket!");
			System.exit(1);
		}

		byte[] data = new byte[65507]; // We are conservative...
		DatagramPacket dp = new DatagramPacket(data, data.length);

		while (true) {
			try {
				socket.receive(dp);
			} catch (IOException e) {
				System.out.println("An IOException occured: " + e);
				System.exit(1);
			}

			String message = new String(dp.getData(), 0, dp.getLength());
			System.out.println("Data: " + message + " Length: "
					+ dp.getLength() + " Address: " + dp.getAddress()
					+ " Port: " + dp.getPort());

			Date date = new Date();
			String dateString = "";
			Locale loc = new Locale("swe");

			switch (message) {
			case "date":
				dateString = DateFormat.getDateInstance(1, loc).format(date);
				break;
			case "time":
				dateString = DateFormat.getTimeInstance(1, loc).format(date);
				break;
			case "quit":
				return;
			default:
				dateString = "Invalid command.";
			}

			byte[] data2 = new byte[65507]; // We are conservative...
			data2 = dateString.getBytes();
			DatagramPacket dp2 = new DatagramPacket(data2, data2.length,
					dp.getAddress(), dp.getPort());

			try {
				socket.send(dp2);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// socket.close();
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Syntax: java receiver <port>");
			System.exit(1);
		}

		int port = Integer.parseInt(args[0]);

		TimeServerUDP server = new TimeServerUDP(port);
		server.run();

	}

}