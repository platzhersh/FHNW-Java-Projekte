package ch.fhnw.edu.efficientalgorithms.lineintersection;

/**
 * An AlgorithmListener is an observer of the execution process of an algorithm.
 * 
 * @author Martin Schaub
 */
public interface AlgorithmListener {

	/**
	 * The algorithm has been launched.
	 */
	void processStarted();

	/**
	 * The algorithm has finished its work.
	 */
	void processEnded();

	/**
	 * For some algorithms its important to report, where they currently stand.
	 * 
	 * @param point current position
	 */
	void currentCoordinate(Point point);

	/**
	 * Algorithms can report some special objects, like results.
	 * 
	 * @param object reported object
	 */
	void reportObject(Object object);
}
