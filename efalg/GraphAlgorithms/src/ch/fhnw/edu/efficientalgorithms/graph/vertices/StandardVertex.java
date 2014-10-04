package ch.fhnw.edu.efficientalgorithms.graph.vertices;

import ch.fhnw.edu.efficientalgorithms.graph.Vertex;

/**
 * Simple vertex implementation where it has just its label.
 * 
 * @author Martin Schaub
 */
public class StandardVertex implements Vertex {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 5337274711673151197L;

	/**
	 * Vertex's label.
	 */
	private final String label;

	/**
	 * Constructor
	 * 
	 * @param label label to set
	 */
	public StandardVertex(final String label) {
		if (label == null) {
			throw new NullPointerException();
		}
		this.label = label;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.graph.Vertex#getLabel()
	 */
	@Override
	public final String getLabel() {
		return label;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return label;
	}
}
