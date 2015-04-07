package threads1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

public class Runner implements Runnable {

	DownThemAll dta;

	public Runner(DownThemAll dta) {
		this.dta = dta;
	}

	@Override
	public void run() {

		String urlString;
		int i = 0;

		while (!dta.stackEmpty()) {

			urlString = dta.popUrl();

			try {
				String fileName = urlString.substring(
						urlString.lastIndexOf('/') + 1,
						urlString.lastIndexOf('.'));
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

				System.out.println(i);
				i++;

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}
}
