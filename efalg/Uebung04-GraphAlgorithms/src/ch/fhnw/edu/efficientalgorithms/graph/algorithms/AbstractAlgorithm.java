package ch.fhnw.edu.efficientalgorithms.graph.algorithms;

import java.awt.Color;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import ch.fhnw.edu.efficientalgorithms.graph.Edge;
import ch.fhnw.edu.efficientalgorithms.graph.GraphAlgorithm;
import ch.fhnw.edu.efficientalgorithms.graph.GraphAlgorithmData;
import ch.fhnw.edu.efficientalgorithms.graph.Vertex;
import ch.fhnw.edu.efficientalgorithms.graph.gui.Node;

/**
 * Abstract base class for the GraphAlgorithms hierarchy.
 * 
 * @author Martin Schaub
 * 
 * @param <V> vertex type
 * @param <E> edge type
 */
public abstract class AbstractAlgorithm<V extends Vertex, E extends Edge> implements GraphAlgorithm<V, E> {

	/**
	 * Algorithms name.
	 */
	private final String name;
	/**
	 * True if this algorithm can run in a separate thread.
	 */
	private final boolean runInThread;

	/**
	 * Constructor
	 * 
	 * @param name algorithm's name
	 * @param runInThread Can this algorithm run in a separate thread
	 */
	public AbstractAlgorithm(final String name, final boolean runInThread) {
		if (name == null) {
			throw new NullPointerException();
		}
		this.name = name;
		this.runInThread = runInThread;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.GraphAlgorithm#isThreadReady()
	 */
	@Override
	public final boolean isThreadReady() {
		return runInThread;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.GraphAlgorithm#name()
	 */
	@Override
	public final String getName() {
		return name;
	}

	/**
	 * Sometimes a start node is needed. It uses the currently selected vertex or just a random one, if no one is
	 * selected.
	 * 
	 * @param data information to selected the data from.
	 * @return start node or null if the graph has no vertices
	 */
	protected V getFirstVertex(final GraphAlgorithmData<V, E> data) {
		if (data == null) {
			throw new NullPointerException();
		}

		// Get the selected vertex
		if (data.getSelectedVertex() != null) {
			return data.getSelectedVertex();
		}

		return getRandomVertex(data);
	}

	/**
	 * Selects a random vertex of the graph.
	 * 
	 * @param data graph to select the vertex from.
	 * @return a randomly selected vertex or null, if the graph does not contain any vertex.
	 */
	protected V getRandomVertex(final GraphAlgorithmData<V, E> data) {
		if (data == null) {
			throw new NullPointerException();
		}

		if (data.getGraph().getNumOfVertices() == 0) {
			return null;
		}

		// num is element of [0,numOfVertices-1]
		int num = (int) (Math.random() * data.getGraph().getNumOfVertices());
		Iterator<V> it = data.getGraph().getVertices().iterator();
		for (int i = 0; i < num; ++i) {
			it.next();
		}

		return it.next();
	}

	/**
	 * Selects a different vertex than the other vertex is.
	 * 
	 * @param data to get the graph
	 * @param other the other vertex to compare to
	 * @return null, if the graph is empty or contains only one vertex and a randomly selected vertex otherwise.
	 */
	protected V getDiffernetVertex(final GraphAlgorithmData<V, E> data, final Vertex other) {
		if (other == null || data == null) {
			throw new NullPointerException();
		}

		if (data.getGraph().getNumOfVertices() <= 2) {
			return null;
		}

		V v = getRandomVertex(data);
		while (v.equals(other)) {
			v = getRandomVertex(data);
		}
		return v;
	}

	/**
	 * Sets the color of a selected edge list to RED.
	 * 
	 * @param list list of edges to color
	 * @param data algorithm data (colormapper for changing the color)
	 */
	protected void highlightEdges(final GraphAlgorithmData<V, E> data, final List<E> list) {
		highlightEdges(data, list, Color.RED);
	}
	

	/**
	 * Sets the color of a selected edge list to a custom color.
	 * 
	 * @param list list of edges to color
	 * @param color color to highlight
	 * @param data algorithm data (colormapper for changing the color)
	 */
	protected void highlightEdges(final GraphAlgorithmData<V, E> data, final List<E> list, final Color color) {
		if (list == null || data == null) {
			throw new NullPointerException();
		}

		for (E e : list) {
			data.getColorMapper().setEdgeColor(e, color);
		}
	}

	/**
	 *Highlights a list of vertices.S
	 * 
	 * @param data algorithm data (need the colormapper)
	 * @param list list of nodes to color
	 * @param color colored nodes.
	 */
	protected void highlightVertices(final GraphAlgorithmData<V, E> data, final List<V> list, final Color color) {
		for (V v : list) {
			data.getColorMapper().setVertexColor(v, color);
		}
	}

	/**
	 * Darkens the edges which aren't in the list
	 * 
	 * @param data needed data (graph and colormapper)
	 * @param list list of edges that aren't highlighted.
	 */
	protected void darkenOtherEdges(final GraphAlgorithmData<V, E> data, final List<E> list) {
		if (list == null || data == null) {
			throw new NullPointerException();
		}

		Set<E> set = new HashSet<E>(list);

		for (E e : data.getGraph().getEdges()) {
			if (!set.contains(e)) {
				data.getColorMapper().setEdgeColor(e, Color.LIGHT_GRAY);
			}
		}
	}

	/**
	 * Gets the other end point of a edge. This method is static, because it might be accessed in inner classes.
	 * 
	 * @param <V> vertex type
	 * @param <E> edge type
	 * 
	 * @param data to get the graph
	 * @param e edge of whose the endpoints are of interest.
	 * @param v one endpoint of the edge.
	 * 
	 * @return the other endpoint
	 */
	protected static <V extends Vertex, E extends Edge> V otherEndpoint(final GraphAlgorithmData<V, E> data, final E e,
			final V v) {
		if (data == null || e == null || v == null) {
			throw new NullPointerException("data=" + data + " e=" + e + " v=" + v);
		}

		List<V> endpoints = data.getGraph().getEndpoints(e);
		if (endpoints.get(0).equals(v)) {
			return endpoints.get(1);
		}

		if (!endpoints.get(1).equals(v)) {
			throw new IllegalArgumentException("Is not an endpoint at all!");
		}

		return endpoints.get(0);
	}

	/**
	 * Calculates the distance from src to dst. This method is static, because it might be accessed in inner classes.
	 * 
	 * @param <V> vertex type
	 * @param <E> edge type.
	 * 
	 * @param data to get the location mapper
	 * @param src first vertex
	 * @param dst second vertex
	 * 
	 * @return distance.
	 */
	protected static <V extends Vertex, E extends Edge> double getDistance(final GraphAlgorithmData<V, E> data,
			final V src, final V dst) {
		if (src == null || dst == null || data == null) {
			throw new NullPointerException();
		}

		Node<V> sNode = data.getLocationMapper().getLocation(src);
		Node<V> dNode = data.getLocationMapper().getLocation(dst);
		int deltaX = sNode.getX() - dNode.getX();
		int deltaY = sNode.getY() - dNode.getY();
		return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
	}
}
