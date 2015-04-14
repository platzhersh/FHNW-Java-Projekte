package semaphore;

public class SemaphoreThread extends Thread {
	private volatile boolean isSuspended;
	
	public void mySuspend() {
		isSuspended = true;
	}
	public void myResume() {
		isSuspended = false;
	}
	
	public SemaphoreThread(Runnable r, String name) {
		super(r,name);
	}
}
