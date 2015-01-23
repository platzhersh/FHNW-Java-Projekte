package ch.fhnw.depa.commands;

import ch.fhnw.depa.things.Light;

public class LightOnCommand implements Command {

	Light light;
	
	@Override
	public void execute() {
		light.on();
	}
	
	public LightOnCommand(Light l) {
		light = l;
	}

}
