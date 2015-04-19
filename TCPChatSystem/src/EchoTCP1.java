import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoTCP1 {

	public EchoTCP1() {

	}

	@SuppressWarnings("resource")
	public void run() {
		ServerSocket server = null;

		try {
			server = new ServerSocket(30000);
		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		}
		while (true) {
			Socket socket = null;
			try {
				socket = server.accept();
			} catch (IOException e) {
				System.out.println(e);
				System.exit(1);
			}
			try {
				System.out.println("New connection from "
						+ socket.getInetAddress().getHostName() + ":"
						+ socket.getPort() + ". Local port: "
						+ socket.getLocalPort());
				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();

				while (true) {
					int length = -1;
					byte[] buffer = new byte[1024];

					while ((length = is.read(buffer)) > -1) {
						System.out.print(new String(buffer, "UTF-8"));
						os.write(buffer, 0, length);
					}
				}

			} catch (IOException e) {
				System.out.println(e);
			}
			try {
				socket.close();
			} catch (IOException e) {
				System.out.println(e);
			}
		}
	}

	public static void main(String[] args) {
		EchoTCP1 server = new EchoTCP1();
		server.run();
	}
}
