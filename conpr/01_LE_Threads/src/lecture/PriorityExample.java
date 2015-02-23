package lecture;

public class PriorityExample {
	public static void main(String argv[]) {
		long n = 1_000_000_000;
		int maxPriority = Thread.currentThread().getThreadGroup().getMaxPriority();
		System.out.println("max priority = " + maxPriority);
		Thread threads[] = new Thread[maxPriority+1];

		for (int i = Thread.MIN_PRIORITY; i <= maxPriority; i++) {
			threads[i] = new Thread(new CounterThread(n));
			threads[i].setPriority(i);
			threads[i].setName("pri" + i);
		}

		for (int i = Thread.MIN_PRIORITY; i <= maxPriority; i++)
			threads[i].start();
	}

	static class CounterThread implements Runnable {
		private Counter counter;
		private long max;

		public CounterThread(long max) {
			this.max = max;
			this.counter = new Counter();
		}
		
		@Override
		public void run() {
			long start = System.currentTimeMillis();
			while (counter.get() < max) {
				counter.increment();
			}
			long time = System.currentTimeMillis() - start;
			System.out.println(String.format(
					"Thread with priority %2d took %5d ms", Thread
							.currentThread().getPriority(), time));
		}
	}

	// is only used by one thread
	static class Counter {
		private long counter = 0;
		public void increment() { counter++; }
		public long get() { return counter; }
	}

}