package ch.fhnw.edu.efficientalgorithms.geneticalgorithms;

/**
 * Represents a function of order 1.
 * 
 * @author Martin Schaub
 */
public interface Function extends Cloneable {

	/**
	 * Evaluates the function at point x.
	 * 
	 * @param x where the function wants to be evaluated
	 * @return evaluation result
	 */
	double getValueAt(double x);

	/**
	 * Deep copy of the object.
	 * 
	 * @return a new copy
	 */
	Function clone();

}
