package ch.fhnw.edu.efficientalgorithms.chessboard;

/**
 * Observes a chessboard.
 * 
 * @author Martin Schaub
 */
public interface ChessBoardListener {

	/**
	 * The field has been changed in the chessboard.
	 * 
	 * @param position the field's position on the chessboard
	 * @param board which chessboard has been changed
	 */
	void fieldChanged(ChessBoard board, Position position);
}
