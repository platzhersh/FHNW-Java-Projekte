package ch.fhnw.depa.observe;

public interface QuackObservable {
	
	public void registerObserver(Observer observer);
	public void notifyObservers();

}
