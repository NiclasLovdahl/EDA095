

public class NameRunner extends Thread {

	int name;

	public NameRunner(int name) {
		this.name = name;
	}

	@Override
	public void run() {
		for (int i = 0; i <= 5; i++) {
			try {
				Thread.sleep((long) Math.random() * 500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(name);
		}
	}

}
