package ch.fhnw.ds.graph.server;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import ch.fhnw.ds.graph.server.GraphService.Node;

public class GraphServiceTest {
	
	Node n1,n2,n3,n4;
	GraphService service;
	
	@Before
	public void setUp(){
		service = new GraphServiceImpl();
		n1 = new Node();
		n2 = new Node();
		n3 = new Node();
		n4 = new Node();
	}
	
	@Test
	public void test1() {
		n1.getChildren().add(n2);
		n1.getChildren().add(n3);
		n2.getChildren().add(n4);
		assertTrue(service.isTree(n1));
		assertTrue(service.isDAG(n1));
	}
	
	@Test
	public void test2() {
		n1.getChildren().add(n2);
		n1.getChildren().add(n3);
		n2.getChildren().add(n4);
		n3.getChildren().add(n4);
		assertFalse(service.isTree(n1));
		assertTrue(service.isDAG(n1));
	}

	@Test
	public void test3() {
		n1.getChildren().add(n2);
		n2.getChildren().add(n3);
		n3.getChildren().add(n4);
		n4.getChildren().add(n1);
		assertFalse(service.isTree(n1));
		assertFalse(service.isDAG(n1));
	}

}
