package ch.fhnw.edu.efficientalgorithms.geneticalgorithms;

/**
 * Represents an observer of an algorithm.
 * 
 * @author Martin Schaub
 */
public interface InterpolationAlgorithmListener {

	/**
	 * This callback method is invoked after the algorithm was started.
	 */
	void algorithmStarted();

	/**
	 *The algorithmFinished method is invoked after the algorithm finished its job.
	 * 
	 * @param endResult final result of the algorithm
	 */
	void algorithmFinished(Function endResult);

	/**
	 * An algorithm may want to report intermediate results. This callback method is then invoked.
	 * 
	 * @param intermediateResult intermediate result of the algorithm
	 */
	void newResult(Function intermediateResult);

}
