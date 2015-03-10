package ch.fhnw.conpr.jmm.stop;

public class StoppingThreads {

	static class StoppableThread extends Thread {
		private boolean running = true;

		public void terminate() {
			running = false;
		}

		public boolean isRunning() {
			return running;
		}

		@Override
		public void run() {
			while (isRunning()) {
				doSomeWork();
//				System.out.println("isRunning() = " + isRunning());
			}
//			System.out.println("isRunning() = " + isRunning());
		}

		private void doSomeWork() {
			for (int i = 0; i < 10; i++) {
				i *= i;
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		StoppableThread t = new StoppableThread();

		System.out.println("starting thread t");
		t.start();
		Thread.sleep(1000);
		System.out.println("t.isRunning() = " + t.isRunning());
		
		t.terminate();
		System.out.println("t.isRunning() = " + t.isRunning());

		t.join();
		System.err.println("DONE");
	}


}
