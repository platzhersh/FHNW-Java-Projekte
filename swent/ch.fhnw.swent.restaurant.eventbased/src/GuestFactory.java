/*
 * Created on Mar 8, 2014
 */
/**
 * @author Wolfgang Weck
 */
public final class GuestFactory {
	private static int expRand(double mean) {
		return (int)(-mean * Math.log(1 - Math.random()) + 0.5);
	}

	public static void scheduleAll(int nofGuests, int meanTime) {
		int time = 0;
		for (int i = 0; i < nofGuests; i++) {
			EventQueue.schedule(new ArrivalEvent(i + 1), time);
			time += expRand(meanTime);
		}
		EventQueue.schedule(new Event() {
			public void handle(int time) {
				RestaurantSimulation.log("closing entrance");
			}
		}, time);
	}

	private static class ArrivalEvent implements Event {
		final int no;

		ArrivalEvent(int no) {
			this.no = no;
		}

		public void handle(int time) {
			new Guest(no).enter();
		}
	}
}
