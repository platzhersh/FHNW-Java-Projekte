package ch.fhnw.edu.efficientalgorithms.graph.gui;

import ch.fhnw.edu.efficientalgorithms.graph.Vertex;

/**
 * Observers of object's coordinates.
 * 
 * @author Martin Schaub
 * 
 * @param <V> vertex type
 */
public interface LocationListener<V extends Vertex> {

	/**
	 * Location of vertex v has been changed.
	 * 
	 * @param v the vertex's position that has been changed.
	 */
	void locationChanged(V v);
}
