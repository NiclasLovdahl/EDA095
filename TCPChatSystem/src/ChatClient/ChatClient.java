package ChatClient;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {

	public static void main(String[] args) {
		// if (args.length != 2) {
		// System.out.println("Syntax: java ChatClient <address> <port>");
		// System.exit(1);
		// }
		// int port = Integer.parseInt(args[1]);
		Socket socket = null;
		InputStream is = null;
		OutputStream os = null;
		try {
			socket = new Socket("localhost", 30000);
			is = socket.getInputStream();
			os = socket.getOutputStream();
		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		}
		InputThread it = new InputThread(is);
		OutputThread ot = new OutputThread(os);
		it.start();
		ot.start();
	}
}