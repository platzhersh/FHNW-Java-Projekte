package lecture;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;


public class Deadlock {
    public static void main(String[] args) throws InterruptedException {
        final Object x = new Object();
        final Object y = new Object();
        
        System.out.println("x = " + x);
        System.out.println("y = " + y);
        
        Thread t1 = new Thread("t1") {
            public void run() { 
                synchronized(x) {
                	unluckyScheduling();
                	synchronized(y) {
                		System.out.println("ok1");
                	}
                }
            }
        };
        Thread t2 = new Thread("t2") {
            public void run() {
                synchronized(y) {
                	unluckyScheduling();
                	synchronized(x) {
                		System.out.println("ok2");
                	}
                }
            }
        };
        
        t1.start(); t2.start();
        // detectDeadlock();
        t1.join(); t2.join();
        
    }
    
    /** Causes deadly scheduling. */
	private static void unluckyScheduling() {
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {}
    }
    
    /** Detects a deadlock and prints relevant information. */
    @SuppressWarnings("unused")
	private static void detectDeadlock() throws InterruptedException {
        Thread.sleep(15); // wait for deadlock :-)
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        long[] deadlockedThreads = threadMXBean.findMonitorDeadlockedThreads();
        
        ThreadInfo[] threadInfo = threadMXBean.getThreadInfo(deadlockedThreads);
        for (ThreadInfo threadInfo2 : threadInfo) {
            System.out.print(threadInfo2);
        }
    }
    
}
