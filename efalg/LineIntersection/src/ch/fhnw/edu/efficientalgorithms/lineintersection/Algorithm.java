package ch.fhnw.edu.efficientalgorithms.lineintersection;

/**
 * Interface for various geometric algorithms.
 * 
 * @author Martin Schaub
 */
public interface Algorithm {

	/**
	 * Actually executes the algorithm.
	 */
	void start();

	/**
	 * Registers a new listener so that he can follow the status of the algorithm.
	 * 
	 * @param listener listener to add.
	 */
	void addProgressListener(AlgorithmListener listener);

	/**
	 * Removes a listener.
	 * 
	 * @param listener listener to remove.
	 */
	void removeProgressListener(AlgorithmListener listener);

}
