package patterns.observer.sensor;

import patterns.observer.Observable;

public class QuittingObserver extends AbstractObserver {

	private final int maxreadings;
	private int countreadings = 0;

	protected QuittingObserver(Sensor s, int maxreadings) {
		super(s);
		this.maxreadings = maxreadings;
	}

	@Override
	public void update(Observable source) {
		if (source == sensor) {
			if (++countreadings == maxreadings) {
				sensor.removeObserver(this);
				System.out.println("Und tsch�ss..!");
			}
		}
	}

}
