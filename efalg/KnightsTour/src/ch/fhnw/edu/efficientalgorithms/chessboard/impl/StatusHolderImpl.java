package ch.fhnw.edu.efficientalgorithms.chessboard.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ch.fhnw.edu.efficientalgorithms.chessboard.ChessBoard;
import ch.fhnw.edu.efficientalgorithms.chessboard.ChessBoardAlgorithm;
import ch.fhnw.edu.efficientalgorithms.chessboard.Position;
import ch.fhnw.edu.efficientalgorithms.chessboard.StatusHolder;
import ch.fhnw.edu.efficientalgorithms.chessboard.StatusHolderListener;

/**
 * Implementation of the chessboard holder.
 * 
 * @author Martin Schaub
 */
public final class StatusHolderImpl implements StatusHolder {

	/**
	 * Stores all registered listeners.
	 */
	private final List<StatusHolderListener> listeners = new LinkedList<StatusHolderListener>();

	/**
	 * Current chessboard
	 */
	private ChessBoard chessBoard;
	/**
	 * Currently running algorithm
	 */
	private ChessBoardAlgorithm runningAlgorithm;
	/**
	 * Stors the currently selected position on the board.
	 */
	private Position selectedPosition;

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.chessboard.StatusHolder#getChessBoard()
	 */
	@Override
	public synchronized ChessBoard getChessBoard() {
		return chessBoard;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.chessboard.StatusHolder#setChessBoard(ch.fhnw.edu.efficientalgorithms.chessboard
	 * .ChessBoard)
	 */
	@Override
	public synchronized void setChessBoard(final ChessBoard chessBoard) {
		if (chessBoard == null) {
			throw new NullPointerException();
		}

		ChessBoard old = this.chessBoard;
		this.chessBoard = chessBoard;
		selectedPosition = null;

		for (StatusHolderListener listener : new ArrayList<StatusHolderListener>(listeners)) {
			listener.newChessBoardLoaded(old, chessBoard);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.chessboard.StatusHolder#addStatusHolderListener(ch.fhnw.edu.efficientalgorithms
	 * .chessboard.StatusHolderListener)
	 */
	@Override
	public synchronized void addStatusHolderListener(final StatusHolderListener listener) {
		if (listener == null) {
			throw new NullPointerException();
		}
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.chessboard.StatusHolder#removeStatusHolderListener(ch.fhnw.edu.efficientalgorithms
	 * .chessboard.StatusHolderListener)
	 */
	@Override
	public synchronized void removeStatusHolderListener(final StatusHolderListener listener) {
		if (listener == null) {
			throw new NullPointerException();
		}
		listeners.remove(listener);
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.chessboard.StatusHolder#isAlgorithmRunning()
	 */
	@Override
	public synchronized boolean isAlgorithmRunning() {
		return runningAlgorithm != null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.chessboard.StatusHolder#algorithmStarted(ch.fhnw.edu.efficientalgorithms.chessboard
	 * .ChessBoardAlgorithm)
	 */
	@Override
	public synchronized void algorithmStarted(final ChessBoardAlgorithm algorithm) {
		if (algorithm == null) {
			throw new NullPointerException();
		}

		runningAlgorithm = algorithm;

		for (StatusHolderListener listener : listeners) {
			listener.algorithmStarted(algorithm);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.chessboard.StatusHolder#algorithmStopped()
	 */
	@Override
	public synchronized void algorithmStopped() {
		if (runningAlgorithm == null) {
			throw new IllegalStateException("No algorithm was running");
		}

		ChessBoardAlgorithm algo = runningAlgorithm;
		runningAlgorithm = null;

		for (StatusHolderListener listener : listeners) {
			listener.algorithmStopped(algo);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.chessboard.StatusHolder#getSelectedPosition()
	 */
	@Override
	public Position getSelectedPosition() {
		return selectedPosition;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.chessboard.StatusHolder#setSelectedPosition(ch.fhnw.edu.efficientalgorithms.chessboard
	 * .Position)
	 */
	@Override
	public void setSelectedPosition(final Position position) {
		if (position == null) {
			throw new NullPointerException();
		}
		selectedPosition = position;

		for (StatusHolderListener listener : listeners) {
			listener.positionSelected(position);
		}
	}

}
