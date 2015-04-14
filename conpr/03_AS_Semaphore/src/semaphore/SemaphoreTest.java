package semaphore;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;


public class SemaphoreTest {
    
    public static void main(String[] args) throws InterruptedException {
        onlyOneInside();
        secondComesAfterReleaseOfFirst();
        twoAtTheSameTime();
        badNotifier();
        badInterruptor();
        doubleAcquire();
        fairness();
    }
    
    static Semaphore newSemaphore(int value) {
    	return new SemaphoreImpl(value);
    }

    static void onlyOneInside() throws InterruptedException {
        final Semaphore s = newSemaphore(1);
        final CountDownLatch l1 = new CountDownLatch(1);
        Thread t1 = run("ok", new Runnable() {
            public void run() {
                s.acquire();
                l1.countDown();
            }
        });
        
        Thread t2 = run("blocked", new Runnable() {
            public void run() {
                await(l1);
                s.acquire();
                fail("should never reach here!");
            }
        });
        
        t1.join();
        t2.join(100);
        success("oneInside");
    }
    
    static void secondComesAfterReleaseOfFirst() throws InterruptedException {
        final Semaphore s = newSemaphore(1);
        final CountDownLatch l1 = new CountDownLatch(1);
        final AtomicReference<String> owner = new AtomicReference<String>("");
        Thread t1 = run("ok1", new Runnable() {
            public void run() {
                s.acquire();
                l1.countDown();
                owner.set("ok1");
                sleep(100);
                owner.set("");
                s.release();
            }
        });
        
        
        Thread t2 = run("ok2", new Runnable() {
            public void run() {
                await(l1);
                s.acquire();
                if("ok1".equals(owner.get())) fail("two in critical region at the same time");
                owner.set("ok2");
                s.release();
            }
        });
        
        
        t1.join();
        t2.join();
        success("secondComesAfterReleaseOfFirst");
    }
    
    
    static void twoAtTheSameTime() throws InterruptedException {
        final Semaphore s = newSemaphore(2);
        final AtomicReference<Boolean> t1Inside = new AtomicReference<Boolean>(false);
        final AtomicReference<Boolean> t2Inside = new AtomicReference<Boolean>(false);
        
        Thread t1 = run("ok1", new Runnable() {
            public void run() {
                s.acquire();
                t1Inside.set(true);
                sleep(100);
                if(!t2Inside.get()) fail("t2 should also be inside!");
                sleep(100);
                t1Inside.set(false);
                s.release();
            }
        });
        
        
        Thread t2 = run("ok2", new Runnable() {
            public void run() {
                s.acquire();
                t2Inside.set(true);
                sleep(100);
                if(!t1Inside.get()) fail("t1 should also be inside!");
                sleep(100);
                t2Inside.set(false);
                s.release();
            }
        });
        t1.join();
        t2.join();
        success("twoAtTheSameTime");
    }
    
    static void badNotifier() throws InterruptedException {
        final Semaphore s = newSemaphore(0);
        Thread t1 = run("ok", new Runnable() {
            public void run() {
                s.acquire();
                fail("should never reach here! Use a private lock and loop over predicate");
            }
        });
        
        Thread t2 = run("notifier", new Runnable() {
            public void run() {
                sleep(100);
                synchronized (s) {
                    s.notifyAll();
                }
            }
        });
        
        t1.join(200);
        t2.join();
        success("badNotifier");
    }
    
    static void badInterruptor() throws InterruptedException {
        final Semaphore s = newSemaphore(0);
        final Thread t1 = run("ok", new Runnable() {
            public void run() {
                s.acquire();
                fail("should never reach here!");
            }
        });
        
        Thread t2 = run("notifier", new Runnable() {
            public void run() {
                sleep(100);
                t1.interrupt();
            }
        });
        
        t1.join(200);
        t2.join();
        success("badInterruptor");
    }
    
    static void doubleAcquire() throws InterruptedException {
        final Semaphore s = newSemaphore(0);
        final CountDownLatch l1 = new CountDownLatch(2);
        
        Thread t1 = run("T1", new Runnable() {
            public void run() {
                s.acquire();
                l1.countDown();
            }
        });
        
        Thread t2 = run("T2", new Runnable() {
            public void run() {
                s.acquire();
                l1.countDown();
            }
        });
        
        sleep(200);
        s.release();
        s.release();
        boolean res = l1.await(200, TimeUnit.MILLISECONDS);
        if(!res) fail("release does not release a waiting thread");

        t1.join(100);
        t2.join(100);
        success("doubleAcquire");
    }
    
   static void fairness() throws InterruptedException {
        final Semaphore s = newSemaphore(0);
        final AtomicReference<String> owner = new AtomicReference<String>("");
        final CountDownLatch l1 = new CountDownLatch(1);
        
        Thread t1 = run("T1", new Runnable() {
            public void run() {
                s.acquire();
                owner.set("T1");
                l1.countDown();
            }
        });
        
        Thread t2 = run("T2", new Runnable() {
            public void run() {
            	sleep(50);
                s.acquire();
                owner.set("T2");
                l1.countDown();
            }
        });
        
        sleep(200);
        s.release();
        await(l1);
        if(!owner.get().equals("T1")) fail("fairness not guaranteed, T1 was first");
        
        t1.join(100);
        t2.join(100);
        success("fairness");
    }
    

    
    static final AtomicBoolean failed = new AtomicBoolean(false);
    
    static void success(String msg) {
        if(!failed.get()) {
            System.out.println("+ " + msg);
        } else {
            failed.set(false);
        }
    }
    
    static void fail(String msg) {
        failed.set(true);
        throw new IllegalStateException(msg);
    }
    
    static void await(CountDownLatch l) {
        try {
            l.await();
        } catch (InterruptedException e) {}
    }

    static Thread run(String name, Runnable r) {
        Thread t = new Thread(r, name);
        t.setDaemon(true);
        t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.err.println(t.getName() + " failed: " + e);
            }
        });
        t.start();
        return t;
    }
    
    static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {}
    }
}
