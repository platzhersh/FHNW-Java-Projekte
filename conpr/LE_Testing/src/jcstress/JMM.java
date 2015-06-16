package jcstress;

import java.util.concurrent.atomic.AtomicInteger;

public class JMM {
   private AtomicInteger ai = new AtomicInteger(5);
   private int i = 1;

   public void run() {
      new Thread(() -> {
         i++;  
         ai.set(i); 
      }, "T1").start();

      new Thread(() -> {
         int _i = i;         // (1)
         int _ai = ai.get(); // (2)
         System.out.println(_i + " " + _ai);
      }, "T2").start();
   }
   public static void main(String[] args) { new JMM().run(); }   
}
