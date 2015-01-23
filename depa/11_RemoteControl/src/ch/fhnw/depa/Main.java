package ch.fhnw.depa;

import ch.fhnw.depa.commands.LightOffCommand;
import ch.fhnw.depa.commands.LightOnCommand;
import ch.fhnw.depa.things.Light;

public class Main {

	public static void main(String[] args) {
		SimpleRemoteControl remote = new SimpleRemoteControl();
		Light l = new Light();
		LightOnCommand c = new LightOnCommand(l);
		LightOffCommand c2 = new LightOffCommand(l);
		
		remote.setCommand(c);
		remote.buttonWasPressed();
		
		remote.setCommand(c2);
		remote.buttonWasPressed();
	}
	

}
