package ch.fhnw.edu.efficientalgorithms.chessboard;

/**
 * Represents a graphical board.
 * 
 * @author Martin Schaub
 */
public interface GraphicalBoard {

	/**
	 * Initializes the board with a new chessboard. All lines and all texts are deleted.
	 * 
	 * @param newBoard new chessboard to use
	 */
	void init(ChessBoard newBoard);

	/**
	 * Draws a line between the start and end position
	 * 
	 * @param start startposition
	 * @param end endposition
	 */
	void drawLine(Position start, Position end);

	/**
	 * Removes the line.
	 * 
	 * @param start startpoint
	 * @param end endpoint
	 */
	void removeLine(Position start, Position end);

	/**
	 * Sets a text at position.
	 * 
	 * @param pos position to set
	 * @param value value to set
	 */
	void setText(Position pos, String value);

}
