package ch.fhnw.edu.efficientalgorithms.geneticalgorithms;

/**
 * Represents a immutable point. Two points must be equal, if they represent the same coordinate.
 * 
 * @author Martin Schaub
 */
public interface Point {

	/**
	 * Gets the x coordinate.
	 * 
	 * @return the x coordinate
	 */
	int getX();

	/**
	 * Gets the y coordinate.
	 * 
	 * @return the y coordinate
	 */
	int getY();

}
