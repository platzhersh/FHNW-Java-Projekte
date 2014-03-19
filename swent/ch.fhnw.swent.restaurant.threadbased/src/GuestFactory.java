/*
 * Created on Mar 8, 2014
 */
/**
 * @author Wolfgang Weck
 */
public final class GuestFactory extends Thread {
	private final int nofGuests, meanTime;

	public GuestFactory(int nofGuests, int meanTime) {
		this.nofGuests = nofGuests;
		this.meanTime = meanTime;
	}

	private static int expRand(double mean) {
		return (int)(-mean * Math.log(1 - Math.random()) + 0.5);
	}

	@Override
	public synchronized void run() {
		for (int i = 0; i < nofGuests; i++) {
			new Guest(i + 1).start();
			long next = System.currentTimeMillis() + expRand(meanTime);
			while (next > System.currentTimeMillis()) {
				try {
					wait(next - System.currentTimeMillis());
				}
				catch (InterruptedException e) {}
			}
		}
	}
}
