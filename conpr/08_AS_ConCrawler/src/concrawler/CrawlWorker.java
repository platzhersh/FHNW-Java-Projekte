package concrawler;

import java.util.Queue;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CrawlWorker implements Runnable {
	
	final Set<String> urlsVisited;
    final Queue<String> urlsToVisit;
    final String toVisit;
	
	public CrawlWorker(Queue<String> urlsToVisit, Set<String> urlsVisited, String toVisit) {
		this.urlsVisited = urlsVisited;
		this.urlsToVisit = urlsToVisit;
		this.toVisit = toVisit;
	}

	@Override
	public void run() {
		try {
			Document doc = Jsoup.parse(Jsoup.connect(toVisit)
					.userAgent("ConCrawler/0.1 Mozilla/5.0")
					.timeout(3000)
					.get().html());

			Elements links = doc.select("a[href]");
			for (Element link : links) {
				String linkString = link.absUrl("href");
					if ((!urlsVisited.contains(linkString)) && linkString.startsWith("http")) {
						urlsToVisit.add(linkString);
					}
			}

		} catch (Exception e) {
			System.out.println("Problem reading '" + toVisit + "'. Message: " + e.getMessage());
		}
	}

}
