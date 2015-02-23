package lecture;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Attack {
    
    public static void main(String[] args) throws InterruptedException {
        final List<Integer> l = Collections.synchronizedList(new LinkedList<Integer>());
        
        Thread good = new Thread() {
            public void run() {
               while(true) {
                   l.add(1);
                   System.out.println("Insert");
                   try { Thread.sleep(1000); } catch (InterruptedException e) {}
               }
            }
        };
        good.start();
        
        Thread.sleep(5000);
        
        Thread bad = new Thread() {
            public void run() {
                synchronized (l) {
                    System.out.println("No more progress!");
                    try { Thread.sleep(Long.MAX_VALUE); } catch (InterruptedException e) {}
                }
            }
        };
        bad.start();
    }
}
