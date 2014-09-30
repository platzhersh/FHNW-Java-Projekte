package patterns.observer.once;

public class PrintObserver implements Observer {
	private String name;

	public PrintObserver(String name) {
		this.name = name;
	}

	@Override
	public void update(Observable source) {
		Sensor s = (Sensor) source;
		System.out.println(name + ": Value has changed: " + s.getValue());
	}

}
