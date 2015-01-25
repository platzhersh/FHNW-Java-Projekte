package ch.fhnw.depa.ducks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ch.fhnw.depa.AbstractQuackable;
import ch.fhnw.depa.observe.Observer;


/***
 * Composite for Quackables
 * type safe implementation instead of transparent
 *
 * @author chregi
 *
 */
public class Flock extends AbstractQuackable {

	List<AbstractQuackable> quackers = new ArrayList<>();
	
	public void add(AbstractQuackable quacker) {
		quackers.add(quacker);
	}
	
	
	@Override
	public void quack() {
		for (AbstractQuackable a : quackers) {
			a.quack();
		}
	}

	@Override
	public String getType() {
		StringBuilder sb = new StringBuilder();
		for (AbstractQuackable a : quackers) {
			sb.append(a.getType()  + ", ");
		}
		return sb.toString();
	}
	
	@Override
	public void registerObserver(Observer observer) {
		Iterator<AbstractQuackable> iterator = quackers.iterator();
		while (iterator.hasNext()) {
			AbstractQuackable quacker = iterator.next();
			quacker.registerObserver(observer);
		}
	}

}
