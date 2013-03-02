
public class Main {

	public static void main(String[] args) {
		ThreadCounter tc1 = new ThreadCounter("t1");
		Thread t1 = new Thread(tc1);
		ThreadCounter tc2 = new ThreadCounter("t2");
		Thread t2 = new Thread(tc2);
		ThreadCounter tc3 = new ThreadCounter("t3");
		Thread t3 = new Thread(tc3);
		ThreadCounter tc4 = new ThreadCounter("t4");
		Thread t4 = new Thread(tc4);
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
	}
	
}
