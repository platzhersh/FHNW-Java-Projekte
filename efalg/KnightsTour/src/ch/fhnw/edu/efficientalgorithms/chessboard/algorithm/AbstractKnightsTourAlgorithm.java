package ch.fhnw.edu.efficientalgorithms.chessboard.algorithm;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

import ch.fhnw.edu.efficientalgorithms.chessboard.ChessBoard;
import ch.fhnw.edu.efficientalgorithms.chessboard.ChessBoardAlgorithm;
import ch.fhnw.edu.efficientalgorithms.chessboard.Position;
import ch.fhnw.edu.efficientalgorithms.chessboard.impl.KnightsTourField;
import ch.fhnw.edu.efficientalgorithms.chessboard.impl.PositionImpl;

/**
 * Abstract base class for knight's tour algorithms.
 * 
 * @author Martin Schaub
 */
public abstract class AbstractKnightsTourAlgorithm implements ChessBoardAlgorithm {

	/**
	 * Possible moves from the knight play figure.
	 * 
	 * MOVES[i][0] = x diff and MOVES[i][1] = y diff
	 */
	protected static final int[][] MOVES = { { 2, 1 }, { 1, 2 }, { -1, 2 }, { -2, 1 }, { -2, -1 }, { -1, -2 }, { 1, -2 },
			{ 2, -1 } };

	/**
	 * Has the algorithm been stopped.
	 */
	private boolean hasBeenStopped = false;

	/**
	 * Has the algorithm been requested to stop?
	 * @return true, if the algorithm needs to be stopped and false otherwise.
	 */
	protected boolean hasBeenStopped() {
		return hasBeenStopped;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.knightstour.ChessBoardAlgorithm#execute(ch.fhnw.edu.efficientalgorithms.knightstour
	 * .ChessBoard, ch.fhnw.edu.efficientalgorithms.knightstour.Position)
	 */
	@Override
	public void execute(final ChessBoard board, final Position startPos) {
		// Initialize the field
		for (int i = 0; i < board.getNumOfRows(); ++i) {
			for (int j = 0; j < board.getNumOfColumns(); ++j) {
				board.setField(new PositionImpl(i, j), new KnightsTourField());
			}
		}

		// Execute the algorithm
		findKnightTour(board, startPos);

		// Test the result.
		if (!isSolution(board) && !hasBeenStopped) {
			JOptionPane.showMessageDialog(null, "The cheesboard with this start position has no knight's tour.",
					"No Solution was found!", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * Searches a knight's tour.
	 * 
	 * @param board chess board to search
	 * @param startPos start position
	 */
	protected abstract void findKnightTour(final ChessBoard board, final Position startPos);

	/**
	 * Gets the selected field form the board. The only additional thing to board's method is, that its automatically
	 * casted.
	 * 
	 * @param board board to get the field from
	 * @param pos field's position.
	 * @return the field
	 */
	protected KnightsTourField getField(final ChessBoard board, final Position pos) {
		return (KnightsTourField) board.getField(pos);
	}

	/**
	 * Gets all valid moves from a position.
	 * 
	 * @param board current chessboard
	 * @param position position from which's view those positions are evaluated.
	 * @return a list containing all possible moves.
	 */
	protected List<Position> getPossitibleMoves(final ChessBoard board, final Position position) {
		List<Position> list = new LinkedList<Position>();

		int row = position.getRow();
		int column = position.getColumn();

		for (int[] pos : MOVES) {
			int newRow = row + pos[0];
			int newCol = column + pos[1];
			if (isInBound(board, newRow, newCol)) {
				Position newPos = new PositionImpl(newRow, newCol);
				if (!getField(board, newPos).isVisited()) {
					list.add(newPos);
				}
			}
		}

		return list;
	}

	/**
	 * Checks if a given position is valid inside the chessboard.
	 * 
	 * @param board the chessboard to check
	 * @param row position's row
	 * @param column position's column
	 * @return true, if it is possible and false otherwise.
	 */
	protected boolean isInBound(final ChessBoard board, final int row, final int column) {
		return row >= 0 && column >= 0 && row < board.getNumOfRows() && column < board.getNumOfColumns();
	}

	/**
	 * Checks if all fields of the chessboard are visited.
	 * 
	 * @param board chessboard to check
	 * @return true, if all are filled and false otherwise
	 */
	protected boolean isFinished(final ChessBoard board) {
		for (int i = 0; i < board.getNumOfRows(); ++i) {
			for (int j = 0; j < board.getNumOfColumns(); ++j) {
				if (!getField(board, new PositionImpl(i, j)).isVisited()) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Checks if the solution is valid.
	 * 
	 * @param board chessboard to check
	 * @return true, if all numbers occur only once in the board and the chessboard is finished
	 */
	protected boolean isSolution(final ChessBoard board) {
		Set<Integer> occured = new HashSet<Integer>(board.getNumOfColumns() * board.getNumOfRows() * 2);
		for (int i = 0; i < board.getNumOfRows(); ++i) {
			for (int j = 0; j < board.getNumOfColumns(); ++j) {
				KnightsTourField toCheck = getField(board, new PositionImpl(i, j));
				if (!toCheck.isVisited() || occured.contains(toCheck.getValue())) {
					return false;
				}
				occured.add(toCheck.getValue());
			}
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.knightstour.ChessBoardAlgorithm#stop()
	 */
	@Override
	public void stop() {
		hasBeenStopped = true;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
}
