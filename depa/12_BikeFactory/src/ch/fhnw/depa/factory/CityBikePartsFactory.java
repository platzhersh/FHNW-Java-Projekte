package ch.fhnw.depa.factory;

import ch.fhnw.depa.parts.*;

public class CityBikePartsFactory extends PartsFactory {

	@Override
	public CityBikeFrame createFrame() {
		return new CityBikeFrame();
	}

	@Override
	public CityBikeBell createBell() {
		return new CityBikeBell();
	}

}
