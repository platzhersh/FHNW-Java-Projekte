package ch.fhnw.edu.efficientalgorithms.chessboard.impl;

import java.util.LinkedList;
import java.util.List;

import ch.fhnw.edu.efficientalgorithms.chessboard.ChessBoard;
import ch.fhnw.edu.efficientalgorithms.chessboard.ChessBoardListener;
import ch.fhnw.edu.efficientalgorithms.chessboard.Field;
import ch.fhnw.edu.efficientalgorithms.chessboard.Position;

/**
 * Implementation of the chessboard.
 * 
 * @author Martin Schaub
 */
public final class ChessBoardImpl implements ChessBoard {

	/**
	 * Stores all registered listeners
	 */
	private final List<ChessBoardListener> listeners = new LinkedList<ChessBoardListener>();
	/**
	 * Stores the content of the field.
	 */
	private final Field[][] fieldContent;

	/**
	 * Number of rows
	 */
	private final int numOfRows;
	/**
	 * Number of rows.
	 */
	private final int numOfColumns;

	/**
	 * Constructor
	 * 
	 * @param numOfRows number of columns
	 * @param numOfColumns number of rows
	 */
	public ChessBoardImpl(final int numOfRows, final int numOfColumns) {
		if (numOfColumns <= 0 || numOfRows <= 0) {
			throw new IllegalArgumentException();
		}
		this.numOfRows = numOfRows;
		this.numOfColumns = numOfColumns;

		fieldContent = new Field[numOfRows][numOfColumns];
		for (int i = 0; i < numOfRows; ++i) {
			for (int j = 0; j < numOfColumns; ++j) {
				fieldContent[i][j] = new KnightsTourField();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.chessboard.ChessBoard#addCheeseBoardListener(ch.fhnw.edu.efficientalgorithms.chessboard
	 * .ChessBoardListener)
	 */
	@Override
	public void addCheeseBoardListener(final ChessBoardListener listener) {
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
	 * ch.fhnw.edu.efficientalgorithms.chessboard.ChessBoard#removeCheeseBoardListener(ch.fhnw.edu.efficientalgorithms
	 * .chessboard.ChessBoardListener)
	 */
	@Override
	public void removeCheeseBoardListener(final ChessBoardListener listener) {
		if (listener == null) {
			throw new NullPointerException();
		}
		listeners.remove(listener);
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.chessboard.ChessBoard#getNumOfColumns()
	 */
	@Override
	public int getNumOfColumns() {
		return numOfColumns;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.chessboard.ChessBoard#getNumOfRows()
	 */
	@Override
	public int getNumOfRows() {
		return numOfRows;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.chessboard.ChessBoard#getField(ch.fhnw.edu.efficientalgorithms.chessboard.Position)
	 */
	@Override
	public Field getField(final Position position) {
		if (position == null) {
			throw new NullPointerException();
		}
		if (position.getRow() >= numOfRows || position.getColumn() >= numOfColumns) {
			throw new IllegalArgumentException();
		}
		return fieldContent[position.getRow()][position.getColumn()];
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.chessboard.ChessBoard#setField(ch.fhnw.edu.efficientalgorithms.chessboard.Position,
	 * ch.fhnw.edu.efficientalgorithms.chessboard.Field)
	 */
	@Override
	public void setField(final Position position, final Field status) {
		if (position == null) {
			throw new NullPointerException();
		}
		if (position.getRow() >= numOfRows || position.getColumn() >= numOfColumns) {
			throw new IllegalArgumentException();
		}

		fieldContent[position.getRow()][position.getColumn()] = status;

		for (ChessBoardListener listener : listeners) {
			listener.fieldChanged(this, position);
		}
	}
}
