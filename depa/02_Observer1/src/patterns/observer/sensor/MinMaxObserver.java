package patterns.observer.sensor;

import patterns.observer.Observable;

public class MinMaxObserver extends AbstractObserver {

	private int min = Integer.MAX_VALUE;
	private int max = Integer.MIN_VALUE;

	public MinMaxObserver(Sensor s) {
		super(s);
	}

	@Override
	public void update(Observable source) {
		if (source == sensor) {
			int temp = sensor.getTemperature();
			if (temp > max) {
				System.out.println("New maximum: " + temp);
				max = temp;
			}
			if (temp < min) {
				System.out.println("New minimum: " + temp);
				min = temp;
			}
		}
	}

	public int getMinimum() {
		return min;
	}

	public int getMaximum() {
		return max;
	}

}
