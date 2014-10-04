package ch.fhnw.edu.efficientalgorithms.graph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ch.fhnw.edu.efficientalgorithms.graph.edges.IntegerEdge;
import ch.fhnw.edu.efficientalgorithms.graph.vertices.IntegerVertex;

/**
 * Extension of the Graph's testcases for directed graphs.
 * 
 * @author Martin Schaub
 */
public abstract class DirectedGraphTest extends GraphTest {

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.graph.GraphTest#addEdge(ch.fhnw.edu.efficientalgorithms.graph.vertices.IntegerVertex
	 * , ch.fhnw.edu.efficientalgorithms.graph.vertices.IntegerVertex,
	 * ch.fhnw.edu.efficientalgorithms.graph.edges.IntegerEdge, ch.fhnw.edu.efficientalgorithms.graph.Graph)
	 */
	@Override
	protected void addEdge(final IntegerVertex from, final IntegerVertex to, final IntegerEdge e,
			final Graph<IntegerVertex, IntegerEdge> graph) {
		assertFalse("Edge is already in the graph. Test error!", graph.getEdges().contains(e));

		int i = graph.getNumOfEdges();

		assertTrue(graph.addEdge(from, to, e));
		assertFalse(graph.addEdge(from, to, e));

		assertEquals(i + 1, graph.getNumOfEdges());
		assertEquals(i + 1, graph.getEdges().size());
		checkEdgeAddition(from, to, e, graph);

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.graph.GraphTest#removeEdge(ch.fhnw.edu.efficientalgorithms.graph.vertices.IntegerVertex
	 * , ch.fhnw.edu.efficientalgorithms.graph.vertices.IntegerVertex,
	 * ch.fhnw.edu.efficientalgorithms.graph.edges.IntegerEdge, ch.fhnw.edu.efficientalgorithms.graph.Graph)
	 */
	@Override
	protected void removeEdge(final IntegerVertex from, final IntegerVertex to, final IntegerEdge e,
			final Graph<IntegerVertex, IntegerEdge> graph) {
		assertTrue("Edge is not in the graph. Test error!", graph.getEdges().contains(e));

		int i = graph.getNumOfEdges();

		assertTrue(graph.removeEdge(e));
		assertFalse(graph.removeEdge(e));

		assertEquals(i - 1, graph.getNumOfEdges());
		assertEquals(i - 1, graph.getEdges().size());
		checkEdgeRemoval(from, to, e, graph);
	}

	/**
	 * Tests whether the graph is really directed or not.
	 */
	@Test
	public void testIsDirected() {
		assertTrue(getGraph().isDirected());
	}

	/**
	 * Tests if two separate edges can be added in both directions.
	 */
	@Test
	public void testAddBothDirections() {
		Graph<IntegerVertex, IntegerEdge> graph = getGraph();

		IntegerVertex v1 = new IntegerVertex(1);
		IntegerVertex v2 = new IntegerVertex(2);

		addVertex(v1, graph);
		addVertex(v2, graph);

		IntegerEdge e1 = new IntegerEdge(1);
		IntegerEdge e2 = new IntegerEdge(2);

		addEdge(v1, v2, e1, graph);
		addEdge(v2, v1, e2, graph);

		assertEquals(e1, graph.getEdge(v1, v2));
		assertEquals(e2, graph.getEdge(v2, v1));

		removeEdge(v1, v2, e1, graph);
		removeEdge(v2, v1, e2, graph);
	}

}
