package ch.fhnw.edu.efficientalgorithms.graph.auxiliary;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ch.fhnw.edu.efficientalgorithms.graph.Vertex;
import ch.fhnw.edu.efficientalgorithms.graph.auxiliary.UnionFind;
import ch.fhnw.edu.efficientalgorithms.graph.auxiliary.UnionFind.TDecorator;
import ch.fhnw.edu.efficientalgorithms.graph.edges.IntegerEdge;
import ch.fhnw.edu.efficientalgorithms.graph.impl.UniversalGraph;
import ch.fhnw.edu.efficientalgorithms.graph.vertices.IntegerVertex;

public class UnionFindTest {
	
	UnionFind<IntegerVertex> uf;
	IntegerVertex a, b, c, d, e;
	IntegerEdge e1, e2, e3, e4, e5, e6, e7, e8;
	UniversalGraph<IntegerVertex, IntegerEdge> graph;
	
	@Before
	public void setup() {
		uf = new UnionFind<IntegerVertex>();
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
		uf.connect(a, b);
		uf.connect(b, c);
		uf.connect(d, e);
		TDecorator td1 = uf.find(a);
		TDecorator td2 = uf.find(b);
		TDecorator td3 = uf.find(c);
		TDecorator td4 = uf.find(d);
		TDecorator td5 = uf.find(e);
		
		assertTrue(td1.connectedTo(td2));
		assertTrue(td1.connectedTo(td3));
		assertTrue(td2.connectedTo(td1));
		assertTrue(td2.connectedTo(td3));
		assertTrue(td3.connectedTo(td1));
		assertTrue(td3.connectedTo(td2));
		
		assertTrue(td4.connectedTo(td5));
		assertTrue(td5.connectedTo(td4));
		
		uf.connect(e,a);
		
		assertTrue(td1.connectedTo(td5));
		assertTrue(td1.connectedTo(td4));
		assertTrue(td2.connectedTo(td5));
		assertTrue(td2.connectedTo(td4));
		assertTrue(td3.connectedTo(td5));
		assertTrue(td3.connectedTo(td4));
		assertTrue(td4.connectedTo(td1));
		assertTrue(td4.connectedTo(td2));
		assertTrue(td4.connectedTo(td3));
		assertTrue(td5.connectedTo(td1));
		assertTrue(td5.connectedTo(td2));
		assertTrue(td5.connectedTo(td3));
		
	}
	
	@Test
	public void testConnectedExtended() {
		uf.connect(a, b);
		TDecorator td1 = uf.find(a);
		TDecorator td2 = uf.find(b);
		TDecorator td3 = uf.find(c);
		
		assertTrue(td1.connectedTo(td2));
		
		assertTrue(!td1.connectedTo(td3));
		
		uf.connect(a, c);
		
		assertTrue(td1.connectedTo(td3));
		
		assertTrue(td2.connectedTo(td3));
		
	}
}
