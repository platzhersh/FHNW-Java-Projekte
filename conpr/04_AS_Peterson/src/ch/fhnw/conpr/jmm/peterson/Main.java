/*
 * Copyright (c) 2010-2013 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package ch.fhnw.conpr.jmm.peterson;

public class Main {
	private static Mutex pm;
	private static volatile int v = 0;

	public static void main(String[] args) {
		Thread t1 = new Thread(r);
		Thread t2 = new Thread(r);
		pm = new PetersonMutex(t1, t2);
		t1.start();
		t2.start();
	}

	private static Runnable r = new Runnable() {
		@Override
		public void run() {
			while (true) {
				criticalSection();
			}
		}
	};

	private static void criticalSection() {
		pm.lock();
		v++;
		sleep(500);
		System.out.println(">> " + v + " "+Thread.currentThread().getName()); // should always print 1
		sleep(500);
		v--;
		pm.unlock();
	}
	
	private static void sleep(int millis) {
	    try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {}
	}
}
