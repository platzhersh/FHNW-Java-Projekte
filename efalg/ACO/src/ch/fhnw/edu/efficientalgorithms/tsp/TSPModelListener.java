package ch.fhnw.edu.efficientalgorithms.tsp;

/**
 * Listens for changes in the TSP model.
 * 
 * @author Martin Schaub
 */
public interface TSPModelListener {

	/**
	 * New Solution was created.
	 * 
	 * @param solution new solution
	 */
	void newSolution(TSPSolution solution);

	/**
	 * A location was added to the model.
	 * 
	 * @param location new location
	 */
	void locationAdded(Location location);

	/**
	 * A location was removed from the model
	 * 
	 * @param location removed location
	 */
	void locationRemoved(Location location);

}
