package ch.fhnw.depa.factory;

import ch.fhnw.depa.AbstractQuackable;
import ch.fhnw.depa.decorators.*;
import ch.fhnw.depa.ducks.*;

public class CountingDuckFactory extends AbstractDuckFactory {

	@Override
	public AbstractQuackable createMallardDuck() {
		return new QuackableCount(new MallardDuck());
	}

	@Override
	public AbstractQuackable createRubberDuck() {
		return new QuackableCount(new RubberDuck());
	}

	@Override
	public AbstractQuackable createDuckCall() {
		return new QuackableCount(new DuckCall());
	}

}
