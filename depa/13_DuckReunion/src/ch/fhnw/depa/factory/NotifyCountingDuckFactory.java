package ch.fhnw.depa.factory;

import ch.fhnw.depa.AbstractQuackable;
import ch.fhnw.depa.decorators.QuackableCount;
import ch.fhnw.depa.decorators.QuackableNotify;
import ch.fhnw.depa.ducks.DuckCall;
import ch.fhnw.depa.ducks.MallardDuck;
import ch.fhnw.depa.ducks.RubberDuck;

public class NotifyCountingDuckFactory extends AbstractDuckFactory {

	@Override
	public AbstractQuackable createMallardDuck() {
		return new QuackableNotify(new QuackableCount(new MallardDuck()));
	}

	@Override
	public AbstractQuackable createRubberDuck() {
		return new QuackableNotify(new QuackableCount(new RubberDuck()));
	}

	@Override
	public AbstractQuackable createDuckCall() {
		return new QuackableNotify(new QuackableCount(new DuckCall()));
	}

}
