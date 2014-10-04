package ch.fhnw.edu.efficientalgorithms.chessboard.gui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import ch.fhnw.edu.efficientalgorithms.chessboard.ChessBoard;
import ch.fhnw.edu.efficientalgorithms.chessboard.ChessBoardAlgorithm;
import ch.fhnw.edu.efficientalgorithms.chessboard.ChessBoardListener;
import ch.fhnw.edu.efficientalgorithms.chessboard.Position;
import ch.fhnw.edu.efficientalgorithms.chessboard.StatusHolderListener;

/**
 * Updates the fields values on the chessboard, when the chessboard was changed.
 * 
 * @author Martin Schaub
 */
public final class ChessBoardFieldUpdater implements ChessBoardListener, StatusHolderListener {

	/**
	 * Label to update.
	 */
	private final JLabel label;
	/**
	 * Label's position.
	 */
	private final Position position;

	/**
	 * Constructor
	 * 
	 * @param label label to update
	 * @param position position of the label
	 */
	public ChessBoardFieldUpdater(final JLabel label, final Position position) {
		if (label == null || position == null) {
			throw new NullPointerException();
		}
		this.label = label;
		this.position = position;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.chessboard.ChessBoardListener#fieldChanged(ch.fhnw.edu.efficientalgorithms.chessboard
	 * .ChessBoard, ch.fhnw.edu.efficientalgorithms.chessboard.Position)
	 */
	@Override
	public void fieldChanged(final ChessBoard board, final Position eventPosition) {
		// nothing to do
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
	 * ch.fhnw.edu.efficientalgorithms.chessboard.StatusHolderListener#newChessBoardLoaded(ch.fhnw.edu.efficientalgorithms
	 * .chessboard.ChessBoard, ch.fhnw.edu.efficientalgorithms.chessboard.ChessBoard)
	 */
	@Override
	public void newChessBoardLoaded(final ChessBoard oldBoard, final ChessBoard newBoard) {
		// nothing to do
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.chessboard.StatusHolderListener#positionSelected(ch.fhnw.edu.efficientalgorithms
	 * .chessboard.Position)
	 */
	@Override
	public void positionSelected(final Position selectedPos) {
		if (selectedPos.equals(position)) {
			label.setBorder(BorderFactory.createLineBorder(Color.RED));
		}
		else {
			label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		}
		label.getParent().repaint();
	}
}
