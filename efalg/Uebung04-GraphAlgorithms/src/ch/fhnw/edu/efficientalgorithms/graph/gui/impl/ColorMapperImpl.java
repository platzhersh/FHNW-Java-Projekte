package ch.fhnw.edu.efficientalgorithms.graph.gui.impl;

import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ch.fhnw.edu.efficientalgorithms.graph.Edge;
import ch.fhnw.edu.efficientalgorithms.graph.GraphListener;
import ch.fhnw.edu.efficientalgorithms.graph.Vertex;
import ch.fhnw.edu.efficientalgorithms.graph.gui.ColorMapper;
import ch.fhnw.edu.efficientalgorithms.graph.gui.ColorMapperListener;

/**
 * Simple implementation of the ColorMapper interface. It uses the GraphListener interface to get notified when some
 * objects can be deleted from the datastructure.
 * 
 * @author Martin Schaub
 * @param <V> vertex type
 * @param <E> edge type
 */
public final class ColorMapperImpl<V extends Vertex, E extends Edge> implements ColorMapper<V, E>, GraphListener<V, E> {

	/**
	 * Stores the listeners.
	 */
	private final Set<ColorMapperListener<V, E>> listeners = new HashSet<ColorMapperListener<V, E>>();

	/***
	 * Saves the vetex's colors.
	 */
	private final Map<V, Color> vertexColor = new HashMap<V, Color>();
	/**
	 * Saves the edge's color.
	 */
	private final Map<E, Color> edgeColor = new HashMap<E, Color>();

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.gui.ColorMapper#getEdgeColor(java.lang.Object)
	 */
	@Override
	public synchronized Color getEdgeColor(final E e) {
		if (e == null) {
			throw new NullPointerException();
		}

		if (edgeColor.containsKey(e)) {
			return edgeColor.get(e);
		}

		return DEFAULT_EDGE_COLOR;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.gui.ColorMapper#getVertexColor(java.lang.Object)
	 */
	@Override
	public synchronized Color getVertexColor(final V v) {
		if (v == null) {
			throw new NullPointerException();
		}

		if (vertexColor.containsKey(v)) {
			return vertexColor.get(v);
		}

		return DEFAULT_VERTEX_COLOR;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.gui.ColorMapper#setEdgeColor(java.lang.Object, java.awt.Color)
	 */
	@Override
	public synchronized void setEdgeColor(final E e, final Color c) {
		if (e == null || c == null) {
			throw new NullPointerException();
		}

		edgeColor.put(e, c);

		for (ColorMapperListener<V, E> listener : listeners) {
			listener.newEdgeColor(e, c);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.gui.ColorMapper#setVertexColor(java.lang.Object, java.awt.Color)
	 */
	@Override
	public synchronized void setVertexColor(final V v, final Color c) {
		if (v == null || c == null) {
			throw new NullPointerException();
		}

		vertexColor.put(v, c);

		for (ColorMapperListener<V, E> listener : listeners) {
			listener.newVertexColor(v, c);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.GraphListener#edgeAdded(java.lang.Object)
	 */
	@Override
	public void edgeAdded(final E e) {
		// nothing to do -> uses the default color automatically
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.GraphListener#edgeRemoved(java.lang.Object)
	 */
	@Override
	public synchronized void edgeRemoved(final E e) {
		edgeColor.remove(e);
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.GraphListener#vertexAdded(java.lang.Object)
	 */
	@Override
	public void vertexAdded(final V v) {
		// nothing to do -> uses the default color automatically
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.GraphListener#vertexRemoved(java.lang.Object)
	 */
	@Override
	public synchronized void vertexRemoved(final V v) {
		vertexColor.remove(v);
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.gui.ColorMapper#reset()
	 */
	@Override
	public synchronized void reset() {
		vertexColor.clear();
		edgeColor.clear();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.graph.gui.ColorMapper#addColorMapperListener(ch.fhnw.edu.efficientalgorithms.graph
	 * .gui.ColorMapperListener)
	 */
	@Override
	public synchronized void addColorMapperListener(final ColorMapperListener<V, E> listener) {
		if (listener == null) {
			throw new NullPointerException();
		}

		listeners.add(listener);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.graph.gui.ColorMapper#removeColorMapperListener(ch.fhnw.edu.efficientalgorithms
	 * .graph.gui.ColorMapperListener)
	 */
	@Override
	public synchronized void removeColorMapperListener(final ColorMapperListener<V, E> listener) {
		if (listener == null) {
			throw new NullPointerException();
		}

		listeners.remove(listener);
	}

}
