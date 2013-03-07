package collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class UnsafeCollections {
    /** Number of inserts per thread. */
    public static final int N_INSERTS = 2000000;
    
    public static void main(String[] args) throws InterruptedException {
        for (Integer i : new Integer[] { 1, 2, 4, 8 }) {
            testCollection(new LinkedList<Integer>(), i);
            // testCollection(java.util.Collections.???, i);
            // testCollection(new java.util.concurrent.???, i);
        }
    }
    
    /** Tests concurrent element insertion into the given col using nThreads. 
     *  Each thread performs N_INSERTS inserts. */
    private static void testCollection(Collection<Integer> col, int nThreads) throws InterruptedException  {
        /* 1. Create threads. */
        List<Inserter> threads = new ArrayList<Inserter>(nThreads);
        for (int i = 0; i < nThreads; i++) {
            threads.add(new Inserter(col, nThreads));
        }
        
        /* 2. Start threads. */
        for (Inserter t : threads) { t.start(); }
        
        /* 3. Wait for termination. */
        for (Inserter t : threads) { t.join(); }
        
        /* 4. Create and print report. */
        String result = "Class: " + col.getClass().getSimpleName() + "\n";
        result += nThreads + " threads used ";
        
        long startTime = Long.MAX_VALUE;
        long endTime = Long.MIN_VALUE;
        for (Inserter t : threads) {
        	startTime = Math.min(t.getStartTime(), startTime);
        	endTime = Math.max(t.getEndTime(), endTime);
        }
        
        result += (endTime-startTime) + "ms\n";
        result += "Expected size = " + N_INSERTS + ", actual size = " + col.size() + "\n";
        System.out.println(result);
    }
    
}

/** Test thread which inserts N_INSERTS into its collection. */
class Inserter extends Thread {
	private final Collection<Integer> col;
    private int nThreads;
    private long startTime;
    private long endTime;
    
    public Inserter(Collection<Integer> col, int nThreads) {
        this.col = col;
        this.nThreads = nThreads;
    }
    @Override
    public void run() {
    	startTime = System.currentTimeMillis();
    	for(int i = 0; i < UnsafeCollections.N_INSERTS / nThreads; i++) {
            col.add(i);
        }
    	endTime = System.currentTimeMillis();
    }
    public long getStartTime() { return startTime; }
	public long getEndTime() { return endTime; }
}
