package uebung3.voronoi.test;

import static org.junit.Assert.*;
import uebung3.voronoi.helpers.Point;
import uebung3.voronoi.helpers.Vector;

import org.junit.Before;
import org.junit.Test;

import uebung3.voronoi.helpers.Edge;

public class EdgeTest {

	Edge e1;
	Edge e2;
	Edge e3;
	
	@Before
	public void setUp(){
		e1 = new Edge(new Point(2,2), new Point(4,2));
		e2 = new Edge(new Point(4,1), new Point(2,3));
		e3 = new Edge(new Point(4,1), new Point(4,3));
	}
	
	@Test
	public void testInterceptionPoint() {
		Point p = Edge.interceptionPoint(e1, e2);
		System.out.println(p);
		assertEquals((new Point(3,2)).equals(p), true);
	}
	
	@Test
	public void testInterceptionPointTwice(){
		Point p1 = Edge.interceptionPoint(e1, e2);
		e1.setLeftEnd(e1.getEnd());
		e1.setRightEnd(p1);
		Point p2 = Edge.interceptionPoint(e1, e2);
		assertEquals(p1.equals(p2),true);
	}
	
	@Test
	public void testStartAndEndpoint() {
		Point p1 = new Point(0,1);
		Point p2 = new Point(0,5);
		Edge e = new Edge(p2,p1);
		assertTrue(e.start.y < e.end.y);
		assertTrue(e.start.y == p1.y);
	}
	
	@Test
	public void testRegionLeftAndRight() {
		Point p1 = new Point(0,1);
		Point p2 = new Point(0,5);
		Edge e = new Edge(p2,p1);
		
		Point left = new Point(3,0);
		Point right = new Point(-3,0);
		
		assertEquals(-1, Vector.ccw(e.start, e.end, left));
		assertEquals(1, Vector.ccw(e.start, e.end, right));
	}
	
	/*
	@Test
	public void testParallel() {
		Point p = Edge.interceptionPoint(e1, e1);
		System.out.println(p);
		assertEquals((new Point(3,2)).equals(p), true);
	}
	
	@Test
	public void testVertical() {
		Point p = Edge.interceptionPoint(e1, e3);
		System.out.println(p);
		assertEquals((new Point(3,2)).equals(p), true);
	}*/

}
