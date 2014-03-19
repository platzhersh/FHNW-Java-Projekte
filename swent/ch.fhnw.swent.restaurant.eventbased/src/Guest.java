/*
 * Created on Mar 8, 2014
 */
/**
 * @author Wolfgang Weck
 */
public final class Guest {
	private final int no;
	private int arrTime;

	public Guest(int no) {
		this.no = no;
	}

	public void enter() {
		int i = (int)(Math.random() * RestaurantSimulation.nofMenuCounters());
		MenuCounter c = RestaurantSimulation.menuCounter(i);
		arrTime = EventQueue.simulationTime();
		RestaurantSimulation.log("Guest " + no + " approaching counter " + i + ", "
				+ c.queueLength() + " already waiting");
		c.arrive(this);
	}

	public void serve() {
		RestaurantSimulation.logDuration("Guest " + no + " needed ",
				EventQueue.simulationTime() - arrTime);
	}

	public int no() {
		return no;
	}
}
