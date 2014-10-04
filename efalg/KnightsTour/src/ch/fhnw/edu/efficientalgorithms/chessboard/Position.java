package ch.fhnw.edu.efficientalgorithms.chessboard;

/**
 * Represents a position in the field. Must be implemented immutable. Two positions are equal, if their row and column
 * are the same.
 * 
 * @author Martin Schaub
 */
public interface Position {

	/**
	 * Returns the row.
	 * 
	 * @return the row
	 */
	int getRow();

	/**
	 * Returns the column.
	 * 
	 * @return the column
	 */
	int getColumn();

}
