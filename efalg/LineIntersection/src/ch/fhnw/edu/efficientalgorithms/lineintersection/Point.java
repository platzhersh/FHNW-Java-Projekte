package ch.fhnw.edu.efficientalgorithms.lineintersection;

/**
 * Defines a point in the Euclidean plane. This point must be immutable.
 * 
 * @author Martin Schaub
 */
public interface Point extends Comparable<Point> {

	/**
	 * Gets the point's X coordinate.
	 * @return the X coordinate.
	 */
	int getX();

	/**
	 * Gets the point's Y coordinate
	 * @return the Y coordinate.
	 */
	int getY();
}
