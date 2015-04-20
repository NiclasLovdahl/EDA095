import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Processor extends Thread {

	URL startURL;
	WebCrawler wc;

	public Processor(URL startURL, WebCrawler wc) {
		this.startURL = startURL;
		this.wc = wc;
	}

	public void run() {

		URLConnection urlc;
		while (!wc.terminate()) {

			String s = wc.popURL();

			if (s != null) {
				try {
					URL url = new URL(s);
					urlc = url.openConnection();

					if (urlc.getContentType().contains("text/html")) {
						String baseString = wc.parseBase(url.toString());

						InputStream is = url.openStream();
						Document doc = Jsoup.parse(is, "UTF-8", baseString);
						Elements links = doc.getElementsByTag("a");
						for (Element link : links) {
							String linkHref = link.attr("href");
							String linkAbsHref = link.attr("abs:href");
							if (!linkAbsHref.equals("") && !linkHref.equals("")
									&& !wc.remainingURLs.contains(linkAbsHref)) {
								if (!linkAbsHref.substring(0, 7).equals(
										"mailto:")
										&& !wc.terminate()) {
									wc.queueURL(linkAbsHref);
									wc.putURL(linkAbsHref);
								}
								if (linkAbsHref.substring(0, 7).equals(
										"mailto:")
										&& !wc.mails.contains(linkAbsHref)) {
									wc.putMail(linkAbsHref);
								}
								if (wc.terminate()) {
									break;

								}
							}

						}
						wc.putURL(url.toString());
						is.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
