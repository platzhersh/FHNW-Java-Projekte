package ch.fhnw.edu.efficientalgorithms.chessboard.impl;

import ch.fhnw.edu.efficientalgorithms.chessboard.Position;

/**
 * Simple but immutable Position implementation
 * 
 * @author Martin Schaub
 */
public class PositionImpl implements Position {

	/**
	 * Represented column.
	 */
	private final int column;
	/**
	 * Represented row.
	 */
	private final int row;

	/**
	 * Constructor
	 * 
	 * @param row represented row
	 * @param column represented column
	 */
	public PositionImpl(final int row, final int column) {
		if (column < 0 || row < 0) {
			throw new IllegalArgumentException();
		}
		this.column = column;
		this.row = row;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.chessboard.Position#getColumn()
	 */
	@Override
	public int getColumn() {
		return column;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.chessboard.Position#getRow()
	 */
	@Override
	public int getRow() {
		return row;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "(" + row + "/" + column + ")";
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		PositionImpl other = (PositionImpl) obj;
		if (column != other.column) {
			return false;
		}
		if (row != other.row) {
			return false;
		}
		return true;
	}

}
