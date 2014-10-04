package ch.fhnw.edu.efficientalgorithms.chessboard.gui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JLabel;

import ch.fhnw.edu.efficientalgorithms.chessboard.ChessBoard;
import ch.fhnw.edu.efficientalgorithms.chessboard.ChessBoardAlgorithm;
import ch.fhnw.edu.efficientalgorithms.chessboard.Position;
import ch.fhnw.edu.efficientalgorithms.chessboard.StatusHolderListener;

/**
 * Updates the statusfield according to the current status from the statusholder.
 * 
 * @author Martin Schaub
 */
public final class StatusUpdater implements StatusHolderListener {

	/**
	 * Label that is updated with the current status.
	 */
	private final JLabel labelToUpdate;

	/**
	 * Start date of the algorithm.
	 */
	private Calendar startDate;

	/**
	 * Constructor
	 * 
	 * @param labelToUpdate label that is updated
	 */
	public StatusUpdater(final JLabel labelToUpdate) {
		if (labelToUpdate == null) {
			throw new NullPointerException();
		}
		this.labelToUpdate = labelToUpdate;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.chessboard.StatusHolderListener#algorithmStarted(ch.fhnw.edu.efficientalgorithms
	 * .chessboard.ChessBoardAlgorithm)
	 */
	@Override
	public void algorithmStarted(final ChessBoardAlgorithm algorithm) {
		startDate = Calendar.getInstance();
		DateFormat formater = new SimpleDateFormat("yy.MM.dd HH:mm:ss");
		labelToUpdate.setText("Algorithm " + algorithm.toString() + " started at " + formater.format(startDate.getTime()));
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.chessboard.StatusHolderListener#algorithmStopped(ch.fhnw.edu.efficientalgorithms
	 * .chessboard.ChessBoardAlgorithm)
	 */
	@Override
	public void algorithmStopped(final ChessBoardAlgorithm algorithm) {
		// cannot use the date formatter for the time difference, because it starts at 01:00 on 1. Jan 1970 and not on
		// 00:00.
		long startMillis = startDate.getTimeInMillis();
		long curMillis = System.currentTimeMillis();
		long runTime = curMillis - startMillis;

		long runMillis = runTime % 1000;
		runTime = (runTime - runMillis) / 1000;
		long runSecs = runTime % 60;
		runTime = (runTime - runSecs) / 60;
		long runMins = runTime % 60;
		runTime = (runTime - runMins) / 60;
		long runHours = runTime;
		String timeString = String.format("%02d:%02d:%02d.%03d", runHours, runMins, runSecs, runMillis);

		labelToUpdate.setText("Algorithm " + algorithm.toString() + " finished. Running time " + timeString
				+ " [HH:mm:ss.SSS]");
		startDate = null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.chessboard.StatusHolderListener#newChessBoardLoaded(ch.fhnw.edu.efficientalgorithms
	 * .chessboard.ChessBoard, ch.fhnw.edu.efficientalgorithms.chessboard.ChessBoard)
	 */
	@Override
	public void newChessBoardLoaded(final ChessBoard oldBoard, final ChessBoard newBoard) {
		labelToUpdate.setText("New board with " + newBoard.getNumOfRows() + " rows and " + newBoard.getNumOfColumns()
				+ " columns loaded");
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
