package ch.fhnw.edu.efficientalgorthims.sudoku.impl.gui;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

import ch.fhnw.edu.efficientalgorthims.sudoku.SudokuBoard;
import ch.fhnw.edu.efficientalgorthims.sudoku.SudokuGUIListener;
import ch.fhnw.edu.efficientalgorthims.sudoku.SudokuSolver;
import ch.fhnw.edu.efficientalgorthims.sudoku.impl.SudokuBacktrackSolver;
import ch.fhnw.edu.efficientalgorthims.sudoku.impl.SudokuBoardImpl;

/**
 * Solves a given sudoku.
 *
 * @author Martin Schaub
 */
public final class SolveAction extends AbstractSudokuGUIAction implements Runnable {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 6061799014852294055L;

	/**
	 * Stores the loaded board. First this is initialized with a empty one.
	 */
	private SudokuBoard loaded = new SudokuBoardImpl();

	/**
	 * To protect it from other GUI actions
	 */
	private final Object lock;

	/**
	 *
	 * Constructor
	 *
	 * @param lock lock to prevent other actions to influence this one.
	 */
	public SolveAction(final Object lock) {
		this.lock = lock;
	}

	/**
	 * Solves a sudoku.
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		// Runs in a thread, so the program does not look crashed during calculation
		new Thread(this).start();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorthims.sudoku.impl.gui.AbstractSudokuGUIAction#boardLoaded(ch.fhnw.edu.efficientalgorthims
	 * .sudoku.SudokuBoard)
	 */
	@Override
	public void boardLoaded(final SudokuBoard newBoard) {
		if (newBoard == null) {
			throw new NullPointerException();
		}
		loaded = newBoard;
	}

	/**
	 * Solves a sudoku.
	 *
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		synchronized (lock) {
			SudokuSolver solver = new SudokuBacktrackSolver();

			for (final SudokuGUIListener listener : listeners) {
				listener.solvingStarted();
			}

			List<SudokuBoard> solutions = solver.solve(loaded.clone());
			if (solutions == null) {
				throw new NullPointerException();
			}
			validate(solutions);

			for (final SudokuGUIListener listener : listeners) {
				listener.solvingEnded(new ArrayList<SudokuBoard>(solutions));
			}
		}
	}

	/**
	 * Validates if all solutions are possible and all are different.
	 *
	 * @param solutions solutions to check
	 */
	private void validate(final List<SudokuBoard> solutions) {
		Set<SudokuBoard> solutionSet = new HashSet<SudokuBoard>(solutions);
		if (solutionSet.size() != solutions.size()) {
			JOptionPane.showMessageDialog(null, "There are duplicates in the produced solutions", "Implementation error",
					JOptionPane.ERROR_MESSAGE);
		}

		for (SudokuBoard board : solutions) {
			for (int i = 0; i < 9; ++i) {
				// Check rows
				final List<Integer> row = board.getRow(i);
				if (hasDuplicates(row)) {
					JOptionPane.showMessageDialog(null, "The following solution has multiple times the same value in row " + i
							+ "\n" + board.toString(), "Implementation error", JOptionPane.ERROR_MESSAGE);
				}
				else if (row.size() != 9) {
					JOptionPane.showMessageDialog(null, "There are values missing in row " + i + "\n" + board.toString(),
							"Implementation error", JOptionPane.ERROR_MESSAGE);
				}
				else {
					// Check columns
					final List<Integer> column = board.getColumn(i);
					if (hasDuplicates(column)) {
						JOptionPane.showMessageDialog(null, "The following solution has multiple times the same value in column "
								+ i + "\n" + board.toString(), "Implementation error", JOptionPane.ERROR_MESSAGE);
					}
					else if (column.size() != 9) {
						JOptionPane.showMessageDialog(null, "There are values missing in column " + i + "\n" + board.toString(),
								"Implementation error", JOptionPane.ERROR_MESSAGE);
					}
					else {
						// Check fields
						final List<Integer> field = board.getField(i);
						if (hasDuplicates(field)) {
							JOptionPane.showMessageDialog(null, "The following solution has multiple times the same value in field "
									+ i + "\n" + board.toString(), "Implementation error", JOptionPane.ERROR_MESSAGE);
						}
						else if (field.size() != 9) {
							JOptionPane.showMessageDialog(null, "There are values missing in field " + i + "\n" + board.toString(),
									"Implementation error", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		}
	}

	/**
	 * Private helper method that searches a list for duplicates.
	 *
	 * @param solutions list to check
	 * @return true if the list has duplicates and else otherwise.
	 */
	private boolean hasDuplicates(final List<Integer> solutions) {
		// No order is defined on the values, therefore this O(n^2) algorithm is used. But for 9 elements, it is not that
		// bad
		for (int i = 0; i < solutions.size(); ++i) {
			for (int j = i + 1; j < solutions.size(); ++j) {
				if (solutions.get(i).equals(solutions.get(j))) {
					return true;
				}
			}
		}
		return false;
	}
}
