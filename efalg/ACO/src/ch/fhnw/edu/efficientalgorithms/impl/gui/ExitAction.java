package ch.fhnw.edu.efficientalgorithms.impl.gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;

/**
 * Closes the window.
 * 
 * @author Martin Schaub
 */
public final class ExitAction extends AbstractAction {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 7102987740572150830L;

	/**
	 * Frame that will be closed
	 */
	private final JFrame frameToExit;

	/**
	 * Constructor
	 * 
	 * @param frameToExit frame to close
	 */
	public ExitAction(final JFrame frameToExit) {
		if (frameToExit == null) {
			throw new NullPointerException();
		}
		this.frameToExit = frameToExit;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		// Exits the application as long as EXIT_ON_CLOSE is the close behavior. But then its better
		// than just System.exit(0)
		frameToExit.dispose();
	}

}
