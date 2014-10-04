package ch.fhnw.edu.efficientalgorithms.geneticalgorithms.gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * Exits the application.
 * 
 * @author Martin Schaub
 */
public class ExitAction extends AbstractAction {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -1082874836854651936L;

	/**
	 * Exits the application
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		System.exit(0);
	}

}
