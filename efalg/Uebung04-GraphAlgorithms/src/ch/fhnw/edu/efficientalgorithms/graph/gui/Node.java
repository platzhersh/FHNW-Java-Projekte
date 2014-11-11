package ch.fhnw.edu.efficientalgorithms.graph.gui;

import java.io.Serializable;

import ch.fhnw.edu.efficientalgorithms.graph.Vertex;

/**
 * Wrapps the x and y coordinates around a vertex.
 * 
 * @author Martin Schaub
 * 
 * @param <V> Vertex type
 */
public class Node<V extends Vertex> implements Serializable {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 6820555574208088966L;

	/**
	 * Wrapped vertex
	 */
	final V vertex;

	/**
	 * X coordinate
	 */
	private int x;
	/**
	 * Y coordinate
	 */
	private int y;

	/**
	 * Constructor
	 * 
	 * @param vertex encapsulated vertex
	 */
	public Node(final V vertex) {
		if (vertex == null) {
			throw new NullPointerException();
		}
		this.vertex = vertex;
	}

	/**
	 * Getter method for the x property.
	 * 
	 * @return the x property's value
	 */
	public int getX() {
		return x;
	}

	/**
	 * Setter method for the x property.
	 * 
	 * @param x the new x to set
	 */
	public void setX(final int x) {
		this.x = x;
	}

	/**
	 * Getter method for the y property.
	 * 
	 * @return the y property's value
	 */
	public int getY() {
		return y;
	}

	/**
	 * Setter method for the y property.
	 * 
	 * @param y the new y to set
	 */
	public void setY(final int y) {
		this.y = y;
	}

	/**
	 * Getter method for the vertex property.
	 * 
	 * @return the vertex property's value
	 */
	public V getVertex() {
		return vertex;
	}
}