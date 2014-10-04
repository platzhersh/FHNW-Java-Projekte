package ch.fhnw.edu.efficientalgorithms.chessboard.gui;

import ch.fhnw.edu.efficientalgorithms.chessboard.ChessBoard;
import ch.fhnw.edu.efficientalgorithms.chessboard.ChessBoardAlgorithm;
import ch.fhnw.edu.efficientalgorithms.chessboard.GraphicalBoard;
import ch.fhnw.edu.efficientalgorithms.chessboard.Position;
import ch.fhnw.edu.efficientalgorithms.chessboard.StatusHolderListener;

/**
 * Updates the fields on a panel, if a new chessboard is loaded.
 * 
 * @author Martin Schaub
 */
public final class ChessBoardLoader implements StatusHolderListener {

	/**
	 * Panel to update.
	 */
	private final GraphicalBoard board;

	/**
	 * Constructor
	 * 
	 * @param board board to update
	 */
	public ChessBoardLoader(final GraphicalBoard board) {
		if (board == null) {
			throw new NullPointerException();
		}
		this.board = board;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.chessboard.StatusHolderListener#newChessBoardLoaded(ch.fhnw.edu.efficientalgorithms
	 * .chessboard.ChessBoard, ch.fhnw.edu.efficientalgorithms.chessboard.ChessBoard)
	 */
	@Override
	public void newChessBoardLoaded(final ChessBoard oldBoard, final ChessBoard newBoard) {
		if (newBoard == null) {
			throw new NullPointerException();
		}

		board.init(newBoard);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.chessboard.StatusHolderListener#algorithmStarted(ch.fhnw.edu.efficientalgorithms
	 * .chessboard.ChessBoardAlgorithm)
	 */
	@Override
	public void algorithmStarted(final ChessBoardAlgorithm algorithm) {
		// nothing to do
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.chessboard.StatusHolderListener#algorithmStopped(ch.fhnw.edu.efficientalgorithms
	 * .chessboard.ChessBoardAlgorithm)
	 */
	@Override
	public void algorithmStopped(final ChessBoardAlgorithm algorithm) {
		// nothing to do
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.chessboard.StatusHolderListener#positionSelected(ch.fhnw.edu.efficientalgorithms
	 * .chessboard.Position)
	 */
	@Override
	public void positionSelected(final Position position) {
		// nothing to do
	}
}
