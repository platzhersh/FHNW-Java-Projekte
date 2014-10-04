package ch.fhnw.edu.efficientalgorthims.sudoku.impl.gui;

import java.util.List;

import javax.swing.JLabel;

import ch.fhnw.edu.efficientalgorthims.sudoku.SudokuBoard;

/**
 * Updates a label with the current status.
 * 
 * @author Martin Schaub
 */
public class StatusUpdater extends AbstractSudokuGUIListener {

	/**
	 * Label to update.
	 */
	private final JLabel status;

	/**
	 * 
	 * Constructor
	 * 
	 * @param status label to update
	 */
	public StatusUpdater(final JLabel status) {
		this.status = status;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorthims.sudoku.impl.gui.AbstractSudokuGUIListener#boardLoaded(ch.fhnw.edu.efficientalgorthims
	 * .sudoku.SudokuBoard)
	 */
	@Override
	public void boardLoaded(final SudokuBoard newBoard) {
		status.setText("status: loaded");
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorthims.sudoku.impl.gui.AbstractSudokuGUIListener#solvingEnded(java.util.List)
	 */
	@Override
	public void solvingEnded(final List<SudokuBoard> solution) {
		status.setText("status: solved");
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorthims.sudoku.impl.gui.AbstractSudokuGUIListener#solvingStarted()
	 */
	@Override
	public void solvingStarted() {
		status.setText("status: solving...");
	}
}
