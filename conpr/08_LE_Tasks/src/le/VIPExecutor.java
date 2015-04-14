package le;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class VIPExecutor {
	enum Prio { High, Low }
	static abstract class PriorityRunnable implements Runnable, Comparable<PriorityRunnable> {
		public final Prio prio;
		public PriorityRunnable(Prio prio) {
			this.prio = prio;
		}
		
		@Override
		public int compareTo(PriorityRunnable o) {
			return prio.compareTo(o.prio);
		}
	}
	
	public static void main(String[] args) {
		ExecutorService ex = new ThreadPoolExecutor(2, 2, 0, TimeUnit.SECONDS, new PriorityBlockingQueue<Runnable>());
		
		
		for (int i = 0; i < 3; i++) {
			ex.execute(new PriorityRunnable(Prio.Low) {
				@Override public void run() {
					System.out.println("Low");
					sleep(1);
				}
			});
		}
		for (int i = 0; i < 2; i++) {
		ex.execute(new PriorityRunnable(Prio.High) {
			@Override public void run() {
				System.out.println("High");
				sleep(1);
			}
		});	
		}
	}
	
	static void sleep(int seconds) {
		try {
			Thread.sleep(seconds*1000);
		} catch (InterruptedException e) {}
	}
}
