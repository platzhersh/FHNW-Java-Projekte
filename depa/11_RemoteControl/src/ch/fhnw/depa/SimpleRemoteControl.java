package ch.fhnw.depa;

import ch.fhnw.depa.commands.Command;

public class SimpleRemoteControl {
	
	Command slot;
	public SimpleRemoteControl() {
		
	}
	
	public void setCommand(Command command) {
		slot = command;
	}
	
	public void buttonWasPressed() {
		slot.execute();
	}
}