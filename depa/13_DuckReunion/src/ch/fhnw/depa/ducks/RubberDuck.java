package ch.fhnw.depa.ducks;

import ch.fhnw.depa.AbstractQuackable;

/***
 * 
 * 
 * @author chregi
 *
 */
public class RubberDuck extends AbstractQuackable {

	@Override
	public String getType() {
		return "RubberDuck";
	}

	@Override
	public void quack() {
		System.out.println("Squeak!");
	}


}
