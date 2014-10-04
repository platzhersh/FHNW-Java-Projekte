package ch.fhnw.edu.efficientalgorithms.geneticalgorithms.gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import ch.fhnw.edu.efficientalgorithms.geneticalgorithms.InterpolationAlgorithm;
import ch.fhnw.edu.efficientalgorithms.geneticalgorithms.ProgramStatus;
import ch.fhnw.edu.efficientalgorithms.geneticalgorithms.ProgramStatus.Status;

/**
 * Executes a algorithm.
 *
 * @author Martin Schaub
 */
public final class ExecuteAlgorithmAction extends AbstractAction implements Runnable {

	/**
	 *Serial Version UID
	 */
	private static final long serialVersionUID = -8443807331406898434L;

	/**
	 * Algorithm to execute.
	 */
	private final InterpolationAlgorithm algorithm;
	/**
	 * Program status to get all recorded points.
	 */
	private final ProgramStatus status;

	/**
	 * Constructor
	 *
	 * @param algorithm algorithm to execute
	 * @param status program status to get all recorded points
	 */
	public ExecuteAlgorithmAction(final InterpolationAlgorithm algorithm, final ProgramStatus status) {
		if (algorithm == null || status == null) {
			throw new NullPointerException();
		}
		this.algorithm = algorithm;
		this.status = status;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		synchronized (status) {
			if (!status.getStatus().equals(Status.Recording)) {
				JOptionPane.showMessageDialog(null, "An algorithm is already running", "Could not start",
						JOptionPane.ERROR_MESSAGE);
			}
			else {
				status.setStatus(Status.AlgorithmRunning);
				new Thread(this).start();
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

		algorithm.start(status.getPoints());

		synchronized (status) {
			status.setStatus(Status.Recording);
		}
	}

}
