package geometry.test;

import static org.junit.Assert.*;

import java.awt.Point;

import junit.framework.Assert;
import geometry.GeometryHelper;

import org.junit.Test;

public class GeometryHelperTest {

	@Test
	public void testCounterClockWise() {
		fail("Not yet implemented");
	}

	@Test
	public void testLineIntersection() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetInterceptionPoint() {
		Point a = new Point(1,1);
		Point b = new Point(3,1);
		Point c = new Point(2,2);
		Point d = new Point(2,0);
		
		Point r = GeometryHelper.getInterceptionPoint(a, b, c, d);
		
		Assert.assertEquals(2, r.x);
		Assert.assertEquals(1, r.y);
	}

}
