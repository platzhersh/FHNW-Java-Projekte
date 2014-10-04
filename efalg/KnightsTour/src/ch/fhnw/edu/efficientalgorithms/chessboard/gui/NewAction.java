package ch.fhnw.edu.efficientalgorithms.chessboard.gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import ch.fhnw.edu.efficientalgorithms.chessboard.StatusHolder;
import ch.fhnw.edu.efficientalgorithms.chessboard.impl.ChessBoardImpl;

/**
 * Loads a new chessboard.
 * 
 * @author Martin Schaub
 */
public class NewAction extends AbstractAction {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -384512846691154771L;
	/**
	 * To update the new chessboard.
	 */
	private final StatusHolder holder;
	/**
	 * Number of rows a new chessboard has.
	 */
	private final int numOfRows;
	/**
	 * Number of columns a new chessboard will have.
	 */
	private final int numOfColumns;

	/**
	 * Constructor
	 * 
	 * @param holder holder to set the new chessboard
	 * @param numOfRows number of rows a newly created chessboard will have
	 * @param numOfColumns how many columns new chessboards will have
	 */
	public NewAction(final StatusHolder holder, final int numOfRows, final int numOfColumns) {
		if (holder == null) {
			throw new NullPointerException();
		}
		if (numOfRows < 0 || numOfColumns < 0) {
			throw new IllegalArgumentException();
		}
		this.holder = holder;
		this.numOfRows = numOfRows;
		this.numOfColumns = numOfColumns;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		synchronized (holder) {
			if (!holder.isAlgorithmRunning()) {
				holder.setChessBoard(new ChessBoardImpl(numOfRows, numOfColumns));
			}
			else {
				JOptionPane.showMessageDialog(null, "Cannot load a new board, because an algorithm is currently running",
						"Erorr loading", JOptionPane.OK_OPTION);
			}
		}
	}
}
