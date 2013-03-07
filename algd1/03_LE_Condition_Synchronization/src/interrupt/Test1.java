package interrupt;

public class Test1 {

	public static void main(String[] args) {
		Thread.currentThread().interrupt();
		System.out.println(Thread.interrupted());
		try {
			Thread.sleep(1000);
			System.out.println("ok1");
		} catch (InterruptedException e) {
			System.out.println("IE: " + Thread.currentThread().isInterrupted());
		}
		
		Thread.currentThread().interrupt();
		System.out.println(Thread.currentThread().isInterrupted());
		try {
			Thread.sleep(1000);
			System.out.println("ok2");
		} catch (InterruptedException e) {
			System.out.println("IE: " + Thread.currentThread().isInterrupted());
		}

	}

}
