import java.util.concurrent.atomic.AtomicReference;


public class Main {
	
	public static void main(String[] args) {
		
		
		Object lock = new Object();
		
		Thread t = new Thread ("T3") {
			
			public synchronized void run() {
				
				System.out.println("yeaah " + Thread.currentThread().getName());
				System.out.println("yeaah " + Thread.currentThread().equals(this));
			}
		};
		t.start();
		
		ThreadLocal<Integer> tl = new ThreadLocal<Integer>() {
			
		};
		AtomicReference<String> ai = new AtomicReference<>();
		
		
		
	}

}
