package ch.fhnw.depa.factory;

import ch.fhnw.depa.Quackable;

public abstract class AbstractDuckFactory {
	
	public abstract Quackable createMallardDuck();
	public abstract Quackable createRubberDuck();
	public abstract Quackable createDuckCall();

}
