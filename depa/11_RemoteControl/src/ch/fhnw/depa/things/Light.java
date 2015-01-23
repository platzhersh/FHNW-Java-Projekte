package ch.fhnw.depa.things;

public class Light {
	boolean isOn;
	
	public Light() {
		isOn = false;
	}
	
	public void on() {
		System.out.println("Light on");
		isOn = true;
	}
	
	public void off() {
		System.out.println("Light off");
		isOn = false;
	}
}
