package ch.fhnw.edu.efficientalgorthims.sudoku.impl.gui;

import java.awt.event.ActionEvent;

/**
 * Action that exits the program.
 *
 * @author Martin Schaub
 */
public final class ExitAction extends AbstractSudokuGUIAction {
	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 4336120206326989842L;

	/**
	 * Exits the program.
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		System.exit(0);
	}
}
