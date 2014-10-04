package ch.fhnw.edu.efficientalgorithms.tsp;

/**
 * Listener interface for the TSPAlgorithm tunables
 * 
 * @author Martin Schaub
 */
public interface TSPAlgorithmTunableListener {

	/**
	 * The tunable with the according attribute has changed.
	 * 
	 * @param attribute attribute that was changed
	 */
	void tunableChanged(String attribute);

}
