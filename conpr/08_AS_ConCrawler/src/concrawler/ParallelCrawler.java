package concrawler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Crawls the web sequentially. This class is already thread safe since it is
 * stateless.
 */
public class ParallelCrawler implements Crawler {
    /** Maximal number of visited urls per request. */
    private static final int MAX_VISITS = 20;
    
    
    ExecutorService exec = new ThreadPoolExecutor(5, 100, 0L, TimeUnit.MINUTES, new ArrayBlockingQueue<>(500));
	ThreadFactory f = new MyThreadFactory();
	

    /**
     * Crawls the web, starting at startURL and returning a list of visited
     * URLs.
     */
    public List<String> crawl(String startURL) {
        /* Contains the already visited urls. */
        
    	// why Boolean?
        final Set<String> urlsVisited = Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());
        
        /* Contains the urls to be visited. */
        final Queue<String> urlsToVisit = new ConcurrentLinkedQueue<String>();
        
        
        urlsToVisit.add(startURL);

        while (urlsVisited.size() < MAX_VISITS) {
        	
        	// could lead to infinite loop if no new URLs are added to the Queue
	        while (urlsToVisit.isEmpty()) {;}
	        
        	String toVisit = urlsToVisit.poll();
	        urlsVisited.add(toVisit);
        	exec.execute(f.newThread(new CrawlWorker(urlsToVisit, urlsVisited, toVisit)));        	
        }
        
        // kill all threads that are still running, because MAX_VISITS is already reached
        List<Runnable> blubb = exec.shutdownNow();        
        System.out.println(blubb.size() + " Threads gekillt");
        
        return new ArrayList<String>(urlsVisited);
    }
}
