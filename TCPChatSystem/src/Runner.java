import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

public class Runner extends Thread {

	Socket socket;
	Participants participants;

	public Runner(Socket s, Participants participants) {
		this.socket = s;
		this.participants = participants;
	}

	@Override
	public void run() {
		try {
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();
			while (true) {
				int length = -1;
				byte[] buffer = new byte[1024];
				String s = "";

				while ((length = is.read(buffer)) > -1) {
					byte[] messageArray = Arrays.copyOfRange(buffer, 0, length);
					s = new String(messageArray, "UTF-8");

					String command = s.substring(0, 3);

					if (command.equals("M: ")) {
						participants.broadcast(s.substring(3));
					} else if (command.equals("E: ")) {
						os.write(s.substring(3).getBytes());
					} else if (command.charAt(0) == 'Q') {
						participants.removeSocket(socket);
						socket.close();
						break;
					} else {
						os.write("Unknown command. \n".getBytes());
					}
					s = "";
				}

			}
		} catch (Exception e) {
			System.out.println("Connection closed.");
		}
		try {
			socket.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
