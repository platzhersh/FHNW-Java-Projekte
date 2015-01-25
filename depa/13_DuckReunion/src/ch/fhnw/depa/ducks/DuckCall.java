package ch.fhnw.depa.ducks;

import ch.fhnw.depa.AbstractQuackable;

/***
 * 
 * @author chregi
 *
 */
public class DuckCall extends AbstractQuackable {


	@Override
	public String getType() {
		return "DuckCall";
	}

	@Override
	public void quack() {
		System.out.println("Kwak!");
	}

}
