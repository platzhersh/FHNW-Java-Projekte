package ch.fhnw.depa;

import ch.fhnw.depa.factory.*;
import ch.fhnw.depa.parts.*;

public class Client {
	
	public static void main(String[] args) {
		PartsFactory f = new MountainBikePartsFactory();
		
		Frame frame = f.createFrame();
		Bell bell = f.createBell();
		
		frame.paintFrame();
		bell.ringBell();
		
		f = new CityBikePartsFactory();
		
		Frame frame2 = f.createFrame();
		Bell bell2 = f.createBell();
		
		frame2.paintFrame();
		bell2.ringBell();
	}
}
