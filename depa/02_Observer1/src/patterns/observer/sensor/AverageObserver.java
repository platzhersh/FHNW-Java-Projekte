package patterns.observer.sensor;

import patterns.observer.Observable;

public class AverageObserver extends AbstractObserver {

	private int readings = 0;
	private double average = 0.0d;

	public AverageObserver(Sensor s) {
		super(s);
	}

	@Override
	public void update(Observable source) {
		if (sensor == source) {
			if (readings == 0) {
				average = sensor.getTemperature();
			} else {
				average = (readings * average + sensor.getTemperature())
						/ (readings + 1);
			}
			readings++;
			System.out.println("Average: " + average);
		}
	}

	public double getAverage() {
		return average;
	}

}
