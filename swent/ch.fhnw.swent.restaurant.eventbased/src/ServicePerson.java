public final class ServicePerson {
	private final int serviceTime;
	private final MenuCounter c;
	private boolean active = false;

	public ServicePerson(MenuCounter c) {
		this.serviceTime = c.serviceTime();
		this.c = c;
		c.registerServicePerson(this);
	}

	private void serveNext() {

		if (c.queueLength() > 0) {
			Guest g = c.nextGuest();
			active = true;
			RestaurantSimulation.log("Now serving guest " + g.no());
			EventQueue.schedule(new ServiceEvent(g), serviceTime);
		} else RestaurantSimulation.log("ServicePerson idle");

	}

	public void wake() {
		if (!active) serveNext();
	}
	private class ServiceEvent implements Event {
		private final Guest g;

		ServiceEvent(Guest g) {
			this.g = g;
		}

		public void handle(int time) {
			g.serve();
			active = false;
			serveNext();
		}
	}
}
