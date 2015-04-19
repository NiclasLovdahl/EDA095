import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoServerTCP3 {

	public EchoServerTCP3() {

	}

	@SuppressWarnings("resource")
	public void run() {
		Mailbox m = new Mailbox();
		ExecutorService service = Executors.newFixedThreadPool(10);

		PrintThread pt = new PrintThread(m);
		pt.start();

		for (int i = 0; i < 10; i++) {
			MailboxThread mt = new MailboxThread(m, Integer.toString(i));
			service.submit(mt);
		}

	}

	public static void main(String[] args) {
		EchoServerTCP3 server = new EchoServerTCP3();
		server.run();
	}
}
