package semaphore;

import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class SemaphoreImpl implements Semaphore {
	private int value;
	
	/* holds threads that have to wait */
	private LinkedBlockingQueue<Thread> q;
	private final LinkedList<Condition> waitQueue;
	
	private final Lock lock = new ReentrantLock();
	
	public static void main(String[] args) {
		SemaphoreImpl sema = new SemaphoreImpl(2);
		Thread t1 = new SemaphoreThread(new Runnable() {
		
			@Override
			public void run() {
				sema.acquire();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("blubb");
				sema.release();
			}
			
		}, "muruk");
		t1.start();
		
		Thread t2 = new SemaphoreThread(new Runnable() {
			
			@Override
			public void run() {
				sema.acquire();
				
				System.out.println("blubb 2");
				sema.release();
			}
			
		}, "muruk 2");
		t2.start();
		
		Thread t3 = new SemaphoreThread(new Runnable() {
			
			@Override
			public void run() {
				sema.acquire();
				System.out.println("blubb 3");
				sema.release();
			}
			
		}, "muruk 3");
		t3.start();
	}

	public SemaphoreImpl(int initial) {
		if (initial < 0) throw new IllegalArgumentException();
		value = initial;
		q = new LinkedBlockingQueue<>();
		waitQueue = new LinkedList<>();
	}

	@Override
	public int available() {
		lock.lock();
		try {
			return value;
		} finally {
			lock.unlock();
		}
	}

	@Override
	public void acquire() {
		lock.lock();
		try {
			System.out.println(Thread.currentThread().getName() + " acquire");
			Condition curr = lock.newCondition();
			waitQueue.add(curr);
			System.out.println(Thread.currentThread().getName() + " added to queue");
			while (available() <= 0 || waitQueue.getFirst() != curr) {
				// The await() releases the lock to let other threads enter the method
				// tries to get lock back on signal()
				try { curr.await(); } catch (InterruptedException e) { }
			}
			value--;
			waitQueue.removeFirst();
			
			if (!waitQueue.isEmpty()) waitQueue.getFirst().signal();
			System.out.println(Thread.currentThread().getName() + " acquired");
		} finally {
			lock.unlock();
		}
	}

	@Override
	public void release() {
		lock.lock();
		try {
			value++;
			if (!waitQueue.isEmpty()) {
				waitQueue.getFirst().signal();
			}
		} finally {
			System.out.println(Thread.currentThread().getName() + " release");
			lock.unlock();
		}
	}
}
