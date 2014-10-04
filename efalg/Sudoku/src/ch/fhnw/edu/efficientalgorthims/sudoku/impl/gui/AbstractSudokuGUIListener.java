package ch.fhnw.edu.efficientalgorthims.sudoku.impl.gui;

import java.util.List;

import ch.fhnw.edu.efficientalgorthims.sudoku.SudokuBoard;
import ch.fhnw.edu.efficientalgorthims.sudoku.SudokuGUIListener;

/**
 * Abstract base class that implements all methods empty. The needed methods can be overwritten by subclasses if needed.
 * 
 * @author Martin Schaub
 */
public abstract class AbstractSudokuGUIListener implements SudokuGUIListener {

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorthims.sudoku.SudokuGUIListener#boardLoaded(ch.fhnw.edu.efficientalgorthims.sudoku.SudokuBoard
	 * )
	 */
	@Override
	public void boardLoaded(final SudokuBoard newBoard) {
		// no implementation
	}

	/*
	 * (non-Javadoc)
	 * @seech.fhnw.edu.efficientalgorthims.sudoku.SudokuGUIListener#boardSelected(ch.fhnw.edu.efficientalgorthims.sudoku.
	 * SudokuBoard)
	 */
	public void boardSelected(final SudokuBoard board) {
		// no implementation
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorthims.sudoku.SudokuGUIListener#solvingEnded(java.util.List)
	 */
	public void solvingEnded(final List<SudokuBoard> solution) {
		// no implementation
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorthims.sudoku.SudokuGUIListener#solvingStarted()
	 */
	public void solvingStarted() {
		// no implementation
	}

}
