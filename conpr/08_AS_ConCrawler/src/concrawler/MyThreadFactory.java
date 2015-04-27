package concrawler;

import java.util.concurrent.ThreadFactory;

public class MyThreadFactory implements ThreadFactory {

	static volatile int count = 0;
	
	@Override
	public synchronized Thread newThread(Runnable r) {
		count++;
		System.out.println("Thread-"+ count + " created");
		return new Thread(r, "Thread-" + count);
	}

}
