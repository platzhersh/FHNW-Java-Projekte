package ch.fhnw.depa.factory;

import ch.fhnw.depa.Quackable;
import ch.fhnw.depa.decorators.*;
import ch.fhnw.depa.ducks.*;

public class DuckFactory extends AbstractDuckFactory {

	@Override
	public Quackable createMallardDuck() {
		return new MallardDuck();
	}

	@Override
	public Quackable createRubberDuck() {
		return new RubberDuck();
	}

	@Override
	public Quackable createDuckCall() {
		return new DuckCall();
	}

}
