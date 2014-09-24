package patterns.observer.sensor;

import java.io.PrintStream;

import patterns.observer.Observable;
import patterns.observer.Observer;

public class ConsoleObserver implements Observer {

	private static final PrintStream CONSOLE = System.err;

	@Override
	public void update(Observable source) {
		int t = ((Sensor) source).getTemperature();
		CONSOLE.println("Sensor has changed to: " + t);
	}

}
