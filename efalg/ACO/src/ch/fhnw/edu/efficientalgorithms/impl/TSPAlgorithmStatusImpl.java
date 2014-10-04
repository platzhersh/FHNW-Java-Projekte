package ch.fhnw.edu.efficientalgorithms.impl;

import ch.fhnw.edu.efficientalgorithms.tsp.TSPAlgorithm;
import ch.fhnw.edu.efficientalgorithms.tsp.TSPAlgorithmStatus;

/**
 * Implementation of the TSP algorithm status.
 * 
 * @author Martin Schaub
 */
public final class TSPAlgorithmStatusImpl implements TSPAlgorithmStatus {

	/**
	 * Stores the current status.
	 */
	private Status status = Status.NO_ALGORITHM_RUNNING;
	/**
	 * Stores the currently running algorithm.
	 */
	private TSPAlgorithm currentlyRunning = null;

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.tsp.TSPAlgorithmStatus#getCurrentlyRunningAlgorithm()
	 */
	@Override
	public synchronized TSPAlgorithm getCurrentlyRunningAlgorithm() {
		return currentlyRunning;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.tsp.TSPAlgorithmStatus#getStatus()
	 */
	@Override
	public synchronized Status getStatus() {
		return status;
	}

	/*
	 * (non-Javadoc)
	 * @see ch.fhnw.edu.efficientalgorithms.tsp.TSPAlgorithmStatus#setAlgorithmFinished()
	 */
	@Override
	public synchronized void setAlgorithmFinished() {
		status = Status.NO_ALGORITHM_RUNNING;
		currentlyRunning = null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * ch.fhnw.edu.efficientalgorithms.tsp.TSPAlgorithmStatus#setAlgorithmRunning(ch.fhnw.edu.efficientalgorithms.tsp.
	 * TSPAlgorithm)
	 */
	@Override
	public synchronized void setAlgorithmRunning(final TSPAlgorithm algorithm) {
		if (algorithm == null) {
			throw new NullPointerException();
		}
		status = Status.ALGORITHM_RUNNING;
		currentlyRunning = algorithm;
	}

}
