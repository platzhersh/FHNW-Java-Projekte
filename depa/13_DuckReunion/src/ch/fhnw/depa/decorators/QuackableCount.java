package ch.fhnw.depa.decorators;
import ch.fhnw.depa.AbstractQuackable;


public class QuackableCount extends AbstractQuackable {

	static int count;
	AbstractQuackable duck;
	
	public QuackableCount(AbstractQuackable duck) {
		this.duck = duck;
	}
	
	@Override
	public void quack() {
		count++;
		duck.quack();
	}
	
	public static int getNumberOfQuacks() {
		return count;
	}

	@Override
	public String getType() {
		return duck.getType();
	}
}
