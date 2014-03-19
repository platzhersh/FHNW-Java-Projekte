/*
 * Created on Mar 8, 2014
 */
/**
 * @author Wolfgang Weck
 */
public final class Guest extends Thread {
	private final int no;
	private boolean served = false;

	public Guest(int no) {
		this.no = no;
	}

	@Override
	public synchronized void run() {
		int i = (int)(Math.random() * RestaurantSimulation.nofMenuCounters());
		MenuCounter c = RestaurantSimulation.menuCounter(i);
		long arrTime = System.currentTimeMillis();
		RestaurantSimulation.log("Guest " + no() + " approaching counter " + i
				+ ", " + c.queueLength() + " already waiting");
		c.arrive(this);
		while (!served) {
			try {
				wait();
			}
			catch (InterruptedException e) {}
		}
		RestaurantSimulation.logDuration("Guest " + no + " needed ",
				System.currentTimeMillis() - arrTime);
	}

	public synchronized void serve() {
		served = true;
		notify();
	}

	public int no() {
		return no;
	}
}
