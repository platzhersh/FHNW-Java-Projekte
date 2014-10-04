package ch.fhnw.edu.efficientalgorithms.tsp;

/**
 * Defines a vertex in the TSP problem. Since it is metric, it can seen as a location in the plane.
 * 
 * @author Martin Schaub
 */
public interface Location {

	/**
	 * Gets the location's label. The label mustn't be empty.
	 * 
	 * @return location's label
	 */
	String getLabel();

	/**
	 * Gets the coordinate of the location
	 * 
	 * @return point's coordinate
	 */
	Point getCoordinate();
}
