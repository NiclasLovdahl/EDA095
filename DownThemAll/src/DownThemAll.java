import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DownThemAll {

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

			while (matcher.find()) {
				if (matcher.group(1).endsWith(".pdf")) {
					download(matcher.group(1));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void download(String urlString) {
		try {
			String fileName = urlString.substring(
					urlString.lastIndexOf('/') + 1, urlString.lastIndexOf('.'));
			URL url = new URL(urlString);
			InputStream in = url.openStream();
			FileOutputStream fos = new FileOutputStream(new File(fileName));

			System.out.println("reading file: " + fileName);
			int length = -1;
			byte[] buffer = new byte[1024];

			while ((length = in.read(buffer)) > -1) {
				fos.write(buffer, 0, length);
			}

			fos.close();
			in.close();
			System.out.println(fileName + " was downloaded");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
