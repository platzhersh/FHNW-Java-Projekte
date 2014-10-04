package ch.fhnw.edu.efficientalgorithms.chessboard.gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import ch.fhnw.edu.efficientalgorithms.chessboard.ChessBoard;
import ch.fhnw.edu.efficientalgorithms.chessboard.ChessBoardAlgorithm;
import ch.fhnw.edu.efficientalgorithms.chessboard.Position;
import ch.fhnw.edu.efficientalgorithms.chessboard.StatusHolderListener;

/**
 * Stops an algorithm execution.
 * 
 * @author Martin Schaub
 */
public class StopAction extends AbstractAction implements StatusHolderListener {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -7677369152912098864L;

	/**
	 * Algorithm to stop.
	 */
	private ChessBoardAlgorithm algorithmToStop;
	/**
	 * Has the algorithm already been stopped.
	 */
	private boolean alreadyStoppped = false;

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		if (alreadyStoppped) {
			JOptionPane.showMessageDialog(null, "Algorithm has already been stopped", "Error stopping",
					JOptionPane.ERROR_MESSAGE);
		}
		else if (algorithmToStop == null) {
			JOptionPane.showMessageDialog(null, "No algorihm is running", "Error stopping", JOptionPane.ERROR_MESSAGE);
		}
		else {
			alreadyStoppped = true;
			algorithmToStop.stop();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.chessboard.StatusHolderListener#algorithmStarted(ch.fhnw.edu.efficientalgorithms
	 * .chessboard.ChessBoardAlgorithm)
	 */
	@Override
	public void algorithmStarted(final ChessBoardAlgorithm algorithm) {
		algorithmToStop = algorithm;
		alreadyStoppped = false;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.chessboard.StatusHolderListener#algorithmStopped(ch.fhnw.edu.efficientalgorithms
	 * .chessboard.ChessBoardAlgorithm)
	 */
	@Override
	public void algorithmStopped(final ChessBoardAlgorithm algorithm) {
		algorithmToStop = null;
		alreadyStoppped = false;
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
	public void positionSelected(final Position position) {
		// nothing to do
	}
}
