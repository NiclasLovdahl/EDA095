import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoServerTCP2 {

	public EchoServerTCP2() {

	}

	@SuppressWarnings("resource")
	public void run() {
		ServerSocket server = null;
		ExecutorService service = Executors.newFixedThreadPool(10);
		Participants participants = new Participants();

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
			System.out.println("New connection from "
					+ socket.getInetAddress().getHostName() + ":"
					+ socket.getPort() + ". Local port: "
					+ socket.getLocalPort());

			participants.addSocket(socket);
			Runner runner = new Runner(socket, participants);
			service.submit(runner);
		}
	}

	public static void main(String[] args) {
		EchoServerTCP2 server = new EchoServerTCP2();
		server.run();
	}
}
