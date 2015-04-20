import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebCrawler {
	LinkedList<String> remainingURLs = new LinkedList<String>();
	HashSet<String> traversedURLs;
	HashSet<String> list2;
	HashSet<String> mails;
	int i = 0;

	public WebCrawler() {
		remainingURLs = new LinkedList<String>();
		list2 = new HashSet<String>();
		mails = new HashSet<String>();
		traversedURLs = new HashSet<String>();
	}

	public synchronized String popURL() {
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String s = remainingURLs.removeFirst();
		if (!traversedURLs.contains(s)) {
			traversedURLs.add(s);
			return s;
		}
		return null;
	}

	public String parseBase(String URL) {
		String s = URL.substring(7);
		int length = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '/') {
				length = i;
				break;
			}
		}
		return "http://" + s.substring(0, length + 1);
	}

	public synchronized boolean terminate() {
		if (list2.size() >= 500) {
			printStats();
			return true;
		} else {
			return false;
		}
	}

	public synchronized void putURL(String url) {
		list2.add(url);
	}

	public synchronized void putMail(String email) {
		mails.add(email);
	}

	public synchronized void queueURL(String url) {
		remainingURLs.add(url);
		notifyAll();
	}

	public synchronized void printStats() {
		if (i == 0) {
			for (String s : mails) {
				System.out.println(s);
			}
			for (String s : list2) {
				System.out.println(s);
			}
			i++;
		}
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) throws IOException {
		WebCrawler wc = new WebCrawler();
		URL url = new URL("http://cs.lth.se/eda095/");

		ExecutorService service = Executors.newFixedThreadPool(10);

		for (int i = 0; i < 10; i++) {
			Processor p = new Processor(url, wc);
			service.submit(p);
		}
		wc.queueURL(url.toString());

		service.shutdown();
	}
}