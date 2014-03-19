/*
 * Created on Mar 8, 2014
 */
/**
 * @author Wolfgang Weck
 */
public final class RestaurantSimulation {
	private static final int NOF_GUESTS = 10, SEC = 10;
	private static final int SERVICE_TIME = 45 * SEC,
			MEAN_ARRIVAL_TIME = 20 * SEC;
	private static long startTime;
	private static MenuCounter[] menuCounters;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		startTime = System.currentTimeMillis();
		menuCounters = new MenuCounter[2];
		for (int i = 0; i < menuCounters.length; i++) {
			menuCounters[i] = new MenuCounter(SERVICE_TIME);
			ServicePerson p = new ServicePerson(menuCounters[i]);
			p.start();
		}
		new GuestFactory(NOF_GUESTS, MEAN_ARRIVAL_TIME).run();
		log("closing entrance");
		for (MenuCounter c : menuCounters)
			c.close();
	}

	public static int nofMenuCounters() {
		return menuCounters.length;
	}

	public static MenuCounter menuCounter(int index) {
		return menuCounters[index];
	}

	private static long now() {
		return (System.currentTimeMillis() - startTime) / SEC;
	}

	public static void log(String s) {
		System.out.println(now() + ": " + s);
	}

	public static void logDuration(String s, long duration) {
		log(s + duration / SEC);
	}
}
