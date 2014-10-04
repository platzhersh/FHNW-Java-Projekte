package ch.fhnw.edu.efficientalgorithms.geneticalgorithms;

import java.util.List;

/**
 * Represents an observer of ProgramStatus objects.
 * 
 * @author Martin Schaub
 */
public interface ProgramStatusListener {

	/**
	 * A point was recorded.
	 * 
	 * @param point recorded point
	 */
	void pointAdded(Point point);

	/**
	 * All points were cleared.
	 * 
	 * @param deletedPoints all deleted points
	 */
	void pointsCleared(List<Point> deletedPoints);
}
