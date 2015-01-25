package ch.fhnw.depa.ducks;

import ch.fhnw.depa.Quackable;

/***
 * 
 * 
 * @author chregi
 *
 */
public class RubberDuck implements Quackable {

	@Override
	public void quack() {
		System.out.println("Squeek!");
	}

}
