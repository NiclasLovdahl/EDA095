public class MailboxThread extends Thread {

	Mailbox m;
	String name;

	public MailboxThread(Mailbox m, String name) {
		this.m = m;
		this.name = name;
	}

	@Override
	public void run() {
		for (int i = 0; i < 5; i++) {
			m.put(name);
			try {
				Thread.sleep((long) 500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
