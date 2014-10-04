package ch.fhnw.edu.efficientalgorthims.sudoku.impl.gui;

import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractAction;

import ch.fhnw.edu.efficientalgorthims.sudoku.SudokuBoard;
import ch.fhnw.edu.efficientalgorthims.sudoku.SudokuGUIListener;

/**
 * Abstract base class for actions that need to send and receive events.
 *
 * @author Martin Schaub
 */
public abstract class AbstractSudokuGUIAction extends AbstractAction implements SudokuGUIListener {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -8125838367333605484L;

	/**
	 * Stores all registered listeners
	 */
	protected List<SudokuGUIListener> listeners = new LinkedList<SudokuGUIListener>();

	/**
	 * Adds a new listener.
	 * @param listener new listener.
	 */
	public void addSudokuGUIListener(final SudokuGUIListener listener) {
		if (listener == null) {
			throw new NullPointerException();
		}
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	/**
	 * Removes a listener.
	 * @param listener listener to remove
	 */
	public void removeSudokuGUIListener(final SudokuGUIListener listener) {
		if (listener == null) {
			throw new NullPointerException();
		}
		listeners.remove(listener);
	}

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
	@Override
	public void boardSelected(final SudokuBoard board) {
		// no implementation
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorthims.sudoku.SudokuGUIListener#solvingEnded(java.util.List)
	 */
	@Override
	public void solvingEnded(final List<SudokuBoard> solution) {
		// no implementation
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorthims.sudoku.SudokuGUIListener#solvingStarted()
	 */
	@Override
	public void solvingStarted() {
		// no implementation
	}

}
