package ch.fhnw.depa;

import java.util.ArrayList;
import java.util.List;

import ch.fhnw.depa.observe.Observer;

/***
 * 
 * Quackables only need to do one thing well: Quack!
 * 
 * @author chregi
 *
 */
public abstract class AbstractQuackable {
	
	List<Observer> observers;
	
	public AbstractQuackable() {
		observers = new ArrayList<>();
	}
	
	public void registerObserver(Observer observer) {
		if (!observers.contains(observer) && null != observer)
			observers.add(observer);
	}
	
	public void removeObserver(Observer observer) {
		observers.remove(observer);
	}
	
	public void notifyObservers() {
		for (Observer o : observers) {
			o.update(this);
		}
	}
	
	public abstract String getType();
	
	public abstract void quack();

}
