package ch.fhnw.depa.factory;

import ch.fhnw.depa.AbstractQuackable;

public abstract class AbstractDuckFactory {
	
	public abstract AbstractQuackable createMallardDuck();
	public abstract AbstractQuackable createRubberDuck();
	public abstract AbstractQuackable createDuckCall();

}
