package le;

import net.jcip.annotations.NotThreadSafe;

@NotThreadSafe
public class Counter {
	private long i = 0;

	public void inc() {
		i++;
	}

	public long getCount() {
		return i;
	}

	static class R implements Runnable {
		private Counter c;

		public R(Counter c) {
			this.c = c;
		}

		public void run() {
			for (int i = 0; i < 100000; i++) {
				c.inc();
			}
		}
	}

	public static void main(String[] args) {
		Counter c = new Counter();
		Runnable r = new R(c);
		Thread t0 = new Thread(r);
		Thread t1 = new Thread(r);
		Thread t2 = new Thread(r);
		Thread t3 = new Thread(r);
		t0.start();
		t1.start();
		t2.start();
		t3.start();

		try {
			t0.join();
			t1.join();
			t2.join();
			t3.join();
		} catch (InterruptedException e) {
		}

		System.out.println(c.getCount());
	}
}
