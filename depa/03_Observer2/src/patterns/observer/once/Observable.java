package patterns.observer.once;

import java.util.Iterator;
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

	public void notifyObservers() {
		//for (Observer obs : observers) {
		// obs.update(this);
		List<Observer> l = new LinkedList<Observer>(observers);
		for (Iterator<Observer> obs = l.iterator(); obs.hasNext(); ) {
			Observer o = obs.next();
			o.update(this);
		}
	}
}
