package ch.fhnw.depa.decorators;
import ch.fhnw.depa.AbstractQuackable;


public class QuackableNotify extends AbstractQuackable {

	static int count;
	AbstractQuackable duck;
	
	public QuackableNotify(AbstractQuackable duck) {
		this.duck = duck;
	}
	
	@Override
	public void quack() {
		duck.quack();
		notifyObservers();
	}
	
	public static int getNumberOfQuacks() {
		return count;
	}

	@Override
	public String getType() {
		return duck.getType();
	}
}
