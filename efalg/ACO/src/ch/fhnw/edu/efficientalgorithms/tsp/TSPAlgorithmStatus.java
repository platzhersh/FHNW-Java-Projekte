package ch.fhnw.edu.efficientalgorithms.tsp;

/**
 * Represents the status of the calculation.
 * 
 * @author Martin Schaub
 */
public interface TSPAlgorithmStatus {

	/**
	 * Different states in which the model can reside
	 */
	enum Status {
		/**
		 * No algorithm is running.
		 */
		NO_ALGORITHM_RUNNING,
		/**
		 * An algorithm is running.
		 */
		ALGORITHM_RUNNING
	}

	/**
	 * Gets the current status.
	 * 
	 * @return the current status
	 */
	Status getStatus();

	/**
	 * The algorithm is started.
	 * 
	 * @param algorithm the currently running algorithm.
	 */
	void setAlgorithmRunning(TSPAlgorithm algorithm);

	/**
	 * Returns the currently running algorithm
	 * 
	 * @return returns the currently running algorithm or null if no one is running
	 */
	TSPAlgorithm getCurrentlyRunningAlgorithm();

	/**
	 * The currently running algorithm has finished.
	 */
	void setAlgorithmFinished();
}
