

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TenThreads {

	public TenThreads() {

	}

	public static void main(String[] args) {
		ExecutorService service = Executors.newFixedThreadPool(10);

		for (int i = 0; i <= 10; i++) {
			NameRunner runner = new NameRunner(i);
			service.submit(runner);
		}
	}

}
