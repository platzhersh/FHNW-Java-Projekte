package ch.fhnw.edu.efficientalgorithms.lineintersection;

/**
 * Represents a line. The line must be implemeted immutable.
 * 
 * @author Martin Schaub
 */
public interface Line {

	/**
	 * Gets the start point of a line.
	 * 
	 * @return the start point.
	 */
	Point getStartPoint();

	/**
	 * Gets the endpoint of the line.
	 * 
	 * @return the endpoint.
	 */
	Point getEndPoint();

}
