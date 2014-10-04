package ch.fhnw.edu.efficientalgorithms.tsp;

import java.util.List;

/**
 * Allows to change settings of a algorithm.
 * 
 * @author Martin Schaub
 */
public interface TSPAlgorithmTunable {

	/**
	 * Gets the TSP algorithm this class tunes.
	 * 
	 * @return tuned algorithm
	 */
	TSPAlgorithm getAlgorithm();

	/**
	 * Gets all tunable attributes.
	 * 
	 * @return a list of all those attributes.
	 */
	List<String> getAttributes();

	/**
	 * A attribute has changed to this value. If the value is correct, null is returned an otherwise a error string.
	 * 
	 * @param attribute which attribute has been changed
	 * @param newValue the new value
	 * 
	 * @return null if the value is OK or an error string otherwise.
	 */
	String valueChanged(String attribute, String newValue);

	/**
	 * Gets the current value of the attribute.
	 * 
	 * @param attribute attribute to get
	 * 
	 * @return current value
	 */
	String getValue(String attribute);

	/**
	 * Adds an observer.
	 * 
	 * @param listener observer to add.
	 */
	void addTSPAlgorithmTunableListener(TSPAlgorithmTunableListener listener);

	/**
	 * Removes an listener.
	 * 
	 * @param listener oberserver to remove.
	 */
	void removeTSPAlgorithmTunableListener(TSPAlgorithmTunableListener listener);
}
