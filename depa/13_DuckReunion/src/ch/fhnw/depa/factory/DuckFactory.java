package ch.fhnw.depa.factory;

import ch.fhnw.depa.AbstractQuackable;
import ch.fhnw.depa.ducks.*;

public class DuckFactory extends AbstractDuckFactory {

	@Override
	public AbstractQuackable createMallardDuck() {
		return new MallardDuck();
	}

	@Override
	public AbstractQuackable createRubberDuck() {
		return new RubberDuck();
	}

	@Override
	public AbstractQuackable createDuckCall() {
		return new DuckCall();
	}

}
