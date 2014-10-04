package ch.fhnw.edu.efficientalgorithms.chessboard.gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * Exits the application.
 * 
 * @author Martin Schaub
 */
public final class ExitAction extends AbstractAction {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -2521606013171952758L;

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		System.exit(0);
	}

}
