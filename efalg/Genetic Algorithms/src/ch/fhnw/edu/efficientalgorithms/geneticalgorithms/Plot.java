package ch.fhnw.edu.efficientalgorithms.geneticalgorithms;

/**
 * Simple plot. Normally a huge plotting framework like jfreeplot would be used.
 * 
 * @author Martin Schaub
 */
public interface Plot {

	/**
	 * Adds a function to the plot
	 * 
	 * @param function to plot
	 */
	void addFunction(Function function);

	/**
	 * Removes a function from the plot. If the function is plotted multiple times only one instance is removed.
	 * 
	 * @param function to remove
	 */
	void removeFunction(Function function);

	/**
	 * Adds a new point to the plot.
	 * 
	 * @param point point to add
	 */
	void addPoint(Point point);

	/**
	 * Removes a point from the list (if it was inside multiple times, it will be removed only once).
	 * 
	 * @param point point to remove
	 */
	void removePoint(Point point);

	/**
	 * Translates a point from the panel representing the plot to a coordinate in the plot. This is necessary, because
	 * Java uses a different coordinate system.
	 * 
	 * @param point point to translate
	 * @return the translated point
	 */
	Point getCoordinateOf(Point point);

}
