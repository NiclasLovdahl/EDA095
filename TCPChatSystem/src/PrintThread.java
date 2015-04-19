public class PrintThread extends Thread {

	Mailbox m;

	public PrintThread(Mailbox m) {
		this.m = m;
	}

	public void run() {
		while (true) {
			System.out.println(m.pop());
		}
	}

}
