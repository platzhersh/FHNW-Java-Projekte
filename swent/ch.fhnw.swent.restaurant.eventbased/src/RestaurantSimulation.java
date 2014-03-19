/*
 * Created on Mar 8, 2014
 */
/**
 * @author Wolfgang Weck
 */
public final class RestaurantSimulation {
	private static final int NOF_GUESTS = 100;
	private static final int SERVICE_TIME = 45, MEAN_ARRIVAL_TIME = 20;
	private static MenuCounter[] menuCounters;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		menuCounters = new MenuCounter[2];
		for (int i = 0; i < menuCounters.length; i++) {
			menuCounters[i] = new MenuCounter(SERVICE_TIME);
			new ServicePerson(menuCounters[i]);
		}
		GuestFactory.scheduleAll(NOF_GUESTS, MEAN_ARRIVAL_TIME);
		EventQueue.run();
	}

	public static int nofMenuCounters() {
		return menuCounters.length;
	}

	public static MenuCounter menuCounter(int index) {
		return menuCounters[index];
	}

	public static void log(String s) {
		System.out.println(EventQueue.simulationTime() + ": " + s);
	}

	public static void logDuration(String s, long duration) {
		log(s + duration);
	}
}
