package ch.fhnw.depa.commands;

import ch.fhnw.depa.things.Light;

public class LightOffCommand implements Command {

	Light light;
	
	@Override
	public void execute() {
		light.off();
	}
	
	public LightOffCommand(Light l) {
		light = l;
	}

}
