package ch.fhnw.edu.efficientalgorithms.graph.edges;

/**
 * Stores a number as label.
 * 
 * @author Martin Schaub
 */
public final class IntegerEdge extends StandardEdge {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -1980154294799216009L;

	/**
	 * Number to display.
	 */
	private final int number;

	/**
	 * Constructor
	 * 
	 * @param number number label.
	 */
	public IntegerEdge(final int number) {
		super(Integer.valueOf(number).toString());
		this.number = number;
	}

	/**
	 * Getter method for the number property.
	 * 
	 * @return the number property's value
	 */
	public int getNumber() {
		return number;
	}

}
