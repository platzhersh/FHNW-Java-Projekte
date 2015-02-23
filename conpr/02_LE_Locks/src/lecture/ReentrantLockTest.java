package lecture;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {

	private final Lock lock = new ReentrantLock();
	private long counter;
	
	public long getAndIncrement() {
		lock.lock();
		try {
			return counter++;
		} finally {
			lock.unlock();
		}
	}
	
	public static void main(String[] args) {
		final Lock lock = new ReentrantLock();
		lock.lock();
		new Thread() { public void run() {
			lock.unlock();
		} }.start();
	}
	
}
