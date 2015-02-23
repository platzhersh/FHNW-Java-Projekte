package lecture;

import net.jcip.annotations.NotThreadSafe;

@NotThreadSafe
public class Counter {
	private int i = 0;

	public void inc() {
		i++;
	}

	public int getCount() {
		return i;
	}

	static class R implements Runnable {
		private Counter c;

		public R(Counter c) {
			this.c = c;
		}

		public void run() {
			for (int k = 0; k < 100000; k++) {
				c.inc();
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		Counter c = new Counter();
		Runnable r = new R(c);
		Thread t0 = new Thread(r);
		Thread t1 = new Thread(r);
		Thread t2 = new Thread(r);
		Thread t3 = new Thread(r);
		
		long st = System.currentTimeMillis();
		t0.start();
		t1.start();
		// Thread.sleep(15);
		t2.start();
		t3.start();

		t0.join();
		t1.join();
		t2.join();
		t3.join();
			
		System.out.println(System.currentTimeMillis() - st + " msec");
		System.out.println(c.getCount());
	}
}
