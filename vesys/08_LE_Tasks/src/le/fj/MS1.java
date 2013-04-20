package le.fj;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class MS1 implements Runnable {
    public final int[] elems, temp;
    private final int start, end;

    private final ExecutorService ex;

    public MS1(int[] elems, ExecutorService ex) {
        this.elems = elems;
        this.start = 0;
        this.end = elems.length;
        this.temp = new int[end];
        this.ex = ex;
    }
    
    public MS1(int[] elems, int[] temp, int start, int end, ExecutorService es) {
        this.elems = elems; this.temp = temp;
        this.start = start; this.end = end;
        this.ex = es;
    }
    

    @Override
    public void run() {
        if(end - start <= 1) {
            return;
        } else {
            int mid = (start + end) / 2;

            MS1 left = new MS1(elems, temp, start, mid, ex);
            MS1 right = new MS1(elems, temp, mid, end, ex);

            Future<?> lf = ex.submit(left);
            Future<?> rf = ex.submit(right);
             try {
                 lf.get(); rf.get();
            } catch (InterruptedException | ExecutionException e) {}
            merge(elems, temp, start,mid,end); 
        }
    }


    private static void merge(int[ ] elem, int[ ] tmp, int leftPos, int rightPos, int rightEnd) {
        if (elem[rightPos] <= elem[rightPos+1]) return;
        
        int leftEnd = rightPos;
        int tmpPos = leftPos;
        int numElements = rightEnd - leftPos;
        
        while( leftPos < leftEnd && rightPos < rightEnd )
            if( elem[leftPos] <= elem[rightPos] )
                tmp[tmpPos++] = elem[leftPos++];
            else
                tmp[tmpPos++] = elem[rightPos++];
        
        while(leftPos < leftEnd) tmp[tmpPos++] = elem[leftPos++];
        
        while(rightPos < rightEnd) tmp[tmpPos++] = elem[rightPos++];
       
        rightEnd--;
        for( int i = 0; i < numElements; i++, rightEnd--)
            elem[rightEnd] = tmp[rightEnd];
    }
    


    private static void fixedPool() throws InterruptedException, ExecutionException {
        int SIZE = 4;
        int[] l = new int[SIZE];
        Random rnd = new Random();
        for (int i = 0; i < l.length; i++) {
            l[i] = rnd.nextInt(10000);
        }

        ExecutorService es = Executors.newFixedThreadPool(3);

        MS1 ms = new MS1(l,es);
        Future<?> f = es.submit(ms);
        f.get();

        es.shutdownNow();
    }
    
    private static void cachedPool() throws InterruptedException, ExecutionException {
        int SIZE = 1000000;
        int[] l = new int[SIZE];
        Random rnd = new Random();
        for (int i = 0; i < l.length; i++) {
            l[i] = rnd.nextInt(10000);
        }
   
        final AtomicInteger i = new AtomicInteger();
        ExecutorService es = Executors.newCachedThreadPool(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
               System.out.println(i.getAndIncrement());
               return new Thread(r);
            }
        });

        MS1 ms = new MS1(l,es);
        Future<?> f = es.submit(ms);
        f.get();
        
        es.shutdownNow();
    }
    

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        fixedPool();
        //cachedPool();
    }
}
