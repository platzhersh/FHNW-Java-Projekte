package ch.fhnw.edu.efficientalgorithms.impl.gui;

import javax.swing.JLabel;

import ch.fhnw.edu.efficientalgorithms.tsp.Location;
import ch.fhnw.edu.efficientalgorithms.tsp.TSPModelListener;
import ch.fhnw.edu.efficientalgorithms.tsp.TSPSolution;

/**
 * Updates the status label.
 * 
 * @author Martin Schaub
 */
public final class StatusUpdater implements TSPModelListener {

	/**
	 * Label to update.
	 */
	private final JLabel label;

	/**
	 * Constructor
	 * 
	 * @param label label to update.
	 */
	public StatusUpdater(final JLabel label) {
		this.label = label;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.tsp.TSPModelListener#locationAdded(ch.fhnw.edu.efficientalgorithms.tsp.Location)
	 */
	@Override
	public void locationAdded(final Location location) {
		// nothing to do
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.tsp.TSPModelListener#locationRemoved(ch.fhnw.edu.efficientalgorithms.tsp.Location)
	 */
	@Override
	public void locationRemoved(final Location location) {
		// nothing to do
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.tsp.TSPModelListener#newSolution(ch.fhnw.edu.efficientalgorithms.tsp.TSPSolution)
	 */
	@Override
	public void newSolution(final TSPSolution solution) {
		String str = String.format("Length %5.3f for solution %d", solution.getLength(), solution
				.getCurrentSolutionNumber());
		label.setText(str);
	}
}
