package ch.fhnw.edu.efficientalgorthims.sudoku.impl.gui;

import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ch.fhnw.edu.efficientalgorthims.sudoku.SudokuBoard;
import ch.fhnw.edu.efficientalgorthims.sudoku.SudokuGUIListener;
import ch.fhnw.edu.efficientalgorthims.sudoku.impl.SudokuBoardImpl;

/**
 * Provides the data set for a JList. In this case the input board as well as all solution boards are displayed. The
 * input board is at index 0.
 *
 * This class acts also as an listener for changes in the selection of the list.
 *
 * @author Martin Schaub
 */
public final class BoardListModel extends AbstractListModel implements SudokuGUIListener, ListSelectionListener {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -6481064275874356466L;

	/**
	 * Stores all solutions.
	 */
	private final List<SudokuBoard> solutions = new LinkedList<SudokuBoard>();
	/**
	 * Saved the loaded board.
	 */
	private SudokuBoard loaded = new SudokuBoardImpl();
	/**
	 * Stores all registered listeners
	 */
	private final List<SudokuGUIListener> listeners = new LinkedList<SudokuGUIListener>();

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
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	@Override
	public Object getElementAt(final int index) {
		if (index == 0) {
			return "Input";
		}
		return "Solution " + index;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.ListModel#getSize()
	 */
	@Override
	public int getSize() {
		return solutions.size() + 1;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorthims.sudoku.SudokuGUIListener#boardLoaded(ch.fhnw.edu.efficientalgorthims.sudoku.SudokuBoard
	 * )
	 */
	@Override
	public void boardLoaded(final SudokuBoard newBoard) {
		if (newBoard == null) {
			throw new NullPointerException();
		}
		loaded = newBoard;
	}

	/*
	 * (non-Javadoc)
	 * @seech.fhnw.edu.efficientalgorthims.sudoku.SudokuGUIListener#boardSelected(ch.fhnw.edu.efficientalgorthims.sudoku.
	 * SudokuBoard)
	 */
	@Override
	public void boardSelected(final SudokuBoard board) {
		// nothing to do
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorthims.sudoku.SudokuGUIListener#solvingEnded(java.util.List)
	 */
	@Override
	public void solvingEnded(final List<SudokuBoard> solution) {
		if (solution == null) {
			throw new NullPointerException();
		}
		solutions.addAll(solution);
		fireIntervalAdded(this, 1, solutions.size() + 1);
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorthims.sudoku.SudokuGUIListener#solvingStarted()
	 */
	@Override
	public void solvingStarted() {
		int size = getSize();
		solutions.clear();
		fireIntervalRemoved(this, 1, size);
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	@Override
	public void valueChanged(final ListSelectionEvent e) {
		if (e.getSource() instanceof JList) {
			JList list = (JList) e.getSource();
			SudokuBoard selected = loaded;
			if (list.getSelectedIndex() > 0) {
				selected = solutions.get(list.getSelectedIndex() - 1);
			}
			for (SudokuGUIListener listener : listeners) {
				listener.boardSelected(selected);
			}
		}
	}
}