package ch.fhnw.edu.efficientalgorithms.graph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ch.fhnw.edu.efficientalgorithms.graph.edges.IntegerEdge;
import ch.fhnw.edu.efficientalgorithms.graph.vertices.IntegerVertex;

/**
 * New testcases for undirected graphs.
 * 
 * @author Martin Schaub
 */
public abstract class UndirectedGraphTest extends GraphTest {

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
		// If there is already a edge, the number is not allowed to increase!
		if (graph.getEdge(from, to) != null) {
			i--;
		}

		assertTrue(graph.addEdge(from, to, e));
		assertFalse(graph.addEdge(from, to, e));
		assertFalse(graph.addEdge(to, from, e));

		assertEquals(i + 1, graph.getNumOfEdges());
		assertEquals(i + 1, graph.getEdges().size());
		checkEdgeAddition(from, to, e, graph);
		checkEdgeAddition(to, from, e, graph);

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
		checkEdgeRemoval(to, from, e, graph);
	}

	/**
	 * Tests whether the graph is really undirected or not.
	 */
	@Test
	public void testIsUnDirected() {
		assertFalse(getGraph().isDirected());
	}

	/**
	 * Tests that a edge correctly overwrites an other when it is added the other way round.
	 */
	@Test
	public void testAddEdgesInBothDirections() {
		Graph<IntegerVertex, IntegerEdge> graph = getGraph();

		IntegerVertex v1 = new IntegerVertex(1);
		IntegerVertex v2 = new IntegerVertex(2);

		addVertex(v1, graph);
		addVertex(v2, graph);

		IntegerEdge e1 = new IntegerEdge(1);
		IntegerEdge e2 = new IntegerEdge(2);

		addEdge(v1, v2, e1, graph);
		assertEquals(1, graph.getNumOfEdges());
		addEdge(v2, v1, e2, graph);
		assertEquals(1, graph.getNumOfEdges());

		assertEquals(e2, graph.getEdge(v1, v2));
		assertEquals(e2, graph.getEdge(v2, v1));

		removeEdge(v2, v1, e2, graph);
		assertEquals(0, graph.getNumOfEdges());
		assertNull(graph.getEdge(v1, v2));
		assertNull(graph.getEdge(v2, v1));
	}
}
