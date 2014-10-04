package ch.fhnw.edu.efficientalgorithms.graph.gui;

import java.awt.Color;

import ch.fhnw.edu.efficientalgorithms.graph.Edge;
import ch.fhnw.edu.efficientalgorithms.graph.Vertex;

/**
 * Listens for color changes.
 * 
 * @author Martin Schaub
 * @param <V> vertex type
 * @param <E> edge type
 */
public interface ColorMapperListener<V extends Vertex, E extends Edge> {

	/**
	 * Is called if a new color is set to an vertex.
	 * 
	 * @param v changed vertex
	 * @param newColor new color
	 */
	void newVertexColor(V v, Color newColor);

	/**
	 * IS called if a new color is set to an edge.
	 * @param e changed edge
	 * @param newColor new color
	 */
	void newEdgeColor(E e, Color newColor);
}
