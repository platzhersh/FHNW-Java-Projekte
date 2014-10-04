package ch.fhnw.edu.efficientalgorithms.tsp;

/**
 * Represents an algorithm that solves or approximates the TSP problem.
 * 
 * @author Martin Schaub
 */
public interface TSPAlgorithm {

	/**
	 * Starts the algorithm.
	 * 
	 * @param model model to use
	 */
	void start(TSPModel model);

	/**
	 * Can the data be modified during the algorithm is running.
	 * 
	 * @return true if it can otherwise false
	 */
	boolean allowsModification();
}
