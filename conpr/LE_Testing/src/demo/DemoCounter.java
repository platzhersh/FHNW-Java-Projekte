package demo;

import javax.annotation.concurrent.NotThreadSafe;

public class DemoCounter {

  public static void main(String[] args) throws InterruptedException {
    Counter c = new Counter();
    Runnable r = new R(c);
    Thread t0 = new Thread(r);
    Thread t1 = new Thread(r);
    
    long st = System.currentTimeMillis();
    t0.start();
    t1.start();

    t0.join();
    t1.join();

    System.out.println(System.currentTimeMillis() - st + " msec");
    System.out.println(c.getCount());
  }
}

@NotThreadSafe
class Counter {
	private volatile int i = 0;

	public void inc() {
		i++;
	}

	public int getCount() {
		return i;
	}
}

class R implements Runnable {
	private final Counter c;

	public R(Counter c) {
		this.c = c;
	}

	public void run() {
		for (int k = 0; k < 10; k++) {
			c.inc();
		}
	}
}

