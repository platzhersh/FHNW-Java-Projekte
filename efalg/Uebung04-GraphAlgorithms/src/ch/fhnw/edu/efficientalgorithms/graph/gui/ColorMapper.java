package ch.fhnw.edu.efficientalgorithms.graph.gui;

import java.awt.Color;

import ch.fhnw.edu.efficientalgorithms.graph.Edge;
import ch.fhnw.edu.efficientalgorithms.graph.Vertex;

/**
 * Maps the graph components to colors.
 * 
 * @author Martin Schaub
 * 
 * @param <V> vertex type
 * @param <E> edge type
 */
public interface ColorMapper<V extends Vertex, E extends Edge> {

	/**
	 * Default edge color, when no user specific is set.
	 */
	public final static Color DEFAULT_EDGE_COLOR = Color.BLACK;
	/**
	 * Default edge color, when no user specific is set.
	 */
	public final static Color DEFAULT_VERTEX_COLOR = Color.BLUE;

	/**
	 * Gets the color of the vertex v.
	 * 
	 * @param v vertex to get the color of.
	 * @return vertex's color.
	 */
	Color getVertexColor(V v);

	/**
	 * Sets the color of the vertex v.
	 * 
	 * @param v vertex whose color is set
	 * @param c new color
	 */
	void setVertexColor(V v, Color c);

	/**
	 * Gets the edge e's color.
	 * 
	 * @param e the edge whose color is queried
	 * @return the color
	 */
	Color getEdgeColor(E e);

	/**
	 * Sets the edge's color.
	 * 
	 * @param e edge whose color is set
	 * @param c new edge color
	 */
	void setEdgeColor(E e, Color c);

	/**
	 * Registers a new listener.
	 * 
	 * @param listener listener to register.
	 */
	void addColorMapperListener(ColorMapperListener<V, E> listener);

	/**
	 * Deregisters a listener.
	 * 
	 * @param listener listener to remove.
	 */
	void removeColorMapperListener(ColorMapperListener<V, E> listener);

	/**
	 * Removes all mappings
	 */
	void reset();

}
