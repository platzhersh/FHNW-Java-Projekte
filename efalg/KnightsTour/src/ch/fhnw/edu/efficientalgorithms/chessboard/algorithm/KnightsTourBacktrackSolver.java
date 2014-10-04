package ch.fhnw.edu.efficientalgorithms.chessboard.algorithm;

import java.util.List;

import ch.fhnw.edu.efficientalgorithms.chessboard.ChessBoard;
import ch.fhnw.edu.efficientalgorithms.chessboard.Position;
import ch.fhnw.edu.efficientalgorithms.chessboard.impl.KnightsTourField;

/**
 * Solves a knight's tour using backtracking.
 * 
 * @author Martin Schaub
 */
public final class KnightsTourBacktrackSolver extends AbstractKnightsTourAlgorithm {

	/*
	 * (non-Javadoc)
	 * @seech.fhnw.edu.efficientalgorithms.knightstour.algorithm.AbstractKnightsTourAlgorithm#findKnightTour(ch.fhnw.edu.
	 * efficientalgorithms.knightstour.ChessBoard, ch.fhnw.edu.efficientalgorithms.knightstour.Position)
	 */
	@Override
	protected void findKnightTour(final ChessBoard board, final Position startPos) {
		backTrack(board, startPos, 0);
	}

	/**
	 * Recursive method to perform the backtracking.
	 * 
	 * @param board board to fill
	 * @param pos position to try
	 * @param num current value to set
	 * @return true, if the values should be stored (correctly finished or stopped).
	 */
	private boolean backTrack(final ChessBoard board, final Position pos, final int num) {
		if (getField(board, pos).isVisited()) {
			throw new IllegalStateException();
		}

		if (hasBeenStopped()) {
			return true;
		}

		// Set the field to the expected value
		board.setField(pos, new KnightsTourField(num));

		// Get all possible moves
		List<Position> possibleMoves = getPossitibleMoves(board, pos);
		if (possibleMoves.size() == 0) {
			if (isFinished(board)) {
				return true;
			}
			board.setField(pos, new KnightsTourField());
			return false;
		}

		// Try all moves
		for (Position move : possibleMoves) {
			if (backTrack(board, move, num + 1)) {
				return true;
			}
		}

		// Reset the field
		board.setField(pos, new KnightsTourField());

		return false;
	}
}
