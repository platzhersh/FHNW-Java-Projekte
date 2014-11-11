package ch.fhnw.edu.efficientalgorithms.graph.edges;

import ch.fhnw.edu.efficientalgorithms.graph.Edge;

/**
 * Produces Integer Edges.
 * 
 * @author Martin Schaub
 */
public class IntegerEdgeFactory extends AbstractEdgeFactory<Edge> {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -7527489771199279060L;

	/**
	 * Next edges number.
	 */
	private int current = 0;

	/**
	 * Constructor
	 */
	public IntegerEdgeFactory() {
		super("Integer Edge");
	}

	/**
	 * Getter method for the current property.
	 * 
	 * @return the current property's value
	 */
	public int getCurrent() {
		return current;
	}

	/**
	 * Setter method for the current property.
	 * 
	 * @param current the new current to set
	 */
	public void setCurrent(final int current) {
		this.current = current;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.EdgeFactory#newEdge()
	 */
	@Override
	public Edge newEdge() {
		return new IntegerEdge(current++);
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.EdgeFactory#getEdgeClass()
	 */
	@Override
	public Class<IntegerEdge> getEdgeClass() {
		return IntegerEdge.class;
	}
}
