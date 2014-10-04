package ch.fhnw.edu.efficientalgorithms.tsp;

import java.util.Set;

/**
 * Defines the MVC model for the metric TSP problem.
 * 
 * @author Martin Schaub
 */
public interface TSPModel {

	/**
	 * Returns all currently saved locations
	 * 
	 * @return all locations
	 */
	Set<Location> getLocations();

	/**
	 * Gets the current set solution. Can be null initially.
	 * 
	 * @return current solution
	 */
	TSPSolution getSolution();

	/**
	 * Set a new solution.
	 * 
	 * @param solution new solution to set
	 */
	void setSolution(TSPSolution solution);

	/**
	 * Decides weather a location is saved at this coordinate or not.
	 * 
	 * @param coordinate coordinate to lookup
	 * @param tolerance how much tolerance in the lookup is requested
	 * 
	 * @return the location or null if no location was there. If multiple locations are there, one is chosen randomly.
	 */
	Location getLocationAt(Point coordinate, int tolerance);

	/**
	 * Adds a location to the model
	 * 
	 * @param location location to add
	 */
	void addLocation(Location location);

	/**
	 * Removes a location from the model.
	 * 
	 * @param location location to remove
	 */
	void removeLocation(Location location);

	/**
	 * Adds a new listener to the model. It has set semantics.
	 * 
	 * @param listener listener to add
	 */
	void addTSPModelLister(TSPModelListener listener);

	/**
	 * Removes a listener from the model.
	 * 
	 * @param listener listener to remove
	 */
	void removeTSPModelListener(TSPModelListener listener);

}
