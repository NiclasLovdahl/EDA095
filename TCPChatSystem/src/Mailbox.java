public class Mailbox {

	String message = "";

	public Mailbox() {

	}

	public synchronized void put(String message) {
		this.message += message;
		notifyAll();
	}

	public synchronized String pop() {
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String s = message;
		message = "";
		return s;
	}

}
