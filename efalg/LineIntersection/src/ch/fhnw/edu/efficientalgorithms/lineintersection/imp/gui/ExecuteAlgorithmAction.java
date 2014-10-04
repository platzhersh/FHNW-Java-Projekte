package ch.fhnw.edu.efficientalgorithms.lineintersection.imp.gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import ch.fhnw.edu.efficientalgorithms.lineintersection.Algorithm;
import ch.fhnw.edu.efficientalgorithms.lineintersection.Drawer;

/**
 * 
 * @author Martin Schaub
 */
public final class ExecuteAlgorithmAction extends AbstractAction implements Runnable {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -8120429046345954167L;

	/**
	 * Executes this algorithm upon action.
	 */
	private final Algorithm algorithm;
	/**
	 * To clean up previously added things.
	 */
	private final Drawer drawer;
	/**
	 * Lock to prevent multiple executions and other destructive things during execution
	 */
	private final Object lock;

	/**
	 * 
	 * Constructor
	 * 
	 * @param algorithm to execute
	 * @param drawer drawer to clean up upon start$
	 * @param lock to lock that allows a disruption free environment
	 */
	public ExecuteAlgorithmAction(final Algorithm algorithm, final Drawer drawer, final Object lock) {
		if (algorithm == null || drawer == null || lock == null) {
			throw new NullPointerException();
		}
		this.algorithm = algorithm;
		this.drawer = drawer;
		this.lock = lock;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		new Thread(this).start();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		synchronized (lock) {
			drawer.clearAllDrawables();
			algorithm.start();
		}
	}
}
