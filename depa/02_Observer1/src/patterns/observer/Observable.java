package patterns.observer;

import java.util.LinkedList;
import java.util.List;

public class Observable {
	private List<Observer> observers = new LinkedList<Observer>();

	public void addObserver(Observer o) {
		observers.add(o);
	}

	public void removeObserver(Observer o) {
		observers.remove(o);
	}

	protected void notifyObservers() {
		for (Observer obs : observers) {
			obs.update(this);
		}
	}
}
