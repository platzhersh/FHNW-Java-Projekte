package ch.fhnw.edu.efficientalgorthims.sudoku;

import java.util.List;

import ch.fhnw.edu.efficientalgorthims.sudoku.impl.Position;
import ch.fhnw.edu.efficientalgorthims.sudoku.impl.Value;

/**
 * Stores the data of a sudoku board.
 * 
 * @author Martin Schaub
 */
public interface SudokuBoard extends Cloneable {

	/**
	 * Sets a new value at the given position. The value must be between 1 and 9.
	 * 
	 * @param pos position
	 * @param val value
	 */
	void setValue(Position pos, Value val);

	/**
	 * Gets the board value from the submitted position.
	 * 
	 * @param pos position to fetch.
	 * @return value.
	 */
	Value getValue(Position pos);

	/**
	 * Clears all values from the board.
	 */
	void clear();

	/**
	 * Helper method to get all values of a field.
	 * 
	 * @param field field number to get the values from.
	 * @return all numbers.
	 */
	List<Integer> getField(final int field);

	/**
	 * Gets all numbers from a column.
	 * 
	 * @param column selected column.
	 * @return all numbers.
	 */
	List<Integer> getColumn(final int column);

	/**
	 * Gets all numbers from a row.
	 * 
	 * @param row selected row
	 * @return all numbers.
	 */
	List<Integer> getRow(final int row);

	/**
	 * Clones the current board
	 * 
	 * @return clone of the current board.
	 */
	SudokuBoard clone();
}
