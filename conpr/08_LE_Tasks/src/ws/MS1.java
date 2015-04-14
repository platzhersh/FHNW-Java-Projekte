package ws;

import java.util.Arrays;
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
      this.elems = elems;
      this.temp = temp;
      this.start = start;
      this.end = end;
      this.ex = es;
   }

   @Override
   public void run() {
      if (end - start <= 1) {
         return;
      } else {
         int mid = (start + end) / 2;

         MS1 left = new MS1(elems, temp, start, mid, ex);
         MS1 right = new MS1(elems, temp, mid, end, ex);

         Future<?> lf = ex.submit(left);
         Future<?> rf = ex.submit(right);
         try {
            print("Waiting for subtasks");
            lf.get();
            rf.get();
            print("Subtasks are ready");
         } catch (Exception e) {
         }
         merge(elems, temp, start, mid, end);
      }
   }

   @SuppressWarnings("unused")
   private void print(String msg) {
      System.out.println(Thread.currentThread().getName() + " : " + msg);
   }

   private static void merge(int[] elem, int[] tmp, int leftPos, int rightPos, int rightEnd) {
      if (elem[rightPos - 1] <= elem[rightPos]) return;
      
      int leftEnd = rightPos;
      int tmpPos = leftPos;
      int numElements = rightEnd - leftPos;

      while (leftPos < leftEnd && rightPos < rightEnd)
         if (elem[leftPos] <= elem[rightPos]) 
            tmp[tmpPos++] = elem[leftPos++];
         else 
            tmp[tmpPos++] = elem[rightPos++];

      while (leftPos < leftEnd)
         tmp[tmpPos++] = elem[leftPos++];

      while (rightPos < rightEnd)
         tmp[tmpPos++] = elem[rightPos++];

      rightEnd--;
      for (int i = 0; i < numElements; i++, rightEnd--)
         elem[rightEnd] = tmp[rightEnd];
   }
   
   private static ExecutorService cachedPool() {
      final AtomicInteger i = new AtomicInteger();
      return Executors.newCachedThreadPool(new ThreadFactory() {
         @Override
         public Thread newThread(Runnable r) {
            System.out.println(i.getAndIncrement());
            return new Thread(r);
         }
      });
   }
   
   private static int[] randomInts(int n) {
      int[] l = new int[n];
      Random rnd = new Random();
      for (int i = 0; i < l.length; i++) {
         l[i] = rnd.nextInt(1000);
      }
      return l;
   }

   public static void main(String[] args) throws InterruptedException, ExecutionException {
      int SIZE = 4;
      int[] data = randomInts(SIZE);
      
      System.out.println("Unsorted: " + Arrays.toString(data));
      
      // Aufgabe 1) 
      // 3 Threads, 4 Elemente sortieren
      ExecutorService es = Executors.newFixedThreadPool(4);
      
      
      // Aufgabe 2)
      // Viele Threads, 4 Elemente sortieren
      // ExecutorService es = cachedPool();
      
      MS1 ms = new MS1(data, es);
      Future<?> f = es.submit(ms);
      f.get();

      es.shutdownNow();
      System.out.println("Sorted: " + Arrays.toString(data));
   }
}
