package ch.fhnw.algd1;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CityTest {
	
	int maxHeight;
	City city;

	@Before
	public void setup() {
		maxHeight = 1400;
		city = new City(maxHeight); 
	}
	
	@Test
	public void testNorth() {
		for (int i = 0; i < maxHeight; i++) {
			int expect = i == 0 ? 1 : 2; 
			assertEquals(expect, city.countBuildingsNORTH(i));
			System.out.println("You can see " + city.countBuildingsNORTH(i) + " buildings from pos "+i+" in direction NORTH.");
		}
	}
	
	@Test
	public void testEast() {
		for (int i = 0; i < maxHeight; i++) {
			assertEquals(maxHeight-i, city.countBuildingsEAST(i));
			System.out.println("You can see " + city.countBuildingsEAST(i) + " buildings from pos "+i+" in direction EAST.");
		}
	}
	
	@Test
	public void testSouth() {
		for (int i = 0; i < maxHeight; i++) {
			assertEquals(maxHeight-i, city.countBuildingsSOUTH(i));
			System.out.println("You can see " + city.countBuildingsSOUTH(i) + " buildings from pos "+i+" in direction SOUTH.");
		}
	}
	
	@Test
	public void testWest() {
		for (int i = 0; i < maxHeight; i++) {
			int expect = i == 0 ? 1 : 2; 
			assertEquals(expect, city.countBuildingsWEST(i));
			System.out.println("You can see " + city.countBuildingsWEST(i) + " buildings from pos "+i+" in direction WEST.");
		}
	}

}
