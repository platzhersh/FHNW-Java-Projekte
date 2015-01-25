package ch.fhnw.depa.ducks;

import ch.fhnw.depa.AbstractQuackable;

/***
 * 
 * @author chregi
 *
 */
public class MallardDuck extends AbstractQuackable {

	@Override
	public String getType() {
		return "MallardDuck";
	}

	@Override
	public void quack() {
		System.out.println("Quack!");
	}


}
