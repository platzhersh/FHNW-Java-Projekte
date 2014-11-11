package ch.fhnw.edu.efficientalgorithms.graph.gui.impl.action;

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
	private static final long serialVersionUID = 3296801549726913449L;

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		System.exit(0);
	}
}
