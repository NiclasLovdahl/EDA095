
import java.io.InputStream;
import java.util.Arrays;

public class InputThread extends Thread {

	InputStream is;

	public InputThread(InputStream is) {
		this.is = is;
	}

	public void run() {
		while (true) {
			int length = -1;
			byte[] buffer = new byte[1024];
			String s = "";
			try {
				while ((length = is.read(buffer)) > -1) {
					s = new String(buffer, "UTF-8");
					if (s.equals("Q")) {
						return;
					}
					System.out.println("\n" + "Recieved message: " + s);
					System.out.println("Command: ");
					Arrays.fill(buffer, (byte) 0);
					s = "";
				}
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
		}
	}
}
