package concrawler;

import java.util.List;

/**
 * The web crawler interface.
 */
public interface Crawler {
    /**
     * Crawls the web, starting at {@code startURL} and returning all links
     * reachable from there. The maximum size of resulting list should be
     * sensibly limited (e.g. 25 Results).
     * @param startURL the URL to start crawling the web
     * @return a list of urls which are reachable from {@code startURL}
     */
    List<String> crawl(String startURL);
}
