package ch.fhnw.depa.observe;

import ch.fhnw.depa.AbstractQuackable;

public class Quackologist implements Observer {

	@Override
	public void update(AbstractQuackable duck) {
		System.out.println(duck.getType() + " hat gerade gequakt");
	}

	
	

}
