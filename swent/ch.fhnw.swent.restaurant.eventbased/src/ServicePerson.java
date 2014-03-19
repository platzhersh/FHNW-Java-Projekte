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



	}

	public void wake() {
		if (!active) serveNext();
	}
}
