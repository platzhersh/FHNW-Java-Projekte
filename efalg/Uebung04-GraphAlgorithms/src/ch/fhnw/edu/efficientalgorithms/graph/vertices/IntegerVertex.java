package ch.fhnw.edu.efficientalgorithms.graph.vertices;

/**
 * A vertex with a intger number.
 * 
 * @author Martin Schaub
 */
public final class IntegerVertex extends StandardVertex {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 5949124310354317058L;

	/**
	 * Represented value.
	 */
	private final int value;

	/**
	 * Constructor
	 * 
	 * @param value value the vertex represents.
	 */
	public IntegerVertex(final int value) {
		super(Integer.valueOf(value).toString());
		this.value = value;
	}

	/**
	 * Getter method for the value property.
	 * 
	 * @return the value property's value
	 */
	public int getValue() {
		return value;
	}
}
