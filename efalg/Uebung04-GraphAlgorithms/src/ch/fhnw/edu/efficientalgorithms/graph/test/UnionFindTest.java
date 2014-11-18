package ch.fhnw.edu.efficientalgorithms.graph.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.fhnw.edu.efficientalgorithms.graph.Edge;
import ch.fhnw.edu.efficientalgorithms.graph.Vertex;
import ch.fhnw.edu.efficientalgorithms.graph.auxiliary.UnionFind;
import ch.fhnw.edu.efficientalgorithms.graph.edges.IntegerEdge;
import ch.fhnw.edu.efficientalgorithms.graph.impl.UniversalGraph;
import ch.fhnw.edu.efficientalgorithms.graph.vertices.IntegerVertex;

public class UnionFindTest {
	
	UnionFind<Vertex> uf;
	IntegerVertex a, b, c, d, e;
	IntegerEdge e1, e2, e3, e4, e5, e6, e7, e8;
	UniversalGraph<IntegerVertex, IntegerEdge> graph;
	
	@Before
	public void setup() {
		uf = new UnionFind<Vertex>();
		a = new IntegerVertex(0);
		b = new IntegerVertex(1);
		c = new IntegerVertex(2);
		d = new IntegerVertex(3);
		e = new IntegerVertex(4);
		
		e1 = new IntegerEdge(1);	// ab
		e2 = new IntegerEdge(8);	// ac
		e3 = new IntegerEdge(1);	// ad
		e4 = new IntegerEdge(5);	// ae
		e5 = new IntegerEdge(6);	// be
		e6 = new IntegerEdge(9);	// bc
		e7 = new IntegerEdge(3);	// cd
		e8 = new IntegerEdge(10);	// de
		
		graph = new UniversalGraph(false, IntegerVertex.class, IntegerEdge.class);
		
		graph.addVertex(a);
		graph.addVertex(b);
		graph.addVertex(c);
		graph.addVertex(d);
		graph.addVertex(e);
		
		
		graph.addEdge(a, b, e1);
		graph.addEdge(a, c, e2);
		graph.addEdge(a, d, e3);
		graph.addEdge(a, e, e4);
		graph.addEdge(b, e, e5);
		graph.addEdge(b, c, e6);
		graph.addEdge(c, d, e7);
		graph.addEdge(d, e, e8);
		
		uf.add(a);
		uf.add(b);
		uf.add(c);
		uf.add(d);
		uf.add(e);
	}

	@Test
	public void testContains() {
		assertTrue(uf.contains(a));
		assertFalse(uf.contains(new IntegerVertex(100)));
	}

	@Test
	public void testAdd() {
		assertTrue(!graph.addVertex(e));
		IntegerVertex f = new IntegerVertex(100);
		assertTrue(graph.addVertex(f));
		
		assertTrue(uf.add(f));
		assertFalse(uf.add(f));
	}

	@Test
	public void testFind() {
		assertTrue(uf.find(e).getValue().equals(e));
	}

	@Test
	public void testConnected() {
		//td1 uf.find(t)
	}

	@Test
	public void testConnect() {
		
	}

}
