package ch.fhnw.edu.efficientalgorithms.chessboard;

/**
 * Interface for an algorithm operating on the chessboard. The algorithm's execute method will be run only once.
 * 
 * @author Martin Schaub
 */
public interface ChessBoardAlgorithm {

	/**
	 * Executes the algorithm..
	 * 
	 * @param board chessboard to solve
	 * @param startPos start position
	 */
	void execute(final ChessBoard board, final Position startPos);

	/**
	 * Stops the execution of the algorithm.
	 */
	void stop();
}
