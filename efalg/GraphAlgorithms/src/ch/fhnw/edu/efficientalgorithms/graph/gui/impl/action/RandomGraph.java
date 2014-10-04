package ch.fhnw.edu.efficientalgorithms.graph.gui.impl.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.AbstractAction;

import ch.fhnw.edu.efficientalgorithms.graph.Edge;
import ch.fhnw.edu.efficientalgorithms.graph.Graph;
import ch.fhnw.edu.efficientalgorithms.graph.Vertex;
import ch.fhnw.edu.efficientalgorithms.graph.gui.ProgramState;

/**
 * Adds a random graph
 * 
 * @author Martin Schaub
 * 
 * @param <V> vertex type
 * @param <E> edge type
 */
public final class RandomGraph<V extends Vertex, E extends Edge> extends AbstractAction {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -626376806343974949L;

	/**
	 * Current program state.
	 */
	private final ProgramState<V, E> state;
	/**
	 * Number of edges per vertex
	 */
	private final int numOfEdgePerVertex;
	/**
	 * Number of vertices in the graph.
	 */
	private final int numVertex;

	/**
	 * Constructor
	 * 
	 * @param state program state
	 * @param numVertex number of vertices in the graph
	 * @param numOfEdgePerVertex number of edges per vertex in mean
	 */
	public RandomGraph(final ProgramState<V, E> state, final int numVertex, final int numOfEdgePerVertex) {
		if (state == null) {
			throw new NullPointerException();
		}
		if (numOfEdgePerVertex < 0 || numVertex < 0) {
			throw new IllegalArgumentException();
		}
		this.state = state;
		this.numOfEdgePerVertex = numOfEdgePerVertex;
		this.numVertex = numVertex;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		Graph<V, E> graph = state.getGraph();

		// Clean the graph
		for (V v : new ArrayList<V>(graph.getVertices())) {
			graph.removeVertex(v);
		}

		// Add new vertices
		Random rand = new Random();
		for (int i = 0; i < numVertex; ++i) {
			V v = state.getVertexFactory().newVertex();
			// new location that fits on the default window
			state.getLocationMapper().setLocation(v, rand.nextInt(850) + 10, rand.nextInt(650) + 10);
			graph.addVertex(v);
		}

		// Add new edges
		for (V src : graph.getVertices()) {
			for (V dst : graph.getVertices()) {
				if (!src.equals(dst) && rand.nextInt(graph.getNumOfVertices()) < numOfEdgePerVertex) {
					graph.addEdge(src, dst, state.getEdgeFactory().newEdge());
				}
			}
		}
	}
}
