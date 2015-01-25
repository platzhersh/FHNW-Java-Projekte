package ch.fhnw.depa.decorators;
import ch.fhnw.depa.Quackable;


public class QuackableCount implements Quackable {

	static int count;
	Quackable duck;
	
	public QuackableCount(Quackable duck) {
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

}
