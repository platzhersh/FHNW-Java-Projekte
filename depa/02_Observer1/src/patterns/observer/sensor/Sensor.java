package patterns.observer.sensor;

import patterns.observer.Observable;

public class Sensor extends Observable {

	private int temperature;

	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int val) {
		temperature = val;
		notifyObservers();
	}

}
