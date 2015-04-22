package impl;

import interfaces.AsyncResult;
import interfaces.Calculation;
import interfaces.Computer;


// Tschech Version
public class BasicComputer2 implements Computer {

	@Override
	public AsyncResult doCalc(Calculation c) {
		return new MyAsyncResult(c);
	}
	
	public class MyAsyncResult implements AsyncResult {
		
		double res;
		volatile boolean ready;
		Thread ct;
		final Object lock;
		
		public MyAsyncResult(Calculation c) {
			res = 0;
			//ready = false;
			lock = new Object();
			Runnable r = new Runnable() {
				@Override
				public void run() {
					int slp = (int) (Math.random() * 1000);
					System.out.println("Thread going to sleep for " + slp + "ms");
					// sleep a bit, to make it all more async :)
					try {
						Thread.sleep(slp);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					synchronized(lock) {
						res = c.calc();
						ready = true;
						lock.notifyAll();
					}
					
					System.out.println("Result calculated: " + res);
				}
			};
			ct = new Thread(r);
			ct.start();
		}
		
		@Override
		public double awaitResult() {
			synchronized(lock) {
				while(!ready) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return res;
			}
		}	
	}
}
