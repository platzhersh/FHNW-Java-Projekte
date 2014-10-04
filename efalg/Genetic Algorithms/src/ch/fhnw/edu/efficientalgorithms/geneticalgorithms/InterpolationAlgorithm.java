package ch.fhnw.edu.efficientalgorithms.geneticalgorithms;

import java.util.List;

/**
 * Represents an algorithm for interpolation. The algorithm status is observable.
 * 
 * @author Martin Schaub
 */
public interface InterpolationAlgorithm {

	/**
	 * Starts the algorithm. This method might be called multiple times, serially on the object.
	 * 
	 * @param points marked points
	 */
	void start(List<Point> points);

	/**
	 * Adds an observer of the algorithm status.
	 * 
	 * @param listener observer to register
	 */
	void addAlgorithmListener(InterpolationAlgorithmListener listener);

	/**
	 * Removes an observer, so it does not receive any status reports anymore.
	 * 
	 * @param listener listener to deregister
	 */
	void removeAlgorithmListener(InterpolationAlgorithmListener listener);
}
