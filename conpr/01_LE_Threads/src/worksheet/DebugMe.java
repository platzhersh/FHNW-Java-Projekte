package worksheet;

import java.io.PrintStream;

public class DebugMe {
	public static void main(String[] args) throws Exception {
		Thread t1 = new Thread(new R(System.out), "1");
		Thread t2 = new Thread(new R(System.err), "2");
		t1.start();
		t2.start();
		System.out.println("#CPUs: " + Runtime.getRuntime().availableProcessors());
		System.out.println("main: done");
	}
}

class R implements Runnable {
	private PrintStream p;

	public R(PrintStream p) {
		this.p = p;
	}

	public void run() {
		for (int i = 0; i < 1000; i++) {
			p.println("Thread" + Thread.currentThread().getName() + ": " + i);
		}
	}
}