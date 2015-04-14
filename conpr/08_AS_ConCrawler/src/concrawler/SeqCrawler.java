package concrawler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Crawls the web sequentially. This class is already thread safe since it is
 * stateless.
 */
public class SeqCrawler implements Crawler {
    /** Maximal number of visited urls per request. */
    private static final int MAX_VISITS = 100;

    /**
     * Crawls the web, starting at startURL and returning a list of visited
     * URLs.
     */
    public List<String> crawl(String startURL) {
        /* Contains the already visited urls. */
        Set<String> urlsVisited = new HashSet<String>();
        /* Contains the urls to be visited. */
        Queue<String> urlsToVisit = new LinkedList<String>();
        urlsToVisit.add(startURL);

        while ((!urlsToVisit.isEmpty()) && urlsVisited.size() < MAX_VISITS) {
            String toVisit = urlsToVisit.poll(); // crawl next url
            urlsVisited.add(toVisit);

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
        return new ArrayList<String>(urlsVisited);
    }
}
