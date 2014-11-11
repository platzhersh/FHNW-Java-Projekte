package ch.fhnw.edu.efficientalgorithms.graph;

import java.io.Serializable;

/**
 * Produces new edges.
 * 
 * @author Martin Schaub
 * 
 * @param <E> edge type to produce
 */
public interface EdgeFactory<E extends Edge> extends Serializable {

	/**
	 * Creates a new edge.
	 * 
	 * @return new edge.
	 */
	E newEdge();

	/**
	 * Gets the class of the produced edges.
	 * 
	 * @return edges class
	 */
	Class<? extends Edge> getEdgeClass();

	/**
	 * Gets the classification name of the produced edges.
	 * 
	 * @return classification
	 */
	String getName();
}
