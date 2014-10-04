package ch.fhnw.edu.efficientalgorithms.lineintersection;

import java.util.Set;

/**
 * Defines a plain used to hold several lines.
 * 
 * @author Martin Schaub
 */
public interface Plane {

	/**
	 * Adds a new line to the plane.
	 * 
	 * @param line the line to add.
	 */
	void addLine(Line line);

	/**
	 * Removes a line from the plane.
	 * 
	 * @param line the line to remove
	 */
	void removeLine(Line line);

	/**
	 * Gets all lines on the plane.
	 * 
	 * @return a set of planes.
	 */
	Set<Line> getLines();

	/**
	 * Removes all lines from the plain.
	 */
	void clear();

	/**
	 * Registers a new listener.
	 * 
	 * @param listener listener to register.
	 */
	void addPlaneListener(PlaneListener listener);

	/**
	 * Deregisters a listener.
	 * 
	 * @param listener listener to deregister.
	 */
	void removePlaneListener(PlaneListener listener);
}
