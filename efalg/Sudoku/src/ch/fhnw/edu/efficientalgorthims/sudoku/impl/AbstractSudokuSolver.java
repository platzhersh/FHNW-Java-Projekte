package ch.fhnw.edu.efficientalgorthims.sudoku.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import ch.fhnw.edu.efficientalgorthims.sudoku.SudokuBoard;
import ch.fhnw.edu.efficientalgorthims.sudoku.SudokuSolver;

/**
 * Common base class that provides some confort for actual implementations.
 *
 * @author Martin Schaub
 */
public abstract class AbstractSudokuSolver implements SudokuSolver {

	/**
	 * Stores the evaluated results.
	 */
	final List<SudokuBoard> solutions = new LinkedList<SudokuBoard>();

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorthims.sudoku.SudokuSolver#solve(ch.fhnw.edu.efficientalgorthims.sudoku.SudokuBoard)
	 */
	public synchronized List<SudokuBoard> solve(final SudokuBoard input) {
		solutions.clear();

		calculateSolutions(input.clone());

		return Collections.unmodifiableList(new ArrayList<SudokuBoard>(solutions));
	}

	/**
	 * Calculates the actual solutions.
	 *
	 * @param input input sudoko board
	 */
	protected abstract void calculateSolutions(SudokuBoard input);

	/**
	 * Adds a new solution.
	 *
	 * @param solution to add
	 */
	protected final void addSolution(final SudokuBoard solution) {
		solutions.add(solution);
	}

	/**
	 * Checks if no rule is violated by using the value.
	 *
	 * @param fieldNumber field that is updated with a new value
	 * @param elementNumber element that is updated with the new value
	 * @param input sudoku board to test
	 * @param val value to test
	 * @return true, if its ok to use this value at this location and false otherwise
	 */
	protected final boolean isPossible(final int fieldNumber, final int elementNumber, final SudokuBoard input, final int val) {
		if (!input.getField(fieldNumber).contains(val)) {
			if (!input.getColumn((fieldNumber % 3) * 3 + elementNumber % 3).contains(val)) {
				if (!input.getRow((fieldNumber / 3) * 3 + elementNumber / 3).contains(val)) {
					return true;
				}
			}
		}
		return false;
	}

}
