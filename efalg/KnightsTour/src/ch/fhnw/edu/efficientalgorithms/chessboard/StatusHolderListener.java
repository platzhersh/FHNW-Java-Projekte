package ch.fhnw.edu.efficientalgorithms.chessboard;

/**
 * Observer interface for the status holder class.
 * 
 * @author Martin Schaub
 */
public interface StatusHolderListener {

	/**
	 * A new chessboard is loaded.
	 * 
	 * @param oldBoard old board (might be null, if this is the first board).
	 * @param newBoard newly set board
	 */
	void newChessBoardLoaded(ChessBoard oldBoard, ChessBoard newBoard);

	/**
	 * A new algorithm has been started.
	 * 
	 * @param algorithm started algorithm
	 */
	void algorithmStarted(ChessBoardAlgorithm algorithm);

	/**
	 * An algorithm has been stopped or was finished.
	 * 
	 * @param algorithm stopped or finished algorithm.
	 */
	void algorithmStopped(ChessBoardAlgorithm algorithm);

	/**
	 * A new position was selected.
	 * 
	 * @param position newly selected position
	 */
	void positionSelected(Position position);
}
