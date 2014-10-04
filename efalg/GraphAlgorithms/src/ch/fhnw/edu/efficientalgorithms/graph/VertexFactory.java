package ch.fhnw.edu.efficientalgorithms.graph;

import java.io.Serializable;

/**
 * Produces new edges.
 * 
 * @author Martin Schaub
 * @param <V> vertex type
 */
public interface VertexFactory<V extends Vertex> extends Serializable {

	/**
	 * Factory method to produce new vertices.
	 * 
	 * @return newly produced vertex
	 */
	V newVertex();

	/**
	 * Gets the vertex type that is produced in this factory.
	 * 
	 * @return vertex type
	 */
	Class<? extends Vertex> getVertexClass();

	/**
	 * Is called if vertex factory should reinizialize itself.
	 */
	void reset();
}
