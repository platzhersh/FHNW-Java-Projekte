package ch.fhnw.depa.factory;

import java.util.List;

import ch.fhnw.depa.AbstractQuackable;
import ch.fhnw.depa.decorators.QuackableCount;
import ch.fhnw.depa.decorators.QuackableNotify;
import ch.fhnw.depa.ducks.DuckCall;
import ch.fhnw.depa.ducks.MallardDuck;
import ch.fhnw.depa.ducks.RubberDuck;
import ch.fhnw.depa.observe.Observer;

public class ObserveCountingDuckFactory extends AbstractDuckFactory {
	
	List<Observer> observers;
	
	public ObserveCountingDuckFactory(List<Observer> observers) {
		this.observers = observers;
	}

	@Override
	public AbstractQuackable createMallardDuck() {
		return registerObservers(new QuackableNotify(new QuackableCount(new MallardDuck())));
	}

	@Override
	public AbstractQuackable createRubberDuck() {
		return registerObservers(new QuackableNotify(new QuackableCount(new RubberDuck())));
	}

	@Override
	public AbstractQuackable createDuckCall() {
		return registerObservers(new QuackableNotify(new QuackableCount(new DuckCall())));
	}
	

	// helper methods
	
	private AbstractQuackable registerObservers(AbstractQuackable duck) {
		for (Observer o : observers) {
			duck.registerObserver(o);
		}
		return duck;
	}

}
