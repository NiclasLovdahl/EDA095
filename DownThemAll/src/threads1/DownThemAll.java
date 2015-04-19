package threads1;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DownThemAll {

	Stack<String> urls;

	public DownThemAll() {

	}

	public void run() {
		try {
			System.out.print("Input URL:");
			Scanner scan = new Scanner(System.in);
			URL url = new URL(scan.next());
			scan.close();
			InputStream is = url.openStream();
			BufferedReader bReader = new BufferedReader(new InputStreamReader(
					is));
			StringBuilder sb = new StringBuilder();

			String input;
			while ((input = bReader.readLine()) != null) {
				sb.append(input + "\n");
			}

			Pattern pattern = Pattern.compile("href=" + "\"" + "(.*?)" + "\"");
			Matcher matcher = pattern.matcher(sb.toString());

			// Runner runner;

			urls = new Stack<String>();

			while (matcher.find()) {
				if (matcher.group(1).endsWith(".pdf")) {

					urls.push(matcher.group(1));

					// runner = new Runner(matcher.group(1));
					// runner.run();
				}
			}

			ExecutorService service = Executors.newFixedThreadPool(10);

			for (int i = 0; i < 10; i++) {
				Runner runner = new Runner(this);
				service.execute(runner);
			}

			service.shutdown();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String popUrl() {
		return urls.pop();
	}

	public boolean stackEmpty() {
		if (urls.empty()) {
			return true;
		}
		return false;
	}

}
