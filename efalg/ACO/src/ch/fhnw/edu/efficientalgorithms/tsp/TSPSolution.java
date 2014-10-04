package ch.fhnw.edu.efficientalgorithms.tsp;

import java.util.List;

/**
 * Represents a solution of the TSP problem.
 * 
 * @author Martin Schaub
 */
public interface TSPSolution {

	/**
	 * Gets the represented tour.
	 * 
	 * @return current tour
	 */
	List<Location> getTour();

	/**
	 * Returns the how many-th solution this is.
	 * 
	 * @return the number of solutions before.
	 */
	int getCurrentSolutionNumber();

	/**
	 * Gets the tour length
	 * 
	 * @return tour length
	 */
	double getLength();
}
