package ch.fhnw.depa.ducks;

import java.util.ArrayList;
import java.util.Iterator;

import ch.fhnw.depa.Quackable;


/***
 * Composite for Quackables
 * type safe implementation instead of transparent
 *
 * @author chregi
 *
 */
public class Flock implements Quackable {

	ArrayList<Quackable> quackers = new ArrayList<Quackable>();
	
	public void add(Quackable quacker) {
		quackers.add(quacker);
	}
	
	@Override
	public void quack() {
		Iterator<Quackable> iterator = quackers.iterator();
		while (iterator.hasNext()) {
			Quackable quacker = iterator.next();
			quacker.quack();
		}
	}

}
