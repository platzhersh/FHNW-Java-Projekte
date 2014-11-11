package ch.fhnw.edu.efficientalgorithms.graph.gui;

import ch.fhnw.edu.efficientalgorithms.graph.Vertex;

/**
 * Maps a vertex to a certain location.
 * 
 * @author Martin Schaub
 * 
 * @param <V> Vertex type
 */
public interface LocationMapper<V extends Vertex> {

	/**
	 * Gets the vertex at location x,y or null when there is no such vertex.
	 * 
	 * @param x x coordinate
	 * @param y y coordinate
	 * @return a vertex at this location or null.
	 */
	V getAtPosition(int x, int y);

	/**
	 * Sets the location of the vertex.
	 * 
	 * @param v vertex
	 * @param x x coordinate
	 * @param y y coordinate
	 */
	void setLocation(V v, int x, int y);

	/**
	 * Gets the x coordinate of the parameter
	 * @param v the vertex whose x coordinate will be returned.
	 * @return node that maps the vertex to the location.
	 */
	Node<V> getLocation(V v);

	/**
	 * Registers a location listener.
	 * 
	 * @param listener location listener to register.
	 */
	void addLocationListener(LocationListener<V> listener);

	/**
	 * Deregisters a location listener
	 * 
	 * @param listener location listener to remove.
	 */
	void removeLocationListener(LocationListener<V> listener);

	/**
	 * Removes all mappings.
	 */
	void reset();

}
