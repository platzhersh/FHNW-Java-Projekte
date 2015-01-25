package ch.fhnw.depa.ducks;

import ch.fhnw.depa.Quackable;

/***
 * 
 * @author chregi
 *
 */
public class DuckCall implements Quackable {

	@Override
	public void quack() {
		System.out.println("Kwak!");
	}

}
