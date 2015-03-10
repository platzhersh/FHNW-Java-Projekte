package ch.fhnw.conpr.jmm.test;

public class JMM1 {
	private static int value = 0;
	private static volatile boolean ready = false;

	public static void main(String[] args) {
		new Thread("T1") {
			public void run() {
				value = 77;
				ready = true;
			}
		}.start();

		new Thread("T2") {
			public void run() {
				if (ready) {
					System.out.println(value);
				}
			}
		}.start();

	}
}
