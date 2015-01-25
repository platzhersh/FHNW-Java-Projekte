package ch.fhnw.depa.observe;

import ch.fhnw.depa.AbstractQuackable;

public interface Observer {
	
	public void update(AbstractQuackable duck);

}
