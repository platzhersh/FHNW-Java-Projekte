package ch.fhnw.edu.efficientalgorithms.impl.gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import ch.fhnw.edu.efficientalgorithms.tsp.TSPAlgorithm;
import ch.fhnw.edu.efficientalgorithms.tsp.TSPAlgorithmStatus;
import ch.fhnw.edu.efficientalgorithms.tsp.TSPModel;
import ch.fhnw.edu.efficientalgorithms.tsp.TSPAlgorithmStatus.Status;

/**
 * Starts an algorithm.
 * 
 * @author Martin Schaub
 */
public final class StartAlgorithmAction extends AbstractAction implements Runnable {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 4457589384191393516L;

	/**
	 * Model to execute the algorithm on.
	 */
	private final TSPModel model;
	/**
	 * Algorithm to execute.
	 */
	private final TSPAlgorithm algorithm;
	/**
	 * To look if an algorithm is currently running.
	 */
	private final TSPAlgorithmStatus status;

	/**
	 * Constructor
	 * 
	 * @param algorithm algorithm to start,
	 * @param model TSP model on which the algorithm is executed
	 * @param status to check and update the current algorithm status.
	 */
	public StartAlgorithmAction(final TSPAlgorithm algorithm, final TSPModel model, final TSPAlgorithmStatus status) {
		if (algorithm == null || model == null || status == null) {
			throw new NullPointerException();
		}
		this.algorithm = algorithm;
		this.model = model;
		this.status = status;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		synchronized (status) {
			if (status.getStatus().equals(Status.ALGORITHM_RUNNING)) {
				JOptionPane
						.showMessageDialog(null, "An algorithm is already running", "Error executing", JOptionPane.OK_OPTION);
			}
			else {
				status.setAlgorithmRunning(algorithm);
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
		algorithm.start(model);
		status.setAlgorithmFinished();
	}
}
