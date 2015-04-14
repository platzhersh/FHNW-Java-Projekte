package le.fj;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

@SuppressWarnings("serial")
public class FJMergeSortSharedIntArray extends RecursiveAction {
    private static final int THRESHOLD = 20000;

    public final int[] elems;
    private final int[] temp;

    private final int start;
    private final int end;

    public FJMergeSortSharedIntArray(int[] elems) {
        this.elems = elems;
        this.start = 0;
        this.end = elems.length;
        this.temp = new int[end];
    }
    
    public FJMergeSortSharedIntArray(int[] elems, int[] temp, int start, int end) {
        this.elems = elems;
        this.temp = temp;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        if (end - start <= THRESHOLD) {
            Arrays.sort(elems, start, end);
        } else {
            int mid = (start + end) / 2;

            FJMergeSortSharedIntArray left = new FJMergeSortSharedIntArray(elems, temp, start, mid);
            FJMergeSortSharedIntArray right = new FJMergeSortSharedIntArray(elems, temp, mid, end);
            left.fork();
            right.invoke();
            left.join();
//            invokeAll(left, right);
            merge(elems, temp, start,mid,end);
        }
    }

    private static void merge(int[ ] elem, int[ ] tmp, int leftPos, int rightPos, int rightEnd) {
        if (elem[rightPos - 1] <= elem[rightPos]) return; // already sorted
       
        int leftEnd = rightPos;
        int tmpPos = leftPos;
        int numElements = rightEnd - leftPos;
        
        // Main loop
        while( leftPos < leftEnd && rightPos < rightEnd )
            if( elem[leftPos] <= elem[rightPos] )
                tmp[tmpPos++] = elem[leftPos++];
            else
                tmp[tmpPos++] = elem[rightPos++];
        
        while(leftPos < leftEnd) 
            tmp[tmpPos++] = elem[leftPos++];
        
        while(rightPos < rightEnd)
            tmp[tmpPos++] = elem[rightPos++];
       
        rightEnd--;
        // Copy tmpArray back
        for( int i = 0; i < numElements; i++, rightEnd-- )
            elem[rightEnd] = tmp[rightEnd];
    }

    public static void main(String[] args) {
        Random rnd = new Random();
        int SIZE = 100_000_000;
        ForkJoinPool fj = new ForkJoinPool();

        int[] data = new int[SIZE];
        for (int i = 0; i < data.length; i++) {
            data[i] = rnd.nextInt(10000);
        }
        
        int[] copy = Arrays.copyOf(data, data.length);

        long start = System.currentTimeMillis();
        FJMergeSortSharedIntArray task = new FJMergeSortSharedIntArray(data);
        fj.invoke(task);
        long duration = System.currentTimeMillis() - start;
        
        System.out.println("FJSort: " + duration + " ms");

        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        
        start = System.currentTimeMillis();
        Arrays.sort(copy);
        duration = System.currentTimeMillis() - start;
        System.out.println("SeqSort: " + duration + " ms");
        
        System.out.println(Arrays.equals(copy, task.elems));
    }
}
