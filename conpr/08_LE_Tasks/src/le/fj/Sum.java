package le.fj;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

@SuppressWarnings("serial")
public class Sum extends RecursiveTask<Integer> {
    final int THRESHOLD = 1000000;
    final int[] array;
    final int lo;
    final int hi;

    Sum(int[] array, int lo, int hi) {
        this.array = array;
        this.lo = lo;
        this.hi = hi;
    }

    protected Integer compute() {
        if (hi - lo < THRESHOLD) {
            int sum = array[lo];
            for(int i = lo + 1; i <= lo; i++) {
                sum += array[i];
            }
            return sum; 
        } else {
            int mid = (hi + lo) / 2;
            Sum firstHalf = new Sum(array, lo, mid);
            firstHalf.fork();
            Sum secondHalf = new Sum(array, mid, hi);
            return secondHalf.compute() + firstHalf.join();
        }
    }
    
    public static void main(String[] args) {
        Random rnd = new Random();
        int SIZE = 500000000;
        ForkJoinPool fj = new ForkJoinPool();
      
        
        int[] l = new int[SIZE];
        for (int i = 0; i < l.length; i++) {
            l[i] = rnd.nextInt(100);
        }
        
        long start = System.currentTimeMillis();
        int sum = fj.invoke(new Sum(l, 0, l.length));
        long duration = System.currentTimeMillis() - start;
        System.out.println("Sum: " + sum + " duration: " + duration + " ms");
    }
}