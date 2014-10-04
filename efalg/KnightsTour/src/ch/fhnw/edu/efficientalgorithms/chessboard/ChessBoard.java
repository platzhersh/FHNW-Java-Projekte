package ch.fhnw.edu.efficientalgorithms.chessboard;

/**
 * Represents a chessboard. In this case it is not just a regular 8x8 chessboard but a board with many different sizes.
 * 
 * @author Martin Schaub
 */
public interface ChessBoard {

	/**
	 * Sets a new status to a field at the specified location.
	 * 
	 * @param position the location of the field
	 * @param status field status.
	 */
	void setField(Position position, Field status);

	/**
	 * Gets the field content from the specified location.
	 * 
	 * @param position the location of the field
	 * @return field status
	 */
	Field getField(Position position);

	/**
	 * Returns the number of rows.
	 * 
	 * @return number of rows
	 */
	int getNumOfRows();

	/**
	 * Returns the number of columns.
	 * 
	 * @return number of columns
	 */
	int getNumOfColumns();

	/**
	 * Registers a new listener
	 * 
	 * @param listener listener to add
	 */
	void addCheeseBoardListener(ChessBoardListener listener);

	/**
	 * Removes a listener from the list of notified listeners.
	 * 
	 * @param listener listener to deregister
	 */
	void removeCheeseBoardListener(ChessBoardListener listener);

}
