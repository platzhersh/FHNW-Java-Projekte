package patterns.observer.once;


public class Sensor extends Observable {
	private double value;

	public Sensor(double value) {
		this.value = value;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
		notifyObservers();
	}

}
