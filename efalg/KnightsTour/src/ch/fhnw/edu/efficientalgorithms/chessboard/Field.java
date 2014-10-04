package ch.fhnw.edu.efficientalgorithms.chessboard;

/**
 * Status of a field in the chessboard. The implementations must be immutable!
 * 
 * @author Martin Schaub
 */
public interface Field {

	/**
	 * Gets the printable label of a field.
	 * 
	 * @return the label.
	 */
	String getLabel();
}
