package ch.fhnw.edu.efficientalgorthims.sudoku.impl.gui;

import javax.swing.JLabel;

import ch.fhnw.edu.efficientalgorthims.sudoku.SudokuBoard;
import ch.fhnw.edu.efficientalgorthims.sudoku.impl.Position;

/**
 * Updates a label, when the sudoku board has been changed.
 * 
 * @author Martin Schaub
 */
public final class LabelUpdater extends AbstractSudokuGUIListener {

	/**
	 * Label to update.
	 */
	private final JLabel label;
	/**
	 * Labels position
	 */
	private final Position position;

	/**
	 * Constructor
	 * 
	 * @param label field to update
	 * @param position position to update
	 */
	public LabelUpdater(final JLabel label, final Position position) {
		if (label == null || position == null) {
			throw new NullPointerException();
		}
		this.label = label;
		this.position = position;
	}

	/*
	 * (non-Javadoc)
	 * @seech.fhnw.edu.efficientalgorthims.sudoku.SudokuGUIListener#boardSelected(ch.fhnw.edu.efficientalgorthims.sudoku.
	 * SudokuBoard)
	 */
	@Override
	public void boardSelected(final SudokuBoard board) {
		if (board == null) {
			throw new NullPointerException();
		}
		label.setText(board.getValue(position).toString());
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorthims.sudoku.impl.gui.AbstractSudokuGUIListener#boardLoaded(ch.fhnw.edu.efficientalgorthims
	 * .sudoku.SudokuBoard)
	 */
	@Override
	public void boardLoaded(final SudokuBoard newBoard) {
		if (newBoard == null) {
			throw new NullPointerException();
		}
		label.setText(newBoard.getValue(position).toString());
	}

}