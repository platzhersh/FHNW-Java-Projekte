package ch.fhnw.edu.efficientalgorithms.graph.gui.impl.action;

import ch.fhnw.edu.efficientalgorithms.graph.Edge;
import ch.fhnw.edu.efficientalgorithms.graph.EdgeFactory;
import ch.fhnw.edu.efficientalgorithms.graph.Graph;
import ch.fhnw.edu.efficientalgorithms.graph.GraphAlgorithmData;
import ch.fhnw.edu.efficientalgorithms.graph.Vertex;
import ch.fhnw.edu.efficientalgorithms.graph.gui.ColorMapper;
import ch.fhnw.edu.efficientalgorithms.graph.gui.LocationMapper;
import ch.fhnw.edu.efficientalgorithms.graph.gui.ProgramState;

/**
 * Implementation which uses the ProgramState as backend.
 * 
 * @author Martin Schaub
 * 
 * @param <V> vertex type
 * @param <E> edge type
 */
public class GraphAlgorithmDataImpl<V extends Vertex, E extends Edge> implements GraphAlgorithmData<V, E> {

	/**
	 * Current program state.
	 */
	private final ProgramState<V, E> state;

	/**
	 * Constructor
	 * 
	 * @param state current program state
	 */
	public GraphAlgorithmDataImpl(final ProgramState<V, E> state) {
		if (state == null) {
			throw new NullPointerException();
		}
		this.state = state;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.GraphAlgorithmData#getColorMapper()
	 */
	@Override
	public ColorMapper<V, E> getColorMapper() {
		return state.getColorMapper();
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.GraphAlgorithmData#getEdgeFactory()
	 */
	@Override
	public EdgeFactory<E> getEdgeFactory() {
		return state.getEdgeFactory();
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.GraphAlgorithmData#getGraph()
	 */
	@Override
	public Graph<V, E> getGraph() {
		return state.getGraph();
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.GraphAlgorithmData#getLocationMapper()
	 */
	@Override
	public LocationMapper<V> getLocationMapper() {
		return state.getLocationMapper();
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.GraphAlgorithmData#getSelectedVertex()
	 */
	@Override
	public V getSelectedVertex() {
		return state.getCurrentlySelectedVertex();
	}

}
