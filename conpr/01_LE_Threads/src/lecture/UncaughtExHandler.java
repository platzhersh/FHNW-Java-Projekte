package lecture;

public class UncaughtExHandler {
	public static void main(String[] args) {
		Thread t = new Thread() {
			public void run() {
				throw new IllegalStateException("Test");
			}
		};
		
		t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				System.out.println("Thread " + t + " died because of " + e);
			}
		});
		
		t.start();
	}
}
