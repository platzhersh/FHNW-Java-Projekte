package uebung3.voronoi.test;

import static org.junit.Assert.*;
import geometry.Point;

import org.junit.Before;
import org.junit.Test;

import uebung3.voronoi.Edge;

public class EdgeTest {

	Edge e1;
	Edge e2;
	Edge e3;
	
	@Before
	public void setUp(){
		e1 = new Edge(new Point(2,2), new Point(4,2),null,null,null,null);
		e2 = new Edge(new Point(4,1), new Point(2,3),null,null,null,null);
		e3 = new Edge(new Point(4,1), new Point(4,3),null,null,null,null);
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
