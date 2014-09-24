package patterns.observer.sensor;

import patterns.observer.Observer;

public abstract class AbstractObserver implements Observer {

	protected Sensor sensor;

	protected AbstractObserver(Sensor s) {
		sensor = s;
		sensor.addObserver(this);
	}

}
