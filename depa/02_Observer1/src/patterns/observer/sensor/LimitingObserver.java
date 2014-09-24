/**
 * 
 */
package patterns.observer.sensor;

import patterns.observer.Observable;

/**
 * @author christoph.denzler
 * 
 */
public class LimitingObserver extends AbstractObserver {

	private int upperlimit;

	public LimitingObserver(Sensor s, int limit) {
		super(s);
		upperlimit = limit;
	}

	@Override
	public void update(Observable source) {
		if (source == sensor && sensor.getTemperature() > upperlimit) {
			sensor.setTemperature(upperlimit);
		}
	}

}
