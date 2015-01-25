package ch.fhnw.depa.factory;

import ch.fhnw.depa.Quackable;
import ch.fhnw.depa.decorators.*;
import ch.fhnw.depa.ducks.*;

public class CountingDuckFactory extends AbstractDuckFactory {

	@Override
	public Quackable createMallardDuck() {
		return new QuackableCount(new MallardDuck());
	}

	@Override
	public Quackable createRubberDuck() {
		return new QuackableCount(new RubberDuck());
	}

	@Override
	public Quackable createDuckCall() {
		return new QuackableCount(new DuckCall());
	}

}
