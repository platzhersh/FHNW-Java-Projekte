package ch.fhnw.edu.efficientalgorithms.geneticalgorithms.gui;

import javax.swing.JLabel;

import ch.fhnw.edu.efficientalgorithms.geneticalgorithms.Function;
import ch.fhnw.edu.efficientalgorithms.geneticalgorithms.InterpolationAlgorithmListener;
import ch.fhnw.edu.efficientalgorithms.geneticalgorithms.Point;
import ch.fhnw.edu.efficientalgorithms.geneticalgorithms.ProgramStatus;

/**
 * Updates a status label.
 *
 * @author Martin Schaub
 */
public final class StatusUpdater implements InterpolationAlgorithmListener {

	/**
	 * Label to update
	 */
	private final JLabel labelToUpdate;
	/**
	 * Program status to get the reference points
	 */
	private final ProgramStatus programStatus;
	/**
	 * Number of intermediate results.
	 */
	private int numberOfResults;

	/**
	 * Constructor
	 *
	 * @param labelToUpdate label to update.
	 * @param programStatus program status to get the reference points.
	 */
	public StatusUpdater(final JLabel labelToUpdate, final ProgramStatus programStatus) {
		if (labelToUpdate == null || programStatus == null) {
			throw new NullPointerException();
		}
		this.labelToUpdate = labelToUpdate;
		this.programStatus = programStatus;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.geneticalgorithms.InterpolationAlgorithmListener#algorithmFinished(ch.fhnw.edu.
	 * efficientalgorithms.geneticalgorithms.Function)
	 */
	@Override
	public void algorithmFinished(final Function endResult) {
		if (endResult == null) {
			throw new NullPointerException();
		}
		labelToUpdate.setText("Status: algorithm finished after " + numberOfResults
				+ " intermediate results, total deviation is " + getDeviation(endResult) + ", best function is " + endResult);
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.geneticalgorithms.InterpolationAlgorithmListener#algorithmStarted()
	 */
	@Override
	public void algorithmStarted() {
		labelToUpdate.setText("Status: algorithm started");
		numberOfResults = 1;
	}

	/*
	 * (non-Javadoc)
	 * @seech.fhnw.edu.efficientalgorithms.geneticalgorithms.InterpolationAlgorithmListener#newResult(ch.fhnw.edu.
	 * efficientalgorithms.geneticalgorithms.Function)
	 */
	@Override
	public void newResult(final Function intermediateResult) {
		if (intermediateResult == null) {
			throw new NullPointerException();
		}
		labelToUpdate.setText("Status: algorithm running," + numberOfResults + " intermediate results, total deviation  "
				+ getDeviation(intermediateResult) + ", best function is " + intermediateResult);
		numberOfResults++;
	}

	/**
	 * Gets the squared deviation of the function at the reference points.
	 *
	 * @param toCheck function to check
	 * @return deviation as string
	 */
	private String getDeviation(final Function toCheck) {
		double dev = 0;
		synchronized (programStatus) {
			for (Point point : programStatus.getPoints()) {
				dev += Math.pow(toCheck.getValueAt(point.getX()) - point.getY(), 2);
			}
		}
		return String.format("%.7e", dev);
	}
}
