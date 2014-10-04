package ch.fhnw.edu.efficientalgorithms.graph.impl;

import ch.fhnw.edu.efficientalgorithms.graph.DirectedGraphTest;
import ch.fhnw.edu.efficientalgorithms.graph.Graph;
import ch.fhnw.edu.efficientalgorithms.graph.edges.IntegerEdge;
import ch.fhnw.edu.efficientalgorithms.graph.vertices.IntegerVertex;

/**
 * Tests the implementation as directed graph.
 * 
 * @author Martin Schaub
 */
public final class UniversalGraphTestDirected extends DirectedGraphTest {

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.GraphTest#getGraph()
	 */
	@Override
	protected Graph<IntegerVertex, IntegerEdge> getGraph() {
		return new UniversalGraph<IntegerVertex, IntegerEdge>(true, IntegerEdge.class, IntegerVertex.class);
	}

}
