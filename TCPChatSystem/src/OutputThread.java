import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class OutputThread extends Thread {

	Socket socket;

	public OutputThread(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		OutputStream os;
		try {
			os = socket.getOutputStream();
		} catch (IOException e1) {
			e1.printStackTrace();
			return;
		}
		while (true) {
			System.out.print("Command: ");
			Scanner scan = new Scanner(System.in);
			try {
				String s = scan.nextLine();
				os.write(s.getBytes());
				if (s.equals("Q")) {

					break;
				}

			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
