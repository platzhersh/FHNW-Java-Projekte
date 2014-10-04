package ch.fhnw.edu.efficientalgorithms.graph.gui.impl;

import java.util.HashSet;
import java.util.Set;

import ch.fhnw.edu.efficientalgorithms.graph.Edge;
import ch.fhnw.edu.efficientalgorithms.graph.EdgeFactory;
import ch.fhnw.edu.efficientalgorithms.graph.Graph;
import ch.fhnw.edu.efficientalgorithms.graph.GraphListener;
import ch.fhnw.edu.efficientalgorithms.graph.Vertex;
import ch.fhnw.edu.efficientalgorithms.graph.VertexFactory;
import ch.fhnw.edu.efficientalgorithms.graph.edges.StandardEdge;
import ch.fhnw.edu.efficientalgorithms.graph.edges.StandardEdgeFactory;
import ch.fhnw.edu.efficientalgorithms.graph.gui.ColorMapper;
import ch.fhnw.edu.efficientalgorithms.graph.gui.LocationMapper;
import ch.fhnw.edu.efficientalgorithms.graph.gui.ProgramState;
import ch.fhnw.edu.efficientalgorithms.graph.gui.ProgramStateListener;
import ch.fhnw.edu.efficientalgorithms.graph.impl.UniversalGraph;
import ch.fhnw.edu.efficientalgorithms.graph.vertices.IntegerVertex;

/**
 * ProgramState implementation.
 *
 * @author Martin Schaub
 *
 * @param <V> vertex type
 * @param <E> edge type
 */
@SuppressWarnings("unchecked")
public final class ProgramStateImpl<V extends Vertex, E extends Edge> implements ProgramState<V, E>,
		GraphListener<V, E> {

	/**
	 * All registered listeners.
	 */
	private final Set<ProgramStateListener<V, E>> listeners = new HashSet<ProgramStateListener<V, E>>();
	/**
	 * Used location mapper.
	 */
	private final LocationMapper<V> locationMapper;
	/**
	 * Used color mapper.
	 */
	private final ColorMapper<V, E> colorMapper;

	/**
	 * Vertex factory is fixed, since only one vertex type is used.
	 */
	private VertexFactory<V> vertexFactory;
	/**
	 * Currently used graph.
	 */
	private Graph<V, E> graph = new UniversalGraph<V, E>(true, StandardEdge.class, IntegerVertex.class);
	/**
	 * The current GUI state.
	 */
	private State state = State.Selection;
	/**
	 * Used edge factory.
	 */
	private EdgeFactory<E> edgeFactory = (EdgeFactory<E>) new StandardEdgeFactory();
	/**
	 * Selected vertex
	 */
	private V selected;

	/**
	 * Constructor
	 *
	 * @param locationMapper location mapper to use
	 * @param colorMapper color mapper to use
	 * @param vertexFactory vertex factory to use
	 */
	public ProgramStateImpl(final LocationMapper<V> locationMapper, final ColorMapper<V, E> colorMapper,
			final VertexFactory<V> vertexFactory) {
		if (locationMapper == null || colorMapper == null || vertexFactory == null) {
			throw new NullPointerException();
		}
		this.locationMapper = locationMapper;
		this.colorMapper = colorMapper;
		this.vertexFactory = vertexFactory;
		graph.addGraphListener(this);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.graph.ProgramState#addStateListener(ch.fhnw.edu.efficientalgorithms.graph.StateListener
	 * )
	 */
	@Override
	public synchronized void addStateListener(final ProgramStateListener<V, E> listener) {
		if (listener == null) {
			throw new NullPointerException();
		}

		listeners.add(listener);
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.ProgramState#getCurrentGraph()
	 */
	@Override
	public synchronized Graph<V, E> getGraph() {
		return graph;
	}

	/*
	 * (non-Javadoc)
	 * @seech.fhnw.edu.efficientalgorithms.graph.ProgramState#removeStateListener(ch.fhnw.edu.efficientalgorithms.graph.
	 * StateListener)
	 */
	@Override
	public synchronized void removeStateListener(final ProgramStateListener<V, E> listener) {
		if (listener == null) {
			throw new NullPointerException();
		}

		listeners.remove(listener);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.graph.ProgramState#setCurrentGraph(ch.fhnw.edu.efficientalgorithms.graph.Graph)
	 */
	@Override
	public synchronized void setGraph(final Graph<V, E> graph) {
		if (graph == null) {
			throw new NullPointerException();
		}
		Graph<V, E> oldGraph = this.graph;
		this.graph = graph;

		// Sets the new listeners
		for (GraphListener<V, E> listener : oldGraph.getListeners()) {
			graph.addGraphListener(listener);
		}

		vertexFactory.reset();

		for (ProgramStateListener<V, E> listener : listeners) {
			listener.newGraphLoaded(graph, oldGraph);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.ProgramState#getEdgeFactory()
	 */
	@Override
	public EdgeFactory<E> getEdgeFactory() {
		return edgeFactory;
	}

	/*
	 * (non-Javadoc)
	 * @seech.fhnw.edu.efficientalgorithms.graph.ProgramState#setEdgeFactory(ch.fhnw.edu.efficientalgorithms.graph.edges.
	 * EdgeFactory)
	 */
	@Override
	public void setEdgeFactory(final EdgeFactory<E> factory) {
		if (factory == null) {
			throw new NullPointerException();
		}
		this.edgeFactory = factory;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.gui.ProgramState#getLocationMapper()
	 */
	@Override
	public LocationMapper<V> getLocationMapper() {
		return locationMapper;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.gui.ProgramState#getState()
	 */
	@Override
	public State getState() {
		return state;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.graph.gui.ProgramState#setState(ch.fhnw.edu.efficientalgorithms.graph.gui.ProgramState
	 * .State)
	 */
	@Override
	public void setState(final State state) {
		this.state = state;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.gui.ProgramState#getColorMapper()
	 */
	@Override
	public ColorMapper<V, E> getColorMapper() {
		return colorMapper;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.gui.ProgramState#getVertexFactory()
	 */
	@Override
	public VertexFactory<V> getVertexFactory() {
		return vertexFactory;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.GraphListener#edgeAdded(ch.fhnw.edu.efficientalgorithms.graph.Edge)
	 */
	@Override
	public void edgeAdded(final E e) {
		notifyGraphChange();
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.GraphListener#edgeRemoved(ch.fhnw.edu.efficientalgorithms.graph.Edge)
	 */
	@Override
	public void edgeRemoved(final E e) {
		notifyGraphChange();
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.GraphListener#vertexAdded(ch.fhnw.edu.efficientalgorithms.graph.Vertex)
	 */
	@Override
	public void vertexAdded(final V v) {
		notifyGraphChange();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.graph.GraphListener#vertexRemoved(ch.fhnw.edu.efficientalgorithms.graph.Vertex)
	 */
	@Override
	public void vertexRemoved(final V v) {
		notifyGraphChange();
	}

	/**
	 * Private helper method to notify the listeners about program changes.
	 */
	private void notifyGraphChange() {
		for (ProgramStateListener<V, E> listener : listeners) {
			listener.graphChanged();
		}
	}

	/*
	 * (non-Javadoc)
	 * @seech.fhnw.edu.efficientalgorithms.graph.gui.ProgramState#setVertexFactory(ch.fhnw.edu.efficientalgorithms.graph.
	 * VertexFactory)
	 */
	@Override
	public void setVertexFactory(final VertexFactory<V> vertexFactory) {
		if (vertexFactory == null) {
			throw new NullPointerException();
		}
		this.vertexFactory = vertexFactory;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.gui.ProgramState#getCurrentlySelectedVertex()
	 */
	@Override
	public V getCurrentlySelectedVertex() {
		return selected;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.graph.gui.ProgramState#setCurrentlySelectedVertex(ch.fhnw.edu.efficientalgorithms
	 * .graph.Vertex)
	 */
	@Override
	public void setCurrentlySelectedVertex(final V v) {
		selected = v;
	}
}
