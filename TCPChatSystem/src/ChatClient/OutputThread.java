package ChatClient;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

public class OutputThread extends Thread {

	OutputStream os;

	public OutputThread(OutputStream os) {
		this.os = os;
	}

	public void run() {
		while (true) {
			System.out.print("Command: ");
			Scanner scan = new Scanner(System.in);
			try {
				os.write(scan.nextLine().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
