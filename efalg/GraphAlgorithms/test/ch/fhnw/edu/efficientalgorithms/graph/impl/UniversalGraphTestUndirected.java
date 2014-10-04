package ch.fhnw.edu.efficientalgorithms.graph.impl;

import ch.fhnw.edu.efficientalgorithms.graph.Graph;
import ch.fhnw.edu.efficientalgorithms.graph.UndirectedGraphTest;
import ch.fhnw.edu.efficientalgorithms.graph.edges.IntegerEdge;
import ch.fhnw.edu.efficientalgorithms.graph.vertices.IntegerVertex;

/**
 * Tests the implementation as a undirected graph.
 * 
 * @author Martin Schaub
 */
public final class UniversalGraphTestUndirected extends UndirectedGraphTest {

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.GraphTest#getGraph()
	 */
	@Override
	protected Graph<IntegerVertex, IntegerEdge> getGraph() {
		return new UniversalGraph<IntegerVertex, IntegerEdge>(false, IntegerEdge.class, IntegerVertex.class);
	}

}
