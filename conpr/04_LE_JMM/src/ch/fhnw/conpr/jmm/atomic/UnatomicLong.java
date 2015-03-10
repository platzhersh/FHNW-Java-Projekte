package ch.fhnw.conpr.jmm.atomic;

// http://stackoverflow.com/questions/17481153/long-and-double-assignments-are-not-atomic-how-does-it-matter
// This only leads to surprising result on 32 Bit JVMs.
public class UnatomicLong implements Runnable {
	private static long test = 0;

	private final long val;

	public UnatomicLong(long val) {
		this.val = val;
	}

	public void run() {
		while (!Thread.interrupted()) {
			test = val;
		}
	}

	public static void main(String[] args) {
		Thread t1 = new Thread(new UnatomicLong(-1));
		Thread t2 = new Thread(new UnatomicLong(0));

		System.out.println(Long.toBinaryString(-1));
		System.out.println(pad(Long.toBinaryString(0), 64));
		System.out.println("starting threads");

		t1.start();
		t2.start();

		long val = test;
		while (val == -1 || val == 0) {
			val = test;
		}

		System.out.println(pad(Long.toBinaryString(val), 64));
		System.out.println(val);

		t1.interrupt();
		t2.interrupt();
	}

	// prepend 0s to the string to make it the target length
	private static String pad(String s, int targetLength) {
		int n = targetLength - s.length();
		for (int x = 0; x < n; x++) {
			s = "0" + s;
		}
		return s;
	}
}



/*
Java8 (32 bit)
==============

% javac ch/fhnw/conpr/jmm/atomic/UnatomicLong.java
% java ch.fhnw.conpr.jmm.atomic.UnatomicLong
1111111111111111111111111111111111111111111111111111111111111111
0000000000000000000000000000000000000000000000000000000000000000
starting threads
0000000000000000000000000000000011111111111111111111111111111111
4294967295

% java ch.fhnw.conpr.jmm.atomic.UnatomicLong
1111111111111111111111111111111111111111111111111111111111111111
0000000000000000000000000000000000000000000000000000000000000000
starting threads
1111111111111111111111111111111100000000000000000000000000000000
-4294967296

*/