package ch.fhnw.depa.factory;

import ch.fhnw.depa.parts.Bell;
import ch.fhnw.depa.parts.Frame;

public abstract class PartsFactory {

	public abstract Frame createFrame();
	public abstract Bell createBell();
	
}
