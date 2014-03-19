public final class ServicePerson extends Thread {
	private final int serviceTime;
	private final MenuCounter c;
	private boolean closed = false;

	public ServicePerson(MenuCounter c) {
		this.serviceTime = c.serviceTime();
		this.c = c;
		c.registerServicePerson(this);
	}

	@Override
	public synchronized void run() {
		while (!closed || c.queueLength() != 0) {

			if (c.queueLength() == 0) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				try {
					wait(serviceTime);
					c.nextGuest().serve();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
		RestaurantSimulation.log("Service ended.");
	}

	public synchronized void wake() {
		notify();
	}

	public synchronized void close() {
		closed = true;
		notify();
	}
}
