package ch.fhnw.edu.efficientalgorithms.graph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ch.fhnw.edu.efficientalgorithms.graph.edges.IntegerEdge;
import ch.fhnw.edu.efficientalgorithms.graph.vertices.IntegerVertex;

/**
 * JUnit test of the Graph class. Tests are performed on a graph obtained from a factory method. In this case a graph
 * with both, vertices and edges are integers.
 * 
 * @author Martin Schaub
 */
public abstract class GraphTest {

	/**
	 * Factory method to produce a empty graph for testing.
	 * 
	 * @return a new graph for testing.
	 */
	protected abstract Graph<IntegerVertex, IntegerEdge> getGraph();

	/**
	 * Adds a new edge and check whether the new edge was added successfully or not.
	 * 
	 * @param from source vertex
	 * @param to destination vertex
	 * @param e edge
	 * @param graph used graph
	 */
	protected abstract void addEdge(IntegerVertex from, IntegerVertex to, IntegerEdge e,
			Graph<IntegerVertex, IntegerEdge> graph);

	/**
	 * Removes a edge and checks that an edge was removed correctly from the graph.
	 * 
	 * @param from source vertex
	 * @param to destination vertex
	 * @param e edge
	 * @param graph used graph
	 */
	protected abstract void removeEdge(IntegerVertex from, IntegerVertex to, IntegerEdge e,
			Graph<IntegerVertex, IntegerEdge> graph);

	/**
	 * Adds a vertex to the graph and checks that everything is ok.
	 * 
	 * @param v vertex to add
	 * @param graph used graph
	 */
	protected void addVertex(final IntegerVertex v, final Graph<IntegerVertex, IntegerEdge> graph) {
		assertFalse("Vertex is already in the graph! Test error", graph.getVertices().contains(v));

		int num = graph.getNumOfVertices();

		assertTrue(graph.addVertex(v));
		assertTrue(graph.getVertices().contains(v));

		assertEquals(num + 1, graph.getVertices().size());
		assertEquals(num + 1, graph.getNumOfVertices());

		assertFalse(graph.addVertex(v));
	}

	/**
	 * Removes a vertex from the graph. The vertex must exist, so all necessary conditions can be checked.
	 * 
	 * @param v vertex to remove
	 * @param graph used graph
	 */
	protected void removeVertex(final IntegerVertex v, final Graph<IntegerVertex, IntegerEdge> graph) {
		assertTrue("Vertex is not in the graph! Test error", graph.getVertices().contains(v));

		int num = graph.getNumOfVertices();

		assertTrue(graph.removeVertex(v));

		assertFalse(graph.getVertices().contains(v));
		assertEquals(num - 1, graph.getVertices().size());
		assertEquals(num - 1, graph.getNumOfVertices());

		assertFalse(graph.removeVertex(v));
	}

	/**
	 * Check whether the new edge was added successfully or not.
	 * 
	 * @param from source vertex
	 * @param to destination vertex
	 * @param e edge
	 * @param graph used graph
	 */
	protected void checkEdgeAddition(final IntegerVertex from, final IntegerVertex to, final IntegerEdge e,
			final Graph<IntegerVertex, IntegerEdge> graph) {

		assertTrue(graph.getEdges().contains(e));
		assertTrue(graph.getIncomingEdges(to).contains(e));
		assertTrue(graph.getOutgoingEdges(from).contains(e));

		if (graph.isDirected()) {
			assertEquals(from, graph.getEndpoints(e).get(0));
			assertEquals(to, graph.getEndpoints(e).get(1));
		}
		else {
			assertTrue(graph.getEndpoints(e).contains(from));
			assertTrue(graph.getEndpoints(e).contains(to));
			assertEquals(2, graph.getEndpoints(e).size());
		}

		assertEquals(e, graph.getEdge(from, to));

		assertTrue(graph.getOutgoingAdjacence(from).contains(to));
		assertTrue(graph.getIncomingAdjacence(to).contains(from));
	}

	/**
	 * Checks that an edge was removed correctly from the graph. Its only one direction considered.
	 * 
	 * @param from source vertex
	 * @param to destination vertex
	 * @param e edge
	 * @param graph used graph
	 */
	protected void checkEdgeRemoval(final IntegerVertex from, final IntegerVertex to, final IntegerEdge e,
			final Graph<IntegerVertex, IntegerEdge> graph) {

		assertFalse(graph.getEdges().contains(e));

		assertFalse(graph.getIncomingEdges(to).contains(e));
		assertFalse(graph.getOutgoingEdges(from).contains(e));

		assertFalse(graph.getOutgoingAdjacence(from).contains(to));
		assertFalse(graph.getIncomingAdjacence(to).contains(from));

		assertNull(graph.getEdge(from, to));

	}

	/**
	 * Tests various situations when adding and removing vertices.
	 */
	@Test
	public void testAddRemoveVertex() {
		Graph<IntegerVertex, IntegerEdge> graph = getGraph();
		IntegerVertex v1 = new IntegerVertex(5);
		IntegerVertex v2 = new IntegerVertex(6);
		IntegerVertex v3 = new IntegerVertex(7);

		addVertex(v1, graph);
		addVertex(v2, graph);
		addVertex(v3, graph);
		removeVertex(v1, graph);
		removeVertex(v2, graph);
		removeVertex(v3, graph);
	}

	/**
	 * Tests adding and removing several edges.
	 */
	@Test
	public void testAddRemoveEdges() {
		Graph<IntegerVertex, IntegerEdge> graph = getGraph();
		IntegerVertex v1 = new IntegerVertex(5);
		IntegerVertex v2 = new IntegerVertex(6);
		IntegerVertex v3 = new IntegerVertex(7);

		addVertex(v1, graph);
		addVertex(v2, graph);
		addVertex(v3, graph);

		IntegerEdge e1 = new IntegerEdge(17);
		IntegerEdge e2 = new IntegerEdge(5);
		IntegerEdge e3 = new IntegerEdge(1);

		addEdge(v1, v2, e1, graph);
		addEdge(v2, v3, e2, graph);
		addEdge(v1, v3, e3, graph);

		removeEdge(v1, v2, e1, graph);
		removeEdge(v2, v3, e2, graph);
		removeEdge(v1, v3, e3, graph);
	}

	/**
	 * Tests that the clone method is correctly implemented.
	 */
	@Test
	public void testCloning() {
		Graph<IntegerVertex, IntegerEdge> graph = getGraph();
		IntegerVertex v1 = new IntegerVertex(3);
		IntegerVertex v2 = new IntegerVertex(4);

		addVertex(v1, graph);
		addVertex(v2, graph);

		IntegerEdge e1 = new IntegerEdge(1);
		addEdge(v1, v2, e1, graph);

		Graph<IntegerVertex, IntegerEdge> clone = graph.clone();
		graph.removeEdge(e1);
		graph.removeVertex(v1);
		graph.removeVertex(v2);

		assertTrue(clone.getVertices().contains(v1));
		assertTrue(clone.getVertices().contains(v2));
		assertTrue(clone.getEdges().contains(e1));
		assertEquals(e1, clone.getEdge(v1, v2));
	}

	/**
	 * Removes a non existing edge.
	 */
	@Test
	public void testRemoveNonExistingEdge() {
		Graph<IntegerVertex, IntegerEdge> graph = getGraph();
		assertFalse(graph.removeEdge(new IntegerEdge(1)));
	}

	/**
	 * Tests a self-loop
	 */
	@Test(expected = RuntimeException.class)
	public void testSelfLoop() {
		Graph<IntegerVertex, IntegerEdge> graph = getGraph();
		IntegerVertex v = new IntegerVertex(5);
		addVertex(v, graph);
		addEdge(v, v, new IntegerEdge(2), graph);
	}

	/**
	 * Tests to add an edge to a non existing vertex.
	 */
	@Test(expected = RuntimeException.class)
	public void testAddEdgeNonExistingVertex() {
		Graph<IntegerVertex, IntegerEdge> graph = getGraph();
		IntegerVertex v = new IntegerVertex(5);
		addVertex(v, graph);
		addEdge(v, new IntegerVertex(1), new IntegerEdge(2), graph);

	}

	/**
	 * Tests to add null as vertex.
	 */
	@Test(expected = RuntimeException.class)
	public void testTryAddingNullVertex() {
		getGraph().addVertex(null);
	}

	/**
	 * Tests to get the incoming edges of a non existing vertex.
	 */
	@Test(expected = RuntimeException.class)
	public void testTryGetInEdgesNonExistingVertex() {
		getGraph().getIncomingEdges(new IntegerVertex(1));
	}

	/**
	 * Tests to get the outgoing edges of a non existing vertex.
	 */
	@Test(expected = RuntimeException.class)
	public void testTryGetOutEdgesNonExistingVertex() {
		getGraph().getOutgoingEdges(new IntegerVertex(1));
	}

	/**
	 * Tests to get the incoming connected vertices of a non existing vertex.
	 */
	@Test(expected = RuntimeException.class)
	public void testTryGetInVertexNonExistingVertex() {
		getGraph().getIncomingAdjacence(new IntegerVertex(1));
	}

	/**
	 * Tests to get the outgoing connected vertices of a non existing vertex.
	 */
	@Test(expected = RuntimeException.class)
	public void testTryGetOutVertexNonExistingVertex() {
		getGraph().getOutgoingAdjacence(new IntegerVertex(1));
	}
}
