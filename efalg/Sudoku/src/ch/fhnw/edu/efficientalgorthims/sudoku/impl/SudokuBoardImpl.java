package ch.fhnw.edu.efficientalgorthims.sudoku.impl;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import ch.fhnw.edu.efficientalgorthims.sudoku.SudokuBoard;

/**
 * Simple implementation of the board.
 * 
 * @author Martin Schaub
 */
public final class SudokuBoardImpl implements SudokuBoard {

	/**
	 * Stores the board's data.
	 */
	private Value[][] board = new Value[9][9];

	/**
	 * Constructor - creates a new empty board
	 */
	public SudokuBoardImpl() {
		clear();
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorthims.sudoku.SudokuBoard#getColumn(int)
	 */
	@Override
	public List<Integer> getColumn(final int column) {
		List<Integer> list = new LinkedList<Integer>();
		for (int i = 0; i < 9; ++i) {
			Value v = board[column / 3 + (i / 3) * 3][column % 3 + (i % 3) * 3];
			if (!v.isEmpty()) {
				list.add(v.getValue());
			}
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorthims.sudoku.SudokuBoard#getPlayfield(int)
	 */
	@Override
	public List<Integer> getField(final int field) {
		List<Integer> list = new LinkedList<Integer>();
		for (int i = 0; i < 9; ++i) {
			Value v = board[field][i];
			if (!v.isEmpty()) {
				list.add(v.getValue());
			}
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorthims.sudoku.SudokuBoard#getRow(int)
	 */
	@Override
	public List<Integer> getRow(final int row) {
		List<Integer> list = new LinkedList<Integer>();
		for (int i = 0; i < 9; ++i) {
			Value v = board[(row / 3) * 3 + i / 3][(row % 3) * 3 + i % 3];
			if (!v.isEmpty()) {
				list.add(v.getValue());
			}
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorthims.sudoku.SudokuBoard#getValue(ch.fhnw.edu.efficientalgorthims.sudoku.Position)
	 */
	@Override
	public Value getValue(final Position pos) {
		return board[pos.getFieldNumber()][pos.getPositionNumber()];
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorthims.sudoku.SudokuBoard#setValue(ch.fhnw.edu.efficientalgorthims.sudoku.Position,
	 * ch.fhnw.edu.efficientalgorthims.sudoku.Value)
	 */
	@Override
	public void setValue(final Position pos, final Value val) {
		board[pos.getFieldNumber()][pos.getPositionNumber()] = val;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorthims.sudoku.SudokuBoard#clear()
	 */
	@Override
	public void clear() {
		for (int i = 0; i < board.length; ++i) {
			for (int j = 0; j < board[i].length; ++j) {
				board[i][j] = new Value();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public SudokuBoardImpl clone() {
		try {
			SudokuBoardImpl ret = (SudokuBoardImpl) super.clone();
			ret.board = Arrays.copyOf(board, board.length);
			for (int i = 0; i < ret.board.length; ++i) {
				ret.board[i] = Arrays.copyOf(board[i], board[i].length);
			}
			return ret;
		}
		catch (CloneNotSupportedException e) {
			throw new InternalError("Cloning not supported?");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		for (int i = 0; i < board.length; ++i) {
			for (int j = 0; j < board[i].length; ++j) {
				result = prime * result + board[i][j].hashCode();
			}
		}
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
		SudokuBoardImpl other = (SudokuBoardImpl) obj;
		// Array.equals does not work! Deep equal is needed!
		if (board.length != other.board.length) {
			return false;
		}
		for (int i = 0; i < board.length; ++i) {
			if (board[i].length != other.board[i].length) {
				return false;
			}
			for (int j = 0; j < board.length; ++j) {
				if (!board[i][j].equals(other.board[i][j])) {
					return false;
				}
			}
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer(10 * 9);
		for (int j = 0; j < 9; ++j) {
			for (int i = 0; i < 9; ++i) {
				Value v = board[(j / 3) * 3 + i / 3][(j % 3) * 3 + i % 3];
				if (!v.isEmpty()) {
					buf.append(v.getValue());
				}
				else {
					buf.append('N');
				}
			}
			buf.append('\n');
		}
		return buf.toString();
	}

}
