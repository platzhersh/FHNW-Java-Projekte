package worksheet;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class CollectionTest {
    /** Number of inserts per thread. */
    public static final int N_INSERTS = 2000000;
    
    @Test
    public void testLinkedListSequential() throws InterruptedException {
    	System.out.println("sequentiell");
       testCollection(new LinkedList<Integer>(), 1);
    }
    
    @Test
    public void testLinkedListParallel() throws InterruptedException {
    	System.out.println("parallel");
       testCollection(Collections.synchronizedList(new LinkedList<Integer>()), Runtime.getRuntime().availableProcessors());
    }
    
    /** Tests concurrent insertion of N_INSERTS elements into the given col using nThreads. 
     *  Each thread performs N_INSERTS/nThreads inserts. */
    private void testCollection(final Collection<Integer> col, final int nThreads) throws InterruptedException  {
        /* 1. Create threads. */
        List<Thread> threads = new ArrayList<Thread>(nThreads);
        for (int n = 0; n < nThreads; n++) {    
            Thread inserter = new Thread(){
            	public void run() {
            		for(int i = 0; i < N_INSERTS / nThreads; i++) {
                        col.add(i);
                  }
            	}
            };       
            threads.add(inserter);
        }
        
        /* 2. Start threads. */
        for (Thread t : threads) { t.start(); }
        
        /* 3. Wait for termination. */
        for (Thread t : threads) { t.join(); }
        
        /* Print col.size and calculate size */
        System.out.println("col.size() = " + col.size());
        int count = 0;
        for (Integer i : col) {
        	count++;
        }
        System.out.println("actual size = " + count);
        
        /* 4. Check number of inserts. */
        assertEquals(N_INSERTS, col.size());
    }
}