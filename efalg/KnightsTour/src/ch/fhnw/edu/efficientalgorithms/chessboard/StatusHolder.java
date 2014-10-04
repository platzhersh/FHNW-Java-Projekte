package ch.fhnw.edu.efficientalgorithms.chessboard;

/**
 * Stores the currently selected chessboard and notifies listeners if the board was replaced. This class is needed,
 * because the chessboard can be replaced and therefore various listeners have interest in knowing this event.
 * 
 * Additionally some status information about the application are stored. This is not good for the cohesion, however for
 * such a small application this is not important.
 * 
 * @author Martin Schaub
 */
public interface StatusHolder {

	/**
	 * Gets the currently set chessboard.
	 * 
	 * @return currently set chessboard
	 */
	ChessBoard getChessBoard();

	/**
	 * Whether currently an algorithm is running or not.
	 * 
	 * @return true, if a algorithm is running and false otherwise
	 */
	boolean isAlgorithmRunning();

	/**
	 * The algorithm was started.
	 * 
	 * @param algorithm started algorithm.
	 */
	void algorithmStarted(ChessBoardAlgorithm algorithm);

	/**
	 * The algorithm has been stopped.
	 */
	void algorithmStopped();

	/**
	 * Sets the currently selected position.
	 * 
	 * @param position currently selected position
	 */
	void setSelectedPosition(Position position);

	/**
	 * Gets the currently selected position.
	 * 
	 * @return currently selected position or null, if no position was selected.
	 */
	Position getSelectedPosition();

	/**
	 * Sets a new chessboard.
	 * 
	 * @param chessBoard new chessboard to set
	 */
	void setChessBoard(ChessBoard chessBoard);

	/**
	 * Registers a new listener.
	 * 
	 * @param listener listener to add
	 */
	void addStatusHolderListener(StatusHolderListener listener);

	/**
	 * Deregisters a listener.
	 * 
	 * @param listener listener to remove
	 */
	void removeStatusHolderListener(StatusHolderListener listener);

}
