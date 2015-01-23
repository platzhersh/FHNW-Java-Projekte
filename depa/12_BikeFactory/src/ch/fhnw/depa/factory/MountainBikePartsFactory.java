package ch.fhnw.depa.factory;

import ch.fhnw.depa.parts.*;

public class MountainBikePartsFactory extends PartsFactory {

	@Override
	public MountainBikeFrame createFrame() {
		return new MountainBikeFrame();
	}

	@Override
	public MountainBikeBell createBell() {
		return new MountainBikeBell();
	}

}
