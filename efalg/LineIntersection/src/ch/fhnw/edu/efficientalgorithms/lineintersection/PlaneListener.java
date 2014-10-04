package ch.fhnw.edu.efficientalgorithms.lineintersection;

/**
 * Listens for changes of the plane.
 * 
 * @author Martin Schaub
 */
public interface PlaneListener {

	/**
	 * Triggered by removing lines from the plane.
	 * 
	 * @param line removed line.
	 */
	void removed(Line line);

	/**
	 * Triggered by adding lines to a plane.
	 * 
	 * @param line added line.
	 */
	void added(Line line);
}
